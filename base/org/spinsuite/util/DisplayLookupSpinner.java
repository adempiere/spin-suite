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

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class DisplayLookupSpinner {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 10:27:04
	 * @param m_ID
	 * @param m_Value
	 */
	public DisplayLookupSpinner(Object m_ID, Object m_Value) {
		this.m_ID = m_ID;
		this.m_Value = m_Value;
	}

	/**	Identifier		*/
	private Object 		m_ID;
	/**	Value			*/
	private Object 		m_Value;
	
	/**
	 * Get Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 10:27:44
	 * @return
	 * @return Object
	 */
	public Object getID(){
		return m_ID;
	}
	
	/**
	 * Get Identifier as String
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 10:29:44
	 * @return
	 * @return String
	 */
	public String getIDToString(){
		if(m_ID != null
				&& String.valueOf(m_ID).length() > 0)
			return String.valueOf(m_ID);
		return null;
	}
	
	/**
	 * Get Identifier as Integer
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 10:31:03
	 * @return
	 * @return int
	 */
	public int getIDAsInteger(){
		if(m_ID != null
				&& String.valueOf(m_ID).length() > 0
				&& m_ID instanceof Integer)
			return (Integer) m_ID;
		return -1;
	}
	
	/**
	 * Get Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 10:31:41
	 * @return
	 * @return Object
	 */
	public Object getValue() {
		if(m_Value != null)
			return m_Value;
		return "";		
	}
	
	/**
	 * Get Value as String
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 10:32:25
	 * @return
	 * @return String
	 */
	public String getValueAsString() {
		if(m_Value != null){
			if(m_Value instanceof String)
				return (String) m_Value;
			else if(m_Value instanceof Integer)
				return String.valueOf(m_Value);
		}
		return "";
	}
	
	@Override
	public String toString() {
		return getValueAsString();
	}
	
}
