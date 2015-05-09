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
import org.spinsuite.sync.content.Invited;
import org.spinsuite.sync.content.SyncMessage_BC;
import org.spinsuite.sync.content.SyncRequest_BC;
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
	/**	Time						*/
	private long					m_millis = 0;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//	
		m_IsRunning = true;
		//	
		if(!MQTTConnection.isNetworkOk(this)
				&& !MQTTConnection.isAutomaticService(this)) {
			stopSelf();
			//	Set to false is Running
			m_IsRunning = false;
			//	
			return START_NOT_STICKY;
		}
		//	
		Timer mTimer = new Timer();
		m_millis = MQTTConnection.getAlarmTime(getApplicationContext());
		mTimer.scheduleAtFixedRate(
				new TimerTask() {
					@Override
					public void run() {
						processThread();
					}      
				}
		, 0, m_millis);
		//	Set to false is Running
		m_IsRunning = false;
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
		//	
		Env.getInstance(getApplicationContext());
		if(!Env.isEnvLoad()
				|| !MQTTConnection.isNetworkOk(this)
				|| !MQTTConnection.isAutomaticService(this))
			return;
		//	Save Current Message
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
				defaultTopics, isReload);
		//	Connection
		if(!connect()) {
			return;
		}
		//	Send Request
		sendOpenRequest();
		//	Send Message
		sendOpenMsg();
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
	 * Send Pending Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public boolean sendOpenMsg() {
		//	Verify Connection
		if(m_Connection.isConnected()) {			
			SyncMessage_BC msgList[] = SPS_BC_Message.getMessage(this, 
					MQTTDefaultValues.STATUS_CREATED, 
					MQTTDefaultValues.TYPE_OUT, true);
			//	
			String m_LocalClient_ID = MQTTConnection.getClient_ID(this);
			for(SyncMessage_BC msgForSend : msgList) {
				try {
					//	Set Client ID
					msgForSend.setLocalClient_ID(m_LocalClient_ID);
					//	Get Request for Topic
					SyncRequest_BC request = SPS_BC_Request.getRequest(this, msgForSend.getSPS_BC_Request_UUID());
					byte[] msg = SerializerUtil.serializeObjectEx(msgForSend);
					MqttMessage message = new MqttMessage(msg);
					message.setQos(MQTTConnection.EXACTLY_ONCE_2);
					message.setRetained(true);
					m_Connection.publish(request.getTopicName(), message);
				} catch (Exception e) {
					LogM.log(this, getClass(), Level.SEVERE, "Error", e);
				}
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
		if(m_Connection.isConnected()) {
			SyncRequest_BC requestList[] = SPS_BC_Request.getRequest(this, MQTTDefaultValues.TYPE_OUT, MQTTDefaultValues.STATUS_CREATED);
			//	
			for(SyncRequest_BC request : requestList) {
				try {
					m_Connection.subscribeEx(request.getTopicName(), MQTTConnection.EXACTLY_ONCE_2);
					//	Send Request
					for(Invited invited : request.getUsers()) {
						//	
						if(invited.getStatus() == null
								|| !invited.getStatus().equals(MQTTDefaultValues.STATUS_CREATED))
							continue;
						//	
						try {
							String m_LocalClient_ID = MQTTConnection.getClient_ID(this);
							request.setLocalClient_ID(m_LocalClient_ID);
							//	Set User Name
							if(!request.isGroup()) {
								request.setName(Env.getContext("#AD_User_Name"));
							}
							byte[] msg = SerializerUtil.serializeObjectEx(request);
							MqttMessage message = new MqttMessage(msg);
							message.setQos(MQTTConnection.EXACTLY_ONCE_2);
							message.setRetained(true);
							m_Connection.publish(MQTTDefaultValues.getRequestTopic(String.valueOf(invited.getAD_USer_ID())), message);
							//	Change Status
							SPS_BC_Request_User.setStatus(this, request.getSPS_BC_Request_UUID(), 
									invited.getAD_USer_ID(), MQTTDefaultValues.STATUS_SENT);
						} catch (Exception e) {
							LogM.log(this, getClass(), Level.SEVERE, "Error", e);
						}
					}
				} catch (MqttSecurityException e) {
					LogM.log(this, getClass(), Level.SEVERE, "Error", e);
				} catch (MqttException e) {
					LogM.log(this, getClass(), Level.SEVERE, "Error", e);
				}
			}
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
