package me.hsgamer.yatpa.command;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.yatpa.Permissions;
import me.hsgamer.yatpa.YATPA;
import me.hsgamer.yatpa.request.RequestEntry;
import me.hsgamer.yatpa.teleport.TeleportResult;

import java.util.Collections;
import java.util.UUID;

public class TeleportAcceptCommand extends TeleportResponseCommand {
    public TeleportAcceptCommand(YATPA plugin) {
        super(plugin, "tpaccept", "Accept a teleport request", Collections.singletonList("tpyes"));
        setPermission(Permissions.TPA_ACCEPT.getName());
    }

    @Override
    protected void execute(RequestEntry requestEntry, UUID requester, UUID target) {
        TeleportResult teleportResult = plugin.getTeleportManager().teleport(requestEntry);
        switch (teleportResult.status) {
            case OFFLINE:
                MessageUtils.sendMessage(target, plugin.getMessageConfig().getTeleportOffline());
                break;
            case IN_TELEPORT:
                MessageUtils.sendMessage(requester, plugin.getMessageConfig().getTeleportInTeleport());
                MessageUtils.sendMessage(target, plugin.getMessageConfig().getTeleportInTeleport());
                break;
            case SUCCESS_DELAYED:
                MessageUtils.sendMessage(teleportResult.player, plugin.getMessageConfig().getTeleportingDelayedFrom(teleportResult.target, plugin.getMainConfig().teleportDelay()));
                MessageUtils.sendMessage(teleportResult.target, plugin.getMessageConfig().getTeleportingDelayedTo(teleportResult.player, plugin.getMainConfig().teleportDelay()));
                break;
            case SUCCESS:
                MessageUtils.sendMessage(teleportResult.player, plugin.getMessageConfig().getTeleportingInstantFrom(teleportResult.target));
                MessageUtils.sendMessage(teleportResult.target, plugin.getMessageConfig().getTeleportingInstantTo(teleportResult.player));
                break;
        }
    }
}
