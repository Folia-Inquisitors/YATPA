package me.hsgamer.yatpa.hook;

import me.hsgamer.yatpa.YATPA;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class HookManager {

    private final YATPA plugin;
    private final List<Hook> registeredHooks;

    public HookManager(YATPA plugin) {
        this.plugin = plugin;
        this.registeredHooks = new ArrayList<>();
    }

    public void addHook(String pluginName, Class<? extends Hook> hookClass) {
        if (!Bukkit.getPluginManager().isPluginEnabled(pluginName)) {
            throw new IllegalArgumentException("Cannot add a Hook because plugin " + pluginName + " isn't loaded");
        }
        try {
            Hook hook = hookClass.getDeclaredConstructor(YATPA.class, String.class).newInstance(plugin, pluginName);
            registeredHooks.add(hook);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Hook must have a public contructor that equals (YATPA, String)", e);
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerAll() {
        registeredHooks.forEach(Hook::onRegister);
    }

    public List<Hook> getRegisteredHooks() {
        return registeredHooks;
    }

}
