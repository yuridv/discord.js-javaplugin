package dev.mrdragon.javaplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class dsCollect implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Você não pode utilizar esse comando pelo console...");
        } else {
            Player player = (Player) sender;
            player.sendMessage("O comando /dscollect foi executado");
            System.out.println("O comando /dscollect foi executado");
        }

        return true;
    }
}
