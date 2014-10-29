/*******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it     *
 * under the terms version 2 of the GNU General Public License as published    *
 * by the Free Software Foundation. This program is distributed in the hope    *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied  *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.            *
 * See the GNU General Public License for more details.                        *
 * You should have received a copy of the GNU General Public License along     *
 * with this program; if not, write to the Free Software Foundation, Inc.,     *
 * 59 Temple Place, Suite 330, Boston, MA                                      *
 * 02111-1307 USA.                                                             *
 *                                                                             *
 * Copyright (C) 2007 Low Heng Sin hengsin@avantz.com                          *
 * Contributor(s):                                                             *
 *                 Teo Sarca, www.arhipac.ro                                   *
 *                 Yamel Senih, www.erpcya.com, yamelsenih@gmail.com           *
 * __________________________________________                                  *
 ******************************************************************************/
package org.spinsuite.model;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 * 
 * @author Low Heng Sin
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 1981760 ] Improve Query class
 * 			<li>BF [ 2030280 ] org.compiere.model.Query apply access filter issue
 * 			<li>FR [ 2041894 ] Add Query.match() method
 * 			<li>FR [ 2107068 ] Query.setOrderBy should be more error tolerant
 * 			<li>FR [ 2107109 ] Add method Query.setOnlyActiveRecords
 * 			<li>FR [ 2421313 ] Introduce Query.firstOnly convenient method
 * 			<li>FR [ 2546052 ] Introduce Query aggregate methods
 * 			<li>FR [ 2726447 ] Query aggregate methods for all return types
 * 			<li>FR [ 2818547 ] Implement Query.setOnlySelection
 * 				https://sourceforge.net/tracker/?func=detail&aid=2818547&group_id=176962&atid=879335
 * 			<li>FR [ 2818646 ] Implement Query.firstId/firstIdOnly
 * 				https://sourceforge.net/tracker/?func=detail&aid=2818646&group_id=176962&atid=879335
 * @author Redhuan D. Oon
 * 			<li>FR: [ 2214883 ] Remove SQL code and Replace for Query // introducing SQL String prompt in log.info 
 *			<li>FR: [ 2214883 ] - to introduce .setClient_ID
 */
public class Query
{
	public static final String AGGREGATE_COUNT		= "COUNT";
	public static final String AGGREGATE_SUM		= "SUM";
	public static final String AGGREGATE_AVG		= "AVG";
	public static final String AGGREGATE_MIN		= "MIN";
	public static final String AGGREGATE_MAX		= "MAX";
	
	private Context 	ctx 								= null;
	private MSPSTable 	table 								= null;
	private String 		whereClause 						= null;
	private String 		orderBy 							= null;
	private DB 			conn 								= null;
	private Object[]	parameters 							= null;
	private boolean 	applyAccessFilter 					= false;
	//private boolean 	applyAccessFilterRW 				= false;
	//private boolean 	applyAccessFilterFullyQualified 	= true;
	private boolean 	onlyActiveRecords 					= false;
	private boolean 	onlyClient_ID 						= false;
	private int 		onlySelection_ID 					= -1;
	
	/**
	 * @param ctx context 
	 * @param table
	 * @param whereClause
	 * @param conn
	 */
	public Query(Context ctx, MSPSTable table, String whereClause, DB conn) {
		this.ctx = ctx;
		this.table = table;
		this.whereClause = whereClause;
		this.conn = conn;
		this.conn = new DB(ctx); 
	}
	
	/**
	 * 
	 * @param ctx
	 * @param tableName
	 * @param whereClause
	 * @param conn
	 */
	public Query(Context ctx, String tableName, String whereClause, DB conn) {
		this(ctx, MSPSTable.get(ctx, tableName, conn), whereClause, conn);
		if (this.table == null)
			throw new IllegalArgumentException("Table Name Not Found - "+tableName);
	}
	
	/**
	 * Set query parameters
	 * @param parameters
	 */
	public Query setParameters(Object ...parameters) {
		this.parameters = parameters;
		return this;
	}
	
	/**
	 * Set query parameters
	 * @param parameters collection of parameters
	 */
	public Query setParameters(List<String> parameters) {
		if (parameters == null) {
			this.parameters = null;
			return this;
		}
		this.parameters = new String[parameters.size()];
		parameters.toArray(this.parameters);
		return this;
	}
	
