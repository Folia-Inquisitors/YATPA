package me.hsgamer.yatpa;

import io.github.projectunified.craftux.spigot.SpigotInventoryUIListener;
import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;
import me.hsgamer.hscore.bukkit.command.CommandManager;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;
import me.hsgamer.yatpa.command.TeleportAcceptCommand;
import me.hsgamer.yatpa.command.TeleportDenyCommand;
import me.hsgamer.yatpa.command.TeleportGuiCommand;
import me.hsgamer.yatpa.command.TeleportHereCommand;
import me.hsgamer.yatpa.command.TeleportNormalCommand;
import me.hsgamer.yatpa.config.MainConfig;
import me.hsgamer.yatpa.config.MessageConfig;
import me.hsgamer.yatpa.cooldown.CooldownManager;
import me.hsgamer.yatpa.gui.TeleportGuiManager;
import me.hsgamer.yatpa.listener.PlayerListener;
import me.hsgamer.yatpa.request.RequestManager;
import me.hsgamer.yatpa.teleport.TeleportManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public final class YATPA extends BasePlugin {
    private final MainConfig mainConfig = ConfigGenerator.newInstance(MainConfig.class, new BukkitConfig(this, "config.yml"));
    private final MessageConfig messageConfig = ConfigGenerator.newInstance(MessageConfig.class, new BukkitConfig(this, "messages.yml"));
    private final RequestManager requestManager = new RequestManager(this);
    private final TeleportManager teleportManager = new TeleportManager(this);
    private final CooldownManager cooldownManager = new CooldownManager(this);

    @Override
    public void load() {
        MessageUtils.setPrefix(messageConfig::getPrefix);
    }

    @Override
    public void enable() {
        unregisterStaleCommands();
        teleportManager.init();

        TeleportGuiManager teleportGuiManager = new TeleportGuiManager(this);
        registerListener(new PlayerListener(this));
        registerListener(new SpigotInventoryUIListener(this));

        registerCommand(new TeleportNormalCommand(this));
        registerCommand(new TeleportHereCommand(this));
        registerCommand(new TeleportAcceptCommand(this));
        registerCommand(new TeleportDenyCommand(this));
        registerCommand(new TeleportGuiCommand(this, "tp-gui", "Open the teleport request GUI", null, teleportGuiManager::openTeleportMenu));
        registerCommand(new TeleportGuiCommand(this, "tpyes-gui", "Accept a teleport request in a GUI", Permissions.TPA_ACCEPT.getName(), teleportGuiManager::openAcceptRequests));
        registerCommand(new TeleportGuiCommand(this, "tpno-gui", "Deny a teleport request in a GUI", Permissions.TPA_DENY.getName(), teleportGuiManager::openDenyRequests));
    }

    @Override
    public void disable() {
        try {
            getCommandManager().unregisterAll();
            CommandManager.syncCommand();
        } catch (Throwable throwable) {
            getLogger().log(Level.WARNING, "Failed to fully unregister commands during shutdown", throwable);
        } finally {
            teleportManager.stop();
        }
    }

    private void unregisterStaleCommands() {
        try {
            Method getCommandMap = getServer().getClass().getMethod("getCommandMap");
            CommandMap commandMap = (CommandMap) getCommandMap.invoke(getServer());
            String[] labels = {
                    "tpa", "tpahere", "tpah", "tpaccept", "tpyes",
                    "tpdeny", "tpno", "tp-gui", "tpyes-gui", "tpno-gui"
            };
            Set<Command> staleCommands = new HashSet<>();
            for (String label : labels) {
                Command command = commandMap.getCommand(label);
                if (isYatpaCommand(command)) {
                    staleCommands.add(command);
                }
                Command namespacedCommand = commandMap.getCommand("yatpa:" + label);
                if (isYatpaCommand(namespacedCommand)) {
                    staleCommands.add(namespacedCommand);
                }
            }
            staleCommands.forEach(CommandManager::unregisterFromKnownCommands);
            if (!staleCommands.isEmpty()) {
                CommandManager.syncCommand();
                getLogger().info("Removed " + staleCommands.size() + " stale command registrations from a previous YATPA instance");
            }
        } catch (ReflectiveOperationException | RuntimeException exception) {
            getLogger().log(Level.WARNING, "Could not check for stale commands during hot-load", exception);
        }
    }

    private boolean isYatpaCommand(Command command) {
        return command != null && command.getClass().getName().startsWith("me.hsgamer.yatpa.command.");
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

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
