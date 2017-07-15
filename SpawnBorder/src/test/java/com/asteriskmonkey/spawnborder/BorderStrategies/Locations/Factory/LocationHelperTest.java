package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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

	@Mock World world;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		when(world.getMaxHeight()).thenReturn(255);
	}

	@Mock LocationMockHelper locMockHelper;
	@Mock Location l;
	@Mock Block block;
	
	@Test
	public void LocationHelper_GetHighestBlockYAt_Normal() {
		when(world.getEnvironment()).thenReturn(Environment.NORMAL);
		
		
		// Substitute a HighestBlockHelper into the call to limit the usage of "new Location" within the getHighestBlockYAt
		when(locMockHelper.getNewLocation(any(World.class), anyLong(), anyLong(), anyLong())).thenReturn(l);
		when(l.getBlock()).thenReturn(block);
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
		when(locMockHelper.getNewLocation(any(World.class), anyLong(), anyLong(), anyLong())).thenReturn(l);
		when(l.getBlock()).thenReturn(block);
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
		when(locMockHelper.getNewLocation(any(World.class), anyLong(), anyLong(), anyLong())).thenReturn(l);
		when(l.getBlock()).thenReturn(block);
		when(block.getType()).thenReturn(Material.AIR).thenReturn(Material.AIR).thenReturn(Material.GRASS);
		
		long x = 0;
		long yGuide = -60;
		long z = 0;
		
		long y = LocationHelper.getHighestBlockYAt(world, x, yGuide, z, locMockHelper);
		assertEquals(2, y);
	}
	
	@Test
	public void LocationHelper_TestSinkBelowWater1() {
		
		Location spyLoc = spy(new Location(world, 0, 200, 0));
		
		List<Location> origLi = Arrays.asList(spyLoc);

		when(spyLoc.getBlock()).thenReturn(block);
		when(block.getType()).thenReturn(Material.WATER).thenReturn(Material.GRASS);
		
		List<Location> li = LocationHelper.sinkBelowWater(origLi);
		
		assertEquals(1, li.size());
		
		assertEquals(199, li.get(0).getBlockY());

	}
	
	@Test
	public void LocationHelper_TestSinkBelowWater3() {
		
		Location spyLoc = spy(new Location(world, 0, 200, 0));
		
		List<Location> origLi = Arrays.asList(spyLoc);

		when(spyLoc.getBlock()).thenReturn(block);
		when(block.getType()).thenReturn(Material.WATER).thenReturn(Material.WATER).thenReturn(Material.WATER).thenReturn(Material.GRASS);
		
		List<Location> li = LocationHelper.sinkBelowWater(origLi);
		
		assertEquals(1, li.size());
		
		assertEquals(197, li.get(0).getBlockY());

	}
	
	@Test
	public void LocationHelper_TestSinkBelowPlants1() {
		
		Location spyLoc = spy(new Location(world, 0, 200, 0));
		
		List<Location> origLi = Arrays.asList(spyLoc);

		when(spyLoc.getBlock()).thenReturn(block);
		when(block.getType()).thenReturn(Material.YELLOW_FLOWER).thenReturn(Material.GRASS);
		
		List<Location> li = LocationHelper.sinkBelowPlants(origLi);
		
		assertEquals(1, li.size());
		
		assertEquals(199, li.get(0).getBlockY());

	}
	
	@Test
	public void LocationHelper_TestSinkBelowPlants2() {
		
		Location spyLoc = spy(new Location(world, 0, 200, 0));
		
		List<Location> origLi = Arrays.asList(spyLoc);

		when(spyLoc.getBlock()).thenReturn(block);
		when(block.getType()).thenReturn(Material.SUGAR_CANE_BLOCK).thenReturn(Material.SUGAR_CANE_BLOCK).thenReturn(Material.SAND);
		
		List<Location> li = LocationHelper.sinkBelowPlants(origLi);
		
		assertEquals(1, li.size());
		
		assertEquals(198, li.get(0).getBlockY());

	}
	
	@Test
	public void LocationHelper_TestSinkBelowTree() {
		
		Location spyLoc = spy(new Location(world, 0, 200, 0));
		
		List<Location> origLi = Arrays.asList(spyLoc);

		when(spyLoc.getBlock()).thenReturn(block);
		when(block.getType()).thenReturn(Material.LEAVES).thenReturn(Material.LEAVES_2).thenReturn(Material.LOG)
			.thenReturn(Material.LOG_2).thenReturn(Material.GRASS);
		
		List<Location> li = LocationHelper.sinkBelowPlants(origLi);
		
		assertEquals(1, li.size());
		
		assertEquals(196, li.get(0).getBlockY());

	}
	
	
	
}
