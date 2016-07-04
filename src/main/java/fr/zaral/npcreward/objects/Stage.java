package fr.zaral.npcreward.objects;

import fr.zaral.npcreward.Lang;
import fr.zaral.npcreward.NpcReward;
import fr.zaral.npcreward.Settings;
import fr.zaral.npcreward.lib.TitlesLib;
import fr.zaral.npcreward.npc.Npc;
import fr.zaral.npcreward.npc.NpcManager;
import fr.zaral.npcreward.utils.BlockUtils;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaral on 28/04/2016.
 */
public class Stage {

	@Getter
	private Player player;
	@Getter
	private Location location;

	@Getter
	private ArrayList<Block> blocklist = new ArrayList<Block>();
	@Getter
	private ArrayList<Npc> npc = new ArrayList<Npc>();
	@Getter
	private boolean canPick = false;
	@Getter
	private int pickLeft;

	public Stage(Player player) {
		this.player = player;
		this.location = player.getLocation();
		this.pickLeft = Settings.get().getMaxPick();
		startStage();
	}

	private void startStage() {
		createPlinth();
		spawnPnj();
	}

	public boolean containsNpc(Npc npc) {
		for (Npc npcc : this.npc) {
			if (npcc.equals(npc)) {
				return true;
			}
		}
		return false;
	}

	public Npc getNpc(String name, Player target) {
		for (Npc npcd : npc) {
			if (npcd.getName().equals(name) && target.equals(npcd.getTarget())) {
				return npcd;
			}
		}
		return null;
	}

	public Npc getNpc(String name) {
		for (Npc npcd : npc) {
			if (npcd.getName().equals(name)) {
				return npcd;
			}
		}
		return null;
	}


	public void pick(Npc npcc) {
		pickLeft--;
		Reward reward = RewardManager.get().getRandomReward();
		reward.setReward(this.player);
		npc.remove(npcc);
		npcc.delete();
		player.playSound(location, Sound.ORB_PICKUP, 1f, 1f);

		if (pickLeft == 0) {
			removeStage();
		} else {
			player.sendMessage(Lang.PICKLEFT.replaceAll("%p", pickLeft + ""));
		}
	}

	private boolean createPlinth() {
		Settings sg = Settings.get();
		World world = player.getWorld();
		Location plinthLoc = new Location(world, location.getBlockX(), location.getY() + 0, location.getBlockZ());
		Location perfectLoc = new Location(world, location.getBlockX() + 0.5, location.getY() + 1, location.getBlockZ() + 0.5);
		int xMin = plinthLoc.getBlockX() - 2;
		int xMax = plinthLoc.getBlockX() + 2;
		int zMin = plinthLoc.getBlockZ() - 2;
		int zMax = plinthLoc.getBlockZ() + 2;
		int y = plinthLoc.getBlockY();
		List<Block> list = BlockUtils.getAllBlockInArea(xMin, xMax, zMin, zMax, y, player);
		player.teleport(perfectLoc);
		String[] splitedFirst = sg.getFirstBlock().split(":");
		Material firstMat = Material.getMaterial(splitedFirst[0]);
		byte dataF = 0;
		if (splitedFirst.length == 2) {
			dataF = Byte.parseByte(splitedFirst[1]);
		}
		if (sg.isUse2Blocks()) {
			String[] splitedSecond = sg.getSecondBlock().split(":");
			Material secondMat = Material.getMaterial(splitedSecond[0]);
			byte dataS = 0;
			if (splitedSecond.length == 2) {
				dataS = Byte.parseByte(splitedSecond[1]);
			}
			int percent = sg.getPercent();
			BlockUtils.replaceBlock(list, firstMat, dataF, true, secondMat, dataS, percent);

		} else {
		BlockUtils.replaceBlock(list, firstMat, dataF, false, null, dataF, 0);
		}
		blocklist.addAll(list);
		/*
		Bukkit.getScheduler().runTaskLater(NpcReward.getInstance(), new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				world.getBlockAt(new Location(world, plinthLoc.getBlockX() + 1, plinthLoc.getY(), plinthLoc.getBlockZ())).setData((byte) 1);
				world.getBlockAt(new Location(world, plinthLoc.getBlockX(), plinthLoc.getY(), plinthLoc.getBlockZ() - 1)).setData((byte) 1);
				world.getBlockAt(new Location(world, plinthLoc.getBlockX() - 1, plinthLoc.getY(), plinthLoc.getBlockZ())).setData((byte) 1);
				world.getBlockAt(new Location(world, plinthLoc.getBlockX() - 1, plinthLoc.getY(), plinthLoc.getBlockZ() - 1)).setData((byte) 1);
				world.getBlockAt(new Location(world, plinthLoc.getBlockX() - 1, plinthLoc.getY(), plinthLoc.getBlockZ() - 2)).setData((byte) 1);
				world.getBlockAt(new Location(world, plinthLoc.getBlockX() + 1, plinthLoc.getY(), plinthLoc.getBlockZ() - 2)).setData((byte) 1);
				world.getBlockAt(new Location(world, plinthLoc.getBlockX() + 2, plinthLoc.getY(), plinthLoc.getBlockZ() - 2)).setData((byte) 1);
				world.getBlockAt(new Location(world, plinthLoc.getBlockX(), plinthLoc.getY(), plinthLoc.getBlockZ() + 2)).setData((byte) 1);
				world.getBlockAt(new Location(world, plinthLoc.getBlockX() + 1, plinthLoc.getY(), plinthLoc.getBlockZ() + 2)).setData((byte) 1);
				world.getBlockAt(new Location(world, plinthLoc.getBlockX() + 2, plinthLoc.getY(), plinthLoc.getBlockZ() + 2)).setData((byte) 1);
				world.getBlockAt(new Location(world, plinthLoc.getBlockX() - 1, plinthLoc.getY(), plinthLoc.getBlockZ() + 1)).setData((byte) 1);

			}
		}, 10L);*/
		return true;
	}

