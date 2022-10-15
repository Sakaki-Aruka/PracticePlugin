package com.github.ytshiyugh.testpluginlinux;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
//import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.w3c.dom.Text;
import org.yaml.snakeyaml.*;

//import java.lang.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.io.*;
public class PublicStoragePull2 implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args){
        if(!(sender instanceof Player)){
            return false;
        }

        if (args.length != 4){  //引数が3個以外だったら弾く
            return false;
        }
        String pull = args[0];  //第1引数　pullとしか入れていない
        String itemID = args[1];  //第2引数　アイテムIDが入っている
        String itemQStr = args[2];  //第3引数　要求量が入っている
        String uuid = args[3];  //第4引数　UUIDが入っている

        if (pull.equalsIgnoreCase("pull")){
            //
        }else{
            return false;
        }

        if (uuid.length() != 36){
            return false;  //第4引数が36桁以外だったら弾く
        }

        Player player = (Player) sender;  //senderをplayer型に変換

        Path currentPath = Paths.get("");  //カレントディレクトリを取得
        String currentPath2 = currentPath.toAbsolutePath().toString();


        String filename = currentPath2+"/plugins/TestPlugin/MessageUUID/"+uuid+".txt";
        File file = new File(filename);
        if (file.exists()){   //一度このメッセージを実行したことがあるので弾く
            sender.sendMessage(ChatColor.RED+"You already clicked this message. The message can work only once.");
            return false;
        }

        try{
            int itemQintReq = Integer.valueOf(itemQStr);  //第3引数の要求量をintに変換
        }catch (NumberFormatException nfe){
            //
            return false;
        }
        int itemQintReq = Integer.valueOf(itemQStr);  //try構文内で定義しても使用できないので再定義

        //sender.sendMessage(ChatColor.GREEN+"[Pull]:Done.");


        String itemPath = currentPath2+"/plugins/TestPlugin/StorageItems/"+itemID;
        File dir = new File(itemPath);
        File[] files = dir.listFiles();
        for (int i = 0;i < files.length;i++){
            File itemfile = files[i];
            System.out.println("itemfile:"+itemfile);


            int start = itemfile.toString().lastIndexOf("/");
            int end = itemfile.toString().lastIndexOf(".txt");

            int itemQStrOnStorage = Integer.valueOf(itemfile.toString().substring(start + 1,end));
            System.out.println("itemQStrStorage:"+itemQStrOnStorage);  //テスト




            int itemQInt = Integer.valueOf(itemQStrOnStorage);
            if (itemQInt <= itemQintReq){  //ストレージにある量よりも要求量の方が大きい場合は抜ける
                //
                sender.sendMessage(ChatColor.RED+"Too big request.");
                break;
            }else{
                //
                String itemStrUpper = "Material."+itemID.toUpperCase(Locale.ROOT);
                ItemStack giveItem = new ItemStack(Material.valueOf(itemID.toUpperCase(Locale.ROOT)),itemQintReq);
                String returnCode = player.getInventory().addItem(giveItem).toString();  //アイテムを与える処理の返り値/長さが2以外だったら渡し残しあり
                System.out.println("return Code:"+returnCode+"/ReturnCodeLength:"+returnCode.length());

                if (returnCode.length() != 2){  //アイテムを与えたときの返り値の長さが2以外だったとき
                    int RemainingStart = returnCode.lastIndexOf("{");
                    int RemainingEnd = returnCode.indexOf("}");
                    int itemIDLength = itemID.length();
                    RemainingStart += itemIDLength + 3;
                    sender.sendMessage(ChatColor.YELLOW+"[Result]:インベントリに空きがなかったので"+returnCode.substring(RemainingStart,RemainingEnd)+"個のアイテムを引き出すことができませんでした。");
                    try{
                        //
                        int writeQ = Integer.valueOf(returnCode.substring(RemainingStart+1,RemainingEnd));
                        int afterStorageInt = itemQInt - itemQintReq + writeQ;  //処理前のストレージ量 - 要求量 + 引き出せなかったアイテム量 = 処理後のストレージアイテム量
                        File fOld = new File(itemPath+"/"+itemQStrOnStorage+".txt");  //リネーム前のファイル名
                        File fNew = new File(itemPath+"/"+afterStorageInt+".txt"); //リネーム後のファイル名
                        fOld.renameTo(fNew);  //リネーム

                    }catch(NumberFormatException NFE){
                        //
                        sender.sendMessage(ChatColor.YELLOW+"[Error]");
                    }
                }else{
                    sender.sendMessage(ChatColor.GREEN+"[Result]:Pull done.");
                    int afterStorageInt = itemQInt - itemQintReq;  //処理後の量（ストレージ側
                    File fOld = new File(itemPath+"/"+itemQStrOnStorage+".txt");  //リネーム前のファイル名
                    File fNew = new File(itemPath+"/"+afterStorageInt+".txt"); //リネーム後のファイル名
                    fOld.renameTo(fNew);  //リネーム
                }

                /*
                World world = ((Player) sender).getWorld();
                Location senderLocation = ((Player) sender).getLocation();
                world.dropItem(senderLocation,giveItem);

                 */

            }
        }


        //以下UUIDファイル製造
        Path path = Paths.get(filename);  //UUIDのテキストファイルを作成
        try{
            Files.createFile(path);  //テキストファイル作成
        }catch (java.io.IOException ioe){
            sender.sendMessage("faild to create file.");  //作成エラーの時は実行者にエラー文を出す（無意味
            System.out.println("Falid to create file."+ioe);
        }
        return false;
    }
}
