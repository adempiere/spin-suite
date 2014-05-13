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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_DynamicTab;
import org.spinsuite.interfaces.I_FragmentSelectListener;
import org.spinsuite.model.MSPSTable;
import org.spinsuite.model.MSequence;
import org.spinsuite.model.PO;
import org.spinsuite.process.DocAction;
import org.spinsuite.util.ActivityParameter;
import org.spinsuite.util.DisplayMenuItem;
import org.spinsuite.util.DisplayRecordItem;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.FilterValue;
import org.spinsuite.util.Msg;
import org.spinsuite.util.TabParameter;
import org.spinsuite.util.ViewIndex;
import org.spinsuite.view.lookup.InfoField;
import org.spinsuite.view.lookup.InfoTab;
import org.spinsuite.view.lookup.LookupButtonPaymentRule;
import org.spinsuite.view.lookup.VLookup;
import org.spinsuite.view.lookup.VLookupButton;
import org.spinsuite.view.lookup.VLookupButtonDocAction;
import org.spinsuite.view.lookup.VLookupCheckBox;
import org.spinsuite.view.lookup.VLookupDateBox;
import org.spinsuite.view.lookup.VLookupSearch;
import org.spinsuite.view.lookup.VLookupSpinner;
import org.spinsuite.view.lookup.VLookupString;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.PopupMenu;
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
	private 	boolean 				m_IsModifying		= false;
	/**	From Tab					*/
	private 	I_DynamicTab			m_FromTab			= null;
	
	/**	Current Status				*/
	protected static final int NEW 		= 0;
	protected static final int MODIFY 	= 1;
	protected static final int SEE 		= 3;
	protected static final int DELETED 	= 4;
	
	/**	Option Menu Item			*/
	private final int O_SHARE			= 1;
	private final int O_DELETE			= 2;
	private final int O_ATTACH			= 3;
	private final int O_VIEW_ATTACH		= 4;
	
	/**	Option Menu					*/
	private MenuItem mi_Search 	= null;
	private MenuItem mi_Edit 	= null;
	private MenuItem mi_Add 	= null;
	private MenuItem mi_More 	= null;
	private MenuItem mi_Cancel 	= null;
	private MenuItem mi_Save 	= null;
	
	private static final float WEIGHT_SUM 	= 2;
	private static final float WEIGHT 		= 1;
	
	/**	Results						*/
	private static final int 		ACTION_TAKE_PHOTO	= 3;
	private static final String 	JPEG_FILE_SUFFIX 	= ".jpg";
	private static String 			TMP_ATTACH_NAME 	= null;
	/**	Images						*/
	private static final int 		IMG_TARGET_W		= 640;
	private static final int 		IMG_TARGET_H		= 480;
	
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
    	//	Set Temporal Image Name
    	TMP_ATTACH_NAME = Env.getImg_DirectoryPathName(getActivity()) 
    								+ File.separator + "TMP" + JPEG_FILE_SUFFIX;
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
    	tabInfo = new InfoTab(getActivity(), tabParam.getSPS_Tab_ID(), conn);
		//	Identifier
		m_Record_ID = Env.getTabRecord_ID(getActivity(), 
				tabParam.getActivityNo(), tabParam.getTabNo());
    	//	Get Model
		if (model == null)
    		model = MSPSTable.getPO(getActivity(), m_Record_ID, tabInfo.getTableName(), conn);
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
			if(field.DisplayType == DisplayType.TABLE_DIR
					|| field.DisplayType == DisplayType.LIST){
				lookup = new VLookupSpinner(getActivity(), field);
			} else if(field.DisplayType == DisplayType.SEARCH){
				lookup = new VLookupSearch(getActivity(), field);
			}
		} else if(field.DisplayType == DisplayType.BUTTON){
			VLookupButton lookupButton = null;
			if(field.ColumnName.equals("DocAction")){
				lookupButton = new VLookupButtonDocAction(getActivity(), field, (DocAction) model, this);
			} else if(field.ColumnName.equals("PaymentRule")){
				//	Payment Rule Button
				lookupButton = new LookupButtonPaymentRule(getActivity(), field);
			} else {
				lookupButton = new VLookupButton(getActivity(), field);
			}
			//	Set Parameters
			lookupButton.setTabParameter(tabParam);
			lookup = lookupButton;
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
	    if(!Env.isCurrentTab(getActivity(), 
	    		tabParam.getActivityNo(), tabParam.getTabNo()))
	    	return;
	    //	
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
        mi_More 	= menu.getItem(3);
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
            	bundle.putInt("SPS_Table_ID", tabInfo.getSPS_Table_ID());
            	bundle.putInt("SPS_Tab_ID", tabInfo.getSPS_Tab_ID());
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
            		
            case R.id.action_more:
            	//	More
            	showPopupMenu();
            	return true;
            
            case R.id.action_cancel:
            	if(m_Record_ID == 0){
            		model.backCopy();
            	}
            	refresh(model.getID(), false);
            	lockView(SEE);
                return true;
                
            case R.id.action_save:
            	if(save())
            		lockView(SEE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Show Popup Menu
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/05/2014, 12:08:11
     * @return void
     */
    private void showPopupMenu(){
    	View menuItemView = getActivity().findViewById(R.id.action_more);
		PopupMenu popupMenu = new PopupMenu(getActivity(), menuItemView);
		//	Share Record
		popupMenu.getMenu().add(Menu.NONE, O_SHARE, 
					Menu.NONE, getString(R.string.Action_Share));
		//	Delete Record
		popupMenu.getMenu().add(Menu.NONE, O_DELETE, 
				Menu.NONE, getString(R.string.Action_Delete));
		//	Attach a File
		popupMenu.getMenu().add(Menu.NONE, O_ATTACH, 
				Menu.NONE, getString(R.string.Action_AttachImage));
		//	View Attachment
		if(model != null
				&& model.getID() > 0){
			int count = DB.getSQLValue(getActivity(), "SELECT COUNT(att.AD_Attachment_ID) FROM AD_Attachment att " +
					"WHERE att.AD_Table_ID = ? AND att.Record_ID = ?", 
					new String[]{String.valueOf(tabInfo.getSPS_Table_ID()), 
											String.valueOf(model.get_ID())});
			//	Exist a Attachment
			if(count != 0)
				popupMenu.getMenu().add(Menu.NONE, O_VIEW_ATTACH, 
						Menu.NONE, getString(R.string.Action_ViewAttachment));
		}
		//	Action
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
	        		case O_SHARE:
	        			return true;
	        		case O_DELETE:
	        			//	Delete
	        			deleteRecord();
	        			return true;
	        		case O_ATTACH:
	        			attachImage();
	        			return true;
	        		case O_VIEW_ATTACH:
	        			viewAttachment();
	        			return true;
				}
				return false;
			}
		});
		//	Show
		popupMenu.show();
	}
    
    /**
     * View Attachment
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/05/2014, 11:00:17
     * @return void
     */
    private void viewAttachment(){
    	Bundle bundle = new Bundle();
		ActivityParameter param = new ActivityParameter();
		param.setFrom_Record_ID(model.get_ID());
		param.setFrom_SPS_Table_ID(tabInfo.getSPS_Table_ID());
    	bundle.putParcelable("Param", param);
    	Intent intent = new Intent(getActivity(), LV_AttachView.class);
		intent.putExtras(bundle);
		startActivity(intent);
    }
    
    /**
     * Delete Record
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/05/2014, 14:04:50
     * @return void
     */
    private void deleteRecord(){
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
    }
    
    /**
     * Action Attach
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/05/2014, 15:29:45
     * @return void
     */
    private void attachImage(){
    	//	Delete Temp File
    	File tmpFile = new File(TMP_ATTACH_NAME);
    	if(tmpFile.exists())
    		tmpFile.delete();
    	//	
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tmpFile));
	    getActivity().startActivityForResult(intent, ACTION_TAKE_PHOTO);
	}
        
    /**
     * valid and save data
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:07:20
     * @return
     * @return boolean
     */
    public boolean save(){
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
    				|| field.IsParent) && lookup.isEmpty()){
    			Msg.alertMustFillField(getActivity(), "\"" + field.Name + "\"", lookup.getChildView());
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
    	//	Refresh Child Index
    	if(this.m_Record_ID != record_ID)
    		refreshIndex();
    	this.m_Record_ID = record_ID;
    	//	Set record Identifier in context
    	Env.setTabRecord_ID(getActivity(), 
    			tabParam.getActivityNo(), tabParam.getTabNo(), m_Record_ID);
    	//	Parent changed
    	if(parentChanged
    			|| record_ID <= 0)
    		model.clear(false);
    	
    	//	Reload
    	model.loadData(m_Record_ID);
    	//	Refresh
    	loadData();
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
     * Load Data in View
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 06/05/2014, 23:28:32
     * @return void
     */
    private void loadData(){
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
    		mi_More.setVisible(false);
    		mi_Add.setVisible(false);
    		mi_Edit.setVisible(false);
    		mi_Search.setVisible(false);
    		m_IsModifying = true;
    	} else if(mode == DELETED) {
    		mi_Cancel.setVisible(false);
    		mi_Save.setVisible(false);
    		mi_More.setVisible(false);
    		mi_Add.setVisible(true);
    		mi_Edit.setVisible(false);
    		mi_Search.setVisible(true);
    		m_IsModifying = false;
    	} else if(mode == SEE) {
    		mi_Cancel.setVisible(false);
    		mi_Save.setVisible(false);
    		mi_More.setVisible(m_Record_ID != 0);
    		mi_Add.setVisible(true);
    		mi_Edit.setVisible(m_Record_ID != 0);
    		mi_Search.setVisible(true);
    		m_IsModifying = false;
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
	    		if(!field.IsReadOnly 
	    				&& !field.IsParent
	    				&& !field.ColumnName.equals("DocAction")){
	    			lookup.setEnabled(true);
	    		} else {
	    			lookup.setEnabled(false);
	    		}
	    	}
		} else if(mode == MODIFY){
			//	Check Is Processed
			boolean isProcessed = Env.getContextAsBoolean(getActivity(), tabParam.getActivityNo(), 
	    				tabParam.getTabNo(), "Processed");
			//	
			for(ViewIndex vIndex : viewList){
	    		VLookup lookup = vIndex.getVLookup();
	    		InfoField field = lookup.getField();
	    		if(
	    			//	Any Field
	    			!field.ColumnName.equals("DocAction")
	    				&& (
	    						//	Updateable and not Processed
	    						((field.IsUpdateable && !isProcessed)
	    							&& !field.IsReadOnly
	    							&& !field.IsParent)
	    						//	Always Updateable
	    						|| field.IsAlwaysUpdateable
	    					)
	    			){
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
	    		if(field.ColumnName.equals("DocAction")
	    				|| (field.DisplayType == DisplayType.BUTTON
	    						&& !field.IsReadOnly))
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
    		if(m_IsModifying)
    			lockView(MODIFY);
    		else if(m_Record_ID == 0)
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
    		ok = refresh(m_Record_ID, true);
    	} else if(tabParam.getTabLevel() > 0){
    		int currentParent_Record_ID = Env.getTabRecord_ID(getActivity(), 
        			tabParam.getActivityNo(), tabParam.getParentTabNo());
        	if(m_Parent_Record_ID != currentParent_Record_ID){
        		ok = refresh(0, true);
        	}
    	} else {
    		loadData();
    	}
    	//	
    	changeMenuView();
    	//	Return
    	return ok;
    }
    
    /**
     * Process Attach
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/05/2014, 15:44:20
     * @param intent
     * @return
     * @return boolean
     */
    private boolean processAttach(Intent intent){
    	File tmpFile = new File(TMP_ATTACH_NAME);
        if(!tmpFile.exists())
        	return false;
		// Get the size of the image
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(TMP_ATTACH_NAME, options);
		int photoW = options.outWidth;
		int photoH = options.outHeight;
		
		//	Figure out which way needs to be reduced less
		int scaleFactor = 1;
		scaleFactor = Math.min(photoW/IMG_TARGET_W, photoH/IMG_TARGET_H);	

		//	Set bitmap options to scale the image decode target
		options.inJustDecodeBounds = false;
		options.inSampleSize = scaleFactor;
		options.inPurgeable = true;

		//	Decode the JPEG file into a Bitmap
		Bitmap mImage = BitmapFactory.decodeFile(TMP_ATTACH_NAME, options);
		
		//	
		if(mImage != null){
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			mImage.compress(Bitmap.CompressFormat.PNG, 100, bos);
			//	Save in DB
			saveAttachment(bos.toByteArray());
			return true;
		}
		return false;
    }
    
    /**
     * Save Attachment
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/05/2014, 20:31:38
     * @param bMArray
     * @return void
     */
    private void saveAttachment(byte[] bMArray){
		//	Save in DataBase
		DB conn = new DB(getActivity());
		DB.loadConnection(conn, DB.READ_WRITE);
		ContentValues values = new ContentValues();         
		//	
		SimpleDateFormat format = DisplayType.getDateFormat_JDBC();
		//	Set Values
		values.put("AD_Attachment_ID", MSequence.getNextID(getActivity(), 
				Env.getAD_Client_ID(getActivity()), tabInfo.getTableName(), conn));
		values.put("AD_Client_ID", Env.getAD_Client_ID(getActivity()));
		values.put("AD_Org_ID", Env.getAD_Org_ID(getActivity()));
		values.put("IsActive", "Y");
		values.put("AD_Table_ID", tabInfo.getSPS_Table_ID());
		values.put("Record_ID", model.getID());
		values.put("Title", new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
		values.put("Created", format.format(new Date()));
		values.put("CreatedBy", Env.getAD_User_ID(getActivity()));
		values.put("Updated", format.format(new Date()));
		values.put("UpdatedBy", Env.getAD_User_ID(getActivity()));
		values.put("BinaryData", bMArray);
		//	
		conn.insertSQL("AD_Attachment", null, values);
		//	Commit
		conn.setTransactionSuccessful();
		//	Close Connection
		DB.closeConnection(conn);
		Msg.toastMsg(getActivity(), getString(R.string.msg_Ok));
		//	Delete File
		File tmpFile = new File(TMP_ATTACH_NAME);
    	if(tmpFile.exists())
    		tmpFile.delete();
    	//	
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	//	Valid is Loaded
    	if(!m_IsLoadOk)
    		return;
    	//	
    	if(requestCode == ACTION_TAKE_PHOTO) {
    		processAttach(data);
    	} else if (resultCode == Activity.RESULT_OK) {
	    	if(data != null){
	    		Bundle bundle = data.getExtras();
	    		//	Item
	    		DisplayRecordItem item = (DisplayRecordItem) bundle.getParcelable("Record");
	    		switch (bundle.getInt(DisplayMenuItem.CONTEXT_ACTIVITY_TYPE)) {
				case DisplayMenuItem.CONTEXT_ACTIVITY_TYPE_Form:
					break;
				case DisplayMenuItem.CONTEXT_ACTIVITY_TYPE_Window:
					break;
				case DisplayMenuItem.CONTEXT_ACTIVITY_TYPE_Process:
					String summary = bundle.getString("Summary");
					boolean isError = bundle.getBoolean("IsError");
					//	Is a Error
					if(isError){
						Msg.alertMsg(getActivity(), 
								getString(R.string.msg_ProcessError), summary);
					} else {
						if(summary != null
								&& summary.length() > 0)
							Msg.toastMsg(getActivity(), summary);
					}
					//	Refresh
					refreshFromChange(true);
					break;
				case DisplayMenuItem.CONTEXT_ACTIVITY_TYPE_Report:
					break;
				case DisplayMenuItem.CONTEXT_ACTIVITY_TYPE_SearchWindow:
	    			//	Refresh
	    			int record_ID = item.getRecord_ID();
	    			//	Verify
	    			if(record_ID != 0)
	    				refresh(record_ID, false);
	    			else
	    				newOption();

					break;
				case DisplayMenuItem.CONTEXT_ACTIVITY_TYPE_SearchColumn:
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
		    		}
					break;
				default:
					break;
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
