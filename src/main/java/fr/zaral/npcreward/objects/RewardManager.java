package fr.zaral.npcreward.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

import fr.zaral.npcreward.NpcReward;
import fr.zaral.npcreward.utils.CodeUtils;
import fr.zaral.npcreward.utils.ConfigAccessor;
import lombok.Getter;

public class RewardManager {

	private static RewardManager instance = null;
	@Getter
	private ArrayList<Reward> rewardList = new ArrayList<>();
	private ConfigAccessor rewardConfig = null;
	public static RewardManager get() {
		if (instance == null) return new RewardManager();
		return instance;
	}
	
	public RewardManager() {
		instance = this;
	}
	
	public void loadRewards() {
		rewardConfig = new ConfigAccessor(NpcReward.getInstance(), "reward.yml");
		rewardConfig.saveDefaultConfig();
		FileConfiguration config = rewardConfig.getConfig();
		Set<String> set = config.getConfigurationSection("Reward").getKeys(false);
		String[] list = set.toArray(new String[set.size()]);
		for (String l : list) {
			String path = "Reward." + l + ".";
			String name = config.getString(path + "name");
			List<String> cmdList = config.getStringList(path + "cmdList");
			List<String> itemList = config.getStringList(path + "itemReward");
			String gMsg = config.getString(path + "globalMessage");
			String pMsg = config.getString(path + "privateMessage");
			this.rewardList.add(new Reward(name, cmdList, itemList, gMsg, pMsg));
			CodeUtils.logToConsole("Reward '" + name + "' added !");
		}
	}
	
	public Reward getRandomReward() {
		int random = CodeUtils.randomInt(0, rewardList.size()-1);
		return rewardList.get(random);
	}
	
	
}
