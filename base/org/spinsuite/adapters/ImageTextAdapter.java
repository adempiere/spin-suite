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
import org.spinsuite.util.DisplayImageTextItem;
import org.spinsuite.util.Env;
import org.spinsuite.view.lookup.VLookupImageText;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class ImageTextAdapter extends ArrayAdapter<DisplayImageTextItem> {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/02/2014, 17:06:03
	 * @param ctx
	 * @param data
	 */
	public ImageTextAdapter(Context ctx, ArrayList<DisplayImageTextItem> data) {
		super(ctx, R.layout.i_image_text, data);
		this.ctx = ctx;
		this.data = data;
		//	Get Preferred Height
		TypedValue value = Env.getResource(ctx, android.R.attr.listPreferredItemHeight);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((WindowManager)(ctx.getSystemService(Context.WINDOW_SERVICE)))
				.getDefaultDisplay().getMetrics(displayMetrics);
		height = value.getDimension(displayMetrics);
	}

	/**	Context						*/
	private Context 						ctx;
	/**	Data						*/
	private ArrayList<DisplayImageTextItem> data = new ArrayList<DisplayImageTextItem>();
	/**	Preferred Item Height		*/
	private float							height = 0;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		VLookupImageText item = (VLookupImageText) convertView;
		if(item == null){
			item = new VLookupImageText(ctx);
			item.setMinimumHeight((int)height);
		}
		
		DisplayImageTextItem diti = data.get(position);
		//	Set Name
		item.setName(diti.getValue());	
		item.setDescription(diti.getDescription());
		//	Set Image
		if(diti.getImage() != null) {
			item.setItemImageBitmap(diti.getImage());
		} else if(diti.getValue() != null
				&& diti.getValue().length() > 0) {
			if(diti.getValue().toLowerCase(
					Env.getLocate()).endsWith(".pdf")) {
				item.setItemImageResource(Env.getResourceID(ctx, R.attr.ic_ls_pdf));
			} else if(diti.getValue().toLowerCase(
					Env.getLocate()).endsWith(".xls")) {
				item.setItemImageResource(Env.getResourceID(ctx, R.attr.ic_ls_xls));
			} else {
				item.setItemImageResource(Env.getResourceID(ctx, R.attr.ic_ls_file));
			}
		}
		//	Return
		return item;
	}
	
}
