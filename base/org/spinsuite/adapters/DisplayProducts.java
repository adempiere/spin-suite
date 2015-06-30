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
 * Copyright (C) 2012-2015 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.adapters;

import java.math.BigDecimal;

import org.spinsuite.util.Env;

/**
 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com 12/6/2015, 1:32:45
 *
 */
public class DisplayProducts {

	private String prd_Value;
	private String prd_Name;
	private String prd_Description;
	private String uom_Symbol;
	private BigDecimal priceEntered = Env.ZERO;
	private BigDecimal lineNetAmt = Env.ZERO;
	private BigDecimal qtyEntered = Env.ZERO;
	private int orderLine_ID = 0;
	
	/**
	 * *** Constructor ***
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param prd_Value
	 * @param prd_Name
	 * @param prd_Description
	 * @param uom_Symbol
	 * @param priceEntered
	 * @param lineNetAmt
	 * @param qtyEntered
	 */
	public DisplayProducts(int orderLine_ID,String prd_Value, String prd_Name,
			String prd_Description, String uom_Symbol, BigDecimal priceEntered,
			BigDecimal lineNetAmt, BigDecimal qtyEntered) {
		super();
		this.orderLine_ID = orderLine_ID;
		this.prd_Value = prd_Value;
		this.prd_Name = prd_Name;
		this.prd_Description = prd_Description;
		this.uom_Symbol = uom_Symbol;
		this.priceEntered = priceEntered;
		this.lineNetAmt = lineNetAmt;
		this.qtyEntered = qtyEntered;
	}

	
	/**
	 * @return the orderLine_ID
	 */
	public int getOrderLine_ID() {
		return orderLine_ID;
	}


	/**
	 * @param orderLine_ID the orderLine_ID to set
	 */
	public void setOrderLine_ID(int orderLine_ID) {
		this.orderLine_ID = orderLine_ID;
	}


	/**
	 * @return the prd_Value
	 */
	public String getPrd_Value() {
		return prd_Value;
	}

	/**
	 * @param prd_Value the prd_Value to set
	 */
	public void setPrd_Value(String prd_Value) {
		this.prd_Value = prd_Value;
	}

	/**
	 * @return the prd_Name
	 */
	public String getPrd_Name() {
		return prd_Name;
	}

	/**
	 * @param prd_Name the prd_Name to set
	 */
	public void setPrd_Name(String prd_Name) {
		this.prd_Name = prd_Name;
	}

	/**
	 * @return the prd_Description
	 */
	public String getPrd_Description() {
		return prd_Description;
	}

	/**
	 * @param prd_Description the prd_Description to set
	 */
	public void setPrd_Description(String prd_Description) {
		this.prd_Description = prd_Description;
	}

	/**
	 * @return the uom_Symbol
	 */
	public String getUom_Symbol() {
		return uom_Symbol;
	}

	/**
	 * @param uom_Symbol the uom_Symbol to set
	 */
	public void setUom_Symbol(String uom_Symbol) {
		this.uom_Symbol = uom_Symbol;
	}

	/**
	 * @return the priceEntered
	 */
	public BigDecimal getPriceEntered() {
		return priceEntered;
	}

	/**
	 * @param priceEntered the priceEntered to set
	 */
	public void setPriceEntered(BigDecimal priceEntered) {
		this.priceEntered = priceEntered;
	}

	/**
	 * @return the lineNetAmt
	 */
	public BigDecimal getLineNetAmt() {
		return lineNetAmt;
	}

	/**
	 * @param lineNetAmt the lineNetAmt to set
	 */
	public void setLineNetAmt(BigDecimal lineNetAmt) {
		this.lineNetAmt = lineNetAmt;
	}

	/**
	 * @return the qtyEntered
	 */
	public BigDecimal getQtyEntered() {
		return qtyEntered;
	}

	/**
	 * @param qtyEntered the qtyEntered to set
	 */
	public void setQtyEntered(BigDecimal qtyEntered) {
		this.qtyEntered = qtyEntered;
	}
	
	
	
	

}
