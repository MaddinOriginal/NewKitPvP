package me.maddinoriginal.newkitpvp.kits.advancedkits;

import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.PlayStyle;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Type=
 * Armor=
 *
 * Strong against=
 * Weak against=
 *
 * Active Ability=
 * Supportive Passive=
 * Survivability Passive=
 * Finisher Passive=
 */

public class Ninja extends Kit {

    @Override
    public String getName() {
        return "Ninja";
    }

    @Override
    public String getTag() {
        return "[NINJ]";
    }

    @Override
    public Material getMaterial() {
        return Material.BLACK_DYE;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.ADVANCED;
    }

    @Override
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[]{PlayStyle.MOBILITY, PlayStyle.CONTROL};
    }

    @Override
    public String getDescription() {
        return "The ninja is the master of deception and illusion. Good luck trying to even hit them consistently.";
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
