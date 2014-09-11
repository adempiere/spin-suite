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
package org.spinsuite.adapters;

import java.util.ArrayList;

import org.spinsuite.base.R;
import org.spinsuite.util.DisplayRecordItem;
import org.spinsuite.util.Env;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class SearchAdapter extends ArrayAdapter<DisplayRecordItem> {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 13:02:43
	 * @param ctx
	 * @param view_ID
	 * @param data
	 */
	public SearchAdapter(Context ctx, int view_ID, ArrayList<DisplayRecordItem> data) {
		super(ctx, view_ID, data);
		this.ctx = ctx;
		this.view_ID = view_ID;
		this.data = data;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/03/2014, 09:22:14
	 * @param ctx
	 * @param view_ID
	 */
	public SearchAdapter(Context ctx, int view_ID) {
		super(ctx, view_ID);
		this.ctx = ctx;
		this.view_ID = view_ID;
		data = new ArrayList<DisplayRecordItem>();
	}

	/**	Context							*/
	private Context 						ctx;
	/**	Data							*/
	private ArrayList<DisplayRecordItem> 	data;
	/**	Backup							*/
	private ArrayList<DisplayRecordItem> 	originalData;
	/**	View Identifier					*/
	private int 							view_ID;
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = (LayoutInflater) ctx
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(view_ID, null);
		}
		
		DisplayRecordItem recordItem = data.get(position);
		
		//	Set Name
		TextView tv_Value = (TextView)item.findViewById(R.id.tv_Value);
		tv_Value.setText(recordItem.getValue());
		
		if(recordItem.getImageURL() != null 
				&& recordItem.getImageURL().length() > 0){
			Resources res = ctx.getResources();
			int resID = res.getIdentifier(recordItem.getImageURL() , "drawable", ctx.getPackageName());
			if(resID != 0){
				Drawable drawable = res.getDrawable(resID);
				tv_Value.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
			}
		} else {
			tv_Value.setCompoundDrawablesWithIntrinsicBounds(
					Env.getResourceID(getContext(), R.attr.ic_ls_ok), 0, 0, 0);
		}
		//	Return
		return item;
	}
	
	@Override
	public Filter getFilter() {
	    return new Filter() {
	        @SuppressWarnings("unchecked")
	        @Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	            data = (ArrayList<DisplayRecordItem>) results.values;
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
	        	ArrayList<DisplayRecordItem> filteredResults = getResults(constraint);
	            //	Result
	            FilterResults results = new FilterResults();
	            //	
	            results.values = filteredResults;
	            results.count = filteredResults.size();
	            
	            return results;
	        }

	        /**
	         * Search
	         * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/03/2014, 03:19:33
	         * @param constraint
	         * @return
	         * @return ArrayList<DisplayRecordItem>
	         */
	        private ArrayList<DisplayRecordItem> getResults(CharSequence constraint) {
	        	//	Verify
	            if(constraint != null
	            		&& constraint.length() > 0) {
	            	//	new Filter
	            	ArrayList<DisplayRecordItem> filteredResult = new ArrayList<DisplayRecordItem>();
	                for(DisplayRecordItem item : originalData) {
	                    if((item.getValue() != null 
	                    		&& item.getValue().toLowerCase().contains(constraint.toString())))
	                        filteredResult.add(item);
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
	public DisplayRecordItem getItem(int position) {
		return data.get(position);
	}
	
}
