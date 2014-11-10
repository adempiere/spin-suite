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
 * Contributor(s): Carlos Parada www.erpcya.com                    *
 *************************************************************************************/
package com._3e.ADInterface;

/**
 *  
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 *
 */
@SuppressWarnings("rawtypes")
public class PField {
	
	/** Field */
	private String m_field;
	/** Value */
	private Object m_value;
	/** Class */
	private Class m_type;

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:14:35
	 * @param field
	 * @param value
	 * @param type
	 */
	public PField(String field, Object value,Class type) {
		// TODO Auto-generated constructor stub
		m_field = field;
		m_value = value;
		m_type = type;
	}

	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:14:58
	 * @param m_field
	 * @return void
	 */
	public void setM_field(String m_field) {
		this.m_field = m_field;
	}

	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:15:23
	 * @param m_type
	 * @return void
	 */
	public void setM_type(Class m_type) {
		this.m_type = m_type;
	}
	
	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:15:43
	 * @param m_value
	 * @return void
	 */
	public void setM_value(Object m_value) {
		this.m_value = m_value;
	}

	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:16:00
	 * @return
	 * @return String
	 */
	public String getM_field() {
		return m_field;
	}

	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:16:23
	 * @return
	 * @return Object
	 */
	public Object getM_value() {
		return m_value;
	}

	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:16:47
	 * @return
	 * @return Class
	 */
	public Class getM_type() {
		return m_type;
	}
}
