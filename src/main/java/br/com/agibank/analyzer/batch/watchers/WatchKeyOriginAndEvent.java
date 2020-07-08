package br.com.agibank.analyzer.batch.watchers;

import java.nio.file.WatchEvent;

public class WatchKeyOriginAndEvent {
    private final String origin;
    private final WatchEvent.Kind<?> event;

    public WatchKeyOriginAndEvent(String origin, WatchEvent.Kind<?> event) {
        this.origin = origin;
        this.event = event;
    }

    public String getOrigin() {
        return this.origin;
    }

    public WatchEvent.Kind<?> getEvent() {
        return this.event;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + ((this.origin == null) ? 0 : this.origin.hashCode());
        result = 31 * result + ((this.event == null) ? 0 : this.event.hashCode());
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
            WatchKeyOriginAndEvent other = (WatchKeyOriginAndEvent)obj;
            if (this.origin == null) {
                if (other.origin != null) {
                    return false;
                }
            } else if (!this.origin.equals(other.origin)) {
                return false;
            }

            if (this.event == null) {
                if (other.event != null) {
                    return false;
                }
            } else if (!this.event.equals(other.event)) {
                return false;
            }

            return true;
        }
    }

    public String toString() {
        return "WatchKeyOriginAndEvent [origin=" + this.origin + ", event=" + this.event + "]";
    }
}
