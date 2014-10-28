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
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MOrderLine extends X_C_OrderLine {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 13:44:58
	 * @param ctx
	 * @param C_OrderLine_ID
	 * @param conn
	 */
	public MOrderLine(Context ctx, int C_OrderLine_ID, DB conn) {
		super(ctx, C_OrderLine_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 13:44:58
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MOrderLine(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}

	@Override
	protected boolean loadDefaultValues() {
		boolean ok = super.loadDefaultValues();
		setFreightAmt(Env.ZERO);
		setLineNetAmt(Env.ZERO);
		//
		setPriceEntered(Env.ZERO);
		setPriceActual(Env.ZERO);
		setPriceLimit(Env.ZERO);
		setPriceList(Env.ZERO);
		//
		setM_AttributeSetInstance_ID(0);
		//
		setQtyEntered(Env.ZERO);
		setQtyOrdered(Env.ZERO);	// 1
		setQtyDelivered(Env.ZERO);
		setQtyInvoiced(Env.ZERO);
		setQtyReserved(Env.ZERO);
		//
		setIsDescription (false);	// N
		setProcessed (false);
		setLine (0);
		//	
		return ok;
	}
	
	/**
	 * 	Get Order Unreserved Qty
	 *	@param ctx context
	 *	@param M_Warehouse_ID wh
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param excludeC_OrderLine_ID exclude C_OrderLine_ID
	 *	@return Unreserved Qty
	 */
	public static BigDecimal getNotReserved (Context ctx, int M_Warehouse_ID, 
		int M_Product_ID, int M_AttributeSetInstance_ID, int excludeC_OrderLine_ID) {
		BigDecimal retValue = Env.ZERO;
		String sql = "SELECT SUM(QtyOrdered-QtyDelivered-QtyReserved) "
			+ "FROM C_OrderLine ol"
			+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
			+ "WHERE ol.M_Warehouse_ID=?"	//	#1
			+ " AND M_Product_ID=?"			//	#2
			+ " AND o.IsSOTrx='Y' AND o.DocStatus='DR'"
			+ " AND QtyOrdered-QtyDelivered-QtyReserved<>0"
			+ " AND ol.C_OrderLine_ID<>?";
		if (M_AttributeSetInstance_ID != 0)
			sql += " AND M_AttributeSetInstance_ID=?";
		//	
		DB conn = new DB(ctx);
		//	Compile SQL
		conn.compileQuery(sql);
		//	Add Parameters
		conn.addInt(M_Warehouse_ID);
		conn.addInt(M_Product_ID);
		conn.addInt(excludeC_OrderLine_ID);
		if(M_AttributeSetInstance_ID != 0)
			conn.addInt(M_AttributeSetInstance_ID);
		//	
		try {
			DB.loadConnection(conn, DB.READ_ONLY);
			//	
			Cursor rs = conn.querySQL();
			if (rs.moveToFirst())
				retValue = new BigDecimal(rs.getDouble(0));
		} catch (Exception e) {
			LogM.log(ctx, MOrderLine.class, Level.SEVERE, sql, e);
		} finally {
			DB.closeConnection(conn);
		}
		//	
		return retValue;
	}	//	getNotReserved
	
}
