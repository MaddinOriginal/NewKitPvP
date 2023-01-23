package me.maddinoriginal.newkitpvp.kits;

public enum KitType {

    //Standard Kits
    //SWORDSMAN (new SwordsmanKit()),
    //ARCHER (new ArcherKit()),
    //TANK (new TankKit()),
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
