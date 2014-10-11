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

import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DynamicTab;
import org.spinsuite.interfaces.I_FragmentSelectListener;
import org.spinsuite.util.Env;
import org.spinsuite.util.TabParameter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
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
	
	/**	Parameters				*/
	private TabParameter		tabParam 			= null;
	/**	Index Fragment			*/
	public static final String 	INDEX_FRAGMENT 		= "Index";
	/**	Detail Fragment			*/
	public static final String 	DETAIL_FRAGMENT 	= "Detail";
	/**	View 					*/
	private View 				m_view 				= null;
	/**	Is Load Ok				*/
	private boolean				m_IsLoadOk			= false;
	/**	Cache Detail Fragment	*/
	private T_DynamicTab		m_detailFragment	= null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(m_view != null)
			return m_view;
		//	Inflate
		m_view =  inflater.inflate(R.layout.t_dynamic_tab_detail, container, false);
		return m_view;
	}
	
	@Override
    public void onDestroyView() {
		FragmentManager mFragmentMgr = getFragmentManager();
        FragmentTransaction mTransaction = mFragmentMgr.beginTransaction();
        Fragment childFragment = mFragmentMgr.findFragmentByTag(INDEX_FRAGMENT);
        if(childFragment != null) {
        	mTransaction.remove(childFragment);
            mTransaction.commit();
        }
        super.onDestroyView();
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
        //	Get Fragment Transaction
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //	
    	if (getActivity().findViewById(R.id.ll_List) != null) {
    		transaction.replace(R.id.ll_List, firstFragment, INDEX_FRAGMENT);
        } else if(getActivity().findViewById(R.id.ll_ListLand) != null){
        	transaction.replace(R.id.ll_index_record_line, firstFragment, INDEX_FRAGMENT);
        }
    	//	Commit
    	transaction.commit();
    	//	Set Ok
    	m_IsLoadOk = true;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    }
	
	@Override
	public void handleMenu() {
		T_DynamicTab dynamicTab = (T_DynamicTab)
                getChildFragmentManager().findFragmentByTag(DETAIL_FRAGMENT);
    	if(dynamicTab != null)
    		dynamicTab.handleMenu();
	}

	@Override
	public TabParameter getTabParameter() {
		return tabParam;
	}

	@Override
	public void onItemSelected(int record_ID) {
        //	Instance if not exists
        if(m_detailFragment == null) {
        	m_detailFragment = new T_DynamicTab();
        	m_detailFragment.setFromTab(this);
            Bundle args = new Bundle();
            args.putParcelable("TabParam", tabParam);
            m_detailFragment.setArguments(args);
        }
        //	Transaction
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //	
        if(getActivity().findViewById(R.id.ll_ListLand) != null) {
        	transaction.replace(R.id.ll_dynamic_tab, m_detailFragment, DETAIL_FRAGMENT);
        } else {
        	transaction.replace(R.id.ll_List, m_detailFragment, DETAIL_FRAGMENT);
        }
        transaction.addToBackStack(null);
        //	
        transaction.commit();
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
    	//	Valid is Loaded
    	if(!m_IsLoadOk)
    		return false;
    	//	
		I_DynamicTab indexRecordLine = (I_DynamicTab) 
				getChildFragmentManager().findFragmentByTag(INDEX_FRAGMENT);
		if(indexRecordLine != null){
			if(!Env.isCurrentTab(getActivity(), 
					tabParam.getActivityNo(), tabParam.getTabNo()))
				backToFragment();
			return indexRecordLine.refreshFromChange(reQuery);
		}
		//	Return
		return true;
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
