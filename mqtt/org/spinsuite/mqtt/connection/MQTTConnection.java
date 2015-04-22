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

import java.util.ArrayList;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.spinsuite.util.Env;

import android.content.Context;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Mar 25, 2015, 7:05:55 PM
 *
 */
public class MQTTConnection {

	/**	Client Identifier			*/
	private String 					m_Client_ID = null;
	/**	Server Host					*/
	private String 					m_Host = null;
	/**	Port						*/
	private int 					m_Port = 0;
	/**	Client Link					*/
	private MqttAndroidClient 		m_ClientLink = null;
	/**	Connection Options			*/
	private MqttConnectOptions 		m_ConnectionOption;
	/**	Flag for SSL Connection		*/
	private boolean 				m_IsSSLConnection = false;
	/**	Callback Event Listener		*/
	private IMqttActionListener		m_ConnectionListener = null;
	/**	Callback					*/
	private MqttCallback			m_Callback = null;
	/**	Context						*/
	private Context					m_Ctx = null;
	/**	Connection					*/
	private static MQTTConnection	m_Connection = null;
	/**	Is Subscribe Done			*/
	private boolean 				m_IsSubscribe = false;
	/**	Current Subscriptions		*/
	private ArrayList<String>		m_SubscribedTopics = null;
	/**	Status						*/
	private int						m_Status = 0;
	
	/**	QoS									*/
	public static final int			AT_MOST_ONCE_0 				= 0;
	public static final int			AT_LEAST_ONCE_1 			= 1;
	public static final int			EXACTLY_ONCE_2 				= 2;
	/**	Default Constants for Context		*/
	private static final String		MQTT_CLIENT_ID 				= "#MQTT_Client_ID";
	private static final String		MQTT_HOST 					= "#MQTT_Host";
	private static final String		MQTT_PORT 					= "#MQTT_Port";
	private static final String		MQTT_IS_SSL_CONNECTION 		= "#MQTT_IsSSLConnection";
	private static final String		MQTT_SSL_FILE_PATH 			= "#MQTT_SSLFilePath";
	private static final String		MQTT_ALARM_TIME 			= "#MQTT_AlamTime";
	private static final String		MQTT_IS_AUTOMATIC_SERVICE 	= "#MQTT_AutomaticService";
	private static final String		MQTT_NETWORK_OK 			= "#MQTT_NetworkOk";
	private static final String		MQTT_USER_NAME 				= "#MQTT_UserName";
	private static final String		MQTT_PASSWORD 				= "#MQTT_Password";
	private static final String		MQTT_TIMEOUT 				= "#MQTT_Timeout";
	private static final String		MQTT_IS_RELOAD_SERVICE 		= "#MQTT_IsreloadService";
	
	/**	Connection Status					*/
	public static final int			CONNECTED					= 1;
	public static final int			DISCONNECTED				= 2;
	public static final int			TRY_CONNECTING				= 3;
	
	
	/**
	 * Default Constructor
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_Client_ID
	 * @param p_Host
	 * @param p_Port
	 * @param p_IsSSLConnection
	 * @param p_ConnectionListener
	 * @param p_SubscribedTopics
	 */
	public MQTTConnection(Context p_Ctx, String p_Client_ID, String p_Host, int p_Port, 
			boolean p_IsSSLConnection, IMqttActionListener p_ConnectionListener, String[] p_SubscribedTopics) {
		m_Ctx = p_Ctx;
		m_Client_ID = p_Client_ID;
	    m_Host = p_Host;
	    m_Port = p_Port;
	    m_IsSSLConnection = p_IsSSLConnection;
	    m_ConnectionListener = p_ConnectionListener;
	    m_ConnectionOption = new MqttConnectOptions();
	    m_ConnectionOption.setUserName(MQTTConnection.getClient_ID(p_Ctx));
	    m_ConnectionOption.setUserName(getMQTTUser(p_Ctx));
	    m_ConnectionOption.setPassword(getMQTTPass(p_Ctx).toCharArray());
	    m_ConnectionOption.setConnectionTimeout(getTimeout(p_Ctx));
	    m_ConnectionOption.setCleanSession(true);
	    //	
	    m_IsSubscribe = false;
	    m_SubscribedTopics = new ArrayList<String>();
	    //	Add Topics
	    if(p_SubscribedTopics != null) {
	    	addTopic(p_SubscribedTopics);
	    	setIsSubscribed(false);
	    }
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
		this(p_Ctx, p_Client_ID, p_Host, p_Port, p_IsSSLConnection, null, null);
	}
	
