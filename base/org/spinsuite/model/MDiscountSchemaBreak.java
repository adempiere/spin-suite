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

import java.math.BigDecimal;

import org.spinsuite.base.DB;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MDiscountSchemaBreak extends X_M_DiscountSchemaBreak {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 16:10:03
	 * @param ctx
	 * @param M_DiscountSchemaBreak_ID
	 * @param conn
	 */
	public MDiscountSchemaBreak(Context ctx, int M_DiscountSchemaBreak_ID,
			DB conn) {
		super(ctx, M_DiscountSchemaBreak_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 16:10:03
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MDiscountSchemaBreak(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * 	Criteria apply
	 *	@param Value amt or qty
	 *	@param M_Product_ID product
	 *	@param M_Product_Category_ID category
	 *	@return true if criteria met
	 */
	public boolean applies (BigDecimal Value, int M_Product_ID, int M_Product_Category_ID)
	{
		if (!isActive())
			return false;
		
		//	below break value
		if (Value.compareTo(getBreakValue()) < 0)
			return false;
		
		//	No Product / Category 
		if (getM_Product_ID() == 0 
			&& getM_Product_Category_ID() == 0)
			return true;
		
		//	Product
		if (getM_Product_ID() == M_Product_ID)
			return true;
		
		//	Category
		if (M_Product_Category_ID != 0)
			return getM_Product_Category_ID() == M_Product_Category_ID;
		//	Is Category
		int m_M_Product_Category_ID = DB.getSQLValue(getCtx(), 
				"SELECT M_Product_Category_ID FROM M_Product WHERE M_Product_ID=?", String.valueOf(M_Product_ID));

		//	Look up Category of Product
		return m_M_Product_Category_ID > 0;
	}	//	applies

}
