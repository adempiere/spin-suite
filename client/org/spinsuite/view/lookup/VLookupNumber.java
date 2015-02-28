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
package org.spinsuite.view.lookup;


import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.spinsuite.base.R;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.TabParameter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Feb 27, 2015, 11:45:32 PM
 *	<li> Selected All on focus
 * 	@see https://adempiere.atlassian.net/browse/SPIN-2
 */
public class VLookupNumber extends GridField {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 */
	public VLookupNumber(Context context) {
		super(context);
		init();
	}

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param attrs
	 */
	public VLookupNumber(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public VLookupNumber(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param m_field
	 */
	public VLookupNumber(Context context, InfoField m_field) {
		this(context, m_field, null);
	}
	
	/**
	 * With Tab Parameter
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param m_field
	 * @param tabParam
	 */
	public VLookupNumber(Context context, InfoField m_field, TabParameter tabParam) {
		super(context, m_field, tabParam);
		init();
	}
	
	/**	String Edit Text	*/
	private EditText 		v_Number 		= null;
	/**	Old Value			*/
	private BigDecimal 		m_OldValue 		= null;
	/**	Column Decimal Format		*/
	private DecimalFormat	m_DecimalFormat = null;

	@SuppressLint("RtlHardcoded")
	@Override
	protected void init() {
		v_Number = new EditText(getContext());
		v_Number.setTextAppearance(getContext(), R.style.TextDynamicTabEditText);
		//	
		v_Number.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus)
					((EditText)v).selectAll();
				//	Listener
				event();
			}
		});
		//	Set Hint
		v_Number.setHint(m_field.Name);
		//	Set Gravity
		v_Number.setGravity(Gravity.RIGHT);
		setEnabled(!m_field.IsReadOnly);
		//	Set Display Type
		v_Number.setInputType(DisplayType.getInputType(m_field.DisplayType));
		//	Selected All on Focus
		v_Number.setSelectAllOnFocus(true);
		//	
		//	Set Format
		m_DecimalFormat = DisplayType.getNumberFormat(getContext(), m_field.DisplayType, 
				m_field.FormatPattern);
		//	Add to View
		addView(v_Number);
	}
	
	/**
	 * Listener
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void event() {
        //	Listener
        if(m_Listener != null)
        	m_Listener.onFieldEvent(this);
	}

	@Override
	public void setValue(Object value) {
		BigDecimal m_Value = DisplayType.getNumber(v_Number.getText().toString());
		//	Set Old Value
		m_OldValue = m_Value;
		//	
		if(value == null) {
			v_Number.setText("");
			m_Value = Env.ZERO;
		} else {
			if(m_field.DisplayType == DisplayType.INTEGER)
				m_Value = new BigDecimal((Integer) value);
			else
				m_Value = (BigDecimal) value;
		}
		//	Set Edit Text
		if(m_Value != null)
			v_Number.setText(m_DecimalFormat.format(m_Value));
	}

	@Override
	public Object getValue() {
		BigDecimal m_Value = DisplayType.getNumber(getContext(), v_Number.getText().toString(), m_field.DisplayType);
		if(m_field.DisplayType == DisplayType.INTEGER)
			return m_Value.intValue();
		//	Default
		return m_Value;
	}
	
	@Override
	public Object getOldValue() {
		if(m_field.DisplayType == DisplayType.INTEGER) {
			if(m_OldValue == null)
				return 0;
			else
				m_OldValue.intValue();
		}
		//	Default
		return m_OldValue;
	}

	@Override
	public boolean isEmpty() {
		return (v_Number.getText() == null 
				|| v_Number.getText().length() == 0);
	}

	@Override
	public View getChildView() {
		return v_Number;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		v_Number.setEnabled(enabled);
	}

	@Override
	public String getDisplayValue() {
		return v_Number.getText().toString();
	}

	@Override
	public void setValueAndOldValue(Object value) {
		BigDecimal m_Value = DisplayType.getNumber(v_Number.getText().toString());
		//	
		if(value == null) {
			v_Number.setText("");
		} else {
			if(m_field.DisplayType == DisplayType.INTEGER)
				m_Value = new BigDecimal((Integer) value);
			else
				m_Value = (BigDecimal) value;
		}
		//	Set Edit Text
		if(m_Value != null)
			v_Number.setText(m_DecimalFormat.format(m_Value));
	}

}
