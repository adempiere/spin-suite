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
import org.spinsuite.util.BackGroundTask;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class T_Login_ProgressSync extends DialogFragment implements OnClickListener{
	
	/** Text View Message*/
	private TextView tv_Msg = null;
	
	/** Progress bar */
	private ProgressBar pb_Progress = null;
	
	/** Background Task */
	private BackGroundTask task ; 
	
	//private Context test ;
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 11/11/2014, 23:15:01
	 * @param task
	 */
	public T_Login_ProgressSync(BackGroundTask task,Context test) {
		this.task = task;
	}
	
	@SuppressLint("InflateParams")
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
		
		builder.setNegativeButton(R.string.Stop, this);
		builder.setPositiveButton(R.string.Hide, this);
		builder.setCancelable(false);
		
		return builder.create();
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		switch (which) {
			/** Cancel task And Dismiss Dialog */
			case -2:
				task.cancelTask(true);
				this.dismiss();
				break;
			case -1:
				
//				NotificationManager notificationManager=(NotificationManager)test.getSystemService(getActivity().NOTIFICATION_SERVICE);
//				  Intent startActivityIntent=new Intent(ImportService.this,ImportService.class);
//				  PendingIntent pendingIntent=PendingIntent.getActivity(test.getApplicationContext(),0,startActivityIntent,0);
//				  notification.setLatestEventInfo(this,"Andlytics import",message,pendingIntent);
//				  notificationManager.notify(NOTIFICATION_ID_PROGRESS,notification);
//				m_notificationMgr = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
//				int icon=R.drawable.bpartner_m;
//				  
//				Notification notification=new Notification(icon,"",System.currentTimeMillis());
//				  //Intent notificationIntent=new Intent(getActivity(),T_Login_ProgressSync.class);
//				  PendingIntent contentIntent=PendingIntent.getActivity(getActivity(),0,getView().ge,0);
//				  notification.setLatestEventInfo(getActivity(),"","",contentIntent);
//				  m_notificationMgr.notify(1,notification);
//				  
				break;
			default:
				break;
		}
	}
	
	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 11/11/2014, 23:15:09
	 * @param msg
	 * @param error
	 * @return void
	 */
	public void setMsg(String msg,boolean error) {
		tv_Msg.setText(msg);
		if (error)
			tv_Msg.setTextColor(Color.RED);
	}
	
	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 11/11/2014, 23:15:14
	 * @param progress
	 * @param error
	 * @return void
	 */
	public void setProgress(int progress, boolean error){
		
		if (progress <= 0 )
			pb_Progress.setIndeterminate(true);
		else
			pb_Progress.setProgress(progress);
		
		if (error)
			pb_Progress.setVisibility(ProgressBar.GONE);
	}
	
	/**
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 11/11/2014, 23:15:17
	 * @param p_Max
	 * @return void
	 */
	public void setMaxValueProgressBar(int p_Max){
		pb_Progress.setIndeterminate(false);
		pb_Progress.setMax(p_Max);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().setTitle(R.string.Sync_Synchronzing);
		getDialog().setCanceledOnTouchOutside(false);
		getDialog().setCancelable(false);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}