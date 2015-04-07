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
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.view.lookup;


import java.util.StringTokenizer;
import java.util.logging.Level;

import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_Search;
import org.spinsuite.interfaces.OnFieldChangeListener;
import org.spinsuite.model.I_C_Location;
import org.spinsuite.model.MCountry;
import org.spinsuite.model.MLocation;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;

@SuppressLint("ValidFragment")
public class V_LocationDialog extends DialogFragment {
	
	
	/**	Main Layout				*/
	private ScrollView				sv_AddLocation 			= null;
	/**	Table Layout			*/
	private TableLayout 			v_tableLayout 			= null;
	/**	Parameter				*/
	private LayoutParams			v_param					= null;
	/**	Activity				*/
	private Activity				v_activity 				= null;
	/**	Address 1				*/
	private GridField				v_Address1				= null;
	/**	Address 2				*/
	private GridField				v_Address2				= null;
	/**	Address 3				*/
	private GridField				v_Address3				= null;
	/**	Address 4				*/
	private GridField				v_Address4				= null;
	/**	City					*/
	private GridField				v_City					= null;
	/**	Country					*/
	private GridField				v_Country				= null;
	/**	Region					*/
	private GridField				v_Region				= null;
	/**	Postal					*/
	private GridField				v_Postal				= null;
	/**	Postal Add				*/
	private GridField				v_Postal_Add				= null;
	/**	Location				*/
	private MLocation				m_location				= null;
	/**	Listener				*/
	private OnFieldChangeListener	m_Listener 				= null;
	/**	Parent					*/
	private I_Search				m_parent				= null;
	/**	Load Ok					*/
	private boolean 				m_IsLoadOk				= false;
	/**	View Weight				*/
	private static final float 		WEIGHT 					= 1;
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 14/10/2014, 13:55:20
	 * @param v_activity
	 * @param parent
	 * @param p_C_Location_ID
	 */
	public V_LocationDialog(Activity v_activity, I_Search parent, int p_C_Location_ID) {
		this.v_activity = v_activity;
		this.m_parent = parent;
		m_location = new MLocation(v_activity, p_C_Location_ID, null);
	}
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.v_location, null);
		
		//	
		sv_AddLocation = (ScrollView) view.findViewById(R.id.sv_AddLocation);
    	//	Table Layout
    	v_tableLayout = new TableLayout(v_activity);
    	sv_AddLocation.addView(v_tableLayout);
    	//	Parameters
    	builder.setView(view);
		//	
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				boolean saved = saveData();
				//	Refresh Parent
				if(saved
						&& m_parent != null)
					m_parent.okAction(m_location.getC_Location_ID());
			}
		});
		
		builder.setNegativeButton(R.string.Action_Cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//	
			}
		});
		
    	//	Instance Listener
    	m_Listener = new OnFieldChangeListener() {
    		@Override
    		public void onFieldEvent(GridField mField) {
    			if(!m_IsLoadOk)
    				return;
    			LogM.log(v_activity, V_LocationDialog.class, 
    					Level.FINE, "Field Event = " + mField.getColumnName());
    			//	Process Reload
    			if(mField.getColumnName().equals(I_C_Location.COLUMNNAME_C_Country_ID)) {
    				//	Region
    				reloadDepending(v_Region);
    				//	City
    				reloadDepending(v_City);
    			} else if(mField.getColumnName().equals(I_C_Location.COLUMNNAME_C_Region_ID)) {
    				reloadDepending(v_City);
    			}
    		}
		};
		//	Load View in pane
		loadView();
		//	
		return builder.create();
	}
	
	/**
	 * Save Date
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 14/10/2014, 17:25:13
	 * @return
	 * @return boolean
	 */
	private boolean saveData() {
		//	Error Message
		String m_ErrorMsg = null;
		if(v_Address1 != null) {
			m_location.setAddress1(v_Address1.getValueAsString());
			//	Valid Mandatory
			if(v_Address1.isMandatory()
					&& v_Address1.isEmpty())
				m_ErrorMsg = "@MustFillField@ \"" + v_Address1.getName() + "\"";
		}
		if(v_Address2 != null) {
			m_location.setAddress2(v_Address2.getValueAsString());
			//	Valid Mandatory
			if(v_Address2.isMandatory()
					&& v_Address1.isEmpty())
				m_ErrorMsg = "@MustFillField@ \"" + v_Address2.getName() + "\"";

		}
		if(v_Address3 != null) {
			m_location.setAddress3(v_Address3.getValueAsString());
			//	Valid Mandatory
			if(v_Address1.isMandatory()
					&& v_Address3.isEmpty())
				m_ErrorMsg = "@MustFillField@ \"" + v_Address3.getName() + "\"";
		}
		if(v_Address4 != null) {
			m_location.setAddress4(v_Address4.getValueAsString());
			//	Valid Mandatory
			if(v_Address4.isMandatory()
					&& v_Address4.isEmpty())
				m_ErrorMsg = "@MustFillField@ \"" + v_Address4.getName() + "\"";
		}
		if(v_City != null) {
			m_location.setC_City_ID(v_City.getValueAsInt());
			//	Set City Name
			m_location.setCity(v_City.getDisplayValue());	
			//	Valid Mandatory
			if(v_City.isMandatory()
					&& v_City.isEmpty())
				m_ErrorMsg = "@MustFillField@ \"" + v_City.getName() + "\"";
		}
		if(v_Postal != null) {
			m_location.setPostal(v_Postal.getValueAsString());
			//	Valid Mandatory
			if(v_Postal.isMandatory()
					&& v_Postal.isEmpty())
				m_ErrorMsg = "@MustFillField@ \"" + v_Postal.getName() + "\"";
		}
		if(v_Postal_Add != null) {
			m_location.setPostal_Add(v_Postal_Add.getValueAsString());
			//	Valid Mandatory
			if(v_Postal_Add.isMandatory()
					&& v_Postal_Add.isEmpty())
				m_ErrorMsg = "@MustFillField@ \"" + v_Postal_Add.getName() + "\"";
		}
		if(v_Country != null) {
			m_location.setC_Country_ID(v_Country.getValueAsInt());
			//	Valid Mandatory
			if(v_Country.isMandatory()
					&& v_Country.isEmpty())
				m_ErrorMsg = "@MustFillField@ \"" + v_Country.getName() + "\"";
		}
		//	Valid
		if(m_ErrorMsg != null)
			Msg.alertMsg(v_activity, m_ErrorMsg);
		//	Save
		try {
			m_location.saveEx();
		} catch (Exception e) {
			LogM.log(v_activity, getClass(), Level.SEVERE, "Error Saving:", e);
			Msg.alertMsg(v_activity, e.getLocalizedMessage());
		}
		return true;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		//	Set Title
		if(m_location.getC_Location_ID() != 0)
			getDialog().setTitle(Msg.getMsg(v_activity, "LocationUpdate"));
		else
			getDialog().setTitle(Msg.getMsg(v_activity, "LocationNew"));
		//	Return
		return v;
	}
	
	/**
	 * Reload Depending Field
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 14/10/2014, 16:19:53
	 * @param mField
	 * @return void
	 */
	private void reloadDepending(GridField mField) {
		//	Trigger refresh
		if(mField == null)
			return;
		//	Load
		InfoField fieldMD = mField.getField();
		if(fieldMD == null)
			return;
		//	do it
		if(DisplayType.isLookup(fieldMD.DisplayType)) {
			if(fieldMD.DisplayType != DisplayType.SEARCH
					&& mField instanceof VLookupSpinner) {
				VLookupSpinner spinner = (VLookupSpinner) mField;
				Object oldValue = spinner.getValue();
				spinner.load(true);
				//	set old value
				spinner.setValueNoReload(oldValue);
			}
		}
	}
	
	/**
	 * Load Default View
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 14/10/2014, 16:07:07
	 * @return void
	 */
	private void loadView() {
    	//	Set Parameter
    	v_param = new LayoutParams(LayoutParams.MATCH_PARENT, 
    			LayoutParams.MATCH_PARENT, WEIGHT);
		//	Get Country
		MCountry m_country = null;
		//	Default Country
		if(m_location.getC_Location_ID() == 0) {
			int m_C_Country_ID = Env.getContextAsInt("#C_Country_ID");
			if(m_C_Country_ID < 0)
				m_country = MCountry.getBaseCountry(v_activity);
			else
				m_country = MCountry.getCountry(v_activity, m_C_Country_ID);
		} else {
			m_country = new MCountry(v_activity, m_location.getC_Country_ID(), null);
		}
		//	
		LogM.log(v_activity, getClass(), Level.FINE, m_country.getName() 
				+ ", Region=" + m_country.isHasRegion() + " " + m_country.getCaptureSequence()
			+ ", C_Location_ID=" + m_location.getC_Location_ID());
		//  sequence of City Postal Region - @P@ @C@ - @C@, @R@ @P@
		String ds = m_country.getCaptureSequence();
		if (ds == null || ds.length() == 0) {
			LogM.log(v_activity, getClass(), Level.SEVERE, "CaptureSequence empty - " + m_country);
			ds = "";	//	@C@,  @P@
		}
		//	
		StringTokenizer st = new StringTokenizer(ds, "@", false);
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			if (s.startsWith("CO")) {
				InfoField field = GridField.loadInfoColumnField(v_activity, 
						I_C_Location.Table_Name, I_C_Location.COLUMNNAME_C_Country_ID);
				if(field == null)
					continue;
				//	
				if(s.endsWith("!"))
					field.IsMandatory = true;
				v_Country = GridField.createLookup(v_activity, field);
				//	Add Listener
				v_Country.setOnFieldChangeListener(m_Listener);
				addView(v_Country);
			} else if (s.startsWith("A1")) {
				InfoField field = GridField.loadInfoColumnField(v_activity, 
						I_C_Location.Table_Name, I_C_Location.COLUMNNAME_Address1);
				if(field == null)
					continue;
				//	
				if(s.endsWith("!"))
					field.IsMandatory = true;
				v_Address1 = GridField.createLookup(v_activity, field);
				addView(v_Address1);
			} else if (s.startsWith("A2")) {
				InfoField field = GridField.loadInfoColumnField(v_activity, 
						I_C_Location.Table_Name, I_C_Location.COLUMNNAME_Address2);
				if(field == null)
					continue;
				//	
				if(s.endsWith("!"))
					field.IsMandatory = true;
				v_Address2 = GridField.createLookup(v_activity, field);
				addView(v_Address2);
			} else if (s.startsWith("A3")) {
				InfoField field = GridField.loadInfoColumnField(v_activity, 
						I_C_Location.Table_Name, I_C_Location.COLUMNNAME_Address3);
				if(field == null)
					continue;
				//	
				if(s.endsWith("!"))
					field.IsMandatory = true;
				v_Address3 = GridField.createLookup(v_activity, field);
				addView(v_Address3);
			} else if (s.startsWith("A4")) {
				InfoField field = GridField.loadInfoColumnField(v_activity, 
						I_C_Location.Table_Name, I_C_Location.COLUMNNAME_Address4);
				if(field == null)
					continue;
				//	
				if(s.endsWith("!"))
					field.IsMandatory = true;
				v_Address4 = GridField.createLookup(v_activity, field);
				addView(v_Address4);
			} else if (s.startsWith("C")) {
				InfoField field = GridField.loadInfoColumnField(v_activity, 
						I_C_Location.Table_Name, I_C_Location.COLUMNNAME_C_City_ID);
				if(field == null)
					continue;
				//	
				if(s.endsWith("!"))
					field.IsMandatory = true;
				field.DisplayType = DisplayType.TABLE_DIR;
				v_City = GridField.createLookup(v_activity, field);
				addView(v_City);
			} else if (s.startsWith("P")) {
				InfoField field = GridField.loadInfoColumnField(v_activity, 
						I_C_Location.Table_Name, I_C_Location.COLUMNNAME_Postal);
				if(field == null)
					continue;
				//	
				if(s.endsWith("!"))
					field.IsMandatory = true;
				v_Postal = GridField.createLookup(v_activity, field);
				addView(v_Postal);
			} else if (s.startsWith("A")) {
				InfoField field = GridField.loadInfoColumnField(v_activity, 
						I_C_Location.Table_Name, I_C_Location.COLUMNNAME_Postal_Add);
				if(field == null)
					continue;
				//	
				if(s.endsWith("!"))
					field.IsMandatory = true;
				v_Postal_Add = GridField.createLookup(v_activity, field);
				addView(v_Postal_Add);
			} else if (s.startsWith("R") && m_country.isHasRegion()) {
				InfoField field = GridField.loadInfoColumnField(v_activity, 
						I_C_Location.Table_Name, I_C_Location.COLUMNNAME_C_Region_ID);
				if(field == null)
					continue;
				//	
				if(s.endsWith("!"))
					field.IsMandatory = true;
				v_Region = GridField.createLookup(v_activity, field);
				//	Listener
				v_Region.setOnFieldChangeListener(m_Listener);
				addView(v_Region);
			}
		}
		//	Fill it
		if (m_location.getC_Location_ID() != 0) {
			if(v_Address1 != null)
				v_Address1.setValueAndOldValue(m_location.getAddress1());
			if(v_Address2 != null)
				v_Address2.setValueAndOldValue(m_location.getAddress2());
			if(v_Address3 != null)
				v_Address3.setValueAndOldValue(m_location.getAddress3());
			if(v_Address4 != null)
				v_Address4.setValueAndOldValue(m_location.getAddress4());
			if(v_City != null)
				v_City.setValueAndOldValue(m_location.getC_City_ID());
			if(v_Postal != null)
				v_Postal.setValueAndOldValue(m_location.getPostal());
			if(v_Postal_Add != null)
				v_Postal_Add.setValueAndOldValue(m_location.getPostal_Add());
		}
		//	Set Country
		if(v_Country != null) {
			if(m_location.getC_Country_ID() != m_country.getC_Country_ID())
				v_Country.setValueAndOldValue(m_country.getC_Country_ID());
			else 
				v_Country.setValueAndOldValue(m_location.getC_Country_ID());
		}
		//	Set Is Load Ok
		m_IsLoadOk = true;
	}	//	Load View
	
	/**
	 * Add View
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 14/10/2014, 14:20:10
	 * @param lookup
	 * @return void
	 */
	private void addView(GridField lookup) {
		//	is Filled
		if(lookup != null)
			v_tableLayout.addView(lookup, v_param);
    }
}