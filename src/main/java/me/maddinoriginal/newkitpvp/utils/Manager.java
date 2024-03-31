package me.maddinoriginal.newkitpvp.utils;

public class Manager {

    private Manager() {
        //setup();
    }

    private static class SingletonHelper {
        private static final Manager INSTANCE = new Manager();
    }

    public static Manager getInstance() {
        return Manager.SingletonHelper.INSTANCE;
    }

}
