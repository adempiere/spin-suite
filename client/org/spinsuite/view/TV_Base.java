/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                                           *
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

import org.spinsuite.adapters.FragmentTabsAdapter;
import org.spinsuite.base.R;
import org.spinsuite.util.ActivityParameter;
import org.spinsuite.util.DisplayMenuItem;
import org.spinsuite.util.Env;
import org.spinsuite.view.custom.Cust_ViewPager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;

/**
 * 
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class TV_Base extends FragmentActivity 
									implements TabHost.OnTabChangeListener, 
												ViewPager.OnPageChangeListener{
	
	/**	Adapter Fragment			*/
	private FragmentTabsAdapter		mAdapterList;
	/**	Pager						*/
	private Cust_ViewPager			mViewPager;
	/**	Parameters						*/
	protected ActivityParameter 	param;
	/**	Activity No					*/
	protected int					m_ActivityNo = 0;
	/**	Tab Host					*/
	private TabHost 				mTabHost;
	/**	Drawer Layout				*/
	private DrawerLayout 			m_DLayout;
	/**	List View with options		*/
    private ListView 				m_DList;
    /**	Toggle						*/
    private ActionBarDrawerToggle 	m_DToggle;
    /**	Flag (Drawer Loaded)		*/
    private boolean 				isDrawerLoaded = false;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	super.setContentView(R.layout.tv_base);
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (ActivityParameter)bundle.getParcelable("Param");
		}
		if(param == null)
    		param = new ActivityParameter();
		//	
    	m_ActivityNo = Env.getActivityNo(this);
    	
    	mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        
    	mViewPager = (Cust_ViewPager)findViewById(R.id.v_pager);
        mAdapterList = new FragmentTabsAdapter(this, mTabHost, mViewPager);
        
        //	Add Listener
        mTabHost.setOnTabChangedListener(this);
        mViewPager.setOnPageChangeListener(this);
        //	
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
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
        m_DLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        m_DList.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				//	Selected Option
				onSelectedDrawerOption((DisplayMenuItem) adapter.getItemAtPosition(position));
			}
        });

        m_DToggle = new ActionBarDrawerToggle(this, m_DLayout, 
        		R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            
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
     * @author Yamel Senih 03/05/2012, 19:08:05
     * @param clazz
     * @param bundle
     * @param tabTag
     * @param title
     * @param img
     * @return void
     */
    protected void addFagment(Class<?> clazz, String tag, int title, Bundle bundle){
    	mAdapterList.addTab(clazz, tag, title, bundle);
    }
    
    /**
     * Add Fragment Tab in FragmentActivity
     * @author Yamel Senih 05/02/2013, 16:58:49
     * @param clazz
     * @param title
     * @return void
     */
    protected void addFagment(Class<?> clazz, String tag, int title){
    	mAdapterList.addTab(clazz, tag, title, null);
    }
    
    /**
     * Add Fragment Tab in FragmentActivity with String title
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/02/2014, 13:23:35
     * @param clazz
     * @param tag
     * @param title
     * @return void
     */
    protected void addFagment(Class<?> clazz, String tag, String title){
    	mAdapterList.addTab(clazz, tag, title, null);
    }
    
    /**
     * Add Fragment Tab
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/02/2014, 09:05:17
     * @param clazz
     * @param tag
     * @param title
     * @param param
     * @return void
     */
    protected void addFagment(Class<?> clazz, String tag, String title, Bundle param){
    	mAdapterList.addTab(clazz, tag, title, param);
    }
    /**
     * Get Current Fragment from View Pager
     * @author Yamel Senih 05/02/2013, 17:11:23
     * @return
     * @return Fragment
     */
    protected Fragment getCurrentFragment() {
		return mAdapterList.getCurrentFragment(mViewPager);
	}
    
    /**
     * Get Fragment from index
     * @author Yamel Senih 05/02/2013, 17:12:46
     * @param index
     * @return
     * @return Fragment
     */
    protected Fragment getFragment(int index) {
		return mAdapterList.getActiveFragment(index, mViewPager);
	}
    
    /**
     * Set Current Fragment Tab
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/03/2014, 10:45:26
     * @param index
     * @return void
     */
    protected void setCurrentFragment(int index) {
		mViewPager.setCurrentItem(index);
	}
    
    /**
     * Enabled ViewPager Navigation
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/06/2013, 17:29:58
     * @param enabled
     * @return void
     */
    public void setPagingEnabled(boolean enabled){
    	mViewPager.setPagingEnabled(enabled);
    }

    @Override
    public void onTabChanged(String tabId) {
        int position = mTabHost.getCurrentTab();
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    	
    }

    @Override
    public void onPageSelected(int position) {
        TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mTabHost.setCurrentTab(position);
        widget.setDescendantFocusability(oldFocusability);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    	
    }   
}
