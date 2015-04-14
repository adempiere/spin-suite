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
import java.util.Random;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.sync.content.SyncMessage;
import org.spinsuite.sync.content.SyncRequest;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 5, 2015, 9:34:06 AM
 *
 */
public class SPS_BC_Message {
	
	/**	Type Values					*/
	public static final String TYPE_IN				= "I";
	public static final String TYPE_OUT				= "O";
	/**	Status Values				*/
	public static final String STATUS_ACCEPTED 		= "A";
	public static final String STATUS_CREATED 		= "C";
	public static final String STATUS_DELIVERED 	= "D";
	public static final String STATUS_REJECT 		= "R";
	public static final String STATUS_SENT 			= "S";
	
	/**
	 * New In Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param message
	 * @return void
	 */
	public static void newInMessage(Context ctx, SyncMessage message) {
		newMessage(ctx, message, TYPE_IN);
	}
	
	/**
	 * New Out Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param message
	 * @return void
	 */
	public static void newOutMessage(Context ctx, SyncMessage message) {
		newMessage(ctx, message, TYPE_OUT);
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
					+ "m.SPS_BC_Request_ID, "
					+ "m.AD_User_ID "
					+ "FROM SPS_BC_Message m "
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
						null, 
						null, 
						rs.getInt(1), 
						rs.getInt(2));
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
	 * @return
	 * @return SyncMessage[]
	 */
	public static SyncMessage[] getMessage(Context ctx, String p_Status, String p_Type, String p_WhereClause) {
		//	
		ArrayList<SyncMessage> msgs = new ArrayList<SyncMessage>();
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_ONLY);
			StringBuffer sql = new StringBuffer("SELECT "
					+ "SPS_BC_Request_ID, "
					+ "SPS_BC_Message_ID, "
					+ "AD_User_ID, "
					+ "Text "
					+ "FROM SPS_BC_Message "
					+ "WHERE Status = ? "
					+ "AND Type = ?");
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
	 * Create a New Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param message
	 * @param p_Type
	 * @return void
	 */
	public static void newMessage(Context ctx, SyncMessage message, String p_Type) {
		if(message == null) {
			LogM.log(ctx, SPS_BC_Message.class, Level.CONFIG, "Null message for Insert");
			return;
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
					+ "Status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			//	Add Values
			int m_AD_Client_ID = Env.getAD_Client_ID();
			int m_AD_Org_ID = Env.getAD_Org_ID();
			int m_SPS_BC_Message_ID = message.getSPS_BC_Message_ID();
			//	For Out
			if(p_Type.equals(TYPE_OUT)) {
				m_SPS_BC_Message_ID = new Random().nextInt();
			}
			int m_AD_User_ID = (p_Type.equals(TYPE_IN)? message.getAD_User_ID(): Env.getAD_User_ID());
			Date now = new Date(System.currentTimeMillis());
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
			conn.addInt(m_SPS_BC_Message_ID);
			conn.addString(p_Type);
			conn.addString(STATUS_CREATED);
			//	Execute
			conn.executeSQL();
			//	Update Header
			conn.compileQuery("UPDATE SPS_BC_Request "
					+ "SET Updated = ?, "
					+ "LastMsg = ? "
					+ "WHERE SPS_BC_Request_ID = ?");
			//	Add Parameters
			conn.addDateTime(now);
			conn.addString(message.getText());
			conn.addInt(message.getSPS_BC_Request_ID());
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
			conn.compileQuery("SELECT m.Text, (strftime('%s', m.Updated)*1000) Updated "
					+ "FROM SPS_BC_Message m "
					+ "WHERE SPS_BC_Request_ID = ? "
					+ "ORDER BY Updated DESC");
			//	Add Parameter
			conn.addInt(request.getSPS_BC_Request_ID());
			//	Execute
			Cursor rs = conn.querySQL();
			String m_LastText = null;
			long m_time = 0;
			if(rs != null
					&& rs.moveToFirst()) {
				m_LastText = rs.getString(0);
				m_time = rs.getLong(1);
			}
			//	
			conn.compileQuery("UPDATE SPS_BC_Request "
					+ "SET Updated = ?, "
					+ "LastMsg = ? "
					+ "WHERE SPS_BC_Request_ID = ?");
			//	Add Parameters
			conn.addDateTime(new Date(m_time));
			conn.addString(m_LastText);
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
}
