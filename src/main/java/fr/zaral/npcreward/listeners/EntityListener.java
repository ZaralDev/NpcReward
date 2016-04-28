package fr.zaral.npcreward.listeners;

import fr.zaral.npcreward.NpcReward;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

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
            if (pl.getStageManager().isInStage(player) != null) {
                event.setCancelled(true);
            }
        } else if (event.getEntity().getType().equals(EntityType.VILLAGER)) {
            for (String name : pl.getSettings().getNpcNames()) {
                if (name.equals(event.getEntity().getCustomName())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityFight(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player)event.getDamager();
            if (pl.getStageManager().isInStage(player) != null) {
                event.setCancelled(true);
            }
        }
    }
}