	/**
	 * Set order by clause.
	 * If the string starts with "ORDER BY" then "ORDER BY" keywords will be discarded. 
	 * @param orderBy SQL ORDER BY clause
	 */
	public Query setOrderBy(String orderBy) {
		this.orderBy = orderBy != null ? orderBy.trim() : null;
		if (this.orderBy != null && this.orderBy.toUpperCase().startsWith("ORDER BY")) {
			this.orderBy = this.orderBy.substring(8);
		}
		return this;
	}
	
	/**
	 * Turn on/off the addition of data access filter
	 * @param flag
	 */
	public Query setApplyAccessFilter(boolean flag) {
		this.applyAccessFilter = flag;
		return this;
	}

	/**
	 * Turn on data access filter with controls
	 * @param flag
	 */
	public Query setApplyAccessFilter(boolean fullyQualified, boolean RW) {
		this.applyAccessFilter = true;
		//this.applyAccessFilterFullyQualified = fullyQualified;
		//this.applyAccessFilterRW = RW;
		return this;
	}
	
	
	/**
	 * Select only active records (i.e. IsActive='Y')
	 * @param onlyActiveRecords
	 */
	public Query setOnlyActiveRecords(boolean onlyActiveRecords) {
		this.onlyActiveRecords = onlyActiveRecords;
		return this;
	}
	
	/**
	 * Set Client_ID true for WhereClause routine to include AD_Client_ID
	 */
	public Query setClient_ID() {
		this.onlyClient_ID = true;
		return this;
	}
	
	/**
	 * Only records that are in T_Selection with AD_PInstance_ID.
	 * @param AD_PInstance_ID
	 */
	public Query setOnlySelection(int AD_PInstance_ID) {
		this.onlySelection_ID = AD_PInstance_ID;
		return this;
	}
	
	/**
	 * Return a list of all po that match the query criteria.
	 * @return List
	 * @throws DBException 
	 */
	@SuppressWarnings("unchecked")
	public <T extends PO> List<T> list() {
		List<T> list = new ArrayList<T>();
		String sql = buildSQL(null, true);
		
		Cursor rs = null;
		try {
			DB.loadConnection(conn, DB.READ_ONLY);
			rs = createResultSet(sql);
			while (rs.moveToNext()) {
				T po = (T)table.getPO(rs, conn);
				list.add(po);
			}
		} catch (Exception e) {
			LogM.log(ctx, getClass(), Level.SEVERE, sql, e);
		} finally {
			DB.closeConnection(conn);
		}
		return list;
	}
	
	/**
	 * Return first PO that match query criteria
	 * @return first PO
	 * @throws DBException
	 */
	@SuppressWarnings("unchecked")
	public <T extends PO> T first() throws Exception {
		T po = null;
		String sql = buildSQL(null, true);
		
		Cursor rs = null;
		try {
			rs = createResultSet(sql);
			if (rs.moveToFirst()) {
				po = (T)table.getPO(rs, conn);
			}
		} catch (Exception e) {
			LogM.log(ctx, getClass(), Level.SEVERE, sql, e);
			throw new Exception(e);
		} finally {
			DB.closeConnection(conn);
		}
		return po;
	}
	
	/**
	 * Return first PO that match query criteria.
	 * If there are more records that match criteria an exception will be throwed 
	 * @return first PO
	 * @throws DBException
	 * @see {@link #first()}
	 */
	@SuppressWarnings("unchecked")
	public <T extends PO> T firstOnly() throws Exception {
		T po = null;
		String sql = buildSQL(null, true);
		
		Cursor rs = null;
		try
		{
			rs = createResultSet(sql);
			if (rs.moveToFirst()) {
				po = (T)table.getPO(rs, conn);
			}
			if (rs.moveToFirst()) {
				throw new Exception("QueryMoreThanOneRecordsFound"); // TODO : translate
			}
		} catch (SQLException e) {
			LogM.log(ctx, getClass(), Level.SEVERE, sql, e);
			throw new Exception(e);
		} finally {
			DB.closeConnection(conn);
		}
		return po;
	}
	
