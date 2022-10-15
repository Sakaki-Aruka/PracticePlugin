package com.github.ytshiyugh.testpluginlinux;
import net.md_5.bungee.api.chat.ClickEvent;
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

//import java.lang.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class Depotest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args){
        if (!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;

        int argsLen = args.length;
        if (argsLen > 2 || argsLen < 1){
            sender.sendMessage("引数はアップロードしたいアイテムとその個数の2つ、もしくはallだけにしてください。");
            return false;
        }



        int firstArgsLen = args[0].length(); //第1引数の長さ

        if (args[0].equalsIgnoreCase("all")){
            //
            //this line for all deposit command
            return false;
        }else if (args[0].equalsIgnoreCase("help")){  //引数がhelpのときはヘルプの文章を掲載する。
            sender.sendMessage("ヘルプコマンドが実行されました。\n引数の種類：pstdには3つの引数があります。\n・help : /pstd helpを実行すると、いま表示されているヘルプページが開きます。\n・all : /pstd allを実行するとあなたのインベントリ内にあるPublicStorageにアップロード可能なアイテムが全てアップロードされます。\n・(アイテムID) : /pstd (アイテムID)を実行すると対象のアイテムが全てアップロードされます。");
            sender.sendMessage("加えて、/pstd (アイテムID) (個数)を実行すると指定したアイテムを指定した個数のみアップロードすることができます。");
            return false;
        }else if (firstArgsLen != 0){
            sender.sendMessage("?");
            return false;
            //普通のdeposit(全量
        }else{  //その他
            sender.sendMessage("その他");
            return false;
        }

        //int depositQuantity;  //とりあえず預けたい数の変数を作成しておく。





        //return false;
    }
}
