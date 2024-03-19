package me.maddinoriginal.newkitpvp.utils;

public class Helper {

    private static int currentID = 0;

    public static int nextID() {
        return currentID++;
    }
}
