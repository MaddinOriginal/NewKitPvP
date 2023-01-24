package me.maddinoriginal.newkitpvp.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();

        String format = "<kit> <rank><player>: <message>";
        format = format.replace("<kit>", ChatColor.BLUE + "[KIT]");
        format = format.replace("<rank>", ChatColor.GREEN + "[VIP]");
        format = format.replace("<player>", p.getDisplayName());
        format = format.replace("<message>", msg);

        e.setFormat(format);
    }
}
