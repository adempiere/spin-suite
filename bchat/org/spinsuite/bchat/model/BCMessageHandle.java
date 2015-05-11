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
package org.spinsuite.bchat.model;

import java.util.logging.Level;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.spinsuite.mqtt.connection.MQTTConnection;
import org.spinsuite.mqtt.connection.MQTTDefaultValues;
import org.spinsuite.sync.content.Invited;
import org.spinsuite.sync.content.SyncMessage_BC;
import org.spinsuite.sync.content.SyncRequest_BC;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.SerializerUtil;

import android.content.Context;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com May 11, 2015, 12:32:08 PM
 *
 */
public class BCMessageHandle {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 */
	private BCMessageHandle(Context p_Ctx) {
		m_Ctx = p_Ctx;
	}
	
	/**	Context					*/
	private Context 				m_Ctx = null;
	/**	Instance				*/
	private static BCMessageHandle 	m_Instance = null;
	
	/**
	 * Get Instance
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return BCMessageHandle
	 */
	public static BCMessageHandle getInstance(Context p_Ctx) {
		if(m_Instance == null) {
			m_Instance = new BCMessageHandle(p_Ctx);
		}
		//	Default Return
		return m_Instance;
	}
	
	/**
	 * Process Messages in threads
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param waitTime
	 * @return void
	 */
	public void processMessageThread(final long waitTime) {
		new Thread(new Runnable() {
			public void run() {
				if(waitTime != 0) {
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error Sleep", e);
					}
				}
				//	Process Message
				processMsg();
			}
		}).start();
	}
	
	/**
	 * Process Messages
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	public void processMessageThread() {
		processMessageThread(0);
	}
	
	/**
	 * Process Messages
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private synchronized void processMsg() {
		//	
		Env.getInstance(m_Ctx);
		if(!Env.isEnvLoad()
				|| !MQTTConnection.isNetworkOk(m_Ctx)
				|| !MQTTConnection.isAutomaticService(m_Ctx))
			return;
		//	Send Request
		sendOpenRequest();
		//	Send Message
		sendOpenMsg();
		//	Send Pending Notifications
		sendOpenNotifications();
	}
	
	/**
	 * Send Pending Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public boolean sendOpenMsg() {
		//	Verify Connection
		MQTTConnection m_Connection = MQTTConnection.getInstance(m_Ctx);
		if(m_Connection.connect()) {			
			SyncMessage_BC msgList[] = SPS_BC_Message.getMessage(m_Ctx, 
					MQTTDefaultValues.STATUS_CREATED, 
					MQTTDefaultValues.TYPE_OUT, true);
			//	
			String m_LocalClient_ID = MQTTConnection.getClient_ID(m_Ctx);
			for(SyncMessage_BC msgForSend : msgList) {
				try {
					//	Set Client ID
					msgForSend.setLocalClient_ID(m_LocalClient_ID);
					//	Get Request for Topic
					SyncRequest_BC request = SPS_BC_Request.getRequest(m_Ctx, msgForSend.getSPS_BC_Request_UUID());
					byte[] msg = SerializerUtil.serializeObjectEx(msgForSend);
					MqttMessage message = new MqttMessage(msg);
					message.setQos(MQTTConnection.EXACTLY_ONCE_2);
					message.setRetained(true);
					m_Connection.publish(request.getTopicName(), message);
				} catch (Exception e) {
					LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
				}
			}
		}
		//	Return Ok
		return true;
	}
	
	/**
	 * Send Open Notification for Acknowledgment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public boolean sendOpenNotifications() {
		//	Verify Connection
		MQTTConnection m_Connection = MQTTConnection.getInstance(m_Ctx);
		if(m_Connection.connect()) {			
			SyncMessage_BC msgList[] = SPS_BC_Message.getMessage(m_Ctx, 
					MQTTDefaultValues.STATUS_CREATED, 
					MQTTDefaultValues.TYPE_IN, true);
			//	Iterate
			for(SyncMessage_BC msgReceipt : msgList) {
				try {
					//	Get Request for Topic
					SyncRequest_BC request = SPS_BC_Request.getRequest(m_Ctx, msgReceipt.getSPS_BC_Request_UUID());
					//	Valid Request
					if(request == null)
						continue;
					SPS_BC_Message.sendAcknowledgment(m_Ctx, msgReceipt, request.getTopicName());
				} catch (Exception e) {
					LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
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
		MQTTConnection m_Connection = MQTTConnection.getInstance(m_Ctx);
		if(m_Connection.connect()) {
			SyncRequest_BC requestList[] = SPS_BC_Request.getRequest(m_Ctx, MQTTDefaultValues.TYPE_OUT, MQTTDefaultValues.STATUS_CREATED);
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
							String m_LocalClient_ID = MQTTConnection.getClient_ID(m_Ctx);
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
							SPS_BC_Request_User.setStatus(m_Ctx, request.getSPS_BC_Request_UUID(), 
									invited.getAD_USer_ID(), MQTTDefaultValues.STATUS_SENT);
						} catch (Exception e) {
							LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
						}
					}
				} catch (MqttSecurityException e) {
					LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
				} catch (MqttException e) {
					LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
				}
			}
		}
		//	Return Ok
		return true;
	}
	
}
