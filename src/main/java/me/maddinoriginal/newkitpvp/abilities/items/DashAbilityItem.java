package me.maddinoriginal.newkitpvp.abilities.items;

import me.maddinoriginal.newkitpvp.abilities.AbilityType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class DashAbilityItem extends AbilityItem {

    @Override
    public String getName() {
        return "Dash Ability";
    }

    @Override
    public Material getMaterial() {
        return Material.GLOWSTONE_DUST;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("Right click to dash forward!");
    }

    @Override
    public void handleLeftClick(Player player, ItemStack item, PlayerInteractEvent event) {
        //AbilityType.DASH.getAbility().useAbility(player);
    }

    @Override
    public void handleRightClick(Player player, ItemStack item, PlayerInteractEvent event) {
        AbilityType.DASH.getAbility().useAbility(player);
    }
}
