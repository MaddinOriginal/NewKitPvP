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
        return null;
    }

    @Override
    public ItemStack[] getArmorContents() {
        return new ItemStack[0];
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[2];

        items[0] = new ItemBuilder(Material.IRON_SWORD)
                .setDisplayName(ChatColor.YELLOW + "Swordsman Sword")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.WHITE + "Belonged to Bigfoot")
                .addEnchantment(Enchantment.DIG_SPEED, 1, false)
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new SnowstormAbilityItem().getItem();

        return items;
    }
}
