package me.maddinoriginal.newkitpvp.kits;

import org.bukkit.ChatColor;

public enum KitCategory {
    STANDARD (ChatColor.GREEN + "Standard"),
    ADVANCED (ChatColor.RED + "Advanced"),
    LEGENDARY (ChatColor.AQUA + "Legendary");

    private String name;

    KitCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
