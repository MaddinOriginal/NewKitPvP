package me.maddinoriginal.newkitpvp.utils;

public enum Killstreak {

    TIER1 ("On A Killing Spree", 3),
    TIER2 ("On A Rampage", 6),
    TIER3 ("Dominating", 9),
    TIER4 ("Unstoppable", 12),
    TIER5 ("Superhuman", 16),
    TIER6 ("Out Of This World", 20),
    TIER7 ("Godlike", 25),
    TIER8 ("Legendary", 30),
    //TIER9 ("A God", 40),
    //TIER10 ("A Legend", 50),
    ;

    private String name;
    private int streakCount;

    Killstreak(String name, int streakCount) {
        this.name = name;
        this.streakCount = streakCount;
    }
}
