package me.maddinoriginal.newkitpvp.listeners;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.data.KitPlayer;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.kits.KitType;
import me.maddinoriginal.newkitpvp.utils.Helper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

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
    public void onTargetChange(EntityTargetLivingEntityEvent e) {
        Entity entity = e.getEntity();
        LivingEntity target = e.getTarget();

        if (target == null) {
            return;
        }

        if (entity.hasMetadata("WolfSummonedBy")) {
            if (target.getUniqueId().toString().equals(entity.getMetadata("WolfSummonedBy").get(0).asString())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player) || !(e.getEntity() instanceof Arrow)) {
            return;
        }

        Player p = (Player) e.getEntity().getShooter();
        KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(p);
        Arrow arrow = (Arrow) e.getEntity();

        if (kp.getKitType().equals(KitType.BOMBERMAN)) {
            arrow.getWorld().createExplosion(arrow.getLocation(), 2.0f, false, true, p);
            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            if (e.getHitEntity() == null) {
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        arrow.remove();
                    }
                }.runTaskLater(NewKitPvP.getInstance(), 3);
            } else {
                e.setCancelled(true);
            }
        }
    }

    /*@EventHandler
    public void onPlayerKnockBack(EntityKnockbackByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();
        KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(p);

        if (kp.getCurrentKit().equals(KitType.BOMBERMAN)) {
            if (e.getCause().equals(EntityKnockbackEvent.KnockbackCause.ENTITY_ATTACK)) {
                System.out.println("" + e.getSourceEntity());
                if (e.getSourceEntity() instanceof LivingEntity) {
                    e.setFinalKnockback(new Vector(0, 2, 0));
                }
            }
        }
    }*/

    @EventHandler
    public void onPrimeExplosion(ExplosionPrimeEvent e) {
        Entity ent = e.getEntity();
        Location loc = e.getEntity().getLocation();

        //Bomberman Kit Grenade
        if (ent instanceof TNTPrimed) {
            //e.setRadius(ent.getMetadata("GrenadePower").get(0).asInt());
            e.setCancelled(true);
            ent.remove();
            try {
                loc.getWorld().createExplosion(loc, ent.getMetadata("GrenadePower").get(0).asFloat(), false,
                        true, Bukkit.getPlayer(ent.getMetadata("GrenadeOwner").get(0).asString()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        List<Block> newBlockList = new ArrayList<>();

        //allow passable blocks to explode
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

        //allow passable blocks to explode
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
