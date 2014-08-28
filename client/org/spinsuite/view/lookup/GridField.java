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


import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.interfaces.OnFieldChangeListener;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.TabParameter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public abstract class GridField extends LinearLayout {
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 16:46:15
	 * @param context
	 */
	public GridField(Context context) {
		super(context);
		mainInit();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 16:46:15
	 * @param context
	 * @param attrs
	 */
	public GridField(Context context, AttributeSet attrs) {
		super(context, attrs);
		mainInit();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 16:46:15
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public GridField(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mainInit();
	}
	
	/**
	 * With Field
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:28:25
	 * @param context
	 * @param m_field
	 * @param m_TabParam
	 */
	public GridField(Context context, InfoField m_field, TabParameter m_TabParam) {
		super(context);
		this.m_field = m_field;
		this.m_TabParam = m_TabParam;
		mainInit();
	}
	
	/**	Text View			*/
	protected TextView					v_Label = null;
	/**	Field Object		*/
	protected InfoField 				m_field = null;
	/**	Parameter			*/
	protected TabParameter 				m_TabParam = null;
	/**	Listener			*/
	protected OnFieldChangeListener 	m_Listener = null;
	/**	Column Index		*/
	private int							m_ColumnIndex = 0;
	
	/**
	 * Main Init
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:31:56
	 * @return void
	 */
	private void mainInit(){
		//	Set Parameter
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.MATCH_PARENT));
		//	Set Orientation
		setOrientation(VERTICAL);
		//	Set Label
		v_Label = new TextView(getContext());
		//	
		if(m_field != null)
			v_Label.setText(m_field.Name);
		//	Add to View
		addView(v_Label);
	}
	
	/**
	 * Get Tab No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/05/2014, 14:04:53
	 * @return
	 * @return int
	 */
	public int getTabNo() {
		if(m_TabParam != null)
			return m_TabParam.getTabNo();
		return 0;
	}
	
	/**
	 * Get Activity No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/05/2014, 14:04:10
	 * @return
	 * @return int
	 */
	public int getActivityNo() { 
		if(m_TabParam != null)
			return m_TabParam.getActivityNo();
		return 0;
	}
	
	/**
	 * Get Field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 22:22:21
	 * @return
	 * @return VOInfoField
	 */
	public InfoField getField(){
		return m_field;
	}
	
	/**
	 * Is Mandatory
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 10:11:43
	 * @return
	 * @return boolean
	 */
	public boolean isMandatory(){
		return m_field.IsMandatory;
	}
	
	/**
	 * Init View
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 20:27:04
	 * @return
	 * @return void
	 */
	protected abstract void init();
	
	/**
	 * Set Value to Lookup
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 08:31:00
	 * @param value
	 * @return void
	 */
	public abstract void setValue(Object value);
	
	/**
	 * Get Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 08:31:05
	 * @return
	 * @return Object
	 */
	public abstract Object getValue();
	
	/**
	 * Get Old Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 15:05:03
	 * @return
	 * @return Object
	 */
	public abstract Object getOldValue();
	
	/**
	 * Get Display Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/04/2014, 10:34:36
	 * @return
	 * @return String
	 */
	public abstract String getDisplayValue();
	
	/**
	 * Is Empty
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 10:15:24
	 * @return
	 * @return boolean
	 */
	public abstract boolean isEmpty();
	
	/**
	 * Get child view
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:14:04
	 * @return
	 * @return View
	 */
	public abstract View getChildView();

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VLookup [v_Label=" + v_Label + ", m_field=" + m_field + "]";
	}
	
	/**
	 * Set On Field Change Listener
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/05/2014, 20:57:22
	 * @param m_Listener
	 * @return void
	 */
	public void setOnFieldChangeListener(OnFieldChangeListener m_Listener){
		this.m_Listener = m_Listener;
	}
	
	/**
	 * Get Value As String
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 12:14:23
	 * @return
	 * @return String
	 */
	public String getValueAsString() {
		//	Valid Field
		InfoField field = getField();
		if(field == null
				|| getValue() == null)
			return null;
		//	String Case
		if(DisplayType.isText(field.DisplayType))
			return (String) getValue();
		//	Boolean Value
		if(DisplayType.isBoolean(field.DisplayType)) { 
			Boolean bValue = (Boolean)getValue();
			return (bValue? "Y": "N");
		}
		//	Numeric
		else
			return String.valueOf(getValue());
	}
	
	/**
	 * Get Value As Boolean
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 12:19:46
	 * @return
	 * @return boolean
	 */
	public boolean getValueAsBoolean() {
		//	Valid Field
		if(m_field == null
				|| getValue() == null)
			return false;
		//	String
		if(DisplayType.isText(m_field.DisplayType))
			return (String.valueOf(getValue()).equals("N")? 
									false: 
										true);
		else
			return false;
	}
	
	/**
	 * Get Value As Integer
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 12:27:14
	 * @return
	 * @return int
	 */
	public int getValueAsInt() {
		//	Valid Field
		if(m_field == null
				|| getValue() == null)
			return 0;
		//	String
		if(DisplayType.isText(m_field.DisplayType)
				|| DisplayType.isNumeric(m_field.DisplayType)
				|| DisplayType.isID(m_field.DisplayType)
				|| DisplayType.isLookup(m_field.DisplayType)) {
			try {
				Object value = getValue();
				if(value instanceof Integer)
					return (Integer) value;
				else
					return Integer.parseInt((String) getValue());
			} catch (Exception e) {
				LogM.log(getContext(), this.getClass(), Level.SEVERE, "Parse Error " + e.toString(), e);
			}
			return 0;
		} else
			return 0;
	}
	
	/**
	 * Get Callout
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:16:50
	 * @return
	 * @return String
	 */
	public String getCallout() {
		if(m_field == null)
			return null;
		//	Default
		return m_field.Callout;
	}
	
	/**
	 * Is Always Updateable
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:19:20
	 * @return
	 * @return boolean
	 */
	public boolean isAlwaysUpdateable() {
		if(m_field == null)
			return false;
		//	Default
		return m_field.IsAlwaysUpdateable;
	}
	
	/**
	 * Get Column Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:20:44
	 * @return
	 * @return String
	 */
	public String getColumnName() {
		if(m_field == null)
			return null;
		//	Default
		return m_field.ColumnName;
	}
	
	/**
	 * Get Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 21:37:21
	 * @return
	 * @return String
	 */
	public String getName() {
		if(m_field == null)
			return null;
		//	Default
		return m_field.Name;
	}
	
	/**
	 * Get Display Type
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:54:16
	 * @return
	 * @return int
	 */
	public int getDisplayType() {
		if(m_field == null)
			return 0;
		//	Default
		return m_field.DisplayType;
	}
	
	/**
	 * Set Column Index
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:23:17
	 * @param m_ColumnIndex
	 * @return void
	 */
	public void setColumnIndex(int m_ColumnIndex) {
		this.m_ColumnIndex = m_ColumnIndex;
	}
	
	/**
	 * Get Column Index
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:23:38
	 * @return
	 * @return int
	 */
	public int getColumnIndex() {
		return m_ColumnIndex;
	}
	
	/**
	 * Is Parent
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:25:33
	 * @return
	 * @return boolean
	 */
	public boolean isParent() {
		if(m_field == null)
			return false;
		//	Default
		return m_field.IsParent;
	}
	
	/**
	 * Is Updateable
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:47:34
	 * @return
	 * @return boolean
	 */
	public boolean isUpdateable() {
		if(m_field == null)
			return false;
		//	Default
		return m_field.IsUpdateable;
	}
	
	/**
	 * Is Read Only
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 17:44:15
	 * @return
	 * @return boolean
	 */
	public boolean isReadOnly() {
		if(m_field == null)
			return false;
		//	Default
		return m_field.IsReadOnly;
	}
	
	/**
	 * Create Lookup Optional Spinner from Lookup
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/05/2014, 11:56:31
	 * @param act
	 * @param m_SPS_Column_ID
	 * @param m_Lookup
	 * @param m_TabParameter
	 * @return
	 * @return GridField
	 */
	public static GridField createLookup(Activity act, int m_SPS_Column_ID, Lookup m_Lookup, TabParameter m_TabParameter) {
		InfoField field = loadInfoColumnField(act, m_SPS_Column_ID);
		//	Create Grid Field
		return createLookup(act, field, m_Lookup, m_TabParameter);
	}
	
	/**
	 * Create Lookup from Column
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/05/2014, 17:40:35
	 * @param act
	 * @param m_SPS_Column_ID
	 * @return
	 * @return GridField
	 */
	public static GridField createLookup(Activity act, int m_SPS_Column_ID) {
		InfoField field = loadInfoColumnField(act, m_SPS_Column_ID);
		//	Create Lookup
		return createLookup(act, field, null, null);
	}
	
	/**
	 * Create Lookup from Column Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/08/2014, 18:07:18
	 * @param act
	 * @param p_TableName
	 * @param p_ColumnName
	 * @return
	 * @return GridField
	 */
	public static GridField createLookup(Activity act, String p_TableName, String p_ColumnName) {
		InfoField field = loadInfoColumnField(act, p_TableName, p_ColumnName);
		//	Create Lookup
		return createLookup(act, field, null, null);
	}
	
	/**
	 * Get Lookup with Tab Parameter
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/08/2014, 18:53:28
	 * @param act
	 * @param p_TableName
	 * @param p_ColumnName
	 * @param p_TabParameter
	 * @return
	 * @return GridField
	 */
	public static GridField createLookup(Activity act, String p_TableName, 
			String p_ColumnName, TabParameter p_TabParameter) {
		InfoField field = loadInfoColumnField(act, p_TableName, p_ColumnName);
		//	Create Lookup
		return createLookup(act, field, null, p_TabParameter);
	}
	
	/**
	 * Create Lookup from Field Info
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/08/2014, 15:17:07
	 * @param act
	 * @param field
	 * @return
	 * @return GridField
	 */
	public static GridField createLookup(Activity act, InfoField field) {
		return createLookup(act, field, null, null);
	}
	
	/**
	 * 
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/08/2014, 15:10:34
	 * @param act
	 * @param field
	 * @return
	 * @return GridField
	 */
	public static GridField createLookup(Activity act, InfoField field, 
			Lookup m_Lookup, TabParameter p_TabParameter) {
		//	Valid Null
		if(field == null)
			return null;
		//	
		GridField gridField = null;
		//	Add
		if(DisplayType.isDate(field.DisplayType)){
			gridField = new VLookupDateBox(act, field);
		} else if(DisplayType.isText(field.DisplayType)){
			VLookupString lookupString = new VLookupString(act, field);
			gridField = lookupString;
		} else if(DisplayType.isBoolean(field.DisplayType)) {
			gridField = new VLookupCheckBox(act, field);
		} else if(DisplayType.isLookup(field.DisplayType)) {
			//	Table Direct
			if(field.DisplayType == DisplayType.TABLE_DIR
					|| field.DisplayType == DisplayType.LIST
					|| field.DisplayType == DisplayType.TABLE) {
				//	Valid Null
				gridField = new VLookupSpinner(act, field, p_TabParameter, m_Lookup);
					//gridField = new VLookupSpinner(act, field);
			} else if(field.DisplayType == DisplayType.SEARCH) {
				gridField = new VLookupSearch(act, field);
			}
		} else if(field.DisplayType == DisplayType.BUTTON) {
			VLookupButton lookupButton = null;
			if(field.ColumnName.equals("DocAction")) {
				//lookupButton = new VLookupButtonDocAction(act, field, (DocAction) mGridTab.getPO());
			} else if(field.ColumnName.equals("PaymentRule")) {
				//	Payment Rule Button
				lookupButton = new LookupButtonPaymentRule(act, field);
			} else {
				lookupButton = new VLookupButton(act, field);
			}
			//	Set Parameters
			gridField = lookupButton;
		}
		return gridField;
	}
	
	/**
	 * Get Info Field from Table Name and Column Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/08/2014, 18:03:32
	 * @param ctx
	 * @param p_TableName
	 * @param p_ColumnName
	 * @return
	 * @return InfoField
	 */
	public static InfoField loadInfoColumnField(Context ctx, String p_TableName, String p_ColumnName) {
		if(p_TableName != null
				&& p_TableName.length() > 0
				&& p_ColumnName != null
				&& p_ColumnName.length() > 0)
			return loadInfoColumnField(ctx, p_TableName, p_ColumnName, 0);
		//	Default validation
		return null;
	}
	
	/**
	 * Get Info Field from Column Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/08/2014, 18:05:05
	 * @param ctx
	 * @param p_SPS_Column_ID
	 * @return
	 * @return InfoField
	 */
	public static InfoField loadInfoColumnField(Context ctx, int p_SPS_Column_ID) {
		if(p_SPS_Column_ID != 0)
			return loadInfoColumnField(ctx, null, null, p_SPS_Column_ID);
		//	Default validation
		return null;
	}
	
	/**
	 * Load Column
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/05/2014, 17:58:13
	 * @param ctx
	 * @param p_TableName
	 * @param p_ColumnName
	 * @param p_SPS_Column_ID
	 * @return
	 * @return InfoField
	 */
	private static InfoField loadInfoColumnField(Context ctx, String p_TableName, 
			String p_ColumnName, int p_SPS_Column_ID){
		//	
		String language = Env.getAD_Language(ctx);
		boolean isBaseLanguage = Env.isBaseLanguage(ctx);
		StringBuffer sql = new StringBuffer();
		//	if Base Language
		if(isBaseLanguage){
			sql.append("SELECT c.AD_Element_ID, c.AD_Reference_ID, c.AD_Reference_Value_ID, c.AD_Val_Rule_ID, c.DefaultValue, " + 
					"c.Callout, c.ColumnName, c.ColumnSQL, c.EntityType, c.FieldLength, c.FormatPattern, c.IsAlwaysUpdateable, " +
					"c.IsCentrallyMaintained, c.IsEncrypted, c.IsIdentifier, c.IsKey, c.IsMandatory, c.IsParent, c.IsSelectionColumn, " +
					"c.IsUpdateable, c.SelectionSeqNo, c.SeqNo, c.SPS_Column_ID, c.SPS_Table_ID, c.ValueMax, c.ValueMin, c.VFormat, " +
					"c.AD_Process_ID, c.Name, c.Description, c.IsActive " +
					//	From
					"FROM SPS_Table t " +
					"INNER JOIN SPS_Column c ON(c.SPS_Table_ID = t.SPS_Table_ID) ");
		} else {
			sql.append("SELECT c.AD_Element_ID, c.AD_Reference_ID, c.AD_Reference_Value_ID, c.AD_Val_Rule_ID, c.DefaultValue, " + 
					"c.Callout, c.ColumnName, c.ColumnSQL, c.EntityType, c.FieldLength, c.FormatPattern, c.IsAlwaysUpdateable, " +
					"c.IsCentrallyMaintained, c.IsEncrypted, c.IsIdentifier, c.IsKey, c.IsMandatory, c.IsParent, c.IsSelectionColumn, " +
					"c.IsUpdateable, c.SelectionSeqNo, c.SeqNo, c.SPS_Column_ID, c.SPS_Table_ID, c.ValueMax, c.ValueMin, c.VFormat, " +
					"c.AD_Process_ID, ct.Name, c.Description, c.IsActive " +
					//	From
					"FROM SPS_Table t " +
					"INNER JOIN SPS_Column c ON(c.SPS_Table_ID = t.SPS_Table_ID) " +
					"INNER JOIN SPS_Column_Trl ct ON(ct.SPS_Column_ID = c.SPS_Column_ID AND ct.AD_Language = '").append(language).append("') ");
		}
		//	Parameters
		String [] values = null;
		//	Where
		if(p_TableName != null
				&& p_ColumnName != null) {
			sql.append("WHERE t.TableName = ? AND c.ColumnName = ? ");
			//	Set Parameters
			values = new String[]{p_TableName, p_ColumnName};
		} else {
			sql.append("WHERE c.SPS_Column_ID = ? ");
			//	Set Parameters
			values = new String[]{String.valueOf(p_SPS_Column_ID)};
		}
		//	Log
		LogM.log(ctx, GridField.class, Level.FINE, "SQL TableInfo SQL:" + sql);
		//	Create Connection
		DB conn = new DB(ctx);
		DB.loadConnection(conn, DB.READ_ONLY);
		//	
		Cursor rs = null;
		rs = conn.querySQL(sql.toString(), values);
		//	
		InfoField iFieldColumn = null;
		if(rs.moveToFirst()){
			int i = 0;
			iFieldColumn = new InfoField();
			String booleanValue = null;
			iFieldColumn.AD_Element_ID = rs.getInt(i++);
			iFieldColumn.DisplayType = rs.getInt(i++);
			iFieldColumn.AD_Reference_Value_ID = rs.getInt(i++);
			iFieldColumn.AD_Val_Rule_ID = rs.getInt(i++);
			iFieldColumn.DefaultValue = rs.getString(i++);
			iFieldColumn.Callout = rs.getString(i++);
			iFieldColumn.ColumnName = rs.getString(i++);
			iFieldColumn.ColumnSQL = rs.getString(i++);
			iFieldColumn.EntityType = rs.getString(i++);
			iFieldColumn.FieldLength = rs.getInt(i++);
			iFieldColumn.FormatPattern = rs.getString(i++);
			booleanValue = rs.getString(i++);
			iFieldColumn.IsAlwaysUpdateable = (booleanValue != null && booleanValue.equals("Y"));
			booleanValue = rs.getString(i++);
			iFieldColumn.IsCentrallyMaintained = (booleanValue != null && booleanValue.equals("Y"));
			booleanValue = rs.getString(i++);
			iFieldColumn.IsEncrypted= (booleanValue != null && booleanValue.equals("Y"));
			booleanValue = rs.getString(i++);
			iFieldColumn.IsIdentifier= (booleanValue != null && booleanValue.equals("Y"));
			booleanValue = rs.getString(i++);
			iFieldColumn.IsKey= (booleanValue != null && booleanValue.equals("Y"));
			booleanValue = rs.getString(i++);
			iFieldColumn.IsMandatory= (booleanValue != null && booleanValue.equals("Y"));
			booleanValue = rs.getString(i++);
			iFieldColumn.IsParent= (booleanValue != null && booleanValue.equals("Y"));
			booleanValue = rs.getString(i++);
			iFieldColumn.IsSelectionColumn = (booleanValue != null && booleanValue.equals("Y"));
			booleanValue = rs.getString(i++);
			iFieldColumn.IsUpdateable = (booleanValue != null && booleanValue.equals("Y"));
			iFieldColumn.SelectionSeqNo = rs.getInt(i++);
			iFieldColumn.SeqNo = rs.getInt(i++);
			iFieldColumn.SPS_Column_ID= rs.getInt(i++);
			iFieldColumn.SPS_Table_ID= rs.getInt(i++);
			iFieldColumn.ValueMax = rs.getString(i++);
			iFieldColumn.ValueMin = rs.getString(i++);
			iFieldColumn.VFormat = rs.getString(i++);
			iFieldColumn.AD_Process_ID = rs.getInt(i++);
			//	Fields
			iFieldColumn.Name = rs.getString(i++);
			iFieldColumn.Description = rs.getString(i++);
			booleanValue = rs.getString(i++);
			iFieldColumn.IsActive = (booleanValue != null && booleanValue.equals("Y"));
		}
		//	Close DB
		DB.closeConnection(conn);
		//	
		return iFieldColumn;
	}
}
