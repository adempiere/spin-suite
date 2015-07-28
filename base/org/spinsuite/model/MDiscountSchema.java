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
import java.util.ArrayList;
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
public class MDiscountSchema extends X_M_DiscountSchema {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 16:03:58
	 * @param ctx
	 * @param M_DiscountSchema_ID
	 * @param conn
	 */
	public MDiscountSchema(Context ctx, int M_DiscountSchema_ID, DB conn) {
		super(ctx, M_DiscountSchema_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/10/2014, 16:03:58
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MDiscountSchema(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}

	/**
	 * 	Calculate Discounted Price
	 *	@param Qty quantity
	 *	@param Price price
	 *	@param M_Product_ID product
	 *	@param M_Product_Category_ID category
	 *	@param BPartnerFlatDiscount flat discount
	 *	@return discount or zero
	 */
	public BigDecimal calculatePrice (BigDecimal Qty, BigDecimal Price,  
		int M_Product_ID, int M_Product_Category_ID,  
		BigDecimal BPartnerFlatDiscount)
	{
		LogM.log(getCtx(), getClass(), Level.FINE, "Price=" + Price + ",Qty=" + Qty +
		         ",M_Product_ID=" + M_Product_ID +
				 ",M_Product_Category_ID=" + M_Product_Category_ID +
				 ",BPartnerFlatDiscount=" + BPartnerFlatDiscount.doubleValue() );
		
		if (Price == null || Env.ZERO.compareTo(Price) == 0)
			return Price;
		//
		BigDecimal discount = calculateDiscount(Qty, Price, 
			M_Product_ID, M_Product_Category_ID, BPartnerFlatDiscount);
		LogM.log(getCtx(), getClass(), Level.FINE, "Discount=" + discount.doubleValue());
		//	nothing to calculate
		if (discount == null || discount.signum() == 0)
			return Price;
		//
		BigDecimal onehundred = new BigDecimal(100);
		BigDecimal multiplier = (onehundred).subtract(discount);
		multiplier = multiplier.divide(onehundred, 6, BigDecimal.ROUND_HALF_UP);
		BigDecimal newPrice = Price.multiply(multiplier);
		LogM.log(getCtx(), getClass(), Level.FINE, "=>" + newPrice);
		return newPrice;
	}	//	calculatePrice
	
	
	/**
	 * 	Calculate Discount Percentage
	 *	@param Qty quantity
	 *	@param Price price
	 *	@param M_Product_ID product
	 *	@param M_Product_Category_ID category
	 *	@param BPartnerFlatDiscount flat discount
	 *	@return discount or zero
	 */
	public BigDecimal calculateDiscount (BigDecimal Qty, BigDecimal Price,  
		int M_Product_ID, int M_Product_Category_ID,
		BigDecimal BPartnerFlatDiscount)
	{
		if (BPartnerFlatDiscount == null)
			BPartnerFlatDiscount = Env.ZERO;
		
		//
		if (DISCOUNTTYPE_FlatPercent.equals(getDiscountType()))
		{
			if (isBPartnerFlatDiscount())
				return BPartnerFlatDiscount;
			return getFlatDiscount();
		}
		//	Not supported
		else if (DISCOUNTTYPE_Formula.equals(getDiscountType())
			|| DISCOUNTTYPE_Pricelist.equals(getDiscountType()))
		{
			LogM.log(getCtx(), getClass(), Level.FINE, "Not supported (yet) DiscountType=" + getDiscountType());
			return Env.ZERO;
		}
		
		//	Price Breaks
		getBreaks(false);
		BigDecimal Amt = Price.multiply(Qty);
		if (isQuantityBased())
			LogM.log(getCtx(), getClass(), Level.FINER, "Qty=" + Qty + ",M_Product_ID=" + M_Product_ID 
					+ ",M_Product_Category_ID=" + M_Product_Category_ID);
		else
			LogM.log(getCtx(), getClass(), Level.FINER, "Amt=" + Amt + ",M_Product_ID=" + M_Product_ID 
					+ ",M_Product_Category_ID=" + M_Product_Category_ID);
		for (int i = 0; i < m_breaks.length; i++)
		{
			MDiscountSchemaBreak br = m_breaks[i];
			if (!br.isActive())
				continue;
			
			if (isQuantityBased())
			{
				if (!br.applies(Qty, M_Product_ID, M_Product_Category_ID))
				{
					LogM.log(getCtx(), getClass(), Level.FINER, "No: " + br);
					continue;
				}
				LogM.log(getCtx(), getClass(), Level.FINER, "Yes: " + br);
			}
			else
			{
				if (!br.applies(Amt, M_Product_ID, M_Product_Category_ID))
				{
					LogM.log(getCtx(), getClass(), Level.FINER, "No: " + br);
					continue;
				}
				LogM.log(getCtx(), getClass(), Level.FINER, "Yes: " + br);
			}
			
			//	Line applies
			BigDecimal discount = null;
			if (br.isBPartnerFlatDiscount())
				discount = BPartnerFlatDiscount;
			else
				discount = br.getBreakDiscount();
			LogM.log(getCtx(), getClass(), Level.FINE, "Discount=>" + discount);
			return discount;
		}	//	for all breaks
		
		return Env.ZERO;
	}	//	calculateDiscount
	
	/**	Breaks							*/
	private MDiscountSchemaBreak[]	m_breaks  = null;
	/**	Lines							*/
	private MDiscountSchemaLine[]	m_lines  = null;
	
	/**
	 * 	Get Breaks
	 *	@param reload reload
	 *	@return breaks
	 */
	public MDiscountSchemaBreak[] getBreaks(boolean reload)
	{
		if (m_breaks != null && !reload)
			return m_breaks;
		
		String sql = "SELECT * FROM M_DiscountSchemaBreak WHERE M_DiscountSchema_ID=? ORDER BY SeqNo";
		ArrayList<MDiscountSchemaBreak> list = new ArrayList<MDiscountSchemaBreak>();
		try {
			loadConnection(DB.READ_ONLY);
			Cursor rs = conn.querySQL(sql.toString(), new String[]{String.valueOf(getM_DiscountSchema_ID())});
			if(rs.moveToFirst()) {
//				2015-07-24 Dixon Martinez
//				Correct error in accordance travel from position 0 + 1 to position 0
//				while (rs.moveToNext()) {
//					list.add(new MDiscountSchemaBreak(getCtx(), rs, get_Connection()));
//				}
				do {
					list.add(new MDiscountSchemaBreak(getCtx(), rs, get_Connection()));
				}while (rs.moveToNext());
//				End Dixon Martinez
			}
		} catch (Exception e) {
			LogM.log(getCtx(), getClass(), Level.SEVERE, sql, e);
		} finally {
			closeConnection();
		}
		m_breaks = new MDiscountSchemaBreak[list.size ()];
		list.toArray (m_breaks);
		return m_breaks;
	}	//	getBreaks
	
	/**
	 * 	Get Lines
	 *	@param reload reload
	 *	@return lines
	 */
	public MDiscountSchemaLine[] getLines(boolean reload)
	{
		if (m_lines != null && !reload) {
			return m_lines;
		}
		
		String sql = "SELECT * FROM M_DiscountSchemaLine WHERE M_DiscountSchema_ID=? ORDER BY SeqNo";
		ArrayList<MDiscountSchemaLine> list = new ArrayList<MDiscountSchemaLine>();
		try {
			loadConnection(DB.READ_ONLY);
			Cursor rs = conn.querySQL(sql.toString(), new String[]{String.valueOf(getM_DiscountSchema_ID())});
			if(rs.moveToFirst()) {
				while (rs.moveToNext()) {
					list.add(new MDiscountSchemaLine(getCtx(), rs, get_Connection()));
				}
			}
		} catch (Exception e) {
			LogM.log(getCtx(), getClass(), Level.SEVERE, sql, e);
		} finally {
			closeConnection();
		}
		m_lines = new MDiscountSchemaLine[list.size ()];
		list.toArray (m_lines);
		return m_lines;
	}	//	getBreaks
}
