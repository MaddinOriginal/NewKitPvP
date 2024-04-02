package me.maddinoriginal.newkitpvp.kits.advancedkits;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.items.WolfHuntAbilityItem;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import me.maddinoriginal.newkitpvp.utils.PlayStyle;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Type= Hybrid Kit with a crossbow and a knife
 * Armor= Mostly green-ish leather armor with some brown tones
 *
 * Strong against= Archer, Tank
 * Weak against= Swordsman, Barbarian
 *
 * Active Ability= Shoot (slightly) homing arrows for X seconds
 * Supportive Passive= Hitting enemies with arrows won't give them no invincibility frames
 * Survivability Passive= Hitting an enemy with an arrow 3 times summons a Wolf with speed effect that hunts that enemy
 * Finisher Passive= Killing enemies gives back the amount of arrows shot since the last kill (max 7 arrows)
 */

public class Hunter extends Kit {

    //Hunter Passive to summon a Wolf
    private final double WOLF_HEALTH = 3.0;
    private final int LIFESPAN_TICKS = 7 * 20;
    private List<Wolf> wolfs = new ArrayList<>();

    @Override
    public String getName() {
        return "Hunter";
    }

    @Override
    public String getTag() {
        return "[HUNT]";
    }

    @Override
    public Material getMaterial() {
        return Material.BONE;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.ADVANCED;
    }

    @Override
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[]{PlayStyle.DAMAGE, PlayStyle.RANGED};
    }

    @Override
    public String getDescription() {
        return "On a hunt with its crossbow. Can shoot homing arrows and summon wolfs to hunt their prey with them.";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //boots
        armor[0] = new ItemBuilder(Material.LEATHER_BOOTS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Boots")

                .setLeatherArmorColor(150, 150, 60, true)

                .setArmorTrim(TrimMaterial.EMERALD, TrimPattern.RIB)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //leggings
        armor[1] = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Leggings")

                .setLeatherArmorColor(0, 120, 20, true)

                .setArmorTrim(TrimMaterial.EMERALD, TrimPattern.TIDE)
                .hideArmorTrim()

                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3, false)

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //chestplate
        armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Chestplate")

                .setLeatherArmorColor(25, 180, 20, true)

                .setArmorTrim(TrimMaterial.EMERALD, TrimPattern.SNOUT)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //helmet
        armor[3] = new ItemBuilder(Material.LEATHER_HELMET)
                .setDisplayName(ChatColor.YELLOW + getName() + " Helmet")

                .setLeatherArmorColor(90, 120, 60, true)

                .setArmorTrim(TrimMaterial.EMERALD, TrimPattern.RIB)
                .hideArmorTrim()

                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false)

                .setUnbreakable(true,true )
                .hideAttributes()
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[9];

        items[0] = new ItemBuilder(Material.CROSSBOW)
                .setDisplayName(ChatColor.YELLOW + getName() + " Crossbow")

                .addEnchantment(Enchantment.MULTISHOT, 1, false)

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new WolfHuntAbilityItem().getItem();

        items[2] = null;
        items[3] = null;

        items[4] = new ItemBuilder(Material.RABBIT_FOOT)
                .setDisplayName(ChatColor.YELLOW + "Lucky Charm")
                .addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3, true)
                .build();

        items[5] = null;
        items[6] = null;

        items[7] = new ItemBuilder(Material.ARROW)
                .setAmount(21)
                .setDisplayName(ChatColor.YELLOW + getName() + " Arrow")
                .build();

        items[8] = null;

        return items;
    }

    /**
     * Summon Wolf passive ability (when hitting enemy with arrow 3 times)
     * @param player The player with the kit who activates the ability
     * @param target The other player that gets targeted by the wolf
     */
    public void summonWolf(Player player, LivingEntity target) {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WOLF_HOWL, 0.5f, 1.0f);

        Wolf wolf = player.getWorld().spawn(player.getLocation(), Wolf.class, c -> {
            //c.setTamed(true);
            //c.setCollarColor(DyeColor.values()[random.nextInt(DyeColor.values().length)]);
            //c.setOwner(player);
            c.setAngry(true);
            c.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 4));
            c.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
            c.setHealth(WOLF_HEALTH);
            c.setMetadata("WolfSummonedBy", new FixedMetadataValue(NewKitPvP.getInstance(), player.getUniqueId()));
            c.setTarget(target);
        });
        wolfs.add(wolf);

        //remove wolfs after lifespan
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!wolf.isDead()) {
                    removeWolf(wolf);
                }
            }
        }.runTaskLater(NewKitPvP.getInstance(), LIFESPAN_TICKS);
    }

    private void removeWolf(Wolf wolf) {
        Location loc = wolf.getLocation();
        wolfs.remove(wolf);

        wolf.remove();
        loc.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc.add(0, 0.4, 0), 8, 0.3, 0.2, 0.3, 0.01);
        loc.getWorld().playSound(loc, Sound.ENTITY_WOLF_DEATH, 1.0f, 1.0f);
    }

    public List<Wolf> getWolfs() {
        return wolfs;
    }
}
