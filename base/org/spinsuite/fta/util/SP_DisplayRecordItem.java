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
package org.spinsuite.fta.util;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class SP_DisplayRecordItem implements Parcelable {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 12:53:53
	 */
	public SP_DisplayRecordItem(){
		
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 22:12:20
	 * @param p_M_Product_ID
	 * @param p_ProductName
	 * @param p_QtySuggested
	 * @param p_Suggested_Uom_ID
	 * @param p_SuggestedUOMSymbol
	 * @param p_QtyDosage
	 * @param p_Dosage_Uom_ID
	 * @param p_DosageUOMSymbol
	 * @param p_Qty
	 * @param p_C_UOID
	 * @param p_OrderUOMSymbol
	 * @param p_DayFrom
	 * @param p_DayTo
	 * @param p_FTA_ProductToApply_ID
	 */
	public SP_DisplayRecordItem(int p_M_Product_ID, String p_ProductName, 
			double p_QtySuggested, int p_Suggested_Uom_ID, String p_SuggestedUOMSymbol, 
			double p_QtyDosage, int p_Dosage_Uom_ID, String p_DosageUOMSymbol,  
			double p_Qty, int p_C_UOID, String p_OrderUOMSymbol, 
			int p_DayFrom, int p_DayTo, int p_FTA_ProductToApply_ID) {
		this.m_M_Product_ID = p_M_Product_ID;
		this.m_ProductName = p_ProductName;
		this.m_QtySuggested = p_QtySuggested;
		this.m_Suggested_UOM_ID = p_Suggested_Uom_ID;
		this.m_QtyDosage = p_QtyDosage; 
		this.m_Dosage_UOM_ID = p_Dosage_Uom_ID;
		this.m_Qty = p_Qty;
		this.m_C_UOM_ID = p_C_UOID;
		this.m_DayFrom = p_DayFrom;
		this.m_DayTo = p_DayTo; 
		this.m_SuggestedUOMSymbol = p_SuggestedUOMSymbol;
		this.m_DosageUOMSymbol = p_DosageUOMSymbol;
		this.m_OrderUOMSymbol = p_OrderUOMSymbol;
		this.m_FTA_ProductToApply_ID = p_FTA_ProductToApply_ID;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 20:29:04
	 * @param p_M_Product_ID
	 */
	public SP_DisplayRecordItem(int p_M_Product_ID) {
		m_M_Product_ID = p_M_Product_ID;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 12:55:11
	 * @param parcel
	 */
	public SP_DisplayRecordItem(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	/**	Identifier				*/
	private int 	m_M_Product_ID 			= 0;
	/**	Product Name			*/
	private String	m_ProductName 			= null;
	/**	Suggested Quantity		*/
	private double 	m_QtySuggested 			= 0;
	/**	Sugested UOM			*/
	private int 	m_Suggested_UOM_ID 		= 0;
	/**	Sugested UOM Symbol		*/
	private String 	m_SuggestedUOMSymbol 	= null;
	/**	Dosage Quantity			*/
	private double 	m_QtyDosage 			= 0;
	/**	Dosage UOM				*/
	private int 	m_Dosage_UOM_ID			= 0;
	/**	Dosage UOM Symbol		*/
	private String 	m_DosageUOMSymbol	 	= null;
	/**	Order Quantity			*/
	private double 	m_Qty 					= 0;
	/**	Order UOM				*/
	private int 	m_C_UOM_ID 				= 0;
	/**	Order UOM Symbol		*/
	private String 	m_OrderUOMSymbol		= null;
	/**	Day From				*/
	private int 	m_DayFrom				= 0;
	/**	Product to Apply ref.	*/
	private int 	m_FTA_ProductToApply_ID = 0;
	
	/**
	 * Get Product to Apply
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/08/2014, 03:47:01
	 * @return
	 * @return int
	 */
	public int getFTA_ProductToApply_ID() {
		return m_FTA_ProductToApply_ID;
	}
	
	/**
	 * Get Suggested UOM Symbol
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 22:09:13
	 * @return
	 * @return String
	 */
	public String getSuggestedUOMSymbol() {
		return m_SuggestedUOMSymbol;
	}

	/**
	 * Get Dosage UOM Symbol
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 22:09:24
	 * @return
	 * @return String
	 */
	public String getDosageUOMSymbol() {
		return m_DosageUOMSymbol;
	}

	/**
	 * Get Order UOM Symbol
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 22:09:35
	 * @return
	 * @return String
	 */
	public String getOrderUOMSymbol() {
		return m_OrderUOMSymbol;
	}

	/**	Day To					*/
	private int 	m_DayTo					= 0;
	/**	Date From				*/
	private Date	m_DateFrom				= null;
	/**	Date To					*/
	private Date	m_DateTo				= null;
	
	/**	Image URL				*/
	private String 	m_ImageURL 				= null;
	
	/**
	 * Get Product ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 12:44:56
	 * @return
	 * @return int
	 */
	public int getM_Product_ID() {
		return m_M_Product_ID;
	}

	/**
	 * Get Suggested Quantity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 20:17:03
	 * @return
	 * @return double
	 */
	public double getQtySuggested() {
		return m_QtySuggested;
	}

	/**
	 * Get Suggested UOM
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 20:16:48
	 * @return
	 * @return int
	 */
	public int getSuggested_UOM_ID() {
		return m_Suggested_UOM_ID;
	}

	/**
	 * Get Quantity Dosage
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 20:16:27
	 * @return
	 * @return double
	 */
	public double getQtyDosage() {
		return m_QtyDosage;
	}

	/**
	 * Get Dosage
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 20:16:07
	 * @return
	 * @return int
	 */
	public int getDosage_UOM_ID() {
		return m_Dosage_UOM_ID;
	}

	/**
	 * Get Quantity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 20:16:00
	 * @return
	 * @return double
	 */
	public double getQty() {
		return m_Qty;
	}
	
	/**
	 * Set Quantity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/08/2014, 02:03:42
	 * @param p_Qty
	 * @return void
	 */
	public void setQty(double p_Qty) {
		m_Qty = p_Qty;
	}

	/**
	 * Get UOM
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 20:15:33
	 * @return
	 * @return int
	 */
	public int getC_UOM_ID() {
		return m_C_UOM_ID;
	}

	/**
	 * Get Day From
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 20:15:26
	 * @return
	 * @return int
	 */
	public int getDayFrom() {
		return m_DayFrom;
	}

	/**
	 * @return the m_DayTo
	 */
	public int getDayTo() {
		return m_DayTo;
	}

	/**
	 * Get Date From
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 20:15:13
	 * @return
	 * @return Date
	 */
	public Date getDateFrom() {
		return m_DateFrom;
	}
	
	/**
	 * Set Date From
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 20:42:42
	 * @param p_DateFrom
	 * @return void
	 */
	public void setDateFrom(Date p_DateFrom) {
		m_DateFrom = p_DateFrom;
	}

	/**
	 * Get Date To
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 20:15:07
	 * @return
	 * @return Date
	 */
	public Date getDateTo() {
		return m_DateTo;
	}
	
	/**
	 * Set Date To
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 20:41:55
	 * @param p_DateTo
	 * @return void
	 */
	public void setDateTo(Date p_DateTo) {
		m_DateTo = p_DateTo;
	}

	/**
	 * Get Product Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 12:45:55
	 * @return
	 * @return String
	 */
	public String getProductName() {
		return m_ProductName;
	}
	
	/**
	 * Get Image URL
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 13:19:59
	 * @return
	 * @return String
	 */
	public String getImageURL() {
		return m_ImageURL;
	}
		
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public SP_DisplayRecordItem createFromParcel(Parcel parcel) {
			return new SP_DisplayRecordItem(parcel);
		}
		public SP_DisplayRecordItem[] newArray(int size) {
			return new SP_DisplayRecordItem[size];
		}
	};
	
	
	
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(m_M_Product_ID);
		parcel.writeString(m_ProductName);
		parcel.writeString(m_ImageURL);
		parcel.writeDouble(m_QtySuggested);
		parcel.writeInt(m_Suggested_UOM_ID);
		parcel.writeString(m_SuggestedUOMSymbol);
		parcel.writeDouble(m_QtyDosage);
		parcel.writeInt(m_Dosage_UOM_ID);
		parcel.writeString(m_DosageUOMSymbol);
		parcel.writeDouble(m_Qty);
		parcel.writeInt(m_C_UOM_ID);
		parcel.writeString(m_OrderUOMSymbol);
		parcel.writeInt(m_DayFrom);
		parcel.writeInt(m_DayTo);
		parcel.writeLong((m_DateFrom != null? m_DateFrom.getTime(): 0));
		parcel.writeLong((m_DateTo != null? m_DateTo.getTime(): 0));
		parcel.writeInt(m_FTA_ProductToApply_ID);
	}
	
	/**
	 * Read
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 12:54:52
	 * @param parcel
	 * @return void
	 */
	private void readToParcel(Parcel parcel) {
		m_M_Product_ID = parcel.readInt();
		m_ProductName = parcel.readString();
		m_ImageURL = parcel.readString();
		m_QtySuggested = parcel.readDouble();
		m_Suggested_UOM_ID = parcel.readInt();
		m_SuggestedUOMSymbol = parcel.readString();
		m_QtyDosage = parcel.readDouble();
		m_Dosage_UOM_ID = parcel.readInt();
		m_DosageUOMSymbol = parcel.readString();
		m_Qty = parcel.readDouble();
		m_C_UOM_ID = parcel.readInt();
		m_OrderUOMSymbol = parcel.readString();
		m_DayFrom = parcel.readInt();
		m_DayTo = parcel.readInt();
		m_DateFrom = new Date(parcel.readLong());
		m_DateTo = new Date(parcel.readLong());
		m_FTA_ProductToApply_ID = parcel.readInt();
	}
	
	@Override
	public String toString() {
		return "SP_DisplayRecordItem [m_M_Product_ID=" + m_M_Product_ID
				+ ", m_ProductName=" + m_ProductName + ", m_QtySuggested="
				+ m_QtySuggested + ", m_Suggested_UOM_ID=" + m_Suggested_UOM_ID
				+ ", m_SuggestedUOMSymbol=" + m_SuggestedUOMSymbol
				+ ", m_QtyDosage=" + m_QtyDosage + ", m_Dosage_UOM_ID="
				+ m_Dosage_UOM_ID + ", m_DosageUOMSymbol=" + m_DosageUOMSymbol
				+ ", m_Qty=" + m_Qty + ", m_C_UOM_ID=" + m_C_UOM_ID
				+ ", m_OrderUOMSymbol=" + m_OrderUOMSymbol + ", m_DayFrom="
				+ m_DayFrom + ", m_FTA_ProductToApply_ID="
				+ m_FTA_ProductToApply_ID + ", m_DayTo=" + m_DayTo
				+ ", m_DateFrom=" + m_DateFrom + ", m_DateTo=" + m_DateTo
				+ ", m_ImageURL=" + m_ImageURL + "]";
	}

}
