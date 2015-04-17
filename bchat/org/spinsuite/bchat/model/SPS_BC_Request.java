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
import org.spinsuite.sync.content.Invited;
import org.spinsuite.sync.content.SyncRequest;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 5, 2015, 9:34:06 AM
 *
 */
public class SPS_BC_Request {
	
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
	 * Create a New In Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param request
	 * @return void
	 */
	public static void newInRequest(Context ctx, SyncRequest request) {
		newRequest(ctx, request, TYPE_IN);
	}
	
	/**
	 * Insert a New Out Request
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param request
	 * @return void
	 */
	public static void newOutRequest(Context ctx, SyncRequest request) {
		newRequest(ctx, request, TYPE_OUT);
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
	 * @return SyncRequest[]
	 */
	public static SyncRequest[] getRequest(Context ctx, String p_Type, String p_Status) {
		//	
		ArrayList<SyncRequest> requests = new ArrayList<SyncRequest>();
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_ONLY);
			//	Compile Query
			conn.compileQuery("SELECT "
					+ "r.SPS_BC_Request_ID, "
					+ "r.Type, "
					+ "r.Topic, "
					+ "r.Name, "
					+ "r.LastMsg, "
					+ "r.AD_User_ID, "
					+ "r.IsGroup, "
					+ "ru.AD_User_ID, "
					+ "ru.Status "
					+ "FROM SPS_BC_Request r "
					+ "LEFT JOIN SPS_BC_Request_User ru ON(ru.SPS_BC_Request_ID = r.SPS_BC_Request_ID) "
					+ "WHERE r.Type = ? "
					+ "AND ru.Status = ? "
					+ "ORDER BY r.SPS_BC_Request_ID, ru.Updated");
			//	Add Parameter
			conn.addString(p_Type);
			conn.addString(p_Status);
			//	Query Data
			Cursor rs = conn.querySQL();
			//	Get Header Data
			if(rs.moveToFirst()) {
				do {
					//	
					SyncRequest request = new SyncRequest(null);
					request.setSPS_BC_Request_ID(rs.getInt(0));
					request.setType(rs.getString(1));
					request.setTopicName(rs.getString(2));
					request.setName(rs.getString(3));
					//	Set Last Message
					request.setLastMsg(rs.getString(4));
					//	Is Group
					request.setIsGroup(rs.getString(6) != null 
							&& rs.getString(6).equals("Y"));
					//	Add Users
					do {
						int currentRequest_ID = rs.getInt(0);
						//	Verify if is other request
						if(request.getSPS_BC_Request_ID() != currentRequest_ID) {
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
		return requests.toArray(new SyncRequest[requests.size()]);
	}
	
	/**
	 * Get Sync Request for Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param p_SPS_BC_Request_ID
	 * @return
	 * @return SyncRequest
	 */
	public static SyncRequest getRequest(Context ctx, int p_SPS_BC_Request_ID) {
		//	Valid 0 ID
		if(p_SPS_BC_Request_ID == 0)
			return null;
		//	
		SyncRequest request = null;
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_ONLY);
			//	Compile Query
			conn.compileQuery("SELECT "
					+ "r.SPS_BC_Request_ID, "
					+ "r.Type, "
					+ "r.Topic, "
					+ "r.Name, "
					+ "r.IsGroup, "
					+ "r.AD_User_ID, "
					+ "r.LastMsg "
					+ "FROM SPS_BC_Request r "
					+ "INNER JOIN AD_User u ON(u.AD_User_ID = r.AD_User_ID) "
					+ "WHERE r.SPS_BC_Request_ID = ?");
			//	Add Parameter
			conn.addInt(p_SPS_BC_Request_ID);
			//	Query Data
			Cursor rs = conn.querySQL();
			//	Get Header Data
			if(rs.moveToFirst()) {
				request = new SyncRequest(
						rs.getInt(0), 
						null, 
						rs.getString(1), 
						rs.getString(2), 
						rs.getString(3), 
						(rs.getString(4) != null 
							&& rs.getString(4).equals("Y")));
				//	Set Last Message
				request.setLastMsg(rs.getString(6));
				//	Query for Lines
				conn.compileQuery("SELECT "
						+ "ru.AD_User_ID, "
						+ "ru.Status "
						+ "FROM SPS_BC_Request_User ru "
						+ "WHERE ru.SPS_BC_Request_ID = ?");
				//	Add Parameter
				conn.addInt(p_SPS_BC_Request_ID);
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
	 * @return void
	 */
	public static void newRequest(Context ctx, SyncRequest request, String p_Type) {
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
					+ "SPS_BC_Request_ID, "
					+ "Topic, "
					+ "Type, "
					+ "IsGroup) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			//	Add Values
			int m_AD_Client_ID = Env.getAD_Client_ID();
			int m_AD_Org_ID = Env.getAD_Org_ID();
			int m_SPS_BC_Request_ID = request.getSPS_BC_Request_ID();
			//	For Out
			if(p_Type.equals(TYPE_OUT)) {
				m_SPS_BC_Request_ID = new Random().nextInt();
			}
			int m_AD_User_ID = Env.getAD_User_ID();
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
			conn.addInt(m_SPS_BC_Request_ID);
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
						+ "SPS_BC_Request_ID, "
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
					conn.addInt(m_SPS_BC_Request_ID);
					conn.addInt(invited.getAD_USer_ID());
					conn.addString(STATUS_CREATED);
					conn.executeSQL();
					conn.clearParameters();
				}
			}
			//	Successful
			conn.setTransactionSuccessful();
			//	Set ID
			request.setSPS_BC_Request_ID(m_SPS_BC_Request_ID);
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
	 * @param p_SPS_Request_IDs
	 * @return void
	 */
	public static void deleteRequest(Context ctx, int [] p_SPS_Request_IDs) {
		if(p_SPS_Request_IDs == null
				|| p_SPS_Request_IDs.length == 0) {
			LogM.log(ctx, SPS_BC_Request.class, Level.CONFIG, "Null where clause for delete");
			return;
		}
		//	Create IN
		StringBuffer inClause = new StringBuffer("WHERE SPS_BC_Request_ID IN(");
		boolean first = true;
		//	Iterate
		for(int id : p_SPS_Request_IDs) {
			if(!first) {
				inClause.append(", ");
			}
			//	Add
			inClause.append(id);
			//	Change First
			if(first) {
				first = false;
			}
		}
		//	Add Last
		inClause.append(")");
		//	Connection
		DB conn = null;
		try {
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
}