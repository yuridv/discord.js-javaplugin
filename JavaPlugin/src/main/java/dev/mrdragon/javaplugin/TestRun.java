package dev.mrdragon.javaplugin;

import dev.mrdragon.javaplugin.database.MongoDB;
import dev.mrdragon.javaplugin.restAPI.API;

public class TestRun {
    public static void main(String[] args) {
        new API().Connect();

        new MongoDB().Connect();
    }
}
