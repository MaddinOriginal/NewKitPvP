package me.maddinoriginal.newkitpvp.kits.standardkits;

import me.maddinoriginal.newkitpvp.abilityitems.items.MinigunAbilityItem;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Type= Ranged Kit with a strong bow and a weak wooden sword
 * Armor= Mostly blue-ish leather armor, except for chainmail leggings
 *
 * Strong against= Tank, Barbarian
 * Weak against= Swordsman, Hunter
 *
 * Active Ability= Minigun to spam arrows with bow
 * Supportive Passive= Gets arrows after X seconds
 * Survivability Passive= Grants speed I Resistance I when out of arrows
 * Finisher Passive= Headshots on enemies without a helmet deal extra damage
 */

public class Archer extends Kit {

    @Override
    public String getName() {
        return "Archer";
    }

    @Override
    public String getTag() {
        return "[ARCH]";
    }

    @Override
    public Material getMaterial() {
        return Material.BOW;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.STANDARD;
    }

    @Override
    public String getDescription() {
        return "The classic ranged combat kit with a bow and arrows. Activating its ability turns the bow into a mini gun that shoots arrows rapidly.";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //Create boots and add them to the returning (armor contents) ItemStack
        armor[0] = new ItemBuilder(Material.LEATHER_BOOTS)
                .setDisplayName(ChatColor.YELLOW + "Archer Boots")
                .setLeatherArmorColor(178, 124, 0)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //Create leggings and add them to the returning (armor contents) ItemStack
        armor[1] = new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + "Archer Leggings")
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //Create chest plate and add them to the returning (armor contents) ItemStack
        armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + "Archer Cloak")
                .setLeatherArmorColor(69, 75, 223)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //Create helmet and add them to the returning (armor contents) ItemStack
        armor[3] = new ItemBuilder(Material.LEATHER_HELMET)
                .setDisplayName(ChatColor.YELLOW + "Archer Hood")
                .setLeatherArmorColor(44, 76, 145)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[9];

        items[0] = new ItemBuilder(Material.WOODEN_SWORD)
                .setDisplayName(ChatColor.RESET + "" + ChatColor.YELLOW + "Archer Sword")
                //.addEnchantment(Enchantment.DAMAGE_ALL, 1, true)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new ItemBuilder(Material.BOW)
                .setDisplayName(ChatColor.RESET + "" + ChatColor.YELLOW + "Archer Bow")
                .addEnchantment(Enchantment.DAMAGE_ALL, 1, true)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[2] = new MinigunAbilityItem().getItem();

        items[3] = null;
        items[4] = null;
        items[5] = null;
        items[6] = null;

        items[7] = new ItemBuilder(Material.ARROW)
                .setAmount(25)
                .setDisplayName(ChatColor.YELLOW + "Archer Arrow")
                .build();

        items[8] = null;

        return items;
    }
}
