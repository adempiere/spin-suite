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
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/09/2014, 23:44:28
	 * @param m_TableName
	 * @param m_KeyColumn
	 * @param m_DisplayColumn
	 * @param m_WhereClause
	 */
	public InfoLookup(String m_TableName, String m_KeyColumn, String m_DisplayColumn, String m_WhereClause) {
		this.TableName = m_TableName;
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

	/**	Table Name			*/
	public String 		TableName 		= null;
	/**	Key Column			*/
	public String 		KeyColumn 		= null;
	/**	Display Column		*/
	public String 		DisplayColumn 	= null;
	/**	Where Clause		*/
	public String		WhereClause 	= null;
	
	@Override
	public String toString() {
		return "InfoLookup [TableName=" + TableName + ", KeyColumn="
				+ KeyColumn + ", DisplayColumn=" + DisplayColumn
				+ ", WhereClause=" + WhereClause + "]";
	}
}
