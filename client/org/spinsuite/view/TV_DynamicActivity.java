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

import java.util.logging.Level;

import org.spinsuite.adapters.MenuAdapter;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DynamicTab;
import org.spinsuite.interfaces.I_FragmentSelectListener;
import org.spinsuite.util.ActivityParameter;
import org.spinsuite.util.DisplayMenuItem;
import org.spinsuite.util.Env;
import org.spinsuite.util.LoadActionMenu;
import org.spinsuite.util.LogM;
import org.spinsuite.util.TabParameter;
import org.spinsuite.view.lookup.LookupMenu;

import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class TV_DynamicActivity extends TV_Base 
									implements I_FragmentSelectListener {
	
	/**	Parameters					*/
	private ActivityParameter 	param			= null;
	/**	Record Identifier			*/
	private int 				m_Record_ID 	= 0;
	/**	Lookup Menu					*/
	private LookupMenu 			lookupMenu 		= null;
	
	/**	Load Action Menu			*/
	private LoadActionMenu		loadActionMenu 	= null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	//	
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			param = (ActivityParameter)bundle.getParcelable("Param");
			m_Record_ID = bundle.getInt("Record_ID");
		}
		if(param == null)
    		param = new ActivityParameter();
		//	New Menu
        lookupMenu = new LookupMenu(getApplicationContext(), LookupMenu.ACTIVITY_MENU, null);
        //	Option
        loadActionMenu = new LoadActionMenu(this, true);
    	//	Title
        getActionBar().setTitle(null);
    	getActionBar().setSubtitle(param.getName());
    	//	
    	//	Load Option List
    	loadDrawerOption();
    	//	Load Tabs
        loadTabs();
    }
    
    /**
     * Load Drawer Option
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/03/2014, 17:45:08
     * @return void
     */
    private void loadDrawerOption(){
    	boolean loaded = lookupMenu.loadChildren(param.getActivityMenu_ID());
		//	
		if(loaded){
			loadDrawer();
			//	Populate
			MenuAdapter mi_adapter = new MenuAdapter(this, R.layout.i_image_text_activity, false, lookupMenu.getData());
			mi_adapter.setDropDownViewResource(R.layout.i_image_text_activity);
			getDrawerList().setAdapter(mi_adapter);
		}
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    protected void onSelectedDrawerOption(DisplayMenuItem item) {
    	super.onSelectedDrawerOption(item);
    	//	Load Menu
    	I_DynamicTab curFr = (I_DynamicTab) getCurrentFragment();
    	if(curFr != null){
    		ActivityParameter actParam = param;
    		TabParameter tabParam = curFr.getTabParameter();
    		if(tabParam != null) {
        		//	Set From Called
        		actParam.setActivityNo(tabParam.getActivityNo());
        		actParam.setFrom_SPS_Table_ID(tabParam.getSPS_Table_ID());
            	actParam.setFrom_Record_ID(Env.getTabRecord_ID(
            			getApplicationContext(), tabParam.getActivityNo(), tabParam.getTabNo()));

    		}
    		//	Is From Activity
        	actParam.setIsFromActivity(true);
        	//	
        	loadActionMenu.loadAction(item, actParam);
    	}
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
		if (itemId == android.R.id.home
				&& !isDrawerLoaded()) {
			// Navigate "up" the demo structure to the launchpad activity.
			// See http://developer.android.com/design/patterns/navigation.html for more.
			NavUtils.navigateUpTo(this, new Intent(this, LV_Menu.class));
			return true;
		}
		//	
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
    	super.onTabSelected(tab, ft);
    	//	
    	invalidateOptionsMenu();
    	I_DynamicTab curFr = (I_DynamicTab) getCurrentFragment();
    	if(curFr != null) {
    		TabParameter tabParam = getCurrentTabParameter();
    		if(tabParam != null) {
    			if(tabParam.getTabLevel() == 0)
    				setIsModifying(curFr.isModifying());
    			else if(tabParam.getTabLevel() > 0)
        			curFr.setIsParentModifying(isModifying());
    		} else {
    			curFr.setIsParentModifying(isModifying());
    		}
    		//	
    		curFr.refreshFromChange(false);
    	}
    }
    
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    	I_DynamicTab curFr = (I_DynamicTab) getCurrentFragment();
    	if(curFr != null) {
    		TabParameter tabParam = getCurrentTabParameter();
    		if(tabParam != null
    				&& tabParam.getTabLevel() == 0)
    			setIsModifying(curFr.isModifying());
    	}
    	//	
    	super.onTabUnselected(tab, ft);
    }
    
    /**
     * Load Tabs
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 13:52:39
     * @return void
     */
    private void loadTabs(){
    	//	Get Tabs from windows activity
    	DB conn = new DB(this);
    	DB.loadConnection(conn, DB.READ_ONLY);
		Cursor rs = null;
		String language = Env.getAD_Language(this);
		boolean isBaseLanguage = Env.isBaseLanguage(this);
		String sql = null;
		//	Handle Translation
		if(isBaseLanguage) {
			sql = new String("SELECT t.SPS_Tab_ID, t.SeqNo, t.TabLevel, " +
					"COALESCE(t.IsReadOnly, 'N') IsReadOnly, " +
					"COALESCE(t.IsInsertRecord, 'N') IsInsertRecord, t.Name, t.Description, " +
					"t.OrderByClause, t.SPS_Table_ID, t.SPS_Window_ID, " +
					"t.WhereClause, t.Classname " +
					"FROM SPS_Tab t " +
					"WHERE t.IsActive = 'Y' " +
					"AND t.SPS_Window_ID = " + param.getSPS_Window_ID() + " " + 
					"ORDER BY t.SeqNo");
		} else {
			sql = new String("SELECT t.SPS_Tab_ID, t.SeqNo, t.TabLevel, " +
					"COALESCE(t.IsReadOnly, 'N') IsReadOnly, " +
					"COALESCE(t.IsInsertRecord, 'N') IsInsertRecord, COALESCE(tt.Name, t.Name) Name, " + 
					"COALESCE(tt.Description, t.Description) Description, " +
					"t.OrderByClause, t.SPS_Table_ID, t.SPS_Window_ID, " +
					"t.WhereClause, t.Classname " +
					"FROM SPS_Tab t " +
					"LEFT JOIN SPS_Tab_Trl tt ON(tt.SPS_Tab_ID = t.SPS_Tab_ID AND tt.AD_Language = '" + language + "') " + 
					"WHERE t.IsActive = 'Y' " +
					"AND t.SPS_Window_ID = " + param.getSPS_Window_ID() + " " + 
					"ORDER BY t.SeqNo");
		}
		//	Execute
		rs = conn.querySQL(sql, null);
		if(rs.moveToFirst()){
			//	Index
			int index = 0;
			int tabNo = 0;
			//	Set Current Tab No
			Env.setCurrentTab(this, m_ActivityNo, 0);
			do{
				TabParameter tabParam = new TabParameter();
				tabParam.setActivityNo(m_ActivityNo);
				tabParam.setSPS_Tab_ID(rs.getInt(index++));
	    		tabParam.setTabNo(tabNo++);
	    		tabParam.setSeqNo(rs.getInt(index++));
	    		tabParam.setTabLevel(rs.getInt(index++));
	    		tabParam.setIsReadOnly(rs.getString(index++).equals("Y"));
	    		tabParam.setIsInsertRecord(rs.getString(index++).equals("Y"));
	    		tabParam.setName(rs.getString(index++));
	    		tabParam.setDescription(rs.getString(index++));
	    		tabParam.setOrderByClause(rs.getString(index++));
	    		tabParam.setSPS_Table_ID(rs.getInt(index++));
	    		tabParam.setSPS_Window_ID(rs.getInt(index++));
	    		tabParam.setWhereClause(rs.getString(index++));
	    		tabParam.setClassname(rs.getString(index++));
	    		//	Parameter
	    		Bundle bundle = new Bundle();
	    		bundle.putParcelable("TabParam", tabParam);
	    		//bundle.putInt("Record_ID", m_Record_ID);
	    		//	Set Context
	    		Env.setContext(getApplicationContext(), m_ActivityNo, 
	    				tabParam.getSPS_Tab_ID(), "SPS_Tab_ID", tabParam.getSPS_Tab_ID());
	    		//	Set record Identifier
	    		if(tabParam.getTabNo() == 0)
	    			Env.setTabRecord_ID(getApplicationContext(), 
	    					tabParam.getActivityNo(), tabParam.getTabNo(), m_Record_ID);
	    		else
	    			Env.setTabRecord_ID(getApplicationContext(), 
	    					tabParam.getActivityNo(), tabParam.getTabNo(), 0);
	    		//	Dynamic Tab
	    		if(tabParam.getSPS_Table_ID() != 0) {
		    		//	Parent Tab
		    		if(tabParam.getTabLevel() != 0) {
		    			Env.setParentTabRecord_ID(getApplicationContext(), 
		    					tabParam.getActivityNo(), tabParam.getTabNo(), m_Record_ID);
		    			addFagment(T_DynamicTabDetail.class, tabParam.getName(), tabParam.getName(), tabParam, bundle);
		    		} else {	//	Add Dynamic Tab
		    			addFagment(T_DynamicTab.class, tabParam.getName(), tabParam.getName(), tabParam, bundle);
		    		}

	    		}
	    		//	Add Custom Tab
	    		else if(tabParam.getClassname() != null
	    				&& tabParam.getClassname().length() > 0) {
	    			try {
	    				Class<?> clazz = Class.forName(tabParam.getClassname());
	    				//	Add
	    				addFagment(clazz, tabParam.getName(), tabParam.getName(), tabParam, bundle);
	    			} catch (ClassNotFoundException e) {
	    				LogM.log(getApplicationContext(), getClass(), Level.SEVERE, "Error:", e);
	    			}
	    		} else 
	    			LogM.log(getApplicationContext(), getClass(), Level.WARNING, "No Class for Tab: " + tabParam.getName());
				//	Reset index
	    		index = 0;
			}while(rs.moveToNext());
		}
		DB.closeConnection(conn);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Fragment currentFragment = getCurrentFragment();
    	if(currentFragment != null)
    		currentFragment.onActivityResult(requestCode, resultCode, data);
    }

	@Override
	public void onItemSelected(int record_ID) {
		invalidateOptionsMenu();
		Fragment curFr = getCurrentFragment();
    	if(curFr != null
    			&& curFr instanceof I_FragmentSelectListener){
    		((I_FragmentSelectListener) curFr).onItemSelected(record_ID);
    	}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//	
		boolean ok = false;
		I_DynamicTab curFr = (I_DynamicTab) getCurrentFragment();
    	if(curFr != null)
    		ok = curFr.onKeyDown(keyCode, event);
    	//	
    	if(!ok)
    		ok = super.onKeyDown(keyCode, event);
		return ok;
	}
}
