package me.maddinoriginal.newkitpvp.gui;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import me.maddinoriginal.newkitpvp.utils.Achievement;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AchievementsGUI extends Menu {

    public AchievementsGUI(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Achievements";
    }

    @Override
    public int getSlots() {
        return 9*4;
    }

    @Override
    public boolean cancelAllClicks() {
        return false;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {

    }

    @Override
    public void setMenuItems() {
        for (Achievement achievement : Achievement.values()) {
            ItemStack item = new ItemBuilder(Material.DIAMOND)
                    .setDisplayName(achievement.getName())
                    .setLore(achievement.getDescription())
                    .build();
            inventory.addItem(item);
        }
    }
}
