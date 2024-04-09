package me.maddinoriginal.newkitpvp.abilityitems;

import me.maddinoriginal.newkitpvp.abilityitems.abilities.*;

import java.util.UUID;

public enum AbilityType {

    //Standard Kit Abilities
    DASH (new DashAbility()),                           //Swordsman Kit
    MINIGUN (new MinigunAbility()),                     //Archer Kit
    SHIELDS (new SurroundingShielsAbility()),           //Tank Kit
    HOMING_ARROWS (new HomingArrowsAbility()),          //Marksman Kit
    AXE_THROW (new AxeThrowAbility()),                  //Barbarian Kit

    //Advanced Kit Abilities
    MAGMA_LAUNCHER (new MagmaLauncherAbility()),        //Pyro Kit
    TELEPORT_FORWARD (new TeleportForwardAbility()),    //Teleporter Kit
    TELEPORT_BEHIND (new TeleportBehindAbility()),      //Assassin Kit
    //ENDERARROW,
    SCARE_OFF (new ScareOffAbility()),                  //Ghost Kit
    AIRSTREAM (new AirStreamAbility()),                 //Feather Kit
    SNOWSTORM (new SnowstormAbility()),                 //Yeti Kit
    DETONATE (new DetonationAbility()),                 //Bomber Kit
    THROW_POTION (new ThrowNegativePotionAbility()),    //Elementalist Kit
    WOLF_HUNT (new WolfHuntAbility()),                  //Unused (previously Hunter Kit)
    EVOKER_FANGS (new EvokerFangAbility()),             //Hunter Kit
    PLANT_BUSH (new PlantBushAbility()),                //Ranger Kit
    DEMON_CIRCLE (new DemonCircleAbility()),            //Demon Kit (?)

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
