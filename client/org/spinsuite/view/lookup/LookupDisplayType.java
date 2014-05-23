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
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class LookupDisplayType {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 15:03:15
	 * @param field
	 */
	public LookupDisplayType(Context ctx, InfoField field){
		this(ctx, 0, 0, field);
	}
	
	public LookupDisplayType(Context ctx, int m_ActivityNo, int m_TabNo, InfoField field){
		this.m_field = field;
		this.ctx = ctx;
		m_InfoLookup = new InfoLookup();
		m_Language = Env.getAD_Language(ctx);
		m_IsBaseLanguage = Env.isBaseLanguage(ctx);
		this.m_ActivityNo = m_ActivityNo;
		this.m_TabNo = m_TabNo;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 16:20:14
	 * @param ctx
	 * @param m_SPS_Table_ID
	 */
	public LookupDisplayType(Context ctx, int m_SPS_Table_ID){
		this.m_SPS_Table_ID = m_SPS_Table_ID;
		this.ctx = ctx;
		m_InfoLookup = new InfoLookup();
		m_Language = Env.getAD_Language(ctx);
		m_IsBaseLanguage = Env.isBaseLanguage(ctx);
	}
	
	/**	Field					*/
	private InfoField 		m_field 				= null;
	/**	Table					*/
	private int				m_SPS_Table_ID 			= 0;
	/**	Context					*/
	private Context			ctx 					= null;
	/**	Optional where clause	*/
	private String			m_OptionalWhereClause 	= null;
	/**	Validation Rule			*/
	private String 			m_ValRule				= null;
	/**	Lookup Information		*/
	private InfoLookup 		m_InfoLookup 			= null;
	/**	Is Loaded				*/
	private boolean 		m_IsLoaded				= false;
	/**	SQL						*/
	private String 			m_SQL					= null;
	/**	Language				*/
	private String 			m_Language 				= "en_US";
	/**	Is Base Language		*/
	private boolean 		m_IsBaseLanguage 		= true;
	/**	Activity No				*/
	private int				m_ActivityNo			= 0;
	/**	Tab No					*/
	private int 			m_TabNo					= 0;
	/**	Has Where				*/
	private boolean 		m_IsHasWhere			= false;
	/**	Context Value Prefix	*/
	private final String	CTX_VALUE_PREFIX 		= "#LK|C|";
	private final String	CTX_VALUE_PREFIX_TABLE	= "#LK|T|";
	private final String	CTX_HAS_WHERE			= "#LK|HW|C|";
	private final String	CTX_HAS_WHERE_TABLE		= "#LK|HW|T|";
	private final String	MARK_WHERE				= "<MARK_WHERE>";
	
	/**
	 * Get Parsed SQL
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 23/05/2014, 15:08:05
	 * @param sql
	 * @return
	 * @return String
	 */
	private String getParsedSQL(String sql) {
		//	If Null
		if(m_OptionalWhereClause == null
				|| m_OptionalWhereClause.length() == 0)
			return sql.replaceAll(MARK_WHERE, "");
		//	Add Where
		String where = " WHERE ";
		//	Evaluate where
		if(m_IsHasWhere)
			where = " AND ";
		//	Return
		return sql.replaceAll(MARK_WHERE, where + m_OptionalWhereClause + " ");
	}
	
	/**
	 * Get SQL
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 15:31:15
	 * @return
	 * @return String
	 */
	public String getSQL(){
		//	Cache
		if(m_IsLoaded)
			return Env.parseContext(ctx, m_ActivityNo, m_TabNo, m_SQL, false, null);
		//	
		boolean isCache = false;
		String ctx_lookup_value = null;
		String ctx_lookup_has_where = null;
		String sqlParsed = null;
		//	
		if(m_SPS_Table_ID == 0){
			//	Context
			ctx_lookup_value = CTX_VALUE_PREFIX + m_field.SPS_Column_ID;
			ctx_lookup_has_where = CTX_HAS_WHERE + m_field.SPS_Column_ID;
			//	
			m_SQL = Env.getContext(ctx, ctx_lookup_value);
			isCache = m_SQL != null;
			//	Return Cache
			if(isCache) {
				m_IsHasWhere = Env.getContextAsBoolean(ctx, ctx_lookup_has_where);
				//	Parse
				sqlParsed = getParsedSQL(m_SQL);
				//	
				LogM.log(ctx, getClass(), Level.FINE, "From Cache[SQL=" + sqlParsed + "]");
				return Env.parseContext(ctx, m_ActivityNo, m_TabNo, sqlParsed, false, null);
			}
			//	Reload
			if(m_field.DisplayType == DisplayType.TABLE_DIR){
				m_SQL = loadSQLTableDirect();
				LogM.log(ctx, getClass(), Level.FINE, "SQL=" + m_SQL);
			} else if((m_field.DisplayType == DisplayType.TABLE 
					|| m_field.DisplayType == DisplayType.SEARCH)
					&& m_field.AD_Reference_Value_ID != 0){
				m_SQL = loadSQLTableSearch();
				LogM.log(ctx, getClass(), Level.FINE, "SQL=" + m_SQL);
			} else if((m_field.DisplayType == DisplayType.LIST)
					&& m_field.AD_Reference_Value_ID != 0){
				m_SQL = loadSQLList();
				LogM.log(ctx, getClass(), Level.FINE, "SQL=" + m_SQL);
			} else {
				m_SQL = loadSQLTableDirect();
				LogM.log(ctx, getClass(), Level.FINE, "SQL=" + m_SQL);
			}
		} else {
			//	Context
			ctx_lookup_value = CTX_VALUE_PREFIX_TABLE + m_SPS_Table_ID;
			ctx_lookup_has_where = CTX_HAS_WHERE_TABLE + m_SPS_Table_ID;
			//	
			m_SQL = Env.getContext(ctx, ctx_lookup_value);
			isCache = m_SQL != null;
			//	Return Cache
			if(isCache) {
				m_IsHasWhere = Env.getContextAsBoolean(ctx, ctx_lookup_has_where);
				//	Parse
				sqlParsed = getParsedSQL(m_SQL);
				//	
				LogM.log(ctx, getClass(), Level.FINE, "From Cache[SQL=" + sqlParsed + "]");
				return Env.parseContext(ctx, m_ActivityNo, m_TabNo, sqlParsed, false, null);
			}
			//	
			m_SQL = loadFromTable();
			LogM.log(ctx, getClass(), Level.FINE, "SQL=" + m_SQL);
		}
		//	Set Is Loaded
		m_IsLoaded = true;
		//	Set to Cache
		Env.setContext(ctx, ctx_lookup_value, m_SQL);
		Env.setContext(ctx, ctx_lookup_has_where, m_IsHasWhere);
		//	Parse SQL
		sqlParsed = getParsedSQL(m_SQL);
		//	
		LogM.log(ctx, getClass(), Level.FINE, "[SQL Without Cache=" + sqlParsed + "]");
		//	Return
		return Env.parseContext(ctx, m_ActivityNo, m_TabNo, sqlParsed, false, null);
	}
	
	/**
	 * Get Info Lookup
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 21:00:23
	 * @return
	 * @return InfoLookup
	 */
	public InfoLookup getInfoLookup(){
		//	Get SQL
		getSQL();
		//	Load ending
		return m_InfoLookup;
	}
	
	/**
	 * Get Validation Rule
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 14:45:20
	 * @return
	 * @return String
	 */
	private String getValRule(){
		if(m_field.AD_Val_Rule_ID == 0)
			return "";
		//	Cache
		if(m_ValRule != null)
			return m_ValRule;
		//	
		String code = DB.getSQLValueString(ctx, "SELECT vr.Code " +
				"FROM AD_Val_Rule vr " +
				"WHERE AD_Val_Rule_ID = " + m_field.AD_Val_Rule_ID);
		//	Parse
		return code;
	}
	
	/**
	 * Get SQL from Table Direct
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 22:06:00
	 * @return String
	 */
	private String loadSQLTableDirect(){
		StringBuffer sql = new StringBuffer();
		StringBuffer where = new StringBuffer();
		String tableName = m_field.ColumnName.replaceAll("_ID", "");
		sql.append("SELECT ").append(tableName).append(".").append(m_field.ColumnName);
		//	Set Info Lookup
		m_InfoLookup.TableName = tableName;
		m_InfoLookup.KeyColumn = m_field.ColumnName;
		//	
		DB conn = new DB(ctx);
		DB.loadConnection(conn, DB.READ_ONLY);
		Cursor rs = null;
		//	Query
		rs = conn.querySQL("SELECT c.ColumnName, c.AD_Reference_ID " +
				"FROM SPS_Table t " +
				"INNER JOIN SPS_Column c ON(c.SPS_Table_ID = t.SPS_Table_ID) " +
				"WHERE t.TableName = ? " +
				"AND c.IsIdentifier = ? ORDER BY SeqNo", new String[]{tableName, "Y"});
		//	First
		boolean isFirst = true;
		if(rs.moveToFirst()){
			sql.append(", ");
			StringBuffer longColumn = new StringBuffer();
			do {
				String columnName = rs.getString(0);
				//	Is First
				if(!isFirst)
					longColumn.append("||'_'||");
				//	
				longColumn.append("COALESCE(").append(tableName).append(".").append(columnName).append(",'')");
				//	Set false
				if(isFirst)
					isFirst = false;
			}while(rs.moveToNext());
			//	
			sql.append(longColumn);
			//	Set Info Lookup
			m_InfoLookup.DisplayColumn = longColumn.toString();
			//	
			//	Separator
		} else {
			sql.append(", ")
				.append(tableName).append(".").append(m_field.ColumnName);
		}
		//	Close
		DB.closeConnection(conn);
		sql.append(" FROM ").append(tableName);
		
		//	Validation Rule
		if(getValRule() != null
				&& getValRule().length() > 0){
			where.append(" WHERE ").append(getValRule());
			//	Add Mark
			m_IsHasWhere = true;
		} else {
			m_IsHasWhere = false;
		}
		where.append(MARK_WHERE);
		//	Add Where Clause to SQL
		sql.append(where);
		//	Return
		return sql.toString();
	}
	
	/**
	 * Get SQL from Reference Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 22:32:49
	 * @return String
	 */
	private String loadSQLTableSearch(){
		StringBuffer sql = new StringBuffer();
		StringBuffer where = new StringBuffer();
		sql.append("SELECT ");
		DB conn = new DB(ctx);
		DB.loadConnection(conn, DB.READ_ONLY);
		Cursor rs = null;
		//	Query
		rs = conn.querySQL("SELECT t.TableName, ck.ColumnName, cd.ColumnName, " +
				"rl.IsValueDisplayed, rl.WhereClause, rl.OrderByClause " +
				"FROM AD_Ref_Table rl " +
				"INNER JOIN SPS_Table t ON(t.AD_Table_ID = rl.AD_Table_ID) " +
				"INNER JOIN SPS_Column ck ON(ck.AD_Column_ID = rl.AD_Key) " +
				"INNER JOIN SPS_Column cd ON(cd.AD_Column_ID = rl.AD_Display) " +
				"WHERE rl.AD_Reference_ID = " + m_field.AD_Reference_Value_ID, null);
		
		if(rs.moveToFirst()){
			String tableName = rs.getString(0);
			String pkColumnName = rs.getString(1);
			String dColumnName = rs.getString(2);
			String isValueDisplayed = rs.getString(3);
			String whereClause = rs.getString(4);
			String orderByClause = rs.getString(5);
			//	Close
			DB.closeConnection(conn);
			//	Set Info Lookup
			m_InfoLookup.TableName = tableName;
			m_InfoLookup.KeyColumn = pkColumnName;			
			//	
			sql.append(tableName).append(".").append(pkColumnName).append(", ");
			//	Display Column
			StringBuffer longColumn = new StringBuffer();
			//	Display Value
			if(isValueDisplayed != null
					&& isValueDisplayed.equals("Y"))
				longColumn.append("COALESCE(").append(tableName).append(".")
						.append("Value").append(", '')").append("||'_'||");
			//	Display Column
			longColumn.append("COALESCE(").append(tableName).append(".").append(dColumnName).append(",'')");
			sql.append(longColumn);
			//	Set Info Lookup
			m_InfoLookup.DisplayColumn = longColumn.toString();
			//	Separator
			sql.append(" FROM ").append(tableName);
			//	Where Clause
			if(whereClause != null
					&& whereClause.length() > 0)
				where.append(" WHERE ").append(whereClause);
			//	Set Where
			//	Validation Rule
			if(getValRule() != null
					&& getValRule().length() > 0){
				//	Add And
				if(where.length() > 0)
					where.append(" AND ");
				else
					where.append(" WHERE ");
				//	
				where.append(getValRule());
			}
			//	Add Mark Where
			if(where.length() > 0)
				m_IsHasWhere = true;
			else
				m_IsHasWhere = false;
			where.append(MARK_WHERE);
			//	Set SQL
			sql.append(where);
			//	Order By Clause
			if(orderByClause != null
					&& orderByClause.length() > 0)
				sql.append(" ORDER BY ").append(orderByClause);
		}
		//	Return
		return sql.toString();
	}
	
	/**
	 * Load SQL for Reference List
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 10:54:32
	 * @return String
	 */
	private String loadSQLList(){
		//	Set SQL
		StringBuffer sql = new StringBuffer("SELECT rl.Value, ");
		//	Handle Language
		if(m_IsBaseLanguage){
			sql.append("rl.Name ");
			//	From
			sql.append("FROM AD_Ref_List rl ");
		} else {
			sql.append("rlt.Name ");
			//	From
			sql.append("FROM AD_Ref_List rl ");
			//	Join
			sql.append("INNER JOIN AD_Ref_List_Trl rlt ON(rlt.AD_Ref_List_ID = rl.AD_Ref_List_ID " +
					"AND rlt.AD_Language = '").append(m_Language).append("') ");
		}
		//	Where Clause			
		sql.append("WHERE rl.AD_Reference_ID = ").append(m_field.AD_Reference_Value_ID);
		//	Validation Rule
		if(getValRule() != null
				&& getValRule().length() > 0){
			//	Add And
			sql.append(" AND ").append(getValRule());
		}
		//	Add Mark
		m_IsHasWhere = true;
		sql.append(MARK_WHERE);
		//	
		sql.append(" ORDER BY 2 ");
		//	Return
		return sql.toString();
	}
	
	/**
	 * Load Sql from Table
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 16:22:06
	 * @return
	 * @return String
	 */
	private String loadFromTable(){
		StringBuffer sql = new StringBuffer();
		String tableName = null;
		DB conn = new DB(ctx);
		DB.loadConnection(conn, DB.READ_ONLY);
		Cursor rs = null;
		//	Query
		rs = conn.querySQL("SELECT t.TableName, c.ColumnName " +
				"FROM SPS_Table t " +
				"INNER JOIN SPS_Column c ON(c.SPS_Table_ID = t.SPS_Table_ID) " +
				"WHERE t.SPS_Table_ID = ? " +
				"AND c.IsIdentifier = ? " +
				"ORDER BY SeqNo", new String[]{String.valueOf(m_SPS_Table_ID), "Y"});
		
		boolean isFirst = true;
		if(rs.moveToFirst()){
			tableName = rs.getString(0);
			//	Set Info Lookup
			m_InfoLookup.TableName = tableName;
			m_InfoLookup.KeyColumn = tableName + "_ID";
			//	
			sql.append("SELECT ").append(tableName).append(".").append(tableName).append("_ID").append(", ");
			//	Display Type
			StringBuffer longColumn = new StringBuffer();
			do {
				String columnName = rs.getString(1);
				//	Is First
				if(!isFirst)
					longColumn.append("||'_'||");
				//	
				longColumn.append("COALESCE(").append(tableName).append(".").append(columnName).append(",'')");
				//	Set false
				if(isFirst)
					isFirst = false;
			}while(rs.moveToNext());
			//	
			sql.append(longColumn);
			//	Set Info Lookup
			m_InfoLookup.DisplayColumn = longColumn.toString();
			//	Separator
		}
		//	Close
		DB.closeConnection(conn);
		sql.append(" FROM ").append(tableName);
		//	Optional Where
		m_IsHasWhere = false;
		sql.append(MARK_WHERE);
		//	Return
		return sql.toString();
	}
	
	/**
	 * Set Optional where clause
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/03/2014, 22:24:26
	 * @param whereClause
	 * @return void
	 */
	public void setCriteria(String whereClause){
		m_OptionalWhereClause = whereClause;
		m_IsLoaded = false;
	}

	@Override
	public String toString() {
		return "LookupDisplayType [m_field=" + m_field + ", m_SPS_Table_ID="
				+ m_SPS_Table_ID + ", ctx=" + ctx + ", m_optionalWhereClause="
				+ m_OptionalWhereClause + ", m_InfoLookup=" + m_InfoLookup
				+ ", m_IsLoaded=" + m_IsLoaded + ", m_SQL=" + m_SQL + "]";
	}
}
