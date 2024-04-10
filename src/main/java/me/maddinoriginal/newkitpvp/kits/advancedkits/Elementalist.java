package me.maddinoriginal.newkitpvp.kits.advancedkits;

import com.google.common.base.Strings;
import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.items.ThrowNegativePotionAbilityItem;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitCategory;
import me.maddinoriginal.newkitpvp.utils.ItemBuilder;
import me.maddinoriginal.newkitpvp.utils.PlayStyle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * Type=
 * Armor=
 *
 * Strong against=
 * Weak against=
 *
 * Active Ability=
 * Supportive Passive=
 * Survivability Passive= Immune to (most) negative potion effects
 * Finisher Passive=
 */

public class Elementalist extends Kit {

    @Override
    public String getName() {
        return "Elementalist";
    }

    @Override
    public String getTag() {
        return "[ELEM]";
    }

    @Override
    public Material getMaterial() {
        return Material.ENDER_EYE;
    }

    @Override
    public KitCategory getCategory() {
        return KitCategory.ADVANCED;
    }

    @Override
    public PlayStyle[] getPlayStyles() {
        return new PlayStyle[]{PlayStyle.TANK, PlayStyle.CONTROL};
    }

    @Override
    public String getDescription() {
        return "Master of potions and effects. Applies negative effects to their enemies while being immune to them.";
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armor = new ItemStack[4];

        //boots
        armor[0] = new ItemBuilder(Material.LEATHER_BOOTS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Boots")

                .setLeatherArmorColor(245, 105, 105, true)

                .setArmorTrim(TrimMaterial.LAPIS, TrimPattern.SILENCE)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //leggings
        armor[1] = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setDisplayName(ChatColor.YELLOW + getName() + " Leggings")

                .setLeatherArmorColor(153, 153, 0, true)

                .setArmorTrim(TrimMaterial.AMETHYST, TrimPattern.RIB)
                .hideArmorTrim()

                .addEnchantment(Enchantment.PROTECTION_PROJECTILE, 3, false)

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //chestplate
        armor[2] = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setDisplayName(ChatColor.YELLOW + getName() + " Chestplate")

                .setLeatherArmorColor(102, 153, 0, true)

                .setArmorTrim(TrimMaterial.LAPIS, TrimPattern.RIB)
                .hideArmorTrim()

                .setUnbreakable(true, true)
                .hideAttributes()
                .build();

        //helmet
        armor[3] = new ItemBuilder(Material.LEATHER_HELMET)
                .setDisplayName(ChatColor.YELLOW + getName() + " Helmet")

                .setLeatherArmorColor(51, 0, 51, true)

                .setArmorTrim(TrimMaterial.AMETHYST, TrimPattern.SILENCE)
                .hideArmorTrim()

                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false)

                .setUnbreakable(true,true )
                .hideAttributes()
                .build();

        return armor;
    }

    @Override
    public ItemStack[] getKitItems() {
        ItemStack[] items = new ItemStack[2];

        items[0] = new ItemBuilder(Material.STONE_SWORD)
                .setDisplayName(ChatColor.YELLOW + getName() + " Sword")
                .setLore(ChatColor.RESET + "" + ChatColor.DARK_GRAY + Strings.repeat('\u2594' + "", 16),
                        ChatColor.GRAY + "Toxic")

                .setUnbreakable(true, true)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        items[1] = new ThrowNegativePotionAbilityItem().getItem();

        return items;
    }

    private Random random = new Random();
    private Map<Player, Integer> effectsRunnables = new HashMap<>();
    private List<PotionEffect> effects = new ArrayList<>(Arrays.asList(
            PotionEffectType.JUMP.createEffect(160, 0),
            PotionEffectType.REGENERATION.createEffect(160, 0),
            PotionEffectType.FIRE_RESISTANCE.createEffect(160, 0),
            PotionEffectType.DAMAGE_RESISTANCE.createEffect(160, 0),
            PotionEffectType.ABSORPTION.createEffect(160, 0),
            PotionEffectType.SPEED.createEffect(160, 0),
            PotionEffectType.LUCK.createEffect(160, 0)
    ));

    public void runEffects(Player p) {
        if (effectsRunnables.containsKey(p)) {
            return;
        }

        BukkitTask runnable = new BukkitRunnable() {

            @Override
            public void run() {
                p.addPotionEffect(effects.get(random.nextInt(effects.size())));
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 20, 300);

        effectsRunnables.put(p, runnable.getTaskId());
    }

    public void cancelEffects(Player p) {
        if (effectsRunnables.containsKey(p)) {
            Bukkit.getScheduler().cancelTask(effectsRunnables.get(p));
            effectsRunnables.remove(p);
        }
    }
}
