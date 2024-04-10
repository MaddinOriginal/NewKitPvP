package me.maddinoriginal.newkitpvp.kits.advancedkits;

import com.google.common.base.Strings;
import me.maddinoriginal.newkitpvp.abilityitems.items.DetonationAbilityItem;
import me.maddinoriginal.newkitpvp.kits.ArrowKit;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import me.maddinoriginal.newkitpvp.utils.PlayStyle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
 * Active Ability= Detonate after X seconds
 * Supportive Passive= Arrows shot will explode on hit
 * Survivability Passive= No damage from explosions
 * Finisher Passive=killing enemies gives back X arrows
 */

public class Bomber extends Kit implements ArrowKit {

    @Override
    public String getName() {
        return "Boomer";
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
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[]{PlayStyle.DAMAGE, PlayStyle.CONTROL};
    }

    @Override
    public String getDescription() {
        return "Stacked with explosions and the ability to detonate itself. Can not take damage from explosions themselves.";
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
        ItemStack[] items = new ItemStack[9];

        items[0] = new ItemBuilder(Material.WOODEN_AXE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Axe")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Might explode too")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new ItemBuilder(Material.CROSSBOW)
                .setDisplayName(ChatColor.YELLOW + "Bomb-Arrow Launcher")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Shoots arrows that explode")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[2] = new DetonationAbilityItem().getItem();

        items[3] = null;
        items[4] = null;
        items[5] = null;
        items[6] = null;
        items[7] = null;
        items[8] = getArrowItem();

        return items;
    }

    @Override
    public ItemStack getArrowItem() {
        return new ItemBuilder(Material.ARROW)
                .setAmount(5)
                .setDisplayName(ChatColor.YELLOW + getName() + " Arrow")
                .build();
    }
}
