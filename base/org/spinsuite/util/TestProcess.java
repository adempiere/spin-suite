package org.spinsuite.util;

import org.spinsuite.interfaces.BackGroundProcess;
import org.spinsuite.login.T_Connection;
import org.spinsuite.login.T_Login_Init;


import android.content.Context;

public class TestProcess implements BackGroundProcess{
	T_Connection m_ctx ;
	int count  = 0;
	BackGroundTask task ;
	public TestProcess(T_Connection ctx) {
		// TODO Auto-generated constructor stub
		m_ctx = ctx;
		task = new BackGroundTask(this, m_ctx);
		task.runTask();
	}
	
	@Override
	public void publishBeforeInit() {
		// TODO Auto-generated method stub
		System.out.println("pase1");
		m_ctx.et_UrlSoap.setText("Pase 1.");
	}

	@Override
	public void publishOnRunning() {
		// TODO Auto-generated method stub
		System.out.println("pase2");
		m_ctx.et_UrlSoap.setText("Running " + count);
	}

	@Override
	public void publishAfterEnd() {
		// TODO Auto-generated method stub
		System.out.println("pase3");
		m_ctx.et_UrlSoap.setText("Pase 3.");
	}

	
	@Override
	public Object run() {
		// TODO Auto-generated method stub
		try {
			for (int i=0 ;i< 20;i++){
				Thread.sleep(1000);
				
				count = i;
				task.refreshGUINow();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	

}
