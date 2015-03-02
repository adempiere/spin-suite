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

import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DynamicTab;
import org.spinsuite.interfaces.I_FragmentSelectListener;
import org.spinsuite.util.Env;
import org.spinsuite.util.TabParameter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class T_DynamicTabDetail extends Fragment 
		implements I_DynamicTab, I_FragmentSelectListener {
	
	/**	Parameters				*/
	private TabParameter		tabParam 			= null;
	/**	Parent Tab Parameter	*/
	private TabParameter 		parentTab 			= null;
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
	/**	List Fragment Cache		*/
	private FV_IndexRecordLine 	m_listFragment		= null;
	/**	Is Same Table			*/
	private boolean				m_IsSameTable		= false;
	/**	Parent Tab Record ID	*/
	private int 				m_Parent_Record_ID 	= 0;
	/**	Is Index Fragment		*/
	private boolean				m_IsDetailAdded		= false;

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
        //	
        childFragment = mFragmentMgr.findFragmentByTag(DETAIL_FRAGMENT);
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
    	//	
    	if(m_IsLoadOk)
    		return;
    	//	Verify if is same table
    	if(parentTab == null) {
    		parentTab = (TabParameter) Env.getContextObject(getActivity(), 
        			tabParam.getActivityNo(), tabParam.getParentTabNo(), 
        			"TabParameter", TabParameter.class);
        	//	
        	if(parentTab != null) {
        		m_IsSameTable = tabParam.getSPS_Table_ID() == parentTab.getSPS_Table_ID();
        	}
    	}
    	//	
    	if(tabParam.getTabLevel() > 0){
    		int[] currentParent_Record_ID = Env.getTabRecord_ID(getActivity(), 
        			tabParam.getActivityNo(), tabParam.getParentTabNo());
        	if(m_Parent_Record_ID != currentParent_Record_ID[0]){
        		m_Parent_Record_ID = currentParent_Record_ID[0];
        	}
    	}
    	//	
    	if(m_listFragment == null) {
    		m_listFragment = new FV_IndexRecordLine();
            //	Set Parameters
        	m_listFragment.setArguments(bundle);
    	}
        //	Get Fragment Transaction
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //	Instance if not exists
        instanceDetailFragment();
        //	Portrait
    	if (getActivity().findViewById(R.id.ll_List) != null) {
    		if(m_IsSameTable) {
    			transaction.add(R.id.ll_List, m_detailFragment, INDEX_FRAGMENT);
    			int[] m_Parent_Record_ID = Env.getTabRecord_ID(getActivity(), 
    	    			tabParam.getActivityNo(), tabParam.getParentTabNo());
    			String[] m_KeyColumn = Env.getTabKeyColumns(getActivity(), 
    	    			tabParam.getActivityNo(), tabParam.getParentTabNo());
    			Env.setTabRecord_ID(getActivity(), 
    					tabParam.getActivityNo(), tabParam.getTabNo(), m_Parent_Record_ID);
    			Env.setTabKeyColumns(getActivity(), 
    					tabParam.getActivityNo(), tabParam.getTabNo(), m_KeyColumn);
    		} else {
    			transaction.add(R.id.ll_List, m_listFragment, INDEX_FRAGMENT);
    		}
        } else if(getActivity().findViewById(R.id.ll_ListLand) != null) {
        	transaction.add(R.id.ll_index_record_line, m_listFragment, INDEX_FRAGMENT);
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

	/**
	 * Instance Detail Fragment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/10/2014, 15:36:28
	 * @return void
	 */
	private void instanceDetailFragment() {
        //	Instance if not exists
        if(m_detailFragment == null) {
        	m_detailFragment = new T_DynamicTab();
        	m_detailFragment.setFromTab(this);
            Bundle args = new Bundle();
            args.putParcelable("TabParam", tabParam);
            m_detailFragment.setArguments(args);
        }
	}
	
	@Override
	public void onItemSelected(int [] record_ID, String [] keyColumns) {
        //	Instance if not exists
        instanceDetailFragment();
        //	Transaction
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //	
        if(getActivity().findViewById(R.id.ll_ListLand) != null) {
        	if(!m_IsDetailAdded) {
        		transaction.add(R.id.ll_dynamic_tab, m_detailFragment, DETAIL_FRAGMENT);
        		m_IsDetailAdded = true;
        	}
        } else {
        	transaction.hide(m_listFragment);
        	if(m_detailFragment.isHidden()) {
        		transaction.show(m_detailFragment);
        	} else {
        		transaction.add(R.id.ll_List, m_detailFragment, DETAIL_FRAGMENT);
        	}
        }
        //	
        transaction.commit();
        //	
        Env.setTabRecord_ID(getActivity(), 
				tabParam.getActivityNo(), tabParam.getTabNo(), record_ID);
        Env.setTabKeyColumns(getActivity(), 
				tabParam.getActivityNo(), tabParam.getTabNo(), keyColumns);
        if(m_detailFragment != null) {
        	m_detailFragment.onItemSelected(record_ID, keyColumns);
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
    	//	Valid is Loaded
    	if(!m_IsLoadOk)
    		return false;
    	//	
    	boolean loaded = false;
    	I_DynamicTab indexRecordLine = (I_DynamicTab) 
				getChildFragmentManager().findFragmentByTag(INDEX_FRAGMENT);
		if(indexRecordLine != null) {
			if(reQuery) {
				if(!Env.isCurrentTab(getActivity(), 
						tabParam.getActivityNo(), tabParam.getTabNo())) {
					indexRecordLine.refreshFromChange(reQuery);
					loaded = true;
				}
			} else if(tabParam.getTabLevel() > 0){
	    		int[] currentParent_Record_ID = Env.getTabRecord_ID(getActivity(), 
	        			tabParam.getActivityNo(), tabParam.getParentTabNo());
	        	if(m_Parent_Record_ID != currentParent_Record_ID[0]){
	        		m_Parent_Record_ID = currentParent_Record_ID[0];
	        		if(!m_IsSameTable) {
	        			indexRecordLine.refreshFromChange(reQuery);
		                //	
		                if(m_detailFragment != null) {
		                	int[] first_ID = Env.getTabRecord_ID(getActivity(), 
		                			tabParam.getActivityNo(), tabParam.getTabNo());
		                	String[] keyColumns = Env.getTabKeyColumns(getActivity(), 
		                			tabParam.getActivityNo(), tabParam.getTabNo());
		                	m_detailFragment.onItemSelected(first_ID, keyColumns);
		                }
		        		loaded = true;
	        		} else {
	        			if(m_detailFragment != null) {
	        				int[] first_ID = Env.getTabRecord_ID(getActivity(), 
		                			tabParam.getActivityNo(), tabParam.getTabNo());
		                	String[] keyColumns = Env.getTabKeyColumns(getActivity(), 
		                			tabParam.getActivityNo(), tabParam.getTabNo());
		                	m_detailFragment.onItemSelected(first_ID, keyColumns);
		                }
	        		}
	        	}
	    	}
			//	
		}
		//	Return
		return loaded;
	}

	@Override
	public void setTabParameter(TabParameter tabParam) {
		//	
	}
	
	/**
	 * Back to Index Fragment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/04/2014, 11:37:12
	 * @return
	 * @return boolean
	 */
	private boolean backToFragment() {
		//	
		if(getActivity().findViewById(R.id.ll_ListLand) != null
				|| m_IsSameTable
				|| m_detailFragment.isHidden())
			return false;
		//	Begin Transaction
		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		//	Begin Transaction
		transaction.hide(m_detailFragment);
		//	
		if(m_listFragment.isHidden()) {
    		transaction.show(m_listFragment);
    	} else {
    		transaction.add(R.id.ll_List, m_listFragment, INDEX_FRAGMENT);
    	}
		//	Commit
		transaction.commit();
	    //	Return
	    return true;
	}

	@Override
	public boolean save() {
		return false;
	}

	@Override
	public boolean isModifying() {
		//	Initial Load
		if(!m_IsLoadOk)
			return false;
		//	
		if(m_detailFragment != null) {
        	m_detailFragment.isModifying();
        }
		return false;
	}

	@Override
	public void setIsParentModifying(boolean isParentModifying) {
		//	Initial Load
		if(!m_IsLoadOk)
			return;
		//	
		if(m_detailFragment != null) {
        	m_detailFragment.setIsParentModifying(isParentModifying);
        }		
	}
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//	Call to Detail Fragment
		if(m_detailFragment != null)
			m_detailFragment.onActivityResult(requestCode, resultCode, data);
	}
}
