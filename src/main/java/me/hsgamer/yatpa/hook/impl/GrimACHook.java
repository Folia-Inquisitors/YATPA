package me.hsgamer.yatpa.hook.impl;

import ac.grim.grimac.api.GrimUser;
import ac.grim.grimac.api.events.FlagEvent;
import me.hsgamer.yatpa.YATPA;
import me.hsgamer.yatpa.event.PostTeleportEvent;
import me.hsgamer.yatpa.hook.Hook;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GrimACHook extends Hook implements Listener {

    private final List<UUID> exemptPlayers;

    public GrimACHook(YATPA plugin, String hookPlugin) {
        super(plugin, hookPlugin);
        this.exemptPlayers = new ArrayList<>();
    }

    @Override
    public void onRegister() {
        Bukkit.getPluginManager().registerEvents(this, super.plugin);
    }

    @EventHandler
    public void onPostTeleport(PostTeleportEvent event) {
        exemptPlayers.add(event.getRequestEntry().requester);
        Bukkit.getScheduler().runTaskLater(super.plugin, () -> {
            exemptPlayers.remove(event.getRequestEntry().requester);
        }, 40);
    }

    @EventHandler
    public void onFlag(FlagEvent event) {
        GrimUser user = event.getPlayer();
        if (!exemptPlayers.contains(user.getUniqueId())) return;

        event.setCancelled(true);
    }

}
