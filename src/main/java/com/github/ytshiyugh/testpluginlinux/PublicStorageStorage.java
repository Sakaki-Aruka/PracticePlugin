package com.github.ytshiyugh.testpluginlinux;

import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.MaterialData;

import java.lang.*;


import java.util.ArrayList;
import java.util.Locale;


public class PublicStorageStorage implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args){
        if(!(sender instanceof Player)) {  //プレイヤー以外から実行されたら抜ける
            return false;
        }
        Player player = (Player) sender;
        PlayerInventory items = player.getInventory();

        for (int i=0;i<36;i++){
            try{
                String item = player.getInventory().getItem(i).getType().name().toString().toLowerCase(Locale.ROOT); //スロットiにあるアイテム名（小文字
                int itemCount = player.getInventory().getItem(i).getAmount();  //スロットiにあるアイテムの個数（int
                sender.sendMessage("Slot"+i+":"+item+"/"+itemCount);
            }catch (NullPointerException nup){
                sender.sendMessage("nurupo");
                continue;
            }
        }

        try {
            String itemOffhand = player.getInventory().getItemInOffHand().getType().name().toString().toLowerCase(Locale.ROOT); //オフハンドにあるアイテム名（小文字
            int OffhandCount = player.getInventory().getItemInOffHand().getAmount();  //オフハンドにあるアイテムの個数（int
            if (itemOffhand.equalsIgnoreCase("air")){  //オフハンドに何もなかったらSlot offhand:Nullって言う
                player.sendMessage("Slot Offhand:Null");
            }
            player.sendMessage("Slot:Offhand:"+itemOffhand+"/"+OffhandCount);
        }catch (NullPointerException nup){
            sender.sendMessage("No item on your offhand.NURUPO.");  //ぬるぽが出たらNo item on your offhand.NURUPO.って言う。（多分ぬるぽ出ない
        }

        return false;
    }
}
