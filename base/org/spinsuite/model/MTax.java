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
import java.util.List;
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
public class MTax extends X_C_Tax {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/10/2014, 21:20:51
	 * @param ctx
	 * @param C_Tax_ID
	 * @param conn
	 */
	public MTax(Context ctx, int C_Tax_ID, DB conn) {
		super(ctx, C_Tax_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/10/2014, 21:20:51
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MTax(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**	100					*/
	private static BigDecimal 	ONEHUNDRED = new BigDecimal(100);
	/**	Child Taxes			*/
	private MTax[]				m_childTaxes = null;
	
	/**
	 * Do we have Postal Codes
	 * @return true if postal codes exist
	 */
	public boolean isPostal() {
		//if(getPostals(false) == null)
			//return false;
		
		//return getPostals(false).length > 0;
		return false;
	}	//	isPostal
	
	/**
	 * 	Get All Tax codes (for AD_Client)
	 *	@param ctx context
	 *	@return MTax
	 */
	public static MTax[] getAll (Context ctx) {
		MTax[] retValue = null;

		//	Create it
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		List<MTax> list = new Query(ctx, I_C_Tax.Table_Name, null, null)
								.setClient_ID()
								.setOrderBy("C_Country_ID, C_Region_ID, To_Country_ID, To_Region_ID")
								.setOnlyActiveRecords(true)
								.list();
		//	
		retValue = list.toArray(new MTax[list.size()]);
		return retValue;
	}	//	getAll
	
	/**
	 * Get Tax
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:14:15
	 * @param ctx
	 * @param p_C_Tax_ID
	 * @return
	 * @return MTax
	 */
	public static MTax get(Context ctx, int p_C_Tax_ID) {
		return get(ctx, p_C_Tax_ID, null);
	}
	
	/**
	 * Get Tax
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:29:40
	 * @param ctx
	 * @param p_C_Tax_ID
	 * @param conn
	 * @return
	 * @return MTax
	 */
	public static MTax get(Context ctx, int p_C_Tax_ID, DB conn) {
		//	
		if(p_C_Tax_ID <= 0)
			return null;
		//	Default
		return new MTax(ctx, p_C_Tax_ID, conn);
	}
	
	/**
	 * 	Get Child Taxes
	 * 	@param requery reload
	 *	@return array of taxes or null
	 */
	public MTax[] getChildTaxes (boolean requery) {
		if (!isSummary())
			return null;
		if (m_childTaxes != null && !requery)
			return m_childTaxes;
		//
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		final String whereClause = COLUMNNAME_Parent_Tax_ID+"=?";
		List<MTax> list = new Query(getCtx(), I_C_Tax.Table_Name, whereClause,  get_Connection())
			.setParameters(getC_Tax_ID())
			.setOnlyActiveRecords(true)
			.list();	
		//red1 - end -
	 
		m_childTaxes = new MTax[list.size ()];
		list.toArray (m_childTaxes);
		return m_childTaxes;
	}	//	getChildTaxes
	
	/**
	 * 	Calculate Tax - no rounding
	 *	@param amount amount
	 *	@param taxIncluded if true tax is calculated from gross otherwise from net 
	 *	@param scale scale 
	 *	@return  tax amount
	 */
	public BigDecimal calculateTax (BigDecimal amount, boolean taxIncluded, int scale) {
		//	Null Tax
		if (isZeroTax())
			return Env.ZERO;
		
		BigDecimal multiplier = getRate().divide(ONEHUNDRED, 12, BigDecimal.ROUND_HALF_UP);		

		BigDecimal tax = null;		
		if (!taxIncluded)	//	$100 * 6 / 100 == $6 == $100 * 0.06
		{
			tax = amount.multiply (multiplier);
		}
		else			//	$106 - ($106 / (100+6)/100) == $6 == $106 - ($106/1.06)
		{
			multiplier = multiplier.add(Env.ONE);
			BigDecimal base = amount.divide(multiplier, 12, BigDecimal.ROUND_HALF_UP); 
			tax = amount.subtract(base);
		}
		BigDecimal finalTax = tax.setScale(scale, BigDecimal.ROUND_HALF_UP);
		LogM.log(getCtx(), getClass(), Level.FINE, "calculateTax " + amount 
			+ " (incl=" + taxIncluded + ",mult=" + multiplier + ",scale=" + scale 
			+ ") = " + finalTax + " [" + tax + "]");
		return finalTax;
	}	//	calculateTax
	
	/**
	 * Is Zero Tax
	 * @return true if tax rate is 0
	 */
	public boolean isZeroTax() {
		return getRate().signum() == 0;
	}	//	isZeroTax

}
