package me.maddinoriginal.newkitpvp.listeners;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.AbilityItem;
import me.maddinoriginal.newkitpvp.abilityitems.AbilityItemManager;
import me.maddinoriginal.newkitpvp.kits.KitType;
import me.maddinoriginal.newkitpvp.powerup.PowerUpManager;
import me.maddinoriginal.newkitpvp.powerup.PowerUpType;
import me.maddinoriginal.newkitpvp.data.KitPlayer;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.utils.LobbyManager;
import me.maddinoriginal.newkitpvp.data.PlayerState;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InteractListener implements Listener {

    NewKitPvP plugin = NewKitPvP.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        ItemStack item = e.getItem();
        Block block = e.getClickedBlock();
        KitPlayer kitPlayer = KitPlayerManager.getInstance().getKitPlayer(p);

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

        if (kitPlayer.getPlayerState().equals(PlayerState.LOBBY)) {
            e.setCancelled(true);

            //return if player does not hold an item to prevent NullPointerException
            if (e.getItem() == null)
                return;

            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            NamespacedKey key = LobbyManager.getInstance().getLobbyItemKey();
            if (container.has(key, PersistentDataType.STRING)) {
                LobbyManager.getInstance().performAction(p, container.get(key, PersistentDataType.STRING));
            }
        }
    }

    @EventHandler
    public void onArrowShoot(EntityShootBowEvent e) {
        if (!(e.getEntity() instanceof Player) || !(e.getProjectile() instanceof Arrow)) {
            return;
        }

        Player p = (Player) e.getEntity();
        KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(p);

        if (kp.getCurrentKit().equals(KitType.BOMBERMAN)) {
            Arrow arrow = (Arrow) e.getProjectile();
            arrow.setColor(Color.MAROON);
        }
    }

    @EventHandler
    public void onPlayerPickUpItem(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            e.setCancelled(true);
            return;
        }
        Player p = (Player) e.getEntity();
        Item item = e.getItem();
        if (item.getPersistentDataContainer().has(PowerUpManager.getInstance().getKey(), PersistentDataType.STRING)) {
            item.remove();
            PowerUpManager.getInstance().activatePowerup(p, PowerUpType.valueOf(item.getPersistentDataContainer().get(PowerUpManager.getInstance().getKey(), PersistentDataType.STRING)));
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onAbilityItemsInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        ItemStack heldItem = e.getItem();

        //return if player does not hold an item to prevent NullPointerException
        if (heldItem == null) return;

        if (isAbilityItem(heldItem)) {
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
                if (!p.hasCooldown(heldItem.getType())) {
                    AbilityItem abilityItem = AbilityItemManager.getInstance().abilityItems.get(getItemId(heldItem));
                    abilityItem.handleRightClick(p, heldItem, e);
                    p.setCooldown(heldItem.getType(), abilityItem.getAbilityType().getAbility().getCooldown());
                }
            }
        }
    }

    @EventHandler
    public void onAbilityItemsEntityInteract(PlayerInteractEntityEvent e) {
        //TODO
    }

    private boolean isAbilityItem(ItemStack item) {
        return (item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(AbilityItemManager.getInstance().getAbilityItemKey(), PersistentDataType.STRING));
    }

    private String getItemId(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().get(AbilityItemManager.getInstance().getAbilityItemKey(), PersistentDataType.STRING);
    }
}
