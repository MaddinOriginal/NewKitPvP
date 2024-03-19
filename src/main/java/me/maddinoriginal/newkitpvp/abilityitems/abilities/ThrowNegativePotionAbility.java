package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ThrowNegativePotionAbility extends Ability {

    private Random random = new Random();
    private List<PotionEffectType> negativeEffects;

    public ThrowNegativePotionAbility() {
        negativeEffects = Arrays.asList(
                PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.POISON,
                PotionEffectType.SLOW, PotionEffectType.WEAKNESS, PotionEffectType.WITHER
        );
    }

    @Override
    public String getName() {
        return "Throw negative Potion";
    }

    @Override
    public String getDescription() {
        return "Throws a potion with a negative effect";
    }

    @Override
    public int getCooldown() {
        return 75;
    }

    @Override
    public boolean useAbility(Player player) {
        Player p = player;

        /**int n = 5 + random.nextInt(5);
        for (int i = 0; i < n; i++) {
            launchPotion(p);
        }*/

        p.getWorld().playSound(p.getEyeLocation(), Sound.ENTITY_WITCH_CELEBRATE, 0.5f, 0.5f);

        new BukkitRunnable() {
            int counter = 0;

            @Override
            public void run() {
                int n = 1 + random.nextInt(2);
                for (int i = 0; i < n; i++) {
                    launchPotion(p);
                }

                p.getWorld().playSound(p.getEyeLocation(), Sound.ENTITY_WITCH_THROW, 0.7f, 0.5f);

                counter++;
                if (counter == 5) {
                    Bukkit.getScheduler().cancelTask(getTaskId());
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, 1);
        return true;
    }

    private void launchPotion(Player p) {
        Vector dir = p.getLocation().getDirection().normalize().setY(0.2).normalize();
        Vector v = dir.clone();

        double x = 0.03 + random.nextDouble(0.05);
        double y = 0.03 + random.nextDouble(0.05);
        double z = 0.03 + random.nextDouble(0.05);

        v.setX(v.getX() + (random.nextBoolean() ? x : -x));
        v.setY(v.getY() + (random.nextBoolean() ? y : -y));
        v.setZ(v.getZ() + (random.nextBoolean() ? z : -z));
        v.multiply(0.75 + random.nextDouble(0.76));

        ItemStack item = createRandomPotion();
        ThrownPotion thrownPotion = p.launchProjectile(ThrownPotion.class);
        thrownPotion.setItem(item);
        thrownPotion.setVelocity(v);
    }

    private ItemStack createRandomPotion() {
        ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

        PotionData potionData = new PotionData(PotionType.UNCRAFTABLE, false, false);
        potionMeta.setBasePotionData(potionData);

        PotionEffect effect = new PotionEffect(negativeEffects.get(random.nextInt(negativeEffects.size())),
                120 + random.nextInt(81), 0, true, true, true);
        potionMeta.addCustomEffect(effect, true);

        potionMeta.setColor(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        potion.setItemMeta(potionMeta);

        return potion;
    }
}
