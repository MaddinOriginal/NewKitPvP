package me.maddinoriginal.newkitpvp.listeners.custom;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.events.PlayerQuitKitPvPEvent;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitKitPvPListener implements Listener {

    @EventHandler
    public void onPlayerQuitKitPvP(PlayerQuitKitPvPEvent e) {
        Player p = e.getPlayer();

        //unregister player as KitPlayer
        KitPlayerManager.getInstance().removeKitPlayer(p.getUniqueId());

        //broadcast message that player quit the game
        Bukkit.broadcastMessage(NewKitPvP.getInstance().getPrefix() + ChatColor.DARK_GREEN + p.getDisplayName() + ChatColor.YELLOW + " has disconnected.");
    }
}
