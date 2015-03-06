package org.spinsuite.util;
/*************************************************************************************
 * Product: Spin-Suite (Making your Business Spin)                                   *
 * This program is free software; you can redistribute it and/or modify it           *
 * under the terms version 2 of the GNU General Public License as published          *
 * by the Free Software Foundation. This program is distributed in the hope          *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied        *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                  *
 * See the GNU General Public License for more details.                              *
 * You should have received a copy of the GNU General Public License along           *
 * with this program; if not, write to the Free Software Foundation, Inc.,           *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                            *
 * For the text or an alternative of this public license, you may reach us           *
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Carlos Parada www.erpconsultoresyasociados.com                    *
 *************************************************************************************/
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;

import org.spinsuite.base.R;
import org.spinsuite.interfaces.BackGroundProcess;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Background Task
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 *
 */
public class BackGroundTask {
	
	BackGroundProcess m_Process;
	Context m_ctx ;
	BackGroundCall call;
	FutureTask<Object> task ;
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 3, 2014, 2:48:37 PM
	 * @param process
	 * @param ctx
	 */
	public BackGroundTask(BackGroundProcess process, Context ctx) {
		// TODO Auto-generated constructor stub
		m_Process = process; 
		m_ctx = ctx;
	}
	
	
	/**
	 * Run Task in Background
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 3, 2014, 2:48:53 PM
	 * @return void
	 */
	public void runTask(){
		call = new BackGroundCall(m_Process, hdl);
		task = new FutureTask<Object>(call);
		ExecutorService executor = Executors.newScheduledThreadPool(1);		
		executor.execute(task);	
	}
	
	/**
	 * Send Process Refresh GUI to Handler
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 3, 2014, 2:51:12 PM
	 * @return void
	 */
	public void refreshGUINow(){
		Message msg = new Message();
		msg.obj = m_Process;
		msg.arg1 = 2; 
		hdl.sendMessage(msg);
	};
	
	/**
	 * GUI Handler 
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 3, 2014, 2:51:12 PM
	 */
	Handler hdl = new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			
			m_Process = (BackGroundProcess) msg.obj;
			
			switch(msg.arg1)
			{
			case 1:
				m_Process.publishBeforeInit();
				break;
			case 2:
				m_Process.publishOnRunning();
				break;
			case 3:
				m_Process.publishAfterEnd();
				break;
			default:
				LogM.log(m_ctx,this.getClass(), Level.INFO, m_ctx.getString(R.string.msg_InvalidProcessState));
				break;
			};
		}
	};
	
	/**
	 * Cancel Task And Interruptep Process 
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 11/11/2014, 22:57:12
	 * @param mayInterruptIfRunning
	 * @return void
	 */
	public void cancelTask(boolean mayInterruptIfRunning){
		if (task != null)
			task.cancel(mayInterruptIfRunning);
	}
	
}
