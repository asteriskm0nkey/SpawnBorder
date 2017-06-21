package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory.LocationHelper.LocationMockHelper;

public class LocationHelperTest {

	// TODO test sinking below water and trees
	
	// FIXME separate unit tests from Integration tests.
	
	@Mock World world;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		when(world.getMaxHeight()).thenReturn(255);
	}

	@Mock LocationMockHelper locMockHelper;
	@Mock Location location;
	@Mock Block block;
	
	@Test
	public void LocationHelper_GetHighestBlockYAt_Normal() {
		when(world.getEnvironment()).thenReturn(Environment.NORMAL);
		
		
		// Substitute a HighestBlockHelper into the call to limit the usage of "new Location" within the getHighestBlockYAt
		when(locMockHelper.getNewLocation(any(World.class), anyLong(), anyLong(), anyLong())).thenReturn(location);
		when(location.getBlock()).thenReturn(block);
		when(block.getType()).thenReturn(Material.AIR).thenReturn(Material.AIR).thenReturn(Material.GRASS);
		
		long x = 0;
		long yGuide = 60;
		long z = 0;
		
		long y = LocationHelper.getHighestBlockYAt(world, x, yGuide, z, locMockHelper);
		assertEquals(253, y);
	}

	@Test
	public void LocationHelper_GetHighestBlockYAt_Nether() {
		when(world.getEnvironment()).thenReturn(Environment.NETHER);
		
		// Substitute a HighestBlockHelper into the call to limit the usage of "new Location" within the getHighestBlockYAt
		when(locMockHelper.getNewLocation(any(World.class), anyLong(), anyLong(), anyLong())).thenReturn(location);
		when(location.getBlock()).thenReturn(block);
		when(block.getType()).thenReturn(Material.AIR).thenReturn(Material.AIR).thenReturn(Material.GRASS);
		
		long x = 0;
		long yGuide = 60;
		long z = 0;
		
		long y = LocationHelper.getHighestBlockYAt(world, x, yGuide, z, locMockHelper);
		assertEquals(58, y);
	}
	
	@Test
	public void LocationHelper_GetHighestBlockYAt_Nether_YBelowMinimum() {
		when(world.getEnvironment()).thenReturn(Environment.NETHER);
		
		// Substitute a HighestBlockHelper into the call to limit the usage of "new Location" within the getHighestBlockYAt
		when(locMockHelper.getNewLocation(any(World.class), anyLong(), anyLong(), anyLong())).thenReturn(location);
		when(location.getBlock()).thenReturn(block);
		when(block.getType()).thenReturn(Material.AIR).thenReturn(Material.AIR).thenReturn(Material.GRASS);
		
		long x = 0;
		long yGuide = -60;
		long z = 0;
		
		long y = LocationHelper.getHighestBlockYAt(world, x, yGuide, z, locMockHelper);
		assertEquals(2, y);
	}
	
}
