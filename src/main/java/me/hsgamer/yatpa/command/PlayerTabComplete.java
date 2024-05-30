package me.hsgamer.yatpa.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.chrommob.fakeplayer.api.FakePlayerAPI;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public interface PlayerTabComplete {
    static @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias,
                                             @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            String argName = args[0];
            List<String> players = Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(argName.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
            FakePlayerAPI.getInstance().getFakePlayerNames().stream()
                    .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(argName.toLowerCase(Locale.ROOT)))
                    .forEach(players::add);
            return players;
        }
        return Collections.emptyList();
    }
}
