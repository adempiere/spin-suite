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
package org.spinsuite.print.layout;


import org.spinsuite.print.InfoReportField;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class VReportLookupString extends VReportLookup {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 14:00:48
	 * @param context
	 */
	public VReportLookupString(Context context) {
		super(context);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 14:00:48
	 * @param context
	 * @param attrs
	 */
	public VReportLookupString(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 14:00:48
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public VReportLookupString(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 14:00:48
	 * @param context
	 * @param m_field
	 */
	public VReportLookupString(Context context, InfoReportField m_field) {
		super(context, m_field);
		init();
	}
	
	
	/**	String Text	View	*/
	private TextView 		v_String = null;

	@Override
	protected void init() {
		v_String = new TextView(getContext());
		//	Add to View
		addView(v_String);
	}
	
	@Override
	public void setValue(Object value) {
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
	public boolean isEmpty() {
		return (v_String.getText() == null 
				|| v_String.getText().length() == 0);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		v_String.setEnabled(enabled);
	}

}
