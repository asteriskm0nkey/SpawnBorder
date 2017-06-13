package com.asteriskmonkey.spawnborder.BorderCommand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.cli.Options;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class BorderCommandTest {

	private static BorderCommand bc;

	@BeforeClass
	public static void beforeClass() {
		bc = new BorderCommand();
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void CLIOptions_AllOptionsExist() {
		Options opts = BorderCommand.getCLIOptions();
		assertTrue(opts.hasShortOption("h"));
		assertTrue(opts.hasShortOption("m"));
		assertTrue(opts.hasShortOption("c"));
		assertTrue(opts.hasShortOption("s"));
		assertTrue(opts.hasShortOption("sh"));
		assertTrue(opts.hasShortOption("e"));
		assertTrue(opts.hasShortOption("w"));
		assertTrue(opts.hasShortOption("r"));
		assertTrue(opts.hasShortOption("si"));
		assertEquals(9, opts.getOptions().size());
	}
	
	@Test
	public void DefaultMaterial_WOOL() {
		assertEquals(Material.WOOL, bc.getMaterial());
	}

	@Test
	public void DefaultColour_RED() {
		assertEquals(DyeColor.RED, bc.getColor());
	}
	
	@Test
	public void DefaultCenterXZ() {
		assertEquals(0,bc.getCenterX());
		assertEquals(0,bc.getCenterZ());
	}
	
	@Test
	public void BorderCommand_getY() {
		assertEquals(60, bc.getGuideY());
	}

}
