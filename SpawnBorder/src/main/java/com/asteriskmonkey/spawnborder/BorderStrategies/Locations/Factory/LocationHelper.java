package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class LocationHelper {
	/* Replacement method for the World.getHighestBlockYAt method that seems badly named (since it gets the air block above for use in light levels)
	 * and would be prone to overflow problems in some contexts.
	 */
	
	private static final int MINHEIGHT = 2;

	public static long getHighestBlockYAt(World world, long x, long yGuide, long z) {
		LocationMockHelper h = new LocationMockHelper();
		//System.out.println("getHighestBlockYAt(World: " + world + ", x: " + x + ", yGuide: " + yGuide + ", z: " + z);
		return getHighestBlockYAt(world, x, yGuide, z, h);
	}

	protected static long getHighestBlockYAt(World world, long x, long yGuide, long z, LocationMockHelper h) {
		long yCoord = 0;
		long yReturn = MINHEIGHT;
		//System.out.println("getHighestBlockYAt(World: " + world + ", x: " + x + ", yGuide: " + yGuide + ", z: " + z);

		switch(world.getEnvironment()) {
		
		case NETHER:
			yCoord = yGuide;
			break;
		case THE_END:
		case NORMAL: // Treat the overworld as the default. If any other type of environment is present, treat it like the overworld;
		default:
			yCoord = world.getMaxHeight();
			break;
		}

		for (long i = yCoord; i >= MINHEIGHT; i--) {
			Location l = h.getNewLocation(world, x, i, z);
			if (l.getBlock().getType() != Material.AIR) {
				yReturn = i;
				break;
			}
		}
		
		return yReturn;
	}
	
	protected static class LocationMockHelper {
		public Location getNewLocation(World w, long x, long y, long z) {
			return new Location(w, x, y, z);
		}
	}
	
	public static List<Location> sinkBelowPlants(List<Location> basicLocations) {
		Material[] materials = { 
				Material.LEAVES, Material.LOG, Material.LOG_2, Material.SAPLING,
				Material.DOUBLE_PLANT, Material.LONG_GRASS, Material.VINE, Material.DEAD_BUSH,
				Material.SUGAR_CANE_BLOCK, Material.COCOA, Material.CACTUS,
				Material.YELLOW_FLOWER, Material.RED_ROSE,
				Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.HUGE_MUSHROOM_1, Material.HUGE_MUSHROOM_2,
				Material.AIR };
		return sinkBelowMaterial(basicLocations, Arrays.asList(materials));
	}

	// trim away long grass
	// remove trees that overlap the border

	// TODO consider caves and underground areas - include the spawn ring
	// for entrances etc for people that
	// venture into spawn caves?

	private static List<Location> sinkBelowMaterial(List<Location> locations, List<Material> materials) {
		List<Location> returnLocations = new LinkedList<>();
		
		ListIterator<Location> li = locations.listIterator();
		while (li.hasNext()) {
			Location l = li.next();
			Block b = l.getBlock();
			Material blockMaterial = b.getType();
			while (b.getY() > MINHEIGHT && materials.contains(blockMaterial)) {
				b = b.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ());
				blockMaterial = b.getType();
				l = b.getLocation();
			}
			returnLocations.add(l);
		}
		
		return returnLocations;
	}

	public static List<Location> sinkBelowWater(List<Location> basicLocations) {
		Material[] materials = { Material.STATIONARY_WATER, Material.WATER, Material.AIR };
		return sinkBelowMaterial(basicLocations, Arrays.asList(materials));
	}

	// Pass in a Block, get back the correct block if it was a tree or water
	// source etc
	private Block getBlock(World w, int x, int z) {
		int y = w.getHighestBlockYAt(x, z) - 1;
		// We want the ground rather than the open air or grass block
		// TODO test to see how this works with transparent blocks like glass,
		// and multilayer reeds etc

		Block b = w.getBlockAt(x, y, z);
		Material type = b.getType();
		while (y > 1 && (type == Material.WATER || type == Material.LEAVES)) {
			// Move down until we find solid ground
			b = w.getBlockAt(x, --y, z);
			type = b.getType();
		}

		Block returnBlock = b;
		return returnBlock;
	}

	// Get a list of all blocks that surround a specific block
	private static List<Block> getSurroundingBlocks(Block b) {
		List<Block> surroundingBlockList = new ArrayList<>();
		World w = b.getWorld();

		for (int xCheck = b.getX() - 1; xCheck <= b.getX() + 1; xCheck++) {
			for (int yCheck = b.getY() - 1; yCheck <= b.getY() + 1; yCheck++) {
				for (int zCheck = b.getZ() - 1; zCheck <= b.getZ() + 1; zCheck++) {

					// Don't bother adding the selected block to the list of
					// surrounding blocks
					if (xCheck != b.getX() || yCheck != b.getY() || zCheck != b.getZ()) {
						surroundingBlockList.add(w.getBlockAt(xCheck, yCheck, zCheck));
					}
				}
			}
		}

		return surroundingBlockList;
	}

	/*
	 * Tree selection in Minecraft. Add a separate collection of wood and leaf
	 * parts of the tree. Parse the wood and count the number of trunks? Thicker
	 * trunks count as 1.
	 * 
	 * For columns of the border material, where there is something vertical,
	 * may be able to use one of the facing properties to give the blocks under
	 * it a material, if they are exposed and between the block and the next
	 * block is on a different level.
	 * 
	 * Sorting of blocks - sort by X, Y and Z. How would a rubik's cube be
	 * sorted?? Would allow the blocks to be stored in a Tree and filling in
	 * gaps between elements for the column idea above would be easier
	 */

	// Get a list of all blocks that represent the scope of a whole tree
	private static List<Block> selectTree(World w, int x, int y, int z) {
		// TODO check to see if this is a valid tree, for example it hasn't been
		// altered with additional decorations
		// and is safe to remove
		// From a given starting point, either leaf or wood block, follow the
		// tree in it's entirety to see if it is a "normal" tree
		List<Block> treeSelection = new ArrayList<>();
		Block blockInTree = w.getBlockAt(x, y, z);
		Material blockType = blockInTree.getType();
		if (!(blockType == Material.WOOD || blockType == Material.LEAVES)) {
			// Invalid tree.
			return treeSelection;
		}

		List<Block> blocksToCheck = new ArrayList<>();
		Set<Block> blocksChecked = new HashSet<>();

		blocksToCheck.add(blockInTree);

		ListIterator<Block> li = blocksToCheck.listIterator();
		while (li.hasNext()) {

			// Get and check the block
			Block block = li.next();

			// Ensure that it's surrounding blocks are added to the appropriate
			// lists
			List<Block> surroundingBlocks = getSurroundingBlocks(blockInTree);

			for (Block surroundingBlock : surroundingBlocks) {
				if (!blocksChecked.contains(surroundingBlock) && !blocksToCheck.contains(surroundingBlock)) {
					li.add(surroundingBlock);
				}
			}

			// Add the block to the list of tree-parts if necessary
			Material blMat = block.getType();
			if (blMat == Material.WOOD || blMat == Material.LEAVES) {
				treeSelection.add(block);
			}

			if (treeSelection.size() > 200) {
				// TODO basic check to prevent dismantling a whole forest.
				// Consider throwing something
				return treeSelection;
			}

			// Remove this block from the list of blocks to check
			li.remove();
		}

		return treeSelection;
	}

	
	
	
}
