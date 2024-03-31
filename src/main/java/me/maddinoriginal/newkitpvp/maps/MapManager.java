package me.maddinoriginal.newkitpvp.maps;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.configuration.MapsConfig;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapManager {

    private FileConfiguration config = MapsConfig.get();

    private String worldsDir = NewKitPvP.getInstance().getDataFolder().toPath() + "/maps";
    private List<File> worldFiles = new ArrayList<>();

    private List<Map> mapPool = new ArrayList<>();
    private Map currentMap;

    /**
     * Instance handling for singleton pattern
     */

    private MapManager() {
        setup();
    }

    private static class SingletonHelper {
        private static final MapManager INSTANCE = new MapManager();
    }

    public static MapManager getInstance() {
        return MapManager.SingletonHelper.INSTANCE;
    }

    /**
     * actual class
     */

    public Map getCurrentMap() {
        return currentMap;
    }

    private void setup() {
        //importWorlds(); TODO
        World world = Bukkit.getWorld("world");
        String name = "Sky Islands";
        String creator = "MaddinOriginal";
        Location lobbySpawn = new Location(world, 0.5, 0, 0.5, 0, 0);
        List<Location> playerSpawns = null;
        List<Location> powerupSpawns = null;

        currentMap = new Map(world, name, creator, lobbySpawn, playerSpawns, powerupSpawns);
    }

    private void importWorlds() {

    }

    private void listWorldFiles() {
        for (String worldKey : config.getRoot().getKeys(false)) {
            if (isValidArena(worldKey)) {

            }
        }
    }

    private boolean isValidArena(String worldKey) {
        List<String> subPaths = Arrays.asList(".name", ".creator", ".lobby.world", ".lobby.x", ".lobby.y", ".lobby.z",
                ".lobby.yaw", ".lobby.pitch", ".spawns");

        for (String subPath : subPaths) {
            if (!config.contains(worldKey + subPath)) {
                return false;
            }
        }

        List<Location> spawnLocs;

        for (String path : config.getConfigurationSection(worldKey + ".spawns").getKeys(false)) {

        }

        if (config.getConfigurationSection(worldKey + ".spawns").getKeys(false).size() == 0) {
            return false;
        }

        return true;
    }

    private void isValidKey(String path) {

    }

    private void check() {
        for (MapType mapType : MapType.values()) {
            config.set(mapType.name() + ".name", mapType.getName());
            config.set(mapType.name() + ".creator", mapType.getName());

            if (!config.contains(mapType.name() + ".lobby.world")) {
                config.set(mapType.name() + ".lobby.world", 0); //TODO
            }

            List<String> paths = Arrays.asList(".lobby.x", ".lobby.y", ".lobby.z", ".lobby.yaw", ".lobby.pitch",
                    ".spawns.1.x", ".spawns.1.y", ".spawns.1.z", ".spawns.1.yaw", ".spawns.1.pitch");

            for (String path : paths) {
                if (!config.contains(mapType.name() + path)) {
                    config.set(mapType.name() + path, 0);
                }
            }
        }
        config.set("testpath", new Location(Bukkit.getWorld("world"), 22, 17.5, 0, 45, 20));
        MapsConfig.save();
    }

    private void load() {

    }

    private void save() {

    }

    private void importMapsFromConfig() {
        for (String worldKey : config.getRoot().getKeys(false)) {
            File file = new File(worldsDir + "/" + worldKey);
            if (file.exists()) {
                System.out.println("File with key " + worldKey + " exists. ");
                try {
                    FileUtils.copyToDirectory(file, Bukkit.getWorldContainer());
                } catch (Exception e) {
                    System.out.println("Rip hat nicht geklappt lol");
                    //nothing
                }
                try {
                    //Bukkit.createWorld(new WorldCreator(worldKey));
                } catch (Exception e) {
                    System.out.println("ERROR RIP LOL");
                    //nothing
                }
                System.out.println(Bukkit.getWorlds());
            } else {
                System.out.println("File with key " + worldKey + " does NOT exist.");
            }
        }
    }

    private void test() {
        for (MapType mapType : MapType.values()) {
            World world = Bukkit.getWorld(mapType.name());
            String mapName = config.getString(mapType.name() + ".name");
            String mapCreator = config.getString(mapType.name() + ".creator");
            Location lobbySpawn = new Location(Bukkit.getWorld(mapType.name()),
                    config.getDouble(mapType.name() + ".lobby.x"),
                    config.getDouble(mapType.name() + ".lobby.y"),
                    config.getDouble(mapType.name() + ".lobby.z"),
                    (float) config.getDouble(mapType.name() + ".lobby.yaw"),
                    (float) config.getDouble(mapType.name() + ".lobby.pitch"));

            List<Location> playerSpawns = new ArrayList<>();
            for (int i = 0; i < 24; i++) {
                if (config.contains(mapType.name() + ".spawns." + i)) {
                    config.set(mapType.name() + ".spawns." + i + ".x", 0);
                }
            }

            List<Location> powerupSpawns = new ArrayList<>();

            Map map = new Map(world, mapName, mapCreator, lobbySpawn, playerSpawns, powerupSpawns);
        }
    }
}
