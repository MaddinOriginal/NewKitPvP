package me.maddinoriginal.newkitpvp.listeners;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.data.KitPlayer;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.kits.KitType;
import me.maddinoriginal.newkitpvp.utils.Helper;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.stream.Collectors;

public class SneakSprintListener implements Listener {

    @EventHandler
    public void onBlockPhysicsUpdate(BlockPhysicsEvent e) {
        //hunter bush plant ability
        if (e.getBlock().getType().equals(Material.SWEET_BERRY_BUSH)) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onFallingBlockForm(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof FallingBlock && e.getEntity().isValid()) {
            Bukkit.broadcastMessage("Falling Block Event!");
            e.setCancelled(true);
            e.getEntity().setTicksLived(1);
        }
    }

    private final double BLASTER_SNEAK_RADIUS_WIDTH = 3.1;
    private final double BLASTER_SNEAK_RADIUS_HEIGHT = 2.6;
    private final int BLASTER_SNEAK_COOLDOWN_TICKS = 30;
    private boolean blasterSneakOnCooldown = false;

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(p);

        //Ghost Kit Invisibility Ability
        if (kp.getCurrentKit().equals(KitType.GHOST)) {
            if (!p.isSneaking()) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, -1, 0));
            } else {
                p.removePotionEffect(PotionEffectType.INVISIBILITY);
            }
        }

        //Blaster Kit Sneak Ability
        else if (kp.getCurrentKit().equals(KitType.BLASTER)) {
            //return if on cooldown or player stopped sneaking instead of starting to sneak
            if (blasterSneakOnCooldown || p.isSneaking()) {
                return;
            }

            blasterSneakOnCooldown = true;

            //Particles + Sound Effects
            p.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, p.getLocation().getX(), p.getLocation().getY() + 0.5, p.getLocation().getZ(), 24, 0.25, 1, 0.25, 0.05);
            p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation().getX(), p.getLocation().getY() + 0.5, p.getLocation().getZ(), 24, 0.25, 0.1, 0.25, 0.25);
            for (int i = 0; i < 24; i++) {
                p.getWorld().spawnParticle(Particle.SPELL_MOB, p.getLocation(), 0, 0.9, 0.9, 0.9, 1);
            }
            p.getWorld().playSound(p, Sound.BLOCK_LAVA_EXTINGUISH, 1.0f, 1.0f);

            Vector v = p.getEyeLocation().getDirection();
            v.setY(0);
            p.setVelocity(new Vector(0, 1.5, 0).add(v.normalize()).normalize().multiply(1.15));

            List<LivingEntity> nearbyLivingEntities = p.getNearbyEntities(BLASTER_SNEAK_RADIUS_WIDTH, BLASTER_SNEAK_RADIUS_HEIGHT, BLASTER_SNEAK_RADIUS_WIDTH)
                    .stream().filter(ent -> ent instanceof LivingEntity).map(ent -> (LivingEntity) ent).collect(Collectors.toList());

            for (LivingEntity livEnt : nearbyLivingEntities) {
                Location entLoc = livEnt.getLocation();
                Location pLoc = p.getLocation();
                entLoc.setY(pLoc.getY() + 0.56);

                livEnt.setVelocity(entLoc.toVector().subtract(pLoc.toVector()).normalize().multiply(1.66));
            }

            new BukkitRunnable() {

                @Override
                public void run() {
                    blasterSneakOnCooldown = false;
                }
            }.runTaskLater(NewKitPvP.getInstance(), BLASTER_SNEAK_COOLDOWN_TICKS);
        }
    }

    @EventHandler
    public void onSprintEvent(PlayerToggleSprintEvent e) {
        Player p = e.getPlayer();
        KitPlayer kp = KitPlayerManager.getInstance().getKitPlayer(p);

        if (kp.getCurrentKit().equals(KitType.PYRO)) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    if (p.isSprinting()) {
                        Block b = p.getLocation().getBlock();

                        //only if block is passable (fully non-collidable in every state)
                        if (b.isPassable() && b.canPlace(Material.FIRE.createBlockData()) && b.getRelative(BlockFace.DOWN).getType().isSolid()) {
                            Helper.resetBlockAfter(b, 42);
                            p.getLocation().getBlock().setType(Material.FIRE);
                        }
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(NewKitPvP.getInstance(), 0, 4);
        }
    }

    private void perform(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*10, 0, false, false, true));
        for (Entity ent : p.getNearbyEntities(10, 10, 10)) {
            if (ent instanceof Golem) {
                ent.playEffect(EntityEffect.LOVE_HEARTS);
            }
        }
    }

    private void perform2(Player p) {
        Bat ent = (Bat) p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.BAT);
        p.addPassenger(ent);

        for (int i = 0; i < 20; i++) {
            Bat entX = (Bat) p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.BAT);

            new BukkitRunnable() {

                @Override
                public void run() {
                    if (entX.isDead()) {
                        Bukkit.getScheduler().cancelTask(getTaskId());
                    }
                    if (entX.getLocation().distance(ent.getLocation()) > 2) {
                        entX.setVelocity(ent.getLocation().toVector().subtract(entX.getLocation().toVector()).normalize().multiply(2));
                    }
                }
            }.runTaskTimer(NewKitPvP.getInstance(), 0, 3);
        }
    }

    private void perform3(Player p) {
        //p.getInventory().addItem(new PlantBushAbilityItem().getItem());

        BlockData blockData = Bukkit.createBlockData(Material.SANDSTONE);
        FallingBlock fallingBlock = p.getWorld().spawnFallingBlock(p.getEyeLocation(), blockData);

        ArmorStand armorStand = (ArmorStand) p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.ARMOR_STAND);
        //armorStand.setVisible(false);
        armorStand.addPassenger(fallingBlock);
        armorStand.setVelocity(p.getEyeLocation().getDirection());
        armorStand.setSmall(true);

        new BukkitRunnable() {
            @Override
            public void run() {
                fallingBlock.remove();
                armorStand.remove();
            }
        }.runTaskLater(NewKitPvP.getInstance(), 20*2);
    }

    private void perform4(Player p) {
        BlockData blockData = Bukkit.createBlockData(Material.SANDSTONE);
        FallingBlock fallingBlock = p.getWorld().spawnFallingBlock(p.getEyeLocation(), blockData);

        fallingBlock.setVelocity(p.getEyeLocation().getDirection());

        new BukkitRunnable() {
            @Override
            public void run() {
                fallingBlock.remove();
            }
        }.runTaskLater(NewKitPvP.getInstance(), 20*7);
    }

    private void perform5(Player p) {
        //p.getInventory().addItem(new WolfHuntAbilityItem().getItem());
        //p.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, p.getLocation().add(0, 1, 0), 64, 1.3, 1.2, 1.3, 0.01);
        Snowball snowball = p.launchProjectile(Snowball.class);
        snowball.setItem(new ItemStack(Material.LEAD));
    }
}
