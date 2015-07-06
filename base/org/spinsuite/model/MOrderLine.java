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
		m_productPrice = null;
		//	
		return ok;
	}
	
	/** Cached Currency Precision	*/
	private int			m_precision = 0;
	
	
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
	
	//	Product Pricing
	private MProductPricing	m_productPrice = null;
	//
	private boolean			m_IsSOTrx = true;

	private int 			m_M_PriceList_ID = 0;
	
	/**
	 * 	Get and calculate Product Pricing
	 *	@param M_PriceList_ID id
	 *	@return product pricing
	 */
	private MProductPricing getProductPricing (int M_PriceList_ID)
	{
		m_productPrice = new MProductPricing (getCtx(), getM_Product_ID(), 
			getC_BPartner_ID(), getQtyOrdered(), m_IsSOTrx);
		m_productPrice.setM_PriceList_ID(M_PriceList_ID);
		m_productPrice.setPriceDate(getDateOrdered());
		//
		m_productPrice.calculatePrice();
		return m_productPrice;
	}	//	getProductPrice
	
	/**
	 * 	Set Price for Product and PriceList.
	 * 	Use only if newly created.
	 * 	Uses standard price list of not set by order constructor
	 */
	public void setPrice()
	{
		if (getM_Product_ID() == 0)
			return;
		if (m_M_PriceList_ID == 0)
			throw new IllegalStateException("PriceList unknown!");
		setPrice (m_M_PriceList_ID);
	}	//	setPrice

	/**
	 * 	Set Price for Product and PriceList
	 * 	@param M_PriceList_ID price list
	 */
	public void setPrice (int M_PriceList_ID)
	{
		if (getM_Product_ID() == 0)
			return;
		//
//		log.fine(toString() + " - M_PriceList_ID=" + M_PriceList_ID);
		getProductPricing (M_PriceList_ID);
		setPriceActual (m_productPrice.getPriceStd());
		setPriceList (m_productPrice.getPriceList());
		setPriceLimit (m_productPrice.getPriceLimit());
		//
		if (getQtyEntered().compareTo(getQtyOrdered()) == 0)
			setPriceEntered(getPriceActual());
		else
			setPriceEntered(getPriceActual().multiply(getQtyOrdered()
				.divide(getQtyEntered(), 12, BigDecimal.ROUND_HALF_UP)));	//	recision
		
		//	Calculate Discount
		setDiscount(m_productPrice.getDiscount());
		//	Set UOM
		setC_UOM_ID(m_productPrice.getC_UOM_ID());
	}	//	setPrice

	/**
	 * 	Get Parent
	 *	@return parent
	 */
	public MOrder getParent()
	{
		if (m_parent == null)
			m_parent = new MOrder(getCtx(), getC_Order_ID(), null);
		return m_parent;
	}	//	getParent

	/**
	 * 	Set Header Info
	 *	@param order order
	 */
	public void setHeaderInfo (MOrder order)
	{
		m_parent = order;
		m_precision = new Integer(order.getPrecision());
		m_M_PriceList_ID = order.getM_PriceList_ID();
		m_IsSOTrx = order.isSOTrx();
	}	//	setHeaderInfo
	
	/**
	 * 	Set Defaults from Order.
	 * 	Does not set Parent !!
	 * 	@param order order
	 */
	public void setOrder (MOrder order)
	{
		setClientOrg(order);
		setC_BPartner_ID(order.getC_BPartner_ID());
		setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());
		setM_Warehouse_ID(order.getM_Warehouse_ID());
		setDateOrdered(order.getDateOrdered());
		setDatePromised(order.getDatePromised());
		setC_Currency_ID(order.getC_Currency_ID());
		//
		setHeaderInfo(order);	//	sets m_order
		//	Don't set Activity, etc as they are overwrites
	}	//	setOrder

	
	/** Parent					*/
	private MOrder			m_parent = null;
	
	@Override
	protected boolean beforeSave(boolean isNew) {
		boolean ok = super.beforeSave(isNew);
		if(!ok)
			return ok;
		
		//	Get Defaults from Parent
		if (getC_BPartner_ID() == 0 || getC_BPartner_Location_ID() == 0
			|| getM_Warehouse_ID() == 0 
			|| getC_Currency_ID() == 0)
			setOrder (getParent());
		if (m_M_PriceList_ID == 0)
			setHeaderInfo(getParent());
		
		//	Set Price if Actual = 0
		if (m_productPrice == null 
			&&  Env.ZERO.compareTo(getPriceActual()) == 0
			&&  Env.ZERO.compareTo(getPriceList()) == 0)
			setPrice();
		//	Check if on Price list
		if (m_productPrice == null)
			getProductPricing(m_M_PriceList_ID);
		m_productPrice.isCalculated();
//		if (!m_productPrice.isCalculated())
//		{
//			throw new ProductNotOnPriceListException(m_productPrice, getLine());
//		}
		
		if (getC_UOM_ID() == 0 
				&& (getM_Product_ID() != 0 
					|| getPriceEntered().compareTo(Env.ZERO) != 0
					|| getC_Charge_ID() != 0))
			{
				int C_UOM_ID = MUOM.getDefault_UOM_ID(getCtx());
				if (C_UOM_ID > 0)
					setC_UOM_ID (C_UOM_ID);
			}
		//	Qty Precision
		if (isNew || is_ValueChanged("QtyEntered"))
			setQtyEntered(getQtyEntered());
		if (isNew || is_ValueChanged("QtyOrdered"))
			setQtyOrdered(getQtyOrdered());
		
		//	FreightAmt Not used
		if (Env.ZERO.compareTo(getFreightAmt()) != 0)
			setFreightAmt(Env.ZERO);

		//	Set Tax
		if (getC_Tax_ID() == 0)
			setTax();

		//	Calculations & Rounding
		setLineNetAmt();	//	extended Amount with or without tax
		setDiscount();
		
		//	Get Line No
		if (getLine() == 0) {
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM C_OrderLine WHERE C_Order_ID=?";
			int ii = DB.getSQLValue(getCtx(), sql, get_Connection(), String.valueOf(getC_Order_ID()));
			setLine (ii);
		}
		//	Update Header
		return ok;
	}
	
	/**
	 *	Set Discount
	 */
	public void setDiscount()
	{
		BigDecimal list = getPriceList();
		//	No List Price
		if (Env.ZERO.compareTo(list) == 0)
			return;
		BigDecimal discount = list.subtract(getPriceActual())
			.multiply(new BigDecimal(100))
			.divide(list, getPrecision(), BigDecimal.ROUND_HALF_UP);
		setDiscount(discount);
	}	//	setDiscount

	
	/**
	 *	Set Tax
	 *	@return true if tax is set
	 */
	public boolean setTax()
	{
		int ii = Tax.get(getCtx(), getM_Product_ID(), getC_Charge_ID(), getDateOrdered(), getDateOrdered(),
			getAD_Org_ID(), getM_Warehouse_ID(),
			getC_BPartner_Location_ID(),		//	should be bill to
			getC_BPartner_Location_ID(), m_IsSOTrx);
		if (ii == 0)
		{
//			log.log(Level.SEVERE, "No Tax found");
			return false;
		}
		setC_Tax_ID (ii);
		return true;
	}	//	setTax
	
	@Override
	protected boolean afterSave(boolean isNew) {
		boolean ok = super.afterSave(isNew);
		if(!ok)
			return ok;
		
		if (!isNew 
				&& is_ValueChanged("C_Tax_ID")) {
			//	Recalculate Tax for old Tax
			if (!getParent().isProcessed())
				if (!updateOrderTax(true))
					return false;
		}
		return updateHeaderTax();
	}
	
	/**
	 * Recalculate order tax
	 * @param oldTax true if the old C_Tax_ID should be used
	 * @return true if success, false otherwise
	 * 
	 * @author teo_sarca [ 1583825 ]
	 */
	private boolean updateOrderTax(boolean oldTax) {
		MOrderTax tax = MOrderTax.get (getCtx(),this, getPrecision(), oldTax, get_Connection());
		if (tax != null) {
			if (!tax.calculateTaxFromLines())
				return false;
			if (tax.getTaxAmt().signum() != 0) {
				if (!tax.save())
					return false;
			}
			else {
				if (!tax.isNew() 
						&& !tax.delete())
					return false;
			}
		}
		return true;
	}
	
	/**
	 *	Update Tax & Header
	 *	@return true if header updated
	 */
	private boolean updateHeaderTax() {
		if (!getParent().isProcessed())
			if (!updateOrderTax(false))
				return false;
		//	Update Order Header
		String sql = "UPDATE C_Order "
			+ " SET TotalLines="
				+ "(SELECT COALESCE(SUM(il.LineNetAmt),0) "
				+ "FROM C_OrderLine il "
				+ "WHERE C_Order.C_Order_ID = il.C_Order_ID) "
			+ "WHERE C_Order_ID= ?";
		int no = DB.executeUpdate(getCtx(), sql, getC_Order_ID());
		if (no != 1)
			LogM.log(getCtx(), getClass(), Level.WARNING, "(1) #" + no);
		//	Is Tax Include
		if (isTaxIncluded(get_Connection()))
			sql = "UPDATE C_Order "
				+ " SET GrandTotal=TotalLines "
				+ "WHERE C_Order_ID=" + getC_Order_ID();
		else
			sql = "UPDATE C_Order "
				+ " SET GrandTotal=TotalLines+"
					+ "(SELECT COALESCE(SUM(it.TaxAmt),0) FROM C_OrderTax it WHERE C_Order.C_Order_ID = it.C_Order_ID) "
					+ "WHERE C_Order_ID = ?";
		no = DB.executeUpdate(getCtx(), sql, getC_Order_ID());
		if (no != 1)
			LogM.log(getCtx(), getClass(), Level.WARNING, "(2) #" + no);
		return no == 1;
	}	//	updateHeaderTax
	
	@Override
	protected boolean afterDelete() {
		boolean ok = super.afterDelete();
		if(!ok)
			return ok;
		//	Default
		return updateHeaderTax();
	}
	
	/**
	 *	Is Tax Included in Amount
	 *  @param conn
	 *	@return true if tax calculated
	 */
	public boolean isTaxIncluded(DB conn) {
		String m_IsTaxIncluded = DB.getSQLValueString(getCtx(),
				"SELECT pl.IsTaxIncluded "
				+ "FROM C_Order o "
				+ "INNER JOIN M_PriceList pl ON(pl.M_PriceList_ID = o.M_PriceList_ID) "
				+ "WHERE o.C_Order_ID = ?",
				conn, 
				String.valueOf(getC_Order_ID()));
		//	Verify if Tax Include
		return m_IsTaxIncluded != null && m_IsTaxIncluded.equals("Y");
	}	//	isTaxIncluded
	
	
	/**
	 * 	Calculate Extended Amt.
	 * 	May or may not include tax
	 */
	public void setLineNetAmt() {
		BigDecimal bd = getPriceActual().multiply(getQtyOrdered()); 
		MTax orderTax = MTax.get(getCtx(), getC_Tax_ID(), get_Connection());
		boolean documentLevel = orderTax.isDocumentLevel();
		boolean isTaxIncluded = isTaxIncluded(get_Connection());
		//	juddm: Tax Exempt & Tax Included in Price List & not Document Level - Adjust Line Amount
		//  http://sourceforge.net/tracker/index.php?func=detail&aid=1733602&group_id=176962&atid=879332
		if (isTaxIncluded && !documentLevel) {
			BigDecimal taxStdAmt = Env.ZERO, taxThisAmt = Env.ZERO;
			
			int m_C_Tax_ID = 0;
			
			//	get the standard tax
			if (getM_Product_ID() == 0) {
				if (getC_Charge_ID() != 0) {
					int m_C_TaxCategory_ID = MCharge.get(getCtx(), getC_Charge_ID(), get_Connection()).getC_TaxCategory_ID();
					m_C_Tax_ID = MTaxCategory.getDefaultTax_ID(getCtx(), m_C_TaxCategory_ID, get_Connection());
				}
			} else {
				int m_C_TaxCategory_ID = MProduct.get(getCtx(), getM_Product_ID(), get_Connection()).getC_TaxCategory_ID();
				m_C_Tax_ID = MTaxCategory.getDefaultTax_ID(getCtx(), m_C_TaxCategory_ID, get_Connection());
			}
			//	Load Tax
			MTax stdTax = MTax.get(getCtx(), m_C_Tax_ID, get_Connection());

			if (stdTax != null) {
				LogM.log(getCtx(), getClass(), Level.FINE, "stdTax rate is " + stdTax.getRate());
				LogM.log(getCtx(), getClass(), Level.FINE, "orderTax rate is " + orderTax.getRate());
				
				taxThisAmt = taxThisAmt.add(orderTax.calculateTax(bd, isTaxIncluded, getPrecision()));
				taxStdAmt = taxStdAmt.add(stdTax.calculateTax(bd, isTaxIncluded, getPrecision()));
				
				bd = bd.subtract(taxStdAmt).add(taxThisAmt);
				
				LogM.log(getCtx(), getClass(), Level.FINE, "Price List includes Tax and Tax Changed on Order Line: New Tax Amt: " 
						+ taxThisAmt + " Standard Tax Amt: " + taxStdAmt + " Line Net Amt: " + bd);	
			}
		}
		
		if (bd.scale() > getPrecision())
			bd = bd.setScale(getPrecision(), BigDecimal.ROUND_HALF_UP);
		super.setLineNetAmt (bd);
	}	//	setLineNetAmt
	
	/**
	 * 	Get Currency Precision from Currency
	 *	@return precision
	 */
	public int getPrecision() {
		if (m_precision != 0)
			return m_precision;
		//
		if (getC_Currency_ID() == 0) {
			if (m_precision != 0)
				return m_precision;
		} 
		if (getC_Currency_ID() != 0) {
			MCurrency cur = MCurrency.get(getCtx(), getC_Currency_ID());
			if (cur.get_ID() != 0) {
				m_precision = cur.getStdPrecision();
				return m_precision;
			}
		}
		//	Fallback
		String sql = "SELECT c.StdPrecision "
			+ "FROM C_Currency c INNER JOIN C_Order x ON (x.C_Currency_ID=c.C_Currency_ID) "
			+ "WHERE x.C_Order_ID=?";
		m_precision = DB.getSQLValue(getCtx(), sql, get_Connection(), String.valueOf(getC_Order_ID()));
		return m_precision;
	}	//	getPrecision
}
