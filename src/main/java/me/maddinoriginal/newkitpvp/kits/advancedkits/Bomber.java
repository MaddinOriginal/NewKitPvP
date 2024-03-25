package me.maddinoriginal.newkitpvp.kits.advancedkits;

import com.google.common.base.Strings;
import me.maddinoriginal.newkitpvp.abilityitems.items.AirStreamAbilityItem;
import me.maddinoriginal.newkitpvp.abilityitems.items.DetonationAbilityItem;
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
 * Supportive Passive=
 * Survivability Passive=
 * Finisher Passive=
 */

public class Bomber extends Kit {

    @Override
    public String getName() {
        return "Boomber";
    }

    @Override
    public String getTag() {
        return "[BOOM]";
    }

    @Override
    public Material getMaterial() {
        return Material.TNT;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.ADVANCED;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //boots
        armor[0] = new ItemBuilder(Material.LEATHER_BOOTS)
                .setDisplayName(ChatColor.YELLOW + "Bomber Boots")

                .setLeatherArmorColor(255, 0, 0, true)

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //leggings
        armor[1] = new ItemBuilder(Material.IRON_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + "Bomber Leggings")

                .setArmorTrim(TrimMaterial.NETHERITE, TrimPattern.DUNE)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //chestplate
        armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + "Bomber Chestplate")

                .setLeatherArmorColor(255, 255, 255, true)

                .setArmorTrim(TrimMaterial.NETHERITE, TrimPattern.SNOUT)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //helmet
        armor[3] = new ItemBuilder(Material.LEATHER_HELMET)
                .setDisplayName(ChatColor.YELLOW + "Bomber Helmet")

                .setLeatherArmorColor(255, 0, 0, true)

                .setUnbreakable(true,true )
                .hideAttributes()
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[2];

        items[0] = new ItemBuilder(Material.WOODEN_AXE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Axe")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Might explode too")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new DetonationAbilityItem().getItem();

        return items;
    }
}
