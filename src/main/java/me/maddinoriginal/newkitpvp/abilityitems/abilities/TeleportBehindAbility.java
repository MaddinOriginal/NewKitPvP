package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
        Location tpLoc = p.getEyeLocation();
        Vector dir = tpLoc.getDirection().normalize();

        for (int i = 0; i < 24; i++) {
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
        return false;
    }
}
