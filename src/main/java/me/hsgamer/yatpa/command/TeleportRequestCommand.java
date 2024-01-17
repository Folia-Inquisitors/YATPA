package me.hsgamer.yatpa.command;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.yatpa.YATPA;
import me.hsgamer.yatpa.request.RequestEntry;
import me.hsgamer.yatpa.request.RequestStatus;
import me.hsgamer.yatpa.request.RequestType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class TeleportRequestCommand extends Command {
    protected final YATPA plugin;

    protected TeleportRequestCommand(YATPA plugin, @NotNull String name, @NotNull String description, @NotNull List<String> aliases) {
        super(name, description, "/" + name + " <player>", aliases);
        this.plugin = plugin;
    }

    protected abstract RequestType getRequestType();

    protected abstract void sendTargetRequest(Player sender, Player targetPlayer);

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!testPermission(sender)) {
            return false;
        }

        if (!(sender instanceof Player)) {
            MessageUtils.sendMessage(sender, plugin.getMessageConfig().getPlayerOnly());
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(getUsage());
            return false;
        }

        Player senderPlayer = (Player) sender;

        long cooldownLeft = plugin.getCooldownManager().getCooldownLeft(senderPlayer.getUniqueId());
        if (cooldownLeft > 0) {
            MessageUtils.sendMessage(sender, plugin.getMessageConfig().getCooldownLeft(cooldownLeft));
            return false;
        }

        Player targetPlayer = sender.getServer().getPlayer(args[0]);

        if (targetPlayer == null) {
            MessageUtils.sendMessage(sender, plugin.getMessageConfig().getPlayerNotFound());
            return false;
        }

        if (targetPlayer.equals(senderPlayer)) {
            MessageUtils.sendMessage(sender, plugin.getMessageConfig().getCannotSendToYourself());
            return false;
        }

        RequestType requestType = getRequestType();

        RequestEntry requestEntry = new RequestEntry(senderPlayer.getUniqueId(), targetPlayer.getUniqueId(), requestType);

        RequestStatus requestStatus = plugin.getRequestManager().request(requestEntry);
        switch (requestStatus) {
            case ALREADY_SENT:
                MessageUtils.sendMessage(sender, plugin.getMessageConfig().getAlreadySent());
                return false;
            case SUCCESS:
                MessageUtils.sendMessage(sender, plugin.getMessageConfig().getRequestSent(targetPlayer));
                sendTargetRequest(senderPlayer, targetPlayer);
                plugin.getCooldownManager().addCooldown(senderPlayer.getUniqueId());
                return true;
            default:
                break;
        }

        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return PlayerTabComplete.tabComplete(sender, alias, args);
    }
}
