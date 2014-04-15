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
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.process;

import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.process.ClientProcess;
import org.spinsuite.process.ProcessInfo;
import org.spinsuite.process.StdProcess;
import org.spinsuite.util.LogM;
import org.spinsuite.view.report.ReportPrintData;

import android.content.Context;


/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class ProcessCtl {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/03/2014, 21:35:17
	 * @param m_pi
	 */
	public ProcessCtl(ProcessInfo m_pi){
		this.m_pi = m_pi;
	}
	
	/**	Process Info			*/
	private ProcessInfo		m_pi 	= null;
	/**	Report Print Data		*/
	private ReportPrintData m_rpd	= null;
	
	/**	Script Prefix			*/
	public static final String SCRIPT_PREFIX = "@script:";
	
	/**
	 * Run Process from Meta-data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/03/2014, 16:15:44
	 * @return void
	 */
	public void runProcess(){
		//	Start Process if exists
		if(m_pi.getClassName() != null
				&& m_pi.getClassName().length() > 0){
			//	Run Class
			if (!startProcess())
				return;
		}
		//	Report
		if (m_pi.isReport()){
			if(m_pi.getAD_ReportView_ID() != 0
					|| m_pi.getAD_PrintFormat_ID() != 0){
				//	Instance Print Data
				m_rpd = new ReportPrintData(m_pi.getCtx(), m_pi, 0, m_pi.getConnection());
				//	Load Data
				m_rpd.loadData();
			}
			//	
			return;
		}
	}
	
	/**
	 * Get Print Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 17:25:33
	 * @return
	 * @return ReportPrintData
	 */
	public ReportPrintData getReportPrintData(){
		return getReportPrintData(0);
	}
	
	/**
	 * Get Print Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/03/2014, 12:14:16
	 * @param m_AD_PrintFormat_ID
	 * @return
	 * @return ReportPrintData
	 */
	public ReportPrintData getReportPrintData(int m_AD_PrintFormat_ID){
		//	Reload
		if(m_AD_PrintFormat_ID != 0)
			m_rpd.loadData(m_AD_PrintFormat_ID);
		//	Return
		return m_rpd;
	}
	
	/**
	 * Set Print Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/03/2014, 12:13:02
	 * @param m_rpd
	 * @return void
	 */
	public void setReportPrintData(ReportPrintData m_rpd){
		this.m_rpd = m_rpd;
	}
	/**
	 * Start Java Process Class
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/03/2014, 14:51:51
	 * @return
	 * @return boolean
	 */
	private boolean startProcess (){
		LogM.log(m_pi.getCtx(), this.getClass(), Level.FINE, m_pi.toString());
		boolean started = false;
		boolean clientOnly = false;
		boolean isScriptProcess = m_pi.getClassName().toLowerCase().startsWith(SCRIPT_PREFIX);
		if (!isScriptProcess) {
			try {
				Class<?> processClass = Class.forName(m_pi.getClassName());
				if (ClientProcess.class.isAssignableFrom(processClass))
					clientOnly = true;
			} catch (Exception e) {}
		}
		//	Run Server Process
		if (m_pi.isServerProcess() 
				&& !clientOnly){
			//	Not yet implemented
			//	Handle Web-Services
		}
		//	Run locally
		if (!started 
				&& (!m_pi.isServerProcess() 
						|| clientOnly)){
			if (isScriptProcess) {
				;//	Not yet implemented
			} else {
				return startJavaProcess(m_pi.getCtx(), m_pi, m_pi.getConnection());
			}
		}
		return !m_pi.isError();
	}   //  startProcess
	
	/**
	 * Start Java Process
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/03/2014, 16:13:51
	 * @param ctx
	 * @param pi
	 * @param conn
	 * @return
	 * @return boolean
	 */
	private boolean startJavaProcess(Context ctx, ProcessInfo pi, DB conn) {
		String className = pi.getClassName();
		//	Not Class
		if(className == null)
			return true;
		//Get Class
		Class<?> processClass = loadJavaClass(className);
		//	
		if(processClass == null){
			//	Error
			String error = ctx.getResources().getString(R.string.msg_Error) + 
					": " + ctx.getResources().getString(R.string.msg_ClassNotFound);
			LogM.log(ctx, getClass(), Level.SEVERE, error);
			//	Set Summary
			pi.setSummary (error, true);
			return false;
		}
		//Get Process
		StdProcess process = null;
		try{
			process = (StdProcess)processClass.newInstance();
		}catch (Exception e){
			String error = ctx.getResources().getString(R.string.msg_Error) + 
					": " + ctx.getResources().getString(R.string.msg_ClassNotFound);
			LogM.log(ctx, getClass(), Level.WARNING, error, e);
			//	Set Summary
			pi.setSummary (error, true);
			return false;
		}
		//	Success
		boolean success = false;
		//	Start
		try{
			success = process.startProcess(pi);
		}catch (Exception e){
			pi.setSummary (ctx.getResources().getString(R.string.msg_ProcessError) 
					+ " " + e.getLocalizedMessage(), true);
			LogM.log(ctx, pi.getClassName(), Level.SEVERE, 
					ctx.getResources().getString(R.string.msg_Error), e);
			return false;
		}
		//	Return
		return success;
	}
		
	/**
	 * Load Class
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/03/2014, 15:30:32
	 * @param className
	 * @return
	 * @return Class<?>
	 */
	private Class<?> loadJavaClass(String className){
		try{
			Class <?> clazz = Class.forName(className);
			// Validate if the class is for specified tableName
			Class<?> superClazz = clazz.getSuperclass();
			while (superClazz != null){
				if (superClazz == StdProcess.class){
					return clazz;
				}
				superClazz = superClazz.getSuperclass();
			}
		} catch (Exception e){
			LogM.log(m_pi.getCtx(), this.getClass(), Level.SEVERE, "Error in Load Class", e);
		}
		return null;
	}
	
}
