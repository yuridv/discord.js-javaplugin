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

        System.out.println(db.Players.find(new Document("_id", "Teste_Plugin")).first());
    }
}
