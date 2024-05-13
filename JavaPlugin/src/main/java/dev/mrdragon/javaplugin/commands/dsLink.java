package dev.mrdragon.javaplugin.commands;

import dev.mrdragon.javaplugin.database.MongoDB;
import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class dsLink implements CommandExecutor {
    MongoDB db;

    public dsLink(MongoDB database) {
        db = database;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Você não pode utilizar esse comando pelo console...");
        } else {
            Player player = (Player) sender;
            Document table = db.Players.find(new Document("_id", player.getName())).first();

            if (table == null) {
                int code = Code();

                Document insert = new Document("_id", player.getName())
                        .append("code", code);

                db.Players.insertOne(insert);

                player.sendMessage("Vá ao nosso discord e digite: /dslink " + code);
            } else {
                String discord = (String) table.get("discord");

                if (!table.get("code").equals(0)) {
                    player.sendMessage("Vá ao nosso discord e digite: /dslink " + table.get("code"));
                } else if (discord == null || discord.isEmpty()) {
                    int code = Code();

                    db.Players.replaceOne(new Document("_id", player.getName()), new Document("code", code));

                    player.sendMessage("Vá ao nosso discord e digite: /dslink " + code);
                } else {
                    player.sendMessage("Conta verificada!");
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
