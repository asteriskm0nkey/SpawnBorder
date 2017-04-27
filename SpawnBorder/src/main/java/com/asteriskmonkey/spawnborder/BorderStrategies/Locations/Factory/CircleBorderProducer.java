package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

public class CircleBorderProducer {
	
	// Bresenham Ellipse Algorithm
	// https://sites.google.com/site/ruslancray/lab/projects/bresenhamscircleellipsedrawingalgorithm/bresenham-s-circle-ellipse-drawing-algorithm
	public static LinkedList<Location> getLocations (World world, long centreX, long centreZ, int length, int width)
	{
		LinkedList<Location> locations = new LinkedList<>();
		
		// FIXME refactor this to make it less ugly.
		
	    int lengthSquared = length * length;
	    int widthSquared = width * width;
	    int fa2 = 4 * lengthSquared, fb2 = 4 * widthSquared;

	    /* first half */
	    for (int x = 0, z = width, sigma = 2*widthSquared+lengthSquared*(1-2*width); widthSquared*x <= lengthSquared*z; x++)
	    {
	        DrawPixel(locations, world, (centreX + x), (centreZ + z));
	        DrawPixel(locations, world, (centreX - x), (centreZ + z));
	        DrawPixel(locations, world, (centreX + x), (centreZ - z));
	        DrawPixel(locations, world, (centreX - x), (centreZ - z));
	        
	        if (sigma >= 0)
	        {
	            sigma += fa2 * (1 - z);
	            z--;
	        }
	        sigma += widthSquared * ((4 * x) + 6);
	    }

	    /* second half */
	    for (int x = length, z = 0, sigma = 2*lengthSquared+widthSquared*(1-2*length); lengthSquared*z <= widthSquared*x; z++)
	    {
	        DrawPixel (locations, world, (centreX + x), (centreZ + z));
	        DrawPixel (locations, world, (centreX - x), (centreZ + z));
	        DrawPixel (locations, world, (centreX + x), (centreZ - z));
	        DrawPixel (locations, world, (centreX - x), (centreZ - z));
	        if (sigma >= 0)
	        {
	            sigma += fb2 * (1 - x);
	            x--;
	        }
	        sigma += lengthSquared * ((4 * z) + 6);
	    }
	    
	    return locations;
	}
	
	
	
	private static void DrawPixel(LinkedList<Location> locations, World world, long x, long z) {
		Location l = getLocation(world, x, z);
        if (!locations.contains(l)) {
			locations.add(l);
		}
        
        // FIXME implement a circlesort? locationsort
	}
	
	private static void LocationSort(LinkedList<Location> locs) {
		/* LocationSort
		 * Start at a single location within the collection
		 *  Find the nearby locations (within 1 block x,z)
		 *  Choose a direction - move in one direction along the path, keep the other found location as the endpoint
		 * At some point, we'll get to a place where we reach the last location. End here.
		 */
		if (locs.size() == 0) return;
		Location thisLoc;
		Location lastLoc;
		Location prevLoc;
		Location nextLoc;
		List<Location> visitedLocations = new ArrayList<Location>();
		List<Location> closestLocations = new ArrayList<Location>();
		
		thisLoc = locs.getFirst();
		visitedLocations.add(thisLoc);
		// Find the closest locations to this one
		for (Location l : locs) {
			if ((thisLoc.getBlockX() - l.getBlockX()) <= 1 && (thisLoc.getBlockZ() - l.getBlockZ()) <= 1) {
				closestLocations.add(l);
			}
		}
	}

	private static Location getLocation(World world, long x, long z) {
		// FIXME getHighestBlockYAt is unsafe from overflow, and will not function correctly in the nether for my purposes
		int y = world.getHighestBlockYAt((int)x, (int)z) - 1;
		Location l = new Location(world, x, y, z); 
		return l;
	}
}