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
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.spinsuite.base.R;
import org.spinsuite.util.DisplayType;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class TFPApplyAdapter extends ArrayAdapter<DisplayTFPApply> {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 17:06:03
	 * @param ctx
	 * @param data
	 */
	public TFPApplyAdapter(Context ctx, ArrayList<DisplayTFPApply> data) {
		super(ctx, R.layout.i_tf_suggested_product, data);
		this.ctx = ctx;
		this.data = data;
		dateFormat = DisplayType.getDateFormat(ctx);
		numberFormat = DisplayType.getNumberFormat(ctx, DisplayType.QUANTITY);
		m_SelectedItems = new SparseBooleanArray();
	}
	
	/**	Context						*/
	private Context 					ctx;
	/**	Data						*/
	private ArrayList<DisplayTFPApply> 	data;
	/**	Decimal Format				*/
	private DecimalFormat				numberFormat = null;
	/**	Decimal Format				*/
	private SimpleDateFormat			dateFormat = null;
	/**	Selected Items IDs			*/
	private SparseBooleanArray 			m_SelectedItems;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(R.layout.i_tf_suggested_product, null);
		}
		
		//	Get Current Data
		DisplayTFPApply mi = data.get(position);
		
		//	Set Product
		TextView tv_Product = (TextView)item.findViewById(R.id.tv_Product);
		tv_Product.setText(mi.getProduct());
		//	Set Date From
		TextView tv_DateFrom = (TextView)item.findViewById(R.id.tv_DateFrom);
		//	Valid Null
		if(mi.getDateFrom() != null)
			tv_DateFrom.setText(dateFormat.format(mi.getDateFrom()));
		else 
			tv_DateFrom.setText("");
		//	Set Date To
		TextView tv_DateTo = (TextView)item.findViewById(R.id.tv_DateTo);
		//	Valid Null
		if(mi.getDateTo() != null)
			tv_DateTo.setText(dateFormat.format(mi.getDateTo()));
		else 
			tv_DateTo.setText("");
		//	Set Quantity Suggested
		TextView tv_QtySuggested = (TextView)item.findViewById(R.id.tv_QtySuggested);
		tv_QtySuggested.setText(numberFormat.format(mi.getQtySuggested()) 
				+ (mi.getSuggestedUOM() != null? " " + mi.getSuggestedUOM(): ""));
		//	Set Quantity Dosage
		TextView tv_QtyDosage = (TextView)item.findViewById(R.id.tv_QtyDosage);
		tv_QtyDosage.setText(numberFormat.format(mi.getQtyDosage()) 
				+ (mi.getDosageUOM() != null? " " + mi.getDosageUOM(): ""));
		//	Set Quantity to Order
		TextView tv_Qty = (TextView)item.findViewById(R.id.tv_QtyOrdered);
		tv_Qty.setText(numberFormat.format(mi.getQty()) 
				+ (mi.getUOM() != null? " " + mi.getUOM(): ""));
		//	Set Warehouse
		TextView tv_Warehouse = (TextView)item.findViewById(R.id.tv_Warehouse);
		if(mi.getWarehouse() != null) {
			tv_Warehouse.setText(ctx.getString(R.string.M_Warehouse_ID) + ": "+ mi.getWarehouse());
			tv_Warehouse.setVisibility(TextView.VISIBLE);
		} else {
			tv_Warehouse.setText("");
			tv_Warehouse.setVisibility(TextView.GONE);
		}
		//	Set Is Applied
		TextView tv_IsApplied = (TextView)item.findViewById(R.id.tv_IsApplied);
		if(mi.isApplied())
			tv_IsApplied.setText(ctx.getString(R.string.msg_Yes));
		else
			tv_IsApplied.setText(ctx.getString(R.string.msg_No));
		
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
	public void remove(DisplayTFPApply object) {
		data.remove(object);
		notifyDataSetChanged();
	}
}
