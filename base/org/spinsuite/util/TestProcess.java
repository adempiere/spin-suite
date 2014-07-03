package org.spinsuite.util;

import org.spinsuite.interfaces.BackGroundProcess;

import android.content.Context;

public class TestProcess implements BackGroundProcess{

	public TestProcess(Context ctx) {
		// TODO Auto-generated constructor stub
		BackGroundTask task = new BackGroundTask(this, ctx);
		task.runTask();
	}
	
	@Override
	public void publishBeforeInit() {
		// TODO Auto-generated method stub
		System.out.println("pase1");
	}

	@Override
	public void publishOnRunning() {
		// TODO Auto-generated method stub
		System.out.println("pase2");
	}

	@Override
	public void publishAfterEnd() {
		// TODO Auto-generated method stub
		System.out.println("pase3");
	}

	@Override
	public Object run(Object... params) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

}
