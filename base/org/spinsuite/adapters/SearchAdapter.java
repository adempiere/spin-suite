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

import java.util.ArrayList;

import org.spinsuite.base.R;
import org.spinsuite.process.DocAction;
import org.spinsuite.util.DisplayRecordItem;
import org.spinsuite.util.Env;
import org.spinsuite.view.lookup.InfoLookup;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class SearchAdapter extends ArrayAdapter<DisplayRecordItem> {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/03/2014, 13:02:43
	 * @param ctx
	 * @param view_ID
	 * @param data
	 */
	public SearchAdapter(Context ctx, int view_ID, ArrayList<DisplayRecordItem> data) {
		super(ctx, view_ID, data);
		this.ctx = ctx;
		this.view_ID = view_ID;
		this.data = data;
		//	Get Preferred Height
		TypedValue value = Env.getResource(ctx, android.R.attr.listPreferredItemHeight);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((WindowManager)(ctx.getSystemService(Context.WINDOW_SERVICE)))
				.getDefaultDisplay().getMetrics(displayMetrics);
		height = value.getDimension(displayMetrics);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 22/03/2014, 09:22:14
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
	/**	Preferred Item Height			*/
	private float							height = 0;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = (LayoutInflater) ctx
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(view_ID, null);
			//	Set Height
			item.setMinimumHeight((int)height);
		}
		//	Get Current Item
		DisplayRecordItem recordItem = data.get(position);
		//	Process Text
		String name = "";
		String description = "";
		//	
		if(recordItem.getValue() != null) {
			String separator = InfoLookup.TABLE_SEARCH_SEPARATOR;
			int indexOf = recordItem.getValue().indexOf(separator);
			//	Verify other
			if(indexOf == -1) {
				separator = InfoLookup.TABLE_SEARCH_VIEW_SEPARATOR;
				indexOf = recordItem.getValue().indexOf(separator);
			}
			//	
			if(indexOf != -1) {
				name = recordItem.getValue().substring(0, indexOf);
				description = recordItem.getValue()
						.substring(indexOf + separator.length())
						.replaceAll(separator, Env.NL);
			} else {
				name = recordItem.getValue();
			}
		}
		//	Set Name
		TextView tv_Name = (TextView)item.findViewById(R.id.tv_Name);
		tv_Name.setTextAppearance(ctx, R.style.TextTitleList);
		tv_Name.setText(name);
		//	Set Description
		TextView tv_Description = (TextView)item.findViewById(R.id.tv_Description);
		tv_Description.setText(description);
		//	Set Image
		ImageView img_Item = (ImageView)item.findViewById(R.id.img_Item);
		if(recordItem.getImageURL() != null 
				&& recordItem.getImageURL().length() > 0){
			Resources res = ctx.getResources();
			int resID = res.getIdentifier(recordItem.getImageURL() , "drawable", ctx.getPackageName());
			if(resID != 0){
				Drawable drawable = res.getDrawable(resID);
				img_Item.setImageDrawable(drawable);
			}
		} else {
			//	Declare Attribute
			int attr_id = 0;
			//	
			if(recordItem.getDocStatus() == null
					|| recordItem.getDocStatus().equals("Y")) {
				attr_id = R.attr.ic_ls_ok;
			} else if(recordItem.getDocStatus().equals(DocAction.STATUS_Drafted)) {
				attr_id = R.attr.ic_ls_doc_status_draft;
			} else if(recordItem.getDocStatus().equals(DocAction.STATUS_InProgress)) {
				attr_id = R.attr.ic_ls_doc_status_in_progress;
			} else if(recordItem.getDocStatus().equals(DocAction.STATUS_Completed)) {
				attr_id = R.attr.ic_ls_doc_status_completed;
			} else if(recordItem.getDocStatus().equals(DocAction.STATUS_Closed)) {
				attr_id = R.attr.ic_ls_doc_status_closed;
			} else if(recordItem.getDocStatus().equals(DocAction.STATUS_Voided)) {
				attr_id = R.attr.ic_ls_doc_status_voided;
			} else if(recordItem.getDocStatus().equals(DocAction.STATUS_Reversed)) {
				attr_id = R.attr.ic_ls_doc_status_reversed;
			} else if(recordItem.getDocStatus().equals(DocAction.STATUS_Invalid)) {
				attr_id = R.attr.ic_ls_doc_status_invalid;
			}
			//	
			img_Item.setImageResource(
					Env.getResourceID(ctx, attr_id));
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
	            //	
	            return results;
	        }

	        /**
	         * Search
	         * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 02/03/2014, 03:19:33
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
	                    		&& item.getValue().toLowerCase(Env.getLocate())
	                    					.contains(constraint.toString().toLowerCase(Env.getLocate()))))
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
