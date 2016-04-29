package fr.zaral.npcreward.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaral on 28/04/2016.
 */
public class CodeUtils {

    public static void logToConsole(String message) {
        Bukkit.getLogger().info("[NpcReward] " + message);
    }


    public static ItemStack createItemStack(String itemStack, String customName, List<String> lore) {
        ItemStack rewardItem = new ItemStack(Material.getMaterial(itemStack));
        ItemMeta itemMeta = rewardItem.getItemMeta();

        List<String> listWithColor = new ArrayList<String>();
        for (String desc : lore) {
            listWithColor.add(ChatColor.translateAlternateColorCodes('&', desc));
        }
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', customName));
        itemMeta.setLore(listWithColor);
        rewardItem.setItemMeta(itemMeta);
        return rewardItem;
    }

    public static void runConsoleCommand(String command) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
