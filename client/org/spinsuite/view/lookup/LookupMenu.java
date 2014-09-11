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

import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.DisplayMenuItem;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class LookupMenu {

	/**	Main Menu Constant		*/
	public static final String MAIN_MENU 		= "M";
	/**	Activity Menu Constant	*/
	public static final String ACTIVITY_MENU 	= "A";
	/**	Quick Action Menu		*/
	public static final String QUICK_ACTION_MENU= "Q";
	
	/**	Menu Type				*/
	private String 						menuType 	= MAIN_MENU;
	/**	Connection				*/
	private DB 							conn		= null;
	/**	Context					*/
	private Context 					ctx 		= null;
	/**	Data					*/
	private ArrayList<DisplayMenuItem> 	data 		= null;
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/03/2014, 17:00:37
	 * @param m_MenuType
	 */
	public LookupMenu(Context ctx, String m_MenuType, DB conn) {
		this.ctx = ctx;
		this.menuType = m_MenuType;
		this.conn = conn;
	}
	
	/**
	 * Load Children of parent
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/03/2014, 17:02:15
	 * @param parent_ID
	 * @return boolean
	 */
	public boolean loadChildren(int parent_ID){
		boolean loaded = false;
		//	
		boolean handleConnection = false;
		if(conn == null){
			conn = new DB(ctx);
			handleConnection = true;
		}
		//	Load Connection
		DB.loadConnection(conn, DB.READ_ONLY);
		//	
		String language = Env.getAD_Language(ctx);
		boolean isBaseLanguage = Env.isBaseLanguage(ctx);
		//	Get Role
		int m_AD_Role_ID = Env.getAD_Role_ID(ctx);
		//	SQL
		StringBuffer sql = new StringBuffer();
		//	if Base Language
		if(isBaseLanguage){
			sql.append("SELECT m.SPS_Menu_ID, m.Name, m.Description, m.Action, m.ImageURL, " +
					"m.SPS_Table_ID, m.WhereClause, m.GroupByClause, m.OrderByClause, " +
					"tn.Parent_ID, m.IsSummary, m.DeploymentType, m.AD_Form_ID, m.SPS_Window_ID, m.AD_Process_ID, " +
					"m.ActivityMenu_ID, COALESCE(m.IsReadWrite, pa.IsReadWrite, wa.IsReadWrite) IsReadWrite, " +
					"m.IsInsertRecord, tn.SeqNo " +
					"FROM SPS_Menu m " +
					"INNER JOIN AD_Tree t ON(t.AD_Table_ID = 53518) " +
					"LEFT JOIN AD_TreeNode tn ON(tn.AD_Tree_ID = t.AD_Tree_ID AND tn.Node_ID = m.SPS_Menu_ID)" +
					"LEFT JOIN AD_Process_Access pa ON(pa.AD_Process_ID = m.AD_Process_ID) " +
					"LEFT JOIN SPS_Window_Access wa ON(wa.SPS_Window_ID = m.SPS_Window_ID) ");
		} else {
			sql.append("SELECT m.SPS_Menu_ID, mt.Name, mt.Description, m.Action, m.ImageURL, " +
					"m.SPS_Table_ID, m.WhereClause, m.GroupByClause, m.OrderByClause, " +
					"tn.Parent_ID, m.IsSummary, m.DeploymentType, m.AD_Form_ID, m.SPS_Window_ID, m.AD_Process_ID, " +
					"m.ActivityMenu_ID, COALESCE(m.IsReadWrite, pa.IsReadWrite, wa.IsReadWrite) IsReadWrite, " +
					"m.IsInsertRecord, tn.SeqNo " +
					"FROM SPS_Menu m " +
					"INNER JOIN AD_Tree t ON(t.AD_Table_ID = 53518) " +
					"INNER JOIN SPS_Menu_Trl mt ON(mt.SPS_Menu_ID = m.SPS_Menu_ID AND mt.AD_Language = '").append(language).append("') " +
					"LEFT JOIN AD_TreeNode tn ON(tn.AD_Tree_ID = t.AD_Tree_ID AND tn.Node_ID = m.SPS_Menu_ID)" +
					"LEFT JOIN AD_Process_Access pa ON(pa.AD_Process_ID = m.AD_Process_ID) " +
					"LEFT JOIN SPS_Window_Access wa ON(wa.SPS_Window_ID = m.SPS_Window_ID) ");
		}
		//	Where Clause
		//	Process Access Role
		sql.append("WHERE (pa.AD_Role_ID = ").append(m_AD_Role_ID).append(" ");
		//	Window Access Role
		sql.append("OR wa.AD_Role_ID = ").append(m_AD_Role_ID).append(") ");
		//	
		sql.append("AND m.MenuType = ? " +
				"AND m.IsActive = 'Y' " +
				"AND (" +
				"		(m.AD_Process_ID IS NOT NULL AND pa.AD_Process_ID IS NOT NULL AND pa.IsActive = 'Y') " +
				"		OR (m.SPS_Window_ID IS NOT NULL AND wa.SPS_Window_ID IS NOT NULL AND wa.IsActive = 'Y')" +
				"	) " +
				"AND tn.Parent_ID = ").append(parent_ID).append(" ");
		//	If is context menu
		if(!menuType.equals("M"))
			sql.append("AND m.IsSummary = ").append("'N'").append(" ");
		//	Order By
		sql.append("ORDER BY tn.SeqNo");
		LogM.log(ctx, getClass(), Level.FINE, "SQL=" + sql);
		LogM.log(ctx, getClass(), Level.FINE, "MenuType=" + menuType);
		Cursor rs = conn.querySQL(sql.toString(), new String[]{menuType});
		data = new ArrayList<DisplayMenuItem>();
		if(rs.moveToFirst()){
			do {
				int i = 0;
				data.add(new DisplayMenuItem(
						rs.getInt(i++), 
						rs.getString(i++), 
						rs.getString(i++), 
						rs.getString(i++), 
						rs.getString(i++), 
						rs.getInt(i++), 
						rs.getString(i++), 
						rs.getString(i++), 
						rs.getString(i++), 
						rs.getInt(i++), 
						rs.getString(i++).equals("Y"), 
						rs.getString(i++), 
						rs.getInt(i++), 
						rs.getInt(i++), 
						rs.getInt(i++), 
						rs.getInt(i++), 
						rs.getString(i++), 
						rs.getString(i++),
						rs.getInt(i++)));
			}while(rs.moveToNext());
			//	Is Loaded
			loaded = true;
		}
		//	Close Connection
		if(handleConnection)
			DB.closeConnection(conn);
		return loaded;
	}
	
	/**
	 * Get Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/03/2014, 17:33:02
	 * @return
	 * @return ArrayList<DisplayMenuItem>
	 */
	public ArrayList<DisplayMenuItem> getData(){
		return data;
	}
	
}
