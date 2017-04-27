package com.asteriskmonkey.spawnborder.BorderStrategies.Completion;

import java.util.LinkedList;

import org.bukkit.Location;

import com.asteriskmonkey.spawnborder.Exceptions.InvalidArgumentException;

public interface BorderCompletionStrategy {
	public LinkedList<Location> getCompletedBorderLocations(LinkedList<Location> skeletonBorder) throws InvalidArgumentException;
}
