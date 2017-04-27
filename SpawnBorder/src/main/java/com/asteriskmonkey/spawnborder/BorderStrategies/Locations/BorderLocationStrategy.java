package com.asteriskmonkey.spawnborder.BorderStrategies.Locations;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.World;

import com.asteriskmonkey.spawnborder.BorderCommand.BorderCommand;

public interface BorderLocationStrategy {
	public LinkedList<Location> getBorderLocations(World world, long centreX, long centreZ, int length, int width, BorderCommand.BorderShape shape);
}
