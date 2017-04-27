package com.asteriskmonkey.spawnborder.BorderStrategies.Completion;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import static org.mockito.Mockito.*;

import org.bukkit.Location;
import org.bukkit.World;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.asteriskmonkey.spawnborder.Exceptions.InvalidArgumentException;

public class ColumnCompletionStrategyTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void getCompletedBorderLocations_nullException() throws InvalidArgumentException {
		ColumnCompletionStrategy strat = new ColumnCompletionStrategy();
		strat.getCompletedBorderLocations(null);
	}
	
	@Test
	public void getCompletedBorderLocationsEmptyList() throws InvalidArgumentException {
		ColumnCompletionStrategy strat = new ColumnCompletionStrategy();
		LinkedList<Location> locs = strat.getCompletedBorderLocations(new LinkedList<>());
		assertNotNull(locs);
		assertEquals(0, locs.size());
	}
	
	@Mock World world;
	
	@Test
	public void getCompletedBorderLocations_NoHeightChange() throws InvalidArgumentException {
		ColumnCompletionStrategy strat = new ColumnCompletionStrategy();
		
		LinkedList<Location> skeletonList = new LinkedList<>();
		for (int z = 0; z<=5; z++) {
			Location l = new Location(world, 0, 10, z); 
			skeletonList.add(l);
		}
		
		LinkedList<Location> locs = strat.getCompletedBorderLocations(skeletonList);
		
		assertEquals(6, locs.size());
		
	}
	
	@Test
	public void getCompletedBorderLocations_HeightChange() throws InvalidArgumentException {
		ColumnCompletionStrategy strat = new ColumnCompletionStrategy();
		
		LinkedList<Location> skeletonList = new LinkedList<>(
				Arrays.asList(
						new Location(world, 0, 10, 0),
						new Location(world, 0, 10, 1),
						new Location(world, 0, 12, 2),
						new Location(world, 0, 12, 3),
						new Location(world, 0, 10, 4),
						new Location(world, 0, 10, 5)));
		
		LinkedList<Location> locs = strat.getCompletedBorderLocations(skeletonList);
		
		assertEquals(8, locs.size());
		
		assertEquals(locs.get(0).getBlockY(), 10);
		assertEquals(locs.get(1).getBlockY(), 10);
		assertEquals(locs.get(2).getBlockY(), 11);
		assertEquals(locs.get(3).getBlockY(), 12);
		assertEquals(locs.get(4).getBlockY(), 12);
		assertEquals(locs.get(5).getBlockY(), 11);
		assertEquals(locs.get(6).getBlockY(), 10);
		assertEquals(locs.get(7).getBlockY(), 10);
	}
	
	@Test(expected=RuntimeException.class)
	public void getCompletedBorderLocations_WorldProblemException() throws InvalidArgumentException {
		ColumnCompletionStrategy strat = new ColumnCompletionStrategy();
		
		World world2 = mock(World.class);
		
		LinkedList<Location> skeletonList = new LinkedList<>();
		Location l1 = new Location(world, 0, 10, 0);
		Location l2 = new Location(world2, 0, 10, 1);
		skeletonList.add(l1);
		skeletonList.add(l2);
		
		strat.getCompletedBorderLocations(skeletonList);
	}
	

}
