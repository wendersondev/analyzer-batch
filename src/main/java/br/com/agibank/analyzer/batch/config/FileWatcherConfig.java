package br.com.agibank.analyzer.batch.config;

import br.com.agibank.analyzer.batch.consumer.FileConsumer;
import br.com.agibank.analyzer.batch.utils.Constants;
import br.com.agibank.analyzer.batch.watchers.FileWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

/**
 * Configuration the fileWatcher with path of files to read
 *
 * @author Wenderson Roberto
 */
@Configuration
public class FileWatcherConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(FileWatcherConfig.class);

    @Value("${file-watcher.path}")
    private String path;

    @Value("${file-watcher.pathWriter}")
    private String pathWriter;

    @Value("${file-watcher.thread-pool}")
    private Integer threadPool;

    @Autowired
    private FileConsumer fileConsumer;

    @Bean
    public FileWatcher configureArchiveFlatFileWatcher() throws IOException {
        LOGGER.info("m=configureArchiveFlatFileWatcher, path={}, threadPool={}", threadPool, path);

        Path pathIn = Paths.get(System.getProperty(Constants.HOMEPATH).concat(path));
        Path pathOut = Paths.get(System.getProperty(Constants.HOMEPATH).concat(pathWriter));

        if(!Files.exists(pathIn)){
            Files.createDirectory(pathIn);
        }

        if(!Files.exists(pathOut)){
            Files.createDirectory(pathOut);
        }

        final FileWatcher fileWatcher = new FileWatcher(Executors.newFixedThreadPool(threadPool));
        fileWatcher.registerConsumerCreateEvent(new File(pathIn.toAbsolutePath().toString()), fileConsumer);
        fileWatcher.start();

        return fileWatcher;
    }


}

