package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class LocationHelper {
	/* Replacement method for the World.getHighestBlockYAt method that seems badly named (since it gets the air block above for use in light levels)
	 * and would be prone to overflow problems in some contexts.
	 */
	
	// FIXME test getHighestBlockYAt with LAVA, WATER or GLASS borders
	
	private static final int MINHEIGHT = 2;

	public static long getHighestBlockYAt(World world, long x, long yGuide, long z) {
		LocationMockHelper h = new LocationMockHelper();
		//System.out.println("getHighestBlockYAt(World: " + world + ", x: " + x + ", yGuide: " + yGuide + ", z: " + z);
		return getHighestBlockYAt(world, x, yGuide, z, h);
	}

	protected static long getHighestBlockYAt(World world, long x, long yGuide, long z, LocationMockHelper h) {
		long yCoord = 0;
		long yReturn = MINHEIGHT;
		//System.out.println("getHighestBlockYAt(World: " + world + ", x: " + x + ", yGuide: " + yGuide + ", z: " + z);

		switch(world.getEnvironment()) {
		
		case NETHER:
			yCoord = yGuide;
			break;
		case THE_END:
		case NORMAL: // Treat the overworld as the default. If any other type of environment is present, treat it like the overworld;
		default:
			yCoord = world.getMaxHeight();
			break;
		}

		for (long i = yCoord; i >= MINHEIGHT; i--) {
			Location l = h.getNewLocation(world, x, i, z);
			if (l.getBlock().getType() != Material.AIR) {
				yReturn = i;
				break;
			}
		}
		
		return yReturn;
	}
	
	protected static class LocationMockHelper {
		public Location getNewLocation(World w, long x, long y, long z) {
			return new Location(w, x, y, z);
		}
	}
}
