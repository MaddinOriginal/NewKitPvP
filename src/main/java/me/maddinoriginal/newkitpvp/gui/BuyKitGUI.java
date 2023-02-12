package me.maddinoriginal.newkitpvp.gui;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import me.maddinoriginal.newkitpvp.events.KitBuyEvent;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.kits.KitType;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import me.maddinoriginal.newkitpvp.utils.KitPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BuyKitGUI extends Menu {

    KitType kit;
    int slotYes = 10;
    int slotNo = 16;

    public BuyKitGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        kit = (KitType) playerMenuUtility.getData("kitToBuy");
    }

    @Override
    public String getMenuName() {
        return ChatColor.YELLOW + "" + ChatColor.BOLD + "Confirm purchase";
    }

    @Override
    public int getSlots() {
        return 9*3;
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
            Bukkit.getServer().getPluginManager().callEvent(new KitBuyEvent(p, kit));
        } else if (slot == slotNo) {
            MenuManager.openMenu(KitSelectorGUI.class, p);
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(slotYes, getYes());
        inventory.setItem(slotNo, getNo());
        inventory.setItem(13, getInfo());
    }

    private ItemStack getYes() {
        ItemBuilder ib = new ItemBuilder(Material.LIME_WOOL);
        ib.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "YES");
        return ib.build();
    }

    private ItemStack getNo() {
        ItemBuilder ib = new ItemBuilder(Material.RED_WOOL);
        ib.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "NO");
        return ib.build();
    }

    private ItemStack getInfo() {
        ItemBuilder ib = new ItemBuilder(Material.PAPER);
        int currentCoins = KitPlayerManager.getInstance().getKitPlayer(p).getData().getCoins().getAmount();

        ib.setDisplayName(ChatColor.BOLD + "" + ChatColor.YELLOW + "Are you sure?");
        ib.setLore(ChatColor.GRAY + "Buying this kit will unlock",
                ChatColor.GRAY + "it permanently.",
                "",
                ChatColor.GRAY + "Kit: " + ChatColor.GREEN + kit.getKit().getName(),
                ChatColor.GRAY + "Price: " + ChatColor.GOLD + getPrice() + " coins",
                "",
                ChatColor.GRAY + "Your current balance: ",
                ChatColor.GOLD + "" + currentCoins + " coins");
        return ib.build();
    }

    //TODO
    private int getPrice() {
        if (kit.getKit().getCategory() == KitCategory.ADVANCED) {
            return 300;
        } else {
            return 2000;
        }
    }
}
