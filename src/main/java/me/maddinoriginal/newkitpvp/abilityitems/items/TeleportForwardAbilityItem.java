package me.maddinoriginal.newkitpvp.abilityitems.items;

import me.maddinoriginal.newkitpvp.abilityitems.AbilityItem;
import me.maddinoriginal.newkitpvp.abilityitems.AbilityType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class TeleportForwardAbilityItem extends AbilityItem {

    @Override
    public String getName() {
        return "Tele-Beam Ability";
    }

    @Override
    public Material getMaterial() {
        return Material.GLOWSTONE_DUST;
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList("Right click to teleport forward!");
    }

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.TELEPORT_FORWARD;
    }

    @Override
    public void handleLeftClick(Player player, ItemStack item, PlayerInteractEvent event) {
        //AbilityType.TELEPORT_FORWARD.getAbility().useAbility(player);
    }

    @Override
    public void handleRightClick(Player player, ItemStack item, PlayerInteractEvent event) {
        AbilityType.TELEPORT_FORWARD.getAbility().useAbility(player);
    }
}
