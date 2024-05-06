package dev.mrdragon.javaplugin;

import dev.mrdragon.javaplugin.restAPI.API;
import dev.mrdragon.javaplugin.utils.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
    Config config = new Config();
    API api = new API();

    @Override
    public void onEnable() {
        System.out.println("[JavaPlugin Server]=> Started!");

        api.Connect();
    }

    @Override
    public void onDisable() {
        System.out.println("[JavaPlugin Server]=> Ended!");
    }
}
