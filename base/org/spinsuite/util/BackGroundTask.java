package org.spinsuite.util;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;

import org.spinsuite.base.R;
import org.spinsuite.interfaces.BackGroundProcess;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class BackGroundTask {
	
	BackGroundProcess m_Process;
	Context m_ctx ;
	
	public BackGroundTask(BackGroundProcess process,Context ctx) {
		// TODO Auto-generated constructor stub
		m_Process = process; 
		m_ctx = ctx;
	}
	
	
	public Object runTask(){
		BackGroundCall call = new BackGroundCall(m_Process, hdl, null);
		FutureTask<Object> task = new FutureTask<Object>(call);
		ExecutorService executor = Executors.newScheduledThreadPool(1);		
		executor.execute(task);	
		return null;
	}
	
	Handler hdl = new Handler(){
		@SuppressWarnings("static-access")
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			
			m_Process = (BackGroundProcess) msg.obj;
			
			switch(m_Process.processState)
			{
			case 1:
				m_Process.publishBeforeInit();
			case 2:
				m_Process.publishOnRunning();
			case 3:
				m_Process.publishAfterEnd();
			default:
				LogM.log(m_ctx,this.getClass(), Level.INFO, m_ctx.getString(R.string.msg_InvalidProcessState));
			};
			
			
		}
	};
	
	
}
