package fr.zaral.npcreward;

import fr.zaral.npcreward.utils.CodeUtils;
import fr.zaral.npcreward.utils.ConfigAccessor;

import org.bukkit.ChatColor;
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
    public static String PICKLEFT;
    private static ConfigAccessor langConfig = null;
    private static FileConfiguration config = null;

    public static void initMessages() {
        CodeUtils.logToConsole("Loading messages...");
        langConfig = new ConfigAccessor(NpcReward.getInstance(), "messages.yml");
        langConfig.saveDefaultConfig();
        config = langConfig.getConfig();
        NOPERM = translateColor(config.getString("NoPermission"));
        ITEM_RECEIVED = translateColor(config.getString("ItemRewardReceived"));
        NOSPACE = translateColor(config.getString("NotEnoughtSpace"));
        PLAYERSNEARBY = translateColor(config.getString("PlayerNearby"));
        SENDERPLAYERSNEARBY = translateColor(config.getString("SenderPlayerNearby"));
        SENDERNOSPACE = translateColor(config.getString("SenderNotEnoughtSpace"));
        TITLE = translateColor(config.getString("Title"));
        SUBTITLE = translateColor(config.getString("Subtitle"));
        PICKLEFT = translateColor(config.getString("PickLeft"));
    }

    public static void reloadConfig() {
        langConfig.reloadConfig();
        initMessages();
    }
    
    private static String translateColor(String msg) {
    	if (msg == null) return null;
    	return ChatColor.translateAlternateColorCodes('&', msg);
    }


}
