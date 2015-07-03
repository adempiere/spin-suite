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
import java.util.Date;

import org.spinsuite.util.DisplayType;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/6/2015, 15:54:04
 *
 */
public class DisplayListProduct implements Parcelable {

	/**	Product Category ID	*/
	private int 		m_M_Product_Category_ID;
	/**	Product Category	*/
	private String 		m_ProductCategory;
	/**	Product ID			*/
	private int 		m_M_Product_ID;
	/**	Product Value		*/
	private String 		m_Value;
	/**	Product Name		*/
	private String 		m_Name;
	/**	Description			*/
	private String 		m_Description;
	/**	UOM ID				*/
	private int 		m_C_UOM_ID;
	/**	UOM Symbol			*/
	private String 		m_UOMSymbol;
	/**	Tax Category ID		*/
	private int 		m_C_TaxCategory_ID;
	/**	Tax Category		*/
	private String 		m_TaxName;
	/**	Tax ID				*/
	private int 		m_C_Tax_ID;
	/**	Tax					*/
	private String 		m_TaxIndicator;
	/**	Tax Rate			*/
	private BigDecimal 	m_TaxRate;
	/**	Price List			*/
	private BigDecimal 	m_PriceList;
	/**	Qty Entered			*/
	private BigDecimal 	m_QtyEntered;
	/**	Qty Ordered			*/
	private BigDecimal 	m_QtyOrdered;
	/**	Price Entered		*/
	private BigDecimal 	m_PriceEntered;
	/**	Line Net Amount		*/
	private BigDecimal 	m_LineNetAmt;
	/**	Price List 			*/
	private int			m_M_PriceList_ID;
	/**	Price List Version	*/
	private int			m_M_PriceList_Version_ID;
	/**	Valid From			*/
	private Date		m_ValidFrom;
	/**	Currency ID			*/
	private int 		m_C_Currency_ID;
	/**	Currency Value		*/
	private String 		m_CurSymbol;
	/**	Order Identifier	*/
	private int 		m_C_OrderLine_ID;
	
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public DisplayListProduct(){
		
	}
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param parcel
	 */
	public DisplayListProduct(Parcel parcel){
		this();
		readToParcel(parcel);
	}
		
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel paramParcel, int flags) {
		paramParcel.writeInt(m_M_Product_Category_ID);
		paramParcel.writeString(m_ProductCategory);
		paramParcel.writeInt(m_M_Product_ID);
		paramParcel.writeString(m_Value);
		paramParcel.writeString(m_Name);
		paramParcel.writeString(m_Description);
		paramParcel.writeInt(m_C_UOM_ID);
		paramParcel.writeString(m_UOMSymbol);
		paramParcel.writeInt(m_C_TaxCategory_ID);
		paramParcel.writeString(m_TaxName);
		paramParcel.writeInt(m_C_Tax_ID);
		paramParcel.writeString(m_TaxIndicator);
		paramParcel.writeDouble(DisplayType.getNumberAsDouble(m_TaxRate));
		paramParcel.writeDouble(DisplayType.getNumberAsDouble(m_PriceList));
		paramParcel.writeDouble(DisplayType.getNumberAsDouble(m_QtyEntered));
		paramParcel.writeDouble(DisplayType.getNumberAsDouble(m_QtyOrdered));
		paramParcel.writeDouble(DisplayType.getNumberAsDouble(m_PriceEntered));
		paramParcel.writeDouble(DisplayType.getNumberAsDouble(m_LineNetAmt));
		paramParcel.writeInt(m_M_PriceList_ID);
		paramParcel.writeInt(m_M_PriceList_Version_ID);
		paramParcel.writeLong(DisplayType.getDataAsLong(m_ValidFrom));
		paramParcel.writeInt(m_C_Currency_ID);
		paramParcel.writeString(m_CurSymbol);
		paramParcel.writeInt(m_C_OrderLine_ID);
	}
	
	/**
	 * Full
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_M_Product_Category_ID
	 * @param p_ProductCategory
	 * @param p_M_Product_ID
	 * @param p_Value
	 * @param p_Name
	 * @param p_Description
	 * @param p_C_UOM_ID
	 * @param p_UOMSymbol
	 * @param p_C_TaxCategory_ID
	 * @param p_TaxName
	 * @param p_C_Tax_ID
	 * @param p_TaxIndicator
	 * @param p_TaxRate
	 * @param p_PriceList
	 * @param p_QtyEntered
	 * @param p_QtyOrdered
	 * @param p_PriceEntered
	 * @param p_LineNetAmt
	 * @param p_M_PriceList_ID
	 * @param p_M_PriceList_Version_ID
	 * @param p_ValidFrom
	 * @param p_C_Currency_ID
	 * @param p_CurrencyName
	 * @param p_C_OrderLine_ID
	 */
	public DisplayListProduct(int p_M_Product_Category_ID, String p_ProductCategory, 
			int p_M_Product_ID, String p_Value, String p_Name, 
			String p_Description, int p_C_UOM_ID, String p_UOMSymbol, int p_C_TaxCategory_ID, 
			String p_TaxName, int p_C_Tax_ID, 
			String p_TaxIndicator, BigDecimal p_TaxRate, 
			BigDecimal p_PriceList, BigDecimal p_QtyEntered, BigDecimal p_QtyOrdered,  
			BigDecimal p_PriceEntered, BigDecimal p_LineNetAmt,
			int p_M_PriceList_ID, int p_M_PriceList_Version_ID, 
			Date p_ValidFrom, int p_C_Currency_ID, String p_CurrencyName, int p_C_OrderLine_ID){
		m_M_Product_Category_ID = p_M_Product_Category_ID;
		m_ProductCategory = p_ProductCategory;
		m_M_Product_ID = p_M_Product_ID;
		m_Value = p_Value;
		m_Name = p_Name;
		m_Description = p_Description;
		m_C_UOM_ID = p_C_UOM_ID;
		m_UOMSymbol = p_UOMSymbol;
		m_C_TaxCategory_ID = p_C_TaxCategory_ID;
		m_TaxName = p_TaxName;
		m_C_Tax_ID = p_C_Tax_ID;
		m_TaxName = p_TaxName;
		m_TaxRate = p_TaxRate;
		m_PriceList = p_PriceList;
		m_QtyEntered = p_QtyEntered;
		m_QtyOrdered = p_QtyOrdered;
		m_PriceEntered = p_PriceEntered;
		m_LineNetAmt = p_LineNetAmt;
		m_M_PriceList_ID = p_M_PriceList_ID;
		m_M_PriceList_Version_ID = p_M_PriceList_Version_ID;
		m_ValidFrom = p_ValidFrom;
		m_C_Currency_ID = p_C_Currency_ID;
		m_CurSymbol = p_CurrencyName;
		m_C_OrderLine_ID = p_C_OrderLine_ID;
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public DisplayListProduct createFromParcel(Parcel parcel) {
			return new DisplayListProduct(parcel);
		}
		public DisplayListProduct[] newArray(int size) {
			return new DisplayListProduct[size];
		}
	};

	/**
	 * Read
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param parcel
	 * @return void
	 */
	private void readToParcel(Parcel parcel) {
		m_M_Product_Category_ID = parcel.readInt();
		m_ProductCategory = parcel.readString();
		m_M_Product_ID = parcel.readInt();
		m_Value = parcel.readString();
		m_Name = parcel.readString();
		m_Description = parcel.readString();
		m_C_UOM_ID = parcel.readInt();
		m_UOMSymbol = parcel.readString();
		m_C_TaxCategory_ID = parcel.readInt();
		m_TaxName = parcel.readString();
		m_C_Tax_ID = parcel.readInt();
		m_TaxIndicator = parcel.readString();
		m_TaxRate = new BigDecimal(parcel.readDouble());
		m_PriceList = new BigDecimal(parcel.readDouble());
		m_QtyEntered = new BigDecimal(parcel.readDouble());
		m_QtyOrdered = new BigDecimal(parcel.readDouble());
		m_PriceEntered = new BigDecimal(parcel.readDouble());
		m_LineNetAmt = new BigDecimal(parcel.readDouble());
		m_M_PriceList_ID = parcel.readInt();
		m_M_PriceList_Version_ID = parcel.readInt();
		//	
		long m_ValidFromLong =  parcel.readInt();
		if(m_ValidFromLong != -1) {
			m_ValidFrom = new Date(m_ValidFromLong);
		} else {
			m_ValidFrom = null;
		}
		m_C_Currency_ID = parcel.readInt();
		m_CurSymbol = parcel.readString();
		m_C_OrderLine_ID = parcel.readInt();
	}
	
	/**
	 * @return the m_M_Product_Category_ID
	 */
	public int getM_Product_Category_ID() {
		return m_M_Product_Category_ID;
	}
	
	/**
	 * @return the m_ProductCategory
	 */
	public String getProductCategory() {
		return m_ProductCategory;
	}
	
	/**
	 * @return the m_M_Product_ID
	 */
	public int getM_Product_ID() {
		return m_M_Product_ID;
	}
	
	/**
	 * @return the m_Name
	 */
	public String getName() {
		return m_Name;
	}
	
	/**
	 * @return the m_Value
	 */
	public String getValue() {
		return m_Value;
	}
	
	/**
	 * @return the m_Description
	 */
	public String getDescription() {
		return m_Description;
	}
	
	/**
	 * @return the m_C_UOM_ID
	 */
	public int getC_UOM_ID() {
		return m_C_UOM_ID;
	}
	
	/**
	 * @return the m_UOMSymbol
	 */
	public String getUOMSymbol() {
		return m_UOMSymbol;
	}
	
	/**
	 * @return the m_C_TaxCategory_ID
	 */
	public int getC_TaxCategory_ID() {
		return m_C_TaxCategory_ID;
	}
	
	/**
	 * @return the m_TaxName
	 */
	public String getTaxName() {
		return m_TaxName;
	}
	
	/**
	 * @return the m_PriceList
	 */
	public BigDecimal getPriceList() {
		return m_PriceList;
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
	 * @return the m_QtyEntered
	 */
	public BigDecimal getQtyEntered() {
		return m_QtyEntered;
	}
	
	/**
	 * @return the m_QtyOrdered
	 */
	public BigDecimal getQtyOrdered() {
		return m_QtyOrdered;
	}
	
	/**
	 * @return the m_PriceEntered
	 */
	public BigDecimal getPriceEntered() {
		return m_PriceEntered;
	}
	
	/**
	 * @return the m_LineNetAmt
	 */
	public BigDecimal getLineNetAmt() {
		return m_LineNetAmt;
	}
	
	/**
	 * @return the m_M_PriceList_ID
	 */
	public int getM_PriceList_ID() {
		return m_M_PriceList_ID;
	}
	
	/**
	 * @return the m_M_PriceList_Version_ID
	 */
	public int getM_PriceList_Version_ID() {
		return m_M_PriceList_Version_ID;
	}
	
	/**
	 * @return the m_ValidFrom
	 */
	public Date getValidFrom() {
		return m_ValidFrom;
	}
	
	/**
	 * @return the m_C_Currency_ID
	 */
	public int getC_Currency_ID() {
		return m_C_Currency_ID;
	}
	
	/**
	 * @return the m_CurSymbol
	 */
	public String getCurSymbol() {
		return m_CurSymbol;
	}
	
	/**
	 * @return the m_C_OrderLine_ID
	 */
	public int getC_OrderLine_ID() {
		return m_C_OrderLine_ID;
	}
	
	/**
	 * @param m_M_Product_Category_ID the m_M_Product_Category_ID to set
	 */
	public void setM_Product_Category_ID(int m_M_Product_Category_ID) {
		this.m_M_Product_Category_ID = m_M_Product_Category_ID;
	}
	/**
	 * @param m_ProductCategory the m_ProductCategory to set
	 */
	public void setProductCategory(String m_ProductCategory) {
		this.m_ProductCategory = m_ProductCategory;
	}
	
	/**
	 * @param m_M_Product_ID the m_M_Product_ID to set
	 */
	public void setM_Product_ID(int m_M_Product_ID) {
		this.m_M_Product_ID = m_M_Product_ID;
	}
	
	/**
	 * @param m_Name the m_Name to set
	 */
	public void setName(String m_Name) {
		this.m_Name = m_Name;
	}
	
	/**
	 * @param m_Value the m_Value to set
	 */
	public void setValue(String m_Value) {
		this.m_Value = m_Value;
	}
	
	/**
	 * @param m_Description the m_Description to set
	 */
	public void setDescription(String m_Description) {
		this.m_Description = m_Description;
	}
	
	/**
	 * @param m_C_UOM_ID the m_C_UOM_ID to set
	 */
	public void setC_UOM_ID(int m_C_UOM_ID) {
		this.m_C_UOM_ID = m_C_UOM_ID;
	}
	
	/**
	 * @param m_UOMSymbol the m_UOMSymbol to set
	 */
	public void setUOMSymbol(String m_UOMSymbol) {
		this.m_UOMSymbol = m_UOMSymbol;
	}
	
	/**
	 * @param m_C_TaxCategory_ID the m_C_TaxCategory_ID to set
	 */
	public void setC_TaxCategory_ID(int m_C_TaxCategory_ID) {
		this.m_C_TaxCategory_ID = m_C_TaxCategory_ID;
	}
	
	/**
	 * @param m_TaxName the m_TaxName to set
	 */
	public void setTaxName(String m_TaxName) {
		this.m_TaxName = m_TaxName;
	}
	
	/**
	 * @param m_PriceList the m_PriceList to set
	 */
	public void setPriceList(BigDecimal m_PriceList) {
		this.m_PriceList = m_PriceList;
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
	 * @param m_QtyEntered the m_QtyEntered to set
	 */
	public void setQtyEntered(BigDecimal m_QtyEntered) {
		this.m_QtyEntered = m_QtyEntered;
	}
	
	/**
	 * @param m_QtyOrdered the m_QtyOrdered to set
	 */
	public void setQtyOrdered(BigDecimal m_QtyOrdered) {
		this.m_QtyEntered = m_QtyOrdered;
	}
	
	/**
	 * @param m_PriceEntered the m_PriceEntered to set
	 */
	public void setPriceEntered(BigDecimal m_PriceEntered) {
		this.m_PriceEntered = m_PriceEntered;
	}
	
	/**
	 * @param m_LineNetAmt the m_LineNetAmt to set
	 */
	public void setLineNetAmt(BigDecimal m_LineNetAmt) {
		this.m_LineNetAmt = m_LineNetAmt;
	}
	
	/**
	 * @param m_M_PriceList_ID the m_M_PriceList_ID to set
	 */
	public void setM_PriceList_ID(int m_M_PriceList_ID) {
		this.m_M_PriceList_ID = m_M_PriceList_ID;
	}
	
	/**
	 * @param m_M_PriceList_Version_ID the m_M_PriceList_Version_ID to set
	 */
	public void setM_PriceList_Version_ID(int m_M_PriceList_Version_ID) {
		this.m_M_PriceList_Version_ID = m_M_PriceList_Version_ID;
	}
	
	/**
	 * @param m_ValidFrom the m_ValidFrom to set
	 */
	public void setValidFrom(Date m_ValidFrom) {
		this.m_ValidFrom = m_ValidFrom;
	}
	
	/**
	 * @param m_C_Currency_ID the m_C_Currency_ID to set
	 */
	public void setC_Currency_ID(int m_C_Currency_ID) {
		this.m_C_Currency_ID = m_C_Currency_ID;
	}
	
	/**
	 * @param m_CurSymbol the m_CurrencyName to set
	 */
	public void setCurrencyName(String m_CurSymbol) {
		this.m_CurSymbol = m_CurSymbol;
	}
	
	/**
	 * @param m_C_OrderLine_ID the m_C_OrderLine_ID to set
	 */
	public void setC_OrderLine_ID(int m_C_OrderLine_ID) {
		this.m_C_OrderLine_ID = m_C_OrderLine_ID;
	}
}