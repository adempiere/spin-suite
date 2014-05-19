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
package org.spinsuite.view.lookup;

import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.DisplayLookupSpinner;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.LogM;
import org.spinsuite.util.TabParameter;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class Lookup {

	/**
	 * *** Constructor ***
	 * @param m_ctx
	 * @param m_field
	 * @param tabParam
	 * @param conn
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 08:20:43
	 */
	public Lookup(Context m_ctx, InfoField m_field, TabParameter tabParam, DB conn) {
		this.m_ctx = m_ctx;
		this.m_field = m_field;
		this.conn = conn;
		
		int m_ActivityNo = 0;
		int m_TabNo = 0;
		//	Set Property
		if(tabParam != null){
			m_ActivityNo = tabParam.getActivityNo();
			m_TabNo = tabParam.getTabNo();
		}
		//	Lookup
		lookup = new LookupDisplayType(m_ctx, m_ActivityNo, m_TabNo, m_field);
	}
	
	/** Context					*/
	private Context 			m_ctx = null;
	/**	Field					*/
	private InfoField 			m_field = null;
	/**	Connection				*/
	private DB 					conn = null;
	/**	Lookup Handler			*/
	private LookupDisplayType 	lookup = null;
	/**	Syntax Error			*/
	private boolean 			isSyntaxError = false;
	/**	Display Lookup Spinner	*/
	private ArrayList<DisplayLookupSpinner> data = null;
	
	/**
	 * Load Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 09:44:09
	 * @return void
	 */
	public void load(){
		load(null, true);
	}
	
	/**
	 * 
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 08:40:36
	 * @param v_Spinner
	 * @param reQuery
	 * @return void
	 */
	public void load(Spinner v_Spinner, boolean reQuery){
		//	Is Syntax Error
		if(isSyntaxError)
			return;
		try{
			//	Cache
			if(!reQuery
					&& data != null){
				//	Set Adapter
				if(v_Spinner.getAdapter() == null)
					setAdapter(v_Spinner);
				return;
			}
			//	
			boolean isHandleConnection = false;
			if(conn == null){
				conn = new DB(m_ctx);
				isHandleConnection = true;
			}
			//	
			DB.loadConnection(conn, DB.READ_ONLY);
			Cursor rs = null;
			//	Query
			rs = conn.querySQL(lookup.getSQL(), null);
			data = new ArrayList<DisplayLookupSpinner>();
			if(rs.moveToFirst()){
				if(!m_field.IsMandatory) {
					if(m_field.DisplayType == DisplayType.LIST)
						data.add(new DisplayLookupSpinner(null, null));
					else
						data.add(new DisplayLookupSpinner(-1, null));
				}
				//	Loop
				do{
					if(m_field.DisplayType == DisplayType.LIST)
						data.add(new DisplayLookupSpinner(rs.getString(0), rs.getString(1)));
					else
						data.add(new DisplayLookupSpinner(rs.getInt(0), rs.getString(1)));
				}while(rs.moveToNext());
			}
			//	Set Adapter
			setAdapter(v_Spinner);
			//	Close
			if(isHandleConnection)
				DB.closeConnection(conn);
		} catch(Exception e){
			isSyntaxError = true;
			LogM.log(m_ctx, getClass(), Level.SEVERE, "Error in Load", e);
		}
	}
	
	/**
	 * Set adapter
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/05/2014, 09:37:16
	 * @param v_Spinner
	 * @return void
	 */
	private void setAdapter(Spinner v_Spinner){
		//	
		if(v_Spinner == null)
			return;
		//	Set Adapter
		ArrayAdapter<DisplayLookupSpinner> sp_adapter = 
    			new ArrayAdapter<DisplayLookupSpinner>(m_ctx, android.R.layout.simple_spinner_item, data);
		//	
		sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//	
		v_Spinner.setAdapter(sp_adapter);
	}
	
	/**
	 * Is Syntax Error
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/03/2014, 11:36:25
	 * @return
	 * @return boolean
	 */
	public boolean isSyntaxError(){
		return isSyntaxError;
	}
}
