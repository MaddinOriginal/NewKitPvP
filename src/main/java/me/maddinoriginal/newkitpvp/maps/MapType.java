package me.maddinoriginal.newkitpvp.maps;

public enum MapType {

    SKY_ISLANDS ("Sky Islands", "MaddinOriginal"),
    ;

    private String name;
    private String creator;

    MapType(String mapName, String mapCreator) {
        this.name = mapName;
        this.creator = mapCreator;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }
}
