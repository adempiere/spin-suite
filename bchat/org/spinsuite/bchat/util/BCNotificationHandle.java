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
package org.spinsuite.bchat.util;

import java.util.Date;
import java.util.logging.Level;

import org.spinsuite.bchat.view.FV_Thread;
import org.spinsuite.bchat.view.FV_ThreadIndex;
import org.spinsuite.bchat.view.V_BChat;
import org.spinsuite.mqtt.connection.MQTTDefaultValues;
import org.spinsuite.sync.content.SyncMessage_BC;
import org.spinsuite.sync.content.SyncRequest_BC;
import org.spinsuite.sync.content.SyncStatus;
import org.spinsuite.util.LogM;

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
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com May 14, 2015, 12:54:30 PM
 *
 */
public class BCNotificationHandle {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 */
	private BCNotificationHandle(Context p_Ctx) {
		m_Ctx = p_Ctx;
	}
	
	/**	Context						*/
	private Context 						m_Ctx = null;
	/**	Instance					*/
	private static BCNotificationHandle 	m_Instance = null;
	/**	Notification Manager		*/
	private NotificationManager 			m_NotificationManager = null;
	/**	Notification Builder		*/
	private Builder 						m_Builder = null;
	
	public static BCNotificationHandle getInstance(Context p_Ctx) {
		if(m_Instance == null) {
			m_Instance = new BCNotificationHandle(p_Ctx);
		}
		//	Default Return
		return m_Instance;
	}
	
	/**
	 * Change Status in List View
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_SPS_BC_Request_UUID
	 * @param p_SPS_BC_Message_UUID
	 * @param p_Status
	 * @return void
	 */
	public void changeUIMsgStatus(final String p_SPS_BC_Request_UUID, 
			final String p_SPS_BC_Message_UUID, final String p_Status) {
		FV_Thread.runOnUI(new Runnable() {
			public void run() {
				try {
					if(FV_Thread.isOpened(p_SPS_BC_Request_UUID)) {
						FV_Thread.changeMsgStatus(p_SPS_BC_Message_UUID, 
								p_Status);
					}
				} catch (Exception e) { 
					LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
				}
			}
		});
	}
	
	/**
	 * Change Connection Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Status
	 * @return void
	 */
	public void changeUIConnectionStatus(final SyncStatus p_Status) {
		FV_Thread.runOnUI(new Runnable() {
			public void run() {
				try {
					FV_Thread.changeConnectionStatus(p_Status);
				} catch (Exception e) { 
					LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
				}
			}
		});
	}
	
	/**
	 * Add New request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Request
	 * @return void
	 */
	public void addRequest(final SyncRequest_BC p_Request) {
		FV_ThreadIndex.runOnUI(new Runnable() {
			public void run() {
				try {
					FV_ThreadIndex.addRequest(p_Request);
				} catch (Exception e) { 
					LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
				}
			}
		});
	}
	
	/**
	 * Add Message to List
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param message
	 * @param p_Type
	 * @param p_Status
	 * @param notify
	 * @return
	 * @return boolean
	 */
	public void addMessage(final SyncMessage_BC message, final String p_Type, final String p_Status, final boolean notify) {
		FV_Thread.runOnUI(new Runnable() {
			public void run() {
				try {
					if(message != null
							&& FV_Thread.isOpened(message.getSPS_BC_Request_UUID())) {
						//	
						FV_Thread.addMsg(new DisplayBChatThreadItem(message.getSPS_BC_Message_UUID(), message.getSPS_BC_Request_UUID(), 
								message.getAD_User_ID(), message.getUserName(), 
								message.getText(), 
								p_Type, 
								p_Status, 
								new Date(System.currentTimeMillis()), 
								message.getFileName(), 
								message.getAttachment()));
						//	Seek To Last
						FV_Thread.seekToLastMsg();
					} else if(notify){
						sendNotification(message);
					}
				} catch (Exception e) { 
					LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error (addMessage)", e);
				}
			}
		});
	}
	
	/**
	 * Send Message Notification
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param message
	 * @return void
	 */
	private void sendNotification(SyncMessage_BC message) {
		//	Instance Notification
		instanceNM(message.getSPS_BC_Request_UUID());
		//	
		SyncRequest_BC request = BCMessageHandle.getInstance(m_Ctx).getRequest(message.getSPS_BC_Request_UUID());
		m_Builder.setContentTitle(request.getName())
			.setContentText(message.getText())
			.setSmallIcon(android.R.drawable.stat_notify_chat);
		//	Show Notification
		m_NotificationManager.notify(MQTTDefaultValues.NOTIFICATION_ID, m_Builder.build());
	}
	
	/**
	 * Instance Notification Manager
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void instanceNM(String p_SPS_BC_Request_UUID) {
		if(m_NotificationManager == null) {
			m_NotificationManager = (NotificationManager) m_Ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		//	
		m_Builder = new NotificationCompat.Builder(m_Ctx);
        Intent intent = new Intent(m_Ctx, V_BChat.class);
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		//	Add Parameter Request
		intent.putExtra("SPS_BC_Request_UUID", p_SPS_BC_Request_UUID);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//	Set Main Activity
		PendingIntent m_PendingIntent = PendingIntent.getActivity(m_Ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		m_Builder.setContentIntent(m_PendingIntent);
		m_Builder.setAutoCancel(true);
		//	Set Vibration
		m_Builder.setVibrate(new long[] {1000, 500, 1000, 500, 1000});
	    //	Set Light
		m_Builder.setLights(Color.GREEN, 3000, 3000);
		//	Set Sound
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		m_Builder.setSound(alarmSound);
	}
}