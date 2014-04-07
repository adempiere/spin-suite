/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                                           *
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
package org.spinsuite.util;

import org.spinsuite.view.lookup.VLookup;

/**
 * @author Yamel Senih
 *
 */
public class ViewIndex {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 22:46:37
	 * @param v_lookup
	 * @param ColumnName
	 */
	public ViewIndex(VLookup v_lookup, String columnName){
		this.v_lookup = v_lookup;
		this.columnName = columnName;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 22:06:18
	 * @param v_lookup
	 * @param columnName
	 * @param columnIndex
	 */
	public ViewIndex(VLookup v_lookup, String columnName, int columnIndex){
		this.v_lookup = v_lookup;
		this.columnName = columnName;
		this.columnIndex = columnIndex;
	}
	
	private int 		columnIndex = -1;
	private	String		columnName = null;
	private VLookup 	v_lookup;
	
	/**
	 * Get Column Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 22:53:37
	 * @return
	 * @return String
	 */
	public String getColumnName(){
		return columnName;
	}
	
	/**
	 * Get Column Index
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 22:05:31
	 * @return
	 * @return int
	 */
	public int getColumnIndex() {
		return columnIndex;
	}
	
	/**
	 * Get Lookup
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 22:47:01
	 * @return
	 * @return VLookup
	 */
	public VLookup getVLookup(){
		return v_lookup;
	}
	
	@Override
	public String toString() {
		return "ColumnName=" + (columnName != null? columnName: "") + "\n" +
				"ColumnIndex=" + columnIndex + "\n" +
				"v_lookup=" + (v_lookup != null? v_lookup.toString(): "");
	}
}
