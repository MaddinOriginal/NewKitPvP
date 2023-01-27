package me.maddinoriginal.newkitpvp.listeners;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilities.items.AbilityItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class InteractListener implements Listener {

    NewKitPvP plugin = NewKitPvP.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        //return if player does not hold an item to prevent NullPointerException
        if (e.getItem() == null)
            return;

        //TODO check which item got clicked to open the corresponding menu
    }

    @EventHandler
    public void onAbilityItemsInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        ItemStack heldItem = e.getItem();
        Block block = e.getClickedBlock();

        //DRUCKPLATTEN BOOST
        if (action.equals(Action.PHYSICAL)) {
            e.setCancelled(true);
            Location loc = p.getLocation();
            if (block.getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
                loc.setPitch(-44);
                p.setVelocity(loc.getDirection().multiply(2.1));
            } else if (block.getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)) {
                loc.setY(loc.getY()+0.2);
                p.teleport(loc);
                loc.setPitch(-10);
                p.setVelocity(loc.getDirection().multiply(3));
            }
        }

        //return if player does not hold an item to prevent NullPointerException
        if (heldItem == null)
            return;

        if (isAbilityItem(heldItem) && (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK))) {
            AbilityItem abilityItem = NewKitPvP.abilityItems.get(getItemId(heldItem));
            abilityItem.handleLeftClick(p, heldItem, e);
        }

        if (isAbilityItem(heldItem) && (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))) {
            AbilityItem abilityItem = NewKitPvP.abilityItems.get(getItemId(heldItem));
            abilityItem.handleRightClick(p, heldItem, e);
        }
    }

    private boolean isAbilityItem(ItemStack item) {
        return (item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(plugin.getAbilityItemKey(), PersistentDataType.STRING));
    }

    private String getItemId(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().get(plugin.getAbilityItemKey(), PersistentDataType.STRING);
    }
}
