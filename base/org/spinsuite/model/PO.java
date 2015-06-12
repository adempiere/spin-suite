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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.content.Context;
import android.database.Cursor;


/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
/**
* @contributor Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com
*  	<li>Add Support to Log for Mobile
*  	@see https://adempiere.atlassian.net/browse/SPIN-6
**/
public abstract class PO {
	
	/** Context                 	*/
	private Context					m_ctx 				= null;
	/** Model Info              	*/
	protected POInfo				m_TableInfo 		= null;
	/** Original Values         	*/
	private Object[]    			m_currentValues 	= null;
	/** New Values              	*/
	private Object[]    			m_OldValues 		= null;
	/**	Connection					*/
	protected DB 					conn 				= null;  
	/** Record_IDs          		*/
	private Object[]       			m_IDs 				= new Object[] {I_ZERO};
	/** Key Columns					*/
	private String[]         		m_KeyColumns 		= null;
	/** Record_IDs Old          	*/
	private Object[]       			m_OldIDs 			= new Object[] {I_ZERO};
	/** Key Columns Old				*/
	private String[]         		m_OldKeyColumns 	= null;
	/** Create New for Multi Key 	*/
	private boolean					isNew 				= true;
	/**	Handle Connection			*/
	private boolean					handConnection 		= true;
	/**	Log Error					*/
	private String					error 				= null;
	/** NULL Value					*/
	public static final String		NULL 				= "NULL";
	/** Zero Integer				*/
	protected static final Integer 	I_ZERO 				= Integer.valueOf(0);
	/**	Is Synchronization			*/
	private boolean					isSynchronization 	= false;
	/**	For Skip Column				*/
	private static String [] 		SKIP_COLUMN			= new String[]{"DocumentNo", "Value"};
	/**	Prefix for Document No		*/
	private static String 			DOCUMENT_NO_PREFIX	= "<";
	/**	Suffix for Document No		*/
	private static String 			DOCUMENT_NO_SUFFIX	= ">";
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 07/07/2012, 23:59:07
	 * @param ctx
	 * @param con_tx
	 * @param tableName
	 * @param ID
	 * @param rs
	 * @param m_AD_Table_ID
	 * @param tableName
	 */
	private PO(Context ctx, String tableName, int [] ID, Cursor rs, DB pConn) {
		//	
		if (ctx == null)
			throw new IllegalArgumentException ("No Context");
		m_ctx = ctx;
		if(pConn != null) {
			conn = pConn;
			handConnection = false;
		} else {
			conn = new DB(ctx);
			handConnection = true;
		}
		//	Load Connection
		loadConnection(DB.READ_ONLY);
		if(tableName != null
				&& tableName.length() > 0) {
			//	get Table ID
			int m_SPS_Table_ID = MSPSTable.getSPS_Table_ID(ctx, tableName, get_Connection());
			//	
			if(m_SPS_Table_ID > 0)
				m_TableInfo = POInfo.getPOInfo (ctx, m_SPS_Table_ID, get_Connection());
		} else {
			//	Load PO Meta-Data
			m_TableInfo = initPO(ctx);
		}
		//	Close
		closeConnection();
		
		if (m_TableInfo == null || m_TableInfo.getTableName() == null)
			throw new IllegalArgumentException ("Invalid PO Info - " + m_TableInfo);
		//
		int size = m_TableInfo.getColumnCount();
		m_currentValues = new Object[size];
		m_OldValues = new Object[size];

		if (rs != null) {
			loadData(rs);
		}else{
			loadData(ID);
		}
	} 
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/02/2014, 21:48:07
	 * @param ctx
	 * @param ID
	 * @param conn
	 */
	public PO(Context ctx, int ID, DB conn) {
		this(ctx, null, new int[]{ID}, null, conn);
	}
	
	/**
	 * Used for Generic PO
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/10/2014, 13:00:43
	 * @param ctx
	 * @param tableName
	 * @param ID
	 * @param conn
	 */
	public PO(Context ctx, String tableName, int ID, DB conn) {
		this(ctx, tableName, new int[]{ID}, null, conn);
	}
	
