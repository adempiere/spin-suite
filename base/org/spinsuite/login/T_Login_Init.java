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


import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.initialload.InitialLoad;
import org.spinsuite.util.SFAAsyncTask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class T_Login_Init extends DialogFragment implements OnClickListener{
	
	/** Text User*/
	private EditText et_User;
	
	/** Text Password*/
	private EditText et_PassWord;
	
	/** Url*/
	private String m_Url;
	
	/** Method*/
	private String m_Method;
	
	/** Name space*/
	private String m_NameSpace;
	
	public T_Login_Init(String p_Url, String p_Method, String p_NameSpace) {
		// TODO Auto-generated constructor stub
		m_Url = p_Url;
		m_Method = p_Method;
		m_NameSpace = p_NameSpace;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view =inflater.inflate(R.layout.t_login_init, null);
		
		et_User = (EditText) view.findViewById(R.id.et_User);
		et_PassWord = (EditText) view.findViewById(R.id.et_Pass);
		///Set Authentication for test
		et_User.setText("SuperUser");
		et_PassWord.setText("System");
		
		builder.setView(view);
		
		builder.setNegativeButton(R.string.Action_Cancel, this);
		builder.setPositiveButton(R.string.Action_Ok, this);
		
		return builder.create();
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		SoapObject reps = null;
		SFAAsyncTask task= new SFAAsyncTask(getActivity(),SFAAsyncTask.progressBarCircleLarge); 
		
		InitialLoad il = new InitialLoad(m_Url, m_NameSpace, m_Method, true, m_NameSpace + m_Method, et_User.getText().toString(), et_PassWord.getText().toString(), "SFAndroidService");
		
		task.run(il, "callService", null);
		
		try {
			while (!task.isDone()){
				//System.out.println("Pase");
		//System.out.println(task.isCancel());
		//System.out.println(task.isDone());
			}
			reps=  (SoapObject)task.getResult();
			//System.out.println(reps.toString());
			//task.run(il, "writeDB", new Object[]{reps,getActivity()});
			//il.writeDB(reps);
			
			//while (!task.isDone()){
				//System.out.println("Pase");
		//System.out.println(task.isCancel());
		//System.out.println(task.isDone());
			//}
			il.writeDB(reps, getActivity());
			String sql = new String("SELECT * FROM AD_Client");
	    	DB con = new DB(getActivity());
	    	con.openDB(DB.READ_ONLY);
	    	Cursor rs = con.querySQL(sql, null);
	    	if(rs.moveToFirst()){
				do {
					System.out.println(rs.getString(0));
					System.out.println(rs.getString(1));
				} while(rs.moveToNext());
			}
	    	con.closeDB(rs);
	    	
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		
		//Env.setContext(this.getActivity(), "#SUser", et_User.getText().toString());
		//Env.setContext(this.getActivity(), "#SPass", et_PassWord.getText().toString());
	}
}
