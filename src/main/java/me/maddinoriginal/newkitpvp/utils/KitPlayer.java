package me.maddinoriginal.newkitpvp.utils;

import me.maddinoriginal.newkitpvp.NewKitPvP;

import java.util.UUID;

public class KitPlayer {

    /**
     * Stores all the data of a player on the server who is playing KitPvP
     * generates a PlayerData that stores all the stats like kills and coins
     * TODO: also stores runtime data like the kit a player uses
     */

    private NewKitPvP plugin = NewKitPvP.getInstance();

    private UUID uuid;
    private String playerName;
    private PlayerData data;

    public KitPlayer(UUID uuid, String playerName) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.data = new PlayerData(uuid, playerName);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerData getData() {
        return data;
    }
}
