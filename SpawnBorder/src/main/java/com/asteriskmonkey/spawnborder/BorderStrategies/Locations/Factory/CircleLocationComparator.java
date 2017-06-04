package com.asteriskmonkey.spawnborder.BorderStrategies.Locations.Factory;

import java.util.Comparator;

import org.bukkit.Location;

public class CircleLocationComparator implements Comparator<Location> {

	private long centreX = 0;
	private long centreZ = 0;
	
	public CircleLocationComparator(long centreX, long centreZ) {
		this.centreX = centreX;
		this.centreZ = centreZ;
	}
	
	/*  From Minecraft Documentation:
	    ------------------------------
	    Coordinates numerically represent your location in a Minecraft world. They are based on a grid where three lines or axes intersect at the origin point. Players initially spawn within a couple hundred blocks of the origin point.
		the x-axis indicates the player's distance east (positive) or west (negative) of the origin point—i.e., the longitude,
		the z-axis indicates the player's distance south (positive) or north (negative) of the origin point—i.e., the latitude,
		the y-axis indicates how high or low (from 0 to 255, with 64 being sea level) the player is—i.e., the elevation,
		thereby forming a right-handed coordinate system (thumb==x, index==y, middle==z), making it easy to remember which axis is which.
	 */
	
	
	/* The below was written assuming a left-handed coordinate system, while Minecraft uses a right-handed system.
	 * The result is that the circle is sorted to be sketched out in an anticlockwise direction, which ultimately makes no difference
	 * but explains the direction of the various comparisons being used.
	 *
	 *		          , - ~|~ - ,
	 *		     , o8      |      o1,
	 *		   ,           |          ,
	 *		  o7     Q4    |   Q1      o2
	 *		 ,             |            ,
	 *		 --------------+-------------
	 *		 ,             |            ,
	 *		  o6     Q3    |   Q2      o3
	 *		   ,           |          ,
	 *		     ,         |       , '
	 *		       o5- , _ | _ o4'
	 * 
	 */
	
	public int getQuadrant(double pX, double pZ) {
		int quadrant = 0;
		if (pX >= centreX && pZ > centreZ) {
			quadrant = 1;
		} else if (pX >= centreX && pZ <= centreZ) {
			quadrant = 2;
		} else if (pX < centreX && pZ <= centreZ) {
			quadrant = 3;
		} else if (pX < centreX && pZ > centreZ) {
			quadrant = 4;
		}
			
		return quadrant;
	}
	
	@Override
	public int compare(Location o1, Location o2) {
		
		int returnVal = 0;
		
		double o1x = o1.getX();
		double o1z = o1.getZ();
		
		double o2x = o2.getX();
		double o2z = o2.getZ();
		
		// Check the quadrants of the two points. If they're in separate quadrants, use that to order.
		int quadrantPoint1 = getQuadrant(o1x, o1z);
		int quadrantPoint2 = getQuadrant(o2x, o2z);
		
		// Quadrant sort
		if (quadrantPoint1 > quadrantPoint2) {
			returnVal = 1;
		} else if (quadrantPoint1 < quadrantPoint2) {
			returnVal = -1;
		} else if (quadrantPoint1 == quadrantPoint2) {
			/* These points are in the same quadrant.
			 * The comparison differs per quadrant, o1 is before o2 in the hypothetical case.
			 *                     Z+
			 *		          , - ~|~ - ,
			 *		     , o8      |      o1,
			 *		   ,           |          ,
			 *		  o7     Q4    |   Q1      o2
			 *		 ,             |            ,
			 *	X-	 --------------+------------- X+
			 *		 ,             |            ,
			 *		  o6     Q3    |   Q2      o3
			 *		   ,           |          ,
			 *		     ,         |       , '
			 *		       o5- , _ | _ o4'
			 *                     Z-
			 */
			
			switch (quadrantPoint1) {
			case 1:
				if (o1x <= o2x && o1z >= o2z) {
					returnVal = -1;
				} else {
					returnVal = 1;
				}
				break;
			case 2:
				if (o1x >= o2x && o1z >= o2z) {
					returnVal = -1;
				} else {
					returnVal = 1;
				}
				break;
			case 3:
				if (o1x >= o2x && o1z <= o2z) {
					returnVal = -1;
				} else {
					returnVal = 1;
				}
				break;
			case 4:
				if (o1x <= o2x && o1z <= o2z) {
					returnVal = -1;
				} else {
					returnVal = 1;
				}
				break;
			default:
				throw new RuntimeException("Invalid Quadrant in Circular location sort!");
			}
		}
		
		//System.out.println("Comparison- o1 [" + o1x + "," + o1z + "] o2 [" + o2x + "," + o2z + "] return " + returnVal);
		
		return returnVal;
	}

}
