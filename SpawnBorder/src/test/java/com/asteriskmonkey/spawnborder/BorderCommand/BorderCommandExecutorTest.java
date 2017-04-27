package com.asteriskmonkey.spawnborder.BorderCommand;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.asteriskmonkey.spawnborder.BorderStrategies.Completion.BorderCompletionStrategy;
import com.asteriskmonkey.spawnborder.BorderStrategies.Completion.ColumnCompletionStrategy;
import com.asteriskmonkey.spawnborder.BorderStrategies.Locations.BorderLocationStrategy;
import com.asteriskmonkey.spawnborder.BorderStrategies.Locations.SimpleBorderLocationStrategy;

public class BorderCommandExecutorTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Mock SimpleBorderLocationStrategy bls;
	@Mock ColumnCompletionStrategy bcs;
	
	@Test
	public void test() {
		BorderCommandExecutor ex = new BorderCommandExecutor(bls, bcs);
	}

}
