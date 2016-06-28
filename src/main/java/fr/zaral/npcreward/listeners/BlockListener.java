package fr.zaral.npcreward.listeners;

import fr.zaral.npcreward.objects.StageManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Zaral on 28/04/2016.
 */
public class BlockListener implements Listener {


    public BlockListener() {
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (StageManager.get().isInStage(player) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (StageManager.get().isInStage(player) != null) {
            event.setCancelled(true);
        }
    }
}
