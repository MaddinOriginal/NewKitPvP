package me.maddinoriginal.newkitpvp.commands.subcommands;

import me.kodysimpson.simpapi.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CoinsCommand extends SubCommand {

    @Override
    public String getName() {
        return "coins";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Get amount of coins of a player";
    }

    @Override
    public String getSyntax() {
        return "/kitpvp coins <get/set/add/subtract> <player> <amount>";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
