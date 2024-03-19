package me.maddinoriginal.newkitpvp.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinKitPvPEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private Player player;

    public PlayerJoinKitPvPEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
