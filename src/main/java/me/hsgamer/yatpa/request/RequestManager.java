package me.hsgamer.yatpa.request;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

public class RequestManager {
    // row: requester, column: target
    private final Table<UUID, UUID, RequestEntry> requestTable;

    public RequestManager() {
        this.requestTable = HashBasedTable.create();
    }

    public RequestStatus request(RequestEntry requestEntry) {
        UUID requester = requestEntry.requester;
        UUID target = requestEntry.target;

        if (requestTable.contains(requester, target)) {
            return RequestStatus.ALREADY_SENT;
        }

        requestTable.put(requester, target, requestEntry);
        return RequestStatus.SUCCESS;
    }

    public Optional<RequestEntry> getAndRemoveLatestRequest(UUID target) {
        RequestEntry requestEntry = requestTable.column(target).values()
                .stream()
                .max(Comparator.comparingLong(o -> o.timestamp))
                .orElse(null);
        if (requestEntry != null) {
            requestTable.remove(requestEntry.requester, requestEntry.target);
        }
        return Optional.ofNullable(requestEntry);
    }

    public Optional<RequestEntry> getAndRemoveRequest(UUID target, UUID requester) {
        return Optional.ofNullable(requestTable.get(requester, target));
    }
}
