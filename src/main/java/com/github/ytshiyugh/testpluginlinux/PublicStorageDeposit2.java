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

import org.yaml.snakeyaml.*;

//import java.lang.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;
import java.io.*;
public class PublicStorageDeposit2 implements CommandExecutor{

    public static void UUIDwrite(String filename){
        //

    }

    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args){
        if (!(sender instanceof Player)){
            return false;
        }
        //
        int argsLen = args.length;  //引数の数
        if (argsLen != 3){  //引数の数が3以外だったら弾く
            return false;
        }

        //test

        String slot = args[0];  //リクエストされたアイテムスロット(str
        String itemQ = args[1];  //リクエストされたアイテム量(str
        String uuid = args[2];  //リクエストメッセージのUUID(str

        Player player = (Player) sender;

        Path currentPath = Paths.get(""); //カレントディレクトリ取得
        String currentPath2 = currentPath.toAbsolutePath().toString();
        String filename = currentPath2+"/plugins/TestPlugin/MessageUUID/"+uuid+".txt";


        File file = new File(filename);
        if (file.exists()){   //一度このメッセージを実行したことがあるので弾く
            sender.sendMessage(ChatColor.RED+"You already clicked this message. The message can work only once.");
            return false;
        }else{  //始めてこのメッセージを実行するので弾かない

            try{
                int slotInt = Integer.valueOf(slot);  //リクエストされたアイテムスロット(int
                int itemQInt = Integer.valueOf(itemQ);  //リクエストされたアイテム量(int

                if (slotInt >35 || slotInt < 0){  //スロット番号が1未満、もしくは35より大きかったら弾く
                    return false;
                }

                if (itemQInt > 64 || itemQInt < 1){  //アイテム量が1未満、もしくは64より大きかったら弾く
                    return false;
                }
                //Player player = (Player) sender;  //senderをplayer型に変換

                try {
                    String itemName = player.getInventory().getItem(slotInt).getType().name().toLowerCase(Locale.ROOT);  //インベントリのアイテム名を取得

                    sender.sendMessage(ChatColor.GREEN + "[Deposit Info]:" + ChatColor.WHITE + itemName + " / " + itemQ );  //何をアップロードしたか表示

                    //アイテム数が記録されたファイルがあるディレクトリのパス
                    String itemDirectory = currentPath2+"/plugins/TestPlugin/StorageItems/" + itemName;
                    System.out.println("Directory:" + itemDirectory);
                    //fileオブジェクト作成
                    File Dir1 = new File(itemDirectory);
                    //ファイル名のリスト作成（実際は1ファイルしかないのでforで回す必要がない
                    try {
                        File[] fileArrayDir1 = Dir1.listFiles();

                        //fileオブジェクトを文字列型に変換
                        for (int i = 0; i < fileArrayDir1.length; i++) {
                            //ファイル名取得
                            String fileNameQ = fileArrayDir1[i].getName();
                            //sender.sendMessage("getName:" + fileNameQ);
                            int fileNameQlen = fileNameQ.length();
                            //色々して数だけ取得
                            String itemQonStorage = fileNameQ.substring(0, fileNameQlen - 4);  //ストレージ上にある個数(Str
                            //sender.sendMessage("on storage quantity:" + itemQonStorage);

                            int itemQonStorageInt = Integer.valueOf(itemQonStorage);  //ストレージ上のアイテム数(int
                            //String itemQonStorageStr = String.valueOf(itemQonStorageInt);

                            //---------------アイテム消去をここら辺に書く
                            int userHaveAmount = player.getInventory().getItem(slotInt).getAmount();
                            int AfterMinus = userHaveAmount - itemQInt;
                            if (AfterMinus >= 0){
                                ItemStack removeItemstack = player.getInventory().getItem(slotInt);
                                removeItemstack.setAmount(AfterMinus);

                                int itemQonStorageAfter = itemQonStorageInt + itemQInt;  //要求量とストレージ上の量を加算した量
                                File fOld = new File(itemDirectory + "/" + fileNameQ);  //変更前のファイル名
                                File fNew = new File(itemDirectory + "/" + itemQonStorageAfter + ".txt");  //変更後のファイル名
                                //sender.sendMessage("after file name:" + itemDirectory + "/" + itemQonStorageAfter + ".txt");  //変更後のファイル名をプレイヤーに表示
                                fOld.renameTo(fNew);  //変更前のファイル名から変更後のファイル名に変更

                                int slotRemove = Integer.valueOf(slot);
                                String itemNameUpper = itemName.toUpperCase(Locale.ROOT);
                            }else{
                                sender.sendMessage("[Error]:Your requests amount is too big. So can't deposit items.");
                                return false;
                            }






                        }
                    } catch (NullPointerException nullPo1) {
                        //ディレクトリ（アイテム名）が存在しなかったら
                        File newDir = new File(itemDirectory);  //fileオブジェクトを作成
                        newDir.mkdir();  //ディレクトリを作成

                        Path NewItemPath = Paths.get(itemDirectory + "/" + itemQ + ".txt");
                        try {
                            Files.createFile(NewItemPath);
                        } catch (java.io.IOException ioeNew) {
                            sender.sendMessage("エラーが発生しています。管理者に報告してください。");
                        }

                        /*File fOld = new File(itemDirectory + "/" + fileNameQ);  //変更前のファイル名
                        File fNew = new File(itemDirectory + "/" + itemQonStorageAfter + ".txt");  //変更後のファイル名
                        sender.sendMessage("after file name:" + itemDirectory + "/" + itemQonStorageAfter + ".txt");  //変更後のファイル名をプレイヤーに表示
                        fOld.renameTo(fNew);  //変更前のファイル名から変更後のファイル名に変更*/
                    }
                }catch(NullPointerException nullPo2){
                    sender.sendMessage(ChatColor.YELLOW+"Error: No items on the slot.");
                }






                /*try {
                    sender.sendMessage("読み込み中");
                    String Storagename = "d:/spigot-build/public_storage/storage_items/storage_items/"+itemName + ".txt";  //ファイルパス
                    File fileRead = new File(Storagename);  //ファイルオブジェクトを作成してファイルパスを代入
                    FileReader filereader = new FileReader(fileRead);  //作成したファイルオブジェクトをファイルリーダーオブジェクトに代入
                    BufferedReader BuffRead = new BufferedReader(filereader);  //まとめて読み込むオブジェクト作成
                    String readThings = BuffRead.toString();  //まとめて読み込んだものを文字列型に変換
                    int readThingsInt = Integer.valueOf(readThings);  //読み込んで文字列型に変換したものをint型に変換しておく

                    int onStorageInt = readThingsInt + itemQInt;  //リクエスト量と読み込んだ量を合算

                    String readThingsIntStr = String.valueOf(onStorageInt);
                    String onStorageIntStr = String.valueOf(onStorageInt);

                    sender.sendMessage("Before upload:"+readThingsIntStr+"/After upload:"+onStorageIntStr);

                    try{
                        FileWriter filewriter = new FileWriter(Storagename);  //filewriterオブジェクト作成
                        PrintWriter printwriter = new PrintWriter(new BufferedWriter(filewriter));  //printwriterオブジェクト作成
                        printwriter.println(onStorageInt);
                        printwriter.close();


                    }catch (java.io.IOException ioe){
                        sender.sendMessage("The file-error occured.");
                      //
                    }

                }catch (java.io.FileNotFoundException fnf){  //ファイルが存在しないときのエラーを拾う(fnf)
                    sender.sendMessage("The file not found.");
                    //
                }

                //Yaml yaml = new Yaml();

                */

                Path path = Paths.get(filename);  //UUIDのテキストファイルを作成
                try{
                    Files.createFile(path);  //テキストファイル作成
                }catch (java.io.IOException ioe){
                    sender.sendMessage("faild to create file.");  //作成エラーの時は実行者にエラー文を出す（無意味
                    System.out.println("Falid to create file."+ioe);
                }

            }catch(NumberFormatException numE){  //stringからintに変換するときにエラー出たら弾く
                return false;
            }
        }
        return false;
    }
}
