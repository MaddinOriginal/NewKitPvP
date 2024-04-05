package me.maddinoriginal.newkitpvp.kits.advancedkits;

import com.google.common.base.Strings;
import me.maddinoriginal.newkitpvp.abilityitems.items.AirStreamAbilityItem;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import me.maddinoriginal.newkitpvp.utils.PlayStyle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

/**
 * Type= Melee
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

public class Blaster extends Kit {

    @Override
    public String getName() {
        return "Blaster";
    }

    @Override
    public String getTag() {
        return "[BLST]";
    }

    @Override
    public Material getMaterial() {
        return Material.FEATHER;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.ADVANCED;
    }

    @Override
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[]{PlayStyle.KNOCKBACK, PlayStyle.MOBILITY};
    }

    @Override
    public String getDescription() {
        return "Knocks its enemies far away and channels air abilities to push them off the map, possibly into the void.";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //boots
        armor[0] = new ItemBuilder(Material.DIAMOND_BOOTS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Boots")

                .setArmorTrim(TrimMaterial.QUARTZ, TrimPattern.SENTRY)
                .hideArmorTrim()

                .addEnchantment(Enchantment.PROTECTION_FALL, 5, true)

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //leggings
        armor[1] = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Leggings")

                .setLeatherArmorColor(170, 170, 220, true)

                .setArmorTrim(TrimMaterial.QUARTZ, TrimPattern.TIDE)
                .hideArmorTrim()

                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3, false)

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //chestplate
        armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Chestplate")

                .setLeatherArmorColor(150, 170, 220, true)

                .setArmorTrim(TrimMaterial.QUARTZ, TrimPattern.SNOUT)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //helmet
        armor[3] = new ItemBuilder(Material.GLASS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Helmet")

                .setUnbreakable(true,true )
                .hideAttributes()
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[9];

        items[0] = new ItemBuilder(Material.WOODEN_SHOVEL)
                .setDisplayName(ChatColor.YELLOW + getName() + " Bat")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Haut einen um")

                .addEnchantment(Enchantment.KNOCKBACK, 1, false)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new ItemBuilder(Material.BOW)
                .setDisplayName(ChatColor.YELLOW + getName() + " Bow")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Haut einen weg")

                .addEnchantment(Enchantment.ARROW_KNOCKBACK, 1, false)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[2] = new AirStreamAbilityItem().getItem();
        items[3] = null;
        items[4] = null;
        items[5] = null;
        items[6] = null;
        items[7] = null;
        items[8] = new ItemBuilder(Material.ARROW)
                .setAmount(5)
                .setDisplayName(ChatColor.YELLOW + getName() + " Arrow")
                .build();

        return items;
    }
}
