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

//Public Storage Pullの対応(Depositって書いたけどPullです

public class PublicStorageDeposit4 implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args){
        if(!(sender instanceof Player)){
            return false;
        }


        if (args.length != 2){  //引数が2個以外だったら弾く
            sender.sendMessage("引数が少なすぎるか多すぎます。アイテムIDと要求量の2つのみにしてください。");
            return false;
        }

        String itemKeyWord = args[0];


        try{
            int itemQint = Integer.valueOf(args[1]);  //2個目の引数を数字に変換
            if (itemQint < 1 && itemQint >2000){  //第2引数の値は1~2000であるか
                sender.sendMessage("無効な値です。第2引数には1から2000の間の整数を入力してください");
                return false;
            }
        }catch(NumberFormatException nfe){  //第2引数をint型へ変換するときにエラーが発生したら
            sender.sendMessage("第2引数には自然数を入力してください");
            return false;
        }

        String uuid = UUID.randomUUID().toString();  //UUIDを生成(共通して利用することで悪用を防ぐ

        Path currentPath = Paths.get(""); //カレントディレクトリの取得
        String currentPath2 = currentPath.toAbsolutePath().toString();

        ArrayList <String> itemNameListArray = new ArrayList<>();  //アイテム名を格納するリスト
        File dir = new File(currentPath2+"/plugins/TestPlugin/StorageItems");
        File[] file = dir.listFiles();

        int findCount = 0;

        for (int i = 0;i < file.length;i++){
            //
            String itemNameStr = file[i].toString();  //アイテム名を格納したリストを順々に取り出していく
            if (itemNameStr.equalsIgnoreCase(itemKeyWord)){  //取り出した要素がキーワード（引数）と一致したらbreakして出る
                break;
            }else{
                //
                int findResult = itemNameStr.indexOf(itemKeyWord);  //findメソッドで取り出した要素の中にキーワード（引数）が含まれていないかチェック
                if (findResult != -1 && findCount <= 10){  //キーワードが含まれていてfindcountが10以下だったら表示
                    //
                    String commandStr = "/pstp2 pull "+uuid;  //クリックしたら実行されるコマンド（UUID付き

                    TextComponent message = new TextComponent(ChatColor.AQUA+"[Pull]:"+ChatColor.WHITE+itemNameStr);  //アイテム名を表示（クリッカブルメッセージ
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,commandStr));  //実行するコマンドを設定
                    findCount++;  //findcountに1加算
                    sender.spigot().sendMessage(message);  //コマンドを表示
                }else{
                    //
                    break;
                }

            }
        }
    sender.sendMessage(ChatColor.GREEN+"[Pull]:"+ChatColor.WHITE+"You send download-request.");
        //
        return false;
    }
}
