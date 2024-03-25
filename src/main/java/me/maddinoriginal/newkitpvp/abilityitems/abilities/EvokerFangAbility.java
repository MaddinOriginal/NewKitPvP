package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EvokerFangAbility extends Ability {

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
        return 130;
    }

    private Vector[] surroundingFangsVector = {
            new Vector(1, 0, 1),
            new Vector(-1, 0, 1),
            new Vector(1, 0, -1),
            new Vector(-1, 0, -1),

            new Vector(2, 0, 0),
            new Vector(0, 0, 2),
            new Vector(-2, 0, 0),
            new Vector(0, 0, -2),

            new Vector(3, 0, 2),
            new Vector(-3, 0, 2),
            new Vector(3, 0, -2),
            new Vector(-3, 0, -2),

            new Vector(2, 0, 3),
            new Vector(-2, 0, 3),
            new Vector(2, 0, -3),
            new Vector(-2, 0, -3)
    };

    @Override
    public boolean useAbility(Player player) {
        Location loc = player.getLocation();
        loc.setY(loc.getBlockY());
        loc.setPitch(0);

        //if block below not solid, get block below
        if (!loc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
            loc.setY(loc.getY() - 1);
        }

        //if block below still not solid cancel ability
        if (!loc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
            player.spawnParticle(Particle.VILLAGER_ANGRY, player.getEyeLocation(), 10, 0.25, 0.1, 0.25);
            player.playSound(player, Sound.ENTITY_IRON_GOLEM_HURT, 1.0f, 1.0f);
            return false;
        }

        player.teleport(loc);
        player.addPotionEffect(PotionEffectType.SLOW.createEffect(10, 9));
        player.addPotionEffect(PotionEffectType.JUMP.createEffect(10, 250));

        for (Vector v : surroundingFangsVector) {
            Location next = loc.clone().add(v);
            if (!next.getBlock().getType().isSolid() && next.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                next.getWorld().spawn(next, EvokerFangs.class, ent -> ent.setOwner(player));
            }
        }

        Vector dir = loc.getDirection().multiply(1.15);
        loc.add(dir.multiply(2.5));

        new BukkitRunnable() {
            int timer = 14;

            @Override
            public void run() {
                loc.add(dir);

                if (!loc.getBlock().getType().isSolid() && loc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                    loc.getWorld().spawn(loc, EvokerFangs.class, ent -> ent.setOwner(player));

                    timer--;

                    if (timer <= 0) {
                        cancel();
                    }
                }
                else {
                    cancel();
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, 1);

        return true;
    }
}
