package com.hheylau.arrowtp.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class Arrow1 implements CommandExecutor, Listener {
    public boolean enable = false;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(enable == true){
            if(sender instanceof Player){
                Player pl= (Player) sender;
                pl.sendMessage(ChatColor.RED +"Player set off");
                System.out.println("player set off");}
            else if (sender instanceof CommandBlock) {
                System.out.println("CommandBlock set off");}
            else if (sender instanceof ConsoleCommandSender) {
                System.out.println("Console set off");
        } enable = false; }
        else {
            if (sender instanceof Player) {
                Player pl = (Player) sender;
                pl.sendMessage(ChatColor.GREEN + "Player set on");
                System.out.println("player set on");
            } else if (sender instanceof CommandBlock) {
                System.out.println("CommandBlock set on");
            } else if (sender instanceof ConsoleCommandSender) {
                System.out.println("Console set on");
                enable = true;

            }
        }

        return true;
    }

    //basic program
    @EventHandler
    public void onArrow(ProjectileHitEvent event){
        if (event.getEntity() instanceof Arrow) {
            if (event.getEntity().getShooter() instanceof Player) {
                Player player = ((Player) event.getEntity().getShooter()).getPlayer();
                Location loc = event.getHitBlock().getLocation();
                player.teleport(SafeToTP(loc));
            }
        }
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
