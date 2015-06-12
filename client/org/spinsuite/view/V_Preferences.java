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
package org.spinsuite.view;

import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_CancelOk;
import org.spinsuite.interfaces.I_PR_FragmentSelect;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class V_Preferences extends FragmentActivity 
		implements I_PR_FragmentSelect {
	
	/**	Index Preferences			*/
	private FV_IndexPreference 		m_PreferenceListFragment = null;
	/**	General Preference			*/
	private T_Pref_General			m_GeneralPreference = null;
	/**	Web-Service Preference		*/
	private T_Pref_WS				m_WSPreference = null;
	/**	MQTT Preference				*/
	private T_Pref_MQTT				m_MQTTPreference = null;
	/**	Is Index Fragment			*/
	private boolean					m_IsDetailAdded	= false;
	/**	Action Bar					*/
    private ActionBar 				actionBar = null;
    /**	Current Preference			*/
	private int 					m_CurrentPreference = 0;
	/**	Index Fragment				*/
	public static final String 		INDEX_FRAGMENT = "Index";
	/**	Detail Fragment				*/
	public static final String 		DETAIL_FRAGMENT = "Detail";
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_preferences);
    	//	Action Bar
    	actionBar = getActionBar();
    	//	
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	actionBar.setHomeButtonEnabled(true);
    	actionBar.setTitle(R.string.app_name);
    	actionBar.setSubtitle(R.string.PR_Preferences);
    	//	
    	loadFragment();
	}

	@Override
	public void onItemSelected(int p_Item_ID) {
	    //	Instance if not exists
        Fragment m_PreferencePane = instanceDetailFragment(p_Item_ID);
        //	Transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //	
        if(findViewById(R.id.ll_pr_list_land) != null) {
        	if(!m_IsDetailAdded) {
        		transaction.add(R.id.ll_pr_pane, m_PreferencePane, DETAIL_FRAGMENT);
        		m_IsDetailAdded = true;
        	} else {
        		transaction.replace(R.id.ll_pr_pane, m_PreferencePane, DETAIL_FRAGMENT);
        	}
        } else {
        	transaction.hide(m_PreferenceListFragment);
        	if(m_PreferencePane.isHidden()) {
        		transaction.show(m_PreferencePane);
        	} else if(!m_PreferencePane.isAdded()) {
        		transaction.add(R.id.ll_pr_list, m_PreferencePane, DETAIL_FRAGMENT);
        	}
        }
        //	
        transaction.commit();
	}
	
	/**
	 * Load Fragments
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
    private void loadFragment() {
    	if(m_PreferenceListFragment == null) {
    		m_PreferenceListFragment = new FV_IndexPreference(this);
    	}
        //	Get Fragment Transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //	Instance if not exists
        instanceDetailFragment(0);
        //	Portrait
    	if (findViewById(R.id.ll_pr_list) != null) {
    		transaction.add(R.id.ll_pr_list, m_PreferenceListFragment, INDEX_FRAGMENT);
        } else if(findViewById(R.id.ll_pr_list_land) != null) {
        	transaction.add(R.id.ll_pr_list_pane, m_PreferenceListFragment, INDEX_FRAGMENT);
        }
    	//	Commit
    	transaction.commit();
    }
    
    /**
     * Instance Preference
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_Item_ID
     * @return Fragment
     */
    private Fragment instanceDetailFragment(int p_Item_ID) {
    	m_CurrentPreference = p_Item_ID;
    	switch (p_Item_ID) {
			case I_PR_FragmentSelect.GENERAL:
				if(m_GeneralPreference == null) {
					m_GeneralPreference = new T_Pref_General(this);
				}
				//	For General Preference
				return m_GeneralPreference;
			case I_PR_FragmentSelect.WEB_SERVICES:
				if(m_WSPreference == null) {
					m_WSPreference = new T_Pref_WS(this);
				}
				//	For WS Preference
				return m_WSPreference;
			case I_PR_FragmentSelect.MQTT:
				if(m_MQTTPreference == null) {
					m_MQTTPreference = new T_Pref_MQTT(this);
				}
				//	For MQTT Preference
				return m_MQTTPreference;
		}
        //	Default
        return null;
	}
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean noBack = false;
    	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			//	
    		noBack = backToFragment();
		}
    	//	No Back
    	if(!noBack) {
    		return super.onKeyDown(keyCode, event);
    	}
		//	Default Return
		return noBack;
	}
    
    /**
     * Back to previous fragment if is necessary
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return boolean
     */
    private boolean backToFragment() {
		//	
		if(findViewById(R.id.ll_pr_list_land) != null
				|| !m_PreferenceListFragment.isHidden()) {
			return false;
		}
		//	Begin Transaction
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		Fragment m_PreferencePane = instanceDetailFragment(m_CurrentPreference);
		//	Save Data
		((I_CancelOk) m_PreferencePane).processActionOk();
		//	Save
		//	Begin Transaction
		transaction.hide(m_PreferencePane);
		//	
		if(m_PreferenceListFragment.isHidden()) {
    		transaction.show(m_PreferenceListFragment);
    	} else {
    		transaction.replace(R.id.ll_pr_list, m_PreferenceListFragment, INDEX_FRAGMENT);
    	}
		//	Commit
		transaction.commit();
	    //	Return
	    return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	        case android.R.id.home:
	    		finish();
	        return true;
	        default:
	        	return super.onOptionsItemSelected(item);
	     }
	}
}
