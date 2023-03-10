package me.maddinoriginal.newkitpvp.listeners;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.utils.KitPlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onPlayerJoinServer(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        //register player as KitPlayer
        KitPlayerManager.getInstance().addKitPlayer(p.getUniqueId(), p.getName());

        //TODO teleport player to lobby
        //TODO set playerstate to lobby state

        setPlayerValues(p);
        setTabList(p);

        e.setJoinMessage(NewKitPvP.getInstance().getPrefix() + ChatColor.DARK_GREEN + p.getDisplayName() + ChatColor.YELLOW + " joined KitPvP."); //set join message
    }

    @EventHandler
    public void onPlayerLeaveServer(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        //unregister player as KitPlayer
        KitPlayerManager.getInstance().removeKitPlayer(p.getUniqueId());

        e.setQuitMessage(NewKitPvP.getInstance().getPrefix() + ChatColor.DARK_GREEN + p.getDisplayName() + ChatColor.YELLOW + " has disconnected."); //set quit message
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
