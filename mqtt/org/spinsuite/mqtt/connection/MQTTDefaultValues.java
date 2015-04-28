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

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Mar 27, 2015, 10:40:46 PM
 *
 */
public class MQTTDefaultValues {
	
	/**	Initial Load Topic								*/
	private static final String INITIAL_LOAD_TOPIC 			= "Public-Initial-Load";
	/**	Default Prefix for ADempiere Synchronization	*/
	private static final String PRE_SYNC_TOPIC 				= "AD-Synchronization-";
	/**	Default Prefix for Request						*/
	private static final String PRE_REQUEST_TOPIC 			= "Server-Request-";
	/**	Default Values for MQTT Server					*/
	public static final String 	DEFAULT_MQTT_SERVER_NAME 	= "test.mosquitto.org";
	/**	User											*/
	public static final String 	DEFAULT_MQTT_USER 			= "";
	/**	Password										*/
	public static final String 	DEFAULT_MQTT_PASS 			= "";
	/**	Keep Alive										*/
	public static final int 	DEFAULT_MQTT_KEEP_ALIVE_INT = 60;
	/**	Port											*/
	public static final int 	DEFAULT_MQTT_PORT 			= 1883;
	/**	Alarm Time										*/
	public static final long 	DEFAULT_MQTT_ALARM_TIME 	= 1000 * 3;
	/**	Notification ID									*/
	public static final int		NOTIFICATION_ID 			= 777;
	
	/**	For Messages and Requests						*/
	/**	Type Values										*/
	public static final String TYPE_IN				= "I";
	public static final String TYPE_OUT				= "O";
	/**	Status Values									*/
	public static final String STATUS_ACCEPTED 		= "A";
	public static final String STATUS_CREATED 		= "C";
	public static final String STATUS_DELIVERED 	= "D";
	public static final String STATUS_READED 		= "R";
	public static final String STATUS_SENT 			= "S";
	public static final String STATUS_REJECT 		= "J";
	/**
	 * Get Synchronization Topic
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_User
	 * @return
	 * @return String
	 */
	public static String getSyncTopic(String p_User) {
		return getUserTopic(PRE_SYNC_TOPIC, p_User);
	}
	
	/**
	 * Get Request Topic
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_User
	 * @return
	 * @return String
	 */
	public static String getRequestTopic(String p_User) {
		return getUserTopic(PRE_REQUEST_TOPIC, p_User);
	}
	
	/**
	 * Get Initial Load Topic
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public static String getInitialLoadTopic() {
		return INITIAL_LOAD_TOPIC;
	}
	
	/**
	 * Get a Topic
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_TopicPrefix
	 * @param p_User
	 * @return
	 * @return String
	 */
	private static String getUserTopic(String p_TopicPrefix, String p_User) {
		//	Valid User
		if(p_User == null
				|| p_User.length() == 0)
			return null;
		//	Default
		return p_TopicPrefix + p_User;
	}
}
