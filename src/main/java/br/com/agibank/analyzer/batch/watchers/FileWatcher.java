package br.com.agibank.analyzer.batch.watchers;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/**
 * Monitoring files in directory to news files
 *
 * @author Wenderson Roberto
 */
public class FileWatcher implements Closeable {

    private volatile AtomicBoolean STARTED;
    private final Map<WatchKey, Path> keys;
    private final ExecutorService executorService;
    private final Map<WatchKeyOriginAndEvent, List<BiConsumer<WatcherEvent, String>>> fileConsumerMapRegister;
    private final Map<WatchKeyOriginAndEvent, WatchServiceWalkAndRegisterWrapper> watchServiceMap;

    public FileWatcher(ExecutorService executorService) throws IOException {
        this.STARTED = new AtomicBoolean(Boolean.TRUE);
        this.keys = Collections.synchronizedMap(new HashMap());
        this.fileConsumerMapRegister = Collections.synchronizedMap(new HashMap());
        this.watchServiceMap = Collections.synchronizedMap(new HashMap());
        this.executorService = executorService;
    }

    public FileWatcher() throws IOException {
        this.STARTED = new AtomicBoolean(Boolean.TRUE);
        this.keys = Collections.synchronizedMap(new HashMap());
        this.fileConsumerMapRegister = Collections.synchronizedMap(new HashMap());
        this.watchServiceMap = Collections.synchronizedMap(new HashMap());
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void registerConsumerCreateEvent(File origin, BiConsumer<WatcherEvent, String> fileConsumer) {
        this.registerConsumerWithoutWalkAndRegisterDirectories(origin, fileConsumer, StandardWatchEventKinds.ENTRY_CREATE);
    }

    public void registerConsumerModifyEvent(File origin, BiConsumer<WatcherEvent, String> fileConsumer) {
        this.registerConsumerWithoutWalkAndRegisterDirectories(origin, fileConsumer, StandardWatchEventKinds.ENTRY_MODIFY);
    }

    public void registerConsumerDeleteEvent(File origin, BiConsumer<WatcherEvent, String> fileConsumer) {
        this.registerConsumerWithoutWalkAndRegisterDirectories(origin, fileConsumer, StandardWatchEventKinds.ENTRY_DELETE);
    }

    public void registerConsumerOverflowEvent(File origin, BiConsumer<WatcherEvent, String> fileConsumer) {
        this.registerConsumerWithoutWalkAndRegisterDirectories(origin, fileConsumer, StandardWatchEventKinds.OVERFLOW);
    }

    public void registerConsumerAllEvents(File origin, BiConsumer<WatcherEvent, String> fileConsumer) {
        this.registerConsumerWithoutWalkAndRegisterDirectories(origin, fileConsumer, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
    }

    public void registerConsumerWithWalkAndRegisterDirectories(File origin, BiConsumer<WatcherEvent, String> fileConsumer, WatchEvent.Kind<?>... events) {
        this.registerConsumer(origin, fileConsumer, Boolean.TRUE, events);
    }

    public void registerConsumerWithoutWalkAndRegisterDirectories(File origin, BiConsumer<WatcherEvent, String> fileConsumer, WatchEvent.Kind<?>... events) {
        this.registerConsumer(origin, fileConsumer, Boolean.FALSE, events);
    }

    public void registerConsumer(File origin, BiConsumer<WatcherEvent, String> fileConsumer, Boolean walkAndRegisterDirectories, WatchEvent.Kind<?>... events) {
        if (!origin.exists()) {
            origin.mkdir();
        }

        if (!origin.isDirectory()) {
            throw new RuntimeException(origin.getAbsolutePath() + " is not a directory.");
        } else {
            Path path = Paths.get(origin.getAbsolutePath());
            Arrays.asList(events)
                    .stream()
                    .forEach((event) -> {
                        try {
                            WatchService watchService = path.getFileSystem().newWatchService();
                            WatchKeyOriginAndEvent watchKeyOriginAndEvent = new WatchKeyOriginAndEvent(origin.getAbsolutePath(), event);
                            this.watchServiceMap.put(watchKeyOriginAndEvent, new WatchServiceWalkAndRegisterWrapper(watchService, walkAndRegisterDirectories));
                            this.walkAndRegisterDirectories(watchService, path, event);
                            if (this.fileConsumerMapRegister.containsKey(watchKeyOriginAndEvent)) {
                                this.fileConsumerMapRegister.get(watchKeyOriginAndEvent).add(fileConsumer);
                            } else {
                                List<BiConsumer<WatcherEvent, String>> list = new ArrayList();
                                list.add(fileConsumer);
                                this.fileConsumerMapRegister.put(watchKeyOriginAndEvent, list);
                            }

                        } catch (IOException var10) {
                            throw new RuntimeException("m=registerConsumer, fileConsumer=" + fileConsumer + ", walkAndRegisterDirectories=" + walkAndRegisterDirectories + "watcherEvents=" + Arrays.toString(events), var10);
                        }
            });
        }
    }

    public boolean unregister(File origin, BiConsumer<WatcherEvent, String> fileConsumer, WatchEvent.Kind<?> event) {
        List<BiConsumer<WatcherEvent, String>> consumerList = (List) this.fileConsumerMapRegister.get(new WatchKeyOriginAndEvent(origin.getAbsolutePath(), event));
        return consumerList != null ? consumerList.remove(fileConsumer) : false;
    }

    public void start() {
        this.STARTED.set(Boolean.TRUE);
        this.executorService.submit(() -> {
            while (this.STARTED.get()) {
                this.processEvent();

                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException var2) {
                    throw new RuntimeException(var2);
                }
            }

        });
    }

    private void processEvent() {
        this.watchServiceMap.entrySet().stream().forEach((entry) -> {
            this.executorService.execute(() -> {
                WatchServiceWalkAndRegisterWrapper wrapper = (WatchServiceWalkAndRegisterWrapper) entry.getValue();

                try {
                    WatchKey key = wrapper.getWatchService().take();
                    Path dir = this.createPath(key);
                    this.poolingEvents(wrapper.getWatchService(), key, dir, wrapper.getWalkAndRegisterDirectories());
                    this.checkValid(key);
                } catch (InterruptedException var5) {
                    throw new RuntimeException("m=processEvent, step=\"teke directory\"", var5);
                }
            });
        });
    }

    private Path createPath(WatchKey key) {
        Path dir = (Path) this.keys.get(key);
        if (dir == null) {
            throw new RuntimeException("m=processEvent, step=\"verify directory\" msg=\"WatchKey not recognized\"");
        } else {
            return dir;
        }
    }

    private void checkValid(WatchKey key) {
        boolean valid = key.reset();
        if (!valid) {
            this.keys.remove(key);
        }

    }

    private void poolingEvents(WatchService watchService, WatchKey key, Path dir, Boolean walkAndRegisterDirectories) {
        key.pollEvents().forEach((event) -> {
            WatchEvent.Kind<?> kind = event.kind();
            Path name = (Path) event.context();
            Path child = Paths.get(dir.toString() + File.separatorChar + name.toString());
            this.dispacherEvent(dir.toFile().getAbsolutePath(), kind, child);
            if (kind == StandardWatchEventKinds.ENTRY_CREATE && walkAndRegisterDirectories && Files.isDirectory(child, new LinkOption[0])) {
                try {
                    this.walkAndRegisterDirectories(watchService, child);
                } catch (IOException var9) {
                    throw new RuntimeException("m=processEvent, step=\"kind \"", var9);
                }
            }

        });
    }

    private void dispacherEvent(String origin, WatchEvent.Kind<?> kind, Path child) {
        WatcherEvent watcherEvent = new WatcherEvent(origin, kind, child);
        WatchKeyOriginAndEvent watchKeyOriginAndEvent = new WatchKeyOriginAndEvent(origin, kind);
        this.fileConsumerMapRegister.get(watchKeyOriginAndEvent)
                .stream()
                .forEach((action) -> {
                    String uuid = UUID.randomUUID().toString();
                    action.accept(watcherEvent, uuid);
        });
    }

    private void registerDirectory(WatchService watchService, Path dir, WatchEvent.Kind<?>... events) throws IOException {
        WatchKey key = dir.register(watchService, events);
        this.keys.put(key, dir);
    }

    private void walkAndRegisterDirectories(final WatchService watchService, Path path, final WatchEvent.Kind<?>... events) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                FileWatcher.this.registerDirectory(watchService, dir, events);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public void close() throws IOException {
        this.STARTED.set(Boolean.FALSE);
    }

}
