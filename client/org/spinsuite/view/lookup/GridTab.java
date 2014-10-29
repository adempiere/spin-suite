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
package org.spinsuite.view.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.model.Callout;
import org.spinsuite.model.MSPSTable;
import org.spinsuite.model.MultiMap;
import org.spinsuite.model.PO;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.Evaluatee;
import org.spinsuite.util.LogM;
import org.spinsuite.util.TabParameter;

import android.content.Context;


/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class GridTab implements Evaluatee {
	
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
		//	Set Identifiers
		m_Record_ID = Env.getTabRecord_ID(m_ctx, 
				m_TabParam.getActivityNo(), m_TabParam.getTabNo());
		m_KeyColums = Env.getTabKeyColumns(m_ctx, 
				m_TabParam.getActivityNo(), m_TabParam.getTabNo());
    	//	Parent
		int[] parent = Env.getTabRecord_ID(m_ctx, 
    			m_TabParam.getActivityNo(), m_TabParam.getParentTabNo());
    	m_Parent_Record_ID = parent[0];
		loadPO();
	}
	
	/**	Fields					*/
	private ArrayList<GridField> 		m_fields 				= null;
	/**	Context					*/
	private Context						m_ctx 					= null;
	/**	Activity No				*/
	private TabParameter 				m_TabParam 				= null;
	/**	Tab Info				*/
	private InfoTab 					m_TabInfo 				= null;
	/**	Persistence Object		*/
	private PO 							model 					= null;
	/**	Connection				*/
	private DB							conn 					= null;
	/**	Active Callouts			*/
	private List<String> 				activeCallouts 			= new ArrayList<String>();
	/**	Active Instance			*/
	private List<Callout> 				activeCalloutInstance 	= new ArrayList<Callout>();
	/**	Record Identifier		*/
	private int[] 						m_Record_ID 			= new int[]{0};
	/**	Key Columns				*/
	private String[] 					m_KeyColums 			= null;
	/**	Parent Record Identifier*/
	private int 						m_Parent_Record_ID 		= 0;
	/**	Error Message			*/
	private String						m_ErrorMsg 				= null;
	/** Map of ColumnName of source field (key) and the dependent field (value) */
	private MultiMap<String,GridField>	m_depOnField 			= new MultiMap<String,GridField>();
	
	
	/**
	 * Add Field to Grid Tab
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 11:48:33
	 * @param v_lookup
	 * @return void
	 */
	public void addField(GridField m_Field) {
		//	Valid Null
		if(m_Field == null)
			return;
		if(model != null)
			m_Field.setColumnIndex(model.getColumnIndex(m_Field.getColumnName()));
		m_fields.add(m_Field);
		//	Add Dependent On
		//  List of ColumnNames, this field is dependent on
		ArrayList<String> list = m_Field.getDependentOn();
		//	Valid Null
		if(list == null)
			return;
		//	Iterate
		for (int i = 0; i < list.size(); i++) {
			String m_FieldName = list.get(i);
			m_depOnField.put(m_FieldName, m_Field);   //  ColumnName, Field
			LogM.log(m_ctx, getClass(), Level.FINE, 
					"Dependent Field Added [" + m_FieldName + ", " + m_Field.getColumnName() + "]");
		}
		//  Add fields all fields are dependent on
		if (m_Field.getColumnName().equals("IsActive")
			|| m_Field.getColumnName().equals("Processed")
			|| m_Field.getColumnName().equals("Processing"))
			m_depOnField.put(m_Field.getColumnName(), null);
	}
	
	/**
	 * Get Value from key
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 11:55:03
	 * @param columnName
	 * @return
	 * @return Object
	 */
	public Object getValue(String columnName) {
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
	public String getValueAsString(String columnName) {
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
	public boolean getValueAsBoolean(String columnName) {
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
	public int getValueAsInt(String columnName) {
		for(GridField field : m_fields) {
			if(field.getColumnName().equals(columnName))
				return field.getValueAsInt();
		}
		//	Return
		return 0;
	}
	
	/**
	 * Set Value to Field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/05/2014, 10:02:12
	 * @param columnName
	 * @param value
	 * @return void
	 */
	public void setValue(String columnName, Object value) {
		for(GridField field : m_fields) {
			if(field.getColumnName().equals(columnName)) {
				field.setValue(value);
				break;
			}
		}
	}
	
	/**
	 * Get Field from Column Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 19:29:56
	 * @param columnName
	 * @return
	 * @return GridField
	 */
	public GridField getField(String columnName) {
		for(GridField field : m_fields) {
			if(field.getColumnName().equals(columnName)) {
				return field;
			}
		}
		//	Default
		return null;
	}
	
	/**
	 * Set Value to Field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/05/2014, 10:03:48
	 * @param mField
	 * @param value
	 * @return void
	 */
	public void setValue(GridField mField, Object value) {
		for(GridField field : m_fields) {
			if(field.equals(mField)) {
				field.setValue(value);
				break;
			}
		}
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
		//	Default
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
					&& retValue.length() != 0) {
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
	 * @return boolean
	 */
	public boolean loadData() {
		LogM.log(m_ctx, getClass(), Level.FINE, "loadData()");
		if(model == null)
			return false;
		//	Get Record Identifier
		int m_Record_ID = model.getID();
		//	
		for (GridField vField: m_fields) {
    		//	
    		if(m_Record_ID <= 0) {
    			//	Set value to parent field
				if(vField.isParent()
						|| vField.getSPS_Column_ID() == m_TabInfo.getTabSPS_Column_ID()) {
					vField.setValue(DisplayType.getContextValue(m_ctx, 
							m_TabParam.getActivityNo(), m_TabParam.getParentTabNo(), vField.getField()));
				} else {
	    			//	Set Default Value
	    			String defaultValue = vField.getDefaultValue();
	    			Object value = null;
	    			if(defaultValue != null
	    					&& defaultValue.length() > 0) {
	    				//value = DisplayType.parseValue(
	    						//Env.parseContext(m_ctx, defaultValue, false), vField.getDisplayType());
	    				value = DisplayType.getContextValue(m_ctx, 
								m_TabParam.getActivityNo(), m_TabParam.getParentTabNo(), vField.getField());
	    			} else {
	    				value = model.get_Value(vField.getColumnIndex());
	    			}
	    			//	Set Value
	    			vField.setValue(value);
				}
			} else {
				//	Set Value from Model
				vField.setValue(model.get_Value(vField.getColumnIndex()));
			}
    		//	Set Current Values
    		DisplayType.setContextValue(m_ctx, m_TabParam.getActivityNo(), 
    				m_TabParam.getTabNo(), vField.getField(), vField.getValue());
    		//	Refresh Display
    		changeDisplayDepending(vField);
    	}
		//	Set ID to Context
		Env.setContext(m_ctx, m_TabParam.getActivityNo(), m_TabParam.getTabNo(), m_TabInfo.getTableKeyName(), m_Record_ID);
		Env.setContext(m_ctx, m_TabParam.getActivityNo(), m_TabInfo.getTableKeyName(), m_Record_ID);
		//	Return
		return true;
	}
	
	/**
	 * Reload depending fields
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 30/08/2014, 20:39:26
	 * @param m_FieldChanged
	 * @return void
	 */
	public void changeDepending(GridField m_FieldChanged) {
		//	
		if(!hasDependants(m_FieldChanged.getColumnName()))
			return;
		//	
		ArrayList<GridField> list = getDependantFields(m_FieldChanged.getColumnName());
		for (int index = 0; index < list.size(); index++) {
			GridField m_DependentField = list.get(index);
			//	Valid Null
			if(m_DependentField == null)
				continue;
			LogM.log(m_ctx, getClass(), Level.FINE, 
					"Callout process dependent child [" + m_FieldChanged.getColumnName() 
							+ " --> " + m_DependentField.getColumnName() + "]");
			//	Get Field Meta-Data
			InfoField fieldMD = m_DependentField.getField();
			if(fieldMD == null)
				continue;
			//	Load
			if(DisplayType.isLookup(fieldMD.DisplayType)) {
				if(fieldMD.DisplayType != DisplayType.SEARCH
						&& m_DependentField instanceof VLookupSpinner) {
					VLookupSpinner spinner = (VLookupSpinner) m_DependentField;
					Object oldValue = spinner.getValue();
					spinner.load(true);
					//	set old value
					spinner.setValueNoReload(oldValue);
				}
			}
		}
	}
	
	/**
	 * Change Display Logic
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 06/09/2014, 14:27:59
	 * @param m_FieldChanged
	 * @return void
	 */
	public void changeDisplayDepending(GridField m_FieldChanged) {
		//	
		if(!hasDependants(m_FieldChanged.getColumnName()))
			return;
		//	
		ArrayList<GridField> list = getDependantFields(m_FieldChanged.getColumnName());
		for (int index = 0; index < list.size(); index++) {
			GridField m_DependentField = list.get(index);
			//	Valid Null
			if(m_DependentField == null)
				continue;
			LogM.log(m_ctx, getClass(), Level.FINE, 
					"Display Logic dependent child [" + m_FieldChanged.getColumnName() 
							+ " --> " + m_DependentField.getColumnName() + "]");
			//	Get Field Meta-Data
			InfoField fieldMD = m_DependentField.getField();
			if(fieldMD == null)
				continue;
			//	Display Logic
			m_DependentField.setVisible(this);
		}
	}
	
	
	/**
	 * Prepare data for New
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:44:34
	 * @return void
	 */
	public void dataNew() {
		//	Has Primary Key
		boolean hasPrimaryKey = (model != null
				? model.hasPrimaryKey()
						: false);
		for (GridField vField: m_fields) {
			if(!vField.isReadOnly()
					&& (!vField.isParent() || !hasPrimaryKey)
					&& vField.getSPS_Column_ID() != m_TabInfo.getTabSPS_Column_ID()
					&& !vField.getColumnName().equals("DocAction")) {
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
		//	Has Primary Key
		boolean hasPrimaryKey = (model != null
				? model.hasPrimaryKey()
						: false);
		for (GridField vField: m_fields) {
			if(
				//	Any Field
				!vField.getColumnName().equals("DocAction")
				&& (
						//	Updateable and not Processed
						((vField.isUpdateable() && !isProcessed())
								&& !vField.isReadOnly()
								&& (!vField.isParent() || !hasPrimaryKey)
								&& vField.getSPS_Column_ID() != m_TabInfo.getTabSPS_Column_ID())
								//	Always Updateable
								|| vField.isAlwaysUpdateable()
						)
			) {
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
	 * @param m_IsReadWrite
	 * @return void
	 */
	public void dataSee(boolean m_IsReadWrite) {
		for (GridField vField: m_fields) {
			if((vField.getColumnName().equals("DocAction")
					|| (vField.getDisplayType() == DisplayType.BUTTON
							&& !vField.isReadOnly())) && m_IsReadWrite)
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
    public boolean save() {
    	boolean ok = true;
		m_Record_ID = Env.getTabRecord_ID(m_ctx, 
				m_TabParam.getActivityNo(), m_TabParam.getTabNo());
		m_KeyColums = Env.getTabKeyColumns(m_ctx, 
				m_TabParam.getActivityNo(), m_TabParam.getTabNo());
    	//	Error
    	if(model == null) {
    		m_ErrorMsg = "@NoModelClass@";
    		return false;
    	}
    	//	Get Values
    	for (GridField vField: m_fields) {
    		if((vField.isMandatory()
    				|| vField.isParent()) && vField.isEmpty()) {
    			m_ErrorMsg = "@MustFillField@ \"@" + vField.getName() + "@\"";
    			//	Set ok to false
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
    	model.set_Value(m_TabInfo.getTableKeyName(), m_Record_ID[0]);
    	//	No saved
    	if(!ok)
    		return ok;
    	//	Save
    	ok = modelSave();
    	//	Return
    	return ok;
    }
    
    /**
     * Save only model
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/08/2014, 16:51:48
     * @return
     * @return boolean
     */
    public boolean modelSave() {
    	boolean ok = true;
    	m_ErrorMsg = null;
    	ok = model.save();
    	//	Set Record Identifier
    	m_Record_ID = model.getIDs();
    	m_KeyColums = model.getKeyColumns();
    	if(ok) {
    		Env.setTabRecord_ID(m_ctx, 
    				m_TabParam.getActivityNo(), m_TabParam.getTabNo(), m_Record_ID);
    		Env.setTabKeyColumns(m_ctx, 
    				m_TabParam.getActivityNo(), m_TabParam.getTabNo(), m_KeyColums);
    		//	
    		Env.setContext(m_ctx, m_TabParam.getActivityNo(), 
    				m_TabParam.getTabNo(), m_TabInfo.getTableKeyName(), m_Record_ID[0]);
    	} else {
    		m_ErrorMsg = model.getError();
    	}
    	//	
    	return ok;
    }
    
    /**
     * Get Record Identifier
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 21:42:37
     * @return
     * @return int
     */
    public int getRecord_ID() {
    	return m_Record_ID[0];
    }
    
    /**
     * Get Keys ID
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 13:43:09
     * @return
     * @return int[]
     */
    public int[] getKeys() {
    	return m_Record_ID;
    }
    
    /**
     * Get Key Columns
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 13:50:44
     * @return
     * @return String[]
     */
    public String[] getKeyColumns() {
    	if(model == null)
    		return null;
    	//	Default
    	return model.getKeyColumns();
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
     * Get Table Identifier
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/10/2014, 22:55:45
     * @return
     * @return int
     */
    public int getSPS_Table_ID() {
    	if(m_TabInfo != null)
    		return m_TabInfo.getSPS_Table_ID();
    	//	Default
    	return 0;
    }
    
    /**
     * Refresh
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 13:36:09
     * @param record_ID
     * @param keyColumn
     * @param parentChanged
     * @return
     * @return boolean
     */
    public boolean refresh(int[] record_ID, String[] keyColumn, boolean parentChanged) {
    	this.m_Record_ID = record_ID;
    	//	Set record Identifier in context
    	Env.setTabRecord_ID(m_ctx, 
				m_TabParam.getActivityNo(), m_TabParam.getTabNo(), m_Record_ID);
		Env.setTabKeyColumns(m_ctx, 
				m_TabParam.getActivityNo(), m_TabParam.getTabNo(), m_KeyColums);
    	//	Parent changed
    	if(parentChanged
    			|| record_ID[0] <= 0)
    		model.clear(false);
    	//	
    	model.loadData(record_ID, keyColumn);
    	//	Refresh
    	loadData();
    	//	Set Identifier
    	Env.setContext(m_ctx, m_TabParam.getActivityNo(), 
    			m_TabParam.getTabNo(), m_TabInfo.getTableKeyName(), model.getID());
    	//	Set Parent Record Identifier
    	int[] parent = Env.getTabRecord_ID(m_ctx, 
    			m_TabParam.getActivityNo(), m_TabParam.getParentTabNo());
    	m_Parent_Record_ID = parent[0];
    	//	Return
    	return !isEmpty();
    }
    
    /**
     * Refresh
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 13:53:10
     * @param record_ID
     * @param parentChanged
     * @return
     * @return boolean
     */
    public boolean refresh(int[] record_ID, boolean parentChanged) {
    	return refresh(record_ID, null, parentChanged);
    }
    
    /**
     * Load Persistence Object
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/05/2014, 11:03:25
     * @return
     * @return boolean
     */
    private boolean loadPO() {
    	Env.setTabRecord_ID(m_ctx, 
				m_TabParam.getActivityNo(), m_TabParam.getTabNo(), m_Record_ID);
		Env.setTabKeyColumns(m_ctx, 
				m_TabParam.getActivityNo(), m_TabParam.getTabNo(), m_KeyColums);
    	//	Get Model
		if (model == null) {
			int instance_ID = m_Record_ID[0];
			if(m_Record_ID.length > 1)
				instance_ID = 0;
			//	
			model = MSPSTable.getPO(m_ctx, instance_ID, m_TabInfo.getTableName(), conn);
			//	
			if(model != null
					&& m_Record_ID.length > 1)
				model.loadData(m_Record_ID, m_KeyColums);
		}
		if(model == null) {
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
    	m_Record_ID = model.getIDs();
    	m_KeyColums = model.getKeyColumns();
    }
    
    /**
     * Delete Record
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 22:06:15
     * @return boolean
     */
    public boolean delete() {
    	m_ErrorMsg = null;
    	//	Valid Error
    	if(!model.delete()) {
    		m_ErrorMsg = model.getError();
    		return false;
    	}
    	return true;
    }
    
    /**
     * Get Error
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 22:07:12
     * @return
     * @return String
     */
    public String getError() { 
    	return m_ErrorMsg;
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
    
    /**
     * Get Is Deleteable
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/09/2014, 19:52:21
     * @return
     * @return boolean
     */
    public boolean isDeleteable() {
    	return model.isDeleteable();
    }
    
	/**************************************************************************
	 *  Has this field dependents ?
	 *  @param columnName column name
	 *  @return true if column has dependent
	 */
	public boolean hasDependants (String columnName) {
	//	m_depOnField.printToLog();
		return m_depOnField.containsKey(columnName);
	}   //  isDependentOn

	/**
	 *  Get dependents fields of columnName
	 *  @param columnName column name
	 *  @return ArrayList with GridFields dependent on columnName
	 */
	public ArrayList<GridField> getDependantFields (String columnName) {
		return m_depOnField.getValues(columnName);
	}   //  getDependentFields
	
	/**
	 * Add Dependent
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/09/2014, 16:05:38
	 * @param key
	 * @param m_Field
	 * @return void
	 */
	public void addDependentField(String key, GridField m_Field) {
		m_depOnField.put(key, m_Field);
	}

	@Override
	public String get_ValueAsString(String variableName) {
		return Env.getContext(m_ctx, m_TabParam.getActivityNo(), variableName);
	}
	
	/**
	 * Get Context
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/10/2014, 21:05:41
	 * @return
	 * @return Context
	 */
	public Context getCtx() {
		return m_ctx;
	}
	
}