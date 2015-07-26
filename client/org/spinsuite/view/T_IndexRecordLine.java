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
import java.util.logging.Level;

import org.spinsuite.adapters.SearchAdapter;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DT_FragmentSelectListener;
import org.spinsuite.util.DisplaySearchItem;
import org.spinsuite.util.Env;
import org.spinsuite.util.FilterValue;
import org.spinsuite.util.IdentifierValueWrapper;
import org.spinsuite.util.LogM;
import org.spinsuite.util.TabParameter;
import org.spinsuite.view.lookup.InfoTab;
import org.spinsuite.view.lookup.Lookup;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Jul 26, 2015, 2:14:25 AM
 * <li> Better view in child tab records
 * @see https://adempiere.atlassian.net/browse/SPIN-24
 */
public class T_IndexRecordLine extends T_FormTab {
    
	/**	Fragment Listener Call Back	*/
	private I_DT_FragmentSelectListener 	m_Callback 			= null;
	/**	Parameters					*/
	private InfoTab 						tabInfo				= null;
	/**	Lookup 						*/
	private Lookup 							lookup 				= null;
	/**	Adapter						*/
	private SearchAdapter 					m_SearchAdapter 	= null;
	/**	View						*/
	private View 							m_View				= null;
	/**	List View					*/
	private ListView						lv_index_records 	= null;
	/**	Parent Tab Record ID		*/
	private int 							m_Parent_Record_ID 	= 0;
	/**	New Item					*/
	private MenuItem 						mi_Add 				= null;
	

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/04/2014, 17:42:27
	 */
    public T_IndexRecordLine(){
    	
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //	
        menu.clear();
        inflater.inflate(R.menu.search, menu);
    	//	do it
        //	Get Items
        menu.findItem(R.id.action_config).setVisible(false);
        menu.findItem(R.id.action_close).setVisible(false);
        mi_Add = menu.findItem(R.id.action_add);
    	//	Set Visible New
    	mi_Add.setVisible(isInsertRecord() && !isReadOnly());
		//	Get Item
		MenuItem item = menu.findItem(R.id.action_search);
		//	Search View
		final View searchView = SearchViewCompat.newSearchView(getActivity());
		if (searchView != null) {
			//	Set Back ground Color
			int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
			EditText searchText = (EditText) searchView.findViewById(id);
			//	Set Parameters
			if(searchText != null)
				searchText.setTextAppearance(getActivity(), R.style.TextSearch);
			//	
			SearchViewCompat.setOnQueryTextListener(searchView,
					new OnQueryTextListenerCompat() {
				@Override
				public boolean onQueryTextChange(String newText) {
					if(m_SearchAdapter != null) {
						String mFilter = !TextUtils.isEmpty(newText) ? newText : null;
						m_SearchAdapter.getFilter().filter(mFilter);
					}
					return true;
				}
			});
			SearchViewCompat.setOnCloseListener(searchView,
					new OnCloseListenerCompat() {
				@Override
				public boolean onClose() {
					if (!TextUtils.isEmpty(SearchViewCompat.getQuery(searchView))) {
						SearchViewCompat.setQuery(searchView, null, true);
					}
					return true;
				}
                    
			});
			MenuItemCompat.setActionView(item, searchView);
		}
		//	Load Ok
		setIsLoadOk(true);
    }
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	Current
		if(m_View != null)
			return m_View;
		
