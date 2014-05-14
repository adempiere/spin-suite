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
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public abstract class VLookup extends LinearLayout {
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 16:46:15
	 * @param context
	 */
	public VLookup(Context context) {
		super(context);
		mainInit();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 16:46:15
	 * @param context
	 * @param attrs
	 */
	public VLookup(Context context, AttributeSet attrs) {
		super(context, attrs);
		mainInit();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 16:46:15
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public VLookup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mainInit();
	}
	
	/**
	 * With Field
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:28:25
	 * @param context
	 * @param m_field
	 * @param m_TabParam
	 */
	public VLookup(Context context, InfoField m_field, TabParameter m_TabParam) {
		super(context);
		this.m_field = m_field;
		this.m_TabParam = m_TabParam;
		mainInit();
	}
	
	/**	Text View			*/
	protected TextView		v_Label = null;
	/**	Field Object		*/
	protected InfoField 	m_field = null;
	/**	Parameter			*/
	protected TabParameter 	m_TabParam = null;
	
	/**
	 * Main Init
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:31:56
	 * @return void
	 */
	private void mainInit(){
		//	Set Parameter
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.MATCH_PARENT));
		//	Set Orientation
		setOrientation(VERTICAL);
		//	Set Label
		v_Label = new TextView(getContext());
		//	
		if(m_field != null)
			v_Label.setText(m_field.Name);
		//	Add to View
		addView(v_Label);
	}
	
	/**
	 * Get Tab No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/05/2014, 14:04:53
	 * @return
	 * @return int
	 */
	public int getTabNo() {
		if(m_TabParam != null)
			return m_TabParam.getTabNo();
		return 0;
	}
	
	/**
	 * Get Activity No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/05/2014, 14:04:10
	 * @return
	 * @return int
	 */
	public int getActivityNo() { 
		if(m_TabParam != null)
			return m_TabParam.getActivityNo();
		return 0;
	}
	
	/**
	 * Get Field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 22:22:21
	 * @return
	 * @return VOInfoField
	 */
	public InfoField getField(){
		return m_field;
	}
	
	/**
	 * Is Mandatory
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 10:11:43
	 * @return
	 * @return boolean
	 */
	public boolean isMandatory(){
		return m_field.IsMandatory;
	}
	
	/**
	 * Init View
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:27:04
	 * @return
	 * @return void
	 */
	protected abstract void init();
	
	/**
	 * Set Value to Lookup
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 08:31:00
	 * @param value
	 * @return void
	 */
	public abstract void setValue(Object value);
	
	/**
	 * Get Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 08:31:05
	 * @return
	 * @return Object
	 */
	public abstract Object getValue();
	
	/**
	 * Get Display Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/04/2014, 10:34:36
	 * @return
	 * @return String
	 */
	public abstract String getDisplayValue();
	
	/**
	 * Is Empty
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 10:15:24
	 * @return
	 * @return boolean
	 */
	public abstract boolean isEmpty();
	
	/**
	 * Get child view
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:14:04
	 * @return
	 * @return View
	 */
	public abstract View getChildView();

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VLookup [v_Label=" + v_Label + ", m_field=" + m_field + "]";
	}
}
