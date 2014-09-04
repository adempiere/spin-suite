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
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Carlos Parada www.erpcya.com             				 		 *
 *************************************************************************************/
package org.spinsuite.initialload;

import java.util.logging.Level;

import org.ksoap2.serialization.SoapObject;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.BackGroundProcess;
import org.spinsuite.login.T_Connection;
import org.spinsuite.login.T_Login_ProgressSync;
import org.spinsuite.util.BackGroundTask;
import org.spinsuite.util.LogM;
import org.spinsuite.util.StringNamePair;

import android.database.Cursor;

public class InitialLoadTask implements BackGroundProcess{

	/** Public Msg*/
	private String m_PublicMsg = "";

	/** Error Indicator*/
	private boolean m_Error = false;
	
	/** Progress Indicator*/
	private int m_Progress = -1 ;
	
	/** Background Task */ 
	private BackGroundTask m_Task = null;
	
	/** Connection Windows*/
	private T_Connection m_Conn = null;
	
	/** Dialog Message Process*/ 
	private T_Login_ProgressSync df = null;
	
	/** Url */
	private String m_URL = ""; 
	
	/** Name Space */ 
	private String m_NameSpace = "";
	
	/** Method */
	private String m_Method = "";
	
	/** Is .Net Service*/
	private boolean m_IsNetService = false;
	
	/** User*/
	private String m_User =  "";
	
	/** Password*/
	private String m_PassWord = "" ;
	
	/** Soap Action */
	private String m_SoapAction = "";
	
	
	private int m_Timeout = 0;
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/09/2014, 00:43:17
	 * @param p_URL
	 * @param p_NameSpace
	 * @param p_Method
	 * @param p_IsNetService
	 * @param p_User
	 * @param p_PassWord
	 * @param p_SoapAction
	 * @param p_Conn
	 * @param p_Timeout
	 */
	public InitialLoadTask(String p_URL, String p_NameSpace, String p_Method,boolean p_IsNetService, String p_User,String p_PassWord,String p_SoapAction,T_Connection p_Conn,String p_Timeout) {
		// TODO Auto-generated constructor stub
		
		m_URL = p_URL;
		m_NameSpace = p_NameSpace;
		m_Method = p_Method;
		m_IsNetService = p_IsNetService;
		m_User = p_User; 
		m_SoapAction = p_SoapAction;
		m_PassWord = p_PassWord;
		m_Conn =p_Conn;
		m_Timeout = Integer.getInteger(p_Timeout, 0);
		
	}
	
	@Override
	public void publishBeforeInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publishOnRunning() {
		// TODO Auto-generated method stub
		df.setMsg(m_PublicMsg,m_Error);
		df.setProgress(m_Progress, m_Error);
	}

	@Override
	public void publishAfterEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object run() {
		// TODO Auto-generated method stub
 
		m_PublicMsg = "Calling";
		m_Task.refreshGUINow();
		
		//Call List of Web Services
		CallListWebServices();
		
		return null;
	}
	
	/**
	 * Run Task And Show Dialog Fragment
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 27/08/2014, 15:56:49
	 * @return void
	 */
	public void runTask(){

		df = new T_Login_ProgressSync();
    	df.show(m_Conn.getFragmentManager(), m_Conn.getResources().getString(R.string.InitSync));
		m_Task = new BackGroundTask(this, m_Conn);
		m_Task.runTask();
	}
	
	/**
	 * Set Public Message
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/09/2014, 23:47:35
	 * @param m_PublicMsg
	 * @return void
	 */
	public void setM_PublicMsg(String m_PublicMsg) {
		this.m_PublicMsg = m_PublicMsg;
	}
	
	/**
	 * Set Progress
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/09/2014, 23:46:46
	 * @param m_Progress
	 * @return void
	 */
	public void setM_Progress(int m_Progress) {
		this.m_Progress = m_Progress;
	}
	
	/**
	 * Set Is Error
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/09/2014, 23:46:03
	 * @param m_Error
	 * @return void
	 */
	public void setM_Error(boolean m_Error) {
		this.m_Error = m_Error;
	}

	/**
	 * Refresh GUI On Task
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/09/2014, 23:43:23
	 * @return void
	 */
	public void refreshGUINow(){
		m_Task.refreshGUINow();
	}
	
	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/09/2014, 23:42:48
	 * @param p_Max
	 * @return void
	 */
	public void setMaxValueProgressBar(int p_Max){
		df.setMaxValueProgressBar(p_Max);
	}
	
	/**
	 * 
	 * @author <a href="mailto:carlosapardam@gmail.com">Carlos Parada</a> 03/09/2014, 23:51:06
	 * @return void
	 */
	private void CallListWebServices(){
		
		//Call Web Service Method Create Metadata
		if (!CallWebService(new StringNamePair(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition),
				new StringNamePair(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodCreateMetaData)))
			return;
		
		//Call Web Service Method Web Sevice Definition
		if (!CallWebService(new StringNamePair(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition),
				new StringNamePair(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodWebServiceDefinition)))
			return;
		
		DB conn = new DB(m_Conn);
		conn.openDB(DB.READ_ONLY);
		Cursor rs = null;
		try{
			//Call Web Service Method Data Synchronization
			String sql = new String("SELECT wst.Value " +
									"FROM SPS_SyncMenu sm " +
									"INNER JOIN WS_WebServiceType wst ON sm.WS_WebServiceType_ID = wm.WS_WebServiceType_ID " +
									"INNER JOIN WS_WebServiceMethod wsm ON wsm.WS_WebServiceMethod_ID = wst.WS_WebServiceMethod_ID " +
									"INNER JOIN WS_WebService ws ON ws.WS_WebService_ID = wst.WS_WebService_ID " +
									"WHERE ws.Value = ? AND wst.Value = ? ");
			
			rs = conn.querySQL(sql, new String[]{InitialLoad.INITIALLOAD_ServiceDefinition,InitialLoad.INITIALLOAD_ServiceMethodDataSynchronization});    	
	    	if(rs.moveToFirst()){
	    		do{
		    		//Call Web Service Method Web Sevice Definition
		    		CallWebService(new StringNamePair(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition),
		    							new StringNamePair(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodDataSynchronization),
		    							new StringNamePair(ILCall.m_ServiceTypeField, rs.getString(0)));
	    		} while(rs.moveToNext());
	    	};
		}catch (Exception e){
			LogM.log(m_Conn, InitialLoadTask.class, Level.SEVERE, e.getLocalizedMessage(), e.getCause());
			m_PublicMsg = e.getLocalizedMessage();
			m_Error = true;
			m_Task.refreshGUINow();
		}
		finally{
			conn.closeDB(rs);
	    	DB.closeConnection(conn);	
		}
	    
	}
	
	
	
	/**
	 * Call to Web Service
	 * @author <a href="mailto:carlosapardam@gmail.com">Carlos Parada</a> 03/09/2014, 23:42:06
	 * @param Params
	 * @return void
	 */
	private boolean CallWebService(StringNamePair... Params){
		InitialLoad il = new InitialLoad(m_URL, m_NameSpace, m_Method, m_IsNetService, m_SoapAction, m_User, m_PassWord, this, m_Timeout, m_Conn);
		
		for (int i=0;i<Params.length;i++)
			il.addPropertyToCall(Params[i].getKey(), Params[i].getName());
		
		//Call Service
		SoapObject so = il.callService();
		
		//Write On Database
		if (so!= null)
			if (!il.writeDB(so))
				return false;
		else
			return false;
		
		return true;
		
	}
		
}
