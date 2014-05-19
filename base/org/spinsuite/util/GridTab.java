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
package org.spinsuite.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.model.Callout;
import org.spinsuite.model.MSPSTable;
import org.spinsuite.model.PO;
import org.spinsuite.view.lookup.GridField;
import org.spinsuite.view.lookup.InfoTab;

import android.content.Context;


/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class GridTab {
	
	/**
	 * 
	 * *** Constructor ***
	 * @param m_ctx
	 * @param m_TabParam
	 * @param m_TabInfo
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 11:47:36
	 */
	public GridTab(Context m_ctx, TabParameter m_TabParam, InfoTab m_TabInfo, DB conn) {
		this.m_ctx = m_ctx;
		this.m_TabParam = m_TabParam;
		this.m_TabInfo = m_TabInfo;
		this.conn = conn;
		m_fields = new ArrayList<GridField>();
		//	Set Identifers
		m_Record_ID = Env.getTabRecord_ID(m_ctx, 
				m_TabParam.getActivityNo(), m_TabParam.getTabNo());
    	//	Parent
    	m_Parent_Record_ID = Env.getTabRecord_ID(m_ctx, 
    			m_TabParam.getActivityNo(), m_TabParam.getParentTabNo());
		loadPO();
	}
	
	/**	Fields					*/
	private ArrayList<GridField> 	m_fields = null;
	/**	Context					*/
	private Context					m_ctx = null;
	/**	Activity No				*/
	private TabParameter 			m_TabParam = null;
	/**	Tab Info				*/
	private InfoTab 				m_TabInfo = null;
	/**	Persistence Object		*/
	private PO 						model = null;
	/**	Connection				*/
	private DB						conn = null;
	/**	Active Callouts			*/
	private List<String> 			activeCallouts = new ArrayList<String>();
	/**	Active Instance			*/
	private List<Callout> 			activeCalloutInstance = new ArrayList<Callout>();
	/**	Record Identifier		*/
	private int 					m_Record_ID = 0;
	/**	Parent Record Identifier*/
	private int 					m_Parent_Record_ID = 0;

	
	/**
	 * Add Field to Grid Tab
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 11:48:33
	 * @param v_lookup
	 * @return void
	 */
	public void addField(GridField m_GridField) {
		m_fields.add(m_GridField);
	}
	
	/**
	 * Get Value from key
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 11:55:03
	 * @param columnName
	 * @return
	 * @return Object
	 */
	public Object getValue(String columnName){
		for(GridField field : m_fields) {
			if(field.getColumnName().equals(columnName))
				return field.getValue();
		}
		//	Return
		return null;
	}
	
	/**
	 * Get Value As String
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 13:42:05
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getValueAsString(String columnName){
		for(GridField field : m_fields) {
			if(field.getColumnName().equals(columnName))
				return field.getValueAsString();
		}
		//	Return
		return null;
	}
	
	/**
	 * Get Value As Boolean
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 13:42:58
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean getValueAsBoolean(String columnName){
		for(GridField field : m_fields) {
			if(field.getColumnName().equals(columnName))
				return field.getValueAsBoolean();
		}
		//	Return
		return false;
	}
	
	/**
	 * Get Value As Integer
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 13:43:27
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getValueAsInt(String columnName){
		for(GridField field : m_fields) {
			if(field.getColumnName().equals(columnName))
				return field.getValueAsInt();
		}
		//	Return
		return 0;
	}
	
	/**
	 * Get Fields
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 13:45:47
	 * @return
	 * @return ArrayList<GridField>
	 */
	public ArrayList<GridField> getFields() {
		return m_fields;
	}
	
	/**
	 * Is Empty
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 13:48:18
	 * @return
	 * @return boolean
	 */
	public boolean isEmpty() { 
		return m_fields.isEmpty();
	}
	
	/**
	 * Is Processed
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 15:04:07
	 * @return
	 * @return boolean
	 */
	public boolean isProcessed() {
		return getValueAsBoolean("Processed");
	}
	
	/**
	 * 
	 * @return list of active call out for this tab
	 */
	public String[] getActiveCallouts() {
		String[] list = new String[activeCallouts.size()];
		return activeCallouts.toArray(list);
	}
	
	/**
	 * 
	 * @return list of active call out instance for this tab
	 */
	public Callout[] getActiveCalloutInstance() {
		Callout[] list = new Callout[activeCalloutInstance.size()];
		return activeCalloutInstance.toArray(list);
	}

	/**************************************************************************
	 *  Process Callout(s).
	 *  <p>
	 *  The Callout is in the string of
	 *  "class.method;class.method;"
	 * If there is no class name, i.e. only a method name, the class is regarded
	 * as CalloutSystem.
	 * The class needs to comply with the Interface Callout.
	 *
	 * For a limited time, the old notation of Sx_matheod / Ux_menthod is maintained.
	 *
	 * @param field field
	 * @return error message or ""
	 * @see org.compiere.model.Callout
	 */
	public String processCallout (GridField field)
	{
		String callout = field.getCallout();
		if (callout == null
				|| callout.length() == 0)
			return "";
		//
		if (isProcessed() && !field.isAlwaysUpdateable())		//	only active records
			return "";			//	"DocProcessed";

		Object value = field.getValue();
		Object oldValue = field.getOldValue();
		LogM.log(m_ctx, getClass(), Level.FINE, field.getColumnName() + "=" + value
			+ " (" + callout + ") - old=" + oldValue);

		StringTokenizer st = new StringTokenizer(callout, ";,", false);
		while (st.hasMoreTokens())      //  for each callout
		{
			String cmd = st.nextToken().trim();
			
			//detect infinite loop
			if (activeCallouts.contains(cmd)) 
				continue;
			
			String retValue = "";
			Callout call = null;
			String method = null;
			int methodStart = cmd.lastIndexOf('.');
			try {
				if (methodStart != -1)      //  no class
				{
						Class<?> cClass = Class.forName(cmd.substring(0,methodStart));
						call = (Callout)cClass.newInstance();
						method = cmd.substring(methodStart+1);
				}
			} catch (Exception e) {
				LogM.log(m_ctx, getClass(), Level.SEVERE, "class", e);
				return "Callout Invalid: " + cmd + " (" + e.toString() + ")";
			}

			if (call == null || method == null || method.length() == 0)
				return "Callout Invalid: " + method;

			try {
				activeCallouts.add(cmd);
				activeCalloutInstance.add(call);
				retValue = call.start(m_ctx, method, m_TabParam.getActivityNo(), this, field, value, oldValue);
			} catch (Exception e) {
				LogM.log(m_ctx, getClass(), Level.SEVERE, "start", e);
				retValue = 	"Callout Invalid: " + e.toString();
				return retValue;
			} finally {
				activeCallouts.remove(cmd);
				activeCalloutInstance.remove(call);
			}
			//	
			if (retValue != null
					&& retValue.length() != 0)		//	interrupt on first error
			{
				LogM.log(m_ctx, getClass(), Level.SEVERE, "Error: " + retValue);
				return retValue;
			}
		}   //  for each callout
		return "";
	}	//	processCallout
	
	/**
	 * Load Data from Model
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 13:55:23
	 * @param model
	 * @return void
	 */
	public void loadData() {
		if(model == null)
			return;
		//	Get Record Identifier
		int m_Record_ID = model.get_ID();
		//	
		for (GridField vField: m_fields) {
    		vField.setValue(model.get_Value(vField.getColumnIndex()));
    		//	
    		if(m_Record_ID <= 0){
				if(vField.isParent()) {
					vField.setValue(DisplayType.getContextValue(m_ctx, 
							m_TabParam.getActivityNo(), m_TabParam.getParentTabNo(), vField.getField()));
				}
			}
    		//	Set Current Values
    		DisplayType.setContextValue(m_ctx, m_TabParam.getActivityNo(), 
    				m_TabParam.getTabNo(), vField.getField(), vField.getValue());
    	}
	}
	
	/**
	 * Prepare data for New
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:44:34
	 * @return void
	 */
	public void dataNew() {
		for (GridField vField: m_fields) {
			if(!vField.isReadOnly()
					&& !vField.isParent()
					&& !vField.getColumnName().equals("DocAction")){
				vField.setEnabled(true);
			} else {
				vField.setEnabled(false);
			}
		}
	}
	
	/**
	 * Prepare Data for Modify
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:49:00
	 * @return void
	 */
	public void dataModify() {
		for (GridField vField: m_fields) {
			if(
				//	Any Field
				!vField.getColumnName().equals("DocAction")
				&& (
						//	Updateable and not Processed
						((vField.isUpdateable() && !isProcessed())
								&& !vField.isReadOnly()
								&& !vField.isParent())
								//	Always Updateable
								|| vField.isAlwaysUpdateable()
						)
			){
				vField.setEnabled(true);
			} else {
				vField.setEnabled(false);
			}
		}
	}
	
	/**
	 * Prepare data for Deleted
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:50:27
	 * @return void
	 */
	public void dataDeleted() {
		for (GridField vField: m_fields) {
			vField.setEnabled(false);
		}
	}
	
	/**
	 * Prepare data for See
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:54:35
	 * @return void
	 */
	public void dataSee() {
		for (GridField vField: m_fields) {
			if(vField.getColumnName().equals("DocAction")
					|| (vField.getDisplayType() == DisplayType.BUTTON
							&& !vField.isReadOnly()))
				vField.setEnabled(true);
			else
				vField.setEnabled(false);
		}
	}
	
    /**
     * valid and save data
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:07:20
     * @return
     * @return boolean
     */
    public boolean save(){
    	boolean ok = true;
    	m_Record_ID = Env.getTabRecord_ID(m_ctx, 
    			m_TabParam.getActivityNo(), m_TabParam.getTabNo());
    	//	Error
    	if(model == null){
    		Msg.alertMsg(m_ctx, m_ctx.getString(R.string.msg_Error)
    				, m_ctx.getString(R.string.msg_Error));
    		return false;
    	}
    	//	Get Values
    	for (GridField vField: m_fields) {
    		if((vField.isMandatory()
    				|| vField.isParent()) && vField.isEmpty()){
    			Msg.alertMustFillField(m_ctx, "\"" + vField.getName() 
    					+ "\"", vField.getChildView());
    			//	set ok to false
    			ok = false;
    			break;
    		}
    		//	Set to model
    		model.set_Value(vField.getColumnName(), vField.getValue());
			//	Set on Context
    		DisplayType.setContextValue(m_ctx, m_TabParam.getActivityNo(), 
    				m_TabParam.getTabNo(), vField.getField(), vField.getValue());
			//
    	}
    	//	Set Record Identifier
    	model.set_Value(m_TabInfo.getTableKeyName(), m_Record_ID);
    	//	No saved
    	if(!ok)
    		return ok;
    	//	Save
    	ok = model.save();
    	//	Set Record Identifier
    	m_Record_ID = model.get_ID();
    	if(ok) {
    		Env.setTabRecord_ID(m_ctx, 
    				m_TabParam.getActivityNo(), m_TabParam.getTabNo(), m_Record_ID);
    		//	
    		Env.setContext(m_ctx, m_TabParam.getActivityNo(), 
    				m_TabParam.getTabNo(), m_TabInfo.getTableKeyName(), m_Record_ID);
    	} else {
    		Msg.alertMsg(m_ctx, m_ctx.getString(R.string.msg_Error), 
    				model.getError());
    	}
    	//	Return
    	return ok;
    }
    
    /**
     * Get Record Identifier
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 21:42:37
     * @return
     * @return int
     */
    public int getRecord_ID() {
    	return m_Record_ID;
    }
    
    /**
     * Get Parent Record Identifier
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 21:58:49
     * @return
     * @return int
     */
    public int getParent_Record_ID() {
    	return m_Parent_Record_ID;
    }
    
    /**
     * Refresh Query
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 06/03/2014, 16:12:59
     * @param m_Record_ID
     * @param parentChanged
     * @return boolean
     */
    public boolean refresh(int record_ID, boolean parentChanged){
    	this.m_Record_ID = record_ID;
    	//	Set record Identifier in context
    	Env.setTabRecord_ID(m_ctx, 
    			m_TabParam.getActivityNo(), m_TabParam.getTabNo(), m_Record_ID);
    	//	Parent changed
    	if(parentChanged
    			|| record_ID <= 0)
    		model.clear(false);
    	
    	//	Reload
    	model.loadData(m_Record_ID);
    	//	Refresh
    	loadData();
    	//	Set Identifier
    	Env.setContext(m_ctx, m_TabParam.getActivityNo(), 
    			m_TabParam.getTabNo(), m_TabInfo.getTableKeyName(), model.getID());
    	//	Set Parent Record Identifier
    	m_Parent_Record_ID = Env.getTabRecord_ID(m_ctx, 
    			m_TabParam.getActivityNo(), m_TabParam.getParentTabNo());
    	//	Return
    	return !isEmpty();
    }
    
    /**
     * Load Persistence Object
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/05/2014, 11:03:25
     * @return
     * @return boolean
     */
    private boolean loadPO(){
    	m_Record_ID = Env.getTabRecord_ID(m_ctx, 
				m_TabParam.getActivityNo(), m_TabParam.getTabNo());
    	//	Get Model
		if (model == null)
    		model = MSPSTable.getPO(m_ctx, m_Record_ID, m_TabInfo.getTableName(), conn);
		if(model == null){
    		return false;
    	}
		//	Set identifier
		Env.setContext(m_ctx, m_TabParam.getActivityNo(), 
				m_TabParam.getTabNo(), m_TabInfo.getTableName() + "_ID", model.getID());
		//	
		return true;
    }
    
    /**
     * Get PO
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 21:53:39
     * @return
     * @return PO
     */
    public PO getPO() {
    	return model;
    }
    
    /**
     * Back to Copy
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 22:03:26
     * @return void
     */
    public void backCopy() {
    	model.backCopy();
    }
    
    /**
     * Delete Record
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 22:06:15
     * @return boolean
     */
    public boolean delete() {
    	return model.delete();
    }
    
    /**
     * Get Error
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 22:07:12
     * @return
     * @return String
     */
    public String getError() { 
    	return model.getError();
    }
    
    /**
     * Copy Values
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 22:08:57
     * @param deleteOld
     * @return void
     */
    public void copyValues(boolean deleteOld) {
    	model.copyValues(deleteOld);
    }
    
}