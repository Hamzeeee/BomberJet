package io.hamza.github.main;

import io.hamza.github.listener.AirStrikeListener;
import io.hamza.github.listener.BomberListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main plugin;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BomberListener(), this);
        getServer().getPluginManager().registerEvents(new AirStrikeListener(), this);
        new BomberListener();
        plugin = this;
    }


    public static Main getPlugin() {
        return plugin;
    }
}
