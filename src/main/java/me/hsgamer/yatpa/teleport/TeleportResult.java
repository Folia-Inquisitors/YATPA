package me.hsgamer.yatpa.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportResult {
    public final TeleportStatus status;
    public final Player player;
    public final Player target;
    public final Location location;

    public TeleportResult(TeleportStatus status, Player player, Player target, Location location) {
        this.status = status;
        this.player = player;
        this.target = target;
        this.location = location;
    }
}
