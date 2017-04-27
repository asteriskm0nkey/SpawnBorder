package com.asteriskmonkey.spawnborder.BorderCommand;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.cli.Options;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BorderCommandParameterizedTest {

	String input;
	String expectedOutput;
	Options opts = BorderCommand.getCLIOptions();
	
	public BorderCommandParameterizedTest(String input, String expectedOutput) {
		super();
		this.input = input;
		this.expectedOutput = expectedOutput;
	}
	
	@Parameters
	public static Collection<String[]> testConditions() {
		String[][] conds = {
				{"true", "h"},
				{"true", "m"},
				{"false", "so"}
		};
		
		return Arrays.asList(conds);
	}
	
	@Test
	public void testCLIOptions_AllOptionsExist() {
		assertEquals(Boolean.parseBoolean(expectedOutput), opts.hasShortOption(input));
	}
	

}
