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

import org.spinsuite.util.DisplayRecordItem;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class VLookupSearch extends VLookup {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 08:14:50
	 * @param activity
	 */
	public VLookupSearch(Activity activity) {
		super(activity);
		this.activity = activity;
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 08:14:50
	 * @param activity
	 * @param attrs
	 */
	public VLookupSearch(Activity activity, AttributeSet attrs) {
		super(activity, attrs);
		this.activity = activity;
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 08:14:50
	 * @param activity
	 * @param attrs
	 * @param defStyle
	 */
	public VLookupSearch(Activity activity, AttributeSet attrs, int defStyle) {
		super(activity, attrs, defStyle);
		this.activity = activity;
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 08:14:50
	 * @param activity
	 * @param m_field
	 */
	public VLookupSearch(Activity activity, InfoField m_field) {
		super(activity, m_field);
		this.activity = activity;
		init();
	}
	
	/**	Search 				*/
	private VSearch 		v_Search = null;
	/**	Activity from		*/
	private Activity 		activity = null;
	
	@Override
	protected void init() {
		//activity.ona
		v_Search = new VSearch(activity, m_field);
		setEnabled(!m_field.IsReadOnly);
		//	Add to View
		addView(v_Search);
	}

	@Override
	public void setValue(Object value) {
		//v_Search.setItem(item);
	}

	@Override
	public Object getValue() {
		return v_Search.getRecord_ID();
	}

	@Override
	public boolean isEmpty() {
		return (v_Search.getRecord_ID() == -1);
	}

	@Override
	public View getChildView() {
		return v_Search;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		v_Search.setEnabled(enabled);
	}
	
	/**
	 * Set Item
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 22:39:23
	 * @param item
	 * @return void
	 */
	public void setItem(DisplayRecordItem item){
		v_Search.setItem(item);
	}

	@Override
	public String getDisplayValue() {
		return v_Search.getValue();
	}

}
