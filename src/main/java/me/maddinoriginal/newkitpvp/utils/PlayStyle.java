package me.maddinoriginal.newkitpvp.utils;

import org.bukkit.ChatColor;

public enum PlayStyle {

    FIGHTER (ChatColor.DARK_GREEN + "Fighter"),
    RANGED (ChatColor.DARK_AQUA + "Ranged"),
    DAMAGE (ChatColor.RED + "Damage"),
    TANK (ChatColor.BLUE + "Tank"),
    MOBILITY (ChatColor.YELLOW + "Mobility"),
    CONTROL (ChatColor.GOLD + "Control"),
    KNOCKBACK (ChatColor.LIGHT_PURPLE + "Knockback"),
    HEAL (ChatColor.GREEN + "Heal");

    private String string;

    PlayStyle(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
