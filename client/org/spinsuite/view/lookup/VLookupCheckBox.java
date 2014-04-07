/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                                           *
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
package org.spinsuite.view.lookup;


import org.spinsuite.base.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class VLookupCheckBox extends VLookup {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:31:59
	 * @param context
	 */
	public VLookupCheckBox(Context context) {
		super(context);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:31:59
	 * @param context
	 * @param attrs
	 */
	public VLookupCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:31:59
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public VLookupCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:31:59
	 * @param context
	 * @param m_field
	 */
	public VLookupCheckBox(Context context, InfoField m_field) {
		super(context, m_field);
		init();
	}
	
	/**	Check Box 			*/
	private CheckBox 		v_CheckBox = null;
	
	@Override
	protected void init() {
		v_CheckBox = new CheckBox(getContext());
		v_CheckBox.setGravity(Gravity.CENTER_VERTICAL);
		v_CheckBox.setText(m_field.Name);
		setEnabled(!m_field.IsReadOnly);
		//	Add to view
		addView(v_CheckBox);
		v_Label.setText("");
		
	}

	@Override
	public void setValue(Object value) {
		boolean flag = false;
		if(value != null){
			if(value instanceof Boolean)
				flag = (Boolean) value;
			else if(value instanceof String)
				flag = ((String) value).equals("Y");
		}
		//	Set Flag
		v_CheckBox.setChecked(flag);
	}

	@Override
	public Object getValue() {
		return v_CheckBox.isChecked();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public View getChildView() {
		return v_CheckBox;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		v_CheckBox.setEnabled(enabled);
	}

	@Override
	public String getDisplayValue() {
		//	Is Checked
		if(v_CheckBox.isChecked())
			return getContext().getString(R.string.msg_Yes);
		//	Default No
		return getContext().getString(R.string.msg_No);
	}
}
