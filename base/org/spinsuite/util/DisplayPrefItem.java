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

import org.spinsuite.view.T_Pref_Parent;

import android.graphics.Bitmap;
import android.os.Parcel;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Jun 13, 2015, 4:29:14 PM
 *
 */
public class DisplayPrefItem extends DisplayImageTextItem {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public DisplayPrefItem() {
		super();
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param pRecord_ID
	 * @param pValue
	 */
	public DisplayPrefItem(int pRecord_ID, String pValue) {
		super(pRecord_ID, pValue);
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param pRecord_ID
	 * @param pValue
	 * @param pImageURL
	 */
	public DisplayPrefItem(int pRecord_ID, String pValue,
			String pImageURL) {
		super(pRecord_ID, pValue, pImageURL);
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Value
	 * @param p_Description
	 * @param p_Image
	 * @param p_PreferencePane
	 */
	public DisplayPrefItem(String p_Value,
			String p_Description, Bitmap p_Image, T_Pref_Parent p_PreferencePane) {
		super(0, p_Value, p_Description, p_Image);
		m_PreferencePane = p_PreferencePane;
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param parcel
	 */
	public DisplayPrefItem(Parcel parcel) {
		super(parcel);
	}
	
	/**	Preference Pane				*/
	private T_Pref_Parent m_PreferencePane = null;
	
	/**
	 * Set a PReferences Pane
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_PreferencePane
	 * @return void
	 */
	public void setPrefPane(T_Pref_Parent p_PreferencePane) {
		m_PreferencePane = p_PreferencePane;
	}
	
	/**
	 * Get Preferences Pane
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return T_Pref_Parent
	 */
	public T_Pref_Parent getPrefPane() {
		return m_PreferencePane;
	}
}
