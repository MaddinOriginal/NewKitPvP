package me.maddinoriginal.newkitpvp.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class ScareOffAbility extends Ability {

    @Override
    public void useAbility(Player p) {
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SCREAM, 1.0f, 1.0f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_WARN, 1.0f, 1.0f);

        for (Player enemies : Bukkit.getOnlinePlayers()) {
            if (enemies != p && enemies.getLocation().distance(p.getLocation()) <= 6) {
                enemies.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2 * 20, 0));
                enemies.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5 * 20, 2));

                Location loc = enemies.getLocation();
                Vector dir = loc.getDirection();

                float yaw = loc.getYaw();
                if (yaw <= 0) { yaw += 180; } else { yaw -= 180; }
                loc.setYaw(yaw);
                loc.setPitch(0);

                enemies.teleport(loc);
                enemies.setVelocity(dir);
            }
        }
    }

    @Override
    String getName() {
        return "Scare Off";
    }

    @Override
    String getDescription() {
        return null;
    }

    @Override
    int getCooldown() {
        return 110;
    }
}
