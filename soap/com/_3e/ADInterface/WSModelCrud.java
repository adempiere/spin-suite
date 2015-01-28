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
public class WSModelCrud extends SoapObject{

	/** Record ID */
	private Integer m_Record_ID;
	
	/** Connection DB */
	private DB m_con;
	
	/** Web Service Type ID */ 
	private Integer m_WS_WebServiceType_ID;
	
	/** Cursor */ 
	private Cursor m_rs;
	
	/** Record ID Static Value*/ 
	public static final String PARAMETER_RecordID="RecordID";
	
	/** Filter Static Value*/
	public static final String PARAMETER_Filter="Filter";
	
	/** Page Number Static Value*/
	public static final String PARAMETER_PageNo = "PageNo";
	
	/** Soap Object Name*/
	public static final String ModelCRUD = "ModelCRUD";
	
	/** Filter */
	private String m_Filter;
	
	/** Page Number*/
	private int m_PageNo = 0 ; 
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/11/2014, 00:09:16
	 * @param ctx
	 * @param NameSpace
	 * @param p_WS_WebServiceType_ID
	 * @param con
	 * @param RecordID
	 * @param rs
	 * @param Filter
	 */
	public WSModelCrud(Context ctx, String NameSpace, Integer p_WS_WebServiceType_ID,DB con,Integer RecordID,Cursor rs,String Filter,int PageNo) {
		super(NameSpace,WSModelCrud.ModelCRUD);
		// TODO Auto-generated constructor stub
		m_Filter=Filter;
		m_WS_WebServiceType_ID = p_WS_WebServiceType_ID;
		m_con = con;
		m_Record_ID = RecordID;
		m_rs = rs;
		m_PageNo = PageNo;
		
		//Load Parameters
		loadWS_Service_Para();

		WSDataRow l_data = new WSDataRow(ctx, NameSpace,m_WS_WebServiceType_ID,m_con, m_rs);
		addProperty(l_data.getName(), l_data);
		
	}

	/**
	 * Load Paameters
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/11/2014, 00:10:54
	 * @return void
	 */
	private void loadWS_Service_Para()
	{
		Cursor rs = m_con.querySQL("Select WST.Value as serviceType,WSP.ParameterName,WSP.ConstantValue,WSP.ParameterType from WS_WebService_Para WSP Inner Join WS_WebServiceType WST On WSP.WS_WebServiceType_ID=WST.WS_WebServiceType_ID Where WSP.WS_WebServiceType_ID =?", new String[]{m_WS_WebServiceType_ID.toString()});
		if (rs.moveToFirst()){
			addProperty(rs.getColumnName(0), rs.getString(0));
			addProperty(PARAMETER_PageNo, m_PageNo);
			do {
				addProperty(rs.getString(1), (rs.getString(1).equals(PARAMETER_RecordID)?
												(m_Record_ID==null?0:m_Record_ID):
													(rs.getString(1).equals(PARAMETER_Filter)?m_Filter:
														rs.getString(2)
												)
											 )
							);
			}while (rs.moveToNext());
		}
		
	}
}
