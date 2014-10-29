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
public class MProduct extends X_M_Product {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 15:51:41
	 * @param ctx
	 * @param M_Product_ID
	 * @param conn
	 */
	public MProduct(Context ctx, int M_Product_ID, DB conn) {
		super(ctx, M_Product_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 15:51:41
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MProduct(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * Get Product
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:14:15
	 * @param ctx
	 * @param p_M_Product_ID
	 * @return
	 * @return MProduct
	 */
	public static MProduct get(Context ctx, int p_M_Product_ID) {
		return get(ctx, p_M_Product_ID, null);
	}
	
	/**
	 * Get Product
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:29:40
	 * @param ctx
	 * @param p_M_Product_ID
	 * @param conn
	 * @return
	 * @return MProduct
	 */
	public static MProduct get(Context ctx, int p_M_Product_ID, DB conn) {
		//	
		if(p_M_Product_ID <= 0)
			return null;
		//	Default
		return new MProduct(ctx, p_M_Product_ID, conn);
	}
	
	/**
	 * 
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/10/2014, 22:42:14
	 * @param ctx
	 * @param p_M_Product_ID
	 * @return
	 * @return int
	 */
	public static int getUOMPrecision(Context ctx, int p_M_Product_ID) {
		return DB.getSQLValue(ctx, "SELECT uom.StdPrecision "
				+ "FROM M_Product p "
				+ "INNER JOIN C_UOM uom ON(uom.C_UOM_ID = p.C_UOM_ID) "
				+ "WHERE p.M_Product_ID = ?", String.valueOf(p_M_Product_ID));
	}
}
