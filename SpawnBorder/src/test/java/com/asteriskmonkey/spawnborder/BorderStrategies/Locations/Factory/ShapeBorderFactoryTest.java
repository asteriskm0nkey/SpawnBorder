package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;

import com.asteriskmonkey.spawnborder.BorderCommand.BorderCommand.BorderShape;

public class ShapeBorderFactoryTest {

	@Mock World world;
	@Mock Block block;
	@Mock Location location;
	@Mock LinkedList<Location> locations;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void ShapeFactory_SquareBorder() throws Exception {
		ShapeBorderFactory fact = new ShapeBorderFactory();
		
		when(world.getBlockAt(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(block);
		when(block.getLocation()).thenReturn(location);
		
		LinkedList<Location> locs = fact.getLocationList(world, 0L, 0L, 5, 5, BorderShape.SQUARE);
		
		assertEquals(16,locs.size());
	}
	
	@Test
	public void ShapeFactory_RectangleBorder() throws Exception {
		ShapeBorderFactory fact = new ShapeBorderFactory();
		
		when(world.getBlockAt(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(block);
		when(block.getLocation()).thenReturn(location);
		
		LinkedList<Location> locs = fact.getLocationList(world, 0L, 0L, 5, 10, BorderShape.RECTANGLE);
		
		assertEquals(26,locs.size());
	}
	
	@Test
	public void ShapeFactory_CircleBorder() throws Exception {
		ShapeBorderFactory fact = new ShapeBorderFactory();
		
		when(world.getBlockAt(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(block);
		when(block.getLocation()).thenReturn(location);
		
		LinkedList<Location> locs = fact.getLocationList(world, 0L, 0L, 5, 5, BorderShape.CIRCLE);
		
		assertEquals(28,locs.size());
	}
	
	@Test
	public void ShapeFactory_EllipseBorder() throws Exception {
		ShapeBorderFactory fact = new ShapeBorderFactory();
		
		when(world.getBlockAt(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(block);
		when(block.getLocation()).thenReturn(location);
		
		LinkedList<Location> locs = fact.getLocationList(world, 0L, 0L, 5, 10, BorderShape.ELLIPSE);
		
		assertEquals(44,locs.size());
	}
	
}
