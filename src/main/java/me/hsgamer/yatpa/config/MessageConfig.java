package me.hsgamer.yatpa.config;

import me.hsgamer.hscore.config.annotation.ConfigPath;
import org.bukkit.entity.Player;

public interface MessageConfig {
    @ConfigPath({"teleport", "teleporting", "from-player", "instant"})
    default String getTeleportingFrom() {
        return "&aYou are being teleported to &f{player}";
    }

    default String getTeleportingFrom(Player player) {
        return getTeleportingFrom().replace("{player}", player.getName());
    }

    @ConfigPath({"teleport", "teleporting", "to-player", "instant"})
    default String getTeleportingTo() {
        return "&a{player} is being teleported to you";
    }

    default String getTeleportingTo(Player player) {
        return getTeleportingTo().replace("{player}", player.getName());
    }

    @ConfigPath({"error", "player-not-found"})
    default String getPlayerNotFound() {
        return "&cPlayer not found";
    }

    @ConfigPath({"error", "yourself"})
    default String getCannotSendToYourself() {
        return "&cYou cannot send a request to yourself";
    }

    @ConfigPath({"error", "player-only"})
    default String getPlayerOnly() {
        return "&cYou must be a player to use this command";
    }

    @ConfigPath({"error", "already-sent"})
    default String getAlreadySent() {
        return "&cYou have already sent a request to this player";
    }

    @ConfigPath({"request", "sent"})
    default String getRequestSent() {
        return "&aYou have sent a request to &f{player}";
    }

    default String getRequestSent(Player player) {
        return getRequestSent().replace("{player}", player.getName());
    }

    @ConfigPath({"request", "received", "normal"})
    default String getRequestReceived() {
        return "&f{player} &ahas requested to teleport to you";
    }

    default String getRequestReceived(Player player) {
        return getRequestReceived().replace("{player}", player.getName());
    }

    @ConfigPath({"request", "received", "here"})
    default String getRequestReceivedHere() {
        return "&f{player} &ahas requested you to teleport to them";
    }

    default String getRequestReceivedHere(Player player) {
        return getRequestReceivedHere().replace("{player}", player.getName());
    }

    @ConfigPath({"request", "received", "note"})
    default String getRequestReceivedNote() {
        return "&aType &f/tpaccept &ato accept or &f/tpdeny &ato deny";
    }
}
