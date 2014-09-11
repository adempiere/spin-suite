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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.spinsuite.interfaces.OnFieldChangeListener;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.TabParameter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class VLookupDateBox extends GridField {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:36:43
	 * @param context
	 */
	public VLookupDateBox(Context context) {
		super(context);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:36:43
	 * @param context
	 * @param attrs
	 */
	public VLookupDateBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:36:43
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public VLookupDateBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:36:43
	 * @param context
	 * @param m_field
	 */
	public VLookupDateBox(Context context, InfoField m_field) {
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
	public VLookupDateBox(Context context, InfoField m_field, TabParameter tabParam) {
		super(context, m_field, tabParam);
		init();
	}

	/**	Date Box			*/
	private VDateBox		v_DateBox = null;
	/**	Old Value			*/
	private Date			m_OldValue = null;

	@Override
	protected void init() {
		initField();
		//	Set Date Box
		SimpleDateFormat format = null;
		if(m_field.FormatPattern != null)
			format = new SimpleDateFormat(m_field.FormatPattern);
		else
			format = DisplayType.getDateFormat(getContext(), m_field.DisplayType);
		//	Instance
		v_DateBox = new VDateBox(getContext(), format, this);
		setEnabled(!m_field.IsReadOnly);
		addView(v_DateBox);
	}
	
	/**
	 * Init Field if is null
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/09/2014, 11:11:58
	 * @return void
	 */
	private void initField() {
		if(m_field == null) {
			m_field = new InfoField();
			m_field.DisplayType = DisplayType.DATE;
		}
	}

	@Override
	public void setValue(Object value) {
		//	Set Old Value
		m_OldValue = v_DateBox.getDate();
		//	
		if(value != null
				&& value instanceof Date)
			v_DateBox.setDate((Date)value);
		else 
			v_DateBox.setDate(null);
	}

	@Override
	public Object getValue() {
		return v_DateBox.getDate();
	}

	@Override
	public Object getOldValue() {
		return m_OldValue;
	}
	
	@Override
	public boolean isEmpty() {
		return (v_DateBox.getDate() == null);
	}

	@Override
	public View getChildView() {
		return v_DateBox;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		v_DateBox.setEnabled(enabled);
	}

	@Override
	public String getDisplayValue() {
		return v_DateBox.getDisplayDate();
	}
	
	@Override
	public void setOnFieldChangeListener(OnFieldChangeListener m_Listener) {
		super.setOnFieldChangeListener(m_Listener);
		//	Listener
		v_DateBox.setOnFieldChangeListener(m_Listener);
	}

}