	public Stage getThis() {
		return this;
	}
	public void removeStage() {
		for (Npc npcList : npc) {
			Bukkit.getScheduler().runTaskLater(NpcReward.getInstance(), new Runnable() {

				@Override
				public void run() {
					player.playSound(location, Sound.ORB_PICKUP, 1f, 1f);
					npcList.delete();
				}
			}, 20L * 1);
		}
		npc.clear();
		Bukkit.getScheduler().runTaskLater(NpcReward.getInstance(), new Runnable() {

			@Override
			public void run() {

				BlockUtils.replaceAirBlock(blocklist);
				player.playSound(player.getLocation(), Sound.GLASS , 1f, 1f);
				StageManager.get().removeStage(getThis());
			}
		}, 20L * 5);
	}

	public void removeStageNoCd() {
		for (Npc npcList : npc) {
			npcList.delete();
		}
		BlockUtils.replaceAirBlock(blocklist);
	}

	int task = 6854;
	int i = 0;
	private void spawnPnj() {
		NpcManager nm = NpcManager.get();
		Settings sg = Settings.get();
		Location location = this.location;
		Location a = new Location(location.getWorld(), location.getBlockX() + 2.5, location.getBlockY() + 1, location.getBlockZ() + 0.5);
		Location b = new Location(location.getWorld(), location.getBlockX() +0.5 , location.getBlockY() + 1, location.getBlockZ() + 2.5);
		Location c = new Location(location.getWorld(), location.getBlockX() - 1.5, location.getBlockY() + 1, location.getBlockZ() + 0.5 );
		Location d = new Location(location.getWorld(), location.getBlockX() +0.5, location.getBlockY() + 1, location.getBlockZ() - 1.5);

		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(NpcReward.getInstance(), new Runnable() {
			@Override
			public void run() {
				switch (i) {
				case (0):
					player.playSound(a, Sound.NOTE_PLING, 1f, 1f);
				npc.add(nm.spawnNpc(sg.getNpcNames()[i], a, sg.getProfession(), player));
				break;
				case (1):
					player.playSound(b, Sound.NOTE_PLING, 1f, 1f);
				npc.add(nm.spawnNpc(sg.getNpcNames()[i], b, sg.getProfession(), player));
				break;
				case (2):
					player.playSound(c, Sound.NOTE_PLING, 1f, 1f);
				npc.add(nm.spawnNpc(sg.getNpcNames()[i], c, sg.getProfession(), player));
				break;
				case (3):
					player.playSound(d, Sound.NOTE_PLING, 1f, 1f);
				npc.add(nm.spawnNpc(sg.getNpcNames()[i], d, sg.getProfession(), player));
				break;
				case (4):
					TitlesLib.sendTitle(player, PacketPlayOutTitle.EnumTitleAction.TITLE, Lang.TITLE, 20, 20, 20, ChatColor.RED);
				TitlesLib.sendTitle(player, PacketPlayOutTitle.EnumTitleAction.SUBTITLE, Lang.SUBTITLE, 20, 20, 20, ChatColor.WHITE);
				player.playSound(location, Sound.LEVEL_UP, 1f, 1f);
				canPick = true;
				Bukkit.getScheduler().cancelTask(task);
				break;
				}
				i++;
			}
		}, 20L * 2, 20L);
	}
}
