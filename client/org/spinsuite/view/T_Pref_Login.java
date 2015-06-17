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


import java.util.logging.Level;

import org.spinsuite.adapters.LoginRoleAdapter;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
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
	
	
	/**	Login User					*/
	private EditText 			et_User;
	/**	Login Pass					*/
	private EditText 			et_Pass;
	/**	Role						*/
	private ExpandableListView 	ev_Role;
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
    	ev_Role.setAdapter(new LoginRoleAdapter(m_ctx));
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
    	if(user != null && user.length() > 0) {
    		if(pass != null && pass.length() > 0){
    			Env.setContext("#SUser", user);
    			Env.setContext("#AD_User_Name", user);
    			Env.setContext("#SPass", pass);
    			if(!Env.isEnvLoad())
    				return true;
    			else if(findUser(user, pass)) {
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
     * Find User on database
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param user
     * @param pass
     * @return
     * @return boolean
     */
    private boolean findUser(String user, String pass){
    	boolean ok = false;
    	try {
    		DB con = new DB(this.getActivity());
        	con.openDB(DB.READ_ONLY);
        	String sql = "SELECT u.AD_User_ID " +
        			"FROM AD_User u " +
        			"WHERE u.Name = ? AND u.PassWord = ?";
        	Cursor rs = con.querySQL(sql, new String[]{user, pass});
        	//
        	if(rs.moveToFirst()){
        		Env.setAD_User_ID(rs.getInt(0));
        		Env.setIsLogin(true);
        		ok = true;
        	} else {
        		Env.setIsLogin(false);
        	}
        	con.closeDB(rs);
    	} catch(Exception e) {
    		LogM.log(getActivity(), getClass(), Level.SEVERE, "Error", e);
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
		return validUser();
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
     		user = Env.getContext("#SUser");
     		if(user != null)
     			et_User.setText(user);
     	}
     	//	Save Pass
     	if(!Env.isRequestPass()){
     		pass = Env.getContext("#SPass");
     		if(pass != null)
     			et_Pass.setText(pass);
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
