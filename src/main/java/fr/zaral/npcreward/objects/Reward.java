package fr.zaral.npcreward.objects;

import fr.zaral.npcreward.utils.CodeUtils;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaral on 30/04/2016.
 */
public class Reward {

    @Getter
    private String name;
    @Getter
    private boolean rare = false;
    @Getter
    private List<String> command = new ArrayList<String>();
    @Getter
    private List<String> itemList = new ArrayList<String>();

    //TODO Send message when a player get reward
    public Reward(String rewardName, boolean rare, List<String> commandList, List<String> itemList) {
        this.name = rewardName;
        this.rare = rare;
        this.command = commandList;
        this.itemList = itemList;
    }

    public void setReward(Player player) {
        for (String item : itemList) {
            String[] itemSplit = item.split(" ");
            if (itemSplit[1] == null) {
                ItemStack itemStack = new ItemStack(Material.getMaterial(itemSplit[0]));
                if (itemStack == null) {
                    player.sendMessage(ChatColor.RED + "[NpcReward] An error occurred please contact an Administrator");
                    return;
                } else
                    player.getInventory().addItem(itemStack);
            } else {
                ItemStack itemStack = new ItemStack(Material.getMaterial(itemSplit[0]), Integer.parseInt(itemSplit[1]));
                if (itemStack == null) {
                    player.sendMessage(ChatColor.RED + "[NpcReward] An error occurred please contact an Administrator");
                    return;
                } else
                    player.getInventory().addItem(itemStack);
            }
        }

        for (String cmd : command) {
            CodeUtils.runConsoleCommand(cmd.replaceAll("%p", player.getName()));
        }
    }
}
