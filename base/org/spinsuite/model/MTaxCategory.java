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
public class MTaxCategory extends X_C_TaxCategory {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/10/2014, 22:26:50
	 * @param ctx
	 * @param C_TaxCategory_ID
	 * @param conn
	 */
	public MTaxCategory(Context ctx, int C_TaxCategory_ID, DB conn) {
		super(ctx, C_TaxCategory_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/10/2014, 22:26:50
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MTaxCategory(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * Get Default Tax
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/10/2014, 22:30:59
	 * @param ctx
	 * @param p_C_TaxCategory_ID
	 * @param conn
	 * @return
	 * @return int
	 */
	public static int getDefaultTax_ID(Context ctx, int p_C_TaxCategory_ID, DB conn) {
		return DB.getSQLValue(ctx, "SELECT t.C_Tax_ID "
				+ "FROM C_Tax t "
				+ "WHERE t.C_TaxCategory_ID = ? "
				+ "ORDER BY t.IsDefault DESC", conn, String.valueOf(p_C_TaxCategory_ID));
	}

}
