package fr.zaral.npcreward;

import fr.zaral.npcreward.npc.NpcManager;
import fr.zaral.npcreward.utils.CodeUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Zaral on 28/04/2016.
 */
public class NpcReward extends JavaPlugin {

    @Getter
    private static NpcReward instance;
    @Getter
    private NpcManager npcManager;
    @Getter
    private Settings settings;

    @Override
    public void onEnable() {
        CodeUtils.logToConsole("Loading plugin...");
        instance = this;
        npcManager = new NpcManager(this);

        CodeUtils.logToConsole("Loading settings...");
        settings = new Settings(this);
        CodeUtils.logToConsole("Loading messages");
        Lang.initMessages();


    }

    @Override
    public void onDisable() {

    }
}
