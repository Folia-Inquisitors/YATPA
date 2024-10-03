package me.hsgamer.yatpa.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PreTeleportRequestEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final String targetPlayerName;
    private final @Nullable Player targetPlayer;
    private boolean cancelled;

    public PreTeleportRequestEvent(Player player, String targetPlayerName, @Nullable Player targetPlayer) {
        this.player = player;
        this.targetPlayerName = targetPlayerName;
        this.targetPlayer = targetPlayer;
    }

    public static HandlerList getHANDLERS() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    @Nullable
    public Player getTargetPlayer() {
        return targetPlayer;
    }

    public String getTargetPlayerName() {
        return targetPlayerName;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
