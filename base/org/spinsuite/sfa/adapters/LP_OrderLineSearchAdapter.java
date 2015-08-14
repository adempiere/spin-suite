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
package org.spinsuite.sfa.adapters;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.sfa.util.DisplayListProduct;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.EditTextHolder;
import org.spinsuite.util.Env;
import org.spinsuite.util.Msg;
import org.spinsuite.util.contribution.ActionItem;
import org.spinsuite.util.contribution.QuickAction;

import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * 
 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com 15/6/2015, 15:42:32
 * @contributor Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * <li> Bad result when get product
 * @see https://adempiere.atlassian.net/browse/SPIN-21 
 *
 */
public class LP_OrderLineSearchAdapter extends BaseAdapter implements Filterable {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param p_C_BPartner_Location_ID
	 * @param data
	 */
	public LP_OrderLineSearchAdapter(Context ctx, int p_C_BPartner_Location_ID, ArrayList<DisplayListProduct> data) {
		this.data = data;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		m_QtyFormat = DisplayType.getNumberFormat(ctx, DisplayType.QUANTITY, "###,###,###,##0.00");
		m_AmtFormat = DisplayType.getNumberFormat(ctx, DisplayType.AMOUNT, "###,###,###,##0.00");
		m_ctx = ctx;
		m_QAct = new QuickAction(ctx);
		m_C_BPartner_Location_ID = p_C_BPartner_Location_ID;
		notifyDataSetChanged();
	}
	
