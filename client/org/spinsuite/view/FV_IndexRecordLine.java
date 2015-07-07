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
import org.spinsuite.interfaces.I_DT_FragmentSelectListener;
import org.spinsuite.util.Env;
import org.spinsuite.util.FilterValue;
import org.spinsuite.util.MultiKeyNamePair;
import org.spinsuite.util.TabParameter;
import org.spinsuite.view.lookup.InfoLookup;
import org.spinsuite.view.lookup.InfoTab;
import org.spinsuite.view.lookup.Lookup;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FV_IndexRecordLine extends T_FormTab {
    
	/**	Fragment Listener Call Back	*/
	private I_DT_FragmentSelectListener 	m_Callback 			= null;
	/**	Parameters					*/
	private TabParameter					tabParam 			= null;
	/**	Tab Info					*/
	private InfoTab 						tabInfo				= null;
	/**	Lookup 						*/
	private Lookup 							lookup 				= null;
	/**	Adapter						*/
	private ArrayAdapter<MultiKeyNamePair> 	m_Adapter			= null;
	/**	View						*/
	private View 							m_View				= null;
	/**	List View					*/
	private ListView						lv_index_records 	= null;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	Current
		if(m_View != null)
			return m_View;
		
		//	Re-Load
		m_View 				= inflater.inflate(R.layout.t_index_record, container, false);
		lv_index_records 	= (ListView) m_View.findViewById(R.id.lv_Index_Records);
		lv_index_records.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
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
    	MultiKeyNamePair pair = m_Adapter.getItem(position);
    	selectIndex(pair.getMultiKey(), lookup.getInfoLookup().KeyColumn);
    	//	Change on List View
    	lv_index_records.setItemChecked(position, true);
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
    		int[] currentParent_Record_ID = Env.getTabRecord_ID(
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
    	m_Adapter = new ArrayAdapter<MultiKeyNamePair>(getActivity(), R.layout.v_lookup_list, values);
    	//	Set Adapter List
    	lv_index_records.setAdapter(m_Adapter);
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
    	if(m_Adapter != null
    			&& !m_Adapter.isEmpty()) {
            //	Set Selected
    		MultiKeyNamePair pair = m_Adapter.getItem(0);
            //	
            Env.setTabRecord_ID(
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
    	Env.setTabRecord_ID(
				tabParam.getActivityNo(), tabParam.getTabNo(), record_ID);
    	Env.setTabKeyColumns(
				tabParam.getActivityNo(), tabParam.getTabNo(), keyColumns);
    	//	
    	m_Callback.onItemSelected(record_ID, keyColumns);

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
    		int[] currentParent_Record_ID = Env.getTabRecord_ID(
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
	public void setTabParameter(TabParameter tabParam) {
		super.setTabParameter(tabParam);
		loadData();
	}
}