	/**
	 * Create Connection from Context Parameters
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_ConnectionListener
	 * @param p_SubscribedTopics
	 */
	public MQTTConnection(Context p_Ctx, IMqttActionListener p_ConnectionListener, String[] p_SubscribedTopics) {
		this(p_Ctx, getClient_ID(p_Ctx), getHost(p_Ctx), getPort(p_Ctx), isSSLConnection(p_Ctx), p_ConnectionListener, p_SubscribedTopics);
	}
	
	/**
	 * Get if is Subscribed to topics
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public boolean isSubscribed() {
		return m_IsSubscribe;
	}
	
	/**
	 * Set Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Status
	 * @return void
	 */
	public void setStatus(int p_Status) {
		m_Status = p_Status;
	}
	
	/**
	 * Get Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public int getStatus() {
		return m_Status;
	}
	
	/**
	 * Set Subscribed
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_IsSubscribe
	 * @return void
	 */
	public void setIsSubscribed(boolean p_IsSubscribe) {
		m_IsSubscribe = p_IsSubscribe;
	}
	
	/**
	 * Get Subscribed Topics
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String[]
	 */
	public String[] getSubscribedTopics() {
		return m_SubscribedTopics.toArray(new String[m_SubscribedTopics.size()]);
	}
	
	/**
	 * Get Client ID
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return String
	 */
	public static String getClient_ID(Context p_Ctx) {
		return Env.getContext(MQTT_CLIENT_ID);
	}
	
	/**
	 * Set Client Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_Client_ID
	 * @return void
	 */
	public static void setClient_ID(Context p_Ctx, String p_Client_ID) {
		Env.setContext(MQTT_CLIENT_ID, p_Client_ID);
	}
	
	/**
	 * Get MQTT Host
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return String
	 */
	public static String getHost(Context p_Ctx) {
		return Env.getContext(MQTT_HOST);
	}
	
	/**
	 * Set Host
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_Host
	 * @return void
	 */
	public static void setHost(Context p_Ctx, String p_Host) {
		Env.setContext(MQTT_HOST, p_Host);
	}
	
	/**
	 * Get SSL File Path
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return String
	 */
	public static String getSSLFilePath(Context p_Ctx) {
		return Env.getContext(MQTT_SSL_FILE_PATH);
	}
	
	/**
	 * Set SSL File Path
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_SSLFilePath
	 * @return void
	 */
	public static void setSSLFilePath(Context p_Ctx, String p_SSLFilePath) {
		Env.setContext(MQTT_SSL_FILE_PATH, p_SSLFilePath);
	}
	/**
	 * Get MQTT Port
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return int
	 */
	public static int getPort(Context p_Ctx) {
		return Env.getContextAsInt(p_Ctx, MQTT_PORT);
	}
	
	/**
	 * Set Connection Port
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_Port
	 * @return void
	 */
	public static void setPort(Context p_Ctx, int p_Port) {
		Env.setContext(MQTT_PORT, p_Port);
	}
	
	/**
	 * Get Timeout
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return int
	 */
	public static int getTimeout(Context p_Ctx) {
		return Env.getContextAsInt(p_Ctx, MQTT_TIMEOUT);
	}
	
	/**
	 * Set Timeout
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_Timeout
	 * @return void
	 */
	public static void setTimeout(Context p_Ctx, int p_Timeout) {
		Env.setContext(MQTT_TIMEOUT, p_Timeout);
	}
	
