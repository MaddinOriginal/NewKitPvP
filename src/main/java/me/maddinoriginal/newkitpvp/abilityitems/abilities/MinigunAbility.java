package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MinigunAbility extends Ability {

    private int stayActivatedFor = 60; //how many ticks the ability stays active after activation
    private final int PERIOD = 4; //every X ticks an arrow shoots

    @Override
    public boolean useAbility(Player player) {

        //TODO
        new BukkitRunnable() {
            int timer = stayActivatedFor;

            @Override
            public void run() {
                if (timer <= 0) {
                    cancel();
                }
                Location spawnLoc = player.getEyeLocation();
                spawnLoc.setY(spawnLoc.getY() - 0.6);
                spawnLoc.setPitch(spawnLoc.getPitch() / 3 - 6);
                Vector dir = spawnLoc.getDirection();

                player.getWorld().spawnArrow(spawnLoc, dir, 1.33f, 2.25f);
                player.getWorld().playSound(player, Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);

                timer -= PERIOD;
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, PERIOD);

        return true;
    }

    @Override
    public String getName() {
        return "Minigun";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 120;
    }
}
