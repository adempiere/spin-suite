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

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.spinsuite.base.R;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.EditTextHolder;
import org.spinsuite.util.Env;
import org.spinsuite.util.SP_DisplayRecordItem;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * 
 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com 15/6/2015, 15:42:32
 *
 */
public class SP_SearchAdapter extends BaseAdapter implements Filterable {

	/**
	 * 
	 * *** Constructor ***
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param data
	 */
	public SP_SearchAdapter(Context ctx, ArrayList<SP_DisplayRecordItem> data) {
		this.data = data;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inputMethod = ((InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE));
		numberFormat = DisplayType.getNumberFormat(ctx, DisplayType.QUANTITY);
		notifyDataSetChanged();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 */
	public SP_SearchAdapter(Context ctx) {
		data = new ArrayList<SP_DisplayRecordItem>();
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inputMethod = ((InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE));
		numberFormat = DisplayType.getNumberFormat(ctx, DisplayType.QUANTITY);
		notifyDataSetChanged();
	}
	
	/**	Data							*/
	private ArrayList<SP_DisplayRecordItem> 	data;
	/**	Backup							*/
	private ArrayList<SP_DisplayRecordItem> 	originalData;
	/**	Inflater						*/
	private LayoutInflater 						inflater = null;
	/**	Input Method					*/
	private InputMethodManager					inputMethod = null;
	
	/**	Decimal Format				*/
	private DecimalFormat				numberFormat = null;
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {		
		View view = convertView;
		//	
		final SP_DisplayRecordItem recordItem = data.get(position);
		//	
		final EditTextHolder holderQtyOrdered = new EditTextHolder();
			
		//	Inflate View
		if(view == null)
			view = inflater.inflate(R.layout.i_ol_addproduct, null);

		//	Set Quantity to Order
		EditText et_QtyOrdered = (EditText)view.findViewById(R.id.et_Qty);
		//	Instance Holder
		holderQtyOrdered.setText(String.valueOf(recordItem.getQty()));
		holderQtyOrdered.setEditText(et_QtyOrdered);
		holderQtyOrdered.getEditText().setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus) {
					//	Set Value
					setNewValue(recordItem, holderQtyOrdered.getText(), position);
				} else {
					holderQtyOrdered.getEditText().selectAll();
				}
			}
		});
		
		holderQtyOrdered.getEditText().setOnEditorActionListener(new OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_DONE
		        		|| actionId == EditorInfo.IME_ACTION_NEXT) {
		        	//	Set Value
		        	setNewValue(recordItem, holderQtyOrdered.getText(), position);
		        }
		        //	
		        return false;
		    }
		});
		//	Add Listener
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				holderQtyOrdered.getEditText().requestFocus();
				holderQtyOrdered.getEditText().selectAll();
				//	Show Keyboard
				inputMethod.toggleSoftInput(InputMethodManager.SHOW_FORCED, 
						InputMethodManager.HIDE_IMPLICIT_ONLY);
			}
		});
	
		//	Set Quantity
		holderQtyOrdered.setText(String.valueOf(recordItem.getQty()));
		view.setTag(holderQtyOrdered);

		//	Set Product Value
		TextView tv_ProductValue = (TextView)view.findViewById(R.id.tv_ProductValue);
		tv_ProductValue.setText(recordItem.getValue());

		//	Set Product Name
		TextView tv_ProductName = (TextView)view.findViewById(R.id.tv_ProductName);
		tv_ProductName.setText(recordItem.getName());
		
		//	Set Product Description
		TextView tv_ProductDesc = (TextView)view.findViewById(R.id.tv_ProductDescription);
		tv_ProductDesc.setText(recordItem.getDescription());
		
		//	Set UOM Symbol
		TextView tv_UOMSymbol = (TextView)view.findViewById(R.id.tv_UOMSymbol);
		tv_UOMSymbol.setText(recordItem.getuOMSymbol());

		//	Return
		return view;
	}
	
	/**
	 * Set New Value
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_NewItem
	 * @param p_Value
	 * @param position
	 * @return void
	 */
	private void setNewValue(SP_DisplayRecordItem p_NewItem, String p_Value, int position) {
		p_NewItem.setQty(DisplayType.getNumber(p_Value));
		//	Set Item
		data.set(position, p_NewItem);
		//	Set to Original Data
		if(originalData != null)
			setToOriginalData(p_NewItem);
	}

	/**
	 * Set To Original Data
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Item
	 * @return void
	 */
	private void setToOriginalData(SP_DisplayRecordItem p_Item) {
		if(p_Item == null
				|| originalData == null)
			return;
		//	Search
		for(int i = 0; i < originalData.size(); i++) {
            if(originalData.get(i).getProduct_ID() == p_Item.getProduct_ID())
            		originalData.set(i, p_Item);
        }
	}
	
	
	@Override
	public Filter getFilter() {
		
	    return new Filter() {
	        @SuppressWarnings("unchecked")
	        @Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	            data = (ArrayList<SP_DisplayRecordItem>) results.values;
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
	        	ArrayList<SP_DisplayRecordItem> filteredResults = getResults(constraint);
	            //	Result
	            FilterResults results = new FilterResults();
	            //	
	            results.values = filteredResults;
	            results.count = filteredResults.size();
	            
	            return results;
	        }

	        /**
	         * Search
	         * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	         * @param constraint
	         * @return
	         * @return ArrayList<SP_DisplayRecordItem>
	         */
	        private ArrayList<SP_DisplayRecordItem> getResults(CharSequence constraint) {
	        	//	Verify
	            if(constraint != null
	            		&& constraint.length() > 0) {
	            	//	new Filter
	            	ArrayList<SP_DisplayRecordItem> filteredResult = new ArrayList<SP_DisplayRecordItem>();
	                for(SP_DisplayRecordItem item : originalData) {
	                    if((item.getName()!= null 
	                    		&& item.getName().toLowerCase().contains(constraint.toString())))
	                        filteredResult.add(item);
	                }
	                return filteredResult;
	            }
	            //	Only Data
	            return originalData;
	            //return data;
	        }
	    };
	}
	
	
	@Override
	public int getCount() {
		return data.size();
	}
	
	@Override
	public SP_DisplayRecordItem getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * Get Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/08/2014, 03:29:00
	 * @return
	 * @return ArrayList<SP_DisplayRecordItem>
	 */
	public ArrayList<SP_DisplayRecordItem> getData() {
		return data;
	}
	
	 
	/**
	 * Get data only selected
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return ArrayList<SP_DisplayRecordItem>
	 */
	public ArrayList<SP_DisplayRecordItem> getSelectedData() {
		//	Temp Data
		ArrayList<SP_DisplayRecordItem> tmpData = new ArrayList<SP_DisplayRecordItem>();
		//	Save all
		if(originalData != null)
			data = originalData;
		//	Get only selected
		for(SP_DisplayRecordItem item : data) {
			//	Add
			if(item.getQty().compareTo(Env.ZERO) == 1)
				tmpData.add(item);
		}
		return tmpData;
	}
	
}
