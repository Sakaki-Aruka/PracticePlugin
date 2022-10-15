package com.github.ytshiyugh.testpluginlinux;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Inventory;



public class PublicStorage implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        if (!(sender instanceof Player)) {
            return false;
        }



        int args_len = args.length;
        if (args_len != 1) {
            return false;
        }
        String new_name = args[0];

        String original_name = sender.getName(); //元の名前を取得

        try {
            if (original_name.equals(new_name)) {
                ((Player) sender).setDisplayName(new_name);  //元の名前と変更したい名前が等しい場合、元の名前だけを表示する
            } else {
                ((Player) sender).setDisplayName(new_name + "[" + original_name + "]");  //元の名前と変更したい名前が等しくない場合、元の名前と新しい名前を表示するようにする
            }
            sender.sendMessage("Your name was updated " + args[0] + "!!");
            return false;
        }catch(CommandException e){  //例外
            System.out.println(e);
            sender.sendMessage("Error");
            return false;
        }
    }
}
