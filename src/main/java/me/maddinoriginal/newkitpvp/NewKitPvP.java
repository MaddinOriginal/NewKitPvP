package me.maddinoriginal.newkitpvp;

import me.kodysimpson.simpapi.menu.MenuManager;
import me.maddinoriginal.newkitpvp.commands.KitPvPCommand;
import me.maddinoriginal.newkitpvp.commands.LobbyCommand;
import me.maddinoriginal.newkitpvp.configuration.MapsConfig;
import me.maddinoriginal.newkitpvp.configuration.PlayerdataConfig;
import me.maddinoriginal.newkitpvp.data.Database;
import me.maddinoriginal.newkitpvp.listeners.*;
import me.maddinoriginal.newkitpvp.listeners.custom.KitBuyListener;
import me.maddinoriginal.newkitpvp.listeners.custom.KitSelectListener;
import me.maddinoriginal.newkitpvp.maps.MapManager;
import me.maddinoriginal.newkitpvp.powerup.PowerUpManager;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class NewKitPvP extends JavaPlugin {

    private static NewKitPvP instance; //instance of this class
    private final PluginManager pm = Bukkit.getPluginManager();
    private String prefix;

    private Database database;

    //managers
    private KitPlayerManager kitPlayerManager;
    private MapManager mapManager;
    private PowerUpManager powerUpManager;

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

        MapsConfig.setup();
        PlayerdataConfig.get().options().copyDefaults(true);
        PlayerdataConfig.save();

        //other things
        registerCommands();
        registerListeners();
        registerCustomListeners();

        //menu manager from simpapi
        MenuManager.setup(this.getServer(), this);

        //init managers
        kitPlayerManager = KitPlayerManager.getInstance();
        mapManager = MapManager.getInstance();
        powerUpManager = PowerUpManager.getInstance();

        setGameRules();
        prepareWorld();

        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Prefix"));

        connectDatabase();

        System.out.println("[KitPvP] plugin started.");
    }

    @Override
    public void onDisable() {
        System.out.println("[KitPvP] disabling the plugin...");

        for (Player p : Bukkit.getOnlinePlayers()) {
            KitPlayerManager.getInstance().getKitPlayer(p).getPlayerData().save();
            p.kickPlayer("The server went down. Try to rejoin in just a moment!");
        }

        this.database.closeConnection();

        System.out.println("[KitPvP] plugin disabled.");
    }

    public static NewKitPvP getInstance() {
        return instance;
    }

    public String getPrefix() {
        return prefix;
    }

    private void registerCommands() {
        getCommand("kitpvp").setExecutor(new KitPvPCommand());
        getCommand("lobby").setExecutor(new LobbyCommand());
    }

    private void registerListeners() {
        pm.registerEvents(new ChatListener(), this);
        pm.registerEvents(new ConnectionListener(), this);
        pm.registerEvents(new DeathRespawnListener(), this);
        pm.registerEvents(new InteractListener(), this);
        pm.registerEvents(new MiscellaneousListener(), this);
        pm.registerEvents(new SneakListener(), this);
    }

    private void registerCustomListeners() {
        pm.registerEvents(new KitBuyListener(), this);
        pm.registerEvents(new KitSelectListener(), this);
        //pm.registerEvents(new PlayerJoinKitPvPListener(), this);
        //pm.registerEvents(new PlayerQuitKitPvPListener(), this);
    }

    //sets the default gamerules for each world TODO
    private void setGameRules() {
        //sets all the gamerules that are generally to be set for every map, no matter which one
        for (World world : getServer().getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            //world.setGameRule(GameRule.BLOCK_EXPLOSION_DROP_DECAY, true);
            world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, true);
            //world.setGameRule(GameRule.COMMAND_MODIFICATION_BLOCK_LIMIT, 32768);
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
            world.setGameRule(GameRule.DO_VINES_SPREAD, false);
            world.setGameRule(GameRule.DO_WARDEN_SPAWNING, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            //world.setGameRule(GameRule.DROWNING_DAMAGE, false);
            //world.setGameRule(GameRule.ENDER_PEARLS_VANISH_ON_DEATH, false);
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

    //sets time and weather for the default world
    private void prepareWorld() {
        for (World world : getServer().getWorlds()) {
            world.setTime(6000);
            world.setClearWeatherDuration(Integer.MAX_VALUE);
        }
    }

    private void connectDatabase() {
        try {
            this.database = new Database();
            database.initializeDatabases();
        } catch (SQLException ex) {
            System.out.println("Unable to connect to database and create tables.");
            ex.printStackTrace();
        }
    }

    public Database getDatabase() {
        return database;
    }
}
