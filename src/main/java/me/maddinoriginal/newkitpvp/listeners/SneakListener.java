package me.maddinoriginal.newkitpvp.listeners;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.items.WolfHuntAbilityItem;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class SneakListener implements Listener {

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

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();

        if (p.isSneaking()) {
            return;
        }

        perform5(p);
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
    }
}
