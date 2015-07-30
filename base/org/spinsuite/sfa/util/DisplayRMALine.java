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
package org.spinsuite.sfa.util;

import java.math.BigDecimal;

/**
 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com 12/6/2015, 1:32:45
 * @contributor Yamel Senih, ySenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class DisplayRMALine {

	/**	Product Category	*/
	private String 		m_ProductCategory;
	/**	Product Value		*/
	private String 		m_Value;
	/**	Product Name		*/
	private String 		m_Name;
	/**	Description			*/
	private String 		m_Description;
	/**	UOM Symbol			*/
	private String 		m_UOMSymbol;
	/**	Tax ID				*/
	private int 		m_C_Tax_ID;
	/**	Tax					*/
	private String 		m_TaxIndicator;
	/**	Tax Rate			*/
	private BigDecimal 	m_TaxRate;
	/**	Qty Entered			*/
	private BigDecimal 	m_QtyReturn;
	/**	Price Entered		*/
	private BigDecimal 	m_PriceEntered;
	/**	Order Identifier	*/
	private int 		m_M_RMALine_ID;
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_M_RMALine_ID
	 * @param p_ProductCategory
	 * @param p_Value
	 * @param p_Name
	 * @param p_Description
	 * @param p_UOMSymbol
	 * @param p_C_Tax_ID
	 * @param p_TaxIndicator
	 * @param p_TaxRate
	 * @param p_PriceEntered
	 * @param p_QtyReturn
	 */
	public DisplayRMALine(int p_M_RMALine_ID, String p_ProductCategory, String p_Value, String p_Name,
			String p_Description, String p_UOMSymbol, 
			int p_C_Tax_ID, String p_TaxIndicator, BigDecimal p_TaxRate, 
			BigDecimal p_PriceEntered, BigDecimal p_QtyReturn) {
		super();
		m_M_RMALine_ID = p_M_RMALine_ID;
		m_ProductCategory = p_ProductCategory;
		m_Value = p_Value;
		m_Name = p_Name;
		m_Description = p_Description;
		m_UOMSymbol = p_UOMSymbol;
		m_C_Tax_ID = p_C_Tax_ID;
		m_TaxIndicator = p_TaxIndicator;
		m_TaxRate = p_TaxRate;
		m_PriceEntered = p_PriceEntered;
		m_QtyReturn = p_QtyReturn;
	}
		
	/**
	 * @return the m_ProductCategory
	 */
	public String getProductCategory() {
		return m_ProductCategory;
	}

	/**
	 * @param m_ProductCategory the m_ProductCategory to set
	 */
	public void setProductCategory(String m_ProductCategory) {
		this.m_ProductCategory = m_ProductCategory;
	}



	/**
	 * @return the orderLine_ID
	 */
	public int getC_OrderLine_ID() {
		return m_M_RMALine_ID;
	}


	/**
	 * @param p_C_OrderLine_ID the orderLine_ID to set
	 */
	public void setC_OrderLine_ID(int p_C_OrderLine_ID) {
		this.m_M_RMALine_ID = p_C_OrderLine_ID;
	}


	/**
	 * @return the prd_Value
	 */
	public String getValue() {
		return m_Value;
	}

	/**
	 * @param p_Value the prd_Value to set
	 */
	public void setValue(String p_Value) {
		this.m_Value = p_Value;
	}

	/**
	 * @return the prd_Name
	 */
	public String getName() {
		return m_Name;
	}

	/**
	 * @param p_Name the prd_Name to set
	 */
	public void setName(String p_Name) {
		this.m_Name = p_Name;
	}

	/**
	 * @return the prd_Description
	 */
	public String getDescription() {
		return m_Description;
	}

	/**
	 * @param p_Description the prd_Description to set
	 */
	public void setDescription(String p_Description) {
		this.m_Description = p_Description;
	}

	/**
	 * @return the uom_Symbol
	 */
	public String getUOM_Symbol() {
		return m_UOMSymbol;
	}

	/**
	 * @param p_UOM_Symbol the uom_Symbol to set
	 */
	public void setUOM_Symbol(String p_UOM_Symbol) {
		this.m_UOMSymbol = p_UOM_Symbol;
	}

	/**
	 * @return the m_C_Tax_ID
	 */
	public int getC_Tax_ID() {
		return m_C_Tax_ID;
	}

	/**
	 * @return the m_TaxIndicator
	 */
	public String getTaxIndicator() {
		return m_TaxIndicator;
	}

	/**
	 * @return the m_TaxRate
	 */
	public BigDecimal getTaxRate() {
		return m_TaxRate;
	}

	/**
	 * @param m_C_Tax_ID the m_C_Tax_ID to set
	 */
	public void setC_Tax_ID(int m_C_Tax_ID) {
		this.m_C_Tax_ID = m_C_Tax_ID;
	}

	/**
	 * @param m_TaxIndicator the m_TaxIndicator to set
	 */
	public void setTaxIndicator(String m_TaxIndicator) {
		this.m_TaxIndicator = m_TaxIndicator;
	}

	/**
	 * @param m_TaxRate the m_TaxRate to set
	 */
	public void setTaxRate(BigDecimal m_TaxRate) {
		this.m_TaxRate = m_TaxRate;
	}

	/**
	 * @return the priceEntered
	 */
	public BigDecimal getPriceEntered() {
		return m_PriceEntered;
	}

	/**
	 * @param p_PriceEntered the priceEntered to set
	 */
	public void setPriceEntered(BigDecimal p_PriceEntered) {
		this.m_PriceEntered = p_PriceEntered;
	}

	/**
	 * @return the qtyReturn
	 */
	public BigDecimal getQtyReturn() {
		return m_QtyReturn;
	}

	/**
	 * @param p_QtyReturn the qtyEntered to set
	 */
	public void setQtyReturn(BigDecimal p_QtyReturn) {
		this.m_QtyReturn = p_QtyReturn;
	}

	@Override
	public String toString() {
		return "DisplayOrderLine [m_ProductCategory=" + m_ProductCategory
				+ ", m_Value=" + m_Value + ", m_Name=" + m_Name
				+ ", m_Description=" + m_Description + ", m_UOMSymbol="
				+ m_UOMSymbol + ", m_C_Tax_ID=" + m_C_Tax_ID
				+ ", m_TaxIndicator=" + m_TaxIndicator + ", m_TaxRate="
				+ m_TaxRate + ", m_QtyEntered=" + m_QtyReturn
				+ ", m_PriceEntered=" + m_PriceEntered
				+ ", m_C_OrderLine_ID=" + m_M_RMALine_ID + "]";
	}
}