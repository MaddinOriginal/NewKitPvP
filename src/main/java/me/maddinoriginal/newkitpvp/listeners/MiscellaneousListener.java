package me.maddinoriginal.newkitpvp.listeners;

import com.google.common.collect.Sets;
import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.data.KitPlayer;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.kits.KitType;
import me.maddinoriginal.newkitpvp.utils.Helper;
import net.minecraft.world.entity.boss.EntityComplexPart;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

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

        if (entity.hasMetadata("SummonedBy")) {
            if (target.getUniqueId().toString().equals(entity.getMetadata("SummonedBy").get(0).asString())) {
                e.setCancelled(true);
            }
        }

        else if (entity instanceof Creeper || entity instanceof EnderDragon) {
            e.setCancelled(true);
        }
    }

    //Firework Rocket flight with Elytra
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        Entity ent = e.getEntity();

        if (ent instanceof Firework) {
            Firework fw = (Firework) ent;

            new BukkitRunnable() {
                @Override
                public void run() {
                    fw.detonate();
                }
            }.runTaskLater(NewKitPvP.getInstance(), 3);
        }
    }

    private Set<PotionEffectType> negativePotionEffects = Sets.newHashSet(PotionEffectType.BAD_OMEN,
            PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.DARKNESS, PotionEffectType.HARM,
            PotionEffectType.HUNGER, PotionEffectType.POISON, PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING,
            PotionEffectType.UNLUCK, PotionEffectType.WEAKNESS, PotionEffectType.WITHER);

    public void onPotionEffect(EntityPotionEffectEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();
        KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(p);

        //Elementalist Kit immune to negative potion effects
        if (kp.getKitType().equals(KitType.ELEMENTALIST)) {
            if (negativePotionEffects.contains(e.getNewEffect().getType())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCreeperIgnite(EntityExplodeEvent e) {
        if (e.getEntity() instanceof Creeper) {
            e.setCancelled(true);
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

        //No damage ticks from arrows from hunters
        else if (kp.getKitType().equals(KitType.HUNTER)) {
            if (e.getHitEntity() == null && e.getHitEntity() instanceof HumanEntity || e.getHitEntity() instanceof Creature) {
                ((LivingEntity) e.getHitEntity()).setNoDamageTicks(0);
            }
        }
    }

    @EventHandler
    public void onAxeThrownHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) e.getEntity();

            if (!snowball.getPassengers().stream().filter(ent -> ent instanceof ItemDisplay).collect(Collectors.toList()).isEmpty()) {
                ItemDisplay axeDisplay = (ItemDisplay) snowball.getPassengers().stream().filter(ent -> ent instanceof ItemDisplay).findFirst().get();

                if (e.getHitBlock() != null) {
                    axeDisplay.leaveVehicle();

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            axeRemoval(axeDisplay);
                        }
                    }.runTaskLater(NewKitPvP.getInstance(), 110);
                }

                else if (e.getHitEntity() != null) {
                    if (e.getHitEntity() instanceof LivingEntity) {
                        LivingEntity hit = (LivingEntity) e.getHitEntity();
                        hit.damage(8.0, snowball);
                        hit.addPotionEffect(PotionEffectType.SLOW.createEffect(100, 2));
                    }
                    axeRemoval(axeDisplay);
                }

                snowball.remove();
            }
        }
    }

    private void axeRemoval(ItemDisplay axeDisplay) {
        axeDisplay.getWorld().spawnParticle(Particle.ITEM_CRACK, axeDisplay.getLocation(), 12, 0.3, 0.3, 0.3, 0, new ItemStack(Material.IRON_AXE));
        axeDisplay.getWorld().playSound(axeDisplay.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.8f, 1.0f);
        axeDisplay.remove();
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
            if(ent.hasMetadata("GrenadePower")) {
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
    }

    //Prevent EnderDragon break blocks
    @EventHandler
    public void stopDragonDamage(EntityExplodeEvent e) {
        Entity ent = e.getEntity();
        if(ent instanceof EnderDragon) {
            for (Block b : e.blockList()) {
                Helper.resetBlockAfter(b, 30);
            }
            //e.blockList().clear();
        }
    }

    //Entity Explosions except ender dragon
    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        if (e.getEntity() instanceof EnderDragon) {
            return;
        }

        List<Block> newBlockList = new ArrayList<>();

        //allow passable blocks to explode
        for (Block b : e.blockList()) {
            if (b.isPassable()) {
                Helper.resetBlockAfter(b, 40);
                newBlockList.add(b);
            }
        }
        e.blockList().clear();
        for (Block b : newBlockList) {
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
                    b.setType(Material.FIRE, false);
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
