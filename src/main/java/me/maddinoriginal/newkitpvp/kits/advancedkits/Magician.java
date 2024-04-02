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

public class Magician extends Kit {

    @Override
    public String getName() {
        return "Magician";
    }

    @Override
    public String getTag() {
        return "[MAGE]";
    }

    @Override
    public Material getMaterial() {
        return Material.ENCHANTING_TABLE;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.ADVANCED;
    }

    @Override
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[]{PlayStyle.DAMAGE, PlayStyle.KNOCKBACK};
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
