package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.World;

public class SquareBorderProducer {
	public static LinkedList<Location> getLocations(World world, long centreX, long guideY, long centreZ, int length, int width) {

		//System.out.println("getLocations(World: " + world + ", centreX: " + centreX + ", yGuide: " + guideY + ", centreZ: " + centreZ);
		
		LinkedList<Location> locations = new LinkedList<>();

		int xStart = (int) (centreX - (width / 2));
		int xEnd = xStart + (length - 1);
		int zStart = (int) (centreZ - (length / 2));
		int zEnd = zStart + (width - 1);

		for (int x = xStart; x < xEnd; x++) {
			long y =  LocationHelper.getHighestBlockYAt(world, x, guideY, zStart);
			Location l = new Location(world, x, y, zStart);
			locations.add(l);
		}

		for (int z = zStart; z < zEnd; z++) {
			long y = LocationHelper.getHighestBlockYAt(world, xEnd, guideY, z);
			Location l = new Location(world, xEnd, y, z);
			locations.add(l);
		}

		for (int x = xEnd; x > xStart; x--) {
			long y = LocationHelper.getHighestBlockYAt(world, x, guideY, zEnd);
			Location l = new Location(world, x, y, zEnd);
			locations.add(l);
		}

		for (int z = zEnd; z > zStart; z--) {
			long y = LocationHelper.getHighestBlockYAt(world, xStart, guideY, z);
			Location l = new Location(world, xStart, y, z);
			locations.add(l);
		}

		return locations;
	}

}
