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
package org.spinsuite.model;

import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * @author Yamel Senih
 *
 */
/**
* @contributor Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com
*  	<li>Add Support to Log for Mobile
*  	@see https://adempiere.atlassian.net/browse/SPIN-6
**/
public class POInfo {
	
	/**
	 * Get PO Information
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/02/2014, 22:00:49
	 * @param ctx
	 * @param p_SPS_Table_ID
	 * @param conn
	 * @return
	 * @return POInfo
	 */
	public static POInfo getPOInfo(Context ctx, int p_SPS_Table_ID, DB conn) {
		POInfo retValue = new POInfo(ctx, p_SPS_Table_ID, conn);
		return retValue;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/02/2014, 22:00:32
	 * @param ctx
	 * @param AD_Table_ID
	 * @param conn
	 */
	public POInfo(Context ctx, int AD_Table_ID, DB conn) {
		loadInfoColumn(ctx, AD_Table_ID, null, conn);
	}
	
	/** Table_ID            	*/
	private int					m_SPS_Table_ID 		= 0;
	/** Table Name          	*/
	private String				m_TableName 		= null;
	/**	Is Deleteable Record	*/
	private boolean 			m_IsDeleteable 		= false;
	/** Columns             	*/
	private POInfoColumn[]		m_columns 			= null;
	/**	Count Column SQL		*/
	private int					m_CountColumnSQL 	= 0;
	/**	Key Column Names		*/
	private String[]			m_keyColumns 		= null;
	/**	Has Primary Key			*/
	private String 				hasPrimaryKey 		= null;
	/** Change Log*/
	private boolean 			m_IsChangeLog		= false;
	/**
	 * Load Column Information
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/02/2014, 21:54:17
	 * @param ctx
	 * @param AD_Table_ID
	 * @param tableName
	 * @return void
	 */
	private void loadInfoColumn(Context ctx, int AD_Table_ID, String tableName, DB p_Conn) {
		//	
		String language = Env.getAD_Language();
		boolean isBaseLanguage = Env.isBaseLanguage();
		//	
		StringBuffer sql = new StringBuffer();
		//	if Base Language
		if(isBaseLanguage) {
			sql.append("SELECT " +
					"t.SPS_Table_ID, " +
					"t.TableName, " +
					"t.IsDeleteable, " +
					"t.IsChangeLog, " +
					"c.AD_Element_ID, " +
					"c.AD_Reference_ID, " +
					"c.AD_Reference_Value_ID, " +
					"c.AD_Val_Rule_ID, " +
					"c.Callout, " +
					"c.ColumnName, " +
					"c.ColumnSQL, " +
					"c.DefaultValue, " +
					"c.Description, " +
					"c.EntityType, " +
					"c.FieldLength, " +
					"c.FormatPattern, " +
					"c.IsAlwaysUpdateable, " +
					"c.IsCentrallyMaintained, " +
					"c.IsEncrypted, " +
					"c.IsIdentifier, " +
					"c.IsKey, " +
					"c.IsMandatory, " +
					"c.IsParent, " +
					"c.IsSelectionColumn, " +
					"c.IsUpdateable, " +
					"c.Name, " +
					"c.SelectionSeqNo, " +
					"c.SeqNo, " +
					"c.SPS_Column_ID, " +
					"c.ValueMax, " +
					"c.ValueMin, " +
					"c.VFormat, " + 
					"c.InfoFactoryClass, " +
					"c.IsAllowLogging "
					);
			//	From
			sql.append("FROM SPS_Table t " +
					"INNER JOIN SPS_Column c ON(c.SPS_Table_ID = t.SPS_Table_ID) ");
		} else {
			sql.append("SELECT " +
					"t.SPS_Table_ID, " +
					"t.TableName, " +
					"t.IsDeleteable, " +
					"t.IsChangeLog, " +
					"c.AD_Element_ID, " +
					"c.AD_Reference_ID, " +
					"c.AD_Reference_Value_ID, " +
					"c.AD_Val_Rule_ID, " +
					"c.Callout, " +
					"c.ColumnName, " +
					"c.ColumnSQL, " +
					"c.DefaultValue, " +
					"c.Description, " +
					"c.EntityType, " +
					"c.FieldLength, " +
					"c.FormatPattern, " +
					"c.IsAlwaysUpdateable, " +
					"c.IsCentrallyMaintained, " +
					"c.IsEncrypted, " +
					"c.IsIdentifier, " +
					"c.IsKey, " +
					"c.IsMandatory, " +
					"c.IsParent, " +
					"c.IsSelectionColumn, " +
					"c.IsUpdateable, " +
					"COALESCE(ct.Name, c.Name) Name, " +
					"c.SelectionSeqNo, " +
					"c.SeqNo, " +
					"c.SPS_Column_ID, " +
					"c.ValueMax, " +
					"c.ValueMin, " +
					"c.VFormat, " + 
					"c.InfoFactoryClass, "+
					"c.IsAllowLogging ");
			//	From
			sql.append("FROM SPS_Table t " +
					"INNER JOIN SPS_Column c ON(c.SPS_Table_ID = t.SPS_Table_ID) " +
					"LEFT JOIN SPS_Column_Trl ct ON(ct.SPS_Column_ID = c.SPS_Column_ID AND ct.AD_Language = '").append(language).append("') ");
		}
		sql.append("WHERE c.IsActive = 'Y' ");
		if(AD_Table_ID != 0)
			sql.append("AND t.SPS_Table_ID = ").append(AD_Table_ID).append(" ");
		else
			sql.append("AND t.TableName = '").append(tableName).append("' ");
		//	Order By
		sql.append(" ORDER BY c.Name");
		
		LogM.log(ctx, getClass(), Level.FINE, "SQL TableInfo SQL:" + sql);
		
		ArrayList<POInfoColumn> columns = new ArrayList<POInfoColumn>();
		DB conn = null;
		if(p_Conn != null)
			conn = p_Conn;
		else
			conn = new DB(ctx);
		if(!conn.isOpen())
			conn.openDB(DB.READ_ONLY);
		Cursor rs = null;
		rs = conn.querySQL(sql.toString(), null);
		if(rs.moveToFirst()) {
			int i = 0;
			m_SPS_Table_ID 	= rs.getInt(i++);
			m_TableName 	= rs.getString(i++);
			m_IsDeleteable	= Env.booleanValue(rs.getString(i++));
			m_IsChangeLog = Env.booleanValue(rs.getString(i++));
			do{
				POInfoColumn iColumn = new POInfoColumn();
				iColumn.SPS_Table_ID = m_SPS_Table_ID;
				iColumn.AD_Element_ID = rs.getInt(i++);
				iColumn.DisplayType = rs.getInt(i++);
				iColumn.AD_Reference_Value_ID = rs.getInt(i++);
				iColumn.AD_Val_Rule_ID = rs.getInt(i++);
				iColumn.Callout = rs.getString(i++);
				iColumn.ColumnName = rs.getString(i++);
				iColumn.ColumnSQL = rs.getString(i++);
				iColumn.DefaultValue = rs.getString(i++);
				iColumn.Description = rs.getString(i++);
				iColumn.EntityType = rs.getString(i++);
				iColumn.FieldLength = rs.getInt(i++);
				iColumn.FormatPattern = rs.getString(i++);
				iColumn.IsAlwaysUpdateable = Env.booleanValue(rs.getString(i++));
				iColumn.IsCentrallyMaintained = Env.booleanValue(rs.getString(i++));
				iColumn.IsEncrypted= Env.booleanValue(rs.getString(i++));
				iColumn.IsIdentifier= Env.booleanValue(rs.getString(i++));
				iColumn.IsKey= Env.booleanValue(rs.getString(i++));
				iColumn.IsMandatory= Env.booleanValue(rs.getString(i++));
				iColumn.IsParent= Env.booleanValue(rs.getString(i++));
				iColumn.IsSelectionColumn= Env.booleanValue(rs.getString(i++));
				iColumn.IsUpdateable= Env.booleanValue(rs.getString(i++));
				iColumn.Name = rs.getString(i++);
				iColumn.SelectionSeqNo = rs.getInt(i++);
				iColumn.SeqNo = rs.getInt(i++);
				iColumn.SPS_Column_ID = rs.getInt(i++);
				iColumn.ValueMax = rs.getString(i++);
				iColumn.ValueMin = rs.getString(i++);
				iColumn.VFormat = rs.getString(i++);
				iColumn.InfoFactoryClass = rs.getString(i++);
				iColumn.IsAllowLogging = Env.booleanValue(rs.getString(i++));
				//Log.i("m_IsAlwaysUpdateable", " - " + m_ColumnName + " = " + m_IsAlwaysUpdateable);
				columns.add(iColumn);
				i = 4;
				//	Seek in Column SQL
				if(iColumn.isColumnSQL())
					m_CountColumnSQL ++;
			}while(rs.moveToNext());
		}
		//	Close DB
		if(p_Conn == null)
			conn.closeDB(rs);
		//	Convert Array
		
		m_columns = new POInfoColumn[columns.size()];
		columns.toArray(m_columns);
		Log.d("Size ", "- " + m_columns.length);
	}
	
	/**
	 * Has Primary Key
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/10/2014, 4:53:17
	 * @return
	 * @return boolean
	 */
	public boolean hasPrimaryKey() {
		//	Cache
		if(hasPrimaryKey != null)
			return hasPrimaryKey.equals("Y");
		//	
		for(POInfoColumn columns : m_columns) {
			if(columns.IsKey) {
				hasPrimaryKey = "Y";
				return true;
			}
		}
		//	Default
		hasPrimaryKey = "N";
		return false;
	}
	
	/**
	 * Get Table Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/02/2012, 23:42:37
	 * @return
	 * @return String
	 */
	public String getTableName() {
		return m_TableName;
	}
	
	/**
	 * Verify if is deleteable record
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 07/06/2012, 09:27:31
	 * @return
	 * @return boolean
	 */
	public boolean isDeleteable() {
		return m_IsDeleteable;
	}
	
	/**
	 * Get Spin-Suite Table Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/02/2012, 23:43:36
	 * @return
	 * @return int
	 */
	public int getSPS_Table_ID() {
		return m_SPS_Table_ID;
	}
	
	/**
	 * Get Array Length
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 00:27:09
	 * @return
	 * @return int
	 */
	public int getColumnCount() {
		if(m_columns != null) {
			return m_columns.length;
		}
		return 0;
	}
	
	/**
	 * Get SQL Columns Quantity
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/07/2012, 11:36:52
	 * @return
	 * @return int
	 */
	public int getCountColumnSQL() {
		return m_CountColumnSQL;
	}
	
	/**
	 * Get Column Name from Index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 00:34:43
	 * @param index
	 * @return
	 * @return String
	 */
	public String getColumnName(int index) {
		if(m_columns != null || index < m_columns.length) {
			return m_columns[index].ColumnName;
		}
		return null;
	}
	
	/**
	 * Get Columns to Insert
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 29/03/2012, 23:58:58
	 * @return
	 * @return String
	 */
	public String getColumnsInsert() {
		StringBuffer columns = new StringBuffer();
		if(m_columns != null) {
			for (int i = 0; i < m_columns.length; i++) {
				if(i > 0)
					columns.append(",");
				columns.append(m_columns[i].ColumnName);
			}	
		}
		return columns.toString();
	}
	
	
	/**
	 * Get AD Column Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/10/2014, 20:33:59
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getAD_Column_ID(String columnName) {
		if(m_columns != null) {
			for (int i = 0; i < m_columns.length; i++) {
				if(m_columns[i].ColumnName.equals(columnName))
					return m_columns[i].AD_Column_ID;
			}	
		}
		return -1;
	}
	
	/**
	 * Get Column Index from Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 00:50:45
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getColumnIndex(String columnName) {
		columnName = columnName.trim();
		if(m_columns != null) {
			for (int i = 0; i < m_columns.length; i++) {
				if(m_columns[i].ColumnName != null 
						&& m_columns[i].ColumnName.equals(columnName)) {
					return i;
				}
			}	
		}
		return -1;
	}
	
	/**
	 * Get Key Columns
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/10/2014, 23:53:23
	 * @return
	 * @return String[]
	 */
	public String[] getKeyColumns() {
		//	Cache
		if(m_keyColumns != null)
			return m_keyColumns;
		//	
		boolean isKeyTable = false;
		if(m_columns != null) {
			ArrayList<String> keyColumns = new ArrayList<String>();
			for (POInfoColumn column : m_columns) {
				if(column.IsParent) {
					keyColumns.add(column.ColumnName);
				} else if(column.IsKey) {
					isKeyTable = true;
					break;
				}
			}
			//	
			if(keyColumns.size() != 0
					&& !isKeyTable) {
				String [] columns = new String[keyColumns.size()];
				keyColumns.toArray(columns);
				m_keyColumns = columns;
				return columns;
			}
		}
		//	Default ID
		if (getColumnIndex(getTableName() + "_ID")!=-1)
			m_keyColumns = new String [] {getTableName() + "_ID"};
		return m_keyColumns;
	}
	
	/**
	 * Is Callout
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/04/2012, 18:44:09
	 * @param index
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isCallout(int index) {
		if(m_columns != null && index >= 0) {
			if(m_columns[index].Callout != null 
					&& m_columns[index].Callout.length() != 0)
				return true;
		}
		return false;
	}
	
	/**
	 * Get SQL Column from Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 00:55:03
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getColumnSQL(String columnName) {
		int index = getColumnIndex(columnName);
		if(index >= 0) {
			return m_columns[index].ColumnSQL;
		}
		return null;
	}
	
	/**
	 * Get SQL Column from Index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/07/2012, 17:55:45
	 * @param index
	 * @return
	 * @return String
	 */
	public String getColumnSQL(int index) {
		if (index < 0 || index >= m_columns.length)
			return null;
		if (m_columns[index].ColumnSQL != null && m_columns[index].ColumnSQL.length() > 0)
			return m_columns[index].ColumnSQL + " AS " + m_columns[index].ColumnName;
		return m_columns[index].ColumnName;
	}
	
	/**
	 * Get Display Type from Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 01:01:40
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getDisplayType(String columnName) {
		int index = getColumnIndex(columnName);
		if(index >= 0) {
			return m_columns[index].DisplayType;
		}
		return 0;
	}
	
	/**
	 * Get Display Type from index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 30/03/2012, 22:36:55
	 * @param index
	 * @return
	 * @return int
	 */
	public int getDisplayType(int index) {
		if(index >= 0) {
			return m_columns[index].DisplayType;
		}
		return 0;
	}
	
	/**
	 * Get Is Manadory Property from name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 01:02:45
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isMandatory(String columnName) {
		int index = getColumnIndex(columnName);
		if(index >= 0) {
			return m_columns[index].IsMandatory;
		}
		return false;
	}
	
	/**
	 * Get Is Mandatory Property from name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/04/2012, 20:58:03
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isMandatory(int index) {
		if(index >= 0) {
			return m_columns[index].IsMandatory;
		}
		return false;
	}
	
	/**
	 * Get Default Value from Column Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 01:04:19
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getDefaultValue(String columnName) {
		int index = getColumnIndex(columnName);
		if(index >= 0) {
			return m_columns[index].DefaultValue;
		}
		return null;
	}
	
	/**
	 * Get Default value from index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 30/03/2012, 23:42:02
	 * @param index
	 * @return
	 * @return String
	 */
	public String getDefaultValue(int index) {
		if(index >= 0) {
			return m_columns[index].DefaultValue;
		}
		return null;
	}
	
	/**
	 * Get Is Updateable
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 01:05:24
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isUpdateable(String columnName) {
		int index = getColumnIndex(columnName);
		if(index >= 0) {
			return m_columns[index].IsUpdateable;
		}
		return false;
	}
	
	/**
	 * Get Is Updateable from Index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/10/2014, 20:40:02
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isUpdateable(int index) {
		if(index >= 0) {
			return m_columns[index].IsUpdateable;
		}
		return false;
	}
	
	/**
	 * Get Is Always Updateable from Column Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/10/2014, 20:40:26
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isAlwaysUpdateable(String columnName) {
		int index = getColumnIndex(columnName);
		if(index >= 0) {
			return m_columns[index].IsAlwaysUpdateable;
		}
		return false;
	}
	
	/**
	 * Get Is Updateable from Index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/05/2012, 09:25:03
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isAlwaysUpdateable(int index) {
		if(index >= 0) {
			return m_columns[index].IsAlwaysUpdateable;
		}
		return false;
	}
	
	/**
	 * Get Name from Column Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 01:06:27
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getName(String columnName) {
		int index = getColumnIndex(columnName);
		if(index >= 0) {
			return m_columns[index].Name;
		}
		return null;
	}
	
	/**
	 * Get Name from Index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/04/2012, 18:22:05
	 * @param index
	 * @return
	 * @return String
	 */
	public String getName(int index) {
		if(index >= 0) {
			return m_columns[index].Name;
		}
		return null;
	}
	
	/**
	 * Get Is Encrypted from Column Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 01:08:04
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isEncrypted(String columnName) {
		int index = getColumnIndex(columnName);
		if(index >= 0) {
			return m_columns[index].IsEncrypted;
		}
		return false;
	}
	
	/**
	 * Get Field Length
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 01:08:52
	 * @param columnName
	 * @return
	 * @return int
	 */
	public int getFieldLength(String columnName) {
		int index = getColumnIndex(columnName);
		if(index >= 0) {
			return m_columns[index].FieldLength;
		}
		return -1;
	}
	
	/**
	 * Get Minimum Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 01:09:37
	 * @param columName
	 * @return
	 * @return String
	 */
	public String getValueMin(String columName) {
		int index = getColumnIndex(columName);
		if(index >= 0) {
			return m_columns[index].ValueMin;
		}
		return null;
	}
	
	/**
	 * Get Is Column SQL from column Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/07/2012, 17:47:53
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public boolean isColumnSQL(String columnName) {
		int index = getColumnIndex(columnName);
		if(index >= 0) {
			return m_columns[index].isColumnSQL();
		}
		return false;
	}
	
	/**
	 * Get Is Column SQL from index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/07/2012, 17:47:53
	 * @param index
	 * @return
	 * @return boolean
	 */
	public boolean isColumnSQL(int index) {
		if(index >= 0) {
			return m_columns[index].isColumnSQL();
		}
		return false;
	}
	
	
	/**
	 * Get Max Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2012, 01:10:09
	 * @param columnName
	 * @return
	 * @return String
	 */
	public String getValueMax(String columnName) {
		int index = getColumnIndex(columnName);
		if(index >= 0) {
			return m_columns[index].ValueMax;
		}
		return null;
	}
	
	/**
	 * Get Column  Name for Select
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/10/2014, 20:47:03
	 * @param ctx
	 * @param infoColumn
	 * @param index
	 * @return
	 * @return String
	 */
	public static String getColumnNameForSelect(Context ctx, POInfo infoColumn, int index) {
    	String value = "''";
    	if(!infoColumn.isColumnSQL(index)) {
    		value = infoColumn.getTableName() + "." + infoColumn.getColumnName(index);
    	} else {
    		//	Parse SQL Column
    		String columnSQL = Env.parseContext(infoColumn.getColumnSQL(index), false);
    		value = columnSQL;
    	}
    	
    	return value;
    }
	
	/**
	 * Get PO Info Column
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2014, 19:35:36
	 * @param columnName
	 * @return
	 * @return POInfoColumn
	 */
	public POInfoColumn getPOInfoColumn(String columnName) {
		int index = getColumnIndex(columnName);
		if(index >= 0) {
			return m_columns[index];
		}
		return null;
	}
	
	/**
	 * Get PO Info Column
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/02/2014, 19:36:41
	 * @param index
	 * @return
	 * @return POInfoColumn
	 */
	public POInfoColumn getPOInfoColumn(int index) {
		if(index >= 0) {
			return m_columns[index];
		}
		return null;
	}
	
	/**
	 * Build select clause
	 * @return stringbuffer
	 */
	public StringBuffer buildSelect() {
		StringBuffer sql = new StringBuffer("SELECT ");
		int size = getColumnCount();
		for (int i = 0; i < size; i++) {
			if (i != 0)
				sql.append(",");
			sql.append(getColumnSQL(i));	//	Normal and Virtual Column
		}
		sql.append(" FROM ").append(getTableName());
		return sql;
	}
	
	/**
	 * Add Support to Log for Mobile 
	 * @param index
	 *            index
	 * @return true if column is allowed to be logged
	 */
	public boolean isAllowLogging(int index) {
		if (index < 0 || index >= m_columns.length)
			return false;
		return m_columns[index].IsAllowLogging;
	} // isAllowLogging
	
	/**
	 * Add Support to Log for Mobile
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 2/3/2015, 18:43:31
	 * @return
	 * @return boolean
	 */
	public boolean IsChangeLog(){
		return m_IsChangeLog;
	}
	
	/**
	 * Get Array of Info Column
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return POInfoColumn[]
	 */
	public POInfoColumn [] getInfoColumnArray() {
		return m_columns;
	}
}
