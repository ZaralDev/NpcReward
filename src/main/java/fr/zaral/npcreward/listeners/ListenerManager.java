package fr.zaral.npcreward.listeners;

import fr.zaral.npcreward.NpcReward;
import org.bukkit.Bukkit;

/**
 * Created by Zaral on 28/04/2016.
 */
public class ListenerManager {

    private NpcReward pl;

    public ListenerManager(NpcReward pl) {
        this.pl = pl;
        load();
    }

    private void load() {
        Bukkit.getPluginManager().registerEvents(new BlockListener(pl), pl);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(pl), pl);
    }
}
