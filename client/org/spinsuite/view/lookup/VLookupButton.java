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


import org.spinsuite.base.R;
import org.spinsuite.util.LoadActionMenu;
import org.spinsuite.util.TabParameter;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class VLookupButton extends GridField {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:31:59
	 * @param activity
	 */
	public VLookupButton(Activity activity) {
		super(activity);
		this.v_activity = activity;
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:31:59
	 * @param activity
	 * @param attrs
	 */
	public VLookupButton(Activity activity, AttributeSet attrs) {
		super(activity, attrs);
		this.v_activity = activity;
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:31:59
	 * @param activity
	 * @param attrs
	 * @param defStyle
	 */
	public VLookupButton(Activity activity, AttributeSet attrs, int defStyle) {
		super(activity, attrs, defStyle);
		this.v_activity = activity;
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:31:59
	 * @param activity
	 * @param m_field
	 */
	public VLookupButton(Activity activity, InfoField m_field) {
		this(activity, m_field, null);
	}
	
	/**
	 * With Tab Parameter
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/05/2014, 14:00:56
	 * @param context
	 * @param m_field
	 * @param tabParam
	 */
	public VLookupButton(Activity activity, InfoField m_field, TabParameter tabParam) {
		super(activity, m_field, tabParam);
		this.v_activity = activity;
		init();
	}
	
	/**	Button 				*/
	protected Button 		v_Button 		= null;
	/**	Value				*/
	private String 			m_Value 		= null;
	/**	Old Value			*/
	private String 			m_OldValue 		= null;
	/**	Load Action Menu	*/
	private LoadActionMenu	loadActionMenu 	= null;
	/**	Activity from		*/
	private Activity 		v_activity	 	= null;
	/**	Tab Parameter		*/
	private TabParameter	tabParam		= null;
	
	@Override	
	protected void init() {
		loadActionMenu = new LoadActionMenu(v_activity, true);
		v_Button = new Button(getContext());
		v_Button.setGravity(Gravity.CENTER_VERTICAL);
		v_Button.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				(int)getResources().getDimension(R.dimen.row_layout_height)));
		
		
		v_Button.setText(m_field.Name);
		setEnabled(!m_field.IsReadOnly);
		//	Add to view
		addView(v_Button);
		v_Label.setText("");
		v_Label.setVisibility(TextView.GONE);
		//	
		v_Button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadActionMenu.loadActionFromActivity(m_field, tabParam);
			}
		});
	}
	
	/**
	 * Set Tab Parameter
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/04/2014, 21:21:23
	 * @param tabParam
	 * @return void
	 */
	public void setTabParameter(TabParameter tabParam){
		this.tabParam = tabParam;
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		super.setOnClickListener(l);
		v_Button.setOnClickListener(l);
	}
	
	@Override
	public void setValue(Object value) {
		//	Set Old Value
		m_OldValue = m_Value;
		//	
		if(value == null
				|| ((String)value).length() <= 0)
			m_Value = null;
		else
			m_Value = (String)value;
	}

	@Override
	public Object getValue() {
		//	Default
		if(m_Value == null)
			return m_Value;
		return m_Value.toString();
	}
	
	@Override
	public Object getOldValue() {
		//	Default
		if(m_OldValue == null)
			return m_OldValue;
		return m_OldValue.toString();
	}

	@Override
	public boolean isEmpty() {
		return (m_Value == null 
				|| m_Value.length() == 0);
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
		return v_Button.getText().toString();
	}
	
	/**
	 * Get Own Activity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/09/2014, 18:06:40
	 * @return
	 * @return Activity
	 */
	public Activity getActivity() {
		return v_activity;
	}

	@Override
	public void setValueAndOldValue(Object value) {
		//	
		if(value == null
				|| ((String)value).length() <= 0)
			m_Value = null;
		else
			m_Value = (String)value;
		//	Set Old Value
		m_OldValue = m_Value;
	}
}
