package me.maddinoriginal.newkitpvp.data.models;

import me.maddinoriginal.newkitpvp.kits.Kit;

public class KitUpgrade {

    private String uuid;
    private Kit kit;

    private int abilityLevel;
    private int passiveLevel;
    private int offenseLevel;
    private int defenseLevel;
    private int prestigeLevel;

    public KitUpgrade(String uuid, Kit kit, int abilityLevel, int passiveLevel, int offenseLevel, int defenseLevel, int prestigeLevel) {
        this.uuid = uuid;
        this.kit = kit;
        this.abilityLevel = abilityLevel;
        this.passiveLevel = passiveLevel;
        this.offenseLevel = offenseLevel;
        this.defenseLevel = defenseLevel;
        this.prestigeLevel = prestigeLevel;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public int getAbilityLevel() {
        return abilityLevel;
    }

    public void setAbilityLevel(int abilityLevel) {
        this.abilityLevel = abilityLevel;
    }

    public int getPassiveLevel() {
        return passiveLevel;
    }

    public void setPassiveLevel(int passiveLevel) {
        this.passiveLevel = passiveLevel;
    }

    public int getOffenseLevel() {
        return offenseLevel;
    }

    public void setOffenseLevel(int offenseLevel) {
        this.offenseLevel = offenseLevel;
    }

    public int getDefenseLevel() {
        return defenseLevel;
    }

    public void setDefenseLevel(int defenseLevel) {
        this.defenseLevel = defenseLevel;
    }

    public int getPrestigeLevel() {
        return prestigeLevel;
    }

    public void setPrestigeLevel(int prestigeLevel) {
        this.prestigeLevel = prestigeLevel;
    }
}
