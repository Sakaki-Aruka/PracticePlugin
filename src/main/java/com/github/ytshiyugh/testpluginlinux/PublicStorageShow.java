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

import org.w3c.dom.Text;
import org.yaml.snakeyaml.*;

//import java.lang.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.io.*;
import org.bukkit.plugin.java.JavaPlugin;


public class PublicStorageShow implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args){
        if(!(sender instanceof Player)){
            return false;
        }
        //
        if (args.length != 1){
            sender.sendMessage(ChatColor.YELLOW+"引数が多すぎるか少なすぎます。");
            return false;
        }
        //
        String showReq = args[0];  //閲覧要求されているアイテムのキーワード
        //
        //sender.sendMessage("-----");
        //
        sender.sendMessage(ChatColor.AQUA+"[Show Result]↓↓↓");

        Path currentPath = Paths.get("");
        Path currentPath2 = currentPath.toAbsolutePath();  //カレントディレクトリ取得→絶対パスに変換(バッチファイルのある階層で動いてる
        System.out.println("Current path:"+currentPath2);


        String Abusolutepath = currentPath2+"/plugins/TestPlugin/StorageItems/";
        System.out.println(Abusolutepath);
        File file = new File(Abusolutepath);
        File[] files = file.listFiles();
        for (int i = 0;i < files.length;i++){
            //
            int IDstart = files[i].toString().lastIndexOf("/") + 1;
            int IDend = files[i].toString().length();
            String itemName = files[i].toString().substring(IDstart,IDend);  //ファイル名から切り出したアイテム名
            //sender.sendMessage("itemName:"+itemName);

            int find =itemName.indexOf(showReq);  //アイテム名から入力されたキーワードを探す
            if (find != -1){  //findで-1ではなかった場合（キーワードがアイテム名から見つかった場合）の処理
                //
                String Qpath = Abusolutepath+itemName;
                File fileQ = new File(Qpath);
                File[] filesQ = fileQ.listFiles();
                System.out.println("filesQ list:"+filesQ.toString());
                try{

                        //
                        int itemQStart = filesQ[0].toString().lastIndexOf("/") +1;
                        int itemQEnd = filesQ[0].toString().lastIndexOf(".txt");
                        sender.sendMessage(ChatColor.GREEN+"[Show]:"+itemName+" / "+filesQ[0].toString().substring(itemQStart,itemQEnd));

                }catch (NullPointerException nullPo){
                    System.out.println(nullPo);
                    break;
                }
            }
        }
        return false;
    }
}
