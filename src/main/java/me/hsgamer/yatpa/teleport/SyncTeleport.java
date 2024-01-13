package me.hsgamer.yatpa.teleport;

import me.hsgamer.hscore.bukkit.scheduler.Scheduler;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class SyncTeleport implements Teleport {
    @Override
    public CompletableFuture<Boolean> teleport(Player player, Location target) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        Scheduler.current().sync().runEntityTask(player, () -> completableFuture.complete(player.teleport(target)));
        return completableFuture;
    }
}
