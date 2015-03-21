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
import org.spinsuite.base.DB;
import org.spinsuite.model.PO;

import android.content.Context;
import android.database.Cursor;

/**
 * 
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 *
 */
public class WSDataRow extends SoapObject{

	/** Web Service ID */ 
	private Integer m_WS_WebServiceType_ID;
	/** DB */
	private DB m_con;
	/** Cursor to Send */
	private Cursor m_rsData;
	/** PO to Send*/
	private	PO m_poData;
	/** Context */
	private Context m_Ctx;
	
	public final static String NameSpace =  "DataRow";
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:33:58
	 * @param ctx
	 * @param NameSpace
	 * @param p_WS_WebServiceType_ID
	 * @param p_con
	 * @param rs
	 */
	public WSDataRow(Context ctx, String NameSpace,Integer p_WS_WebServiceType_ID,DB p_con,Cursor data) {
		super(NameSpace, WSDataRow.NameSpace);
		m_Ctx=ctx;
		m_WS_WebServiceType_ID =p_WS_WebServiceType_ID;
		m_con = p_con;
		m_rsData = data;
		setFields();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 12/3/2015, 0:58:18
	 * @param ctx
	 * @param NameSpace
	 * @param p_WS_WebServiceType_ID
	 * @param p_con
	 * @param data
	 */
	public WSDataRow(Context ctx, String NameSpace,Integer p_WS_WebServiceType_ID,DB p_con,PO data) {
		super(NameSpace, WSDataRow.NameSpace);
		m_Ctx=ctx;
		m_WS_WebServiceType_ID =p_WS_WebServiceType_ID;
		m_con = p_con;
		m_poData = data;
		setFields();
	}
	
	/**
	 * Set Fields to Send a Web Service
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:37:18
	 * @return void
	 */
	private void setFields(){
		Cursor rs = m_con.querySQL("SELECT AC.ColumnName " +
									" FROM " + 	
									"WS_WebServiceType WST " + 
									"INNER JOIN WS_WebServiceFieldInput WSI ON (WST.WS_WebServiceType_ID = WSI.WS_WebServiceType_ID) " +
									"INNER JOIN AD_Column AC ON (AC.AD_Column_ID = WSI.AD_Column_ID) " +
									"WHERE WST.WS_WebServiceType_ID=?", new String[]{m_WS_WebServiceType_ID.toString()});
		int i;
		WSField l_field;
		String l_NameColumn;
		Object fieldData = null;
		if (rs.moveToFirst()){
			do {
				l_NameColumn=rs.getString(0);
				if (m_poData != null){
					fieldData = m_poData.get_SyncValue(l_NameColumn);
					System.out.println(fieldData);}
				else if (m_rsData != null){
					i = m_rsData.getColumnIndex(l_NameColumn);
					fieldData= m_rsData.getString(i);
				}
				if (fieldData != null){
					l_field = new WSField(getM_Ctx(), getNamespace(),l_NameColumn,fieldData.toString());
					addProperty(l_field.getName(), l_field);
				}
				
			}while (rs.moveToNext());
		}
	}

	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:36:58
	 * @return
	 * @return Context
	 */
	public Context getM_Ctx() {
		return m_Ctx;
	}

	
}

