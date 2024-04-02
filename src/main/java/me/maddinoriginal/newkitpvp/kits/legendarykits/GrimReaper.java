package me.maddinoriginal.newkitpvp.kits.legendarykits;

import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import me.maddinoriginal.newkitpvp.utils.PlayStyle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class GrimReaper extends Kit {

    @Override
    public String getName() {
        return "Grim Reaper";
    }

    @Override
    public String getTag() {
        return "[GRIM]";
    }

    @Override
    public Material getMaterial() {
        return Material.NETHERITE_HOE;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.LEGENDARY;
    }

    @Override
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[]{PlayStyle.MOBILITY, PlayStyle.DAMAGE};
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //Create boots and add them to the returning (armor contents) ItemStack
        armor[0] = new ItemBuilder(Material.NETHERITE_BOOTS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Boots")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                .build();

        //Create leggings and add them to the returning (armor contents) ItemStack
        armor[1] = new ItemBuilder(Material.NETHERITE_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Leggings")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                .build();

        //Create chest plate and add them to the returning (armor contents) ItemStack
        armor[2] = new ItemBuilder(Material.NETHERITE_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Chestplate")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                .build();

        //Create helmet and add them to the returning (armor contents) ItemStack
        armor[3] = new ItemBuilder(Material.NETHERITE_HELMET)
                .setDisplayName(ChatColor.YELLOW + getName() + " Helmet")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[9];

        items[0] = new ItemBuilder(Material.NETHERITE_HOE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Scythe")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = null; //TODO

        items[2] = null;
        items[3] = null;
        items[4] = null;
        items[5] = null;
        items[6] = null;
        items[7] = null;
        items[8] = null;

        return items;
    }
}
