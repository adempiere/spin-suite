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
package org.spinsuite.sync;

import java.util.List;

import org.ksoap2.serialization.SoapObject;
import org.spinsuite.interfaces.BackGroundProcess;
import org.spinsuite.model.MSPSSyncMenu;
import org.spinsuite.model.MWSWebServiceType;
import org.spinsuite.util.BackGroundTask;
import org.spinsuite.util.Env;
import org.spinsuite.util.StringNamePair;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

public class InitialLoadTask extends Service implements BackGroundProcess{


	/** Public Msg*/
	private String m_PublicMsg = "";

	/** Error Indicator*/
	private boolean m_Error = false;
	
	/** Progress Indicator*/
	private int m_Progress = -1 ;
	
	/** Background Task */ 
	private BackGroundTask m_Task = null;
	
	/** Connection Windows*/
	//private T_Connection m_Conn = null;
	
	/** Dialog Message Process*/ 
	//private T_Login_ProgressSync df = null;
	
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
	
	/** Timeout */
	private int m_Timeout = 0;
	
	/** Notification Manager*/
	private NotificationManager m_NFManager = null;
	
	/** Max Value Progress Bar*/
	private int m_MaxPB = 0;
	
	/** Builder*/
	private Builder m_Builder = null;
	
	/** Pending Intent T_Connection Fragment Activity*/ 
	private PendingIntent m_PendingIntent = null; 
	private static final String 	KEY_POS_TAB			= "posTab";
	
	
	
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
	/*public InitialLoadTask(String p_URL, String p_NameSpace, String p_Method,boolean p_IsNetService, String p_User,String p_PassWord,String p_SoapAction,T_Connection p_Conn,String p_Timeout) {
		// TODO Auto-generated constructor stub
		
		m_URL = p_URL;
		m_NameSpace = p_NameSpace;
		m_Method = p_Method;
		m_IsNetService = p_IsNetService;
		m_User = p_User; 
		m_SoapAction = p_SoapAction;
		m_PassWord = p_PassWord;
		m_Conn =p_Conn;
		m_Timeout = Integer.parseInt(p_Timeout);
		
		//Set Notification Panel
		m_NFManager = (NotificationManager) m_Conn.getSystemService(Context.NOTIFICATION_SERVICE);
		m_Builder = new NotificationCompat.Builder(m_Conn);
		
		//
		Intent intent = new Intent(m_Conn, T_Connection.class); 
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(m_Conn);
		stackBuilder.addParentStack(T_Connection.class);
		// Adds the Intent to the top of the stack
		stackBuilder.addNextIntent(intent);
		m_PendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		m_Builder.setContentIntent(m_PendingIntent);
		
	}*/
	
	@Override
	public void publishBeforeInit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void publishOnRunning() {
		// TODO Auto-generated method stub
		//df.setMsg(m_PublicMsg,m_Error);
		//df.setProgress(m_Progress, m_Error);
		m_Builder.setContentTitle("Titulo")
								.setContentText(m_PublicMsg)
								.setProgress(m_MaxPB, m_Progress, m_Progress == -1)
								.setSmallIcon(android.R.drawable.stat_sys_download)
								;
		m_NFManager.notify(0, m_Builder.build());
	}

	@Override
	public void publishAfterEnd() {
		// TODO Auto-generated method stub
	}

	@Override
	public Object run() {
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
		m_Task = new BackGroundTask(this, this);
		//df = new T_Login_ProgressSync(m_Task,m_Conn);
    	//df.show(m_Conn.getFragmentManager(), m_Conn.getResources().getString(R.string.InitSync));
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
		//df.setMaxValueProgressBar(p_Max);
		m_MaxPB = p_Max;
	}
	
