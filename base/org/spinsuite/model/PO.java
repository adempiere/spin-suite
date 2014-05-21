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
package org.spinsuite.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;


/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public abstract class PO {

	/** Context                 	*/
	private Context					m_ctx;
	/** Model Info              	*/
	protected POInfo				m_TableInfo = null;
	/** Original Values         	*/
	private Object[]    			m_currentValues = null;
	/** New Values              	*/
	private Object[]    			m_oldValues = null;
	/**	Connection					*/
	protected DB 					conn = null;  
	/** Record_IDs          		*/
	private Object[]       			m_IDs = new Object[] {0};
	/** Key Columns					*/
	private String[]         		m_KeyColumns = null;
	/** Create New for Multi Key 	*/
	private boolean					isNew = true;
	/**	Deleted ID					*/
	private int						m_currentId = 0;
	/**	Old ID						*/
	private int						m_oldId = 0;
	/**	Handle Connection			*/
	private boolean					handConnection = true;
	/**	Log Error					*/
	private String					error = null;
	/**	Deleted ID					*/
	private int						m_idOld = 0;
	
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/07/2012, 23:59:07
	 * @param ctx
	 * @param con_tx
	 * @param ID
	 * @param rs
	 * @param m_AD_Table_ID
	 * @param tableName
	 */
	private PO(Context ctx, int ID, Cursor rs, DB pConn) {
		//	
		if (ctx == null)
			throw new IllegalArgumentException ("No Context");
		m_ctx = ctx;
		if(pConn != null){
			conn = pConn;
			handConnection = false;
		} else {
			conn = new DB(ctx);
			handConnection = true;
		}
		//	Load Connection
		loadConnection(DB.READ_ONLY);
		//	Load PO Meta-Data
		m_TableInfo = initPO(ctx);
		//	Close
		closeConnection();
		
		if (m_TableInfo == null || m_TableInfo.getTableName() == null)
			throw new IllegalArgumentException ("Invalid PO Info - " + m_TableInfo);
		//
		int size = m_TableInfo.getColumnLength();
		m_currentValues = new Object[size];
		m_oldValues = new Object[size];

		if (rs != null){
			loadData(rs);
		}else{
			loadData(ID);
		}
	} 
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 21:48:07
	 * @param ctx
	 * @param ID
	 * @param conn
	 */
	public PO(Context ctx, int ID, DB conn){
		this(ctx, ID, null, conn);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 21:36:15
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public PO(Context ctx, Cursor rs, DB conn){
		this(ctx, 0, rs, conn);
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
	 *  Return Deleted Single Key Record ID
	 *  @return ID or 0
	 */
	public int get_IDOld() {
		return m_idOld;
	}   //  getID
	
	/**
	 * Load data from Cursor
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 06/02/2014, 11:12:35
	 * @param rs
	 * @return void
	 */
	private void loadData(Cursor rs) {
		if(rs != null){
			//	Load Data
			loadDataQuery(rs, true);
			m_IDs = new Object[] {get_ValueAsInt(m_TableInfo.getTableName() + "_ID")};
			m_KeyColumns = new String[] {m_TableInfo.getTableName() + "_ID"};	
		}
	}
	
	/**
	 * Load data from ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2012, 01:32:45
	 * @param ID
	 * @return void
	 */
	public boolean loadData(int ID) {
		boolean ok = false;
		LogM.log(getCtx(), getClass(), Level.FINE, "loadData=" + String.valueOf(ID));
		if(ID > 0){
			m_IDs = new Object[] {ID};
			m_currentId = ID;
			m_KeyColumns = new String[] {m_TableInfo.getTableName() + "_ID"};
			ok = loadDataQuery(ID);
		} else {
			isNew = true;
			ok = loadDefaultValues();
			setAD_Client_ID(Env.getAD_Client_ID(getCtx()));
			setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		}
		return ok;
	}
	
	/**
	 * Get Current ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 06/02/2014, 11:51:23
	 * @return
	 * @return int
	 */
	public int getID() {
		return m_currentId;
	}
	
	/**
	 * Copy values from temporal array, optional delete old values 
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/05/2012, 04:50:51
	 * @param deleteOld
	 * @return void
	 */
	public void copyValues(boolean deleteOld) {
		m_oldValues = m_currentValues;
		if(deleteOld){
			isNew = true;
			m_currentValues = new Object[m_TableInfo.getColumnLength()];
			m_oldId = m_currentId;
		}
	}
	
	/**
	 * back to previous copy
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/05/2012, 04:46:14
	 * @return void
	 */
	public void backCopy() {
		this.isNew = false;
		m_currentValues = m_oldValues;
		m_currentId = m_oldId;
	}
	
	/**
	 * Get is New
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/05/2012, 05:00:52
	 * @return
	 * @return boolean
	 */
	public boolean isNew() {
		return isNew;
	}
	
	/**
	 * Load data from meta-data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2012, 01:34:39
	 * @param ID
	 * @return boolean
	 */
	private boolean loadDataQuery(int ID) {
		boolean ok = false;
		StringBuffer sql = new StringBuffer("SELECT ");
		for(int i = 0; i < m_TableInfo.getColumnLength(); i++){
			POInfoColumn column = m_TableInfo.getPOInfoColumn(i);
			if (i != 0)
				sql.append(",");
			if(!column.isColumnSQL()){
				//if(DisplayType.isDate(column.DisplayType))
					//sql.append("(strftime('%s', ").append(column.ColumnName).append(")*1000)");
				//else
					sql.append(column.ColumnName);
			}
			else
				sql.append(Env.parseContext(m_ctx, column.ColumnSQL, false));
		}
		sql.append(" FROM ");
		sql.append(m_TableInfo.getTableName());
		sql.append(" WHERE ");
		sql.append(get_WhereClause(true));
		
		LogM.log(getCtx(), getClass(), Level.FINE, "loadDataQuery SQL=" + sql.toString());
		
		//	info
		LogM.log(getCtx(), getClass(), Level.FINE, "loadDataQuery(ID).sql=" + sql.toString());
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 12:12:01
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
		for(int i = 0; i < m_TableInfo.getColumnLength(); i++){
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
			else if(DisplayType.isBoolean(displayType)){
				String value = rs.getString(index);
				m_currentValues[i] = (value != null && value.equals("Y"));
			} else if(DisplayType.isDate(displayType)){
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
			//	Set Is Ok
			if(!ok){
				isNew = false;
				ok = true;
			}
			LogM.log(getCtx(), getClass(), Level.FINE, "Old Value=" + m_oldValues[i] + " New Value=" + m_currentValues[i]);	
		}
		return ok;
	}
	
	/**
	 * Load Default Values
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/05/2014, 09:13:13
	 * @return
	 * @return boolean
	 */
	private boolean loadDefaultValues() {
		boolean ok = false;
		try {
			//	Iterate
			for(int i = 0; i < m_TableInfo.getColumnLength(); i++){
				//	Get Column
				POInfoColumn column = m_TableInfo.getPOInfoColumn(i);
				m_currentValues[i] = parseValue(column, i, false, false);
				//	
				LogM.log(getCtx(), getClass(), Level.FINE, "Old Value=" + m_oldValues[i]);	
			}
			//	Set Ok Value
			ok = true;
		} catch(Exception e){
			LogM.log(getCtx(), getClass(), Level.SEVERE, "Error: " + e.getMessage(), e);
		}
		return ok;
	}
	
	/**
	 * Set ID and mark this like update
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/05/2012, 03:38:12
	 * @param ID
	 * @return void
	 */
	public void setIDUpdate(int ID) {
		if(ID > 0){
			m_IDs = new Object[] {ID};
			m_currentId = ID;
			m_oldId = m_currentId;
			m_KeyColumns = new String[] {m_TableInfo.getTableName() + "_ID"};
			isNew = false;
		}
	}
	
	/**
	 * 	Create Single/Multi Key Where Clause
	 * 	@param withValues if true uses actual values otherwise ?
	 * 	@return where clause
	 */
	protected String get_WhereClause (boolean withValues) {
		StringBuffer sb = new StringBuffer();
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
	 * Get values from where clause
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/05/2012, 02:44:17
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2012, 00:22:52
	 * @param columnName
	 * @param value
	 * @return
	 * @return boolean
	 */
	public final boolean set_Value(String columnName, Object value){
		int index = m_TableInfo.getColumnIndex(columnName);
		if(index >= 0){
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 22:09:48
	 * @param columnName
	 * @return
	 * @return int
	 */
	public final int getColumnIndex(String columnName) {
		return m_TableInfo.getColumnIndex(columnName);
	}
	
	/**
	 * Set value with index
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/04/2012, 21:05:26
	 * @param index
	 * @param value
	 * @return
	 * @return boolean
	 */
	public final boolean set_Value(int index, Object value) {
		LogM.log(getCtx(), getClass(), Level.FINE, "index = " + index + ", value = " + value);
		if(index >= 0){
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 30/03/2012, 19:03:40
	 * @return
	 * @return String[]
	 */
	public String [] getValuesAsString(){
		String []values = null;
		if(m_currentValues != null){
			values = new String[m_currentValues.length];
			for (int i = 0; i < m_currentValues.length; i++) {
				values[i] = (String)m_currentValues[i];
			}
		}
		return values;
	}
	
	/**
	 * Get value with columnName
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/03/2012, 00:22:56
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
	 * Get value with index
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/05/2012, 18:32:38
	 * @param index
	 * @return
	 * @return Object
	 */
	public final Object get_Value(int index) {
		if(index >= 0){
			LogM.log(getCtx(), getClass(), Level.FINE, "Value = " + m_currentValues[index]);
			return m_currentValues[index];
		}
		return null;
	}
	
	/**
	 * Get value as integer
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 30/03/2012, 00:26:31
	 * @param columnName
	 * @return
	 * @return int
	 */
	public final int get_ValueAsInt(String columnName) {
		return get_ValueAsInt(columnName, m_currentValues);
	}
	
	/**
	 * Get previous value as integer
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/08/2012, 23:23:17
	 * @param columnName
	 * @return
	 * @return int
	 */
	public final int get_OldValueAsInt(String columnName) {
		return get_ValueAsInt(columnName, m_oldValues);
	}
	
	/**
	 * Get value as integer with columnName
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/08/2012, 23:22:35
	 * @param columnName
	 * @param m_arrayValues
	 * @return
	 * @return int
	 */
	private int get_ValueAsInt(String columnName, Object [] m_arrayValues) {
		int index = m_TableInfo.getColumnIndex(columnName);
		int displayType = m_TableInfo.getDisplayType(index);
		if(index >= 0){
			if(m_arrayValues[index] != null){
				if(DisplayType.isNumeric(displayType) 
						|| DisplayType.isID(displayType)){
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 20/05/2012, 02:13:50
	 * @param index
	 * @return
	 * @return int
	 */
	public final int get_ValueAsInt(int index) {
		int displayType = m_TableInfo.getDisplayType(index);
		if(index >= 0){
			if(m_currentValues[index] != null){
				if(DisplayType.isNumeric(displayType) 
						|| DisplayType.isID(displayType)){
					Object value = m_currentValues[index];
					if(value != null){
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 30/03/2012, 00:28:37
	 * @param columnName
	 * @return
	 * @return boolean
	 */
	public final boolean get_ValueAsBoolean(String columnName) {
		int index = m_TableInfo.getColumnIndex(columnName);
		LogM.log(getCtx(), getClass(), Level.FINE, "columnName = " + columnName);
		if(index >= 0){
			LogM.log(getCtx(), getClass(), Level.FINE, "Value = " + m_oldValues[index]);
			if(DisplayType.isText(m_TableInfo.getDisplayType(columnName))){
				return "Y".equals(((String)m_currentValues[index]));
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Save data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2012, 23:45:04
	 * @return
	 * @return boolean
	 */
	public boolean save() {
		try{
			saveEx();
			error = null;
			return true;
		}catch (Exception e) {
			error = e.getMessage();
			LogM.log(getCtx(), getClass(), Level.SEVERE, "Error: ", e);
		}
		return false;
	}
	
	/**
	 * Save data with Exception
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/04/2012, 20:22:37
	 * @throws Exception
	 * @return void
	 */
	public void saveEx() throws Exception {
		loadConnection(DB.READ_WRITE);
		boolean fine = beforeSave(isNew);
		if(!fine)
			throw new Exception("saveEx.beforeSave");
		//	Set Default Values
		setLogValues(isNew);
		if(isNew)
			saveNew();
		else
			saveUpdate();
		
		fine = afterSave(isNew);
		
		if(!fine)
			throw new Exception("saveEx.afterSave");
	}
	
	/**
	 * Delete record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/05/2012, 02:06:03
	 * @return
	 * @return boolean
	 */
	public boolean delete() {
		try{
			deleteEx();
			error = null;
			return true;
		}catch (Exception e) {
			error = e.getMessage();
			LogM.log(getCtx(), getClass(), Level.SEVERE, " Error ", e);
		}
		return false;
	}
	
	/**
	 * Delete record with exception
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/05/2012, 02:04:13
	 * @throws Exception
	 * @return void
	 */
	public void deleteEx() throws Exception {
		
		try{
			loadConnection(DB.READ_WRITE);
			//	Before Delete
			boolean fine = beforeDelete();
			if(!fine)
				throw new Exception("delete.beforeDelete");
			
			conn.deleteSQL(m_TableInfo.getTableName(), get_WhereClause(false), get_WhereClauseValues());
			if(handConnection)
				conn.setTransactionSuccessful();
			//	
			clear(true);
			LogM.log(getCtx(), getClass(), Level.FINE, (String)m_oldValues[0]);
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			closeConnection();
		}
	}
	
	/**
	 * Clean array
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/05/2012, 03:26:13
	 * @param deleteBackup
	 * @return void
	 */
	public void clear(boolean deleteBackup) {
		isNew = true;
		m_oldId = m_currentId;
		m_currentId = 0;
		int size = m_TableInfo.getColumnLength();
		m_currentValues = new Object[size];
		//	Load default Values
		loadDefaultValues();
		//	
		if(deleteBackup){
			m_oldId = 0;
			m_oldValues = new Object[size];
		}
	}
	
	
	/**
	 * Save a new record.... use INSERT SQL
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 30/03/2012, 22:10:05
	 * @return
	 * @return boolean
	 * @throws Exception 
	 */
	private boolean saveNew() throws Exception {
		StringBuffer columns = new StringBuffer();
		StringBuffer sym = new StringBuffer();
		ArrayList<Object> listValues = new ArrayList<Object>();
		//	
		try{
			for (int i = 0; i < m_TableInfo.getColumnLength(); i++) {
				POInfoColumn column = m_TableInfo.getPOInfoColumn(i);
				if(!column.isColumnSQL()){
					if(i > 0){
						columns.append(",");
						sym.append(",");
					}
					columns.append(column.ColumnName);
					sym.append("?");
					//	
					Object value = parseValue(column, i, true, true);
					if(column.IsMandatory 
							&& value == null)
						throw new Exception(m_ctx.getResources().getString(R.string.MustFillField) + 
								" \"" + column.ColumnName + "\"");
					listValues.add(value);
					LogM.log(getCtx(), getClass(), Level.FINE, column.ColumnName + "=" + value + " Mandatory=" + column.IsMandatory);
				}
			}
			String sql = "INSERT INTO " + 
					m_TableInfo.getTableName() + 
					" (" + 
					columns.toString() + 
					") VALUES(" + 
					sym.toString() + 
					")";
			conn.executeSQL(sql, listValues.toArray());
			if(handConnection)
				conn.setTransactionSuccessful();
			
			//	Load Values
			m_IDs = new Object[] {m_currentId};
			m_KeyColumns = new String[] {m_TableInfo.getTableName() + "_ID"};
			isNew = false;
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection();
		}
		return false;
	}
	
	/**
	 * Update a record.... use UPDATE SQL
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/05/2012, 21:11:58
	 * @return
	 * @throws Exception
	 * @return boolean
	 */
	private boolean saveUpdate() throws Exception {
		StringBuffer columns = new StringBuffer();
		ArrayList<Object> listValues = new ArrayList<Object>();
		//	
		try{
			for (int i = 0; i < m_TableInfo.getColumnLength(); i++) {
				POInfoColumn column = m_TableInfo.getPOInfoColumn(i);
				if(!column.isColumnSQL()
						&& !column.ColumnName.equals("Created")
						&& !column.ColumnName.equals("CreatedBy")){
					if(i > 0)
						columns.append(",");
					//	
					columns.append(column.ColumnName)
							.append("=")
							.append("?");
					//	
					Object value = parseValue(column, i, false, true);
					if(column.IsMandatory 
							&& value == null)
						throw new Exception(m_ctx.getResources().getString(R.string.MustFillField) + 
								" \"" + column.ColumnName + "\"");
					if(!column.ColumnName.equals("Created")
							&& !column.ColumnName.equals("CreatedBy")){
						listValues.add(value);
						LogM.log(getCtx(), getClass(), Level.FINE, column.ColumnName + "=" + value + " Mandatory=" + column.IsMandatory);
					}
				}
			}
			//	
			String sql = "UPDATE " + 
					m_TableInfo.getTableName() + 
					" SET " + 
					columns.toString() +
					" WHERE "  + get_WhereClause(true);
			
			conn.executeSQL(sql, listValues.toArray());
			if(handConnection)
				conn.setTransactionSuccessful();
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection();
		}
		return false;
	}
	
	/**
	 * Set Column Values default
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/02/2014, 23:03:03
	 * @param isNew
	 * @return void
	 */
	private void setLogValues(boolean isNew) {
		int m_AD_User_ID = Env.getAD_User_ID(m_ctx);
		Date currentDate = Env.getCurrentDate();
		if(isNew){
			setCreatedBy(m_AD_User_ID);
			setCreated(Env.getCurrentDate());
		}
		setUpdatedBy(m_AD_User_ID);
		setUpdated(currentDate);
		
	}
	
	/**
	 * Get value for insert record
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/05/2012, 21:13:26
	 * @param column
	 * @param index
	 * @param isNew
	 * @param toSave
	 * @return
	 * @throws Exception
	 * @return Object
	 */
	public final Object parseValue(POInfoColumn column, int index, boolean isNew, boolean toSave) throws Exception {
		if(index >= 0){
			Object value = m_currentValues[index]; 
			if(isNew
					&& column.ColumnName.equals(m_TableInfo.getTableName() + "_ID")){
				m_currentId = MSequence.getNextID(m_ctx, getAD_Client_ID(), getTableName(), conn);
				//	Set ID
				set_Value(index, m_currentId);
				return m_currentId;
			} else if(isNew 
					&& column.ColumnName.equals("DocumentNo")){
					//	Get Document Type
					int m_C_DocType_ID = get_ValueAsInt("C_DocType_ID");
					//	Target Document
					if(m_C_DocType_ID == 0)
						m_C_DocType_ID = get_ValueAsInt("C_DocTypeTarget_ID");
					//	Get Document No
					String documentNo = MSequence.getDocumentNo(getCtx(), m_C_DocType_ID, m_TableInfo.getTableName(), true, conn);
					return documentNo;
			} else {
				if(value == null
						&& column.ColumnName.equals("DocumentNo")){
					//	Get Document Type
					int m_C_DocType_ID = get_ValueAsInt("C_DocType_ID");
					//	Target Document
					if(m_C_DocType_ID == 0)
						m_C_DocType_ID = get_ValueAsInt("C_DocTypeTarget_ID");
					//	Get Document No
					String documentNo = MSequence.getDocumentNo(getCtx(), m_C_DocType_ID, m_TableInfo.getTableName(), false, conn);
					return documentNo;
				} else if(value != null){
					Object returnValue = DisplayType.getJDBC_Value(column.DisplayType, value, !toSave, !toSave);
					return returnValue;
				} else if(column.DefaultValue != null){
					if(toSave)
						return Env.parseContext(getCtx(), (String)column.DefaultValue, false);
					else
						return DisplayType.parseValue(
										Env.parseContext(getCtx(), (String)column.DefaultValue, false)
										, column.DisplayType);
				} else
					return null;
			}
		}
		return null;
	}
	
	/**
	 * Trigger before save
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2012, 23:10:35
	 * @param isNew
	 * @return
	 * @return boolean
	 */
	protected boolean beforeSave(boolean isNew) {
		return true;
	}
	
	/**
	 * Trigger after save
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2012, 23:40:55
	 * @param isNew
	 * @return
	 * @return boolean
	 */
	protected boolean afterSave(boolean isNew) {
		return true;
	}
	
	/**
	 * Trigger before delete
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2012, 23:10:40
	 * @return
	 * @return boolean
	 */
	protected boolean beforeDelete() {
		return true;
	}
	
	/**
	 * Trigger after delete
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/03/2012, 23:42:51
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
	protected int getSPS_Table_ID() {
		return m_TableInfo.getSPS_Table_ID();
	}
	
	/**
	 * Get table name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/06/2012, 18:01:39
	 * @return
	 * @return String
	 */
	protected String getTableName() {
		return m_TableInfo.getTableName();
	}
	
	/**
	 * Create a new connection to DB
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/05/2012, 17:57:15
	 * @return void
	 */
	public void loadConnection(int type) {
		if(!conn.isOpen()){
			if(handConnection){
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/07/2012, 18:13:44
	 * @return void
	 */
	public void closeConnection() {
		LogM.log(getCtx(), getClass(), Level.FINE, "Close");
		if(conn.isOpen()){
			if(handConnection){
				LogM.log(getCtx(), getClass(), Level.FINE, "handConnection");
				if(conn.getBd().inTransaction())
					conn.endTransaction();
				conn.close();
			}
		}
    }
	
	/**
	 * Get Current Connection
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 22:10:28
	 * @return
	 * @return DB
	 */
	public DB get_Connection() {
		return conn;
	}
	
	/**
	 * Get Error Message
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/07/2012, 20:35:15
	 * @return
	 * @return String
	 */
	public String getError() {
		return error;
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 14:05:30
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
	
}
