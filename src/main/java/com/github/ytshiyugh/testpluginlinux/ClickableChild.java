package com.github.ytshiyugh.testpluginlinux;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.MaterialData;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

//import java.lang.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.io.*;

public class ClickableChild implements  CommandExecutor{
    @Override
    public boolean onCommand (CommandSender sender,Command command,String label,String[] args){
        if (!(sender instanceof Player)){ //送信者がプレイヤーでなければ弾く
            return false;
        }

        int LenArgs = args.length;  //引数の個数を取得
        if (LenArgs != 4){  //引数の個数が4つ以外だったら弾く
            sender.sendMessage("error1");
            return false;
        }

        String Slot = args[0];  //アイテムのスロット
        String itemID = args[1];  //アイテムのID
        String quantity = args[2];  //アイテムの個数
        String uuid = args[3];  //メッセージのUUID

        String filename = "d:/spigot-build/message-uuid/"+uuid+".txt";

        File file = new File(filename); //UUIDをファイル名とするテキストファイルが存在するか検索する準備
        if (file.exists()){
            sender.sendMessage("invalid string.");  //引数として渡されたUUIDをファイル名とするテキストファイルが存在＝一度クリックしたことがある→よって実行しない
            return false;
        }else{
            sender.sendMessage("----------\n"+ ChatColor.AQUA +"successful\n"+ChatColor.WHITE+"UUID is "+uuid+"\n----------");  //UUIDをファイル名とするテキストファイルが存在しない＝初めての実行→よって実行する
            Path path = Paths.get(filename);
            try{
                Files.createFile(path);
            }catch(java.io.IOException ioe){
                sender.sendMessage(ioe.toString());
            }
        }


        return false;
    }
}
