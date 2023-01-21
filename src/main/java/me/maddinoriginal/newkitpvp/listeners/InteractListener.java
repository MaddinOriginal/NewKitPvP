package me.maddinoriginal.newkitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract (PlayerInteractEvent e) {
        Player p = e.getPlayer();

        //return if player does not hold an item
        if (e.getItem() == null)
            return;

        //TODO check which item got clicked to open the corresponding menu
    }

}
