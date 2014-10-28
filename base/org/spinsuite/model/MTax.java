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
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.model;

import java.util.List;

import org.spinsuite.base.DB;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MTax extends X_C_Tax {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/10/2014, 21:20:51
	 * @param ctx
	 * @param C_Tax_ID
	 * @param conn
	 */
	public MTax(Context ctx, int C_Tax_ID, DB conn) {
		super(ctx, C_Tax_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 27/10/2014, 21:20:51
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MTax(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * Do we have Postal Codes
	 * @return true if postal codes exist
	 */
	public boolean isPostal() {
		//if(getPostals(false) == null)
			//return false;
		
		//return getPostals(false).length > 0;
		return false;
	}	//	isPostal
	
	/**
	 * 	Get All Tax codes (for AD_Client)
	 *	@param ctx context
	 *	@return MTax
	 */
	public static MTax[] getAll (Context ctx) {
		MTax[] retValue = null;

		//	Create it
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		List<MTax> list = new Query(ctx, I_C_Tax.Table_Name, null, null)
								.setClient_ID()
								.setOrderBy("C_Country_ID, C_Region_ID, To_Country_ID, To_Region_ID")
								.setOnlyActiveRecords(true)
								.list();
		//	
		retValue = list.toArray(new MTax[list.size()]);
		return retValue;
	}	//	getAll

}
