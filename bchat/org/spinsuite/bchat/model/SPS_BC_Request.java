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

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.spinsuite.base.DB;
import org.spinsuite.mqtt.connection.MQTTConnection;
import org.spinsuite.mqtt.connection.MQTTDefaultValues;
import org.spinsuite.sync.content.Invited;
import org.spinsuite.sync.content.SyncRequest_BC;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.SerializerUtil;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 5, 2015, 9:34:06 AM
 *
 */
public class SPS_BC_Request {
	
	/**
	 * Create a New In Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param request
	 * @return void
	 */
	public static void newInRequest(Context ctx, SyncRequest_BC request) {
		newRequest(ctx, request, MQTTDefaultValues.TYPE_IN, null);
	}
	
	/**
	 * Insert a New Out Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param request
	 * @param p_Status
	 * @return void
	 */
	public static void newOutRequest(Context ctx, SyncRequest_BC request, String p_Status) {
		newRequest(ctx, request, MQTTDefaultValues.TYPE_OUT, p_Status);
	}
	
	/**
	 * Get All Topics
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @return
	 * @return String[]
	 */
	public static String[] getTopics(Context ctx) {
		//	
		ArrayList<String> topics = new ArrayList<String>();
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_ONLY);
			//	Compile Query
			conn.compileQuery("SELECT r.Topic "
					+ "FROM SPS_BC_Request r "
					+ "WHERE r.IsActive = ? ");
			//	Add Parameter
			conn.addBoolean(true);
			//	Query Data
			Cursor rs = conn.querySQL();
			//	Get Header Data
			if(rs.moveToFirst()) {
				do {
					//	
					topics.add(rs.getString(0));
				} while(rs.moveToNext());
			}
		} catch (Exception e) {
			LogM.log(ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
		//	Default Return
		return topics.toArray(new String[topics.size()]);
	}
	
	/**
	 * Get Request with Type and Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param p_Type
	 * @param p_Status
	 * @return
	 * @return SyncRequest_BC[]
	 */
	public static SyncRequest_BC[] getRequest(Context ctx, String p_Type, String p_Status) {
		//	
		ArrayList<SyncRequest_BC> requests = new ArrayList<SyncRequest_BC>();
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_ONLY);
			//	Compile Query
			conn.compileQuery("SELECT "
					+ "r.SPS_BC_Request_UUID, "
					+ "r.Topic, "
					+ "r.Name, "
					+ "r.LastMsg, "
					+ "r.LastFileName, "
					+ "r.IsGroup, "
					+ "ru.AD_User_ID, "
					+ "ru.Status "
					+ "FROM SPS_BC_Request r "
					+ "LEFT JOIN SPS_BC_Request_User ru ON(ru.SPS_BC_Request_UUID = r.SPS_BC_Request_UUID) "
					+ "WHERE r.Type = ? "
					+ "AND ru.Status = ? "
					+ "ORDER BY r.SPS_BC_Request_UUID, ru.Updated");
			//	Add Parameter
			conn.addString(p_Type);
			conn.addString(p_Status);
			//	Query Data
			Cursor rs = conn.querySQL();
			//	Get Header Data
			if(rs.moveToFirst()) {
				do {
					//	
					SyncRequest_BC request = new SyncRequest_BC(
							rs.getString(0), 
							null, 
							rs.getString(1), 
							rs.getString(2), 
							rs.getString(3),
							rs.getString(4), 
							(rs.getString(5) != null 
								&& rs.getString(5).equals("Y")));
					//	Add Users
					do {
						String currentRequest_UUID = rs.getString(0);
						//	Verify if is other request
						if(request.getSPS_BC_Request_UUID() != null
								&& currentRequest_UUID != null
								&& request.getSPS_BC_Request_UUID().equals(currentRequest_UUID)) {
							break;
						}
						//	
						request.addUser(new Invited(rs.getInt(7), rs.getString(8)));
					} while(rs.moveToNext());
					//	Add Request
					requests.add(request);
				} while(rs.moveToNext());
			}
		} catch (Exception e) {
			LogM.log(ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
		//	Default Return
		return requests.toArray(new SyncRequest_BC[requests.size()]);
	}
	
	/**
	 * Get Sync Request for Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param p_SPS_BC_Request_UUID
	 * @return
	 * @return SyncRequest_BC
	 */
	public static SyncRequest_BC getRequest(Context ctx, String p_SPS_BC_Request_UUID) {
		//	Valid 0 ID
		if(p_SPS_BC_Request_UUID == null)
			return null;
		//	
		SyncRequest_BC request = null;
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_ONLY);
			//	Compile Query
			conn.compileQuery("SELECT "
					+ "r.SPS_BC_Request_UUID, "
					+ "r.Topic, "
					+ "r.Name, "
					+ "r.LastMsg, "
					+ "r.LastFileName, "
					+ "r.IsGroup "
					+ "FROM SPS_BC_Request r "
					+ "INNER JOIN AD_User u ON(u.AD_User_ID = r.AD_User_ID) "
					+ "WHERE r.SPS_BC_Request_UUID = ?");
			//	Add Parameter
			conn.addString(p_SPS_BC_Request_UUID);
			//	Query Data
			Cursor rs = conn.querySQL();
			//	Get Header Data
			if(rs.moveToFirst()) {
				request = new SyncRequest_BC(
						rs.getString(0), 
						null, 
						rs.getString(1), 
						rs.getString(2), 
						rs.getString(3),
						rs.getString(4), 
						(rs.getString(5) != null 
							&& rs.getString(5).equals("Y")));
				//	Query for Lines
				conn.compileQuery("SELECT "
						+ "ru.AD_User_ID, "
						+ "ru.Status "
						+ "FROM SPS_BC_Request_User ru "
						+ "WHERE ru.SPS_BC_Request_UUID = ?");
				//	Add Parameter
				conn.addString(request.getSPS_BC_Request_UUID());
				//	Query Data
				rs = conn.querySQL();
				if(rs.moveToFirst()) {
					do {
						request.addUser(new Invited(rs.getInt(0), rs.getString(1)));
					} while(rs.moveToNext());
				}
				//	End
			}
		} catch (Exception e) {
			LogM.log(ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
		//	Default Return
		return request;
	}
	
	/**
	 * Insert a new Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param request
	 * @param p_Type
	 * @param p_Status
	 * @return void
	 */
	public static void newRequest(Context ctx, SyncRequest_BC request, String p_Type, String p_Status) {
		if(request == null) {
			LogM.log(ctx, SPS_BC_Request.class, Level.CONFIG, "Null request for Insert");
			return;
		}
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_WRITE);
			//	Compile Query
			conn.compileQuery("INSERT INTO "
					+ "SPS_BC_Request("
					+ "AD_Client_ID, "
					+ "AD_Org_ID, "
					+ "AD_User_ID, "
					+ "Name, "
					+ "Created, "
					+ "CreatedBy, "
					+ "Updated, "
					+ "UpdatedBy, "
					+ "IsActive, "
					+ "SPS_BC_Request_UUID, "
					+ "Topic, "
					+ "Type, "
					+ "IsGroup) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			//	Add Values
			int m_AD_Client_ID = Env.getAD_Client_ID();
			int m_AD_Org_ID = Env.getAD_Org_ID();
			//	Add Topic
			if(request.getTopicName() == null) {
				request.setTopicName(UUID.randomUUID().toString());
			}
			int m_AD_User_ID = Env.getAD_User_ID();
			//	Set ID
			if(request.getSPS_BC_Request_UUID() == null) {
				String m_SPS_BC_Request_UUID = UUID.randomUUID().toString();
				request.setSPS_BC_Request_UUID(m_SPS_BC_Request_UUID);
			}
			//	Set Status
			if(p_Status == null) {
				p_Status = MQTTDefaultValues.STATUS_CREATED;
			}
			//	
			Date now = new Date(System.currentTimeMillis());
			conn.addInt(m_AD_Client_ID);
			conn.addInt(m_AD_Org_ID);
			conn.addInt(m_AD_User_ID);
			conn.addString(request.getName());
			conn.addDateTime(now);
			conn.addInt(m_AD_User_ID);
			conn.addDateTime(now);
			conn.addInt(m_AD_User_ID);
			conn.addBoolean(true);
			conn.addString(request.getSPS_BC_Request_UUID());
			conn.addString(request.getTopicName());
			conn.addString(p_Type);
			conn.addBoolean(request.isGroup());
			//	Execute
			conn.executeSQL();
			//	Add Child or Request Users
			if(request.getIvitedQty() > 0) {
				conn.compileQuery("INSERT INTO "
						+ "SPS_BC_Request_User("
						+ "AD_Client_ID, "
						+ "AD_Org_ID, "
						+ "Created, "
						+ "CreatedBy, "
						+ "Updated, "
						+ "UpdatedBy, "
						+ "IsActive, "
						+ "SPS_BC_Request_UUID, "
						+ "AD_User_ID, "
						+ "Status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				//	Add Users
				for(Invited invited : request.getUsers()) {
					conn.addInt(m_AD_Client_ID);
					conn.addInt(m_AD_Org_ID);
					conn.addDateTime(now);
					conn.addInt(m_AD_User_ID);
					conn.addDateTime(now);
					conn.addInt(m_AD_User_ID);
					conn.addBoolean(true);
					conn.addString(request.getSPS_BC_Request_UUID());
					conn.addInt(invited.getAD_USer_ID());
					conn.addString(MQTTDefaultValues.STATUS_CREATED);
					conn.executeSQL();
					conn.clearParameters();
				}
			}
			//	Successful
			conn.setTransactionSuccessful();
		} catch (Exception e) {
			LogM.log(ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
	}
	
	/**
	 * Delete Request from IDs
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param p_SPS_Request_UUIDs
	 * @return void
	 */
	public static void deleteRequest(Context ctx, String [] p_SPS_Request_UUIDs) {
		if(p_SPS_Request_UUIDs == null
				|| p_SPS_Request_UUIDs.length == 0) {
			LogM.log(ctx, SPS_BC_Request.class, Level.CONFIG, "Null where clause for delete");
			return;
		}
		//	Connection
		DB conn = null;
		try {
			//	Create IN
			StringBuffer inClause = new StringBuffer("WHERE SPS_BC_Request_UUID IN(");
			boolean first = true;
			//	Iterate
			for(String id : p_SPS_Request_UUIDs) {
				if(!first) {
					inClause.append(", ");
				}
				//	Add
				inClause.append("'").append(id).append("'");
				//	Change First
				if(first) {
					first = false;
				}
			}
			//	Add Last
			inClause.append(")");
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_WRITE);
			//	Compile Query
			conn.compileQuery("DELETE "
					+ "FROM SPS_BC_Message " + inClause.toString());
			//	Delete
			conn.executeSQL();
			//	Compile Query
			conn.compileQuery("DELETE "
					+ "FROM SPS_BC_Request_User " + inClause.toString());
			//	Delete
			conn.executeSQL();
			//	Delete Request
			conn.compileQuery("DELETE FROM SPS_BC_Request " + inClause.toString());
			//	Execute
			conn.executeSQL();
			//	Successful
			conn.setTransactionSuccessful();
		} catch (Exception e) {
			LogM.log(ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
	}
	
	/**
     * Send Message
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_ctx
     * @param request
     * @return void
     */
    public static void sendRequest(Context p_ctx, SyncRequest_BC request) {
    	//	Valid Request
    	if(request == null) {
    		return;
    	}
		//	Save Request
		newOutRequest(p_ctx, request, MQTTDefaultValues.STATUS_SENDING);
		//	Send
		MQTTConnection m_Connection = MQTTConnection.getInstance(p_ctx);
		try {
			if(m_Connection.connect()) {
				m_Connection.subscribeEx(request.getTopicName(), MQTTConnection.EXACTLY_ONCE_2);
				//	Send Request
				for(Invited invited : request.getUsers()) {
					//	
					if(invited.getStatus() == null
							|| !invited.getStatus().equals(MQTTDefaultValues.STATUS_CREATED))
						continue;
					//	
					try {
						String m_LocalClient_ID = MQTTConnection.getClient_ID(p_ctx);
						request.setLocalClient_ID(m_LocalClient_ID);
						//	Set User Name
						if(!request.isGroup()) {
							request.setName(Env.getContext("#AD_User_Name"));
						}
						//	
						byte[] msg = SerializerUtil.serializeObjectEx(request);
						MqttMessage message = new MqttMessage(msg);
						message.setQos(MQTTConnection.EXACTLY_ONCE_2);
						message.setRetained(true);
						m_Connection.publish(MQTTDefaultValues.getRequestTopic(String.valueOf(invited.getAD_USer_ID())), message);
						//	Change Status
						SPS_BC_Request_User.setStatus(p_ctx, request.getSPS_BC_Request_UUID(), 
								invited.getAD_USer_ID(), MQTTDefaultValues.STATUS_SENT);
					} catch (Exception e) {
						LogM.log(p_ctx, SPS_BC_Request.class, Level.SEVERE, "Error", e);
					}
				}
			} else {
				LogM.log(p_ctx, SPS_BC_Request.class, Level.SEVERE, "Error Sending Request (No Connected)");
			}
		} catch (MqttSecurityException e) {
			LogM.log(p_ctx, SPS_BC_Request.class, Level.SEVERE, "Error", e);
		} catch (MqttException e) {
			LogM.log(p_ctx, SPS_BC_Request.class, Level.SEVERE, "Error", e);
		}
    }
    
    /**
     * Change Status
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param ctx
     * @param p_SPS_BC_Request_UUID
     * @param p_Status
     * @return void
     */
    public static void setStatus(Context ctx, String p_SPS_BC_Request_UUID, String p_Status) {
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_WRITE);
			//	Compile Query
			conn.compileQuery("UPDATE SPS_BC_Request "
					+ "SET Status = ? "
					+ "WHERE SPS_BC_Request_UUID = ? ");
			//	Add Parameter
			conn.addString(p_Status);
			conn.addString(p_SPS_BC_Request_UUID);
			conn.executeSQL();
			//	Successful
			conn.setTransactionSuccessful();
		} catch (Exception e) {
			LogM.log(ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
	}
}