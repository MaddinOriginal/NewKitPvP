package me.maddinoriginal.newkitpvp.configuration;

public class ConfigManager {

    private ConfigManager() {}

    private static class SingletonHelper {
        private static final ConfigManager INSTANCE = new ConfigManager();
    }

    public static ConfigManager getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
