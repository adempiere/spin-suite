/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                                           *
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
package org.spinsuite.util;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.spinsuite.base.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

public class SFAAsyncTask{

	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 05/03/2014, 23:53:03
	 */
	public SFAAsyncTask(Context p_ctx, int p_pbType) {
		// TODO Auto-generated constructor stub
		/*SFAAsyncTaskDF df = new SFAAsyncTaskDF(p_pbType, m_ProgressBar);
		df.show(((FragmentActivity)p_ctx).getSupportFragmentManager(), "");*/
		m_ctx = p_ctx;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 06/03/2014, 23:03:38
	 */
	public SFAAsyncTask() {
		// TODO Auto-generated constructor stub
		
	}
	
	/**
	 * Run Method
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 05/03/2014, 22:39:48
	 * @param p_Class
	 * @param p_Method
	 * @param p_Params
	 * @return void
	 */
	public void run(Object p_Object,String p_Method,Object[] p_Params){
		m_Call = new SFACallable<Object>(m_ctx,p_Object, p_Method, p_Params);
		m_FutureTask = new SFAFutureTask<Object>(m_Call);
		ExecutorService executor = Executors.newFixedThreadPool(1);

		executor.execute(m_FutureTask);
	}
	
	/**
	 * get Result From Method invoked
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 05/03/2014, 22:50:20
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @return Object
	 */
	public Object getResult() throws InterruptedException, ExecutionException{
		if (m_FutureTask != null)
			return m_FutureTask.get();
		
		return null;
	}
	
	/**
	 * Cancel Task 
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 05/03/2014, 22:55:57
	 * @param p_mayInterruptIfRunning
	 * @return void
	 */
	public void cancelTask(boolean p_mayInterruptIfRunning){
		if (m_FutureTask != null)
			m_FutureTask.cancel(p_mayInterruptIfRunning);
	}
	
	/**
	 * Is Cancelled Task
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 05/03/2014, 23:09:49
	 * @return
	 * @return boolean
	 */
	public boolean isCancel(){
		if (m_FutureTask != null)
			return m_FutureTask.isCancelled();
		
		return false;
	}
	
	/**
	 * Is Done Task
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 05/03/2014, 23:10:46
	 * @return
	 * @return boolean
	 */
	public boolean isDone(){
		if (m_FutureTask != null)
			return m_FutureTask.isDone();
		
		return false;
	}
	/**
	 * Create Progress Bar
	 * @author <a href="mailto:carlosapardam@gmail.com">Carlos Parada</a> 05/03/2014, 23:02:20
	 * @return void
	 */
	public void createProgressBar(){
		if (m_ProgressBar== null)
			m_ProgressBar = new ProgressBar(m_ctx);
	}
	
	/**
	 * Create Progress Bar 
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 05/03/2014, 23:05:20
	 * @param p_attrs
	 * @return void
	 */
	public void createProgressBar(AttributeSet p_attrs){
		if (m_ProgressBar== null)
			m_ProgressBar = new ProgressBar(m_ctx, p_attrs);
	}
	
	/**
	 * Create Progress Bar 
	 * @author <a href="mailto:carlsaparadam@gmail.com">Carlos Parada</a> 05/03/2014, 23:07:10
	 * @param p_attrs
	 * @param p_DefStyle
	 * @return void
	 */
	public void createProgressBar(AttributeSet p_attrs, int p_DefStyle){
		if (m_ProgressBar== null)
			m_ProgressBar = new ProgressBar(m_ctx, p_attrs, p_DefStyle);
	}
	
	/**
	 * Set Max to Progress Bar
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 05/03/2014, 23:20:13
	 * @param p_Max
	 * @return void
	 */
	public void setMaxBar(int p_Max){
		if (m_ProgressBar!= null)
			m_ProgressBar.setMax(p_Max);
	}
	
	/**
	 * Set Progress
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 05/03/2014, 23:19:49
	 * @param p_Progress
	 * @return void
	 */
	public void setProgressBar(int p_Progress){
		if (m_ProgressBar!= null)
			m_ProgressBar.setProgress(p_Progress);
	}
	
	
	/** Progress Bar*/
	private ProgressBar m_ProgressBar;
	
	/** Context */
	private Context m_ctx;
	
	/** Callable */
	private SFACallable<Object> m_Call ;
	
	/** SFA Future Task*/
	private SFAFutureTask<Object> m_FutureTask ;
	
	/** Executor */
	private SFAExecutor m_Executor= new SFAExecutor();
	
	/** Progress Bar Types*/ 
	public static int progressBarCircleLarge  = R.id.pb_High;
	public static int progressBarCircleMedium = R.id.pb_Medium;
	public static int progressBarCircleShort  = R.id.pb_Short;
	public static int progressBarLine = R.id.pb_Large;

	
}

@SuppressLint("ValidFragment")
class SFAAsyncTaskDF extends DialogFragment{
	
	public SFAAsyncTaskDF(int p_pbType,ProgressBar p_ProgressBar) {
		// TODO Auto-generated constructor stub
		m_pbType = p_pbType;
		m_ProgressBar = p_ProgressBar;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view =inflater.inflate(R.layout.v_progressbar, null);
		
		//Create Progress Bar Types
		m_pbCircleLarge = (ProgressBar) view.findViewById(SFAAsyncTask.progressBarCircleLarge);
		m_pbCircleMedium= (ProgressBar) view.findViewById(SFAAsyncTask.progressBarCircleMedium);
		m_pbCircleShort = (ProgressBar) view.findViewById(SFAAsyncTask.progressBarCircleShort);
		m_pbLine = (ProgressBar) view.findViewById(SFAAsyncTask.progressBarLine);
		
		//Show ProgressBar Type 
		m_pbCircleLarge.setVisibility((m_pbType == SFAAsyncTask.progressBarCircleLarge ? ProgressBar.VISIBLE: ProgressBar.GONE )); 
		m_pbCircleMedium.setVisibility((m_pbType == SFAAsyncTask.progressBarCircleMedium ? ProgressBar.VISIBLE: ProgressBar.GONE ));
		m_pbCircleShort.setVisibility((m_pbType == SFAAsyncTask.progressBarCircleShort ? ProgressBar.VISIBLE: ProgressBar.GONE ));
		m_pbLine.setVisibility((m_pbType == SFAAsyncTask.progressBarLine ? ProgressBar.VISIBLE: ProgressBar.GONE ));
		
		m_ProgressBar = (m_pbType == SFAAsyncTask.progressBarCircleLarge ? m_pbCircleLarge :
							(m_pbType == SFAAsyncTask.progressBarCircleMedium ? m_pbCircleMedium : 
								(m_pbType == SFAAsyncTask.progressBarCircleShort ? m_pbCircleShort : m_pbLine
								)
							)
						); 
				
		builder.setView(view);
		return builder.create();
	}
	
	/** Progress Bar Objects*/
	private ProgressBar m_pbCircleLarge ;
	private ProgressBar m_pbCircleMedium ;
	private ProgressBar m_pbCircleShort ;
	private ProgressBar m_pbLine ;
	
	/** Progress Bar Type */
	private int m_pbType =0;
	
	/** Progress Bar*/
	private ProgressBar m_ProgressBar;
}