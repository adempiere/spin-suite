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
 * Copyright (C) 2012-2015 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Carlos Parada www.erpcya.com             				 		 *
 *************************************************************************************/
package org.spinsuite.sync;

import java.util.List;
import java.util.logging.Level;

import org.ksoap2.serialization.SoapObject;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.BackGroundProcess;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.model.MSPSSyncMenu;
import org.spinsuite.model.MWSWebServiceType;
import org.spinsuite.util.BackGroundTask;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;
import org.spinsuite.util.StringNamePair;
import org.spinsuite.util.SyncValues;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

/**
 * 
 * @author Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com Mar 7, 2015, 3:53:08 AM
 *
 */
public class InitialLoadTask implements BackGroundProcess {


	/** Public Msg				*/
	private String 				m_PublicMsg = "";
	/** Error Indicator			*/
	private boolean 			m_Error = false;
	/** Progress Indicator		*/
	private int 				m_Progress = -1 ;
	/** Background Task 		*/ 
	private BackGroundTask 		m_Task = null;
	/** Connection Windows		*/
	private Activity 			m_Callback = null;
	/** Dialog Message Process	*/ 
	//private T_Login_ProgressSync df = null;
	/** Url 					*/
	private String 				m_URL = ""; 
	/** Name Space 				*/ 
	private String 				m_NameSpace = "";
	/** Method 					*/
	private String 				m_Method = "";
	/** Is .Net Service			*/
	private boolean 			m_IsNetService = false;
	/** User					*/
	private String 				m_User =  "";
	/** Password				*/
	private String 				m_PassWord = "" ;	
	/** Soap Action 			*/
	private String 				m_SoapAction = "";
	/** Timeout 				*/
	private int 				m_Timeout = 0;
	/** Notification Manager	*/
	private NotificationManager m_NFManager = null;
	/** Max Value Progress Bar	*/
	private int 				m_MaxPB = 0;
	/** Builder					*/
	private Builder 			m_Builder = null;
	/** Pending Intent Fragment */ 
	private PendingIntent 		m_PendingIntent = null; 
	/**	Title					*/
	private String				m_Title = null;
	/**	Last Message			*/
	private String 				m_LastMsg = null;
	private static final String KEY_POS_TAB	= "posTab";
	/**	Notification ID			*/
	private static final int	NOTIFICATION_ID = 1;
	
	
	
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
	 * @param p_Callback
	 * @param p_Timeout
	 */
	public InitialLoadTask(String p_URL, String p_NameSpace, String p_Method, boolean p_IsNetService, 
			String p_User, String p_PassWord, String p_SoapAction, Activity p_Callback, int p_Timeout) {
		m_URL = p_URL;
		m_NameSpace = p_NameSpace;
		m_Method = p_Method;
		m_IsNetService = p_IsNetService;
		m_User = p_User; 
		m_SoapAction = p_SoapAction;
		m_PassWord = p_PassWord;
		m_Callback = p_Callback;
		m_Timeout = p_Timeout;
		m_Title = m_Callback.getString(R.string.Sync_Synchronzing);
		//Set Notification Panel
		m_NFManager = (NotificationManager) m_Callback.getSystemService(Context.NOTIFICATION_SERVICE);
		m_Builder = new NotificationCompat.Builder(m_Callback);
		//	
		setPendingIntent();
		
		//
		
		//TaskStackBuilder stackBuilder = TaskStackBuilder.create(m_Conn);
		//stackBuilder.addParentStack(T_Connection.class);
		// Adds the Intent to the top of the stack
		//stackBuilder.addNextIntent(intent);
		//m_PendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		//m_Builder.setContentIntent(m_PendingIntent);
		
	}
	
