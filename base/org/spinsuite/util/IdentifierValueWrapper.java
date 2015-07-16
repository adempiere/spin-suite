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
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Jul 16, 2015, 9:02:53 AM
 *
 */
public class IdentifierValueWrapper extends IdentifierWrapper {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_DisplayType
	 * @param p_Name
	 * @param p_Value
	 */
	public IdentifierValueWrapper(int p_DisplayType, String p_Name, String p_Value) {
		super(p_DisplayType, p_Name);
		m_Value = p_Value;
	}

	/** Value				*/
	private String m_Value = null;

	/**
	 * @return the m_Value
	 */
	public String getValue() {
		return m_Value;
	}

	/**
	 * @param m_Value the m_Value to set
	 */
	public void setValue(String m_Value) {
		this.m_Value = m_Value;
	}

	@Override
	public String toString() {
		return "IdentifierValueWrapper [m_Value=" + m_Value + "]";
	}
}