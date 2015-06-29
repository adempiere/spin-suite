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
package org.spinsuite.view;


import org.spinsuite.base.R;
import org.spinsuite.login.Login;
import org.spinsuite.util.Env;
import org.spinsuite.util.Msg;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Feb 26, 2015, 11:51:10 PM
 *	<li> Login Correct
 * 	@see https://adempiere.atlassian.net/browse/SPIN-2
 */
public class T_Pref_Request_Pass extends T_Pref_Parent {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public T_Pref_Request_Pass() {
		super();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_ctx
	 */
	public T_Pref_Request_Pass(Context p_ctx) {
		super(p_ctx);
	}
	
	/**	Login Pass				*/
	private EditText 			et_Passcode;
	/**	Callback				*/
	private Activity 			m_Callback = null;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //	Current
    	if(m_View != null)
        	return m_View;
        //	RE-Load
        m_View = inflater.inflate(R.layout.t_pref_request_pass, container, false);
    	return m_View;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	if(m_IsLoadOk)
    		return;
    	//	
    	et_Passcode = (EditText)	m_View.findViewById(R.id.et_Passcode);
    	//	Add Listener
//    	et_Passcode.setOnFocusChangeListener(new OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//		        //	Listener
//				if(!hasFocus) {
//					boolean isValid = validPasscode();
//					//	Verify
//					if(isValid) {
//						setOkPassCode();
//					}
//				}
//			}
//		});
		m_IsLoadOk = true;
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        m_Callback = activity;
    }
    
    /**
     * Load Access
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void setOkPassCode() {
    	if(m_Callback instanceof Login) {
    		((Login)m_Callback).loadConfig();
    	}
    }
    
    /**
     * Valid Pass code
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return boolean
     */
    private boolean validPasscode() {
    	String pass = et_Passcode.getText().toString().trim();
    	int passcode = Integer.parseInt(pass);
    	//	Valid passcode
    	boolean ok = Env.validLoginPasscode(m_ctx, passcode);
    	if(!ok) {
    		Msg.toastMsg(m_ctx, 
					getResources().getString(R.string.msg_PasscodeIsInvalid));
    	}
    	return ok;
    }
    
    /**
     * Valid Exit
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return boolean
     */
    private boolean validExit() {
    	return true;
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	//	Load Data
    	loadData();
    }
    
	@Override
	public boolean processActionOk() {
		return validPasscode();
	}

	@Override
	public boolean processActionCancel() {
		return validExit();
	}

	@Override
	public boolean loadData() {
		//	
		return true;
	}

	@Override
	public void setEnabled(boolean enabled) {
		if(!m_IsLoadOk)
			return;
		et_Passcode.setEnabled(enabled);
	}
}
