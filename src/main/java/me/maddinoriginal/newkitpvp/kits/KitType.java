package me.maddinoriginal.newkitpvp.kits;

import me.maddinoriginal.newkitpvp.kits.standardkits.Archer;
import me.maddinoriginal.newkitpvp.kits.standardkits.Swordsman;
import me.maddinoriginal.newkitpvp.kits.standardkits.Tank;

public enum KitType {

    NONE (null),

    //Standard Kits
    SWORDSMAN (new Swordsman()),
    ARCHER (new Archer()),
    TANK (new Tank()),
    //HUNTER (new HunterKit()),
    //BARBARIAN (new BarbarianKit()),

    //Advanced Kits
    //PYRO (new PyroKit()),
    //TELEPORTER (),
    //NINJA (),
    //ASSASSIN (),
    //ELEMENTALIST (),
    //ARBALIST (),
    //GHOST (),
    //BLASTER (),
    //YETI (),
    //BOMBERMAN (),
    //FARMER (),
    //FISHERMAN (),
    //POSEIDON (),
    //BOMBARCHER (),
    //MAD_SCIENTIST (),

    //Legendary Kits
    //DARK_KNIGHT (),
    //GRIM_REAPER (),
    //BLACK_DRAGON (),
    ;

    private Kit kit;

    KitType(Kit kit) {
        this.kit = kit;
    }

    public Kit getKit() {
        return kit;
    }
}
