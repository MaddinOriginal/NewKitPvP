package me.maddinoriginal.newkitpvp.kits.advancedkits;

import com.google.common.base.Strings;
import me.maddinoriginal.newkitpvp.abilityitems.items.PlantBushAbilityItem;
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
 * Type= Ranged Kit with a crossbow
 * Armor=
 *
 * Strong against=
 * Weak against=
 *
 * Active Ability= Plants a bush that slows down enemies and hurts half a heart when they try to move
 * Supportive Passive= Shooting enemies in bushes regains arrows TODO
 * Survivability Passive= Shooting enemies regains health TODO
 * Finisher Passive= Shooting enemies has a 33% chance to spawn a bush under them (if possible)
 */

public class Ranger extends Kit {

    @Override
    public String getName() {
        return "Ranger";
    }

    @Override
    public String getTag() {
        return "[RNGR]";
    }

    @Override
    public Material getMaterial() {
        return Material.SWEET_BERRY_BUSH;
    }

    @Override
    public KitCategory getCategory() {
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

                .addEnchantment(Enchantment.QUICK_CHARGE, 5, true)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new PlantBushAbilityItem().getItem();

        items[7] = new ItemBuilder(Material.ARROW)
                .setDisplayName(ChatColor.YELLOW + getName() + " Arrow")
                .setAmount(12)
                .build();

        return items;
    }
}
