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
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Carlos Parada www.erpcya.com                      				 *
 *************************************************************************************/
package org.spinsuite.login;


import org.spinsuite.base.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class T_Login_ProgressSync extends DialogFragment implements OnClickListener{
	
	
	private TextView tv_Msg = null; 
	private ProgressBar pb_Progress = null;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view =inflater.inflate(R.layout.v_progressbar, null);
		
		tv_Msg =(TextView) view.findViewById(R.id.tv_MsgText);
		tv_Msg.setVisibility(TextView.VISIBLE);
		
		pb_Progress  = (ProgressBar) view.findViewById(R.id.pb_Large);
		pb_Progress.setVisibility(ProgressBar.VISIBLE);
		
		builder.setView(view);
		
		builder.setNegativeButton(R.string.Action_Cancel, this);
		
		return builder.create();
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
	}
	
	
	public void setMsg(String msg,boolean error) {
		tv_Msg.setText(msg);
		if (error)
			tv_Msg.setTextColor(Color.RED);
	}
	
	public void setProgress(int progress, boolean error){
		
		if (progress <= 0 )
			pb_Progress.setIndeterminate(true);
		else
			pb_Progress.setProgress(progress);
		
		if (error)
			pb_Progress.setVisibility(ProgressBar.GONE);
		
	}
	
	public void setMaxValueProgressBar(int p_Max){
		pb_Progress.setIndeterminate(false);
		pb_Progress.setMax(p_Max);
	}
}