	/**	Data							*/
	private ArrayList<DisplayListProduct> 		data;
	/**	Backup							*/
	private ArrayList<DisplayListProduct> 		originalData;
	/**	Inflater						*/
	private LayoutInflater 						inflater = null;
	/**	Decimal Format					*/
	private DecimalFormat						m_QtyFormat = null;
	/**	Decimal Format					*/
	private DecimalFormat						m_AmtFormat = null;
	/**	Current Item					*/
	private DisplayListProduct					m_CurrentRecordItem = null;
	/**	Current Quantity Ordered		*/
	private String								m_CurrentValue = null;
	/**	Current Position				*/
	private int									m_CurrentPosition = 0;
	/**	Current Text View Amount		*/
	private TextView							m_CurrentLineNetAmt = null;
	/**	Has Focus						*/
	private boolean 							m_HasFocus = false;
	/**	Context							*/
	private Context								m_ctx;
	/**	View							*/
	private QuickAction 						m_QAct = null;
	/**	Business Partner				*/
	private int									m_C_BPartner_Location_ID = 0;
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {		
		View view = convertView;
		//	
		final DisplayListProduct recordItem = data.get(position);
		//	
		final EditTextHolder holderQtyEntered = new EditTextHolder();
			
		//	Inflate View
		if(view == null)
			view = inflater.inflate(R.layout.i_ol_add_product, null);

		//	Set Quantity to Order
		EditText et_QtyOrdered = (EditText)view.findViewById(R.id.et_Qty);
		LinearLayout ll_ol_product_description = (LinearLayout) view.findViewById(R.id.ll_ol_product_description);
		LinearLayout ll_ol_qty_description = (LinearLayout) view.findViewById(R.id.ll_ol_qty_description);
		
		//	Instance Holder
		holderQtyEntered.setText(m_QtyFormat.format(recordItem.getQtyEntered()));
		holderQtyEntered.setEditText(et_QtyOrdered);		
		//	Add Listener
		ll_ol_product_description.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadLastSO(recordItem.getM_Product_ID(), v);
			}
		});
		//	
		ll_ol_qty_description.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				holderQtyEntered.getEditText().requestFocus();
				holderQtyEntered.getEditText().selectAll();
			}
		});
		//	Set Quantity
		holderQtyEntered.setText(m_QtyFormat.format(recordItem.getQtyEntered()));
		//	Set Product Value
		TextView tv_ProductCategory = (TextView)view.findViewById(R.id.tv_ProductCategory);
		tv_ProductCategory.setText(recordItem.getProductCategory());
		
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
		tv_UOMSymbol.setText(recordItem.getUOMSymbol());

		//	Set Price List
		TextView tv_PriceList = (TextView)view.findViewById(R.id.tv_PriceList);
		tv_PriceList.setText(m_AmtFormat.format(recordItem.getPriceList()));
		
		//	Set Tax Indicator
		TextView tv_lb_TaxInicator = (TextView)view.findViewById(R.id.tv_lb_TaxIndicator);
		tv_lb_TaxInicator.setText(recordItem.getTaxIndicator());
		
		//	Set Tax Amount
		TextView tv_TaxRate = (TextView)view.findViewById(R.id.tv_TaxRate);
		tv_TaxRate.setText(m_AmtFormat.format(recordItem.getTaxRate()));
		
		//	Set Line Net
		final TextView tv_LineNetAmt = (TextView)view.findViewById(R.id.tv_LineNetAmt);
		tv_LineNetAmt.setText(m_AmtFormat.format(recordItem.getLineNetAmt()));
		holderQtyEntered.getEditText().setOnEditorActionListener(new OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_DONE
		        		|| actionId == EditorInfo.IME_ACTION_NEXT) {
		        	//	Set Value
					setNewValue(recordItem, holderQtyEntered.getText(), position);
					recordItem.setLineNetAmt(calculateAmt(recordItem));
					tv_LineNetAmt.setText(m_AmtFormat.format(recordItem.getLineNetAmt()));
		        }
		        //	
		        return false;
		    }
		});
		//	For Focus
		holderQtyEntered.getEditText().setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus) {
					//	Set Value
					setNewValue(recordItem, holderQtyEntered.getText(), position);
					recordItem.setLineNetAmt(calculateAmt(recordItem));
					tv_LineNetAmt.setText(m_AmtFormat.format(recordItem.getLineNetAmt()));
				} else {
					holderQtyEntered.getEditText().selectAll();
					//	
					Env.showKeyBoad(m_ctx);
					//	Test
//					int[] location = new int[2];					
//					v.getLocationOnScreen(location);
//					System.err.println(" [0] = " + location[0] + " [1] = " + location[1]);
				}
				//	Set Has Focus
				m_HasFocus = hasFocus;
			}
		});
		//	For Changes in Key
		holderQtyEntered.getEditText().addTextChangedListener(new TextWatcher() {
			
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
				m_CurrentLineNetAmt = tv_LineNetAmt;
				m_CurrentRecordItem = recordItem;
			}
		});
		//	
		view.setTag(holderQtyEntered);
		//	Return
		return view;
	}
	
	/**
	 * Get Last 3 Delivery
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_M_Product_ID
	 * @param v
	 * @return void
	 */
	public void loadLastSO(int m_M_Product_ID, View v){
    	String m_QtyDeliveredLabel = m_ctx.getResources().getString(R.string.QtyDelivered);
    	String m_QtyReturnLabel = m_ctx.getResources().getString(R.string.QtyReturned);
    	DB conn = new DB(m_ctx);
    	DB.loadConnection(conn, DB.READ_ONLY);
		Cursor rs = null;
		String sql = new String("SELECT " +
				"io.M_InOut_ID, " +
				"io.DocumentNo, " +
				"io.MovementDate, " +
				"iol.MovementQty, " +
				"COALESCE(SUM(rl.Qty), 0) QtyReturn " +
				"FROM M_InOut io " +
				"INNER JOIN M_InOutLine iol ON(iol.M_InOut_ID = io.M_InOut_ID) " +
				"LEFT JOIN M_RMALine rl ON (rl.M_InOutLine_ID = iol.M_InOutLine_ID) " +
				"WHERE iol.M_Product_ID = ? " +
				"AND io.C_BPartner_Location_ID = ? " + 
				"GROUP BY iol.M_Product_ID " +  
				"ORDER BY io.MovementDate " +
				"LIMIT 3");
		//	Compile
		conn.compileQuery(sql);
		//	Add Parameter
		conn.addInt(m_M_Product_ID);
		conn.addInt(m_C_BPartner_Location_ID);
		//	Get Result Set
		rs = conn.querySQL();
		//	
		m_QAct.clear();
		if(rs != null
				&& rs.moveToFirst()){
			do {
				Date validFrom = DisplayType.getDate(rs.getString(2));
				SimpleDateFormat format = Env.getDateFormat(m_ctx);
				String m_MovementDate = format.format(validFrom);
				String m_QtyDelivered = m_QtyFormat.format(rs.getDouble(3));
				String m_QtyReturn = m_QtyFormat.format(rs.getDouble(4));
				//	
				m_QAct.addActionItem(new ActionItem(
						rs.getInt(0), 
						"<" + rs.getString(1) + ">" + 
						"\n" + 
						"<" + m_MovementDate + ">" + 
						"\n" + 
						m_QtyDeliveredLabel + "=<" + m_QtyDelivered + ">" + 
						"\n" + 
						m_QtyReturnLabel + "=<" + m_QtyReturn + ">"));
			} while(rs.moveToNext());
			//	Show
			m_QAct.show(v);
		} else {
			Msg.toastMsg(m_ctx, "@No@ @InfoProduct@");
		}
		//	
		DB.closeConnection(conn);
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
		changeValue();
		m_CurrentRecordItem = null;
		m_HasFocus = false;
		//	Hide Keyboard
		Env.hideKeyBoad(m_ctx);
	}
	
	/**
	 * Change Values in Array and calculate Amount
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	private void changeValue() {
		setNewValue(m_CurrentRecordItem, m_CurrentValue, m_CurrentPosition);
		m_CurrentRecordItem.setLineNetAmt(calculateAmt(m_CurrentRecordItem));
		m_CurrentLineNetAmt.setText(m_AmtFormat.format(m_CurrentRecordItem.getLineNetAmt()));
	}
	
	/**
	 * Calculate Amount for line with tax
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param recordItem
	 * @return BigDecimal
	 */
	private BigDecimal calculateAmt(DisplayListProduct recordItem) {
		BigDecimal m_TaxRate = recordItem.getTaxRate();
		BigDecimal m_Price = recordItem.getPriceList();
		BigDecimal m_QtyEntered = recordItem.getQtyEntered();
		BigDecimal m_LineNetAmt = Env.ZERO;
		BigDecimal m_TaxAmt = Env.ZERO;
		BigDecimal m_AmtWithoutTax = Env.ZERO;
		//	Valid Null
		if(m_TaxRate == null)
			m_TaxRate = Env.ZERO;
		if(m_Price == null)
			m_Price = Env.ZERO;
		if(m_QtyEntered == null)
			m_QtyEntered = Env.ZERO;
		//	Calculate
		m_TaxAmt = m_TaxRate.divide(Env.ONEHUNDRED);
		m_AmtWithoutTax = m_QtyEntered.multiply(m_Price);
		m_TaxAmt = m_TaxAmt.multiply(m_AmtWithoutTax);
		m_LineNetAmt = m_AmtWithoutTax.add(m_TaxAmt);
		//	Return
		return m_LineNetAmt;
	}
	
	/**
	 * Set New Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_NewItem
	 * @param p_Value
	 * @param position
	 * @return void
	 */
	private void setNewValue(DisplayListProduct p_NewItem, String p_Value, int position) {
		p_NewItem.setQtyEntered(DisplayType.getNumber(p_Value));
		//	Set Item
		if(position < data.size()) {
			DisplayListProduct m_OldItem = data.get(position);
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
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Item
	 * @return void
	 */
	private void setToOriginalData(DisplayListProduct p_Item) {
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
	public Filter getFilter() {
		
	    return new Filter() {
	        @SuppressWarnings("unchecked")
	        @Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	            data = (ArrayList<DisplayListProduct>) results.values;
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
	        	ArrayList<DisplayListProduct> filteredResults = getResults(constraint);
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
	        private ArrayList<DisplayListProduct> getResults(CharSequence constraint) {
	        	//	Verify
	            if(constraint != null
	            		&& constraint.length() > 0) {
	            	//	new Filter
	            	ArrayList<DisplayListProduct> filteredResult = new ArrayList<DisplayListProduct>();
	                for(DisplayListProduct item : originalData) {
	                    if((item.getProductCategory()!= null 
	                    		&& item.getProductCategory().toLowerCase().contains(constraint.toString()))
	                    		|| (item.getValue()!= null 
	                    		&& item.getValue().toLowerCase().contains(constraint.toString()))
	                    		|| (item.getName()!= null 
	    	                    		&& item.getName().toLowerCase().contains(constraint.toString()))
	    	                    || (item.getDescription()!= null 
	    	                    		&& item.getDescription().toLowerCase().contains(constraint.toString())))
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
	public DisplayListProduct getItem(int position) {
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
	public ArrayList<DisplayListProduct> getData() {
		return data;
	}
	
	 
	/**
	 * Get data only selected
	 * @author Dixon Martinez, dmartinez@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return ArrayList<SP_DisplayRecordItem>
	 */
	public ArrayList<DisplayListProduct> getSelectedData() {
		//	Temp Data
		ArrayList<DisplayListProduct> tmpData = new ArrayList<DisplayListProduct>();
		//	Save all
		if(originalData != null)
			data = originalData;
		//	Get only selected
		for(DisplayListProduct item : data) {
			//	Add
			if(item.getQtyEntered().compareTo(Env.ZERO) > 0)
				tmpData.add(item);
		}
		return tmpData;
	}	
}