package me.maddinoriginal.newkitpvp.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TeleportBehindAbility extends Ability {

    @Override
    public void useAbility(Player p) {
        Location loc = p.getLocation();
        Vector dir = loc.getDirection().clone().normalize();
        Location tpLoc = loc.clone();
        dir.normalize();

        for (int i = 0; i < 24; i++) {
            tpLoc.add(dir);
            tpLoc.getWorld().playEffect(tpLoc, Effect.MOBSPAWNER_FLAMES, 1);

            for (Player enemy : Bukkit.getOnlinePlayers()) {
                if (enemy == p) {
                    return;
                }
                if (enemy.getLocation().distance(tpLoc) <= 2.5) {
                    Location enemyloc = enemy.getLocation();
                    enemyloc.setPitch(0.0f);
                    Vector enemydirstraight = enemyloc.getDirection().normalize();
                    enemyloc.setPitch(-25.0f);
                    Vector enemydirup = enemyloc.getDirection().normalize();
                    enemyloc.setPitch(90.0f);
                    Vector enemydirdown = enemyloc.getDirection().normalize();
                    enemyloc.setPitch(0.0f);
                    p.teleport(enemyloc.subtract(enemydirstraight.multiply(0.5)));
                    enemy.damage(3.5);
                    enemy.setVelocity(enemydirup.multiply(1.25));
                    p.setVelocity(enemydirdown);
                    return;
                }
            }
        }
    }

    @Override
    String getName() {
        return "Shadow Step";
    }

    @Override
    String getDescription() {
        return null;
    }

    @Override
    int getCooldown() {
        return 80;
    }
}
