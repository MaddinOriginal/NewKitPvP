package me.maddinoriginal.newkitpvp.abilityitems.items;

import me.maddinoriginal.newkitpvp.abilityitems.AbilityItem;
import me.maddinoriginal.newkitpvp.abilityitems.AbilityType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class SnowstormAbilityItem extends AbilityItem {
    @Override
    public String getName() {
        return "Snowstorm Ability";
    }

    @Override
    public Material getMaterial() {
        return Material.GLOWSTONE_DUST;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("Right click to summon a snowstorm!");
    }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.SNOWSTORM;
    }

    @Override
    public void handleLeftClick(Player player, ItemStack item, PlayerInteractEvent event) {
        //AbilityType.SNOWSTORM.getAbility().useAbility(player);
    }

    @Override
    public void handleRightClick(Player player, ItemStack item, PlayerInteractEvent event) {
        AbilityType.SNOWSTORM.getAbility().useAbility(player);
    }
}
