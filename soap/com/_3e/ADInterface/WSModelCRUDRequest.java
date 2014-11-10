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
public class WSModelCRUDRequest extends SoapObject{

	/** Name Space */
	public final static String NameSpace = "ModelCRUDRequest";
	/** Model Crud */ 
	private WSModelCrud m_mc;
	/** Login Request*/ 
	private WSADLoginRequest m_al;
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/11/2014, 00:13:59
	 * @param ctx
	 * @param NameSpace
	 * @param p_WS_WebServiceType_ID
	 * @param con
	 * @param RecordID
	 * @param rs
	 * @param Filter
	 */
	public WSModelCRUDRequest(Context ctx, String NameSpace,Integer p_WS_WebServiceType_ID,DB con,Integer RecordID,Cursor rs,String Filter) {
		super(NameSpace, WSModelCRUDRequest.NameSpace);
		m_mc = new WSModelCrud(ctx, NameSpace, p_WS_WebServiceType_ID, con, RecordID, rs,Filter);
		m_al = new WSADLoginRequest(ctx, NameSpace);
		addProperty(m_mc.getName(), m_mc);
		addProperty(m_al.getName(), m_al);

	}	
}
