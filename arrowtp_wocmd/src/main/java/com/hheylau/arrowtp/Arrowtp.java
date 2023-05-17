package com.hheylau.arrowtp;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Arrowtp extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this,this);
        getCommand("arrowsetup").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args[0]){
            case "true":
                getServer().getPluginManager().registerEvents(this,this);
                saveconfigfile(true);
                break;
            case "false":
                HandlerList.unregisterAll();
                saveconfigfile(false);
                break;
            default:
                if(getConfig().getString("status") == "true"){
                    //turn off
                    HandlerList.unregisterAll();
                    saveconfigfile(false);
            } else{
                    getServer().getPluginManager().registerEvents(this,this);
                    //turn on
                    saveconfigfile(true);
            }
                break;
        }
        return true;
    }

    private void saveconfigfile(boolean b) {
        getConfig().set("status", b);
        saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @EventHandler
    public void onArrow(ProjectileHitEvent event){
        if (event.getEntity() instanceof Arrow) {
            if (event.getEntity().getShooter() instanceof Player) {
                Player player = ((Player) event.getEntity().getShooter()).getPlayer();
                Location loc = event.getHitBlock().getLocation();
                player.teleport(transform(event.getHitBlockFace(), loc, player));
                System.out.println(event.getHitBlockFace());
            }
        }
    }
    public Location transform(BlockFace face, Location loc, Player pl){
        Location tp;
        switch (face.toString()){
            case "NORTH":
                System.out.println("North runned");
                tp =OtherBlockFace((float)-0.5, (float)0.5,loc);
                break;
            case "EAST":
                System.out.println("east runned");
                tp =OtherBlockFace((float)0.5, (float)-0.5,loc);
                break;
            case "SOUTH":
                System.out.println("sorth runned");
                tp =OtherBlockFace((float)0.5,(float)1.5,loc);
                break;
            case "WEST":
                System.out.println("WEst runned");
                tp =OtherBlockFace((float)1.5,(float)0.5,loc);
                break;
            default:
                tp = SafeToTP(loc);
                break;
        }
        return tp;
    }
    public Location OtherBlockFace(Float x, Float y, Location loca){
        loca.setX(loca.getX() + x);
        loca.setZ(loca.getZ() + y);
        return loca;

    }
    public Location SafeToTP(Location loc){
        Location find = loc;
        Block block = loc.getBlock();
        while(!(block.getBlockData().getMaterial().toString() == "AIR")) {
            find.setY(find.getY() + 1);
            block = find.getBlock();
        }find.setY(find.getY()+1);
        return find;
    }
}
