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
package org.spinsuite.bchat.adapters;

import java.util.ArrayList;

import org.spinsuite.base.R;
import org.spinsuite.bchat.util.DisplayBChatThreadListItem;
import org.spinsuite.util.Env;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class BChatThreadListAdapter extends ArrayAdapter<DisplayBChatThreadListItem> {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/02/2014, 17:06:03
	 * @param ctx
	 * @param id_View
	 * @param data
	 */
	public BChatThreadListAdapter(Context ctx, ArrayList<DisplayBChatThreadListItem> data) {
		super(ctx, R.layout.i_bchat_thread_list, data);
		this.ctx = ctx;
		this.id_View = R.layout.i_bchat_thread_list;
		this.data = data;
	}

	/**	Context						*/
	private Context 								ctx;
	/**	Data						*/
	private ArrayList<DisplayBChatThreadListItem> 	data = new ArrayList<DisplayBChatThreadListItem>();
	/**	Backup						*/
	private ArrayList<DisplayBChatThreadListItem> 	originalData;
	/**	Identifier of View			*/
	private int 									id_View;
	/**	Max Size					*/
	private static final int						MAX_SIZE = 100;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(id_View, null);
		}
		
		DisplayBChatThreadListItem diti = data.get(position);
		
		//	Set Name
		TextView tv_Name = (TextView)item.findViewById(R.id.tv_Name);
		tv_Name.setText(diti.getValue());
		
		//	Set Description
		TextView tv_Description = (TextView)item.findViewById(R.id.tv_Conversation);
		tv_Description.setText(diti.getDescription());
		
		ImageView img_Item = (ImageView)item.findViewById(R.id.img_Contact);
		img_Item.setLayoutParams(new LayoutParams(MAX_SIZE, MAX_SIZE));
		//	Set Image
		if(diti.getImage() != null) {
			img_Item.setImageBitmap(diti.getImage());
		} else {
			img_Item.setImageResource(Env.getResourceID(ctx, R.attr.ic_dr_bc_action_person));
		}
		//	Set Time
		TextView tv_Time = (TextView)item.findViewById(R.id.tv_Time);
		tv_Time.setText(diti.getTimeAsString());
		//	Set Status
		TextView tv_Status = (TextView)item.findViewById(R.id.tv_Status);
		tv_Status.setText(diti.getStatus());
		//	Return
		return item;
	}
	
	@Override
	public Filter getFilter() {
	    return new Filter() {
	        @SuppressWarnings("unchecked")
	        @Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	            data = (ArrayList<DisplayBChatThreadListItem>) results.values;
	            if (results.count > 0) {
	            	notifyDataSetChanged();
	            } else {
	            	notifyDataSetInvalidated();
	            }  
	        }

	        @Override
	        protected FilterResults performFiltering(CharSequence constraint) {
	            //	Populate Original Data
	        	if(originalData == null)
	            	originalData = data;
	        	//	Get filter result
	        	ArrayList<DisplayBChatThreadListItem> filteredResults = getResults(constraint);
	            //	Result
	            FilterResults results = new FilterResults();
	            //	
	            results.values = filteredResults;
	            results.count = filteredResults.size();
	            //	
	            return results;
	        }

	        /**
	         * Search
	         * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 02/03/2014, 03:19:33
	         * @param constraint
	         * @return
	         * @return ArrayList<DisplayBChatThreadListItem>
	         */
	        private ArrayList<DisplayBChatThreadListItem> getResults(CharSequence constraint) {
	        	//	Verify
	            if(constraint != null
	            		&& constraint.length() > 0) {
	            	//	new Filter
	            	ArrayList<DisplayBChatThreadListItem> filteredResult = new ArrayList<DisplayBChatThreadListItem>();
	                for(DisplayBChatThreadListItem item : originalData) {
	                    if((item.getValue() != null 
	                    		&& item.getValue().toLowerCase(Env.getLocate())
	                    					.contains(constraint.toString().toLowerCase(Env.getLocate())))
	                    	|| (item.getDescription() != null 
		                    		&& item.getDescription().toLowerCase(Env.getLocate())
                					.contains(constraint.toString().toLowerCase(Env.getLocate())))) {
	                    	filteredResult.add(item);
	                    }
	                }
	                return filteredResult;
	            }
	            //	Only Data
	            return originalData;
	        }
	    };
	}
	
	@Override
	public int getCount() {
		return data.size();
	}
	
	@Override
	public DisplayBChatThreadListItem getItem(int position) {
		return data.get(position);
	}
}
