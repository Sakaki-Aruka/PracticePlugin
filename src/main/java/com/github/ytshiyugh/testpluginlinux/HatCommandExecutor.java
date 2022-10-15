package com.github.ytshiyugh.testpluginlinux;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


public class HatCommandExecutor implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        PlayerInventory inv = player.getInventory();
        ItemStack hand = inv.getItemInMainHand();
        ItemStack helmet = inv.getHelmet();
        inv.setItemInMainHand(helmet);
        inv.setHelmet(hand);
        return false;
    }
}
