package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class MagmaLauncherAbility extends Ability {

    private Vector[] velocities = {
            new Vector(0.25, 0.4, 0.25),
            new Vector(-0.25, 0.4, 0.25),
            new Vector(0.25, 0.4, -0.25),
            new Vector(-0.25, 0.4, -0.25)
    };

    @Override
    public boolean useAbility(Player player) {
        Player p = player;
        Location eyeLoc = p.getEyeLocation();
        Vector dir = eyeLoc.getDirection().normalize().multiply(1.5);
        ArrayList<MagmaCube> miniSlimes = new ArrayList<>();

        MagmaCube slime = (MagmaCube) p.getWorld().spawnEntity(eyeLoc.add(dir), EntityType.MAGMA_CUBE);
        slime.setSize(5);
        slime.setInvulnerable(true);
        slime.setVelocity(new Vector(
                p.getLocation().getDirection().getX() * 1.8,
                0.4f,
                p.getLocation().getDirection().getZ() * 1.8)
        );

        new BukkitRunnable() {
            @Override
            public void run() {
                Location loc = slime.getLocation();
                slime.remove();
                loc.getWorld().createExplosion(loc, 2.8f, true, true);
                loc.getWorld().spawnParticle(Particle.FLAME, loc, 64, 0.75, 0.66, 0.75, 0.09);

                for (int i = 0; i < 4; i++) {
                    MagmaCube miniSlime = (MagmaCube) loc.getWorld().spawnEntity(loc, EntityType.MAGMA_CUBE);
                    miniSlimes.add(miniSlime);
                    miniSlime.setSize(2);
                    miniSlime.setInvulnerable(true);
                    miniSlime.setVelocity(velocities[i]);
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (MagmaCube slime : miniSlimes) {
                            Location loc = slime.getLocation();
                            slime.remove();
                            loc.getWorld().createExplosion(loc, 2.2f, true, true);
                            loc.getWorld().spawnParticle(Particle.FLAME, loc, 16, 0.33, 0.33, 0.33, 0.07);
                        }
                    }
                }.runTaskLater(NewKitPvP.getInstance(), 15);
            }
        }.runTaskLater(NewKitPvP.getInstance(), 20);
        return true;
    }

    @Override
    public String getName() {
        return "Magma Launcher";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 125;
    }
}
