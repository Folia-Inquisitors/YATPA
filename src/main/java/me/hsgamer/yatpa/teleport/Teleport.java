package me.hsgamer.yatpa.teleport;

import me.hsgamer.yatpa.teleport.impl.AsyncTeleport;
import me.hsgamer.yatpa.teleport.impl.SyncTeleport;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public interface Teleport {
    static Teleport get() {
        return AsyncTeleport.isSupported() ? new AsyncTeleport() : new SyncTeleport();
    }

    CompletableFuture<Boolean> teleport(Player player, Location target);
}
