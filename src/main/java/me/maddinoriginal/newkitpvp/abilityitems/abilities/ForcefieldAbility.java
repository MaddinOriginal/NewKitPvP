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

public class ForcefieldAbility extends Ability {

    private double multWidth = 2.8;
    private double multHeight = 3.2;

    @Override
    public String getName() {
        return "Forcefield";
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
        new BukkitRunnable() {
            int i = 50;

            @Override
            public void run() {
                if (i > 0) {
                    Location loc = player.getLocation();
                    loc.setY(loc.getY() + 1.2);

                    drawSphere(loc);
                    pushAwayEntities(player);

                    i--;
                } else {
                    Bukkit.getScheduler().cancelTask(getTaskId());
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, 4);
        return true;
    }

    private void drawSphere(Location location) {
        for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
            double radius = Math.sin(i) * multWidth;
            double y = Math.cos(i) * multHeight;
            for (double a = 0; a < Math.PI * 2; a+= Math.PI / 10) {
                double x = Math.cos(a) * radius;
                double z = Math.sin(a) * radius;
                location.add(x, y, z);
                location.getWorld().spawnParticle(Particle.CRIT_MAGIC, location, 1, 0.0, 0.0, 0.0, 0.01);
                //location.getWorld().spawnParticle(Particle.BLOCK_MARKER, location, 1, 0.0, 0.0, 0.0, 0.01, Bukkit.createBlockData(Material.TNT));
                //TODO Flash Particle testen
                location.subtract(x, y, z);
            }
        }
    }

    private void pushAwayEntities(Player p) {
        for (Entity ent : p.getNearbyEntities(multWidth, multHeight, multWidth)) {
            if (ent instanceof LivingEntity && !(ent instanceof ArmorStand)) {
                LivingEntity livEnt = (LivingEntity) ent;
                livEnt.damage(1.5);
                Vector vec = livEnt.getLocation().toVector().subtract(p.getLocation().toVector());

                vec.normalize();
                if (vec.getY() >= 0) {
                    if (vec.getY() <= 0.4) {
                        vec.setY(0.4);
                    }
                    vec.multiply(1.5);
                } else if (vec.getY() >= -0.6) {
                    vec.setY(0.1);
                    vec.multiply(3);
                } else {
                    vec.setY(vec.getY() / 2);
                    vec.multiply(4);
                }

                livEnt.setVelocity(vec);
            }
        }
    }
}
