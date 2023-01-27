package me.maddinoriginal.newkitpvp.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class PlantBushAbility extends Ability {

    private int lifetimeTicks = 8;

    @Override
    public void useAbility(Player p) {
        //TODO revisit (bei zwei bushes von verschiedenen abilities funktionierts nicht richtig)
        ItemStack seeds = new ItemStack(Material.SWEET_BERRIES);
        Location loc = p.getLocation();
        Vector dir = loc.getDirection().clone().normalize().multiply(2);

        Item drop = p.getWorld().dropItem(p.getEyeLocation(), seeds);
        drop.setPickupDelay(lifetimeTicks + 10);
        drop.setVelocity(dir);

        new BukkitRunnable() {

            @Override
            public void run() {
                Location loc = drop.getLocation();
                drop.remove();

                Block block = loc.getBlock();
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
                List<Block> blocklist = Arrays.asList(block, north, northEast, east, southEast, south, southWest, west, northWest,
                        top, topNorth, topEast, topSouth, topWest);

                if (block.getRelative(BlockFace.DOWN).getType().isSolid()) {
                    for (Block b : blocklist) {
                        if (b.getType().isSolid()) {
                            continue;
                        }
                        Block previous = b;
                        BlockData previousData = previous.getBlockData();
                        Location previousLoc = b.getLocation();
                        b.setType(Material.SWEET_BERRY_BUSH, false);
                        Ageable data = (Ageable) b.getBlockData();
                        data.setAge(1);
                        b.setBlockData(data);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                previousLoc.getBlock().setType(previous.getType());
                                previousLoc.getBlock().setBlockData(previousData);
                            }
                        }.runTaskLater(NewKitPvP.getInstance(), 90);
                    }
                }
            }
        }.runTaskLater(NewKitPvP.getInstance(), lifetimeTicks);
    }

    @Override
    String getName() {
        return "Plant Bush";
    }

    @Override
    String getDescription() {
        return null;
    }

    @Override
    int getCooldown() {
        return 90;
    }
}
