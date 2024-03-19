package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class ShootBulletsAbility extends Ability {

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
        return 0;
    }

    @Override
    public boolean useAbility(Player player) {
        new BukkitRunnable() {
            int i = 30;

            @Override
            public void run() {
                performLaser(player);
                i--;
                if (i == 0) {
                    Bukkit.getScheduler().cancelTask(getTaskId());
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, 8);
        return true;
    }

    private void performLaser(Player p) {
        new BukkitRunnable() {
            int i = 10;
            Item item;
            World w = p.getWorld();
            Location loc = p.getEyeLocation();
            Vector vec = loc.getDirection().normalize();

            @Override
            public void run() {
                //Spawn item drop
                if (item == null) {
                    item = w.dropItem(loc, new ItemStack(Material.COPPER_BLOCK));
                    w.playSound(loc, Sound.ITEM_CROSSBOW_SHOOT, 1.0f, 1.0f);
                }

                item.setVelocity(vec);

                //calculate enemy hit
                List<Entity> nearby = item.getNearbyEntities(0.4, 0.3, 0.4);
                for (Entity ent : nearby) {
                    if (ent == p) {
                        continue;
                    }
                    if (ent instanceof LivingEntity && !(ent instanceof ArmorStand)) {
                        w.createExplosion(item.getLocation(), 1.0f, false, false);
                        ((LivingEntity) ent).damage(1.5, p);
                        i = 0;
                        break;
                    }
                }

                //cancel task
                if (i <= 0 || item.isOnGround()) {
                    item.remove();
                    Bukkit.getScheduler().cancelTask(getTaskId());
                    return;
                }
                i--;
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, 2);
    }
}
