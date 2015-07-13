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
 * Copyright (C) 2012-2015 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.util;

import java.util.ArrayList;

import org.spinsuite.base.DB;

import org.spinsuite.base.R;
import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Jun 13, 2015, 11:08:51 PM
 *
 */
public class LoadDataSpinner {
		
	/**
	 * Load Spinner from SQL
	 * @author Yamel Senih 26/04/2012, 12:14:22
	 * @param ctx
	 * @param sp
	 * @param sql
	 * @param hiddenValue
	 * @return
	 * @return int
	 */
	public static int load(Context ctx, Spinner sp, String sql, boolean hiddenValue, boolean isMandatory){
		int id = 0;
		//Log.i("SQL Load Spinner", "- " + sql);
		DB con = new DB(ctx);
		con.openDB(DB.READ_ONLY);
		id = load(ctx, con, sp, sql, hiddenValue, isMandatory);
		con.closeDB(null);
		return id;
	}
	
	/**
	 * Load Data for Spinner
	 * @author Yamel Senih 01/05/2012, 03:18:40
	 * @param ctx
	 * @param con
	 * @param sp
	 * @param sql
	 * @param hiddenValue
	 * @return
	 * @return int
	 */
	public static int load(Context ctx, DB con, Spinner sp, String sql, boolean hiddenValue, boolean firstSpace){
		int id = 0;
		Cursor rs = con.querySQL(sql, null);
		ArrayList<DisplaySpinner> data = new ArrayList<DisplaySpinner>();
		if(rs.moveToFirst()){
			if(firstSpace){
				data.add(new DisplaySpinner(0, null, null));
			}
			do {
				if(hiddenValue)
					data.add(new DisplaySpinner(rs.getInt(0), rs.getString(1), rs.getString(2)));
				else
					data.add(new DisplaySpinner(rs.getInt(0), rs.getString(1)));
			} while(rs.moveToNext());
			ArrayAdapter<DisplaySpinner> sp_adapter = 
	    			new ArrayAdapter<DisplaySpinner>(ctx, R.layout.v_lookup_spinner, data);
			sp_adapter.setDropDownViewResource(R.layout.v_lookup_spinner_drop_down);
			sp.setAdapter(sp_adapter);
			//	Position
			sp.setSelection(0);
			id = ((DisplaySpinner)sp.getItemAtPosition(0)).getID();
		} else {
			data.add(new DisplaySpinner(0, ""));
			ArrayAdapter<DisplaySpinner> sp_adapter = 
	    			new ArrayAdapter<DisplaySpinner>(ctx, R.layout.v_lookup_spinner, data);
			sp_adapter.setDropDownViewResource(R.layout.v_lookup_spinner_drop_down);
			sp.setAdapter(sp_adapter);
		}
		rs.close();
		return id;
	}
}
