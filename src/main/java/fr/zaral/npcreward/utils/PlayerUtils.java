package fr.zaral.npcreward.utils;

import org.bukkit.Location;

public class PlayerUtils {

    public static Boolean isInside(Location loc, Location corner1, Location corner2) {
        double xMin = 0;
        double xMax = 0;

        double zMin = 0;
        double zMax = 0;
        double x = loc.getX();
        double z = loc.getZ();
 
        xMin = Math.min(corner1.getX(), corner2.getX());
        xMax = Math.max(corner1.getX(), corner2.getX());
 
        zMin = Math.min(corner1.getZ(), corner2.getZ());
        zMax = Math.max(corner1.getZ(), corner2.getZ());
        double limit = corner1.getY() - loc.getY();
        return (x >= xMin && x <= xMax && z >= zMin && z <= zMax && limit <= 3 && limit >= -3);
    }
    
}
