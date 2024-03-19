package me.maddinoriginal.newkitpvp.configuration;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapsConfig {

    private static File file;
    private static FileConfiguration customFile;

    //Find config file or generate if it does not exist
    public static void setup() {
        file = new File(NewKitPvP.getInstance().getDataFolder(), "maps.yml");

        //File erstellen, falls sie noch nicht existiert
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);

        //Folgender code kopiert alle default Werte der yml file aus der jar, sofern der jeweilige path fehlen sollte
        //Link zum Forum Beitrag: https://www.spigotmc.org/threads/how-to-get-default-custom-config.328178/
        InputStream customClassStream = NewKitPvP.getInstance().getClass().getResourceAsStream("/maps.yml");
        InputStreamReader strR = new InputStreamReader(customClassStream);
        FileConfiguration defaults = YamlConfiguration.loadConfiguration(strR);

        for (String path : defaults.getKeys(true)) {
            if (!customFile.contains(path)) {
                customFile.set(path, defaults.get(path));
            }
        }
        save();
    }

    public static FileConfiguration get() {
        return customFile;
    }

    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
