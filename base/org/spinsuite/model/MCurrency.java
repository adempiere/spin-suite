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

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MCurrency extends X_C_Currency {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/10/2014, 21:01:15
	 * @param ctx
	 * @param C_Currency_ID
	 * @param conn
	 */
	public MCurrency(Context ctx, int C_Currency_ID, DB conn) {
		super(ctx, C_Currency_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/10/2014, 21:01:15
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MCurrency(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * 	Get Standard Precision.
	 *	@param ctx Context
	 *	@param C_Currency_ID currency
	 *	@return Standard Precision
	 */
	public static int getStdPrecision (Context ctx, int C_Currency_ID) {
		int m_StdPrecision = DB.getSQLValue(ctx, "SELECT StdPrecision "
				+ "FROM C_Currency "
				+ "WHERE C_Currency_ID = " + C_Currency_ID);
		if(m_StdPrecision <= 0)
			return 2;
		//	
		return m_StdPrecision;
	}	//	getStdPrecision
	
	/**
	 * Get Currency
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:14:15
	 * @param ctx
	 * @param p_C_Currency_ID
	 * @return
	 * @return MCurrency
	 */
	public static MCurrency get(Context ctx, int p_C_Currency_ID) {
		return get(ctx, p_C_Currency_ID, null);
	}
	
	/**
	 * Get Currency
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:29:40
	 * @param ctx
	 * @param p_C_Currency_ID
	 * @param conn
	 * @return
	 * @return MCurrency
	 */
	public static MCurrency get(Context ctx, int p_C_Currency_ID, DB conn) {
		//	
		if(p_C_Currency_ID <= 0)
			return null;
		//	Default
		return new MCurrency(ctx, p_C_Currency_ID, conn);
	}

}
