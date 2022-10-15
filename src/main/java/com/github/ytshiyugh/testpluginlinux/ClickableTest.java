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

import java.util.UUID;

//import java.lang.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class ClickableTest implements  CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args){
        if (!(sender instanceof Player)){
            return false;
        }
        UUID tellUUID = UUID.randomUUID();
        String tellUUIDstr = tellUUID.toString();

        String tellthings = "/clickabletest-child 1 1 1 "+tellUUIDstr;  //下のRuncommandで実行する独自コマンド

        TextComponent message = new TextComponent(ChatColor.YELLOW +"Click me!");
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,tellthings));  //ここに独自コマンドを設置しておく（default: trueのやつ
        sender.spigot().sendMessage(message);
        String click = message.getClickEvent().toString();
        sender.sendMessage("click event:"+click);


        return false;
    }
}
