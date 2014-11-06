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
 * Contributor(s): Carlos Parada www.erpconsultoresyasociados.com                    *
 *************************************************************************************/
package com._3e.ADInterface;

import org.ksoap2.serialization.SoapObject;
import org.spinsuite.base.DB;
import android.content.Context;
import android.database.Cursor;

/**
 * 
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 *
 */
public class WSModelRunProcess extends SoapObject{

	/** Record ID */
	private Integer m_Record_ID;
	/** Connection Database */
	private DB m_con;
	/** Web Service Type ID */
	private Integer m_WS_WebServiceType_ID;
	/** Cursor*/
	private Cursor m_rs;
	/** Record ID Static Value */
	public final static String PARAMETER_RecordID="RecordID";
	/** Filter Static Value */
	public final static String PARAMETER_Filter="Filter";
	/** Name Space */
	public final static String NameSpace = "ModelRunProcess";
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/11/2014, 00:17:13
	 * @param ctx
	 * @param NameSpace
	 * @param p_WS_WebServiceType_ID
	 * @param con
	 * @param RecordID
	 * @param rs
	 */
	public WSModelRunProcess(Context ctx, String NameSpace, Integer p_WS_WebServiceType_ID,DB con,Integer RecordID,Cursor rs) {
		super(NameSpace,WSModelRunProcess.NameSpace);
		m_WS_WebServiceType_ID = p_WS_WebServiceType_ID;
		m_con = con;
		setM_Record_ID(RecordID);
		m_rs = rs;
		
		loadWS_Service_Para();

		WSParamValues l_data = new WSParamValues(ctx, NameSpace,m_WS_WebServiceType_ID,m_con, m_rs);
		addProperty(l_data.getName(), l_data);
		
	}

	/**
	 * Load Parameters
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/11/2014, 00:20:48
	 * @return void
	 */
	private void loadWS_Service_Para(){
		Cursor rs = m_con.querySQL("Select WST.Value as serviceType From WS_WebServiceType WST Where WST.WS_WebServiceType_ID =?", new String[]{m_WS_WebServiceType_ID.toString()});
		if (rs.moveToFirst()){
			addProperty(rs.getColumnName(0), rs.getString(0));
		}
		
	}

	public Integer getM_Record_ID() {
		return m_Record_ID;
	}

	public void setM_Record_ID(Integer m_Record_ID) {
		this.m_Record_ID = m_Record_ID;
	}

	
	
	
}
