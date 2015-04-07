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

import java.util.Date;
import java.util.Random;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.sync.content.SyncRequest;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;

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
		//	Create Connection
		DB conn = DB.loadConnection(ctx, DB.READ_WRITE);
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
				+ "Type) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		//	Add Values
		int m_AD_Client_ID = Env.getAD_Client_ID();
		int m_AD_Org_ID = Env.getAD_Org_ID();
		int m_SPS_BC_Request_ID = new Random().nextInt();
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
		conn.addString(request.getRequestType());
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
			for(Integer invited : request.getUsers()) {
				conn.addInt(m_AD_Client_ID);
				conn.addInt(m_AD_Org_ID);
				conn.addDateTime(now);
				conn.addInt(m_AD_User_ID);
				conn.addDateTime(now);
				conn.addInt(m_AD_User_ID);
				conn.addBoolean(true);
				conn.addInt(m_SPS_BC_Request_ID);
				conn.addInt(invited);
				conn.addString(STATUS_CREATED);
				conn.executeSQL();
			}
		}
		//	Successful
		conn.setTransactionSuccessful();
		//	End Transaction
		DB.closeConnection(conn);
	}
}