	/**
	 * Set Pending Item
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void setPendingIntent() {
		ActivityManager m_ActivityManager = (ActivityManager) m_Callback.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = m_ActivityManager.getRunningTasks(1);
		ActivityManager.RunningTaskInfo task = tasks.get(0);
		ComponentName mainActivity = task.baseActivity;
		Intent intent = new Intent();
		intent.setComponent(mainActivity);
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		//	Set Main Activity
		m_PendingIntent = PendingIntent.getActivity(m_Callback, 0, intent, 0);
	}
	
	@Override
	public void publishBeforeInit() {
		Msg.toastMsg(m_Callback, m_Callback.getString(R.string.Sync_StartingDownload));
	}

	@Override
	public void publishOnRunning() {
		//df.setMsg(m_PublicMsg,m_Error);
		//df.setProgress(m_Progress, m_Error);
		m_Builder.setContentIntent(m_PendingIntent);
		m_Builder.setContentTitle(m_Title)
								.setContentText(m_PublicMsg)
								.setProgress(m_MaxPB, m_Progress, m_Progress == -1)
								.setSmallIcon(android.R.drawable.stat_sys_download)
								;
		//m_Builder.setStyle(inboxStyle);
		m_NFManager.notify(NOTIFICATION_ID, m_Builder.build());
	}

	@Override
	public void publishAfterEnd() {
		Msg.alertMsg(m_Callback, (m_Error
									? m_PublicMsg
											: m_LastMsg));
	}

	@Override
	public Object run() {
		//Call List of Web Services
		callListWebServices();
		return null;
	}
	
	/**
	 * Run Task And Show Dialog Fragment
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 27/08/2014, 15:56:49
	 * @return void
	 */
	public void runTask(){
		m_Task = new BackGroundTask(this, m_Callback);
		//df = new T_Login_ProgressSync(m_Task,m_Conn);
    	//df.show(m_Conn.getFragmentManager(), m_Conn.getResources().getString(R.string.InitSync));
		m_NFManager = (NotificationManager) m_Callback.getSystemService(Context.NOTIFICATION_SERVICE);
		m_Builder = new NotificationCompat.Builder(m_Callback);
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
	private void callListWebServices() {
		//	Get Previous Milliseconds
		long previousMillis = System.currentTimeMillis();
		//Call Web Service Method Create Metadata
		refreshMSG(m_Callback.getString(R.string.Calling) 
				+ " " + m_Callback.getString(R.string.Sync_WebServiceDefinition), false, -1);
		if (!callWebService(new StringNamePair(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition),
				new StringNamePair(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodCreateMetaData),
				new StringNamePair(ILCall.m_WSNumber, "0")))
			return;
		
		//Call Web Service Method Web Service Definition
		
		refreshMSG(m_Callback.getString(R.string.Calling) 
				+ " " + m_Callback.getString(R.string.Sync_WebService), false, -1);
		if (!callWebService(new StringNamePair(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition),
				new StringNamePair(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodWebServiceDefinition),
				new StringNamePair(ILCall.m_Page, "0"),
				new StringNamePair(ILCall.m_WSNumber, "0")
				))
			return;
		
		List<MSPSSyncMenu> sm = MSPSSyncMenu.getNodes(m_Callback, "0", InitialLoad.INITIALLOAD_ServiceDefinition, 
				InitialLoad.INITIALLOAD_ServiceMethodDataSynchronization, null, null);
		
		try {
			MWSWebServiceType wst = new MWSWebServiceType(m_Callback, 0, null);
			for (MSPSSyncMenu mspsSyncMenu : sm) {
				//	Load Data from DB
				wst.loadData(mspsSyncMenu.getWS_WebServiceType_ID());
				//	
				refreshMSG(m_Callback.getString(R.string.Calling) 
						+ " " +  mspsSyncMenu.getName() , false, -1);
				//Call Web Service Method Web Sevice Definition
	    		if (!callWebService(new StringNamePair(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition),
	    							new StringNamePair(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodDataSynchronization),
	    							new StringNamePair(ILCall.m_ServiceTypeField, wst.getValue()),
	    							new StringNamePair(ILCall.m_Page, "0"),
	    							new StringNamePair(ILCall.m_WSNumber, "0")))
	    			break;		
			}
		} catch(Exception e) {
			m_Error = true;
			LogM.log(m_Callback, getClass(), Level.SEVERE, "Error", e);
		} finally {
			//	Get After Milliseconds
			long afterMillis = System.currentTimeMillis();
			long duration = afterMillis - previousMillis;
			//	Set Last Message
			m_LastMsg = m_Callback.getString(R.string.DownloadEnding) + " " 
					+ m_Callback.getString(R.string.Sync_Duration) 
					+ ": " + SyncValues.getDifferenceValue(duration);
			//	Refresh Notification
			setMaxValueProgressBar(0);
			refreshMSG(m_LastMsg , false, 0);
			//	Set Context
			setContext();
		}
		//df.dismiss();
	}
	
	/**
	 * Call to Web Service
	 * @author <a href="mailto:carlosapardam@gmail.com">Carlos Parada</a> 03/09/2014, 23:42:06
	 * @param Params
	 * @return void
	 */
	private boolean callWebService(StringNamePair... Params){
		StringNamePair[] p_Params = new StringNamePair[Params.length];
		int CurrentPage = 0;
		int CurrentWS = 0;
		int pages = 0;
		int CountWS = 0;
		int iWS = -1;
		int iPage =-1;
		InitialLoad il = new InitialLoad(m_URL, m_NameSpace, m_Method, 
				m_IsNetService, m_SoapAction, m_User, m_PassWord
				//, this
				, m_Timeout, null);
		
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
					return callWebService(p_Params);
				}
				else{
					if (iPage != -1)
						p_Params[iPage] = new StringNamePair(Params[iPage].getKey(), Integer.valueOf(0).toString());

					p_Params[iWS] = new StringNamePair(Params[iWS].getKey(), Integer.valueOf(CurrentWS).toString());
					
					refreshMSG(m_PublicMsg, false, -1);
					return callWebService(p_Params);
				}
			}
			else
			{
				if (CurrentPage - pages != 0 && pages > 0){
					p_Params[iWS] = new StringNamePair(Params[iWS].getKey(), Integer.valueOf(0).toString());
					p_Params[iPage] = new StringNamePair(Params[iPage].getKey(), Integer.valueOf(CurrentPage).toString());
					refreshMSG(m_PublicMsg, false, -1);
					return callWebService(p_Params);
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
	 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * <li>Add Translation
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
		Env.setIsEnvLoad(m_Callback, true);
		Env.setContext(m_Callback, "#SUser", m_User);
		Env.setContext(m_Callback, "#SPass", m_PassWord);
		Env.setSavePass(m_Callback, true);
		Env.setAutoLogin(m_Callback, true);
		Env.setContext(m_Callback, KEY_POS_TAB, 1);
		Env.setContext(m_Callback, "#Timeout", m_Timeout);
		//	Refresh Callback
		I_Login fr = ((I_Login)m_Callback);
		if(!m_Error) {
			fr.loadData();
			fr.setEnabled(true);
		}
	}
}
