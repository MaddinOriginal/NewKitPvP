package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import me.maddinoriginal.newkitpvp.utils.Helper;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Goat;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class DemonCircleAbility extends Ability {

    private final double SIZE = 1.6;
    private final int DELAY = 10;
    private final int DAMAGE_TICKS = 15;
    private final int DAMAGE_TIMES = 6;
    private final int TICKS_ALIVE = DAMAGE_TICKS * DAMAGE_TIMES + DELAY;

    @Override
    public String getName() {
        return "Demon Circle";
    }

    @Override
    public String getDescription() {
        return "Description missing...";
    }

    @Override
    public int getCooldown() {
        return TICKS_ALIVE + 200;
    }

    @Override
    public boolean useAbility(Player player) {
        Location spawnLoc = player.getLocation().clone();
        spawnLoc.setX(spawnLoc.getBlockX() + 0.5);
        spawnLoc.setY(spawnLoc.getBlockY());
        spawnLoc.setZ(spawnLoc.getBlockZ() + 0.5);

        if (!spawnLoc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
            spawnLoc.setY(spawnLoc.getY() - 1);
        }

        /*int tries = 3;

        while (tries > 0 && !spawnLoc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
            tries--;
            spawnLoc.setY(spawnLoc.getY()-1);
        }

        if (!spawnLoc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
            player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 12, 0.75, 0.25, 0.75, 0.1);
            player.getWorld().playSound(player.getEyeLocation(), Sound.ENTITY_ENDERMITE_DEATH, 0.75f, 1.0f);
            return false;
        }*/

        createPlatformUnderneath(spawnLoc, false);

        Helper.drawAlchemyCircle(spawnLoc.add(0, 0.1, 0), SIZE, TICKS_ALIVE, 3);

        Goat goat = spawnLoc.getWorld().spawn(spawnLoc, Goat.class, ent -> {
            ent.setLeftHorn(true);
            ent.setRightHorn(true);

            ent.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1.0);
            ent.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
            ent.addPotionEffect(PotionEffectType.SLOW.createEffect(TICKS_ALIVE, 250));
            ent.addPotionEffect(PotionEffectType.JUMP.createEffect(TICKS_ALIVE, 250));

            ent.setCollidable(false);
            ent.setInvulnerable(true);
        });

        goat.getWorld().playSound(goat, Sound.ENTITY_WITHER_SPAWN, 0.8f, 1.0f);

        new BukkitRunnable() {
            int timer = DAMAGE_TIMES;
            double heal = 0;

            @Override
            public void run() {
                if (timer > 0) {
                    timer--;
                }
                else {
                    Location goatDeath = goat.getLocation().add(0, 0.3, 0);
                    goat.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, goat.getLocation(), 40, 3.5, 0.5, 3.5, 0.2);
                    goat.getWorld().playSound(goat.getEyeLocation(), Sound.ENTITY_WITHER_HURT, 0.6f, 1.0f);
                    goat.setHealth(0);
                    goatDeath.getWorld().spawnParticle(Particle.FALLING_LAVA, goatDeath, 24, 0.4, 0.2, 0.4);

                    if (heal > 0) {
                        player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(0, 1.8, 0), (int) heal*2, 0.5, 0.2, 0.5, 1);
                        try {
                            player.setHealth(player.getHealth() + heal);
                        } catch (IllegalArgumentException e) {
                            player.setHealth(20.0);
                        }
                    }

                    cancel();
                    return;
                }

                List<LivingEntity> nearby = goat.getNearbyEntities(SIZE*3, SIZE*3, SIZE*3).stream()
                        .filter(ent -> ent instanceof LivingEntity && !(ent instanceof ArmorStand)).map(ent -> (LivingEntity) ent).collect(Collectors.toList());

                if (nearby.contains(player)) {
                    nearby.remove(player);
                }

                if (!nearby.isEmpty()) {
                    goat.setScreaming(true);
                    goat.playEffect(EntityEffect.GOAT_RAISE_HEAD);
                    goat.getWorld().spawnParticle(Particle.FLAME, goat.getLocation(), 40, 3.5, 0.5, 3.5, 0.2);
                    goat.getWorld().playSound(goat.getEyeLocation(), Sound.ENTITY_BLAZE_SHOOT, 0.75f, 1.0f);

                    for (LivingEntity ent : nearby) {
                        ent.damage(2.0, player);
                        heal += 1.0;
                    }
                } else {
                    goat.setScreaming(false);
                    goat.playEffect(EntityEffect.GOAT_LOWER_HEAD);
                    goat.getWorld().spawnParticle(Particle.WHITE_SMOKE, goat.getLocation(), 40, 3.5, 0.5, 3.5, 0.2);
                    goat.getWorld().playSound(goat.getEyeLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 0.75f, 1.0f);
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), DELAY, DAMAGE_TICKS);

        return true;
    }

    private void createPlatformUnderneath(Location location, boolean airAbove) {
        //Platform below circle
        for (Block block : Helper.getSphereFlat(location.clone().add(0, -1, 0), 6, false)) {
            if (block.isPassable()) {
                Helper.resetBlockAfter(block, TICKS_ALIVE);
                block.setType(Material.TINTED_GLASS, false);
            }
        }
        //blocks above platform replaced to air
        if (airAbove) {
            for (Block block : Helper.getSphereFlat(location.clone(), 6, false)) {
                Helper.resetBlockAfter(block, TICKS_ALIVE);
                block.setType(Material.AIR, false);
            }
        }
    }
}
