package me.maddinoriginal.newkitpvp.kits.standardkits;

import com.google.common.base.Strings;
import me.maddinoriginal.newkitpvp.abilityitems.items.AxeThrowAbilityItem;
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
 * Type= Melee Kit with an Iron Axe
 * Armor= Full leather armor in brown-ish red tones
 *
 * Strong against= Swordsman, Hunter
 * Weak against= Archer, Tank
 *
 * Active Ability= Throws an axe at enemies
 * Supportive Passive= enemies hit by a thrown axe are affected by bleeding effect
 * Survivability Passive= Gains Regeneration for X seconds when HP fall below 3 hearts
 * Finisher Passive= Killing enemies grants Strengh for X seconds
 */

public class Barbarian extends Kit {

    @Override
    public String getName() {
        return "Barbarian";
    }

    @Override
    public String getTag() {
        return "[BARB]";
    }

    @Override
    public Material getMaterial() {
        return Material.SHIELD;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.STANDARD;
    }

    @Override
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[]{PlayStyle.FIGHTER, PlayStyle.DAMAGE};
    }

    @Override
    public String getDescription() {
        return "Equipped with an axe that it can throw and a shield to block incoming attacks.";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //boots
        armor[0] = new ItemBuilder(Material.LEATHER_BOOTS)
                .setDisplayName(ChatColor.YELLOW + "Barbarian Boots")

                .setLeatherArmorColor(150, 120, 110, true)

                .setArmorTrim(TrimMaterial.COPPER, TrimPattern.SILENCE)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //leggings
        armor[1] = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + "Barbarian Leggings")

                .setLeatherArmorColor(150, 120, 55, true)

                .setArmorTrim(TrimMaterial.COPPER, TrimPattern.EYE)
                .hideArmorTrim()

                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true)

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //chestplate
        armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + "Barbarian Chestplate")

                .setLeatherArmorColor(160, 35, 0, true)

                .setArmorTrim(TrimMaterial.COPPER, TrimPattern.DUNE)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //helmet
        armor[3] = new ItemBuilder(Material.LEATHER_HELMET)
                .setDisplayName(ChatColor.YELLOW + "Barbarian Helmet")

                .setLeatherArmorColor(210, 120, 0, true)

                .setArmorTrim(TrimMaterial.REDSTONE, TrimPattern.VEX)
                .hideArmorTrim()

                .setUnbreakable(true,true )
                .hideAttributes()
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[9];

        items[0] = new ItemBuilder(Material.IRON_AXE)
                .setDisplayName(ChatColor.YELLOW + "Barbarian Axe")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Smithed to smash heads",
                        ChatColor.GRAY + "Use carefully")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new ItemBuilder(Material.SHIELD)
                .setDisplayName(ChatColor.YELLOW + "Barbarian Axe")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Shield to block")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[2] = new AxeThrowAbilityItem().getItem();

        items[3] = null;
        items[4] = null;
        items[5] = null;
        items[6] = null;
        items[7] = null;
        items[8] = null;

        return items;
    }
}
