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

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.spinsuite.base.R;
import org.spinsuite.util.DisplayType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/6/2015, 11:36:50
 *
 */
public class OrderLineAdapter extends ArrayAdapter<DisplayProducts> {
	
	/**	Context						*/
	private Context 					ctx;
	/**	Data						*/
	private ArrayList<DisplayProducts> 	data;
	/**	Decimal Format				*/
	private DecimalFormat				qtyNumberFormat = null;
	/**	Decimal Format				*/
	private DecimalFormat				amtNumberFormat = null;
	
	public OrderLineAdapter(Context ctx, ArrayList<DisplayProducts> data) {
		super(ctx, R.layout.i_ol_product, data);
		this.ctx = ctx;
		this.data = data;
		qtyNumberFormat = DisplayType.getNumberFormat(ctx, DisplayType.QUANTITY);
		amtNumberFormat = DisplayType.getNumberFormat(ctx, DisplayType.AMOUNT);
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(R.layout.i_ol_product, null);
		}
		
		//	Get Current Data
		DisplayProducts mi = data.get(position);

		//	Set Product Value
		TextView tv_ProductValue = (TextView)item.findViewById(R.id.tv_ProductValue);
		tv_ProductValue.setText(mi.getPrd_Value());

		//	Set Product Name
		TextView tv_ProductName = (TextView)item.findViewById(R.id.tv_ProductName);
		tv_ProductName.setText(mi.getPrd_Name());
		
		//	Set Product Description
		TextView tv_ProductDesc = (TextView)item.findViewById(R.id.tv_ProductDescription);
		tv_ProductDesc.setText(mi.getPrd_Description());
		
		//	Set UOM Symbol
		TextView tv_UOMSymbol = (TextView)item.findViewById(R.id.tv_UOMSymbol);
		tv_UOMSymbol.setText(mi.getUom_Symbol());
		
		//	Set Price
		TextView tv_Price = (TextView) item.findViewById(R.id.tv_Price);
		tv_Price.setText(amtNumberFormat.format(mi.getPriceEntered()));
		
		//	Set Net Line Amount
		TextView tv_LineNetAmt = (TextView) item.findViewById(R.id.tv_LineNetAmt);
		tv_LineNetAmt.setText(amtNumberFormat.format(mi.getLineNetAmt()));
		//	Set Qty
		TextView tv_Qty = (TextView)item.findViewById(R.id.tv_Qty);
		tv_Qty.setText(qtyNumberFormat.format(mi.getQtyEntered())); 

		//	Return
		return item;
	}
}
