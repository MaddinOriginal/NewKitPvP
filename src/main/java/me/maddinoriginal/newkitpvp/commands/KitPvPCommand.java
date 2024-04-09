package me.maddinoriginal.newkitpvp.commands;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.maddinoriginal.newkitpvp.gui.KitSelectorGUI;
import me.maddinoriginal.newkitpvp.data.KitPlayerManager;
import me.maddinoriginal.newkitpvp.data.PlayerData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.entity.*;

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

            if (!p.isOp())
                return false;

            else if (args[0].equalsIgnoreCase("selectkit")) {
                try {
                    MenuManager.openMenu(KitSelectorGUI.class, p);
                } catch (MenuManagerException | MenuManagerNotSetupException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (args[0].equalsIgnoreCase("test")) {
                org.bukkit.inventory.ItemStack stack = p.getInventory().getItemInMainHand();
                net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(stack);



                org.bukkit.inventory.ItemStack bukkitItemStack = org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack.asBukkitCopy(nmsItemStack);
            }
            else if (args[0].equalsIgnoreCase("test2")) {
                //new HomingArrowsAbility().useAbility(p);

                //Illusioner illusioner = p.getWorld().spawn(p.getLocation(), Illusioner.class);
                //Entity ent = p.getWorld().spawnEntity(p.getLocation(), EntityType.MINECART_MOB_SPAWNER);

                //Bukkit.broadcastMessage("" + p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(NamespacedKey.MINECRAFT, "can-destroy")));
                //p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().set(new NamespacedKey(NamespacedKey.MINECRAFT, "can-destroy"), PersistentDataType.STRING, "minecraft:snow");

                org.bukkit.inventory.ItemStack itemMain = p.getInventory().getItemInMainHand();
                org.bukkit.inventory.ItemStack itemOff = p.getInventory().getItemInOffHand();

                net.minecraft.world.item.ItemStack nmsItemStackMain = CraftItemStack.asNMSCopy(itemMain);
                net.minecraft.world.item.ItemStack nmsItemStackOff = CraftItemStack.asNMSCopy(itemOff);

                NBTTagCompound compoundMain = (nmsItemStackMain.w());
                NBTTagCompound compoundOff = (nmsItemStackMain.w());

                NBTTagList canDestroyList = new NBTTagList();
                canDestroyList.add(net.minecraft.nbt.NBTTagString.a("minecraft:snow"));

                compoundMain.a("CanDestroy", canDestroyList);
                nmsItemStackMain.c(compoundMain);

                org.bukkit.inventory.ItemStack bukkitItemStack = org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack.asBukkitCopy(nmsItemStackMain);
                p.getInventory().addItem(bukkitItemStack);

                Bukkit.broadcastMessage("dummy garbage");

                //new SurroundingShielsAbility().useAbility(p);

                /*Material material = Material.DIAMOND_CHESTPLATE;

                ItemDisplay disp1 = p.getWorld().spawn(p.getLocation(), ItemDisplay.class, ent -> {
                    ent.setItemStack(new ItemStack(material));
                    ent.setTransformation(new Transformation(
                            new Vector3f(0, -0.75f, 0),
                            new AxisAngle4f(0, 0, 0, 0),
                            new Vector3f(1, 1, 1),
                            new AxisAngle4f(0, 0, 0, 0)));
                    p.addPassenger(ent);
                });

                ItemDisplay disp2 = p.getWorld().spawn(p.getLocation(), ItemDisplay.class, ent -> {
                    ent.setItemStack(new ItemStack(material));
                    ent.setTransformation(new Transformation(
                            new Vector3f(0, -0.75f, 0),
                            new AxisAngle4f(0, 0, 0, 0),
                            new Vector3f(1, 1, 1),
                            new AxisAngle4f(0, 0, 0, 0)));
                    p.addPassenger(ent);
                });

                ItemDisplay disp3 = p.getWorld().spawn(p.getLocation(), ItemDisplay.class, ent -> {
                    ent.setItemStack(new ItemStack(material));
                    ent.setTransformation(new Transformation(
                            new Vector3f(0, -0.75f, 0),
                            new AxisAngle4f(0, 0, 0, 0),
                            new Vector3f(1, 1, 1),
                            new AxisAngle4f(0, 0, 0, 0)));
                    p.addPassenger(ent);
                });

                new BukkitRunnable() {
                    float time = 0f;

                    @Override
                    public void run() {
                        time = time + 0.05f;
                        Helper.rotateDisplayPassenger(disp1, time);
                        Helper.rotateDisplayPassenger(disp2, time + 0.3333f);
                        Helper.rotateDisplayPassenger(disp3, time + 0.6667f);
                        //Helper.rotateDisplayPassenger(disp4, time + 0.75f);
                    }
                }.runTaskTimer(NewKitPvP.getInstance(), 1, 1);*/


                //p.sendMessage("" + Bukkit.getScheduler().getPendingTasks());

                /*Location loc = p.getEyeLocation().clone();
                loc.setPitch(0);

                Snowball snowball = p.getWorld().spawn(p.getEyeLocation(), Snowball.class, sb -> {
                    sb.setShooter(p);
                    sb.setGravity(false);
                    sb.setVelocity(loc.getDirection().multiply(1.2));
                    sb.setItem(new ItemStack(Material.CAVE_AIR));
                });

                ItemDisplay display = p.getWorld().spawn(p.getEyeLocation(), ItemDisplay.class, disp -> {
                    disp.setItemStack(new ItemStack(Material.IRON_AXE));
                    //id.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.FIRSTPERSON_RIGHTHAND);
                    disp.setTransformation(new Transformation(
                            new Vector3f(0, 0, 0),
                            new AxisAngle4f(1.0f, 0f, 0f, -1f),
                            new Vector3f(2, 2, 2),
                            new AxisAngle4f(0f, 0f, 0f, 0f)));
                    disp.setRotation(p.getLocation().getYaw() + 90, 0);
                    snowball.addPassenger(disp);
                });

                new BukkitRunnable() {
                    int timer = 42;
                    float fl = 1.0f;
                    float added = 1.5f;
                    Projectile proj = snowball;

                    @Override
                    public void run() {
                        if (timer <= 0 || proj.isDead()) {
                            cancel();
                            return;
                        } else {
                            timer--;
                        }

                        if (timer <= 7) {
                            proj.setGravity(true);
                        }

                        fl += added;
                        added = added * 0.95f;

                        proj.getLocation().getWorld().playSound(proj.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT, added + 0.1f, 1.0f);

                        display.setTransformation(new Transformation(
                                new Vector3f(0, 0, 0),
                                new AxisAngle4f(fl, 0f, 0f, -1f),
                                new Vector3f(2, 2, 2),
                                new AxisAngle4f(0f, 0f, 0f, 0f)));

                        //display.setRotation(p.getLocation().getYaw() + 90, 0);
                    }
                }.runTaskTimer(NewKitPvP.getInstance(), 1, 1);*/

                /*ItemDisplay display = p.getWorld().spawn(p.getEyeLocation(), ItemDisplay.class, id -> {
                    id.setItemStack(new ItemStack(Material.IRON_AXE));
                    id.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.FIRSTPERSON_RIGHTHAND);
                    id.setTransformation(new Transformation(
                            new Vector3f(0, -1, 0),
                            new AxisAngle4f(0f, 0f, 45f, 0f),
                            new Vector3f(3, 2, 2),
                            new AxisAngle4f(0f, 0f, 0f, 0f)));
                    proj.addPassenger(id);
                });*/

                /*ItemFrame frame = (ItemFrame) p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.ITEM_FRAME);
                frame.setItem(new ItemStack(Material.IRON_AXE));
                frame.setVisible(false);

                p.getWorld().spawn(p.getEyeLocation(), ArmorStand.class, as -> {
                    as.setVelocity(p.getLocation().getDirection().multiply(1.5));
                    as.addPassenger(frame);
                });*/

                /*Location loc = p.getLocation().add(0, 0.1, 0);
                double size = 2.0;
                int ticks = 120;
                int period = 3;
                Helper.drawCircle(loc, 3.0 *size, 0.2, ticks, period);
                Helper.drawHexagon(loc, 2.94 *size, 0.2, ticks, period, false);
                Helper.drawHexagon(loc, 2.486 *size, 0.2, ticks, period, true);
                Helper.drawSmallCircles(loc, 0.2 *size, 2.0, 1.4 *size, ticks, period);
                Helper.drawLines(loc, 1.1 *size, 0, 0.2, ticks, period);
                Helper.drawLines(loc, 0.66 *size, 1.5 *size, 0.2, ticks, period);
                Helper.drawCircle(loc, 0.75 *size, 1.0, ticks, period);*/

                /*
                double multiplier = 2.66;
                double stepLength = 0.2;

                Location loc = p.getLocation().add(0, 0.2, 0);
                loc.setPitch(0);

                Vector dir = loc.getDirection().setY(0).normalize().multiply(multiplier);
                loc.add(dir);
                loc.setYaw(loc.getYaw() + 60);

                for (int i = 0; i < 6; i++) {
                    loc.setYaw(loc.getYaw() + 60);
                    dir = loc.getDirection().normalize().multiply(multiplier);

                    for (int j = 0; j < dir.length()/stepLength; j++) {
                        loc.add(dir.clone().normalize().multiply(stepLength));
                        loc.getWorld().spawnParticle(Particle.FLAME, loc, 1, 0, 0, 0, 0);
                    }
                }*/

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
