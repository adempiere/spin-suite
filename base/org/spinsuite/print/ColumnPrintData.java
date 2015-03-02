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
package org.spinsuite.print;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class ColumnPrintData {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/03/2014, 11:42:07
	 * @param m_Value
	 * @param m_Suffix
	 */
	public ColumnPrintData(String m_Value, String m_Suffix) {
		this.m_Value = m_Value;
		this.m_Suffix = m_Suffix;
	}
	
	/**	Prefix				*/
	private String 	m_Value 		= null;
	/**	Suffix				*/
	private String 	m_Suffix 		= null;
	/**	Function Name		*/
	private String 	m_FunctionValue = null;
	
	/**
	 * Get Prefix
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 18:02:38
	 * @return
	 * @return String
	 */
	public String getValue() {
		return m_Value;
	}
	
	/**
	 * Get Suffix
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 24/03/2014, 18:02:50
	 * @return
	 * @return String
	 */
	public String getSuffix() {
		return m_Suffix;
	}
	
	/**
	 * Get Function Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/04/2014, 10:05:30
	 * @return
	 * @return String
	 */
	public String getFunctionValue(){
		return m_FunctionValue;
	}
	
	/**
	 * Set Function Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/04/2014, 10:07:11
	 * @param m_FunctionName
	 * @return void
	 */
	public void setFunctionValue(String m_FunctionName){
		this.m_FunctionValue = m_FunctionName;
	}
	/**
	 * Set Prefix
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/03/2014, 18:00:53
	 * @param m_Value
	 * @return void
	 */
	public void setValue(String m_Value){
		this.m_Value = m_Value;
	}
	
	/**
	 * Set Suffix
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 28/03/2014, 18:01:02
	 * @param m_Suffix
	 * @return void
	 */
	public void setSuffix(String m_Suffix){
		this.m_Suffix = m_Suffix;
	}

	@Override
	public String toString() {
		return "ColumnPrintData [m_Value=" + m_Value + ", m_Suffix=" + m_Suffix
				+ ", m_FunctionValue=" + m_FunctionValue + "]";
	}
}
