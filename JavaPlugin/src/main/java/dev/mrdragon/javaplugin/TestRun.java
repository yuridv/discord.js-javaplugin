package dev.mrdragon.javaplugin;

import dev.mrdragon.javaplugin.database.MongoDB;
import dev.mrdragon.javaplugin.restAPI.API;
import org.bson.Document;

public class TestRun {
    static API api = new API();
    static MongoDB db = new MongoDB();

    public static void main(String[] args) {
        api.Connect();
        db.Connect();

        Document table = db.Players.find(new Document("_id", "MrDragon")).first();
        assert table != null;

        String discord = (String) table.get("discord");
        if (discord == null || discord.isEmpty()) {
            System.out.println("Y");
        } else {
            System.out.println("N");
        }

        Document update = new Document()
                .append("code", 123)
                .append("discord", "");

        db.Players.replaceOne(new Document("_id", "MrDragon"), update);
    }
}