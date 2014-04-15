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
package org.spinsuite.util;

import java.util.ArrayList;

import org.spinsuite.base.DB;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * @author Yamel Senih
 *
 */
public class LoadDataSpinner {
		
	/**
	 * Carga un Spinner a partir de una Consulta SQL
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
	 * Carga los datos en un Spinner
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
	    			new ArrayAdapter<DisplaySpinner>(ctx, android.R.layout.simple_spinner_item, data);
			sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp.setAdapter(sp_adapter);
			//	Position
			sp.setSelection(0);
			id = ((DisplaySpinner)sp.getItemAtPosition(0)).getID();
		} else {
			data.add(new DisplaySpinner(0, ""));
			ArrayAdapter<DisplaySpinner> sp_adapter = 
	    			new ArrayAdapter<DisplaySpinner>(ctx, android.R.layout.simple_spinner_item, data);
			sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp.setAdapter(sp_adapter);
		}
		rs.close();
		return id;
	}
	
	/**
	 * Carga los datos de las Opciones de Filtrado en un Spinner pasando como 
	 * parametro la conexion
	 * @author Yamel Senih 01/05/2012, 03:35:41
	 * @param ctx
	 * @param sp
	 * @param sql
	 * @return
	 * @return int
	 */
	/*public static int loadFilterOption(Context ctx, Spinner sp, String sql){
		int id = 0;
		DB con = new DB(ctx);
		con.openDB(DB.READ_ONLY);
		id = loadFilterOption(ctx, con, sp, sql);
		con.closeDB(null);
		return id;
	}*/
	
	/**
	 * Carga los datos del las opciones de filtrado de registros
	 * @author Yamel Senih 01/05/2012, 03:34:05
	 * @param ctx
	 * @param con
	 * @param sp
	 * @param sql
	 * @return
	 * @return int
	 */
	/*public static int loadFilterOption(Context ctx, DB con, Spinner sp, String sql){
		int id = 0;
		Cursor rs = con.querySQL(sql, null);
		LinkedList<DisplayFilterOption> data = new LinkedList<DisplayFilterOption>();
		if(rs.moveToFirst()){
			do {
				data.add(new DisplayFilterOption(rs.getInt(0), rs.getString(1), rs.getInt(2), 
						rs.getString(3), rs.getString(4), rs.getString(5)));
			} while(rs.moveToNext());
			ArrayAdapter<DisplayFilterOption> sp_adapter = 
	    			new ArrayAdapter<DisplayFilterOption>(ctx, android.R.layout.simple_spinner_item, data);
			sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp.setAdapter(sp_adapter);
			//	Position
			sp.setSelection(0);
			id = ((DisplayFilterOption)sp.getItemAtPosition(0)).getID();
		} else {
			data.add(new DisplayFilterOption(0, ""));
			ArrayAdapter<DisplayFilterOption> sp_adapter = 
	    			new ArrayAdapter<DisplayFilterOption>(ctx, android.R.layout.simple_spinner_item, data);
			sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp.setAdapter(sp_adapter);
		}
		rs.close();
		return id;
	}*/
	
	/**
	 * Obtiene los Identificadores de una tabla
	 * @author Yamel Senih 21/05/2012, 21:42:15
	 * @param con
	 * @param tableName
	 * @return
	 * @return String
	 */
	public static String getIdentifierFromTable(DB con, String tableName){
		StringBuilder identifier = new StringBuilder("");
		String sql = new String("SELECT c.ColumnName " +
				"FROM AD_Table t " +
				"INNER JOIN AD_Column c ON(c.AD_Table_ID = t.AD_Table_ID) " +
				"WHERE t.TableName = '" + tableName + "' " +
				"AND (c.ColumnName IN('DocumentNo', 'Name', 'Description', 'DocStatus', 'IsActive') " +
    			"OR c.IsIdentifier = 'Y') " +
    			"ORDER BY c.SeqNo, c.Name " +
    			"LIMIT 4");
		
		Cursor rs = con.querySQL(sql, null);
		boolean pass = false;
		if(rs.moveToFirst()){
			do {
				String column = rs.getString(0);
				if(pass)
					identifier.append(" || IFNULL('-' || " + column + ", '')");
				else
					identifier.append("IFNULL(" + column + ", '')");
				pass = true;
			} while(rs.moveToNext());
		}
		//	Obtiene los identificadores
		return identifier.toString();
	}
}
