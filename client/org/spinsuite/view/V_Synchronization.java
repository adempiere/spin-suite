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
package org.spinsuite.view;

import org.spinsuite.base.R;
import org.spinsuite.util.Env;
import org.spinsuite.view.lookup.LookupMenu;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class V_Synchronization extends FragmentActivity {
	
	/**	Action Bar					*/
    private ActionBar 				actionBar = null;
    /**	Menu Fragment			*/
	private T_Menu					m_Menu = null;
	/**	Flag Fragment Added		*/
	private boolean 				m_FragmentAdded = false;
	/**	Index Fragment			*/
	public final String 			TAG_FRAGMENT = "SyncMenu";
    
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_synchronization);
		
    	//	Action Bar
    	actionBar = getActionBar();
    	//	
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	actionBar.setHomeButtonEnabled(true);
    	actionBar.setTitle(R.string.app_name);
    	actionBar.setSubtitle(R.string.Sync_Synchronzing);
    	//	
    	loadFragment();       
	}
	
	/**
	 * Load Fragments
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
    private void loadFragment() {
    	if(m_Menu == null) {
    		m_Menu = new T_Menu(this, LookupMenu.SYNCHRONIZATION_MENU);
    	}
    	
    	 //	Get Fragment Transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //	Instance if not exists
        //	Portrait
        if (findViewById(R.id.ll_synchronization) != null) {
        	if (!m_FragmentAdded) {
        		transaction.add(R.id.ll_synchronization, m_Menu, TAG_FRAGMENT);
        		m_FragmentAdded = true;
            } else {
            	transaction.replace(R.id.ll_synchronization, m_Menu, TAG_FRAGMENT);
            }
        }
    	//	Nice To Have
//    	else if(findViewById(R.id.ll_pr_list_land) != null) {
//        	transaction.add(R.id.ll_pr_list_pane, m_Menu, TAG_FRAGMENT);
//        }
    	//	Commit
    	transaction.commit();
    	//	
    	invalidateOptionsMenu();
    	//	Hide Keyboad
    	Env.hideKeyBoad(this);
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(Env.isEnvLoad()) {
    		boolean noBack = false;
    		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
    			//	
    			if(m_Menu != null) {
    				noBack = m_Menu.backToParent();
    			}
    		}
        	//	No Back
        	if(!noBack) {
        		return super.onKeyDown(keyCode, event);
        	} else {
        		return noBack;
        	}
    	}
		//	Default Return
		return super.onKeyDown(keyCode, event);
	}
}