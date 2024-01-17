package me.hsgamer.yatpa.request;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import me.hsgamer.yatpa.YATPA;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class RequestManager {
    private final YATPA plugin;
    private final Table<UUID, UUID, RequestEntry> requestTable; // row: requester, column: target

    public RequestManager(YATPA plugin) {
        this.plugin = plugin;
        this.requestTable = HashBasedTable.create();
    }

    private boolean isValid(RequestEntry requestEntry) {
        long timeout = plugin.getMainConfig().teleportTimeoutMillis();
        return timeout > 0 && requestEntry.timestamp + timeout > System.currentTimeMillis();
    }

    public RequestStatus request(RequestEntry requestEntry) {
        UUID requester = requestEntry.requester;
        UUID target = requestEntry.target;

        RequestEntry currentRequest = requestTable.get(requester, target);
        if (currentRequest != null && isValid(currentRequest)) {
            return RequestStatus.ALREADY_SENT;
        }

        requestTable.put(requester, target, requestEntry);
        return RequestStatus.SUCCESS;
    }

    public FetchRequestResult getRequest(UUID target, @Nullable UUID requester) {
        RequestEntry latestRequest = null;
        boolean hasManyRequests = false;

        if (requester != null) {
            RequestEntry requestEntry = requestTable.get(requester, target);
            if (requestEntry != null && isValid(requestEntry)) {
                latestRequest = requestEntry;
            }
        } else {
            for (RequestEntry requestEntry : requestTable.column(target).values()) {
                if (!isValid(requestEntry)) {
                    continue;
                }

                if (latestRequest == null) {
                    latestRequest = requestEntry;
                } else {
                    if (latestRequest.timestamp < requestEntry.timestamp) {
                        latestRequest = requestEntry;
                    }
                    hasManyRequests = true;
                }
            }
        }

        return new FetchRequestResult(latestRequest, hasManyRequests);
    }
}
