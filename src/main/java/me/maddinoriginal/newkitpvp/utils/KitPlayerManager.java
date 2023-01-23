package me.maddinoriginal.newkitpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class KitPlayerManager {

    private KitPlayerManager() {}

    private static class SingletonHelper {
        private static final KitPlayerManager INSTANCE = new KitPlayerManager();
    }

    public static KitPlayerManager getInstance() {
        return KitPlayerManager.SingletonHelper.INSTANCE;
    }

    /**
     * manages runtime data of players with a list of KitPlayer Objects (one per player online)
     * has methods used to read and write player data in KitPlayer Objects and playerdata.yml
     */

    private HashMap<String, KitPlayer> kitplayers = new HashMap<>();

    public KitPlayer getKitPlayer(Object object) {
        if (object instanceof Player) {
            Player target = (Player) object;
            if (!kitplayers.containsKey(target.getUniqueId().toString())) {
                return null;
            }
            return kitplayers.get(target.getUniqueId().toString());
        }
        if (object instanceof UUID) {
            UUID uuid = (UUID) object;
            if (!kitplayers.containsKey(uuid.toString())) {
                return null;
            }
            return kitplayers.get(uuid.toString());
        }
        if (object instanceof String) {
            String string = (String) object;
            if (!kitplayers.containsKey(string)) {
                return null;
            }
            return kitplayers.get(string);
        }
        return null;
    }

    public void addKitPlayer(UUID uuid, String name) {
        if (kitplayers.containsKey(uuid)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Spielerdaten existieren bereits ??");
            return;
        }
        kitplayers.put(uuid.toString(), new KitPlayer(uuid, name));
    }

    public void removeKitPlayer(UUID uuid) {
        if (!kitplayers.containsKey(uuid.toString())) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Keine Spielerdaten existieren zum Entfernen !!");
            return;
        }
        kitplayers.get(uuid.toString()).getData().save();
        kitplayers.remove(uuid.toString());
    }


}
