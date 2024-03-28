package me.maddinoriginal.newkitpvp.kits.advancedkits;

import me.maddinoriginal.newkitpvp.abilityitems.items.WolfHuntAbilityItem;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

/**
 * Type= Hybrid Kit with a crossbow and a knife
 * Armor= Mostly green-ish leather armor with some brown tones
 *
 * Strong against= Archer, Tank
 * Weak against= Swordsman, Barbarian
 *
 * Active Ability= Shoot (slightly) homing arrows for X seconds
 * Supportive Passive= Hitting enemies with arrows won't give them no invincibility frames
 * Survivability Passive= Hitting an enemy with an arrow 3 times summons a Wolf with speed effect that hunts that enemy
 * Finisher Passive= Killing enemies gives back the amount of arrows shot since the last kill (max 7 arrows)
 */

public class Hunter extends Kit {

    @Override
    public String getName() {
        return "Hunter";
    }

    @Override
    public String getTag() {
        return "[HUNT]";
    }

    @Override
    public Material getMaterial() {
        return Material.BONE;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.ADVANCED;
    }

    @Override
    public String getDescription() {
        return "On a hunt with its crossbow. Can shoot homing arrows and summon wolfs to hunt their prey with them.";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //boots
        armor[0] = new ItemBuilder(Material.LEATHER_BOOTS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Boots")

                .setLeatherArmorColor(150, 150, 60, true)

                .setArmorTrim(TrimMaterial.EMERALD, TrimPattern.RIB)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //leggings
        armor[1] = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Leggings")

                .setLeatherArmorColor(0, 120, 20, true)

                .setArmorTrim(TrimMaterial.EMERALD, TrimPattern.TIDE)
                .hideArmorTrim()

                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3, false)

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //chestplate
        armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Chestplate")

                .setLeatherArmorColor(25, 180, 20, true)

                .setArmorTrim(TrimMaterial.EMERALD, TrimPattern.SNOUT)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //helmet
        armor[3] = new ItemBuilder(Material.LEATHER_HELMET)
                .setDisplayName(ChatColor.YELLOW + getName() + " Helmet")

                .setLeatherArmorColor(90, 120, 60, true)

                .setArmorTrim(TrimMaterial.EMERALD, TrimPattern.RIB)
                .hideArmorTrim()

                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false)

                .setUnbreakable(true,true )
                .hideAttributes()
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[9];

        items[0] = new ItemBuilder(Material.CROSSBOW)
                .setDisplayName(ChatColor.YELLOW + getName() + " Crossbow")

                .addEnchantment(Enchantment.MULTISHOT, 1, false)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new WolfHuntAbilityItem().getItem();

        items[2] = null;
        items[3] = null;

        items[4] = new ItemBuilder(Material.RABBIT_FOOT)
                .setDisplayName(ChatColor.YELLOW + "Lucky Charm")
                .addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3, true)
                .build();

        items[5] = null;
        items[6] = null;

        items[7] = new ItemBuilder(Material.ARROW)
                .setAmount(21)
                .setDisplayName(ChatColor.YELLOW + getName() + " Arrow")
                .build();

        items[8] = null;

        return items;
    }
}
