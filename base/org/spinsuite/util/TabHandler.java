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
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.util;

import org.spinsuite.base.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class TabHandler {
    
	
	/**
     * 
     * *** Constructor ***
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 23/08/2014, 14:56:24
     * @param activity
     * @param tag
     * @param clz
     * @param args
     */
    public TabHandler(FragmentActivity activity, String tag, Class<?> clz, TabParameter tabParameter, Bundle args) {
        m_Activity = activity;
        m_Tag = tag;
        m_Class = clz;
        m_Args = args;
        m_Fragment = m_Activity.getSupportFragmentManager().findFragmentByTag(m_Tag);
        m_TabParameter = tabParameter;
    }
	
	/**	Fragment			*/
	private 		Fragment 			m_Fragment;
	/**	Activity			*/
    private 		FragmentActivity 	m_Activity;
    /**	Parameter			*/
    private 		Bundle 				m_Args;
    /**	Tag					*/
    private final 	String 				m_Tag;
    /**	Class				*/
    private final 	Class<?> 			m_Class;
    /**	Tab Parameter		*/
    private 		TabParameter		m_TabParameter;
    
    /**
     * Get Fragment
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 23/08/2014, 11:50:44
     * @return
     * @return Fragment
     */
    public Fragment getFragment() {
    	return m_Fragment;
    }
    
    /**
     * Get Arguments
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/08/2014, 10:28:41
     * @return
     * @return Bundle
     */
    public Bundle getArgs() {
    	return m_Args;
    }
    
    /**
     * Get Tag
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/08/2014, 10:24:50
     * @return
     * @return String
     */
    public String getTag() {
    	return m_Tag;
    }
    
    /**
     * Get Class for Fragment
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/08/2014, 10:24:14
     * @return
     * @return Class<?>
     */
    public Class<?> getClazz() {
    	return m_Class;
    }
    
    /**
     * Get Class Name
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/08/2014, 10:23:47
     * @return
     * @return String
     */
    public String getClassName() {
    	if(m_Class == null)
    		return null;
    	return m_Class.getName();
    }
    
    /**
     * Load Fragment
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/08/2014, 10:51:24
     * @param m_FragmentManager
     * @return void
     */
    public void loadFragment(FragmentManager m_FragmentManager) {
        //	Begin Transaction
        FragmentTransaction transaction = m_FragmentManager.beginTransaction();
        //	
    	if (m_Fragment == null) {
            m_Fragment = Fragment.instantiate(m_Activity, m_Class.getName(), m_Args);
            //	Replace Fragment
            transaction.add(R.id.content_frame, m_Fragment, m_Tag);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        } else {
            if (m_Fragment.isHidden()) {
            	transaction.show(m_Fragment);
            }
        }
    	//	Commit
        transaction.commit();
    }//	android.R.id.content R.id.content_frame
    
    /**
     * Unload Fragment
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/08/2014, 10:51:09
     * @param m_FragmentManager
     * @return void
     */
    public void unLoadFragment(FragmentManager m_FragmentManager) {
    	if (m_Fragment != null) {
            //	Begin Transaction
            FragmentTransaction transaction = m_FragmentManager.beginTransaction();
            transaction.hide(m_Fragment);
        	//	Commit
            transaction.commit();
        }
    }
    
    /**
     * Get Tab Parameter
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/09/2014, 21:45:19
     * @return
     * @return TabParameter
     */
    public TabParameter getTabParameter() {
    	return m_TabParameter;
    }
}