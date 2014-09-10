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

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.content.Context;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public abstract class StdProcess {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/03/2014, 15:36:10
	 */
	public StdProcess() {
	
	}
	
	/**	Process Info			*/
	private ProcessInfo	m_pi 	= null;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 *  <code>
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter para : params){
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("A_Asset_Group_ID"))
				p_A_Asset_Group_ID = para.getParameterAsInt();
			else if (name.equals("GuaranteeDate"))
				p_GuaranteeDate = (Date)para.getParameter();
			else if (name.equals("AttachAsset"))
				p_AttachAsset = "Y".equals(para.getParameter());
			else
				LogM.log(getCtx(), pi.getClassName(), Level.SEVERE, "Unknown Parameter: " + name);
		}
	 *  </code>
	 */
	abstract protected void prepare();

	/**
	 *  Perform process.
	 *  @return Message (variables are parsed)
	 *  @throws Exception if not successful e.g.
	 */
	abstract protected String doIt() throws Exception;
	
	/**
	 * Process
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/03/2014, 17:04:35
	 * @return
	 * @return boolean
	 */
	private boolean process(){
		String msg = null;
		boolean success = true;
		try{
			prepare();
			msg = doIt();
		} catch (Throwable e){
			msg = e.getLocalizedMessage();
			if (msg == null)
				msg = e.toString();
			if (e.getCause() != null)
				LogM.log(m_pi.getCtx(), getClass(), Level.SEVERE, msg, e.getCause());
			else 
				LogM.log(m_pi.getCtx(), getClass(), Level.SEVERE, msg, e);
			success = false;
		}
		//	Get Error
		if ("@Error@".equals(msg))
			success = false;
		
		//	Parse Variables
		//msg = Env.parseTranslation(m_ctx, msg);
		m_pi.setSummary (Msg.parseTranslation(getCtx(), msg), !success);
		//	
		return success;
	}   //  process
	
	/**
	 * Start Process
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/03/2014, 22:27:54
	 * @param pi
	 * @return
	 * @return boolean
	 */
	public final boolean startProcess (ProcessInfo pi){
		//  Preparation
		m_pi = pi;
		DB conn = m_pi.getConnection();
		//	
		if(conn == null
				|| m_pi.isHandleConnection()){
			conn = new DB(m_pi.getCtx());
			m_pi.setIsHandleConnection(true);
		}
		//	process
		boolean success = process();
		//	Close Connection
		if(m_pi.isHandleConnection())
			DB.closeConnection(conn);
		return success;
	}   //  startProcess	
	
	/**************************************************************************
	 *  Add Log Entry
	 *  @param date date or null
	 *  @param id record id or 0
	 *  @param number number or null
	 *  @param msg message or null
	 */
	public void addLog (int id, Date date, BigDecimal number, String msg){
		if (m_pi != null)
			m_pi.addLog(id, date, number, Msg.parseTranslation(getCtx(), msg));
		LogM.log(m_pi.getCtx(), this.getClass(), Level.INFO, id + " - " + date + " - " + number + " - " + msg);
	}	//	addLog

	/**
	 * 	Add Log
	 *	@param msg message
	 */
	public void addLog (String msg){
		if (msg != null)
			addLog (0, null, null, Msg.parseTranslation(getCtx(), msg));
	}	//	addLog
	
	/**
	 * Get Context
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/03/2014, 15:38:32
	 * @return
	 * @return Context
	 */
	public Context getCtx(){
		return m_pi.getCtx();
	}
	
	/**
	 * Get Current Connection
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/03/2014, 15:39:02
	 * @return
	 * @return DB
	 */
	public DB getConnection(){
		return m_pi.getConnection();
	}
	
	/**
	 * Get Window No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/03/2014, 15:39:35
	 * @return
	 * @return int
	 */
	public int getActivityNo(){
		return m_pi.getActivityNo();
	}
	
	/**
	 * Get Table ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/04/2014, 22:41:28
	 * @return
	 * @return int
	 */
	public int getTable_ID(){
		return m_pi.getTable_ID();
	}
	
	/**
	 * Get Record ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/04/2014, 22:41:52
	 * @return
	 * @return int
	 */
	public int getRecord_ID(){
		return m_pi.getRecord_ID();
	}
	
	/**
	 * Get Parameters
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/03/2014, 17:12:43
	 * @return
	 * @return ProcessInfoParameter[]
	 */
	public ProcessInfoParameter[] getParameter(){
		return m_pi.getParameter();
	}
}