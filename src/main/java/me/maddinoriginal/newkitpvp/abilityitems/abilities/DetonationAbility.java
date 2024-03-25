package me.maddinoriginal.newkitpvp.abilityitems.abilities;

import me.maddinoriginal.newkitpvp.abilityitems.Ability;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DetonationAbility extends Ability {

    @Override
    public String getName() {
        return "Detonate";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getCooldown() {
        return 110;
    }

    private int ticksToDetonate = 50;

    @Override
    public boolean useAbility(Player player) {
        Player p = player;
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, ticksToDetonate, 1));
        TNTPrimed primedTNT = p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
        p.addPassenger(primedTNT);
        primedTNT.setFuseTicks(ticksToDetonate);
        primedTNT.setFireTicks(ticksToDetonate);
        primedTNT.setCustomName(ChatColor.RED + ChatColor.BOLD.toString() + "DETONATING");
        primedTNT.setCustomNameVisible(true);
        return true;
    }
}
