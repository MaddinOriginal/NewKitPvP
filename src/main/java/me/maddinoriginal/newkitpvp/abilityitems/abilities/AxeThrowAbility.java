package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class AxeThrowAbility extends Ability {

    @Override
    public String getName() {
        return "Axe Throw";
    }

    @Override
    public String getDescription() {
        return "Throws an axe";
    }

    @Override
    public int getCooldown() {
        return 120;
    }

    @Override
    public boolean useAbility(Player player) {
        Location loc = player.getEyeLocation().clone();
        loc.setPitch(0);

        Snowball snowball = player.getWorld().spawn(player.getEyeLocation(), Snowball.class, sb -> {
            sb.setShooter(player);
            sb.setGravity(false);
            sb.setVelocity(loc.getDirection().multiply(1.2));
            sb.setItem(new ItemStack(Material.OAK_BUTTON));
        });

        ItemDisplay display = player.getWorld().spawn(player.getEyeLocation(), ItemDisplay.class, disp -> {
            disp.setItemStack(new ItemStack(Material.IRON_AXE));
            //id.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.FIRSTPERSON_RIGHTHAND);
            disp.setTransformation(new Transformation(
                    new Vector3f(0, 0, 0),
                    new AxisAngle4f(1.0f, 0f, 0f, -1f),
                    new Vector3f(2, 2, 2),
                    new AxisAngle4f(0f, 0f, 0f, 0f)));
            disp.setRotation(player.getLocation().getYaw() + 90, 0);
            snowball.addPassenger(disp);
        });

        new BukkitRunnable() {
            int timer = 42;
            float fl = 1.0f;
            float added = 1.5f;
            Projectile proj = snowball;

            @Override
            public void run() {
                if (timer <= 0 || proj.isDead()) {
                    cancel();
                    return;
                } else {
                    timer--;
                }

                if (timer <= 7) {
                    proj.setGravity(true);
                }

                fl += added;
                added = added * 0.95f;

                proj.getLocation().getWorld().playSound(proj.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT, added + 0.1f, 1.0f);

                display.setTransformation(new Transformation(
                        new Vector3f(0, 0, 0),
                        new AxisAngle4f(fl, 0f, 0f, -1f),
                        new Vector3f(2, 2, 2),
                        new AxisAngle4f(0f, 0f, 0f, 0f)));

                //display.setRotation(p.getLocation().getYaw() + 90, 0);
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 1, 1);

        return true;
    }
}
