package com.asteriskmonkey.spawnborder.BorderCommand;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;

import com.asteriskmonkey.spawnborder.BorderCommand.BorderCommand.BorderShape;
import com.asteriskmonkey.spawnborder.Exceptions.InvalidArgumentException;
import com.asteriskmonkey.spawnborder.Exceptions.InvalidOptionException;

public final class BorderCommandBuilder {
	private static final long MINGUIDEY = 2;

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
			/*case "environment":
				setEnvironment(optVal);
				break;*/
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

	/*
	private BorderCommandBuilder setEnvironment(String optVal) throws InvalidOptionException {
		Environment envi = Environment.valueOf(optVal);
		if (envi == null) {
			throw new InvalidOptionException("Invalid environment '" + optVal + "'");
		}
		
		borderCommand.setEnvironment(envi);
		return this;
	}
	*/

	public BorderCommandBuilder setRemoveTrees(boolean b) {
		borderCommand.setRemoveTrees(b);
		return this;
	}

	public BorderCommandBuilder setSinkBelowWater(boolean b) {
		borderCommand.setSinkBelowWater(b);
		return this;
	}

	public BorderCommandBuilder setSize(String sizeStr) throws InvalidOptionException {
		// Format can be of the form: n or x,z

		int length = 0;
		int width = 0;
		
		try (Scanner sizeScanner = new Scanner(sizeStr)) {
			sizeScanner.useDelimiter(",");
			
			length = sizeScanner.nextInt();
			try {
				width = sizeScanner.nextInt();
			} catch (NoSuchElementException e) {
				width = length;
			}
		} catch (NoSuchElementException e) {
			throw new InvalidOptionException("Invalid size option + '" + sizeStr + "'");
		}

		return this.setSize(length, width);
	}

	public BorderCommandBuilder setSize(int length, int width) throws InvalidOptionException {
		if (length > minSize && width > minSize) {
			borderCommand.setSize(length, width);
		} else {
			throw new InvalidOptionException("Size too small - minimum is " + minSize);
		}
		return this;
	}

	public BorderCommandBuilder setMaterial(String materialName) throws InvalidOptionException {
		Material m = Material.getMaterial(materialName.toUpperCase());
		if (m != null) {
			borderCommand.setMaterial(m);
		} else {
			throw new InvalidOptionException("Invalid material '" + materialName + "'");
		}
		return this;
	}

	public BorderCommandBuilder setCenter(String centreStr) throws InvalidOptionException {
		long centreX = 0;
		long guideY = 0;
		long centre = 0;
		
		try (Scanner coordScanner = new Scanner(centreStr)) {
			coordScanner.useDelimiter(",");
			
			centreX = new Double(coordScanner.nextDouble()).longValue();
			guideY = new Double(coordScanner.nextDouble()).longValue();
			centre = new Double(coordScanner.nextDouble()).longValue();
			
			if (guideY < MINGUIDEY) {
				throw new InvalidOptionException("Invalid Y value '" + guideY + "'");
			}
		} catch (NoSuchElementException e) {
			throw new InvalidOptionException("Invalid format for center '" + centreStr + "'");
		}
		
		return this.setCenter(centreX, guideY, centre);
	}

	public BorderCommandBuilder setCenter(long centrex, long guideY, long centrez) {
		borderCommand.setCenter(centrex, guideY, centrez);
		return this;
	}

	public BorderCommandBuilder setShape(String shape) {
		// use the bordershape enum
		try {
			BorderShape b = BorderShape.valueOf(shape.toUpperCase());
			borderCommand.setShape(b);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Shape not defined '" + shape + "'");
		}
		return this;
	}

	public BorderCommandBuilder setWorld(String world) throws InvalidOptionException {

		World w = Bukkit.getWorld(world);
		if (w == null) {
			throw new InvalidOptionException("Invalid world choice + '" + world + "'");
		}

		borderCommand.setWorld(w);
		return this;
	}

	// Converts from a string colour to a bukkit Color if it exists
	public BorderCommandBuilder setColor(String colour) throws InvalidOptionException {
		
		if (colour.length() == 0) {
			throw new InvalidOptionException("Invalid colour ''");
		}
		
		DyeColor d = null;
		try {
			 d = DyeColor.valueOf(colour.toUpperCase());
		} catch (IllegalArgumentException e) {
			// valueOf throws exception if the colour doesn't exist.
			throw new InvalidOptionException("Invalid colour + '" + colour + "'");
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