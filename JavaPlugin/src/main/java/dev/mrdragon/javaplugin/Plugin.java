package dev.mrdragon.javaplugin;

import dev.mrdragon.javaplugin.database.MongoDB;
import dev.mrdragon.javaplugin.restAPI.API;
import dev.mrdragon.javaplugin.utils.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
    Config config = new Config();
    MongoDB db = new MongoDB();
    API api = new API();

    @Override
    public void onEnable() {
        System.out.println("[JavaPlugin Server]=> Started Successfully!");

        db.Connect();
        api.Connect();
    }

    @Override
    public void onDisable() {
        System.out.println("[JavaPlugin Server]=> Ended!");
    }
}
