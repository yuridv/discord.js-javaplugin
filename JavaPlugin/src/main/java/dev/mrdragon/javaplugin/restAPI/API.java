package dev.mrdragon.javaplugin.restAPI;

import dev.mrdragon.javaplugin.utils.Config;
import io.javalin.Javalin;
import io.javalin.core.util.JavalinLogger;

public class API {
    Config config = new Config();

    public void Connect() {
        int PORT = Integer.parseInt(config.get("PORT"));

        JavalinLogger.enabled = false;
        org.eclipse.jetty.util.log.Log.getProperties().setProperty("org.eclipse.jetty.util.log.announce", "false");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(API.class.getClassLoader());

        Javalin app = Javalin.create()
                .start(PORT);

        app.get("*", (r) -> {
            System.out.println("[JavaPlugin API]=> Request received!");
            r.status(200).result("Hello World");
        });

        Thread.currentThread().setContextClassLoader(classLoader);

        System.out.println("[JavaPlugin API]=> Successfully Loaded!");
    }
}