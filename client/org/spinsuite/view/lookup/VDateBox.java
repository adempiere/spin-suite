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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

import org.spinsuite.base.R;
import org.spinsuite.interfaces.OnFieldChangeListener;
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
 * @author Yamel Senih
 *
 */
public class VDateBox extends LinearLayout implements OnClickListener, OnDateSetListener {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 10:56:21
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 21:12:47
	 * @param context
	 * @param format
	 * @param m_Field
	 */
	public VDateBox(Context context, SimpleDateFormat format, GridField m_Field){
		super(context);
		ctx = context;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.v_lookup_date, this);
        formatFront = format;
        this.m_Field = m_Field;
        init();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 10:56:28
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
    private SimpleDateFormat 		formatFront = null;
    private SimpleDateFormat 		formatBack = null;
    private Calendar 				cal;
    private OnFieldChangeListener 	m_Listener = null;
    private GridField				m_Field = null;
    
    /**
     * Set Field Listener
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/05/2014, 21:39:08
     * @param m_Listener
     * @return void
     */
    public void setOnFieldChangeListener(OnFieldChangeListener m_Listener) {
    	this.m_Listener = m_Listener;
    }
    
    /**
     * Init
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 12:07:35
     * @return void
     */
	private void init(){
		isInEditMode();
		//	Load Edit Text
		et_Date = (EditText) findViewById(R.id.et_Date);
		//	Load Button Date
		ib_Date = (ImageButton) findViewById(R.id.ib_Date);
		//	
		ib_Date.setOnClickListener(this);
		
		formatFront = new SimpleDateFormat("dd/MM/yyyy");
		formatBack = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
        // display the current date (this method is below)
        updateDisplay();

	}
	
	/**
	 * Update the display
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 10:57:05
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
     * Create Dialog for set Date
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 10:59:06
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 10:34:57
	 * @return
	 * @return String
	 */
	public String getDateAsString(){
		//	Get Date
        Date date = getDate();
        if(date != null)
        	return formatBack.format(date);
		return null;
	}
	
	/**
	 * Get Display Date
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/04/2014, 10:41:10
	 * @return
	 * @return String
	 */
	public String getDisplayDate(){
		return et_Date.getText().toString();
	}
	
	/**
	 * Get Date
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 10:40:23
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
			return formatFront.parse(date);
		} catch (ParseException e) {
			LogM.log(getContext(), getClass(), Level.SEVERE, "getDateAsString()", e);
		}
        return null;
	}
	
	/**
	 * Get date with formatt
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 11:04:45
	 * @param format
	 * @return
	 * @return String
	 */
	public Date getDate(String format){
        String date = et_Date.getText().toString();
        //Date date;
		try {
			return formatFront.parse(date);
		} catch (ParseException e) {
			LogM.log(getContext(), getClass(), Level.SEVERE, "getDate(String)", e);
		}       
        return null;
    }
	
	/**
	 * Set format to date
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 11:07:00
	 * @param date
	 * @return
	 * @return String
	 */
	private String formatDate(Date date){
		return formatFront.format(date);
	}
	
	/**
	 * Set Date
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 12:09:20
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
	 * Set View Format
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 12:10:23
	 * @param format
	 * @return void
	 */
	public void setFormat(SimpleDateFormat format){
		this.formatFront = format;
		updateDisplay();
	}
	
	/**
	 * Set View Format
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 21:01:16
	 * @param format
	 * @return void
	 */
	public void setFormat(String format){
		if(format == null)
			return;
		formatFront = new SimpleDateFormat(format);
		updateDisplay();
	}
	
	/**
	 * Get View Format
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 12:10:34
	 * @return
	 * @return SimpleDateFormat
	 */
	public SimpleDateFormat getFormat(){
		return formatFront;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		et_Date.setEnabled(enabled);
		ib_Date.setEnabled(enabled);
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
	 * Set Date Listener
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/02/2014, 12:11:01
	 * @param mDateSetListener
	 * @return void
	 */
	public void setOnDateSetListener(OnDateSetListener mDateSetListener){
		this.mDateSetListener = mDateSetListener;
	}
}
