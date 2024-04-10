package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class TeleportBehindAbility extends Ability {

    @Override
    public String getName() {
        return "Shadow Step";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 90;
    }

    @Override
    public boolean useAbility(Player p) {
        RayTraceResult result = p.getWorld().rayTrace(p.getEyeLocation(), p.getEyeLocation().getDirection(), 16, FluidCollisionMode.NEVER, true, 2.0,
                entity -> (entity instanceof LivingEntity && !(entity instanceof ArmorStand) && !entity.equals(p)));

        try {
            assert result != null;
            LivingEntity target = (LivingEntity) result.getHitEntity();
            p.getWorld().spawnParticle(Particle.REVERSE_PORTAL, p.getEyeLocation(), 20, 0.5, 0.5, 0.5, 0.05);
            p.getWorld().playSound(p.getEyeLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            assert target != null;
            teleportToTarget(p, target);
            return true;
        } catch (Exception ex) {
            return false;
        }

        /*for (int i = 0; i < 24; i++) {
            tpLoc.add(dir);
            tpLoc.getWorld().spawnParticle(Particle.FLAME, tpLoc, 1, 0, 0, 0, 0.01);

            for (Entity ent : tpLoc.getWorld().getNearbyEntities(tpLoc, 0.5, 0.5, 0.5)) {
                if (!(ent instanceof LivingEntity) || ent == p) {
                    continue;
                }

                LivingEntity livEnt = (LivingEntity) ent;
                Location enemyloc = livEnt.getLocation();
                enemyloc.setPitch(0.0f);
                Vector enemydirstraight = enemyloc.getDirection().normalize();
                enemyloc.setPitch(-25.0f);
                Vector enemydirup = enemyloc.getDirection().normalize();
                enemyloc.setPitch(90.0f);
                Vector enemydirdown = enemyloc.getDirection().normalize();
                enemyloc.setPitch(0.0f);
                p.teleport(enemyloc.subtract(enemydirstraight.multiply(0.5)));
                livEnt.damage(0.5);
                livEnt.setVelocity(enemydirup.multiply(1.25));
                p.setVelocity(enemydirdown);
                return true;
            }
        }
        return false;*/
    }

    private void teleportToTarget(Player p, LivingEntity target) {
        Location targetLoc = target.getLocation();
        targetLoc.setPitch(0f);
        Vector targetDirFront = targetLoc.getDirection();
        targetLoc.setPitch(-25.0f);
        Vector targetDirUp = targetLoc.getDirection();
        targetLoc.setPitch(90.0f);
        Vector targetDirDown = targetLoc.getDirection().normalize();
        targetLoc.setPitch(0.0f);

        p.teleport(targetLoc.subtract(targetDirFront.multiply(0.5)));
        targetLoc.setY(targetLoc.getY() + 1.6);
        p.getWorld().spawnParticle(Particle.REVERSE_PORTAL, targetLoc, 20, 0.5, 0.5, 0.5, 0.05);
        p.getWorld().playSound(p.getEyeLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 1.0f);
        //target.damage(0.5);
        target.setVelocity(targetDirUp.multiply(0.25));
        p.setVelocity(targetDirDown);
    }
}
