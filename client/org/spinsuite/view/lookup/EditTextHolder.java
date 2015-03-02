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
package org.spinsuite.view.lookup;

import android.widget.EditText;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class EditTextHolder {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/08/2014, 01:15:35
	 */
	public EditTextHolder() {
		
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 23/10/2014, 9:43:56
	 * @param v_EditText
	 */
	public EditTextHolder(EditText v_EditText) {
		this.v_EditText = v_EditText;
	}
	
	/**	Edit Text			*/
	private EditText v_EditText = null;
	
	/**
	 * Set Edit Text
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/08/2014, 01:15:26
	 * @param p_EditText
	 * @return void
	 */
	public void setEditText(EditText p_EditText) {
		v_EditText = p_EditText;
	}
	
	/**
	 * Set Text
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/08/2014, 01:16:36
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
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/08/2014, 01:09:41
	 * @return
	 * @return EditText
	 */
	public EditText getEditText() {
		return v_EditText;
	}
	
	/**
	 * Get Text from Edit Text
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/08/2014, 02:10:05
	 * @return
	 * @return String
	 */
	public String getText() {
		if(v_EditText == null)
			return null;
		//	
		return v_EditText.getText().toString();
	}
}
