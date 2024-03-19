package me.maddinoriginal.newkitpvp.maps;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;
import java.util.Random;

public class Map {

    private World world;
    private String name;
    private String creator;
    private Location lobbySpawn;
    private List<Location> playerSpawns;
    private List<Location> powerupSpawns;

    private Random random = new Random();

    public Map(World world, String name, String creator, Location lobbySpawn, List<Location> playerSpawns, List<Location> powerupSpawns) {
        this.world = world;
        this.name = name;
        this.creator = creator;
        this.lobbySpawn = lobbySpawn;
        this.playerSpawns = playerSpawns;
        this.powerupSpawns = powerupSpawns;
    }

    public Location getRandomPlayerSpawn() {
        int i = random.nextInt(playerSpawns.size());
        return playerSpawns.get(i).clone();
    }

    public Location getRandomPowerupSpawn() {
        int i = random.nextInt(powerupSpawns.size());
        return powerupSpawns.get(i).clone();
    }

    public World getWorld() {
        return world;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public Location getLobbySpawn() {
        return lobbySpawn;
    }
}
