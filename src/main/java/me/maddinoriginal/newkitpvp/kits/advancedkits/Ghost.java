package me.maddinoriginal.newkitpvp.kits.advancedkits;

import com.google.common.base.Strings;
import me.maddinoriginal.newkitpvp.abilityitems.items.ScareOffAbilityItem;
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

public class Ghost extends Kit {

    @Override
    public String getName() {
        return "Ghost";
    }

    @Override
    public String getTag() {
        return "[GHST]";
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_AXE;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.ADVANCED;
    }

    @Override
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[]{PlayStyle.CONTROL, PlayStyle.FIGHTER};
    }

    @Override
    public String getDescription() {
        return "Can turn invisible and scare their enemies away.";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //boots
        armor[0] = null;/*new ItemBuilder(Material.CHAINMAIL_BOOTS)
                .setDisplayName(ChatColor.YELLOW + "Ghost Boots")

                .setArmorTrim(TrimMaterial.QUARTZ, TrimPattern.SPIRE)
                .hideArmorTrim()

                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();*/

        //leggings
        armor[1] = new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + "Ghost Leggings")

                .setArmorTrim(TrimMaterial.REDSTONE, TrimPattern.HOST)
                .hideArmorTrim()

                .addEnchantment(Enchantment.SWIFT_SNEAK, 5, true)

                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //chestplate
        armor[2] = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + "Ghost Chestplate")

                .setArmorTrim(TrimMaterial.REDSTONE, TrimPattern.SILENCE)
                .hideArmorTrim()

                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        //helmet
        armor[3] = new ItemBuilder(Material.CHAINMAIL_HELMET)
                .setDisplayName(ChatColor.YELLOW + "Ghost Helmet")

                .setArmorTrim(TrimMaterial.REDSTONE, TrimPattern.SHAPER)
                .hideArmorTrim()

                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[2];

        items[0] = new ItemBuilder(Material.IRON_AXE) //TODO
                .setDisplayName(ChatColor.YELLOW + "Ghost Axe")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.WHITE + "Spooky...")
                .setUnbreakable(true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new ScareOffAbilityItem().getItem();

        return items;
    }
}
