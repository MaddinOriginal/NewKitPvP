package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;

public class HomingArrowsAbility extends Ability {

    private final int ACTIVE_TICKS = 140;
    private final int PERIOD = 1;

    private static final List<Player> activePlayers = new ArrayList<>();

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
        return ACTIVE_TICKS + 200;
    }

    @Override
    public boolean useAbility(Player player) {
        if (activePlayers.contains(player)) {
            System.out.println("Player " + player.getName() + " tried activating Homing Arrows, but the effect is already/still active.");
            return false;
        }

        activePlayers.add(player);

        System.out.println("Homing Arrows for player " + player.getName() + " activated for " + ACTIVE_TICKS + " ticks.");
        player.sendMessage(ChatColor.GREEN + "");

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

    private final double RAY_SIZE = 1.6; //upgrades 1.64, 1.68, 1.72, 1.76, 1.8, 1.84, 1.88, 1.92, 1.96, 2.0
    private final double MAX_DISTANCE = 12;

    private LivingEntity getHomingTarget(Projectile proj) {
        Player shooter = (Player) proj.getShooter();

        //System.out.println("Testing arrow at " + proj.getLocation());

        RayTraceResult result = proj.getWorld().rayTraceEntities(proj.getLocation(),
                proj.getLocation().getDirection(), MAX_DISTANCE, RAY_SIZE,
                ent -> (ent instanceof HumanEntity || ent instanceof Creature) && ent != shooter &&
                        !(ent.hasMetadata("SummonedBy") && ent.getMetadata("SummonedBy").get(0).asString().equals(shooter.getUniqueId().toString())));

        try {
            return (LivingEntity) result.getHitEntity();
        } catch (Exception ex) {
            return null;
        }
    }
}
