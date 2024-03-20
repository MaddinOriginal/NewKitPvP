package me.maddinoriginal.newkitpvp.abilityitems;

import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.items.*;
import org.bukkit.NamespacedKey;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AbilityItemManager {

    /**
     * Instance handling for singleton pattern
     */

    private AbilityItemManager() {
        setup();
    }

    private static class SingletonHelper {
        private static final AbilityItemManager INSTANCE = new AbilityItemManager();
    }

    public static AbilityItemManager getInstance() {
        return AbilityItemManager.SingletonHelper.INSTANCE;
    }

    /**
     * Actual class starting here
     */

    private NamespacedKey abilityItemKey = new NamespacedKey(NewKitPvP.getInstance(), "ability-item-key");
    public Map<String, AbilityItem> abilityItems = new HashMap<>();

    private void setup() {
        registerItems(new AirStreamAbilityItem(), new DashAbilityItem(), new DetonationAbilityItem(),
                new MagmaLauncherAbilityItem(), new MinigunAbilityItem(), new PlantBushAbilityItem(),
                new ScareOffAbilityItem(), new SnowstormAbilityItem(), new TeleportBehindAbilityItem(),
                new TeleportForwardAbilityItem(), new ThrowNegativePotionAbilityItem(), new WolfHuntAbilityItem());
    }

    public NamespacedKey getAbilityItemKey() {
        return abilityItemKey;
    }

    private void registerItems(AbilityItem... items) {
        Arrays.asList(items).forEach(ai-> abilityItems.put(ai.getId(), ai));
    }

}
