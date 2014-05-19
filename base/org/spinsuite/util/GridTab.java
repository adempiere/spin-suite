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

import java.util.ArrayList;

import org.spinsuite.view.lookup.VLookup;


/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class GridTab {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 11:47:36
	 */
	public GridTab() {
		m_Fields = new ArrayList<GridField>();
	}
	
	/**	Fields					*/
	private ArrayList<GridField> m_Fields = null;
	
	/**
	 * Add Field to Grid Tab
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 11:48:33
	 * @param v_lookup
	 * @return void
	 */
	public void addField(VLookup v_lookup, String columnName) {
		addField(v_lookup, -1);
	}
	
	/**
	 * Add Field to Grid Tab with index
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 11:49:39
	 * @param v_lookup
	 * @param columnIndex
	 * @return void
	 */
	public void addField(VLookup v_lookup, int columnIndex) {
		m_Fields.add(new GridField(v_lookup, columnIndex));
	}
	
	/**
	 * Get Value from key
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 11:55:03
	 * @param columnName
	 * @return
	 * @return Object
	 */
	public Object getValue(String columnName){
		for(GridField field : m_Fields) {
			if(field.getColumnName().equals(columnName))
				return null;
		}
		return null;
	}
}
