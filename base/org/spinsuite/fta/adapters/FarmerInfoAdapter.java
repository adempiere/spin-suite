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
import org.spinsuite.util.DisplayType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class FarmerInfoAdapter extends ArrayAdapter<DisplayFarmingInfo> {

	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 17:06:03
	 * @param ctx
	 * @param data
	 */
	public FarmerInfoAdapter(Context ctx, ArrayList<DisplayFarmingInfo> data) {
		super(ctx, R.layout.i_farmer_info, data);
		this.ctx = ctx;
		this.data = data;
		numberFormat = DisplayType.getNumberFormat(ctx, DisplayType.QUANTITY, "###,###.##");
	}
	
	/**	Context						*/
	private Context 						ctx;
	/**	Data						*/
	private ArrayList<DisplayFarmingInfo> 	data;
	/**	Decimal Format				*/
	private DecimalFormat					numberFormat = null;
	/**	Current Product Category ID	*/
	private int								m_Curr_M_Product_Category_ID = 0;
	/**	Current Farming Stage ID	*/
	private int								m_Curr_FTA_FarmingStage_ID = 0;

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(R.layout.i_farmer_info, null);
		}
		//	Get Current Data
		DisplayFarmingInfo mi = data.get(position);
		
		//	Set Product Category
		TextView tv_ProductCategory = (TextView)item.findViewById(R.id.tv_ProductCategory);
		tv_ProductCategory.setText(mi.getProductCategory());
		tv_ProductCategory.setTextAppearance(ctx, R.style.TextHeaderReport);
		//	Farming Stage
		TextView tv_FarmingStage = (TextView)item.findViewById(R.id.tv_FarmingStage);
		tv_FarmingStage.setText(mi.getFarmingStage());
		tv_FarmingStage.setTextAppearance(ctx, R.style.TextHeaderReport);
		//	Quantity Dosage
		TextView tv_QtyDosageLabel = (TextView)item.findViewById(R.id.tv_QtyDosageLabel);
		tv_QtyDosageLabel.setTextAppearance(ctx, R.style.TextHeaderReport);
		//	Quantity Dosage By Effective Area
		TextView tv_QtyDosageByAreaLabel = (TextView)item.findViewById(R.id.tv_QtyDosageByAreaLabel);
		tv_QtyDosageByAreaLabel.setTextAppearance(ctx, R.style.TextHeaderReport);
		//	Product
		TextView tv_Product = (TextView)item.findViewById(R.id.tv_Product);
		tv_Product.setText(mi.getProduct());
		//	Dosage
		TextView tv_QtyDosage = (TextView)item.findViewById(R.id.tv_QtyDosage);
		tv_QtyDosage.setText(numberFormat.format(mi.getQtyDosage()) 
				+ (mi.getUOMSymbol() != null? " " + mi.getUOMSymbol(): ""));
		//	Dosage By Effective Area
		TextView tv_QtyDosageByArea = (TextView)item.findViewById(R.id.tv_QtyDosageByArea);
		//	Divide
		double qtyByArea = 0;
		if(mi.getEffectiveArea() != 0)
			qtyByArea = mi.getQtyDosage() / mi.getEffectiveArea();
		else if(mi.getArea() != 0)
			qtyByArea = mi.getQtyDosage() / mi.getArea();
		//	
		tv_QtyDosageByArea.setText(numberFormat.format(qtyByArea) 
				+ (mi.getUOMSymbol() != null? " " + mi.getUOMSymbol(): ""));
		//	Verify Farming stage Change
		if(m_Curr_FTA_FarmingStage_ID != mi.getFTA_FarmingStage_ID()
				|| m_Curr_M_Product_Category_ID != mi.getM_Product_Category_ID()) {
			m_Curr_FTA_FarmingStage_ID = mi.getFTA_FarmingStage_ID();
			tv_FarmingStage.setVisibility(View.VISIBLE);
			tv_QtyDosageLabel.setVisibility(View.VISIBLE);
			tv_QtyDosageByAreaLabel.setVisibility(View.VISIBLE);
		} else {
			tv_FarmingStage.setVisibility(View.GONE);
			tv_QtyDosageLabel.setVisibility(View.GONE);
			tv_QtyDosageByAreaLabel.setVisibility(View.GONE);
		}
		//	Verify Category Change
		if(m_Curr_M_Product_Category_ID != mi.getM_Product_Category_ID()) {
			m_Curr_M_Product_Category_ID = mi.getM_Product_Category_ID();
			tv_ProductCategory.setVisibility(View.VISIBLE);
		} else {
			tv_ProductCategory.setVisibility(View.GONE);
		}
		//	Return
		return item;
	}

}
