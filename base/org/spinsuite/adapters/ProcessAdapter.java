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
package org.spinsuite.adapters;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.spinsuite.base.R;
import org.spinsuite.process.ProcessInfoLog;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.Msg;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * <li> Bug in Standard Search
 * @see https://adempiere.atlassian.net/browse/SPIN-23
 * <li> Add Support to Menu in Standard Search
 * @see https://adempiere.atlassian.net/browse/SPIN-26
 */
public class ProcessAdapter extends ArrayAdapter<ProcessInfoLog> {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/03/2014, 13:02:43
	 * @param ctx
	 * @param data
	 * @param p_TableName
	 */
	public ProcessAdapter(Context ctx, List<ProcessInfoLog> data) {
		super(ctx, R.layout.i_process, data);
		this.ctx = ctx;
		this.view_ID = R.layout.i_process;
		setDropDownViewResource(R.layout.i_process);
		this.data = data;
		//	Get Preferred Height
		TypedValue value = Env.getResource(ctx, android.R.attr.listPreferredItemHeight);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((WindowManager)(ctx.getSystemService(Context.WINDOW_SERVICE)))
				.getDefaultDisplay().getMetrics(displayMetrics);
		height = value.getDimension(displayMetrics);
		//	Date Format
    	m_DateFormat = DisplayType.getDateFormat(ctx);
    	m_NumberFormat = DisplayType.getNumberFormat(ctx, DisplayType.NUMBER);
	}

	/**	Context							*/
	private Context 						ctx;
	/**	Data							*/
	private List<ProcessInfoLog> 			data;
	/**	View Identifier					*/
	private int 							view_ID;
	/**	Preferred Item Height			*/
	private float							height = 0;
	/**	Date Format						*/
	private SimpleDateFormat 				m_DateFormat = null;
	/**	Number Format					*/
	private NumberFormat 					m_NumberFormat = null;
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {		
		View item = convertView;
		//	Get Current Item
		ProcessInfoLog recordItem = data.get(position);
		//		
		if(item == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(view_ID, null);
			//	Set Height
			item.setMinimumHeight((int)height);
		}
		//	Set Values
		//	Get Linear Layout
		LinearLayout ll_Date = (LinearLayout) item.findViewById(R.id.ll_Date);
		//	Set Name
		TextView tv_Msg = (TextView)item.findViewById(R.id.tv_Msg);
		if(recordItem.getP_Msg() != null) {
			tv_Msg.setText(Msg.parseTranslation(ctx, recordItem.getP_Msg()));
		}
		//	For Date and Number
		if(recordItem.getP_Date() != null
				|| recordItem.getP_Number() != null) {
			//	For Date
			if(recordItem.getP_Date() != null) {
				TextView tv_Date = (TextView)item.findViewById(R.id.tv_Date);
				tv_Date.setText(m_DateFormat.format(recordItem.getP_Date()));
			}
			//	For Number
			if(recordItem.getP_Number() != null) {
				TextView tv_Number = (TextView)item.findViewById(R.id.tv_Number);
				tv_Number.setText(m_NumberFormat.format(recordItem.getP_Number()));
			}
		} else {
			ll_Date.setVisibility(View.GONE);
		}
		//	Return
		return item;
	}
}