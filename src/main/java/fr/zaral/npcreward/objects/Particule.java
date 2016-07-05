package fr.zaral.npcreward.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.zaral.npcreward.NpcReward;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class Particule {

	@Getter
	private Location location;
	@Getter
	private boolean play = false;
	
	private int task = 52645;
	public Particule(Location location) {
		this.location = location;
	}

	public void play() {
		play = true;
		sendPacket(EnumParticle.ENCHANTMENT_TABLE, location, 0, 0, 0, 0.1f, 5);
		run();
	}

	public void stop() {
		play = false;
	}

	private void run() {
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(NpcReward.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (play) {
					sendPacket(EnumParticle.ENCHANTMENT_TABLE, location, 0, 0, 0, 0.1f, 5);
				} else {
					Bukkit.getScheduler().cancelTask(task);
				}
			}
		}, 20L, 20L);
	}

	private void sendPacket(EnumParticle particle, Location location, float offsetX, float offsetY, float offsetZ, float data, int count) {
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(),
				offsetX, offsetY, offsetZ, data, count);
		for (Player player : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}
}
