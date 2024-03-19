package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LightningfieldAbility extends Ability {

    private double multWidth = 5.2;
    private double multHeight = 1.5;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 0;
    }

    @Override
    public boolean useAbility(Player player) {
        //Lightning strike (plus electric particles)
        new BukkitRunnable() {
            int i = 20*10;
            World w = player.getWorld();

            @Override
            public void run() {
                //Electric particles
                player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, player.getEyeLocation(), 5, 0.4, 1.2, 0.4, 0.1);
                player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, player.getEyeLocation(), 8, 1.2, 0.5, 1.2, 0.05);

                //Lightning
                for (Entity ent : player.getNearbyEntities(multWidth, multHeight, multWidth)) {
                    if (ent instanceof LivingEntity && !(ent instanceof ArmorStand)) {
                        LivingEntity livEnt = (LivingEntity) ent;
                        w.strikeLightning(livEnt.getLocation());
                        w.spawnParticle(Particle.FLASH, livEnt.getEyeLocation(), 1);
                    }
                }

                //cancel task
                i = i - 5;
                if (i <= 0) {
                    Bukkit.getScheduler().cancelTask(getTaskId());
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, 5);

        //Range indicating particles
        new BukkitRunnable() {
            int i = 20*10;

            @Override
            public void run() {
                //Particles Circle
                Location loc = player.getLocation();
                double radius = Math.sin(Math.PI/2) * multWidth;
                double y = Math.cos(Math.PI/2);
                for (double a = 0; a < Math.PI * 2; a+= Math.PI / 10) {
                    double x = Math.cos(a) * radius;
                    double z = Math.sin(a) * radius;
                    loc.add(x, y, z);
                    loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 3, 0.1, 0.1, 0.1, 0.1);
                    loc.subtract(x, y, z);
                }

                //cancel task
                i = i - 10;
                if (i <= 0) {
                    Bukkit.getScheduler().cancelTask(getTaskId());
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, 10);
        return true;
    }

    /*
    new BukkitRunnable() {
            @Override
            public void run() {
                Vector vec = player.getEyeLocation().getDirection();
                vec.normalize();
                vec.multiply(1.3);
                Location loc = player.getEyeLocation();
                loc.setY(loc.getY() + 0.25);

                for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
                    double radius = Math.sin(i) * 1.5;
                    double y = Math.cos(i) * 1.5;
                    for (double a = 0; a < Math.PI * 2; a+= Math.PI / 10) {
                        double x = Math.cos(a) * radius;
                        double z = Math.sin(a) * radius;
                        loc.add(x, y, z);
                        loc.getWorld().spawnParticle(Particle.FLASH, loc, 1, 0.0, 0.0, 0.0, 0.01);
                        loc.subtract(x, y, z);
                    }
                }

                for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
                    double radius = Math.sin(i) * 3;
                    double y = Math.cos(i) * 3;
                    for (double a = 0; a < Math.PI * 2; a+= Math.PI / 10) {
                        double x = Math.cos(a) * radius;
                        double z = Math.sin(a) * radius;
                        loc.add(x, y, z);
                        loc.getWorld().spawnParticle(Particle.FLASH, loc, 1, 0.0, 0.0, 0.0, 0.01);
                        loc.subtract(x, y, z);
                    }
                }

                for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
                    double radius = Math.sin(i) * 5;
                    double y = Math.cos(i) * 5;
                    for (double a = 0; a < Math.PI * 2; a+= Math.PI / 10) {
                        double x = Math.cos(a) * radius;
                        double z = Math.sin(a) * radius;
                        loc.add(x, y, z);
                        loc.getWorld().spawnParticle(Particle.FLASH, loc, 1, 0.0, 0.0, 0.0, 0.01);
                        loc.subtract(x, y, z);
                    }
                }

                loc.setY(loc.getY() - 0.25);
                loc.add(vec);
                player.getWorld().spawnParticle(Particle.FLASH, loc, 6, 0.0, 0.0, 0.0, 0.01);
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, 4);
     */
}
