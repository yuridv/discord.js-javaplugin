package dev.mrdragon.javaplugin.restAPI.Routes.Events;

import io.javalin.http.Context;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Spawned {

    public Spawned(Context req) {
        Document body = Document.parse(req.body());

        String event = (String) body.get("event");

        String title = "§l§fUm §6Evento §fAleatório §6Apareceu";
        String desc = "§l§2✔ §fNo §6Chat §fdo §9Discord§f, #eventos";

//        if (Objects.equals(event, "chest")) {
//            title = "§l§fUm §6Baú §fAleatório §6Apareceu";
//        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, desc, 10, 200, 20);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1.0f, 1.0f);
        }

        req.json(new Document("status", "success"));
    }

}
