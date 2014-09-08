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

import org.spinsuite.adapters.FragmentTabArray;
import org.spinsuite.base.R;
import org.spinsuite.util.ActivityParameter;
import org.spinsuite.util.DisplayMenuItem;
import org.spinsuite.util.Env;
import org.spinsuite.util.TabHandler;
import org.spinsuite.util.TabParameter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class TV_Base extends Activity 
						implements ActionBar.TabListener {
	
	/**	Parameters						*/
	protected ActivityParameter 	param;
	/**	Activity No					*/
	protected int					m_ActivityNo = 0;
	/**	Drawer Layout				*/
	private DrawerLayout 			m_DLayout;
	/**	List View with options		*/
    private ListView 				m_DList;
    /**	Toggle						*/
    private ActionBarDrawerToggle 	m_DToggle;
    /**	Flag (Drawer Loaded)		*/
    private boolean 				isDrawerLoaded = false;
    /**	Action Bar					*/
    private ActionBar 				actionBar = null;
    /**	Array with fragments		*/
    private FragmentTabArray		m_FragmentArray = null;
    /**	Log Tabs					*/
    private boolean					m_IsModifying = false;
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.tv_base);
    	
    	actionBar = getActionBar();
    	 
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (ActivityParameter)bundle.getParcelable("Param");
		}
		if(param == null)
    		param = new ActivityParameter();
		//	
    	m_ActivityNo = Env.getActivityNo(this);
    	//	
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	actionBar.setHomeButtonEnabled(true);
    	//	Instance Array
    	m_FragmentArray = new FragmentTabArray(this);
    }
    
    /**
     * Load Drawer
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/03/2014, 09:23:22
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
				//	Selected Option
				onSelectedDrawerOption((DisplayMenuItem) adapter.getItemAtPosition(position));
			}
        });

        m_DToggle = new ActionBarDrawerToggle(this, m_DLayout, 
        		Env.getResourceID(this, R.attr.ic_ab_drawer), R.string.drawer_open, R.string.drawer_close) {
            
        	public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        //	Set Toggle
        m_DLayout.setDrawerListener(m_DToggle);
        //	
        isDrawerLoaded = true;
    }
    
    /**
     * To implemented by user
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/03/2014, 11:37:09
     * @param item
     * @return void
     */
    protected void onSelectedDrawerOption(DisplayMenuItem item){
    	//	
    }
    
    /**
     * Is Drawer Loader
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/03/2014, 10:29:47
     * @return
     * @return boolean
     */
    protected boolean isDrawerLoaded(){
    	return isDrawerLoaded;
    }
    
    /**
     * Is Drawer Layout Open
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/03/2014, 10:32:25
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
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/03/2014, 17:43:59
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
    	return super.onCreateOptionsMenu(menu);
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
    
    /**
     * Add Fragment Tab in FragmentActivity
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/09/2014, 21:47:41
     * @param clazz
     * @param tag
     * @param title
     * @param tabParameter
     * @param bundle
     * @return void
     */
    protected void addFagment(Class<?> clazz, String tag, int title, TabParameter tabParameter, Bundle bundle){
    	Tab tab = actionBar.newTab();
    	m_FragmentArray.addTab(tag, clazz, tabParameter, bundle);
    	tab.setTabListener(this);
        tab.setText(getResources().getString(title));
        actionBar.addTab(tab);
    	
    }
    
    /**
     * Add Fragment Tab in FragmentActivity
     * @author Yamel Senih 05/02/2013, 16:58:49
     * @param clazz
     * @param title
     * @param tabParameter
     * @return void
     */
    protected void addFagment(Class<?> clazz, String tag, int title, TabParameter tabParameter) {
    	Tab tab = actionBar.newTab();
    	m_FragmentArray.addTab(tag, clazz, tabParameter, null);
    	tab.setTabListener(this);
    	tab.setText(getResources().getString(title));
        actionBar.addTab(tab);
    }
    
    /**
     * Add Fragment without Tab Parameter
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/09/2014, 21:51:47
     * @param clazz
     * @param tag
     * @param title
     * @return void
     */
    protected void addFagment(Class<?> clazz, String tag, int title) {
    	addFagment(clazz, tag, title, null);
    }
    
    /**
     * Add Fragment Tab in FragmentActivity with String title
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/02/2014, 13:23:35
     * @param clazz
     * @param tag
     * @param title
     * @param tabParameter
     * @return void
     */
    protected void addFagment(Class<?> clazz, String tag, String title, TabParameter tabParameter) {
    	Tab tab = actionBar.newTab();
    	tab.setText(title);
    	m_FragmentArray.addTab(tag, clazz, tabParameter, null);
    	tab.setTabListener(this);
        actionBar.addTab(tab);
    }
    
    /**
     * Add Fragment Tab
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 09:05:17
     * @param clazz
     * @param tag
     * @param title
     * @param tabParameter
     * @param param
     * @return void
     */
    protected void addFagment(Class<?> clazz, String tag, String title, TabParameter tabParameter, Bundle param){
    	Tab tab = actionBar.newTab();
    	m_FragmentArray.addTab(tag, clazz, tabParameter, param);
    	tab.setTabListener(this);
    	tab.setText(title);
        actionBar.addTab(tab);
    }
    /**
     * Get Current Fragment from View Pager
     * @author Yamel Senih 05/02/2013, 17:11:23
     * @return
     * @return Fragment
     */
    protected Fragment getCurrentFragment() {
		int index = actionBar.getSelectedTab().getPosition();
    	return getFragment(index);
	}
    
    /**
     * Get Fragment from index
     * @author Yamel Senih 05/02/2013, 17:12:46
     * @param index
     * @return
     * @return Fragment
     */
    protected Fragment getFragment(int index) {
		TabHandler tab = m_FragmentArray.getTab(index);
    	return tab.getFragment();
	}
    
    /**
     * Get Tab Handler
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 10:21:40
     * @param index
     * @return
     * @return TabListener
     */
    private TabHandler getTabHandler(int index) {
		TabHandler tab = m_FragmentArray.getTab(index);
    	return tab;
	}
    
    /**
     * Get Current Tab Handler
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/08/2014, 10:52:35
     * @return
     * @return TabHandler
     */
    private TabHandler getCurrentTabHandler() {
    	int index = actionBar.getSelectedTab().getPosition();
		return getTabHandler(index);
    }
    
    /**
     * Set Current Fragment Tab
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/03/2014, 10:45:26
     * @param index
     * @return void
     */
    protected void setCurrentFragment(int index) {
		actionBar.setSelectedNavigationItem(index);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		TabHandler tabHandler = getCurrentTabHandler();
		tabHandler.loadFragment(tab, ft);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		//	Set Old Position
		TabHandler tabHandler = getCurrentTabHandler();
		tabHandler.unLoadFragment(tab, ft);
	}
	
	/**
	 * Set Lock Tabs
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/09/2014, 18:28:19
	 * @param m_IsModifying
	 * @return void
	 */
	protected void setIsModifying(boolean m_IsModifying) {
		this.m_IsModifying = m_IsModifying;
	}
	
	/**
	 * Is Modifying
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/09/2014, 18:31:05
	 * @return
	 * @return boolean
	 */
	protected boolean isModifying() {
		return m_IsModifying;
	}
	
	/**
	 * Get Current Tab Parameter
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/09/2014, 21:49:34
	 * @return
	 * @return TabParameter
	 */
	protected TabParameter getCurrentTabParameter() {
		TabHandler tabHandler = getCurrentTabHandler();
		return tabHandler.getTabParameter();
	}
}
