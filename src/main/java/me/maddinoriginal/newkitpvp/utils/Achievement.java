package me.maddinoriginal.newkitpvp.utils;

public enum Achievement {

    FIRST_JOIN ("Welcome to the game", "Join KitPvP for the first time", 5),
    ALL_POWERUPS ("Collect 'em all", "Collect every PowerUp at least once", 10),
    KILL_STREAK_3 ("Jack The Ripper", "Achieve a streak of 3 kills without dying", 10),
    BUY_KIT ("New Kit who dis?", "Buy a kit to unlock it permanently", 5),
    MILLION_COINS ("Who wants to be a Millionaire?", "Collect an overall amount of 1 million coins", 25),
    DOUBLE_KILL ("Double Kill!", "Kill two players in short succession", 10)
    ;

    private String name;
    private String description;
    private int points;

    Achievement(String name, String description, int points) {
        this.name = name;
        this.description = description;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPoints() {
        return points;
    }
}
