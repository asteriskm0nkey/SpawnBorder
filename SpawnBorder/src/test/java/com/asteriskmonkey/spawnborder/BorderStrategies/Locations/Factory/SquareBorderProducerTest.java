package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import static org.junit.Assert.assertEquals;
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
public class SquareBorderProducerTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		PowerMockito.mockStatic(LocationHelper.class);
		when(LocationHelper.getHighestBlockYAt(any(World.class), anyLong(), anyLong(), anyLong())).thenReturn(60L);
	}

	@Mock World world;
	
	@Test
	public void SquareBorderProducer_TestLocations_Normal_0() {
		
		LinkedList<Location> ls = SquareBorderProducer.getLocations(world, 0, 60, 0, 4, 4);
		assertEquals(12, ls.size());
		assertEquals("Location{world=world,x=-2.0,y=60.0,z=-2.0,pitch=0.0,yaw=0.0}", ls.get(0).toString());
		assertEquals("Location{world=world,x=-1.0,y=60.0,z=-2.0,pitch=0.0,yaw=0.0}", ls.get(1).toString());
		assertEquals("Location{world=world,x=0.0,y=60.0,z=-2.0,pitch=0.0,yaw=0.0}", ls.get(2).toString());
		assertEquals("Location{world=world,x=1.0,y=60.0,z=-2.0,pitch=0.0,yaw=0.0}", ls.get(3).toString());
		assertEquals("Location{world=world,x=1.0,y=60.0,z=-1.0,pitch=0.0,yaw=0.0}", ls.get(4).toString());
		assertEquals("Location{world=world,x=1.0,y=60.0,z=0.0,pitch=0.0,yaw=0.0}", ls.get(5).toString());
		assertEquals("Location{world=world,x=1.0,y=60.0,z=1.0,pitch=0.0,yaw=0.0}", ls.get(6).toString());
		assertEquals("Location{world=world,x=0.0,y=60.0,z=1.0,pitch=0.0,yaw=0.0}", ls.get(7).toString());
		assertEquals("Location{world=world,x=-1.0,y=60.0,z=1.0,pitch=0.0,yaw=0.0}", ls.get(8).toString());
		assertEquals("Location{world=world,x=-2.0,y=60.0,z=1.0,pitch=0.0,yaw=0.0}", ls.get(9).toString());
		assertEquals("Location{world=world,x=-2.0,y=60.0,z=0.0,pitch=0.0,yaw=0.0}", ls.get(10).toString());
		assertEquals("Location{world=world,x=-2.0,y=60.0,z=-1.0,pitch=0.0,yaw=0.0}", ls.get(11).toString());
	}
	
	@Test
	public void SquareBorderProducer_TestLocations_Normal_Positive() {
		
		LinkedList<Location> ls = SquareBorderProducer.getLocations(world, 10, 60, 10, 4, 4);
		assertEquals(12, ls.size());
		assertEquals("Location{world=world,x=8.0,y=60.0,z=8.0,pitch=0.0,yaw=0.0}", ls.get(0).toString());
		assertEquals("Location{world=world,x=9.0,y=60.0,z=8.0,pitch=0.0,yaw=0.0}", ls.get(1).toString());
		assertEquals("Location{world=world,x=10.0,y=60.0,z=8.0,pitch=0.0,yaw=0.0}", ls.get(2).toString());
		assertEquals("Location{world=world,x=11.0,y=60.0,z=8.0,pitch=0.0,yaw=0.0}", ls.get(3).toString());
		assertEquals("Location{world=world,x=11.0,y=60.0,z=9.0,pitch=0.0,yaw=0.0}", ls.get(4).toString());
		assertEquals("Location{world=world,x=11.0,y=60.0,z=10.0,pitch=0.0,yaw=0.0}", ls.get(5).toString());
		assertEquals("Location{world=world,x=11.0,y=60.0,z=11.0,pitch=0.0,yaw=0.0}", ls.get(6).toString());
		assertEquals("Location{world=world,x=10.0,y=60.0,z=11.0,pitch=0.0,yaw=0.0}", ls.get(7).toString());
		assertEquals("Location{world=world,x=9.0,y=60.0,z=11.0,pitch=0.0,yaw=0.0}", ls.get(8).toString());
		assertEquals("Location{world=world,x=8.0,y=60.0,z=11.0,pitch=0.0,yaw=0.0}", ls.get(9).toString());
		assertEquals("Location{world=world,x=8.0,y=60.0,z=10.0,pitch=0.0,yaw=0.0}", ls.get(10).toString());
		assertEquals("Location{world=world,x=8.0,y=60.0,z=9.0,pitch=0.0,yaw=0.0}", ls.get(11).toString());
	}
	
	@Test
	public void SquareBorderProducer_TestLocations_Normal_Negative() {
		
		LinkedList<Location> ls = SquareBorderProducer.getLocations(world, -10, 60, -10, 4, 4);
		assertEquals(12, ls.size());
		assertEquals("Location{world=world,x=-12.0,y=60.0,z=-12.0,pitch=0.0,yaw=0.0}", ls.get(0).toString());
		assertEquals("Location{world=world,x=-11.0,y=60.0,z=-12.0,pitch=0.0,yaw=0.0}", ls.get(1).toString());
		assertEquals("Location{world=world,x=-10.0,y=60.0,z=-12.0,pitch=0.0,yaw=0.0}", ls.get(2).toString());
		assertEquals("Location{world=world,x=-9.0,y=60.0,z=-12.0,pitch=0.0,yaw=0.0}", ls.get(3).toString());
		assertEquals("Location{world=world,x=-9.0,y=60.0,z=-11.0,pitch=0.0,yaw=0.0}", ls.get(4).toString());
		assertEquals("Location{world=world,x=-9.0,y=60.0,z=-10.0,pitch=0.0,yaw=0.0}", ls.get(5).toString());
		assertEquals("Location{world=world,x=-9.0,y=60.0,z=-9.0,pitch=0.0,yaw=0.0}", ls.get(6).toString());
		assertEquals("Location{world=world,x=-10.0,y=60.0,z=-9.0,pitch=0.0,yaw=0.0}", ls.get(7).toString());
		assertEquals("Location{world=world,x=-11.0,y=60.0,z=-9.0,pitch=0.0,yaw=0.0}", ls.get(8).toString());
		assertEquals("Location{world=world,x=-12.0,y=60.0,z=-9.0,pitch=0.0,yaw=0.0}", ls.get(9).toString());
		assertEquals("Location{world=world,x=-12.0,y=60.0,z=-10.0,pitch=0.0,yaw=0.0}", ls.get(10).toString());
		assertEquals("Location{world=world,x=-12.0,y=60.0,z=-11.0,pitch=0.0,yaw=0.0}", ls.get(11).toString());
	}
	
}
