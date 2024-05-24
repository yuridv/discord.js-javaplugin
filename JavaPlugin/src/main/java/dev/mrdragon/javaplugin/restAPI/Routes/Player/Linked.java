package dev.mrdragon.javaplugin.restAPI.Routes.Player;

import io.javalin.http.Context;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Linked {

    public Linked(Context req) {
        Document body = Document.parse(req.body());

        String player = (String) body.get("player");

        if (player == null || player.isEmpty()) {
            Document error = new Document("error", "Você precisa passar o campo 'player'...");
            req.json(error);
        } else {
            Player p = Bukkit.getPlayer(player);
            if (p == null) {
                Document error = new Document("error", "O player passado não está online...");
                req.json(error);
            } else {
                p.sendTitle("§l§2Conta Vinculada", "§l§2✔ §aA sua conta foi vinculada ao nosso discord!", 10, 200, 20);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                req.json(new Document("status", "success"));
            }
        }
    }

}