	/**
	 * Return first ID
	 * @return first ID or -1 if not found
	 * @throws DBException
	 */
	public int firstId() throws Exception {
		return firstId(false);
	}
	
	/**
	 * Return first ID.
	 * If there are more results and exception is thrown.
	 * @return first ID or -1 if not found
	 * @throws DBException
	 */
	public int firstIdOnly() throws Exception {
		return firstId(true);
	}
	
	/**
	 * Get First ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/10/2014, 19:55:04
	 * @param assumeOnlyOneResult
	 * @return
	 * @throws Exception
	 * @return int
	 */
	private int firstId(boolean assumeOnlyOneResult) throws Exception {
		String[] keys = table.getKeyColumns();
		if (keys.length != 1) {
			throw new Exception("Table " + table + " has 0 or more than 1 key columns");
		}

		StringBuffer selectClause = new StringBuffer("SELECT ");
		selectClause.append(keys[0]);
		selectClause.append(" FROM ").append(table.getTableName());
		String sql = buildSQL(selectClause, true);

		int id = -1;
		Cursor rs = null;
		try {
			rs = createResultSet(sql);
			if (rs.moveToFirst()) {
				id = rs.getInt(1);
			}
			if (assumeOnlyOneResult && rs.moveToFirst()) {
				throw new Exception("QueryMoreThanOneRecordsFound"); // TODO : translate
			}
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			DB.closeConnection(conn);
		}
		//
		return id;
	}

	
	/**
	 * red1 - returns full SQL string - for caller needs
	 * @return buildSQL(null,true)
	 * 
	 */
	public String getSQL() throws Exception {
 		return buildSQL(null, true);
	}

	/**
	 * Aggregate given expression on this criteria
	 * @param sqlExpression
	 * @param sqlFunction 
	 * @return aggregated value
	 * @throws DBException
	 */
	public BigDecimal aggregate(String sqlExpression, String sqlFunction) throws Exception {
		return aggregate(sqlExpression, sqlFunction, BigDecimal.class);
	}

	/**
	 * Aggregate given expression on this criteria
	 * @param <T>
	 * @param sqlExpression
	 * @param sqlFunction
	 * @param returnType
	 * @return aggregated value
	 * @throws DBException
	 */
	@SuppressWarnings("unchecked")
	public <T> T aggregate(String sqlExpression, String sqlFunction, Class<T> returnType) throws Exception {
		if (sqlFunction == null
				|| sqlFunction.trim().length() == 0) {
			throw new Exception("No Aggregate Function defined");
		}
		if (sqlExpression == null
				|| sqlExpression.trim().length() == 0) {
			if (AGGREGATE_COUNT == sqlFunction) {
				sqlExpression = "*";
			} else {
				throw new Exception("No Expression defined");
			}
		}
		
		StringBuffer sqlSelect = new StringBuffer("SELECT ").append(sqlFunction).append("(")
					.append(sqlExpression).append(")")
					.append(" FROM ").append(table.getTableName());
		
		T value = null;
		T defaultValue = null;
		
		String sql = buildSQL(sqlSelect, false);
		Cursor rs = null;
		try {
			rs = createResultSet(sql);
			if (rs.moveToFirst()) {
				if (returnType.isAssignableFrom(BigDecimal.class)) {
					value = (T)new BigDecimal(rs.getDouble(0));
					defaultValue = (T)Env.ZERO;
				} else if (returnType.isAssignableFrom(Double.class)) {
					value = (T)Double.valueOf(rs.getDouble(0));
					defaultValue = (T)Double.valueOf(0.00);
				} else if (returnType.isAssignableFrom(Integer.class)) {
					value = (T)Integer.valueOf(rs.getInt(0));
					defaultValue = (T)Integer.valueOf(0);
				} else if (returnType.isAssignableFrom(Timestamp.class)) {
					value = (T)new Date(rs.getLong(0));
				} else if (returnType.isAssignableFrom(Boolean.class)) {
					value = (T) Boolean.valueOf("Y".equals(rs.getString(0)));
					defaultValue = (T) Boolean.FALSE;
				} else {
					value = (T)rs.getString(0);
				}
			}
			if (rs.moveToFirst()) {
				throw new Exception("QueryMoreThanOneRecordsFound"); // TODO : translate
			}
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			DB.closeConnection(conn);
		}
		//
		if (value == null)
		{
			value = defaultValue;
		}
		return value;
	}
	
