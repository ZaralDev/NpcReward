package fr.zaral.npcreward.listeners;

import fr.zaral.npcreward.objects.Stage;
import fr.zaral.npcreward.objects.StageManager;

import java.util.List;

import org.bukkit.block.Block;
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
		} else {
			Block block = event.getBlock();
			List<Stage> stage = StageManager.get().getStageList();
			stage.forEach(s-> {
				if (s.getBlocklist().contains(block)) {
					event.setCancelled(true);
					return;
				}
			});
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (StageManager.get().isInStage(player) != null) {
			event.setCancelled(true);
		} else {
			Block block = event.getBlock();
			List<Stage> stage = StageManager.get().getStageList();
			stage.forEach(s-> {
				if (s.getBlocklist().contains(block)) {
					event.setCancelled(true);
					return;
				}
			});
		}

	}
}
