package com.asteriskmonkey.spawnborder.BorderStrategies.Locations;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

import com.asteriskmonkey.spawnborder.BorderCommand.BorderCommand;

public interface BorderLocationStrategy {
	public List<Location> getBorderLocations(World world, long centreX, long centreY, long centreZ, int length, int width, BorderCommand.BorderShape shape);
}
