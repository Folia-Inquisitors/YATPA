package me.hsgamer.yatpa.command;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.yatpa.YATPA;
import me.hsgamer.yatpa.request.RequestEntry;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class TeleportResponseCommand extends Command implements PlayerTabComplete {
    protected final YATPA plugin;

    protected TeleportResponseCommand(YATPA plugin, @NotNull String name, @NotNull String description, @NotNull List<String> aliases) {
        super(name, description, "/" + name + " [player]", aliases);
        this.plugin = plugin;
    }

    protected abstract void execute(RequestEntry requestEntry, UUID requester, UUID target);

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!testPermission(sender)) {
            return true;
        }

        if (!(sender instanceof Player)) {
            MessageUtils.sendMessage(sender, plugin.getMessageConfig().getPlayerOnly());
            return false;
        }

        Player targetPlayer = (Player) sender;

        Player requestPlayer;
        if (args.length > 0) {
            requestPlayer = sender.getServer().getPlayer(args[0]);
            if (requestPlayer == null) {
                MessageUtils.sendMessage(sender, plugin.getMessageConfig().getPlayerNotFound());
                return false;
            }
        } else {
            requestPlayer = null;
        }

        Optional<RequestEntry> optionalRequestEntry = requestPlayer == null
                ? plugin.getRequestManager().getAndRemoveLatestRequest(targetPlayer.getUniqueId())
                : plugin.getRequestManager().getAndRemoveRequest(targetPlayer.getUniqueId(), requestPlayer.getUniqueId());

        if (!optionalRequestEntry.isPresent()) {
            MessageUtils.sendMessage(sender, plugin.getMessageConfig().getNoRequest());
            return false;
        }

        RequestEntry requestEntry = optionalRequestEntry.get();
        execute(requestEntry, requestEntry.requester, requestEntry.target);
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            String argName = args[0];
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(argName.toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
        }
        return super.tabComplete(sender, alias, args);
    }
}
