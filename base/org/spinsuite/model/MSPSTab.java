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

import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MSPSTab extends X_SPS_Tab {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 13:54:53
	 * @param ctx
	 * @param SPS_Tab_ID
	 * @param conn
	 */
	public MSPSTab(Context ctx, int SPS_Tab_ID, DB conn) {
		super(ctx, SPS_Tab_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 13:54:53
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MSPSTab(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * Get Children Field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/02/2014, 01:14:25
	 * @return
	 * @return MSFAField[]
	 */
	public MSPSField[] getFields(){
		ArrayList<MSPSField> fields = new ArrayList<MSPSField>();
		loadConnection(DB.READ_ONLY);
		Cursor rs = null;
		rs = conn.querySQL("SELECT f.* " +
				"FROM SPS_Field f " +
				"WHERE f.IsActive = 'Y' " +
				"AND f.SPS_Tab_ID = " + getSPS_Tab_ID() + " " +
				"ORDER BY f.SeqNo", null);
		if(rs.moveToFirst()){
			do{
				fields.add(new MSPSField(getCtx(), rs, conn));
			}while(rs.moveToNext());
		}
		closeConnection();
		MSPSField[] arrayFields = new MSPSField[fields.size()];
		fields.toArray(arrayFields);
		LogM.log(getCtx(), getClass(), Level.FINE, "Size=" + arrayFields.length);
		return arrayFields;
	}
	
	@Override
	public String toString() {
		return "Name=" + getName() 
				+ "\nSPS_Tab_ID=" + getSPS_Tab_ID();
	}
	
	
}
