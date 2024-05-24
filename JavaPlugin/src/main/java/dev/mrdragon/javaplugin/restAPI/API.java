package dev.mrdragon.javaplugin.restAPI;

import dev.mrdragon.javaplugin.restAPI.Routes.Player.Linked;
import dev.mrdragon.javaplugin.restAPI.Routes.Player.UnLinked;
import dev.mrdragon.javaplugin.restAPI.Routes.Player.Status;
import dev.mrdragon.javaplugin.restAPI.Routes.Events.Spawned;
import dev.mrdragon.javaplugin.utils.Config;
import io.javalin.Javalin;
import io.javalin.core.util.JavalinLogger;

public class API {
    Config config = new Config();

    public Javalin app;

    public void Connect() {
        if (app == null) {
            int PORT = Integer.parseInt(config.get("PORT"));

            JavalinLogger.enabled = false;
            org.eclipse.jetty.util.log.Log.getProperties().setProperty("org.eclipse.jetty.util.log.announce", "false");

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(API.class.getClassLoader());

            app = Javalin.create()
                    .start(PORT);

            app.post("/player/linked", Linked::new);
            app.post("/player/unlinked", UnLinked::new);
            app.get("/player/status", Status::new);

            app.post("/events/spawned", Spawned::new);

            Thread.currentThread().setContextClassLoader(classLoader);

            System.out.println("[JavaPlugin API]=> Started Successfully!!");
        }
    }
}