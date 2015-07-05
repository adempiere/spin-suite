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

import android.widget.EditText;

/**
 * 
 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com 15/6/2015, 15:41:47
 *
 */
public class EditTextHolder {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/08/2014, 01:15:35
	 */
	public EditTextHolder() {
	}
	
	/**	Edit Text			*/
	private EditText 	v_EditText = null;
	
	/**
	 * Set Edit Text
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_EditText
	 * @return void
	 */
	public void setEditText(EditText p_EditText) {
		v_EditText = p_EditText;
	}
	
	/**
	 * Set Text
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param text
	 * @return void
	 */
	public void setText(CharSequence text) {
		if(v_EditText == null)
			return;
		//	
		v_EditText.setText(text);
	}
	
	/**
	 * Get Edit Text
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return EditText
	 */
	public EditText getEditText() {
		return v_EditText;
	}
	
	/**
	 * Get Text from Edit Text
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getText() {
		if(v_EditText == null)
			return null;
		//	
		return v_EditText.getText().toString();
	}
	
	@Override
	public String toString() {
		return "EditTextHolder [v_EditText=" + v_EditText.getText() + "]";
	}
}
