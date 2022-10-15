package com.github.ytshiyugh.testpluginlinux;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static java.nio.file.Files.readString;
import static org.bukkit.Bukkit.getServer;
import static org.bukkit.Bukkit.getWorld;

public class WorldTeleport implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        if(!(sender instanceof Player)){
            return false;
        }
        Player player =(Player)sender;

        //now current directory get
        Path currentPath = Paths.get("");
        String currentPath2 = currentPath.toAbsolutePath().toString();


        //↓ネザー用(本番環境に入れる前に削除しておく
        double xN = 0.0;
        double yN = 200.0;
        double zN = 0.0;
        Location locationNether = new Location(Bukkit.getWorld("world_nether"),xN,yN,zN);
        //ネザー用終わり

        //オーバーワールド用↓(本番環境用
        double xO = -16.0;
        double yO = 69.0;
        double zO = 64.0;
        Location locationOverworld = new Location(Bukkit.getWorld("world"),xO,yO,zO);
        //オーバーワールド用終わり

        //ウィークリーエンド用(本番環境
        double xW = 10.0;
        double yW = 61.0;
        double zW = 0.0;
        Location locationWeeklyEnd = new Location(Bukkit.getWorld("weekly_end"),xW,yW,zW);
        //ウィークリーエンド用終わり

        //Everyoneワールド用(本番環境
        double xE = 0.0;
        double yE = 102.0;
        double zE = 0.0;
        Location locationEveryone = new Location(Bukkit.getWorld("everyone"),xE,yE,zE);
        //Everyoneワールド用終わり

        //Lobby用(本番環境
        double xL = 0.0;
        double yL = -60.0;
        double zL = 0.0;
        Location locationLobby = new Location(Bukkit.getWorld("test"),xL,yL,zL);
        //Lobby用終わり

        if (args.length != 1){  //引数が1つ以外だったら弾く
            return false;
        }

        String worldArg = args[0];  //引数

        if (worldArg.equalsIgnoreCase("n")){
            sender.sendMessage(ChatColor.GREEN+"[Result]:"+ChatColor.WHITE +"You will teleport to nether.");
            try{
                player.teleport(locationNether);
            }catch (IllegalArgumentException IAE2){
                System.out.println("IAE 2");
            }


        }else if (worldArg.equalsIgnoreCase("o")){
            sender.sendMessage(ChatColor.GREEN+"[Result]:"+ChatColor.WHITE +"You will teleport to over world.");

            String senderPathStr = currentPath2+"/plugins/TestPlugin/DiamondAllow/"+sender.getName();
            File senderDiamondPath = new File(senderPathStr);
            //進捗ダイヤモンドを達成したかチェック
            if (senderDiamondPath.exists() || player.getScoreboardTags().contains("DiamondAllow")){
                //ok
                System.out.println("Diamond Check OK");
            }else{
                sender.sendMessage(ChatColor.YELLOW+"[Result]:このコマンドを使用するには進捗[ダイヤモンド!]を達成してください。");
                return false;
            }

            try{
                player.teleport(locationOverworld);

                //

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
                //
            }catch (IllegalArgumentException IAE1){
                System.out.println("IAE 1");
            }


        }else if (worldArg.equalsIgnoreCase("w")){
            sender.sendMessage(ChatColor.GREEN+"[Result]:"+ChatColor.WHITE +"Teleported weekly_end");

            String senderPathStr = currentPath2+"/plugins/TestPlugin/DiamondAllow/"+sender.getName();
            File senderDiamondPath = new File(senderPathStr);
            //進捗ダイヤモンドを達成したかチェック
            if (senderDiamondPath.exists() || player.getScoreboardTags().contains("DiamondAllow")){
                //ok
                System.out.println("Diamond check OK");
            }else{
                sender.sendMessage(ChatColor.YELLOW+"[Result]:このコマンドを使用するには進捗[ダイヤモンド!]を達成してください。");
                return false;
            }

            try{
                player.teleport(locationWeeklyEnd);
            }catch(IllegalArgumentException IAE3){
                System.out.println("IAE3");
            }


        }else if (worldArg.equalsIgnoreCase("e")){
            sender.sendMessage(ChatColor.GREEN+"[Result]:"+ChatColor.WHITE +"Teleported Everyone");
            try{
                player.teleport(locationEveryone);
            }catch (IllegalArgumentException IAE4){
                System.out.println("IAE4");
            }


        } else if (worldArg.equalsIgnoreCase("l")){
            sender.sendMessage(ChatColor.GREEN+"[Result]:"+ChatColor.WHITE +"Teleported Lobby");
            try{
                player.teleport(locationLobby);
            }catch (IllegalArgumentException IAE5){
                System.out.println("IAE5");
            }


        }else{
            sender.sendMessage("You send invalid argument.");
            return false;
        }


        return false;
    }
}
