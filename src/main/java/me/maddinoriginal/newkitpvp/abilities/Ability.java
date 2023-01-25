package me.maddinoriginal.newkitpvp.abilities;

import org.bukkit.entity.Player;

public abstract class Ability {

    abstract public void useAbility(Player player);

    abstract String getName();
    abstract String getDescription();
    abstract int getCooldown();
    //abstract AbilityItem getItem();
}
