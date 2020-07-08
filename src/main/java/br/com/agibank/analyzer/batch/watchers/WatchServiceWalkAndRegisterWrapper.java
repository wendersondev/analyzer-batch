package br.com.agibank.analyzer.batch.watchers;

import java.nio.file.WatchService;

public class WatchServiceWalkAndRegisterWrapper {
    private final WatchService watchService;
    private final Boolean walkAndRegisterDirectories;

    public WatchServiceWalkAndRegisterWrapper(WatchService watchService, Boolean walkAndRegisterDirectories) {
        this.watchService = watchService;
        this.walkAndRegisterDirectories = walkAndRegisterDirectories;
    }

    public WatchService getWatchService() {
        return this.watchService;
    }

    public Boolean getWalkAndRegisterDirectories() {
        return this.walkAndRegisterDirectories;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.walkAndRegisterDirectories == null) ? 0 : this.walkAndRegisterDirectories.hashCode());
        result = 31 * result + ((this.watchService == null) ? 0 : this.watchService.hashCode());
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
            WatchServiceWalkAndRegisterWrapper other = (WatchServiceWalkAndRegisterWrapper)obj;
            if (this.walkAndRegisterDirectories == null) {
                if (other.walkAndRegisterDirectories != null) {
                    return false;
                }
            } else if (!this.walkAndRegisterDirectories.equals(other.walkAndRegisterDirectories)) {
                return false;
            }

            if (this.watchService == null) {
                if (other.watchService != null) {
                    return false;
                }
            } else if (!this.watchService.equals(other.watchService)) {
                return false;
            }

            return true;
        }
    }

    public String toString() {
        return "WatchServiceWalkAndRegisterWrapper [watchService=" + this.watchService + ", walkAndRegisterDirectories=" + this.walkAndRegisterDirectories + "]";
    }
}
