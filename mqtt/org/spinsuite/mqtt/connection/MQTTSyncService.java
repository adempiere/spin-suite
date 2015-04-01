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

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;

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
		super("MQTTSyncService");
	}
	
	/**	Connection					*/
	private MQTTConnection 			m_Connection = null;
	/**	Current Instance			*/
	private static MQTTSyncService	m_CurrentService = null;
	/**	Connect						*/
	private static boolean 			m_IsRunning = false;
	
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
		if(!Env.isEnvLoad(getApplicationContext()))
			return;
		//	
		MQTTConnection.setClient_ID(getApplicationContext(), "0101010101");
		MQTTConnection.setHost(getApplicationContext(), "192.168.1.102");
		MQTTConnection.setPort(getApplicationContext(), 1883);
		MQTTConnection.setIsSSLConnection(getApplicationContext(), false);
		MqttConnectOptions connOptions = new MqttConnectOptions();
		connOptions.setUserName("admin");
		connOptions.setPassword("admin".toCharArray());
		connOptions.setConnectionTimeout(100);
		//	
		m_Connection = MQTTConnection.getInstance(getApplicationContext(), new MQTTListener(getApplicationContext()));
		//	
		m_Connection.setConnectionOptions(connOptions);
		//	
		m_Connection.setCallback(new MQTTConnectionCalback(getApplicationContext()));
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
		AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarm.set(AlarmManager.RTC_WAKEUP,
		    System.currentTimeMillis() + (1000 * 10), 
		    PendingIntent.getService(this, 0, new Intent(this, MQTTSyncService.class), 0)
		);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		schedule();
	}
	
}
