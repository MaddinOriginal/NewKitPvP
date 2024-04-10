package me.maddinoriginal.newkitpvp.kits.standardkits;

import com.google.common.base.Strings;
import me.maddinoriginal.newkitpvp.abilityitems.items.HomingArrowsAbilityItem;
import me.maddinoriginal.newkitpvp.abilityitems.items.PlantBushAbilityItem;
import me.maddinoriginal.newkitpvp.kits.ArrowKit;
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
 * Type= Ranged Kit with a crossbow
 * Armor=
 *
 * Strong against= Archer, Tank
 * Weak against= Swordsman, Barbarian
 *
 * Active Ability=
 * Supportive Passive=
 * Survivability Passive=
 * Finisher Passive=
 */

public class Marksman extends Kit implements ArrowKit {

    @Override
    public String getName() {
        return "Marksman";
    }

    @Override
    public String getTag() {
        return "[MARK]";
    }

    @Override
    public Material getMaterial() {
        return Material.CROSSBOW;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.STANDARD;
    }

    @Override
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[]{PlayStyle.RANGED, PlayStyle.DAMAGE};
    }

    @Override
    public String getDescription() {
        return "The Marksman uses their crossbow to shoot arrows that pierce through any armor and shields.";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //boots
        armor[0] = new ItemBuilder(Material.LEATHER_BOOTS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Boots")

                .setLeatherArmorColor(60, 60, 190, true)

                .setArmorTrim(TrimMaterial.EMERALD, TrimPattern.RIB)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //leggings
        armor[1] = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Leggings")

                .setLeatherArmorColor(60, 0, 233, true)

                .setArmorTrim(TrimMaterial.EMERALD, TrimPattern.TIDE)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //chestplate
        armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Chestplate")

                .setLeatherArmorColor(0, 60, 215, true)

                .setArmorTrim(TrimMaterial.EMERALD, TrimPattern.SNOUT)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //helmet
        armor[3] = new ItemBuilder(Material.LEATHER_HELMET)
                .setDisplayName(ChatColor.YELLOW + getName() + " Helmet")

                .setLeatherArmorColor(35, 35, 166, true)

                .setArmorTrim(TrimMaterial.EMERALD, TrimPattern.RIB)
                .hideArmorTrim()

                .setUnbreakable(true,true )
                .hideAttributes()
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[8];

        items[0] = new ItemBuilder(Material.CROSSBOW)
                .setDisplayName(ChatColor.YELLOW + getName() + " Crossbow")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Shoots arrows lol")

                .addEnchantment(Enchantment.QUICK_CHARGE, 1, false)
                .addEnchantment(Enchantment.PIERCING, 5, true)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new HomingArrowsAbilityItem().getItem();

        items[7] = getArrowItem();

        return items;
    }

    @Override
    public ItemStack getArrowItem() {
        return new ItemBuilder(Material.ARROW)
                .setDisplayName(ChatColor.YELLOW + getName() + " Arrow")
                .setAmount(22)
                .build();
    }
}
