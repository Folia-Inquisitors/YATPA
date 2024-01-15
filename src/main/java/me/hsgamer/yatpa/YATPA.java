package me.hsgamer.yatpa;

import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;
import me.hsgamer.yatpa.command.TeleportAcceptCommand;
import me.hsgamer.yatpa.command.TeleportDenyCommand;
import me.hsgamer.yatpa.command.TeleportHereCommand;
import me.hsgamer.yatpa.command.TeleportNormalCommand;
import me.hsgamer.yatpa.config.MainConfig;
import me.hsgamer.yatpa.config.MessageConfig;
import me.hsgamer.yatpa.listener.PlayerListener;
import me.hsgamer.yatpa.request.RequestManager;
import me.hsgamer.yatpa.teleport.TeleportManager;

import java.util.Collections;
import java.util.List;

public final class YATPA extends BasePlugin {
    private final MainConfig mainConfig = ConfigGenerator.newInstance(MainConfig.class, new BukkitConfig(this, "config.yml"));
    private final MessageConfig messageConfig = ConfigGenerator.newInstance(MessageConfig.class, new BukkitConfig(this, "messages.yml"));
    private final RequestManager requestManager = new RequestManager();
    private final TeleportManager teleportManager = new TeleportManager(this);

    @Override
    public void load() {
        MessageUtils.setPrefix(messageConfig::getPrefix);
    }

    @Override
    public void enable() {
        teleportManager.init();

        registerListener(new PlayerListener(this));

        registerCommand(new TeleportNormalCommand(this));
        registerCommand(new TeleportHereCommand(this));
        registerCommand(new TeleportAcceptCommand(this));
        registerCommand(new TeleportDenyCommand(this));
    }

    @Override
    public void disable() {
        teleportManager.stop();
    }

    @Override
    protected List<Class<?>> getPermissionClasses() {
        return Collections.singletonList(Permissions.class);
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public TeleportManager getTeleportManager() {
        return teleportManager;
    }
}
