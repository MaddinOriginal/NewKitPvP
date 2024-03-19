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
                .setLeatherArmorColor(178, 124, 0)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                .build();

        //Create leggings and add them to the returning (armor contents) ItemStack
        armor[1] = new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Leggings")
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                .build();

        //Create chest plate and add them to the returning (armor contents) ItemStack
        armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Chestplate")
                .setLeatherArmorColor(69, 75, 223)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                .build();

        //Create helmet and add them to the returning (armor contents) ItemStack
        armor[3] = new ItemBuilder(Material.LEATHER_HELMET)
                .setDisplayName(ChatColor.YELLOW + getName() + " Helmet")
                .setLeatherArmorColor(44, 76, 145)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[9];

        items[0] = new ItemBuilder(Material.WOODEN_SWORD)
                .setDisplayName(getName() + " Sword")
                .setLore(ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.YELLOW + "Made from wood that cannot be burned")
                .addEnchantment(Enchantment.DAMAGE_ALL, 1, true)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
                .build();

        items[1] = new ItemBuilder(Material.BOW)
                .setDisplayName(ChatColor.YELLOW + getName() + " Bow")
                .setLore(ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.YELLOW + "Made from wood that cannot be burned")
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
                .build();

        items[2] = new MagmaLauncherAbilityItem().getItem();

        items[7] = new ItemBuilder(Material.ARROW)
                .setAmount(25)
                .setDisplayName(ChatColor.YELLOW + getName() + " Arrow")
                .build();

        return items;
    }
}
