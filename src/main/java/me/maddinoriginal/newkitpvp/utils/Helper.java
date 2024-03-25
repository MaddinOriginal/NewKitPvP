package me.maddinoriginal.newkitpvp.utils;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    private static Map<Location, BlockState> blockStates = new HashMap<>();
    private static int currentID = 0;

    public static void resetBlockAfter(Block b, int ticks) {
        //give block a unique id to recognize later if it is still the same block we want to revert the state for
        int blockID = nextID();
        b.setMetadata("BlockID", new FixedMetadataValue(NewKitPvP.getInstance(), blockID));

        //add the state to our hashmap if it is not yet in the map
        if (!blockStates.containsKey(b.getLocation())) {
            blockStates.put(b.getLocation(), b.getState());
        }

        //task to revert the state of the block after ticks amount of time
        new BukkitRunnable() {
            @Override
            public void run() {
                //TODO get(0) must not always return the correct value we want to have e.g. if other plugins interfere
                int currentID = b.getMetadata("BlockID").get(0).asInt();

                //check if still the same unique id as before and only then change it back
                if (currentID == blockID) {
                    BlockState previous = blockStates.get(b.getLocation());
                    b.setType(previous.getType());
                    b.setBlockData(previous.getBlockData());
                    blockStates.remove(b.getLocation());
                }
            }
        }.runTaskLater(NewKitPvP.getInstance(), ticks);
    }

    public static int nextID() {
        return currentID++;
    }
}
