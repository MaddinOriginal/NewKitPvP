package me.maddinoriginal.newkitpvp.events;

import me.maddinoriginal.newkitpvp.kits.KitType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class KitSelectEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    private final Player player;
    private final KitType kitType;

    public KitSelectEvent(Player player, KitType kitType) {
        this.player = player;
        this.kitType = kitType;
        this.isCancelled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public KitType getKitType() {
        return kitType;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }
}
