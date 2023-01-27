package me.maddinoriginal.newkitpvp.abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TeleportForwardAbility extends Ability {

    private Set<Material> transparentBlocks = new HashSet<>(Arrays.asList(Material.AIR, Material.GRASS,
            Material.TALL_GRASS, Material.LAVA, Material.WATER, Material.DANDELION, Material.POPPY,
            Material.OAK_LEAVES, Material.BIRCH_LEAVES, Material.SPRUCE_LEAVES, Material.JUNGLE_LEAVES,
            Material.ACACIA_LEAVES, Material.DARK_OAK_LEAVES, Material.AZALEA_LEAVES,
            Material.FLOWERING_AZALEA_LEAVES, Material.MANGROVE_LEAVES, Material.COBWEB));

    @Override
    public void useAbility(Player p) {
        double dist;
        double subtract = Math.abs(p.getLocation().getPitch())/15;
        float yaw = p.getLocation().getYaw();
        float pitch = p.getLocation().getPitch();

        Block targetBlock = null;

        if (p.isSneaking()) {
            dist = 14 - (Math.pow((subtract + 1), 0.65d) * 4 - 4);
            if (yaw <= 0) { yaw += 180; } else { yaw -= 180; }
        } else {
            dist = 22 - (Math.pow(subtract, 0.65d) * 4);
        }

        //PREPARING TELEPORTATION TO LOCATION OR TO THE NEARBY TARGET BLOCK
        try {
            targetBlock = p.getTargetBlock(transparentBlocks, (int) Math.ceil(dist));
        } catch (IllegalStateException e) {
            System.out.println("[KitPvP] " + p.getDisplayName() + " tried teleporting inside block with Beamer Kit.");
            System.out.println("=> Not printing the Stacktrace because it happens sometimes.");
        }

        Location targetLoc;
        if (targetBlock != null) {
            targetLoc = targetBlock.getLocation();
            targetLoc.setX(targetLoc.getX()+0.5);
            targetLoc.setZ(targetLoc.getZ()+0.5);
        } else {
            targetLoc = p.getLocation();
            targetLoc.add(p.getLocation().getDirection().normalize().multiply(dist));
        }
        targetLoc.setYaw(yaw);
        targetLoc.setPitch(pitch);
        p.teleport(targetLoc);
        while (p.getLocation().getBlock().getType().isSolid() || p.getLocation().getBlock().getRelative(BlockFace.UP).getType().isSolid()) {
            Location oneUp = p.getLocation().add(0, 1, 0);
            p.teleport(oneUp);
        }
    }

    @Override
    String getName() {
        return "Tele-Beam";
    }

    @Override
    String getDescription() {
        return null;
    }

    @Override
    int getCooldown() {
        return 50;
    }
}
