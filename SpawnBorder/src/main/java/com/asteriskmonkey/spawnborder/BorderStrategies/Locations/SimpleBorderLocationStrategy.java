package com.asteriskmonkey.spawnborder.BorderStrategies.Locations;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.World;

import com.asteriskmonkey.spawnborder.BorderCommand.BorderCommand;
import com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory.ShapeBorderFactory;

public class SimpleBorderLocationStrategy implements BorderLocationStrategy {

	@Override
	public LinkedList<Location> getBorderLocations(World world, long centreX, long centreZ, int length, int width, BorderCommand.BorderShape shape) {
		
		ShapeBorderFactory factory = new ShapeBorderFactory();
		LinkedList<Location> locations = factory.getLocationList(world, centreX, centreZ, length, width, shape);
		return locations;
	}

}
