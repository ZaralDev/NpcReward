package fr.zaral.npcreward.npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

/**
 * Created by Zaral on 28/04/2016.
 */
public class NpcManager {

	private static NpcManager instance;

	public static NpcManager get() {
		if (instance == null) return new NpcManager();
		return instance;
	}

	public NpcManager() {
		instance = this;
	}


	public Npc spawnNpc(String name, Location location, Villager.Profession profession, Player target) {
		Npc npc = new Npc(name, location, profession, target);
		npc.spawn(target);
		return npc;
	}


	
	public Npc spawnNpc(String name, Location location, Player target) {
		Npc npc = new Npc(name, location, target);
		npc.spawn(target);
		return npc;
	}

}
