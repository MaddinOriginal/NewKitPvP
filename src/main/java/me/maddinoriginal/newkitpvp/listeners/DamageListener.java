package me.maddinoriginal.newkitpvp.listeners;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.data.KitPlayer;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.kits.KitType;
import me.maddinoriginal.newkitpvp.kits.advancedkits.Hunter;
import me.maddinoriginal.newkitpvp.utils.Helper;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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
    public void onZombieDamage(EntityDamageEvent e) {
        Entity ent = e.getEntity();

        if (ent instanceof Zombie) {
            Bukkit.broadcastMessage("Zombie: " + ((Zombie) ent).getHealth());

            new BukkitRunnable() {

                @Override
                public void run() {
                    Bukkit.broadcastMessage("" + ((Zombie) ent).getHealth());
                }
            }.runTaskLater(NewKitPvP.getInstance(), 2);
        }
    }

    private final double EVOKER_MULTIPLIER = 0.5; //multiplier for the damage taken by evoker fangs to its owner

    private boolean grenadeOnCooldown = false;
    private int GRENADE_COOLDOWN_TICKS = 200; //cooldown in ticks for the grenade ability of bomber kit
    private final double KNOCKBACK_MULTIPLIER = 3.3; //the multiplier for the players knockback added on grenade drop
    private final int GRENADE_FUSE_TICKS = 13; //grenade fuse ticks before it explodes
    private final float GRENADE_POWER = 0.6f; //explosion radius of grenades explosion

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();
        KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(p);
        Entity ent = e.getDamager();

        //Less damage from evoker fangs to the player who summoned them (owner)
        if (ent instanceof EvokerFangs && Objects.equals(((EvokerFangs) ent).getOwner(), p)) {
            e.setDamage(e.getDamage() * EVOKER_MULTIPLIER); //e.setCancelled(true);
        }

        //Prevent Damage from MagmaSlime spawned by own MagmaLauncher Ability
        else if (ent instanceof MagmaCube && ent.getTicksLived() <= 10) {
            e.setCancelled(true);
        }

        //Bomber Kit drop live TNT when attacked
        if (kp.getCurrentKit().equals(KitType.BOMBERMAN)) {
            if (ent instanceof LivingEntity) {
                if (p.getHealth() >= 10.0 || grenadeOnCooldown) {
                    return;
                }

                grenadeOnCooldown = true;

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        grenadeOnCooldown = false;
                    }
                }.runTaskLater(NewKitPvP.getInstance(), GRENADE_COOLDOWN_TICKS);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (p.isDead()) {
                            cancel();
                            return;
                        }
                        p.setVelocity(p.getVelocity().multiply(KNOCKBACK_MULTIPLIER).add(new Vector(0, 0.4, 0)));
                        p.getWorld().spawn(p.getLocation(), TNTPrimed.class, ent -> {
                            ent.setSource(p);
                            ent.setFuseTicks(GRENADE_FUSE_TICKS);
                            ent.setMetadata("GrenadePower", new FixedMetadataValue(NewKitPvP.getInstance(), GRENADE_POWER));
                            ent.setMetadata("GrenadeOwner", new FixedMetadataValue(NewKitPvP.getInstance(), p.getUniqueId()));
                            //ent.setGravity(false);
                        });
                    }
                }.runTaskLater(NewKitPvP.getInstance(), 1);
            }
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
            else if (kp.getCurrentKit().equals(KitType.SWORDSMAN)) {
                e.setCancelled(true);
                p.damage(e.getDamage(), proj);
            }
        }

        if (proj.getShooter() instanceof Player) {
            Player shooter = (Player) proj.getShooter();
            KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(shooter);

            //Arbalist ability chance to try and place a bush under hit enemy
            if (kp.getCurrentKit().equals(KitType.ARBALIST)) {

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

            else if (kp.getCurrentKit().equals(KitType.HUNTER)) {
                ((Hunter) KitType.HUNTER.getKit()).summonWolf(shooter, ent);
            }
        }
    }
}
