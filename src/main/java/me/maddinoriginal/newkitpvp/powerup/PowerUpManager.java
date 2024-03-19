package me.maddinoriginal.newkitpvp.powerup;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.AbilityType;
import me.maddinoriginal.newkitpvp.data.KitPlayer;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerUpManager {

    private List<Location> powerupLocations = new ArrayList<>();
    private List<ArmorStand> armorstands = new ArrayList<>();
    World world = Bukkit.getWorlds().get(0); //TODO
    Random random = new Random();
    private NamespacedKey key = new NamespacedKey(NewKitPvP.getInstance(), "powerup");

    /**
     * Singleton handling
     */

    private PowerUpManager() {
        setup();
    }

    private static class SingletonHelper {
        private static final PowerUpManager INSTANCE = new PowerUpManager();
    }

    public static PowerUpManager getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * actual class
     */

    private void setup() {
        initializeLocations();
        initializeArmorStands();

        startPowerupTimer();
        startActivePowerupTimer();
    }

    private void initializeLocations() {
        powerupLocations.add(new Location(world, 47.5, -19.0 -0.55, -50.5));
        powerupLocations.add(new Location(world, -42.5, -26.0 -0.55, 29.5));
        powerupLocations.add(new Location(world, -8.5, -26.0 -0.55, -65.5));
        powerupLocations.add(new Location(world, 27.5, -26.0 -0.55, 65.5));
        powerupLocations.add(new Location(world, -50.5, -29.0 -0.55, -28.5));
    }

    private void initializeArmorStands() {
        for (Location loc : powerupLocations) {
            ArmorStand armorstand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            armorstand.setGravity(false);
            armorstand.setVisible(false);
            //armorstand.setMarker(true);
            armorstands.add(armorstand);

            List<Entity> removeList = armorstand.getNearbyEntities(2.2, 3.6, 2.2);
            for (Entity ent : removeList) {
                if (ent instanceof ArmorStand) {
                    ArmorStand stand = (ArmorStand) ent;
                    if (!stand.getPassengers().isEmpty()) {
                        for (Entity passenger : stand.getPassengers()) {
                            passenger.remove();
                        }
                    }
                    ent.remove();
                }
            }
        }
    }

    private void startActivePowerupTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (ArmorStand armorStand : armorstands) {
                    if (!armorStand.getPassengers().isEmpty()) {
                        Location loc = armorStand.getLocation();
                        loc.setY(loc.getY() + 0.6);
                        armorStand.getWorld().spawnParticle(Particle.SPELL_MOB_AMBIENT, loc, 10);
                    }
                }
            }
        }.runTaskTimerAsynchronously(NewKitPvP.getInstance(), 0, 10);
    }

    private void startPowerupTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                trySpawnNewPowerup();
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 20*10, 20*25);
    }

    private void trySpawnNewPowerup() {
        List<ArmorStand> inactive = new ArrayList<>();
        for (ArmorStand armorstand : armorstands) {
            if (armorstand.getPassengers().isEmpty()) {
                inactive.add(armorstand);
            }
        }
        if (!inactive.isEmpty()) {
            startAnimation(inactive.get(random.nextInt(inactive.size())), PowerUpType.values()[random.nextInt(PowerUpType.values().length)]);
        } else {
            //Bukkit.broadcastMessage("All Powerup Locations already contain a powerup -.-");
        }
    }

    private void startAnimation(ArmorStand armorStand, PowerUpType type) {
        Location startLocation = armorStand.getLocation();
        int startHeight = 40;

        startLocation.setY(startLocation.getY() + startHeight + 2);

        new BukkitRunnable() {
            int i = startHeight;
            Location loc = startLocation;

            @Override
            public void run() {
                if (i > 0) {
                    loc.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc, 8, 0.3, 0.2, 0.3, 0.01);
                    loc.setY(loc.getY() - 1);
                    i--;
                } else {
                    loc.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc, 16);
                    loc.setY(loc.getY() - 2);
                    loc.getWorld().spawnParticle(Particle.SPELL_MOB_AMBIENT, loc, 24, 0.3, 0, 0.3);
                    loc.getWorld().playSound(loc, Sound.ENTITY_WITHER_SPAWN, 0.5f, 0.5f);
                    spawnPowerUp(armorStand, type);
                    Bukkit.getScheduler().cancelTask(getTaskId());
                }
            }
        }.runTaskTimer(NewKitPvP.getInstance(), 0, 4);
    }

    private void spawnPowerUp(ArmorStand armorStand, PowerUpType type) {
        ItemStack item = new ItemStack(type.getMaterial());
        item.getItemMeta().getPersistentDataContainer().set(key, PersistentDataType.STRING, type.toString());
        Entity powerup = world.dropItemNaturally(armorStand.getLocation(), item);
        powerup.getPersistentDataContainer().set(key, PersistentDataType.STRING, type.toString());
        armorStand.addPassenger(powerup);
    }

    public void activatePowerup(Player p, PowerUpType type) {
        KitPlayer kitPlayer = KitPlayerManager.getInstance().getKitPlayer(p);
        switch (type) {
            case SPEED:
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*20, 1));
                break;
            case REGEN:
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*16, 0));
                break;
            case STRENGTH:
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*12, 0));
                break;
            case FORCEFIELD:
                AbilityType.FORCE_FIELD.getAbility().useAbility(p);
                break;
            case LIGHTNINGFIELD:
                AbilityType.LIGHTNING_FIELD.getAbility().useAbility(p);
                break;
            case COPPERCANNON:
                AbilityType.COPPER_CANNON.getAbility().useAbility(p);
                break;
            case COINS:
                kitPlayer.getPlayerData().getCoins().increaseAmount(50);
                break;
            case TOKENS:
                kitPlayer.getPlayerData().getTokens().increaseAmount(20);
                break;
            default:
                System.out.println("[ERROR] Unknown Powerup Type.");
                break;
        }
        p.sendMessage(ChatColor.YELLOW + "PowerUp activated: " + ChatColor.AQUA + type.getName());
    }

    public NamespacedKey getKey() {
        return key;
    }
}
