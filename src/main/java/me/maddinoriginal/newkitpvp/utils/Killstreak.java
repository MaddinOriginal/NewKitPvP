package me.maddinoriginal.newkitpvp.utils;

public enum Killstreak {

    TIER1 ("On A Killing Spree", 3),
    TIER2 ("On A Rampage", 5), // 6 maybe?
    TIER3 ("Dominating", 8), // 9 maybe?
    TIER4 ("Unstoppable", 12),
    TIER5 ("Superhuman", 16), // Supernatural instead?
    TIER6 ("Out Of This World", 20),
    TIER7 ("Godlike", 25),
    TIER8 ("Legendary", 30),
    //TIER9 ("A Literal God", 40),
    //TIER10 ("A True Legend", 50),
    ;

    private String name;
    private int streakCount;

    Killstreak(String name, int streakCount) {
        this.name = name;
        this.streakCount = streakCount;
    }
}
