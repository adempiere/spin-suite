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
package org.spinsuite.view.lookup;

import java.util.logging.Level;

import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_Search;
import org.spinsuite.util.DisplayRecordItem;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.view.V_StandardSearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Mar 2, 2015, 2:54:43 AM
 *
 */
public class VSearch extends LinearLayout 
						implements OnClickListener, I_Search {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param activity
	 */
	public VSearch(Activity activity) {
		super(activity);
		this.v_activity = activity;
		LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.v_lookup_search, this);
        init();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param activity
	 * @param callback
	 * @param m_field
	 */
	public VSearch(Activity activity, GridField callback, InfoField m_field) {
		super(activity);
		this.v_activity = activity;
		this.m_field = m_field;
		this.m_callback = callback;
		LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.v_lookup_search, this);
        init();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param activity
	 * @param m_field
	 */
	public VSearch(Activity activity, InfoField m_field) {
		this(activity, null, m_field);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param activity
	 * @param attrs
	 */
	public VSearch(Activity activity, AttributeSet attrs) {
		super(activity, attrs);
		this.v_activity = activity;
		LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.v_lookup_search, this);
        init();
	}
	
	/**	Search Button			*/
	private ImageButton 		ib_Search;
	/**	Edit text display		*/
	private EditText 			et_Search;
	/**	Activity				*/
    private Activity 			v_activity;
    /**	Item					*/
    private DisplayRecordItem 	item;
    /**	Field					*/
    private InfoField			m_field;
    /**	Grid Field				*/
    private GridField			m_callback;
	
    /**
     * Init
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    protected void init() {
		isInEditMode();
		//	Load Edit Text
		et_Search = (EditText) findViewById(R.id.et_Search);
		//	Load Button Search
		ib_Search = (ImageButton) findViewById(R.id.ib_Search);
		//	
		ib_Search.setOnClickListener(this);
		//	Optional Business Partner and Product
		if(m_field.ColumnName.equals("C_BPartner_ID"))
			ib_Search.setImageResource(
					Env.getResourceID(getContext(), R.attr.ic_lt_search_bpartner));
		else if(m_field.ColumnName.equals("M_Product_ID"))
			ib_Search.setImageResource(
					Env.getResourceID(getContext(), R.attr.ic_lt_search_product));
		else if(m_field.ColumnName.equals("C_Location_ID"))
			ib_Search.setImageResource(
					Env.getResourceID(getContext(), R.attr.ic_lt_search_location));
	}
	
	/**
	 * Set Search Criteria
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param item
	 * @return void
	 */
	public void setItem(DisplayRecordItem item){
		this.item = item;
		updateDisplay();
	}
	
	/**
	 * Update Display
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void updateDisplay(){
		et_Search.setText((item != null? item.getValue(): null));
	}
	
	/**
	 * Get Display Record Item
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return DisplayRecordItem
	 */
	public DisplayRecordItem getItem(){
		return item;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		et_Search.setEnabled(enabled);
		ib_Search.setEnabled(enabled);
		if(enabled) {
			et_Search.setTextColor(
					getResources().getColor(R.color.lookup_text_read_write));
		} else {
			et_Search.setTextColor(
					getResources().getColor(R.color.lookup_text_read_only));
		}
	}
	
	@Override
	public void onClick(View v) {
		if(m_field != null){
    		Bundle bundle = new Bundle();
    		bundle.putParcelable("Field", m_field);
    		Class<?> clazz = V_StandardSearch.class;
    		//	Intent from Info Factory Class
    		if(m_field.InfoFactoryClass != null
    				&& m_field.InfoFactoryClass.length() > 0) {
    			clazz = getClassFromName(m_field.InfoFactoryClass);
    		} else if(m_field.DisplayType == DisplayType.LOCATION) {
    			//	
    			V_LocationDialog v_locationDialog = new V_LocationDialog(v_activity, this, getRecord_ID());
    			v_locationDialog.show(v_activity.getFragmentManager(), "Location");
    			//	finish
    			return;
    		} else if(m_field.DisplayType == DisplayType.LOCATOR) {
    			//	not yet implemented
    		} else if(m_field.DisplayType == DisplayType.ACCOUNT) {
    			//	not yet implemented
    		} else if(m_field.DisplayType == DisplayType.PATTRIBUTE) {
    			//	not yet implemented
    		} else if(m_field.DisplayType == DisplayType.PRINTER_NAME) {
    			//	not yet implemented
    		} else if(m_field.DisplayType == DisplayType.FILE_NAME) {
    			//	not yet implemented
    		} else if(m_field.DisplayType == DisplayType.FILE_PATH) {
    			//	not yet implemented
    		} else if(m_field.DisplayType == DisplayType.ASSIGNMENT) {
    			//	not yet implemented
    		} else if(m_field.ColumnName.equals("C_BPartner_ID")) {
    			//	not yet implemented
    		} else if(m_field.ColumnName.equals("M_Product_ID")) {
    			//	not yet implemented
    		}
    		//	
    		Intent intent = new Intent(v_activity, clazz);
    		intent.putExtras(bundle);
    		//	Start
    		v_activity.startActivityForResult(intent, 0);
    	}
	}
	
	/**
	 * Get Class from path
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param className
	 * @return
	 * @return Class<?>
	 */
	private Class<?> getClassFromName(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return clazz;
		} catch (ClassNotFoundException e) {
			LogM.log(v_activity, getClass(), Level.SEVERE, "Error:", e);
		}
		return null;
	}
	
	/**
	 * Get Record Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public int getRecord_ID(){
		if(item != null)
			return item.getRecord_ID();
		return -1;
	}
	
	/**
	 * Get Keys
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int[]
	 */
	public int[] getKeys(){
		if(item != null)
			return item.getKeys();
		return new int[]{-1};
	}
	
	/**
	 * Get Current Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getValue(){
		if(item != null)
			return item.getValue();
		return null;
	}

	@Override
	public void okAction(Object value) {
		//	Valid Null
		if(m_callback != null) {
			m_callback.setValue(value);
		}
	}
	
}