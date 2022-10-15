package com.github.ytshiyugh.testpluginlinux;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;




public class AllowFry implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        int args0_len = args.length; //引数の要素数を取得
        if (args0_len != 1){ //要素数が1つ以外だったらコマンド失敗
            return false;
        }

        if (args[0].equalsIgnoreCase("on")) {  //第1引数が大文字小文字区別無しのonだった場合飛行モードに設定
            sender.sendMessage("Fly mode on!");
            ((Player) sender).setAllowFlight(true);
            return true;  //返り値:コマンド成功

        }else if (args[0].equalsIgnoreCase("off")) {  //第1引数が大文字小文字区別なしのoffだった場合飛行モードを終了
            sender.sendMessage("Fly mode off!");
            ((Player) sender).setAllowFlight(false);
            return true;  //返り値：コマンド成功

        }else{
            return false;  //第1引数無し/第1引数が0n/off以外/引数が一つ以上の3つ以外の場合
        }
    }
}
