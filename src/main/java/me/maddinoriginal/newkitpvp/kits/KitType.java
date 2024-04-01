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
    MARKSMAN (new Marksman()),
    BARBARIAN (new Barbarian()),

    //Advanced Kits
    PYRO (new Pyro()),
    TELEPORTER (new Teleporter()),
    ASSASSIN (new Assassin()),
    RANGER (new Ranger()),
    ELEMENTALIST (new Elementalist()),
    GHOST (new Ghost()),
    HUNTER (new Hunter()),
    BLASTER (new Blaster()),
    YETI (new Yeti()),
    BOMBERMAN (new Bomber()),
    //NINJA (new Ninja()),
    //FARMER (),
    //FISHERMAN (),
    //POSEIDON (),
    //BOMBARCHER (),
    //MAD_SCIENTIST (),

    //Legendary Kits
    DARK_KNIGHT (new DarkKnight()),
    GRIM_REAPER (new GrimReaper()),
    BLACK_DRAGON (new BlackDragon()),
    DEMON_KING (new DemonKing());

    private Kit kit;

    KitType(Kit kit) {
        this.kit = kit;
    }

    public Kit getKit() {
        return kit;
    }
}
