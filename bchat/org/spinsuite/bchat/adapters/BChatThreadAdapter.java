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
package org.spinsuite.bchat.adapters;

import java.util.ArrayList;

import org.spinsuite.base.R;
import org.spinsuite.bchat.model.SPS_BC_Message;
import org.spinsuite.bchat.util.DisplayBChatThreadItem;
import org.spinsuite.util.Env;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class BChatThreadAdapter extends ArrayAdapter<DisplayBChatThreadItem> {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/02/2014, 17:06:03
	 * @param ctx
	 * @param id_View
	 * @param data
	 */
	public BChatThreadAdapter(Context ctx, ArrayList<DisplayBChatThreadItem> data) {
		super(ctx, R.layout.i_bchat_thread, data);
		this.ctx = ctx;
		this.id_View = R.layout.i_bchat_thread;
		this.data = data;
	}

	/**	Context						*/
	private Context 								ctx;
	/**	Data						*/
	private ArrayList<DisplayBChatThreadItem> 		data = new ArrayList<DisplayBChatThreadItem>();
	/**	Identifier of View			*/
	private int 									id_View;
	/**	Max Size					*/
	//private static final int						MAX_SIZE = 100;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if(item == null){
			LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(id_View, null);
		}
		
		DisplayBChatThreadItem diti = data.get(position);
		
		RelativeLayout rl_Conversation = (RelativeLayout) item.findViewById(R.id.rl_Conversation);
		int id_att = R.attr.ic_bc_bubble_local;
		//	For Type Message change Background
		if(diti.getType().equals(SPS_BC_Message.TYPE_IN))
			id_att = R.attr.ic_bc_bubble_remote;
		//	
		rl_Conversation.setBackgroundResource(Env.getResourceID(ctx, id_att));
		//	Set Conversation
		TextView tv_Conversation = (TextView) item.findViewById(R.id.tv_Conversation);
		tv_Conversation.setText(diti.getValue());
		//	Set Time
		TextView tv_Time = (TextView)item.findViewById(R.id.tv_Time);
		tv_Time.setText(diti.getTimeAsString());
		//	Return
		return item;
	}
}
