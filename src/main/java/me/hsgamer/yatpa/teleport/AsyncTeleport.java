package me.hsgamer.yatpa.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

public class AsyncTeleport implements Teleport {
    public static boolean isSupported() {
        try {
            Class<?> clazz = Class.forName("org.bukkit.entity.Player");
            Method method = clazz.getMethod("teleportAsync", Location.class);
            return method.getReturnType().equals(CompletableFuture.class);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public CompletableFuture<Boolean> teleport(Player player, Location target) {
        return player.teleportAsync(target);
    }
}
