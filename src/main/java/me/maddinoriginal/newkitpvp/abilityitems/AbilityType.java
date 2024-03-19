package me.maddinoriginal.newkitpvp.abilityitems;

import me.maddinoriginal.newkitpvp.abilityitems.abilities.*;

import java.util.UUID;

public enum AbilityType {

    //Standard Kit Abilities
    DASH (new DashAbility()),                           //Swordsman Kit
    MINIGUN (new MinigunAbility()),                   //Archer Kit
    //HEALAURA,
    PLANT_BUSH (new PlantBushAbility()),                //Hunter Kit
    //SHIELD,

    //Advanced Kit Abilities
    MAGMA_LAUNCHER (new MagmaLauncherAbility()),         //Pyro Kit
    TELEPORT_FORWARD (new TeleportForwardAbility()),    //Teleporter Kit
    TELEPORT_BEHIND (new TeleportBehindAbility()),      //Ninja Kit
    //ENDERARROW,
    SCARE_OFF (new ScareOffAbility()),                  //Ghost Kit
    AIRSTREAM (new AirStreamAbility()),                 //Feather Kit
    //RAGE,
    DETONATE (new DetonationAbility()),
    THROW_POTION (new ThrowNegativePotionAbility()),
    WOLF_HUNT (new WolfHuntAbility()),

    //Other (Shop Items and Powerups)
    //SMASHER,
    FORCE_FIELD (new ForcefieldAbility()),
    LIGHTNING_FIELD (new LightningfieldAbility()),
    COPPER_CANNON (new ShootBulletsAbility()),
    ;

    private Ability ability;
    //private UUID id;

    AbilityType(Ability ability) {
        this.ability = ability;
        //this.id = UUID.randomUUID();
    }

    public Ability getAbility() {
        return ability;
    }
}
