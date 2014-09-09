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
package org.spinsuite.print;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.ReportSortColumnPair;
import org.spinsuite.view.lookup.InfoField;
import org.spinsuite.view.lookup.InfoLookup;
import org.spinsuite.view.lookup.Lookup;

import android.content.Context;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class ReportPrintQuery {
	
	/**
	 * With Criteria
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2014, 16:40:47
	 * @param ctx
	 * @param m_AD_PrintFormat_ID
	 * @param conn
	 */
	public ReportPrintQuery(Context ctx, int m_AD_PrintFormat_ID, DB conn) {
		this.ctx = ctx;
		//	Load Report Information
		m_IR = new InfoReport(ctx, m_AD_PrintFormat_ID, conn);
		m_orderColumns = new ArrayList<ReportSortColumnPair>();
		m_AD_Language = Env.getAD_Language(ctx);
		m_IsBaseLanguage = Env.isBaseLanguage(ctx);
	}
	
	/**	Context								*/
	private Context 							ctx = null;
	/**	Info Report							*/
	private InfoReport 							m_IR = null;
	/**	SQL									*/
	private StringBuffer						m_SQL = null;
	/**	Where Clause						*/
	private StringBuffer						m_from = null;
	/**	Columns Order By					*/
	private ArrayList<ReportSortColumnPair> 	m_orderColumns = null;
	/**	Columns								*/
	private ArrayList<InfoReportField>			m_columns = null;
	/**	Column Quantity						*/
	private int 								m_columnQty = 0;
	/**	Language							*/
	private String 								m_AD_Language = "en_US";
	/**	Is Base Language					*/
	private boolean 							m_IsBaseLanguage = true;
	/**	Mark with where clause				*/
	private final String						MARK_WHERE = "<MARK_WHERE>";
	
	/**	Constant to Inner	*/
	private final String 	INNER_JOIN 	= "INNER JOIN";
	private final String 	LEFT_JOIN 	= "LEFT JOIN";
	private final String	FROM		= "FROM";
	private final String 	ON 			= "ON";
	private final String 	AND 		= "AND";
	private final String 	EQUAL 		= "=";
	private final String	POINT		= ".";
	private final String 	ORDER_BY 	= "ORDER BY";
	private final String 	WHERE 		= "WHERE";
	private final String 	AS 			= "AS";
	private final String	ALIAS_PREFIX= "dc";
	
	/**
	 * Get Table Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/03/2014, 17:45:06
	 * @return
	 * @return String
	 */
	public String getTableName(){
		return m_IR.getTableName();
	}
	
	/**
	 * Get Report SQL
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 12:09:46
	 * @param whereClause
	 * @return
	 * @return String
	 */
	public String getSQL(String whereClause){
		boolean hasWhereClause = (whereClause != null && whereClause.length() > 0);
		loadQuery(hasWhereClause);
		//	append Where Clause
		if(!hasWhereClause)
			return m_SQL.toString().replaceAll(MARK_WHERE, "");
		//	Default return
		return m_SQL.toString().replace(MARK_WHERE, whereClause);
	}
	
	/**
	 * Get Report Information
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 12:10:32
	 * @return
	 * @return InfoReport
	 */
	public InfoReport getInfoReport(){
		return m_IR;
	}
	
	/**
	 * Contain a where
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 12:11:23
	 * @return
	 * @return boolean
	 */
	public boolean isWhere(){
		return m_IR.getWhereClause() != null 
				&& m_IR.getWhereClause().length() > 0;
	}
	
	/**
	 * Get Column Quantity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 15:34:24
	 * @return
	 * @return int
	 */
	public int getColumnQty(){
		return m_columnQty;
	}
	
	/**
	 * Get Columns
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 15:44:47
	 * @return
	 * @return InfoReportField[]
	 */
	public InfoReportField[] getColumns(){
		InfoReportField[] columns = new InfoReportField[m_columns.size()];
		m_columns.toArray(columns);
		return columns;
	}
	
	/**
	 * Load Query for report
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 21:41:02
	 * @param hasWhereClause
	 * @return void
	 */
	private void loadQuery(boolean hasWhereClause) {
		//	Reload
		m_SQL = new StringBuffer("SELECT ");
		String m_tableName = m_IR.getTableName();
		//	
		boolean isFirst = true;
		//	From
		m_from = new StringBuffer(" ")
						.append(FROM)
						.append(" ")
						.append(m_IR.getTableName()).append(" ");
		//	Iterate
		int columnIndex = 1;
		//	New Columns
		m_columns = new ArrayList<InfoReportField>();
		//	Iterate
		for(InfoReportField field : m_IR.getInfoReportFields()){
			//	Get Lookup
			InfoLookup lookup = getInfoLookup(field);
			//	Get Lookup Field
			String lookupField = getLookupColumn(m_tableName, field, lookup);
			lookupField = "(" + lookupField + ")";
			//	Add to Query
			if(field.IsPrinted){
				String alias = ALIAS_PREFIX + columnIndex++;
				//	Add Alias
				lookupField = lookupField + " " + AS + " " + alias;
				//	Add Separator
				if(!isFirst)
					m_SQL.append(", ");
				//	Add Column
				m_SQL.append(lookupField);
				//	Add Join
				if(DisplayType.isLookup(field.DisplayType))
					addJoin(m_tableName, field, lookup, field.IsMandatory);
				//	Not is first
				if(isFirst)
					isFirst = false;
				//	Add to Sort Columns
				if(field.IsOrderBy)
					m_orderColumns.add(new ReportSortColumnPair(field.SortNo, alias));
				//	Update Column Quantity
				m_columnQty++;
				//	Add Columns
				m_columns.add(field);
			} else {
				if(field.IsOrderBy)
					m_orderColumns.add(new ReportSortColumnPair(field.SortNo, lookupField));
			}
		}
		//	Sort by Sort No
		Collections.sort(m_orderColumns);
		//	Add Sort
		isFirst = true;
		StringBuffer m_OrderBy = new StringBuffer();
		for(ReportSortColumnPair sortField : m_orderColumns){
			//	Add Separator
			if(!isFirst)
				m_OrderBy.append(", ");
			//	Add Field to Order By
			m_OrderBy.append(sortField);
			//	Not is first
			if(isFirst)
				isFirst = false;			
		}
		//	Add From
		m_SQL.append(m_from).append(" ");
		//	Add Where Clause
		if(m_IR.getWhereClause() != null
				&& m_IR.getWhereClause().length() > 0){
			m_SQL.append(WHERE).append(" ")
				.append(m_IR.getWhereClause()).append(" ");
			//	Criteria
			if(hasWhereClause)
				m_SQL.append("AND ").append(MARK_WHERE).append(" ");
		} else if(hasWhereClause)
			m_SQL.append(WHERE).append(" ")
				.append(MARK_WHERE).append(" ");
		//	Set Order By
		if(m_OrderBy.length() > 0)
			m_SQL.append(ORDER_BY).append(" ").append(m_OrderBy);
		//	Log
		LogM.log(ctx, getClass(), Level.FINE, "SQL Report Reloaded=[" + m_SQL.toString() + "]");
	}
	
	/**
	 * Get Lookup Column from Field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/03/2014, 00:01:26
	 * @param tableName
	 * @param field
	 * @param lookup
	 * @return
	 * @return String
	 */
	private String getLookupColumn(String tableName, InfoReportField field, InfoLookup lookup){
		String lookupField = new String();
		if(DisplayType.isLookup(field.DisplayType)){
			//	Set Column
			if(lookup.DisplayColumn != null)
				lookupField = lookup.DisplayColumn;
			else
				lookupField = tableName + "." + field.ColumnName;
		} else if(DisplayType.isDate(field.DisplayType)){
			lookupField = "(strftime('%s', " + tableName + POINT + field.ColumnName + ")*1000)";
		} else {
			lookupField = tableName + POINT + field.ColumnName;
		}
		LogM.log(ctx, getClass(), Level.FINE, "getLookupColumn(" + tableName + ", " 
								+ field.ColumnName + " - " + field.SPS_Column_ID + ") = " + lookupField);
		//	Return
		return lookupField;
	}
	
	/**
	 * Get Lookup Column Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 20:33:33
	 * @param field
	 * @return
	 * @return InfoLookup
	 */
	private InfoLookup getInfoLookup(InfoReportField field){
		if(!DisplayType.isLookup(field.DisplayType))
			return null;
		//	Do it
		InfoField lookupField = new InfoField(field);
		Lookup m_lookup = new Lookup(ctx, lookupField);
		return m_lookup.getInfoLookup();
	}
	
	/**
	 * Add Jo to from
	 * @authoinr <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 21:11:45
	 * @param linkColumn
	 * @param lookup
	 * @param isMandatory
	 * @return void
	 */
	private void addJoin(String tableName, InfoReportField linkColumn, InfoLookup lookup, boolean isMandatory){
		//	Is Mandatory
		if(isMandatory)
			m_from.append(INNER_JOIN).append(" ");
		else
			m_from.append(LEFT_JOIN).append(" ");
		//	Table Name
		m_from.append(lookup.TableName).append(" ");
		//	On
		m_from.append(ON).append("(")
							.append(lookup.TableName).append(POINT).append(lookup.KeyColumn)
							.append(EQUAL).append(tableName).append(POINT).append(linkColumn.ColumnName);
		if(linkColumn.DisplayType == DisplayType.LIST) {
			m_from.append(" ").append(AND).append(" ")
								.append(lookup.TableName).append(POINT)
								.append(InfoLookup.REFERENCE_TN).append("_ID")
								.append(EQUAL).append(linkColumn.AD_Reference_Value_ID);
		}
		//	Add finish
		m_from.append(")").append(" ");
		//	Add Translation to List
		if(linkColumn.DisplayType == DisplayType.LIST
				&& !m_IsBaseLanguage) {
			if(isMandatory)
				m_from.append(INNER_JOIN).append(" ");
			else
				m_from.append(LEFT_JOIN).append(" ");
			//	Table Name
			m_from.append(lookup.TableName).append("_Trl ");
			//	On
			m_from.append(ON).append("(")
								.append(InfoLookup.REF_LIST_TN).append("_Trl")
								.append(POINT).append(InfoLookup.REF_LIST_TN).append("_ID")
								.append(EQUAL).append(InfoLookup.REF_LIST_TN)
								.append(POINT).append(InfoLookup.REF_LIST_TN).append("_ID ")
								.append(AND).append(" ").append(InfoLookup.REF_LIST_TN).append("_Trl")
								.append(POINT).append(InfoLookup.AD_LANGUAGE_CN)
								.append(EQUAL).append("'").append(m_AD_Language).append("'").append(")").append(" ");
		}
	}
}
