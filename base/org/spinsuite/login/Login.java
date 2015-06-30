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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.model.MUser;
import org.spinsuite.sync.SyncService;
import org.spinsuite.util.Env;
import org.spinsuite.util.LoginFragmentItem;
import org.spinsuite.util.Msg;
import org.spinsuite.util.SyncValues;
import org.spinsuite.view.T_Menu;
import org.spinsuite.view.T_Pref_General;
import org.spinsuite.view.T_Pref_Login;
import org.spinsuite.view.T_Pref_MQTT;
import org.spinsuite.view.T_Pref_Request_Pass;
import org.spinsuite.view.T_Pref_WS;

import test.LoadInitData;
import android.app.ActionBar;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Jun 15, 2015, 1:52:50 AM
 *
 */
public class Login extends FragmentActivity implements I_Login {
	
	/**	Menu Fragment			*/
	private T_Menu							m_Menu 				= null;
	/**	Valid Login Fragment	*/
	private T_Pref_Login					m_Login				= null;
	/**	Request Pass Fragment	*/
	private T_Pref_Request_Pass				m_RequestPasscode	= null;
	/**	Action Bar				*/
    private ActionBar 						actionBar 			= null;
	/**	Index Fragment			*/
	public final String 					TAG_FRAGMENT 		= "Menu";
	/**	Current Item			*/
	private int 							m_CurrentItem 		= 0;
	/**	Flag Fragment Added		*/
	private boolean 						m_FragmentAdded 	= false;
	/**	Preference Pane			*/
	private ArrayList<LoginFragmentItem>	m_PrefPane 			= new ArrayList<LoginFragmentItem>();
	/**	Data Base				*/
	private final String 					DATA_BASE 			= "D";
	/**	Role Access				*/
	private final String 					ROLE_ACCESS 		= "R";
	/**	Load Access Type		*/
	private String							m_LoadType 			= ROLE_ACCESS;
	/**	Activity				*/
	private Activity						v_activity			= null;
	/** Notification Manager	*/
	private NotificationManager 			m_NFManager 		= null;
	/** Max Value Progress Bar	*/
	private int 							m_MaxPB 			= 0;
	/** Builder					*/
	private Builder 						m_Builder 			= null;
	/** Pending Intent Fragment */ 
	private PendingIntent 					m_PendingIntent 	= null;
	/**	Notification ID			*/
	private static final int				NOTIFICATION_ID 	= 1;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
    	Env.getInstance(getApplicationContext(), true);
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
    	//	
    	v_activity = this;
    	//	
    	if(validLogin()
    			|| !Env.isEnvLoad()) {
    		loadConfig();
    	}
	}
	
	/**
	 * Validate Login User
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	private boolean validLogin() {
		if(Env.isEnvLoad()) {
			String user = Env.getContext(this, "#SUser");
	    	String pass = Env.getContext(this, "#SPass");
	    	//	Find User by pass
			if(MUser.findUserID(this, user, pass) >= 0) {
				//	validation login
				if(Env.isRequestPass(this)) {
					m_RequestPasscode = new T_Pref_Request_Pass(this);
					loadFragment(m_RequestPasscode);
				} else {
					loadAccess();
					return true;
				}
			} else {
				m_Login = new T_Pref_Login(this, false);
				loadFragment(m_Login);
			}
		}
		//	Default Return
		return false;
	}
	
	
	/**
	 * Load a Fragment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Fragment
	 * @return void
	 */
	private void loadFragment(Fragment p_Fragment) {
        //	Get Fragment Transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //	Instance if not exists
        //	Portrait
        if (findViewById(R.id.ll_login) != null) {
        	if (!m_FragmentAdded) {
        		transaction.add(R.id.ll_login, p_Fragment, TAG_FRAGMENT);
        		m_FragmentAdded = true;
            } else {
            	transaction.replace(R.id.ll_login, p_Fragment, TAG_FRAGMENT);
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
    
    /**
	 * Start Synchronization for Initial Load
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 8, 2014, 11:25:21 AM
	 * @return void
	 */
	private void startSynchronization() {
		//	For Test Data
		if(Env.getContextAsBoolean("#LoadTestData")) {
			loadDefaultData();
			//	Default Return
			return;
		}
		//	Create Directory
		Env.createDefaultDirectory(this);
		//	
		setEnabled(false);
		//	Add Value to Web
		String url = Env.getContext("#SUrlSoap");
		String user = Env.getContext("#SUser");
		String pass = Env.getContext("#SPass");
		//	
		//	Instance Bundle
		Bundle bundle = new Bundle();
		//	Add Parameters
		bundle.putString(SyncValues.KEY_SOAP_URL, SyncValues.getInitialUrl(url));
		bundle.putString(SyncValues.KEY_NAME_SPACE, SyncValues.DEFAULT_NAME_SPACE);
		bundle.putString(SyncValues.KEY_METHOD, SyncValues.DEFAULT_METHOD);
		bundle.putBoolean(SyncValues.KEY_NET_SERVICE, true);
		bundle.putString(SyncValues.KEY_USER, user);
		bundle.putString(SyncValues.KEY_PASS, pass);
		bundle.putString(SyncValues.KEY_SOAP_ACTION, SyncValues.DEFAULT_NAME_SPACE + SyncValues.DEFAULT_METHOD);
		bundle.putInt(SyncValues.KEY_TIMEOUT, Env.getContextAsInt("#Timeout"));
		//	Instance Service
		Intent m_Service = new Intent(this, SyncService.class);
		m_Service.putExtras(bundle);
		startService(m_Service);
	}
	
	/**
	 * Load Configuration
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	public void loadConfig() {
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
    		if(m_Menu == null) {
        		m_Menu = new T_Menu(this);
        	}
    		//	Set Title
    		actionBar.setSubtitle(Msg.getMsg(this, "SelectMenuItem"));
    		//	
    		loadFragment(m_Menu);
    	} else {
    		instanceFragment();
    		//	Load Fragment
    		loadFragment(m_PrefPane.get(m_CurrentItem).getPref());
    		//	Register Receiver
        	LocalBroadcastManager.getInstance(this).registerReceiver(
        			new BroadcastReceiver() {
        			    @Override
        			    public void onReceive(Context context, Intent intent) {
        			    	changeValues(context, intent);
        			    }
        			}, 
        			new IntentFilter(SyncValues.BC_IL_FILTER));
    	}
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
    	String subMsg = intent.getStringExtra(SyncValues.BC_KEY_SUB_MSG);
    	int maxPB = intent.getIntExtra(SyncValues.BC_KEY_MAX_VALUE, -1);
    	int progress = intent.getIntExtra(SyncValues.BC_KEY_PROGRESS, -1);
    	//	Valid Max Value for Progress Bar
    	if(maxPB != -1)
    		m_MaxPB = maxPB;
    	//	
    	//	Valid Status
    	if(status == null)
    		return;
    	//	Verify Status and Instance Notification
    	if(msgType != null 
    			&& msgType.equals(SyncValues.BC_MSG_TYPE_ERROR)) {
			m_Builder.setContentTitle(msg)
									.setSmallIcon(android.R.drawable.stat_sys_download);
			//	Set To Error
			Env.setIsEnvLoad(false);
    		//	Set Value for Sync
    		Env.setContext("#InitialLoadSynchronizing", false);
    		android.app.AlertDialog.Builder ask = Msg.confirmMsg(this, msg);
    		ask.setPositiveButton(getResources().getString(R.string.msg_Acept), new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				dialog.dismiss();
    				startSynchronization();
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
    		Env.setContext("#InitialLoadSynchronizing", true);
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
        		//	Load Default Tabs
        		reloadActivity();
        	}
    		//	Set Sub Title
    		if(subMsg != null
    				&& subMsg.length() > 0) {
    			m_Builder.setContentText(subMsg);
    		}
    		//	Notify
    		m_NFManager.notify(NOTIFICATION_ID, m_Builder.build());
    	}
    }
    
    /**
     * Re-Load Activity
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void reloadActivity(){
    	Intent refresh = new Intent(getApplicationContext(), Login.class);
    	refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
	public void loadDefaultData() {
		m_LoadType = DATA_BASE;
		new LoadAccessTask().execute();
	}
	
	/**
	 * Load Access
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	public void loadAccess() {
		m_LoadType = ROLE_ACCESS;
		new LoadAccessTask().execute();
	}
	
	/**
	 * Instance Fragment for Menu
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void instanceFragment() {
		//	Add Login
		m_PrefPane.add(new LoginFragmentItem(new T_Pref_Login(this, false), 
				getString(R.string.PR_Login), getString(R.string.PR_D_Login), true));
		//	Add Web-Services
		m_PrefPane.add(new LoginFragmentItem(new T_Pref_WS(this), 
				getString(R.string.PR_WS), getString(R.string.PR_D_WS), true));
		//	Add MQTT Protocol
		m_PrefPane.add(new LoginFragmentItem(new T_Pref_MQTT(this), 
				getString(R.string.PR_MQTT), getString(R.string.PR_D_MQTT), false));
		//	Add General Preferences
		m_PrefPane.add(new LoginFragmentItem(new T_Pref_General(this), 
				getString(R.string.PR_General), getString(R.string.PR_D_General), false));
    	//	Add your custom preferences panels
    	//	***********************
    	//	End Custom preferences
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
	
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(Env.isEnvLoad()) {
    		if(m_Menu != null) {
    			m_Menu.onActivityResult(requestCode, resultCode, data);
    		}
    	} else {
    		m_PrefPane.get(m_CurrentItem).getPref().onActivityResult(requestCode, resultCode, data);
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //	Change Menu
        if(!Env.isEnvLoad()) {
        	getMenuInflater().inflate(R.menu.previous_next, menu);

            menu.findItem(R.id.action_previous).setEnabled(m_CurrentItem > 0);
            //	
            menu.findItem(R.id.action_next).setTitle(
            		(m_CurrentItem == m_PrefPane.size() - 1)
                    	? R.string.Action_Finish
                    			: R.string.Action_Next);
        } else {
        	getMenuInflater().inflate(R.menu.cancel_ok, menu);
        }
        //	Default Return
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                loadFragment(m_PrefPane.get(--m_CurrentItem).getPref());
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
            	//	Valid Data
            	boolean isOk = m_PrefPane.get(m_CurrentItem).getPref().processActionOk();
            	if(!isOk) {
            		return false;
            	}
            	//	
            	if(!(m_CurrentItem == m_PrefPane.size() - 1)) {
            		loadFragment(m_PrefPane.get(++m_CurrentItem).getPref());
            	} else {
            		startSynchronization();
            	}
                return true;
            case R.id.action_ok:
            	if(m_Login != null) {
            		boolean ok = m_Login.processActionOk();
            		if(ok) {
            			loadConfig();
            		}
            	} else if(m_RequestPasscode != null) {
            		boolean ok = m_RequestPasscode.processActionOk();
            		if(ok) {
            			loadConfig();
            		}
            	}
            	return true;
            case R.id.action_cancel:
            	finish();
            	return true;
        }

        return super.onOptionsItemSelected(item);
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
				Env.loadRoleAccess();
				//	Load Context
				Env.loadContext();
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
				reloadActivity();
			} else if(m_LoadType.equals(ROLE_ACCESS)) {
				//	Start Activity
				;
			}
		}
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
