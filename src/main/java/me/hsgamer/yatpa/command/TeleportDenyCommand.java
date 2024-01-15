package me.hsgamer.yatpa.command;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.yatpa.Permissions;
import me.hsgamer.yatpa.YATPA;
import me.hsgamer.yatpa.request.RequestEntry;

import java.util.Collections;
import java.util.UUID;

public class TeleportDenyCommand extends TeleportResponseCommand {
    public TeleportDenyCommand(YATPA plugin) {
        super(plugin, "tpdeny", "Deny a teleport request", Collections.singletonList("tpno"));
        setPermission(Permissions.TPA_DENY.getName());
    }

    @Override
    protected void execute(RequestEntry requestEntry, UUID requester, UUID target) {
        MessageUtils.sendMessage(requester, plugin.getMessageConfig().getRequestDenyFrom(target));
        MessageUtils.sendMessage(target, plugin.getMessageConfig().getRequestDenyTo(requester));
    }
}
