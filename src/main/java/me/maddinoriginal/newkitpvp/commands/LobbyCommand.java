package me.maddinoriginal.newkitpvp.commands;

import me.maddinoriginal.newkitpvp.utils.LobbyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LobbyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            //command sender is not a player
            sender.sendMessage("Only players may execute this command");
            return false;
        }

        Player p = (Player) sender;

        LobbyManager.getInstance().joinLobby(p);

        return false;
    }
}
