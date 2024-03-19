package me.maddinoriginal.newkitpvp.utils;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.data.PlayerState;
import me.maddinoriginal.newkitpvp.gui.AchievementsGUI;
import me.maddinoriginal.newkitpvp.gui.KitSelectorGUI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LobbyManager {

    /**
     * singleton instance handling
     */

    private LobbyManager() {
        setup();
    }

    private static class SingletonHelper {
        private static final LobbyManager INSTANCE = new LobbyManager();
    }

    public static LobbyManager getInstance() {
        return LobbyManager.SingletonHelper.INSTANCE;
    }

    /**
     * actual class
     */

    private NamespacedKey key = new NamespacedKey(NewKitPvP.getInstance(), "lobby-item-key");

    private ItemStack[] lobbyInventory;
    private final String kitSelectorId = "kit-selector";
    private final String achievementsId = "achievements";
    private final String quitServerId = "quit-server";

    private void setup() {
        initLobbyItems();
    }

    private void initLobbyItems() {
        lobbyInventory = new ItemStack[9];

        lobbyInventory[0] = new ItemBuilder(Material.CHEST)
                .setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Kit Selector")
                .setLore(ChatColor.GRAY + "Click to open the", ChatColor.GRAY + "Kit Selector")
                .setPersistentData(key, kitSelectorId)
                .build();
        lobbyInventory[4] = new ItemBuilder(Material.DIAMOND)
                .setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Achievements Menu")
                .setLore(ChatColor.GRAY + "Click to open the", ChatColor.GRAY + "Achievements Menu")
                .setPersistentData(key, achievementsId)
                .build();
        lobbyInventory[8] = new ItemBuilder(Material.BARRIER)
                .setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Quit KitPvP")
                .setLore(ChatColor.GRAY + "Click to leave the", ChatColor.GRAY + "server")
                .setPersistentData(key, quitServerId)
                .build();
    }

    public void joinLobby(Player player) {
        teleportToLobby(player);
        KitPlayerManager.getInstance().getKitPlayer(player).setPlayerState(PlayerState.LOBBY);
        setLobbyInventory(player);
    }

    public void performAction(Player player, String string) {
        switch (string) {
            case kitSelectorId:
                try {
                    MenuManager.openMenu(KitSelectorGUI.class, player);
                } catch (MenuManagerException | MenuManagerNotSetupException e) {
                    throw new RuntimeException(e);
                }
                break;
            case achievementsId:
                try {
                    MenuManager.openMenu(AchievementsGUI.class, player);
                } catch (MenuManagerException | MenuManagerNotSetupException e) {
                    throw new RuntimeException(e);
                }
                break;
            case quitServerId:
                player.kickPlayer(ChatColor.GREEN + "Thank you for playing! See you soon.");
                break;
            default:
                break;
        }
    }

    public void setLobbyInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setContents(lobbyInventory);
        player.getInventory().setHeldItemSlot(0);
    }

    public void teleportToLobby(Player player) {
        player.teleport(new Location(player.getWorld(), 0.5, 0, 0.5)); //TODO change teleport coordinates according to map
    }

    public NamespacedKey getLobbyItemKey() {
        return key;
    }
}
