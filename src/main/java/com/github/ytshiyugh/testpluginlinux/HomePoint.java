package com.github.ytshiyugh.testpluginlinux;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files.*;

import static java.nio.file.Files.readString;


public class HomePoint implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command,String labal,String[] args){
        if(!(sender instanceof Player)){
            return false;
        }

        if (args.length != 1){
            sender.sendMessage(ChatColor.YELLOW+"[Result]:"+ChatColor.WHITE+"引数が多すぎます。set/tel/remのどれかのみを使用してください");

            //sender.sendMessage("Your Locale:"+((Player) sender).getLocation());
            return false;
        }

        String commandArgs = args[0];
        Player player = (Player) sender;

        Path currentPath = Paths.get("");  //カレントディレクトリを取得  (d:\spigot-build)
        String currentPath2 = currentPath.toAbsolutePath().toString();

        if (commandArgs.equalsIgnoreCase("tel")){
            //tel

            String userPathStringX = currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+sender.getName()+"/x.txt";  //ファイルパスを作成(x
            String userPathStringY = currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+sender.getName()+"/y.txt";  //ファイルパスを作成(y
            String userPathStringZ = currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+sender.getName()+"/z.txt";  //ファイルパスを作成(z

            Path userPathPathX = Paths.get(userPathStringX);  //作成したファイルパスをPath型に変換
            Path userPathPathY = Paths.get(userPathStringY);  //作成したファイルパスをPath型に変換
            Path userPathPathZ = Paths.get(userPathStringZ);  //作成したファイルパスをPath型に変換

            try {
                String readThingsX = readString(userPathPathX);  //senderの座標ファイルを開いて中身をString型として取得
                String readThingsY = readString(userPathPathY);
                String readThingsZ = readString(userPathPathZ);

                double homeX = Double.parseDouble(readThingsX);
                double homeY = Double.parseDouble(readThingsY);
                double homeZ = Double.parseDouble(readThingsZ);

                Location homePoint = new Location(Bukkit.getWorld("world"),homeX,homeY,homeZ);  //ホームポイントのLocation作成
                player.teleport(homePoint);  //テレポート
                sender.sendMessage(ChatColor.GREEN+"[Result]:"+ChatColor.WHITE+"ホームポイントに転送されました。");

            } catch (IOException e) {  //ファイルが見つからないなどのエラー
                System.out.println("HomePoint Read Error");
                sender.sendMessage(ChatColor.YELLOW+"[Result]:ホームポイントが見つかりませんでした/n/home setを実行してホームポイントを設定してください");
                return false;
            }

        }else if (commandArgs.equalsIgnoreCase("set")){
            //set
            String senderDimension = player.getWorld().getName();
            if (senderDimension.equalsIgnoreCase("world")){

                String senderName = player.getName();   //senderの名前取得
                String senderLocation = player.getLocation().toString();  //senderの現在地取得

                File senderPath = new File(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName);  //確認するディレクトリ名を作成
                if (senderPath.exists()){
                    //ディレクトリがあったら
                    System.out.println("Sender Directory found.");
                }else{
                    //ディレクトリがなかったら
                    senderPath.mkdir();  //ディレクトリ作成
                }
                //まだまだ処理書くよ
                int xStart = senderLocation.lastIndexOf(",x=") + 3;
                int xEnd = senderLocation.lastIndexOf(",y=");
                String xStr = senderLocation.substring(xStart,xEnd);

                int yStart = xEnd + 3;
                int yEnd = senderLocation.lastIndexOf(",z=");
                String yStr = senderLocation.substring(yStart,yEnd);

                int zStart = yEnd + 3;
                int zEnd = senderLocation.lastIndexOf(",pitch=");
                String zStr = senderLocation.substring(zStart,zEnd);

                System.out.println("x:"+xStr+"/y:"+yStr+"/z:"+zStr);

                try{
                    //String to double
                    double xDouble = Double.parseDouble(xStr);
                    double yDouble = Double.parseDouble(yStr);
                    double zDouble = Double.parseDouble(zStr);
                    //Location set
                    Location TpTo = new Location(Bukkit.getWorld("world"),xDouble,yDouble,zDouble);



                }catch (NumberFormatException NFE){
                    //Error
                    System.out.println("HomeSet Error!");
                    sender.sendMessage(ChatColor.YELLOW+"[Result]:エラーが発生しました");
                    return false;
                }

                //まだまだ書くよ(次はファイル書き込み
                File senderOldLocationXFile = new File(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName+"/x.txt");
                File senderOldLocationYFile = new File(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName+"/y.txt");
                File senderOldLocationZFile = new File(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName+"/z.txt");

                Path senderOldLocationXPath = Paths.get(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName+"/x.txt");
                Path senderOldLocationYPath = Paths.get(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName+"/y.txt");
                Path senderOldLocationZPath = Paths.get(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName+"/z.txt");

                if (senderOldLocationXFile.exists()){
                    //If are there Remove old Location file
                    try{
                        Files.delete(senderOldLocationXPath);
                    }catch (IOException IOE){
                        return false;
                    }
                }

                if (senderOldLocationYFile.exists()){
                    try{
                        Files.delete(senderOldLocationYPath);
                    }catch (IOException IOE){
                        return false;
                    }
                }

                if (senderOldLocationZFile.exists()){
                    try{
                        Files.delete(senderOldLocationZPath);
                    }catch (IOException IOE){
                        return false;
                    }
                }

                try{
                    PrintWriter xWriter = new PrintWriter(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName+"/x.txt");
                    //write LocationX
                    xWriter.println(xStr);
                    xWriter.close();
                }catch (IOException IOE){
                    return false;
                }

                try{
                    PrintWriter yWriter = new PrintWriter(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName+"/y.txt");
                    //write LocationY
                    yWriter.println(yStr);
                    yWriter.close();
                }catch (IOException IOE){
                    return false;
                }

                try{
                    PrintWriter zWriter = new PrintWriter(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName+"/z.txt");
                    //write LocationZ
                    zWriter.println(zStr);
                    zWriter.close();
                }catch (IOException IOE){
                    return false;
                }

                sender.sendMessage(ChatColor.GREEN+"[Result]:"+ChatColor.WHITE+"ホームポイントが登録されました");


            }else{  //オーバーワールド以外のディメンジョンにいるとき(登録失敗
                sender.sendMessage(ChatColor.YELLOW+"[Result]:ホームポイントの登録はオーバーワールドで行ってください/n[Result]:その他のワールドでは登録できません");
                return false;
            }


        }else if (commandArgs.equalsIgnoreCase("rem")){
            //remove
            String senderName = sender.getName();
            Path removePathX = Paths.get(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName+"/x.txt");
            Path removePathY = Paths.get(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName+"/y.txt");
            Path removePathZ = Paths.get(currentPath2+"/plugins/TestPlugin/HomePoint/home-location/"+senderName+"/z.txt");
            sender.sendMessage(ChatColor.GREEN+"[Result]:"+ChatColor.WHITE+"ホームポイントが削除されました");

            try{
                //remove sender's Location Files
                Files.delete(removePathX);
                Files.delete(removePathY);
                Files.delete(removePathZ);
            }catch (IOException IOE){
                sender.sendMessage(ChatColor.YELLOW+"[Result]:エラーが発生しました。ホームポイントを削除できませんでした");
                System.out.println("HomeLocation Remove Error");
            }

        }else{
            sender.sendMessage(ChatColor.YELLOW+"[Result]:"+ChatColor.WHITE+"不明な引数です。set/tel/remのどれかのみを使用してください");
            return false;
        }

        return false;
    }
}
