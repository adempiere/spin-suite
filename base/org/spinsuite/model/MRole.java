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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.content.Context;
import android.database.Cursor;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Aug 16, 2015, 4:40:58 AM
 *
 */
public class MRole extends X_AD_Role {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param AD_Role_ID
	 * @param conn
	 */
	public MRole(Context ctx, int AD_Role_ID, DB conn) {
		super(ctx, AD_Role_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MRole(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * Get Default Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return MRole
	 */
	public static MRole getDefault() {
		return getDefault(Env.getCtx(), false);
	}
	
	/**
	 * Get Default Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param reload
	 * @return
	 * @return MRole
	 */
	public static MRole getDefault(Context ctx, boolean reload) {
		int AD_Role_ID = Env.getContextAsInt(ctx, "#AD_Role_ID");
		//	Valid Role
		if(m_Intance == null
				|| reload
				|| m_Intance.getAD_Role_ID() != AD_Role_ID) {
			m_Intance = new MRole(ctx, AD_Role_ID, null);
		}
		return m_Intance;
	}	//	getDefault
	
	/**	Access SQL Read Write						*/
	public static final boolean		SQL_RW = true;
	/**	Access SQL Read Only						*/
	public static final boolean		SQL_RO = false;
	/**	Access SQL Fully Qualified					*/
	public static final boolean		SQL_FULLYQUALIFIED = true;
	/**	Access SQL Not Fully Qualified				*/
	public static final boolean		SQL_NOTQUALIFIED = false;

	/**	The AD_User_ID of the SuperUser				*/
	public static final int			SUPERUSER_USER_ID = 100;
	/**	The AD_User_ID of the System Administrator	*/
	public static final int			SYSTEM_USER_ID = 0;

	/** User 										*/
	private int						m_AD_User_ID = -1;	

	/**	Positive List of Organizational Access		*/	
	private OrgAccess[]				m_orgAccess = null;
	/**	Role Instance								*/
	private static MRole			m_Intance = null;
	
	/**
	 * 	Set Logged in user
	 *	@param AD_User_ID user requesting info
	 */
	public void setAD_User_ID(int AD_User_ID)
	{
		m_AD_User_ID = AD_User_ID;
	}	//	setAD_User_ID

	/**
	 * 	Get Logged in user
	 *	@return AD_User_ID user requesting info
	 */
	public int getAD_User_ID()
	{
		return m_AD_User_ID;
	}	//	getAD_User_ID

	
	/**************************************************************************
	 * 	Load Access Info
	 * 	@param reload re-load from disk
	 */
	public void loadAccess (boolean reload) {
		loadOrgAccess(reload);
	}	//	loadAccess

	/**
	 * 	Load Org Access
	 *	@param reload reload
	 */
	private void loadOrgAccess (boolean reload) {
		if (!(reload || m_orgAccess == null))
			return;
		//
		ArrayList<OrgAccess> list = new ArrayList<OrgAccess>();

		if (isUseUserOrgAccess())
			loadOrgAccessUser(list);
		else
			loadOrgAccessRole(list);
		
		m_orgAccess = new OrgAccess[list.size()];
		list.toArray(m_orgAccess); 
		LogM.log(getCtx(), getClass(), Level.FINE, "#" + m_orgAccess.length + (reload ? " - reload" : "")); 
//		if (Ini.isClient())
//		{
//			StringBuffer sb = new StringBuffer();
//			for (int i = 0; i < m_orgAccess.length; i++)
//			{
//				if (i > 0)
//					sb.append(",");
//				sb.append(m_orgAccess[i].AD_Org_ID);
//			}
//			Env.setContext(Env.getCtx(), "#User_Org", sb.toString());
//		}
	}	//	loadOrgAccess

	/**
	 * 	Load Org Access User
	 *	@param list list
	 */
	private void loadOrgAccessUser(ArrayList<OrgAccess> list) {
		String whereClause = new String("AD_User_ID=? AND IsActive='Y'");
		//	Get Sequences
		List<MUserOrgAccess> m_UserOrgAccess = new Query(getCtx(), I_AD_User_OrgAccess.Table_Name, whereClause, null)
			.setParameters(getAD_User_ID())
			.setOrderBy(I_AD_User_OrgAccess.COLUMNNAME_IsReadOnly)
			.<MUserOrgAccess>list();
		//	Valid and Add
		if(m_UserOrgAccess != null
				&& m_UserOrgAccess.size() > 0) {
			for(MUserOrgAccess userOrgAccess : m_UserOrgAccess) {
				loadOrgAccessAdd (list, new OrgAccess(userOrgAccess.getAD_Client_ID(), 
						userOrgAccess.getAD_Org_ID(), userOrgAccess.isReadOnly()));
			}
		}
	}	//	loadOrgAccessRole

	/**
	 * 	Load Org Access Role
	 *	@param list list
	 */
	private void loadOrgAccessRole(ArrayList<OrgAccess> list) {
		String whereClause = new String("AD_Role_ID=? AND IsActive='Y'");
		//	Get Sequences
		List<MRoleOrgAccess> m_UserOrgAccess = new Query(getCtx(), I_AD_Role_OrgAccess.Table_Name, whereClause, null)
			.setParameters(getAD_Role_ID())
			.setOrderBy(I_AD_Role_OrgAccess.COLUMNNAME_IsReadOnly)
			.<MRoleOrgAccess>list();
		//	Valid and Add
		if(m_UserOrgAccess != null
				&& m_UserOrgAccess.size() > 0) {
			for(MRoleOrgAccess roleOrgAccess : m_UserOrgAccess) {
				loadOrgAccessAdd (list, new OrgAccess(roleOrgAccess.getAD_Client_ID(), 
						roleOrgAccess.getAD_Org_ID(), roleOrgAccess.isReadOnly()));
			}
		}
	}	//	loadOrgAccessRole
	
	/**
	 * 	Load Org Access Add Tree to List
	 *	@param list list
	 *	@param oa org access
	 *	@see org.compiere.util.Login
	 */
	private void loadOrgAccessAdd (ArrayList<OrgAccess> list, OrgAccess oa) {
		if (list.contains(oa))
			return;
		list.add(oa);
		//	Do we look for trees?
//		if (getAD_Tree_Org_ID() == 0)
//			return;
//		MOrg org = MOrg.get(getCtx(), oa.AD_Org_ID);
//		if (!org.isSummary())
//			return;
//		//	Summary Org - Get Dependents
//		MTree_Base tree = MTree_Base.get(getCtx(), getAD_Tree_Org_ID(), get_TrxName());
//		String sql =  "SELECT AD_Client_ID, AD_Org_ID FROM AD_Org "
//			+ "WHERE IsActive='Y' AND AD_Org_ID IN (SELECT Node_ID FROM "
//			+ tree.getNodeTableName()
//			+ " WHERE AD_Tree_ID=? AND Parent_ID=? AND IsActive='Y')";
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try
//		{
//			pstmt = DB.prepareStatement (sql, get_TrxName());
//			pstmt.setInt (1, tree.getAD_Tree_ID());
//			pstmt.setInt(2, org.getAD_Org_ID());
//			rs = pstmt.executeQuery ();
//			while (rs.next ())
//			{
//				int AD_Client_ID = rs.getInt(1);
//				int AD_Org_ID = rs.getInt(2);
//				loadOrgAccessAdd (list, new OrgAccess(AD_Client_ID, AD_Org_ID, oa.readOnly));
//			}
//		}
//		catch (Exception e)
//		{
//			log.log (Level.SEVERE, sql, e);
//		}
//		finally
//		{
//			DB.close(rs, pstmt);
//			rs = null; pstmt = null;
//		}
	}	//	loadOrgAccessAdd
	
	/**************************************************************************
	 * 	Get Client Where Clause Value 
	 * 	@param rw read write
	 * 	@return "AD_Client_ID=0" or "AD_Client_ID IN(0,1)"
	 */
	public String getClientWhere (boolean rw) {
		//	All Orgs - use Client of Role
		if (isAccessAllOrgs()) {
			if (rw || getAD_Client_ID() == 0)
				return "AD_Client_ID=" + getAD_Client_ID();
			return "AD_Client_ID IN (0," + getAD_Client_ID() + ")";
		}

		//	Get Client from Org List
		loadOrgAccess (false);
		//	Unique Strings
		HashSet<String> set = new HashSet<String>();
		if (!rw)
			set.add("0");
		//	Positive List
		for (int i = 0; i < m_orgAccess.length; i++)
			set.add(String.valueOf(m_orgAccess[i].AD_Client_ID));
		//
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = set.iterator();
		boolean oneOnly = true;
		while (it.hasNext()) {
			if (sb.length() > 0) {
				sb.append(",");
				oneOnly = false;
			}
			sb.append(it.next());
		}
		//	
		if (oneOnly) {
			if (sb.length() > 0)
				return "AD_Client_ID=" + sb.toString();
			else {
				LogM.log(getCtx(), getClass(), Level.SEVERE, "No Access Org records");
				return "AD_Client_ID=-1";	//	No Access Record
			}
		}
		return "AD_Client_ID IN(" + sb.toString() + ")";
	}	//	getClientWhereValue
	
	/**
	 * 	Access to Client
	 *	@param AD_Client_ID client
	 *	@param rw read write access
	 *	@return true if access
	 */
	public boolean isClientAccess(int AD_Client_ID, boolean rw) {
		if (AD_Client_ID == 0 && !rw)	//	can always read System
			return true;
		//
		// Check Access All Orgs:
		if (isAccessAllOrgs()) {
			// User has access to given AD_Client_ID if the role is defined on that AD_Client_ID
			return getAD_Client_ID() == AD_Client_ID;
		}
		//
		loadOrgAccess(false);
		//	Positive List
		for (int i = 0; i < m_orgAccess.length; i++) {
			if (m_orgAccess[i].AD_Client_ID == AD_Client_ID) {
				if (!rw)
					return true;
				if (!m_orgAccess[i].readOnly)	//	rw
					return true;
			}
		}
		return false;
	}	//	isClientAccess
	
	/**
	 * 	Get Org Where Clause Value 
	 * 	@param rw read write
	 * 	@return "AD_Org_ID=0" or "AD_Org_ID IN(0,1)" or null (if access all org)
	 */
	public String getOrgWhere (boolean rw) {
		if (isAccessAllOrgs())
			return null;
		loadOrgAccess(false);
		//	Unique Strings
		HashSet<String> set = new HashSet<String>();
		if (!rw)
			set.add("0");
		//	Positive List
		for (int i = 0; i < m_orgAccess.length; i++) {
			if (!rw)
				set.add(String.valueOf(m_orgAccess[i].AD_Org_ID));
			else if (!m_orgAccess[i].readOnly)	//	rw
				set.add(String.valueOf(m_orgAccess[i].AD_Org_ID));
		}
		//
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = set.iterator();
		boolean oneOnly = true;
		while (it.hasNext()) {
			if (sb.length() > 0)
			{
				sb.append(",");
				oneOnly = false;
			}
			sb.append(it.next());
		}
		if (oneOnly) {
			if (sb.length() > 0)
				return "AD_Org_ID=" + sb.toString();
			else {
				LogM.log(getCtx(), getClass(), Level.SEVERE, "No Access Org records");
				return "AD_Org_ID=-1";	//	No Access Record
			}
		}		
		return "AD_Org_ID IN(" + sb.toString() + ")";
	}	//	getOrgWhereValue
	
	/**
	 * 	Access to Org
	 *	@param AD_Org_ID org
	 *	@param rw read write access
	 *	@return true if access
	 */
	public boolean isOrgAccess(int AD_Org_ID, boolean rw) {
		if (isAccessAllOrgs())
			return true;
		if (AD_Org_ID == 0 && !rw)		//	can always read common org
			return true;
		loadOrgAccess(false);
		
		//	Positive List
		for (int i = 0; i < m_orgAccess.length; i++) {
			if (m_orgAccess[i].AD_Org_ID == AD_Org_ID) {
				if (!rw)
					return true;
				if (!m_orgAccess[i].readOnly)	//	rw
					return true;
				return false;
			}
		}
		return false;
	}	//	isOrgAccess
	
	/*************************************************************************
	 *	Appends where clause to SQL statement for Table
	 *
	 *	@param SQL			existing SQL statement
	 *	@param TableNameIn	Table Name or list of table names AAA, BBB or AAA a, BBB b
	 *	@param fullyQualified	fullyQualified names
	 *	@param rw			if false, includes System Data
	 *	@return				updated SQL statement
	 */
	public String addAccessSQL (String SQL, String TableNameIn, 
		boolean fullyQualified, boolean rw) {
		StringBuffer retSQL = new StringBuffer();

		//	Cut off last ORDER BY clause
		String orderBy = "";
		int posOrder = SQL.lastIndexOf(" ORDER BY ");
		if (posOrder != -1) {
			orderBy = SQL.substring(posOrder);
			retSQL.append(SQL.substring(0, posOrder));
		} else
			retSQL.append(SQL);

		//	Parse SQL
		AccessSqlParser asp = new AccessSqlParser(retSQL.toString());
		AccessSqlParser.TableInfo[] ti = asp.getTableInfo(asp.getMainSqlIndex()); 

		//  Do we have to add WHERE or AND
		if (asp.getMainSql().indexOf(" WHERE ") == -1)
			retSQL.append(" WHERE ");
		else
			retSQL.append(" AND ");

		//	Use First Table
		String tableName = "";
		if (ti.length > 0) {
			tableName = ti[0].getSynonym();
			if (tableName.length() == 0)
				tableName = ti[0].getTableName();
		}
		if (TableNameIn != null && !tableName.equals(TableNameIn)) {
			String msg = "TableName not correctly parsed - TableNameIn=" 
				+ TableNameIn + " - " + asp;
			if (ti.length > 0)
				msg += " - #1 " + ti[0]; 
			msg += "\n = " + SQL;
			LogM.log(getCtx(), getClass(), Level.SEVERE, msg);
			tableName = TableNameIn;
		}

//		if (! tableName.equals(X_AD_PInstance_Log.Table_Name)) { // globalqss, bug 1662433 
			//	Client Access
		if (fullyQualified)
			retSQL.append(tableName).append(".");
		retSQL.append(getClientWhere(rw));

		//	Org Access
		if (!isAccessAllOrgs()) {
			retSQL.append(" AND ");
			if (fullyQualified)
				retSQL.append(tableName).append(".");
			retSQL.append(getOrgWhere(rw));
		}
//		} else {
//			retSQL.append("1=1");
//		}
		
//		//	** Data Access	**
//		for (int i = 0; i < ti.length; i++) {
//			String TableName = ti[i].getTableName();
//			
//			//[ 1644310 ] Rev. 1292 hangs on start
//			if (TableName.toUpperCase().endsWith("_TRL")) continue;
//			if (isView(TableName)) continue;
//			
//			int AD_Table_ID = getAD_Table_ID (TableName);
//			//	Data Table Access
//			if (AD_Table_ID != 0 && !isTableAccess(AD_Table_ID, !rw)) {
//				retSQL.append(" AND 1=3");	//	prevent access at all
//				log.fine("No access to AD_Table_ID=" + AD_Table_ID 
//					+ " - " + TableName + " - " + retSQL);
//				break;	//	no need to check further 
//			}
//			
//			//	Data Column Access
//	
//	
//		
//			//	Data Record Access
//			String keyColumnName = "";
//			if (fullyQualified) {
//				keyColumnName = ti[i].getSynonym();	//	table synonym
//				if (keyColumnName.length() == 0)
//					keyColumnName = TableName;
//				keyColumnName += ".";
//			}
//			//keyColumnName += TableName + "_ID";	//	derived from table
//			if (getIdColumnName(TableName) == null) continue;
//			keyColumnName += getIdColumnName(TableName); 
//	
//			//log.fine("addAccessSQL - " + TableName + "(" + AD_Table_ID + ") " + keyColumnName);
//			String recordWhere = getRecordWhere (AD_Table_ID, keyColumnName, rw);
//			if (recordWhere.length() > 0)
//			{
//				retSQL.append(" AND ").append(recordWhere);
//				log.finest("Record access - " + recordWhere);
//			}
//		}	//	for all table info
//		
//		//	Dependent Records (only for main SQL)
//		String mainSql = asp.getMainSql();
//		loadRecordAccess(false);
//		int AD_Table_ID = 0;
//		String whereColumnName = null;
//		ArrayList<Integer> includes = new ArrayList<Integer>();
//		ArrayList<Integer> excludes = new ArrayList<Integer>();
//		for (int i = 0; i < m_recordDependentAccess.length; i++)
//		{
//			String columnName = m_recordDependentAccess[i].getKeyColumnName
//				(asp.getTableInfo(asp.getMainSqlIndex()) );
//			if (columnName == null)
//				continue;	//	no key column
//			
//			if (mainSql.toUpperCase().startsWith("SELECT COUNT(*) FROM ")) {
//				// globalqss - Carlos Ruiz - [ 1965744 ] Dependent entities access problem
//				// this is the count select, it doesn't have the column but needs to be filtered
//				 MTable table = MTable.get(getCtx(), tableName);
//				 if (table == null)
//					 continue;
//				 MColumn column = table.getColumn(columnName);
//				 if (column == null || column.isVirtualColumn() || !column.isActive())
//					 continue;
//			} else {
//				int posColumn = mainSql.indexOf(columnName);
//				if (posColumn == -1)
//					continue;
//				//	we found the column name - make sure it's a column name
//				char charCheck = mainSql.charAt(posColumn-1);	//	before
//				if (!(charCheck == ',' || charCheck == '.' || charCheck == ' ' || charCheck == '('))
//					continue;
//				charCheck = mainSql.charAt(posColumn+columnName.length());	//	after
//				if (!(charCheck == ',' || charCheck == ' ' || charCheck == ')'))
//					continue;
//			}
//			
//			if (AD_Table_ID != 0 && AD_Table_ID != m_recordDependentAccess[i].getAD_Table_ID())
//				retSQL.append(getDependentAccess(whereColumnName, includes, excludes));
//			
//			AD_Table_ID = m_recordDependentAccess[i].getAD_Table_ID();
//			//	*** we found the column in the main query
//			if (m_recordDependentAccess[i].isExclude())
//			{
//				excludes.add(m_recordDependentAccess[i].getRecord_ID());
//				log.fine("Exclude " + columnName + " - " + m_recordDependentAccess[i]);
//			}
//			else if (!rw || !m_recordDependentAccess[i].isReadOnly())
//			{
//				includes.add(m_recordDependentAccess[i].getRecord_ID());
//				log.fine("Include " + columnName + " - " + m_recordDependentAccess[i]);
//			}
//			whereColumnName = getDependentRecordWhereColumn (mainSql, columnName);
//		}	//	for all dependent records
//		retSQL.append(getDependentAccess(whereColumnName, includes, excludes));
		//
		retSQL.append(orderBy);
		LogM.log(getCtx(), getClass(), Level.FINEST, retSQL.toString());
		return retSQL.toString();
	}	//	addAccessSQL

	/**
	 * 	Get Dependent Access 
	 *	@param whereColumnName column
	 *	@param includes ids to include
	 *	@param excludes ids to exclude
	 *	@return where clause starting with AND or ""
	 */
//	private String getDependentAccess(String whereColumnName,
//		ArrayList<Integer> includes, ArrayList<Integer> excludes) {
//		if (includes.size() == 0 && excludes.size() == 0)
//			return "";
//		if (includes.size() != 0 && excludes.size() != 0)
//			LogM.log(getCtx(), getClass(), Level.WARNING, "Mixing Include and Excluse rules - Will not return values");
//		
//		StringBuffer where = new StringBuffer(" AND ");
//		if (includes.size() == 1)
//			where.append(whereColumnName).append("=").append(includes.get(0));
//		else if (includes.size() > 1) {
//			where.append(whereColumnName).append(" IN (");
//			for (int ii = 0; ii < includes.size(); ii++) {
//				if (ii > 0)
//					where.append(",");
//				where.append(includes.get(ii));
//			}
//			where.append(")");
//		}
//		else if (excludes.size() == 1) {
//			where.append("(" + whereColumnName + " IS NULL OR ");
//			where.append(whereColumnName).append("<>").append(excludes.get(0)).append(")");
//		}
//		else if (excludes.size() > 1) {
//			//@Trifon - MySQL
//			// (C_PaymentTerm_ID IS NULL OR 
//			where.append("(").append(whereColumnName).append(" IS NULL OR "); // @Trifon
//			where.append(whereColumnName).append(" NOT IN (");
//			for (int ii = 0; ii < excludes.size(); ii++) {
//				if (ii > 0)
//					where.append(",");
//				where.append(excludes.get(ii));
//			}
//			where.append(")");
//			where.append(")"); // @Trifon
//		}
//		LogM.log(getCtx(), getClass(), Level.FINEST, where.toString());
//		return where.toString();
//	}	//	getDependentAccess
	
	
	/**
	 * 	Get Dependent Record Where clause
	 *	@param mainSql sql to examine
	 *	@param columnName columnName
	 *	@return where clause column "x.columnName"
	 */
//	private String getDependentRecordWhereColumn (String mainSql, String columnName) {
//		String retValue = columnName;	//	if nothing else found
//		int index = mainSql.indexOf(columnName);
//		if (index == -1)
//			return retValue;
//		//	see if there are table synonym
//		int offset = index - 1;
//		char c = mainSql.charAt(offset);
//		if (c == '.') {
//			StringBuffer sb = new StringBuffer();
//			while (c != ' ' && c != ',' && c != '(') {
//				sb.insert(0, c);
//				c = mainSql.charAt(--offset);
//			}
//			sb.append(columnName);
//			return sb.toString();
//		}
//		return retValue;
//	}	//	getDependentRecordWhereColumn



	/**
	 *	UPADATE - Can I Update the record.
	 *  Access error info (AccessTableNoUpdate) is saved in the log
	 * 
	 * @param AD_Client_ID comntext to derive client/org/user level
	 * @param AD_Org_ID number of the current window to retrieve context
	 * @param AD_Table_ID table
	 * @param Record_ID record id
	 * @param createError boolean
	 * @return true if you can update
	 * see org.compiere.model.MTable#dataSave(boolean)
	 **/
	public boolean canUpdate (int AD_Client_ID, int AD_Org_ID, 
		int AD_Table_ID, int Record_ID, boolean createError) {
		String userLevel = getUserLevel();	//	Format 'SCO'

		if (userLevel.indexOf('S') != -1)	//	System cannot change anything
			return true;

		boolean	retValue = true;
		String whatMissing = "";

		//	System == Client=0 & Org=0
		if (AD_Client_ID == 0 && AD_Org_ID == 0
			&& userLevel.charAt(0) != 'S') {
			retValue = false;
			whatMissing += "S";
		}

		//	Client == Client!=0 & Org=0
		else if (AD_Client_ID != 0 && AD_Org_ID == 0
			&& userLevel.charAt(1) != 'C') {
			if (userLevel.charAt(2) == 'O' && isOrgAccess(AD_Org_ID, true))
				;	//	Client+Org with access to *
			else
			{
				retValue = false;
				whatMissing += "C";
			}
		}

		//	Organization == Client!=0 & Org!=0
		else if (AD_Client_ID != 0 && AD_Org_ID != 0
			&& userLevel.charAt(2) != 'O') {
			retValue = false;
			whatMissing += "O";
		}

		// Client Access: Verify if the role has access to the given client - teo_sarca, BF [ 1982398 ]
		if (retValue) {
			retValue = isClientAccess(AD_Client_ID, true); // r/w access
		}
		
		// Org Access: Verify if the role has access to the given organization - teo_sarca, patch [ 1628050 ]
		if (retValue) {
			retValue = isOrgAccess(AD_Org_ID, true); // r/w access
			whatMissing="W";
		}
		
		//	Data Access
//		if (retValue)
//			retValue = isTableAccess(AD_Table_ID, false);
//		
//		if (retValue && Record_ID != 0)
//			retValue = isRecordAccess(AD_Table_ID, Record_ID, false);
//		
		if (!retValue && createError) {
			LogM.log(getCtx(), getClass(), Level.WARNING, "AccessTableNoUpdat AD_Client_ID=" + AD_Client_ID 
					+ ", AD_Org_ID=" + AD_Org_ID + ", UserLevel=" + userLevel
					+ " => missing=" + whatMissing);
			LogM.log(getCtx(), getClass(), Level.WARNING, toString());
		}
		return retValue;
	}	//	canUpdate

	/**
	 *	VIEW - Can I view record in Table with given TableLevel.
	 *  <code>
	 *	TableLevel			S__ 100		4	System info
	 *						SCO	111		7	System shared info
	 *						SC_ 110		6	System/Client info
	 *						_CO	011		3	Client shared info
	 *						_C_	011		2	Client shared info
	 *						__O	001		1	Organization info
	 *  </code>
	 * 
	 * 	@param ctx	context
	 *	@param TableLevel	AccessLevel
	 *	@return	true/false
	 *  Access error info (AccessTableNoUpdate, AccessTableNoView) is saved in the log
	 *  see org.compiere.model.MTabVO#loadTabDetails(MTabVO, ResultSet)
	 **/
	public boolean canView(Context ctx, String TableLevel) {
		String userLevel = getUserLevel();	//	Format 'SCO'

		boolean retValue = true;

		//	7 - All
		if (X_AD_Table.ACCESSLEVEL_All.equals(TableLevel))
			retValue = true;
			 
		//	4 - System data requires S
		else if (X_AD_Table.ACCESSLEVEL_SystemOnly.equals(TableLevel) 
			&& userLevel.charAt(0) != 'S')
			retValue = false;

		//	2 - Client data requires C
		else if (X_AD_Table.ACCESSLEVEL_ClientOnly.equals(TableLevel) 
			&& userLevel.charAt(1) != 'C')
			retValue = false;

		//	1 - Organization data requires O
		else if (X_AD_Table.ACCESSLEVEL_Organization.equals(TableLevel) 
			&& userLevel.charAt(2) != 'O')
			retValue = false;

		//	3 - Client Shared requires C or O
		else if (X_AD_Table.ACCESSLEVEL_ClientPlusOrganization.equals(TableLevel)
			&& (!(userLevel.charAt(1) == 'C' || userLevel.charAt(2) == 'O')) )
				retValue = false;

		//	6 - System/Client requires S or C
		else if (X_AD_Table.ACCESSLEVEL_SystemPlusClient.equals(TableLevel)
			&& (!(userLevel.charAt(0) == 'S' || userLevel.charAt(1) == 'C')) )
			retValue = false;

		if (retValue)
			return retValue;

		//  Notification
		/**
		if (forInsert)
			log.saveWarning("AccessTableNoUpdate",
				"(Required=" + TableLevel + "("
				+ getTableLevelString(Env.getAD_Language(ctx), TableLevel)
				+ ") != UserLevel=" + userLevel);
		else
		**/
		LogM.log(getCtx(), getClass(), Level.WARNING, "AccessTableNoView Required=" + TableLevel + "("
				+ getTableLevelString(Env.getAD_Language(ctx), TableLevel)
				+ ") != UserLevel=" + userLevel);
		LogM.log(getCtx(), getClass(), Level.INFO, toString());
		return retValue;
	}	//	canView


	/**
	 *	Returns clear text String of TableLevel
	 *  @param AD_Language language
	 *  @param TableLevel level
	 *  @return info
	 */
	private String getTableLevelString (String AD_Language, String TableLevel) {
		String level = TableLevel + "??";
		if (TableLevel.equals("1"))
			level = "AccessOrg";
		else if (TableLevel.equals("2"))
			level = "AccessClient";
		else if (TableLevel.equals("3"))
			level = "AccessClientOrg";
		else if (TableLevel.equals("4"))
			level = "AccessSystem";
		else if (TableLevel.equals("6"))
			level = "AccessSystemClient";
		else if (TableLevel.equals("7"))
			level = "AccessShared";
		//	
		return Msg.getMsg(getCtx(), AD_Language, level);
	}	//	getTableLevelString

	/**
	 * 	Get Table ID from name
	 *	@param tableName table name
	 *	@return AD_Table_ID or 0
	 */
//	private int getAD_Table_ID (String tableName)
//	{
//		loadTableInfo(false);
//		Integer ii = (Integer)m_tableName.get(tableName);
//		if (ii != null)
//			return ii.intValue();
//	//	log.log(Level.WARNING,"getAD_Table_ID - not found (" + tableName + ")");
//		return 0;
//	}	//	getAD_Table_ID
	
	/**
	 * 	Show (Value) Preference Menu
	 *	@return true if preference type is not None
	 */
	public boolean isShowPreference() {
		return !X_AD_Role.PREFERENCETYPE_None.equals(getPreferenceType());
	}	//	isShowPreference
}