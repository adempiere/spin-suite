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

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DynamicTab;
import org.spinsuite.interfaces.I_FragmentSelectListener;
import org.spinsuite.util.Env;
import org.spinsuite.util.FilterValue;
import org.spinsuite.util.MultiKeyNamePair;
import org.spinsuite.util.TabParameter;
import org.spinsuite.view.lookup.InfoLookup;
import org.spinsuite.view.lookup.InfoTab;
import org.spinsuite.view.lookup.Lookup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FV_IndexRecordLine extends ListFragment 
									implements I_DynamicTab {
    
	/**	Fragment Listener Call Back	*/
	private I_FragmentSelectListener 		m_Callback 			= null;
	/**	Parameters					*/
	private TabParameter					tabParam 			= null;
	/**	Tab Info					*/
	private InfoTab 						tabInfo				= null;
	/**	Lookup 						*/
	private Lookup 							lookup 				= null;
	/**	Adapter						*/
	private ArrayAdapter<MultiKeyNamePair> 	adapter				= null;
	/**	Parent Tab Record ID		*/
	private int 							m_Parent_Record_ID 	= 0;
	/**	Is Load Ok					*/
	private boolean							m_IsLoadOk			= false;
	

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/04/2014, 17:42:27
	 */
    public FV_IndexRecordLine(){
    	
    }
    
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
        //	Get Arguments
        Bundle bundle = getArguments();
    	if(bundle != null)
			tabParam = (TabParameter)bundle.getParcelable("TabParam");
    	//	
    	if(tabParam != null
    			&& tabParam.getTabLevel() > 0){
    		int[] currentParent_Record_ID = Env.getTabRecord_ID(getActivity(), 
        			tabParam.getActivityNo(), tabParam.getParentTabNo());
        	if(m_Parent_Record_ID != currentParent_Record_ID[0]){
        		m_Parent_Record_ID = currentParent_Record_ID[0];
        	}
    	}
    	//	Load Ok
    	m_IsLoadOk = true;
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
    private boolean loadData(){
    	//	Instance Tab Information
    	tabInfo = new InfoTab(getActivity(), tabParam.getSPS_Tab_ID(), null);
    	FilterValue criteria = tabInfo.getCriteria(getActivity(), 
				tabParam.getActivityNo(), tabParam.getParentTabNo());
    	//	Load SQL
    	if(lookup == null){
    		lookup = new Lookup(getActivity(), tabParam.getSPS_Table_ID());
    		//	Get Where Clause
    		lookup.setCriteria(criteria.getWhereClause());
    	}
    	//	Get Values
    	MultiKeyNamePair[] values = DB.getMultiKeyNamePairs(getActivity(), 
    			lookup.getSQL().replaceAll(InfoLookup.TABLE_SEARCH_SEPARATOR, 
    					InfoLookup.TABLE_SEARCH_VIEW_SEPARATOR), 
    					lookup.getInfoLookup().KeyColumn.length, criteria.getValues());
        //	Is Loaded
    	boolean isLoaded = (values != null && values.length != 0);
    	//	Set Adapter
    	if(!isLoaded){
    		MultiKeyNamePair voidRecord = new MultiKeyNamePair(new int[]{0}, getActivity().getString(R.string.msg_NewRecord));
    		values = new MultiKeyNamePair[]{voidRecord};
    	}
    	//	Instance Adapter
    	adapter = new ArrayAdapter<MultiKeyNamePair>(getActivity(), R.layout.v_lookup_list, values);
    	//	Set Adapter List
    	setListAdapter(adapter);
    	//	Return
        return isLoaded;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
        //	Choice Mode
        if (getFragmentManager()
        		.findFragmentByTag(T_DynamicTabDetail.INDEX_FRAGMENT) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            m_Callback = (I_FragmentSelectListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement I_FragmentSelectListener");
        }
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //	Set Selected
    	MultiKeyNamePair pair = adapter.getItem(position);
    	selectIndex(pair.getMultiKey(), lookup.getInfoLookup().KeyColumn);
    	//	Change on List View
    	getListView().setItemChecked(position, true);
    }
    
    /**
     * Select first record
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/10/2014, 17:56:40
     * @return void
     */
    public void selectFirst() {
    	//	Select first record
    	if(adapter != null
    			&& !adapter.isEmpty()) {
            //	Set Selected
    		MultiKeyNamePair pair = adapter.getItem(0);
            //	
            Env.setTabRecord_ID(getActivity(), 
    				tabParam.getActivityNo(), tabParam.getTabNo(), pair.getMultiKey());
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
    	Env.setTabRecord_ID(getActivity(), 
				tabParam.getActivityNo(), tabParam.getTabNo(), record_ID);
    	Env.setTabKeyColumns(getActivity(), 
				tabParam.getActivityNo(), tabParam.getTabNo(), keyColumns);
    	//	
    	m_Callback.onItemSelected(record_ID, keyColumns);

    }

	@Override
	public void handleMenu() {
		
	}

	@Override
	public TabParameter getTabParameter() {
		return null;
	}

	@Override
	public boolean refreshFromChange(boolean reQuery) {
	   	//	Valid is Loaded
    	if(!m_IsLoadOk)
    		return false;
 		//	Load Data
		boolean loaded = true;
		if(reQuery){
			loaded = loadData();
			selectFirst();
		} else if(tabParam.getTabLevel() > 0){
    		int[] currentParent_Record_ID = Env.getTabRecord_ID(getActivity(), 
        			tabParam.getActivityNo(), tabParam.getParentTabNo());
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public void setTabParameter(TabParameter tabParam) {
		this.tabParam = tabParam;
		loadData();
	}

	@Override
	public boolean save() {
		return false;
	}

	@Override
	public boolean isModifying() {
		return false;
	}

	@Override
	public void setIsParentModifying(boolean enabled) {
		//	
	}
}