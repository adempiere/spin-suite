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

import java.util.logging.Level;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.spinsuite.bchat.util.BCMessageHandle;
import org.spinsuite.bchat.util.BCNotificationHandle;
import org.spinsuite.sync.content.SyncAcknowledgment;
import org.spinsuite.sync.content.SyncMessage_BC;
import org.spinsuite.sync.content.SyncParent;
import org.spinsuite.sync.content.SyncRequest_BC;
import org.spinsuite.util.LogM;
import org.spinsuite.util.SerializerUtil;

import android.content.Context;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 1, 2015, 3:36:57 AM
 *
 */
public class MQTTBCListener implements IMqttActionListener {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 */
	public MQTTBCListener(Context p_Ctx) {
		m_Ctx = p_Ctx;
	}

	/**	Context					*/
	private Context 			m_Ctx = null;
	
	@Override
	public void onFailure(IMqttToken token, Throwable e) {
		LogM.log(m_Ctx, getClass(), Level.SEVERE, "Send Error", e);
		if(token instanceof IMqttDeliveryToken) {
			IMqttDeliveryToken deliveryToken = (IMqttDeliveryToken) token;
			try {
				MqttMessage msg = deliveryToken.getMessage();
				if(msg != null) {
					SyncParent parent = (SyncParent) SerializerUtil
							.deserializeObject(deliveryToken.getMessage().getPayload());
						//	Verify Message
					if(parent instanceof SyncRequest_BC) {
						SyncRequest_BC request = (SyncRequest_BC) parent;
						BCMessageHandle.getInstance(m_Ctx).setRequestStatus(
								request.getSPS_BC_Request_UUID(), MQTTDefaultValues.STATUS_CREATED);
					} else if(parent instanceof SyncMessage_BC) {
						SyncMessage_BC message = (SyncMessage_BC) parent;
						BCMessageHandle.getInstance(m_Ctx).setMessageStatus(
								message.getSPS_BC_Message_UUID(), MQTTDefaultValues.STATUS_CREATED);
					} else if(parent instanceof SyncAcknowledgment) {
						SyncAcknowledgment acknowledgment = (SyncAcknowledgment) parent;
						String newStatus = acknowledgment.getStatus();
						//	Set Status
						if(acknowledgment.equals(MQTTDefaultValues.STATUS_DELIVERED)) {
							newStatus = MQTTDefaultValues.STATUS_FN_DELIVERED;
						} else if(acknowledgment.equals(MQTTDefaultValues.STATUS_READED)) {
							newStatus = MQTTDefaultValues.STATUS_FN_READED;
						}
						//	Set Status
						BCMessageHandle.getInstance(m_Ctx).setMessageStatus(
								acknowledgment.getSPS_BC_Message_UUID(), newStatus);
					}
				}
			} catch (MqttException ex) {
				LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error (onFailure)", ex);
			}
		}
		//	Re-Send
		BCMessageHandle.getInstance(m_Ctx).processMessageThread(MQTTConnection.getTimeForReconnect(m_Ctx));
	}

	@Override
	public void onSuccess(IMqttToken token) {
		//	Validate Token
		if(token instanceof IMqttDeliveryToken) {
			IMqttDeliveryToken deliveryToken = (IMqttDeliveryToken) token;
			notifyDeliveryComplete(deliveryToken);
		}
		//	Log
		LogM.log(m_Ctx, getClass(), Level.FINE, "Send is Ok");
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
			if(parent instanceof SyncRequest_BC) {
				;
			} else if(parent instanceof SyncMessage_BC) {
				//	Change Status
				final SyncMessage_BC message = (SyncMessage_BC) parent;
				BCMessageHandle.getInstance(m_Ctx).setMessageStatus(
						message.getSPS_BC_Message_UUID(), MQTTDefaultValues.STATUS_SENT);
				//	Change UI Status
				BCNotificationHandle.getInstance(m_Ctx)
					.changeUIStatus(message.getSPS_BC_Request_UUID(), 
							message.getSPS_BC_Message_UUID(), MQTTDefaultValues.STATUS_SENT);
			} else if(parent instanceof SyncAcknowledgment) {
				SyncAcknowledgment acknowledgment = (SyncAcknowledgment) parent;
				BCMessageHandle.getInstance(m_Ctx).setMessageStatus(
						acknowledgment.getSPS_BC_Message_UUID(), acknowledgment.getStatus());
			}
		} catch (MqttException e) {
			LogM.log(m_Ctx, getClass(), Level.SEVERE, "Error", e);
		}
	}
}
