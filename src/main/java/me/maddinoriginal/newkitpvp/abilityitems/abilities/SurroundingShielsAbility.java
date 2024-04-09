package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import me.maddinoriginal.newkitpvp.utils.Helper;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.*;

public class SurroundingShielsAbility extends Ability implements Listener {

    private static final Map<Player, List<ItemDisplay>> shieldedPlayers = new HashMap<>();

    @Override
    public String getName() {
        return "Surrounding Shields";
    }

    @Override
    public String getDescription() {
        return "Surrounds the player with shield floating around them to protect them from incoming attacks.";
    }

    @Override
    public int getCooldown() {
        return 260;
    }

    @Override
    public boolean useAbility(Player player) {
        if (shieldedPlayers.containsKey(player)) {
            return false;
        }

        Material material = Material.DIAMOND_CHESTPLATE;
        List<ItemDisplay> shields = new ArrayList<>();
        int amount = 3;
        float heightAdjust = -0.75f;

        for (int i = 0; i < amount; i++) {
            ItemDisplay display = player.getWorld().spawn(player.getLocation(), ItemDisplay.class, ent -> {
                ent.setItemStack(new ItemStack(material));
                ent.setTransformation(new Transformation(
                        new Vector3f(0, heightAdjust, 0),
                        new AxisAngle4f(0, 0, 0, 0),
                        new Vector3f(1, 1, 1),
                        new AxisAngle4f(0, 0, 0, 0)));
                player.addPassenger(ent);
            });

            shields.add(display);
        }

        new BukkitRunnable() {
            float time = 0f;

            @Override
            public void run() {
                time = time + 0.05f;

                for (int i = 0; i < shields.size(); i++) {
                    Helper.rotateDisplayPassenger(shields.get(i), time + ((float) i /shields.size()));
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 1, 1);

        shieldedPlayers.put(player, shields);
        return true;
    }

    @EventHandler
    public void onDamageWhenShielded(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();

        if (shieldedPlayers.containsKey(p)) {
            if (shieldedPlayers.get(p).isEmpty()) {
                shieldedPlayers.remove(p);
            }
            else {
                shieldedPlayers.get(p).get(0).remove();
                shieldedPlayers.get(p).remove(0);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                p.setNoDamageTicks(10);
                e.setCancelled(true);
            }
        }
    }
}
