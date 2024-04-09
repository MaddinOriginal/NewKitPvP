package me.maddinoriginal.newkitpvp.abilityitems.items;

import me.maddinoriginal.newkitpvp.abilityitems.AbilityItem;
import me.maddinoriginal.newkitpvp.abilityitems.AbilityType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SurroundingShieldsAbilityItem extends AbilityItem {

    @Override
    public String getName() {
        return "Surrounding Shield";
    }

    @Override
    public Material getMaterial() {
        return Material.GLOWSTONE_DUST;
    }

    @Override
    public List<String> getLore() {
        return List.of("Surrounds the player with shields on activation");
    }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.SHIELDS;
    }

    @Override
    public void handleLeftClick(Player player, ItemStack item, PlayerInteractEvent event) {
        //AbilityType.SHIELDS.getAbility().useAbility(player);
    }

    @Override
    public void handleRightClick(Player player, ItemStack item, PlayerInteractEvent event) {
        AbilityType.SHIELDS.getAbility().useAbility(player);
    }
}
