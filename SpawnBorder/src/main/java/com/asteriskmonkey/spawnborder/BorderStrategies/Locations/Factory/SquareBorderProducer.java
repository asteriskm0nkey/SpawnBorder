package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class SquareBorderProducer {
	public static LinkedList<Location> getLocations(World world, long centreX, long centreZ, int length, int width) {

		// FIXME getHighestBlockAt works on light levels, so messes up with a LAVA, WATER or GLASS border.
		// FIXME will break in the Nether. What would getHighestBlockAt return?!

		/* 	TODO link the last entry in the locations to the first entry?
		 * Would potentially cause some breakages. Keep it as a soft constraint that the end of the list joins to the start of the list
		 * May be useful later with a smoothed border, but not so much right now.
		 */
		
		LinkedList<Location> locations = new LinkedList<>();
		
		int xStart = (int) (centreX - (width / 2));
		int xEnd = xStart + (length - 1);
		int zStart = (int) (centreZ - (length / 2));
		int zEnd = zStart + (width - 1);
		
		for (int x = xStart; x < xEnd; x++) {
			int y = world.getHighestBlockYAt(x, zStart) - 1;
			Block b = world.getBlockAt(x, y, zStart);
			Location l = b.getLocation();
			locations.add(l);
		}
		
		for (int z = zStart; z < zEnd; z++) {
			int y = world.getHighestBlockYAt(xEnd, z) - 1;
			Block b = world.getBlockAt(xEnd, y, z);
			Location l = b.getLocation();
			locations.add(l);
		}
		
		for (int x = xEnd; x > xStart; x--) {
			int y = world.getHighestBlockYAt(x, zEnd) - 1;
			Block b = world.getBlockAt(x, y, zEnd);
			Location l = b.getLocation(); 
			locations.add(l);
		}

		for (int z = zEnd; z > zStart; z--) {
			int y = world.getHighestBlockYAt(xStart, z) - 1;
			Block b = world.getBlockAt(xStart, y, z);
			Location l = b.getLocation();
			locations.add(l);
		}
		

		
		return locations;
	}
}
