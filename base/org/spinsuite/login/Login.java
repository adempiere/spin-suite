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
package org.spinsuite.login;

import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_CancelOk;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;
import org.spinsuite.view.LV_Menu;
import org.spinsuite.view.TV_Base;

import test.LoadInitData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

/**
 * This demonstrates how you can implement switching between the tabs of a
 * TabHost through fragments, using FragmentTabHost.
 */
public class Login extends TV_Base implements I_CancelOk {
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	//	Reset Activity No
    	Env.resetActivityNo(getApplicationContext());
    	//	
    	super.onCreate(savedInstanceState);
        
        addFagment(T_Login.class, "Conn", R.string.tt_Conn);
        addFagment(T_Role.class, "LoginRole", R.string.tt_LoginRole);
        
    	//*/
    	//CreatePDFTest.GenerarPDF(this);
    	setPagingEnabled(false);
    	// Validate SD
    	if(Env.isEnvLoad(this)){
    		if(!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
    			if(!Env.getDB_PathName(this).equals(DB.DB_NAME)){
    				Msg.alertMsg(this, getResources().getString(R.string.msg_SDNotFound), 
    						getResources().getString(R.string.msg_SDNotFoundDetail));
    				finish();
        		}	
    		}
    		//	Load Language
    		String languaje = Env.getAD_Language(this);
    		if(languaje != null) {
    			Env.changeLanguage(this, languaje);
    		}
    		else {
    			languaje = Env.getSOLanguage(this);
    			Env.setAD_Language(this, languaje);
    		}
    		
    	} else {
    		LoadInitData initData = new LoadInitData(this);
    		initData.initialLoad_copyDB();
    	}
    	//	
    	LogM.setTraceLevel(getApplicationContext(), Level.SEVERE);
    	//	
    }  
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.cancel_ok, menu);
        MenuItem item = menu.getItem(0);
        item.setVisible(true);
        // Add either a "next" or "finish" button to the action bar, depending on which page
        // is currently selected.
        /*MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);*/
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, Login.class));
                return true;

            case R.id.action_ok:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                processActionOk();
                return true;

            case R.id.action_cancel:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                processActionCancel();
                return true;
            case R.id.action_config:
        		Intent intent = new Intent(this, T_Connection.class);
				startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Process OK Action
     * @author Yamel Senih 24/04/2012, 18:03:59
     * @return void
     */
    @Override
    public boolean processActionOk(){
    	I_Login fr = (I_Login)getCurrentFragment();
    	boolean ret = fr.aceptAction();
		if(getCurrentFragment() instanceof T_Login){
			if(ret){
				if(!Env.isEnvLoad(this)){
					Intent intent = new Intent(this, T_Connection.class);
					startActivity(intent);
				} else {
					//	Is Logged
					if(Env.isLogin(this)){
						//pager.getChildAt(2).setEnabled(true);
						setCurrentFragment(2);
						fr = (I_Login)getCurrentFragment();
						ret = fr.loadData();	
					} else {
						//pager.getChildAt(2).setEnabled(true);
					}
				}
			}
		} else if(getCurrentFragment() instanceof T_Role){
			if(ret){
				//Intent intent = new Intent(this, MV_Menu.class);
				//startActivity(intent);
				//Msg.toastMsg(getApplication(), "Hi");
				Intent intent = new Intent(this, LV_Menu.class);
				startActivity(intent);
			} else {
				
			}
		}
		return true;
    }
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!Env.isEnvLoad(this)){
        	setCurrentFragment(0);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        /*int currentTab = mTabHost.getCurrentTab();
        Env.setContext(this, KEY_POS_TAB, currentTab);*/
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
    
	@Override
	public boolean processActionCancel() {
		return false;
	}

	@Override
	public Intent getParam() {
		return null;
	}
}