	/**
	 * Get for Is SSL Connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isSSLConnection(Context p_Ctx) {
		return Env.getContextAsBoolean(MQTT_IS_SSL_CONNECTION);
	}
	
	/**
	 * Set Is SSL Connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_IsSSLConnection
	 * @return void
	 */
	public static void setIsSSLConnection(Context p_Ctx, boolean p_IsSSLConnection) {
		Env.setContext(MQTT_IS_SSL_CONNECTION, p_IsSSLConnection);
	}
	
	/**
	 * Verify if is Automatic Service
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isAutomaticService(Context p_Ctx) {
		return Env.getContextAsBoolean(MQTT_IS_AUTOMATIC_SERVICE);
	}
	
	/**
	 * Set Is Automatic Service for MQTT
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_IsAutomaticService
	 * @return void
	 */
	public static void setIsAutomaticService(Context p_Ctx, boolean p_IsAutomaticService) {
		Env.setContext(MQTT_IS_AUTOMATIC_SERVICE, p_IsAutomaticService);
	}
	
	/**
	 * Verify if is Reload
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isReloadService(Context p_Ctx) {
		return Env.getContextAsBoolean(MQTT_IS_RELOAD_SERVICE);
	}
	
	/**
	 * Set the Reload property
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_IsReloadService
	 * @return void
	 */
	public static void setIsReloadService(Context p_Ctx, boolean p_IsReloadService) {
		Env.setContext(MQTT_IS_RELOAD_SERVICE, p_IsReloadService);
	}
	
	/**
	 * Set Alamr Time in milliseconds
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param time
	 * @return void
	 */
	public static void setAlarmTime(Context p_Ctx, long time) {
		Env.setContext(p_Ctx, MQTT_ALARM_TIME, time);
	}
	
	/**
	 * Get Alarm Time in milliseconds
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return long
	 */
	public static long getAlarmTime(Context p_Ctx) {
		return Env.getContextAsLong(p_Ctx, MQTT_ALARM_TIME);
	}
	
	/**
	 * Is Network Ok
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isNetworkOk(Context p_Ctx) {
		return Env.getContextAsBoolean(MQTT_NETWORK_OK);
	}
	
	/**
	 * Set Network Ok
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param ok
	 * @return void
	 */
	public static void setNetworkOk(Context p_Ctx, boolean ok) {
		Env.setContext(p_Ctx, MQTT_NETWORK_OK, ok);
	}
	
	/**
	 * Get User Name for connection with MQTT
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return String
	 */
	public static String getMQTTUser(Context p_Ctx) {
		return Env.getContext(MQTT_USER_NAME);
	}
	
	/**
	 * Set User Name for connection with MQTT Server
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_UserName
	 * @return void
	 */
	public static void setMQTTUser(Context p_Ctx, String p_UserName) {
		Env.setContext(MQTT_USER_NAME, p_UserName);
	}
	
	/**
	 * Get Password for connection with MQTT Server
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return String
	 */
	public static String getMQTTPass(Context p_Ctx) {
		String pass = Env.getContext(MQTT_PASSWORD);
		//	Valid Null
		if(pass == null)
			pass = "";
		//	Return
		return pass;
	}
	
	/**
	 * Set Password for connection with MQTT Server
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_Password
	 * @return void
	 */
	public static void setMQTTPassword(Context p_Ctx, String p_Password) {
		Env.setContext(MQTT_PASSWORD, p_Password);
	}
	
	/**
	 * Get Instance for Connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_ConnectionListener
	 * @param p_Callback
	 * @param p_SubscribedTopics
	 * @param reLoad Reload Instance
	 * @return
	 * @return MQTTConnection
	 */
	public static MQTTConnection getInstance(Context p_Ctx, IMqttActionListener p_ConnectionListener, 
			String[] p_SubscribedTopics, boolean reLoad) {
		if(m_Connection == null
				|| reLoad) {
			m_Connection = new MQTTConnection(p_Ctx, p_ConnectionListener, p_SubscribedTopics);
			//	Set to false reload
			if(reLoad) {
				MQTTConnection.setIsAutomaticService(p_Ctx, false);
			}
		}
		//	Default Return
		return m_Connection;
	}
	
