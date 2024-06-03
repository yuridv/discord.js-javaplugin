package dev.mrdragon.javaplugin;

import dev.mrdragon.javaplugin.database.MongoDB;
import dev.mrdragon.javaplugin.restAPI.API;
import dev.mrdragon.javaplugin.utils.Config;
import org.bson.Document;

public class TestRun {
    static API api = new API();
    static MongoDB db = new MongoDB();
    static Config config = new Config();

    public static void main(String[] args) {
        api.Connect();
        db.Connect();

        Document server = db.Servers.find(new Document("_id", "TODOS")).first();
        System.out.println(server.get("taxa"));
    }
}