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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

import org.spinsuite.base.R;
import org.spinsuite.interfaces.OnFieldChangeListener;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.LogM;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Mar 2, 2015, 2:49:00 AM
 *
 */
public class VDateBox extends LinearLayout implements OnClickListener, OnDateSetListener {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 */
	public VDateBox(Context context) {
		super(context);
		ctx = context;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.v_lookup_date, this);
        init();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param format
	 * @param m_Field
	 */
	public VDateBox(Context context, SimpleDateFormat format, GridField m_Field){
		super(context);
		ctx = context;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.v_lookup_date, this);
        frontFormat = format;
        this.m_Field = m_Field;
        init();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param attrs
	 */
	public VDateBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		ctx = context;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.v_lookup_date, this);
        init();
	}
	
	private ImageButton 			ib_Date;
	private EditText 				et_Date;
    private Context 				ctx;
    private SimpleDateFormat 		frontFormat = null;
    private SimpleDateFormat 		backFormat = null;
    private Calendar 				cal;
    private OnFieldChangeListener 	m_Listener = null;
    private GridField				m_Field = null;
    
    /**
     * Set Field listener
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param m_Listener
     * @return void
     */
    public void setOnFieldChangeListener(OnFieldChangeListener m_Listener) {
    	this.m_Listener = m_Listener;
    }
    
    /**
     * Init Date
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
	private void init(){
		isInEditMode();
		//	Load Edit Text
		et_Date = (EditText) findViewById(R.id.et_Date);
		et_Date.setTextAppearance(getContext(), R.style.TextDynamicTabEditText);
		//	Load Button Date
		ib_Date = (ImageButton) findViewById(R.id.ib_Date);
		//	
		ib_Date.setOnClickListener(this);
		
		frontFormat = DisplayType.getDateFormat(getContext(), DisplayType.DATE);
		backFormat = DisplayType.getDateFormat_JDBC();
		
        // display the current date (this method is below)
        updateDisplay();

	}
	
	/**
	 * Refresh Display
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void updateDisplay() {
		if(cal != null)
			et_Date.setText(formatDate(cal.getTime()));
		else
			et_Date.setText("");
    }
    
	/**
	 * Set Date
	 */
    private OnDateSetListener mDateSetListener =
    	new OnDateSetListener() {

    		public void onDateSet(DatePicker view, int year, 
    				int monthOfYear, int dayOfMonth) {
                //	Set New Instance
    			if(cal == null)
                	cal = Calendar.getInstance();
    			cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDisplay();
                //	Listener
                if(m_Listener != null)
                	m_Listener.onFieldEvent(m_Field);
    		}
    };
    
    /**
     * Create Dialog
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return Dialog
     */
	private Dialog createDialog() {
		Calendar newCal;
		//	set new Cal
		if(cal != null)
			newCal = cal;
		else
			newCal = Calendar.getInstance();
		return new DatePickerDialog(ctx,
				mDateSetListener,
				newCal.get(Calendar.YEAR),
				newCal.get(Calendar.MONTH), 
				newCal.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * Get Date As String
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getDateAsString(){
		//	Get Date
        Date date = getDate();
        if(date != null)
        	return backFormat.format(date);
		return null;
	}
	
	/**
	 * Get Display Date
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getDisplayDate(){
		return et_Date.getText().toString();
	}
	
	/**
	 * Get Date
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return Date
	 */
	public Date getDate(){
		String date = et_Date.getText().toString();
		//	Valid Null
		if(date == null
				|| date.length() == 0)
			return null;
		//	do it
        try {
			return frontFormat.parse(date);
		} catch (ParseException e) {
			LogM.log(getContext(), getClass(), Level.SEVERE, "getDateAsString()", e);
		}
        return null;
	}
	
	/**
	 * Get Date with format
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param format
	 * @return
	 * @return Date
	 */
	public Date getDate(String format){
        String date = et_Date.getText().toString();
        //Date date;
		try {
			return frontFormat.parse(date);
		} catch (ParseException e) {
			LogM.log(getContext(), getClass(), Level.SEVERE, "getDate(String)", e);
		}       
        return null;
    }
	
	/**
	 * Format Date
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param date
	 * @return
	 * @return String
	 */
	private String formatDate(Date date){
		return frontFormat.format(date);
	}
	
	/**
	 * Set Date value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param date
	 * @return void
	 */
	public void setDate(Date date){
		try{
			if(date == null){
				cal = null;
			} else {
				if(cal == null)
					cal = Calendar.getInstance();
				cal.setTime(date);
			}
			updateDisplay();
		} catch(Exception e) {
			LogM.log(getContext(), getClass(), Level.SEVERE, "setDate()", e);
			et_Date.setText("");
		}
	}
	
	/**
	 * Set View format
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param format
	 * @return void
	 */
	public void setFormat(SimpleDateFormat format){
		this.frontFormat = format;
		updateDisplay();
	}
	
	/**
	 * Set Format
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param format
	 * @return void
	 */
	public void setFormat(String format){
		if(format == null)
			return;
		frontFormat = new SimpleDateFormat(format);
		updateDisplay();
	}
	
	/**
	 * Get View Format
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return SimpleDateFormat
	 */
	public SimpleDateFormat getFormat(){
		return frontFormat;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		et_Date.setEnabled(enabled);
		ib_Date.setEnabled(enabled);
		if(enabled) {
			et_Date.setTextColor(
					getResources().getColor(R.color.lookup_text_read_write));
		} else {
			et_Date.setTextColor(
					getResources().getColor(R.color.lookup_text_read_only));
		}
	}
	
	@Override
	public void onClick(View v) {
		createDialog().show();
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
        //	Set New Instance
		if(cal == null)
        	cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDisplay();
	}
	
	/**
	 * Set Listener
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param mDateSetListener
	 * @return void
	 */
	public void setOnDateSetListener(OnDateSetListener mDateSetListener){
		this.mDateSetListener = mDateSetListener;
	}
}
