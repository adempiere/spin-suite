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
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.spinsuite.bchat.model.SPS_BC_Message;
import org.spinsuite.bchat.model.SPS_BC_Request;
import org.spinsuite.bchat.model.SPS_BC_Request_User;
import org.spinsuite.bchat.util.BC_OpenMsg;
import org.spinsuite.bchat.util.DisplayBChatThreadItem;
import org.spinsuite.bchat.view.FV_Thread;
import org.spinsuite.bchat.view.V_BChat;
import org.spinsuite.sync.content.Invited;
import org.spinsuite.sync.content.SyncMessage;
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
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
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
		m_OpenMsg = BC_OpenMsg.getInstance();
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
	/**	Message Queue				*/
	private BC_OpenMsg				m_OpenMsg = null;
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
				new MqttCallback() {
					
					@Override
					public void messageArrived(String topic, MqttMessage msg) throws Exception {
						if(msg != null) {
							//	Verify if is Duplicated
							if(msg.isDuplicate())
								return;
							SyncParent parent = (SyncParent) SerializerUtil.deserializeObject(msg.getPayload());
							//	Verify if is local
							if(parent.getLocalClient_ID() != null
									&& parent.getLocalClient_ID()
											.equals(MQTTConnection.getClient_ID(getApplicationContext()))) {
								return;
							}
							if(parent instanceof SyncRequest) {
								SyncRequest request = (SyncRequest) parent;
								//	Save request
								requestArrived(request);
								//	Subscribe to Topic request
								subscribeToRequest(request);
							} else if(parent instanceof SyncMessage) {
								SyncMessage message = (SyncMessage) parent;
								saveMessageArrived(message);
							}
						}
					}
					
					@Override
					public void deliveryComplete(IMqttDeliveryToken token) {
						
					}
					
					@Override
					public void connectionLost(Throwable e) {
						forConnectionLost(e);
					}
				}, defaultTopics, isReload);
		//	Connection
		connect();
		//	Send Request
		sendOpenRequest();
		//	Send Message
		sendOpenMsg();
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
				SPS_BC_Message.newOutMessage(this, (SyncMessage) openMsg);
			}
		}
	}
	
	/**
	 * Subscribe to Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param request
	 * @return void
	 */
	private void subscribeToRequest(SyncRequest request) {
		try {
			//	Verify Connection
			if(m_Connection.isConnected()) {
				m_Connection.subscribeEx(request.getTopicName(), MQTTConnection.AT_LEAST_ONCE_1);
			}
		} catch (MqttSecurityException e) {
			LogM.log(this, getClass(), Level.SEVERE, "Error", e);
		} catch (MqttException e) {
			LogM.log(this, getClass(), Level.SEVERE, "Error", e);
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
					+ "AND (ru.Status = '" + SPS_BC_Request.STATUS_SENT + "' OR r.Type = '" + SPS_BC_Request.TYPE_IN + "'))");
			//	
			String m_LocalClient_ID = MQTTConnection.getClient_ID(this);
			for(SyncMessage msgForSend : msgList) {
				//	Set Client ID
				msgForSend.setLocalClient_ID(m_LocalClient_ID);
				//	Get Request for Topic
				SyncRequest request = SPS_BC_Request.getRequest(this, msgForSend.getSPS_BC_Request_ID());
				byte[] msg = SerializerUtil.serializeObject(msgForSend);
				MqttMessage message = new MqttMessage(msg);
				message.setQos(MQTTConnection.AT_LEAST_ONCE_1);
				message.setRetained(true);
				m_Connection.publish(request.getTopicName(), message);
				//	Change Status
				SPS_BC_Message.setStatus(this, msgForSend.getSPS_BC_Message_ID(), SPS_BC_Message.STATUS_SENT);
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
					m_Connection.subscribeEx(request.getTopicName(), MQTTConnection.AT_LEAST_ONCE_1);
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
						message.setQos(MQTTConnection.AT_LEAST_ONCE_1);
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
		SPS_BC_Request.newInRequest(this, request);
	}
	
	/**
	 * For Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param message
	 * @return void
	 */
	private void saveMessageArrived(final SyncMessage message) {
		SPS_BC_Message.newInMessage(this, message);
		//	Instance Notification Manager
		instanceNM();
		//	Notify
		FV_Thread.runOnUI(new Runnable() {
			public void run() {
				try {
					//	Add to List View
					if(!addMessage(message, SPS_BC_Message.TYPE_IN)) {
						sendNotification(message);
					}
				} catch (Exception e) { 
					LogM.log(getApplicationContext(), MQTTSyncService.class, Level.SEVERE, "Error", e);
				}
			}
		});
	}
	
	/**
	 * Add Message to List
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param message
	 * @param p_Type
	 * @return
	 * @return boolean
	 */
	private boolean addMessage(SyncMessage message, String p_Type) {
		if(message != null
				&& FV_Thread.isOpened(message.getSPS_BC_Request_ID())) {
			FV_Thread.addMsg(new DisplayBChatThreadItem(message.getSPS_BC_Message_ID(), 
					message.getText(), message.getSPS_BC_Request_ID(), 
					message.getAD_User_ID(), null, 
					p_Type, 
					SPS_BC_Message.STATUS_CREATED, 
					new Date(System.currentTimeMillis())));
			return true;
		}
		//	Default Return
		return false;
	}
	
	/**
	 * Send Message Notification
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param message
	 * @return void
	 */
	private void sendNotification(SyncMessage message) {
		m_Builder.setContentTitle("In Message")
			.setContentText(message.getText())
			.setSmallIcon(android.R.drawable.stat_notify_chat);
		//	Show Notification
		m_NotificationManager.notify(NOTIFICATION_ID, m_Builder.build());
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
			//	Set Vibration
			m_Builder.setVibrate(new long[] {1000, 500, 1000, 500, 1000});
		    //	Set Light
			m_Builder.setLights(Color.GRAY, 3000, 3000);
			//	Set Sound
			Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			m_Builder.setSound(alarmSound);
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
