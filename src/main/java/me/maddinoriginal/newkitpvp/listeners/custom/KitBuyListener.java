package me.maddinoriginal.newkitpvp.listeners.custom;

import me.maddinoriginal.newkitpvp.events.KitBuyEvent;
import me.maddinoriginal.newkitpvp.kits.KitType;
import me.maddinoriginal.newkitpvp.utils.KitPlayerManager;
import me.maddinoriginal.newkitpvp.utils.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class KitBuyListener implements Listener {

    @EventHandler
    public void onKitBuy(KitBuyEvent e) {
        Player p = e.getPlayer();
        KitType kit = e.getKitType();
        int price = kit.getKit().getPrice();
        PlayerData data = KitPlayerManager.getInstance().getKitPlayer(p).getData();

        if (data.getCoins().getAmount() < price) {
            e.setCancelled(true);
            p.sendMessage(ChatColor.RED + "You do not have enough coins!");
            return;
        }

        data.getCoins().decreaseAmount(price);
        data.unlockKit(kit);
        p.sendMessage(ChatColor.GREEN + "Kit unlocked successfully!");
    }
}
