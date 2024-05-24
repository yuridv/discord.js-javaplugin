package dev.mrdragon.javaplugin.restAPI.Routes.Player;

import io.javalin.http.Context;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Status {

    public Status(Context req) {
        String player = req.queryParam("player");

        if (player == null || player.isEmpty()) {
            Document error = new Document("error", "Você precisa passar o campo 'player'...");
            req.json(error);
        } else {
            Player p = Bukkit.getPlayer(player);
            if (p != null && p.isOnline()) {
                req.json(new Document("status", "Online"));
            } else {
                req.json(new Document("status", "Offline"));
            }
        }
    }
}
