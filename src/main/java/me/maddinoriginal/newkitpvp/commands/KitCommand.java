package me.maddinoriginal.newkitpvp.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            //command sender is not a player
            sender.sendMessage("Only players may execute this command");
            return false;
        }

        Player p = (Player) sender;

        p.teleport(new Location(p.getWorld(), 0, 0, 0));
        //TODO set lobby inventory

        return false;
    }
}
