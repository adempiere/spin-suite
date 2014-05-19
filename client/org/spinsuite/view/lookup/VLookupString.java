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


import org.spinsuite.util.TabParameter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class VLookupString extends GridField {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:25:23
	 * @param context
	 */
	public VLookupString(Context context) {
		super(context);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:25:23
	 * @param context
	 * @param attrs
	 */
	public VLookupString(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:25:23
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public VLookupString(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:40:26
	 * @param context
	 * @param m_field
	 */
	public VLookupString(Context context, InfoField m_field) {
		this(context, m_field, null);
	}
	
	/**
	 * With Tab Parameter
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/05/2014, 14:00:56
	 * @param context
	 * @param m_field
	 * @param tabParam
	 */
	public VLookupString(Context context, InfoField m_field, TabParameter tabParam) {
		super(context, m_field, tabParam);
		init();
	}
	
	/**	String Edit Text	*/
	private EditText 		v_String = null;
	/**	Old Value			*/
	private String 			m_OldValue = null;
	

	@Override
	protected void init() {
		v_String = new EditText(getContext());
		v_String.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
		        //	Listener
		        if(m_Listener != null)
		        	m_Listener.onFieldEvent(m_field);
			}
		});
		//	Set Hint
		v_String.setHint(m_field.Name);
		setEnabled(!m_field.IsReadOnly);
		//	Add to View
		addView(v_String);
	}
	
	/**
	 * Set Input Type
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:22:22
	 * @param type
	 * @return void
	 */
	public void setInputType(int type){
		v_String.setInputType(type);
	}

	@Override
	public void setValue(Object value) {
		//	Set Old Value
		m_OldValue = v_String.getText().toString();
		//	
		if(value == null
				|| ((String)value).length() <= 0)
			v_String.setText("");
		else
			v_String.setText((String)value);
	}

	@Override
	public Object getValue() {
		return v_String.getText().toString();
	}
	
	@Override
	public Object getOldValue() {
		return m_OldValue;
	}

	@Override
	public boolean isEmpty() {
		return (v_String.getText() == null 
				|| v_String.getText().length() == 0);
	}

	@Override
	public View getChildView() {
		return v_String;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		v_String.setEnabled(enabled);
	}

	@Override
	public String getDisplayValue() {
		return v_String.getText().toString();
	}

}
