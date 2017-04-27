package com.asteriskmonkey.spawnborder.BorderCommand;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.material.Colorable;
import org.bukkit.material.MaterialData;

public class BorderCommand implements Cloneable {
	//TODO add Serializable?
	// Store the command object

	static Options opts;
	static String usageText;
	
	static {
		opts = new Options();
		opts.addOption("h", "help", false, "Get this usage text");
		opts.addOption("m", "material", true, "Choose a material to make the border from");
		opts.addOption("c", "color", true, "Choose a color for the material (if appropriate)");
		opts.addOption("s", "size", true, "Choose a size for the border (default 200)");
		opts.addOption("sh", "shape", true, "Choose a shape for the border (default square)");
		opts.addOption("e", "center", true, "Choose a location for the center (default 0,0 or player location)");
		opts.addOption("w", "world", true, "Choose a world to apply the border (default normal)");
		opts.addOption("r", "removeTrees", false, "Remove trees along the border");
		opts.addOption("si", "sinkBelowWater", false, "Sink the border below water");
		
		StringBuilder usageBuilder = new StringBuilder();
		usageBuilder.append("Usage");
		for (Option opt: opts.getOptions())
		{
			usageBuilder.append("-");
			usageBuilder.append(opt.getOpt());
			usageBuilder.append(" or ");
			usageBuilder.append("--");
			usageBuilder.append(opt.getLongOpt());
			if (opt.hasArg()) {
				usageBuilder.append(" <argument>");
			}
			usageBuilder.append(": ");
			usageBuilder.append(opt.getDescription());
			usageBuilder.append(System.getProperty("line.separator"));
		}
		
		usageText = usageBuilder.toString();
	}

	private int length = 0;
	private int width = 0;
	private Material material = Material.WOOL;
	private DyeColor colour = DyeColor.RED;
	private World world;
	private BorderShape shape = BorderShape.SQUARE;
	private long centerX = 0;
	private long centerZ = 0;
	private boolean removeTrees = false;
	private boolean sinkBelowWater = false;
	
	// TODO border thickness?? Potentially big changes required in the shapeproducer algorithms
	// to account for the additional locations nearby in sorting.
	
	public static String getUsageText() {
		return usageText;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public DyeColor getColor() {
		return colour;
	}
	
	public long getCenterX() {
		return centerX;
	}
	
	public long getCenterZ() {
		return centerZ;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getWidth() {
		return width;
	}
	
	public World getWorld() {
		return world;
	}
	
	public boolean getSinkBelowWater() {
		return sinkBelowWater;
	}
	
	public boolean getRemoveTrees() {
		return removeTrees;
	}
	
	protected void setSize(int length, int width) {
		this.length = length;
		this.width = width;
	}

	protected void setMaterial(Material m) {
		
		Class<? extends MaterialData> materialDataClass = m.getData();
		if (!Colorable.class.isAssignableFrom(materialDataClass)) {
			// This material doesn't implement colorable, so can't have an assigned color
			colour = null;
		} else {
			//MaterialData md = materialDataClass;
				
			//.setColor(colour);
		}
		this.material = m;
	}

	protected void setColor(DyeColor color) {
		this.colour = color;
	}

	protected void setCenter(long x, long z) {
		this.centerX = x;
		this.centerZ = z;
	}

	protected void setWorld(World world) {
		
		this.world = world;
	}
	

	protected void setSinkBelowWater(boolean sinkBelowWater) {
		this.sinkBelowWater = sinkBelowWater;
	}

	protected void setRemoveTrees(boolean removeTrees) {
		this.removeTrees = removeTrees;
	}

	public enum BorderShape {
		SQUARE, RECTANGLE, CIRCLE, ELLIPSE
	}

	protected void setShape(BorderShape shape) {
		this.shape = shape; 
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("A ");

		res.append(shape.toString()).append(" ");
		
		if (colour != null) {
			res.append(colour.toString()).append(" ");
		}

		if (material != null) {
			res.append(material.toString()).append(" ");
		}

		res.append("border ");

		if (length > 0 && width > 0) {
			res.append(length).append("x").append(width).append(" ");
		}

		res.append("at ").append(centerX).append(",").append(centerZ).append(" ");

		if (world != null) {
			res.append("in world ").append(world.getName()).append(" ");
		}

		res.trimToSize();

		return res.toString();
	}
	
	public static Options getCLIOptions() {
		return opts;
	}
	
	public BorderCommand clone() throws CloneNotSupportedException {
		return (BorderCommand) super.clone();
	}
	
	public BorderShape getShape() {
		return shape;
	}

}
