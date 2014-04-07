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


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.Menu;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class TV_DynamicActivity extends TV_Base implements I_FragmentSelectListener {
	
	/**	Parameters					*/
	private ActivityParameter 	param			= null;
	/**	Record Identifier			*/
	private int 				m_Record_ID 	= 0;
	/**	Lookup Menu					*/
	private LookupMenu 			lookupMenu 		= null;
	
	/**	Load Action Menu			*/
	private LoadActionMenu		loadActionMenu 	= null;;
	
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
			MenuAdapter mi_adapter = new MenuAdapter(this, R.layout.i_activity_menu, true, lookupMenu.getData());
			mi_adapter.setDropDownViewResource(R.layout.i_activity_menu);
			getDrawerList().setAdapter(mi_adapter);
		}
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(!isDrawerLayoutOpen()){
        	I_DynamicTab curFr = (I_DynamicTab) getCurrentFragment();
        	//	Handle Current Menu
        	if(curFr != null)
        		curFr.handleMenu();
        }
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
    		//	Set From Called
        	actParam.setFrom_SFA_Table_ID(tabParam.getSFA_Table_ID());
        	actParam.setFrom_Record_ID(Env.getTabRecord_ID(getApplicationContext(), m_ActivityNo, tabParam.getTabNo()));
        	//	
        	loadActionMenu.loadAction(item, actParam);
    	}
    }
    
    @Override
    public void onPageSelected(int position) {
    	super.onPageSelected(position);
    	invalidateOptionsMenu();
    	I_DynamicTab curFr = (I_DynamicTab) getCurrentFragment();
    	if(curFr != null)
    		curFr.refreshFromChange(false);
    }
    
    /**
     * Load Tabs
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 13:52:39
     * @return void
     */
    private void loadTabs(){
    	//	Get Tabs from windows activity
    	DB conn = new DB(getApplicationContext());
    	DB.loadConnection(conn, DB.READ_ONLY);
		Cursor rs = null;
		rs = conn.querySQL("SELECT t.SFA_Tab_ID, t.SeqNo, t.TabLevel, " +
				"COALESCE(t.IsReadOnly, 'N'), t.Name, t.Description, " +
				"t.OrderByClause, t.SFA_Table_ID, t.SFA_Window_ID, " +
				"t.WhereClause, t.Classname " +
				"FROM SFA_Tab t " +
				"WHERE t.IsActive = 'Y' " +
				"AND t.SFA_Window_ID = " + param.getSFA_Window_ID() + " " + 
				"ORDER BY t.SeqNo", null);
		if(rs.moveToFirst()){
			int index = 0;
			int tabNo = 0;
			do{
				TabParameter tabParam = new TabParameter();
				tabParam.setActivityNo(m_ActivityNo);
				tabParam.setSFA_Tab_ID(rs.getInt(index++));
	    		tabParam.setTabNo(tabNo++);
	    		tabParam.setSeqNo(rs.getInt(index++));
	    		tabParam.setTabLevel(rs.getInt(index++));
	    		tabParam.setIsReadOnly(rs.getString(index++).equals("Y"));
	    		tabParam.setName(rs.getString(index++));
	    		tabParam.setDescription(rs.getString(index++));
	    		tabParam.setOrderByClause(rs.getString(index++));
	    		tabParam.setSFA_Table_ID(rs.getInt(index++));
	    		tabParam.setSFA_Window_ID(rs.getInt(index++));
	    		tabParam.setWhereClause(rs.getString(index++));
	    		tabParam.setClassname(rs.getString(index++));
	    		//	Parameter
	    		Bundle bundle = new Bundle();
	    		bundle.putParcelable("TabParam", tabParam);
	    		//bundle.putInt("Record_ID", m_Record_ID);
	    		//	Set Context
	    		Env.setContext(getApplicationContext(), m_ActivityNo, 
	    				tabParam.getSFA_Tab_ID(), "SFA_Tab_ID", tabParam.getSFA_Tab_ID());
	    		//	Set record Identifier
	    		if(tabParam.getTabNo() == 0)
	    			Env.setTabRecord_ID(getApplicationContext(), 
	    					tabParam.getActivityNo(), tabParam.getTabNo(), m_Record_ID);
	    		else
	    			Env.setTabRecord_ID(getApplicationContext(), 
	    					tabParam.getActivityNo(), tabParam.getTabNo(), 0);
	    		
	    		//	Parent Tab
	    		if(tabParam.getTabLevel() != 0){
	    			Env.setParentTabRecord_ID(getApplicationContext(), 
	    					tabParam.getActivityNo(), tabParam.getTabNo(), m_Record_ID);
	    			addFagment(T_DynamicTabDetail.class, tabParam.getName(), tabParam.getName(), bundle);
	    		} else if(tabParam.getSFA_Table_ID() != 0){	//	Add Dynamic Tab
	    			addFagment(T_DynamicTab.class, tabParam.getName(), tabParam.getName(), bundle);
	    		}
	    		//	Add Custom Tab
	    		else if(tabParam.getClassname() != null
	    				&& tabParam.getClassname().length() > 0){
	    			try {
	    				Class<?> clazz = Class.forName(tabParam.getClassname());
	    				//	Add
	    				addFagment(clazz, tabParam.getName(), tabParam.getName(), bundle);
	    			} catch (ClassNotFoundException e) {
	    				LogM.log(getApplicationContext(), getClass(), Level.SEVERE, "Error:", e);
	    			}
	    		}
				//	Reset index
	    		index = 0;
			}while(rs.moveToNext());
		}
		DB.closeConnection(conn);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == Activity.RESULT_OK) {
	    	if(data != null){
	    		Fragment currentFragment = getCurrentFragment();
	    		if(currentFragment != null)
	    			currentFragment.onActivityResult(requestCode, resultCode, data);
	    	}
    	}
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
