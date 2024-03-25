package me.maddinoriginal.newkitpvp.kits.standardkits;

import com.google.common.base.Strings;
import me.maddinoriginal.newkitpvp.abilityitems.items.DashAbilityItem;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

/**
 * Type= Melee Kit with a stone sword
 * Armor= Mostly iron armor, except for diamond chestplate
 *
 * Strong against= Swordsman, Barbarian
 * Weak against= Archer, Hunter
 *
 * Active Ability= TODO
 * Supportive Passive= TODO
 * Survivability Passive=
 * Finisher Passive=
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

                .setArmorTrim(TrimMaterial.DIAMOND, TrimPattern.COAST)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //leggings
        items[1] = new ItemBuilder(Material.IRON_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + "Tank Leggings")

                .setArmorTrim(TrimMaterial.DIAMOND, TrimPattern.TIDE)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //chestplate
        items[2] = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + "Tank Chestplate")

                .setArmorTrim(TrimMaterial.IRON, TrimPattern.SHAPER)
                .hideArmorTrim()

                .addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4, true)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //helmet
        items[3] = new ItemBuilder(Material.IRON_HELMET)
                .setDisplayName(ChatColor.YELLOW + "Tank Helmet")

                .setArmorTrim(TrimMaterial.DIAMOND, TrimPattern.TIDE)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        return items;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[2];

        items[0] = new ItemBuilder(Material.STONE_SWORD)
                .setDisplayName(ChatColor.YELLOW + "Tank Sword")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Aus verhärtetem Gestein, vom",
                        ChatColor.GRAY + "Steinmonster persönlich abgebaut")

                //.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10, true)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //items[1] = new DashAbilityItem().getItem(); //TODO

        return items;
    }
}
