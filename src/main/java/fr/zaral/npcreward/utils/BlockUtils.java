package fr.zaral.npcreward.utils;

import fr.zaral.npcreward.NpcReward;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaral on 28/04/2016.
 */
public class BlockUtils {

    public static List<Block> getAllBlockInAreaByType(int xMin, int xMax, int zMin, int zMax, int y, Player player) {

        List<Block> list = new ArrayList<Block>();
        int x0, z0, x1, z1;
        Block temp_b, temp_c, temp_d;
        World w = player.getWorld();

        x0 = xMin;
        x1 = xMax;
        z0 = zMin;
        z1 = zMax;

        for (int x = x0; x <= x1; ++x) {

            for (int z = z0; z <= z1; ++z) {

                temp_b = w.getBlockAt(x, y, z);
                temp_c = w.getBlockAt(x, y + 1, z);
                temp_d = w.getBlockAt(x, y + 2, z);

                list.add(temp_c);
                list.add(temp_d);
                list.add(temp_b);
            }
        }
        return list;
    }

    public static void replaceBlock(List<Block> list, Material type) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(NpcReward.getInstance(), new Runnable() {
            @Override
            public void run() {
                Bukkit.getScheduler().runTask(NpcReward.getInstance(), new Runnable() {

                    @Override
                    public void run() {
                        if (type == Material.AIR) {
                            for (Block b : list)
                                b.setType(type);
                        } else {
                            for (Block b : list)
                                b.setType(type);
                        }
                    }
                });
            }
        });
    }


    public static List<Block> getAllBlockInArea(int xMin, int xMax, int zMin, int zMax, int y, Player player) {

        List<Block> list = new ArrayList<Block>();
        int x0, z0, x1, z1;
        Block temp_b;
        World w = player.getWorld();

        x0 = xMin;
        x1 = xMax;
        z0 = zMin;
        z1 = zMax;

        for (int x = x0; x <= x1; ++x) {

            for (int z = z0; z <= z1; ++z) {

                temp_b = w.getBlockAt(x, y, z);


                list.add(temp_b);
            }
        }
        return list;
    }

}
