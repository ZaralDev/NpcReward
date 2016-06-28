package fr.zaral.npcreward.commands;

import fr.zaral.npcreward.NpcReward;

/**
 * Created by Zaral on 28/04/2016.
 */
public class CommandManager {

    private NpcReward pl;

    public CommandManager(NpcReward pl) {
        this.pl = pl;
        loadCommands();
    }

    private void loadCommands() {
        pl.getCommand("itemreward").setExecutor(new ItemRewardCommand(pl));
        pl.getCommand("rewardreloadconfig").setExecutor(new ReloadCommand());
    }


}
