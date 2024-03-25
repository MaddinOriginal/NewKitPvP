package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
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
    public boolean useAbility(Player player) {
        Player p = player;

        timer = 5;
        multiplier = 2f;

        loc = p.getLocation();
        loc.setPitch(-8);
        vel = loc.getDirection().multiply(multiplier);
        p.setVelocity(vel); //.multiply(1.5)

        new BukkitRunnable() {

            @Override
            public void run() {
                if (timer > 0) {
                    loc.setPitch(loc.getPitch() + 2.25f);
                    multiplier -= 0.1;
                    vel = loc.getDirection().multiply(multiplier);

                    p.setVelocity(vel);
                    timer--;
                } else {
                    Bukkit.getScheduler().cancelTask(this.getTaskId());
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 1, 2);
        return true;
    }

    @Override
    public String getName() {
        return "Dash";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 70;
    }
}
