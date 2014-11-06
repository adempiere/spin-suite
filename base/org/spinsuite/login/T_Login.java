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
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.login;


import java.util.ArrayList;
import java.util.Locale;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.util.DisplaySpinner;
import org.spinsuite.util.Env;
import org.spinsuite.util.Msg;
import org.spinsuite.view.custom.Cust_Spinner;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

public class T_Login extends Fragment implements I_Login {
	private EditText 		et_User;
	private EditText 		et_Pass;
	private CheckBox 		ch_SavePass;
	private CheckBox 		ch_AutoLogin;
	private Cust_Spinner	sp_Language;
	private View 			m_View = null;
	private boolean			m_IsLoadOk = false;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //	Current
    	if(m_View != null)
        	return m_View;
        //	RE-Load
        m_View = inflater.inflate(R.layout.t_login, container, false);
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
    	ch_SavePass = (CheckBox) 		m_View.findViewById(R.id.ch_SavePass);
    	ch_AutoLogin = (CheckBox) 		m_View.findViewById(R.id.ch_AutoLogin);
    	sp_Language = (Cust_Spinner) 	m_View.findViewById(R.id.sp_Language);
    	
    	ArrayList <DisplaySpinner> data = new ArrayList<DisplaySpinner>();
    	for(Locale loc : Locale.getAvailableLocales()){
    		data.add(new DisplaySpinner(0, loc.getDisplayName(), loc.toString()));
    	}
		sp_Language.load(data);
		m_IsLoadOk = true;
    }
    
    /**
     * Reload Activity
     * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/02/2013, 18:56:16
     * @param language
     * @return void
     */
    private void reloadLanguage(String language){
    	Env.changeLanguage(getActivity(), language);
    	Intent refresh = new Intent(getActivity(), Login.class);
		startActivity(refresh);
		getActivity().finish();
    }
    
    /**
     * Valid User and Password
     * @author Yamel Senih 04/02/2013, 19:36:24
     * @return
     * @return boolean
     */
    private boolean validUser() {
    	String user = et_User.getText().toString().trim();
    	String pass = et_Pass.getText().toString().trim();
    	if(user != null && user.length() > 0){
    		if(pass != null && pass.length() > 0){
    			Env.setContext(this.getActivity(), "#SUser", user);
    			Env.setContext(this.getActivity(), "#SPass", pass);
    			Env.setSavePass(this.getActivity(), ch_SavePass.isChecked());
    			Env.setAutoLogin(this.getActivity(), ch_AutoLogin.isChecked());
    			String language = (String)((DisplaySpinner)sp_Language.getSelectedItem()).getHiddenValue();
    			if(!language.equals(Env.getAD_Language(getActivity()))){
    				Env.setAD_Language(getActivity(), language);
    				reloadLanguage(language);
    			}
    			if(!Env.isEnvLoad(this.getActivity()))
    				return true;
    			else if(findUser(user, pass)){
    				return true;
    			} else {
    				Msg.alertMsg(this.getActivity(), 
    						getResources().getString(R.string.msg_UserPassDoNotMatch));
    			}
    		} else {
    			Msg.alertMustFillField(this.getActivity(), R.string.Pass, et_Pass);
    		}
    	} else {
    		Msg.alertMustFillField(this.getActivity(), R.string.User, et_User);
    	}
    	return false;
    }
    
    /**
     * Find User on database
     * @author Yamel Senih 04/02/2013, 19:38:06
     * @param user
     * @param pass
     * @return
     * @return boolean
     */
    private boolean findUser(String user, String pass){
    	boolean ok = false;
    	DB con = new DB(this.getActivity());
    	con.openDB(DB.READ_ONLY);
    	String sql = "SELECT u.AD_User_ID " +
    			"FROM AD_User u " +
    			"WHERE u.Name = ? AND u.PassWord = ?";
    	Cursor rs = con.querySQL(sql, new String[]{user, pass});
    	//
    	if(rs.moveToFirst()){
    		Env.setAD_User_ID(this.getActivity(), rs.getInt(0));
    		Env.setIsLogin(this.getActivity(), true);
    		ok = true;
    	} else {
    		Env.setIsLogin(this.getActivity(), false);
    	}
    	con.closeDB(rs);
    	return ok;
    }
    
    /**
     * Valid Exit
     * @author Yamel Senih 04/02/2013, 19:39:16
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
	public boolean aceptAction() {
		return validUser();
	}

	@Override
	public boolean cancelAction() {
		return validExit();
	}

	@Override
	public boolean loadData() {
		String user = et_User.getText().toString();
     	String pass = et_Pass.getText().toString();
     	//boolean isSavePass = ch_SavePass.isChecked();
     	if(user == null || user.length() == 0){
     		user = Env.getContext(this.getActivity(), "#SUser");
     		if(user != null)
     			et_User.setText(user);
     	}
     	
     	//	Save Pass Check
     	ch_SavePass.setChecked(Env.isSavePass(this.getActivity()));
     	//	Auto Login Check
     	ch_AutoLogin.setChecked(Env.isAutoLogin(this.getActivity()));
     	
     	//	Save Pass
 		if(pass == null || pass.length() == 0){
 			if(Env.isSavePass(this.getActivity())){
 				pass = Env.getContext(this.getActivity(), "#SPass");
 				if(pass != null)
 					et_Pass.setText(pass);
 			}
 			
 		}
 		//	Select Language
 		String language = Env.getAD_Language(getActivity());
 		if(language != null
 				&& language.length() != 0){
 			sp_Language.setSelectedHiddenValue(language);
 		} else
 			sp_Language.setSelectedHiddenValue(Env.BASE_LANGUAGE);
 		//	
		return true;
	}
    
}
