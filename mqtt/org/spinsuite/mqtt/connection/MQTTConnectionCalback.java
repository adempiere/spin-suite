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

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.spinsuite.sync.content.SyncParent;
import org.spinsuite.sync.content.SyncRequest;
import org.spinsuite.util.Msg;
import org.spinsuite.util.SerializerUtil;

import android.content.Context;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 1, 2015, 3:43:58 AM
 *
 */
public class MQTTConnectionCalback implements MqttCallback {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 */
	public MQTTConnectionCalback(Context p_Ctx) {
		m_Ctx = p_Ctx;
	}

	/**	Context					*/
	private Context m_Ctx = null;

	@Override
	public void connectionLost(Throwable ex) {
		Msg.toastMsg(m_Ctx, "Error Connection Lost: " + ex.getLocalizedMessage());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage msg) throws Exception {
		System.err.println(topic + ":" + msg);
		if(msg != null) {
			SyncParent parent = (SyncParent) SerializerUtil.deserializeObject(msg.getPayload());
			if(parent instanceof SyncRequest) {
				SyncRequest request = (SyncRequest) parent;
				if(request.getRequestType().equals(SyncRequest.RT_BUSINESS_CHAT)) {
					Msg.alertMsg(m_Ctx, "Call from: " + request.getLocalClient_ID() + " Topic: " + request.getTopicName());
				}
			}
		}
	}

}
