package me.maddinoriginal.newkitpvp;

import me.kodysimpson.simpapi.menu.MenuManager;
import me.maddinoriginal.newkitpvp.abilities.items.*;
import me.maddinoriginal.newkitpvp.commands.KitPvPCommand;
import me.maddinoriginal.newkitpvp.configuration.PlayerdataConfig;
import me.maddinoriginal.newkitpvp.listeners.ChatListener;
import me.maddinoriginal.newkitpvp.listeners.ConnectionListener;
import me.maddinoriginal.newkitpvp.listeners.InteractListener;
import me.maddinoriginal.newkitpvp.listeners.custom.KitBuyListener;
import me.maddinoriginal.newkitpvp.listeners.custom.KitSelectListener;
import me.maddinoriginal.newkitpvp.utils.KitPlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class NewKitPvP extends JavaPlugin {

    private static NewKitPvP instance; //instance of this class
    private final PluginManager pm = Bukkit.getPluginManager();
    private static String prefix;

    public static NamespacedKey abilityItemKey;
    public static Map<String, AbilityItem> abilityItems = new HashMap<>();

    @Override
    public void onEnable() {
        System.out.println("[KitPvP] starting the plugin...");

        instance = this;

        //create both the data folder and config.yml file if either not yet created and copy/paste the default values
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        PlayerdataConfig.setup();
        PlayerdataConfig.get().options().copyDefaults(true);
        PlayerdataConfig.save();

        registerCommands();
        registerListeners();
        registerCustomListeners();

        MenuManager.setup(this.getServer(), this);

        setGameRules();
        prepareWorld();

        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix"));

        abilityItemKey = new NamespacedKey(this, "ability-item-key");

        registerItems(new DashAbilityItem(), new PlantBushAbilityItem(), new TeleportForwardAbilityItem(), new MagmaLauncherAbilityItem());

        System.out.println("[KitPvP] plugin started.");
    }

    @Override
    public void onDisable() {
        System.out.println("[KitPvP] disabling the plugin...");

        for (Player p : Bukkit.getOnlinePlayers()) {
            KitPlayerManager.getInstance().getKitPlayer(p).getData().save();
            p.kickPlayer("Server is reloading. You can rejoin in just a second!");
        }

        System.out.println("[KitPvP] plugin disabled.");
    }

    public static NewKitPvP getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static NamespacedKey getAbilityItemKey() {
        return abilityItemKey;
    }

    private void registerItems(AbilityItem... items) {
        Arrays.asList(items).forEach(ai-> abilityItems.put(ai.getId(), ai));
    }

    private void registerCommands() {
        getCommand("kitpvp").setExecutor(new KitPvPCommand());
    }

    private void registerListeners() {
        pm.registerEvents(new ChatListener(), this);
        pm.registerEvents(new ConnectionListener(), this);
        pm.registerEvents(new InteractListener(), this);
    }

    private void registerCustomListeners() {
        pm.registerEvents(new KitBuyListener(), this);
        pm.registerEvents(new KitSelectListener(), this);
    }

    private void setGameRules() {
        for (World world : getServer().getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            //world.setGameRule(GameRule.BLOCK_EXPLOSION_DROP_DECAY, true);
            world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, true);
            //world.setGameRule(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, false);
            world.setGameRule(GameRule.DISABLE_RAIDS, true);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
            world.setGameRule(GameRule.DO_FIRE_TICK, false);
            world.setGameRule(GameRule.DO_INSOMNIA, false);
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            //world.setGameRule(GameRule.DO_LIMITED_CRAFTING, false);
            world.setGameRule(GameRule.DO_MOB_LOOT, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
            world.setGameRule(GameRule.DO_TILE_DROPS, false);
            world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
            world.setGameRule(GameRule.DO_WARDEN_SPAWNING, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            //world.setGameRule(GameRule.DROWNING_DAMAGE, false);
            world.setGameRule(GameRule.FALL_DAMAGE, false);
            //world.setGameRule(GameRule.FIRE_DAMAGE, true);
            //world.setGameRule(GameRule.FORGIVE_DEAD_PLAYERS, true);
            //world.setGameRule(GameRule.FREEZE_DAMAGE, false);
            //world.setGameRule(GameRule.GLOBAL_SOUND_EVENTS, true);
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            //world.setGameRule(GameRule.LAVA_SOURCE_CONVERSION, false);
            //world.setGameRule(GameRule.LOG_ADMIN_COMMANDS, true);
            //world.setGameRule(GameRule.MAX_COMMAND_CHAIN_LENGTH, 65536);
            //world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, 24);
            //world.setGameRule(GameRule.MOB_EXPLOSION_DROP_DECAY, true);
            world.setGameRule(GameRule.MOB_GRIEFING, false);
            world.setGameRule(GameRule.NATURAL_REGENERATION, true);
            //world.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 100);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setGameRule(GameRule.REDUCED_DEBUG_INFO, true);
            //world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, true);
            //world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, true);
            //world.setGameRule(GameRule.SNOW_ACCUMULATION_HEIGHT, 1);
            //world.setGameRule(GameRule.SPAWN_RADIUS, 10);
            world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
            //world.setGameRule(GameRule.TNT_EXPLOSION_DROP_DECAY, true);
            //world.setGameRule(GameRule.UNIVERSAL_ANGER, false);
            //world.setGameRule(GameRule.WATER_SOURCE_CONVERSION, true);
        }
    }

    private void prepareWorld() {
        for (World world : getServer().getWorlds()) {
            world.setTime(6000);
            world.setClearWeatherDuration(Integer.MAX_VALUE);
        }
    }
}
