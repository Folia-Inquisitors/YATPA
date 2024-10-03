package me.hsgamer.yatpa.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TeleportPlayerListEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final List<String> playerNames;

    public TeleportPlayerListEvent(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
