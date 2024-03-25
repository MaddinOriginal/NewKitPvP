package me.maddinoriginal.newkitpvp.listeners;

import me.maddinoriginal.newkitpvp.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiscellaneousListener implements Listener {

    private final Random RANDOM = new Random();

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onVoidDamage(EntityDamageEvent e) {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            e.setDamage(Integer.MAX_VALUE);
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        List<Block> newBlockList = new ArrayList<>();
        for (Block b : e.blockList()) {
            if (b.isPassable()) {
                Helper.resetBlockAfter(b, 40);
                newBlockList.add(b);
            }
        }
        e.blockList().clear();
        for (Block b: newBlockList) {
            e.blockList().add(b);
        }
    }

    @EventHandler
    public void onExplosion(BlockExplodeEvent e) {
        List<Block> newBlockList = new ArrayList<>();
        for (Block b : e.blockList()) {
            if (b.isPassable()) {
                Helper.resetBlockAfter(b, 40);
                newBlockList.add(b);
                if (RANDOM.nextBoolean()) {
                    b.setType(Material.FIRE);
                }
            }
        }
        e.blockList().clear();
        for (Block b: newBlockList) {
            e.blockList().add(b);
        }
    }

    @EventHandler
    public void onFirePlaced(BlockIgniteEvent e) {
        Helper.resetBlockAfter(e.getBlock(), 25 + RANDOM.nextInt(10));
    }

    /*private Material[] matList = Material.values();
    private int pointer = 0;

    @EventHandler
    public void onBlockRotieren(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();

        if (action.equals(Action.LEFT_CLICK_AIR)) {
            pointer++;
        } else if (action.equals(Action.RIGHT_CLICK_AIR)) {
            pointer--;
        }

        p.getInventory().setHelmet(new ItemStack(matList[pointer]));
        p.sendMessage((pointer+1) + "/" + matList.length + " (" + matList[pointer].name() + ")");
    }*/

    /**
     * Small Medium Large Amethyst Bud and Amethyst Cluster
     * Bone
     * Structure, Jigsaw
     * Lightning Rod
     * (Calibrated) Sculk Sensor
     * Slime / Honey Block
     * Scaffolding
     * Corals (alive)
     * Glass (Astronaut)
     * Ice (Yeti?)
     * Monster Spawner / Trial Spawner
     * Chorus plant and fruit
     * End Rod
     * Decorated Pot
     * Big Dripleaf
     * Azalea (Flowering Azalea)
     * Mangrove roots and leafs
     * Magma Block
     * Structure Void
     * Shield, Trident, Leash
     * Crafter
     * Copper Grate
     */
}
