package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WolfHuntAbility extends Ability {

    private final int lifespan = 20*7;

    @Override
    public String getName() {
        return "Wolf Hunt";
    }

    @Override
    public String getDescription() {
        return "Test";
    }

    @Override
    public int getCooldown() {
        return 250;
    }

    @Override
    public boolean useAbility(Player player) {
        Player p = player;

        List<Wolf> wolfs = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            wolfs.add(summonWolf(p));
        }

        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WOLF_HOWL, 1.0f, 1.0f);

        //make wolfs constantly target nearest player(s)
        new BukkitRunnable() {

            @Override
            public void run() {
                for (Wolf wolf : wolfs) {
                    List<Entity> ents = wolf.getNearbyEntities(16, 8, 16).stream().filter(ent ->  ent instanceof Player).map(ent -> (Player) ent).collect(Collectors.toList());

                    if (ents.contains(p)) {
                        ents.remove(p);
                    }

                    if (ents.isEmpty()) {
                        continue;
                    }

                    Player closest = (Player) ents.stream().min(Comparator.comparingDouble(p -> p.getLocation().distance(wolf.getLocation()))).get();

                    wolf.setTarget(closest);
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 1, 10);

        //remove wolfs after lifespan
        new BukkitRunnable() {
            boolean once = true;

            @Override
            public void run() {
                for (Wolf wolf : wolfs) {
                    if (!wolf.isDead()) {
                        wolf.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, wolf.getLocation().add(0, 0.4, 0), 8, 0.3, 0.2, 0.3, 0.01);
                        if (once) {
                            once = false;
                            wolf.getWorld().playSound(wolf.getLocation(), Sound.ENTITY_WOLF_DEATH, 1.0f, 1.0f);
                        }
                        wolf.remove();
                    }
                }
                wolfs.clear();
            }
        }.runTaskLater(NewKitPvP.getInstance(), lifespan);
        return true;
    }

    private Wolf summonWolf(Player p) {
        Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
        //wolf.setTamed(true);
        //wolf.setCollarColor(DyeColor.values()[random.nextInt(DyeColor.values().length)]);
        //wolf.setOwner(p);

        wolf.setAngry(true);
        wolf.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 5));
        wolf.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
        wolf.setHealth(5);
        //wolf.setTarget(p);

        return wolf;
    }
}
