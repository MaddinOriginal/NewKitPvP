package me.maddinoriginal.newkitpvp.listeners;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.data.models.PlayerStats;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.maps.LobbyManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onPlayerJoinServer(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        //messages
        e.setJoinMessage(NewKitPvP.getInstance().getPrefix() + ChatColor.DARK_GREEN + p.getDisplayName() + ChatColor.YELLOW + " joined KitPvP."); //set join message
        p.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Welcome to KitPvP",
                ChatColor.YELLOW + "A minigame designed by MaddinOrignal",
                10, 50, 20); //send title message to player

        //register player as KitPlayer
        KitPlayerManager.getInstance().addKitPlayer(p.getUniqueId(), p.getName());

        //teleport player to lobby and set lobby items
        LobbyManager.getInstance().joinLobby(p);

        //database
        try {
            doDatabaseStuff(p);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        //other
        setPlayerValues(p);
        setTabList(p);
    }

    @EventHandler
    public void onPlayerLeaveServer(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        //unregister player as KitPlayer
        KitPlayerManager.getInstance().removeKitPlayer(p.getUniqueId());

        e.setQuitMessage(NewKitPvP.getInstance().getPrefix() + ChatColor.DARK_GREEN + p.getDisplayName() + ChatColor.YELLOW + " has disconnected."); //set quit message
    }


    private void doDatabaseStuff(Player p) throws SQLException {
        PlayerStats stats = NewKitPvP.getInstance().getDatabase().findPlayerStatsByUUID(p.getUniqueId().toString());

        if (stats == null) {
            stats = new PlayerStats(p.getUniqueId().toString(), 0, 0, 0, 0);
            NewKitPvP.getInstance().getDatabase().createPlayerStats(stats);
        }
    }

    private void setPlayerValues(Player p) {
        p.setFoodLevel(20); //set hunger bar to full
        p.setSaturation(0); //set saturation to 0 because higher saturation speeds up natural health regen too much
        p.setGameMode(GameMode.ADVENTURE); //set players' gamemode to adventure
        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Integer.MAX_VALUE); //old combat spam clicking
    }

    //set the tab list header and footer for the player who joined
    private void setTabList(Player p) {
        p.setPlayerListHeader(ChatColor.GOLD + "" + ChatColor.BOLD + "Welcome to KitPvP by MaddinOriginal");
        p.setPlayerListFooter(ChatColor.YELLOW + "" + ChatColor.ITALIC + "For more info use /kitpvp help");
    }
}
