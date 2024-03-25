package me.maddinoriginal.newkitpvp.kits.advancedkits;

import com.google.common.base.Strings;
import me.maddinoriginal.newkitpvp.abilityitems.items.SnowstormAbilityItem;
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
 * Type=
 * Armor=
 *
 * Strong against=
 * Weak against=
 *
 * Active Ability=
 * Supportive Passive= Snow spawns around you, that can be mined with a shovel to get snowballs
 * Survivability Passive=
 * Finisher Passive= deal extra damage to players that are under the freeze effect
 */

public class Yeti extends Kit {

    @Override
    public String getName() {
        return "Yeti";
    }

    @Override
    public String getTag() {
        return "[YETI]";
    }

    @Override
    public Material getMaterial() {
        return Material.SNOWBALL;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.ADVANCED;
    }

    @Override
    public String getDescription() {
        return "Missing description";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //boots
        armor[0] = new ItemBuilder(Material.IRON_BOOTS)
                .setDisplayName(ChatColor.YELLOW + "Yeti Boots")

                .setArmorTrim(TrimMaterial.QUARTZ, TrimPattern.SPIRE)
                .hideArmorTrim()

                .addEnchantment(Enchantment.PROTECTION_FIRE, 4, false)
                .addEnchantment(Enchantment.FROST_WALKER, 2, false)

                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //leggings
        armor[1] = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + "Yeti Leggings")

                .setLeatherArmorColor(255, 225, 255, true)

                .setArmorTrim(TrimMaterial.QUARTZ, TrimPattern.SNOUT)
                .hideArmorTrim()

                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //chestplate
        armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + "Yeti Chestplate")

                .setLeatherArmorColor(225, 255, 255, true)

                .setArmorTrim(TrimMaterial.QUARTZ, TrimPattern.SENTRY)
                .hideArmorTrim()

                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //helmet
        armor[3] = new ItemBuilder(Material.LEATHER_HELMET)
                .setDisplayName(ChatColor.YELLOW + "Yeti Helmet")

                .setLeatherArmorColor(255, 255, 225, true)

                .setArmorTrim(TrimMaterial.QUARTZ, TrimPattern.WILD)
                .hideArmorTrim()

                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[2];

        items[0] = new ItemBuilder(Material.IRON_SHOVEL)
                .setDisplayName(ChatColor.YELLOW + "Yeti Shovel")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.WHITE + "Belonged to Bigfoot")
                .addEnchantment(Enchantment.DIG_SPEED, 3, false)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new SnowstormAbilityItem().getItem();

        return items;
    }
}
