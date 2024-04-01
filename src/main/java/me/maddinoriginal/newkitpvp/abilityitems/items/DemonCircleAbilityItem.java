package me.maddinoriginal.newkitpvp.abilityitems.items;

import me.maddinoriginal.newkitpvp.abilityitems.AbilityItem;
import me.maddinoriginal.newkitpvp.abilityitems.AbilityType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class DemonCircleAbilityItem extends AbilityItem {

    @Override
    public String getName() {
        return "Demon Circle Ability";
    }

    @Override
    public Material getMaterial() {
        return Material.GLOWSTONE_DUST;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("Summons a demonic Alchemy Circle");
    }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.DEMON_CIRCLE;
    }

    @Override
    public void handleLeftClick(Player player, ItemStack item, PlayerInteractEvent event) {
        //AbilityType.DEMON_CIRCLE.getAbility().useAbility(player);
    }

    @Override
    public void handleRightClick(Player player, ItemStack item, PlayerInteractEvent event) {
        AbilityType.DEMON_CIRCLE.getAbility().useAbility(player);
    }
}
