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
package org.spinsuite.mqtt.connection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.spinsuite.bchat.model.SPS_BC_Message;
import org.spinsuite.bchat.model.SPS_BC_Request;
import org.spinsuite.bchat.model.SPS_BC_Request_User;
import org.spinsuite.bchat.util.BC_OpenMsg;
import org.spinsuite.sync.content.Invited;
import org.spinsuite.sync.content.SyncMessage;
import org.spinsuite.sync.content.SyncParent;
import org.spinsuite.sync.content.SyncRequest;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.SerializerUtil;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 21, 2015, 4:15:10 PM
 *
 */
public class MQTTSyncService extends Service {

	/**	Connection					*/
	private MQTTConnection 			m_Connection = null;
	/**	Current Instance			*/
	private static MQTTSyncService	m_CurrentService = null;
	/**	Connect						*/
	private static boolean 			m_IsRunning = false;
	/**	Message Queue				*/
	private BC_OpenMsg				m_OpenMsg = null;
	/**	Callback					*/
	private MQTTConnectionCallback	m_CallBack = null;
	/**	Time						*/
	private long					m_millis = 0;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(!MQTTConnection.isNetworkOk(this)
				&& !MQTTConnection.isAutomaticService(this)) {
			stopSelf();
		}
		//	
		Timer mTimer = new Timer();
		m_millis = MQTTConnection.getAlarmTime(getApplicationContext());
		mTimer.scheduleAtFixedRate(
				new TimerTask(){
					@Override
					public void run() {
						processThread();
					}      
				}
		, 0, m_millis);
		return START_STICKY;
	}
	
	/**
	 * Process Messages in threads
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void processThread() {
		new Thread(new Runnable() {
			public void run() {
				processMsg();
			}
		}).start();
	}
	
	/**
	 * Process Messages
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void processMsg() {
		//	Start Service
		m_OpenMsg = BC_OpenMsg.getInstance();
		//	
		Env.getInstance(getApplicationContext());
		if(!Env.isEnvLoad()
				|| !MQTTConnection.isNetworkOk(this)
				|| !MQTTConnection.isAutomaticService(this)
				|| MQTTSyncService.isRunning())
			return;
		//	
		m_IsRunning = true;
		//	Save Current Message
		saveMsg();
		//	Verify Reload Service
		boolean isReload = MQTTConnection.isReloadService(this);
		//	Default Topics
		String[] defaultTopics = null;
		if(isReload
				|| m_Connection == null) {
			defaultTopics = SPS_BC_Request.getTopics(getApplicationContext());
		}
		//	Get Connection
		m_Connection = MQTTConnection.getInstance(getApplicationContext(), 
				new MQTTListener(getApplicationContext()), 
				defaultTopics, isReload);
		
		m_CallBack = (MQTTConnectionCallback) m_Connection.getCallback();
		if(m_CallBack == null) {
			m_CallBack = new MQTTConnectionCallback(getApplicationContext());
			m_Connection.setCallback(m_CallBack);
		}
		//	Connection
		if(!connect()) {
			m_IsRunning = false;
			return;
		}
		//	Send Request
		sendOpenRequest();
		//	Send Message
		sendOpenMsg();
		//	Set to false is Running
		m_IsRunning = false;
		//	
		long currentTime = System.currentTimeMillis();
		long nextRun = currentTime + m_millis;
		//	Date Format
		SimpleDateFormat sdf = DisplayType.getDateFormat(this, DisplayType.DATE_TIME);
		//	Log
		LogM.log(this, getClass(), Level.FINE, "Current Time[" + currentTime + "]-[" + sdf.format(new Date(currentTime)) + "]");
		LogM.log(this, getClass(), Level.FINE, "Next Run[" + nextRun + "]-[" + sdf.format(new Date(nextRun)) + "]");
	}
	
	/**
	 * Get Instance
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return MQTTSyncService
	 */
	public static MQTTSyncService getInstance() {
		if(m_CurrentService == null) {
			m_CurrentService = new MQTTSyncService();
		}
		//	
		return m_CurrentService;
	}
	
	/**
	 * Verify if is running process
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public static boolean isRunning() {
		return m_IsRunning;
	}
	
	/**
	 * Save Msg
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void saveMsg() {
		m_OpenMsg = BC_OpenMsg.getInstance();
		SyncParent openMsg = null;
		while ((openMsg = m_OpenMsg.getOpenMsg(true)) != null) {
			if(openMsg instanceof SyncMessage) {
				SyncMessage message = (SyncMessage) openMsg;
				SPS_BC_Message.newOutMessage(this, message);
				MQTTConnectionCallback.addMessage(message, SPS_BC_Message.TYPE_OUT);
			}
		}
	}
	
	/**
	 * Send Pending Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public boolean sendOpenMsg() {
		//	Verify Connection
		if(m_Connection.isConnected()) {			
			SyncMessage msgList[] = SPS_BC_Message.getMessage(this, 
					SPS_BC_Message.STATUS_CREATED, 
					SPS_BC_Message.TYPE_OUT, 
					"EXISTS(SELECT 1 FROM SPS_BC_Request_User ru "
					+ "INNER JOIN SPS_BC_Request r ON(r.SPS_BC_Request_ID = ru.SPS_BC_Request_ID) "
					+ "WHERE ru.SPS_BC_Request_ID = SPS_BC_Message.SPS_BC_Request_ID "
					+ "AND (ru.Status = '" + SPS_BC_Request.STATUS_SENT + "' OR r.Type = '" + SPS_BC_Request.TYPE_IN + "'))", 
					true);
			//	
			String m_LocalClient_ID = MQTTConnection.getClient_ID(this);
			for(SyncMessage msgForSend : msgList) {
				//	Set Client ID
				msgForSend.setLocalClient_ID(m_LocalClient_ID);
				//	Get Request for Topic
				SyncRequest request = SPS_BC_Request.getRequest(this, msgForSend.getSPS_BC_Request_ID());
				byte[] msg = SerializerUtil.serializeObject(msgForSend);
				MqttMessage message = new MqttMessage(msg);
				message.setQos(MQTTConnection.EXACTLY_ONCE_2);
				message.setRetained(true);
				m_Connection.publish(request.getTopicName(), message);				
			}
		}
		//	Return Ok
		return true;
	}
	
	/**
	 * Send Open Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_Connection
	 * @return
	 * @return boolean
	 */
	public boolean sendOpenRequest() {
		try {
			//	Verify Connection
			if(m_Connection.isConnected()) {
				SyncRequest requestList[] = SPS_BC_Request.getRequest(this, SPS_BC_Request.TYPE_OUT, SPS_BC_Request.STATUS_CREATED);
				//	
				for(SyncRequest request : requestList) {
					m_Connection.subscribeEx(request.getTopicName(), MQTTConnection.EXACTLY_ONCE_2);
					//	Send Request
					for(Invited invited : request.getUsers()) {
						//	
						if(invited.getStatus() == null
								|| !invited.getStatus().equals(SPS_BC_Message.STATUS_CREATED))
							continue;
						//	
						String m_LocalClient_ID = MQTTConnection.getClient_ID(this);
						request.setLocalClient_ID(m_LocalClient_ID);
						//	Set User Name
						if(!request.isGroup()) {
							request.setName(Env.getContext("#AD_User_Name"));
						}
						byte[] msg = SerializerUtil.serializeObject(request);
						MqttMessage message = new MqttMessage(msg);
						message.setQos(MQTTConnection.EXACTLY_ONCE_2);
						message.setRetained(true);
						m_Connection.publish(MQTTDefaultValues.getRequestTopic(String.valueOf(invited.getAD_USer_ID())), message);
						//	Change Status
						SPS_BC_Request_User.setStatus(this, request.getSPS_BC_Request_ID(), 
								invited.getAD_USer_ID(), SPS_BC_Message.STATUS_SENT);
					}
				}
			}
		} catch (MqttSecurityException e) {
			LogM.log(this, getClass(), Level.SEVERE, "Error", e);
		} catch (MqttException e) {
			LogM.log(this, getClass(), Level.SEVERE, "Error", e);
		}
		//	Return Ok
		return true;
	}

	/**
	 * Connect with Server
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return boolean
	 */
	private boolean connect() {
		if(m_Connection.isConnected()) {
			LogM.log(getApplicationContext(), getClass(), 
					Level.FINE, "connect(): Already Connected");
			return true;
		}
		//	
		try {
			//	Connect
			if(m_Connection.getStatus() == MQTTConnection.TRY_CONNECTING) {
				return false;
			}
			m_Connection.connect();
			m_Connection.setStatus(MQTTConnection.TRY_CONNECTING);
			LogM.log(getApplicationContext(), getClass(), 
					Level.FINE, "connect(): Try Connecting");
			//	
			return true;
		} catch (Exception e) {
			LogM.log(getApplicationContext(), getClass(), 
					Level.SEVERE, "connect(): Error", e);
		}
		//	
		return false;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(m_Connection != null
				&& m_Connection.isConnected()) {
			try {
				m_Connection.disconnect();
			} catch (MqttException e) {
				LogM.log(getApplicationContext(), getClass(), 
						Level.SEVERE, "disconnect(): Error", e);
			}
		}
	}
}
