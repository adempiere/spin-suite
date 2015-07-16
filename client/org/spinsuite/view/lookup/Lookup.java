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
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.view.lookup;

import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.ActivityParameter;
import org.spinsuite.util.DisplayLookupSpinner;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.IdentifierValueWrapper;
import org.spinsuite.util.IdentifierWrapper;
import org.spinsuite.util.LogM;
import org.spinsuite.util.TabParameter;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *   <li> Add Format Pattern to Date and Number in Lookup
 *   @see https://adempiere.atlassian.net/browse/SPIN-19
 *
 */
public class Lookup {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/09/2014, 10:07:25
	 * @param ctx
	 * @param field
	 * @param tableAlias
	 */
	public Lookup(Context ctx, InfoField field, String tableAlias) {
		this(ctx, 0, 0, field, tableAlias);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/09/2014, 10:08:19
	 * @param ctx
	 * @param field
	 */
	public Lookup(Context ctx, InfoField field) {
		this(ctx, 0, 0, field, null);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/08/2014, 14:58:02
	 * @param ctx
	 * @param m_ActivityNo
	 * @param m_TabNo
	 * @param field
	 * @param tableAlias
	 */
	public Lookup(Context ctx, int m_ActivityNo, int m_TabNo, InfoField field, String tableAlias) {
		this(ctx, null, field, tableAlias);
		//	New
		m_TabParam = new TabParameter();
		m_TabParam.setActivityNo(m_ActivityNo);
		m_TabParam.setTabNo(m_TabNo);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/09/2014, 11:43:05
	 * @param ctx
	 * @param tabParam
	 * @param field
	 * @param tableAlias
	 */
	public Lookup(Context ctx, TabParameter tabParam, InfoField field, String tableAlias) {
		this.m_field = field;
		this.m_ctx = ctx;
		m_Language = Env.getAD_Language();
		m_IsBaseLanguage = Env.isBaseLanguage();
		m_TabParam = tabParam;
		m_TableAlias = tableAlias;
		//	Add Alias
		if(tableAlias != null
				&& tableAlias.length() > 0) {
			ctx_lookup_value = CTX_VALUE_PREFIX + m_Language + "|" + tableAlias + "|" + m_field.SPS_Column_ID;
			ctx_lookup_has_where = CTX_HAS_WHERE + m_Language + "|" + tableAlias + "|" + m_field.SPS_Column_ID;
			ctx_lookup_info = CTX_LOOKUP_INFO_PREFIX + m_Language + "|" + tableAlias + "|" + m_field.SPS_Column_ID;
		} else {
			ctx_lookup_value = CTX_VALUE_PREFIX + m_Language + "|" + m_field.SPS_Column_ID;
			ctx_lookup_has_where = CTX_HAS_WHERE + m_Language + "|" + m_field.SPS_Column_ID;
			ctx_lookup_info = CTX_LOOKUP_INFO_PREFIX + m_Language + "|" + m_field.SPS_Column_ID;
		}
		//	Valid Null
		if(m_TabParam == null) {
			m_TabParam = new TabParameter();
			m_TabParam.setActivityNo(0);
			m_TabParam.setTabNo(0);
		}
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/09/2014, 20:12:45
	 * @param ctx
	 * @param m_SPS_Column_ID
	 * @param tableAlias
	 */
	public Lookup(Context ctx, int m_SPS_Column_ID, String tableAlias) {
		this(ctx, null, m_SPS_Column_ID, tableAlias);
	}
	
	/**
	 * With Column Identifier
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/09/2014, 20:10:58
	 * @param ctx
	 * @param tabParam
	 * @param m_SPS_Column_ID
	 * @param tableAlias
	 */
	public Lookup(Context ctx, TabParameter tabParam, int m_SPS_Column_ID, String tableAlias) {
		this.m_field = GridField.loadInfoColumnField(ctx, m_SPS_Column_ID);
		this.m_ctx = ctx;
		m_Language = Env.getAD_Language();
		m_IsBaseLanguage = Env.isBaseLanguage();
		m_TabParam = tabParam;
		m_TableAlias = tableAlias;
		//	Add Alias
		if(tableAlias != null
				&& tableAlias.length() > 0) {
			ctx_lookup_value = CTX_VALUE_PREFIX + m_Language + "|" + tableAlias + "|" + m_field.SPS_Column_ID;
			ctx_lookup_has_where = CTX_HAS_WHERE + m_Language + "|" + tableAlias + "|" + m_field.SPS_Column_ID;
			ctx_lookup_info = CTX_LOOKUP_INFO_PREFIX + m_Language + "|" + tableAlias + "|" + m_field.SPS_Column_ID;
		} else {
			ctx_lookup_value = CTX_VALUE_PREFIX + m_Language + "|" + m_field.SPS_Column_ID;
			ctx_lookup_has_where = CTX_HAS_WHERE + m_Language + "|" + m_field.SPS_Column_ID;
			ctx_lookup_info = CTX_LOOKUP_INFO_PREFIX + m_Language + "|" + m_field.SPS_Column_ID;
		}
		//	Valid Null
		if(m_TabParam == null) {
			m_TabParam = new TabParameter();
			m_TabParam.setActivityNo(0);
			m_TabParam.setTabNo(0);
		}
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/09/2014, 10:09:45
	 * @param ctx
	 * @param tabParam
	 * @param field
	 */
	public Lookup(Context ctx, TabParameter tabParam, InfoField field) {
		this(ctx, tabParam, field, null);
	}
	
	/**
	 * With Column
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/09/2014, 12:19:50
	 * @param ctx
	 * @param m_SPS_Column_ID
	 * @param tabParam
	 */
	public Lookup(Context ctx, int m_SPS_Column_ID, TabParameter tabParam) {
		//	Load Field
		m_field = GridField.loadInfoColumnField(ctx, m_SPS_Column_ID);
		//	Set Property
		this.m_ctx = ctx;
		m_Language = Env.getAD_Language();
		m_IsBaseLanguage = Env.isBaseLanguage();
		ctx_lookup_value = CTX_VALUE_PREFIX + m_Language + "|" + m_field.SPS_Column_ID;
		ctx_lookup_has_where = CTX_HAS_WHERE + m_Language + "|" + m_field.SPS_Column_ID;
		ctx_lookup_info = CTX_LOOKUP_INFO_PREFIX + m_Language + "|" + m_field.SPS_Column_ID;
		m_TabParam = tabParam;
		//	Valid Null
		if(m_TabParam == null) {
			m_TabParam = new TabParameter();
			m_TabParam.setActivityNo(0);
			m_TabParam.setTabNo(0);
		}
	}
	
	/**
	 * Private Constructor
	 * With Table Name and column Name for lookup manual
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/09/2014, 21:08:52
	 * @param ctx
	 * @param m_TableName
	 * @param m_ColumnName
	 * @param activityParam
	 * @param tabParam
	 */
	private Lookup(Context ctx, String m_TableName, String m_ColumnName, ActivityParameter activityParam, TabParameter tabParam) {
		
		if(activityParam != null) {
			m_TabParam = new TabParameter();
			m_TabParam.setActivityNo(activityParam.getActivityNo());
		}
		//	Load Field
		m_field = GridField.loadInfoColumnField(ctx, m_TableName, m_ColumnName);
		//	Set Property
		this.m_ctx = ctx;
		m_Language = Env.getAD_Language();
		m_IsBaseLanguage = Env.isBaseLanguage();
		ctx_lookup_value = CTX_VALUE_PREFIX + m_Language + "|" + m_field.SPS_Column_ID;
		ctx_lookup_has_where = CTX_HAS_WHERE + m_Language + "|" + m_field.SPS_Column_ID;
		ctx_lookup_info = CTX_LOOKUP_INFO_PREFIX + m_Language + "|" + m_field.SPS_Column_ID;
		m_TabParam = tabParam;
		//	Valid Null
		if(m_TabParam == null) {
			m_TabParam = new TabParameter();
			m_TabParam.setActivityNo(0);
			m_TabParam.setTabNo(0);
		}
	}
	
	/**
	 * With Table Name and column Name for lookup manual
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/09/2014, 21:09:51
	 * @param ctx
	 * @param m_TableName
	 * @param m_ColumnName
	 * @param activityParam
	 */
	public Lookup(Context ctx, String m_TableName, String m_ColumnName, ActivityParameter activityParam) {
		this(ctx, m_TableName, m_ColumnName, activityParam, null);
	}
	
	/**
	 * With Table Name and column Name for lookup manual
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/09/2014, 21:10:38
	 * @param ctx
	 * @param m_TableName
	 * @param m_ColumnName
	 * @param tabParam
	 */
	public Lookup(Context ctx, String m_TableName, String m_ColumnName, TabParameter tabParam) {
		this(ctx, m_TableName, m_ColumnName, null, tabParam);
	}
	
	/**
	 * Get from Search
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/03/2014, 16:20:14
	 * @param ctx
	 * @param m_SPS_Table_ID
	 */
	public Lookup(Context ctx, int m_SPS_Table_ID) {
		this.m_SPS_Table_ID = m_SPS_Table_ID;
		this.m_ctx = ctx;
		m_Language = Env.getAD_Language();
		m_IsBaseLanguage = Env.isBaseLanguage();
		ctx_lookup_value = CTX_VALUE_PREFIX_TABLE + m_Language + "|" + m_SPS_Table_ID;
		ctx_lookup_has_where = CTX_HAS_WHERE_TABLE + m_Language + "|" + m_SPS_Table_ID;
		ctx_lookup_info = CTX_LOOKUP_INFO_PREFIX_TABLE + m_Language + "|" + m_SPS_Table_ID;
		m_TabParam = new TabParameter();
		m_TabParam.setActivityNo(0);
		m_TabParam.setTabNo(0);
	}
	
	/**	Field					*/
	private InfoField 							m_field 				= null;
	/**	Table					*/
	private int									m_SPS_Table_ID 			= 0;
	/**	Context					*/
	private Context								m_ctx 					= null;
	/**	Optional where clause	*/
	private String								m_OptionalWhereClause 	= null;
	/**	Validation Rule			*/
	private String 								m_ValRule				= null;
	/**	Lookup Information		*/
	private InfoLookup 							m_InfoLookup 			= null;
	/**	Is Loaded				*/
	private boolean 							m_IsLoaded				= false;
	/**	SQL						*/
	private String 								m_SQL					= null;
	/**	SQL Join				*/
	private StringBuffer						m_From					= null;
	/**	Language				*/
	private String 								m_Language 				= null;
	/**	Is Base Language		*/
	private boolean 							m_IsBaseLanguage 		= true;
	/**	Has Where				*/
	private boolean 							m_IsHasWhere			= false;
	/**	Syntax Error			*/
	private boolean 							isSyntaxError 			= false;
	/**	Display Lookup Spinner	*/
	private ArrayList<DisplayLookupSpinner> 	data 					= null;
	/**	Tab Parameter			*/
	protected TabParameter 						m_TabParam 				= null;
	/**	Table Alias				*/
	private String								m_TableAlias			= null;
	
	private String			ctx_lookup_value 		= null;
	private String 			ctx_lookup_has_where 	= null;
	private String 			ctx_lookup_info		 	= null;
	/**	Context Value Prefix	*/
	private final String	CTX_VALUE_PREFIX 				= "LK|C|";
	private final String	CTX_VALUE_PREFIX_TABLE			= "LK|T|";
	private final String	CTX_HAS_WHERE					= "LK|HW|C|";
	private final String	CTX_HAS_WHERE_TABLE				= "LK|HW|T|";
	private final String	CTX_LOOKUP_INFO_PREFIX 			= "LKI|C|";
	private final String	CTX_LOOKUP_INFO_PREFIX_TABLE	= "LKI|T|";
	private final String	MARK_WHERE						= "<MARK_WHERE>";
	
	/**	Constant to Inner	*/
	private final String 	LEFT_JOIN 					= "LEFT JOIN";
	private final String 	ON 							= "ON";
	private final String 	AND 						= "AND";
	private final String 	EQUAL 						= "=";
	private final String	POINT						= ".";
	private final String 	AS 							= "AS";
	private final String	ALIAS_PREFIX_IDENTIFIER		= "tda";
	private final String	DOC_STATUS					= "DocStatus";
	private final String	IS_ACTIVE					= "IsActive";
	
	/**
	 * Get Parsed SQL
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 23/05/2014, 15:08:05
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
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/03/2014, 15:31:15
	 * @return
	 * @return String
	 */
	public String getSQL() {
		//	Cache
		if(m_IsLoaded)
			return Env.parseContext(m_ctx, m_TabParam.getActivityNo(), m_TabParam.getTabNo(), getParsedSQL(m_SQL), false, null);
		//	
		boolean isCache = false;
		String sqlParsed = null;
		//	
		if(m_SPS_Table_ID == 0) {
			//	
			m_SQL = Env.getContext(ctx_lookup_value);
			isCache = m_SQL != null;
			//	Return Cache
			if(isCache) {
				m_IsHasWhere = Env.getContextAsBoolean(ctx_lookup_has_where);
				m_InfoLookup = (InfoLookup) Env.getContextObject(ctx_lookup_info, InfoLookup.class);
				//	From Cache Where Clause
				if(m_InfoLookup != null)
					LogM.log(m_ctx, getClass(), Level.FINE, "From Cache[Where Clause=" + m_InfoLookup.WhereClause + "]");
				//	Parse
				sqlParsed = getParsedSQL(m_SQL);
				//	
				LogM.log(m_ctx, getClass(), Level.FINE, "From Cache[SQL=" + sqlParsed + "]");
				return Env.parseContext(m_ctx, m_TabParam.getActivityNo(), m_TabParam.getTabNo(), sqlParsed, false, null);
			}
			//	Reload
			LogM.log(m_ctx, getClass(), Level.FINE, 
					"Reload Field=(" + m_field.ColumnName + ", " 
							+ m_field.DisplayType + ", " + m_field.AD_Reference_Value_ID + ")");
			//	
			if(m_field.DisplayType == DisplayType.TABLE_DIR) {
				m_SQL = loadSQLTableDirect();
				LogM.log(m_ctx, getClass(), Level.FINE, "SQLTableDirect=" + m_SQL);
			} else if((m_field.DisplayType == DisplayType.TABLE 
					|| m_field.DisplayType == DisplayType.SEARCH)
					&& m_field.AD_Reference_Value_ID != 0) {
				m_SQL = loadSQLTableSearch();
				LogM.log(m_ctx, getClass(), Level.FINE, "SQLTableSearch=" + m_SQL);
			} else if((m_field.DisplayType == DisplayType.LIST)
					&& m_field.AD_Reference_Value_ID != 0) {
				m_SQL = loadSQLList();
				LogM.log(m_ctx, getClass(), Level.FINE, "SQLList=" + m_SQL);
			} else {
				m_SQL = loadSQLTableDirect();
				LogM.log(m_ctx, getClass(), Level.FINE, "SQLTableDirectDefault=" + m_SQL);
			}
		} else {
			//	
			m_SQL = Env.getContext(ctx_lookup_value);
			isCache = m_SQL != null;
			//	Return Cache
			if(isCache) {
				m_IsHasWhere = Env.getContextAsBoolean(ctx_lookup_has_where);
				m_InfoLookup = (InfoLookup) Env.getContextObject(ctx_lookup_info, InfoLookup.class);
				//	From Cache Where Clause
				if(m_InfoLookup != null)
					LogM.log(m_ctx, getClass(), Level.FINE, "From Cache[Where Clause=" + m_InfoLookup.WhereClause + "]");
				//	Parse
				sqlParsed = getParsedSQL(m_SQL);
				//	
				LogM.log(m_ctx, getClass(), Level.FINE, "From Cache[SQL=" + sqlParsed + "]");
				return Env.parseContext(m_ctx, m_TabParam.getActivityNo(), m_TabParam.getTabNo(), sqlParsed, false, null);
			}
			//	
			m_SQL = loadFromTable();
			LogM.log(m_ctx, getClass(), Level.FINE, "SQL=" + m_SQL);
		}
		//	Set Is Loaded
		m_IsLoaded = true;
		//	Set to Cache
		Env.setContext(ctx_lookup_value, m_SQL);
		Env.setContext(ctx_lookup_has_where, m_IsHasWhere);
		Env.setContextObject(ctx_lookup_info, m_InfoLookup);
		//	Parse SQL
		sqlParsed = getParsedSQL(m_SQL);
		//	
		//	From Cache Where Clause
		if(m_InfoLookup != null)
			LogM.log(m_ctx, getClass(), Level.FINE, "Without Cache[Where Clause=" + m_InfoLookup.WhereClause + "]");
		//	
		LogM.log(m_ctx, getClass(), Level.FINE, "[SQL Without Cache=" + sqlParsed + "]");
		//	Return
		return Env.parseContext(m_ctx, m_TabParam.getActivityNo(), m_TabParam.getTabNo(), sqlParsed, false, null);
	}
	
	/**
	 * Get Info Lookup
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 21:00:23
	 * @return
	 * @return InfoLookup
	 */
	public InfoLookup getInfoLookup() {
		//	Get SQL
		if(m_InfoLookup == null) {
			m_InfoLookup = (InfoLookup) Env.getContextObject(ctx_lookup_info, InfoLookup.class);
			LogM.log(m_ctx, getClass(), Level.FINE, "[Get Lookup From Cache=" + m_InfoLookup + "]");
		}
		if(m_InfoLookup == null)
			getSQL();
		//	Load ending
		return m_InfoLookup;
	}
	
	/**
	 * Get Validation Rule
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/02/2014, 14:45:20
	 * @return
	 * @return String
	 */
	private String getValRule() {
		if(m_field.AD_Val_Rule_ID == 0)
			return "";
		//	Cache
		if(m_ValRule != null)
			return m_ValRule;
		//	
		String code = DB.getSQLValueString(m_ctx, "SELECT vr.Code " +
				"FROM AD_Val_Rule vr " +
				"WHERE AD_Val_Rule_ID = " + m_field.AD_Val_Rule_ID);
		//	Parse
		return code;
	}
	
	/**
	 * Get SQL from Table Direct
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/02/2014, 22:06:00
	 * @return String
	 */
	private String loadSQLTableDirect() {
		//	Instance Lookup
		m_InfoLookup = new InfoLookup();
		//	Instance join
		m_From = new StringBuffer();
		//	
		StringBuffer sql = new StringBuffer();
		StringBuffer where = new StringBuffer();
		String tableName = m_field.ColumnName.replaceAll("_ID", "");
		//	Set Info Lookup
		m_InfoLookup.TableName = tableName;
		m_InfoLookup.KeyColumn = new String[]{m_field.ColumnName};
		//	Set Table Alias
		if(m_TableAlias == null
				|| m_TableAlias.length() == 0)
			m_TableAlias = tableName;
		//	
		m_InfoLookup.TableAlias = m_TableAlias;
		//	SQL
		sql.append("SELECT ").append(m_TableAlias).append(".").append(m_field.ColumnName);
		//	
		DB conn = new DB(m_ctx);
		DB.loadConnection(conn, DB.READ_ONLY);
		Cursor rs = null;
		//	Query
		rs = conn.querySQL("SELECT c.ColumnName, COALESCE(ct.Name, c.Name) Name, c.SPS_Column_ID, c.AD_Reference_ID " +
				"FROM SPS_Table t " +
				"INNER JOIN SPS_Column c ON(c.SPS_Table_ID = t.SPS_Table_ID) " +
				"LEFT JOIN SPS_Column_Trl ct ON(ct.SPS_Column_ID = c.SPS_Column_ID AND ct.AD_Language = '" + m_Language + "') " +
				"WHERE t.TableName = ? " +
				"AND c.IsIdentifier = ? ORDER BY SeqNo", new String[]{tableName, "Y"});
		//	First
		boolean isFirst = true;
		//	Alias Identifier
		String aliasPrefix = ALIAS_PREFIX_IDENTIFIER + m_field.ColumnName;
		int aliasCount = 1;
		if(rs.moveToFirst()) {
			sql.append(", ");
			StringBuffer longColumn = new StringBuffer();
			do {
				String columnName = rs.getString(0);
				String name = rs.getString(1);
				int m_SPS_Column_ID = rs.getInt(2);
				int displayType = rs.getInt(3);
				//	Is First
				if(!isFirst) {
					longColumn.append("||");
				}
				//	
				longColumn.append("'")
					.append(InfoLookup.TABLE_SEARCH_SEPARATOR)
					.append("'||");
				//	
				if(DisplayType.isLookup(displayType)) {
					Lookup lookup = new Lookup(m_ctx, m_SPS_Column_ID, aliasPrefix + aliasCount++);
					InfoLookup infoLookup = lookup.getInfoLookup();
					//	Add to Display Column
					longColumn.append(infoLookup.DisplayColumn);
					//	Add Join
					addJoin(m_TableAlias, lookup.getField(), infoLookup);
				} else {
					m_InfoLookup.IdentifiesColumn.add(new IdentifierWrapper(displayType, name));
					longColumn.append("COALESCE(").append(m_TableAlias).append(".").append(columnName).append(",'')");
				}
				//	Set false
				if(isFirst)
					isFirst = false;
			} while(rs.moveToNext());
			//	
			sql.append(longColumn);
			//	Check Document Status
			int m_DocAction_ID = DB.getSQLValue(m_ctx, "SELECT c.SPS_Column_ID " +
										"FROM SPS_Column c " +
										"WHERE c.SPS_Table_ID = " + m_SPS_Table_ID + " " +
										"AND c.ColumnName = ?", DOC_STATUS);
			//	
			String lastColumn = IS_ACTIVE;
			//	Verify Last Column like Document status
			if(m_DocAction_ID > 0) {
				lastColumn = DOC_STATUS;
			}
			//	Add Column Last Column
			sql.append(", ").append(tableName).append(".").append(lastColumn);
			//	Set Info Lookup
			m_InfoLookup.DisplayColumn = longColumn.toString();
			m_InfoLookup.TableJoin = m_From.toString();
			//	
			//	Separator
		} else {
			sql.append(", ")
				.append(tableName).append(".").append(m_field.ColumnName);
		}
		//	Close
		DB.closeConnection(conn);
		sql.append(" FROM ").append(tableName)
				.append(" AS ").append(m_TableAlias);
		//	Add Joins
		if(m_From.length() > 0)
			sql.append(" ").append(m_From);
		
		//	Validation Rule
		if(getValRule() != null
				&& getValRule().length() > 0) {
			where.append(" WHERE ").append(getValRule());
			//	Add Mark
			m_IsHasWhere = true;
		} else {
			m_IsHasWhere = false;
		}
		//	
		m_InfoLookup.WhereClause = where.toString();
		//	
		where.append(MARK_WHERE);
		//	Add Where Clause to SQL
		sql.append(where);
		//	Return
		return sql.toString();
	}
	
	/**
	 * Get SQL from Reference Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/02/2014, 22:32:49
	 * @return String
	 */
	private String loadSQLTableSearch() {
		//	Instance Lookup
		m_InfoLookup = new InfoLookup();		
		//	
		StringBuffer sql = new StringBuffer();
		StringBuffer where = new StringBuffer();
		sql.append("SELECT ");
		DB conn = new DB(m_ctx);
		DB.loadConnection(conn, DB.READ_ONLY);
		Cursor rs = null;
		//	Query
		rs = conn.querySQL("SELECT t.TableName, ck.ColumnName, cd.ColumnName, " +
				"rl.IsValueDisplayed, rl.WhereClause, rl.OrderByClause, cd.AD_Reference_ID, COALESCE(ct.Name, cd.Name) Name " +
				"FROM AD_Ref_Table rl " +
				"INNER JOIN SPS_Table t ON(t.AD_Table_ID = rl.AD_Table_ID) " +
				"INNER JOIN SPS_Column ck ON(ck.AD_Column_ID = rl.AD_Key) " +
				"INNER JOIN SPS_Column cd ON(cd.AD_Column_ID = rl.AD_Display) " +
				"LEFT JOIN SPS_Column_Trl ct ON(ct.SPS_Column_ID = cd.SPS_Column_ID AND ct.AD_Language = '" + m_Language + "') " +
				"WHERE rl.AD_Reference_ID = " + m_field.AD_Reference_Value_ID, null);
		//	
		if(rs.moveToFirst()) {
			String tableName = rs.getString(0);
			String pkColumnName = rs.getString(1);
			String dColumnName = rs.getString(2);
			String isValueDisplayed = rs.getString(3);
			String whereClause = rs.getString(4);
			String orderByClause = rs.getString(5);
			int displayType = rs.getInt(6);
			String name = rs.getString(7);
			//	Close
			DB.closeConnection(conn);
			//	Set Lookup Info
			m_InfoLookup.TableName = tableName;
			m_InfoLookup.KeyColumn = new String[]{pkColumnName};
			//	Set Table Alias
			if(m_TableAlias == null
					|| m_TableAlias.length() == 0)
				m_TableAlias = tableName;
			//	
			m_InfoLookup.TableAlias = m_TableAlias;
			//	
			sql.append(m_TableAlias).append(".").append(pkColumnName).append(", ");
			//	Display Column
			StringBuffer longColumn = new StringBuffer();
			//	Display Value
			if(isValueDisplayed != null
					&& isValueDisplayed.equals("Y")) {
				longColumn.append("COALESCE(").append(m_TableAlias).append(".")
							.append("Value").append(", '')");
				//	
				longColumn.append("||");
			}
			//	Add Display Type
			longColumn.append("'")
				.append(InfoLookup.TABLE_SEARCH_SEPARATOR)
				.append("'||");
			//	Display Column
			longColumn.append("COALESCE(").append(m_TableAlias).append(".").append(dColumnName).append(",'')");
			sql.append(longColumn);
			//	Check Document Status
			int m_DocAction_ID = DB.getSQLValue(m_ctx, "SELECT c.SPS_Column_ID " +
										"FROM SPS_Column c " +
										"WHERE c.SPS_Table_ID = " + m_SPS_Table_ID + " " +
										"AND c.ColumnName = ?", DOC_STATUS);
			//	
			String lastColumn = IS_ACTIVE;
			//	Verify Last Column like Document status
			if(m_DocAction_ID > 0) {
				lastColumn = DOC_STATUS;
			}
			//	Add Column Last Column
			sql.append(", ").append(tableName).append(".").append(lastColumn);
			//	Set Info Lookup
			m_InfoLookup.DisplayColumn = longColumn.toString();
			//	Add To Meta-Data
			m_InfoLookup.IdentifiesColumn.add(new IdentifierWrapper(displayType, name));
			//	Separator
			sql.append(" FROM ").append(tableName)
					.append(" AS ").append(m_TableAlias);
			//	Where Clause
			if(whereClause != null
					&& whereClause.length() > 0)
				where.append(" WHERE ").append(whereClause);
			//	Set Where
			//	Validation Rule
			if(getValRule() != null
					&& getValRule().length() > 0) {
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
			//	Where
			m_InfoLookup.WhereClause = where.toString();
			//	Mark
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
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/02/2014, 10:54:32
	 * @return String
	 */
	private String loadSQLList() {
		//	Instance Lookup
		m_InfoLookup = new InfoLookup();
		//	Set Table Alias
		if(m_TableAlias == null
				|| m_TableAlias.length() == 0)
			m_TableAlias = InfoLookup.REF_LIST_TN;
		
		//	Set Lookup Info
		m_InfoLookup.KeyColumn = new String[]{"Value"};
		m_InfoLookup.TableName = InfoLookup.REF_LIST_TN;
		m_InfoLookup.TableAlias = m_TableAlias;
		//	Set SQL
		StringBuffer sql = new StringBuffer("SELECT ").append(m_TableAlias).append(".").append("Value, ");
		//	Add Display Type
		sql.append("'")
			.append(InfoLookup.TABLE_SEARCH_SEPARATOR)
			.append("'||");
		//	Handle Language
		if(m_IsBaseLanguage) {
			//	Add Column
			sql.append(m_TableAlias).append(".").append("Name ");
			//	From
			sql.append("FROM ").append(m_InfoLookup.TableName).append(" AS ").append(m_TableAlias).append(" ");
			//	Set Lookup Info
			m_InfoLookup.DisplayColumn = "COALESCE(" + m_TableAlias + ".Name,'')";
		} else {
			sql.append("COALESCE(").append(m_TableAlias).append(InfoLookup.TR_TABLE_SUFFIX).append(".").append("Name")
						.append(", ").append(m_TableAlias).append(".").append("Name").append(") Name ");
			//	From
			sql.append("FROM ").append(InfoLookup.REF_LIST_TN).append(" AS ").append(m_TableAlias);
			//	Join
			sql.append(" LEFT JOIN ").append(InfoLookup.REF_LIST_TN).append(InfoLookup.TR_TABLE_SUFFIX)
							.append(" AS ").append(m_TableAlias).append(InfoLookup.TR_TABLE_SUFFIX)
							.append(" ON(").append(m_TableAlias).append(InfoLookup.TR_TABLE_SUFFIX).append(".")
							.append("AD_Ref_List_ID = ").append(m_TableAlias).append(".").append("AD_Ref_List_ID")
							.append(" AND ").append(m_TableAlias).append(InfoLookup.TR_TABLE_SUFFIX)
							.append(".").append("AD_Language = '").append(m_Language).append("') ");
			//	Set Lookup Info
			m_InfoLookup.DisplayColumn = "COALESCE(" + 
											m_TableAlias + 
											InfoLookup.TR_TABLE_SUFFIX + 
											".Name,'')";
		}
		//	Where Clause			
		sql.append("WHERE ").append(m_TableAlias).append(".").append("AD_Reference_ID = ")
				.append(m_field.AD_Reference_Value_ID);
		//	Validation Rule
		if(getValRule() != null
				&& getValRule().length() > 0) {
			//	Add And
			sql.append(" AND ").append(getValRule());
			//	Set Where
			m_InfoLookup.WhereClause = getValRule();
		}
		//	Add Display Type
		String name = DB.getSQLValueString(m_ctx, "SELECT COALESCE(rlt.Name, rl.Name) Name "
				+ "FROM AD_Ref_List rl "
				+ "LEFT JOIN AD_Ref_List_Trl rlt ON(rlt.AD_Ref_List_ID = rl.AD_Ref_List_ID AND rlt.AD_Language = ?) "
				+ "WHERE rl.AD_Ref_List_ID = ?"
				, m_Language, String.valueOf(m_field.AD_Reference_Value_ID));
		//	Add
		m_InfoLookup.IdentifiesColumn.add(new IdentifierWrapper(DisplayType.STRING, name));
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
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/03/2014, 16:22:06
	 * @return
	 * @return String
	 */
	private String loadFromTable() {
		//	Instance Lookup
		m_InfoLookup = new InfoLookup();
		//	Instance join
		m_From = new StringBuffer();
		//	
		StringBuffer sql = new StringBuffer();
		String tableName = null;
		DB conn = new DB(m_ctx);
		DB.loadConnection(conn, DB.READ_ONLY);
		Cursor rs = null;
		boolean isParent = false;
		//	Query
		rs = conn.querySQL("SELECT t.TableName, c.ColumnName, COALESCE(ct.Name, c.Name) Name, c.SPS_Column_ID, c.AD_Reference_ID " +
				"FROM SPS_Table t " +
				"INNER JOIN SPS_Column c ON(c.SPS_Table_ID = t.SPS_Table_ID) " + 
				"LEFT JOIN SPS_Column_Trl ct ON(ct.SPS_Column_ID = c.SPS_Column_ID AND ct.AD_Language = '" + m_Language + "') " +
				"WHERE t.SPS_Table_ID = ? " +
				"AND c.IsIdentifier = ? " +
				"ORDER BY SeqNo", new String[]{String.valueOf(m_SPS_Table_ID), "Y"});
		//	Is Parent
		if(!rs.moveToFirst()) {
			isParent = true;
			rs = conn.querySQL("SELECT t.TableName, c.ColumnName, COALESCE(ct.Name, c.Name) Name, c.SPS_Column_ID, c.AD_Reference_ID " +
					"FROM SPS_Table t " +
					"INNER JOIN SPS_Column c ON(c.SPS_Table_ID = t.SPS_Table_ID) " +
					"LEFT JOIN SPS_Column_Trl ct ON(ct.SPS_Column_ID = c.SPS_Column_ID AND ct.AD_Language = '" + m_Language + "') " +
					"WHERE t.SPS_Table_ID = ? " +
					"AND (c.IsKey = ? OR c.IsParent = ?) " +
					"ORDER BY c.IsKey DESC", new String[]{String.valueOf(m_SPS_Table_ID), "Y", "Y"});
		}
		//	
		boolean isFirst = true;
		if(rs.moveToFirst()) {
			tableName = rs.getString(0);
			//	Set Info Lookup
			m_InfoLookup.TableName = tableName;
			ArrayList<String> keyColumns = new ArrayList<String>();
			if(!isParent)
				m_InfoLookup.KeyColumn = new String[]{tableName + "_ID"};
			//	Alias Identifier
			String aliasPrefix = ALIAS_PREFIX_IDENTIFIER + tableName;
			int aliasCount = 1;
			//	Display Type
			StringBuffer longColumn = new StringBuffer();
			do {
				String columnName = rs.getString(1);
				String name = rs.getString(2);
				int m_SPS_Column_ID = rs.getInt(3);
				int displayType = rs.getInt(4);
				//	
				if(isParent) {
					//	Add Key
					keyColumns.add(columnName);
					//	
					if(isFirst) {
						m_InfoLookup.KeyColumn = new String[]{columnName};
					}
				}
				//	Is First
				if(!isFirst) {
					longColumn.append("||");
				}
				//	
				longColumn.append("'")
					.append(InfoLookup.TABLE_SEARCH_SEPARATOR)
					.append("'||");
				//	
				if(DisplayType.isLookup(displayType)) {
					Lookup lookup = new Lookup(m_ctx, m_SPS_Column_ID, aliasPrefix + aliasCount++);
					InfoLookup infoLookup = lookup.getInfoLookup();
					//	Add to Display Column
					longColumn.append(infoLookup.DisplayColumn);
					//	Add Join
					addJoin(tableName, lookup.getField(), infoLookup);
				} else {
					m_InfoLookup.IdentifiesColumn.add(new IdentifierWrapper(displayType, name));
					longColumn.append("COALESCE(").append(tableName).append(".").append(columnName).append(",'')");
				}
				//	Set false
				if(isFirst)
					isFirst = false;
			}while(rs.moveToNext());
			//	
			//	Add Select
			sql.append("SELECT ");
			//	
			if(isParent) {
				m_InfoLookup.KeyColumn = new String[keyColumns.size()];
				keyColumns.toArray(m_InfoLookup.KeyColumn);
				//	Iterate KeyColumns
				for(String column : m_InfoLookup.KeyColumn) {
					sql.append(tableName).append(".")
						.append(column).append(", ");
				}
			} else {
				sql.append(tableName).append(".")
					.append(m_InfoLookup.KeyColumn[0]).append(", ");
			}
			//	Add Long Column
			sql.append(longColumn);
			//	Check Document Status
			int m_DocAction_ID = DB.getSQLValue(m_ctx, "SELECT c.SPS_Column_ID " +
										"FROM SPS_Column c " +
										"WHERE c.SPS_Table_ID = " + m_SPS_Table_ID + " " +
										"AND c.ColumnName = ?", DOC_STATUS);
			//	
			String lastColumn = IS_ACTIVE;
			//	Verify Last Column like Document status
			if(m_DocAction_ID > 0) {
				lastColumn = DOC_STATUS;
			}
			//	Add Column Last Column
			sql.append(", ").append(tableName).append(".").append(lastColumn);
			//	Set Info Lookup
			m_InfoLookup.DisplayColumn = longColumn.toString();
			m_InfoLookup.TableJoin = m_From.toString();
			//	Separator
		}
		//	Close
		DB.closeConnection(conn);
		sql.append(" FROM ").append(tableName);
		//	Add Joins
		if(m_From.length() > 0)
			sql.append(" ").append(m_From);
		//	Optional Where
		m_IsHasWhere = false;
		m_InfoLookup.WhereClause = null;
		sql.append(MARK_WHERE);
		LogM.log(m_ctx, getClass(), Level.FINE, "SQL (loadFromTable())= " + sql.toString());
		//	Return
		return sql.toString();
	}
	
	/**
	 * Add Join
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/09/2014, 21:06:48
	 * @param tableName
	 * @param linkColumn
	 * @param lookup
	 * @return void
	 */
	private void addJoin(String tableName, InfoField linkColumn, InfoLookup lookup) {
		//	Is Mandatory
		m_From.append(LEFT_JOIN).append(" ");
		//	Table Name
		m_From.append(lookup.TableName).append(" ").append(AS).append(" ").append(lookup.TableAlias).append(" ");
		//	On
		m_From.append(ON).append("(")
							.append(lookup.TableAlias).append(POINT).append(lookup.KeyColumn[0])
							.append(EQUAL).append(tableName).append(POINT).append(linkColumn.ColumnName);
		if(linkColumn.DisplayType == DisplayType.LIST) {
			m_From.append(" ").append(AND).append(" ")
								.append(lookup.TableAlias).append(POINT)
								.append(InfoLookup.REFERENCE_TN).append("_ID")
								.append(EQUAL).append(linkColumn.AD_Reference_Value_ID);
		}
		//	Add finish
		m_From.append(")").append(" ");
		//	Add Translation to List
		if(linkColumn.DisplayType == DisplayType.LIST
				&& !m_IsBaseLanguage) {
			m_From.append(LEFT_JOIN).append(" ");
			//	Table Name
			m_From.append(lookup.TableName).append(InfoLookup.TR_TABLE_SUFFIX).append(" ")
								.append(AS).append(" ").append(lookup.TableAlias).append(InfoLookup.TR_TABLE_SUFFIX).append(" ");
			//	On
			m_From.append(ON).append("(")
								.append(lookup.TableAlias).append(InfoLookup.TR_TABLE_SUFFIX)
								.append(POINT).append(InfoLookup.REF_LIST_TN).append("_ID")
								.append(EQUAL).append(lookup.TableAlias)
								.append(POINT).append(InfoLookup.REF_LIST_TN).append("_ID ")
								.append(AND).append(" ").append(lookup.TableAlias).append(InfoLookup.TR_TABLE_SUFFIX)
								.append(POINT).append(InfoLookup.AD_LANGUAGE_CN)
								.append(EQUAL).append("'").append(m_Language).append("'").append(")").append(" ");
		}
	}
	
	/**
	 * Set Optional where clause
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/03/2014, 22:24:26
	 * @param whereClause
	 * @return void
	 */
	public void setCriteria(String whereClause) {
		m_OptionalWhereClause = whereClause;
		m_IsLoaded = false;
	}

	/**
	 * Load Data
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 19/05/2014, 09:44:09
	 * @return void
	 */
	public void load() {
		load(true);
	}
	
	/**
	 * 
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 19/05/2014, 08:40:36
	 * @param reQuery
	 * @return void
	 */
	public void load(boolean reQuery) {
		//	Is Syntax Error
		if(isSyntaxError)
			return;
		try{
			//	Cache
			if(!reQuery
					&& data != null) {
				//	Set Adapter
				//if(v_Spinner.getAdapter() == null)
					//setAdapter(v_Spinner);
				return;
			}
			//	
			DB conn = new DB(m_ctx);
			//	
			DB.loadConnection(conn, DB.READ_ONLY);
			Cursor rs = null;
			//	Query
			rs = conn.querySQL(getSQL(), null);
			data = new ArrayList<DisplayLookupSpinner>();
			if(rs.moveToFirst()) {
				if(!m_field.IsMandatory) {
					if(m_field.DisplayType == DisplayType.LIST)
						data.add(new DisplayLookupSpinner(null, null));
					else
						data.add(new DisplayLookupSpinner(-1, null));
				}
				//	Loop
				do{
					String value = Env.parseLookup(m_ctx, m_InfoLookup, 
							rs.getString(1), 
							InfoLookup.TABLE_SEARCH_VIEW_SEPARATOR);
					if(m_field.DisplayType == DisplayType.LIST)
						data.add(new DisplayLookupSpinner(rs.getString(0), value));
					else
						data.add(new DisplayLookupSpinner(rs.getInt(0), value));
				}while(rs.moveToNext());
			}
			//	Close
			DB.closeConnection(conn);
		} catch(Exception e) {
			isSyntaxError = true;
			data = new ArrayList<DisplayLookupSpinner>();
			LogM.log(m_ctx, getClass(), Level.SEVERE, "Error in Load", e);
		}
	}
	
	/**
	 * Is Syntax Error
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/03/2014, 11:36:25
	 * @return
	 * @return boolean
	 */
	public boolean isSyntaxError() {
		return isSyntaxError;
	}
	
	/**
	 * Return Data Loaded
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/09/2014, 11:29:41
	 * @return
	 * @return ArrayList<DisplayLookupSpinner>
	 */
	public ArrayList<DisplayLookupSpinner> getData() {
		return data;
	}
	
	/**
	 * Get Field
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/09/2014, 11:39:13
	 * @return
	 * @return InfoField
	 */
	public InfoField getField() {
		return m_field;
	}
	
	/**
	 * Get Tab Parameter
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/09/2014, 11:54:05
	 * @return
	 * @return TabParameter
	 */
	public TabParameter getTabParameter() {
		return m_TabParam;
	}
	
	/**
	 * Get Validation
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/09/2014, 11:59:07
	 * @return
	 * @return String
	 */
	public String getValidation() {
		getInfoLookup();
		if(m_InfoLookup != null)
			return m_InfoLookup.WhereClause;
		//	Default
		return null;
	}
	
	@Override
	public String toString() {
		return "LookupDisplayType [m_field=" + m_field + ", m_SPS_Table_ID="
				+ m_SPS_Table_ID + ", ctx=" + m_ctx + ", m_optionalWhereClause="
				+ m_OptionalWhereClause + ", m_InfoLookup=" + m_InfoLookup
				+ ", m_IsLoaded=" + m_IsLoaded + ", m_SQL=" + m_SQL + "]";
	}
}