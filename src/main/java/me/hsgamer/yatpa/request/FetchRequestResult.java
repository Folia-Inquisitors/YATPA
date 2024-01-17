package me.hsgamer.yatpa.request;

import java.util.Optional;

public class FetchRequestResult {
    private final RequestEntry latestRequest;
    private final boolean hasManyRequests;

    public FetchRequestResult(RequestEntry latestRequest, boolean hasManyRequests) {
        this.latestRequest = latestRequest;
        this.hasManyRequests = hasManyRequests;
    }

    public Optional<RequestEntry> getLatestRequest() {
        return Optional.ofNullable(latestRequest);
    }

    public boolean hasManyRequests() {
        return hasManyRequests;
    }
}
