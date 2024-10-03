package me.hsgamer.yatpa.event;

import me.hsgamer.yatpa.request.RequestEntry;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TeleportEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RequestEntry requestEntry;

    public TeleportEvent(RequestEntry requestEntry) {
        this.requestEntry = requestEntry;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public RequestEntry getRequestEntry() {
        return requestEntry;
    }
}
