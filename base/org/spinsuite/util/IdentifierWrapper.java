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
package org.spinsuite.util;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Jul 14, 2015, 8:54:22 PM
 *
 */
public class IdentifierWrapper {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_DisplayType
	 * @param p_Name
	 */
	public IdentifierWrapper(int p_DisplayType, String p_Name) {
		m_DisplayType = p_DisplayType;
		m_Name = p_Name;
	}
	
	/**	Display Type		*/
	private int m_DisplayType = 0;
	/**	Name				*/
	private String m_Name = null;
	
	/**
	 * @return the m_DisplayType
	 */
	public int getDisplayType() {
		return m_DisplayType;
	}
	/**
	 * @return the m_Name
	 */
	public String getName() {
		return m_Name;
	}
	/**
	 * @param m_DisplayType the m_DisplayType to set
	 */
	public void setDisplayType(int m_DisplayType) {
		this.m_DisplayType = m_DisplayType;
	}
	/**
	 * @param m_Name the m_Name to set
	 */
	public void setName(String m_Name) {
		this.m_Name = m_Name;
	}
	
	@Override
	public String toString() {
		return "IdentifierWrapper [m_DisplayType=" + m_DisplayType
				+ ", m_Name=" + m_Name + "]";
	}	
}