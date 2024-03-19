package me.maddinoriginal.newkitpvp.abilityitems;

import org.bukkit.entity.Player;

public abstract class Ability {

    public abstract String getName();
    public abstract String getDescription();
    public abstract int getCooldown();

    public abstract boolean useAbility(Player player);

    /*public String getId() {
        return getClass().getSimpleName();
    }*/

    /*public ItemStack toItem() {
        ItemStack item = new ItemStack(Material.GLOWSTONE_DUST, 1);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        meta.setDisplayName(ChatColor.YELLOW + getName());
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + getDescription()); //TODO
        meta.setLore(lore);
        container.set(AbilityItemManager.getInstance().getAbilityItemKey(), PersistentDataType.STRING, getId());
        item.setItemMeta(meta);
        return item;
    }*/
}
