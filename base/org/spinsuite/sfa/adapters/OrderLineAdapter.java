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
package org.spinsuite.sfa.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.spinsuite.base.R;
import org.spinsuite.sfa.util.DisplayOrderLine;
import org.spinsuite.util.DisplayType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/6/2015, 11:36:50
 *
 */
public class OrderLineAdapter extends ArrayAdapter<DisplayOrderLine> {
	
	/**	Context						*/
	private Context 						ctx;
	/**	Data						*/
	private ArrayList<DisplayOrderLine> 	data;
	/**	Decimal Format				*/
	private DecimalFormat					m_QtyNumberFormat = null;
	/**	Decimal Format				*/
	private DecimalFormat					m_AmtNumberFormat = null;
	/**	Selected Items IDs			*/
	private SparseBooleanArray 				m_SelectedItems;
	
	public OrderLineAdapter(Context ctx, ArrayList<DisplayOrderLine> data) {
		super(ctx, R.layout.i_order_line, data);
		this.ctx = ctx;
		this.data = data;
		m_QtyNumberFormat = DisplayType.getNumberFormat(ctx, DisplayType.QUANTITY);
		m_AmtNumberFormat = DisplayType.getNumberFormat(ctx, DisplayType.AMOUNT);
		m_SelectedItems = new SparseBooleanArray();
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(R.layout.i_order_line, null);
		}
		
		//	Get Current Data
		DisplayOrderLine mi = data.get(position);

		//	Set Product Value
		TextView tv_ProductCategory = (TextView)item.findViewById(R.id.tv_ProductCategory);
		tv_ProductCategory.setText(mi.getProductCategory());
		
		//	Set Product Value
		TextView tv_ProductValue = (TextView)item.findViewById(R.id.tv_ProductValue);
		tv_ProductValue.setText(mi.getValue());

		//	Set Product Name
		TextView tv_ProductName = (TextView)item.findViewById(R.id.tv_ProductName);
		tv_ProductName.setText(mi.getName());
		
		//	Set Product Description
		TextView tv_ProductDesc = (TextView)item.findViewById(R.id.tv_ProductDescription);
		tv_ProductDesc.setText(mi.getDescription());
		
		//	Set UOM Symbol
		TextView tv_UOMSymbol = (TextView)item.findViewById(R.id.tv_UOMSymbol);
		tv_UOMSymbol.setText(mi.getUOM_Symbol());
		
		//	Set Price
		TextView tv_Price = (TextView) item.findViewById(R.id.tv_Price);
		tv_Price.setText(m_AmtNumberFormat.format(mi.getPriceEntered()));
		
		//	Set Net Line Amount
		TextView tv_LineNetAmt = (TextView) item.findViewById(R.id.tv_LineNetAmt);
		tv_LineNetAmt.setText(m_AmtNumberFormat.format(mi.getLineNetAmt()));
		//	Set Qty
		TextView tv_Qty = (TextView)item.findViewById(R.id.tv_Qty);
		tv_Qty.setText(m_QtyNumberFormat.format(mi.getQtyEntered())); 

		//	Return
		return item;
	}
	
	/**
	 * Remove Selections
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	public void removeSelection() {
		m_SelectedItems = new SparseBooleanArray();
		notifyDataSetChanged();
	}
	
	/**
	 * Toogle Selection
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param position
	 * @return void
	 */
	public void toggleSelection(int position) {
		selectView(position, !m_SelectedItems.get(position));
	}

	/**
	 * Select View
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param position
	 * @param value
	 * @return void
	 */
	public void selectView(int position, boolean value) {
		if (value) {
			m_SelectedItems.put(position, value);
		} else {
			m_SelectedItems.delete(position);
		}
		//	Is Change
		notifyDataSetChanged();
	}

	/**
	 * Get Selected Count
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public int getSelectedCount() {
		return m_SelectedItems.size();
	}

	/**
	 * Get Selected Items
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return SparseBooleanArray
	 */
	public SparseBooleanArray getSelectedItems() {
		return m_SelectedItems;
	}
	
	@Override
	public void remove(DisplayOrderLine object) {
		data.remove(object);
		notifyDataSetChanged();
	}
}
