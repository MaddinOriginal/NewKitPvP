package me.maddinoriginal.newkitpvp.kits.legendarykits;

import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BlackDragon extends Kit {

    @Override
    public String getName() {
        return "Black Dragon";
    }

    @Override
    public String getTag() {
        return "[BLDR]";
    }

    @Override
    public Material getMaterial() {
        return Material.DRAGON_HEAD;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.LEGENDARY;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public ItemStack[] getArmorContents() {
        return new ItemStack[0];
    }

    @Override
    public ItemStack[] getKitItems() {
        return new ItemStack[0];
    }
}