	/**
	 * Get Instance without topics
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_ConnectionListener
	 * @param p_Callback
	 * @param reLoad
	 * @return
	 * @return MQTTConnection
	 */
	public static MQTTConnection getInstance(Context p_Ctx, IMqttActionListener p_ConnectionListener, 
			boolean reLoad) {
		return getInstance(p_Ctx, p_ConnectionListener, null, reLoad);
	}
	
	/**
	 * Get Connection Instance without Connection Listener
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return MQTTConnection
	 */
	public static MQTTConnection getInstance(Context p_Ctx) {
		return getInstance(p_Ctx, null, null, false);
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
			//	Set Call Back
			if(m_Callback != null) {
				m_ClientLink.setCallback(m_Callback);
			}
		} else if(m_ClientLink.isConnected()) {
			return;
		} else {
			m_ClientLink.unregisterResources();
		}
		//	Connect
		m_ClientLink.connect(m_ConnectionOption, m_Ctx, m_ConnectionListener);
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
		return m_ClientLink != null && m_ClientLink.isConnected();
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
		addTopic(p_Topic);
		m_ClientLink.subscribe(p_Topic, p_QoS);
	}
	
	/**
	 * Add Topic to list
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Topic
	 * @return void
	 */
	private void addTopic(String p_Topic) {
		int currentPos = m_SubscribedTopics.indexOf(p_Topic);
		if(currentPos > -1) {
			m_SubscribedTopics.set(currentPos, p_Topic);
		} else {
			m_SubscribedTopics.add(p_Topic);
		}
		//	Set Is Subscribed Topics
		if(!isSubscribed()) {
			setIsSubscribed(true);
		}
	}
	
	/**
	 * Add Array Topics
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Topics
	 * @return void
	 */
	private void addTopic(String[] p_Topics) {
		//	Valid Null
		if(p_Topics == null) {
			return;
		}
		//	Add Topics
		for(String p_Topic : p_Topics) {
			addTopic(p_Topic);
		}
	}
	
	/**
	 * Subscribe to Topics array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Topic
	 * @param p_QoS
	 * @throws MqttSecurityException
	 * @throws MqttException
	 * @return void
	 */
	public void subscribeEx(String[] p_Topic, int[] p_QoS) throws MqttSecurityException, MqttException {
		addTopic(p_Topic);
		m_ClientLink.subscribe(p_Topic, p_QoS);
	}
	
	/**
	 * Susbcribe current topics
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_QoS
	 * @throws MqttSecurityException
	 * @throws MqttException
	 * @return void
	 */
	public void subscribeEx(int p_QoS) throws MqttSecurityException, MqttException {
		String [] currentTopics = getSubscribedTopics();
		//	Valid none
		if(currentTopics == null
				|| currentTopics.length == 0) {
			return;
		}
		int [] p_QoS_Array = new int[currentTopics.length];
		//	Fill array
		for(int i = 0; i < p_QoS_Array.length; i++) {
			p_QoS_Array[i] = p_QoS;
		}
		//	Subscribe
		subscribeEx(getSubscribedTopics(), p_QoS_Array);
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

	@Override
	public String toString() {
		return "MQTTConnection [m_Client_ID=" + m_Client_ID + ", m_Host="
				+ m_Host + ", m_Port=" + m_Port + ", m_ClientLink="
				+ m_ClientLink + ", m_ConnectionOption=" + m_ConnectionOption
				+ ", m_IsSSLConnection=" + m_IsSSLConnection
				+ ", m_ConnectionListener=" + m_ConnectionListener
				+ ", m_Callback=" + m_Callback + ", m_Ctx=" + m_Ctx
				+ ", m_IsSubscribe=" + m_IsSubscribe + ", m_SubscribedTopics="
				+ m_SubscribedTopics + "]";
	}
}
