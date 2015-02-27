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
package org.spinsuite.fta.adapters;

import java.util.ArrayList;

import org.spinsuite.base.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class TFLineAdapter extends ArrayAdapter<DisplayTFLine> {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 17:06:03
	 * @param ctx
	 * @param data
	 */
	public TFLineAdapter(Context ctx, ArrayList<DisplayTFLine> data) {
		super(ctx, R.layout.i_tf_line, data);
		this.ctx = ctx;
		this.data = data;
	}
	
	/**	Context						*/
	private Context 					ctx;
	/**	Data						*/
	private ArrayList<DisplayTFLine> 	data;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(R.layout.i_tf_line, null);
		}
		
		DisplayTFLine mi = data.get(position);
		
		//	Set Farm
		TextView tv_Farm = (TextView)item.findViewById(R.id.tv_Farm);
		tv_Farm.setText(mi.getFarm());
		//	Set Farm Division
		TextView tv_FarmDivision = (TextView)item.findViewById(R.id.tv_FarmDivision);
		tv_FarmDivision.setText(mi.getFarmDivision());
		//	Set Farming
		TextView tv_Farming = (TextView)item.findViewById(R.id.tv_Farming);
		tv_Farming.setText(mi.getFarming());
		//	Set Farming Stage
		TextView tv_FarmingStage = (TextView)item.findViewById(R.id.tv_FarmingStage);
		tv_FarmingStage.setText(mi.getFarmingStage());
		//	Set Observation Type
		TextView tv_ObservationType = (TextView)item.findViewById(R.id.tv_ObservationType);
		tv_ObservationType.setText(mi.getObservationType());
		//	Set Comments
		TextView tv_Comments = (TextView)item.findViewById(R.id.tv_Comments);
		tv_Comments.setText(mi.getComments());		
		//	Return
		return item;
	}
	
}
