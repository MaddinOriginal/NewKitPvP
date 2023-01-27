package me.maddinoriginal.newkitpvp.abilities;

import java.util.UUID;

public enum AbilityType {

    //Standard Kit Abilities
    DASH (new DashAbility()),                           //Swordsman Kit
    //MINIGUN (new MinigunAbility()),                   //Archer Kit
    //HEALAURA,
    PLANT_BUSH (new PlantBushAbility()),                //Hunter Kit
    //SHIELD,

    //Advanced Kit Abilities
    MAGMA_LAUNCHER(new MagmaLauncherAbility()),         //Pyro Kit
    TELEPORT_FORWARD (new TeleportForwardAbility()),    //Teleporter Kit
    TELEPORT_BEHIND (new TeleportBehindAbility()),      //Ninja Kit
    //ENDERARROW,
    SCARE_OFF (new ScareOffAbility()),                  //Ghost Kit
    AIRSTREAM (new AirStreamAbility()),                 //Feather Kit
    //RAGE,
    //DETONATE,

    //Other (Shop Items)
    //SMASHER,
    ;

    private Ability ability;
    private UUID id;

    AbilityType(Ability ability) {
        this.ability = ability;
        this.id = UUID.randomUUID();
    }

    public Ability getAbility() {
        return ability;
    }
    public UUID getId() {
        return id;
    }
}
