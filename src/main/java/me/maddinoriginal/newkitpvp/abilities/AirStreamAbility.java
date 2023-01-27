package me.maddinoriginal.newkitpvp.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AirStreamAbility extends Ability {

    private int timer = 0;

    @Override
    public void useAbility(Player player) {
        Player p = player;
        Location eyeLoc = p.getEyeLocation();
        Vector dirStep = eyeLoc.getDirection().clone().normalize().multiply(2.25);

        new BukkitRunnable() {

            @Override
            public void run() {
                eyeLoc.add(dirStep);
                eyeLoc.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, eyeLoc, 1);

                for (Player enemy : Bukkit.getOnlinePlayers()) {
                    if (enemy.getLocation().distance(eyeLoc) < 3.3 && enemy != p) {
                        enemy.setVelocity(dirStep.clone());
                    }
                }

                timer++;
                if (timer >= 10)
                    Bukkit.getScheduler().cancelTask(this.getTaskId());
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, 5);
    }

    @Override
    String getName() {
        return "Airstream";
    }

    @Override
    String getDescription() {
        return null;
    }

    @Override
    int getCooldown() {
        return 140;
    }
}
