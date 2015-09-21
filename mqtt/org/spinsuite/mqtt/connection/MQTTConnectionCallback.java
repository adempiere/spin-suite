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
import java.util.logging.Level;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.spinsuite.base.DB;
import org.spinsuite.bchat.util.BCMessageHandle;
import org.spinsuite.bchat.util.BCNotificationHandle;
import org.spinsuite.sync.content.Invited;
import org.spinsuite.sync.content.SyncAcknowledgment;
import org.spinsuite.sync.content.SyncMessage_BC;
import org.spinsuite.sync.content.SyncParent;
import org.spinsuite.sync.content.SyncRequest_BC;
import org.spinsuite.sync.content.SyncStatus;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.SerializerUtil;

import android.content.Context;

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
	/**	Connection					*/
	private MQTTConnection 			m_Connection = null;
	
	@Override
	public void connectionLost(Throwable e) {
		forConnectionLost(e);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		//	
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
			if(parent instanceof SyncRequest_BC) {
				SyncRequest_BC request = (SyncRequest_BC) parent;
				//	Valid if Exists
				if(existsRequest(request))
					return;
				//	Valid Request for same user
				if(!request.isGroup()
						&& request.getAD_User_ID() == Env.getAD_User_ID()) {
					request.setType(MQTTDefaultValues.TYPE_OUT);
					String newName = getNewRequestName(request);
					request.setName(newName);
				}
				//	Save request
				requestArrived(request);
				//	Subscribe to Topic request
				subscribeToRequest(request);
			} else if(parent instanceof SyncMessage_BC) {
				SyncMessage_BC message = (SyncMessage_BC) parent;
				saveMessageArrived(message, topic);
			} else if(parent instanceof SyncAcknowledgment) {
				SyncAcknowledgment acknowledgment = (SyncAcknowledgment) parent;
				//	Change DB Status
				BCMessageHandle.getInstance(m_Ctx).setMessageStatus(
						acknowledgment.getSPS_BC_Message_UUID(), acknowledgment.getStatus());
				//	Possible Change UI Status
				BCNotificationHandle.getInstance(m_Ctx)
					.changeUIMsgStatus(acknowledgment.getSPS_BC_Request_UUID(), 
						acknowledgment.getSPS_BC_Message_UUID(), acknowledgment.getStatus());
			} else if(parent instanceof SyncStatus) {
				SyncStatus status = (SyncStatus) parent;
				//	Set status
				BCMessageHandle.getInstance(m_Ctx).setMessageStatus(
						status.getSPS_BC_Request_UUID(), status.getStatus());
				//	Change Status
				BCNotificationHandle.getInstance(m_Ctx)
					.changeUIConnectionStatus(status);
			}
		}
	}
	
	/**
	 * Get New Request Name from original Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param request
	 * @return
	 * @return String
	 */
	private String getNewRequestName(SyncRequest_BC request) {
		//	Get Other User
		int m_LocalUser_ID = Env.getAD_User_ID();
		int m_AD_User_ID = -1;
		for(Invited invited : request.getUsers()) {
    		if(invited.getAD_User_ID() != m_LocalUser_ID) {
    			m_AD_User_ID = invited.getAD_User_ID();
    			break;
    		}
    	}
		//	Get Name
		String m_NewName = DB.getSQLValueString(m_Ctx, 
				"SELECT u.Name "
				+ "FROM AD_User u "
				+ "WHERE u.AD_User_ID = " + m_AD_User_ID);
		
		//	Default Return
		return m_NewName;
	}
	
	/**
	 * Verify if Exists Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param request
	 * @return
	 * @return boolean
	 */
	private boolean existsRequest(SyncRequest_BC request) {
		String topic = DB.getSQLValueString(m_Ctx, 
				"SELECT Topic "
				+ "FROM SPS_BC_Request "
				+ "WHERE Topic = ?", request.getTopicName());
		//	Verify if exists
		return (topic != null);
	}
	
	/**
	 * For Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param request
	 * @throws Exception
	 * @return void
	 */
	private void requestArrived(SyncRequest_BC request) throws Exception {
		boolean ok = BCMessageHandle.getInstance(m_Ctx).newInRequest(request);
		//	Add UI Request
		if(ok) {
			BCNotificationHandle.getInstance(m_Ctx)
				.addRequest(request);
		}
	}
	
	/**
	 * For Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param message
	 * @param p_Topic
	 * @return void
	 */
	private void saveMessageArrived(SyncMessage_BC message, String p_Topic) {
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
		//	
		boolean ok = false;
		boolean isSameUser = message.getAD_User_ID() == Env.getAD_User_ID();
		String m_Type = MQTTDefaultValues.TYPE_IN;
		String m_Status = MQTTDefaultValues.STATUS_CREATED;
		//	Valid if is same user
		if(isSameUser) {
			m_Type = MQTTDefaultValues.TYPE_OUT;
			m_Status = MQTTDefaultValues.STATUS_SENT;
			ok = BCMessageHandle.getInstance(m_Ctx).newOutMessage(message, m_Status);
		} else {
			ok = BCMessageHandle.getInstance(m_Ctx).newInMessage(message);
		}
		//	
		if(ok) {
			if(!isSameUser) {
				//	Send Acknowledgment
				BCMessageHandle.getInstance(m_Ctx)
					.sendStatusAcknowledgment(message, p_Topic, MQTTDefaultValues.STATUS_DELIVERED);
			}
			//	Notify
			BCNotificationHandle.getInstance(m_Ctx)
				.addMessage(message, m_Type, m_Status, !isSameUser);
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
		MQTTConnection.getInstance(m_Ctx).tryConnect();
	}
	
	/**
	 * Subscribe to Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param request
	 * @return void
	 */
	private void subscribeToRequest(SyncRequest_BC request) {
		try {
			m_Connection = MQTTConnection.getInstance(m_Ctx);
			//	Verify Connection
			if(m_Connection.connect()) {
				m_Connection.subscribeEx(request.getTopicName(), MQTTConnection.EXACTLY_ONCE_2);
			}
		} catch (MqttSecurityException e) {
			LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
		} catch (MqttException e) {
			LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
		}
	}	
}
