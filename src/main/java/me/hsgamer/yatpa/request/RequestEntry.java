package me.hsgamer.yatpa.request;

import java.util.UUID;

public class RequestEntry {
    public final UUID requester;
    public final UUID target;
    public final RequestType type;
    public final long timestamp = System.currentTimeMillis();
    private boolean done = false;

    public RequestEntry(UUID requester, UUID target, RequestType type) {
        this.requester = requester;
        this.target = target;
        this.type = type;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
