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
package org.spinsuite.adapters;

import java.util.ArrayList;

import org.spinsuite.view.TabListener;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class FragmentTabArray {
	
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/08/2014, 11:41:24
	 * @param activity
	 */
	public FragmentTabArray (Activity activity) {
		this.activity = activity;
	}
	
	/**	Activity						*/
	private Activity activity;
	/**	Array with Tab  Listener		*/
	private ArrayList<TabListener> m_Array = new ArrayList<TabListener>();
	
	/**
	 * Add Tab to Array
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/08/2014, 11:41:55
	 * @param tag
	 * @param clazz
	 * @param args
	 * @return TabListener
	 */
	public TabListener addTab(String tag, Class<?> clazz, Bundle args) {
		TabListener tabListener = new TabListener(activity, tag, clazz, args);
		m_Array.add(tabListener);
		return tabListener;
	}
	
	/**
	 * Get Tab Listener from index
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/08/2014, 11:48:53
	 * @param index
	 * @return
	 * @return TabListener
	 */
	public TabListener getTab(int index) {
		return m_Array.get(index);
	}
	
}
