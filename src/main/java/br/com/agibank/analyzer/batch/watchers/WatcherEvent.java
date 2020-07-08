package br.com.agibank.analyzer.batch.watchers;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class WatcherEvent implements Comparable<WatcherEvent> {
    private final String origin;
    private final WatchEvent.Kind<?> kind;
    private final Path path;

    public WatcherEvent(String origin, WatchEvent.Kind<?> kind, Path path) {
        this.origin = origin;
        this.kind = kind;
        this.path = path;
    }

    public String getOrigin() {
        return this.origin;
    }

    public WatchEvent.Kind<?> getKind() {
        return this.kind;
    }

    public Path getPath() {
        return this.path;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.kind == null) ? 0 : this.kind.hashCode());
        result = 31 * result + ((this.path == null) ? 0 : this.path.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            WatcherEvent other = (WatcherEvent)obj;
            if (this.kind == null) {
                if (other.kind != null) {
                    return false;
                }
            } else if (!this.kind.equals(other.kind)) {
                return false;
            }

            if (this.path == null) {
                if (other.path != null) {
                    return false;
                }
            } else if (!this.path.equals(other.path)) {
                return false;
            }

            return true;
        }
    }

    public String toString() {
        return "WatcherEvent [kind=" + this.kind + ", path=" + this.path + "]";
    }

    public int compareTo(WatcherEvent watcherEvent) {
        return this.path.compareTo(watcherEvent.path) + (this.kind.hashCode() - watcherEvent.kind.hashCode());
    }

}
