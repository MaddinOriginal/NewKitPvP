package me.maddinoriginal.newkitpvp.kits.standardkits;

import com.google.common.base.Strings;
import me.maddinoriginal.newkitpvp.abilityitems.items.DashAbilityItem;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import me.maddinoriginal.newkitpvp.utils.PlayStyle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Type= Melee Kit with an iron sword
 * Armor= Mostly iron armor, except for gold helmet (and chainmail leggings)
 *
 * Strong against= Archer, Hunter
 * Weak against= Tank, Barbarian
 *
 * Active Ability= Dash straight in front
 * Supportive Passive= No Knockback from projectiles
 * Survivability Passive= Getting hit by arrows has a X% chance to grant regeneration for 2 seconds
 * Finisher Passive= Killing enemies resets the active ability cooldown instantly
 */

public class Swordsman extends Kit {

    @Override
    public String getName() {
        return "Swordsman";
    }

    @Override
    public String getTag() {
        return "[SWD]";
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_SWORD;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.STANDARD;
    }

    @Override
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[]{PlayStyle.FIGHTER, PlayStyle.MOBILITY};
    }

    @Override
    public String getDescription() {
        return "Has the ability to quickly dash forward. It is also resistent to knockback from most projectiles including arrows.";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //boots
        armor[0] = new ItemBuilder(Material.LEATHER_BOOTS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Boots")

                .setLeatherArmorColor(140, 69, 23)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //leggings
        armor[1] = new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Leggings")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //chestplate
        armor[2] = new ItemBuilder(Material.IRON_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Chestplate")

                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3, true)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //helmet
        armor[3] = new ItemBuilder(Material.CHAINMAIL_HELMET)
                .setDisplayName(ChatColor.YELLOW + getName() + " Helmet")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[2];

        items[0] = new ItemBuilder(Material.IRON_SWORD)
                .setDisplayName(ChatColor.YELLOW + "Swordsman Sword")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Hergestellt aus dem besten Stahl",
                        ChatColor.GRAY + "des ganzen Landes")

                .addEnchantment(Enchantment.SWEEPING_EDGE, 3, false)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new DashAbilityItem().getItem();

        return items;
    }
}
