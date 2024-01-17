package me.hsgamer.yatpa.cooldown;

import me.hsgamer.yatpa.YATPA;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownManager {
    private final YATPA plugin;
    private final Map<UUID, Long> cooldownMap = new ConcurrentHashMap<>();

    public CooldownManager(YATPA plugin) {
        this.plugin = plugin;
    }

    public void addCooldown(UUID uuid) {
        cooldownMap.put(uuid, System.currentTimeMillis() + plugin.getMainConfig().teleportCooldownMillis());
    }

    public long getCooldownLeft(UUID uuid) {
        return cooldownMap.getOrDefault(uuid, 0L) - System.currentTimeMillis();
    }
}
