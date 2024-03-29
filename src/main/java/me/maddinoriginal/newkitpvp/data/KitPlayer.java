package me.maddinoriginal.newkitpvp.data;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.kits.Kit;
import me.maddinoriginal.newkitpvp.kits.KitType;

import java.util.UUID;

public class KitPlayer {

    /**
     * Stores all the data of a player on the server who is playing KitPvP
     * generates a PlayerData that stores all the stats like kills and coins
     * also stores runtime data like the player state or the kit a player uses
     */

    private NewKitPvP plugin = NewKitPvP.getInstance();

    private UUID uuid;
    private String playerName;
    private PlayerData data;

    private PlayerState playerState;
    private KitType kitType;
    private Kit kit;

    public KitPlayer(UUID uuid, String playerName) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.data = new PlayerData(uuid, playerName);
        playerState = PlayerState.LOBBY;
        kitType = KitType.NONE;
        kit = null;
    }

    public UUID getUuid() {
        return uuid;
    }
    public String getPlayerName() {
        return playerName;
    }
    public PlayerData getPlayerData() {
        return data;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
    public void setPlayerState(PlayerState state) {
        playerState = state;
    }

    public KitType getKitType() {
        return kitType;
    }
    public void setKitType(KitType type) {
        kitType = type;
    }

    public Kit getKit() {
        return kit;
    }
    public void setKit(Kit kit) {
        this.kit = kit;
    }
}
