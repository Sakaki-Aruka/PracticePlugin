package com.github.ytshiyugh.testpluginlinux;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
//import org.apache.commons.lang.ObjectUtils;
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

import org.yaml.snakeyaml.*;

//import java.lang.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.io.*;

//Public storage depositの引数がallの時の処理を担当

public class PublicStorageDeposit3 implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        int argsLen = args.length;
        if (argsLen != 2) {  //引数の個数が2個以外だったら弾く
            return false;
        }

        String all = args[0];  //第１引数を格納
        String uuid = args[1];  //第２引数を格納

        if (all.equalsIgnoreCase("all")) {
            //
        } else {
            return false;  //第１引数がallじゃないので弾く
        }

        if (uuid.length() != 36){
            return false;  //第２引数が36桁以外だったら弾く
        }

        //fc51d9bd-10f1-4c55-b204-bdf9d470696d  ←UUID見本
        Path currentPath = Paths.get("");  //カレントディレクトリ取得
        String currentPath2 = currentPath.toAbsolutePath().toString();

        String filename = currentPath2+"/plugins/TestPlugin/MessageUUID/" + uuid + ".txt";
        File file = new File(filename);
        if (file.exists()) {   //一度このメッセージを実行したことがあるので弾く
            sender.sendMessage(ChatColor.RED + "You already clicked this message. The message can work only once.");
            return false;
            //

        }
        //return false;
        int ignoreWork = 0;

        Player player = (Player) sender;  //senderをplayer型に変換

        for (int i = 0;i<36;i++){
            try {
                String depositItems = player.getInventory().getItem(i).getType().name().toString().toLowerCase(Locale.ROOT);  //小文字にしたアイテムID
                int depositItemsQ = player.getInventory().getItem(i).getAmount();  //アイテムの数量(int

                String[] DenyItems = {"helmet","chestplate","leggins","boots","bow","sword","tipped_arrow","crossbow","shield","trident","enchanted_book","flint_and_steel","shovel","pickaxe","axe","hoe","rod","shears","potion","writable_book","firework_rocket","banner","carrot_on_a_stick","warped_fungus_on_a_stick","elytra","shulker_box","player_head","map","water_bucket","lava_bucket","skull","obsidian","tnt","compass"};

                for (int i3 = 0;i3 < DenyItems.length-1;i3 ++){
                    int DenyItemFind = depositItems.indexOf(DenyItems[i3]);
                    if (DenyItemFind != -1){
                        ignoreWork+=1;
                    }
                }


                if (ignoreWork == 0) {
                    String itemDirectory = currentPath2 + "/plugins/TestPlugin/StorageItems/" + depositItems;
                    System.out.println("Directory:" + itemDirectory);

                    File Dir1 = new File(itemDirectory);
                    try {
                        File[] fileArrayDir1 = Dir1.listFiles();

                        for (int i2 = 0; i2 < fileArrayDir1.length; i2++) {
                            String fileNameQ = fileArrayDir1[i2].getName();

                            int fileNameQlen = fileNameQ.length();
                            String itemQonStorage = fileNameQ.substring(0, fileNameQlen - 4);

                            int itemQonStorageInt = Integer.valueOf(itemQonStorage);

                            int itemQonStorageAfter = itemQonStorageInt + depositItemsQ;
                            File fOld = new File(itemDirectory + "/" + fileNameQ);
                            File fNew = new File(itemDirectory + "/" + itemQonStorageAfter + ".txt");
                            fOld.renameTo(fNew);


                        }
                    } catch (NullPointerException nullPo2) {
                        File newDir = new File(itemDirectory);
                        newDir.mkdir();

                        Path NewItemPath = Paths.get(itemDirectory + "/" + depositItemsQ + ".txt");
                        try {
                            Files.createFile(NewItemPath);
                        } catch (java.io.IOException ioe) {
                            sender.sendMessage("エラーが発生しています。管理者に報告してください");
                        }
                    }

                    ItemStack removeItemstack = player.getInventory().getItem(i);  //インベントリのi番目のスロットをitemstack型にして定義
                    removeItemstack.setAmount(0);  //定義したスロットのアイテムの個数を０個にする
                    sender.sendMessage(ChatColor.GREEN + "[Deposit]:" + ChatColor.WHITE + depositItems + ChatColor.AQUA + "/" + ChatColor.WHITE + depositItemsQ);
                    ignoreWork = 0;
                    //
                }else{
                    //
                    ignoreWork = 0;
                }

            }catch (NullPointerException nuruPo1){
                continue;
            }


        }
        //
        Path uuidPath = Paths.get(currentPath2+"/plugins/TestPlugin/MessageUUID/"+uuid+".txt");
        try{
            Files.createFile(uuidPath);
        }catch (java.io.IOException ioe){
            sender.sendMessage("Error on create file.");
            System.out.println("Faild to create file."+ioe);
        }
        return false;
    }
}
