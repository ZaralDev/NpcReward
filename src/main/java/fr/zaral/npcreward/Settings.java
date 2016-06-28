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

    private NpcReward pl;
    private FileConfiguration config;

    @Getter
    private ItemStack itemReward = null;

    @Getter
    private Villager.Profession profession = null;

    @Getter
    private String[] npcNames = new String[3];

    public Settings(NpcReward pl) {
        this.pl = pl;
        load();
    }

    private void load() {
        pl.saveDefaultConfig();
        config = pl.getConfig();


        @SuppressWarnings("unchecked")
		List<String> lore = (List<String>) config.getList("CustomItem.Lore");
        for (int i = 0; i < lore.size(); i++) {
            lore.get(i).replace("&", "§");
        }
        CodeUtils.createItemStack(config.getString("CustomItem.Type"), config.getString("CustomItem.Name").replace("&", "§"), lore);

        npcNames[0] = config.getString("CustomNames.name1").replace("&", "§");
        npcNames[1] = config.getString("CustomNames.name2").replace("&", "§");
        npcNames[2] = config.getString("CustomNames.name3").replace("&", "§");
        npcNames[3] = config.getString("CustomNames.name4").replace("&", "§");

        profession = Villager.Profession.valueOf(config.getString("NpcType"));

    }


}
