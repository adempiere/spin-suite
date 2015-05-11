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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.spinsuite.base.DB;
import org.spinsuite.mqtt.connection.MQTTConnection;
import org.spinsuite.mqtt.connection.MQTTDefaultValues;
import org.spinsuite.sync.content.SyncAcknowledgment;
import org.spinsuite.sync.content.SyncMessage_BC;
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
public class SPS_BC_Message {
	
	/**
	 * New In Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param message
	 * @return boolean
	 */
	public static boolean newInMessage(Context ctx, SyncMessage_BC message) {
		return newMessage(ctx, message, MQTTDefaultValues.TYPE_IN, null);
	}
	
	/**
	 * New Out Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param message
	 * @param p_Status
	 * @return boolean
	 */
	public static boolean newOutMessage(Context ctx, SyncMessage_BC message, String p_Status) {
		return newMessage(ctx, message, MQTTDefaultValues.TYPE_OUT, p_Status);
	}
	
	/**
	 * Get Message from Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param p_SPS_BC_Message_UUID
	 * @return
	 * @return SyncMessage_BC
	 */
	public static SyncMessage_BC getMessage(Context ctx, int p_SPS_BC_Message_UUID) {
		//	Valid 0 ID
		if(p_SPS_BC_Message_UUID <= 0)
			return null;
		//	
		SyncMessage_BC msg = null;
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_ONLY);
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
				msg.setAttachment(getAttachment(ctx, msg.getFileName()));
				//	End
			}
		} catch (Exception e) {
			LogM.log(ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
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
	 * @param ctx
	 * @param p_Status
	 * @param p_Type
	 * @param p_WithAttachment
	 * @return
	 * @return SyncMessage_BC[]
	 */
	public static SyncMessage_BC[] getMessage(Context ctx, String p_Status, String p_Type, boolean p_WithAttachment) {
		//	
		ArrayList<SyncMessage_BC> msgList = new ArrayList<SyncMessage_BC>();
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_ONLY);
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
					msg.setAttachment(getAttachment(ctx, msg.getFileName()));
					//	Add Request
					msgList.add(msg);
				} while(rs.moveToNext());
			}
		} catch (Exception e) {
			LogM.log(ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
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
	private static byte[] getAttachment(Context ctx, String p_FileName) {
		//	Valid Null
		if(p_FileName == null) {
			return null;
		}
		//	Get from file
		return SerializerUtil.getFromFile(
				Env.getBC_IMG_DirectoryPathName(ctx) + File.separator + p_FileName);
	}
	
	/**
	 * Create a New Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param message
	 * @param p_Type
	 * @param p_Status
	 * @return boolean
	 */
	public static boolean newMessage(Context ctx, SyncMessage_BC message, String p_Type, String p_Status) {
		boolean ok = false;
		if(message == null) {
			LogM.log(ctx, SPS_BC_Message.class, Level.CONFIG, "Null message for Insert");
			return ok;
		}
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_WRITE);
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
				SyncRequest_BC request = SPS_BC_Request.getRequest(ctx, message.getSPS_BC_Request_UUID());
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
			LogM.log(ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
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
	 * @param ctx
	 * @param p_SPS_BC_Message_UUID
	 * @param p_Status
	 * @return void
	 */
	public static void setStatus(Context ctx, String p_SPS_BC_Message_UUID, String p_Status) {
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_WRITE);
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
			LogM.log(ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
	}
	
	/**
	 * Delete Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param request
	 * @param p_WhereClause
	 * @return void
	 */
	public static void deleteMessage(Context ctx, SyncRequest_BC request, String p_WhereClause) {
		if(request == null) {
			LogM.log(ctx, SPS_BC_Message.class, Level.CONFIG, "Null request for delete");
			return;
		}
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_WRITE);
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
     * @param message
     * @return void
     */
    public static void sendMsg(Context p_ctx, SyncMessage_BC message) {
    	//	Valid Message
    	if(message == null) {
    		return;
    	}
		//	Save Message
		newOutMessage(p_ctx, message, MQTTDefaultValues.STATUS_SENDING);
		//	Send
		MQTTConnection m_Connection = MQTTConnection.getInstance(p_ctx);
		boolean sent = false;
		if(m_Connection.connect()) {
			try {
				//	Set Client ID
				message.setLocalClient_ID(MQTTConnection.getClient_ID(p_ctx));
				//	Get Request for Topic
				SyncRequest_BC request = SPS_BC_Request.getRequest(p_ctx, message.getSPS_BC_Request_UUID());
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
				LogM.log(p_ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
				//	Try Send
				BCMessageHandle.getInstance(p_ctx).processMessageThread();
			}
		} else {
			LogM.log(p_ctx, SPS_BC_Message.class, Level.SEVERE, "Error (No Connected)");
		}
		//	
		if(!sent) {
			SPS_BC_Message.setStatus(p_ctx, 
					message.getSPS_BC_Message_UUID(), MQTTDefaultValues.STATUS_CREATED);
		}
    }
    
    /**
     * Send Acknowledgement
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_ctx
     * @param message
     * @param p_Topic
     * @return void
     */
    public static void sendAcknowledgment(Context p_ctx, SyncMessage_BC message, String p_Topic) {
    	//	Valid Message
    	if(message == null) {
    		return;
    	}
    	//	Send
		MQTTConnection m_Connection = MQTTConnection.getInstance(p_ctx);
		if(m_Connection.connect()) {
			try {
				SyncAcknowledgment acknowledgment = new SyncAcknowledgment(MQTTConnection.getClient_ID(p_ctx));
				//	Set Request Identifier
				acknowledgment.setSPS_BC_Request_UUID(message.getSPS_BC_Request_UUID());
				//	Set Message Identifier
				acknowledgment.setSPS_BC_Message_UUID(message.getSPS_BC_Message_UUID());
				//	Set User Identifier
				acknowledgment.setAD_User_ID(Env.getAD_User_ID(p_ctx));
				//	
				byte[] payload = SerializerUtil.serializeObjectEx(acknowledgment);
				MqttMessage msg = new MqttMessage(payload);
				msg.setQos(MQTTConnection.EXACTLY_ONCE_2);
				msg.setRetained(true);
				m_Connection.publishEx(p_Topic, msg);
			} catch (Exception e) {
				LogM.log(p_ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
			}
		} else {
			LogM.log(p_ctx, SPS_BC_Message.class, Level.SEVERE, "Error (No Connected)");
		}
    }
}
