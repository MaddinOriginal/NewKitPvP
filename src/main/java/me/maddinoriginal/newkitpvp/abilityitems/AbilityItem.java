package me.maddinoriginal.newkitpvp.abilityitems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public abstract class AbilityItem {

    public abstract String getName();

    public abstract Material getMaterial();

    public abstract List<String> getLore();

    public abstract AbilityType getAbilityType();

    public abstract void handleLeftClick(Player player, ItemStack item, PlayerInteractEvent event);

    public abstract void handleRightClick(Player player, ItemStack item, PlayerInteractEvent event);

    public String getId() {
        return getClass().getSimpleName();
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(getMaterial(), 1);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        meta.setDisplayName(ChatColor.YELLOW + getName());
        ArrayList<String> lore = new ArrayList<>();
        for (String s : getLore()) {
            lore.add(ChatColor.GRAY + s);
        }
        meta.setLore(lore);
        container.set(AbilityItemManager.getInstance().getAbilityItemKey(), PersistentDataType.STRING, getId());
        item.setItemMeta(meta);
        return item;
    }
}
