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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.spinsuite.bchat.model.SPS_BC_Message;
import org.spinsuite.bchat.model.SPS_BC_Request;
import org.spinsuite.bchat.util.DisplayBChatThreadItem;
import org.spinsuite.bchat.view.FV_Thread;
import org.spinsuite.bchat.view.V_BChat;
import org.spinsuite.sync.content.SyncMessage;
import org.spinsuite.sync.content.SyncParent;
import org.spinsuite.sync.content.SyncRequest;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.SerializerUtil;

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
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 19, 2015, 9:52:13 PM
 *
 */
public class MQTTConnectionCallback implements MqttCallback {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 */
	public MQTTConnectionCallback(Context p_Ctx) {
		m_Ctx = p_Ctx;
	}

	/**	Context						*/
	private Context 				m_Ctx = null;
	/**	Notification Manager		*/
	private NotificationManager 	m_NotificationManager = null;
	/**	Notification Builder		*/
	private Builder 				m_Builder = null;
	/**	Connection					*/
	private MQTTConnection 			m_Connection = null;
	/**	Notification ID				*/
	private static final int		NOTIFICATION_ID = 0;
	
	@Override
	public void connectionLost(Throwable e) {
		forConnectionLost(e);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		notifyDeliveryComplete(token);
	}

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
							.equals(MQTTConnection.getClient_ID(m_Ctx))) {
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
	
	/**
	 * For Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param request
	 * @throws Exception
	 * @return void
	 */
	private void requestArrived(SyncRequest request) throws Exception {
		SPS_BC_Request.newInRequest(m_Ctx, request);
	}
	
	/**
	 * For Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param message
	 * @return void
	 */
	private void saveMessageArrived(final SyncMessage message) {
		//	Save File in Folder
		if(message.getFileName() != null
				&& message.getFileName().length() > 0
				&& message.getAttachment() != null) {
			try {		
				String fileName = Env.getBC_IMG_DirectoryPathName(m_Ctx) + File.separator + message.getFileName();
				FileOutputStream fos = new FileOutputStream(fileName);
				//	Write
				fos.write(message.getAttachment());
				fos.close();
			} catch (FileNotFoundException e) {
				LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error Saving File", e);
			} catch (IOException e) {
				LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error Saving File", e);
			}
		}
		SPS_BC_Message.newInMessage(m_Ctx, message);
		//	Instance Notification Manager
		instanceNM();
		//	Notify
		if(!addMessage(message, SPS_BC_Message.TYPE_IN)) {
			sendNotification(message);
		}
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
			m_NotificationManager = (NotificationManager) m_Ctx.getSystemService(Context.NOTIFICATION_SERVICE);
	        m_Builder = new NotificationCompat.Builder(m_Ctx);
	        Intent intent = new Intent(m_Ctx, V_BChat.class);
			intent.setAction(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			//	Set Main Activity
			PendingIntent m_PendingIntent = PendingIntent.getActivity(m_Ctx, 0, intent, 0);
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
		m_Connection = MQTTConnection.getInstance(m_Ctx);
		m_Connection.setStatus(MQTTConnection.DISCONNECTED);
		LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error Connection Lost", e);
	}
	
	/**
	 * Subscribe to Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param request
	 * @return void
	 */
	private void subscribeToRequest(SyncRequest request) {
		try {
			m_Connection = MQTTConnection.getInstance(m_Ctx);
			//	Verify Connection
			if(m_Connection.isConnected()) {
				m_Connection.subscribeEx(request.getTopicName(), MQTTConnection.EXACTLY_ONCE_2);
			}
		} catch (MqttSecurityException e) {
			LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
		} catch (MqttException e) {
			LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
		}
	}
	
	/**
	 * Notify if delivery is complete
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param token
	 * @return void
	 */
	private void notifyDeliveryComplete(IMqttDeliveryToken token) {
		try {
			MqttMessage msg = token.getMessage();
			SyncParent parent = (SyncParent) SerializerUtil.deserializeObject(msg.getPayload());
			//	Verify if is local	
			if(parent instanceof SyncRequest) {
				;
			} else if(parent instanceof SyncMessage) {
				//	Change Status
				final SyncMessage message = (SyncMessage) parent;
				SPS_BC_Message.setStatus(m_Ctx, 
						message.getSPS_BC_Message_ID(), SPS_BC_Message.STATUS_SENT);
				//	Change UI Status
				changeUIStatus(message, SPS_BC_Message.STATUS_SENT);
			}
		} catch (MqttException e) {
			LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
		}
	}
	
	/**
	 * Change Status in List View
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param message
	 * @param p_Status
	 * @return void
	 */
	private void changeUIStatus(final SyncMessage message, final String p_Status) {
		FV_Thread.runOnUI(new Runnable() {
			public void run() {
				try {
					if(FV_Thread.isOpened(message.getSPS_BC_Request_ID())) {
						FV_Thread.changeMsgStatus(message.getSPS_BC_Message_ID(), 
								p_Status);
					}
				} catch (Exception e) { 
					LogM.log(m_Ctx, MQTTSyncService.class, Level.SEVERE, "Error", e);
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
	public static boolean addMessage(final SyncMessage message, final String p_Type) {
		FV_Thread.runOnUI(new Runnable() {
			public void run() {
				try {
					if(message != null
							&& FV_Thread.isOpened(message.getSPS_BC_Request_ID())) {
						FV_Thread.addMsg(new DisplayBChatThreadItem(message.getSPS_BC_Message_ID(), 
								message.getText(), message.getSPS_BC_Request_ID(), 
								message.getAD_User_ID(), message.getUserName(), 
								p_Type, 
								SPS_BC_Message.STATUS_CREATED, 
								new Date(System.currentTimeMillis()), 
								message.getFileName(), 
								message.getAttachment()));
						//	Seek To Last
						FV_Thread.seekToLastMsg();
					}
				} catch (Exception e) { 
					LogM.log(Env.getCtx(), MQTTSyncService.class, Level.SEVERE, "Error", e);
				}
			}
		});
		//	Default Return
		return false;
	}
	
}
