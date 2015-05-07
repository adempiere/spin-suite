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
import java.util.Random;
import java.util.logging.Level;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.spinsuite.base.DB;
import org.spinsuite.mqtt.connection.MQTTConnection;
import org.spinsuite.mqtt.connection.MQTTDefaultValues;
import org.spinsuite.sync.content.SyncMessage;
import org.spinsuite.sync.content.SyncRequest;
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
	public static boolean newInMessage(Context ctx, SyncMessage message) {
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
	public static boolean newOutMessage(Context ctx, SyncMessage message, String p_Status) {
		return newMessage(ctx, message, MQTTDefaultValues.TYPE_OUT, p_Status);
	}
	
	/**
	 * Get Message from Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param p_SPS_BC_Message_ID
	 * @return
	 * @return SyncMessage
	 */
	public static SyncMessage getMessage(Context ctx, int p_SPS_BC_Message_ID) {
		//	Valid 0 ID
		if(p_SPS_BC_Message_ID <= 0)
			return null;
		//	
		SyncMessage message = null;
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_ONLY);
			//	Compile Query
			conn.compileQuery("SELECT "
					+ "m.Text, "
					+ "m.FileName, "
					+ "m.SPS_BC_Request_ID, "
					+ "m.AD_User_ID, "
					+ "u.Name, "
					+ "m.Topic "
					+ "FROM SPS_BC_Message m "
					+ "INNER JOIN AD_User u ON(u.AD_User_ID = m.AD_User_ID) "
					+ "WHERE m.SPS_BC_Message_ID = ?");
			//	Add Parameter
			conn.addInt(p_SPS_BC_Message_ID);
			//	Query Data
			Cursor rs = conn.querySQL();
			//	Get Header Data
			if(rs.moveToFirst()) {
				message = new SyncMessage(
						null, 
						rs.getString(0), 
						rs.getString(1), 
						null, 
						rs.getInt(2), 
						rs.getInt(3), 
						rs.getString(4), 
						rs.getString(5));
				//	End
			}
		} catch (Exception e) {
			LogM.log(ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
		//	Default Return
		return message;
	}
	
	/**
	 * Get Message from Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param p_Status
	 * @param p_Type
	 * @param p_WhereClause
	 * @param p_WithAttachment
	 * @return
	 * @return SyncMessage[]
	 */
	public static SyncMessage[] getMessage(Context ctx, String p_Status, String p_Type, String p_WhereClause, boolean p_WithAttachment) {
		//	
		ArrayList<SyncMessage> msgs = new ArrayList<SyncMessage>();
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_ONLY);
			StringBuffer sql = new StringBuffer("SELECT "
					+ "SPS_BC_Message.SPS_BC_Request_ID, "
					+ "SPS_BC_Message.SPS_BC_Message_ID, "
					+ "SPS_BC_Message.AD_User_ID, "
					+ "SPS_BC_Message.Text, "
					+ "u.Name UserName, "
					+ "SPS_BC_Message.FileName, "
					+ "SPS_BC_Message.Topic "
					+ "FROM SPS_BC_Message "
					+ "INNER JOIN AD_User u ON(u.AD_User_ID = SPS_BC_Message.AD_User_ID) "
					+ "WHERE SPS_BC_Message.Status = ? "
					+ "AND SPS_BC_Message.Type = ?");
			//	Add Where Clause
			if(p_WhereClause != null
					&& p_WhereClause.trim().length() > 0) {
				sql.append(" AND ")
					.append(p_WhereClause);
			}
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
					SyncMessage msg = new SyncMessage(null);
					msg.setSPS_BC_Request_ID(rs.getInt(0));
					msg.setSPS_BC_Message_ID(rs.getInt(1));
					msg.setAD_User_ID(rs.getInt(2));
					msg.setText(rs.getString(3));
					msg.setUserName(rs.getString(4));
					msg.setFileName(rs.getString(5));
					msg.setTopicName(rs.getString(6));
					//	Get Attachment
					msg.setAttachment(getAttachment(ctx, msg.getFileName()));
					//	Add Request
					msgs.add(msg);
				} while(rs.moveToNext());
			}
		} catch (Exception e) {
			LogM.log(ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
		} finally {
			//	End Transaction
			DB.closeConnection(conn);
		}
		//	Default Return
		return msgs.toArray(new SyncMessage[msgs.size()]);
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
	public static boolean newMessage(Context ctx, SyncMessage message, String p_Type, String p_Status) {
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
					+ "SPS_BC_Request_ID, "
					+ "SPS_BC_Message_ID, "
					+ "Type, "
					+ "Status, "
					+ "FileName, "
					+ "Topic) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			//	Add Values
			int m_AD_Client_ID = Env.getAD_Client_ID();
			int m_AD_Org_ID = Env.getAD_Org_ID();
			int m_SPS_BC_Message_ID = new Random().nextInt();
			int m_AD_User_ID = message.getAD_User_ID();
			String m_Topic = message.getTopicName();
			message.setSPS_BC_Message_ID(m_SPS_BC_Message_ID);
			//	For Out
			if(p_Type.equals(MQTTDefaultValues.TYPE_OUT)) {
				m_AD_User_ID = Env.getAD_User_ID(); 
			} else if(p_Type.equals(MQTTDefaultValues.TYPE_IN)) {
				SyncRequest request = SPS_BC_Request.getRequest(ctx, message.getTopicName());
				if(request != null) {
					message.setSPS_BC_Request_ID(request.getSPS_BC_Request_ID());
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
			conn.addInt(message.getSPS_BC_Request_ID());
			conn.addInt(message.getSPS_BC_Message_ID());
			conn.addString(p_Type);
			conn.addString(p_Status);
			conn.addString(message.getFileName());
			conn.addString(m_Topic);
			//	Execute
			conn.executeSQLEx();
			//	Update Header
			conn.compileQuery("UPDATE SPS_BC_Request "
					+ "SET Updated = ?, "
					+ "LastMsg = ?, "
					+ "LastFileName = ? "
					+ "WHERE SPS_BC_Request_ID = ?");
			//	Add Parameters
			conn.addDateTime(now);
			conn.addString(message.getText());
			conn.addString(message.getFileName());
			conn.addInt(message.getSPS_BC_Request_ID());
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
	 * @param p_SPS_BC_Message_ID
	 * @param p_Status
	 * @return void
	 */
	public static void setStatus(Context ctx, int p_SPS_BC_Message_ID, String p_Status) {
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_WRITE);
			//	Compile Query
			conn.compileQuery("UPDATE SPS_BC_Message "
					+ "SET Status = ? "
					+ "WHERE SPS_BC_Message_ID = ? ");
			//	Add Parameter
			conn.addString(p_Status);
			conn.addInt(p_SPS_BC_Message_ID);
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
	public static void deleteMessage(Context ctx, SyncRequest request, String p_WhereClause) {
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
					+ "WHERE SPS_BC_Request_ID = ?");
			//	Add Where Clause
			if(p_WhereClause != null
					&& p_WhereClause.trim().length() > 0) {
				sql.append(" AND ")
					.append(p_WhereClause);
			}
			//	Compile Query
			conn.compileQuery(sql.toString());
			//	Add Values
			conn.addInt(request.getSPS_BC_Request_ID());
			conn.executeSQL();
			//	Get Last Message
			conn.compileQuery("SELECT m.Text, m.FileName, (strftime('%s', m.Updated)*1000) Updated "
					+ "FROM SPS_BC_Message m "
					+ "WHERE SPS_BC_Request_ID = ? "
					+ "ORDER BY Updated DESC");
			//	Add Parameter
			conn.addInt(request.getSPS_BC_Request_ID());
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
					+ "WHERE SPS_BC_Request_ID = ?");
			//	Add Parameters
			conn.addDateTime(new Date(m_time));
			conn.addString(m_LastText);
			conn.addString(m_LastFileName);
			conn.addInt(request.getSPS_BC_Request_ID());
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
    public static void sendMsg(Context p_ctx, SyncMessage message) {
    	//	Valid Message
    	if(message == null) {
    		return;
    	}
		//	Save Message
		newOutMessage(p_ctx, message, MQTTDefaultValues.STATUS_SENDING);
		//	Send
		MQTTConnection m_Connection = MQTTConnection.getInstance(p_ctx);
		if(m_Connection.isConnected()) {
			try {
				//	Set Client ID
				message.setLocalClient_ID(MQTTConnection.getClient_ID(p_ctx));
				//	Get Request for Topic
				SyncRequest request = SPS_BC_Request.getRequest(p_ctx, message.getTopicName());
				//	Valid Request
				if(request == null) {
					return;
				}
				//	
				byte[] payload = SerializerUtil.serializeObjectEx(message);
				MqttMessage msg = new MqttMessage(payload);
				msg.setQos(MQTTConnection.EXACTLY_ONCE_2);
				msg.setRetained(true);
				m_Connection.publish(request.getTopicName(), msg);
			} catch (Exception e) {
				LogM.log(p_ctx, SPS_BC_Message.class, Level.SEVERE, "Error", e);
				SPS_BC_Message.setStatus(p_ctx, 
						message.getSPS_BC_Message_ID(), MQTTDefaultValues.STATUS_CREATED);
			}
		} else {
			setStatus(p_ctx, 
					message.getSPS_BC_Message_ID(), MQTTDefaultValues.STATUS_CREATED);
		}
    }
}
