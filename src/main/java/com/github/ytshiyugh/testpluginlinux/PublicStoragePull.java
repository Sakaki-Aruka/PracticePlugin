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

public class PublicStoragePull implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args){
        if(!(sender instanceof Player)){
            return false;
        }


        if (args.length != 2){  //引数が2個以外だったら弾く
            sender.sendMessage("引数が少なすぎるか多すぎます。アイテムIDと要求量の2つのみにしてください");
            return false;
        }

        String itemKeyWord = args[0];
        String BEFindStr = sender.getName().substring(0,2);
        if (BEFindStr.indexOf("BE")!=-1){
            //BE match
            Player player = (Player)sender;

            try{
                int itemQint = Integer.valueOf(args[1]);  //2個目の引数を数字に変換
                if (itemQint < 1 || itemQint >2000){  //第2引数の値は1~2000であるか
                    sender.sendMessage("無効な値です。第2引数には1から2000の間の整数を入力してください");
                    return false;
                }
            }catch(NumberFormatException nfe){  //第2引数をint型へ変換するときにエラーが発生したら
                sender.sendMessage("第2引数には自然数を入力してください");
                return false;
            }

            sender.sendMessage("[Result]:Are you BE Player?Aren't you?");
            //get current directory
            Path currentPath = Paths.get("");
            String ABScurrent = currentPath.toAbsolutePath().toString();
            //to use to check the target item's file there
            File itemStorageParent = new File(ABScurrent+"/plugins/TestPlugin");
            String itemStoragePathStr = ABScurrent+"/plugins/TestPlugin/StorageItems/"+itemKeyWord;
            Path itemStoragePathPath = Paths.get(itemStoragePathStr);
            int itemAmountRequestInt = Integer.valueOf(args[1]);

            String senderPathStr = ABScurrent+"/plugins/TestPlugin/DiamondAllow/"+sender.getName();
            File senderDiamondPath = new File(senderPathStr);
            //進捗ダイヤモンドを達成したかチェック
            if (senderDiamondPath.exists() || player.getScoreboardTags().contains("DiamondAllow")){
                //ok
                System.out.println("Diamond check OK");
            }else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "[Result]:このコマンドを使用するには進捗[ダイヤモンド!]を達成してください。");
                return false;
            }
            //ダイヤチェック終わり

            if (itemStorageParent.exists()){
                //引数に指定されたアイテムのファイルが存在する場合
                //アイテム名のファイルの中身を取得(個数.txt
                File[] itemAmountFile = itemStoragePathPath.toFile().listFiles();
                for (int i =0;i<itemAmountFile.length;i++){
                    //
                    String itemAmountStr = itemAmountFile[i].toString();
                    int lastBackslash = itemAmountStr.lastIndexOf("/");
                    itemAmountStr = itemAmountStr.substring(lastBackslash+1,itemAmountStr.length()-4);
                    try{
                        //exchange string to int(item amount
                        int itemAmountInt = Integer.valueOf(itemAmountStr);
                        if (itemAmountRequestInt >=itemAmountInt){
                            sender.sendMessage(ChatColor.YELLOW+"[Error]:ストレージにあるアイテムの個数よりも多いリクエストです。/n[Error]:ストレージ上には"+itemAmountStr+"個あります。");
                            return false;
                        }
                        int remainingStorageInt = itemAmountInt - itemAmountRequestInt;

                        File fOld = new File(itemStoragePathStr+"/"+itemAmountStr+".txt");
                        File fNew = new File(itemStoragePathStr+"/"+remainingStorageInt+".txt");
                        fOld.renameTo(fNew);

                        String itemIdUpper = itemKeyWord.toUpperCase(Locale.ROOT);
                        ItemStack giveItem = new ItemStack(Material.valueOf(itemIdUpper),itemAmountRequestInt);
                        String returnCode = player.getInventory().addItem(giveItem).toString();
                        if (returnCode.length() != 2){
                            //アイテムを与えたときの返り値の長さが2以外だったとき(与えられなかったアイテムがあるとき
                            int RemainingStart = returnCode.lastIndexOf("{");
                            int RemainingEnd = returnCode.indexOf("}");
                            int ItemIdLength = itemKeyWord.length();
                            RemainingStart += ItemIdLength + 3;
                            sender.sendMessage(ChatColor.YELLOW+"[Result]:インベントリに空きがなかったので"+returnCode.substring(RemainingStart,RemainingEnd)+"個のアイテムを引き出すことができませんでした。");
                            try{
                                int writeAmount = Integer.valueOf(returnCode.substring(RemainingStart,RemainingEnd));
                                //処理前のストレージ量 - 要求量 + 引き出せなかったアイテム量 = 処理後のストレージアイテム量
                                int afterStorageInt = itemAmountInt - itemAmountRequestInt + writeAmount;

                                File fNew2 = new File(itemStoragePathStr+"/"+afterStorageInt+".txt");
                                fNew.renameTo(fNew2);
                                return false;
                            }catch(NumberFormatException NFE2){
                                System.out.println("NFE2:"+NFE2);
                                sender.sendMessage(ChatColor.YELLOW+"[Error];NFE");
                                return false;
                            }

                        }
                        return false;

                    }catch(NumberFormatException NFE){
                        System.out.println("NFE:"+NFE);
                        sender.sendMessage(ChatColor.YELLOW+"[Error]:エラー");
                        return false;
                    }
                }
            }else{
                //引きスに指定されたアイテムのファイルが存在しない場合
                sender.sendMessage(ChatColor.YELLOW+"[Error]:リクエストされたアイテムはストレージ上に存在しませんでした。");
                return false;
            }

            //BE終わり

        }else{
            //JE match
            try{
                int itemQint = Integer.valueOf(args[1]);  //2個目の引数を数字に変換
                if (itemQint < 1 || itemQint >2000){  //第2引数の値は1~2000であるか
                    sender.sendMessage("無効な値です。第2引数には1から2000の間の整数を入力してください");
                    return false;
                }
            }catch(NumberFormatException nfe){  //第2引数をint型へ変換するときにエラーが発生したら
                sender.sendMessage("第2引数には自然数を入力してください");
                return false;
            }

            String uuid = UUID.randomUUID().toString();  //UUIDを生成(共通して利用することで悪用を防ぐ

            ArrayList <String> itemNameListArray = new ArrayList<>();  //アイテム名を格納するリスト

            Path currentPath = Paths.get("");  //カレントディレクトリを取得
            String currentPath2 = currentPath.toAbsolutePath().toString();



            String senderPathStr = currentPath2+"/plugins/TestPlugin/DiamondAllow/"+sender.getName();
            File senderDiamondPath = new File(senderPathStr);
            //進捗ダイヤモンドを達成したかチェック
            if (senderDiamondPath.exists()){
                //ok
                System.out.println("Diamond check OK");
            }else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "[Result]:このコマンドを使用するには進捗[ダイヤモンド!]を達成してください。");
                return false;
            }



            File dir = new File(currentPath2+"/plugins/TestPlugin/StorageItems");
            File[] files = dir.listFiles();

            int findCount = 0;
            int suggestCount = 0;

            int itemQint = Integer.valueOf(args[1]);  //try構文内で定義したものは使用できないので再定義

            for (int i = 0;i < files.length;i++){
                //
                String itemNameStr = files[i].toString();  //アイテム名を格納したリストを順々に取り出していく
                int itemNameSplit = itemNameStr.lastIndexOf("/") + 1;  //後ろから検索したときのスラッシュの位置（アイテム名切り出し用
                int itemNameFileLen = itemNameStr.length();


                String itemNameStrSplit = itemNameStr.substring(itemNameSplit,itemNameFileLen); //アイテム名


                int findResult = itemNameStrSplit.indexOf(itemKeyWord);  //findメソッドで取り出した要素の中にキーワード（引数）が含まれていないかチェック
                //System.out.println("string find result:"+findResult);
                //sender.sendMessage(itemNameStr);
                if (itemNameStrSplit.equalsIgnoreCase(itemKeyWord)){  //取り出した要素がキーワード（引数）と一致したら色々処理をする
                    System.out.println("perfect-match.");

                    String commandStr = "/pstp2 pull "+itemNameStrSplit+" "+itemQint+" "+uuid;  //クリックされたら実行されるコマンド（UUID付き

                    TextComponent message = new TextComponent(ChatColor.AQUA+"[Pull]:"+ChatColor.WHITE+itemNameStrSplit+" / "+itemQint);  //アイテム名を表示（クリッカブルメッセージ
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,commandStr));  //実行するコマンドを設定

                    sender.spigot().sendMessage(message);  //コマンドを表示
                    //break;
                }else{//アイテム名が引数と一致しなかった場合

                    if (findResult != -1 && findCount <= 10){  //キーワードが含まれていてfindcountが10以下だったら表示
                        //
                        String commandStr = "/pstp2 pull "+itemNameStrSplit+" "+itemQint+" "+uuid;  //クリックしたら実行されるコマンド（UUID付き

                        if (suggestCount == 0){
                            sender.sendMessage(ChatColor.YELLOW+"↓↓↓ Are you looking for these items? ↓↓↓");
                            suggestCount +=1;
                        }

                        TextComponent message = new TextComponent(ChatColor.AQUA+"[Pull]:"+ChatColor.WHITE+itemNameStrSplit+" / "+itemQint);  //アイテム名を表示（クリッカブルメッセージ
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,commandStr));  //実行するコマンドを設定
                        findCount++;  //findcountに1加算
                        sender.spigot().sendMessage(message);  //コマンドを表示
                    }else{
                        //System.out.println("No keyword.");
                        //continue;
                        //
                        //break;
                    }

                }
            }
        }




        //sender.sendMessage(ChatColor.YELLOW+"[Pull]:"+ChatColor.WHITE+"You send download-request.");
        //
        return false;
    }
}

