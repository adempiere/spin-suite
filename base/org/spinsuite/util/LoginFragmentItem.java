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

import org.spinsuite.view.T_Pref_Parent;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Jun 22, 2015, 10:10:46 AM
 *
 */
public class LoginFragmentItem {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Pref
	 * @param p_Title
	 * @param p_Description
	 * @param p_IsMandatory
	 */
	public LoginFragmentItem(T_Pref_Parent p_Pref, String p_Title, String p_Description, boolean p_IsMandatory) {
		m_Pref = p_Pref;
		m_Title = p_Title;
		m_Description = p_Description;
		m_IsMandatory = p_IsMandatory;
	}

	/**	Preference Parent		*/
	private T_Pref_Parent 	m_Pref = null;
	/**	Title					*/
	private String 			m_Title = null;
	/**	Description				*/
	private String 			m_Description = null;
	/**	Is Mandatory			*/
	private boolean 		m_IsMandatory = false;
	
	/**
	 * @return the m_Pref
	 */
	public T_Pref_Parent getPref() {
		return m_Pref;
	}
	
	/**
	 * @return the m_Title
	 */
	public String getTitle() {
		return m_Title;
	}
	
	/**
	 * @return the m_Description
	 */
	public String getDescription() {
		return m_Description;
	}
	
	/**
	 * @return the m_IsMandatory
	 */
	public boolean isIsMandatory() {
		return m_IsMandatory;
	}
	
	/**
	 * @param m_Pref the m_Pref to set
	 */
	public void setPref(T_Pref_Parent m_Pref) {
		this.m_Pref = m_Pref;
	}
	
	/**
	 * @param m_Title the m_Title to set
	 */
	public void setTitle(String m_Title) {
		this.m_Title = m_Title;
	}
	
	/**
	 * @param m_Description the m_Description to set
	 */
	public void setDescription(String m_Description) {
		this.m_Description = m_Description;
	}
	
	/**
	 * @param m_IsMandatory the m_IsMandatory to set
	 */
	public void setIsMandatory(boolean m_IsMandatory) {
		this.m_IsMandatory = m_IsMandatory;
	}

	@Override
	public String toString() {
		return "LoginFragmentItem [m_Title=" + m_Title
				+ ", m_Description=" + m_Description + ", m_IsMandatory="
				+ m_IsMandatory + "]";
	}
}