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
public class MCharge extends X_C_Charge {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/10/2014, 22:34:23
	 * @param ctx
	 * @param C_Charge_ID
	 * @param conn
	 */
	public MCharge(Context ctx, int C_Charge_ID, DB conn) {
		super(ctx, C_Charge_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/10/2014, 22:34:23
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MCharge(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * Get Product
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:14:15
	 * @param ctx
	 * @param p_C_Charge_ID
	 * @return
	 * @return MCharge
	 */
	public static MCharge get(Context ctx, int p_C_Charge_ID) {
		return get(ctx, p_C_Charge_ID, null);
	}
	
	/**
	 * Get Product
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:29:40
	 * @param ctx
	 * @param p_C_Charge_ID
	 * @param conn
	 * @return
	 * @return MCharge
	 */
	public static MCharge get(Context ctx, int p_C_Charge_ID, DB conn) {
		//	
		if(p_C_Charge_ID <= 0)
			return null;
		//	Default
		return new MCharge(ctx, p_C_Charge_ID, conn);
	}

}
