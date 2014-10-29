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
public class MOrderTax extends X_C_OrderTax {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 14:09:33
	 * @param ctx
	 * @param C_OrderTax_ID
	 * @param conn
	 */
	public MOrderTax(Context ctx, int C_OrderTax_ID, DB conn) {
		super(ctx, C_OrderTax_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 14:09:33
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MOrderTax(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/** Tax							*/
	private MTax 		m_tax 		= null;
	/** Cached Precision			*/
	private int			m_precision = 0;
	
	/**
	 * 	Get Precision
	 * 	@return Returns the precision or 2
	 */
	public int getPrecision() {
		if (m_precision == 0)
			return 2;
		return m_precision;
	}	//	getPrecision

	/**
	 * 	Set Precision
	 *	@param precision The precision to set.
	 */
	protected void setPrecision (int precision) {
		m_precision = precision;
	}	//	setPrecision
	
	/**
	 * 	Get Tax Line for Order Line
	 *	@param line Order line
	 *	@param precision currency precision
	 *	@param oldTax get old tax
	 *	@param conn transaction
	 *	@return existing or new tax
	 */
	public static MOrderTax get (Context ctx, MOrderLine line, int precision, 
		boolean oldTax, DB conn) {
		MOrderTax retValue = null;
		if (line == null || line.getC_Order_ID() == 0) {
			LogM.log(line.getCtx(), MOrderTax.class, Level.FINE, "No Order");
			return null;
		}
		int C_Tax_ID = line.getC_Tax_ID();
		boolean isOldTax = oldTax && line.is_ValueChanged(MOrderTax.COLUMNNAME_C_Tax_ID); 
		if (isOldTax) {
			int old = line.get_ValueOldAsInt(MOrderTax.COLUMNNAME_C_Tax_ID);
			if (old <= 0) {
				LogM.log(ctx, MOrderTax.class, Level.FINE, "No Old Tax");
				return null;
			}
			C_Tax_ID = ((Integer)old).intValue();
		}
		if (C_Tax_ID == 0) {
			LogM.log(ctx, MOrderTax.class, Level.FINE, "No Tax");
			return null;
		}
		
		String sql = "SELECT * FROM C_OrderTax WHERE C_Order_ID=? AND C_Tax_ID=?";
		conn.compileQuery(sql);
		try {
			conn.addInt(line.getC_Order_ID());
			conn.addInt(C_Tax_ID);
			Cursor rs = conn.querySQL();
			if (rs.moveToFirst())
				retValue = new MOrderTax(line.getCtx(), rs, conn);
		} catch (Exception e) {
			LogM.log(ctx, MOrderTax.class, Level.SEVERE, sql, e);
		}
		if (retValue != null) {
			retValue.setPrecision(precision);
			LogM.log(ctx, MOrderTax.class, Level.FINE, "(old=" + oldTax + ") " + retValue);
			return retValue;
		}
		// If the old tax was required and there is no MOrderTax for that
		// return null, and not create another MOrderTax - teo_sarca [ 1583825 ]
		else {
			if (isOldTax)
				return null;
		}
		
		//	Create New
		retValue = new MOrderTax(line.getCtx(), 0, conn);
		retValue.setClientOrg(line);
		retValue.setC_Order_ID(line.getC_Order_ID());
		retValue.setC_Tax_ID(line.getC_Tax_ID());
		retValue.setPrecision(precision);
		retValue.setIsTaxIncluded(line.isTaxIncluded(conn));
		LogM.log(line.getCtx(), MOrderTax.class, Level.FINE, "(new) " + retValue);
		return retValue;
	}	//	get
	
	/**************************************************************************
	 * 	Calculate/Set Tax Amt from Order Lines
	 * 	@return true if calculated
	 */
	public boolean calculateTaxFromLines() {
		BigDecimal taxBaseAmt = Env.ZERO;
		BigDecimal taxAmt = Env.ZERO;
		//
		boolean documentLevel = getTax().isDocumentLevel();
		MTax tax = getTax();
		//
		String sql = "SELECT LineNetAmt "
				+ "FROM C_OrderLine "
				+ "WHERE C_Order_ID=? AND C_Tax_ID=?";
		DB conn = get_Connection();
		conn.compileQuery(sql);
		try {
			conn.addInt(getC_Order_ID());
			conn.addInt(getC_Tax_ID());
			Cursor rs = conn.querySQL();
			while (rs.moveToNext()) {
				BigDecimal baseAmt = new BigDecimal(rs.getDouble(rs.getColumnIndex("LineNetAmt")));
				taxBaseAmt = taxBaseAmt.add(baseAmt);
				//
				if (!documentLevel)		// calculate line tax
					taxAmt = taxAmt.add(tax.calculateTax(baseAmt, isTaxIncluded(), getPrecision()));
			}
		} catch (Exception e) {
			LogM.log(getCtx(), getClass(), Level.SEVERE, "Error calculateTaxFromLines()", e);
			taxBaseAmt = null;
		}
		//	Set Processed
		setProcessed(false);
		if (taxBaseAmt == null)
			return false;
		
		//	Calculate Tax
		if (documentLevel)		//	document level
			taxAmt = tax.calculateTax(taxBaseAmt, isTaxIncluded(), getPrecision());
		setTaxAmt(taxAmt);

		//	Set Base
		if (isTaxIncluded())
			setTaxBaseAmt (taxBaseAmt.subtract(taxAmt));
		else
			setTaxBaseAmt (taxBaseAmt);
		LogM.log(getCtx(), getClass(), Level.FINE, toString());
		return true;
	}	//	calculateTaxFromLines
	
	/**
	 * 	Get Tax
	 *	@return tax
	 */
	protected MTax getTax() {
		if (m_tax == null)
			m_tax = MTax.get(getCtx(), getC_Tax_ID());
		return m_tax;
	}	//	getTax

}
