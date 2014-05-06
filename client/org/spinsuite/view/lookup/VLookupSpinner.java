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
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class VLookupSpinner extends VLookup {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:27:59
	 * @param context
	 */
	public VLookupSpinner(Context context) {
		super(context);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:27:59
	 * @param context
	 * @param attrs
	 */
	public VLookupSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:27:59
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public VLookupSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/02/2014, 08:27:59
	 * @param context
	 * @param m_field
	 */
	public VLookupSpinner(Context context, InfoField m_field) {
		super(context, m_field);
		init();
	}
	
	/**	String 				*/
	private Spinner 		v_Spinner = null;
	/**	Syntax Error		*/
	private boolean 		isSyntaxError = false;
	//	
	@Override
	protected void init() {
		v_Spinner = new Spinner(getContext());
		//	
		setEnabled(!m_field.IsReadOnly);
		//	Add to View
		addView(v_Spinner);
		//	Load Data
		load();
		//	Set Default Value
		if(m_field.DefaultValue != null
					&& m_field.DefaultValue.length() > 0)
			setValue(Env.parseContext(getContext(), m_field.DefaultValue, true));
	}
	
	/**
	 * Get Position from value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/03/2014, 11:32:32
	 * @param value
	 * @return
	 * @return int
	 */
	private int getPosition(Object value){
		int pos = -1;
		for(int i = 0; i < v_Spinner.getCount(); i++){
			DisplayLookupSpinner ds = (DisplayLookupSpinner) v_Spinner.getItemAtPosition(i);
			if(m_field.DisplayType == DisplayType.LIST){
				if(ds.getIDToString() != null
						&& ds.getIDToString().equals((String) value)){
					pos = i;
					break;	
				}
			} else {
				//	Only Integer
				if(!(value instanceof Integer))
					continue;
				//	
				if(ds.getIDAsInteger() == (Integer)value){
					pos = i;
					break;
				}	
			}
		}
		//	
		return pos;
	}

	@Override
	public void setValue(Object value) {
		if(value == null)
			return;
		int pos = getPosition(value);
		//	Reload
		if(pos == -1
				&& !isSyntaxError) {
			//	Load
			load();
			//	Set Value
			pos = getPosition(value);
		}
		//	
		if(pos > -1)
			v_Spinner.setSelection(pos);
	}

	@Override
	public Object getValue() {
		DisplayLookupSpinner item = (DisplayLookupSpinner) v_Spinner.getSelectedItem();
		if(item != null) {
			if(m_field.DisplayType != DisplayType.LIST)
				return item.getIDAsInteger();
			else
				return item.getIDToString();
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		Object value = getValue();
		if(value != null){
			if(value instanceof Integer){
				if(((Integer)value) > -1)
					return false;
			} else if(value instanceof String){
				if(String.valueOf(value).length() != 0)
					return false;
			}
		}
		return true;
	}

	@Override
	public View getChildView() {
		return v_Spinner;
	}
	
	/**
	 * Load Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 23:27:09
	 * @return void
	 */
	private void load(){
		try{
			LookupDisplayType lookup = new LookupDisplayType(getContext(), m_field);
			DB conn = new DB(getContext());
			DB.loadConnection(conn, DB.READ_ONLY);
			Cursor rs = null;
			//	Query
			rs = conn.querySQL(lookup.getSQL(), null);
			ArrayList<DisplayLookupSpinner> data = new ArrayList<DisplayLookupSpinner>();
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
				ArrayAdapter<DisplayLookupSpinner> sp_adapter = 
		    			new ArrayAdapter<DisplayLookupSpinner>(getContext(), android.R.layout.simple_spinner_item, data);
				sp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				v_Spinner.setAdapter(sp_adapter);
			}
			//	Close
			DB.closeConnection(conn);
		} catch(Exception e){
			isSyntaxError = true;
			LogM.log(getContext(), getClass(), Level.SEVERE, "Error in Load", e);
		}
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
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		v_Spinner.setEnabled(enabled);
	}

	@Override
	public String getDisplayValue() {
		//	Get Value
		DisplayLookupSpinner item = (DisplayLookupSpinner) v_Spinner.getSelectedItem();
		//	
		if(item != null)
			return item.getValueAsString();
		return null;
	}
}
