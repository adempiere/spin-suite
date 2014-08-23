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
package org.spinsuite.util;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class TabListener implements ActionBar.TabListener {
    
	
	/**
     * 
     * *** Constructor ***
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/08/2014, 14:56:24
     * @param activity
     * @param tag
     * @param clz
     * @param args
     */
    public TabListener(Activity activity, String tag, Class<?> clz, Bundle args) {
        m_Activity = activity;
        m_Tag = tag;
        m_Class = clz;
        m_Args = args;
        m_Fragment=m_Activity.getFragmentManager().findFragmentByTag(m_Tag);
    }
	
	/**	Fragment			*/
	private 		Fragment 		m_Fragment;
	/**	Activity			*/
    private 		Activity 		m_Activity;
    /**	Parameter			*/
    private 		Bundle 			m_Args;
    /**	Tag					*/
    private final 	String 			m_Tag;
    /**	Class				*/
    private final 	Class<?> 		m_Class;

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        if (m_Fragment == null) {
            m_Fragment = Fragment.instantiate(m_Activity, m_Class.getName(), m_Args);
            ft.replace(android.R.id.content, m_Fragment, m_Tag);
        } else {
            if (m_Fragment.isDetached()) {
                ft.attach(m_Fragment);
            }
        }
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (m_Fragment != null) {
            ft.detach(m_Fragment);
        }
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    	
    }
    
    /**
     * Get Fragment
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/08/2014, 11:50:44
     * @return
     * @return Fragment
     */
    public Fragment getFragment() {
    	return m_Fragment;
    }
}