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

import java.io.*;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

        //try world stuff
        //extractWorld();
        //copyWorld();
        //loadWorld();

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
        pm.registerEvents(new DamageListener(), this);
        pm.registerEvents(new DeathRespawnListener(), this);
        pm.registerEvents(new InteractListener(), this);
        pm.registerEvents(new MiscellaneousListener(), this);
        pm.registerEvents(new SneakSprintListener(), this);
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
            world.setGameRule(GameRule.MOB_GRIEFING, true); //TODO
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

    public boolean extractWorld() {
        // Returns true if the minigame_world folder already exists inside the datafolder
        // Returns true if the minigame_world folder has successfully been placed inside the datafolder
        // Returns false if something went wrong
        try {
            // Getting the data folder
            // The data folder is located in the plugins folder of your server and by default is has the same name as the plugin
            // If you have worked with config files before, this is the same folder where the default config is copied to
            File dataFolder = getDataFolder();

            // Making the folder if it doesn't exist yet
            if (!dataFolder.exists())
                dataFolder.mkdirs();

            // This File object represents the minigame_world folder inside the data folder
            File minigameWorld = new File(dataFolder, "world");

            if (!minigameWorld.exists()) {

                // Getting an Input Stream object with the getResourceAsStream method
                // Make sure there is a "/" before "minigame_world.zip" otherwise it won't work
                // "minigame_world" is the exact name I gave my world folder
                // ".zip" indicates that the file is a zip file
                InputStream inputStream = getClass().getResourceAsStream("/world.zip");

                // This method gets the zipped world folder from the plugin resources, unzips it and puts it in the datafolder
                // It will return true if it successfully extracted the world folder from the jar otherwise false
                boolean extracted = extractFromJar(inputStream, dataFolder);

                return extracted;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean extractFromJar(InputStream inputStream, File destination) {

        // Creating a new ZipInputStream object that takes in inputStream as a parameter
        // It is needed to use a zip input stream because the world folder is zipped
        try (ZipInputStream input = new ZipInputStream(inputStream)) {

            // Iterating over all the entries of the zip input stream
            // Note that getNextEntry() does not only return a ZipEntry object, but it also positions
            // the stream at the beginning of that entry
            for (ZipEntry entry = input.getNextEntry(); entry != null; entry = input.getNextEntry()) {

                // Checking if the entry is not a directory
                // If it is not a directory, it is a file
                // If the file is inside a directory, that directory will be made
                if ( ! entry.isDirectory()) {

                    // Making a file object that takes in destination en entry.getName() as parameters
                    File file = new File(destination, entry.getName());

                    // Getting the parent of the file and making the directories
                    File parent = file.getParentFile();
                    parent.mkdirs();

                    // Making an output stream into the file
                    FileOutputStream output = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    // The read method reads bytes from the entry where the stream is currently positioned
                    // It puts the read bytes into the buffer and returns the amount of bytes that have been read
                    // If there are no more bytes in the stream, the read method returns -1
                    while ((bytesRead = input.read(buffer)) != -1)
                        // Writing the bytes that are inside the buffer into the output stream
                        output.write(buffer, 0, bytesRead);

                    // Closing the output stream
                    output.close();
                }
            }
            // Once the method is done iterating over the zip entries, it closes the zip input stream
            input.close();

            // If there were no exceptions, the method will return true
            return true;
        } catch (IOException e) {
            // If there is an exception, the method will print the stack trace and return false
            e.printStackTrace();
            return false;
        }
    }

    public boolean copyWorld() {
        // Returns false if the source doesn't exist
        // Returns true if the method successfully copied the source folder into the destination folder
        // Returns false if something went wrong
        try {
            // This file represents the minigame_world folder inside the plugin data folder
            File sourceFolder = new File(getDataFolder(), "world");

            // If the source doesn't exist, the method can't continue, so make sure you create the folder with the extractWorld method before using this method
            if (!sourceFolder.exists())
                return false;

            // This File object represents the place where we're gonna copy the source folder
            // The destination folder will be placed in the server folder (same folder with world, world_nether, plugins, etc)
            File destinationFolder = new File("world_active");

            // Making the destination folder if it doesn't exist yet
            if (!destinationFolder.exists())
                destinationFolder.mkdirs();

            // This method makes a copy of the source and puts it into the destination
            boolean copied = copyDirectory(sourceFolder, destinationFolder);
            return copied;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyDirectory(File source, File destination) {

        // This method assumes that File source is a directory (folder)
        // It also assumes that File destination already exists
        // It puts each file from the source directory in the destination directory
        // If a file in the source is also a directory, this method calls itself again (Recursion) and passes in
        // the file as the source and new File(destination, file) as the new destination

        try {
            // Putting all the files in an array
            File[] files = source.listFiles();

            // Iterating over the array
            for (File file : files) {

                // Storing the name of the file
                String fileName = file.getName();
                // Making a File object
                File outputFile = new File(destination, fileName);

                // Checking if the file is a directory
                if (file.isDirectory()) {

                    // Making the directory in the destination file
                    outputFile.mkdirs();

                    // Calling the copyDirectory method again
                    copyDirectory(file, outputFile);

                }
                // If the file is a file
                else {

                    // Making an input stream from the file
                    FileInputStream input = new FileInputStream(file);
                    // Making an output stream to the destination
                    FileOutputStream output = new FileOutputStream(outputFile);


                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    // The read method reads bytes from the input stream and puts these bytes in the buffer
                    // It also returns the amount of bytes that have been put in the buffer
                    // If there are no more bytes to read, it returns -1
                    while ((bytesRead = input.read(buffer)) != -1)
                        // Writing the bytes that are in the buffer into the output stream
                        output.write(buffer, 0, bytesRead);

                    // Closing the input stream and the output stream
                    input.close();
                    output.close();

                }

            }

            // If there are no errors, the method will return true
            return true;
        }
        catch (IOException e) {
            // If there is an error, the method will print out the stack trace and return false
            e.printStackTrace();
            return false;
        }
    }

    public World loadWorld() {
        // Returns null if the world folder does not exist
        // Returns the loaded world if the method successfully loaded the world
        // Returns null if something went wrong
        try {
            // This file represents the world that we want to load
            File worldToLoad = new File("world_active");

            // Returning null if the world we want to load does not even exist
            if (!worldToLoad.exists())
                return null;

            // Making a WorldCreator object which is used to load the world
            WorldCreator worldCreator = new WorldCreator("world_active");

            // The createWorld method does not only create new worlds but also loads worlds that already exist
            World loadedWorld = worldCreator.createWorld();
            return loadedWorld;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
