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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

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
	 * @param id_View
	 * @param data
	 */
	public ImageTextAdapter(Context ctx, int id_View, ArrayList<DisplayImageTextItem> data) {
		super(ctx, id_View, data);
		this.ctx = ctx;
		this.id_View = id_View;
		this.data = data;
	}

	/**	Context						*/
	private Context 						ctx;
	/**	Data						*/
	private ArrayList<DisplayImageTextItem> data = new ArrayList<DisplayImageTextItem>();
	/**	Identifier of View			*/
	private int 							id_View;
	/**	Max Size					*/
	private static final int				MAX_SIZE = 200;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(id_View, null);
		}
		
		DisplayImageTextItem diti = data.get(position);
		
		//	Set Name
		TextView tv_Name = (TextView)item.findViewById(R.id.tv_Name);
		tv_Name.setText(diti.getValue());
		
		//	Set Description
		TextView tv_Description = (TextView)item.findViewById(R.id.tv_Description);
		tv_Description.setText(diti.getDescription());
		
		ImageView img_Item = (ImageView)item.findViewById(R.id.img_Item);
		img_Item.setLayoutParams(new LayoutParams(MAX_SIZE, MAX_SIZE));
		//	Set Image
		if(diti.getImage() != null) {
			img_Item.setImageBitmap(diti.getImage());
		} else if(diti.getValue() != null
				&& diti.getValue().length() > 0) {
			if(diti.getValue().toLowerCase(
					Env.getLocate()).endsWith(".pdf")) {
				img_Item.setImageResource(Env.getResourceID(ctx, R.attr.ic_ls_pdf));
			} else if(diti.getValue().toLowerCase(
					Env.getLocate()).endsWith(".xls")) {
				img_Item.setImageResource(Env.getResourceID(ctx, R.attr.ic_ls_xls));
			} else {
				img_Item.setImageResource(Env.getResourceID(ctx, R.attr.ic_ls_file));
			}
		}
		//	Return
		return item;
	}
	
}
