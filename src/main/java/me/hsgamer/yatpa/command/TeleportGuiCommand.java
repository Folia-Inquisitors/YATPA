package me.hsgamer.yatpa.command;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.yatpa.YATPA;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.function.Consumer;

public class TeleportGuiCommand extends Command {
    private final YATPA plugin;
    private final Consumer<Player> guiAction;

    public TeleportGuiCommand(YATPA plugin, String name, String description, String permission, Consumer<Player> guiAction) {
        super(name, description, "/" + name, Collections.emptyList());
        this.plugin = plugin;
        this.guiAction = guiAction;
        if (permission != null) {
            setPermission(permission);
        }
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!plugin.isEnabled()) {
            return false;
        }
        if (!testPermission(sender)) {
            return true;
        }
        if (!(sender instanceof Player)) {
            MessageUtils.sendMessage(sender, plugin.getMessageConfig().getPlayerOnly());
            return false;
        }
        guiAction.accept((Player) sender);
        return true;
    }
}
