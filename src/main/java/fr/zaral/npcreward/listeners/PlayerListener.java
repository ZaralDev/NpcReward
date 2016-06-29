package fr.zaral.npcreward.listeners;

import fr.zaral.npcreward.Lang;
import fr.zaral.npcreward.NpcReward;
import fr.zaral.npcreward.Settings;
import fr.zaral.npcreward.npc.Npc;
import fr.zaral.npcreward.objects.Stage;
import fr.zaral.npcreward.objects.StageManager;
import fr.zaral.npcreward.utils.PlayerUtils;

import org.bukkit.Location;
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
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

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
		} else {
			for (Stage i : StageManager.get().getStageList()) {
				Location mainLoc = i.getLocation();
				Location corner1 = new Location(mainLoc.getWorld(), mainLoc.getBlockX() + 4, mainLoc.getBlockY(), mainLoc.getBlockZ() +4 );
				Location corner2 = new Location(mainLoc.getWorld(), mainLoc.getBlockX() - 4, mainLoc.getBlockY(), mainLoc.getBlockZ() -4 );
				if (PlayerUtils.isInside(player.getLocation(), corner1, corner2)) {
					double dX = player.getLocation().getX() - mainLoc.getX();
					//double dY = player.getLocation().getY() - mainLoc.getY();
					double dZ = player.getLocation().getZ() - mainLoc.getZ();
					double yaw = - Math.atan2(dZ, dX) ;
					double pitch = 2;
					double x = Math.sin(pitch) * Math.cos(yaw);
					double z = Math.sin(pitch) * Math.sin(yaw);
					double y = Math.cos(pitch);
					
					Vector vector = new Vector(x, y, z);
					player.setVelocity(vector.multiply(1.001));
				}
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
				if (clicked.getItemMeta().getDisplayName().equals(pl.getSettings().getItemReward().getItemMeta().getDisplayName())) {

					event.setCancelled(true);
					if (player.hasPermission("npcreward.item.use")) {
						if (!player.isOnGround()) {
							player.sendMessage(Lang.ISNOTONGROUND);
							return;
						}

						if (sm.newStage(player, null, true)) {
							if (player.getItemInHand().getAmount() != 1) {
								player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
							} else {
								player.setItemInHand(null);
							}
						}
					} else {
						player.sendMessage(Lang.NOPERM);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Stage stage = StageManager.get().isInStage(player);
		if (stage != null) {
			stage.removeStageNoCd();
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
		} else if (clicked instanceof Villager) {
			for (String name : Settings.get().getNpcNames()) {
				if (clicked.getName().equals(name)) {
					event.setCancelled(true);

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
