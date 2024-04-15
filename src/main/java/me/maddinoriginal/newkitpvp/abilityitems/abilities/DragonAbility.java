package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DragonAbility extends Ability {

    @Override
    public String getName() {
        return "Dragon Transform";
    }

    @Override
    public String getDescription() {
        return "Transform into a dragon";
    }

    @Override
    public int getCooldown() {
        return 250;
    }

    @Override
    public boolean useAbility(Player player) {
        Location start = player.getLocation();
        start.setPitch(0);
        Vector v = start.getDirection();
        Location dragonSpawnLocation = start.clone().subtract(start.clone().getDirection().multiply(8));
        dragonSpawnLocation.setYaw(dragonSpawnLocation.getYaw() + 180);

        EnderDragon dragon = player.getWorld().spawn(dragonSpawnLocation, EnderDragon.class, ent -> {
            ent.setMetadata("SummonedBy", new FixedMetadataValue(NewKitPvP.getInstance(), player.getUniqueId()));
            ent.setPhase(EnderDragon.Phase.CIRCLING);
        });

        dragon.getWorld().playSound(dragon, Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);

        ArmorStand stand = player.getWorld().spawn(start, ArmorStand.class, armor -> {
            armor.setInvisible(true);
            armor.setGravity(false);
            armor.setInvulnerable(true);
        });

        player.setGameMode(GameMode.SPECTATOR);
        player.setSpectatorTarget(stand);

        dragon.setPhase(EnderDragon.Phase.LEAVE_PORTAL);
        dragon.setRotation(dragonSpawnLocation.getYaw(), dragonSpawnLocation.getPitch());

        new BukkitRunnable() {
            int timer = 9;

            @Override
            public void run() {
                if (timer > 0) {
                    dragon.setPhase(EnderDragon.Phase.LEAVE_PORTAL);
                    dragon.setRotation(dragonSpawnLocation.getYaw(), dragonSpawnLocation.getPitch());
                    dragon.setVelocity(v);
                    timer--;
                } else {
                    dragon.remove();
                    stand.remove();
                    player.teleport(start);
                    player.setGameMode(GameMode.ADVENTURE);
                    cancel();
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 1, 3);

        return true;
    }
}
