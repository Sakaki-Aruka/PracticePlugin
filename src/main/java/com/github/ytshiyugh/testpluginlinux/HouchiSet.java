package com.github.ytshiyugh.testpluginlinux;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HouchiSet implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args){
        if (!(sender instanceof Player)) {
            return false;
        }
        //String new_name = args[0];
        int args_len = args.length;
        if (args_len != 1) {  //引数が1つ以外だったらコマンド失敗
            return false;
        }

        String start_or_finish = args[0];
        Player player = (Player)sender;
        if (start_or_finish.equalsIgnoreCase("start")) {  //引数が大文字小文字区別なしのstartだったら元の名前に「:放置中」を追加する。
            String original_name = sender.getName();
            String afk_name = original_name+":放置中";
            player.setPlayerListName(afk_name);  //リストに表示される名前を変更
            sender.sendMessage("You entered 放置mode.");
            player.setDisplayName(afk_name);  //チャット欄に表示される名前を変更
        }
        else if (start_or_finish.equalsIgnoreCase("end")) {  //引数が大文字小文字区別なしのendだったら名前から「:放置中」を消す
            String original_name = sender.getName();
            player.setPlayerListName(original_name);  //リストに表示される名前を元に戻す
            player.setDisplayName(original_name);
            player.sendMessage("You deactived 放置mode.");  //チャット欄に表示される名前を元に戻す
        }
        return false;
    }
}
