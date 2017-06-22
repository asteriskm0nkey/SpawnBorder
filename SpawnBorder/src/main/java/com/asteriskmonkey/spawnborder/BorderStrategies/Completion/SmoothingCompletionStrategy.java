package com.asteriskmonkey.spawnborder.BorderStrategies.Completion;

import java.util.List;

import org.bukkit.Location;

public class SmoothingCompletionStrategy implements BorderCompletionStrategy {

	@Override
	public List<Location> getCompletedBorderLocations(List<Location> skeletonBorder) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	// TODO begin here... map out roughly what I want the border function to do, block by block
	
	/*
	 * TODO link the last entry in the locations to the first entry? Would
	 * potentially cause some breakages. Keep it as a soft constraint that
	 * the end of the list joins to the start of the list May be useful
	 * later with a smoothed border, but not so much right now.
	 */

	
	// More advanced strategies can add gradients or smooth the land surrounding the border using the Diamond-Square method
	
	
	/* BorderSmoothingCompletionStrategy
	 * Start with a block. Look at the block surrounding this block and average them out?
	 * 1 1B 1 1
	 * 1 1* 2 1    replace 2 with a 1
	 * 1 1B 1 1
	 * 
	 * 5 5B 3
	 * 5 5B 3		
	 * 5 5B 3
	 * 
	 * Looking at the contours of the land, rather than the immediate surrounding blocks? The second example would just find air
	 * 
	 * Lay a mesh down on the land that matches the shape of the border and several squares surrounding it, then contort the 
	 * mesh, pulling up low parts, flattening high parts?
	 * Read article on terrain generation by Robert Martin - he discusses an algorithm for averaging by choosing points.
	 * Diamond-square algorithm?
	 */
}