	/**
	 * Count items that match query criteria
	 * @return count
	 * @throws DBException
	 */
	public int count() throws Exception {
		return aggregate("*", AGGREGATE_COUNT).intValue();
	}
	
	/**
	 * SUM sqlExpression for items that match query criteria
	 * @param sqlExpression
	 * @return sum
	 */
	public BigDecimal sum(String sqlExpression) {
		try {
			return aggregate(sqlExpression, AGGREGATE_SUM);
		} catch (Exception e) {
			LogM.log(ctx, getClass(), Level.SEVERE, "Error sum", e); 
		}
		return null;
	}
	/**
	 * Check if there items for query criteria
	 * @return true if exists, false otherwise
	 * @throws DBException
	 */
	public boolean match() throws Exception {
		String sql = buildSQL(new StringBuffer("SELECT 1 FROM ").append(table.getTableName()), false);
		Cursor rs = null;
		try {
			rs = createResultSet(sql);
			if (rs.moveToFirst())
				return true;
		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			DB.closeConnection(conn);
		}
		return false;
	}
	
	/**
	 * Return an Iterator implementation to fetch one PO at a time. The implementation first retrieve
	 * all IDS that match the query criteria and issue sql query to fetch the PO when caller want to
	 * fetch the next PO. This minimize memory usage but it is slower than the list method.
	 * @return Iterator
	 * @throws DBException 
	 */
	/*public <T extends PO> Iterator<T> iterate() throws Exception {
		String[] keys = table.getKeyColumns();
		StringBuffer sqlBuffer = new StringBuffer(" SELECT ");
		for (int i = 0; i < keys.length; i++) {
			if (i > 0)
				sqlBuffer.append(", ");
			sqlBuffer.append(keys[i]);
		}
		sqlBuffer.append(" FROM ").append(table.getTableName());
		String sql = buildSQL(sqlBuffer, true);
		
		PreparedStatement pstmt = null;
		Cursor rs = null;
		List<Object[]> idList = new ArrayList<Object[]>();
		try {
			rs = createResultSet(sql);
			while (rs.moveToFirst())
			{
				Object[] ids = new Object[keys.length];
				for (int i = 0; i < ids.length; i++) {
					ids[i] = rs.getInt(i);
				}
				idList.add(ids);
			}
		} catch (Exception e) {
			LogM.log(ctx, getClass(), Level.SEVERE, sql, e);
			throw new Exception(e);
		} finally {
			DB.closeConnection(conn);
		}
		//	
		return new POIterator<T>(table, idList, conn);
	}*/
	
	/**
	 * Return a simple wrapper over a jdbc resultset. It is the caller responsibility to
	 * call the close method to release the underlying database resources.
	 * @return POResultSet
	 * @throws DBException 
	 */
	/*public <T extends PO> POResultSet<T> scroll() throws DBException
	{
		String sql = buildSQL(null, true);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		POResultSet<T> rsPO = null;
		try
		{
			pstmt = DB.prepareStatement (sql, conn);
			rs = createResultSet(pstmt);
			rsPO = new POResultSet<T>(table, pstmt, rs, conn);
			rsPO.setCloseOnError(true);
			return rsPO;
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			throw new DBException(e, sql);
		}
		finally
		{
			// If there was an error, then close the statement and resultset
			if (rsPO == null) {
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
		}
	}*/
	
