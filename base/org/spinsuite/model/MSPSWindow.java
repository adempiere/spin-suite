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
public class MSPSWindow extends X_SPS_Window {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 11:52:04
	 * @param ctx
	 * @param SPS_Window_ID
	 * @param conn
	 */
	public MSPSWindow(Context ctx, int SPS_Window_ID, DB conn) {
		super(ctx, SPS_Window_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 11:52:04
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MSPSWindow(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * Get children Tab
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 13:56:37
	 * @return
	 * @return MSFATab[]
	 */
	public MSPSTab[] getTabs(){
		ArrayList<MSPSTab> tabs = new ArrayList<MSPSTab>();
		loadConnection(DB.READ_ONLY);
		Cursor rs = null;
		rs = conn.querySQL("SELECT t.* " +
				"FROM SPS_Tab t " +
				"WHERE t.IsActive = 'Y' " +
				"AND t.SPS_Window_ID = " + getSPS_Window_ID(), null);
		if(rs.moveToFirst()){
			do{
				tabs.add(new MSPSTab(getCtx(), rs, conn));
			}while(rs.moveToNext());
		}
		closeConnection();
		MSPSTab[] arrayTabs = new MSPSTab[tabs.size()];
		tabs.toArray(arrayTabs);
		LogM.log(getCtx(), getClass(), Level.FINE, "Size=" + arrayTabs.length);
		return arrayTabs;
	}
	
	@Override
	public String toString() {
		return "Name=" + getName() 
				+ "\nSPS_Window_ID=" + getSPS_Window_ID();
	}

}
