package me.maddinoriginal.newkitpvp;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class NewKitPvP extends JavaPlugin {

    private final String PREFIX = "[KitPvP] ";

    @Override
    public void onEnable() {
        System.out.println(PREFIX + "starting the plugin...");

        registerCommands();
        registerListeners();
        registerCustomListeners();

        setGameRules();
        prepareWorld();

        System.out.println(PREFIX + "plugin started.");
    }

    @Override
    public void onDisable() {
        System.out.println(PREFIX + "disabling the plugin...");

        System.out.println(PREFIX + "plugin disabled.");
    }

    private void registerCommands() {
        //TODO
    }

    private void registerListeners() {
        //TODO
    }

    private void registerCustomListeners() {
        //TODO
    }

    private void setGameRules() {
        for (World world : getServer().getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, true);
            //world.setGameRule(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, false);
            world.setGameRule(GameRule.DISABLE_RAIDS, true);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
            world.setGameRule(GameRule.DO_FIRE_TICK, false);
            world.setGameRule(GameRule.DO_INSOMNIA, false);
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            //world.setGameRule(GameRule.DO_LIMITED_CRAFTING, false);
            world.setGameRule(GameRule.DO_MOB_LOOT, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
            world.setGameRule(GameRule.DO_TILE_DROPS, false);
            world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            //world.setGameRule(GameRule.DROWNING_DAMAGE, false);
            world.setGameRule(GameRule.FALL_DAMAGE, false);
            //world.setGameRule(GameRule.FIRE_DAMAGE, true);
            //world.setGameRule(GameRule.FORGIVE_DEAD_PLAYERS, true);
            //world.setGameRule(GameRule.FREEZE_DAMAGE, false);
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            //world.setGameRule(GameRule.LOG_ADMIN_COMMANDS, true);
            world.setGameRule(GameRule.MOB_GRIEFING, false);
            world.setGameRule(GameRule.NATURAL_REGENERATION, true);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setGameRule(GameRule.REDUCED_DEBUG_INFO, true);
            //world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, true);
            //world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, true);
            world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
        }
    }

    private void prepareWorld() {
        for (World world : getServer().getWorlds()) {
            world.setTime(6000);
            world.setClearWeatherDuration(Integer.MAX_VALUE);
        }
    }
}
