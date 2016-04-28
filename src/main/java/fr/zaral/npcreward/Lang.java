package fr.zaral.npcreward;

import fr.zaral.npcreward.utils.CodeUtils;
import fr.zaral.npcreward.utils.ConfigAccessor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Zaral on 28/04/2016.
 */
public class Lang {

    public static String NOPERM;
    public static String ITEM_RECEIVED;
    public static String NOSPACE;
    public static String PLAYERSNEARBY;
    public static String SENDERNOSPACE;
    public static String SENDERPLAYERSNEARBY;
    public static String TITLE;
    public static String SUBTITLE;
    private static ConfigAccessor langConfig = null;
    private static FileConfiguration config = null;

    public static void initMessages() {
        CodeUtils.logToConsole("Loading messages...");
        langConfig = new ConfigAccessor(NpcReward.getInstance(), "messages.yml");
        langConfig.saveDefaultConfig();
        config = langConfig.getConfig();
        NOPERM = config.getString("NoPermission");
        ITEM_RECEIVED = config.getString("ItemRewardReceived");
        NOSPACE = config.getString("NotEnoughtSpace");
        PLAYERSNEARBY = config.getString("PlayerNearby");
        SENDERPLAYERSNEARBY = config.getString("SenderPlayerNearby");
        SENDERNOSPACE = config.getString("SenderNotEnoughtSpace");
        TITLE = config.getString("Title");
        SUBTITLE = config.getString("Subtitle");
    }

    public static void reloadConfig() {
        langConfig.reloadConfig();
        initMessages();
    }


}