	/**
	 * Used for Generic PO
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/10/2014, 20:19:03
	 * @param ctx
	 * @param tableName
	 * @param rs
	 * @param conn
	 */
	public PO(Context ctx, String tableName, Cursor rs, DB conn) {
		this(ctx, tableName, new int[]{0}, rs, conn);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/02/2014, 21:36:15
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public PO(Context ctx, Cursor rs, DB conn) {
		this(ctx, null, new int[]{0}, rs, conn);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/10/2014, 23:58:11
	 * @param ctx
	 * @param ID
	 * @param conn
	 */
	public PO(Context ctx, int []ID, DB conn) {
		this(ctx, null, ID, null, conn);
	}
	
	/**
	 *  Initialize and return PO_Info
	 *  @param ctx context
	 *  @return POInfo
	 */
	protected abstract POInfo initPO (Context ctx);
	
	/**
	 *  Return Single Key Record ID
	 *  @return ID or 0
	 */
	public int get_ID() {
		Object oo = m_IDs[0];
		if (oo != null && oo instanceof Integer)
			return ((Integer)oo).intValue();
		return 0;
	}   //  getID

	/**
	 * Get Array IDs
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 13:38:16
	 * @return
	 * @return int[]
	 */
	public int[] getIDs() {
		int[] retValue= new int[m_IDs.length];
		int len = 0;
		
		for(int i=0;i<m_IDs.length;i++){
			if (m_IDs[i] instanceof Integer){
				retValue[len] = (Integer)m_IDs[i];
				len++;
			}
		}
		int newRetValue[] = new int[len];
		System.arraycopy(retValue, 0, newRetValue, 0, len);
		return newRetValue;
	}
	
	
	/**
	 * Load data from Cursor
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 06/02/2014, 11:12:35
	 * @param rs
	 * @return void
	 */
	private void loadData(Cursor rs) {
		if(rs != null) {
			//	Load Data
			loadDataQuery(rs, true);
			reloadKey();
		}
	}
	
	/**
	 * Get Key Columns
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 13:49:31
	 * @return
	 * @return String[]
	 */
	public String[] getKeyColumns() {
		return m_KeyColumns;
	}
	
	/**
	 * Load data from ID
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/03/2012, 01:32:45
	 * @param ID
	 * @return void
	 */
	public boolean loadData(int... ID) {
		return loadData(ID, null);
	}
	
	/**
	 * Load Data from ID and Key Columns
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 12:07:37
	 * @param ID
	 * @param KeyColumns
	 * @return
	 * @return boolean
	 */
	public boolean loadData(int [] ID, String [] KeyColumns) {
		boolean ok = false;
		LogM.log(getCtx(), getClass(), Level.FINE, "loadData=" + String.valueOf(ID));
		if(ID != null
				&& ID[0] > 0)  {//&& Integer.parseInt(ID[0].toString()) > 0)) {
			//	Verify Inconsistent
			if(m_IDs == null 
					|| m_IDs.length != ID.length) {
				m_IDs = new Object[ID.length];
			}
			//	Copy
			for(int i = 0; i < ID.length; i++) {
				m_IDs[i] = ID[i];
			}
			//	
			m_KeyColumns = KeyColumns;
			if(m_KeyColumns == null
					|| m_KeyColumns.length == 0)
				m_KeyColumns = m_TableInfo.getKeyColumns();
			ok = loadDataQuery();
		} else {
			isNew = true;
			ok = loadDefaultValues();
			setAD_Client_ID(Env.getAD_Client_ID());
			setAD_Org_ID(Env.getAD_Org_ID());
		}
		return ok;
	}
	
	/**
	 * Get Current ID
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 06/02/2014, 11:51:23
	 * @return
	 * @return int
	 */
	public int getID() {
		return get_ID();
	}
	
	/**
	 * Copy values from temporal array, optional delete old values 
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/05/2012, 04:50:51
	 * @param deleteOld
	 * @return void
	 */
	public void copyValues(boolean deleteOld) {
		m_OldValues = m_currentValues;
		if(deleteOld) {
			isNew = true;
			m_currentValues = new Object[m_TableInfo.getColumnCount()];
			m_OldIDs = m_IDs;
			m_OldKeyColumns = m_KeyColumns;
		}
	}
	
	/**
	 * back to previous copy
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/05/2012, 04:46:14
	 * @return void
	 */
	public void backCopy() {
		this.isNew = false;
		m_currentValues = m_OldValues;
		m_IDs = m_OldIDs;
		m_KeyColumns = m_OldKeyColumns;
	}
	
	/**
	 * Get is New
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/05/2012, 05:00:52
	 * @return
	 * @return boolean
	 */
	public boolean isNew() {
		return isNew;
	}

	/**
	 * Has Primary Key
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/10/2014, 4:53:45
	 * @return
	 * @return boolean
	 */
	public boolean hasPrimaryKey() {
		return m_TableInfo.hasPrimaryKey();
	}
	
	/**
	 * Load data from meta-data
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/03/2012, 01:34:39
	 * @param ID
	 * @return boolean
	 */
	private boolean loadDataQuery() {
		boolean ok = false;
		StringBuffer sql = new StringBuffer("SELECT ");
		for(int i = 0; i < m_TableInfo.getColumnCount(); i++) {
			POInfoColumn column = m_TableInfo.getPOInfoColumn(i);
			if (i != 0)
				sql.append(",");
			//	
			if(!column.isColumnSQL())
				sql.append(column.ColumnName);
			else
				sql.append(Env.parseContext(column.ColumnSQL, false));
		}
		sql.append(" FROM ");
		sql.append(m_TableInfo.getTableName());
		sql.append(" WHERE ");
		sql.append(get_WhereClause(true));
		
		LogM.log(getCtx(), getClass(), Level.FINE, "loadDataQuery SQL=" + sql.toString());
		
		//	info
		LogM.log(getCtx(), getClass(), Level.FINE, "loadDataQuery().sql=" + sql.toString());
		//
		Cursor rs = null;
		//	Load Connection
		loadConnection(DB.READ_ONLY);
		
		rs = conn.querySQL(sql.toString(), null);
		//	Load From Cursor
		ok = loadDataQuery(rs, false);
		//	Close Connection
		closeConnection();
		return ok;
	}
	
	/**
	 * Load from Cursor
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 15/02/2014, 12:12:01
	 * @param rs
	 * @param isCursor
	 * @return
	 * @return boolean
	 */
	private boolean loadDataQuery(Cursor rs, boolean isCursor) {
		boolean ok = false;
		if(!isCursor 
				&& !rs.moveToFirst())
			return ok;
		//	For Parse Date
		SimpleDateFormat sdf = DisplayType.getTimestampFormat_Default();
		//	Iterate
		for(int i = 0; i < m_TableInfo.getColumnCount(); i++) {
			//	Get Column
			POInfoColumn column = m_TableInfo.getPOInfoColumn(i);
			int displayType = column.DisplayType;
			int index = i;
			//	Get Index
			if(isCursor)
				 index = rs.getColumnIndex(column.ColumnName);
			if(index == -1)
				continue;
			//	
			if (DisplayType.isText(displayType)
					|| displayType == DisplayType.LIST
					|| displayType == DisplayType.BUTTON)						
				m_currentValues[i] = rs.getString(index);
			else if (DisplayType.isID(displayType) 
					|| displayType == DisplayType.INTEGER)
				m_currentValues[i] = rs.getInt(index);
			else if(DisplayType.isBigDecimal(displayType))
				m_currentValues[i] = DisplayType.getNumber(rs.getString(index));
			else if (DisplayType.isLOB(displayType))
				m_currentValues[i] = rs.getBlob(index);
			else if(DisplayType.isBoolean(displayType)) {
				String value = rs.getString(index);
				m_currentValues[i] = (value != null && value.equals("Y"));
			} else if(DisplayType.isDate(displayType)) {
				String date = rs.getString(i);
				if(date != null) {
					try {
						m_currentValues[i] = sdf.parse(date);
					} catch (ParseException e) {
						m_currentValues[i] = null;
						LogM.log(getCtx(), getClass(), Level.SEVERE, "Parse Error", e);
					}
				} else { 
					m_currentValues[i] = null;
				}
				
			}
			//Carlos Parada Add Support to Log for Mobile
			m_OldValues[i] = DisplayType.getJDBC_Value(column.DisplayType, m_currentValues[i], false, false
					//2015-03-26 Carlos Parada Add ColumnName
					,column.ColumnName);
					//End Carlos Parada
			//End Carlos Parada
			
			//	Set Is Ok
			if(!ok) {
				isNew = false;
				ok = true;
			}
			LogM.log(getCtx(), getClass(), Level.FINE, "Old Value=" + m_OldValues[i] + " New Value=" + m_currentValues[i]);	
		}
		return ok;
	}
	
	/**
	 * Load Default Values
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 07/05/2014, 09:13:13
	 * @return
	 * @return boolean
	 */
	protected boolean loadDefaultValues() {
		boolean ok = false;
		try {
			//	Iterate
			for(int i = 0; i < m_TableInfo.getColumnCount(); i++) {
				//	Get Column
				POInfoColumn column = m_TableInfo.getPOInfoColumn(i);
				m_currentValues[i] = parseValue(column, i, false, false);
				//Carlos Parada Add Support to Log for Mobile 
				m_OldValues[i] = m_currentValues[i]; 
				//End Carlos Parada
				LogM.log(getCtx(), getClass(), Level.FINE, "Old Value=" + m_OldValues[i]);	
			}
			//	Set Ok Value
			ok = true;
		} catch(Exception e) {
			LogM.log(getCtx(), getClass(), Level.SEVERE, "Error: " + e.getLocalizedMessage(), e);
		}
		return ok;
	}
	
	/**
	 * Set ID and mark this like update
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 23/05/2012, 03:38:12
	 * @param ID
	 * @return void
	 */
	public void setIDUpdate(Object ID) {
		//if(ID > 0) {
		if(ID != null){
			m_IDs = new Object[] {ID};
			m_OldIDs = m_IDs;
			m_KeyColumns = m_TableInfo.getKeyColumns();
			isNew = false;
		}
	}
	
	/**
	 * Reload Key Columns
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/10/2014, 19:38:27
	 * @return void
	 */
	private void reloadKey() {
		m_KeyColumns = m_TableInfo.getKeyColumns();
		m_IDs = new Object[m_KeyColumns.length];
		//	
		for (int i = 0; i < m_KeyColumns.length; i++) {
			m_IDs[i] = get_Value(m_KeyColumns[i]);
		}
	}
	
	/**
	 * 	Create Single/Multi Key Where Clause
	 * 	@param withValues if true uses actual values otherwise ?
	 *  @param reloadKey
	 * 	@return where clause
	 */
	protected String get_WhereClause (boolean withValues, boolean reloadKey) {
		StringBuffer sb = new StringBuffer();
		//	Reload Key
		if(reloadKey)
			reloadKey();
		//	
		for (int i = 0; i < m_IDs.length; i++)
		{
			if (i != 0)
				sb.append(" AND ");
			sb.append(m_KeyColumns[i]).append("=");
			if (withValues)
			{
				if (m_KeyColumns[i].endsWith("_ID"))
					sb.append(m_IDs[i]);
				else
					sb.append("'").append(m_IDs[i]).append("'");
			}
			else
				sb.append("?");
		}
		return sb.toString();
	}	//	getWhereClause
	
	/**
	 * Get Where clause
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/10/2014, 19:17:14
	 * @param withValues
	 * @return
	 * @return String
	 */
	protected String get_WhereClause (boolean withValues) {
		return get_WhereClause(withValues, false);
	}
	
	/**
	 * Get values from where clause
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/05/2012, 02:44:17
	 * @return
	 * @return String
	 */
	protected String [] get_WhereClauseValues() {
		String[] values = new String [m_IDs.length];
		for (int i = 0; i < m_IDs.length; i++) {
			values[i] = String.valueOf(m_IDs[i]);
		}
		return values;
	}	//	getWhereClauseValues
	
	/**
	 * Set Value with columnName
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/03/2012, 00:22:52
	 * @param columnName
	 * @param value
	 * @return
	 * @return boolean
	 */
	public final boolean set_Value(String columnName, Object value) {
		int index = m_TableInfo.getColumnIndex(columnName);
		if(index >= 0) {
			if(value != null)
				m_currentValues[index] = value;
			else
				m_currentValues[index] = null;
			return true;
		} else 
			LogM.log(getCtx(), getClass(), Level.FINE, "columnName = " + columnName + ", value = " + value + " Not Found");
		return false;
	}
	
	/**
	 * Get Column Index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/02/2014, 22:09:48
	 * @param columnName
	 * @return
	 * @return int
	 */
	public final int getColumnIndex(String columnName) {
		return m_TableInfo.getColumnIndex(columnName);
	}
	
	/**
	 * Set value with index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/04/2012, 21:05:26
	 * @param index
	 * @param value
	 * @return
	 * @return boolean
	 */
	public final boolean set_Value(int index, Object value) {
		LogM.log(getCtx(), getClass(), Level.FINE, "index = " + index + ", value = " + value);
		if(index >= 0) {
			if(value != null)
				m_currentValues[index] = value;
			else
				m_currentValues[index] = null;
			return true;
		} else 
			LogM.log(getCtx(), getClass(), Level.FINE, "value = " + value + " Column Not Found");
		return false;
	}
	
	/**
	 * Get value as String
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 30/03/2012, 19:03:40
	 * @return
	 * @return String[]
	 */
	public String [] getValuesAsString() {
		String []values = null;
		if(m_currentValues != null) {
			values = new String[m_currentValues.length];
			for (int i = 0; i < m_currentValues.length; i++) {
				values[i] = (String)m_currentValues[i];
			}
		}
		return values;
	}
	
	/**
	 * Get value with columnName
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/03/2012, 00:22:56
	 * @param columnName
	 * @return
	 * @return Object
	 */
	public final Object get_Value(String columnName) {
		int index = m_TableInfo.getColumnIndex(columnName);
		LogM.log(getCtx(), getClass(), Level.FINE, "columnName = " + columnName);
		return get_Value(index);
	}
	
	/**
	 *  Is Value Changed
	 *  @param index index
	 *  @return true if changed
	 */
	public final boolean is_ValueChanged (int index) {
		if (index < 0 || index >= m_TableInfo.getColumnCount()) {
			LogM.log(getCtx(), getClass(), Level.WARNING, "Index invalid - " + index);
			return false;
		}
		if (m_currentValues[index] == null)
			return false;
		return !m_currentValues[index].equals(m_OldValues[index]);
	}   //  is_ValueChanged

	/**
	 *  Is Value Changed
	 *  @param columnName column name
	 *  @return true if changed
	 */
	public final boolean is_ValueChanged (String columnName) {
		int index = getColumnIndex(columnName);
		if (index < 0) {
			LogM.log(getCtx(), getClass(), Level.WARNING, "Column not found - " + columnName);
			return false;
		}
		return is_ValueChanged (index);
	}   //  is_ValueChanged
	
	/**
	 * Get value with index
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/05/2012, 18:32:38
	 * @param index
	 * @return
	 * @return Object
	 */
	public final Object get_Value(int index) {
		if(index >= 0) {
			LogM.log(getCtx(), getClass(), Level.FINE, "Value = " + m_currentValues[index]);
			return m_currentValues[index];
		}
		return null;
	}
	
	/**
	 * Get value as integer
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 30/03/2012, 00:26:31
	 * @param columnName
	 * @return
	 * @return int
	 */
	public final int get_ValueAsInt(String columnName) {
		return get_ValueAsInt(columnName, m_currentValues);
	}
	
	/**
	 * Get previous value as integer
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/08/2012, 23:23:17
	 * @param columnName
	 * @return
	 * @return int
	 */
	public final int get_ValueOldAsInt(String columnName) {
		return get_ValueAsInt(columnName, m_OldValues);
	}
	
	/**
	 * Get value as integer with columnName
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/08/2012, 23:22:35
	 * @param columnName
	 * @param m_arrayValues
	 * @return
	 * @return int
	 */
	private int get_ValueAsInt(String columnName, Object [] m_arrayValues) {
		int index = m_TableInfo.getColumnIndex(columnName);
		int displayType = m_TableInfo.getDisplayType(index);
		if(index >= 0) {
			if(m_arrayValues[index] != null) {
				if(DisplayType.isNumeric(displayType) 
						|| DisplayType.isID(displayType)) {
					return (Integer)m_arrayValues[index];
				} else {
					return 0;
				}
			}
		}
		return 0;
	}
	
	/**
	 * Get value with index as integer
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/05/2012, 02:13:50
	 * @param index
	 * @return
	 * @return int
	 */
	public final int get_ValueAsInt(int index) {
		int displayType = m_TableInfo.getDisplayType(index);
		if(index >= 0) {
			if(m_currentValues[index] != null) {
				if(DisplayType.isNumeric(displayType) 
						|| DisplayType.isID(displayType)) {
					Object value = m_currentValues[index];
					if(value != null) {
						try{
							return (Integer)value;
						} catch (Exception e) {
							LogM.log(getCtx(), getClass(), Level.SEVERE, " Error:", e);
						}
					}
				} else {
					return 0;
				}	
			}
		}
		return 0;
	}
	
	/**
	 * Get value as boolean with columnName
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 30/03/2012, 00:28:37
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public final boolean get_ValueAsBoolean(String columnName) {
		int index = m_TableInfo.getColumnIndex(columnName);
		LogM.log(getCtx(), getClass(), Level.FINE, "columnName = " + columnName);
		if(index >= 0) {
			LogM.log(getCtx(), getClass(), Level.FINE, "Value = " + m_OldValues[index]);
			if(DisplayType.isText(m_TableInfo.getDisplayType(columnName))) {
				return "Y".equals(((String)m_currentValues[index]));
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Save data
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 29/03/2012, 23:45:04
	 * @return
	 * @return boolean
	 */
	public boolean save() {
		try {
			saveEx();
			error = null;
			return true;
		} catch (Exception e) {
			error = e.getLocalizedMessage();
			LogM.log(getCtx(), getClass(), Level.SEVERE, "Error: ", e);
		}
		return false;
	}
	
	/**
	 * Save data with Exception
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/04/2012, 20:22:37
	 * @throws Exception
	 * @return void
	 */
	public void saveEx() throws Exception {
		try {
			loadConnection(DB.READ_WRITE);
			boolean fine = beforeSave(isNew);
			if(!fine)
				throw new Exception("@saveEx.beforeSave@: " + getError());
			//	Set Default Values
			setLogValues(isNew);
			if(isNew)
				saveNew();
			else
				saveUpdate();
			//	Close Connection
			closeConnection();
			//	
			fine = afterSave(isNew);
			
			if(!fine)
				throw new Exception("@saveEx.afterSave@: " + getError());
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection();
		}		
	}
	
	/**
	 * Delete record
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/05/2012, 02:06:03
	 * @return
	 * @return boolean
	 */
	public boolean delete() {
		try{
			deleteEx();
			error = null;
			return true;
		} catch (Exception e) {
			error = e.getLocalizedMessage();
			LogM.log(getCtx(), getClass(), Level.SEVERE, " Error ", e);
		}
		return false;
	}
	
	/**
	 * Delete record with exception
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/05/2012, 02:04:13
	 * @throws Exception
	 * @return void
	 */
	public void deleteEx() throws Exception {
		try{
			//	Valid Is Deleteable
			if(!isDeleteable())
				throw new Exception("Cannot Delete Record (Is Not Deleteable)");
			
			loadConnection(DB.READ_WRITE);
			//	Before Delete
			boolean fine = beforeDelete();
			if(!fine)
				throw new Exception("@Error@ " + getError());
			//	
			conn.deleteSQL(m_TableInfo.getTableName(), get_WhereClause(false, true), get_WhereClauseValues());
			
			MSession session = MSession.get (getCtx(), false);
			if (session == null)
				LogM.log(getCtx(), PO.class, Level.FINE, "No Session found");
			
			if( m_TableInfo.IsChangeLog())
			{
				//	Change Log
				if (session != null && m_IDs.length == 1)
				{
					int SPS_ChangeLog_ID = 0;
					int size = m_TableInfo.getColumnCount();
					for (int i = 0; i < size; i++)
					{
						POInfoColumn column = m_TableInfo.getPOInfoColumn(i);
						Object value = m_OldValues[i];
						if (value != null
							&& column.IsAllowLogging		//	logging allowed
							&& !column.IsEncrypted		//	not encrypted
							&& !"Password".equals(column.ColumnName)
							)
						{
							// change log on delete
							MSPSChangeLog cLog = session.changeLog (
								conn, SPS_ChangeLog_ID,
								m_TableInfo.getSPS_Table_ID(), column.SPS_Column_ID,
								m_IDs[0], getAD_Client_ID(), getAD_Org_ID(), value, null, MSPSChangeLog.EVENTCHANGELOG_Delete);
							if (cLog != null)
								SPS_ChangeLog_ID = cLog.getSPS_ChangeLog_ID();
						}
					}	//   for all fields
				}
				
				
				if (conn == null)
					LogM.log(getCtx(), PO.class, Level.FINE, "complete");
				else
					LogM.log(getCtx(), PO.class, Level.FINE, "[" + conn.toString() + "] - complete");
			}
			//2015-03-13 Carlos Parada Add Sync Record 
			if (MSession.logMigration(this, m_TableInfo))
				createSyncRecord(MSPSSyncTable.EVENTCHANGELOG_Delete,m_IDs[0]);
			//End Carlos Parada 
			//	Housekeeping
			m_IDs[0] = PO.I_ZERO;
			
			if(handConnection)
				conn.setTransactionSuccessful();
			
			//	
			clear(true);
			LogM.log(getCtx(), getClass(), Level.FINE, (String)m_OldValues[0]);
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection();
		}
	}
	
	/**
	 * Clean array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/05/2012, 03:26:13
	 * @param deleteBackup
	 * @return void
	 */
	public void clear(boolean deleteBackup) {
		isNew = true;
		m_OldIDs = m_IDs;
		m_OldKeyColumns = m_KeyColumns;
		m_IDs = new Object[]{I_ZERO};
		int size = m_TableInfo.getColumnCount();
		m_currentValues = new Object[size];
		//	Load default Values
		loadDefaultValues();
		//	
		if(deleteBackup) {
			m_OldIDs = new Object[]{I_ZERO};
			m_OldValues = new Object[size];
		}
	}
	
	
	/**
	 * Save a new record.... use INSERT SQL
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 30/03/2012, 22:10:05
	 * @return
	 * @return boolean
	 * @throws Exception 
	 */
	private boolean saveNew() throws Exception {
		StringBuffer columns = new StringBuffer();
		StringBuffer sym = new StringBuffer();
		ArrayList<Object> listValues = new ArrayList<Object>();
		Object value = null;
		MSession session = MSession.get (getCtx(), false);
		int SPS_ChangeLog_ID = 0;
		if (session == null)
			LogM.log(getCtx(), PO.class, Level.FINE, "No Session found");
		//	
		try{
			for (int i = 0; i < m_TableInfo.getColumnCount(); i++) {
				POInfoColumn column = m_TableInfo.getPOInfoColumn(i);
				if(!column.isColumnSQL()) {
					if(i > 0) {
						columns.append(",");
						sym.append(",");
					}
					columns.append(column.ColumnName);
					sym.append("?");
					//	
					value = parseValue(column, i, true, true);
					
					if(column.IsMandatory
							&& !isSkipColumn(column.ColumnName)
							&& value == null)
						throw new Exception(Msg.getMsg(getCtx(), "@MustFillField@") + 
								" \"@" + column.ColumnName + "@\"");
					listValues.add(value);
					LogM.log(getCtx(), getClass(), Level.FINE, column.ColumnName + "=" + value + " Mandatory=" + column.IsMandatory);
				}
				//Carlos Parada Add Support to Log for Mobile
				if (   session != null
						&& m_IDs.length == 1
						&& m_TableInfo.isAllowLogging(i)		//	logging allowed
						&& !column.IsEncrypted		//	not encrypted
						&& !"Password".equals(column.ColumnName)
						&& MSession.logMigration(this, m_TableInfo)
						&& !isSynchronization()
						)
				{
					// change log on new
					MSPSChangeLog cLog = session.changeLog (
							conn, SPS_ChangeLog_ID,
							m_TableInfo.getSPS_Table_ID(), column.SPS_Column_ID,
							get_ID(), getAD_Client_ID(), getAD_Org_ID(), null, value, MSPSChangeLog.EVENTCHANGELOG_Insert);
					if (cLog != null)
						SPS_ChangeLog_ID = cLog.getSPS_ChangeLog_ID();
				}
				//End Carlos Parada
			}
			String sql = "INSERT INTO " + 
					m_TableInfo.getTableName() + 
					" (" + 
					columns.toString() + 
					") VALUES(" + 
					sym.toString() + 
					")";
			conn.executeSQLEx(sql, listValues.toArray());
			
			//2015-03-13 Carlos Parada Add Sync Record 
			if (MSession.logMigration(this, m_TableInfo))
				createSyncRecord(MSPSSyncTable.EVENTCHANGELOG_Insert,m_IDs[0]);
			//End Carlos Parada 
			
			if(handConnection)
				conn.setTransactionSuccessful();
			//	Reload Key
			reloadKey();
			isNew = false;
		} catch (Exception e) {
			throw e;
		}
		//	
		return false;
	}
	
	/**
	 * Update a record.... use UPDATE SQL
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/05/2012, 21:11:58
	 * @return
	 * @throws Exception
	 * @return boolean
	 */
	private boolean saveUpdate() throws Exception {
		StringBuffer columns = new StringBuffer();
		ArrayList<Object> listValues = new ArrayList<Object>();
		//Carlos Parada Add Support to Log for Mobile
		MSession session = MSession.get (getCtx(), false);
		if (session == null)
			LogM.log(getCtx(), PO.class, Level.FINE, "No Session found");
		
		int SPS_ChangeLog_ID = 0;
		//End Carlos Parada
		//	
		try{
			for (int i = 0; i < m_TableInfo.getColumnCount(); i++) {
				POInfoColumn column = m_TableInfo.getPOInfoColumn(i);
				//	Valid SQL or Default Columns
				if(column.isColumnSQL()
						|| column.ColumnName.equals("Created")
						|| column.ColumnName.equals("CreatedBy"))
					continue;
				//	Do it
				if(i > 0)
					columns.append(",");
				//	
				columns.append(column.ColumnName)
						.append("=")
						.append("?");
				//	
				Object value = parseValue(column, i, false, true);
				if(column.IsMandatory 
						&& !isSkipColumn(column.ColumnName)
						&& value == null)
					throw new Exception(Msg.getMsg(getCtx(), "MustFillField") + 
							" \"@" + column.ColumnName + "@\"");
				if(!column.ColumnName.equals("Created")
						&& !column.ColumnName.equals("CreatedBy")) {
					listValues.add(value);
					LogM.log(getCtx(), getClass(), Level.FINE, 
							column.ColumnName + "=" + value + " Mandatory=" + column.IsMandatory);
				}
				
				//Carlos Parada Add Support to Log for Mobile
				if (session != null
						&& m_IDs.length == 1
						&& column.IsAllowLogging		//	logging allowed
						&& !column.IsEncrypted		//	not encrypted
						&& !"Password".equals(column.ColumnName)
						&& !isSynchronization()
						)
					{
						Object oldV = m_OldValues[i];
						Object newV = value;
						if (oldV != null && oldV == NULL )
							oldV = null;
						if (newV != null && newV == NULL)
							newV = null;
						// change log on update
						MSPSChangeLog cLog = session.changeLog (
							conn, SPS_ChangeLog_ID,
							m_TableInfo.getSPS_Table_ID(), column.SPS_Column_ID,
							get_ID(), getAD_Client_ID(), getAD_Org_ID(), oldV, newV, MSPSChangeLog.EVENTCHANGELOG_Update);
						if (cLog != null)
							SPS_ChangeLog_ID = cLog.getSPS_ChangeLog_ID();
					}
				//End Carlos Parada
			}
			String sql = "UPDATE " + 
					m_TableInfo.getTableName() + 
					" SET " + 
					columns.toString() +
					" WHERE "  + get_WhereClause(true, true);
			
			conn.executeSQLEx(sql, listValues.toArray());
			//	
			//2015-03-13 Carlos Parada Add Sync Record 
			if (MSession.logMigration(this, m_TableInfo))
				createSyncRecord(MSPSSyncTable.EVENTCHANGELOG_Update,m_IDs[0]);
			//End Carlos Parada 
			if(handConnection && conn.inTransaction())
				conn.setTransactionSuccessful();
		} catch (Exception e) {
			throw e;
		}
		//	
		return false;
	}
	
	/**
	 * Set Column Values default
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/02/2014, 23:03:03
	 * @param isNew
	 * @return void
	 */
	private void setLogValues(boolean isNew) {
		int m_AD_User_ID = Env.getAD_User_ID();
		Date currentDate = Env.getCurrentDate();
		if(isNew) {
			setCreatedBy(m_AD_User_ID);
			setCreated(Env.getCurrentDate());
		}
		setUpdatedBy(m_AD_User_ID);
		setUpdated(currentDate);
		
	}
	
	/**
	 * Get value for insert record
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/05/2012, 21:13:26
	 * @param column
	 * @param index
	 * @param isNew
	 * @param toSave
	 * @return
	 * @throws Exception
	 * @return Object
	 */
	public final Object parseValue(POInfoColumn column, int index, boolean isNew, boolean toSave) throws Exception {
		if(index >= 0) {
			Object value = m_currentValues[index]; 
			if(isNew) {
				if(column.ColumnName.equals(m_TableInfo.getTableName() + "_ID")) {
					Integer ID = (Integer) value;
					//	2015-06-12 Dixon Martinez
					//	Check is Is Synchronization
					if (( ID != null && ID > 0) 
							|| isSynchronization())
						m_IDs = new Object[]{ID};
					//	End Dixon Martinez
					else
						m_IDs = new Object[]{MSequence.getNextID(m_ctx, getAD_Client_ID(), getTableName(), conn)};
					//	Set ID
					set_Value(index, (m_IDs[0] != null && m_IDs[0] instanceof Integer ? ((Integer)m_IDs[0]).intValue(): m_IDs[0]));
					return m_IDs[0];
				} else if(column.ColumnName.equals("DocumentNo")
						&& (value == null 
								|| ((String)value).startsWith(DOCUMENT_NO_PREFIX) 
										&& ((String)value).endsWith(DOCUMENT_NO_SUFFIX))) {
						//	Get Document Type
						int m_C_DocType_ID = get_ValueAsInt("C_DocType_ID");
						//	Target Document
						if(m_C_DocType_ID == 0)
							m_C_DocType_ID = get_ValueAsInt("C_DocTypeTarget_ID");

						//2015-05-16 Dixon Martinez Bad Code
						//Get Document No
						String documentNo = MSequence.getDocumentNo(getCtx(), m_C_DocType_ID, m_TableInfo.getTableName(), toSave, conn);
						//	Add Prefix
						if(documentNo != null
								&& !toSave) {
							documentNo = DOCUMENT_NO_PREFIX + documentNo + DOCUMENT_NO_SUFFIX;
						}
						//End Dixon Martinez
						return documentNo;
				} else if(value != null) {
					Object returnValue = DisplayType.getJDBC_Value(column.DisplayType, value, !toSave, !toSave
							//2015-03-26 Carlos Parada Add ColumnName
							,column.ColumnName
							//End Carlos Parada
							);
					return returnValue;
				} else if(column.DefaultValue != null) {
					if(toSave)
						return Env.parseContext((String)column.DefaultValue, false);
					else
						return DisplayType.parseValue(
										Env.parseContext((String)column.DefaultValue, false)
										, column.DisplayType);
				} else
					return null;
			} else{
				if(column.ColumnName.equals("DocumentNo")
						&& (value == null 
						|| ((String)value).startsWith(DOCUMENT_NO_PREFIX) 
								&& ((String)value).endsWith(DOCUMENT_NO_SUFFIX))) {
					//	Get Document Type
					int m_C_DocType_ID = get_ValueAsInt("C_DocType_ID");
					//	Target Document
					if(m_C_DocType_ID == 0)
						m_C_DocType_ID = get_ValueAsInt("C_DocTypeTarget_ID");
					
					//2015-05-16 Dixon Martinez Bad Code
					//Get Document No
					String documentNo = MSequence.getDocumentNo(getCtx(), m_C_DocType_ID, m_TableInfo.getTableName(), toSave, conn);
					//	Add Prefix
					if(documentNo != null
							&& !toSave) {
						documentNo = DOCUMENT_NO_PREFIX + documentNo + DOCUMENT_NO_SUFFIX;
					}
					//End Dixon Martinez
					return documentNo;
				} else if(value != null) {
					Object returnValue = DisplayType.getJDBC_Value(column.DisplayType, value, !toSave, !toSave
							//2015-03-26 Carlos Parada Add ColumnName
							,column.ColumnName
							//End Carlos Parada
							);
					return returnValue;
				} else if(column.DefaultValue != null) {
					if(toSave)
						return Env.parseContext((String)column.DefaultValue, false);
					else
						return DisplayType.parseValue(
										Env.parseContext((String)column.DefaultValue, false)
										, column.DisplayType);
				} else
					return null;
			}
		}
		return null;
	}
	
	/**
	 * Trigger before save
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/03/2012, 23:10:35
	 * @param isNew
	 * @return
	 * @return boolean
	 */
	protected boolean beforeSave(boolean isNew) {
		return true;
	}
	
	/**
	 * Trigger after save
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 29/03/2012, 23:40:55
	 * @param isNew
	 * @return
	 * @return boolean
	 */
	protected boolean afterSave(boolean isNew) {
		return true;
	}
	
	/**
	 * Trigger before delete
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 27/03/2012, 23:10:40
	 * @return
	 * @return boolean
	 */
	protected boolean beforeDelete() {
		return true;
	}
	
	/**
	 * Trigger after delete
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 29/03/2012, 23:42:51
	 * @return
	 * @return boolean
	 */
	protected boolean afterDelete() {
		return true;
	}
	
	/**
	 *  Initialize and return PO_Info
	 *  @param ctxInto context
	 *  @return int
	 */
	public int getSPS_Table_ID() {
		return m_TableInfo.getSPS_Table_ID();
	}
	
	/**
	 * Get table name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 15/06/2012, 18:01:39
	 * @return
	 * @return String
	 */
	protected String getTableName() {
		return m_TableInfo.getTableName();
	}
	
	/**
	 * Create a new connection to DB
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/05/2012, 17:57:15
	 * @return void
	 */
	public void loadConnection(int type) {
		if(!conn.isOpen()) {
			if(handConnection) {
				LogM.log(getCtx(), getClass(), Level.FINE, "handConnection");
				conn.openDB(type);
				if(type == DB.READ_WRITE)
					conn.beginTransaction();
			}
		}
    }
	
	/**
	 * Close Connection to DB
	 * maneja conexion esta en true
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/07/2012, 18:13:44
	 * @return void
	 */
	public void closeConnection() {
		LogM.log(getCtx(), getClass(), Level.FINE, "Close");
		if(conn.isOpen()) {
			if(handConnection) {
				LogM.log(getCtx(), getClass(), Level.FINE, "handConnection");
				if(conn.getDB().inTransaction())
					conn.endTransaction();
				conn.close();
			}
		}
    }
	
	/**
	 * Get Current Connection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/02/2014, 22:10:28
	 * @return
	 * @return DB
	 */
	public DB get_Connection() {
		return conn;
	}
	
	/**
	 * Get Error Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/07/2012, 20:35:15
	 * @return
	 * @return String
	 */
	public String getError() {
		return error;
	}
	
	/**
	 * Set Error
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 1/11/2014, 13:26:07
	 * @param error
	 * @return void
	 */
	protected void setError(String error) {
		this.error = error;
	}
	
	/**************************************************************************
	 * 	Set AD_Client
	 * 	@param AD_Client_ID client
	 */
	public final void setAD_Client_ID (int AD_Client_ID) {
		set_Value("AD_Client_ID", AD_Client_ID);
	}	//	setAD_Client_ID

	/**
	 * 	Get AD_Client
	 * 	@return AD_Client_ID
	 */
	public final int getAD_Client_ID()
	{
		Integer ii = (Integer)get_Value("AD_Client_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}	//	getAD_Client_ID

	/**
	 * 	Set AD_Org
	 * 	@param AD_Org_ID org
	 */
	public final void setAD_Org_ID (int AD_Org_ID) {
		set_Value("AD_Org_ID", AD_Org_ID);
	}	//	setAD_Org_ID

	/**
	 * 	Get AD_Org
	 * 	@return AD_Org_ID
	 */
	public int getAD_Org_ID() {
		Integer ii = (Integer)get_Value("AD_Org_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}	//	getAD_Org_ID

	/**
	 * 	Overwrite Client Org if different
	 *	@param AD_Client_ID client
	 *	@param AD_Org_ID org
	 */
	protected void setClientOrg (int AD_Client_ID, int AD_Org_ID) {
		if (AD_Client_ID != getAD_Client_ID())
			setAD_Client_ID(AD_Client_ID);
		if (AD_Org_ID != getAD_Org_ID())
			setAD_Org_ID(AD_Org_ID);
	}	//	setClientOrg

	/**
	 * 	Overwrite Client Org if different
	 *	@param po persistent object
	 */
	protected void setClientOrg (PO po) {
		setClientOrg(po.getAD_Client_ID(), po.getAD_Org_ID());
	}	//	setClientOrg

	/**
	 * 	Set Active
	 * 	@param active active
	 */
	public final void setIsActive (boolean active) {
		set_Value("IsActive", active);
	}	//	setActive

	/**
	 *	Is Active
	 *  @return is active
	 */
	public final boolean isActive() {
		Boolean bb = (Boolean)get_Value("IsActive");
		if (bb != null)
			return bb.booleanValue();
		return false;
	}	//	isActive

	/**
	 * 	Get Created
	 * 	@return created
	 */
	public final Date getCreated() {
		return (Date)get_Value("Created");
	}	//	getCreated

	/**
	 * 	Get Updated
	 *	@return updated
	 */
	public final Date getUpdated() {
		return (Date)get_Value("Updated");
	}	//	getUpdated

	/**
	 * 	Get CreatedBy
	 * 	@return AD_User_ID
	 */
	public final int getCreatedBy() {
		Integer ii = (Integer)get_Value("CreatedBy");
		if (ii == null)
			return 0;
		return ii.intValue();
	}	//	getCreateddBy

	/**
	 * 	Get UpdatedBy
	 * 	@return AD_User_ID
	 */
	public final int getUpdatedBy() {
		Integer ii = (Integer)get_Value("UpdatedBy");
		if (ii == null)
			return 0;
		return ii.intValue();
	}	//	getUpdatedBy

	/**
	 * 	Set UpdatedBy
	 * 	@param p_UpdateBy user
	 */
	protected final void setUpdatedBy (int p_UpdateBy) {
		set_Value("UpdatedBy", p_UpdateBy);
	}	//	setUpdatedBy

	/**
	 * 	Set CreatedBy
	 * 	@param p_CreatedBy user
	 */
	protected final void setCreatedBy (int p_CreatedBy) {
		set_Value("CreatedBy", p_CreatedBy);
	}	//	setCreatedBy
	
	/**
	 * 	Set Created
	 * 	@param created
	 */
	protected final void setCreated (Date created) {
		set_Value("Created", created);
	}	//	setCreated
	
	/**
	 * 	Set Updated
	 * 	@param updated
	 */
	protected final void setUpdated (Date updated) {
		set_Value("Updated", updated);
	}	//	setCreated
	
	/**
	 * Get Context from PO
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 15/02/2014, 14:05:30
	 * @return
	 * @return Context
	 */
	public Context getCtx() {
		return m_ctx;
	}
	
	@Override
	public String toString() {
		return "TableName=" + getTableName();
	}
	
	/**
	 * Get Is Deleteable Record
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/09/2014, 19:48:49
	 * @return
	 * @return boolean
	 */
	public boolean isDeleteable() {
		return m_TableInfo.isDeleteable();
	}

	/**
	 * 
	 * @author Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com 03/09/2014, 23:10:35
	 * @return void
	 * @throws Exception 
	 */
	private void createSyncRecord(String p_EventChangeLog,Object p_ID) throws Exception{
		//No Create Record When Synchronization
		if (isSynchronization())
			return;
		
		String whereClause = "SPS_Table_ID = " + getSPS_Table_ID() + " AND "
							+ "Record_ID = " + p_ID + " AND "
							+ "EventChangeLog IN ('" + X_SPS_SyncTable.EVENTCHANGELOG_Insert + "','"+X_SPS_SyncTable.EVENTCHANGELOG_Update + "') AND IsSynchronized='N'";
		
		MSPSSyncTable synctable = MSPSSyncTable.getSyncTable(getCtx(), conn, whereClause);
		
		if (synctable== null)
			synctable = new MSPSSyncTable(getCtx(), 0, conn);
		
		if (synctable.getSPS_SyncTable_ID()==0){
			//synctable.setRecord_ID(p_ID);
			synctable.set_Value("Record_ID", p_ID);
			synctable.setEventChangeLog(p_EventChangeLog);
			synctable.setSPS_Table_ID(getSPS_Table_ID());
			synctable.setIsSynchronized(false);
			synctable.save();
		}
		else{
			if (p_EventChangeLog.equals(X_SPS_SyncTable.EVENTCHANGELOG_Delete))
				synctable.deleteEx();
		}
	}

	/**
	 * 
	 * @author Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com 19/3/2015, 2:12:17
	 * @return
	 * @return boolean
	 */
	public boolean isSynchronization() {
		return isSynchronization;
	}
	
	/**
	 * 
	 * @author Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com 19/3/2015, 2:12:21
	 * @param isSynchronization
	 * @return void
	 */
	public void setSynchronization(boolean isSynchronization) {
		this.isSynchronization = isSynchronization;
	}
	
	/**
	 * Get Sync Value value with columnName
	 ** @author Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com 28/03/2012, 00:22:56
	 * @param columnName
	 * @return
	 * @return Object
	 */
	public final Object get_SyncValue(String columnName) {
		int displayType= m_TableInfo.getDisplayType(columnName);
		int index = m_TableInfo.getColumnIndex(columnName);
		LogM.log(getCtx(), getClass(), Level.FINE, "columnName = " + columnName);
		
		if (Arrays.binarySearch(new int[]{DisplayType.ID, DisplayType.TABLE, 
											DisplayType.TABLE_DIR, DisplayType.ACCOUNT,
											DisplayType.ROW_ID, DisplayType.COLOR, DisplayType.SEARCH,
											DisplayType.LOCATION, DisplayType.LOCATOR,
											DisplayType.ASSIGNMENT, DisplayType.PATTRIBUTE}, displayType)>=0){
			
			String tableName = columnName.replace("_ID", "");
			Object retValue = null;
			String sql ="SELECT SyncRecord_ID " + 
						"FROM " +
						"SPS_SyncTable " +  
						"WHERE "+ 
						"IsSynchronized = 'Y' " +
						"AND EventChangeLog = '"+ X_SPS_SyncTable.EVENTCHANGELOG_Insert + "'" + 
						"AND Record_ID =  " + get_Value(index) + " " +
						//"AND SPS_Table_ID = " + getSPS_Table_ID();
						"AND EXISTS (SELECT 1 FROM SPS_Table WHERE SPS_SyncTable.SPS_Table_ID = SPS_Table.SPS_Table_ID AND SPS_Table.TableName = '"+tableName+"')";
			retValue =DB.getSQLValueString(getCtx(), sql, conn, new String[]{});
			if (retValue == null)
				retValue = get_Value(index) ;
			if (retValue.equals(0))
				retValue = null; 
			return retValue;
		} else {
			return DisplayType.getJDBC_Value(displayType, get_Value(index), false, false, columnName);
		}
	}
	
	/**
	 * Get Hash Map
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> array = new HashMap<String, Object>();
		POInfoColumn[] m_ColumnNames = m_TableInfo.getInfoColumnArray();
		//	Put in Array
		for(int i = 0; i < m_ColumnNames.length; i++) {
			array.put(m_ColumnNames[i].Name, m_currentValues[i]);
		}
		//	Default Result
		return array;
	}
	
	/**
	 * Is a Skip column
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_ColumnName
	 * @return
	 * @return boolean
	 */
	public static boolean isSkipColumn(String p_ColumnName) {
		//	Valid Parameter
		if(p_ColumnName == null) {
			return false;
		}
		//	Verify
		for(String skipColumn : SKIP_COLUMN) {
			if(p_ColumnName.equals(skipColumn)) {
				return true;
			}
		}
		//	Default
		return false;
	}
}
