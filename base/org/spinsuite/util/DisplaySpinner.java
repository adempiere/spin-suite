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
 * @author Yamel Senih
 *
 */
public class DisplaySpinner {
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 10:22:51
	 * @param m_ID
	 * @param value
	 */
	public DisplaySpinner(int m_ID, String value){
		this.m_ID = m_ID;
		this.value = value;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 10:23:01
	 * @param m_ID
	 * @param value
	 * @param hiddenValue
	 */
	public DisplaySpinner(int m_ID, String value, Object hiddenValue){
		this.m_ID = m_ID;
		this.value = value;
		this.hiddenValue = hiddenValue;
	}
	
	/**	ID Column		*/
	private int m_ID = 0;
	/**	Name of Column	*/
	private String value = null;
	/**	Name 2			*/
	private Object hiddenValue = null;
	
	
	/**
	 * Get Identifier from selected item
	 * @author Yamel Senih 26/04/2012, 11:07:20
	 * @return
	 * @return int
	 */
	public int getID(){
		return m_ID;
	}
	
	/**
	 * Get Identifier as String
	 * @author Yamel Senih 11/05/2012, 15:58:25
	 * @return
	 * @return String
	 */
	public String getStringFromID(){
		return String.valueOf(m_ID);
	}
	
	/**
	 * Get value from item
	 * @author Yamel Senih 26/04/2012, 11:08:08
	 * @return
	 * @return String
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * Get hidden value
	 * @author Yamel Senih 01/05/2012, 02:58:59
	 * @return
	 * @return Object
	 */
	public Object getHiddenValue(){
		return hiddenValue;
	}
	
	/**
	 * Set Identifier
	 * @author Yamel Senih 26/04/2012, 11:08:56
	 * @param m_ID
	 * @return void
	 */
	public void setID(int m_ID){
		this.m_ID = m_ID;
	}
	
	/**
	 * Set Value
	 * @author Yamel Senih 26/04/2012, 11:09:46
	 * @param value
	 * @return void
	 */
	public void setValue(String value){
		this.value = value;
	}
	
	/**
	 * Set Hidden Value
	 * @author Yamel Senih 01/05/2012, 02:58:06
	 * @param hiddenValue
	 * @return void
	 */
	public void setHiddenValue(Object hiddenValue){
		this.hiddenValue = hiddenValue;
	}
	
	@Override
	public String toString(){
		return value;
	}
}
