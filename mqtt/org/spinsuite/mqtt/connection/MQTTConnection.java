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

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import android.content.Context;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Mar 25, 2015, 7:05:55 PM
 *
 */
public class MQTTConnection {

	/**	Client Identifier			*/
	private String 				m_Client_ID = null;
	/**	Server Host					*/
	private String 				m_Host = null;
	/**	Port						*/
	private int 				m_Port = 0;
	/**	Client Link					*/
	private MqttAndroidClient 	m_ClientLink = null;
	/**	Connection Options			*/
	private MqttConnectOptions 	m_ConnectionOption;
	/**	Flag for SSL Connection		*/
	private boolean 			m_IsSSLConnection = false;
	/**	Callback Event Listener		*/
	private IMqttActionListener	m_ConnectionListener = null;
	/**	Callback					*/
	private MqttCallback		m_Callback = null;
	/**	Context						*/
	private Context				m_Ctx = null;
	
	/**	Qos						*/
	public static final int		AT_MOST_ONCE = 0;
	public static final int		AT_LEAST_ONCE = 1;
	public static final int		EXACTLY_ONCE = 2;

	/**
	 * Default Constructor
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_Client_ID
	 * @param p_Host
	 * @param p_Port
	 * @param p_IsSSLConnection
	 * @param p_ConnectionListener
	 */
	public MQTTConnection(Context p_Ctx, String p_Client_ID, String p_Host, int p_Port, 
			boolean p_IsSSLConnection, IMqttActionListener p_ConnectionListener) {
		m_Ctx = p_Ctx;
		m_Client_ID = p_Client_ID;
	    m_Host = p_Host;
	    m_Port = p_Port;
	    m_IsSSLConnection = p_IsSSLConnection;
	    m_ConnectionListener = p_ConnectionListener;
	}
	
	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_Client_ID
	 * @param p_Host
	 * @param p_Port
	 * @param p_IsSSLConnection
	 */
	public MQTTConnection(Context p_Ctx, String p_Client_ID, String p_Host, int p_Port, 
			boolean p_IsSSLConnection) {
		this(p_Ctx, p_Client_ID, p_Host, p_Port, p_IsSSLConnection, null);
	}
	
	/**
	 * Create Uri for connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Client_ID
	 * @param p_Host
	 * @param p_Port
	 * @param p_IsSSLConnection
	 * @return
	 * @return String
	 */
	private String createURI(String p_Client_ID, String p_Host, int p_Port, boolean p_IsSSLConnection) {
		String m_URI = null;
		if (p_IsSSLConnection) {
			m_URI = "ssl://" + p_Host + ":" + p_Port;
		} else {
			m_URI = "tcp://" + p_Host + ":" + p_Port;
		}
		//	Return Uri
		return m_URI;
	}
	
	/**
	 * Set Callback
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Callback
	 * @return void
	 */
	public void setCallback(MqttCallback p_Callback) {
		m_Callback = p_Callback;
		if(m_ClientLink != null) {
			m_ClientLink.setCallback(m_Callback);
		}
	}
	
	/**
	 * Connect to Server
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_ConnectionListener
	 * @throws MqttException
	 * @return void
	 */
	public void connect(IMqttActionListener p_ConnectionListener) throws MqttException {
		if(p_ConnectionListener != null) {
			m_ConnectionListener = p_ConnectionListener;
		}
		//	Get Server URI
		String serverURI = createURI(m_Client_ID, m_Host, m_Port, m_IsSSLConnection);
		if(m_ClientLink == null) {
			m_ClientLink = new MqttAndroidClient(m_Ctx, serverURI, m_Client_ID);
		} else if(m_ClientLink.isConnected()) {
			return;
		}
		//	Connect
		m_ClientLink.connect(m_ConnectionOption, m_ConnectionListener);
	}
	
	/**
	 * Connect to Server without Callback
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @throws MqttException
	 * @return void
	 */
	public void connect() throws MqttException {
		connect(null);
	}
	
