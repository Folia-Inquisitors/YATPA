package me.hsgamer.yatpa.command;

import me.hsgamer.yatpa.event.TeleportPlayerListEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public interface PlayerTabComplete {
    static List<String> getPlayerNames() {
        List<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerNames.add(player.getName());
        }
        TeleportPlayerListEvent event = new TeleportPlayerListEvent(playerNames);
        Bukkit.getPluginManager().callEvent(event);
        return playerNames;
    }

    static @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            String argName = args[0];
            return getPlayerNames().stream()
                    .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(argName.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
