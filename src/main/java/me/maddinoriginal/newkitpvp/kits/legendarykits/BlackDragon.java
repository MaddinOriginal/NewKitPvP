package me.maddinoriginal.newkitpvp.kits.legendarykits;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.items.DragonAbilityItem;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.Helper;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import me.maddinoriginal.newkitpvp.utils.PlayStyle;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BlackDragon extends Kit {

    @Override
    public String getName() {
        return "Black Dragon";
    }

    @Override
    public String getTag() {
        return "[DRAG]";
    }

    @Override
    public Material getMaterial() {
        return Material.DRAGON_HEAD;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.LEGENDARY;
    }

    @Override
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[]{PlayStyle.KNOCKBACK, PlayStyle.HEAL, PlayStyle.MOBILITY, PlayStyle.DAMAGE};
    }

    @Override
    public String getDescription() {
        return "Turns into a black dragon, hence the name of this kit.";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //Create boots and add them to the returning (armor contents) ItemStack
        armor[0] = new ItemBuilder(Material.NETHERITE_BOOTS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Feet")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                .build();

        //Create leggings and add them to the returning (armor contents) ItemStack
        armor[1] = new ItemBuilder(Material.NETHERITE_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Legs")

                .addEnchantment(Enchantment.PROTECTION_FIRE, 8, true)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                .build();

        //Create chest plate and add them to the returning (armor contents) ItemStack
        armor[2] = new ItemBuilder(Material.ELYTRA)
                .setDisplayName(ChatColor.YELLOW + getName() + " Wings")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                .build();

        //Create helmet and add them to the returning (armor contents) ItemStack
        armor[3] = new ItemBuilder(Material.DRAGON_HEAD)
                .setDisplayName(ChatColor.YELLOW + getName() + " Head")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_UNBREAKABLE)
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[9];

        items[0] = new ItemBuilder(Material.NETHERITE_AXE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Claw")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new DragonAbilityItem().getItem();

        items[2] = new ItemBuilder(Material.FIREWORK_ROCKET)
                .setAmount(1)
                .setDisplayName(ChatColor.YELLOW + "Flight boost")
                .build();

        items[3] = null;
        items[4] = null;
        items[5] = null;
        items[6] = null;
        items[7] = null;
        items[8] = null;

        return items;
    }

    public void summonCrystal(Player player) {
        Location loc = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();

        if (!loc.getBlock().isPassable()) {
            Helper.resetBlockAfter(loc.getBlock(), 100);
            loc.getBlock().setType(Material.OBSIDIAN);
        }

        Location crystalLoc = loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).getLocation().add(new Vector(0.5, 0, 0.5));
        EnderCrystal crystal = player.getWorld().spawn(crystalLoc, EnderCrystal.class, ent -> {
            ent.setShowingBottom(true);
            ent.setBeamTarget(player.getLocation());
        });

        List<LivingEntity> nearby = crystal.getNearbyEntities(4, 3, 4).stream().filter(entity -> entity instanceof LivingEntity)
                .filter(Predicate.not(entity -> entity instanceof ArmorStand)).filter(Predicate.not(entity -> entity.equals(player)))
                .map(entity -> (LivingEntity) entity).collect(Collectors.toList());

        Vector addV = new Vector(0, 1, 0);
        for (LivingEntity ent : nearby) {
            ent.setVelocity(ent.getLocation().toVector().subtract(crystal.getLocation().toVector()).normalize().add(addV).multiply(1.5));
        }

        new BukkitRunnable() {
            int timer = 100;

            @Override
            public void run() {
                if (crystal.isDead()) {
                    cancel();
                    return;
                }

                if (timer > 0) {
                    crystal.setBeamTarget(player.getLocation());
                    if (timer % 10 == 0) {
                        try {
                            player.setHealth(player.getHealth() + 4);
                            player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(new Vector(0, 2, 0)), 7, 0.25, 0, 0.25);
                            player.playSound(player, Sound.ENTITY_WARDEN_HEARTBEAT, 1.0f, 1.0f);
                        } catch (Exception ex) {
                            if (player.getHealth() < 20) {
                                player.setHealth(20);
                                player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(new Vector(0, 2, 0)), 7, 0.25, 0, 0.25);
                                player.playSound(player, Sound.ENTITY_WARDEN_HEARTBEAT, 1.0f, 1.0f);
                            }
                            crystal.remove();
                        }
                    }
                    timer--;
                } else {
                    crystal.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, crystal.getLocation(), 8, 0.5, 0.5, 0.5, 0);
                    crystal.remove();
                    cancel();
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 3, 3);
    }
}
