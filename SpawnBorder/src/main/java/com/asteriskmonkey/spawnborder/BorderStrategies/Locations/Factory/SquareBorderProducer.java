package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.block.Block;

public class SquareBorderProducer {
	public static LinkedList<Location> getLocations(World world, long centreX, long centreZ, int length, int width) {

		// FIXME getHighestBlockAt works for light levels, so messes up with a
		// LAVA, WATER or GLASS border.
		// FIXME will break in the Nether. What would getHighestBlockAt return?!

		/*
		 * TODO link the last entry in the locations to the first entry? Would
		 * potentially cause some breakages. Keep it as a soft constraint that
		 * the end of the list joins to the start of the list May be useful
		 * later with a smoothed border, but not so much right now.
		 */

		LinkedList<Location> locations = new LinkedList<>();

		int xStart = (int) (centreX - (width / 2));
		int xEnd = xStart + (length - 1);
		int zStart = (int) (centreZ - (length / 2));
		int zEnd = zStart + (width - 1);

		for (int x = xStart; x < xEnd; x++) {
			long y =  ShapeBorderFactory.getHighestBlockYAt(world, x, zStart);
			Location l = new Location(world, x, y, zStart);
			locations.add(l);
		}

		for (int z = zStart; z < zEnd; z++) {
			long y = ShapeBorderFactory.getHighestBlockYAt(world, xEnd, z);
			Location l = new Location(world, xEnd, y, z);
			locations.add(l);
		}

		for (int x = xEnd; x > xStart; x--) {
			long y = ShapeBorderFactory.getHighestBlockYAt(world, x, zEnd);
			Location l = new Location(world, x, y, zEnd);
			locations.add(l);
		}

		for (int z = zEnd; z > zStart; z--) {
			long y = ShapeBorderFactory.getHighestBlockYAt(world, xStart, z);
			Location l = new Location(world, xStart, y, z);
			locations.add(l);
		}

		return locations;
	}

}
