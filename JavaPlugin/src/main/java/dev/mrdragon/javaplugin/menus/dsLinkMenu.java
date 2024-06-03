package dev.mrdragon.javaplugin.menus;

import dev.mrdragon.javaplugin.utils.Config;
import dev.mrdragon.javaplugin.utils.Request;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class dsLinkMenu {
    Config config = new Config();

    public Inventory buildInventory(Player player, Document table, Document server) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, config.get("MESSAGE"));

        if (server == null) server = new Document("_id", "TODOS")
                .append("taxa", 15);

        String discord = table.get("discord").toString();

        Document res = null;
        try {
            res = new Request().req("/users/name?id=" + table.get("discord"), "GET", null);
            if (res.get("name") != null) discord = res.get("name").toString();
        } catch (IOException e) {}

        for (int i = 0; i <= 26; i++) {
            ItemStack item;
            ItemMeta meta = null;

            if (i == 0 || i == 8 || i == 18 || i == 26) {
                item = new ItemStack(Material.CHAIN);
                meta = item.getItemMeta();
                meta.setDisplayName(" ");
            } else if (i == 10) {
                item = new ItemStack(Material.SUNFLOWER);
                meta = item.getItemMeta();
                meta.setDisplayName("§6★ §aConverter Moedas");
                meta.setLore(asList(
                        "  §c✂ §eTaxa de Conversão: §c" + server.get("taxa") + "§7%",
                        " ",
                        "  §3➠ §7Aqui você pode converter suas",
                        "     §7moedas em outros tipos de moedas,",
                        "     §7para obter VIP's, itens e entre outros!"
                ));
            } else if (i == 13) {
                item = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta head = (SkullMeta) item.getItemMeta();
                head.setOwningPlayer(player);
                head.setDisplayName("§3➠ §f§lDiscord: §c" + discord);
                head.setLore(asList(
                        " §2✔ §e§lPlayer Level: §a" + table.get("level"),
                        " §2↑ §a§lPlayer XP: §d" + table.get("xp") + "/" + Math.round((((int) table.get("level") * 1.8) * 500) + 1000),
                        " ",
                        " §2♢ §c§lDiscord Coin's: §6" + table.get("money"),
                        "  §3➠ §7São moedas que só podem ser ganhas",
                        "     §7no Discord através de eventos, permitindo",
                        "     §7comprar beneficios ou itens pelo discord!",
                        " ",
                        " §2✧ §3§lTotal Coin's: §6" + table.get("money_total"),
                        "  §3➠ §7São moedas que só podem ser ganhas",
                        "     §7convertendo seus Discord Coin's, permitindo",
                        "     §7comprar itens exclusivos ou trocar por cash!"
                ));
                item.setItemMeta(head);
            } else if (i == 16) {
                List<String> listStart = Arrays.asList("  §5✴ §eSuas recompensas:");

                ArrayList<String> list = new ArrayList<>(listStart);

                if (table.get("items") != null) {
                    String[] items = table.get("items").toString()
                            .replace("[", "")
                            .replace("]", "")
                            .trim()
                            .split(", ");
                    if (items.length > 0) {
                        for (int num = 0; num < items.length; num++) {
                            String collect = config.get("COLLECT_ITEM_" + items[num]);
                            String quanty = config.get("COLLECT_QUANTY_" + items[num]);

                            if (collect != null) {
                                Material material = Material.getMaterial(collect);
                                if (material != null) list.add("    §2• §a" + quanty + " " + material.name());
                            }
                        }
                    } else list.add("    §4✘ §cNada para Coletar!");
                } else list.add("    §4✘ §cNada para Coletar!");

                list.add(" ");
                list.add("  §3♢ §7Total de Baús Abertos: §5" + table.get("chests"));
                list.add(" ");
                list.add("  §3➠ §7Aqui você pode coletar suas");
                list.add("     §7recompensas obtidas através de");
                list.add("     §7eventos que aconteceram no discord!");

                item = new ItemStack(Material.ENDER_CHEST);
                meta = item.getItemMeta();
                meta.setDisplayName("§d✉ §aBaús do Discord");
                meta.setLore(list);
            } else {
                item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                meta = item.getItemMeta();
                meta.setDisplayName(" ");
            }

            if (meta != null) item.setItemMeta(meta);
            inventory.setItem(i, item);
        }

        return inventory;
    }

}
