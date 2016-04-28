package fr.zaral.npcreward.objects;

import fr.zaral.npcreward.NpcReward;
import fr.zaral.npcreward.npc.Npc;
import fr.zaral.npcreward.utils.BlockUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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

    private float walkSpeed = 1;
    @Getter
    private ArrayList<Block> blocklist = new ArrayList<Block>();
    @Getter
    private ArrayList<Npc> npc = new ArrayList<Npc>();


    public Stage(Player player) {
        this.player = player;
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
    }

    private boolean createPlinth(Location center, Player player) {
        World world = player.getWorld();
        boolean cantPlace = false;
        Location plinthLoc = new Location(world, center.getBlockX(), center.getY() + 0, center.getBlockZ());
        Location perfectLoc = new Location(world, center.getBlockX() + 0.5, center.getY() + 1, center.getBlockZ() + 0.5);
        int xMin = plinthLoc.getBlockX() - 2;
        int xMax = plinthLoc.getBlockX() + 2;
        int zMin = plinthLoc.getBlockZ() - 2;
        int zMax = plinthLoc.getBlockZ() + 2;
        int y = (int) plinthLoc.getBlockY();
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
}
