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
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.sync;

import java.util.List;
import java.util.logging.Level;

import org.ksoap2.serialization.SoapObject;
import org.spinsuite.base.R;
import org.spinsuite.model.MSPSSyncMenu;
import org.spinsuite.model.MWSWebServiceType;
import org.spinsuite.util.LogM;
import org.spinsuite.util.StringNamePair;
import org.spinsuite.util.SyncValues;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Mar 13, 2015, 11:07:07 PM
 *
 */
public class SyncService extends IntentService {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param name
	 */
	public SyncService(String name) {
		super(name);
		m_BCast = LocalBroadcastManager.getInstance(this);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public SyncService() {
		this("SyncService");
	}
	
	/** Url 					*/
	private String 						m_URL = ""; 
	/** Name Space 				*/ 
	private String 						m_NameSpace = "";
	/** Method 					*/
	private String 						m_Method = "";
	/** Is .Net Service			*/
	private boolean 					m_IsNetService = false;
	/** User					*/	
	private String 						m_User =  "";
	/** Password				*/
	private String 						m_PassWord = "" ;	
	/** Soap Action 			*/
	private String 						m_SoapAction = "";
	/** Timeout 				*/
	private int 						m_Timeout = 0;
	/**	Broadcast				*/
	private LocalBroadcastManager 		m_BCast = null;
	/**	Instance				*/
	private static boolean				m_IsRunning = false;
	
	/**
	 * Verify if Running
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public static boolean isRunning() {
		return m_IsRunning;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		m_IsRunning = true;
		Bundle extras = intent.getExtras();
		m_URL 			= extras.getString(SyncValues.KEY_SOAP_URL);
		m_NameSpace 	= extras.getString(SyncValues.KEY_NAME_SPACE);
		m_Method 		= extras.getString(SyncValues.KEY_METHOD);
		m_IsNetService 	= extras.getBoolean(SyncValues.KEY_NET_SERVICE);
		m_User 			= extras.getString(SyncValues.KEY_USER);
		m_PassWord 		= extras.getString(SyncValues.KEY_PASS);
		m_SoapAction 	= extras.getString(SyncValues.KEY_SOAP_ACTION);
		m_Timeout 		= extras.getInt(SyncValues.KEY_TIMEOUT);
		//	
		callListWebServices();
	}
	
	
	/**
	 * Refresh Message
	 * @author <a href="mailto:carlosapardam@gmail.com">Carlos Parada</a> 18/10/2014, 13:25:58
	 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * <li>Add Translation
	 * @param p_Status
	 * @param p_Msg
	 * @param p_Error
	 * @param p_Progress
	 * @return void
	 */
	public void sendStatus(String p_Status, String p_Msg, boolean p_Error, Integer p_Progress) {
		Intent m_Filter = new Intent(SyncValues.BC_IL_FILTER);
		//	Valid Progress
		if(p_Progress == null)
			p_Progress = -1;
		//	Valid Message to send
		if(p_Msg != null
				&& p_Msg.length() > 0) {
			m_Filter.putExtra(SyncValues.BC_KEY_MSG, p_Msg);
		}
		//	Valid Status
		if(p_Status != null
				&& p_Status.length() > 0) {
			m_Filter.putExtra(SyncValues.BC_KEY_STATUS, p_Status);
		}
		//	Verify Message Type
		String msgType = SyncValues.BC_MSG_TYPE_ERROR;
		if(!p_Error
				&& p_Progress > 0) {
			msgType = SyncValues.BC_MSG_TYPE_PROGRESS;
		} else if(!p_Error) {
			msgType = SyncValues.BC_MSG_TYPE_MSG;
		}
		//	
		m_Filter.putExtra(SyncValues.BC_KEY_MSG_TYPE, msgType);
		if(p_Progress > 0) {
			m_Filter.putExtra(SyncValues.BC_KEY_PROGRESS, p_Progress);
		} else {
			m_Filter.putExtra(SyncValues.BC_KEY_MAX_VALUE, 0);
		}
		//	Send
		m_BCast.sendBroadcast(m_Filter);
	}
	
	/**
	 * Without Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Msg
	 * @param p_Error
	 * @param p_Progress
	 * @return void
	 */
	public void sendStatus(String p_Msg, boolean p_Error, Integer p_Progress) {
		sendStatus(SyncValues.BC_STATUS_PROGRESS, p_Msg, p_Error, p_Progress);
	}
	
	/**
	 * Send Service Status Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Status
	 * @param p_Msg
	 * @return void
	 */
	public void sendStatus(String p_Status, String p_Msg) {
		sendStatus(p_Status, p_Msg, false, -1);
	}
	
	/**
	 * Set Max Value for Notification
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_MaxValue
	 * @return void
	 */
	public void setMaxValueProgressBar(int p_MaxValue) {
		Intent m_Filter = new Intent(SyncValues.BC_IL_FILTER);
		m_Filter.putExtra(SyncValues.BC_KEY_STATUS, SyncValues.BC_STATUS_PROGRESS);
		m_Filter.putExtra(SyncValues.BC_KEY_MAX_VALUE, p_MaxValue);
		//	Send
		m_BCast.sendBroadcast(m_Filter);
	}
	
	/**
	 * 
	 * @author <a href="mailto:carlosapardam@gmail.com">Carlos Parada</a> 03/09/2014, 23:51:06
	 * @return void
	 */
	private void callListWebServices() {
		//	Get Previous Milliseconds
		long previousMillis = System.currentTimeMillis();
		String m_LastMsg = null;
		sendStatus(SyncValues.BC_STATUS_START, null);
		//Call Web Service Method Create Meta-data
		sendStatus(getString(R.string.Calling) 
				+ " " + getString(R.string.Sync_WebServiceDefinition), false, -1);
		
		boolean callIsOk = callWebService(new StringNamePair(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition),
				new StringNamePair(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodCreateMetaData),
				new StringNamePair(ILCall.m_WSNumber, "0"));
		//	Verify Call
		if (!callIsOk) {
			m_IsRunning = callIsOk;
			return;
		}
		
		//Call Web Service Method Web Service Definition
		
		sendStatus(getString(R.string.Calling) 
				+ " " + getString(R.string.Sync_WebService), false, -1);
		//	
		callIsOk = callWebService(new StringNamePair(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition),
				new StringNamePair(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodWebServiceDefinition),
				new StringNamePair(ILCall.m_Page, "0"),
				new StringNamePair(ILCall.m_WSNumber, "0"));
		//	Verify Call
		if (!callIsOk) {
			m_IsRunning = callIsOk;
			return;
		}
		
		List<MSPSSyncMenu> sm = MSPSSyncMenu.getNodes(this, "0", InitialLoad.INITIALLOAD_ServiceDefinition, 
				InitialLoad.INITIALLOAD_ServiceMethodDataSynchronization, null, null);
		
		try {
			MWSWebServiceType wst = new MWSWebServiceType(this, 0, null);
			for (MSPSSyncMenu mspsSyncMenu : sm) {
				//	Load Data from DB
				wst.loadData(mspsSyncMenu.getWS_WebServiceType_ID());
				//	
				sendStatus(getString(R.string.Calling) 
						+ " " +  mspsSyncMenu.getName() , false, -1);
				//	Call Web Service Method Web Service Definition
	    		callIsOk = callWebService(new StringNamePair(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition),
	    							new StringNamePair(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodDataSynchronization),
	    							new StringNamePair(ILCall.m_ServiceTypeField, wst.getValue()),
	    							new StringNamePair(ILCall.m_Page, "0"),
	    							new StringNamePair(ILCall.m_WSNumber, "0"));
	    		//	Verify Call
	    		if (!callIsOk) {
	    			m_IsRunning = callIsOk;
	    			break;
	    		}
			}
		} catch(Exception e) {
			LogM.log(this, getClass(), Level.SEVERE, "Error", e);
			m_LastMsg = e.getLocalizedMessage();
		} finally {
			m_IsRunning = callIsOk;
			//	Get After Milliseconds
			long afterMillis = System.currentTimeMillis();
			long duration = afterMillis - previousMillis;
			//	Set Last Message
			if(m_LastMsg == null) {
				m_LastMsg = getString(R.string.DownloadEnding) + " " 
						+ getString(R.string.Sync_Duration) 
						+ ": " + SyncValues.getDifferenceValue(duration);
				//	
				sendStatus(SyncValues.BC_STATUS_END, m_LastMsg);
			} else  {
				sendStatus(SyncValues.BC_STATUS_END, m_LastMsg, true, -1);
			}
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
				m_IsNetService, m_SoapAction, m_User, m_PassWord, m_Timeout, this);
		
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
		
		if (so != null){
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
					//	Set to Call
					sendStatus(getString(R.string.Calling) 
							+ " " + getString(R.string.Sync_WebService), false, -1);
					return callWebService(p_Params);
				}
				else{
					if (iPage != -1)
						p_Params[iPage] = new StringNamePair(Params[iPage].getKey(), Integer.valueOf(0).toString());

					p_Params[iWS] = new StringNamePair(Params[iWS].getKey(), Integer.valueOf(CurrentWS).toString());
					//	Set to Call
					sendStatus(getString(R.string.Calling) 
							+ " " + getString(R.string.Sync_WebService), false, -1);
					return callWebService(p_Params);
				}
			}
			else
			{
				if (CurrentPage - pages != 0 && pages > 0){
					p_Params[iWS] = new StringNamePair(Params[iWS].getKey(), Integer.valueOf(0).toString());
					p_Params[iPage] = new StringNamePair(Params[iPage].getKey(), Integer.valueOf(CurrentPage).toString());
					//	Set to Call
					sendStatus(getString(R.string.Calling) 
							+ " " + getString(R.string.Sync_WebService), false, -1);
					return callWebService(p_Params);
				}
			}
		} else {
			return false;
		}
		//	
		return true;	
	}
}
