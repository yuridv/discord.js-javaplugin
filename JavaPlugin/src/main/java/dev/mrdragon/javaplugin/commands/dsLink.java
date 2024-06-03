package dev.mrdragon.javaplugin.commands;

import dev.mrdragon.javaplugin.database.MongoDB;
import dev.mrdragon.javaplugin.menus.dsLinkMenu;
import dev.mrdragon.javaplugin.utils.Config;
import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class dsLink implements CommandExecutor {
    Config config = new Config();
    MongoDB db;

    public dsLink(MongoDB database) {
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
                int code = Code();

                Document insert = new Document("_id", player.getName())
                        .append("discord", "")
                        .append("code", code)
                        .append("money", 0)
                        .append("money_total", 0)
                        .append("level", 0)
                        .append("xp", 0)
                        .append("chests", 0);

                db.Players.insertOne(insert);

                player.sendMessage(config.get("MESSAGE") + "§eVá ao nosso discord e digite: §3/link " + code);
            } else {
                String discord = (String) table.get("discord");

                if (!table.get("code").equals(0)) {
                    player.sendMessage(config.get("MESSAGE") + "§eVá ao nosso discord e digite: §3/link " + table.get("code"));
                } else if (discord == null || discord.isEmpty()) {
                    int code = Code();

                    Document update = new Document(table)
                            .append("code", code);

                    db.Players.replaceOne(table, update);

                    player.sendMessage(config.get("MESSAGE") + "§eVá ao nosso discord e digite: §3/link " + code);
                } else {
                    Document server = db.Servers.find(new Document("_id", "TODOS")).first();
                    player.sendMessage(config.get("MESSAGE") + "§eAbrindo suas informações! Aguarde...");
                    player.openInventory(new dsLinkMenu().buildInventory(player, table, server));
                }
            }
        }
        return true;
    }

    public int Code() {
        int code = (int) Math.floor(Math.random() * (999999 - 100000 + 1) + 100000);

        Document table = db.Players.find(new Document("code", code)).first();

        if (table == null) {
            return code;
        } else {
            return Code();
        }
    }
}
