package com.paulpanosch.newriserealms;

import com.paulpanosch.newriserealms.commands.GetCrystal;
import org.bukkit.plugin.java.JavaPlugin;

public final class NewRiseRealms extends JavaPlugin {
    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        this.getCommand("getCrystal").setExecutor(new GetCrystal());
        this.getServer().getPluginManager().registerEvents(new EventManager(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}
