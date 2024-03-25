package me.maddinoriginal.newkitpvp.kits.advancedkits;

import com.google.common.base.Strings;
import me.maddinoriginal.newkitpvp.abilityitems.items.MagmaLauncherAbilityItem;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Type= Hybrid
 * Armor= Red/brown colored leather armor
 *
 * Strong against=
 * Weak against=
 *
 * Active Ability=
 * Supportive Passive=
 * Survivability Passive=
 * Finisher Passive=
 */

public class Pyro extends Kit {

    @Override
    public String getName() {
        return "Pyromane";
    }

    @Override
    public String getTag() {
        return "[PYRO]";
    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_ROD;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.ADVANCED;
    }

    @Override
    public String getDescription() {
        return "Harness the power of the nether with this kit. It can launch magma cubes and leaves a trail of fire behind when sprinting. Also its immune to any burning damage";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //Create boots and add them to the returning (armor contents) ItemStack
        armor[0] = new ItemBuilder(Material.LEATHER_BOOTS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Boots")

                .setLeatherArmorColor(178, 124, 0, true)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //Create leggings and add them to the returning (armor contents) ItemStack
        armor[1] = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Leggings")

                .setLeatherArmorColor(222, 111, 0, true)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //Create chest plate and add them to the returning (armor contents) ItemStack
        armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Chestplate")

                .setLeatherArmorColor(255, 155, 0, true)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //Create helmet and add them to the returning (armor contents) ItemStack
        armor[3] = new ItemBuilder(Material.LEATHER_HELMET)
                .setDisplayName(ChatColor.YELLOW + getName() + " Helmet")

                .setLeatherArmorColor(255, 111, 0, true)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[9];

        items[0] = new ItemBuilder(Material.BLAZE_ROD)
                .setDisplayName(ChatColor.YELLOW + getName() + " Rod")
                .setLore(ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Hot af")

                .addEnchantment(Enchantment.DAMAGE_ALL, 4, false)
                .addEnchantment(Enchantment.FIRE_ASPECT, 1, false)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
                .build();

        items[1] = new ItemBuilder(Material.BOW)
                .setDisplayName(ChatColor.YELLOW + getName() + " Bow")
                .setLore(ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Made from wood that cannot be burned")

                .addEnchantment(Enchantment.ARROW_FIRE, 1, false)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
                .build();

        items[2] = new MagmaLauncherAbilityItem().getItem();

        items[7] = new ItemBuilder(Material.ARROW)
                .setAmount(7)
                .setDisplayName(ChatColor.YELLOW + getName() + " Arrow")
                .build();

        return items;
    }
}
