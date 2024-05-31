package me.hsgamer.yatpa.config;

import me.hsgamer.hscore.config.annotation.ConfigPath;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface MessageConfig {
    @ConfigPath("prefix")
    default String getPrefix() {
        return "&e[&6YATPA&e] ";
    }

    @ConfigPath({"teleport", "teleporting", "from-player", "instant"})
    default String getTeleportingInstantFrom() {
        return "&aYou are being teleported to &f{player}";
    }

    default String getTeleportingInstantFrom(Player player) {
        return getTeleportingInstantFrom().replace("{player}", player.getName());
    }

    @ConfigPath({"teleport", "teleporting", "to-player", "instant"})
    default String getTeleportingInstantTo() {
        return "&a{player} is being teleported to you";
    }

    default String getTeleportingInstantTo(Player player) {
        return getTeleportingInstantTo().replace("{player}", player.getName());
    }

    @ConfigPath({"teleport", "teleporting", "from-player", "delayed"})
    default String getTeleportingDelayedFrom() {
        return "&aYou will be teleported to &f{player} &ain &f{delay} &aseconds";
    }

    default String getTeleportingDelayedFrom(Player player, int delay) {
        return getTeleportingDelayedFrom().replace("{player}", player.getName()).replace("{delay}", String.valueOf(delay));
    }

    @ConfigPath({"teleport", "teleporting", "to-player", "delayed"})
    default String getTeleportingDelayedTo() {
        return "&a{player} will be teleported to you in &f{delay} &aseconds";
    }

    default String getTeleportingDelayedTo(Player player, int delay) {
        return getTeleportingDelayedTo().replace("{player}", player.getName()).replace("{delay}", String.valueOf(delay));
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

    @ConfigPath({"error", "no-request"})
    default String getNoRequest() {
        return "&cYou don't have any request";
    }

    @ConfigPath({"error", "teleport", "offline"})
    default String getTeleportOffline() {
        return "&cThe player is offline";
    }

    @ConfigPath({"error", "teleport", "in-teleport"})
    default String getTeleportInTeleport() {
        return "&cThe player is in teleport";
    }

    @ConfigPath({"error", "cooldown"})
    default String getCooldownLeft() {
        return "&cYou need to wait &f{cooldown} &cmore seconds to send a request";
    }

    default String getCooldownLeft(long cooldown) {
        return getCooldownLeft().replace("{cooldown}", String.valueOf(cooldown / 1000));
    }

    @ConfigPath({"error", "too-many-requests"})
    default String getTooManyRequests() {
        return "&cYou have too many requests. Please specify a player.";
    }

    @ConfigPath({"request", "sent"})
    default String getRequestSent() {
        return "&aYou have sent a request to &f{player}";
    }

    default String getRequestSent(Player player) {
        return getRequestSent().replace("{player}", player.getName());
    }

    default String getRequestSent(String name) {
        return getRequestSent().replace("{player}", name);
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

    @ConfigPath({"request", "deny", "from"})
    default String getRequestDenyFrom() {
        return "&c{player} &chas denied your request";
    }

    default String getRequestDenyFrom(UUID uuid) {
        return getRequestDenyFrom().replace("{player}", Optional.of(Bukkit.getOfflinePlayer(uuid)).map(OfflinePlayer::getName).orElse(uuid.toString()));
    }

    @ConfigPath({"request", "deny", "to"})
    default String getRequestDenyTo() {
        return "&cYou have denied the request from &f{player}";
    }

    default String getRequestDenyTo(UUID uuid) {
        return getRequestDenyTo().replace("{player}", Optional.of(Bukkit.getOfflinePlayer(uuid)).map(OfflinePlayer::getName).orElse(uuid.toString()));
    }
}
