package com.hheylau.arrowtp;

import com.hheylau.arrowtp.commands.Arrow1;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArrowTP extends JavaPlugin{

    @Override
    public void onEnable() {
        // Plugin startup logic
            System.out.println("ArrowTP is loaded");
            getServer().getPluginManager().registerEvents(new Arrow1(),this);
        //getCommand("atp").setExecutor(new Arrow1());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void enabled(){

    }

}