	/**
	 * 
	 * @author <a href="mailto:carlosapardam@gmail.com">Carlos Parada</a> 03/09/2014, 23:51:06
	 * @return void
	 */
	private void CallListWebServices(){
		
		//Call Web Service Method Create Metadata
		refreshMSG("Calling " + "Create Metadata",false,-1);
		if (!CallWebService(new StringNamePair(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition),
				new StringNamePair(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodCreateMetaData),
				new StringNamePair(ILCall.m_WSNumber, "0")))
			return;
		
		//Call Web Service Method Web Sevice Definition
		
		refreshMSG("Calling " + "Web Sevice Definition",false,-1);
		if (!CallWebService(new StringNamePair(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition),
				new StringNamePair(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodWebServiceDefinition),
				new StringNamePair(ILCall.m_Page, "0"),
				new StringNamePair(ILCall.m_WSNumber, "0")
				))
			return;
		
		List<MSPSSyncMenu> sm = MSPSSyncMenu.getNodes(this, "0", InitialLoad.INITIALLOAD_ServiceDefinition, InitialLoad.INITIALLOAD_ServiceMethodDataSynchronization, null, null);
		
		try{
			for (MSPSSyncMenu mspsSyncMenu : sm) {
				
				MWSWebServiceType wst = new MWSWebServiceType(this, mspsSyncMenu.getWS_WebServiceType_ID(), null);
				refreshMSG("Calling " +  mspsSyncMenu.getName() ,false,-1);
				//Call Web Service Method Web Sevice Definition
	    		if (!CallWebService(new StringNamePair(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition),
	    							new StringNamePair(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodDataSynchronization),
	    							new StringNamePair(ILCall.m_ServiceTypeField, wst.getValue()),
	    							new StringNamePair(ILCall.m_Page, "0"),
	    							new StringNamePair(ILCall.m_WSNumber, "0")))
	    			break;		
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		setContext();
		//df.dismiss();
	}
	
	/**
	 * Call to Web Service
	 * @author <a href="mailto:carlosapardam@gmail.com">Carlos Parada</a> 03/09/2014, 23:42:06
	 * @param Params
	 * @return void
	 */
	private boolean CallWebService(StringNamePair... Params){
		StringNamePair[] p_Params = new StringNamePair[Params.length];
		int CurrentPage = 0;
		int CurrentWS = 0;
		int pages = 0;
		int CountWS = 0;
		int iWS = -1;
		int iPage =-1;
		InitialLoad il = new InitialLoad(m_URL, m_NameSpace, m_Method, m_IsNetService, m_SoapAction, m_User, m_PassWord, this, m_Timeout, this);
		
		for (int i=0;i<Params.length;i++){
			il.addPropertyToCall(Params[i].getKey(), Params[i].getName());
			
			if (Params[i].getKey().equals(ILCall.m_Page)){
				CurrentPage = Integer.parseInt(Params[i].getName());
				CurrentPage++;
				iPage = i ;
			}
			else if (Params[i].getKey().equals(ILCall.m_WSNumber)){
				CurrentWS = Integer.parseInt(Params[i].getName());
				CurrentWS++;
				iWS = i;
			}
			else
				p_Params[i] = Params[i];
		}
		
		//Call Service
		SoapObject so = il.callService();
		
		if (so!= null){
			if (so.hasProperty(ILCall.m_Pages))
				pages = Integer.parseInt(so.getPropertyAsString(ILCall.m_Pages));
			if (so.hasProperty(ILCall.m_WSCount))
				CountWS = Integer.parseInt(so.getPropertyAsString(ILCall.m_WSCount));
			
			if (!il.writeDB(so,pages,CurrentPage))
				return false;
			
			so = null;
			il = null;
			if (CurrentWS - CountWS != 0){
				if (CurrentPage - pages != 0 && pages > 0){
					
					if (iWS != -1)
						p_Params[iWS] = new StringNamePair(Params[iWS].getKey(), Integer.valueOf(CurrentWS -1).toString());
					
					p_Params[iPage] = new StringNamePair(Params[iPage].getKey(), Integer.valueOf(CurrentPage).toString());
					
					refreshMSG(m_PublicMsg, false, -1);
					return CallWebService(p_Params);
				}
				else{
					if (iPage != -1)
						p_Params[iPage] = new StringNamePair(Params[iPage].getKey(), Integer.valueOf(0).toString());

					p_Params[iWS] = new StringNamePair(Params[iWS].getKey(), Integer.valueOf(CurrentWS).toString());
					
					refreshMSG(m_PublicMsg, false, -1);
					return CallWebService(p_Params);
				}
			}
			else
			{
				if (CurrentPage - pages != 0 && pages > 0){
					p_Params[iWS] = new StringNamePair(Params[iWS].getKey(), Integer.valueOf(0).toString());
					p_Params[iPage] = new StringNamePair(Params[iPage].getKey(), Integer.valueOf(CurrentPage).toString());
					refreshMSG(m_PublicMsg, false, -1);
					return CallWebService(p_Params);
				}
			}
		}
		else
			return false;
		
		return true;
		
	}
	/**
	 * 
	 * @author <a href="mailto:carlosapardam@gmail.com">Carlos Parada</a> 09/09/2014, 21:46:44
	 * @return
	 * @return int
	 */
	public int getM_Progress() {
		return m_Progress;
	}
	
	/**
	 * Refresh Message
	 * @author <a href="mailto:carlosapardam@gmail.com">Carlos Parada</a> 18/10/2014, 13:25:58
	 * @param p_MSG
	 * @param p_Error
	 * @param p_Progress
	 * @return void
	 */
	public void refreshMSG(String p_MSG, boolean p_Error, Integer p_Progress){
		m_PublicMsg = p_MSG;
		m_Error = p_Error;
		if (p_Progress != null)
			m_Progress = p_Progress;
		
		m_Task.refreshGUINow();
	}
	
	/**
	 * Set Context
	 * @author Yamel Senih 30/11/2012, 11:55:26
	 * @return void
	 */
	public void setContext() {
		Env.setIsEnvLoad(this, true);
		Env.setContext(this, "#SUser", m_User);
		Env.setContext(this, "#SPass", m_PassWord);
		Env.setSavePass(this, true);
		Env.setAutoLogin(this, true);
		Env.setContext(this, KEY_POS_TAB, 1);
		Env.setContext(this, "#Timeout", m_Timeout);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//intent.get
		
		//m_Conn = (T_Connection)getBaseContext();
		m_URL = intent.getCharSequenceExtra("URL").toString();
		m_NameSpace = intent.getCharSequenceExtra("NameSpace").toString();
		m_Method = intent.getCharSequenceExtra("Method").toString();
		m_IsNetService = intent.getBooleanExtra("IsNetService",false);
		m_User = intent.getCharSequenceExtra("User").toString(); 
		m_SoapAction = intent.getCharSequenceExtra("SoapAction").toString();
		m_PassWord = intent.getCharSequenceExtra("PassWord").toString();
		//m_Conn =p_Conn;
		m_Timeout = intent.getIntExtra("Timeout",0);
		
		//Set Notification Panel
		m_NFManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		m_Builder = new NotificationCompat.Builder(this);
		this.runTask();
		//
		//Intent intent = new Intent(m_Conn, T_Connection.class); 
		//TaskStackBuilder stackBuilder = TaskStackBuilder.create(m_Conn);
		//stackBuilder.addParentStack(T_Connection.class);
		// Adds the Intent to the top of the stack
		//stackBuilder.addNextIntent(intent);
		//m_PendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		//m_Builder.setContentIntent(m_PendingIntent);
		
		return super.onStartCommand(intent, flags, startId);
	}

}
