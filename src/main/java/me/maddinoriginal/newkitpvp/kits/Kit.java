package me.maddinoriginal.newkitpvp.kits;

import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Kit {

    public abstract String getName();
    public abstract String getTag();
    public abstract Material getIconMaterial();
    //public abstract KitCategory getCategory();
    public abstract String getDescription();

    public abstract ItemStack[] getArmorContents();
    public abstract ItemStack[] getKitItems();
    //public abstract AbilityType getAbility();
    //public abstract PassiveType getPassive();

    public ItemStack getIcon() {
        return new ItemBuilder(getIconMaterial())
                .setDisplayName(getName())
                .setLore(getDescription())
                .build();
    }

    public void setPlayerKit(Player p) {
        p.getInventory().clear();
        p.getInventory().setContents(getKitItems());
        p.getInventory().setArmorContents(getArmorContents());
        p.setPlayerListName(getTag() + p.getDisplayName());
    }

}
