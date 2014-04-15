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
package org.spinsuite.view.report;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public abstract class VReportLookup extends LinearLayout {
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 16:46:15
	 * @param context
	 */
	public VReportLookup(Context context) {
		super(context);
		mainInit();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 16:46:15
	 * @param context
	 * @param attrs
	 */
	public VReportLookup(Context context, AttributeSet attrs) {
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
	public VReportLookup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mainInit();
	}
	
	/**
	 * With Field
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:28:25
	 * @param context
	 * @param m_field
	 */
	public VReportLookup(Context context, InfoReportField m_field) {
		super(context);
		this.m_field = m_field;
		mainInit();
	}
	
	/**	Text View			*/
	protected TextView				v_Label = null;
	/**	Field Object		*/
	protected InfoReportField 	m_field = null;
	
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
	 * Get Field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 22:22:21
	 * @return
	 * @return VOInfoField
	 */
	public InfoReportField getField(){
		return m_field;
	}
	
	/**
	 * Set Only Text Field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 11:05:08
	 * @return void
	 */
	public void setOnlyField(){
		v_Label.setVisibility(GONE);
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
	 * Is Empty
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 10:15:24
	 * @return
	 * @return boolean
	 */
	public abstract boolean isEmpty();
}
