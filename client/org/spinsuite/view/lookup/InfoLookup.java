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

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class InfoLookup {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 20:44:35
	 */
	public InfoLookup(){
		//	
	}

	/**	Table Name					*/
	public String 			TableName 				= null;
	/**	Key Column					*/
	public String[] 		KeyColumn 				= null;
	/**	Display Column				*/
	public String 			DisplayColumn 			= null;
	/**	Where Clause				*/
	public String			WhereClause 			= null;
	/**	Table Alias					*/
	public String 			TableAlias				= null;
	/**	Join with Tables			*/
	public String 			TableJoin				= null;
	
	
	/**	Reference (Table Name)		*/
	public static final String 	REFERENCE_TN		 			= "AD_Reference";
	/**	Reference List (Table Name)	*/
	public static final String 	REF_LIST_TN 					= "AD_Ref_List";
	/**	Reference Table (Table Name)*/
	public static final String 	REF_TABLE_TN 					= "AD_Ref_Table";
	/**	Reference (Column Name)		*/
	public static final String 	AD_LANGUAGE_CN 					= "AD_Language";
	/**	Translation Add				*/
	public static final String 	TR_TABLE_SUFFIX 				= "_Trl";
	/**	Table Search mark Separator	*/
	public static final String	TABLE_SEARCH_SEPARATOR			= "<MARK_SEPARATOR>";
	/**	Table Search Separator	*/
	public static final String	TABLE_SEARCH_VIEW_SEPARATOR		= "_";
	
	@Override
	public String toString() {
		return "InfoLookup [TableName=" + TableName 
				+ ", DisplayColumn=" + DisplayColumn
				+ ", WhereClause=" + WhereClause + ", TableAlias=" + TableAlias
				+ ", TableJoin=" + TableJoin + "]";
	}
}