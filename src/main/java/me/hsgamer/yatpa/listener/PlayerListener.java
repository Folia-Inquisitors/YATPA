package me.hsgamer.yatpa.listener;

import me.hsgamer.yatpa.YATPA;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final YATPA plugin;

    public PlayerListener(YATPA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getTeleportManager().cancelTeleport(event.getPlayer());
    }
}
