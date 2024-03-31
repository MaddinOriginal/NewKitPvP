package me.maddinoriginal.newkitpvp.kits;

import me.maddinoriginal.newkitpvp.kits.advancedkits.*;
import me.maddinoriginal.newkitpvp.kits.legendarykits.*;
import me.maddinoriginal.newkitpvp.kits.standardkits.*;

public enum KitType {

    NONE (null),

    //Standard Kits
    SWORDSMAN (new Swordsman()),
    ARCHER (new Archer()),
    TANK (new Tank()),
    ARBALIST (new Marksman()),
    BARBARIAN (new Barbarian()),

    //Advanced Kits
    PYRO (new Pyro()),
    TELEPORTER (new Teleporter()),
    //NINJA (new Ninja()),
    ASSASSIN (new Assassin()),
    ELEMENTALIST (new Elementalist()),
    HUNTER (new Hunter()),
    GHOST (new Ghost()),
    BLASTER (new Blaster()),
    YETI (new Yeti()),
    BOMBERMAN (new Bomber()),
    //FARMER (),
    //FISHERMAN (),
    //POSEIDON (),
    //BOMBARCHER (),
    //MAD_SCIENTIST (),

    //Legendary Kits
    DARK_KNIGHT (new DarkKnight()),
    GRIM_REAPER (new GrimReaper()),
    BLACK_DRAGON (new BlackDragon());

    private Kit kit;

    KitType(Kit kit) {
        this.kit = kit;
    }

    public Kit getKit() {
        return kit;
    }
}
