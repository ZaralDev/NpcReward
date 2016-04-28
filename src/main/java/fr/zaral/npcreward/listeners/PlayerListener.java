package fr.zaral.npcreward.listeners;

import fr.zaral.npcreward.NpcReward;
import fr.zaral.npcreward.objects.Stage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Stage stage = pl.getStageManager().isInStage(player);
        ItemStack clicked = event.getItem();
        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (stage != null) {
                event.setCancelled(true);
                return;
            } else if (clicked != null && clicked.hasItemMeta()) {
                if (clicked.equals(pl.getSettings().getItemReward())) {
                    event.setCancelled(true);
                    pl.getStageManager().newStage(player, null, true);
                }
            }
        }
    }

}
