package fr.zaral.npcreward.objects;

import fr.zaral.npcreward.Lang;
import fr.zaral.npcreward.NpcReward;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static fr.zaral.npcreward.utils.BlockUtils.getAllBlockInAreaByType;

/**
 * Created by Zaral on 28/04/2016.
 */
public class StageManager {

	private static StageManager instance;
    @Getter
    private ArrayList<Stage> stageList = new ArrayList<Stage>();
    @Getter
    private ArrayList<Reward> rewardList = new ArrayList<Reward>();

    public static StageManager get() {
    	if (instance == null) return new StageManager();
    	return instance;
    }
    
    public StageManager() {
    	instance = this;
    }

    public Stage isInStage(Player player) {
        for (Stage stage : stageList) {
            if (player.equals(stage.getPlayer())) {
                return stage;
            }
        }
        return null;
    }
    private boolean startNewStage(Player player, @Nullable CommandSender sender) {
        World world = player.getWorld();
        Location center = player.getLocation();
        boolean cantPlace = false;
        Location plinthLoc = new Location(world, center.getBlockX(), center.getY() + 0, center.getBlockZ());
        int xMin = plinthLoc.getBlockX() - 2;
        int xMax = plinthLoc.getBlockX() + 2;
        int zMin = plinthLoc.getBlockZ() - 2;
        int zMax = plinthLoc.getBlockZ() + 2;
        int y = plinthLoc.getBlockY();
        List<Block> checkBlock = getAllBlockInAreaByType(xMin, xMax, zMin, zMax, y, player);
        for (int i = 0 ; i < checkBlock.size() ; i++) {
        	Material type = checkBlock.get(i).getType();
            if (!type.equals(Material.AIR)) {
            	if (type.equals(Material.LONG_GRASS) || type.equals(Material.YELLOW_FLOWER))
            		continue;
                cantPlace = true;
            }
        }

        boolean nearbyPlayer = false;
        List<Entity> listEntity = player.getNearbyEntities(5,5,5);
        for (int i = 0 ; i < listEntity.size(); i++) {
            if (listEntity.get(i).getType() == EntityType.PLAYER) {
                Player p = (Player) listEntity.get(i);
                if (!p.getName().equals(player.getName())) {
                    nearbyPlayer = true;
                }
            }
        }
        if (cantPlace) {
            if (sender != null)
                sender.sendMessage(Lang.SENDERNOSPACE);
            player.sendMessage(Lang.NOSPACE);
            return false;
        } else if (nearbyPlayer) {
            if (sender != null)
                sender.sendMessage(Lang.SENDERPLAYERSNEARBY);
            player.sendMessage(Lang.PLAYERSNEARBY);
            return false;
        }
        return true;
    }

    public boolean newStage(Player player, @Nullable CommandSender sender, boolean withItem) {
        if (startNewStage(player, sender)) {
            if (withItem) {
                ItemStack item = null;
                int slot = 0;
                boolean ok = false;
                for (int i = 0; i < player.getInventory().getContents().length; i++) {
                    if (ok) break;
                    ItemStack itemStack = player.getInventory().getItem(i);
                    if (itemStack != null && itemStack.hasItemMeta()) {
                        if (itemStack.equals(NpcReward.getInstance().getSettings().getItemReward())) {
                            item = itemStack;
                            slot = i;
                            ok = true;
                        }
                    }
                }
                if (ok) {
                    int amount = item.getAmount();
                    if (amount == 1) {
                        player.getInventory().remove(item);
                    } else {
                        player.getInventory().getItem(slot).setAmount(amount--);
                    }
                }
            }
            stageList.add(new Stage(player));
            return true;
        } 
        return false;
    }
    
    public void removeStage(Stage stage) {
    	if (stageList.contains(stage)) {
    		this.stageList.remove(stage);
    	}
    }
}
