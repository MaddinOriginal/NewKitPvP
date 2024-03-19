package me.maddinoriginal.newkitpvp.powerup;

import org.bukkit.Material;

public enum PowerUpType {

    // Possible blocks: Coal, Iron, Gold, Diamond, Redstone, Lapis Lazuli, Emerald, Netherite,
    // Copper, Amethyst

    SPEED ("Speed Up", Material.DIAMOND_BLOCK),
    REGEN ("Health Regeneration", Material.REDSTONE_BLOCK),
    STRENGTH ("Strength Buff", Material.NETHERITE_BLOCK),
    //HASTE (Material.LAPIS_BLOCK),
    FORCEFIELD ("Forcefield", Material.AMETHYST_BLOCK),
    LIGHTNINGFIELD ("Lightningfield", Material.IRON_BLOCK),
    COPPERCANNON ("Copper Cannon", Material.COPPER_BLOCK),
    COINS ("Coins", Material.GOLD_BLOCK),
    TOKENS ("Tokens", Material.EMERALD_BLOCK);

    private String name;
    private Material material;

    PowerUpType(String name, Material material) {
        this.name = name;
        this.material = material;
    }

    public String getName() {
        return name;
    }
    public Material getMaterial() {
        return material;
    }
}
