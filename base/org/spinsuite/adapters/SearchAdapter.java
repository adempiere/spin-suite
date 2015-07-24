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
import org.spinsuite.util.DisplaySearchItem;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.IdentifierValueWrapper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * <li> Bug in Standard Search
 * @see https://adempiere.atlassian.net/browse/SPIN-23
 */
public class SearchAdapter extends ArrayAdapter<DisplaySearchItem> {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/03/2014, 13:02:43
	 * @param ctx
	 * @param data
	 */
	public SearchAdapter(Context ctx, ArrayList<DisplaySearchItem> data) {
		super(ctx, R.layout.i_search, data);
		this.ctx = ctx;
		this.view_ID = R.layout.i_search;
		setDropDownViewResource(R.layout.i_search);
		this.data = data;
		//	Get Preferred Height
		TypedValue value = Env.getResource(ctx, android.R.attr.listPreferredItemHeight);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((WindowManager)(ctx.getSystemService(Context.WINDOW_SERVICE)))
				.getDefaultDisplay().getMetrics(displayMetrics);
		height = value.getDimension(displayMetrics);
		//	Set Parameter
    	v_param = new LayoutParams(LayoutParams.MATCH_PARENT, 
    			LayoutParams.WRAP_CONTENT);
	}

	/**	Context							*/
	private Context 						ctx;
	/**	Data							*/
	private ArrayList<DisplaySearchItem> 	data;
	/**	Backup							*/
	private ArrayList<DisplaySearchItem> 	originalData;
	/**	View Identifier					*/
	private int 							view_ID;
	/**	Preferred Item Height			*/
	private float							height = 0;
	/**	Column Parameter				*/
	private LayoutParams					v_param	= null;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		View item = convertView;
		//	Get Current Item
		DisplaySearchItem recordItem = data.get(position);
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
		LinearLayout ll_Text = (LinearLayout) item.findViewById(R.id.ll_Text);
		//	Set Name
		TextView tv_Name = (TextView)item.findViewById(R.id.tv_Name);
		tv_Name.setTextAppearance(ctx, R.style.TextTitleList);
		//	
		if(recordItem.getDisplayValues() != null) {
			//	Get Array
			IdentifierValueWrapper m_DisplayValues[] = recordItem.getDisplayValues();
			//	Extract name from value
			if(m_DisplayValues != null) {
				boolean isFirst = true;
				//	
				if((ll_Text.getChildCount() - 1) > 0) {
					ll_Text.removeViews(1, ll_Text.getChildCount() - 1);
				}
				//	Get Values
				for(int i = 0; i < m_DisplayValues.length; i++) {
					IdentifierValueWrapper value = m_DisplayValues[i];
					//	Valid Null
					if(value.getValue() == null
						|| value.getValue().length() == 0)
						continue;
					//	
					if(isFirst) {
						tv_Name.setText(value.getValue());
						isFirst = false;
					} else {
						TextView tv_ValueAdded = null;
						if(ll_Text.getChildCount() > i) {
							tv_ValueAdded = (TextView) ll_Text.getChildAt(i);
							tv_ValueAdded.setText(value.getName() + ": " + value.getValue());
						} else {
							tv_ValueAdded = loadDescriptionTextView(value.getName() + ": " + value.getValue() , false);
							ll_Text.addView(tv_ValueAdded);
						}
						//	
						if(DisplayType.isNumeric(value.getDisplayType())) {
							tv_ValueAdded.setGravity(Gravity.END);
						}
					}
				}
			}
		}
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
	
	/**
	 * Load Description Text view for list
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param text
	 * @param isBold
	 * @return
	 * @return TextView
	 */
	private TextView loadDescriptionTextView(String text, boolean isBold){
    	//	New Text Field
		TextView tv_column = new TextView(ctx);
		//	Set Parameters
		tv_column.setLayoutParams(v_param);
		//	
		if(isBold)
			tv_column.setTextAppearance(ctx, R.style.TextItemMenu);
		else
			tv_column.setTextAppearance(ctx, R.style.TextItemSmallMenu);
		//	Set Text
		tv_column.setText(text);
		//	Return
		return tv_column;
	}
	
	@Override
	public Filter getFilter() {
	    return new Filter() {
	        @SuppressWarnings("unchecked")
	        @Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	            data = (ArrayList<DisplaySearchItem>) results.values;
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
	        	ArrayList<DisplaySearchItem> filteredResults = getResults(constraint);
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
	         * @return ArrayList<DisplaySearchItem>
	         */
	        private ArrayList<DisplaySearchItem> getResults(CharSequence constraint) {
	        	//	Verify
	            if(constraint != null
	            		&& constraint.length() > 0) {
	            	//	new Filter
	            	ArrayList<DisplaySearchItem> filteredResult = new ArrayList<DisplaySearchItem>();
	                for(DisplaySearchItem item : originalData) {
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
	public DisplaySearchItem getItem(int position) {
		return data.get(position);
	}
	
}
