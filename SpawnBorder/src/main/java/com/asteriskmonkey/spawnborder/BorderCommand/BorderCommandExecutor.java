package com.asteriskmonkey.spawnborder.BorderCommand;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.Colorable;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitScheduler;

import com.asteriskmonkey.spawnborder.SpawnBorder;
import com.asteriskmonkey.spawnborder.BorderStrategies.Completion.BorderCompletionStrategy;
import com.asteriskmonkey.spawnborder.BorderStrategies.Locations.BorderLocationStrategy;
import com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory.LocationHelper;
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

		// The basic skeleton of the border, with a block on each x and z
		// location, but possibly missing blocks within the y axis that would
		// make the
		// list a complete border - the completion strategy fills in the gaps in
		// the y axis
		List<Location> basicBorderLocs = locationStrategy.getBorderLocations(bc.getWorld(), bc.getCenterX(),
				bc.getGuideY(), bc.getCenterZ(), bc.getLength(), bc.getWidth(), bc.getShape());

		basicBorderLocs = LocationHelper.sinkBelowPlants(basicBorderLocs);
		
		if (bc.getRemoveTrees()) {
			// removeTrees(highestBlocks);
		}
		
		if (bc.getSinkBelowWater()) {
			basicBorderLocs = LocationHelper.sinkBelowWater(basicBorderLocs);
		}

		// The completed border, depending on implementation, should fill in
		// these gaps to create a solid border
		List<Location> completedLocations = completionStrategy.getCompletedBorderLocations(basicBorderLocs);

		BukkitScheduler bs = Bukkit.getScheduler();

		int i = 0;
		for (Location l : completedLocations) {
			i++;
			bs.runTaskLater(SpawnBorder.getPlugin(SpawnBorder.class), () -> {
/*				System.out.println(l.getWorld() + ": " + l.getBlockX() + ","
						+ l.getBlockY() + "," + l.getBlockZ());*/
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
			}, i * 5L);
		}
	}
}
