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
package org.spinsuite.bchat.util;

import java.io.File;
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
import org.spinsuite.sync.content.SyncAcknowledgment;
import org.spinsuite.sync.content.SyncMessage_BC;
import org.spinsuite.sync.content.SyncRequest_BC;
import org.spinsuite.sync.content.SyncStatus;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.SerializerUtil;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com May 17, 2015, 12:42:46 AM
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
	
	/**	Context						*/
	private Context 					m_Ctx = null;
	/**	Instance					*/
	private static BCMessageHandle 		m_Instance = null;

	/**
	 * Get Current Instance
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
	 * Create a New In Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param request
	 * @return void
	 */
	public void newInRequest(SyncRequest_BC request) {
		newRequest(request, MQTTDefaultValues.TYPE_IN, null);
	}
	
	/**
	 * Insert a New Out Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param request
	 * @param p_Status
	 * @return void
	 */
	public void newOutRequest(SyncRequest_BC request, String p_Status) {
		newRequest(request, MQTTDefaultValues.TYPE_OUT, p_Status);
	}
	
	/**
	 * Get All Topics
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_Ctx
	 * @return
	 * @return String[]
	 */
	public String[] getTopics() {
		//	
		ArrayList<String> topics = new ArrayList<String>();
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(m_Ctx, DB.READ_ONLY);
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
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
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
	 * @param p_Type
	 * @param p_Status
	 * @return
	 * @return SyncRequest_BC[]
	 */
	public SyncRequest_BC[] getRequest(String p_Type, String p_Status) {
		//	
		ArrayList<SyncRequest_BC> requests = new ArrayList<SyncRequest_BC>();
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(m_Ctx, DB.READ_ONLY);
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
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
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
	 * @param p_SPS_BC_Request_UUID
	 * @return
	 * @return SyncRequest_BC
	 */
	public SyncRequest_BC getRequest(String p_SPS_BC_Request_UUID) {
		//	Valid 0 ID
		if(p_SPS_BC_Request_UUID == null)
			return null;
		//	
		SyncRequest_BC request = null;
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(m_Ctx, DB.READ_ONLY);
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
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
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
	 * @param request
	 * @param p_Type
	 * @param p_Status
	 * @return void
	 */
	public void newRequest(SyncRequest_BC request, String p_Type, String p_Status) {
		if(request == null) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.CONFIG, "Null request for Insert");
			return;
		}
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(m_Ctx, DB.READ_WRITE);
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
					conn.addInt(invited.getAD_User_ID());
					conn.addString(MQTTDefaultValues.STATUS_CREATED);
					conn.executeSQL();
					conn.clearParameters();
				}
			}
			//	Successful
			conn.setTransactionSuccessful();
		} catch (Exception e) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
	}
	
	/**
	 * Delete Request from IDs
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_SPS_Request_UUIDs
	 * @return void
	 */
	public void deleteRequest(String [] p_SPS_Request_UUIDs) {
		if(p_SPS_Request_UUIDs == null
				|| p_SPS_Request_UUIDs.length == 0) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.CONFIG, "Null where clause for delete");
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
			conn = DB.loadConnection(m_Ctx, DB.READ_WRITE);
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
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
	}
	
	/**
     * Send Message
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param request
     * @return void
     */
    public void sendRequest(SyncRequest_BC request) {
    	//	Valid Request
    	if(request == null) {
    		return;
    	}
		//	Save Request
		newOutRequest(request, MQTTDefaultValues.STATUS_SENDING);
		//	Send
		MQTTConnection m_Connection = MQTTConnection.getInstance(m_Ctx);
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
						String m_LocalClient_ID = MQTTConnection.getClient_ID(m_Ctx);
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
						m_Connection.publish(MQTTDefaultValues.getRequestTopic(String.valueOf(invited.getAD_User_ID())), message);
						//	Change Status
						setUserStatus(request.getSPS_BC_Request_UUID(), 
								invited.getAD_User_ID(), MQTTDefaultValues.STATUS_SENT);
					} catch (Exception e) {
						LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
					}
				}
			} else {
				LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error Sending Request (No Connected)");
			}
		} catch (MqttSecurityException e) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
		} catch (MqttException e) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
		}
    }
    
    /**
     * Change Status
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_SPS_BC_Request_UUID
     * @param p_Status
     * @return void
     */
    public void setRequestStatus(String p_SPS_BC_Request_UUID, String p_Status) {
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(m_Ctx, DB.READ_WRITE);
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
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
	}
	
    
    /**
	 * New In Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param message
	 * @return boolean
	 */
	public boolean newInMessage(SyncMessage_BC message) {
		return newMessage(message, MQTTDefaultValues.TYPE_IN, null);
	}
	
	/**
	 * New Out Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param message
	 * @param p_Status
	 * @return boolean
	 */
	public boolean newOutMessage(SyncMessage_BC message, String p_Status) {
		return newMessage(message, MQTTDefaultValues.TYPE_OUT, p_Status);
	}
	
	/**
	 * Get Message from Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_SPS_BC_Message_UUID
	 * @return
	 * @return SyncMessage_BC
	 */
	public SyncMessage_BC getMessage(int p_SPS_BC_Message_UUID) {
		//	Valid 0 ID
		if(p_SPS_BC_Message_UUID <= 0)
			return null;
		//	
		SyncMessage_BC msg = null;
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(m_Ctx, DB.READ_ONLY);
			//	Compile Query
			conn.compileQuery("SELECT "
					+ "m.SPS_BC_Message_UUID, "
					+ "m.SPS_BC_Request_UUID, "
					+ "m.AD_User_ID, "
					+ "u.Name UserName, "
					+ "m.Text, "
					+ "m.FileName "
					+ "FROM SPS_BC_Message m "
					+ "INNER JOIN AD_User u ON(u.AD_User_ID = m.AD_User_ID) "
					+ "WHERE m.SPS_BC_Message_UUID = ?");
			//	Add Parameter
			conn.addInt(p_SPS_BC_Message_UUID);
			//	Query Data
			Cursor rs = conn.querySQL();
			//	Get Header Data
			if(rs.moveToFirst()) {
				msg = new SyncMessage_BC(
						rs.getString(0), 
						null, 
						rs.getString(1), 
						rs.getInt(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5), 
						null);
				//	Get Attachment
				msg.setAttachment(getAttachment(msg.getFileName()));
				//	End
			}
		} catch (Exception e) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
		//	Default Return
		return msg;
	}
	
	/**
	 * Get Message from Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Status
	 * @param p_Type
	 * @param p_WithAttachment
	 * @return
	 * @return SyncMessage_BC[]
	 */
	public SyncMessage_BC[] getMessage(String p_Status, String p_Type, boolean p_WithAttachment) {
		//	
		ArrayList<SyncMessage_BC> msgList = new ArrayList<SyncMessage_BC>();
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(m_Ctx, DB.READ_ONLY);
			StringBuffer sql = new StringBuffer("SELECT "
					+ "m.SPS_BC_Message_UUID, "
					+ "m.SPS_BC_Request_UUID, "
					+ "m.AD_User_ID, "
					+ "u.Name UserName, "
					+ "m.Text, "
					+ "m.FileName "
					+ "FROM SPS_BC_Message m "
					+ "INNER JOIN AD_User u ON(u.AD_User_ID = m.AD_User_ID) "
					+ "WHERE m.Status = ? "
					+ "AND m.Type = ?");
			//	Compile Query
			conn.compileQuery(sql.toString());
			//	Add Parameter
			conn.addString(p_Status);
			conn.addString(p_Type);
			//	Query Data
			Cursor rs = conn.querySQL();
			//	Get Header Data
			if(rs.moveToFirst()) {
				do {
					//	
					SyncMessage_BC msg = new SyncMessage_BC(
							rs.getString(0), 
							null, 
							rs.getString(1), 
							rs.getInt(2), 
							rs.getString(3), 
							rs.getString(4), 
							rs.getString(5), 
							null);
					//	Get Attachment
					msg.setAttachment(getAttachment(msg.getFileName()));
					//	Add Request
					msgList.add(msg);
				} while(rs.moveToNext());
			}
		} catch (Exception e) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
		//	Default Return
		return msgList.toArray(new SyncMessage_BC[msgList.size()]);
	}
	
	/**
	 * Get Attachment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param p_FileName
	 * @return
	 * @return byte[]
	 */
	private byte[] getAttachment(String p_FileName) {
		//	Valid Null
		if(p_FileName == null) {
			return null;
		}
		//	Get from file
		return SerializerUtil.getFromFile(
				Env.getBC_IMG_DirectoryPathName(m_Ctx) + File.separator + p_FileName);
	}
	
	/**
	 * Create a New Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param message
	 * @param p_Type
	 * @param p_Status
	 * @return boolean
	 */
	public boolean newMessage(SyncMessage_BC message, String p_Type, String p_Status) {
		boolean ok = false;
		if(message == null) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.CONFIG, "Null message for Insert");
			return ok;
		}
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(m_Ctx, DB.READ_WRITE);
			//	Compile Query
			conn.compileQuery("INSERT INTO "
					+ "SPS_BC_Message("
					+ "AD_Client_ID, "
					+ "AD_Org_ID, "
					+ "AD_User_ID, "
					+ "Text, "
					+ "Created, "
					+ "CreatedBy, "
					+ "Updated, "
					+ "UpdatedBy, "
					+ "IsActive, "
					+ "SPS_BC_Request_UUID, "
					+ "SPS_BC_Message_UUID, "
					+ "Type, "
					+ "Status, "
					+ "FileName) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			//	Add Values
			int m_AD_Client_ID = Env.getAD_Client_ID();
			int m_AD_Org_ID = Env.getAD_Org_ID();
			//	Add ID
			if(message.getSPS_BC_Message_UUID() == null) {
				String m_SPS_BC_Message_UUID = UUID.randomUUID().toString();
				message.setSPS_BC_Message_UUID(m_SPS_BC_Message_UUID);
			}
			//	
			int m_AD_User_ID = message.getAD_User_ID();
			//	For Out
			if(p_Type.equals(MQTTDefaultValues.TYPE_OUT)) {
				m_AD_User_ID = Env.getAD_User_ID(); 
			} else if(p_Type.equals(MQTTDefaultValues.TYPE_IN)) {
				SyncRequest_BC request = getRequest(message.getSPS_BC_Request_UUID());
				if(request != null) {
					message.setSPS_BC_Request_UUID(request.getSPS_BC_Request_UUID());
				}
			}
			//	
			Date now = new Date(System.currentTimeMillis());
			if(p_Status == null) {
				p_Status = MQTTDefaultValues.STATUS_CREATED;
			}
			conn.addInt(m_AD_Client_ID);
			conn.addInt(m_AD_Org_ID);
			conn.addInt(m_AD_User_ID);
			conn.addString(message.getText());
			conn.addDateTime(now);
			conn.addInt(m_AD_User_ID);
			conn.addDateTime(now);
			conn.addInt(m_AD_User_ID);
			conn.addBoolean(true);
			conn.addString(message.getSPS_BC_Request_UUID());
			conn.addString(message.getSPS_BC_Message_UUID());
			conn.addString(p_Type);
			conn.addString(p_Status);
			conn.addString(message.getFileName());
			//	Execute
			conn.executeSQLEx();
			//	Update Header
			conn.compileQuery("UPDATE SPS_BC_Request "
					+ "SET Updated = ?, "
					+ "LastMsg = ?, "
					+ "LastFileName = ? "
					+ "WHERE SPS_BC_Request_UUID = ?");
			//	Add Parameters
			conn.addDateTime(now);
			conn.addString(message.getText());
			conn.addString(message.getFileName());
			conn.addString(message.getSPS_BC_Request_UUID());
			//	Execute
			conn.executeSQLEx();		
			//	Successful
			conn.setTransactionSuccessful();
			//	
			ok = true;
			//	
		} catch (Exception e) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
		//	Return
		return ok;
	}
	
	/**
	 * Change Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_SPS_BC_Message_UUID
	 * @param p_Status
	 * @return void
	 */
	public void setMessageStatus(String p_SPS_BC_Message_UUID, String p_Status) {
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(m_Ctx, DB.READ_WRITE);
			//	Compile Query
			conn.compileQuery("UPDATE SPS_BC_Message "
					+ "SET Status = ? "
					+ "WHERE SPS_BC_Message_UUID = ? ");
			//	Add Parameter
			conn.addString(p_Status);
			conn.addString(p_SPS_BC_Message_UUID);
			conn.executeSQL();
			//	Successful
			conn.setTransactionSuccessful();
		} catch (Exception e) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
	}
	
	/**
	 * Delete Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param request
	 * @param p_WhereClause
	 * @return void
	 */
	public void deleteMessage(SyncRequest_BC request, String p_WhereClause) {
		if(request == null) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.CONFIG, "Null request for delete");
			return;
		}
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(m_Ctx, DB.READ_WRITE);
			//	
			StringBuffer sql = new StringBuffer("DELETE "
					+ "FROM SPS_BC_Message "
					+ "WHERE SPS_BC_Request_UUID = ?");
			//	Add Where Clause
			if(p_WhereClause != null
					&& p_WhereClause.trim().length() > 0) {
				sql.append(" AND ")
					.append(p_WhereClause);
			}
			//	Compile Query
			conn.compileQuery(sql.toString());
			//	Add Values
			conn.addString(request.getSPS_BC_Request_UUID());
			conn.executeSQL();
			//	Get Last Message
			conn.compileQuery("SELECT m.Text, m.FileName, (strftime('%s', m.Updated)*1000) Updated "
					+ "FROM SPS_BC_Message m "
					+ "WHERE SPS_BC_Request_UUID = ? "
					+ "ORDER BY Updated DESC");
			//	Add Parameter
			conn.addString(request.getSPS_BC_Request_UUID());
			//	Execute
			Cursor rs = conn.querySQL();
			String m_LastText = null;
			String m_LastFileName = null;
			long m_time = 0;
			if(rs != null
					&& rs.moveToFirst()) {
				m_LastText = rs.getString(0);
				m_LastFileName = rs.getString(1);
				m_time = rs.getLong(2);
			}
			//	
			conn.compileQuery("UPDATE SPS_BC_Request "
					+ "SET Updated = ?, "
					+ "LastMsg = ?, "
					+ "LastFileName = ? "
					+ "WHERE SPS_BC_Request_UUID = ?");
			//	Add Parameters
			conn.addDateTime(new Date(m_time));
			conn.addString(m_LastText);
			conn.addString(m_LastFileName);
			conn.addString(request.getSPS_BC_Request_UUID());
			//	Execute
			conn.executeSQL();		
			//	Successful
			conn.setTransactionSuccessful();
		} catch (Exception e) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
	}
	
	/**
     * Send Message
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param message
     * @return void
     */
    public void sendMsg(SyncMessage_BC message) {
    	//	Valid Message
    	if(message == null) {
    		return;
    	}
		//	Save Message
		newOutMessage(message, MQTTDefaultValues.STATUS_SENDING);
		//	Send
		MQTTConnection m_Connection = MQTTConnection.getInstance(m_Ctx);
		boolean sent = false;
		if(m_Connection.connect()) {
			try {
				//	Set Client ID
				message.setLocalClient_ID(MQTTConnection.getClient_ID(m_Ctx));
				//	Get Request for Topic
				SyncRequest_BC request = getRequest(message.getSPS_BC_Request_UUID());
				//	Valid Request
				if(request == null) {
					return;
				}
				//	
				byte[] payload = SerializerUtil.serializeObjectEx(message);
				MqttMessage msg = new MqttMessage(payload);
				msg.setQos(MQTTConnection.EXACTLY_ONCE_2);
				msg.setRetained(true);
				m_Connection.publishEx(request.getTopicName(), msg);
				sent = true;
			} catch (Exception e) {
				LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
				//	Try Send
				processMessageThread();
			}
		} else {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error (No Connected)");
		}
		//	
		if(!sent) {
			setMessageStatus(message.getSPS_BC_Message_UUID(), MQTTDefaultValues.STATUS_CREATED);
		}
    }
    
    /**
     * 
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_SPS_BC_Request_UUID
     * @param p_Status
     * @return void
     */
    public void sendStatus(String p_SPS_BC_Request_UUID, String p_Status) {
    	//	Valid Message
    	if(p_Status == null) {
    		return;
    	}
		//	Send
		MQTTConnection m_Connection = MQTTConnection.getInstance(m_Ctx);
		boolean sent = false;
		if(m_Connection.connect()) {
			try {
				//	Set Client ID
				SyncStatus status = new SyncStatus(MQTTConnection.getClient_ID(m_Ctx));
				//	Get Request for Topic
				status.setSPS_BC_Request_UUID(p_SPS_BC_Request_UUID);
				//	Status
				status.setStatus(p_Status);
				status.setAD_User_ID(Env.getAD_User_ID(m_Ctx));
				//	
				byte[] payload = SerializerUtil.serializeObjectEx(status);
				MqttMessage msg = new MqttMessage(payload);
				msg.setQos(MQTTConnection.EXACTLY_ONCE_2);
				msg.setRetained(true);
				m_Connection.publishEx(MQTTDefaultValues.getUserStatusTopic(), msg);
				sent = true;
			} catch (Exception e) {
				LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
				//	Try Send
				processMessageThread();
			}
		} else {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error (No Connected)");
		}
		//	
		if(!sent) {
			
		}
    }
    
    /**
     * Send Acknowledgement
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_SPS_BC_Request_UUID
     * @param p_SPS_BC_Message_UUID
     * @param p_Topic
     * @param p_Status
     * @return void
     */
    public void sendStatusAcknowledgment(String p_SPS_BC_Request_UUID, String p_SPS_BC_Message_UUID, 
    		String p_Topic, String p_Status) {
    	//	Send
		MQTTConnection m_Connection = MQTTConnection.getInstance(m_Ctx);
		if(m_Connection.connect()) {
			try {
				SyncAcknowledgment acknowledgment = new SyncAcknowledgment(MQTTConnection.getClient_ID(m_Ctx));
				//	Set Request Identifier
				acknowledgment.setSPS_BC_Request_UUID(p_SPS_BC_Request_UUID);
				//	Set Message Identifier
				acknowledgment.setSPS_BC_Message_UUID(p_SPS_BC_Message_UUID);
				//	Set User Identifier
				acknowledgment.setAD_User_ID(Env.getAD_User_ID(m_Ctx));
				//	Set status
				acknowledgment.setStatus(p_Status);
				if(p_Topic == null) {
					SyncRequest_BC request = getRequest(p_SPS_BC_Request_UUID);
					//	Valid Request
					if(request == null) {
						return;
					}
					//	Get Topic
					p_Topic = request.getTopicName();
				}
				//	
				byte[] payload = SerializerUtil.serializeObjectEx(acknowledgment);
				MqttMessage msg = new MqttMessage(payload);
				msg.setQos(MQTTConnection.EXACTLY_ONCE_2);
				msg.setRetained(true);
				m_Connection.publishEx(p_Topic, msg);
			} catch (Exception e) {
				LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
			}
		} else {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error (No Connected)");
		}
    }
    
    /**
     * Send Acknowledgement With Message
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param message
     * @param p_Topic
     * @param p_Status
     * @return void
     */
    public void sendStatusAcknowledgment(SyncMessage_BC message, String p_Topic, String p_Status) {
    	//	Valid Message
    	if(message == null) {
    		return;
    	}
    	//	Send
    	sendStatusAcknowledgment(message.getSPS_BC_Request_UUID(), 
    			message.getSPS_BC_Message_UUID(), p_Topic, p_Status);
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
		sendOpenNotifications(MQTTDefaultValues.STATUS_FN_DELIVERED);
		//	For Readed
		sendOpenNotifications(MQTTDefaultValues.STATUS_FN_READED);
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
			SyncMessage_BC msgList[] = getMessage(
					MQTTDefaultValues.STATUS_CREATED, 
					MQTTDefaultValues.TYPE_OUT, true);
			//	
			String m_LocalClient_ID = MQTTConnection.getClient_ID(m_Ctx);
			for(SyncMessage_BC msgForSend : msgList) {
				try {
					//	Set Client ID
					msgForSend.setLocalClient_ID(m_LocalClient_ID);
					//	Get Request for Topic
					SyncRequest_BC request = getRequest(msgForSend.getSPS_BC_Request_UUID());
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
	 * @param p_CurrentStatus
	 * @return
	 * @return boolean
	 */
	public boolean sendOpenNotifications(String p_CurrentStatus) {
		//	
		if(p_CurrentStatus == null) {
			return false;
		}
		//	Verify Connection
		MQTTConnection m_Connection = MQTTConnection.getInstance(m_Ctx);
		if(m_Connection.connect()) {			
			SyncMessage_BC msgList[] = getMessage( 
					p_CurrentStatus, 
					MQTTDefaultValues.TYPE_IN, true);
			//	Iterate
			for(SyncMessage_BC msgReceipt : msgList) {
				try {
					//	Get Request for Topic
					SyncRequest_BC request = getRequest(msgReceipt.getSPS_BC_Request_UUID());
					//	Valid Request
					if(request == null)
						continue;
					//	New Status
					String newStatus = null;
					if(p_CurrentStatus.equals(MQTTDefaultValues.STATUS_FN_DELIVERED)) {
						newStatus = MQTTDefaultValues.STATUS_DELIVERED;
					} else if(p_CurrentStatus.equals(MQTTDefaultValues.STATUS_FN_READED)) {
						newStatus = MQTTDefaultValues.STATUS_READED;
					}
					//	Set Status
					sendStatusAcknowledgment(msgReceipt, 
							request.getTopicName(), newStatus);
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
			SyncRequest_BC requestList[] = getRequest(MQTTDefaultValues.TYPE_OUT, MQTTDefaultValues.STATUS_CREATED);
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
							m_Connection.publish(MQTTDefaultValues.getRequestTopic(String.valueOf(invited.getAD_User_ID())), message);
							//	Change Status
							setUserStatus(request.getSPS_BC_Request_UUID(), 
									invited.getAD_User_ID(), MQTTDefaultValues.STATUS_SENT);
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
	
	/************************************************************************************************
	 * For User                                                                                     *
	 ************************************************************************************************/
	
	/**
	 * Change Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_SPS_BC_Request_UUID
	 * @param p_Status
	 * @return void
	 */
	public void setUserStatus(String p_SPS_BC_Request_UUID, int p_AD_User_ID, String p_Status) {
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(m_Ctx, DB.READ_WRITE);
			//	Compile Query
			conn.compileQuery("UPDATE SPS_BC_Request_User "
					+ "SET Status = ? "
					+ "WHERE AD_User_ID = ?" 
					+ (p_SPS_BC_Request_UUID != null
									? " AND SPS_BC_Request_UUID = ?"
											:""));
			//	Add Parameter
			conn.addString(p_Status);
			if(p_SPS_BC_Request_UUID != null) {
				conn.addString(p_SPS_BC_Request_UUID);
			}
			conn.addInt(p_AD_User_ID);
			conn.executeSQL();
			//	Successful
			conn.setTransactionSuccessful();
		} catch (Exception e) {
			LogM.log(m_Ctx, BCMessageHandle.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
	}
}
