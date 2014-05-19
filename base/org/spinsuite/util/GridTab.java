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

import org.spinsuite.model.Callout;
import org.spinsuite.model.PO;
import org.spinsuite.view.lookup.GridField;

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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 11:47:36
	 */
	public GridTab(Context m_ctx, TabParameter m_TabParam) {
		this.m_ctx = m_ctx;
		this.m_TabParam = m_TabParam;
		m_fields = new ArrayList<GridField>();
	}
	
	/**	Fields					*/
	private ArrayList<GridField> 	m_fields = null;
	/**	Context					*/
	private Context					m_ctx = null;
	/**	Activity No				*/
	private TabParameter 			m_TabParam = null;
	/**	Active Callouts			*/
	private List<String> 			activeCallouts = new ArrayList<String>();
	/**	Active Instance			*/
	private List<Callout> 			activeCalloutInstance = new ArrayList<Callout>();

	
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
	 * Load Data from Model
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 13:55:23
	 * @param model
	 * @return void
	 */
	public void loadData(PO model) {
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
	 * 
	 * @return list of active call out for this tab
	 */
	public String[] getActiveCallouts()
	{
		String[] list = new String[activeCallouts.size()];
		return activeCallouts.toArray(list);
	}
	
	/**
	 * 
	 * @return list of active call out instance for this tab
	 */
	public Callout[] getActiveCalloutInstance()
	{
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
}