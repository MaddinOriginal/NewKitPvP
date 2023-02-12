package me.maddinoriginal.newkitpvp.kits.standardkits;

import me.maddinoriginal.newkitpvp.abilities.items.DashAbilityItem;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Melee Kit with a stone sword
 * mostly iron armor, except for diamond chestplate
 * Ability: TODO
 * Passive: TODO
 */

public class Tank extends Kit {

    @Override
    public String getName() {
        return "Tank";
    }

    @Override
    public String getTag() {
        return "[TANK]";
    }

    @Override
    public Material getMaterial() {
        return Material.DIAMOND_CHESTPLATE;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.STANDARD;
    }

    @Override
    public String getDescription() {
        return "Can take a lot of hits thanks to a strong set of armor. Paired with abilities to regain health this kit is a master of surviving.";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] items = new ItemStack[4];

        //boots
        items[0] = new ItemBuilder(Material.IRON_BOOTS)
                .setDisplayName(ChatColor.YELLOW + "Tank Boots")
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //leggings
        items[1] = new ItemBuilder(Material.IRON_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + "Tank Leggings")
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //chestplate
        items[2] = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + "Tank Chestplate")
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //helmet
        items[3] = new ItemBuilder(Material.IRON_HELMET)
                .setDisplayName(ChatColor.YELLOW + "Tank Helmet")
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        return items;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[2];

        items[0] = new ItemBuilder(Material.STONE_SWORD)
                .setDisplayName(ChatColor.YELLOW + "Tank Sword")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY /*+ Strings.repeat('\u2594', 16)*/, //TODO
                        ChatColor.YELLOW + "Aus verhärtetem Gestein, vom",
                        ChatColor.YELLOW + "Steinmonster persönlich abgebaut")
                .addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10, true)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS)
                .build();

        items[1] = new DashAbilityItem().getItem();//TODO

        return items;
    }
}
