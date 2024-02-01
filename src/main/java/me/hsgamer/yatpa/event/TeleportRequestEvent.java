package me.hsgamer.yatpa.event;

import me.hsgamer.yatpa.request.RequestEntry;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TeleportRequestEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final RequestEntry requestEntry;
    private boolean cancelled;

    public TeleportRequestEvent(RequestEntry requestEntry) {
        this.requestEntry = requestEntry;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    public RequestEntry getRequestEntry() {
        return requestEntry;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