	/**
	 * Build SQL Clause
	 * @param selectClause optional; if null the select clause will be build according to POInfo
	 * @return final SQL
	 * @throws Exception 
	 */
	private final String buildSQL(StringBuffer selectClause, boolean useOrderByClause) {
		if (selectClause == null) {
			POInfo info = POInfo.getPOInfo(this.ctx, table.getSPS_Table_ID(), conn);
			if (info == null) {
				throw new IllegalStateException("No POInfo found for AD_Table_ID="+table.getAD_Table_ID());
			}
			selectClause = info.buildSelect();
		}
		
		StringBuffer whereBuffer = new StringBuffer(); 
		if (whereClause != null
				&& whereClause.trim().length() != 0) {
			if (whereBuffer.length() > 0)
				whereBuffer.append(" AND ");
			whereBuffer.append("(").append(this.whereClause).append(")");
		}
		if (this.onlyActiveRecords) {
			if (whereBuffer.length() > 0)
				whereBuffer.append(" AND ");
			whereBuffer.append("IsActive=?");
		}
		if (this.onlyClient_ID) //red1
		{
			if (whereBuffer.length() > 0)
				whereBuffer.append(" AND ");
			whereBuffer.append("AD_Client_ID=?");
		}
		if (this.onlySelection_ID > 0) {
			String[] keys = table.getKeyColumns();
			if (keys.length != 1) {
				LogM.log(ctx, getClass(), Level.SEVERE, "Table " + table + " has 0 or more than 1 key columns");
			}
			//
			if (whereBuffer.length() > 0)
				whereBuffer.append(" AND ");
			whereBuffer.append(" EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID=?"
					+" AND s.T_Selection_ID="+table.getTableName()+"."+keys[0]+")");
		}
		
		StringBuffer sqlBuffer = new StringBuffer(selectClause);
		if (whereBuffer.length() > 0)
		{
			sqlBuffer.append(" WHERE ").append(whereBuffer);
		}
		if (useOrderByClause 
				&& (orderBy != null && orderBy.trim().length() != 0)) {
			sqlBuffer.append(" ORDER BY ").append(orderBy);
		}
		String sql = sqlBuffer.toString();
		if (applyAccessFilter) {
			//	Not yet implemented
			//MRole role = MRole.getDefault(this.ctx, false);
			//sql = role.addAccessSQL(sql, table.getTableName(), applyAccessFilterFullyQualified, applyAccessFilterRW);
		}
		LogM.log(ctx, getClass(), Level.FINEST, 
				"TableName = " + table.getTableName() + "... SQL = " + sql); //red1  - to assist in debugging SQL
		return sql;
	}
	
	/**
	 * Create Cursor
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/10/2014, 19:37:03
	 * @param sql
	 * @return
	 * @throws Exception
	 * @return Cursor
	 */
	private final Cursor createResultSet (String sql) throws Exception {
		//	Compile Query
		conn.compileQuery(sql);
		//	Add Parameters
		if(parameters != null) {
			for(Object parameter : parameters) {
				conn.addParameter(parameter);	
			}
		}
		//	
		if (this.onlyActiveRecords) {
			conn.addBoolean(true);
			LogM.log(ctx, getClass(), Level.FINEST, "Parameter IsActive = Y");
		}
		if (this.onlyClient_ID) {
			int AD_Client_ID = Env.getAD_Client_ID(ctx);
			conn.addInt(AD_Client_ID);
			LogM.log(ctx, getClass(), Level.FINEST, "Parameter AD_Client_ID = " + AD_Client_ID);
		}
		if (this.onlySelection_ID > 0) {
			conn.addInt(onlySelection_ID);
			LogM.log(ctx, getClass(), Level.FINEST, "Parameter Selection AD_PInstance_ID = " + this.onlySelection_ID);
		}
		//	
		return conn.querySQL();
	}
	
	/**
	 * Get a Array with the IDs for this Query
	 * @return Get a Array with the IDs
	 * @throws Exception 
	 */
	public int[] getIDs () throws Exception {
		String[] keys = table.getKeyColumns();
		if (keys.length != 1) {
			throw new Exception("Table " + table + " has 0 or more than 1 key columns");
		}

		StringBuffer selectClause = new StringBuffer("SELECT ");
		selectClause.append(keys[0]);
		selectClause.append(" FROM ").append(table.getTableName());
		String sql = buildSQL(selectClause, true);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		Cursor rs = null;
		try {
			rs = createResultSet(sql);
			while (rs.moveToNext()) {
				list.add(rs.getInt(0));
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DB.closeConnection(conn);
		}
		//	Convert to array
		int[] retValue = new int[list.size()];
		for (int i = 0; i < retValue.length; i++)
		{
			retValue[i] = list.get(i);
		}
		return retValue;
	}	//	get_IDs

}