		//	Re-Load
		m_View 				= inflater.inflate(R.layout.t_index_record, container, false);
		lv_index_records 	= (ListView) m_View.findViewById(R.id.lv_Index_Records);
		lv_index_records.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long arg3) {
				selectItem(position);
			}
        });
		//	Add Listener for List
		return m_View;
	}
    
    /**
     * Select a Item
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param position
     * @return void
     */
    private void selectItem(int position) {
        //	Set Selected
    	DisplaySearchItem pair = m_SearchAdapter.getItem(position);
    	selectIndex(pair.getKeys(), lookup.getInfoLookup().KeyColumn);
    	//	Change on List View
    	lv_index_records.setItemChecked(position, true);
    }
    
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	//	
    	if(getTabParameter() != null
    			&& getTabLevel() > 0){
    		int[] currentParent_Record_ID = Env.getTabRecord_ID(
        			getActivityNo(), getParentTabNo());
        	if(m_Parent_Record_ID != currentParent_Record_ID[0]){
        		m_Parent_Record_ID = currentParent_Record_ID[0];
        	}
    	}
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    /**
     * Load List
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/04/2014, 21:11:36
     * @return
     * @return boolean
     */
    private boolean loadData() {
    	if(getCallback() == null)
    		return false;
    	//	Instance Tab Information
    	tabInfo = new InfoTab(getActivity(), getSPS_Tab_ID(), null);
    	FilterValue criteria = tabInfo.getCriteria(getActivity(), 
				getActivityNo(), getParentTabNo());
    	//	Load SQL
    	if(lookup == null){
    		lookup = new Lookup(getActivity(), getSPS_Table_ID());
    		//	Get Where Clause
    		lookup.setCriteria(criteria.getWhereClause());
    	}
    	//	Get Values
    	ArrayList<DisplaySearchItem> data = new ArrayList<DisplaySearchItem>();
    	//	Get values from DB
    	try{
			//	
			DB conn = new DB(getActivity());
			DB.loadConnection(conn, DB.READ_ONLY);
			Cursor rs = null;
			//	Query
			rs = conn.querySQL(lookup.getSQL(), criteria.getValues());
			//	
			String[] keyColumns = lookup.getInfoLookup().KeyColumn;
			int keyCount = keyColumns.length;
			if(rs.moveToFirst()) {
				//	Loop
				do{
					//	Declare Keys
					int[] keys = new int[keyCount];
					//	Get Keys
					for(int i = 0; i < keyCount; i++) {
						keys[i] = rs.getInt(i);
					}
					//	Tmp Key count
					int keyCountAdd = keyCount;
					//	
					String value = rs.getString(keyCountAdd++);
					IdentifierValueWrapper[] columnValues = Env.parseLookupArray(getActivity(), lookup.getInfoLookup(), value);
					data.add(new DisplaySearchItem (
							keys, 
							keyColumns, 
							Env.parseLookup(getActivity(), lookup.getInfoLookup(), value, Env.NL), 
							rs.getString(keyCountAdd++), 
							columnValues));
				} while(rs.moveToNext());
			}
			//	Close
			DB.closeConnection(conn);
		} catch(Exception e) {
			LogM.log(getActivity(), getClass(), Level.SEVERE, "Error in Load", e);
		}
    	//	Is Loaded
    	boolean m_IsLoadedData = (data.size() != 0);
    	//	Instance Adapter
    	m_SearchAdapter = new SearchAdapter(getActivity(), data);
    	//	Set Adapter List
    	lv_index_records.setAdapter(m_SearchAdapter);
    	//	Return
        return m_IsLoadedData;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	int itemId = item.getItemId();
    	if(itemId == R.id.action_add) {
    		selectIndex(new int[]{-1}, null);
    	}
		//	
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
        //	Choice Mode
        if (getFragmentManager()
        		.findFragmentByTag(T_DynamicTabDetail.INDEX_FRAGMENT) != null) {
        	lv_index_records.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            m_Callback = (I_DT_FragmentSelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement I_FragmentSelectListener");
        }
    }
    
    /**
     * Select first record
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/10/2014, 17:56:40
     * @return void
     */
    public void selectFirst() {
    	//	Select first record
    	if(m_SearchAdapter != null
    			&& !m_SearchAdapter.isEmpty()) {
            //	Set Selected
    		DisplaySearchItem pair = m_SearchAdapter.getItem(0);
            //	
            Env.setTabRecord_ID(
    				getActivityNo(), getTabNo(), pair.getKeys());
    	}
    }
    
    /**
     * Select Index
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 02/04/2014, 10:21:07
     * @param record_ID
     * @return void
     */
    private void selectIndex(int [] record_ID, String [] keyColumns){
    	//	Set Record Identifier
    	Env.setTabRecord_ID(
				getActivityNo(), getTabNo(), record_ID);
    	Env.setTabKeyColumns(
				getActivityNo(), getTabNo(), keyColumns);
    	//	
    	m_Callback.onItemSelected(record_ID, keyColumns);

    }

	@Override
	public boolean refreshFromChange(boolean reQuery) {
	   	//	Valid is Loaded
    	if(!isLoadOk())
    		return false;
 		//	Load Data
		boolean loaded = true;
		if(reQuery){
			loaded = loadData();
			selectFirst();
		} else if(getTabLevel() > 0){
    		int[] currentParent_Record_ID = Env.getTabRecord_ID(
        			getActivityNo(), getParentTabNo());
        	if(m_Parent_Record_ID != currentParent_Record_ID[0]){
        		m_Parent_Record_ID = currentParent_Record_ID[0];
        		loaded = loadData();
        		selectFirst();
        		//	
        		return loaded;
        	}
    	}
		return loaded;
	}

	@Override
	public void setTabParameter(TabParameter tabParam) {
		super.setTabParameter(tabParam);
		loadData();
	}
}