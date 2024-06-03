package dev.mrdragon.javaplugin;

import dev.mrdragon.javaplugin.commands.dsUnLink;
import dev.mrdragon.javaplugin.commands.dsLink;
import dev.mrdragon.javaplugin.commands.test;
import dev.mrdragon.javaplugin.database.MongoDB;
import dev.mrdragon.javaplugin.listeners.MenuInteraction;
import dev.mrdragon.javaplugin.restAPI.API;
import dev.mrdragon.javaplugin.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements CommandExecutor {
    Config config = new Config();
    MongoDB db = new MongoDB();
    API api = new API();

    @Override
    public void onEnable() {
        System.out.println("[JavaPlugin Server]=> Started Successfully!");

        loadConfig();

        db.Connect();
        api.Connect();

        getCommand("dslink").setExecutor(new dsLink(db));
        getCommand("dsunlink").setExecutor(new dsUnLink(db));
        getCommand("test").setExecutor(new test(db, this.getDataFolder()));

        Bukkit.getPluginManager().registerEvents(new MenuInteraction(db), this);
    }

    @Override
    public void onDisable() {
        System.out.println("[JavaPlugin Server]=> Ended!");
        if (api.app != null) {
            api.app.close();
        }
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
