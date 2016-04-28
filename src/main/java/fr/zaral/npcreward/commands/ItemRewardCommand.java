package fr.zaral.npcreward.commands;

import fr.zaral.npcreward.Lang;
import fr.zaral.npcreward.NpcReward;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Zaral on 28/04/2016.
 */
public class ItemRewardCommand implements CommandExecutor {

    private final NpcReward pl;

    public ItemRewardCommand(NpcReward pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("npcreward.admin")) {
                player.sendMessage(Lang.NOPERM);
                return true;
            }
        } else {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "You can't give item to Console !");
                return true;
            }
        }

        if (args.length == 0) {
            ((Player)sender).getInventory().addItem(pl.getSettings().getItemReward());
            sender.sendMessage(Lang.ITEM_RECEIVED);
        } else {
            Player target = (Player) sender;
            target.getInventory().addItem(pl.getSettings().getItemReward());
            target.sendMessage(Lang.ITEM_RECEIVED);
            sender.sendMessage("Reward successfully sent !");
        }


        return true;
    }
}
