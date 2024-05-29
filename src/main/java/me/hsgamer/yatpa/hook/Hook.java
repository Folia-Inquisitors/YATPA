package me.hsgamer.yatpa.hook;

import me.hsgamer.yatpa.YATPA;

public abstract class Hook {

    protected final YATPA plugin;
    protected final String hookPlugin;

    protected Hook(YATPA plugin, String hookPlugin) {
        this.plugin = plugin;
        this.hookPlugin = hookPlugin;
    }

    protected abstract void onRegister();

    public String getHookPlugin() {
        return hookPlugin;
    }

}
