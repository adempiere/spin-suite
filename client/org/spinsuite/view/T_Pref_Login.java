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


import org.spinsuite.adapters.LoginRoleAdapter;
import org.spinsuite.base.R;
import org.spinsuite.login.Login;
import org.spinsuite.model.MUser;
import org.spinsuite.util.Env;
import org.spinsuite.util.Msg;
import org.spinsuite.util.SyncValues;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Feb 26, 2015, 11:51:10 PM
 *	<li> Login Correct
 * 	@see https://adempiere.atlassian.net/browse/SPIN-2
 */
public class T_Pref_Login extends T_Pref_Parent {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public T_Pref_Login() {
		super();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_ctx
	 */
	public T_Pref_Login(Context p_ctx) {
		super(p_ctx);
	}
	
	/**
	 * For Login
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_ctx
	 * @param p_IsReloadActivity
	 */
	public T_Pref_Login(Context p_ctx, boolean p_IsReloadActivity) {
		this(p_ctx);
		m_IsReloadActivity = p_IsReloadActivity;
	}
	
	
	/**	Login User					*/
	private EditText 			et_User;
	/**	Login Pass					*/
	private EditText 			et_Pass;
	/**	Role						*/
	private ExpandableListView 	ev_Role;
	/**	Old User					*/
	private String				m_OldUser;
	/**	Old Password				*/
	private String				m_OldPass;
	/**	Has Changes					*/
	private boolean				m_IsHasChanges = false;
	/**	Reload Activity				*/
	private boolean 			m_IsReloadActivity = true;
	/**	Login Role Adapter			*/
	private LoginRoleAdapter	m_LoginRoleAdapter = null;
	
	
	/**	Key for Valid User Flag		*/
	public static final String	KEY_LOGIN_VALID_USER = "#PR_Login_Valid_User";
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //	Current
    	if(m_View != null)
        	return m_View;
        //	RE-Load
        m_View = inflater.inflate(R.layout.t_pref_login, container, false);
    	return m_View;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	if(m_IsLoadOk)
    		return;
    	//	
    	et_User = (EditText) 			m_View.findViewById(R.id.et_User);
    	et_Pass = (EditText) 			m_View.findViewById(R.id.et_Pass);
    	ev_Role	= (ExpandableListView)	m_View.findViewById(R.id.ev_Role);
    	//	
    	et_Pass.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
		        //	Listener
				if(!hasFocus) {
					boolean isValid = validUser();
					//	Enable Role
					ev_Role.setEnabled(isValid);
					//	Load Role
					if(isValid) {
						ev_Role.expandGroup(0);
					} else {
						ev_Role.collapseGroup(0);
					}
				}
			}
		});
    	ev_Role.setClickable(true);
    	ev_Role.setGroupIndicator(null);
    	m_LoginRoleAdapter = new LoginRoleAdapter(m_ctx);
    	ev_Role.setAdapter(m_LoginRoleAdapter);
    	//	Enable / Disable
    	ev_Role.setEnabled(Env.getContextAsBoolean(KEY_LOGIN_VALID_USER));
		m_IsLoadOk = true;
    }
    
    /**
     * Valid User and Password
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return boolean
     */
    private boolean validUser() {
    	String user = et_User.getText().toString().trim();
    	String pass = et_Pass.getText().toString().trim();
    	//	Verify if has changes
    	if((m_OldUser != null && user == null)
    		|| (m_OldUser == null && user != null)
    		|| (!m_OldUser.equals(user))) {
    		m_IsHasChanges = true;
    	} else if((m_OldPass != null && pass == null)
        		|| (m_OldPass == null && pass != null)
        		|| (!m_OldPass.equals(pass))) {
        		m_IsHasChanges = true;
        }
    	//	
    	if(user != null && user.length() > 0) {
    		if(pass != null && pass.length() > 0) {
    			//	
    			Env.setContext(m_ctx, "#SUser", user);
    			Env.setContext(m_ctx, "#AD_User_Name", user);
    			Env.setContext(m_ctx, "#SPass", pass);
    			//	Just when is Loaded Application
    			if(!Env.isEnvLoad()) {
    				Env.setContext(KEY_LOGIN_VALID_USER, true);
    				return true;
    			} else if(findUser(user, pass)) {
    				Env.setContext(KEY_LOGIN_VALID_USER, true);
    				return true;
    			} else {
    				Msg.toastMsg(m_ctx, 
    						getResources().getString(R.string.msg_UserPassDoNotMatch));
    			}
    		} else {
    			Msg.toastMsg(m_ctx, 
						getResources().getString(R.string.MustFillField) 
						+ " \"" + getResources().getString(R.string.Pass) + "\"");
    		}
    	} else {
    		Msg.toastMsg(m_ctx, 
					getResources().getString(R.string.MustFillField) 
					+ " \"" + getResources().getString(R.string.User) + "\"");
    	}
    	//	
    	Env.setContext(KEY_LOGIN_VALID_USER, false);
    	return false;
    }
    
    /**
     * Reload Activity
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void reloadActivity(){
    	Intent refresh = new Intent(m_ctx, Login.class);
    	refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(refresh);
		getActivity().finish();
    }
    
    /**
     * Find User on database
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param user
     * @param pass
     * @return
     * @return boolean
     */
    private boolean findUser(String user, String pass){
    	boolean ok = false;
    	int m_AD_User_ID = MUser.findUserID(m_ctx, user, pass);
    	if(m_AD_User_ID >= 0) {
    		Env.setAD_User_ID(m_AD_User_ID);
    		ok = true;
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
		boolean ok = validUser();
		if(m_IsHasChanges
				&& m_IsReloadActivity
				&& Env.isEnvLoad()) {
			reloadActivity();
		}
		return ok;
	}

	@Override
	public boolean processActionCancel() {
		return validExit();
	}

	@Override
	public boolean loadData() {
		String user = et_User.getText().toString();
     	String pass = et_Pass.getText().toString();
     	//boolean isSavePass = ch_SavePass.isChecked();
     	if(user == null || user.length() == 0){
     		user = Env.getContext(m_ctx, "#SUser");
     		if(user != null) {
     			et_User.setText(user);
     		} else if(!Env.isEnvLoad()) {
     			//	Set Authentication for test
     			et_User.setText(SyncValues.DEFAULT_AD_USER);
     		}
     	}
     	//	Save Pass
     	pass = Env.getContext(m_ctx, "#SPass");
     	if(pass != null) {
     		et_Pass.setText(pass);
     	} else if(!Env.isEnvLoad()) {
     		//	Set Authentication for test
     		et_Pass.setText(SyncValues.DEFAULT_AD_PASS);
     	}
     	//	
     	m_OldUser = user;
     	m_OldPass = pass;
     	//	Valid User
     	if(Env.isEnvLoad()) {
     		validUser();
     	}
 		//	
		return true;
	}

	@Override
	public void setEnabled(boolean enabled) {
		if(!m_IsLoadOk)
			return;
		et_User.setEnabled(enabled);
    	et_Pass.setEnabled(enabled);
    	ev_Role.setEnabled(enabled);
	}
}
