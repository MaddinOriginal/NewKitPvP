package me.maddinoriginal.newkitpvp.kits.standardkits;

import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Melee Kit with an iron sword
 * mostly iron armor, except for gold helmet (and chainmail leggings)
 * Ability: Dash straight in front
 * Passive: No Knockback from projectiles
 */

public class Swordsman extends Kit {

    @Override
    public String getName() {
        return "Swordsman";
    }

    @Override
    public String getTag() {
        return "[SWD]";
    }

    @Override
    public Material getIconMaterial() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //boots
        armor[0] = new ItemBuilder(Material.IRON_BOOTS)
                .setDisplayName(ChatColor.YELLOW + "Swordsman Boots")
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //leggings
        armor[1] = new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + "Swordsman Leggings")
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //chestplate
        armor[2] = new ItemBuilder(Material.IRON_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + "Swordsman Chestplate")
                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3, true)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //helmet
        armor[3] = new ItemBuilder(Material.GOLDEN_HELMET)
                .setDisplayName(ChatColor.YELLOW + "Swordsman Helmet")
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[2];

        items[0] = new ItemBuilder(Material.IRON_SWORD)
                .setDisplayName(ChatColor.YELLOW + "Swordsman Sword")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY /*+ Strings.repeat('\u2594', 16)*/, //TODO
                        ChatColor.WHITE + "Hergestellt aus dem besten Stahl",
                        ChatColor.WHITE + "des ganzen Landes")
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = /*new AbilityItem(Material.GLOWSTONE_DUST, AbilityType.PLANT_BUSH);*/
                new ItemBuilder(Material.GLOWSTONE_DUST)
                .setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Dash Ability")
                .setLore(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Click to use")
                .addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10, false)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .build();

        return items;
    }
}
