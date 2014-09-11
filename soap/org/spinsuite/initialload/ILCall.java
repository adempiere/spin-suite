/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2014 E.R.P. Consultores y Asociados, C.A.               *
 * All Rights Reserved.                                                       *
 * Contributor(s): Carlos Parada www.erpconsultoresyasociados.com             *
 *****************************************************************************/

package org.spinsuite.initialload;

import org.ksoap2.serialization.SoapObject;

/**
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 */
public class ILCall extends SoapObject{
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 23:07:45
	 * @param p_NameSpace
	 * @param p_User
	 * @param p_PassWord
	 * @param p_ServiceType
	 */
	public ILCall(String p_NameSpace, String p_User, String p_PassWord) {
		// TODO Auto-generated constructor stub
		super(p_NameSpace, m_ILCALLService);
		addProperty(m_UserField, p_User);
		addProperty(m_PassWordField, p_PassWord);
	}
	
	
	public static String m_ILCALLService = "ILCall";
	
	public static String m_UserField = "User";
	
	public static String m_PassWordField = "PassWord";
	
	public static String m_ServiceDefinitionField = "serviceDefinition";
	
	public static String m_ServiceMethodField = "serviceMethod";
	
	public static String m_ServiceTypeField = "serviceType";
	
}
