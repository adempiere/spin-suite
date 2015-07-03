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
package org.spinsuite.adapters;

import java.util.ArrayList;

import org.spinsuite.util.TabHandler;
import org.spinsuite.util.TabParameter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class FragmentTabArray {
	
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 23/08/2014, 11:41:24
	 * @param activity
	 */
	public FragmentTabArray (FragmentActivity activity) {
		this.activity = activity;
	}
	
	/**	Activity						*/
	private FragmentActivity 		activity;
	/**	Array with Tab  Listener		*/
	private ArrayList<TabHandler> 	m_Array = new ArrayList<TabHandler>();
	
	/**
	 * Add Tab to Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 23/08/2014, 11:41:55
	 * @param tag
	 * @param p_Title
	 * @param clazz
	 * @param args
	 * @return TabListener
	 */
	public void addTab(String tag, String p_Title, Class<?> clazz, TabParameter tabParameter, Bundle args) {
		TabHandler tabListener = new TabHandler(activity, tag, p_Title, clazz, tabParameter, args);
		m_Array.add(tabListener);
	}
	
	/**
	 * Get Tab Listener from index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 23/08/2014, 11:48:53
	 * @param index
	 * @return
	 * @return TabListener
	 */
	public TabHandler getTab(int index) {
		return m_Array.get(index);
	}
	
	/**
	 * Get Size
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public int size() {
		return m_Array.size();
	}
	
}
