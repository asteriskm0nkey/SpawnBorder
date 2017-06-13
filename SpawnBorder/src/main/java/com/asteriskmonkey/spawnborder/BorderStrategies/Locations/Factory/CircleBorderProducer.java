package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.World;

public class CircleBorderProducer {
	
	// Bresenham Ellipse Algorithm
	// https://sites.google.com/site/ruslancray/lab/projects/bresenhamscircleellipsedrawingalgorithm/bresenham-s-circle-ellipse-drawing-algorithm
	public static LinkedList<Location> getLocations (World world, long centreX, long guideY, long centreZ, int length, int width)
	{
		LinkedList<Location> locations = new LinkedList<>();
		
	    /* first half */
	    
	    for (int x = 0, z = width, sigma = (2 * width * width) + (length * length *(1-2*width));
	    		width * width * x <= length * length * z;
	    		x++)
	    {
	        DrawPixel(locations, world, (centreX + x), guideY, (centreZ + z));
	        DrawPixel(locations, world, (centreX - x), guideY, (centreZ + z));
	        DrawPixel(locations, world, (centreX + x), guideY, (centreZ - z));
	        DrawPixel(locations, world, (centreX - x), guideY, (centreZ - z));
	        
	        if (sigma >= 0)
	        {
	            sigma += (4 * length * length) * (1 - z);
	            z--;
	        }
	        sigma += width * width * ((4 * x) + 6);
	    }
	    
	    /* second half */
		
	    for (int x = length, z = 0, sigma = (2 * length * length)+(width * width * (1-2*length));
	    		length * length * z <= width * width * x;
	    		z++)
	    {
	        DrawPixel (locations, world, (centreX + x), guideY, (centreZ + z));
	        DrawPixel (locations, world, (centreX - x), guideY, (centreZ + z));
	        DrawPixel (locations, world, (centreX + x), guideY, (centreZ - z));
	        DrawPixel (locations, world, (centreX - x), guideY, (centreZ - z));
	        if (sigma >= 0) 
	        {
	            sigma += (4 * width * width) * (1 - x);
	            x--;
	        }
	        sigma += length * length * ((4 * z) + 6);
	    }
	    
	    CircleLocationComparator clc = new CircleLocationComparator(centreX, centreZ);
	    locations.sort(clc);
	    
	    return locations;
	}
	
	private static void DrawPixel(LinkedList<Location> locations, World world, long x, long yGuide, long z) {
		Location l = getLocation(world, x, yGuide, z);
        if (!locations.contains(l)) {
			locations.add(l);
		}
	}
	
	private static Location getLocation(World world, long x, long yGuide, long z) {
		long highestY = LocationHelper.getHighestBlockYAt(world, x, yGuide, z);
		return new Location(world, x, highestY, z);
	}
}