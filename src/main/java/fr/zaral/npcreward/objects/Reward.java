package fr.zaral.npcreward.objects;

import fr.zaral.npcreward.utils.CodeUtils;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
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
    private List<String> command = new ArrayList<String>();
    @Getter
    private List<String> itemList = new ArrayList<String>();
    @Getter
    private String privateMsg, globalMsg;
    
    public Reward(String rewardName, List<String> commandList, List<String> itemList
    		, String globalMessage, String privateMessage) {
        this.name = rewardName;
        this.command = commandList;
        this.itemList = itemList;
        this.privateMsg = privateMessage;
        this.globalMsg = globalMessage;
    }

    public void setReward(Player player) {
        for (String item : itemList) {
            String[] itemSplit = item.split(" ");
            if (itemSplit[1] == null) {
                ItemStack itemStack = new ItemStack(Material.getMaterial(itemSplit[0]));
                player.getInventory().addItem(itemStack);
            } else {
                ItemStack itemStack = new ItemStack(Material.getMaterial(itemSplit[0]), Integer.parseInt(itemSplit[1]));
                player.getInventory().addItem(itemStack);
            }
        }

        for (String cmd : command) {
            CodeUtils.runConsoleCommand(ChatColor.translateAlternateColorCodes('&', cmd.replaceAll("%p", player.getName()).replaceAll("%n", this.name)));
        }
        
        if (globalMsg != null && !globalMsg.equals("")) {
        	Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.globalMsg.replaceAll("%p", player.getName()).replaceAll("%n", this.name)));
        }
        
        if (privateMsg != null && !privateMsg.equals("")) {
        	player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.privateMsg.replaceAll("%p", player.getName()).replaceAll("%n", this.name)));
        }
    }
}
