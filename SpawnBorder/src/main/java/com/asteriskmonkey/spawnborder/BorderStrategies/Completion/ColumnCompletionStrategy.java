package com.asteriskmonkey.spawnborder.BorderStrategies.Completion;

import java.util.LinkedList;
import java.util.ListIterator;

import org.bukkit.Location;

import com.asteriskmonkey.spawnborder.Exceptions.InvalidArgumentException;

public class ColumnCompletionStrategy implements BorderCompletionStrategy {

	@Override
	public LinkedList<Location> getCompletedBorderLocations(LinkedList<Location> skeletonBorder) throws InvalidArgumentException {
		
		if (skeletonBorder==null) {
			throw new InvalidArgumentException();
		}
		
		LinkedList<Location> completedBorder = new LinkedList<>();
		
		// Parse the linked-list. Anywhere that there is a difference in the y axis from the one block to the next
		// add additional visible locations into the linkedlist at the correct position in the list.
		ListIterator<Location> li1 = skeletonBorder.listIterator();
		ListIterator<Location> li2 = skeletonBorder.listIterator();
		
		// Using two list iterators to parse through the skeleton list in order to create a completed list
		
		while (li2.hasNext() && skeletonBorder.size() >= 2) {
			if (!li1.hasPrevious()) {
				//System.out.println("li1 doesn't have previous");
				// only skip one if this is the first run
				li2.next(); // skip one
			}
			
			//System.out.println("li1 ni: " + li1.nextIndex());
			Location loc1 = li1.next();
			completedBorder.add(loc1);
			
			//System.out.println("li2 ni: " + li2.nextIndex());
			Location loc2 = li2.next();
			
			if (!loc1.getWorld().equals(loc2.getWorld())) {
				// something's gone drastically wrong if these two locations are in separate worlds!
				throw new RuntimeException();
			}
			
			int y1 = loc1.getBlockY();
			int y2 = loc2.getBlockY();
			
			//System.out.println("y1: " + y1 + ", y2: " + y2);

			// There's a difference of >1 in between the two location's y coordinates - there's a missing
			// block which would make the border look disjointed
			if (Math.abs(y2 - y1) > 1) {
				// Need to know which is the higher block, so we can place the other blocks
				// under that one's x and z coordinate
				
				//System.out.println("Difference trigger");
				Location highestBlock = (y1 > y2) ? loc1 : loc2;
				
				int yMin = Math.min(y1, y2);
				int yMax = Math.max(y1, y2);
				
				for (int y = yMin + 1; y < yMax; y++) {
					Location newLoc = new Location(loc2.getWorld(), highestBlock.getBlockX(), y, highestBlock.getBlockZ());
					completedBorder.add(newLoc); // add it before the second location
					//System.out.println("Added block");
				}
			}
			
			if(!li2.hasNext()) {
				// The case where we're at the end of the skeletonList.
				// We won't add this block on the next pass so must add it now.
				completedBorder.add(loc2);
			}
			
		}
		
		return completedBorder;
	}

}
