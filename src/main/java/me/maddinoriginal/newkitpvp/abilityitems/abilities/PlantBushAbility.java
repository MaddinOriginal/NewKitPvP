package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import me.maddinoriginal.newkitpvp.utils.Helper;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantBushAbility extends Ability {

    private final int DELAY = 8;
    private final int MAX_TRIES = 12;
    private final int TICKS_BETWEEN_TRIES = 2;
    private final int REMOVE_AFTER = 150;
    private Map<Location, BlockState> blockStates = new HashMap<>();

    @Override
    public String getName() {
        return "Plant Bush";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 75;
    }

    @Override
    public boolean useAbility(Player p) {
        ItemStack seeds = new ItemStack(Material.SWEET_BERRIES);
        Location loc = p.getLocation();
        Vector dir = loc.getDirection().clone().normalize().multiply(2);

        Item drop = p.getWorld().dropItem(p.getEyeLocation(), seeds);
        drop.setPickupDelay(DELAY + (MAX_TRIES * TICKS_BETWEEN_TRIES) + 1);
        drop.setVelocity(dir);

        new BukkitRunnable() {
            int tries = MAX_TRIES;

            @Override
            public void run() {
                Location loc = drop.getLocation();
                Block block = loc.getBlock();

                if (!block.getRelative(BlockFace.DOWN).getType().isSolid()) {
                    if (tries > 0) {
                        tries--;
                    }
                    else {
                        block.getWorld().playEffect(block.getLocation().add(0.5, 0, 0.5), Effect.ENDER_SIGNAL, 0);
                        drop.remove();
                        cancel();
                    }
                    return;
                }

                drop.remove();

                Block north = block.getRelative(BlockFace.NORTH);
                Block northEast = block.getRelative(BlockFace.NORTH_EAST);
                Block east = block.getRelative(BlockFace.EAST);
                Block southEast = block.getRelative(BlockFace.SOUTH_EAST);
                Block south = block.getRelative(BlockFace.SOUTH);
                Block southWest = block.getRelative(BlockFace.SOUTH_WEST);
                Block west = block.getRelative(BlockFace.WEST);
                Block northWest = block.getRelative(BlockFace.NORTH_WEST);
                Block top = block.getRelative(BlockFace.UP);
                Block topNorth = block.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH);
                Block topEast = block.getRelative(BlockFace.UP).getRelative(BlockFace.EAST);
                Block topSouth = block.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH);
                Block topWest = block.getRelative(BlockFace.UP).getRelative(BlockFace.WEST);
                Block botNorth = block.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH);
                Block botEast = block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST);
                Block botSouth = block.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH);
                Block botWest = block.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST);
                List<Block> blocklist = Arrays.asList(block, north, northEast, east, southEast, south, southWest, west, northWest,
                        top, topNorth, topEast, topSouth, topWest, botNorth, botEast, botSouth, botWest);

                for (Block b : blocklist) {
                    if (b.getType().isSolid()) {
                        continue;
                    }

                    //Resets the block after given ticks have passed
                    Helper.resetBlockAfter(b, REMOVE_AFTER);

                    //replace current Block with the berry bush block
                    b.setType(Material.SWEET_BERRY_BUSH, false);
                    Ageable data = (Ageable) b.getBlockData();
                    data.setAge(1);
                    b.setBlockData(data);
                    b.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, b.getLocation().clone().add(0.5, 0.5, 0.5), 3, 0.2, 0.2, 0.2);
                }
                cancel();
            }
        }.runTaskTimer(NewKitPvP.getInstance(), DELAY, TICKS_BETWEEN_TRIES);
        return true;
    }
}
