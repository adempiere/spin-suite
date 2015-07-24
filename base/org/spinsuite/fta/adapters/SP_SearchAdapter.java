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

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.spinsuite.base.R;
import org.spinsuite.fta.util.EditTextHolder;
import org.spinsuite.fta.util.SP_DisplayRecordItem;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class SP_SearchAdapter extends BaseAdapter implements Filterable {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/03/2014, 13:02:43
	 * @param ctx
	 * @param data
	 */
	public SP_SearchAdapter(Context ctx, ArrayList<SP_DisplayRecordItem> data) {
		this.data = data;
		numberFormat = DisplayType.getNumberFormat(ctx, DisplayType.QUANTITY);
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inputMethod = ((InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE));
		m_QtyFormat = DisplayType.getNumberFormat(ctx, DisplayType.QUANTITY, "###,###,###,##0.00");
		notifyDataSetChanged();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/03/2014, 09:22:14
	 * @param ctx
	 * @param view_ID
	 */
	public SP_SearchAdapter(Context ctx) {
		data = new ArrayList<SP_DisplayRecordItem>();
		numberFormat = DisplayType.getNumberFormat(ctx, DisplayType.QUANTITY);
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inputMethod = ((InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE));
		m_QtyFormat = DisplayType.getNumberFormat(ctx, DisplayType.QUANTITY, "###,###,###,##0.00");
		notifyDataSetChanged();
	}
	
	/**	Data							*/
	private ArrayList<SP_DisplayRecordItem> 	data;
	/**	Backup							*/
	private ArrayList<SP_DisplayRecordItem> 	originalData;
	/**	Decimal Format					*/
	private DecimalFormat						numberFormat = null;
	/**	Inflater						*/
	private LayoutInflater 						inflater = null;
	/**	Input Method					*/
	private InputMethodManager					inputMethod = null;
	/**	Decimal Format					*/
	private DecimalFormat						m_QtyFormat = null;
	/**	Has Focus						*/
	private boolean 							m_HasFocus = false;
	/**	Current Item					*/
	private SP_DisplayRecordItem				m_CurrentRecordItem = null;
	/**	Current Quantity				*/
	private String								m_CurrentValue = null;
	/**	Current Position				*/
	private int									m_CurrentPosition = 0;
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {		
		View view = convertView;
		//	
		final SP_DisplayRecordItem recordItem = data.get(position);
		//	
		final EditTextHolder holderQtyOrdered = new EditTextHolder();
		//	Inflate View
		if(view == null)
			view = inflater.inflate(R.layout.i_suggested_product_search, null);	
//		LinearLayout ll_sp_description = (LinearLayout) view.findViewById(R.id.ll_sp_description);
		LinearLayout ll_sp_qty = (LinearLayout) view.findViewById(R.id.ll_sp_qty);
		//	Set Quantity to Order
		EditText et_QtyOrdered = (EditText)view.findViewById(R.id.et_QtyOrdered);
		//	Instance Holder
		holderQtyOrdered.setEditText(et_QtyOrdered);
		holderQtyOrdered.setText(m_QtyFormat.format(recordItem.getQty()));
		holderQtyOrdered.getEditText().setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus) {
					//	Set Value
					setNewValue(recordItem, holderQtyOrdered.getText(), position);
				} else {
					holderQtyOrdered.getEditText().selectAll();
				}
				//	Set Has Focus
				m_HasFocus = hasFocus;
				//	
				inputMethod.toggleSoftInput(InputMethodManager.SHOW_FORCED, 
						InputMethodManager.HIDE_IMPLICIT_ONLY);
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
		ll_sp_qty.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				holderQtyOrdered.getEditText().requestFocus();
				holderQtyOrdered.getEditText().selectAll();
			}
		});
		//	For Changes in Key
		holderQtyOrdered.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				//	Valid Focus
				if(!m_HasFocus)
					return;
				//	
				m_CurrentValue = s.toString();
				m_CurrentPosition = position;
				m_CurrentRecordItem = recordItem;
			}
		});
		view.setTag(holderQtyOrdered);
		
		//	Set Category
		TextView tv_ProductCategory = (TextView)view.findViewById(R.id.tv_ProductCategory);
		tv_ProductCategory.setText(recordItem.getProductCategory());
		//	Set Value
		TextView tv_ProductValue = (TextView)view.findViewById(R.id.tv_ProductValue);
		tv_ProductValue.setText(recordItem.getProductValue());
		//	Set Name
		TextView tv_ProductName = (TextView)view.findViewById(R.id.tv_ProductName);
		tv_ProductName.setText(recordItem.getProductName());
		//	Set Description
		TextView tv_ProductDescription = (TextView)view.findViewById(R.id.tv_ProductDescription);
		if(recordItem.getProductDescription() == null) {
			tv_ProductDescription.setVisibility(View.GONE);
		} else {
			tv_ProductDescription.setVisibility(View.GONE);
		}
		//	
		tv_ProductDescription.setText(recordItem.getProductDescription());
		//	Set Day From
		TextView tv_DayFrom = (TextView)view.findViewById(R.id.tv_DayFrom);
		tv_DayFrom.setText(numberFormat.format(recordItem.getDayFrom()));
		//	Set Day To
		TextView tv_DayTo = (TextView)view.findViewById(R.id.tv_DayTo);
		tv_DayTo.setText(numberFormat.format(recordItem.getDayTo()));
		//	Set Quantity Suggested
		TextView tv_QtySuggested = (TextView)view.findViewById(R.id.tv_QtySuggested);
		tv_QtySuggested.setText(numberFormat.format(recordItem.getQtySuggested()) 
				+ (recordItem.getSuggestedUOMSymbol() != null? " " + recordItem.getSuggestedUOMSymbol(): ""));
		//	Set Quantity Dosage
		TextView tv_QtyDosage = (TextView)view.findViewById(R.id.tv_QtyDosage);
		tv_QtyDosage.setText(numberFormat.format(recordItem.getQtyDosage()) 
				+ (recordItem.getDosageUOMSymbol() != null? " " + recordItem.getDosageUOMSymbol(): ""));
		//	Return
		return view;
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
	         * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 02/03/2014, 03:19:33
	         * @param constraint
	         * @return
	         * @return ArrayList<DisplayRecordItem>
	         */
	        private ArrayList<SP_DisplayRecordItem> getResults(CharSequence constraint) {
	        	//	Verify
	            if(constraint != null
	            		&& constraint.length() > 0) {
	            	//	new Filter
	            	ArrayList<SP_DisplayRecordItem> filteredResult = new ArrayList<SP_DisplayRecordItem>();
	                for(SP_DisplayRecordItem item : originalData) {
	                    if((item.getProductCategory() != null 
	                    		&& item.getProductCategory().toLowerCase().contains(constraint.toString()))
	                    	|| (item.getProductValue() != null 
		                    		&& item.getProductValue().toLowerCase().contains(constraint.toString()))
	                    	|| (item.getProductName() != null 
	                    		&& item.getProductName().toLowerCase().contains(constraint.toString()))
	                    	|| (item.getProductDescription() != null 
		                    		&& item.getProductDescription().toLowerCase().contains(constraint.toString())))
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
	
	/**
	 * Set Current Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	public void setCurrentValue() {
		if(m_CurrentRecordItem == null) {
			return;
		}
		//	
		setNewValue(m_CurrentRecordItem, m_CurrentValue, m_CurrentPosition);
		m_CurrentRecordItem = null;
		m_HasFocus = false;
	}
	
	/**
	 * Set New Value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 29/08/2014, 21:10:27
	 * @param p_NewItem
	 * @param p_Value
	 * @param position
	 * @return void
	 */
	private void setNewValue(SP_DisplayRecordItem p_NewItem, String p_Value, int position) {
		p_NewItem.setQty(DisplayType.getNumber(p_Value));
		//	Valid same Product
		if(position < data.size()) {
			SP_DisplayRecordItem m_OldItem = data.get(position);
			//	Validate Product
			if(m_OldItem.getM_Product_ID() == p_NewItem.getM_Product_ID()) {
				data.set(position, p_NewItem);
			} else {
				return;
			}
		}
		//	Set to Original Data
		if(originalData != null)
			setToOriginalData(p_NewItem);
	}
	
	/**
	 * Set To Original Data
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 28/08/2014, 10:10:38
	 * @param p_Item
	 * @return void
	 */
	private void setToOriginalData(SP_DisplayRecordItem p_Item) {
		if(p_Item == null
				|| originalData == null)
			return;
		//	Search
		for(int i = 0; i < originalData.size(); i++) {
            if(originalData.get(i).getM_Product_ID() == p_Item.getM_Product_ID())
            		originalData.set(i, p_Item);
        }
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 31/08/2014, 03:32:03
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
