package com.asteriskmonkey.spawnborder.BorderCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.material.Colorable;
import org.bukkit.material.MaterialData;

import com.asteriskmonkey.spawnborder.BorderStrategies.Completion.BorderCompletionStrategy;
import com.asteriskmonkey.spawnborder.BorderStrategies.Locations.BorderLocationStrategy;
import com.asteriskmonkey.spawnborder.Exceptions.InvalidArgumentException;

public class BorderCommandExecutor {
	// Executes a BorderCommand object. Translates the bordercommand settings
	// into changes to the world.

	// interacts with the world etc AND
	// saves details of what changes were made in order to provide an undo of
	// all damage done.

	// TODO SRP broken by "and" above. Split further.
	// Undo might not be necessary with regenerateChunk? Give both options?

	// TODO becoming spaghetti code. Refactor

	
	/* Tree selection in Minecraft.
		Add a separate collection of wood and leaf parts of the tree.
		Parse the wood and count the number of trunks? Thicker trunks count as 1.
		
		For columns of the border material, where there is something vertical, may be able to use one of the facing properties to give the blocks under it a material, if they are exposed and between the block and the next block is on a different level.
		
		Sorting of blocks - sort by X, Y and Z. How would a rubik's cube be sorted??
		Would allow the blocks to be stored in a Tree and filling in gaps between elements for the column idea above would be easier
	 */
	
	private BorderLocationStrategy locationStrategy;
	private BorderCompletionStrategy completionStrategy;
	
	public BorderCommandExecutor(BorderLocationStrategy bls, BorderCompletionStrategy bcs) {
		this.locationStrategy = bls;
		this.completionStrategy = bcs;
	}
	
	public void execute(BorderCommand bc) throws InvalidArgumentException {
		// parse and execute the BorderCommand on the specific World and realm
		
		Material borderMaterial = bc.getMaterial();
		DyeColor borderColor = bc.getColor();

		if (bc.getWorld() == null) {
			throw new RuntimeException("Invalid World defined");
		}

		// The basic skeleton of the border, with a block on each x and z location, but possibly
		// missing blocks within the y axis that would make the list a complete border.
		LinkedList<Location> basicBorderLocs = locationStrategy.getBorderLocations(bc.getWorld(),
				bc.getCenterX(), bc.getCenterZ(), bc.getLength(), bc.getWidth(), bc.getShape());
		
		// The completed border, depending on implementation, should fill in these gaps to create a solid line
		LinkedList<Location> completedLocations = completionStrategy.getCompletedBorderLocations(basicBorderLocs);
		
		
		
		if (bc.getRemoveTrees()) {
			//removeTrees(highestBlocks);
		} else {
			//sinkBelowTrees(highestBlocks);
		}
		
		if (bc.getSinkBelowWater()) {
			//sinkBelowWater(highestBlocks);
		}

		for (Location l : completedLocations) {
			Block b = l.getBlock();
			b.setType(borderMaterial);
			
			Class<? extends MaterialData> md = borderMaterial.getData();
			if (Colorable.class.isAssignableFrom(md)) {
				// This material implements colorable, so assign it's color
				
				switch (borderMaterial) {
				case WOOL: 
					b.setData(borderColor.getWoolData());
					break;
				default:
					b.setData(borderColor.getDyeData());
				}
			}
		}
		
		// TODO check the material type.
		// If it's water, sink the border, and check the surrounding blocks for
		// where a column of material is necessary
		// trim away long grass
		// remove trees that overlap the border

		// TODO consider caves and underground areas - include the spawn ring for entrances etc for people that
		// venture into spawn caves?

	}
	
	private void sinkBelowMaterial(List<Block> blocks, List<Material> materials) {
		ListIterator<Block> li = blocks.listIterator();
		while (li.hasNext()) {
			Block b = li.next();
			Material blockMaterial = b.getType();
			while (b.getY() > 1 && materials.contains(blockMaterial)) {
				b = b.getWorld().getBlockAt(b.getX(), b.getY() - 1, b.getZ());
				blockMaterial = b.getType();
			}
			li.set(b);
		}
	}

	private void sinkBelowTrees(List<Block> blocks) {
		Material[] materials = { Material.LEAVES, Material.WOOD, Material.AIR };
		sinkBelowMaterial(blocks, Arrays.asList(materials));
	}
	
	private void sinkBelowWater(List<Block> blocks) {
		Material[] materials = { Material.WATER, Material.AIR };
		sinkBelowMaterial(blocks, Arrays.asList(materials));
	}

	private void removeTrees(List<Block> highestBlocks) {
		// TODO Auto-generated method stub
		
	}

	// Pass in a Block, get back the correct block if it was a tree or water source etc
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
	private List<Block> getSurroundingBlocks(Block b) {
		List<Block> surroundingBlockList = new ArrayList<>();
		World w = b.getWorld();

		for (int xCheck = b.getX() - 1; xCheck <= b.getX() + 1; xCheck++) {
			for (int yCheck = b.getY() - 1; yCheck <= b.getY() + 1; yCheck++) {
				for (int zCheck = b.getZ() - 1; zCheck <= b.getZ() + 1; zCheck++) {
					
					// Don't bother adding the selected block to the list of surrounding blocks
					if (xCheck != b.getX() || yCheck != b.getY() || zCheck != b.getZ()) {
						surroundingBlockList.add(w.getBlockAt(xCheck, yCheck, zCheck));
					}
				}
			}
		}
		
		return surroundingBlockList;
	}

	// Get a list of all blocks that represent the scope of a whole tree
	private List<Block> selectTree(World w, int x, int y, int z) {
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
			
			//Get and check the block
			Block block = li.next();
			
			// Ensure that it's surrounding blocks are added to the appropriate lists			
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
				// basic check to prevent dismantling a whole forest. TODO Consider throwing something
				return treeSelection;
			}
			
			// Remove this block from the list of blocks to check
			li.remove();
		}
		
		return treeSelection;
	}

}
