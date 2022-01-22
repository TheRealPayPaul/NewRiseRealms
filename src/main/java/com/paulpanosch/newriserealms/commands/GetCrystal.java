package com.paulpanosch.newriserealms.commands;

import com.paulpanosch.newriserealms.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetCrystal implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            player.getInventory().addItem(ItemManager.getPowerfulCrystal());
            return true;
        }
        return false;
    }
}
