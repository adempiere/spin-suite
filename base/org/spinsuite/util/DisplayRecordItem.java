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
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.util;

import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class DisplayRecordItem implements Parcelable {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/03/2014, 12:53:53
	 */
	public DisplayRecordItem(){
		
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/03/2014, 12:44:25
	 * @param pRecord_ID
	 * @param pValue
	 * @param pDescription
	 */
	public DisplayRecordItem(int[] p_Record_ID, String pValue) {
		this.m_Record_ID = p_Record_ID;
		this.m_Value = pValue;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 19/10/2014, 22:07:23
	 * @param p_Record_ID
	 * @param pValue
	 */
	public DisplayRecordItem(int p_Record_ID, String pValue) {
		this(new int[]{p_Record_ID}, pValue);
	}
	
	/**
	 * With Image URL
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/03/2014, 13:19:26
	 * @param pRecord_ID
	 * @param pValue
	 * @param pDescription
	 * @param pImageURL
	 */
	public DisplayRecordItem(int[] p_Record_ID, String pValue, String pImageURL) {
		this(p_Record_ID, pValue);
		this.m_ImageURL = pImageURL;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 19/10/2014, 22:07:44
	 * @param p_Record_ID
	 * @param pValue
	 * @param pImageURL
	 */
	public DisplayRecordItem(int p_Record_ID, String pValue, String pImageURL) {
		this(new int[]{p_Record_ID}, pValue, pImageURL);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 19/10/2014, 22:45:32
	 * @param p_Record_ID
	 * @param p_KeyColumns
	 * @param pValue
	 * @param m_DocStatus
	 * @param pImageURL
	 */
	public DisplayRecordItem(int[] p_Record_ID, String[] p_KeyColumns, String pValue, String m_DocStatus, String pImageURL) {
		this(p_Record_ID, pValue);
		this.m_KeyColumns = p_KeyColumns;
		this.m_ImageURL = pImageURL;
		this.m_DocStatus = m_DocStatus;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/03/2014, 12:55:11
	 * @param parcel
	 */
	public DisplayRecordItem(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	/**	Identifier				*/
	private int[] 		m_Record_ID 	= new int[]{0};
	/**	Key Column				*/
	private String[]	m_KeyColumns	= null;
	/**	Value					*/
	private String		m_Value 		= null;
	/**	Document Status			*/
	private String 		m_DocStatus 	= null;
	/**	Image URL				*/
	private String 		m_ImageURL 		= null;
	
	/**
	 * Get Record Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/03/2014, 12:44:56
	 * @return
	 * @return int
	 */
	public int getRecord_ID() {
		return m_Record_ID[0];
	}
	
	/**
	 * Get Keys
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 19/10/2014, 21:44:24
	 * @return
	 * @return int[]
	 */
	public int[] getKeys() {
		return m_Record_ID;
	}
	
	/**
	 * Get Key Columns
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 13:49:31
	 * @return
	 * @return String[]
	 */
	public String[] getKeyColumns() {
		return m_KeyColumns;
	}
	
	/**
	 * Get Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/03/2014, 12:45:55
	 * @return
	 * @return String
	 */
	public String getValue() {
		return m_Value;
	}
	
	/**
	 * Get Image URL
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/03/2014, 13:19:59
	 * @return
	 * @return String
	 */
	public String getImageURL() {
		return m_ImageURL;
	}
	
	/**
	 * Get Document Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/09/2014, 16:11:51
	 * @return
	 * @return String
	 */
	public String getDocStatus() {
		return m_DocStatus;
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public DisplayRecordItem createFromParcel(Parcel parcel) {
			return new DisplayRecordItem(parcel);
		}
		public DisplayRecordItem[] newArray(int size) {
			return new DisplayRecordItem[size];
		}
	};
	
	
	
	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(m_Record_ID.length);
		parcel.writeIntArray(m_Record_ID);
		parcel.writeString(m_Value);
		parcel.writeString(m_ImageURL);
		parcel.writeString(m_DocStatus);
	}
	
	/**
	 * Read
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/03/2014, 12:54:52
	 * @param parcel
	 * @return void
	 */
	private void readToParcel(Parcel parcel) {
		int length = parcel.readInt();
		m_Record_ID = new int[length];
		parcel.readIntArray(m_Record_ID);
		m_Value = parcel.readString();
		m_ImageURL = parcel.readString();
		m_DocStatus = parcel.readString();
	}

	@Override
	public String toString() {
		return "DisplayRecordItem [m_Record_ID=" + Arrays.toString(m_Record_ID)
				+ ", m_Value=" + m_Value + ", m_DocStatus=" + m_DocStatus
				+ ", m_ImageURL=" + m_ImageURL + "]";
	}
}
