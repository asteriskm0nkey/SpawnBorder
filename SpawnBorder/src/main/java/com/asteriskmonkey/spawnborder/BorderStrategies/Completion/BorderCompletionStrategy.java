package com.asteriskmonkey.spawnborder.BorderStrategies.Completion;

import java.util.List;

import org.bukkit.Location;

import com.asteriskmonkey.spawnborder.Exceptions.InvalidArgumentException;

public interface BorderCompletionStrategy {
	public List<Location> getCompletedBorderLocations(List<Location> skeletonBorder) throws InvalidArgumentException;
}
