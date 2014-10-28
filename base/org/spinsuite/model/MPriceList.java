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
public class MPriceList extends X_M_PriceList {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 14:21:39
	 * @param ctx
	 * @param M_PriceList_ID
	 * @param conn
	 */
	public MPriceList(Context ctx, int M_PriceList_ID, DB conn) {
		super(ctx, M_PriceList_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 14:21:39
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MPriceList(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * 	Get Standard Currency Precision
	 *	@param ctx context 
	 *	@param M_PriceList_ID price list
	 *	@return precision
	 */
	public static int getStandardPrecision (Context ctx, int M_PriceList_ID) {
		return DB.getSQLValue(ctx, "SELECT c.StdPrecision "
				+ "FROM M_PriceList pl "
				+ "INNER JOIN C_Currency c ON(c.C_Currency_ID = pl.C_Currency_ID) "
				+ "WHERE pl.M_PriceList_ID = ?", String.valueOf(M_PriceList_ID));
	}	//	getStandardPrecision

}
