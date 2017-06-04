package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;

import com.asteriskmonkey.spawnborder.BorderCommand.BorderCommand;

public final class ShapeBorderFactory {
	public LinkedList<Location> getLocationList(World world, long centreX, long centreZ, int length, int width, BorderCommand.BorderShape shape) {
		
		LinkedList<Location> locations = null;
		
		switch (shape) {
		case SQUARE:
		case RECTANGLE:
			locations = SquareBorderProducer.getLocations(world, centreX, centreZ, length, width);
			break;
		case CIRCLE:
		case ELLIPSE:
			locations = CircleBorderProducer.getLocations(world, centreX, centreZ, length, width);
			break;
		}
		return locations;
	}
	
	public static long getHighestBlockYAt(World world, long x, long z) {
		long yCoord = 0;

		Environment env = world.getEnvironment();
		
		// FIXME getHighestBlockYAt will not function correctly in the nether for my purposes
		
		switch(env) {
		
		case NETHER:
			break;
		case THE_END:
			break;
		case NORMAL:
		default:
			// Treat the overworld as the default. If any other type of environment is present, treat it like the overworld;
			yCoord = world.getMaxHeight();
			for (int i = world.getMaxHeight() - 1; i >= 0; i--) {
				Location l = new Location(world, x, i, z);
				if (l.getBlock().getType() != Material.AIR) {
					yCoord = i;
					break;
				}
			}	
		}

		return yCoord;
	}
	
}
