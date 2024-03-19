package me.maddinoriginal.newkitpvp.listeners.custom;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.events.PlayerJoinKitPvPEvent;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinKitPvPListener implements Listener {

    @EventHandler
    public void onPlayerJoinKitPvP(PlayerJoinKitPvPEvent e) {
        Player p = e.getPlayer();

        //register player as KitPlayer
        KitPlayerManager.getInstance().addKitPlayer(p.getUniqueId(), p.getName());

        //TODO teleport player to lobby
        //TODO set playerstate to lobby state

        setPlayerValues(p);
        setTabList(p);

        //broadcast message that player joined the game
        Bukkit.broadcastMessage(NewKitPvP.getInstance().getPrefix() + ChatColor.DARK_GREEN + p.getDisplayName() + ChatColor.YELLOW + " joined KitPvP.");
    }

    private void setPlayerValues(Player p) {
        p.setFoodLevel(20); //set hunger bar to full
        p.setSaturation(Integer.MAX_VALUE); //set saturation very high
        p.setGameMode(GameMode.ADVENTURE); //set players gamemode to adventure
        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Integer.MAX_VALUE); //old combat spam clicking
    }

    //set the tab list header and footer for the player who joined
    private void setTabList(Player p) {
        p.setPlayerListHeader(ChatColor.GOLD + "" + ChatColor.BOLD + "Welcome to KitPvP by MaddinOriginal");
        p.setPlayerListFooter(ChatColor.YELLOW + "" + ChatColor.ITALIC + "For more info use /kitpvp help");
    }
}
