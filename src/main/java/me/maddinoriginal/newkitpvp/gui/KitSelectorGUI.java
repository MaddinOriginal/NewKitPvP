package me.maddinoriginal.newkitpvp.gui;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import me.maddinoriginal.newkitpvp.events.KitSelectEvent;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.kits.KitType;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import org.apache.commons.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitSelectorGUI extends Menu {



    public KitSelectorGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Kit Selector";
    }

    @Override
    public int getSlots() {
        return 9*2;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        for (KitType kitType : KitType.values()) {
            if (kitType == KitType.NONE) {
                continue;
            }
            if (kitType.getKit() == null || kitType.getKit().getMaterial() == null) {
                System.out.println("[ERROR] Missing kit or material for " + kitType + " Kit!");
                continue;
            }

            if (kitType.getKit().getMaterial().equals(e.getCurrentItem().getType())) { //TODO
                Player p = (Player) e.getWhoClicked();
                InventoryAction action = e.getAction();

                if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                    //open KitSettings Menu
                    MenuManager.openMenu(KitSettingsGUI.class, playerMenuUtility.getOwner());
                } else {
                    //give player kit items / armor / inventory
                    if (kitType.getKit().getCategory() == KitCategory.STANDARD || KitPlayerManager.getInstance().getKitPlayer(p).getPlayerData().hasKitUnlocked(kitType)) {
                        p.closeInventory();
                        Bukkit.getServer().getPluginManager().callEvent(new KitSelectEvent(p, kitType));
                    } else {
                        int currentCoins = KitPlayerManager.getInstance().getKitPlayer(p).getPlayerData().getCoins().getAmount();
                        if (currentCoins >= getKitPrice(kitType.getKit())) {
                            playerMenuUtility.setData("kitToBuy", kitType);
                            MenuManager.openMenu(BuyKitGUI.class, p);
                        } else {
                            p.closeInventory();
                            p.sendMessage(ChatColor.RED + "You do not have enough coins to buy this kit!");
                        }
                    }
                }
                return;
            }
        }
    }

    @Override
    public void setMenuItems() {
        for (KitType kitType : KitType.values()) {
            if (kitType == KitType.NONE) {
                continue;
            }
            if (kitType.getKit() == null) {
                System.out.println("[ERROR] Missing Icon for " + kitType + " Kit!");
                continue;
            }
            inventory.addItem(createKitIcon(kitType));
        }
    }

    private ItemStack createKitIcon(KitType kitType) {
        Kit kit = kitType.getKit();

        ItemBuilder ib = new ItemBuilder(Material.BARRIER);
        if (kit.getMaterial() != null) {
            ib.setMaterial(kit.getMaterial());
        }
        if (kit.getName() != null) {
            ib.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + kit.getName());
        } else {
            ib.setDisplayName(ChatColor.DARK_RED + "Unknown Kit");
        }

        ib.setLore(createIconLore(kitType));
        ib.hideAttributes();
        return ib.build();
    }

    private List<String> createIconLore(KitType kitType) {
        Kit kit = kitType.getKit();
        List<String> lore = new ArrayList<>();

        char lineSymbol = '\u2594';
        String line = lineSymbol + "";
        lore.add(ChatColor.DARK_GRAY + "" + line.repeat(16));

        try {
            lore.add(ChatColor.GRAY + "Category: " + kit.getCategory().getName() + " Kit");
        } catch (NullPointerException e) {
            lore.add(ChatColor.DARK_RED + "ERROR_UNKNOWN_CATEGORY");
        }
        lore.add("");

        try {
            String description = WordUtils.wrap(kit.getDescription(), 26);
            for (String s : description.split(System.lineSeparator())) {
                lore.add(ChatColor.GRAY + s);
            }
        } catch (NullPointerException e) {
            lore.add(ChatColor.DARK_RED + "ERROR_MISSING_DESCRIPTION");
        }
        lore.add("");

        //TODO
        //lore.add(ChatColor.DARK_GREEN + "Strong against: " + ChatColor.WHITE + "" + ChatColor.ITALIC + "%strength");
        //lore.add(ChatColor.DARK_RED + "Weak against: " + ChatColor.WHITE + "" + ChatColor.ITALIC + "%weakness");
        //lore.add("");

        if (getKitPrice(kit) <= 0) {
            lore.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + ChatColor.BOLD + "FREE");
        }
        else {
            String priceLore = ChatColor.GRAY + "Price: " + ChatColor.GOLD + getKitPrice(kit) + " coins";
            if (KitPlayerManager.getInstance().getKitPlayer(p).getPlayerData().hasKitUnlocked(kitType)) {
                for (ChatColor color : ChatColor.values()) {
                    priceLore = priceLore.replace(color.toString(), color + "" + ChatColor.STRIKETHROUGH);
                }
                lore.add(priceLore);
                lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Kit already unlocked!");
            } else {
                lore.add(priceLore);
                lore.add("");
                lore.add(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Click to buy this kit");
                lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Shift-Click for more info...");
                return lore;
            }
        }
        lore.add("");

        lore.add(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Click to select this kit");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Shift-Click for more info...");

        return lore;
    }

    private int getKitPrice(Kit kit) {
        if (kit.getCategory() == null) {
            return 0;
        }
        switch (kit.getCategory()) {
            case ADVANCED:
                return 300;
            case LEGENDARY:
                return 2000;
            default:
                return 0;
        }
    }
}
