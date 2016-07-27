package fr.zaral.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import fr.zaral.npcreward.objects.Stage;
import lombok.Getter;

public class RewardEndEvent extends NpcRewardEvent {

	@Getter
	private final Player target;
	@Getter
	private Stage stage;
	
	public RewardEndEvent(Stage stage, Player target) {
		this.stage = stage;
		this.target = target;
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
