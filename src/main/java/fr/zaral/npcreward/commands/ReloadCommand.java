package fr.zaral.npcreward.commands;

import fr.zaral.npcreward.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zaral on 28/04/2016.
 */
public class ReloadCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("npcreward.admin")) {
                sender.sendMessage(Lang.NOPERM);
                return true;
            }
            Lang.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "[NpcReward] Messages have been relaoded !");
        }
        return true;
    }


}
