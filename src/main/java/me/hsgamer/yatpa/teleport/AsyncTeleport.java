package me.hsgamer.yatpa.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class PaperTeleport implements Teleport {
    @Override
    public CompletableFuture<Boolean> teleport(Player player, Location target) {
        return player.tele
    }
}
