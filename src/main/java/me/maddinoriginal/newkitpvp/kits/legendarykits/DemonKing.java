package me.maddinoriginal.newkitpvp.kits.legendarykits;

import me.maddinoriginal.newkitpvp.abilityitems.items.DemonCircleAbilityItem;
import me.maddinoriginal.newkitpvp.abilityitems.items.WolfHuntAbilityItem;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class DemonKing extends Kit {

    @Override
    public String getName() {
        return "Demon King";
    }

    @Override
    public String getTag() {
        return "[DMN]";
    }

    @Override
    public Material getMaterial() {
        return Material.GOAT_HORN;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.LEGENDARY;
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
        ItemStack[] items = new ItemStack[9];

        items[0] = new ItemBuilder(Material.NETHERITE_SWORD)
                .setDisplayName(ChatColor.YELLOW + getName() + " Greatsword")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new DemonCircleAbilityItem().getItem();

        items[2] = null;
        items[3] = null;
        items[4] = null;
        items[5] = null;
        items[6] = null;
        items[7] = null;
        items[8] = null;

        return items;
    }
}
