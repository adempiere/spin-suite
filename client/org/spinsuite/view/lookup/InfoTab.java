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
package org.spinsuite.view.lookup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.FilterValue;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class InfoTab {
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 13:07:07
	 * @param ctx
	 * @param SFA_Tab_ID
	 * @param conn
	 */
	public InfoTab(Context ctx, int SFA_Tab_ID, DB conn){
		loadInfoColumnField(ctx, SFA_Tab_ID, false, conn);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/03/2014, 23:29:52
	 * @param ctx
	 * @param SFA_Tab_ID
	 * @param onlySelectionColumn
	 * @param conn
	 */
	public InfoTab(Context ctx, int SFA_Tab_ID, boolean onlySelectionColumn, DB conn){
		loadInfoColumnField(ctx, SFA_Tab_ID, onlySelectionColumn, conn);
	}
	
	
	/** Fields	             		*/
	private InfoField[]				m_fields = null;
	
	/**	Tab Name					*/
	private String 					TabName 			= null;
	private int 					SFA_Tab_ID 			= 0;
	private int 					SFA_Table_ID 		= 0;
	private String 					TableName 			= null;
	private int 					SFA_Window_ID 		= 0;
	private int 					AD_Process_ID 		= 0;
	private String 					Classname 			= null;
	private String 					TabDescription 		= null;
	private String 					TabHelp 			= null;
	private boolean					TabIsInsertRecord 	= false;
	private boolean 				TabIsReadOnly 		= false;
	private String 					OrderByClause 		= null;
	private int 					Parent_Column_ID 	= 0;
	private int 					TabSFA_Column_ID 	= 0;
	private int 					TabSeqNo 			= 0;
	private int 					TabLevel 			= 0;
	private String 					WhereClause 		= null;
	private FilterValue				filterValue			= null;
	/**	Parents Fields				*/
	private InfoField [] 			m_parentFields 		= null;
	private static final int 		HEADER_INDEX 		= 17;
	
	/**
	 * Get Where Clause
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:16:35
	 * @return
	 * @return String
	 */
	public String getWhereClause() {
		return WhereClause;
	}
	
	/**
	 * Get Tab Level
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:16:07
	 * @return
	 * @return int
	 */
	public int getTabLevel() {
		return TabLevel;
	}
	
	/**
	 * Get Tab Sequence
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:15:38
	 * @return
	 * @return int
	 */
	public int getTabSeqNo() {
		return TabSeqNo;
	}
	
	/**
	 * Get Tab SFA Column Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:15:01
	 * @return
	 * @return int
	 */
	public int getTabSFA_Column_ID() {
		return TabSFA_Column_ID;
	}
	
	/**
	 * Get Parent Column Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:14:19
	 * @return
	 * @return int
	 */
	public int getParent_Column_ID() {
		return Parent_Column_ID;
	}
	
	/**
	 * Get Order By Clause
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:13:50
	 * @return
	 * @return String
	 */
	public String getOrderByClause() {
		return OrderByClause;
	}
	
	/**
	 * Get Tab Is Read Only
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:13:20
	 * @return
	 * @return boolean
	 */
	public boolean getTabIsReadOnly() {
		return TabIsReadOnly;
	}
	
	/**
	 * Is Insert Record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:11:34
	 * @return
	 * @return boolean
	 */
	public boolean getTabIsInsertRecord() {
		return TabIsInsertRecord;
	}
	
	/**
	 * Get Tab Help
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:10:59
	 * @return
	 * @return String
	 */
	public String getTabHelp() {
		return TabHelp;
	}
	
	/**
	 * Get Tab Description
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:10:04
	 * @return
	 * @return String
	 */
	public String getTabDescription() {
		return TabDescription;
	}
	
	/**
	 * Get Class Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:09:28
	 * @return
	 * @return String
	 */
	public String getClassname() {
		return Classname;
	}
	
	/**
	 * Get Process Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:08:45
	 * @return
	 * @return int
	 */
	public int getAD_Process_ID() {
		return AD_Process_ID;
	}
	
	/**
	 * Get Window Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:08:15
	 * @return
	 * @return int
	 */
	public int getSFA_Window_ID() {
		return SFA_Window_ID;
	}
	
	/**
	 * Get Table Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:07:41
	 * @return
	 * @return int
	 */
	public int getSFA_Table_ID(){
		return SFA_Table_ID;
	}
	/**
	 * Get Tab Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:07:00
	 * @return
	 * @return int
	 */
	public int getSFA_Tab_ID(){
		return SFA_Tab_ID;
	}
	
	/**
	 * Get Tab Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 18:06:30
	 * @return
	 * @return String
	 */
	public String getTabName(){
		return TabName;
	}
	
	
	/**
	 * Load Info Fields
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/03/2014, 23:27:15
	 * @param ctx
	 * @param SFA_Tab_ID
	 * @param onlySelection
	 * @param p_Conn
	 * @return void
	 */
	private void loadInfoColumnField(Context ctx, int SFA_Tab_ID, boolean onlySelection,DB p_Conn){
		//	Is Mandatory
		if(SFA_Tab_ID == 0)
			return;
		//	
		String language = Env.getAD_Language(ctx);
		boolean isBaseLanguage = Env.isBaseLanguage(ctx);
		StringBuffer sql = new StringBuffer();
		//	if Base Language
		if(isBaseLanguage){
			sql.append("SELECT t.Name, t.SFA_Tab_ID, t.SFA_Table_ID,t.SFA_Window_ID, t.AD_Process_ID, tb.TableName, " +
					"t.Classname,t.Description, t.Help, t.IsInsertRecord, t.IsReadOnly, t.OrderByClause, " +
					"t.Parent_Column_ID,t.SFA_Column_ID, t.SeqNo, t.TabLevel, t.WhereClause, c.AD_Element_ID, " +
					"CASE WHEN f.AD_Reference_ID IS NOT NULL THEN f.AD_Reference_ID ELSE C.AD_Reference_ID END AD_Reference_ID, " +
					"CASE WHEN f.AD_Reference_Value_ID IS NOT NULL THEN f.AD_Reference_Value_ID ELSE c.AD_Reference_Value_ID END AD_Reference_Value_ID, " +
					"CASE WHEN f.AD_Val_Rule_ID IS NOT NULL THEN f.AD_Val_Rule_ID ELSE c.AD_Val_Rule_ID END AD_Val_Rule_ID, " +
					"c.Callout, c.ColumnName, c.ColumnSQL, c.DefaultValue, c.EntityType, c.FieldLength, " +
					"c.FormatPattern, c.IsAlwaysUpdateable, c.IsCentrallyMaintained, c.IsEncrypted, c.IsIdentifier, c.IsKey, " +
					"CASE WHEN f.IsMandatory = 'Y' THEN f.IsMandatory ELSE c.IsMandatory END IsMandatory, " +
					"c.IsParent, c.IsSelectionColumn, c.IsUpdateable, c.SelectionSeqNo, " +
					"c.SeqNo, c.SFA_Column_ID, c.SFA_Table_ID, c.ValueMax, c.ValueMin, c.VFormat, c.AD_Process_ID, p.AD_Form_ID, " +
					//	Fields
					"f.Name, f.Description, f.Help, f.AD_FieldGroup_ID, f.DisplayLogic, f.IsActive, f.IsDisplayed, " +
					"f.IsReadOnly, f.IsSameLine, f.SeqNo, f.SFA_Field_ID " +
					//	From
					"FROM SFA_Tab t " +
					"INNER JOIN SFA_Table tb ON(tb.SFA_Table_ID = t.SFA_Table_ID) " +
					"INNER JOIN SFA_Field f ON(f.SFA_Tab_ID = t.SFA_Tab_ID) " +
					"INNER JOIN SFA_Column c ON(c.SFA_Column_ID = f.SFA_Column_ID) " +
					"LEFT JOIN AD_Process p ON(p.AD_Process_ID = c.AD_Process_ID) ");
		} else {
			sql.append("SELECT tt.Name, t.SFA_Tab_ID, t.SFA_Table_ID,t.SFA_Window_ID, t.AD_Process_ID, tb.TableName, " +
					"t.Classname,tt.Description, tt.Help, t.IsInsertRecord, t.IsReadOnly, t.OrderByClause, " +
					"t.Parent_Column_ID,t.SFA_Column_ID,t.SeqNo, t.TabLevel, t.WhereClause, c.AD_Element_ID, " +
					"CASE WHEN f.AD_Reference_ID IS NOT NULL THEN f.AD_Reference_ID ELSE C.AD_Reference_ID END AD_Reference_ID, " +
					"CASE WHEN f.AD_Reference_Value_ID IS NOT NULL THEN f.AD_Reference_Value_ID ELSE c.AD_Reference_Value_ID END AD_Reference_Value_ID, " +
					"CASE WHEN f.AD_Val_Rule_ID IS NOT NULL THEN f.AD_Val_Rule_ID ELSE c.AD_Val_Rule_ID END AD_Val_Rule_ID, " +
					"c.Callout, c.ColumnName, c.ColumnSQL, c.DefaultValue, c.EntityType, c.FieldLength, " +
					"c.FormatPattern, c.IsAlwaysUpdateable, c.IsCentrallyMaintained, c.IsEncrypted, c.IsIdentifier, c.IsKey, " +
					"CASE WHEN f.IsMandatory = 'Y' THEN f.IsMandatory ELSE c.IsMandatory END IsMandatory, " +
					"c.IsParent, c.IsSelectionColumn, c.IsUpdateable, c.SelectionSeqNo, " +
					"c.SeqNo, c.SFA_Column_ID, c.SFA_Table_ID, c.ValueMax, c.ValueMin, c.VFormat, c.AD_Process_ID, p.AD_Form_ID, " +
					//	Fields
					"ft.Name, ft.Description, ft.Help, f.AD_FieldGroup_ID, f.DisplayLogic, f.IsActive, f.IsDisplayed, " +
					"f.IsReadOnly, f.IsSameLine, f.SeqNo, f.SFA_Field_ID " +
					//	From
					"FROM SFA_Tab t " +
					"INNER JOIN SFA_Table tb ON(tb.SFA_Table_ID = t.SFA_Table_ID) " +
					"INNER JOIN SFA_Tab_Trl tt ON(tt.SFA_Tab_ID = t.SFA_Tab_ID AND tt.AD_Language = '").append(language).append("') " +
					"INNER JOIN SFA_Field f ON(f.SFA_Tab_ID = t.SFA_Tab_ID) " +
					"INNER JOIN SFA_Field_Trl ft ON(ft.SFA_Field_ID = f.SFA_Field_ID AND ft.AD_Language = '").append(language).append("') " +
					"INNER JOIN SFA_Column c ON(c.SFA_Column_ID = f.SFA_Column_ID) " +
					"LEFT JOIN AD_Process p ON(p.AD_Process_ID = c.AD_Process_ID) ");
		}
		//	Where
		sql.append("WHERE t.IsActive = 'Y' ")
					.append("AND f.IsActive = 'Y' ")
					.append("AND t.SFA_Tab_ID = ").append(SFA_Tab_ID).append(" ");
		//	Only Selection Columns
		if(onlySelection)
			sql.append("AND c.IsSelectionColumn = 'Y' ");
		//	Order By
		sql.append("ORDER BY f.SeqNo");
		
		LogM.log(ctx, getClass(), Level.FINE, "SQL TableInfo SQL:" + sql);
		
		ArrayList<InfoField> columns = new ArrayList<InfoField>();
		DB conn = null;
		if(p_Conn != null)
			conn = p_Conn;
		else
			conn = new DB(ctx);
		if(!conn.isOpen())
			conn.openDB(DB.READ_ONLY);
		Cursor rs = null;
		rs = conn.querySQL(sql.toString(), null);
		if(rs.moveToFirst()){
			int i = 0;
			//	
			TabName = rs.getString(i++);
			this.SFA_Tab_ID = rs.getInt(i++);
			SFA_Table_ID = rs.getInt(i++);
			SFA_Window_ID = rs.getInt(i++);
			AD_Process_ID = rs.getInt(i++);
			TableName = rs.getString(i++);
			Classname = rs.getString(i++);
			TabDescription = rs.getString(i++);
			TabHelp = rs.getString(i++);
			TabIsInsertRecord = rs.getString(i++).equals("Y");
			TabIsReadOnly = rs.getString(i++).equals("Y");
			OrderByClause = rs.getString(i++);
			Parent_Column_ID = rs.getInt(i++);
			TabSFA_Column_ID = rs.getInt(i++);
			TabSeqNo = rs.getInt(i++);
			TabLevel = rs.getInt(i++);
			WhereClause = rs.getString(i++);
			//	
			do{
				InfoField iFieldColumn = new InfoField();
				String booleanValue = null;
				iFieldColumn.AD_Element_ID = rs.getInt(i++);
				iFieldColumn.DisplayType = rs.getInt(i++);
				iFieldColumn.AD_Reference_Value_ID = rs.getInt(i++);
				iFieldColumn.AD_Val_Rule_ID = rs.getInt(i++);
				iFieldColumn.Callout = rs.getString(i++);
				iFieldColumn.ColumnName = rs.getString(i++);
				iFieldColumn.ColumnSQL = rs.getString(i++);
				iFieldColumn.DefaultValue = rs.getString(i++);
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
				iFieldColumn.SFA_Column_ID= rs.getInt(i++);
				iFieldColumn.SFA_Table_ID= rs.getInt(i++);
				iFieldColumn.ValueMax = rs.getString(i++);
				iFieldColumn.ValueMin = rs.getString(i++);
				iFieldColumn.VFormat = rs.getString(i++);
				iFieldColumn.AD_Process_ID = rs.getInt(i++);
				iFieldColumn.AD_Form_ID = rs.getInt(i++);
				//	Fields
				iFieldColumn.Name = rs.getString(i++);
				iFieldColumn.Description = rs.getString(i++);
				iFieldColumn.Help = rs.getString(i++);
				iFieldColumn.AD_FieldGroup_ID = rs.getInt(i++);
				iFieldColumn.DisplayLogic = rs.getString(i++);
				booleanValue = rs.getString(i++);
				iFieldColumn.IsActive = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iFieldColumn.IsDisplayed = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iFieldColumn.IsReadOnly = (booleanValue != null && booleanValue.equals("Y"));
				booleanValue = rs.getString(i++);
				iFieldColumn.IsSameLine = (booleanValue != null && booleanValue.equals("Y"));
				iFieldColumn.FieldSeqNo = rs.getInt(i++);
				iFieldColumn.SFA_Field_ID = rs.getInt(i++);
				//	Add Field
				columns.add(iFieldColumn);
				//	Set index
				i = HEADER_INDEX;
			}while(rs.moveToNext());
		}
		//	Close DB
		if(p_Conn == null)
			conn.closeDB(rs);
		//	Convert Array
		
		m_fields = new InfoField[columns.size()];
		columns.toArray(m_fields);
		Log.d("Size ", "- " + m_fields.length);
	}
	
	/**
	 * Return Field Array
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 21/02/2014, 16:25:29
	 * @return
	 * @return VOInfoField[]
	 */
	public InfoField[] getFields(){
		return m_fields;
	}
	
	/**
	 * Get Table Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 20:47:27
	 * @return
	 * @return String
	 */
	public String getTableName(){
		return TableName;
	}
	
	/**
	 * Get Key Column Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/03/2014, 12:04:31
	 * @return
	 * @return String
	 */
	public String getTableKeyName(){
		return TableName + "_ID";
	}
	
	/**
	 * Get AD_Element
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:36:22
	 * @param index
	 * @return
	 * @return int
	 */
	public int getAD_Element_ID(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].AD_Element_ID;
	}
	
	/**
	 * Get Display Type
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:37:35
	 * @param index
	 * @return
	 * @return int
	 */
	public int getDisplayType(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].DisplayType;
	}
	
	/**
	 * Get Reference Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:39:00
	 * @param index
	 * @return
	 * @return int
	 */
	public int getAD_Reference_Value_ID(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].AD_Reference_Value_ID;
	}
	
	/**
	 * Get Validation
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:39:11
	 * @param index
	 * @return
	 * @return int
	 */
	public int AD_Val_Rule_ID(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].AD_Val_Rule_ID;
	}
	
	/**
	 * Get Call Out
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:39:21
	 * @param index
	 * @return
	 * @return String
	 */
	public String getCallout(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].Callout;
	}
	
	/**
	 * Get Column Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:41:37
	 * @param index
	 * @return
	 * @return String
	 */
	public String getColumnName(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].ColumnName;
	}
	
	/**
	 * Get Column SQL
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:41:56
	 * @param index
	 * @return
	 * @return String
	 */
	public String getColumnSQL(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].ColumnSQL;
	}
	
	public String getDefaultValue(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].DefaultValue;
	}
	
	/**
	 * Get Entity Type
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:42:05
	 * @param index
	 * @return
	 * @return String
	 */
	public String getEntityType(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].EntityType;
	}
	
	/**
	 * Get Field Lenght
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:42:12
	 * @param index
	 * @return
	 * @return int
	 */
	public int getFieldLength(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].FieldLength;
	}
	
	/**
	 * Get Formatt Pattern
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:45:52
	 * @param index
	 * @return
	 * @return String
	 */
	public String getFormatPattern(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].FormatPattern;
	}
	
	/**
	 * Get Always Updateable
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:46:03
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isAlwaysUpdateable(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsAlwaysUpdateable;
	}
	
	/**
	 * Is Centrally Maintained
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:46:18
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isCentrallyMaintained(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsCentrallyMaintained;
	}
	
	/**
	 * Is Encrypted
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:46:33
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isEncrypted(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsEncrypted;
	}
	
	/**
	 * Is Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:46:40
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isIdentifier(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsIdentifier;
	}
	
	/**
	 * Is Selection Column
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:52:58
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isSelectionColumn(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsSelectionColumn;
	}
	
	/**
	 * Is Key
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:53:08
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isKey(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsKey;
	}
	
	/**
	 * Is Mandatory
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:53:14
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isMandatory(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsMandatory;
	}
	
	/**
	 * Is Parent Link Column
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:53:20
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isParent(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsParent;
	}
	
	/**
	 * Is Updateable
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:53:34
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isUpdateable(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsUpdateable;
	}
	
	/**
	 * Is Active
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:53:44
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isActive(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsActive;
	}
	
	/**
	 * Is Displayed
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:53:53
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isDisplayed(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsDisplayed;
	}
	
	/**
	 * Is Read Only
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:54:03
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isReadOnly(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsReadOnly;
	}
	
	/**
	 * Is Same Line
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:54:11
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isSameLine(int index) {
		if(m_fields == null || index <= 0)
			return false;
		return m_fields[index].IsSameLine;
	}
	
	/**
	 * Get Selection Column Sequence
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:54:20
	 * @param index
	 * @return
	 * @return int
	 */
	public int getSelectionSeqNo(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].SelectionSeqNo;
	}
	
	/**
	 * Get Sequence
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:54:30
	 * @param index
	 * @return
	 * @return int
	 */
	public int getSeqNo(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].SeqNo;
	}
	
	/**
	 * Get SFA Column Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:54:36
	 * @param index
	 * @return
	 * @return int
	 */
	public int getSFA_Column_ID(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].SFA_Column_ID;
	}
	
	/**
	 * Get SFA Table Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:54:57
	 * @param index
	 * @return
	 * @return int
	 */
	public int getSFA_Table_ID(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].SFA_Table_ID;
	}
	
	/**
	 * Get Max Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:55:15
	 * @param index
	 * @return
	 * @return String
	 */
	public String getValueMax(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].ValueMax;
	}
	
	/**
	 * Get Min Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:55:30
	 * @param index
	 * @return
	 * @return String
	 */
	public String getValueMin(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].ValueMin;
	}
	
	/**
	 * Get View Formatt
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:55:48
	 * @param index
	 * @return
	 * @return String
	 */
	public String getVFormat(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].VFormat;
	}
	
	/**
	 * Get Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:55:57
	 * @param index
	 * @return
	 * @return String
	 */
	public String getName(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].Name;
	}
	
	/**
	 * Get Description
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:59:31
	 * @param index
	 * @return
	 * @return String
	 */
	public String getDescription(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].Description;
	}
	
	/**
	 * Get Help
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:59:40
	 * @param index
	 * @return
	 * @return String
	 */
	public String getHelp(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].Help;
	}
	
	/**
	 * Get Field Group Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 12:59:53
	 * @param index
	 * @return
	 * @return int
	 */
	public int getAD_FieldGroup_ID(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].AD_FieldGroup_ID;
	}
	
	/**
	 * Get Display Type
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 13:00:10
	 * @param index
	 * @return
	 * @return String
	 */
	public String getDisplayLogic(int index) {
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index].DisplayLogic;
	}
	
	/**
	 * Get Field Sequence
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 13:00:19
	 * @param index
	 * @return
	 * @return int
	 */
	public int getFieldSeqNo(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].FieldSeqNo;
	}
	
	/**
	 * Get SFA Field Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 13:00:30
	 * @param index
	 * @return
	 * @return int
	 */
	public int getSFA_Field_ID(int index) {
		if(m_fields == null || index <= 0)
			return 0;
		return m_fields[index].SFA_Field_ID;
	}
	
	/**
	 * Get Lenght
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 13:18:38
	 * @return
	 * @return int
	 */
	public int getLength(){
		if(m_fields == null)
			return 0;
		return m_fields.length;
	}
	
	/**
	 * Get Field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/02/2014, 13:22:52
	 * @param index
	 * @return
	 * @return VOInfoField
	 */
	public InfoField getField(int index){
		if(m_fields == null || index <= 0)
			return null;
		return m_fields[index];
	}
	
	/**
	 * Get field from column identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/03/2014, 18:11:04
	 * @param SFA_Column_ID
	 * @return
	 * @return VOInfoField
	 */
	public InfoField getFieldFromColumn(int SFA_Column_ID){
		if(m_fields == null || SFA_Column_ID <= 0)
			return null;
		//	Get from column
		for(InfoField field : m_fields){
			if(field.SFA_Column_ID == SFA_Column_ID)
				return field;
		}
		return null;
	}
	
	/**
	 * Get Parent fields
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/03/2014, 17:39:23
	 * @param reQuery
	 * @return
	 * @return VOInfoField[]
	 */
	public InfoField [] getParentField(boolean reQuery){
		if(reQuery 
				|| m_parentFields == null) {
			ArrayList<InfoField> parentFields = new ArrayList<InfoField>();
			//	Loop
			for(InfoField field : m_fields){
				if(field.IsParent)
					parentFields.add(field);
			}
			m_parentFields = new InfoField[parentFields.size()];
			parentFields.toArray(m_parentFields);
		}
		//	Return cache
		return m_parentFields;
	}
	
	/**
	 * Get Criteria
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/04/2014, 20:53:10
	 * @param ctx
	 * @param m_WindowNo
	 * @param m_Parent_TabNo
	 * @return
	 * @return FilterValue
	 */
	public FilterValue getCriteria(Context ctx, int m_WindowNo, int m_Parent_TabNo){
		//	Cache
		if(filterValue != null)
			return filterValue;
    	//	Load
		InfoField [] parentFields = null;
    	//	Is Parent by Tab
    	if(getParent_Column_ID() != 0)
    		parentFields = new InfoField[]{getFieldFromColumn(getParent_Column_ID())};
    	else
    		parentFields = getParentField(false);
    	//	
    	StringBuffer sqlWhere = new StringBuffer();
    	//	
    	filterValue = new FilterValue();
    	for(InfoField field : parentFields){
    		if(sqlWhere.length() > 0)
    			sqlWhere.append(" AND ");
    		//	Add Criteria Column Filter
    		sqlWhere.append(field.ColumnName).append(" = ?");
    		//	Add Value
    		filterValue.addValue(DisplayType.getContextValue(ctx, 
    				m_WindowNo, m_Parent_TabNo, field));
    	}
    	//	Set Where
    	filterValue.setWhereClause(sqlWhere.toString());
    	return filterValue;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InfoTab [m_fields=" + Arrays.toString(m_fields) + ", TabName="
				+ TabName + ", SFA_Tab_ID=" + SFA_Tab_ID + ", SFA_Table_ID="
				+ SFA_Table_ID + ", TableName=" + TableName
				+ ", SFA_Window_ID=" + SFA_Window_ID + ", AD_Process_ID="
				+ AD_Process_ID + ", Classname=" + Classname
				+ ", TabDescription=" + TabDescription + ", TabHelp=" + TabHelp
				+ ", TabIsInsertRecord=" + TabIsInsertRecord
				+ ", TabIsReadOnly=" + TabIsReadOnly + ", OrderByClause="
				+ OrderByClause + ", Parent_Column_ID=" + Parent_Column_ID
				+ ", TabSFA_Column_ID=" + TabSFA_Column_ID + ", TabSeqNo="
				+ TabSeqNo + ", TabLevel=" + TabLevel + ", WhereClause="
				+ WhereClause + ", filterValue=" + filterValue
				+ ", m_parentFields=" + Arrays.toString(m_parentFields) + "]";
	}
}
