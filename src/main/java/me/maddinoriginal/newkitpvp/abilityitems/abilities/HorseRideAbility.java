package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class HorseRideAbility extends Ability {

    @Override
    public String getName() {
        return "Ride a horse";
    }

    @Override
    public String getDescription() {
        return "Ride a horse";
    }

    @Override
    public int getCooldown() {
        return 330;
    }

    @Override
    public boolean useAbility(Player player) {
        Horse horse = player.getWorld().spawn(player.getLocation(), Horse.class, ent -> {
            ent.setColor(Horse.Color.BLACK);
            ent.setStyle(Horse.Style.BLACK_DOTS);

            ent.getInventory().setSaddle(new ItemStack(Material.SADDLE));
            //ent.getInventory().setArmor(new ItemStack(Material.DIAMOND_HORSE_ARMOR));

            ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
            ent.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.6);
            ent.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.99);

            ent.setJumpStrength(1.0);
            ent.addPotionEffect(PotionEffectType.SLOW_FALLING.createEffect(-1, 1));

            ent.setTamed(true);
            ent.addPassenger(player);
        });

        player.getVehicle().getWorld().playSound(player, Sound.ITEM_GOAT_HORN_SOUND_2, 1.0f, 1.0f);

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!horse.isDead()) {
                    horse.remove();
                }
            }
        }.runTaskLater(NewKitPvP.getInstance(), 300);

        return true;
    }
}
