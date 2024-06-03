package dev.mrdragon.javaplugin.listeners;

import dev.mrdragon.javaplugin.database.MongoDB;
import dev.mrdragon.javaplugin.menus.dsConvertMenu;
import dev.mrdragon.javaplugin.utils.Config;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class MenuInteraction implements Listener {
    Config config = new Config();
    MongoDB db;

    public MenuInteraction(MongoDB database) {
        db = database;
    }

    @EventHandler
    public void onClickInventory(final InventoryClickEvent event) {
        InventoryView view = event.getView();

        if (view.getTitle().equals(config.get("MESSAGE"))) {
            event.setCancelled(true);

            Player p = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            Document table = db.Players.find(new Document("_id", p.getName())).first();

            if (item != null && table != null) {
                ItemMeta meta = item.getItemMeta();

                assert meta != null;
                if (meta.getDisplayName().contains("§aConverter Moedas")) {
                    p.closeInventory();
                    Document server = db.Servers.find(new Document("_id", "TODOS")).first();
                    p.openInventory(new dsConvertMenu().buildInventory(p, table, server));
                }
            }

        } else if (view.getTitle().equals("§l§6★ §2Converter Moedas")) {
            event.setCancelled(true);

            Player p = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            Document table = db.Players.find(new Document("_id", p.getName())).first();

        }
    }

}
