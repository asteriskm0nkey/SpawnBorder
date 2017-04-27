package com.asteriskmonkey.spawnborder.BorderCommand;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.asteriskmonkey.spawnborder.Exceptions.InvalidArgumentException;
import com.asteriskmonkey.spawnborder.Exceptions.InvalidOptionException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Bukkit.class)
public class BorderCommandBuilderTest {

	@Mock World w;
	
	@Before
	public void setup() throws NumberFormatException, InvalidOptionException {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(Bukkit.class);
		
		when(Bukkit.getWorld(anyString())).thenReturn(w);
		when(w.getName()).thenReturn("world");
		
		builder = new BorderCommandBuilder();
		builder.setSize("5");
	}

	BorderCommandBuilder builder = null;

	/* =================================
	 * 			Basic Case
	 * ================================= */

	@Test
	public void BorderCommandBuilder_BasicSize() throws InvalidArgumentException {
		assertEquals("A SQUARE RED WOOL border 5x5 at 0,0 in world world ", builder.build().toString());
	}
	
	/* =================================
	 * 			Constructor optionMap
	 * ================================= */
	
	@Test
	public void BorderCommandBuilder_OptionMap_Empty() throws InvalidArgumentException, InvalidOptionException {
		Map<String,String> optionMap = Collections.EMPTY_MAP;
		builder = new BorderCommandBuilder(optionMap);
	}
	
	@Test
	public void BorderCommandBuilder_OptionMap() throws InvalidArgumentException, InvalidOptionException {
		Map<String,String> optionMap = new HashMap<>();
		optionMap.put("size", "7");
		optionMap.put("material", "wool");
		optionMap.put("color", "red");
		optionMap.put("shape", "square");
		optionMap.put("center", "0,0");
		optionMap.put("world", "world");
		optionMap.put("sinkBelowWater", "");
		optionMap.put("removeTrees", "");
		
		builder = new BorderCommandBuilder(optionMap);
		assertEquals("A SQUARE RED WOOL border 7x7 at 0,0 in world world ", builder.build().toString());
	}
	
	@Test(expected=InvalidArgumentException.class)
	public void BorderCommandBuilder_OptionMap_InvalidOption_Exception() throws InvalidArgumentException, InvalidOptionException {
		Map<String,String> optionMap = new HashMap<>();
		optionMap.put("sdklfjsdf", "7");
		builder = new BorderCommandBuilder(optionMap);
	}

	/* =================================
	 * 			Center
	 * ================================= */

	@Test
	public void BorderCommandBuilder_BasicCenter()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setCenter("10,10");
		assertEquals("A SQUARE RED WOOL border 5x5 at 10,10 in world world ", builder.build().toString());
	}
	
	@Test(expected=InvalidOptionException.class)
	public void BorderCommandBuilder_InvalidCenter()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setCenter("");
	}
	
	/* =================================
	 * 			Shape
	 * ================================= */
	@Test
	public void BorderCommandBuilder_BasicShape()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setShape("circle");
		assertEquals("A CIRCLE RED WOOL border 5x5 at 0,0 in world world ", builder.build().toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void BorderCommandBuilder_Shape_DoesntExist_Exception()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setShape("sdjkfhsdf");
	}
	
	/* =================================
	 * 			World
	 * ================================= */
	@Test(expected=InvalidOptionException.class)
	public void BorderCommandBuilder_World_DoesntExist_Exception()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		when(Bukkit.getWorld(anyString())).thenReturn(null);
		builder.setWorld("Pandora");
	}	

	/* =================================
	 * 			Materials
	 * ================================= */
	@Test
	public void BorderCommandBuilder_BasicMaterial()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setMaterial("WOOD");
		assertEquals("A SQUARE WOOD border 5x5 at 0,0 in world world ", builder.build().toString());
	}

	@Test(expected = InvalidOptionException.class)
	public void BorderCommandBuilder_BasicMaterial_DoesntExist_Exception()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setMaterial("adamantium");
	}

	@Test
	public void BorderCommandBuilder_BasicMaterial_lowercase()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setMaterial("glass");
		assertEquals("A SQUARE GLASS border 5x5 at 0,0 in world world ", builder.build().toString());
	}
	
	/* =================================
	 * 			Colours
	 * ================================= */
	
	@Test
	public void BorderCommandBuilder_BasicColour()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setColor("green");
		assertEquals("A SQUARE GREEN WOOL border 5x5 at 0,0 in world world ", builder.build().toString());
	}

	@Test(expected = InvalidOptionException.class)
	public void BorderCommandBuilder_BasicColour_DoesntExist_Exception()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setColor("skjdfhsdf");
	}
	
	@Test(expected = InvalidOptionException.class)
	public void BorderCommandBuilder_BasicColour_0length_Exception()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setColor("");
	}
	
	/* =================================
	 * 			Size
	 * ================================= */

	@Test(expected = InvalidOptionException.class)
	public void BorderCommandBuilder_BasicSize_0_Exception()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setSize("0,0");
	}

	@Test
	public void BorderCommandBuilder_BasicSize_3()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setSize("3");
	}

	@Test(expected = InvalidOptionException.class)
	public void BorderCommandBuilder_BasicSize_22_Exception()
			throws InvalidArgumentException, NumberFormatException, InvalidOptionException {
		builder.setSize(2, 2);
	}
	

	/* =================================
	 * 			Build
	 * ================================= */

	@Test
	public void BorderCommandBuilder_Build() throws InvalidArgumentException {
		BorderCommand bc = builder.build();
		assertNotNull(bc);
	}
	
	@Mock BorderCommand borderCommand;
	
	@Test(expected=InvalidArgumentException.class)
	public void BorderCommandBuilder_Build_0length() throws InvalidArgumentException {
		Whitebox.setInternalState(builder, "borderCommand", borderCommand);
		when(borderCommand.getLength()).thenReturn(0);
		when(borderCommand.getWidth()).thenReturn(0);
		builder.build();
		verify(borderCommand).getLength();
		verify(borderCommand).getWidth();
	}
	
}
