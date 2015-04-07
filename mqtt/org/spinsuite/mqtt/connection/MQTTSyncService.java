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
import java.util.logging.Level;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.spinsuite.bchat.model.SPS_BC_Request;
import org.spinsuite.bchat.view.V_BChat;
import org.spinsuite.sync.content.SyncParent;
import org.spinsuite.sync.content.SyncRequest;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.SerializerUtil;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Mar 30, 2015, 10:34:27 PM
 *
 */
public class MQTTSyncService extends IntentService {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param name
	 */
	public MQTTSyncService(String name) {
		super(name);
	}

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public MQTTSyncService() {
		this("MQTTSyncService");
	}
	
	/**	Connection					*/
	private MQTTConnection 			m_Connection = null;
	/**	Current Instance			*/
	private static MQTTSyncService	m_CurrentService = null;
	/**	Connect						*/
	private static boolean 			m_IsRunning = false;
	/**	Notification Manager		*/
	private NotificationManager 	m_NotificationManager = null;
	/**	Notification Builder		*/
	private Builder 				m_Builder = null;
	/**	Notification ID				*/
	private static final int		NOTIFICATION_ID = 0;
	
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
	
	@Override
	protected void onHandleIntent(Intent intent) {
		m_IsRunning = true;
		Env.getInstance(getApplicationContext());
		if(!Env.isEnvLoad()
				|| !MQTTConnection.isNetworkOk(this)
				|| !MQTTConnection.isAutomaticService(this))
			return;
		//	Verify Reload Service
		boolean isReload = MQTTConnection.isReloadService(this);
		//	Get Connection
		m_Connection = MQTTConnection.getInstance(getApplicationContext(), 
				new MQTTListener(getApplicationContext()), 
				new MqttCallback() {
					
					@Override
					public void messageArrived(String topic, MqttMessage msg) throws Exception {
						if(msg != null) {
							//	Verify if is Duplicated
							if(msg.isDuplicate())
								return;
							SyncParent parent = (SyncParent) SerializerUtil.deserializeObject(msg.getPayload());
							if(parent instanceof SyncRequest) {
								SyncRequest request = (SyncRequest) parent;
								if(request.getRequestType().equals(SyncRequest.RT_BUSINESS_CHAT)) {
									requestArrived(request);
								}
							}
						}
					}
					
					@Override
					public void deliveryComplete(IMqttDeliveryToken arg0) {
						
					}
					
					@Override
					public void connectionLost(Throwable e) {
						forConnectionLost(e);
					}
				}, isReload);
		//	Connection
		connect();
	}

	/**
	 * Connect with Server
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void connect() {
		if(m_Connection.isConnected()) {
			LogM.log(getApplicationContext(), getClass(), 
					Level.FINE, "connect(): Already Connected");
			return;
		}
		//	
		try {
			m_Connection.connect();
			LogM.log(getApplicationContext(), getClass(), 
					Level.FINE, "connect(): Try Connecting");
		} catch (Exception e) {
			LogM.log(getApplicationContext(), getClass(), 
					Level.SEVERE, "connect(): Error", e);
		}
		//	
	}
	
	/**
	 * Schedule Service
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void schedule() {
		Intent service = new Intent(this, MQTTSyncService.class);
		AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
		if(MQTTConnection.isNetworkOk(this)
				&& MQTTConnection.isAutomaticService(this)) {
			//	
			long currentTime = System.currentTimeMillis();
			long nextRun = currentTime + MQTTConnection.getAlarmTime(getApplicationContext());
			//	Date Format
			SimpleDateFormat sdf = DisplayType.getDateFormat(this, DisplayType.DATE_TIME);
			//	Log
			LogM.log(this, getClass(), Level.FINE, "Current Time[" + currentTime + "]-[" + sdf.format(new Date(currentTime)) + "]");
			LogM.log(this, getClass(), Level.FINE, "Next Run[" + nextRun + "]-[" + sdf.format(new Date(nextRun)) + "]");
			//	Set Alarm
			PendingIntent sender = PendingIntent.getService(this, 0, service, 0);
			alarm.set(AlarmManager.RTC_WAKEUP, nextRun, sender);
		} else {
			PendingIntent sender = PendingIntent.getBroadcast(this, 0, service, 0);
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			//	Cancel (Wait for Network)
			alarmManager.cancel(sender);
			//	Log
			LogM.log(this, getClass(), Level.FINE, "Alarm Stoped");
		}
	}
	
	/**
	 * For Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param request
	 * @throws Exception
	 * @return void
	 */
	private void requestArrived(SyncRequest request) throws Exception {
		if(request.getRequestType().equals(SyncRequest.RT_BUSINESS_CHAT)) {
			SPS_BC_Request.newInRequest(this, request);
			//	Instance Notification Manager
			instanceNM();
			//	
			m_Builder.setContentTitle("Call from: " + request.getLocalClient_ID())
    			.setContentText("Topic: " + request.getTopicName())
    			.setSmallIcon(android.R.drawable.stat_notify_chat);
            m_NotificationManager.notify(NOTIFICATION_ID, m_Builder.build());
		}
	}
	
	/**
	 * Instance Notification Manager
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void instanceNM() {
		if(m_NotificationManager == null) {
			m_NotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	        m_Builder = new NotificationCompat.Builder(this);
	        Intent intent = new Intent(this, V_BChat.class);
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			//	Set Main Activity
			PendingIntent m_PendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
			m_Builder.setContentIntent(m_PendingIntent);
			m_Builder.setAutoCancel(true);
		}
	}
	
	/**
	 * Connection is Lost
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param e
	 * @return void
	 */
	private void forConnectionLost(Throwable e) {
		LogM.log(this, getClass(), Level.SEVERE, "Error Connection Lost", e);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		schedule();
	}
}
