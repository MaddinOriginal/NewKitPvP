package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.entity.Player;

public class SurroundingShiels extends Ability {

    @Override
    public String getName() {
        return "Surrounding Shields";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 260;
    }

    @Override
    public boolean useAbility(Player player) {
        return false;
    }
}
