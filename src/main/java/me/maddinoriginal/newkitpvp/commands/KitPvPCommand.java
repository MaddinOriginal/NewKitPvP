package me.maddinoriginal.newkitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitPvPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            //command sender is not a player
            sender.sendMessage("Only players may execute this command");
            return false;
        }

        Player p = (Player) sender;

        //TODO implement permissions correctly
        if (!p.isOp())
            return false;

        //TODO change this it is just for testing purposes
        p.sendMessage("Saturation Level: " + p.getSaturation());

        return false;
    }
}
