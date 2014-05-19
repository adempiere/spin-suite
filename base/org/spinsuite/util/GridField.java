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
package org.spinsuite.util;

import java.util.logging.Level;

import org.spinsuite.view.lookup.InfoField;
import org.spinsuite.view.lookup.VLookup;

/**
 * @author Yamel Senih
 *
 */
public class GridField {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 22:46:37
	 * @param v_lookup
	 */
	public GridField(VLookup v_lookup){
		this.v_lookup = v_lookup;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 22:06:18
	 * @param v_lookup
	 * @param columnIndex
	 */
	public GridField(VLookup v_lookup, int columnIndex){
		this.v_lookup = v_lookup;
		this.columnIndex = columnIndex;
	}
	
	private int 		columnIndex = -1;
	private VLookup 	v_lookup;
	
	/**
	 * Get Column Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 24/02/2014, 22:53:37
	 * @return
	 * @return String
	 */
	public String getColumnName(){
		//	
		if(v_lookup.getField() != null)
			return v_lookup.getField().ColumnName;
		return null;
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
	
	/**
	 * Get Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 12:04:01
	 * @return
	 * @return Object
	 */
	public Object getValue() {
		return v_lookup.getValue();
	}
	
	/**
	 * Get Value As String
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 12:14:23
	 * @return
	 * @return String
	 */
	public String getValueAsString() {
		//	Valid Field
		InfoField field = v_lookup.getField();
		if(field == null
				|| v_lookup.getValue() == null)
			return null;
		//	String Case
		if(DisplayType.isText(field.DisplayType))
			return (String) v_lookup.getValue();
		//	Boolean Value
		if(DisplayType.isBoolean(field.DisplayType)) { 
			Boolean bValue = (Boolean)v_lookup.getValue();
			return (bValue? "Y": "N");
		}
		//	Numeric
		else
			return String.valueOf(v_lookup.getValue());
	}
	
	/**
	 * Get Value As Boolean
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 12:19:46
	 * @return
	 * @return boolean
	 */
	public boolean getValueAsBoolean() {
		//	Valid Field
		InfoField field = v_lookup.getField();
		if(field == null
				|| v_lookup.getValue() == null)
			return false;
		//	String
		if(DisplayType.isText(field.DisplayType))
			return (String.valueOf(v_lookup.getValue()).equals("N")? 
									false: 
										true);
		else
			return false;
	}
	
	/**
	 * Get Value As Integer
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 12:27:14
	 * @return
	 * @return int
	 */
	public int getValueAsInt() {
		//	Valid Field
		InfoField field = v_lookup.getField();
		if(field == null
				|| v_lookup.getValue() == null)
			return 0;
		//	String
		if(DisplayType.isText(field.DisplayType)
				|| DisplayType.isNumeric(field.DisplayType)
				|| DisplayType.isID(field.DisplayType)
				|| DisplayType.isLookup(field.DisplayType)) {
			try {
				return Integer.parseInt((String) v_lookup.getValue());
			} catch (Exception e) {
				LogM.log(v_lookup.getContext(), this.getClass(), Level.SEVERE, "Parse Error", e);
			}
			return 0;
		} else
			return 0;
	}
	
	@Override
	public String toString() {
		return "ColumnName=" + (v_lookup.getField() != null? v_lookup.getField().ColumnName: "") + "\n" +
				"ColumnIndex=" + columnIndex + "\n" +
				"v_lookup=" + (v_lookup != null? v_lookup.toString(): "");
	}
}
