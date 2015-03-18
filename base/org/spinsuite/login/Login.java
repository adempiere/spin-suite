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
 * Copyright (C) 2012-2015 E.R.P. Consultores y Asociados, C.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.login;

import java.util.List;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.model.MCountry;
import org.spinsuite.sync.SyncService;
import org.spinsuite.util.Env;
import org.spinsuite.util.Msg;
import org.spinsuite.util.SyncValues;
import org.spinsuite.view.LV_Menu;
import org.spinsuite.view.TV_Base;

import test.LoadInitData;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Feb 26, 2015, 11:49:41 PM
 * 	<li> Login Correct
 * 	@see https://adempiere.atlassian.net/browse/SPIN-2
 *
 */
public class Login extends TV_Base implements I_Login {
    
	/**	Data Base				*/
	private final String 		DATA_BASE 		= "D";
	/**	Role Access				*/
	private final String 		ROLE_ACCESS 	= "R";
	/**	Load Access Type		*/
	private String				m_LoadType 		= ROLE_ACCESS;
	/**	Activity				*/
	private Activity			v_activity		= null;
	/**	Sync					*/
	private T_Login_Init		m_LoginInit;
	/**	Enable					*/
	//private boolean				m_Enabled		= true;
	/** Notification Manager	*/
	private NotificationManager m_NFManager = null;
	/** Max Value Progress Bar	*/
	private int 				m_MaxPB = 0;
	/** Builder					*/
	private Builder 			m_Builder = null;
	/** Pending Intent Fragment */ 
	private PendingIntent 		m_PendingIntent = null; 
	/**	Notification ID			*/
	private static final int	NOTIFICATION_ID = 1;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	//	Reset Activity No
    	Env.resetActivityNo(getApplicationContext());
    	//	
    	super.onCreate(savedInstanceState);
    	//	Set Activity
        v_activity = this;
    	// Validate SD
    	if(Env.isEnvLoad(this)) {
        	//	
        	addFagment(T_Login.class, "Conn", R.string.tt_Conn);
            addFagment(T_Role.class, "LoginRole", R.string.tt_LoginRole);
    		setEnabled(true);
    		//	
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
    		setEnabled(false);
    		//	
    		if(m_LoginInit == null
    				&& !SyncService.isRunning())
    			loadInitSync();
    		//	For Demo
    		//m_LoadType = DATA_BASE;
			//new LoadAccessTask().execute();
    	}
		//	Register Receiver
    	LocalBroadcastManager.getInstance(this).registerReceiver(
    			new BroadcastReceiver() {
    			    @Override
    			    public void onReceive(Context context, Intent intent) {
    			    	changeValues(context, intent);
    			    }
    			}, 
    			new IntentFilter(SyncValues.BC_IL_FILTER));
    	//	
    }
    
    /**
     * Change Values for Login and notifications
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param context
     * @param intent
     * @return void
     */
    private void changeValues(Context context, Intent intent) {
    	String status = intent.getStringExtra(SyncValues.BC_KEY_STATUS);
    	String msgType = intent.getStringExtra(SyncValues.BC_KEY_MSG_TYPE);
    	String msg = intent.getStringExtra(SyncValues.BC_KEY_MSG);
    	int progress = intent.getIntExtra(SyncValues.BC_KEY_PROGRESS, -1);
    	//	Valid Status
    	if(status == null)
    		return;
    	//	Verify Status and Instance Notification
    	if(msgType != null 
    			&& msgType.equals(SyncValues.BC_MSG_TYPE_ERROR)) {
			m_Builder.setContentTitle(msg)
									.setSmallIcon(android.R.drawable.stat_sys_download);
			//	Set To Error
			Env.setIsEnvLoad(this, false);
    		//	Set Value for Sync
    		Env.setContext(this, "#InitialLoadSynchronizing", false);
    		android.app.AlertDialog.Builder ask = Msg.confirmMsg(this, msg);
    		ask.setPositiveButton(getResources().getString(R.string.msg_Acept), new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				dialog.dismiss();
    				loadInitSync();
    			}
    		});
    		ask.setNegativeButton(getResources().getString(R.string.msg_Cancel), new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				dialog.dismiss();
    				finish();
    			}
    		});
    		//	
    		if(!isFinishing())
    			ask.show();
    		//	Notify
    		m_NFManager.notify(NOTIFICATION_ID, m_Builder.build());
		} else if(status.equals(SyncValues.BC_STATUS_START)
    			|| m_PendingIntent == null) {
    		setInstanceNotification();
    		//	Set Value for Sync
    		Env.setContext(v_activity, "#InitialLoadSynchronizing", true);
    	} else { 
    		m_Builder.setContentIntent(m_PendingIntent);
    		//	
    		if(status.equals(SyncValues.BC_STATUS_PROGRESS)) {
        		m_Builder.setContentTitle(msg)
        								.setProgress(m_MaxPB, progress, progress == -1)
        								.setSmallIcon(android.R.drawable.stat_sys_download);
        	} else if(status.equals(SyncValues.BC_STATUS_END)) {
        		m_Builder.setContentTitle(msg)
            								.setSmallIcon(android.R.drawable.stat_sys_download);
        		//	Set Default Values
        		setContext();
        	}
    		//	Notify
    		m_NFManager.notify(NOTIFICATION_ID, m_Builder.build());
    	}
    }
    
    /**
     * Reload Activity
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void reloadActivity(){
    	Intent refresh = new Intent(this, Login.class);
		startActivity(refresh);
		finish();
    }
    
    /**
	 * Set Pending Item
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void setInstanceNotification() {
		m_NFManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		m_Builder = new NotificationCompat.Builder(this);
		ActivityManager m_ActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = m_ActivityManager.getRunningTasks(1);
		ActivityManager.RunningTaskInfo task = tasks.get(0);
		ComponentName mainActivity = task.baseActivity;
		Intent intent = new Intent();
		intent.setComponent(mainActivity);
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		//	Set Main Activity
		m_PendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
	}
    
    /**
	 * Set Context
	 * @author Yamel Senih 30/11/2012, 11:55:26
	 * @return void
	 */
	public void setContext() {
		Env.setIsEnvLoad(this, true);
		Env.setSavePass(this, true);
		Env.setAutoLogin(this, true);
		//	Set Value for Sync
		Env.setContext(this, "#InitialLoadSynchronizing", false);
		//	Reload
		reloadActivity();
	}
    
    /**
     * Load Initial Synchronization
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void loadInitSync() {
    	//	
    	if(m_LoginInit == null)
    		m_LoginInit = new T_Login_Init(this);
    	//	
    	m_LoginInit.show(getFragmentManager(), 
				this.getResources().getString(R.string.InitSync));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.cancel_ok, menu);
        MenuItem item = menu.getItem(0);
        item.setVisible(true);
        setVisibleProgress(!Env.isEnvLoad(v_activity));
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
		if (itemId == R.id.action_cancel) {
			cancelAction();
			return true;
		} else {
			//	Valid Enable
			if(!Env.isEnvLoad(this)) {
				loadInitSync();
				return true;
			}
			//	
			if (itemId == R.id.action_ok) {
				aceptAction();
				return true;
			} else if (itemId == R.id.action_config) {
				Intent intent = new Intent(this, T_Connection.class);
				startActivity(intent);
				return true;
			}
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
    public boolean aceptAction() {
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
	public void setEnabled(boolean enabled) {
    	//m_Enabled = enabled;
    	int size = getSize();
    	for(int i = 0; i < size; i++) {
    		I_Login fr = (I_Login)getFragment(i);
    		if(fr != null){
    			fr.setEnabled(enabled);
    		}
    	}
	}
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
//        if(!Env.isEnvLoad(this)) {
//        	setCurrentFragment(0);
//        }
    }
    @Override
    protected void onPause() {
        super.onPause();
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
	public boolean cancelAction() {
		return false;
	}
	
	/**
	 * Re-Load Data when is synchronized
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	public boolean loadData() {
		I_Login fr = (I_Login)getCurrentFragment();
    	if(fr != null){
			return fr.loadData();
		}
    	//	Default
    	return false;
	}
	
	/**
	 * Load Task for access
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Feb 26, 2015, 11:50:13 PM
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
				//	Load Context
				Env.loadContext(v_activity);
			}
			//	
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Void... progress) {
			
		}

		@Override
		protected void onPostExecute(Void result) {
			//	Hide dialog
			v_PDialog.dismiss();
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
				//	Valid Auto Login
				if(Env.isAutoLogin(v_activity))
					finish();
			}
		}
	}
}
