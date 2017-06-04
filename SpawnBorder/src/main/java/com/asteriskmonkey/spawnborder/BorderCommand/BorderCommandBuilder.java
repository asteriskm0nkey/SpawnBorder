package com.asteriskmonkey.spawnborder.BorderCommand;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;

import com.asteriskmonkey.spawnborder.BorderCommand.BorderCommand.BorderShape;
import com.asteriskmonkey.spawnborder.Exceptions.InvalidArgumentException;
import com.asteriskmonkey.spawnborder.Exceptions.InvalidOptionException;

public final class BorderCommandBuilder {
	private BorderCommand borderCommand;

	private int minSize = 2;

	public BorderCommandBuilder() {
		borderCommand = new BorderCommand();
		try {
			this.setWorld("world"); // TODO read configuration to determine if this is the correct default world
		} catch (InvalidOptionException e) {
			throw new RuntimeException("Unexpected world name");
		}
	}

	public BorderCommandBuilder(Map<String, String> optionMap) throws InvalidOptionException, InvalidArgumentException {
		this();

		for (String optKey : optionMap.keySet()) {
			String optVal = optionMap.get(optKey);
			switch (optKey) {
			case "size":
				setSize(optVal);
				break;
			case "material":
				setMaterial(optVal);
				break;
			case "color":
				setColor(optVal);
				break;
			case "shape":
				setShape(optVal);
				break;
			case "center":
				setCenter(optVal);
				break;
			case "world":
				setWorld(optVal);
				break;
			case "sinkBelowWater":
				setSinkBelowWater(true);
				break;
			case "removeTrees":
				setRemoveTrees(true);
				break;
			default:
				throw new InvalidArgumentException("Unknown argument: " + optKey);
			}
		}
	}

	public BorderCommandBuilder setRemoveTrees(boolean b) {
		borderCommand.setRemoveTrees(b);
		return this;
	}

	public BorderCommandBuilder setSinkBelowWater(boolean b) {
		borderCommand.setSinkBelowWater(b);
		return this;
	}

	public BorderCommandBuilder setSize(String sizeStr) throws InvalidOptionException, NumberFormatException {
		// Format can be of the form: n or x,z

		int length = 0;
		int width = 0;
		int dividerLoc = sizeStr.indexOf(",");
		if (dividerLoc > 0) {
			length = Integer.parseInt(sizeStr.substring(0, dividerLoc));
			width = Integer.parseInt(sizeStr.substring(dividerLoc + 1, sizeStr.length()));

		} else {
			int size = Integer.parseInt(sizeStr);
			length = size;
			width = size;
		}

		return this.setSize(length, width);
	}

	public BorderCommandBuilder setSize(int length, int width) throws InvalidOptionException {
		if (length > minSize && width > minSize) {
			borderCommand.setSize(length, width);
		} else {
			throw new InvalidOptionException("Invalid size");
		}
		return this;
	}

	public BorderCommandBuilder setMaterial(String materialName) throws InvalidOptionException {
		Material m = Material.getMaterial(materialName.toUpperCase());
		if (m != null) {
			borderCommand.setMaterial(m);
		} else {
			throw new InvalidOptionException("Invalid material");
		}
		return this;
	}

	public BorderCommandBuilder setCenter(String centerStr) throws InvalidOptionException {
		long x = 0;
		long z = 0;
		int dividerLoc = centerStr.indexOf(",");
		// TODO more error checking on this string
		if (centerStr.length() == 0 || dividerLoc == -1) {
			throw new InvalidOptionException("Invalid format for option");
		}
		x = Long.parseLong(centerStr.substring(0, dividerLoc));
		z = Long.parseLong(centerStr.substring(dividerLoc + 1, centerStr.length()));

		return this.setCenter(x, z);
	}

	public BorderCommandBuilder setCenter(long x, long z) {
		borderCommand.setCenter(x, z);
		return this;
	}

	public BorderCommandBuilder setShape(String shape) {
		// use the bordershape enum
		try {
			BorderShape b = BorderShape.valueOf(shape.toUpperCase());
			borderCommand.setShape(b);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Shape not defined");
		}
		return this;
	}

	public BorderCommandBuilder setWorld(String world) throws InvalidOptionException {

		World w = Bukkit.getWorld(world);
		if (w == null) {
			throw new InvalidOptionException("Invalid world choice");
		}

		borderCommand.setWorld(w);
		return this;
	}

	// Converts from a string colour to a bukkit Color if it exists
	public BorderCommandBuilder setColor(String colour) throws InvalidOptionException {
		
		if (colour.length() == 0) {
			throw new InvalidOptionException("Invalid colour - 0 length string");
		}
		
		DyeColor d = null;
		try {
			 d = DyeColor.valueOf(colour.toUpperCase());
		} catch (IllegalArgumentException e) {
			// valueOf throws exception if the colour doesn't exist.
			throw new InvalidOptionException("Invalid colour");
		}

		if (d != null) {
			borderCommand.setColor(d);
		}
		return this;
	}
	
	public BorderCommand build() throws InvalidArgumentException {

		if (borderCommand.getLength() == 0 || borderCommand.getWidth() == 0) {
			throw new InvalidArgumentException("Border size is 0");
		}

		BorderCommand returnBC = new BorderCommand();
		try {
			returnBC = borderCommand.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("BorderCommand failed to clone itself.");
		}
		return returnBC;
	}
}