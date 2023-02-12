package me.maddinoriginal.newkitpvp.utils;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;

    /**
     * Creates a new ItemBuilder
     * @param mat The material used to create the itemstack
     */
    public ItemBuilder(Material mat) {
        item = new ItemStack(mat, 1);
        meta = item.getItemMeta();
    }

    /**
     * Sets the Material of the item
     * @param mat The material that the item is going to be set to
     */
    public ItemBuilder setMaterial(Material mat) {
        item.setType(mat);
        return this;
    }

    /**
     * Sets the amount of the item
     * @param amount The item amount of the itemstack
     */
    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    /**
     * Sets the damage of the item to its durability
     * @param dmg the damage that the items durability should have
     * @return
     */
    public ItemBuilder setDamage(short dmg) {
        if (meta instanceof Damageable) {
            ((Damageable) meta).setDamage(dmg);
        } else {
            System.out.println(NewKitPvP.getInstance().getPrefix() + " item in ItemBuilder is not damageable!");
        }
        return this;
    }

    /**
     * Sets the name of the itemstack item
     * @param name the name that the item should have
     */
    public ItemBuilder setDisplayName(String name) {
        meta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the lore in the itemmeta of the itemstack
     * @param lore the lore that the item should have
     */
    public ItemBuilder setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }
    public ItemBuilder setLore(List<String> lore) {
        meta.setLore(lore);
        return this;
    }

    /**
     * Adds an enchantment to the itemmeta of the itemstack
     * @param ench the type of enchantment
     * @param level the level of the enchantment
     * @param ignoreLevelRestriction If true, ignores minecrafts vanilla enchantment level restrictions
     */
    public ItemBuilder addEnchantment(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        meta.addEnchant(ench, level, ignoreLevelRestriction);
        return this;
    }
    public ItemBuilder addEnchantment(Enchantment ench, int level, boolean ignoreLevelRestriction, boolean hide) {
        meta.addEnchant(ench, level, ignoreLevelRestriction);
        if (hide) { meta.addItemFlags(ItemFlag.HIDE_ENCHANTS); }
        return this;
    }

    /**
     * Sets the color of a LeatherArmor (Only if item is a leather armor item)
     * @param red the part of red
     * @param green the part of green
     * @param blue the part of blue
     */
    public ItemBuilder setLeatherArmorColor(int red, int green, int blue) {
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) meta).setColor(Color.fromRGB(red, green, blue));
        } else {
            System.out.println(NewKitPvP.getInstance().getPrefix() + " item in ItemBuilder is not leather armor!");
        }
        return this;
    }

    /**
     * Makes the itemstack unbreakable
     * If true, the item does not lose durability ever
     */
    public ItemBuilder setUnbreakable() {
        meta.setUnbreakable(true);
        return this;
    }
    public ItemBuilder setUnbreakable(boolean hide) {
        meta.setUnbreakable(true);
        if (hide) { meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE); }
        return this;
    }

    /**
     * Adds ItemFlags, for example ItemFlag.HIDE_ENCHANTS to hide enchants
     * @param flags all ItemFlags that will be added to the itemmeta of the itemstack
     */
    public ItemBuilder addItemFlag(ItemFlag... flags) {
        for (ItemFlag flag : flags) {
            meta.addItemFlags(flag);
        }
        return this;
    }

    /**
     * Adds the ItemFlag that hides all attributes
     */
    public ItemBuilder hideAttributes() {
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        return this;
    }

    /**
     * Eventually creates the final itemstack and returns it
     * Use this method at the end
     * @return the finished itemstack with all the previous modifications
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
