package me.maddinoriginal.newkitpvp.commands;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.maddinoriginal.newkitpvp.NewKitPvP;
import me.maddinoriginal.newkitpvp.abilityitems.abilities.EvokerFangAbility;
import me.maddinoriginal.newkitpvp.gui.KitSelectorGUI;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.data.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class KitPvPCommand implements CommandExecutor {

    private int pointer = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            //command sender is not a player
            sender.sendMessage("Only players may execute this command");
            return false;
        }

        Player p = (Player) sender;

        //TODO implement permissions correctly
        if (!p.isOp())
            return false;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("stats")) {
                PlayerData data = KitPlayerManager.getInstance().getKitPlayer(p).getPlayerData();
                p.sendMessage("=== Your Stats ===");
                p.sendMessage("Kills: " + data.getKills().getAmount());
                p.sendMessage("Deaths: " + data.getDeaths().getAmount());
                p.sendMessage("Assists: " + data.getAssists().getAmount());
                p.sendMessage("Coins: " + data.getCoins().getAmount());
                p.sendMessage("Tokens: " + data.getTokens().getAmount());
            }
            if (args[0].equalsIgnoreCase("selectkit")) {
                try {
                    MenuManager.openMenu(KitSelectorGUI.class, p);
                } catch (MenuManagerException | MenuManagerNotSetupException e) {
                    throw new RuntimeException(e);
                }
            }
            if (args[0].equalsIgnoreCase("test")) {
                //AbilityType.WOLF_HUNT.getAbility().useAbility(p);
                //p.sendMessage("" + Bukkit.getScheduler().getPendingTasks());
                //p.getWorld().spawnArrow(p.getEyeLocation(), p.getEyeLocation().getDirection(), 1.0f, 0.01f);

                /*
                Location loc = p.getLocation();
                loc.setY(loc.getY() - 1.2);

                Vex vex = (Vex) p.getWorld().spawnEntity(loc, EntityType.VEX);
                vex.setAware(false);
                vex.setGravity(false);
                vex.setSilent(true);
                vex.addPassenger(p);

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        Location loc = p.getEyeLocation().clone();
                        loc.setPitch(0);
                        vex.setVelocity(loc.getDirection());
                    }
                }.runTaskTimer(NewKitPvP.getInstance(), 0, 3);*/


                /*Location eyeLoc = p.getEyeLocation();
                Vector dir = eyeLoc.getDirection().normalize().multiply(0.2);

                assert EntityType.ARROW.getEntityClass() != null;
                Arrow arrow = (Arrow) p.getWorld().spawn(eyeLoc, EntityType.ARROW.getEntityClass(), a -> {
                    a.setGravity(false);
                    a.setVelocity(dir);
                    ((Arrow) a).setColor(Color.fromRGB(220, 186, 182));
                });

                new BukkitRunnable() {
                    Vector velocity = dir;

                    @Override
                    public void run() {
                        Vector newVelocity = velocity.normalize();
                        Vector playerVector = p.getEyeLocation().toVector().normalize();

                        newVelocity = playerVector.subtract(newVelocity).normalize().multiply(0.2);

                        velocity = newVelocity;
                        arrow.setVelocity(velocity);
                    }
                }.runTaskTimer(NewKitPvP.getInstance(), 20, 5);*/
            }
        }

        return false;
    }
}
