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
package org.spinsuite.view;


import java.util.ArrayList;

import org.spinsuite.adapters.MenuAdapter;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.bchat.view.V_BChat;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.model.MSession;
import org.spinsuite.util.ActivityParameter;
import org.spinsuite.util.DisplayMenuItem;
import org.spinsuite.util.DisplayRecordItem;
import org.spinsuite.util.KeyNamePair;
import org.spinsuite.util.LoadActionMenu;
import org.spinsuite.view.lookup.LookupMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * @contributor Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com
 *  	<li>Add Support to Log for Mobile
 *  	@see https://adempiere.atlassian.net/browse/SPIN-6
 */
public class T_Menu extends Fragment implements I_Login {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public T_Menu() {
		super();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_ctx
	 */
	public T_Menu(Context p_ctx) {
		
	}
	
	
	/**	List				*/
	private ListView 			menu;
	/**	Database Connection	*/
	private DB 					conn = null;
	/**	Parameter			*/
	private ActivityParameter 	param = null;
	/**	Activity No			*/
	protected int 				m_ActivityNo = 0;
	/**	Current Bundle		*/
	private Bundle 				currentOptionBundle = null;
	/**	Current Option Menu	*/
	private DisplayMenuItem 	currentMenuItem = null;
	/**	Lookup Menu			*/
	private LookupMenu 			lookupMenu = null;
	/**	Load Action Menu	*/
	private LoadActionMenu		loadActionMenu = null;
	/**	Current View		*/
	private View 				m_View = null;
	/**	Is Load Ok			*/
	private boolean				m_IsLoadOk = false;
	/**	Context				*/
	private Context				m_ctx = null;
	/**	Callback			*/
	private Activity 			m_Callback = null;
	/**	Array of Parent		*/
	private ArrayList<KeyNamePair>	m_ParentArray = new ArrayList<KeyNamePair>();
	/**	Current Parent ID	*/
	private int 				m_CurrentParent_ID = 0;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //	Current
    	if(m_View != null)
        	return m_View;
        //	RE-Load
        m_View = inflater.inflate(R.layout.v_menu, container, false);
    	return m_View;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	if(m_IsLoadOk)
    		return;
    	//	
		//	Get Params
		Bundle bundle = getActivity().getIntent().getExtras();		
    	if(bundle != null){
    		param = (ActivityParameter)bundle.getParcelable("Param");
		}
    	if(param == null)
    		param = new ActivityParameter();
		//	
    	m_ActivityNo = param.getSPS_Menu_ID();
    	//	Add Context
    	if(m_ctx == null) {
    		m_ctx = getActivity();
    	}
        //	
        menu = (ListView) m_View.findViewById(R.id.lv_Menu);
        //	
        menu.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				DisplayMenuItem item = (DisplayMenuItem) adapter.getItemAtPosition(position);
				param.setParent_ID(item.getSPS_Menu_ID());
				param.setActivityMenu_ID(item.getActivityMenu_ID());
				//	Load from Action
				currentOptionBundle = loadActionMenu.loadAction(item, param);
				currentMenuItem = item;
				if(item.isSummary()) {
					//	Get Current Menu ID and Sub Title
					m_ParentArray.add(new KeyNamePair(m_CurrentParent_ID, 
							m_Callback.getActionBar().getSubtitle().toString()));
					m_CurrentParent_ID = item.getSPS_Menu_ID();
					//	Change Title
					m_Callback.getActionBar().setSubtitle(currentMenuItem.getName());
					//	Load Data
					loadData();
				}
			}
        });
        //	new Menu
        lookupMenu = new LookupMenu(m_ctx, LookupMenu.MAIN_MENU, conn);
        //	Action Menu Loader
        loadActionMenu = new LoadActionMenu(m_Callback, false);
        //Carlos Parada Add Support to Log for Mobile
        MSession.get (m_ctx, true);
        //End Carlos Parada
        //	Set Load
        m_IsLoadOk = true;
        //	Load Data
        loadData();
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        m_Callback = activity;
    }
    
    /**
     * Back to Parent Menu
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return boolean
     */
    public boolean backToParent() {
    	//	Verify if is Main
    	if(m_ParentArray.size() == 0) {
    		return false;
    	}
    	//	Reload
    	int index = m_ParentArray.size() - 1;
    	KeyNamePair pair = m_ParentArray.get(index);
    	m_CurrentParent_ID = pair.getKey();
    	//	Change Subtitle
    	m_Callback.getActionBar().setSubtitle(pair.getName());
    	loadData();
    	//	Delete Parent of Array
    	m_ParentArray.remove(index);
    	//	Default
    	return true;
    }
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == Activity.RESULT_OK) {
	    	if(data != null){
	    		Bundle bundle = data.getExtras();
	    		DisplayRecordItem item = (DisplayRecordItem) bundle.getParcelable("Record");
	    		//	if a record is not null
	    		if(item != null){
	    			int[] keys = item.getKeys();
		    		currentOptionBundle.putIntArray("Record_ID", keys);
		    		loadActionMenu.loadActivityWithAction(currentMenuItem, currentOptionBundle);
	    		}
	    	}
    	}
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    }
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	        case android.R.id.home:
	    		//	Auto Login
//	    		Env.setAutoLoginComfirmed(false);
	        	m_Callback.finish();
	        return true;
	        case R.id.action_bchat:
	        	Intent bChat = new Intent(m_Callback, V_BChat.class);
				startActivity(bChat);
				return true;
	        case R.id.action_config:
	        	Intent preferences = new Intent(m_Callback, V_Preferences.class);
				startActivity(preferences);
				return true;
	        default:
	        	return super.onOptionsItemSelected(item);
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
		//	Load Children
		lookupMenu.loadChildren(m_CurrentParent_ID);
		//	
		MenuAdapter mi_adapter = new MenuAdapter(m_Callback, R.layout.i_image_text, true, lookupMenu.getData());
		mi_adapter.setDropDownViewResource(R.layout.i_image_text);
		menu.setAdapter(mi_adapter);
		//	
		return true;
	}

	@Override
	public void setEnabled(boolean enabled) {
		
	}
}
