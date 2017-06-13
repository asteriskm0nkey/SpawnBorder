package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CircleLocationComparatorTest {

	private CircleLocationComparator clc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		clc = new CircleLocationComparator(0, 0);
	}

	/* ===============================
	 * Quadrant Tests
	 * ===============================
	 */  
	
	@Test
	public void CircleLocationComparator_Quadrant1() {
		int quad = clc.getQuadrant(10, 10);
		assertEquals(1, quad);
	}
	
	@Test
	public void CircleLocationComparator_Quadrant2() {
		int quad = clc.getQuadrant(10, -10);
		assertEquals(2, quad);
	}
	
	@Test
	public void CircleLocationComparator_Quadrant3() {
		int quad = clc.getQuadrant(-10, -10);
		assertEquals(3, quad);
	}
	
	@Test
	public void CircleLocationComparator_Quadrant4() {
		int quad = clc.getQuadrant(-10, 10);
		assertEquals(4, quad);
	}
	
	@Test
	public void CircleLocationComparator_Quadrant_Border_1_to_2() {
		int quadp1 = clc.getQuadrant(10, 1);
		int quadp2 = clc.getQuadrant(10, 0);
		assertEquals(1, quadp1);
		assertEquals(2, quadp2);
	}
	
	@Test
	public void CircleLocationComparator_Quadrant_Border_2_to_3() {
		int quadp1 = clc.getQuadrant(0, -10);
		int quadp2 = clc.getQuadrant(-1, -10);
		assertEquals(2, quadp1);
		assertEquals(3, quadp2);
	}
	
	@Test
	public void CircleLocationComparator_Quadrant_Border_3_to_4() {
		int quadp1 = clc.getQuadrant(-10, 0);
		int quadp2 = clc.getQuadrant(-10, 1);
		assertEquals(3, quadp1);
		assertEquals(4, quadp2);
	}
	
	@Test
	public void CircleLocationComparator_Quadrant_Border_4_to_1() {
		int quadp1 = clc.getQuadrant(-1, 10);
		int quadp2 = clc.getQuadrant(0, 10);
		assertEquals(4, quadp1);
		assertEquals(1, quadp2);
	}
	
	/* ===============================
	 * Circle Sort Tests
	 * ===============================
	 */	
	
	
	@Mock
	Location loc1;
	@Mock
	Location loc2;

	@Test
	public void CircleLocationComparator_Quadrant1_vs_Quadrant2() {
		List<Location> ls = new LinkedList<>();

		when(loc1.getX()).thenReturn(5.0);
		when(loc1.getZ()).thenReturn(7.0);

		when(loc2.getX()).thenReturn(5.0);
		when(loc2.getZ()).thenReturn(-7.0);

		ls.add(loc1);
		ls.add(loc2);
		ls.sort(clc);
		assertEquals(loc1, ls.get(0));
		assertEquals(loc2, ls.get(1));

	}

	@Test
	public void CircleLocationComparator_Quadrant1_vs_Quadrant3() {
		List<Location> ls = new LinkedList<>();

		when(loc1.getX()).thenReturn(5.0);
		when(loc1.getZ()).thenReturn(7.0);

		when(loc2.getX()).thenReturn(-5.0);
		when(loc2.getZ()).thenReturn(-7.0);

		ls.add(loc1);
		ls.add(loc2);
		ls.sort(clc);
		assertEquals(loc1, ls.get(0));
		assertEquals(loc2, ls.get(1));

	}

	@Test
	public void CircleLocationComparator_Quadrant1_vs_Quadrant4() {
		List<Location> ls = new LinkedList<>();

		when(loc1.getX()).thenReturn(5.0);
		when(loc1.getZ()).thenReturn(7.0);

		when(loc2.getX()).thenReturn(-5.0);
		when(loc2.getZ()).thenReturn(7.0);

		ls.add(loc1);
		ls.add(loc2);
		ls.sort(clc);
		assertEquals(loc1, ls.get(0));
		assertEquals(loc2, ls.get(1));
	}

	@Test
	public void CircleLocationComparator_Quadrant4_vs_Quadrant1() {
		List<Location> ls = new LinkedList<>();

		when(loc1.getX()).thenReturn(-5.0);
		when(loc1.getZ()).thenReturn(7.0);

		when(loc2.getX()).thenReturn(5.0);
		when(loc2.getZ()).thenReturn(7.0);

		ls.add(loc1);
		ls.add(loc2);
		ls.sort(clc);
		assertEquals(loc2, ls.get(0));
		assertEquals(loc1, ls.get(1));
	}

	@Test
	public void CircleLocationComparator_Quadrant1_loc1_lt_loc2() {
		List<Location> ls = new LinkedList<>();

		when(loc1.getX()).thenReturn(5.0);
		when(loc1.getZ()).thenReturn(7.0);

		when(loc2.getX()).thenReturn(7.0);
		when(loc2.getZ()).thenReturn(5.0);
		
		ls.add(loc1);
		ls.add(loc2);
		ls.sort(clc);
		
		assertEquals(loc1, ls.get(0));
		assertEquals(loc2, ls.get(1));
	}

	@Test
	public void CircleLocationComparator_Quadrant1_loc1_gt_loc2() {
		List<Location> ls = new LinkedList<>();

		when(loc1.getX()).thenReturn(7.0);
		when(loc1.getZ()).thenReturn(5.0);

		when(loc2.getX()).thenReturn(5.0);
		when(loc2.getZ()).thenReturn(7.0);

		ls.add(loc1);
		ls.add(loc2);
		ls.sort(clc);
		assertEquals(loc2, ls.get(0));
		assertEquals(loc1, ls.get(1));
	}

	@Test
	public void CircleLocationComparator_Quadrant1_loc1_z_eq_loc2() {
		List<Location> ls = new LinkedList<>();

		when(loc1.getX()).thenReturn(7.0);
		when(loc1.getZ()).thenReturn(7.0);

		when(loc2.getX()).thenReturn(7.0);
		when(loc2.getZ()).thenReturn(5.0);

		ls.add(loc1);
		ls.add(loc2);
		ls.sort(clc);
		assertEquals(loc1, ls.get(0));
		assertEquals(loc2, ls.get(1));
	}

	@Test
	public void CircleLocationComparator_Mixed_Quadrants() {

		// Comparison- o1 [1.0,3.0] o2 [0.0,-3.0] return -1
		List<Location> ls = new LinkedList<>();

		when(loc1.getX()).thenReturn(0.0);
		when(loc1.getZ()).thenReturn(-3.0);

		when(loc2.getX()).thenReturn(1.0);
		when(loc2.getZ()).thenReturn(3.0);

		ls.add(loc1);
		ls.add(loc2);
		ls.sort(clc);
		assertEquals(loc2, ls.get(0));
		assertEquals(loc1, ls.get(1));
	}

	@Test
	public void CircleLocationComparator_AdjacentPoints() {

		// Comparison- o1 [1.0,3.0] o2 [0.0,3.0] return 1
		List<Location> ls = new LinkedList<>();

		when(loc1.getX()).thenReturn(0.0);
		when(loc1.getZ()).thenReturn(3.0);

		when(loc2.getX()).thenReturn(1.0);
		when(loc2.getZ()).thenReturn(3.0);

		ls.add(loc1);
		ls.add(loc2);
		ls.sort(clc);
		assertEquals(loc1, ls.get(0));
		assertEquals(loc2, ls.get(1));
	}

	@Test
	public void CircleLocationComparator_FullCircle_radius3() {
		
		List<Location> ls = new LinkedList<>();
		
		double[][] unsortedCoords = { {2,2}, {0,-3}, {-2,2}, {1,3}, {0,3}, {3,1}, {3,-1}, {1,-3}, {-1,-3}, {2,-2}, {-2,-2}, {-3,-1},
				{-3,0}, {-1,3}, {-3,1}, {3,0} };
		double[][] sortedCoords = { {0,3}, {1,3}, {2,2}, {3,1}, {3,0}, {3,-1}, {2,-2}, {1,-3}, {0,-3}, {-1,-3}, {-2,-2}, {-3,-1},
				{-3,0}, {-3,1}, {-2,2}, {-1,3}};
		
		for (double[] coordPair : unsortedCoords) {
			Location loc = mock(Location.class);
			when(loc.getX()).thenReturn(coordPair[0]);
			when(loc.getZ()).thenReturn(coordPair[1]);
			ls.add(loc);
		}
		
		ls.sort(clc);
		
		int i = 0;
		double delta = 0.01; // only needs to be accurate to the nearest whole number
		for (double[] coordPair : sortedCoords) {
			assertEquals(coordPair[0], ls.get(i).getX(), delta);
			assertEquals(coordPair[1], ls.get(i).getZ(), delta);
			i++;
		}
	}
	
	@Test(expected=RuntimeException.class)
	public void CircleLocationComparator_Invalid_Quadrant() {
		List<Location> ls = new LinkedList<>();

		when(loc1.getX()).thenReturn(0.0);
		when(loc1.getZ()).thenReturn(3.0);

		when(loc2.getX()).thenReturn(1.0);
		when(loc2.getZ()).thenReturn(3.0);
		
		when(clc.getQuadrant(anyDouble(), anyDouble())).thenReturn(5);

		ls.add(loc1);
		ls.add(loc2);
		ls.sort(clc); // throws a RuntimeException
		
	}

}