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

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_CancelOk;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.model.MCountry;
import org.spinsuite.util.Env;
import org.spinsuite.util.Msg;
import org.spinsuite.view.LV_Menu;
import org.spinsuite.view.TV_Base;

import test.LoadInitData;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
    
	/**	Data Base			*/
	private final String 	DATA_BASE 		= "D";
	/**	Role Access			*/
	private final String 	ROLE_ACCESS 	= "R";
	/**	Load Access Type	*/
	private String			m_LoadType 		= ROLE_ACCESS;
	/**	Activity			*/
	private Activity		v_activity		= null;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	//	Reset Activity No
    	Env.resetActivityNo(getApplicationContext());
    	//	
    	super.onCreate(savedInstanceState);
    	//	
    	addFagment(T_Login.class, "Conn", R.string.tt_Conn);
        addFagment(T_Role.class, "LoginRole", R.string.tt_LoginRole);
        //	Set Activity
        v_activity = this;
    	//*/
    	//CreatePDFTest.GenerarPDF(this);
    	// Validate SD
    	if(Env.isEnvLoad(this)){
    		if(!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
    			if(!Env.getDB_PathName(this).equals(DB.DB_NAME)){
    				Msg.alertMsg(this, getResources().getString(R.string.msg_SDNotFoundDetail));
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
    		//	Auto Login
    		if(Env.isAutoLoginConfirmed(this)) {
    			if(!Env.isAccessLoaded(this)) {
					//	Load Access Role
					m_LoadType = ROLE_ACCESS;
					new LoadAccessTask().execute();
				} else {
					//	Start Activity
					Intent intent = new Intent(this, LV_Menu.class);
					startActivity(intent);
				}
    			finish();
    		}
    	} else {
    		m_LoadType = DATA_BASE;
			new LoadAccessTask().execute();
    	}
    	//	
    }  
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.cancel_ok, menu);
        MenuItem item = menu.getItem(0);
        item.setVisible(true);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
		if (itemId == android.R.id.home) {
			// Navigate "up" the demo structure to the launchpad activity.
			// See http://developer.android.com/design/patterns/navigation.html for more.
			NavUtils.navigateUpTo(this, new Intent(this, Login.class));
			return true;
		} else if (itemId == R.id.action_ok) {
			// Go to the previous step in the wizard. If there is no previous step,
			// setCurrentItem will do nothing.
			processActionOk();
			return true;
		} else if (itemId == R.id.action_cancel) {
			// Advance to the next step in the wizard. If there is no next step, setCurrentItem
			// will do nothing.
			processActionCancel();
			return true;
		} else if (itemId == R.id.action_config) {
			Intent intent = new Intent(this, T_Connection.class);
			startActivity(intent);
			return true;
		}
		//	
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
		if(fr instanceof T_Login){
			if(ret){
				if(!Env.isEnvLoad(this)){
					Intent intent = new Intent(this, T_Connection.class);
					startActivity(intent);
				} else {
					//	Is Logged
					if(Env.isLogin(this)){
						setCurrentFragment(1);
						fr = (I_Login)getCurrentFragment();	
					} else {
						//pager.getChildAt(2).setEnabled(true);
					}
				}
			}
		} else if(fr instanceof T_Role){
			if(ret){
				//	Set Confirm Login
				Env.setAutoLoginComfirmed(this, Env.isAutoLogin(this));
				//	Set Country Code
				String language = Env.getAD_Language(this);
				//	
				if(language == null
						|| language.length() < 5)
					language = Env.BASE_LANGUAGE;
				//	
				String countryCode = language.substring(3);
				//	
				if(countryCode == null)
					countryCode = Env.BASE_COUNTRY_CODE;
				//	Set to Context
				int m_C_Country_ID = MCountry.getC_Country_IDFromCode(this, countryCode);
				//	Set Country
				if(m_C_Country_ID < 0)
					m_C_Country_ID = 0;
				//	
				Env.setContext(this, "#C_Country_ID", m_C_Country_ID);
				//	
				if(!Env.isAccessLoaded(this)) {
					//	Load Access Role
					m_LoadType = ROLE_ACCESS;
					new LoadAccessTask().execute();
				} else {
					//	Start Activity
					Intent intent = new Intent(this, LV_Menu.class);
					startActivity(intent);
					//	Valid Auto Login
					if(Env.isAutoLogin(this))
						finish();
				}
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
	
	/**
	 * Load Access
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
	 *
	 */
	private class LoadAccessTask extends AsyncTask<Void, Void, Void> {

		/**	Progress Bar			*/
		private ProgressDialog 		v_PDialog;
		
		@Override
		protected void onPreExecute() {
			v_PDialog = ProgressDialog.show(v_activity, null, 
					getString((m_LoadType.equals(ROLE_ACCESS)
									? R.string.msg_LoadingAccess
									: R.string.msg_LoadingDB)), false, false);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			if(m_LoadType.equals(DATA_BASE)) {
				LoadInitData initData = new LoadInitData(v_activity);
	    		initData.initialLoad_copyDB();
			} else if(m_LoadType.equals(ROLE_ACCESS)) {
				//	Load Role Access
				Env.loadRoleAccess(v_activity);
			}
			//	
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Void... progress) {
			
		}

		@Override
		protected void onPostExecute(Void result) {
			//	
			if(m_LoadType.equals(DATA_BASE)) {
				I_Login fr = (I_Login)getCurrentFragment();
				if(fr != null) {
					fr.loadData();
				}
			} else if(m_LoadType.equals(ROLE_ACCESS)) {
				//	Start Activity
				Intent intent = new Intent(v_activity, LV_Menu.class);
				startActivity(intent);				
			}
			//	Hide dialog
			v_PDialog.dismiss();
			//	Valid Auto Login
			if(Env.isAutoLogin(v_activity))
				finish();
		}
	}
}
