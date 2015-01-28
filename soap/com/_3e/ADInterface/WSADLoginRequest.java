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
import org.spinsuite.util.Env;
import android.content.Context;

/**
 * 
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 *
 */
public class WSADLoginRequest extends SoapObject{

	public final static String NameSpace =  "ADLoginRequest";
	public final static String User =  "user";
	public final static String Pass =  "pass";
	public final static String ClientID =  "ClientID";
	public final static String RoleID =  "RoleID";
	public final static String OrgID =  "OrgID";
	public final static String WarehouseID =  "WarehouseID";
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 03/11/2014, 23:25:28
	 * @param ctx
	 * @param NameSpace
	 */
	public WSADLoginRequest(Context ctx,String NameSpace) {
		super(NameSpace,WSADLoginRequest.NameSpace);
		addProperty(WSADLoginRequest.User, Env.getContext(ctx, "#SUser"));
		addProperty(WSADLoginRequest.Pass, Env.getContext(ctx, "#SPass"));
		addProperty(WSADLoginRequest.ClientID, Env.getAD_Client_ID(ctx));
		addProperty(WSADLoginRequest.RoleID, Env.getAD_Role_ID(ctx));
		addProperty(WSADLoginRequest.OrgID, Env.getAD_Org_ID(ctx));
		addProperty(WSADLoginRequest.WarehouseID, Env.getM_Warehouse_ID(ctx));
	}

}
