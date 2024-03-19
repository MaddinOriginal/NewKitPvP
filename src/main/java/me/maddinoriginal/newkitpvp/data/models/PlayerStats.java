package me.maddinoriginal.newkitpvp.data.models;

public class PlayerStats {

    private String uuid;
    private int kills;
    private int deaths;
    private int coins;
    private int tokens;
    //private int experience; //TODO

    public PlayerStats(String uuid, int kills, int deaths, int coins, int tokens) {
        this.uuid = uuid;
        this.kills = kills;
        this.deaths = deaths;
        this.coins = coins;
        this.tokens = tokens;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }
}
