package com.github.ytshiyugh.testpluginlinux;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;


public class DiamondAdvancement implements Listener{
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        //event
        Player player = event.getPlayer();

        //get current directory
        Path currentPath = Paths.get("");
        //絶対パスに変換してからStr型に変換
        String currentPath2 = currentPath.toAbsolutePath().toString();

        //採掘したブロックのID(小文字
        String mineBlock = event.getBlock().getType().toString().toLowerCase(Locale.ROOT);
        //採掘したブロックが深層ダイヤモンド鉱石かダイヤモンド鉱石なら処理をする
        if (mineBlock.equalsIgnoreCase("diamond_ore") || mineBlock.equalsIgnoreCase("deepslate_diamond_ore")){
            //Check DiamondAllow File codes.
            //player.sendMessage("You allowed to join over world.");

            String senderPath = currentPath2+"/plugins/TestPlugin/DiamondAllow/"+player.getName() ;
            File addDiamond = new File(senderPath);

            //ダイヤ取得済みリストに追加されていたら
            if (addDiamond.exists() || player.getScoreboardTags().contains("DiamondAllow")){
                System.out.println("Diamond Check OK");

            }else{
                player.sendMessage(ChatColor.AQUA+"[Welcome!]ダイヤを取得したのでオーバーワールドに参加できるようになりました\n[Let's Join!]/wtp oを実行してオーバーワールドにジャンプしましょう！");
                player.addScoreboardTag("DiamondAllow");
            }
        }



    }
}
