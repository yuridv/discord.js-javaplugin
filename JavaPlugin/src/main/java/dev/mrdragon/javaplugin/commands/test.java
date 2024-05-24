package dev.mrdragon.javaplugin.commands;

import dev.mrdragon.javaplugin.database.MongoDB;
import dev.mrdragon.javaplugin.utils.Config;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;

public class test implements CommandExecutor {
    Config config = new Config();
    MongoDB db;
    File folder;

    public test(MongoDB database, File dataFolder) {
        db = database;
        folder = dataFolder;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println(config.get("MESSAGE") + "§3Você não pode utilizar esse comando pelo console...");
        } else {
            Player player = (Player) sender;
            player.sendTitle("§l§4Conta Desvinculada", "§l§4❌ §cA sua conta foi desvinculada do nosso discord!", 10, 200, 20);
            player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);
        }
        return true;
    }
}
