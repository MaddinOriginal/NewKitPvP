package me.maddinoriginal.newkitpvp.listeners;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.data.KitPlayer;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.kits.KitType;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.UUID;

public class DeathRespawnListener implements Listener {

    private Random random = new Random();
    private int lifetimeTicks = 75;
    private int addedLifetimeTicks = 15;
    private int pickupDelay = lifetimeTicks + addedLifetimeTicks;

    @EventHandler
    public void onDeathRespawn(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player damaged = (Player) e.getEntity();

            if (damaged.getHealth() - e.getFinalDamage() <= 0) {

                if (damaged.getInventory().contains(Material.TOTEM_OF_UNDYING)) {
                    e.setCancelled(true);
                    damaged.playEffect(EntityEffect.TOTEM_RESURRECT);
                    damaged.setHealth(20.0);
                    damaged.getInventory().remove(Material.TOTEM_OF_UNDYING); //TODO removes all totems of undying if there is more than one
                    return;
                }

                //e.setCancelled(true);
                //createDeathEffect(damaged);

                //damaged.teleport(new Location(damaged.getWorld(), 0.5, -14, 0.5)); //TODO
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(p);

        createDeathEffect(p);

        if (e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            KitPlayer killer_kp = KitPlayerManager.getInstance().getKitPlayer(killer);

            if (killer_kp.getKitType().equals(KitType.BOMBERMAN)) {
                ItemStack item = killer.getInventory().getItem(killer.getInventory().first(Material.ARROW));
                if (item != null) {
                    item.setAmount(item.getAmount() + 5);
                } else {
                    killer.getInventory().addItem(new ItemBuilder(Material.ARROW)
                            .setAmount(5)
                            .setDisplayName(ChatColor.YELLOW + KitType.BOMBERMAN.getKit().getName() + " Arrow")
                            .build());
                }
            }
        }
    }

    private void createDeathEffect(Player killed) {
        Location loc = killed.getLocation();
        loc.getWorld().playSound(loc, Sound.ENTITY_VILLAGER_DEATH, 0.5f, 0.5f);

        //drop a skull at the location the player died
        Item skull = killed.getWorld().dropItemNaturally(loc, getPlayerSkull(killed));
        skull.setPickupDelay(pickupDelay);

        //remove the skull after lifetimeTicks amount of time
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!skull.isDead()) {
                    skull.remove();
                }
            }
        }.runTaskLater(NewKitPvP.getInstance(), lifetimeTicks + random.nextInt(addedLifetimeTicks));

        //drop a lot of redstone dust, bones and beef at the location that the player died at
        for (int i = 0; i < 8 + random.nextInt(4); i++) {
            double chance = random.nextDouble();
            ItemStack item = new ItemStack(Material.AIR);
            if (chance < 0.66) {
                item.setType(Material.REDSTONE);
            } else if (chance < 0.85) {
                item.setType(Material.BONE);
            } else {
                item.setType(Material.BEEF);
            }

            //set a unique name so the items do not stack
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("" + UUID.randomUUID());
            item.setItemMeta(meta);
            Item drop = killed.getWorld().dropItemNaturally(loc, item);
            drop.setPickupDelay(pickupDelay);

            //add a sound
            drop.getWorld().playSound(loc, Sound.ENTITY_GENERIC_DEATH, 0.1f, 0.1f);

            //remove the dropped item after lifetimeTicks amount of time
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!drop.isDead()) {
                        drop.remove();
                    }
                }
            }.runTaskLater(NewKitPvP.getInstance(), lifetimeTicks + random.nextInt(addedLifetimeTicks));
        }

        //spawn lava particles
        killed.getWorld().spawnParticle(Particle.FALLING_LAVA, loc.getX(),loc.getY() + killed.getHeight()/2, loc.getZ(),
                100, killed.getWidth() / 2, killed.getHeight() / 4, killed.getWidth() / 2,
                5, null, true);
    }

    private ItemStack getPlayerSkull(Player player) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(player);
        skullMeta.setDisplayName("" + UUID.randomUUID());
        skull.setItemMeta(skullMeta);

        return skull;
    }
}
