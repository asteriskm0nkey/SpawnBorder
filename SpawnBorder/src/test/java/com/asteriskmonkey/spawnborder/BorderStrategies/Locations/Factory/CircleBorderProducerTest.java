package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.World;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LocationHelper.class)
public class CircleBorderProducerTest {

	@Mock
	World world;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(LocationHelper.class);
		when(LocationHelper.getHighestBlockYAt(any(World.class), anyLong(), anyLong(), anyLong())).thenReturn(60L);
	}

	@Test
	public void CircleBorderProducer_getLocations_0() {
		LinkedList<Location> ls = CircleBorderProducer.getLocations(world, 0, 60, 0, 4, 4);
		assertEquals(24, ls.size());
		assertEquals("Location{world=world,x=0.0,y=60.0,z=4.0,pitch=0.0,yaw=0.0}", ls.get(0).toString());
		assertEquals("Location{world=world,x=1.0,y=60.0,z=4.0,pitch=0.0,yaw=0.0}", ls.get(1).toString());
		assertEquals("Location{world=world,x=2.0,y=60.0,z=3.0,pitch=0.0,yaw=0.0}", ls.get(2).toString());
		assertEquals("Location{world=world,x=3.0,y=60.0,z=3.0,pitch=0.0,yaw=0.0}", ls.get(3).toString());
		assertEquals("Location{world=world,x=3.0,y=60.0,z=2.0,pitch=0.0,yaw=0.0}", ls.get(4).toString());
		assertEquals("Location{world=world,x=4.0,y=60.0,z=1.0,pitch=0.0,yaw=0.0}", ls.get(5).toString());
		assertEquals("Location{world=world,x=4.0,y=60.0,z=0.0,pitch=0.0,yaw=0.0}", ls.get(6).toString());
		assertEquals("Location{world=world,x=4.0,y=60.0,z=-1.0,pitch=0.0,yaw=0.0}", ls.get(7).toString());
		assertEquals("Location{world=world,x=3.0,y=60.0,z=-2.0,pitch=0.0,yaw=0.0}", ls.get(8).toString());
		assertEquals("Location{world=world,x=3.0,y=60.0,z=-3.0,pitch=0.0,yaw=0.0}", ls.get(9).toString());
		assertEquals("Location{world=world,x=2.0,y=60.0,z=-3.0,pitch=0.0,yaw=0.0}", ls.get(10).toString());
		assertEquals("Location{world=world,x=1.0,y=60.0,z=-4.0,pitch=0.0,yaw=0.0}", ls.get(11).toString());
		assertEquals("Location{world=world,x=0.0,y=60.0,z=-4.0,pitch=0.0,yaw=0.0}", ls.get(12).toString());
		assertEquals("Location{world=world,x=-1.0,y=60.0,z=-4.0,pitch=0.0,yaw=0.0}", ls.get(13).toString());
		assertEquals("Location{world=world,x=-2.0,y=60.0,z=-3.0,pitch=0.0,yaw=0.0}", ls.get(14).toString());
		assertEquals("Location{world=world,x=-3.0,y=60.0,z=-3.0,pitch=0.0,yaw=0.0}", ls.get(15).toString());
		assertEquals("Location{world=world,x=-3.0,y=60.0,z=-2.0,pitch=0.0,yaw=0.0}", ls.get(16).toString());
		assertEquals("Location{world=world,x=-4.0,y=60.0,z=-1.0,pitch=0.0,yaw=0.0}", ls.get(17).toString());
		assertEquals("Location{world=world,x=-4.0,y=60.0,z=0.0,pitch=0.0,yaw=0.0}", ls.get(18).toString());
		assertEquals("Location{world=world,x=-4.0,y=60.0,z=1.0,pitch=0.0,yaw=0.0}", ls.get(19).toString());
		assertEquals("Location{world=world,x=-3.0,y=60.0,z=2.0,pitch=0.0,yaw=0.0}", ls.get(20).toString());
		assertEquals("Location{world=world,x=-3.0,y=60.0,z=3.0,pitch=0.0,yaw=0.0}", ls.get(21).toString());
		assertEquals("Location{world=world,x=-2.0,y=60.0,z=3.0,pitch=0.0,yaw=0.0}", ls.get(22).toString());
		assertEquals("Location{world=world,x=-1.0,y=60.0,z=4.0,pitch=0.0,yaw=0.0}", ls.get(23).toString());
	}
	
	@Test
	public void CircleBorderProducer_getLocations_Positive() {
		LinkedList<Location> ls = CircleBorderProducer.getLocations(world, 60, 60, 60, 4, 4);
		assertEquals(24, ls.size());
		assertEquals("Location{world=world,x=60.0,y=60.0,z=64.0,pitch=0.0,yaw=0.0}", ls.get(0).toString());
		assertEquals("Location{world=world,x=61.0,y=60.0,z=64.0,pitch=0.0,yaw=0.0}", ls.get(1).toString());
		assertEquals("Location{world=world,x=62.0,y=60.0,z=63.0,pitch=0.0,yaw=0.0}", ls.get(2).toString());
		assertEquals("Location{world=world,x=63.0,y=60.0,z=63.0,pitch=0.0,yaw=0.0}", ls.get(3).toString());
		assertEquals("Location{world=world,x=63.0,y=60.0,z=62.0,pitch=0.0,yaw=0.0}", ls.get(4).toString());
		assertEquals("Location{world=world,x=64.0,y=60.0,z=61.0,pitch=0.0,yaw=0.0}", ls.get(5).toString());
		assertEquals("Location{world=world,x=64.0,y=60.0,z=60.0,pitch=0.0,yaw=0.0}", ls.get(6).toString());
		assertEquals("Location{world=world,x=64.0,y=60.0,z=59.0,pitch=0.0,yaw=0.0}", ls.get(7).toString());
		assertEquals("Location{world=world,x=63.0,y=60.0,z=58.0,pitch=0.0,yaw=0.0}", ls.get(8).toString());
		assertEquals("Location{world=world,x=63.0,y=60.0,z=57.0,pitch=0.0,yaw=0.0}", ls.get(9).toString());
		assertEquals("Location{world=world,x=62.0,y=60.0,z=57.0,pitch=0.0,yaw=0.0}", ls.get(10).toString());
		assertEquals("Location{world=world,x=61.0,y=60.0,z=56.0,pitch=0.0,yaw=0.0}", ls.get(11).toString());
		assertEquals("Location{world=world,x=60.0,y=60.0,z=56.0,pitch=0.0,yaw=0.0}", ls.get(12).toString());
		assertEquals("Location{world=world,x=59.0,y=60.0,z=56.0,pitch=0.0,yaw=0.0}", ls.get(13).toString());
		assertEquals("Location{world=world,x=58.0,y=60.0,z=57.0,pitch=0.0,yaw=0.0}", ls.get(14).toString());
		assertEquals("Location{world=world,x=57.0,y=60.0,z=57.0,pitch=0.0,yaw=0.0}", ls.get(15).toString());
		assertEquals("Location{world=world,x=57.0,y=60.0,z=58.0,pitch=0.0,yaw=0.0}", ls.get(16).toString());
		assertEquals("Location{world=world,x=56.0,y=60.0,z=59.0,pitch=0.0,yaw=0.0}", ls.get(17).toString());
		assertEquals("Location{world=world,x=56.0,y=60.0,z=60.0,pitch=0.0,yaw=0.0}", ls.get(18).toString());
		assertEquals("Location{world=world,x=56.0,y=60.0,z=61.0,pitch=0.0,yaw=0.0}", ls.get(19).toString());
		assertEquals("Location{world=world,x=57.0,y=60.0,z=62.0,pitch=0.0,yaw=0.0}", ls.get(20).toString());
		assertEquals("Location{world=world,x=57.0,y=60.0,z=63.0,pitch=0.0,yaw=0.0}", ls.get(21).toString());
		assertEquals("Location{world=world,x=58.0,y=60.0,z=63.0,pitch=0.0,yaw=0.0}", ls.get(22).toString());
		assertEquals("Location{world=world,x=59.0,y=60.0,z=64.0,pitch=0.0,yaw=0.0}", ls.get(23).toString());
	}

	@Test
	public void CircleBorderProducer_getLocations_Negative() {
		LinkedList<Location> ls = CircleBorderProducer.getLocations(world, -60, 60, -60, 4, 4);
		assertEquals(24, ls.size());
		assertEquals("Location{world=world,x=-60.0,y=60.0,z=-56.0,pitch=0.0,yaw=0.0}", ls.get(0).toString());
		assertEquals("Location{world=world,x=-59.0,y=60.0,z=-56.0,pitch=0.0,yaw=0.0}", ls.get(1).toString());
		assertEquals("Location{world=world,x=-58.0,y=60.0,z=-57.0,pitch=0.0,yaw=0.0}", ls.get(2).toString());
		assertEquals("Location{world=world,x=-57.0,y=60.0,z=-57.0,pitch=0.0,yaw=0.0}", ls.get(3).toString());
		assertEquals("Location{world=world,x=-57.0,y=60.0,z=-58.0,pitch=0.0,yaw=0.0}", ls.get(4).toString());
		assertEquals("Location{world=world,x=-56.0,y=60.0,z=-59.0,pitch=0.0,yaw=0.0}", ls.get(5).toString());
		assertEquals("Location{world=world,x=-56.0,y=60.0,z=-60.0,pitch=0.0,yaw=0.0}", ls.get(6).toString());
		assertEquals("Location{world=world,x=-56.0,y=60.0,z=-61.0,pitch=0.0,yaw=0.0}", ls.get(7).toString());
		assertEquals("Location{world=world,x=-57.0,y=60.0,z=-62.0,pitch=0.0,yaw=0.0}", ls.get(8).toString());
		assertEquals("Location{world=world,x=-57.0,y=60.0,z=-63.0,pitch=0.0,yaw=0.0}", ls.get(9).toString());
		assertEquals("Location{world=world,x=-58.0,y=60.0,z=-63.0,pitch=0.0,yaw=0.0}", ls.get(10).toString());
		assertEquals("Location{world=world,x=-59.0,y=60.0,z=-64.0,pitch=0.0,yaw=0.0}", ls.get(11).toString());
		assertEquals("Location{world=world,x=-60.0,y=60.0,z=-64.0,pitch=0.0,yaw=0.0}", ls.get(12).toString());
		assertEquals("Location{world=world,x=-61.0,y=60.0,z=-64.0,pitch=0.0,yaw=0.0}", ls.get(13).toString());
		assertEquals("Location{world=world,x=-62.0,y=60.0,z=-63.0,pitch=0.0,yaw=0.0}", ls.get(14).toString());
		assertEquals("Location{world=world,x=-63.0,y=60.0,z=-63.0,pitch=0.0,yaw=0.0}", ls.get(15).toString());
		assertEquals("Location{world=world,x=-63.0,y=60.0,z=-62.0,pitch=0.0,yaw=0.0}", ls.get(16).toString());
		assertEquals("Location{world=world,x=-64.0,y=60.0,z=-61.0,pitch=0.0,yaw=0.0}", ls.get(17).toString());
		assertEquals("Location{world=world,x=-64.0,y=60.0,z=-60.0,pitch=0.0,yaw=0.0}", ls.get(18).toString());
		assertEquals("Location{world=world,x=-64.0,y=60.0,z=-59.0,pitch=0.0,yaw=0.0}", ls.get(19).toString());
		assertEquals("Location{world=world,x=-63.0,y=60.0,z=-58.0,pitch=0.0,yaw=0.0}", ls.get(20).toString());
		assertEquals("Location{world=world,x=-63.0,y=60.0,z=-57.0,pitch=0.0,yaw=0.0}", ls.get(21).toString());
		assertEquals("Location{world=world,x=-62.0,y=60.0,z=-57.0,pitch=0.0,yaw=0.0}", ls.get(22).toString());
		assertEquals("Location{world=world,x=-61.0,y=60.0,z=-56.0,pitch=0.0,yaw=0.0}", ls.get(23).toString());
	}
	
}
