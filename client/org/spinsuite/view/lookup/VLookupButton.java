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


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class VLookupButton extends VLookup {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:31:59
	 * @param context
	 */
	public VLookupButton(Context context) {
		super(context);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:31:59
	 * @param context
	 * @param attrs
	 */
	public VLookupButton(Context context, AttributeSet attrs) {
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
	public VLookupButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:31:59
	 * @param context
	 * @param m_field
	 */
	public VLookupButton(Context context, InfoField m_field) {
		super(context, m_field);
		init();
	}
	
	/**	Button 			*/
	private Button 		v_Button = null;
	
	@Override
	protected void init() {
		v_Button = new CheckBox(getContext());
		v_Button.setGravity(Gravity.CENTER_VERTICAL);
		v_Button.setText(m_field.Name);
		setEnabled(!m_field.IsReadOnly);
		//	Add to view
		addView(v_Button);
		v_Label.setText("");
		
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		super.setOnClickListener(l);
		v_Button.setOnClickListener(l);
	}
	
	@Override
	public void setValue(Object value) {
		/*boolean flag = false;
		if(value != null){
			if(value instanceof Boolean)
				flag = (Boolean) value;
			else if(value instanceof String)
				flag = ((String) value).equals("Y");
		}
		//	Set Flag
		v_CheckBox.setChecked(flag);*/
	}

	@Override
	public Object getValue() {
		return null;//v_CheckBox.isChecked();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public View getChildView() {
		return v_Button;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		v_Button.setEnabled(enabled);
	}

	@Override
	public String getDisplayValue() {
		return null;
	}
}
