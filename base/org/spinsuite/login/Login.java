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
package org.spinsuite.login;

import java.util.Date;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.util.Env;
import org.spinsuite.util.Msg;
import org.spinsuite.view.T_Menu;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Jun 15, 2015, 1:52:50 AM
 *
 */
public class Login extends FragmentActivity implements I_Login {
	
	/**	Menu Fragment		*/
	private T_Menu			m_Menu = null;
	/**	Action Bar			*/
    private ActionBar 		actionBar = null;
	/**	Index Fragment		*/
	public final String 	TAG_FRAGMENT = "Menu";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
    	Env.getInstance(getApplicationContext());
    	//	Reset Activity No
    	Env.resetActivityNo();
    	//	
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.v_login);
    	//	Action Bar
    	actionBar = getActionBar();
    	//	
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	actionBar.setHomeButtonEnabled(true);
    	actionBar.setTitle(R.string.app_name);
    	actionBar.setSubtitle(Msg.getMsg(this, "SelectMenuItem"));
    	//	
    	loadConfig();
	}
	
	/**
	 * Load Fragment for paint
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void loadFragment() {
    	if(m_Menu == null) {
    		m_Menu = new T_Menu(this);
    	}
        //	Get Fragment Transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //	Instance if not exists
        //	Portrait
    	if (findViewById(R.id.ll_login) != null) {
    		transaction.add(R.id.ll_login, m_Menu, TAG_FRAGMENT);
        }
    	//	NTH
//    	else if(findViewById(R.id.ll_pr_list_land) != null) {
//        	transaction.add(R.id.ll_pr_list_pane, m_Menu, TAG_FRAGMENT);
//        }
    	//	Commit
    	transaction.commit();
    }
	
	/**
	 * Load Configuration
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void loadConfig() {
        // Validate SD
    	if(Env.isEnvLoad()) {
        	//	
    		setEnabled(true);
    		//	
    		if(!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
    			if(!Env.getDB_PathName().equals(DB.DB_NAME)){
    				Msg.alertMsg(this, getResources().getString(R.string.msg_SDNotFoundDetail));
    				finish();
        		}	
    		}
    		//	Load Language
    		String languaje = Env.getAD_Language();
    		if(languaje != null
    				&& languaje.length() > 0) {
    			Env.changeLanguage(languaje);
    		}
    		else {
    			languaje = Env.getSOLanguage();
    			Env.setAD_Language(languaje);
    		}
    		//	Set Login Date
    		Env.loginDate(this, new Date());
    		//	Load Tree
    		loadFragment();
    	}
	}

    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean noBack = false;
    	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			//	
    		noBack = m_Menu.backToParent();
		}
    	//	No Back
    	if(!noBack) {
    		return super.onKeyDown(keyCode, event);
    	}
		//	Default Return
		return noBack;
	}
	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	m_Menu.onActivityResult(requestCode, resultCode, data);
    }
    
	@Override
	public boolean aceptAction() {
		return false;
	}

	@Override
	public boolean cancelAction() {
		return false;
	}

	@Override
	public boolean loadData() {
		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		
	}
}
