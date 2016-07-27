package fr.zaral.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import fr.zaral.npcreward.objects.Stage;
import lombok.Getter;

public class RewardStartEvent extends NpcRewardEvent implements Cancellable {

	private boolean cancelled = false;
	@Getter
	private final Player target;
	@Getter
	private Stage stage;
	
	public RewardStartEvent(Stage stage, Player target) {
		this.stage = stage;
		this.target = target;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean paramBoolean) {
		cancelled  = paramBoolean;
		
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private static final HandlerList handlers = new HandlerList();

}
