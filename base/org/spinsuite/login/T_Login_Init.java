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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

@SuppressLint("ValidFragment")
public class T_Login_Init extends DialogFragment implements OnClickListener{
	
	/** Text User*/
	private EditText et_User;
	
	/** Text Password*/
	private EditText et_PassWord;
	
	
	/** */
	private T_Connection m_T_Connection = null;
	
	public T_Login_Init(T_Connection p_Con) {
		m_T_Connection = p_Con;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
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
		
		String m_ValidUser = et_User.getText().toString();
		String m_ValidPass = et_PassWord.getText().toString();
		
		if (which == -1){
			if (!m_ValidUser.equals("") && !m_ValidPass.equals(""))
				m_T_Connection.startSynchronization(m_ValidUser, m_ValidPass);
		}		
		//Env.setContext(this.getActivity(), "#SUser", et_User.getText().toString());
		//Env.setContext(this.getActivity(), "#SPass", et_PassWord.getText().toString());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().setTitle(R.string.tt_Conn);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
}