	/**
	 * Disconnect from Server
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @throws MqttException
	 * @return void
	 */
	public void disconnect() throws MqttException {
		if(m_ClientLink != null) {
			m_ClientLink.disconnect();
		}
	}
	
	/**
	 * Verify if is Connected
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public boolean isConnected() {
		return m_ClientLink.isConnected();
	}
	
	/**
	 * Set Client Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getClient_ID() {
	    return m_Client_ID;
	}
	
	/**
	 * Get Host Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getHostName() {
		return m_Host;
	}
	
	/**
	 * Get Client Link
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return MqttAndroidClient
	 */
	public MqttAndroidClient getClientLink() {
		return m_ClientLink;
	}
	
	/**
	 * Get Callback
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return MqttCallback
	 */
	public MqttCallback getCallback() {
		return m_Callback;
	}
	
	/**
	 * Get Connection Listener
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return IMqttActionListener
	 */
	public IMqttActionListener getConectionListener() {
		return m_ConnectionListener;
	}
	
	/**
	 * Set Connection Options
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param connectOptions
	 * @return void
	 */
	public void setConnectionOptions(MqttConnectOptions connectOptions) {
	    m_ConnectionOption = connectOptions;
	}
	
	/**
	 * Get Connection Options
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return MqttConnectOptions
	 */
	public MqttConnectOptions getConnectionOptions() {
	    return m_ConnectionOption;
	}
	
	/**
	 * Get Port for Connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public int getPort() {
	    return m_Port;
	}
	
	/**
	 * Verify if is SSL Connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public boolean isSSLConnection() {
		return m_IsSSLConnection;
	}
	
	/**
	 * Publish Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Topic
	 * @param p_PayLoad
	 * @param p_QoS
	 * @param p_IsRetained
	 * @throws MqttPersistenceException
	 * @throws MqttException
	 * @return void
	 */
	public void publishEx(String p_Topic, byte[] p_PayLoad, 
			int p_QoS, boolean p_IsRetained) throws MqttPersistenceException, MqttException {
		m_ClientLink.publish(p_Topic, p_PayLoad, p_QoS, p_IsRetained);
	}
	
	/**
	 * Publish Message from Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Topic
	 * @param p_Message
	 * @throws MqttPersistenceException
	 * @throws MqttException
	 * @return void
	 */
	public void publishEx(String p_Topic, MqttMessage p_Message) throws MqttPersistenceException, MqttException {
		m_ClientLink.publish(p_Topic, p_Message);
	}
	
	/**
	 * Publish Message from Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Topic
	 * @param p_Message
	 * @return
	 * @return boolean
	 */
	public boolean publish(String p_Topic, MqttMessage p_Message) {
		try {
			publishEx(p_Topic, p_Message);
			return true;
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
		//	Default
		return false;
	}
	
	/**
	 * Publish Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Topic
	 * @param p_PayLoad
	 * @param p_QoS
	 * @param p_IsRetained
	 * @return
	 * @return boolean
	 */
	public boolean publish(String p_Topic, byte[] p_PayLoad, 
			int p_QoS, boolean p_IsRetained) {
		try {
			publishEx(p_Topic, p_PayLoad, p_QoS, p_IsRetained);
			return true;
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
		//	Default
		return false;
	}
	
	/**
	 * Subscribe to Topic
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Topic
	 * @param p_QoS
	 * @throws MqttSecurityException
	 * @throws MqttException
	 * @return void
	 */
	public void subscribeEx(String p_Topic, int p_QoS) throws MqttSecurityException, MqttException {
		m_ClientLink.subscribe(p_Topic, p_QoS);
	}
	
	/**
	 * Un Subscribe from Topic
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Topic
	 * @throws MqttException
	 * @return void
	 */
	public void unSubscribeEx(String p_Topic) throws MqttException {
		m_ClientLink.unsubscribe(p_Topic);
	}
	
}
