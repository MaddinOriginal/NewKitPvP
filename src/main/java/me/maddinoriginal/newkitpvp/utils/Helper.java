package me.maddinoriginal.newkitpvp.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import me.maddinoriginal.newkitpvp.NewKitPvP;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helper {

    private static Map<Location, BlockState> blockStates = new HashMap<>();
    private static int currentID = 0;

    public static int nextID() {
        return currentID++;
    }

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
                    b.setType(previous.getType(), false);
                    b.setBlockData(previous.getBlockData(), false);
                    blockStates.remove(b.getLocation());
                }
            }
        }.runTaskLater(NewKitPvP.getInstance(), ticks);
    }

    private static float offset = 0.66f; //min 0.55f or above

    public static void rotateDisplayPassenger(ItemDisplay display, float time) {
        float x = (float) Math.sin(2 * Math.PI * time);
        float z = (float) Math.cos(2 * Math.PI * time);
        float angle = (float) (time * 2 * Math.PI);

        display.setTransformation(new Transformation(
                new Vector3f(x * offset, -0.75f, z * offset),
                new AxisAngle4f(angle, 0, 1, 0),
                new Vector3f(1, 1, 1),
                new AxisAngle4f(0, 0, 0, 0)));
    }

    public static void hideArmor(Player player) {
        PacketContainer clearArmorPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        clearArmorPacket.getIntegers().write(0, player.getEntityId());

        ArrayList<EnumWrappers.ItemSlot> slots = new ArrayList<>();
        slots.add(EnumWrappers.ItemSlot.FEET);
        slots.add(EnumWrappers.ItemSlot.LEGS);
        slots.add(EnumWrappers.ItemSlot.CHEST);
        slots.add(EnumWrappers.ItemSlot.HEAD);
        slots.add(EnumWrappers.ItemSlot.MAINHAND);
        slots.add(EnumWrappers.ItemSlot.OFFHAND);

        // Create a list of slot-item pairs with empty items (air) for the packet
        List<Pair<EnumWrappers.ItemSlot, ItemStack>> slotItemPairs = new ArrayList<>();
        for (EnumWrappers.ItemSlot itemSlot : slots) {
            ItemStack airItem = new ItemStack(Material.AIR);
            Pair<EnumWrappers.ItemSlot, ItemStack> slotItemPair = new Pair<>(itemSlot, airItem);
            slotItemPairs.add(slotItemPair);
        }
        clearArmorPacket.getSlotStackPairLists().write(0, slotItemPairs);

        // Send the clear armor packet to all players in the same world
        List<Player> playersInWorld = player.getWorld().getPlayers();
        for (Player currentPlayer : playersInWorld) {
            try {
                if (currentPlayer != player) {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(currentPlayer, clearArmorPacket);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<Block> getSphere(Location location, int radius, boolean empty) {
        List<Block> blocks = new ArrayList<>();

        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();

        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int y = by - radius; y <= by + radius; y++) {
                for (int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx - x) * (bx - x) + (bz - z) * (bz - z) + (by - y) * (by - y));
                    if (distance < radius * radius && (!empty || distance > (radius - 1.01) * (radius - 1.01))) {
                        blocks.add(new Location(location.getWorld(), x, y, z).getBlock());
                    }
                }
            }
        }

        return blocks;
    }

    public static List<Block> getSphereFlat(Location location, int radius, boolean empty) {
        List<Block> blocks = new ArrayList<>();

        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();

        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int z = bz - radius; z <= bz + radius; z++) {
                double distance = ((bx - x) * (bx - x) + (bz - z) * (bz - z));
                if (distance < radius * radius && (!empty || distance > (radius - 1.01) * (radius - 1.01))) {
                    blocks.add(new Location(location.getWorld(), x, by, z).getBlock());
                }
            }
        }

        return blocks;
    }

    public static void drawAlchemyCircle(Location loc, double size, int ticks, int period) {
        Helper.drawCircle(loc, 3.0 *size, 0.04, ticks, period); //outer circle
        Helper.drawHexagon(loc, 2.94 *size, 0.2, ticks, period, false); //outer hexagon
        Helper.drawHexagon(loc, 2.486 *size, 0.2, ticks, period, true); //inner hexagon
        Helper.drawSmallCircles(loc, 0.2 *size, 0.6, 1.4 *size, ticks, period); //small circles
        Helper.drawLines(loc, 1.1 *size, 0, 0.2, ticks, period); //lines from center to circles
        Helper.drawLines(loc, 0.6 *size, 1.5 *size, 0.2, ticks, period); //lines from circles away
        Helper.drawCircle(loc, 0.75 *size, 0.16, ticks, period); //inner circle
    }

    public static void drawLines(Location location, double length, double skip, double precision, int ticksAlive, int period) {
        location.setPitch(0);

        new BukkitRunnable() {
            Location loc;
            Vector dir;
            int timer = ticksAlive;

            @Override
            public void run() {
                if (timer <= 0) {
                    cancel();
                    return;
                } else {
                    timer = timer - period;
                }

                for (int i = 0; i < 6; i++) {
                    location.setYaw(location.getYaw() + 60);
                    loc = location.clone().add(location.clone().getDirection().multiply(skip));
                    dir = loc.getDirection().multiply(length);

                    for (int j = 0; j < dir.length()/precision; j++) {
                        loc.add(dir.clone().normalize().multiply(precision));
                        loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 1, 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
                    }
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, period);
    }

    public static void drawCircle(Location location, double size, double precision, int ticksAlive, int period) {
        assert precision > 0;

        new BukkitRunnable() {
            Location loc;
            int timer = ticksAlive;

            @Override
            public void run() {
                if (timer <= 0) {
                    cancel();
                    return;
                } else {
                    timer = timer - period;
                }

                loc = location;

                for (double d = 0; d <= 2*Math.PI; d += precision) {
                    Location particleLoc = location.clone();
                    particleLoc.setX(location.getX() + Math.cos(d) * size);
                    particleLoc.setZ(location.getZ() + Math.sin(d) * size);
                    location.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 1, new Particle.DustOptions(Color.RED, 1));
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, period);
    }

    public static void drawSmallCircles(Location location, double size, double precision, double distance, int ticksAlive, int period) {
        location.setPitch(0);

        for (int i = 0; i < 6; i++) {
            location.setYaw(location.getYaw() + 60);
            drawCircle(location.clone().add(location.getDirection().multiply(distance)), size, precision, ticksAlive, period);
        }
    }

    public static void drawHexagon(Location location, double size, double precision, int ticksAlive, int period, boolean rotated) {
        new BukkitRunnable() {
            Location loc;
            int timer = ticksAlive;

            @Override
            public void run() {
                if (timer <= 0) {
                    cancel();
                    return;
                } else {
                    timer = timer - period;
                }

                loc = location.clone();
                loc.setPitch(0);

                if (rotated) {
                    loc.setYaw(loc.getYaw() + 30);
                }

                Vector dir = loc.getDirection().setY(0).normalize().multiply(size);
                loc.add(dir);
                loc.setYaw(loc.getYaw() + 60);

                for (int i = 0; i < 6; i++) {
                    loc.setYaw(loc.getYaw() + 60);
                    dir = loc.getDirection().normalize().multiply(size);

                    for (int j = 0; j < dir.length()/precision; j++) {
                        loc.add(dir.clone().normalize().multiply(precision));
                        loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 1, 0, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
                    }
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, period);
    }
}
