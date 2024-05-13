package dev.mrdragon.javaplugin;

import dev.mrdragon.javaplugin.commands.dsCollect;
import dev.mrdragon.javaplugin.commands.dsLink;
import dev.mrdragon.javaplugin.database.MongoDB;
import dev.mrdragon.javaplugin.restAPI.API;
import dev.mrdragon.javaplugin.utils.Config;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;


public final class Plugin extends JavaPlugin implements CommandExecutor {
    Config config = new Config();
    MongoDB db = new MongoDB();
    API api = new API();

    @Override
    public void onEnable() {
        System.out.println("[JavaPlugin Server]=> Started Successfully!");

        db.Connect();
        api.Connect();

        getCommand("dslink").setExecutor(new dsLink(db));
        getCommand("dscollect").setExecutor(new dsCollect());
    }

    @Override
    public void onDisable() {
        System.out.println("[JavaPlugin Server]=> Ended!");
    }
}
