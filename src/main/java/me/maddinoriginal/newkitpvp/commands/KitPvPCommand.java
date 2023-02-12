package me.maddinoriginal.newkitpvp.commands;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.maddinoriginal.newkitpvp.gui.KitSelectorGUI;
import me.maddinoriginal.newkitpvp.utils.KitPlayerManager;
import me.maddinoriginal.newkitpvp.utils.PlayerData;
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

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("stats")) {
                PlayerData data = KitPlayerManager.getInstance().getKitPlayer(p).getData();
                p.sendMessage("=== Your Stats ===");
                p.sendMessage("Kills: " + data.getKills().getAmount());
                p.sendMessage("Deaths: " + data.getDeaths().getAmount());
                p.sendMessage("Assists: " + data.getAssists().getAmount());
                p.sendMessage("Coins: " + data.getCoins().getAmount());
                p.sendMessage("Tokens: " + data.getTokens().getAmount());
            }
            if (args[0].equalsIgnoreCase("selectkit")) {
                try {
                    MenuManager.openMenu(KitSelectorGUI.class, p);
                } catch (MenuManagerException e) {
                    throw new RuntimeException(e);
                } catch (MenuManagerNotSetupException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return false;
    }
}
