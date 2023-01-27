package me.maddinoriginal.newkitpvp.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DashAbility extends Ability {

    private int timer;
    private float multiplier;
    private Location loc;
    private Vector vel;

    @Override
    public void useAbility(Player player) {
        Player p = player;

        timer = 6;
        multiplier = 2f;

        loc = p.getLocation();
        loc.setPitch(-7);
        vel = loc.getDirection().multiply(multiplier);
        p.setVelocity(vel.multiply(1.5));

        new BukkitRunnable() {

            @Override
            public void run() {
                if (timer > 0) {
                    loc.setPitch(loc.getPitch() + 2);
                    multiplier -= 0.1;
                    vel = loc.getDirection().multiply(multiplier);

                    p.setVelocity(vel.multiply(1.3));
                    timer--;
                } else {
                    Bukkit.getScheduler().cancelTask(this.getTaskId());
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 1, 2);
    }

    @Override
    String getName() {
        return "Dash";
    }

    @Override
    String getDescription() {
        return null;
    }

    @Override
    int getCooldown() {
        return 70;
    }
}
