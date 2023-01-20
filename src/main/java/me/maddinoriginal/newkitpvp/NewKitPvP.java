package me.maddinoriginal.newkitpvp;

import org.bukkit.plugin.java.JavaPlugin;

public final class NewKitPvP extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        System.out.println("Enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("Disabled");
    }
}
