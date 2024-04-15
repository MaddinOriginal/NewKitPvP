package me.maddinoriginal.newkitpvp.kits.advancedkits;

import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.PlayStyle;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Cowboy extends Kit {

    @Override
    public String getName() {
        return "Cowboy";
    }

    @Override
    public String getTag() {
        return "[COW]";
    }

    @Override
    public Material getMaterial() {
        return Material.LEAD;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.ADVANCED;
    }

    @Override
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[0];
    }

    @Override
    public String getDescription() {
        return "A Cowboy with a gun and shit";
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
