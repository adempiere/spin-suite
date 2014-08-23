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

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DynamicTab;
import org.spinsuite.interfaces.I_FragmentSelectListener;
import org.spinsuite.util.Env;
import org.spinsuite.util.FilterValue;
import org.spinsuite.util.KeyNamePair;
import org.spinsuite.util.TabParameter;
import org.spinsuite.view.lookup.InfoTab;
import org.spinsuite.view.lookup.LookupDisplayType;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FV_IndexRecordLine extends ListFragment implements I_DynamicTab {
    
	/**	Fragment Listener Call Back	*/
	private I_FragmentSelectListener 	m_Callback 			= null;
	/**	Parameters					*/
	private TabParameter				tabParam 			= null;
	/**	Tab Info					*/
	private InfoTab 					tabInfo				= null;
	/**	Lookup 						*/
	private LookupDisplayType 			lookup 				= null;
	/**	Layout Type					*/
	private int 						layout				= 0;
	/**	Adapter						*/
	private ArrayAdapter<KeyNamePair> 	adapter				= null;
	/**	Parent Tab Record ID		*/
	private int 						m_Parent_Record_ID 	= 0;
	

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/04/2014, 17:42:27
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
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //	Get Layout
        layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB 
        		? android.R.layout.simple_list_item_activated_1 
        				: android.R.layout.simple_list_item_1;
    }
    
    /**
     * Load List
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/04/2014, 21:11:36
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
    		lookup = new LookupDisplayType(getActivity(), tabParam.getSPS_Table_ID());
    		//	Get Where Clause
    		lookup.setCriteria(criteria.getWhereClause());
    	}
    	//	Get Values
    	KeyNamePair[] values = DB.getKeyNamePairs(getActivity(), 
    			lookup.getSQL(), criteria.getValues());
        //	Is Loaded
    	boolean isLoaded = (values != null && values.length != 0);
    	//	Set Adapter
    	if(!isLoaded){
    		KeyNamePair voidRecord = new KeyNamePair(0, getActivity().getString(R.string.msg_NewRecord));
    		values = new KeyNamePair[]{voidRecord};
    	}
    	//	Instance Adapter
    	adapter = new ArrayAdapter<KeyNamePair>(getActivity(), layout, values);
    	//	Set Adapter List
    	setListAdapter(adapter);
    	//	Return
        return isLoaded;
    }

    @Override
    public void onStart() {
        super.onStart();
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
    	KeyNamePair pair = adapter.getItem(position);
    	selectIndex(pair.getKey());
    	//	Change on List View
    	getListView().setItemChecked(position, true);
    }
    
    /**
     * Select Index
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/04/2014, 10:21:07
     * @param record_ID
     * @return void
     */
    private void selectIndex(int record_ID){
    	//	Set Record Identifier
    	Env.setTabRecord_ID(getActivity(), 
				tabParam.getActivityNo(), tabParam.getTabNo(), record_ID);
    	//	
    	m_Callback.onItemSelected(record_ID);

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
		//	Load Data
		boolean loaded = true;
		if(reQuery){
			loaded = loadData();
		} else if(tabParam.getTabLevel() > 0){
    		int currentParent_Record_ID = Env.getTabRecord_ID(getActivity(), 
        			tabParam.getActivityNo(), tabParam.getParentTabNo());
        	if(m_Parent_Record_ID != currentParent_Record_ID){
        		m_Parent_Record_ID = currentParent_Record_ID;
        		loaded = loadData();
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
}