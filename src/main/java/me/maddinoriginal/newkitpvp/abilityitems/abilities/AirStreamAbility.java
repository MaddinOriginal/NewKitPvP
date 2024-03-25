package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;

public class AirStreamAbility extends Ability {

    @Override
    public boolean useAbility(Player p) {
        new BukkitRunnable() {
            private static final int DURATION_IN_TICKS = 60;
            private static final double RADIUS_STREAM = 1.2D;
            private static final double RADIUS_PLAYER = 1.66D;
            private static final double ROTATION_DEGREES_PER_TICK = 30;
            private static final double TRAVEL_DISTANCE_PER_TICK = 0.5D;

            private int ticks = 0;

            private final Vector dir = -0.1 < p.getEyeLocation().getDirection().clone().normalize().getY() && p.getEyeLocation().getDirection().clone().normalize().getY() < 0 ? p.getEyeLocation().getDirection().clone().setY(0).normalize() : p.getEyeLocation().getDirection().clone().normalize();
            private final Location currentLoc = p.getEyeLocation().clone().setDirection(dir);

            @Override
            public void run() {
                if (ticks++ >= DURATION_IN_TICKS || currentLoc.getWorld() == null) {
                    this.cancel();
                    return;
                }

                //currentLoc.getWorld().spawnParticle(Particle.REDSTONE, currentLoc, 1, new Particle.DustOptions(Color.fromRGB(255, 245, 235), 3));
                currentLoc.getWorld().playSound(currentLoc, Sound.ENTITY_STRIDER_STEP_LAVA, 1.0f, 1.0f);
                if (ticks % 8 == 1) {
                    currentLoc.getWorld().playSound(currentLoc, Sound.BLOCK_LAVA_EXTINGUISH, 0.5f, 0.5f);
                }

                Vector offset = getPerpendicular(dir).multiply(RADIUS_STREAM).rotateAroundAxis(dir, Math.toRadians(ROTATION_DEGREES_PER_TICK * ticks));
                for (int i = 0 ; i < 3; i++)
                    currentLoc.getWorld().spawnParticle(Particle.REDSTONE, currentLoc.clone().add(offset), 1,
                            new Particle.DustOptions(Color.fromBGR(250, 245, 250), 5));

                //Entities wegschleudern
                Collection<Entity> nearbyEntites = currentLoc.getWorld().getNearbyEntities(currentLoc, RADIUS_PLAYER, RADIUS_PLAYER, RADIUS_PLAYER);
                for (Entity ent : nearbyEntites) {
                    if (ent instanceof LivingEntity && !(ent instanceof ArmorStand) && !ent.equals(p)) {
                        //TODO Funktion finden die if Abfragen ersetzt
                        if (dir.clone().getY() < 0.1) {
                            ent.setVelocity(dir.clone().normalize().setY(dir.clone().getY() + 0.22).normalize().multiply(0.6));
                        } else if (dir.clone().getY() < 0.2) {
                            ent.setVelocity(dir.clone().normalize().setY(dir.clone().getY() + 0.16).normalize().multiply(0.6));
                        } else {
                            ent.setVelocity(dir.clone().normalize().setY(dir.clone().getY() + 0.1).normalize().multiply(0.6));
                        }
                    }
                }

                currentLoc.add(dir.clone().multiply(TRAVEL_DISTANCE_PER_TICK));
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, 1);
        return true;
    }

    private Vector getPerpendicular(Vector vec) {
        if (!vec.isNormalized())
            vec = vec.clone().normalize();

        return Math.abs(vec.getZ()) < Math.abs(vec.getX()) ?
                new Vector(vec.getY(), -vec.getX(), 0) : new Vector(0, -vec.getZ(), vec.getY());
    }

    @Override
    public String getName() {
        return "Airstream";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 140;
    }
}
