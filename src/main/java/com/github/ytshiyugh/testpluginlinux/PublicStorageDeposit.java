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

//Public Storage Depositを使用したら最初に呼び出されるやつ。
//ここで引数を判定してコマンドを出す。

public class PublicStorageDeposit implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        //get First 2 letter
        String BEFind = sender.getName().substring(0, 2);

        if (BEFind.indexOf("BE") != -1) {
            sender.sendMessage("You are BE Player.Aren't you?");

            if (args[0].equals("all")){
                int ignoreWork = 0;
                Path currentPath = Paths.get("");
                String currentPath2 = currentPath.toAbsolutePath().toString();

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

                    //return false;
                }
                return false;
            }

            if (args.length != 2){
                sender.sendMessage(ChatColor.YELLOW+"[Error]:引数が足りません。アイテムID/個数の順番で記述してください");
                return false;
            }

            String itemName = args[0];
            String[] DenyItems = {"helmet", "chestplate", "leggins", "boots", "bow", "sword", "tipped_arrow", "crossbow", "shield", "trident", "enchanted_book", "flint_and_steel", "shovel", "pickaxe", "axe", "hoe", "rod", "shears", "potion", "writable_book", "firework_rocket", "banner", "carrot_on_a_stick", "warped_fungus_on_a_stick", "elytra", "shulker_box", "player_head", "map", "water_bucket", "lava_bucket", "skull", "obsidian", "tnt", "compass","echo_shard","goat_horn","bundle","disc_fragment","recovery_compass","frogspawn","tadpole_bucket"};

            for (int i10i = 0;i10i < DenyItems.length -1;i10i++){
                if (itemName.equalsIgnoreCase(DenyItems[i10i])){
                    sender.sendMessage(ChatColor.YELLOW+"[Error]:You can't deposit this item.");
                    return false;
                }
            }


            int remainingAmount = 0;
            //get current directory


            try{
                int itemAmountReq = Integer.valueOf(args[1]);
                int itemAmountHaveTotal = 0;
                Path currentPath = Paths.get("");
                String currentPath2 = currentPath.toAbsolutePath().toString();
                int nullPoCounter = 0;



                for (int i1i = 0;i1i<36;i1i++){
                    //itemName research



                    //Lower-case itemID
                    try{
                        String itemHaveName = player.getInventory().getItem(i1i).getType().toString().toLowerCase(Locale.ROOT);
                        //get amount that slot(i1i) item
                        int itemAmount_i1i = player.getInventory().getItem(i1i).getAmount();


                        //comparison RequestItemId and user have ItemId
                        if (itemHaveName.equals(itemName)){
                            //comparison Request amount and have item-amount
                            if (itemAmount_i1i >= itemAmountReq){
                                //set remaining-item amount
                                remainingAmount = itemAmount_i1i - itemAmountReq;
                                ItemStack remainingItemStack = player.getInventory().getItem(i1i);
                                remainingItemStack.setAmount(remainingAmount);
                                sender.sendMessage(ChatColor.GREEN+"[Deposit]:"+ChatColor.WHITE+itemName+ChatColor.AQUA+"/"+ChatColor.WHITE+itemAmountReq);
                                sender.sendMessage("Remaining item amount:"+remainingAmount);

                                File itemStorageFile = new File(currentPath2+"/plugins/TestPlugin/StorageItems/"+itemName);
                                File[] itemStorageAmountFile = itemStorageFile.listFiles();
                                for (int i5i=0;itemStorageAmountFile.length>i5i;i5i++){
                                    //
                                    String amountStr = itemStorageAmountFile[i5i].getName();
                                    String amountIntStr = amountStr.substring(0,amountStr.length()-4);
                                    System.out.println("amountIntStr:"+amountIntStr);
                                    try{
                                        int amouIntInt = Integer.valueOf(amountIntStr);
                                        File fOld = new File(currentPath2+"/plugins/TestPlugin/StorageItems/"+itemName+"/"+amountIntStr+".txt");
                                        int amountAfter = amouIntInt + itemAmountReq;
                                        File fNew = new File(currentPath2+"/plugins/TestPlugin/StorageItems/"+itemName+"/"+amountAfter+".txt");
                                        fOld.renameTo(fNew);
                                        return false;
                                    }catch (NumberFormatException NFE){
                                        System.out.println("NFE:"+NFE);
                                        sender.sendMessage("NFE");
                                        return false;
                                    }
                                }
                                break;
                            }
                    }


                    }catch (NullPointerException NullPo){
                        if (nullPoCounter == 0){
                            sender.sendMessage(ChatColor.YELLOW+"[Error]:正しくないアイテムIDを入力している可能性があります。");
                        }

                        nullPoCounter += 1;
                    }

                }return false;


            }catch(NumberFormatException NFE){
                System.out.println("BE Deposit NFE");
                sender.sendMessage("[Error]:Sorry ,The request that you send is invalid request.");
                return false;
            }
            //return false;
            //BE finish


        } else {
            int argsLen = args.length;
            if (argsLen != 1) {  //引数が１つ以外だったら弾く
                sender.sendMessage("引数が多すぎるか少なすぎるよ");
                return false;
            }

            //カレントディレクトリを取得
            Path currentPath = Paths.get("");
            //カレントディレクトリを絶対パスに変換
            String currentPath2 = currentPath.toAbsolutePath().toString();

            String senderPathStr = currentPath2 + "/plugins/TestPlugin/DiamondAllow/" + sender.getName();
            File senderDiamondPath = new File(senderPathStr);
            //進捗ダイヤモンドを達成したかチェック
            if (senderDiamondPath.exists() || player.getScoreboardTags().contains("DiamondAllow")) {
                //ok
                System.out.println("Diamond check OK");
            } else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "[Result]:このコマンドを使用するには進捗[ダイヤモンド!]を達成してください。");
                return false;
            }

            if (args[0].equalsIgnoreCase("all")) {
                UUID uuid = UUID.randomUUID();  //実行するコマンドに貼り付けるUUID
                String uuidStr = uuid.toString();  //UUIDを文字列型に変換
                //String iStr = String.valueOf(i);  //iを文字列型に変換
                String commandStr = "/pstd3 all " + uuidStr;  //ClickEventで実行するコマンド

                TextComponent message = new TextComponent(ChatColor.AQUA + "[Deposit]:" + ChatColor.WHITE + "---All---"); //アイテム名とリクエストした個数を表示してクリックできるよう
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandStr));  //commandStrで設定したコマンドを実行する
                sender.spigot().sendMessage(message);
                //all depositの時の処理を書く
                //sender.sendMessage("you issued the server command that Public Strorage Deposit All.");
                return false;


            } else if (args[0].equalsIgnoreCase("slot")) {
                //スロット単位のdepositの処理書く
                sender.sendMessage("You issued the server command that Public Storage Deposit Slot-All");
            }

            int DenyCount = 0;

            try {
                //
                int reqQ = Integer.valueOf(args[0]);
                if (reqQ < 1 || reqQ > 64) {  //要求された量が1個未満、もしくは64個より多いときは弾く
                    sender.sendMessage("Over 64 or under 0");
                    return false;
                }
                String reqQStr = String.valueOf(reqQ);
                sender.sendMessage(ChatColor.AQUA + "[Deposit]:↓↓↓/n[Deposit]:↓↓↓");

                for (int i = 0; i < 36; i++) {
                    //アイテム取得とかいろいろ
                    try {
                        String item = player.getInventory().getItem(i).getType().name().toString().toLowerCase(Locale.ROOT); //i番目のスロットからアイテム名取得
                        int itemQ = player.getInventory().getItem(i).getAmount(); //i番目のスロットからアイテムの数を取得
                        if (itemQ >= reqQ) {  //i番目のスロットのアイテム数が要求量以上だったら表示
                            //
                            UUID uuid = UUID.randomUUID();  //実行するコマンドに貼り付けるUUID
                            String uuidStr = uuid.toString();  //UUIDを文字列型に変換
                            String iStr = String.valueOf(i);  //iを文字列型に変換
                            String commandStr = "/pstd2 " + i + " " + reqQ + " " + uuidStr;  //ClickEventで実行するコマンド

                            String[] DenyItems = {"helmet", "chestplate", "leggins", "boots", "bow", "sword", "tipped_arrow", "crossbow", "shield", "trident", "enchanted_book", "flint_and_steel", "shovel", "pickaxe", "axe", "hoe", "rod", "shears", "potion", "writable_book", "firework_rocket", "banner", "carrot_on_a_stick", "warped_fungus_on_a_stick", "elytra", "shulker_box", "player_head", "map", "water_bucket", "lava_bucket", "skull", "obsidian", "tnt", "compass"};
                            int DenyItemsLen = DenyItems.length - 1;
                            for (int ii = 0; ii < DenyItemsLen; ii++) {
                                //
                                int FindDeny = item.indexOf(DenyItems[ii]);
                                if (FindDeny != -1) {
                                    //IDに禁止アイテムが含まれるものが見つかった時
                                    sender.sendMessage(ChatColor.AQUA + "[Deposit]:" + ChatColor.YELLOW + "This item can't deposit.");
                                    DenyCount += 1;
                                    break;
                                }
                            }

                            if (DenyCount == 0) {
                                TextComponent message = new TextComponent(ChatColor.AQUA + "[Deposit]:" + ChatColor.WHITE + item + "/" + reqQStr); //アイテム名とリクエストした個数を表示してクリックできるよう
                                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, commandStr));  //commandStrで設定したコマンドを実行する
                                sender.spigot().sendMessage(message);  //depositするアイテム名と個数を表示する
                            }

                            DenyCount = 0;


                        }
                    } catch (NullPointerException nullPo) {
                        //System.out.println(nullPo);  //i番目のスロットにアイテムが無かったらぬるぽ ガッ
                        ///sender.sendMessage("error2");
                        //continue;
                    }
                }
            } catch (NumberFormatException numE) {
                sender.sendMessage("引数が不正です");
                return false;
            }

            //sender.sendMessage("error3");
            return false;
        //if BEFind 終わり
        }
    }



}
