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
package org.spinsuite.util;

import java.math.BigDecimal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/6/2015, 15:54:04
 *
 */
public class SP_DisplayRecordItem implements Parcelable {

	/**	Identifier				*/
	private int 		product_ID 			= 0;
	/**	Value of Product		*/
	private String 		value				= "";
	/**	Name of Product		*/
	private String 		name				= "";
	/**	Description of Product		*/
	private String 		description			= "";
	/**	UOM Symbol			*/
	private String 		uOMSymbol			= "";
	/**	Qty Entered			*/
	private BigDecimal 	qty				= Env.ZERO;	
	
	private int p_C_OrderLine_ID		= 0;
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public SP_DisplayRecordItem(){
	}
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param parcel
	 */
	public SP_DisplayRecordItem(Parcel parcel){
		this();
		readToParcel(parcel);
	}

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param product_ID
	 * @param value
	 * @param name
	 * @param description
	 * @param uOMSymbol
	 * @param qty
	 */
	public SP_DisplayRecordItem(int product_ID, String value, String name,
			String description, String uOMSymbol, BigDecimal qty,
			int p_C_OrderLine_ID) {
		super();
		this.product_ID = product_ID;
		this.value = value;
		this.name = name;
		this.description = description;
		this.uOMSymbol = uOMSymbol;
		this.qty = qty;
		this.p_C_OrderLine_ID = p_C_OrderLine_ID;
	}
	
	/**
	 * @return the product_ID
	 */
	public int getProduct_ID() {
		return product_ID;
	}

	/**
	 * @param product_ID the product_ID to set
	 */
	public void setProduct_ID(int product_ID) {
		this.product_ID = product_ID;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the uOMSymbol
	 */
	public String getuOMSymbol() {
		return uOMSymbol;
	}

	/**
	 * @param uOMSymbol the uOMSymbol to set
	 */
	public void setuOMSymbol(String uOMSymbol) {
		this.uOMSymbol = uOMSymbol;
	}

	/**
	 * @return the qty
	 */
	public BigDecimal getQty() {
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	/**
	 * @return the p_C_OrderLine_ID
	 */
	public int getP_C_OrderLine_ID() {
		return p_C_OrderLine_ID;
	}
	
	/**
	 * @param p_C_OrderLine_ID the p_C_OrderLine_ID to set
	 */
	public void setP_C_OrderLine_ID(int p_C_OrderLine_ID) {
		this.p_C_OrderLine_ID = p_C_OrderLine_ID;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel paramParcel, int flags) {
		paramParcel.writeInt(product_ID);
		paramParcel.writeString(value);
		paramParcel.writeString(name);
		paramParcel.writeString(description);
		paramParcel.writeString(uOMSymbol);
		paramParcel.writeDouble(qty.doubleValue());
		paramParcel.writeInt(p_C_OrderLine_ID);
		
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

	/**
	 * Read
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param parcel
	 * @return void
	 */
	private void readToParcel(Parcel parcel) {
		product_ID = parcel.readInt();
		value = parcel.readString();
		name = parcel.readString();
		description = parcel.readString();
		uOMSymbol = parcel.readString();
		qty = new BigDecimal(parcel.readDouble());
		p_C_OrderLine_ID = parcel.readInt();
	}
	
	@Override
	public String toString() {
		return "SP_DisplayRecordItem [product_ID=" + product_ID
				+ ", value=" + value
				+ ", name=" + name
				+ ", description=" + description
				+ ", uOMSymbol=" + uOMSymbol
				+ ", qty=" + qty
				+ "]";
	}

}
