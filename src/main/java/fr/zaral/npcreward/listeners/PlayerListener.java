package fr.zaral.npcreward.listeners;

import fr.zaral.npcreward.Lang;
import fr.zaral.npcreward.NpcReward;
import fr.zaral.npcreward.Settings;
import fr.zaral.npcreward.npc.Npc;
import fr.zaral.npcreward.objects.Stage;
import fr.zaral.npcreward.objects.StageManager;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Zaral on 28/04/2016.
 */
public class PlayerListener implements Listener {

	private NpcReward pl;

	public PlayerListener(NpcReward pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (StageManager.get().isInStage(player) != null) {
			if (event.getFrom().distance(event.getTo()) > 0.05) {
				event.setTo(event.getFrom());
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		StageManager sm = StageManager.get();
		Stage stage =  sm.isInStage(player);
		ItemStack clicked = event.getItem();
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			if (stage != null) {
				event.setCancelled(true);
				return;
			} else if (clicked != null && clicked.hasItemMeta()) {
				if (clicked.equals(pl.getSettings().getItemReward())) {
					event.setCancelled(true);
					if (player.hasPermission("npcreward.item.use")) {
						if (player.isOnGround()) {
							player.sendMessage(Lang.ISNOTONGROUND);
							return;
						}
						sm.newStage(player, null, true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractEntityEvent event ) {
		Player player = event.getPlayer();
		Stage stage = StageManager.get().isInStage(player);
		Entity clicked = event.getRightClicked();
		if (stage != null) {
			event.setCancelled(true);
			if (clicked instanceof Villager) {
				Npc npc = stage.getNpc(clicked.getName(), player);
				if (npc != null && stage.isCanPick()) {
					stage.pick(npc);
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			StageManager sm = StageManager.get();
			if (sm.isInStage(player) != null && !Settings.get().isDamageEnabledStage()) {
				event.setCancelled(true);
			}
		}
	}

}
