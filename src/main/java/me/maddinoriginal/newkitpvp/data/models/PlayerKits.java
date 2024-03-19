package me.maddinoriginal.newkitpvp.data.models;

public class PlayerKits {

    private String uuid;
    private boolean arbalist;
    private boolean assassin;
    private boolean blaster;
    private boolean bomber;
    private boolean elementalist;
    private boolean ghost;
    private boolean magician;
    private boolean ninja;
    private boolean pyro;
    private boolean teleporter;
    private boolean yeti;

    public PlayerKits(String uuid, boolean arbalist, boolean assassin, boolean blaster, boolean bomber, boolean elementalist, boolean ghost, boolean magician, boolean ninja, boolean pyro, boolean teleporter, boolean yeti) {
        this.uuid = uuid;
        this.arbalist = arbalist;
        this.assassin = assassin;
        this.blaster = blaster;
        this.bomber = bomber;
        this.elementalist = elementalist;
        this.ghost = ghost;
        this.magician = magician;
        this.ninja = ninja;
        this.pyro = pyro;
        this.teleporter = teleporter;
        this.yeti = yeti;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean hasArbalist() {
        return arbalist;
    }

    public void setArbalist(boolean arbalist) {
        this.arbalist = arbalist;
    }

    public boolean hasAssassin() {
        return assassin;
    }

    public void setAssassin(boolean assassin) {
        this.assassin = assassin;
    }

    public boolean hasBlaster() {
        return blaster;
    }

    public void setBlaster(boolean blaster) {
        this.blaster = blaster;
    }

    public boolean hasBomber() {
        return bomber;
    }

    public void setBomber(boolean bomber) {
        this.bomber = bomber;
    }

    public boolean hasElementalist() {
        return elementalist;
    }

    public void setElementalist(boolean elementalist) {
        this.elementalist = elementalist;
    }

    public boolean hasGhost() {
        return ghost;
    }

    public void setGhost(boolean ghost) {
        this.ghost = ghost;
    }

    public boolean hasMagician() {
        return magician;
    }

    public void setMagician(boolean magician) {
        this.magician = magician;
    }

    public boolean hasNinja() {
        return ninja;
    }

    public void setNinja(boolean ninja) {
        this.ninja = ninja;
    }

    public boolean hasPyro() {
        return pyro;
    }

    public void setPyro(boolean pyro) {
        this.pyro = pyro;
    }

    public boolean hasTeleporter() {
        return teleporter;
    }

    public void setTeleporter(boolean teleporter) {
        this.teleporter = teleporter;
    }

    public boolean hasYeti() {
        return yeti;
    }

    public void setYeti(boolean yeti) {
        this.yeti = yeti;
    }
}
