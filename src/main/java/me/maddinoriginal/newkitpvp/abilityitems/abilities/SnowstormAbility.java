package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SnowstormAbility extends Ability {

    private double multWidth = 2.5;
    private double multHeight = 2.5;

    @Override
    public String getName() {
        return "Snowstorm";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 210;
    }

    @Override
    public boolean useAbility(Player player) {
        double radius = 1.2D;

        new BukkitRunnable() {

            @Override
            public void run() {
                player.getNearbyEntities(radius, radius, radius);
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, 4);
        return false;
    }

    private void drawSphere(Location location) {
        for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
            double radius = Math.sin(i) * multWidth;
            double y = Math.cos(i) * multHeight;
            for (double a = 0; a < Math.PI * 2; a+= Math.PI / 10) {
                double x = Math.cos(a) * radius;
                double z = Math.sin(a) * radius;
                location.add(x, y, z);
                location.getWorld().spawnParticle(Particle.CLOUD, location, 1, 0.0, 0.0, 0.0, 0.01);
                location.getWorld().spawnParticle(Particle.BLOCK_MARKER, location, 1, 0.0, 0.0, 0.0, 0.01, Bukkit.createBlockData(Material.SNOWBALL));
                location.subtract(x, y, z);
            }
        }
    }
}
