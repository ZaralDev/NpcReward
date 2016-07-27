package fr.zaral.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import fr.zaral.npcreward.npc.Npc;
import fr.zaral.npcreward.objects.Reward;
import fr.zaral.npcreward.objects.Stage;
import lombok.Getter;
import lombok.Setter;

public class PlayerPickEvent  extends NpcRewardEvent {

	@Getter
	private final Player target;
	@Getter
	private final Stage stage;
	@Getter
	@Setter
	private Reward reward;
	@Getter
	private final Npc npc;
	
	public PlayerPickEvent(Stage stage, Player target, Reward reward, Npc npc) {
		this.stage = stage;
		this.target = target;
		this.reward = reward;
		this.npc = npc;
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
