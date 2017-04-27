package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.World;

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
	
}
