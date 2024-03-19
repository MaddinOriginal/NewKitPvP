package me.maddinoriginal.newkitpvp.utils;

import org.bukkit.ChatColor;

public enum ServerRank {

    OWNER (ChatColor.DARK_RED + "[OWNER]"),
    ADMIN (ChatColor.RED + "[ADMIN]"),
    MODERATOR (ChatColor.DARK_GREEN + "[MOD]"),
    MVP (ChatColor.AQUA + "[MVP]"),
    VIP (ChatColor.GREEN + "[VIP]"),
    DEFAULT ("");

    private String tag;

    ServerRank(String tag) {
        this.tag = tag;
    }

    private String getTag() {
        return tag;
    }
}
