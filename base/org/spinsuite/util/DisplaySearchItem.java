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

import android.os.Parcel;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Jul 14, 2015, 4:17:24 PM
 *
 */
public class DisplaySearchItem extends DisplayRecordItem {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Record_ID
	 * @param pValue
	 */
	public DisplaySearchItem(int[] p_Record_ID, String pValue) {
		super(p_Record_ID, pValue);
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Record_ID
	 * @param pValue
	 */
	public DisplaySearchItem(int p_Record_ID, String pValue) {
		super(p_Record_ID, pValue);
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Record_ID
	 * @param pValue
	 * @param pImageURL
	 */
	public DisplaySearchItem(int[] p_Record_ID, String pValue, String pImageURL) {
		super(p_Record_ID, pValue, pImageURL);
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Record_ID
	 * @param pValue
	 * @param pImageURL
	 */
	public DisplaySearchItem(int p_Record_ID, String pValue, String pImageURL) {
		super(p_Record_ID, pValue, pImageURL);
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Record_ID
	 * @param p_KeyColumns
	 * @param pValue
	 * @param m_DocStatus
	 */
	public DisplaySearchItem(int[] p_Record_ID, String[] p_KeyColumns,
			String pValue, String m_DocStatus, IdentifierValueWrapper[] p_DisplayValues) {
		super(p_Record_ID, p_KeyColumns, pValue, m_DocStatus, null);
		m_DisplayValues = p_DisplayValues;
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param parcel
	 */
	public DisplaySearchItem(Parcel parcel) {
		super(parcel);
	}
	
	/**	Key Name Pair		*/
	private IdentifierValueWrapper[] 	m_DisplayValues = null;

	/**
	 * @return the m_DisplayValues
	 */
	public IdentifierValueWrapper[] getDisplayValues() {
		return m_DisplayValues;
	}

	/**
	 * @param m_DisplayValues the m_DisplayValues to set
	 */
	public void setDisplayValues(IdentifierValueWrapper[] m_DisplayValues) {
		this.m_DisplayValues = m_DisplayValues;
	}
}