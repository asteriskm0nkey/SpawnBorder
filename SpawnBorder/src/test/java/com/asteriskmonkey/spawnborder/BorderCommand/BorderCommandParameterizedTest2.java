package com.asteriskmonkey.spawnborder.BorderCommand;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.cli.Options;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BorderCommandParameterizedTest2 {

	String input;
	Options opts = BorderCommand.getCLIOptions();
	
	public BorderCommandParameterizedTest2(String input) {
		super();
		this.input = input;
	}
	
	@Parameters
	public static Collection<String[]> testConditions() {
		String[][] conds = {
				{"h"},
				{"m"},
				{"si"}
		};
		
		return Arrays.asList(conds);
	}
	
	@Test
	public void testCLIOptions_AllOptionsExist() {
		assertTrue(opts.hasShortOption(input));
	}
	

}
