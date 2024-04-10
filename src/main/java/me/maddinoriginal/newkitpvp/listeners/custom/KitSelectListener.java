package me.maddinoriginal.newkitpvp.listeners.custom;

import me.maddinoriginal.newkitpvp.events.KitSelectEvent;
import me.maddinoriginal.newkitpvp.kits.KitType;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.data.PlayerState;
import me.maddinoriginal.newkitpvp.kits.advancedkits.Elementalist;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KitSelectListener implements Listener {

    @EventHandler
    public void onKitSelect(KitSelectEvent e) {
        Player p = e.getPlayer();
        KitType kit = e.getKitType();

        if (kit == KitType.NONE) {
            e.setCancelled(true);
            return;
        }

        kit.getKit().setPlayerKit(p);
        KitPlayerManager.getInstance().getKitPlayer(p).setKitType(kit);
        //TODO KitPlayerManager.getInstance().getKitPlayer(p).setKit();
        joinArena(p);

        if (kit == KitType.ELEMENTALIST) {
            ((Elementalist) kit.getKit()).runEffects(p);
        }
    }

    private void joinArena(Player p) {
        p.teleport(new Location(p.getWorld(), 0.5, -14, 0.5)); //TODO change teleport coordinates according to map
        KitPlayerManager.getInstance().getKitPlayer(p).setPlayerState(PlayerState.INGAME);
    }
}
