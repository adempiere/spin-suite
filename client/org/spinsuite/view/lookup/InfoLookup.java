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

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class InfoLookup {

	/**
	 * Full
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/09/2014, 10:45:05
	 * @param m_TableName
	 * @param m_TableAlias
	 * @param m_KeyColumn
	 * @param m_DisplayColumn
	 * @param m_WhereClause
	 */
	public InfoLookup(String m_TableName, String m_TableAlias, String m_KeyColumn, String m_DisplayColumn, String m_WhereClause) {
		this.TableName = m_TableName;
		this.TableAlias = m_TableAlias;
		this.KeyColumn = m_KeyColumn;
		this.DisplayColumn = m_DisplayColumn;
		this.WhereClause = m_WhereClause;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/03/2014, 20:44:35
	 */
	public InfoLookup(){
		//	
	}

	/**	Table Name					*/
	public String 			TableName 				= null;
	/**	Key Column					*/
	public String 			KeyColumn 				= null;
	/**	Display Column				*/
	public String 			DisplayColumn 			= null;
	/**	Where Clause				*/
	public String			WhereClause 			= null;
	/**	Table Alias					*/
	public String 			TableAlias				= null;
	/**	Join with Tables			*/
	public String 			TableJoin				= null;
	
	
	/**	Reference (Table Name)		*/
	public static final String 	REFERENCE_TN		 	= "AD_Reference";
	/**	Reference List (Table Name)	*/
	public static final String 	REF_LIST_TN 			= "AD_Ref_List";
	/**	Reference Table (Table Name)*/
	public static final String 	REF_TABLE_TN 			= "AD_Ref_Table";
	/**	Reference (Column Name)		*/
	public static final String 	AD_LANGUAGE_CN 			= "AD_Language";
	/**	Translation Add				*/
	public static final String 	TR_TABLE_SUFFIX 		= "_Trl";
	/**	Table Search mark Separator	*/
	public static final String	TABLE_SEARCH_SEPARATOR	= "<MARK_SEPARATOR>";
	
	@Override
	public String toString() {
		return "InfoLookup [TableName=" + TableName + ", KeyColumn="
				+ KeyColumn + ", DisplayColumn=" + DisplayColumn
				+ ", WhereClause=" + WhereClause + ", TableAlias=" + TableAlias
				+ ", TableJoin=" + TableJoin + "]";
	}
}