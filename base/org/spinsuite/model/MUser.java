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
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.model;

import org.spinsuite.base.DB;
import org.spinsuite.util.RSACrypt;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MUser extends X_AD_User {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 14:14:51
	 * @param ctx
	 * @param AD_User_ID
	 * @param conn
	 */
	public MUser(Context ctx, int AD_User_ID, DB conn) {
		super(ctx, AD_User_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 14:14:51
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MUser(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}

	/**
	 * Find User Identifier from user and password
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param user
	 * @param pass
	 * @return
	 * @return int
	 */
	public static int findUserID(Context ctx, String user, String pass) {
		String localPass  = pass;
		String isEncrypted = DB.getSQLValueString(ctx, "SELECT c.IsEncrypted "
				+ "FROM SPS_Column c "
				+ "WHERE c.SPS_Table_ID = ? "
				+ "AND c.ColumnName = ?", String.valueOf(SPS_Table_ID), COLUMNNAME_Password);
		//	Encript pass
		if(isEncrypted != null
				&& isEncrypted.equals("Y")) {
			localPass = RSACrypt.getInstance(ctx).encrypt((String) pass);
		}
		//	Find
		return DB.getSQLValue(ctx, "SELECT u.AD_User_ID " +
    			"FROM AD_User u " +
    			"WHERE u.Name = ? AND u.PassWord = ?", user, localPass);
	}	
}