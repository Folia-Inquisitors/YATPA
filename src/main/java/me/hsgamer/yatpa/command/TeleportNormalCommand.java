package me.hsgamer.yatpa.command;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.yatpa.Permissions;
import me.hsgamer.yatpa.YATPA;
import me.hsgamer.yatpa.request.RequestType;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.function.Consumer;

public class TeleportNormalCommand extends TeleportRequestCommand {
    public TeleportNormalCommand(YATPA plugin, Consumer<Player> guiAction) {
        super(plugin, "tpa", "Request a teleport to the player", Collections.emptyList(), guiAction);
        setPermission(Permissions.TPA.getName());
    }

    @Override
    protected RequestType getRequestType() {
        return RequestType.NORMAL;
    }

    @Override
    protected void sendTargetRequest(Player sender, Player targetPlayer) {
        MessageUtils.sendMessage(targetPlayer, plugin.getMessageConfig().getRequestReceived(sender));
        MessageUtils.sendMessage(targetPlayer, plugin.getMessageConfig().getRequestReceivedNote());
    }
}
