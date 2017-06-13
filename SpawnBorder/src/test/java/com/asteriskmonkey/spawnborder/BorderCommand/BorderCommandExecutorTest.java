package com.asteriskmonkey.spawnborder.BorderCommand;

import static org.mockito.Mockito.when;

import org.bukkit.World;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.asteriskmonkey.spawnborder.BorderStrategies.Completion.ColumnCompletionStrategy;
import com.asteriskmonkey.spawnborder.BorderStrategies.Locations.SimpleBorderLocationStrategy;
import com.asteriskmonkey.spawnborder.Exceptions.InvalidArgumentException;

public class BorderCommandExecutorTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Mock SimpleBorderLocationStrategy bls;
	@Mock ColumnCompletionStrategy bcs;
	@Mock BorderCommand bc;
	@Mock World world;
	
	@Test(expected=RuntimeException.class)
	public void BorderCommandExecutor_Execute_NullWorld() throws InvalidArgumentException {
		when(bc.getWorld()).thenReturn(null);
		BorderCommandExecutor ex = new BorderCommandExecutor(bls, bcs);
		ex.execute(bc);
	}
	
	@Ignore
	@Test
	public void BorderCommandExecutor_Execute_ValidWorld() throws InvalidArgumentException {
		when(bc.getWorld()).thenReturn(world);
		
		BorderCommandExecutor ex = new BorderCommandExecutor(bls, bcs);
		ex.execute(bc);
	}

}
