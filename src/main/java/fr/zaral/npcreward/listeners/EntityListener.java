package fr.zaral.npcreward.listeners;

import fr.zaral.npcreward.NpcReward;
import fr.zaral.npcreward.objects.StageManager;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

/**
 * Created by Zaral on 28/04/2016.
 */
public class EntityListener implements Listener {

	private NpcReward pl;

	public EntityListener(NpcReward pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (StageManager.get().isInStage(player) != null) {
				event.setCancelled(true);
			}
		} else if (event.getEntity().getType().equals(EntityType.VILLAGER)) {
			Entity entity = event.getEntity();
			for (String name : pl.getSettings().getNpcNames()) {
				if (name.equals(event.getEntity().getCustomName())) {
					if (event instanceof EntityDamageByEntityEvent) {
						EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
						Entity damager = e.getDamager();
						double dX = damager.getLocation().getX() - entity.getLocation().getX();
						//double dY = player.getLocation().getY() - mainLoc.getY();
						double dZ = damager.getLocation().getZ() - entity.getLocation().getZ();
						double yaw = - Math.atan2(dZ, dX) ;
						double pitch = 2;
						double x = Math.sin(pitch) * Math.cos(yaw);
						double z = Math.sin(pitch) * Math.sin(yaw);
						double y = Math.cos(pitch);

						Vector vector = new Vector(x, y, z);
						damager.setVelocity(vector.multiply(1.001));					}
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onEntityFight(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player)event.getDamager();
			if (StageManager.get().isInStage(player) != null) {
				event.setCancelled(true);
			}
		}
	}
}
