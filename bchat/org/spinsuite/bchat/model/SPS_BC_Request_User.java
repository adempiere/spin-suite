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

import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.LogM;

import android.content.Context;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 5, 2015, 9:34:06 AM
 *
 */
public class SPS_BC_Request_User {
	
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
	 * Change Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param p_SPS_BC_Request_ID
	 * @param p_Status
	 * @return void
	 */
	public static void setStatus(Context ctx, int p_SPS_BC_Request_ID, int p_AD_User_ID, String p_Status) {
		//	Connection
		DB conn = null;
		try {
			//	Create Connection
			conn = DB.loadConnection(ctx, DB.READ_WRITE);
			//	Compile Query
			conn.compileQuery("UPDATE SPS_BC_Request_User "
					+ "SET Status = ? "
					+ "WHERE SPS_BC_Request_ID = ? "
					+ "AND AD_User_ID = ?");
			//	Add Parameter
			conn.addString(p_Status);
			conn.addInt(p_SPS_BC_Request_ID);
			conn.addInt(p_AD_User_ID);
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