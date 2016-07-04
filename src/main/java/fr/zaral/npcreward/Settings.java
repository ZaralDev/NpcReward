package fr.zaral.npcreward;

import fr.zaral.npcreward.utils.CodeUtils;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Zaral on 28/04/2016.
 */
public class Settings {

	private static Settings settings = null;
    private NpcReward pl;
    private FileConfiguration config;

    @Getter
    private ItemStack itemReward = null;

    @Getter
    private int maxPick = 2;
    @Getter
    private Villager.Profession profession = null;

    @Getter
    private String[] npcNames = new String[4];
    
    @Getter
    private boolean damageEnabledStage = false;
    
    @Getter
    private boolean use2Blocks = false;
    
    @Getter
    private String firstBlock, secondBlock;
    
    @Getter
    private int percent;
    
    public static Settings get() {
    	if (settings == null) return new Settings(NpcReward.getInstance());
    	return settings;
    }
    public Settings(NpcReward pl) {
        this.pl = pl;
        
        load();
    }

    public void load() {
        pl.saveDefaultConfig();
        config = pl.getConfig();


        @SuppressWarnings("unchecked")
		List<String> lore = (List<String>) config.getList("CustomItem.Lore");
        for (int i = 0; i < lore.size(); i++) {
            lore.get(i).replace("&", "§");
        }
        itemReward = CodeUtils.createItemStack(config.getString("CustomItem.Type"), config.getString("CustomItem.Name").replace("&", "§"), lore);
        npcNames[0] = config.getString("CustomNames.name1").replace("&", "§");
        npcNames[1] = config.getString("CustomNames.name2").replace("&", "§");
        npcNames[2] = config.getString("CustomNames.name3").replace("&", "§");
        npcNames[3] = config.getString("CustomNames.name4").replace("&", "§");
        profession = Villager.Profession.valueOf(config.getString("NpcType"));
        maxPick = config.getInt("MaxPick");
        damageEnabledStage = config.getBoolean("DamageEnabledOnStage");
        firstBlock = config.getString("BlockOnGroundID");
        use2Blocks = config.getBoolean("Use2block");
        if (use2Blocks) {
        	percent = config.getInt("PercentOfSecondBlock");
        	secondBlock = config.getString("SecondBlockID");
        }
        
    }
    
}
