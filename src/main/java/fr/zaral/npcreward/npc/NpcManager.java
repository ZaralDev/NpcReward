package fr.zaral.npcreward.npc;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;

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
	
    @Getter
    private ArrayList<Npc> npcList = new ArrayList<>();

    public Npc  spawnNpc(String name, Location location, Villager.Profession profession, Player target) {
        return new Npc(this, name, location, profession, target);
    }

    public Npc getNpc(String name, Player target) {
        for (Npc npc : npcList) {
            if (npc.getName().equals(name) && target.equals(npc.getTarget())) {
                return npc;
            }
        }
        return null;
    }
    public Npc spawnNpc(String name, Location location, Player target) {
        return new Npc(this, name, location, target);
    }

}
