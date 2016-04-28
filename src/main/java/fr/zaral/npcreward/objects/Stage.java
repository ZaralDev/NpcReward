package fr.zaral.npcreward.objects;

import fr.zaral.npcreward.Lang;
import fr.zaral.npcreward.NpcReward;
import fr.zaral.npcreward.lib.TitlesLib;
import fr.zaral.npcreward.npc.Npc;
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

    private float walkSpeed = 1;
    @Getter
    private ArrayList<Block> blocklist = new ArrayList<Block>();
    @Getter
    private ArrayList<Npc> npc = new ArrayList<Npc>();
    @Getter
    private boolean canPick = false;

    public Stage(Player player) {
        this.player = player;
        this.location = player.getLocation();
    }

    private void freezePlayer() {
        walkSpeed = player.getWalkSpeed();
        player.setWalkSpeed(0);
    }

    private void unfreezePlayer() {
        player.setWalkSpeed(walkSpeed);
    }

    private void startStage() {
        freezePlayer();
        createPlinth();
        spawnPnj();
    }

    private boolean createPlinth() {
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
                BlockUtils.replaceBlock(list, Material.SMOOTH_BRICK);
                blocklist.addAll(list);
                Bukkit.getScheduler().runTaskLater(NpcReward.getInstance(), new Runnable() {
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
                }, 10L);
        return true;
    }

    int task = 6854;
    int i = 0;
    private void spawnPnj() {
        NpcReward pl = NpcReward.getInstance();
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
                        npc.add(pl.getNpcManager().spawnNpc(pl.getSettings().getNpcNames()[i], a, pl.getSettings().getProfession(), player));
                        break;
                    case (1):
                        player.playSound(b, Sound.NOTE_PLING, 1f, 1f);
                        npc.add(pl.getNpcManager().spawnNpc(pl.getSettings().getNpcNames()[i], b, pl.getSettings().getProfession(), player));
                        break;
                    case (2):
                        player.playSound(c, Sound.NOTE_PLING, 1f, 1f);
                        npc.add(pl.getNpcManager().spawnNpc(pl.getSettings().getNpcNames()[i], c, pl.getSettings().getProfession(), player));
                        break;
                    case (3):
                        player.playSound(d, Sound.NOTE_PLING, 1f, 1f);
                        npc.add(pl.getNpcManager().spawnNpc(pl.getSettings().getNpcNames()[i], d, pl.getSettings().getProfession(), player));
                        break;
                    case (4):
                        TitlesLib.sendTitle(player, PacketPlayOutTitle.EnumTitleAction.TITLE, Lang.TITLE, 20, 20, 20, ChatColor.RED);
                        TitlesLib.sendTitle(player, PacketPlayOutTitle.EnumTitleAction.SUBTITLE, Lang.SUBTITLE, 20, 20, 20, ChatColor.WHITE);
                        player.playSound(location, Sound.LEVEL_UP, 1f, 1f);
                        canPick = true;
                        Bukkit.getScheduler().cancelTask(task);
                        break;
                }
            }
        }, 20L * 2, 20L);
    }
}
