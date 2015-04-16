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
package org.spinsuite.bchat.view;

import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.bchat.adapters.BChatContactAdapter;
import org.spinsuite.bchat.util.DisplayBChatContactItem;
import org.spinsuite.interfaces.I_BC_FragmentSelect;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class V_BChat extends FragmentActivity 
		implements I_BC_FragmentSelect {
	
	/**	Drawer Layout				*/
	private DrawerLayout 						m_DLayout;
	/**	List View with options		*/
    private ListView 							m_DList;
    /**	Toggle						*/
    private ActionBarDrawerToggle 				m_DToggle;
    /**	Flag (Drawer Loaded)		*/
    private boolean 							isDrawerLoaded = false;
    /**	Action Bar					*/
    private ActionBar 							actionBar = null;
    /** Menu						*/
    private Menu								m_CurrentMenu = null;
    /**	Contact Data				*/
    private ArrayList<DisplayBChatContactItem> 	m_BChatContactData = null;
	/**	Index Fragment				*/
	public static final String 					INDEX_FRAGMENT = "Index";
	/**	Detail Fragment				*/
	public static final String 					DETAIL_FRAGMENT = "Detail";
	/**	Select Type					*/
	public static final int						TYPE_REQUEST_USER = 1;
	public static final int						TYPE_REQUEST_GROUP = 2;
	public static final int						TYPE_SELECT_CONVERSATION = 3;
	/**	Fragment					*/
	private FV_ThreadIndex 						m_ThereadListFragment = null;
	/**	Detail Fragment				*/
	private FV_Thread 							m_ThreadFragment = null;
	/**	Is Index Fragment			*/
	private boolean								m_IsDetailAdded	= false;
	/**	Request						*/
	private int									m_SPS_BC_Request_ID = 0;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	//	Add Support to Progress Bar in Action Bar
    	requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    	//	
    	super.setContentView(R.layout.v_business_chat);
    	//	Set ProgressBar to false
    	setProgressBarIndeterminateVisibility(false);
    	
    	actionBar = getActionBar();
    	//	
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	actionBar.setHomeButtonEnabled(true);
    	actionBar.setTitle(R.string.app_name);
    	actionBar.setSubtitle(R.string.BChat);
    	//	Load Drawer
    	loadDrawer();
    	//	Load Contact
    	loadContact();
    	//	
    	loadFragment();
    }
    
    /**
     * Load Fragment
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void loadFragment() {
    	if(m_ThereadListFragment == null) {
    		m_ThereadListFragment = new FV_ThreadIndex(this);
    	}
        //	Get Fragment Transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //	Instance if not exists
        instanceDetailFragment();
        //	Portrait
    	if (findViewById(R.id.ll_bc_list) != null) {
    		transaction.add(R.id.ll_bc_list, m_ThereadListFragment, INDEX_FRAGMENT);
        } else if(findViewById(R.id.ll_bc_list_land) != null) {
        	transaction.add(R.id.ll_bc_thread_list, m_ThereadListFragment, INDEX_FRAGMENT);
        }
    	//	Commit
    	transaction.commit();
    }
    
    /**
     * Instan
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void instanceDetailFragment() {
        //	Instance if not exists
        if(m_ThreadFragment == null) {
        	m_ThreadFragment = new FV_Thread(this);
        }
	}
    
	@Override
	public void onItemSelected(int p_Record_ID, String p_Name, int p_Type) {
	       //	Instance if not exists
        instanceDetailFragment();
        //	Transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //	
        if(findViewById(R.id.ll_bc_list_land) != null) {
        	if(!m_IsDetailAdded) {
        		transaction.add(R.id.ll_bc_thread, m_ThreadFragment, DETAIL_FRAGMENT);
        		m_IsDetailAdded = true;
        	}
        } else {
        	transaction.hide(m_ThereadListFragment);
        	if(m_ThreadFragment.isHidden()) {
        		transaction.show(m_ThreadFragment);
        	} else if(!m_ThreadFragment.isAdded()) {
        		transaction.add(R.id.ll_bc_list, m_ThreadFragment, DETAIL_FRAGMENT);
        	}
        }
        //	
        transaction.commit();
        //	
        if(m_ThreadFragment != null) {
        	if(p_Type == TYPE_REQUEST_USER) {
        		m_ThreadFragment.requestUser(p_Record_ID, p_Name);
        	} else if(p_Type == TYPE_SELECT_CONVERSATION) {
        		m_ThreadFragment.selectConversation(p_Record_ID);
        	}
        }
	} 
    
    /**
     * Load Drawer
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 14/03/2014, 09:23:22
     * @return void
     */
    protected void loadDrawer(){
    	//	
    	m_DLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //	
    	m_DList = (ListView) findViewById(R.id.left_drawer);
        //	
        m_DLayout.setDrawerShadow(
        		Env.getResourceID(this, R.attr.ic_ab_drawer_shadow), GravityCompat.START);
        
        m_DList.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				m_DLayout.closeDrawers();
				DisplayBChatContactItem item = (DisplayBChatContactItem) adapter.getItemAtPosition(position);
				onItemSelected(item.getRecord_ID(), item.getValue(), TYPE_REQUEST_USER);
			}
        });

        m_DToggle = new ActionBarDrawerToggle(this, m_DLayout, 
        		R.string.drawer_open, R.string.drawer_close) {
            
        	public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
            
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
            	super.onConfigurationChanged(newConfig);
            }
        };
        //	
        m_DToggle.syncState();
        //	Set Toggle
        m_DLayout.setDrawerListener(m_DToggle);
        isDrawerLoaded = true;
    }
    
    /**
     * Load Contact Users
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void loadContact() {
    	if(m_BChatContactData == null) {
    		DB conn = new DB(this);
    		//	Load Connection
    		DB.loadConnection(conn, DB.READ_ONLY);
    		//	
    		//	SQL
    		String sql = new String("SELECT u.AD_User_ID, u.Name "
    				+ "FROM AD_User u "
    				+ "WHERE u.AD_User_ID <> " + Env.getAD_User_ID());
    		//	
			LogM.log(this, getClass(), Level.FINE, "SQL Load Contact BChat=" + sql);
			//	
			Cursor rs = conn.querySQL(sql.toString(), null);
			//	Instance
			m_BChatContactData = new ArrayList<DisplayBChatContactItem>();
			if(rs.moveToFirst()){
				do {
					m_BChatContactData.add(
							new DisplayBChatContactItem(
									rs.getInt(0), 
									rs.getString(1), 
									null
							)
					);
				} while(rs.moveToNext());
			}
	    	//	Close Connection
	    	DB.closeConnection(conn);
    	}
    	//	
    	BChatContactAdapter mi_adapter = new BChatContactAdapter(this, m_BChatContactData);
		mi_adapter.setDropDownViewResource(R.layout.i_bchat_contact);
		getDrawerList().setAdapter(mi_adapter);
    }
    
    /**
     * Set Visible a Indeterminate Progress Bar and hide or show Option Menu
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param visible
     * @return void
     */
    protected void setVisibleProgress(boolean visible) {
    	//	
    	setProgressBarIndeterminateVisibility(visible);
    	setVisibleMenu(!visible);
    }
    
    /**
     * Set Visible menu
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param visible
     * @return void
     */
    protected void setVisibleMenu(boolean visible) {
    	//	Valid Null
    	if(m_CurrentMenu == null)
    		return;
    	//	Set Visible
    	for(int i = 0; i < m_CurrentMenu.size(); i++) {
    		m_CurrentMenu.getItem(i).setVisible(visible);
    	}
    }
    
    /**
     * Is Drawer Loader
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/03/2014, 10:29:47
     * @return
     * @return boolean
     */
    protected boolean isDrawerLoaded(){
    	return isDrawerLoaded;
    }
    
    /**
     * Is Drawer Layout Open
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/03/2014, 10:32:25
     * @return
     * @return boolean
     */
    protected boolean isDrawerLayoutOpen(){
    	if(isDrawerLoaded)
        	return m_DLayout.isDrawerOpen(m_DList);
    	return false;
    }
    
    /**
     * Get Drawer List
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 14/03/2014, 17:43:59
     * @return
     * @return ListView
     */
    protected ListView getDrawerList(){
    	return m_DList;
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(isDrawerLoaded)
        	m_DToggle.syncState();
    }
    
    

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(isDrawerLoaded)
        	m_DToggle.onConfigurationChanged(newConfig);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	boolean ok = super.onCreateOptionsMenu(menu);
    	m_CurrentMenu = menu;
    	return ok;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(isDrawerLoaded) {
    		if (m_DToggle.onOptionsItemSelected(item)) {
                return true;
            }
    	}
        // Handle action buttons
        switch(item.getItemId()) {
        	case R.id.action_new_group:
        		Intent groupAdd = new Intent(this, V_BChat_AddGroup.class);
        		startActivityForResult(groupAdd, 0);
        		return true;
        	default:
        		return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(isDrawerLoaded){
        	boolean drawerOpen = m_DLayout.isDrawerOpen(m_DList);
        	if(drawerOpen)
        		menu.setGroupVisible(R.id.group_tab_menu, false);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean noBack = false;
    	if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			//	
    		noBack = backToFragment();
		}
    	//	No Back
    	if(!noBack)
    		return super.onKeyDown(keyCode, event);
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
		if(findViewById(R.id.ll_bc_list_land) != null
				|| !m_ThereadListFragment.isHidden()) {
			return false;
		}
		//	Begin Transaction
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		//	Begin Transaction
		transaction.hide(m_ThreadFragment);
		//	
		if(m_ThereadListFragment.isHidden()) {
    		transaction.show(m_ThereadListFragment);
    	} else {
    		transaction.replace(R.id.ll_bc_list, m_ThereadListFragment, INDEX_FRAGMENT);
    	}
		//	Commit
		transaction.commit();
		//	Reload Data
		if(m_ThereadListFragment != null) {
			m_ThereadListFragment.loadData();
		}
	    //	Return
	    return true;
	}
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if(m_SPS_BC_Request_ID != 0) {
    		onItemSelected(m_SPS_BC_Request_ID, null, TYPE_SELECT_CONVERSATION);
    		m_SPS_BC_Request_ID = 0;
    	}
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	//	
    	if (resultCode == Activity.RESULT_OK) {
	    	if(data != null) {
	    		m_SPS_BC_Request_ID = data.getIntExtra("SPS_BC_Request_ID", 0);
	    	}
    	}
    }
    
}
