package me.maddinoriginal.newkitpvp.gui;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import me.maddinoriginal.newkitpvp.events.KitBuyEvent;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class QuitServerGUI extends Menu {

    int slotYes = 10;
    int slotNo = 16;

    public QuitServerGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Leave the server?";
    }

    @Override
    public int getSlots() {
        return 9*1;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        int slot = e.getSlot();

        if (slot == slotYes) {
            p.closeInventory();
            p.kickPlayer(ChatColor.GREEN + "Thank you for playing! See you soon.");
        } else if (slot == slotNo) {
            p.closeInventory();
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(slotYes, getYes());
        inventory.setItem(slotNo, getNo());
    }

    private ItemStack getYes() {
        ItemBuilder ib = new ItemBuilder(Material.LIME_WOOL);
        ib.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "DISCONNECT");
        return ib.build();
    }

    private ItemStack getNo() {
        ItemBuilder ib = new ItemBuilder(Material.RED_WOOL);
        ib.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "CANCEL");
        return ib.build();
    }
}
