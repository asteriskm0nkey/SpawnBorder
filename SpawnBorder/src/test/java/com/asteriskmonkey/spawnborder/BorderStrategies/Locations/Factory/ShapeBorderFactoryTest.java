package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.asteriskmonkey.spawnborder.BorderCommand.BorderCommand.BorderShape;

public class ShapeBorderFactoryTest {

	@Mock World world;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(world.getEnvironment()).thenReturn(Environment.NORMAL);
	}

	@Test
	public void ShapeFactory_SquareBorder() throws Exception {
		ShapeBorderFactory fact = new ShapeBorderFactory();
		
		LinkedList<Location> locs = fact.getLocationList(world, 0L, 60L, 0L, 5, 5, BorderShape.SQUARE);
		
		assertEquals(16,locs.size());
	}
	
	@Test
	public void ShapeFactory_RectangleBorder() throws Exception {
		ShapeBorderFactory fact = new ShapeBorderFactory();
		
		LinkedList<Location> locs = fact.getLocationList(world, 0L, 60L, 0L, 5, 10, BorderShape.RECTANGLE);
		
		assertEquals(26,locs.size());
	}
	
	@Test
	public void ShapeFactory_CircleBorder() throws Exception {
		ShapeBorderFactory fact = new ShapeBorderFactory();
		
		LinkedList<Location> locs = fact.getLocationList(world, 0L, 60L, 0L, 5, 5, BorderShape.CIRCLE);
		
		assertEquals(28,locs.size());
	}
	
	@Test
	public void ShapeFactory_EllipseBorder() throws Exception {
		ShapeBorderFactory fact = new ShapeBorderFactory();
		
		LinkedList<Location> locs = fact.getLocationList(world, 0L, 60L, 0L, 5, 10, BorderShape.ELLIPSE);
		
		assertEquals(44,locs.size());
	}
	
}
