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

import java.util.ArrayList;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DynamicTab;
import org.spinsuite.interfaces.I_FragmentSelectListener;
import org.spinsuite.model.MSFATable;
import org.spinsuite.model.PO;
import org.spinsuite.util.DisplayRecordItem;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.FilterValue;
import org.spinsuite.util.Msg;
import org.spinsuite.util.TabParameter;
import org.spinsuite.util.ViewIndex;
import org.spinsuite.view.lookup.InfoField;
import org.spinsuite.view.lookup.InfoTab;
import org.spinsuite.view.lookup.VLookup;
import org.spinsuite.view.lookup.VLookupCheckBox;
import org.spinsuite.view.lookup.VLookupDateBox;
import org.spinsuite.view.lookup.VLookupSearch;
import org.spinsuite.view.lookup.VLookupSpinner;
import org.spinsuite.view.lookup.VLookupString;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;

/**
 * @author Yamel Senih
 *
 */
public class T_DynamicTab extends Fragment 
						implements I_DynamicTab, I_FragmentSelectListener {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/04/2014, 22:32:14
	 */
	public T_DynamicTab(){
		//	
	}
	
	/**
	 * Set Tab From
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/04/2014, 22:09:38
	 * @param m_FromTab
	 * @return void
	 */
	public void setFromTab(I_DynamicTab m_FromTab){
		this.m_FromTab = m_FromTab; 
	}

	/**	Parameters	*/
	private 	TabParameter	 		tabParam			= null;
	private 	DB 						conn 				= null;
	private 	PO 						model				= null;
	private 	ArrayList<ViewIndex>	viewList			= null;
	private 	InfoTab 				tabInfo				= null;
	private 	ScrollView 				v_scroll			= null;
	private 	TableLayout 			v_tableLayout		= null;
	private 	LinearLayout 			v_row				= null;
	private 	LayoutParams			v_param				= null;
	private 	int						m_Record_ID			= 0;
	private 	int 					m_Parent_Record_ID 	= 0;
	private 	boolean					m_IsLoadOk			= false;
	//private 	boolean 				m_IsModifying		= false;
	/**	From Tab					*/
	private I_DynamicTab				m_FromTab			= null;
	
	/**	Current Status				*/
	protected static final int NEW 		= 0;
	protected static final int MODIFY 	= 1;
	protected static final int SEE 		= 3;
	protected static final int DELETED 	= 4;
	
	/**	Option Menu					*/
	private MenuItem mi_Search 	= null;
	private MenuItem mi_Edit 	= null;
	private MenuItem mi_Add 	= null;
	private MenuItem mi_Delete 	= null;
	private MenuItem mi_Cancel 	= null;
	private MenuItem mi_Save 	= null;
	
	private static final float WEIGHT_SUM 	= 2;
	private static final float WEIGHT 		= 1;
	
	private static final String TAB_NO = "TabNo";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v =  inflater.inflate(R.layout.t_dynamic_tab, container, false);
    	//	Scroll
    	v_scroll = (ScrollView) v.findViewById(R.id.sv_DynamicTab);
		return v;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	//	
    	Bundle bundle = getArguments();
    	if(bundle != null)
			tabParam = (TabParameter)bundle.getParcelable("TabParam");
		//	Is Not ok Load
    	if(tabParam == null)
    		return;
    	//	Init Load
    	initLoad();
	}
	
	/**
	 * Init Fragment
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/04/2014, 13:50:51
	 * @return void
	 */
	private void initLoad(){
		//	
    	m_IsLoadOk = true;
    	//	Retain Instance
    	if(tabParam.getTabLevel() == 0)
    		setRetainInstance(true);
    	
    	m_Record_ID = Env.getTabRecord_ID(getActivity(), 
    			tabParam.getActivityNo(), tabParam.getTabNo());
    	//	Parent
    	m_Parent_Record_ID = Env.getTabRecord_ID(getActivity(), 
    			tabParam.getActivityNo(), tabParam.getParentTabNo());
    	
    	//	Set Parameter
    	v_param = new LayoutParams(LayoutParams.MATCH_PARENT, 
    			LayoutParams.MATCH_PARENT, WEIGHT);    	
    	//	Table Layout
    	v_tableLayout = new TableLayout(getActivity());
    	//	Add View
    	v_scroll.addView(v_tableLayout);
    	//	View
    	viewList = new ArrayList<ViewIndex>();
    	//	
    	loadView();
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    }
    
    /**
     * Get Tab Parameter
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/03/2014, 15:49:28
     * @return
     * @return TabParameter
     */
    @Override
    public TabParameter getTabParameter(){
    	return tabParam;
    }
    
    /**
     * Load View Objects
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 16:37:56
     * @return
     * @return boolean
     */
    protected boolean loadView(){
    	boolean ok = false;
    	tabInfo = new InfoTab(getActivity(), tabParam.getSFA_Tab_ID(), conn);
		//	Identifier
		m_Record_ID = Env.getTabRecord_ID(getActivity(), 
				tabParam.getActivityNo(), tabParam.getTabNo());
    	//	Get Model
		if (model == null)
    		model = MSFATable.getPO(getActivity(), m_Record_ID, tabInfo.getTableName(), conn);
		if(model == null){
    		Msg.alertMsg(getActivity(), getResources().getString(R.string.msg_Error)
    				, getResources().getString(R.string.msg_Error));
    		return false;
    	}
		//	Set identifier
		Env.setContext(getActivity(), tabParam.getActivityNo(), 
				tabParam.getTabNo(), tabInfo.getTableName() + "_ID", model.getID());
		
		//	Add Fields
    	for(InfoField field : tabInfo.getFields()){
    		if(!field.IsDisplayed)
    			continue;
    		//	Add View to Layout
    		addView(field);
    	}
    	return ok;
    }
 
    /**
     * Add to view
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:45:54
     * @param field
     * @return void
     */
    private void addView(InfoField field){
    	
    	boolean isSameLine = field.IsSameLine;
    	boolean isFirst = false;
		VLookup lookup = null;
    	//	Add New Row
		if(isFirst = (v_row == null)
				|| !isSameLine) {
			v_row = new LinearLayout(getActivity());
			v_row.setOrientation(LinearLayout.HORIZONTAL);
			v_row.setWeightSum(WEIGHT_SUM);
		}
		//	Add
		if(DisplayType.isDate(field.DisplayType)){
			lookup = new VLookupDateBox(getActivity(), field);
		} else if(DisplayType.isText(field.DisplayType)){
			VLookupString lookupString = new VLookupString(getActivity(), field);
			lookupString.setInputType(DisplayType.getInputType(field.DisplayType));
			lookup = lookupString;
		} else if(DisplayType.isBoolean(field.DisplayType)){
			lookup = new VLookupCheckBox(getActivity(), field);
		} else if(DisplayType.isLookup(field.DisplayType)){
			//	Table Direct
			if(field.DisplayType == DisplayType.TABLE_DIR){
				lookup = new VLookupSpinner(getActivity(), field);
			} else if(field.DisplayType == DisplayType.SEARCH){
				lookup = new VLookupSearch(getActivity(), field);
			}
		}
		//	is Filled
		if(lookup != null){
			ViewIndex index = new ViewIndex(lookup, field.ColumnName, model.getColumnIndex(field.ColumnName));
			lookup.setLayoutParams(v_param);
			//	Set Value
			if(m_Record_ID >= 0){
				lookup.setValue(model.get_Value(index.getColumnIndex()));
				//	Load Default
				if(m_Record_ID == 0){
					if(field.IsParent) {
						lookup.setValue(DisplayType.getContextValue(getActivity(), 
								tabParam.getActivityNo(), tabParam.getParentTabNo(), field));
					} else if(tabParam.getTabLevel() > 0) {
						lookup.setValue(DisplayType.getContextValue(getActivity(), 
							tabParam.getActivityNo(), tabParam.getTabNo(), field));
					}
				}
	    		//	Set Current Values
	    		DisplayType.setContextValue(getActivity(), tabParam.getActivityNo(), 
		    				tabParam.getTabNo(), field, lookup.getValue());
			}
			v_row.addView(lookup);
			viewList.add(index);
			
		}
		//	Add Row
		if((lookup != null && !isSameLine)
				|| isFirst)
			v_tableLayout.addView(v_row);
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
	    menu.clear();
        inflater.inflate(R.menu.dynamic_tab, menu);
    	//	Valid is Loaded
    	if(!m_IsLoadOk)
    		return;
    	//	do it
        //	Get Items
        mi_Search 	= menu.getItem(0);
        mi_Edit 	= menu.getItem(1);
        mi_Add	 	= menu.getItem(2);
        mi_Delete 	= menu.getItem(3);
        mi_Cancel 	= menu.getItem(4);
        mi_Save 	= menu.getItem(5);
        //	Lock View
    	changeMenuView();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        	case R.id.action_search:
            	Bundle bundle = new Bundle();
            	bundle.putInt("SFA_Table_ID", tabInfo.getSFA_Table_ID());
            	bundle.putInt("SFA_Tab_ID", tabInfo.getSFA_Tab_ID());
            	if(tabParam.getTabLevel() > 0){
            		FilterValue criteria = tabInfo.getCriteria(getActivity(), 
            				tabParam.getActivityNo(), tabParam.getParentTabNo());
            		bundle.putParcelable("Criteria", criteria);
            	}
            	Intent intent = new Intent(getActivity(), LV_Search.class);
    			intent.putExtras(bundle);
    			//	Start with result
    			startActivityForResult(intent, 0);
                return true;
            
            case R.id.action_edit:
            	lockView(MODIFY);
            	return true;
            	
            case R.id.action_add:
            	newOption();
            	return true;
            		
            case R.id.action_delete:
            	String msg_Acept = this.getResources().getString(R.string.msg_Acept);
    			Builder ask = Msg.confirmMsg(getActivity(), getResources().getString(R.string.msg_AskDelete));
    			ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
    				public void onClick(DialogInterface dialog, int which) {
    					dialog.cancel();
    					if(model.delete()){
    						refresh(0, false);
    						lockView(DELETED);
    			    		//	Refresh
    			    		refreshIndex();
    					}
    					else
    						Msg.alertMsg(getActivity(), 
    								getResources().getString(R.string.msg_Error), model.getError());
    				}
    			});
    			ask.show();
            	return true;
            
            case R.id.action_cancel:
            	if(m_Record_ID == 0){
            		model.backCopy();
            	}
            	refresh(model.getID(), false);
            	lockView(SEE);
                return true;
                
            case R.id.action_save:
            	if(saveData())
            		lockView(SEE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    /**
     * valid and save data
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:07:20
     * @return
     * @return boolean
     */
    private boolean saveData(){
    	boolean ok = true;
    	m_Record_ID = Env.getTabRecord_ID(getActivity(), 
    			tabParam.getActivityNo(), tabParam.getTabNo());
    	//	Error
    	if(model == null){
    		Msg.alertMsg(getActivity(), getResources().getString(R.string.msg_Error)
    				, getResources().getString(R.string.msg_Error));
    		return false;
    	}
    	//	Get Values
    	for (ViewIndex vIndex: viewList) {
    		VLookup lookup = vIndex.getVLookup();
    		InfoField field = lookup.getField();
    		if((field.IsMandatory
    				|| field.IsParent)
    				&& lookup.isEmpty()){
    			Msg.alertMustFillField(getActivity(), field.Name, lookup.getChildView());
    			//	set ok to false
    			ok = false;
    			break;
    		}
    		//	Set to model
    		model.set_Value(vIndex.getColumnIndex(), lookup.getValue());
			//	Set on Context
    		DisplayType.setContextValue(getActivity(), tabParam.getActivityNo(), 
    				tabParam.getTabNo(), field, lookup.getValue());
			//
		}
    	//	Set Record Identifier
    	model.set_Value(tabInfo.getTableKeyName(), m_Record_ID);
    	//	No saved
    	if(!ok)
    		return ok;
    	//	Save
    	ok = model.save();
    	//	Set Record Identifier
    	m_Record_ID = model.get_ID();
    	if(ok) {
    		Env.setTabRecord_ID(getActivity(), 
    				tabParam.getActivityNo(), tabParam.getTabNo(), m_Record_ID);
    		//	
    		Env.setContext(getActivity(), tabParam.getActivityNo(), 
    				tabParam.getTabNo(), tabInfo.getTableKeyName(), m_Record_ID);
    		//	Refresh
    		refreshIndex();
    	} else {
    		Msg.alertMsg(getActivity(), getResources().getString(R.string.msg_Error), 
    				model.getError());
    	}
    	//	Return
    	return ok;
    }

    /**
     * Refresh Header Index
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/04/2014, 08:17:32
     * @return void
     */
    private void refreshIndex(){
    	//	Refresh
    	if(m_FromTab != null)
    		m_FromTab.refreshFromChange(true);
    }
    
    /**
     * Refresh Query
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 06/03/2014, 16:12:59
     * @param m_Record_ID
     * @param parentChanged
     * @return boolean
     */
    private boolean refresh(int record_ID, boolean parentChanged){
    	this.m_Record_ID = record_ID;
    	//	Set record Identifier in context
    	Env.setTabRecord_ID(getActivity(), 
    			tabParam.getActivityNo(), tabParam.getTabNo(), m_Record_ID);
    	//	Parent changed
    	if(parentChanged
    			|| record_ID <= 0)
    		model.clear();
    	
    	//	Reload
    	model.loadData(m_Record_ID);
    	//	Refresh
    	for (ViewIndex vIndex: viewList) {
    		VLookup lookup = vIndex.getVLookup();
    		InfoField field = lookup.getField();
    		lookup.setValue(model.get_Value(vIndex.getColumnIndex()));
    		//	
    		if(m_Record_ID == 0){
				if(field.IsParent) {
					lookup.setValue(DisplayType.getContextValue(getActivity(), 
							tabParam.getActivityNo(), tabParam.getParentTabNo(), field));
				}
			}
    		//	Set Current Values
    		DisplayType.setContextValue(getActivity(), tabParam.getActivityNo(), 
	    				tabParam.getTabNo(), field, lookup.getValue());
    		
    		
    	}
    	//	Set Identifier
    	Env.setContext(getActivity(), tabParam.getActivityNo(), 
				tabParam.getTabNo(), tabInfo.getTableKeyName(), model.getID());
    	//	Set Parent Record Identifier
    	m_Parent_Record_ID = Env.getTabRecord_ID(getActivity(), 
    			tabParam.getActivityNo(), tabParam.getParentTabNo());
    	//	
    	changeMenuView();
    	//	Return
    	return !viewList.isEmpty();
    }
    
    /**
     * Handle lock view
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 06/03/2014, 16:14:12
     * @param mode
     * @return void
     */
    private void lockView(int mode){
    	//	If New Record
    	if(mode == NEW
    			|| mode == MODIFY) {
    		mi_Cancel.setVisible(true);
    		mi_Save.setVisible(true);
    		mi_Delete.setVisible(false);
    		mi_Add.setVisible(false);
    		mi_Edit.setVisible(false);
    		mi_Search.setVisible(false);
    		//m_IsModifying = true;
    	} else if(mode == DELETED) {
    		mi_Cancel.setVisible(false);
    		mi_Save.setVisible(false);
    		mi_Delete.setVisible(false);
    		mi_Add.setVisible(true);
    		mi_Edit.setVisible(false);
    		mi_Search.setVisible(true);
    		//m_IsModifying = false;
    	} else if(mode == SEE) {
    		mi_Cancel.setVisible(false);
    		mi_Save.setVisible(false);
    		mi_Delete.setVisible(m_Record_ID != 0);
    		mi_Add.setVisible(true);
    		mi_Edit.setVisible(m_Record_ID != 0);
    		mi_Search.setVisible(true);
    		//m_IsModifying = false;
    	}
    	//	Enable
    	enableView(mode);
    }
    
    /**
     * Enable Elements
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 06/03/2014, 17:38:53
     * @param mode
     * @return void
     */
    public void enableView(int mode){
		if(mode == NEW){
			for(ViewIndex vIndex : viewList){
	    		VLookup lookup = vIndex.getVLookup();
	    		InfoField field = lookup.getField();
	    		if((!field.IsReadOnly 
	    				&& !field.IsParent)
	    			|| field.ColumnName.equals("DocAction")){
	    			lookup.setEnabled(true);
	    		} else {
	    			lookup.setEnabled(false);
	    		}
	    	}
		} else if(mode == MODIFY){
			for(ViewIndex vIndex : viewList){
	    		VLookup lookup = vIndex.getVLookup();
	    		InfoField field = lookup.getField();
	    		if(
	    			//	Document Action
	    			(field.ColumnName.equals("DocAction")
	    				&& !field.IsReadOnly
	    				&& (field.IsUpdateable || field.IsAlwaysUpdateable))
	    			||
	    			//	Any Field
	    			(!field.ColumnName.equals("DocAction")
	    					&& (field.IsUpdateable || field.IsAlwaysUpdateable)
	    					&& !field.IsReadOnly
	    					&& !field.IsParent)){
	    			lookup.setEnabled(true);
	    		} else {
	    			lookup.setEnabled(false);
	    		}
	    	}	
		} else if(mode == DELETED){
			for(ViewIndex vIndex : viewList){
	    		VLookup lookup = vIndex.getVLookup();
	    		lookup.setEnabled(false);
	    	}
		} else if(mode == SEE){
			for(ViewIndex vIndex : viewList){
	    		VLookup lookup = vIndex.getVLookup();
	    		InfoField field = lookup.getField();
	    		if(field.ColumnName.equals("DocAction"))
	    			lookup.setEnabled(true);
	    		else
	    			lookup.setEnabled(false);
	    	}
		}
	}
    
    /**
     * New Option
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/03/2014, 00:22:33
     * @return void
     */
    private void newOption(){
    	//	Backup
    	if(m_Record_ID != 0){
    		model.copyValues(true);
    	}
    	//	
    	refresh(0, false);
    	lockView(NEW);
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //	Set Tab No
        outState.putInt(TAB_NO, tabParam.getTabNo());
    }
    
    /**
     * Handle menu items
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/03/2014, 23:57:35
     * @return void
     */
    @Override
    public void handleMenu(){
    	//	Valid is Loaded
    	if(!m_IsLoadOk)
    		return;
    	//	do it
    	changeMenuView();
    }
    
    /**
     * Change Menu View
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/04/2014, 18:17:58
     * @return void
     */
    private void changeMenuView(){
    	if(mi_Search != null){
            //	Lock View
    		/*if(m_IsModifying)
    			lockView(MODIFY);
    		else */if(m_Record_ID == 0)
        		lockView(NEW);
        	else
        		lockView(SEE);
        }
    }
    
    /**
     * Refresh from parent activity
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/03/2014, 10:53:18
     * @return boolean
     */
    public boolean refreshFromChange(boolean reQuery){
    	//	Valid is Loaded
    	boolean ok = false;
    	if(!m_IsLoadOk)
    		return false;
    	//	do it
    	if(reQuery){
    		ok = refresh(0, true);
    	} else if(tabParam.getTabLevel() > 0){
    		int currentParent_Record_ID = Env.getTabRecord_ID(getActivity(), 
        			tabParam.getActivityNo(), tabParam.getParentTabNo());
        	if(m_Parent_Record_ID != currentParent_Record_ID){
        		ok = refresh(0, true);
        	}
    	}
    	//	
    	changeMenuView();
    	//	Return
    	return ok;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	//	Valid is Loaded
    	if(!m_IsLoadOk)
    		return;
    	//	do it
    	if (resultCode == Activity.RESULT_OK) {
	    	if(data != null){
	    		Bundle bundle = data.getExtras();
	    		DisplayRecordItem item = (DisplayRecordItem) bundle.getParcelable("Record");
	    		String columnName = bundle.getString("ColumnName");
	    		//	if a field or just search
	    		if(columnName != null){
	    			for (ViewIndex vIndex: viewList) {
	    	    		VLookup lookup = vIndex.getVLookup();
	    	    		if(vIndex.getColumnName().equals(columnName)){
	    	    			((VLookupSearch) lookup).setItem(item);
	    	    			break;
	    	    		}
	    			}
	    		} else {
	    			//	Refresh
	    			int record_ID = item.getRecord_ID();
	    			//	Verify
	    			if(record_ID != 0) {
	    				refresh(record_ID, false);
	    			} else {
	    				newOption();
	    			}
	    		}
	    	}
    	}
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public void onItemSelected(int record_ID) {
		refresh(record_ID, false);	
	}

	@Override
	public void setTabParameter(TabParameter tabParam) {
		if(tabParam == null)
			return;
		//	Set Parameter
		this.tabParam = tabParam;
		//	Initial Load
		initLoad();
	}	
}
