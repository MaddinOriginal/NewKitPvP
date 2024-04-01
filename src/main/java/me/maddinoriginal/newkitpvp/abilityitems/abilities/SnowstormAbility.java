package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.kits.KitType;
import me.maddinoriginal.newkitpvp.utils.Helper;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SnowstormAbility extends Ability {

    private final int RESET_AFTER = 110;
    private Random random = new Random();

    @Override
    public String getName() {
        return "Snowstorm";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 210 + RESET_AFTER;
    }

    @Override
    public boolean useAbility(Player player) {
        Location loc = player.getLocation().getBlock().getLocation().add(0.5, 0.5, 0.5);
        double radius = 7.0;

        ArmorStand armorStand = player.getWorld().spawn(loc, ArmorStand.class, as -> {
            as.setMarker(true);
            as.setInvisible(true);
            as.setAI(false);
            as.setGravity(false);
            as.setInvulnerable(true);
        });

        //list for fake igloo blocks to be removed again after x amount ticks
        List<ItemDisplay> displayList = new ArrayList<>();

        //for every block in a sphere with radius + 1
        for (Block block : Helper.getSphere(loc, (int) radius + 1, false)) {

            //spawn snow particles and sound effects
            Location blockLoc = block.getLocation().add(0.5, 0.5, 0.5);
            block.getWorld().spawnParticle(Particle.SNOWFLAKE, blockLoc, 1, 0.1, 0.1, 0.1, 0.1);
            if (block.getY()+0.5 == loc.getY()) {
                block.getWorld().playSound(blockLoc, Sound.BLOCK_POWDER_SNOW_STEP, 0.2f, 1.0f);
            }

            //place igloo fake blocks and barriers
            if (block.getY() >= loc.getY()-1.5 && blockLoc.distance(loc) >= radius-1) {
                ItemDisplay display = block.getWorld().spawn(blockLoc, ItemDisplay.class, dis -> dis.setItemStack(new ItemStack(Material.SNOW_BLOCK)));
                displayList.add(display);
                Helper.resetBlockAfter(block, RESET_AFTER);
                block.setType(Material.BARRIER, false);
            }

            //Place snow layer
            if (block.isPassable() && block.canPlace(Material.SNOW.createBlockData())) {
                Helper.resetBlockAfter(block, RESET_AFTER + random.nextInt(11));
                block.setType(Material.SNOW, false);
            }
        }

        loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_WIND_BURST, 1.0f, 1.0f);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (ItemDisplay is : displayList) {
                    is.remove();
                }
                displayList.clear();
                cancel();
            }
        }.runTaskLater(NewKitPvP.getInstance(), RESET_AFTER);

        //Freeze nearby LivingEntities
        List<LivingEntity> nearbyLivingEntities = armorStand.getNearbyEntities(radius, radius, radius).stream()
                .filter(ent -> ent instanceof LivingEntity).map(ent -> (LivingEntity) ent).collect(Collectors.toList());
        for (LivingEntity ent : nearbyLivingEntities) {
            if (ent instanceof Player && KitPlayerManager.getInstance().getKitPlayer(player).getKitType().equals(KitType.YETI)) {
                continue;
            }
            ent.setFreezeTicks(RESET_AFTER + 140);
        }

        return false;
    }

    private void drawSphere(Location location, double widthMultiplier, double heightMultiplier) {
        for (double i = 0; i <= Math.PI; i += Math.PI / (6 * heightMultiplier)) {
            double radius = Math.sin(i) * widthMultiplier;
            double y = Math.cos(i) * heightMultiplier;
            for (double a = 0; a < Math.PI * 2; a+= Math.PI / (6 * widthMultiplier)) {
                double x = Math.cos(a) * radius;
                double z = Math.sin(a) * radius;
                location.add(x, y, z);
                location.getWorld().spawnParticle(Particle.CLOUD, location, 1, 0.0, 0.0, 0.0, 0.01);
                location.getWorld().spawnParticle(Particle.BLOCK_MARKER, location, 1, 0.0, 0.0, 0.0, 0.01, Bukkit.createBlockData(Material.SNOW_BLOCK));
                location.subtract(x, y, z);
            }
        }
    }
}
