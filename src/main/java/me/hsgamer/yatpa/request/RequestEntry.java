package me.hsgamer.yatpa.request;

import java.util.UUID;

public class RequestEntry {
    public final UUID requester;
    public final UUID target;
    public final RequestType type;
    public final long timestamp = System.currentTimeMillis();

    public RequestEntry(UUID requester, UUID target, RequestType type) {
        this.requester = requester;
        this.target = target;
        this.type = type;
    }
}
