package me.hsgamer.yatpa.teleport;

import me.hsgamer.hscore.bukkit.scheduler.Scheduler;
import me.hsgamer.hscore.bukkit.scheduler.Task;
import me.hsgamer.yatpa.YATPA;
import me.hsgamer.yatpa.request.RequestEntry;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BooleanSupplier;

public class TeleportManager {
    private final Map<UUID, Task> inTeleportMap = new ConcurrentHashMap<>();
    private final Teleport teleport = Teleport.get();
    private final YATPA plugin;
    private Task effectTask;

    public TeleportManager(YATPA plugin) {
        this.plugin = plugin;
    }

    public TeleportResult teleport(RequestEntry requestEntry) {
        Player requester = plugin.getServer().getPlayer(requestEntry.requester);
        Player target = plugin.getServer().getPlayer(requestEntry.target);

        if (requester == null || target == null) {
            return new TeleportResult(TeleportStatus.OFFLINE, null, null, null);
        }

        Player player;
        Player targetPlayer;
        Location targetLocation;
        switch (requestEntry.type) {
            case NORMAL:
                player = requester;
                targetPlayer = target;
                targetLocation = targetPlayer.getLocation();
                break;
            case HERE:
                player = target;
                targetPlayer = requester;
                targetLocation = targetPlayer.getLocation();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestEntry.type);
        }

        if (isInTeleport(player)) {
            return new TeleportResult(TeleportStatus.IN_TELEPORT, player, targetPlayer, targetLocation);
        }

        BooleanSupplier teleportRunnable = new BooleanSupplier() {
            private CompletableFuture<Boolean> teleportFuture;

            @Override
            public boolean getAsBoolean() {
                if (teleportFuture == null) {
                    teleportFuture = teleport.teleport(player, targetLocation);
                    return true;
                } else {
                    return !teleportFuture.isDone();
                }
            }
        };

        TeleportStatus status;
        Task task;
        if (plugin.getMainConfig().hasTeleportDelay()) {
            status = TeleportStatus.SUCCESS_DELAYED;
            task = Scheduler.plugin(plugin).async().runTaskTimer(teleportRunnable, plugin.getMainConfig().teleportDelayTicks(), 0L);
        } else {
            status = TeleportStatus.SUCCESS;
            task = Scheduler.plugin(plugin).async().runTaskTimer(teleportRunnable, 0L, 0L);
        }
        inTeleportMap.put(player.getUniqueId(), task);
        return new TeleportResult(status, player, targetPlayer, targetLocation);
    }

    public boolean isInTeleport(Player player) {
        Task task = inTeleportMap.get(player.getUniqueId());
        return task != null && !task.isCancelled();
    }

    public void cancelTeleport(Player player) {
        Task task = inTeleportMap.remove(player.getUniqueId());
        if (task != null) {
            task.cancel();
        }
    }

    public void init() {
        int effectPeriod = plugin.getMainConfig().teleportEffectPeriod();
        effectTask = Scheduler.plugin(plugin).async().runTaskTimer(() -> {
            for (Map.Entry<UUID, Task> taskEntry : inTeleportMap.entrySet()) {
                Player player = plugin.getServer().getPlayer(taskEntry.getKey());
                Task task = taskEntry.getValue();
                if (player == null || task.isCancelled()) {
                    continue;
                }

                Scheduler.plugin(plugin)
                        .sync()
                        .runEntityTask(
                                player,
                                () -> plugin.getMainConfig().teleportEffect().forEach(effect -> effect.apply(player))
                        );
            }
        }, effectPeriod, effectPeriod);
    }

    public void stop() {
        effectTask.cancel();
        inTeleportMap.values().forEach(Task::cancel);
        inTeleportMap.clear();
    }
}
