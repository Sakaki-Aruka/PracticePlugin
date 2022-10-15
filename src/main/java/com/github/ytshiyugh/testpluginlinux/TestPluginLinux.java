package com.github.ytshiyugh.testpluginlinux;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TestPluginLinux extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Hello world");
        getCommand("hat").setExecutor(new HatCommandExecutor());
        getCommand("fly").setExecutor(new AllowFry());
        getCommand("pstnamechange").setExecutor(new PublicStorage());
        getCommand("houchi").setExecutor(new HouchiSet());
        getCommand("psttest").setExecutor(new PublicStorageStorage());
        getCommand("depotest").setExecutor(new Depotest());
        getCommand("clickabletest").setExecutor(new ClickableTest());
        getCommand("clickabletest-child").setExecutor((new ClickableChild()));
        getCommand("pstd").setExecutor(new PublicStorageDeposit());
        getCommand("pstd2").setExecutor(new PublicStorageDeposit2());
        getCommand("pstd3").setExecutor(new PublicStorageDeposit3());
        getCommand("pstp").setExecutor(new PublicStoragePull());
        getCommand("pstp2").setExecutor(new PublicStoragePull2());
        getCommand("psts").setExecutor(new PublicStorageShow());
        getCommand("wtp").setExecutor((new WorldTeleport()));
        getCommand("home").setExecutor(new HomePoint());

        getServer().getPluginManager().registerEvents(new DiamondAdvancement(),this);




        /*
        World worldWorld = Bukkit.getWorld("world");
        World worldNether = Bukkit.getWorld("world_nether");
        World worldEnd = Bukkit.getWorld("world_the_end");
         */

        WorldCreator loadWorld = new WorldCreator("world");
        WorldCreator loadNether = new WorldCreator("world_nether");
        WorldCreator loadEnd = new WorldCreator("world_the_end");

        ///*
        //本番環境に出すときはここにワールドを追加する

        WorldCreator loadWeeklyEnd = new WorldCreator("weekly_end").environment(World.Environment.THE_END);
        WorldCreator loadEveryone = new WorldCreator("everyone");
        WorldCreator loadLobby = new WorldCreator("test");

        //*/

        Bukkit.createWorld(loadWorld);
        Bukkit.createWorld(loadNether);
        Bukkit.createWorld(loadEnd);

        ///*
        //本番環境に出すときはここに読み込みを追加

        Bukkit.createWorld(loadWeeklyEnd);
        Bukkit.createWorld(loadEveryone);
        Bukkit.createWorld(loadLobby);

        //*/


        //以下ファイルが存在するかの確認
        Path pathItemStorage = Paths.get("TestPlugin");
        Path pathMessageUUID = Paths.get("MessageUUIDs");
        if (Files.exists(pathItemStorage)){
            getLogger().info("PublicStorage Directory Check OK.");
        }else{
            getLogger().info("PublicStorage Directory Check NO.");

        }

        if (Files.exists(pathMessageUUID)){
            getLogger().info("Message UUID Directory Check OK.");
        }else{
            getLogger().info("Message UUID Directory Check No.");
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
