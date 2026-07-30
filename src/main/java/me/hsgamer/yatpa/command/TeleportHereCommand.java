package me.hsgamer.yatpa.command;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.yatpa.Permissions;
import me.hsgamer.yatpa.YATPA;
import me.hsgamer.yatpa.request.RequestType;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.function.Consumer;

public class TeleportHereCommand extends TeleportRequestCommand {
    public TeleportHereCommand(YATPA plugin, Consumer<Player> guiAction) {
        super(plugin, "tpahere", "Request to teleport a player to you", Arrays.asList("tpah", "tph"), guiAction);
        setPermission(Permissions.TPA_HERE.getName());
    }

    @Override
    protected RequestType getRequestType() {
        return RequestType.HERE;
    }

    @Override
    protected void sendTargetRequest(Player sender, Player targetPlayer) {
        MessageUtils.sendMessage(targetPlayer, plugin.getMessageConfig().getRequestReceivedHere(sender));
        MessageUtils.sendMessage(targetPlayer, plugin.getMessageConfig().getRequestReceivedNote());
    }
}
