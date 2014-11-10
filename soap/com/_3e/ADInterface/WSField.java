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
 * Copyright (C) 2012-2012 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Carlos Parada www.erpcya.com                    					 *
 *************************************************************************************/
package com._3e.ADInterface;
import org.ksoap2.serialization.SoapObject;
import android.content.Context;

/**
 * @author Carlos Parada
 *
 */
public class WSField extends SoapObject{

	/** Column Name */
	private String m_ColumnName;
	/** Value */
	private Object m_Value;
	/** NameSpace */
	public static final String NameSpace = "field";
	/** Column */
	public static final String Column = "field";
	/** Val */
	public static final String Val = "field";
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:19:54
	 * @param ctx
	 * @param NameSpace
	 * @param p_ColumnName
	 * @param p_Value
	 */
	public WSField(Context ctx, String NameSpace,String p_ColumnName,Object p_Value) {
		super(NameSpace, WSField.NameSpace);
		
		m_ColumnName = p_ColumnName;
		m_Value = p_Value;
		addAttribute(WSField.Column,m_ColumnName);
		addProperty(WSField.Val, m_Value);
	}
	
}
