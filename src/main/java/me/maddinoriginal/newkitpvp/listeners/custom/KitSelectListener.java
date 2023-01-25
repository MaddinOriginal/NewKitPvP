package me.maddinoriginal.newkitpvp.listeners.custom;

import me.maddinoriginal.newkitpvp.events.KitSelectEvent;
import me.maddinoriginal.newkitpvp.kits.KitType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KitSelectListener implements Listener {

    @EventHandler
    public void onKitSelect (KitSelectEvent e) {
        Player p = e.getPlayer();
        KitType kit = e.getKitType();

        if (kit == KitType.NONE) {
            e.setCancelled(true);
            return;
        }

        kit.getKit().setPlayerKit(p);
        p.teleport(new Location(p.getWorld(), 0, 100, 0)); //TODO change teleport coordinates
    }
}
