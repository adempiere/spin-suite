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

import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DynamicTab;
import org.spinsuite.interfaces.I_FragmentSelectListener;
import org.spinsuite.util.Env;
import org.spinsuite.util.TabParameter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class T_DynamicTabDetail extends Fragment 
		implements I_DynamicTab, I_FragmentSelectListener {
	
	/**	Parameters			*/
	private TabParameter		tabParam 		= null;
	/**	Index Fragment		*/
	public static final String 	INDEX_FRAGMENT 	= "Index";
	/**	Detail Fragment		*/
	public static final String 	DETAIL_FRAGMENT = "Detail";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v =  inflater.inflate(R.layout.t_dynamic_tab_detail, container, false);
		return v;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	//	Not Change
    	setRetainInstance(true);
    	
    	Bundle bundle = getArguments();
    	if(bundle != null)
			tabParam = (TabParameter)bundle.getParcelable("TabParam");
			
    	if(tabParam == null)
    		tabParam = new TabParameter();
    	
    	
    	FV_IndexRecordLine firstFragment = new FV_IndexRecordLine();
        //	Set Parameters
        firstFragment.setArguments(bundle);
        //	
    	if (getActivity().findViewById(R.id.ll_List) != null) {
    		getChildFragmentManager().beginTransaction()
                    .add(R.id.ll_List, firstFragment, INDEX_FRAGMENT).commit();
        } else if(getActivity().findViewById(R.id.ll_ListLand) != null){
        	getChildFragmentManager().beginTransaction()
            .add(R.id.ll_index_record_line, firstFragment, INDEX_FRAGMENT).commit();
        }

	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    }
	
	@Override
	public void handleMenu() {
		//	
	}

	@Override
	public TabParameter getTabParameter() {
		return tabParam;
	}

	@Override
	public void onItemSelected(int record_ID) {
		//	
		T_DynamicTab dynamicTab = (T_DynamicTab)
                getChildFragmentManager().findFragmentByTag(DETAIL_FRAGMENT);
		//	
        if (dynamicTab != null) {
        	//	Set Parameters
        	if(dynamicTab.getTabParameter() == null)
        		dynamicTab.setTabParameter(tabParam);
        	//	
            dynamicTab.onItemSelected(record_ID);
        } else {
        	T_DynamicTab detailFragment = new T_DynamicTab();
        	detailFragment.setFromTab(this);
            Bundle args = new Bundle();
            args.putParcelable("TabParam", tabParam);
            detailFragment.setArguments(args);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            //	
            if(getActivity().findViewById(R.id.ll_ListLand) != null)
            	transaction.add(R.id.ll_dynamic_tab, detailFragment, DETAIL_FRAGMENT);
            else 
            	transaction.replace(R.id.ll_List, detailFragment, DETAIL_FRAGMENT);
            transaction.addToBackStack(null);
            //	
            transaction.commit();
        }
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			//	
			return backToFragment();
		}
		//	
		return false;
	}

	@Override
	public boolean refreshFromChange(boolean reQuery) {
		I_DynamicTab indexRecordLine = (I_DynamicTab) 
				getChildFragmentManager().findFragmentByTag(INDEX_FRAGMENT);
		if(indexRecordLine != null){
			if(!Env.isCurrentTab(getActivity(), 
					tabParam.getActivityNo(), tabParam.getTabNo()))
				backToFragment();
			return indexRecordLine.refreshFromChange(reQuery);
		}
		//	Return
		return false;
	}

	@Override
	public void setTabParameter(TabParameter tabParam) {
		//	
	}
	
	/**
	 * Back to Index Fragment
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/04/2014, 11:37:12
	 * @return
	 * @return boolean
	 */
	private boolean backToFragment(){
		if(getActivity().findViewById(R.id.ll_ListLand) != null)
			return false;
		FragmentManager fm = getChildFragmentManager();
	    if (fm.getBackStackEntryCount() > 0){
	    	//	Get Back
			fm.popBackStack();
	    	return true;
	    }
	    //	Return
	    return false;
	}
}
