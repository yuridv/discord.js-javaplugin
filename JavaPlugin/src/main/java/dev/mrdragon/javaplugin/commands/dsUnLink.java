package dev.mrdragon.javaplugin.commands;

import dev.mrdragon.javaplugin.database.MongoDB;
import dev.mrdragon.javaplugin.utils.Config;
import org.bson.Document;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class dsUnLink implements CommandExecutor {
    Config config = new Config();
    MongoDB db;

    public dsUnLink(MongoDB database) {
        db = database;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println(config.get("MESSAGE") + "§3Você não pode utilizar esse comando pelo console...");
        } else {
            Player player = (Player) sender;
            Document table = db.Players.find(new Document("_id", player.getName())).first();

            if (table == null) {
                player.sendMessage(config.get("MESSAGE") + "§cA sua conta já não está vinculada ao discord!");
            } else {
                String discord = (String) table.get("discord");
                if (discord == null || discord.isEmpty()) {
                    player.sendMessage(config.get("MESSAGE") + "§cA sua conta já não está vinculada ao discord!");
                } else {
                    Document update = new Document(table)
                            .append("discord", "");

                    db.Players.replaceOne(table, update);

                    player.sendTitle("§l§4Conta Desvinculada", "§l§4❌ §cA sua conta foi desvinculada do nosso discord!", 10, 200, 20);
                    player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);
                    player.sendMessage(config.get("MESSAGE") + "§eA sua conta foi §6desvinculada §edo discord!");
                }
            }
        }
        return true;
    }
}