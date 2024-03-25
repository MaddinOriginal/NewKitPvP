package me.maddinoriginal.newkitpvp.listeners;

import me.maddinoriginal.newkitpvp.data.KitPlayer;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.kits.KitType;
import me.maddinoriginal.newkitpvp.utils.Helper;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.Random;

public class DamageListener implements Listener {

    private final Random RANDOM = new Random();

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();
        KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(p);

        //prevent explosion damage for bomber kit
        if (kp.getCurrentKit().equals(KitType.BOMBERMAN)) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
                    || e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
                e.setCancelled(true);
            }
        }

        //prevent fire damage for pyro kit
        else if (kp.getCurrentKit().equals(KitType.PYRO)) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FIRE)
                    || e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK)
                    || e.getCause().equals(EntityDamageEvent.DamageCause.HOT_FLOOR)
                    || e.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();
        //KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(p);
        Entity ent = e.getDamager();

        if (ent instanceof EvokerFangs && Objects.equals(((EvokerFangs) ent).getOwner(), p)) {
            e.setDamage(e.getDamage() * 0.5); //e.setCancelled(true);
        }

        //Prevent Damage from MagmaSlime spawned by own MagmaLauncher Ability
        if (ent instanceof MagmaCube && ent.getTicksLived() <= 10) {
            e.setCancelled(true);
        }
    }

    private int rangeLimitFromBehind = 36;

    @EventHandler
    public void onEntityDamageByPlayer(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof LivingEntity)) {
            return;
        }

        LivingEntity ent = (LivingEntity) e.getEntity();
        Player p = (Player) e.getDamager();
        KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(p);

        //assassin kit instant kill under 2.5 hearts && damage entity from behind 150%
        if (kp.getCurrentKit().equals(KitType.ASSASSIN)) {
            //instantkill under 2.5 hearts (5.0 health points)
            if (ent.getHealth() <= 5) {
                //e.setCancelled(true);
                ent.setHealth(0);
            }
            //more damage when attack from behind entity
            else {
                double yawPlayer = p.getLocation().getYaw();
                double yawEntity = ent.getLocation().getYaw();
                double distance = distanceBetweenTwoAngles(yawPlayer, yawEntity);

                if (distance <= rangeLimitFromBehind) {
                    e.setDamage(e.getDamage() * 1.5);
                }
            }
        }
    }

    private double distanceBetweenTwoAngles(double yaw1, double yaw2) {
        double phi = Math.abs(yaw1 - yaw2) % 360;
        return phi > 180 ? 360 - phi : phi;
    }

    private final int RESET_AFTER = 50;

    @EventHandler
    public void onEntityDamageByProjectile(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Projectile) || !(e.getEntity() instanceof LivingEntity)) {
            return;
        }

        LivingEntity ent = (LivingEntity) e.getEntity();
        Projectile proj = (Projectile) e.getDamager();

        if (ent instanceof Player) {
            Player p = (Player) ent;
            KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(p);

            //assassin ability to get resistance 1
            if (kp.getCurrentKit().equals(KitType.ASSASSIN)) {
                if (proj instanceof Arrow) {
                    p.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(150, 0));
                }
            }

            //swordsman ability no knockback from projectiles
            if (kp.getCurrentKit().equals(KitType.SWORDSMAN)) {
                e.setCancelled(true);
                p.damage(e.getDamage(), proj);
            }
        }

        if (proj.getShooter() instanceof Player) {
            Player shooter = (Player) proj.getShooter();
            KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(shooter);

            //hunter ability chance to try and place a bush under hit enemy
            if (kp.getCurrentKit().equals(KitType.HUNTER)) {

                //33% chance to do it
                if (RANDOM.nextDouble() < 0.33) {
                    Block b = ent.getLocation().getBlock();

                    //only if a berry bush can be placed here naturally
                    if (b.canPlace(Material.SWEET_BERRY_BUSH.createBlockData())) {

                        //only if it is not already a bush there
                        if (b.getType().equals(Material.SWEET_BERRY_BUSH)) {
                            return;
                        }

                        //resets block after given ticks
                        Helper.resetBlockAfter(b, RESET_AFTER);

                        //sets block to a berry bush
                        b.setType(Material.SWEET_BERRY_BUSH, true);
                        Ageable data = (Ageable) b.getBlockData();
                        data.setAge(1);
                        b.setBlockData(data);
                        b.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, b.getLocation().clone().add(0.5, 0.5, 0.5), 3, 0.2, 0.2, 0.2);
                    }
                }
            }
        }
    }
}
