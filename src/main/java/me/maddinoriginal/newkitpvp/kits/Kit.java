package me.maddinoriginal.newkitpvp.kits;

import me.maddinoriginal.newkitpvp.utils.PlayStyle;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Kit {

    public abstract String getName();
    public abstract String getTag();

    public abstract Material getMaterial();
    public abstract KitCategory getCategory();
    public abstract PlayStyle[] getPlayStyles();
    public abstract String getDescription();

    public abstract ItemStack[] getArmorContents();
    public abstract ItemStack[] getKitItems();
    //public abstract AbilityType getAbility();
    //public abstract PassiveType getPassive();

    public void setPlayerKit(Player p) {
        p.getInventory().clear();
        p.getInventory().setContents(getKitItems());
        p.getInventory().setArmorContents(getArmorContents());
        p.setPlayerListName(getTag() + p.getDisplayName());
    }

    public int getPrice() {
        if (getCategory() == null) {
            return 0;
        }
        switch (getCategory()) {
            case ADVANCED:
                return 350;
            case LEGENDARY:
                return 2000;
            default:
                return 0;
        }
    }
}
