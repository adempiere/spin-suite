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

import java.util.logging.Level;

import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_Search;
import org.spinsuite.util.DisplayRecordItem;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.view.LV_Search;

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
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class VSearch extends LinearLayout 
						implements OnClickListener, I_Search {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/08/2012, 19:44:23
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 21:49:47
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/10/2014, 22:29:00
	 * @param activity
	 * @param m_field
	 */
	public VSearch(Activity activity, InfoField m_field) {
		this(activity, null, m_field);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/08/2012, 19:44:18
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
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 21:07:21
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
					Env.getResourceID(v_activity, R.attr.ic_lt_search_bpartner));
		else if(m_field.ColumnName.equals("M_Product_ID"))
			ib_Search.setImageResource(
					Env.getResourceID(v_activity, R.attr.ic_lt_search_product));
		else if(m_field.ColumnName.equals("C_Location_ID"))
			ib_Search.setImageResource(
					Env.getResourceID(v_activity, R.attr.ic_lt_search_location));
	}
	
	/**
	 * Set Criteria
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/08/2012, 20:07:26
	 * @param item
	 * @return void
	 */
	public void setItem(DisplayRecordItem item){
		this.item = item;
		updateDisplay();
	}
	
	/**
	 * Update display
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/03/2014, 22:41:40
	 * @return void
	 */
	private void updateDisplay(){
		et_Search.setText((item != null? item.getValue(): null));
	}
	
	/**
	 * Get Display Record Iten
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/08/2012, 19:31:14
	 * @return
	 * @return DisplayMenuItem
	 */
	public DisplayRecordItem getItem(){
		return item;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		et_Search.setEnabled(enabled);
		ib_Search.setEnabled(enabled);
	}
	
	@Override
	public void onClick(View v) {
		if(m_field != null){
    		Bundle bundle = new Bundle();
    		bundle.putParcelable("Field", m_field);
    		Class<?> clazz = LV_Search.class;
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
    		}
    		//	
    		Intent intent = new Intent(v_activity, clazz);
    		intent.putExtras(bundle);
    		//	Start
    		v_activity.startActivityForResult(intent, 0);
    	}
	}
	
	/**
	 * Get Class from Class Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/10/2014, 11:28:26
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/05/2012, 17:57:52
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/10/2014, 22:05:06
	 * @return
	 * @return int[]
	 */
	public int[] getKeys(){
		if(item != null)
			return item.getKeys();
		return new int[]{-1};
	}
	
	/**
	 * Get Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/05/2012, 17:59:10
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