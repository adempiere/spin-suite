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
package org.spinsuite.util;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class DisplayRecordItem implements Parcelable {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 12:53:53
	 */
	public DisplayRecordItem(){
		
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 12:44:25
	 * @param pRecord_ID
	 * @param pValue
	 * @param pDescription
	 */
	public DisplayRecordItem(int pRecord_ID, String pValue) {
		this.m_Record_ID = pRecord_ID;
		this.m_Value = pValue;
	}
	
	/**
	 * With Image URL
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 13:19:26
	 * @param pRecord_ID
	 * @param pValue
	 * @param pDescription
	 * @param pImageURL
	 */
	public DisplayRecordItem(int pRecord_ID, String pValue, String pImageURL) {
		this(pRecord_ID, pValue);
		this.m_ImageURL = pImageURL;
	}
	
	/**
	 * With Document Status
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/09/2014, 11:36:57
	 * @param pRecord_ID
	 * @param pValue
	 * @param m_DocStatus
	 * @param pImageURL
	 */
	public DisplayRecordItem(int pRecord_ID, String pValue, String m_DocStatus, String pImageURL) {
		this(pRecord_ID, pValue);
		this.m_ImageURL = pImageURL;
		this.m_DocStatus = m_DocStatus;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 12:55:11
	 * @param parcel
	 */
	public DisplayRecordItem(Parcel parcel){
		this();
		readToParcel(parcel);
	}
	
	/**	Identifier				*/
	private int 	m_Record_ID 	= 0;
	/**	Value					*/
	private String	m_Value 		= null;
	/**	Document Status			*/
	private String 	m_DocStatus 	= null;
	/**	Image URL				*/
	private String 	m_ImageURL 		= null;
	
	/**
	 * Get Record Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 12:44:56
	 * @return
	 * @return int
	 */
	public int getRecord_ID() {
		return m_Record_ID;
	}
	
	/**
	 * Get Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 12:45:55
	 * @return
	 * @return String
	 */
	public String getValue() {
		return m_Value;
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
	
	/**
	 * Get Document Status
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/09/2014, 16:11:51
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
		parcel.writeInt(m_Record_ID);
		parcel.writeString(m_Value);
		parcel.writeString(m_ImageURL);
		parcel.writeString(m_DocStatus);
	}
	
	/**
	 * Read
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 12:54:52
	 * @param parcel
	 * @return void
	 */
	private void readToParcel(Parcel parcel) {
		m_Record_ID = parcel.readInt();
		m_Value = parcel.readString();
		m_ImageURL = parcel.readString();
		m_DocStatus = parcel.readString();
	}

	@Override
	public String toString() {
		return "DisplayRecordItem [m_Record_ID=" + m_Record_ID + ", m_Value="
				+ m_Value + ", m_DocStatus=" + m_DocStatus + ", m_ImageURL="
				+ m_ImageURL + "]";
	}
}
