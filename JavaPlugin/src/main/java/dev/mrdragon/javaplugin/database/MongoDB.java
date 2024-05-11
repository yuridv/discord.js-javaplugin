package dev.mrdragon.javaplugin.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.mrdragon.javaplugin.utils.Config;
import org.bson.Document;

public class MongoDB {
    Config config = new Config();

    MongoClient client;
    MongoDatabase db;
    public MongoCollection<Document> Players;

    public void Connect() {
        if (db == null) {
            client = MongoClients.create(config.get("MONGO_DB"));
            db = client.getDatabase("test");
            Players = db.getCollection("players");

            System.out.println("[JavaPlugin Database] Connected Successfully!");
        }
    }
}
