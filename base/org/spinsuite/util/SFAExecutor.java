package org.spinsuite.util;

import java.util.concurrent.Executor;

public class SFAExecutor implements Executor{
	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		command.run();
	}	
}