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

import org.eclipse.paho.client.mqttv3.MqttException;
import org.spinsuite.bchat.model.BCMessageHandle;
import org.spinsuite.util.LogM;

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
		//	Verify Messages
		BCMessageHandle.getInstance(this).processMessageThread();
		//	Set to false is Running
		m_IsRunning = false;
		return START_STICKY;
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
