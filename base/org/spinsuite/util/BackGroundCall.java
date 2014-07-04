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
package org.spinsuite.util;

import java.util.concurrent.Callable;

import org.spinsuite.interfaces.BackGroundProcess;

import android.os.Handler;
import android.os.Message;


/**
 *	Background Call.
 *	Call Background process
 *  @contributor <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 
 */
public class BackGroundCall implements Callable<Object>{

	private BackGroundProcess m_Process;
	private Handler m_Hdl;
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 3, 2014, 2:39:04 PM
	 * @param process
	 * @param hdl
	 */
	public BackGroundCall(BackGroundProcess process,Handler hdl) {
		// TODO Auto-generated constructor stub
		m_Process = process;
		m_Hdl = hdl;
	}
	
	/**
	 * Call to Process
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 3, 2014, 2:39:04 PM
	 */
	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		Object retValue;
		
		//Set Message Before run Process
		Message msgBefore = new Message();
		msgBefore.obj = m_Process;
		msgBefore.arg1 = 1;
		
		//Send Process Object Before Run 
		m_Hdl.sendMessage(msgBefore);
		
		retValue = m_Process.run();
		 
		Message msgAfter = new Message();
		msgAfter.obj = m_Process;
		msgAfter.arg1=3;
		
		//Send Process Object After Run
		m_Hdl.sendMessage(msgAfter);
		
		return retValue;
	}
}
