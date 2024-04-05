package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;

public class HomingArrowsAbility extends Ability {

    private final int ACTIVE_TICKS = 150;
    private final int PERIOD = 1;
    private final double RAY_SIZE = 1.6; //upgrades 1.64, 1.68, 1.72, 1.76, 1.8, 1.84, 1.88, 1.92, 1.96, 2.0
    private final double MAX_DISTANCE = 12;

    private static final List<Player> activePlayers = new ArrayList<>();

    @Override
    public String getName() {
        return "Homing Arrows";
    }

    @Override
    public String getDescription() {
        return "Upon activation all arrows will automatically change direction to the nearest target if there is one";
    }

    @Override
    public int getCooldown() {
        return ACTIVE_TICKS + 200;
    }

    @Override
    public boolean useAbility(Player player) {
        if (activePlayers.contains(player)) {
            return false;
        }

        activePlayers.add(player);
        player.sendMessage(ChatColor.GREEN + "Homing Arrows activated for " + ChatColor.YELLOW + ACTIVE_TICKS/20 + ChatColor.GREEN + " seconds!");

        new BukkitRunnable() {
            int ticksRemaining = ACTIVE_TICKS;

            @Override
            public void run() {
                if (ticksRemaining <= 0) {
                    System.out.println("Cancelling Homing Arrows for player " + player.getName());
                    activePlayers.remove(player);
                    cancel();
                    return;
                } else {
                    ticksRemaining -= PERIOD;
                }

                for (Entity ent : player.getWorld().getEntities()) {
                    if (ent instanceof Arrow) {
                        Arrow arrow = (Arrow) ent;

                        if (Objects.equals(arrow.getShooter(), player) && !arrow.isOnGround()) {
                            LivingEntity target = getHomingTarget(arrow);

                            //System.out.println("Target= " + target);

                            Particle particle = ticksRemaining % 2 == 0 ? Particle.SQUID_INK : Particle.GLOW_SQUID_INK;
                            arrow.getWorld().spawnParticle(particle, arrow.getLocation(), 1, 0, 0, 0, 0.01);

                            if (target != null) {
                                Vector v = target.getEyeLocation().toVector().subtract(arrow.getLocation().toVector());
                                arrow.setVelocity(v.normalize().multiply(arrow.getVelocity().length()));
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, PERIOD);

        return true;
    }

    private LivingEntity getHomingTarget(Projectile proj) {
        Player shooter = (Player) proj.getShooter();

        RayTraceResult result = proj.getWorld().rayTraceEntities(proj.getLocation(),
                proj.getLocation().getDirection(), MAX_DISTANCE, RAY_SIZE,
                ent -> (ent instanceof LivingEntity && !(ent instanceof ArmorStand) && ent != shooter &&
                        !(ent.hasMetadata("SummonedBy") && ent.getMetadata("SummonedBy").get(0).asString().equals(shooter.getUniqueId().toString()))));

        try {
            return (LivingEntity) result.getHitEntity();
        } catch (Exception ex) {
            return null;
        }
    }
}
