package dev.mrdragon.javaplugin.menus;

import dev.mrdragon.javaplugin.utils.Config;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static java.util.Arrays.asList;

public class dsConvertMenu {
    Config config = new Config();

    public Inventory buildInventory(Player player, Document table, Document server) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9 , "§l§6★ §2Converter Moedas");

        if (server == null) server = new Document("_id", "TODOS")
                .append("taxa", 15);

        for (int i = 0; i <= 26; i++) {
            ItemStack item;
            ItemMeta meta = null;

            if (i == 2) {
                item = new ItemStack(Material.BOOK);
                meta = item.getItemMeta();
                meta.setDisplayName("§6★ §3§lConversão de Discord Coin's §c§l(Minima)");
                meta.setLore(asList(
                        " §2✔ §aMoney §fem §3Discord Coin's",
                        "  §7[§2+§7] §fClique com o §4Botão Esquerdo §fpara §6Converter",
                        "   §a100K Money §fem §3" + formatNumber((double) (1000L * (100 - (int) server.get("taxa"))) / 100) + " Discord Coin's §7(§fTaxa: §c" + server.get("taxa") + "%§7)",
                        " ",
                        " §2✔ §3Discord Coin's §fem §aMoney",
                        "  §7[§4-§7] §fClique com o §4Botão Direito §fpara §6Converter",
                        "   §31K Discord Coin's §fem §a" + formatNumber((double) (100000L * (100 - (int) server.get("taxa"))) / 100) + " Money §7(§fTaxa: §c" + server.get("taxa") + "%§7)"
                ));
            } else if (i == 4) {
                item = new ItemStack(Material.PAPER);
                meta = item.getItemMeta();
                meta.setDisplayName("§6★ §5§lConversão de Total Coin's §c§l(Minima)");
                meta.setLore(asList(
                        " §2✔ §3Discord Coin's §fem §5Total Coin's",
                        "  §7[§2+§7] §fClique com o §4Botão Esquerdo §fpara §6Converter",
                        "   §31K Discord Coin's §fem §5" + formatNumber((double) ((100 - (int) server.get("taxa"))) / 100) + " Total Coin's §7(§fTaxa: §c" + server.get("taxa") + "%§7)",
                        " ",
                        " §2✔ §5Total Coin's §fem §3Discord Coin's",
                        "  §7[§4-§7] §fClique com o §4Botão Direito §fpara §6Converter",
                        "   §51 Total Coin's §fem §3" + formatNumber((double) (1000L * (100 - (int) server.get("taxa"))) / 100) + " Discord Coin's §7(§fTaxa: §c" + server.get("taxa") + "%§7)"
                ));
            } else if (i == 6) {
                item = new ItemStack(Material.GLASS_BOTTLE);
                meta = item.getItemMeta();
                meta.setDisplayName("§e★ §6§lConverter CASH §c§l(Minima)");
                meta.setLore(asList(
                        " §2✔ §5Total Coin's §fem §6CASH",
                        "  §7[§2+§7] §fClique com o §4Botão Esquerdo §fpara §6Converter",
                        "   §5100 Total Coin's §fem §6" + formatNumber((double) ((100 - (int) server.get("taxa"))) / 100) + " CASH §7(§fTaxa: §c" + server.get("taxa") + "%§7)",
                        " ",
                        " §2✔ §6CASH §fem §5Total Coin's",
                        "  §7[§4-§7] §fClique com o §4Botão Direito §fpara §6Converter",
                        "   §61 CASH §fem §5" + formatNumber((double) (100L * (100 - (int) server.get("taxa"))) / 100) + " Total Coin's §7(§fTaxa: §c" + server.get("taxa") + "%§7)"
                ));


            } else if (i == 20) {
                item = new ItemStack(Material.ENCHANTED_BOOK);
                meta = item.getItemMeta();
                meta.setDisplayName("§6★ §3§lConversão de Discord Coin's §4§l(Máxima)");
                meta.setLore(asList(
                        " §2✔ §aMoney §fem §3Discord Coin's",
                        "  §7[§2+§7] §fClique com o §4Botão Esquerdo §fpara §6Converter",
                        "   §a100K Money §fem §3" + formatNumber((double) (1000L * (100 - (int) server.get("taxa"))) / 100) + " Discord Coin's §7(§fTaxa: §c" + server.get("taxa") + "%§7)",
                        " ",
                        " §2✔ §3Discord Coin's §fem §aMoney",
                        "  §7[§4-§7] §fClique com o §4Botão Direito §fpara §6Converter",
                        "   §31K Discord Coin's §fem §a" + formatNumber((double) (100000L * (100 - (int) server.get("taxa"))) / 100) + " Money §7(§fTaxa: §c" + server.get("taxa") + "%§7)"
                ));
            } else if (i == 22) {
                item = new ItemStack(Material.MAP);
                meta = item.getItemMeta();
                meta.addEnchant(Enchantment.LUCK, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.setDisplayName("§6★ §5§lConversão de Total Coin's §4§l(Máxima)");
                meta.setLore(asList(
                        " §2✔ §3Discord Coin's §fem §5Total Coin's",
                        "  §7[§2+§7] §fClique com o §4Botão Esquerdo §fpara §6Converter",
                        "   §31K Discord Coin's §fem §5" + formatNumber((double) ((100 - (int) server.get("taxa"))) / 100) + " Total Coin's §7(§fTaxa: §c" + server.get("taxa") + "%§7)",
                        " ",
                        " §2✔ §5Total Coin's §fem §3Discord Coin's",
                        "  §7[§4-§7] §fClique com o §4Botão Direito §fpara §6Converter",
                        "   §51 Total Coin's §fem §3" + formatNumber((double) (1000L * (100 - (int) server.get("taxa"))) / 100) + " Discord Coin's §7(§fTaxa: §c" + server.get("taxa") + "%§7)"
                ));
            } else if (i == 24) {
                item = new ItemStack(Material.EXPERIENCE_BOTTLE);
                meta = item.getItemMeta();
                meta.setDisplayName("§e★ §6§lConverter CASH §4§l(Máxima)");
                meta.setLore(asList(
                        " §2✔ §5Total Coin's §fem §6CASH",
                        "  §7[§2+§7] §fClique com o §4Botão Esquerdo §fpara §6Converter",
                        "   §5100 Total Coin's §fem §6" + formatNumber((double) ((100 - (int) server.get("taxa"))) / 100) + " CASH §7(§fTaxa: §c" + server.get("taxa") + "%§7)",
                        " ",
                        " §2✔ §6CASH §fem §5Total Coin's",
                        "  §7[§4-§7] §fClique com o §4Botão Direito §fpara §6Converter",
                        "   §61 CASH §fem §5" + formatNumber((double) (100L * (100 - (int) server.get("taxa"))) / 100) + " Total Coin's §7(§fTaxa: §c" + server.get("taxa") + "%§7)"
                ));
            } else if (i == 0 || i == 8 || i == 18 || i == 26) {
                item = new ItemStack(Material.CHAIN);
                meta = item.getItemMeta();
                meta.setDisplayName(" ");
            } else if (i >= 9 && i <= 17) {
                item = new ItemStack(Material.IRON_BARS);
                meta = item.getItemMeta();
                meta.setDisplayName(" ");
            } else {
                item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                meta = item.getItemMeta();
                meta.setDisplayName(" ");
            }

            item.setItemMeta(meta);
            inventory.setItem(i, item);
        }

        return inventory;
    }

    public String formatNumber(double number) {
        if (number >= 1_000_000_000_000L) {
            return format(number, 1_000_000_000_000.0, "T");
        } else if (number >= 1_000_000_000) {
            return format(number, 1_000_000_000.0, "B");
        } else if (number >= 1_000_000) {
            return format(number, 1_000_000.0, "M");
        } else if (number >= 1_000) {
            return format(number, 1_000.0, "K");
        } else {
            return format(number, 1.0, "");
        }
    }

    public String format(double number, double divisor, String suffix) {
        double result = number / divisor;

        String format = String.format("%.2f%s", result, "");
        if (format.endsWith(",00")) {
            return format.substring(0, format.length() - 3) + suffix;
        } else {
            return format;
        }
    }
}