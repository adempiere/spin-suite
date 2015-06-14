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


import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.R;
import org.spinsuite.login.Login;
import org.spinsuite.util.Env;
import org.spinsuite.util.Language;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Feb 26, 2015, 11:51:10 PM
 *	<li> Login Correct
 * 	@see https://adempiere.atlassian.net/browse/SPIN-2
 */
public class T_Pref_General extends T_Pref_Parent {
	
	/**
	 * Default
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public T_Pref_General() {
		super();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_ctx
	 */
	public T_Pref_General(Context p_ctx) {
		super(p_ctx);
	}
	
	
	/**	Request Login				*/
	private CheckBox		ch_RequestLogin;
	/**	Language					*/
	private Spinner			sp_Language;
	/**	Log Level					*/
	private Spinner 		sp_LogLevel;
	/**	Save data SD				*/
	private CheckBox 		ch_SaveSD;
	/**	Drop Data Base				*/
	private Button			butt_DropDB;	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //	Current
    	if(m_View != null)
        	return m_View;
        //	RE-Load
        m_View = inflater.inflate(R.layout.t_pref_general, container, false);
    	return m_View;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	if(m_IsLoadOk)
    		return;
    	//	
    	ch_RequestLogin = (CheckBox) m_View.findViewById(R.id.ch_RequestLogin);
    	sp_Language 	= (Spinner) m_View.findViewById(R.id.sp_Language);
    	sp_LogLevel 	= (Spinner) m_View.findViewById(R.id.sp_LogLevel);
    	ch_SaveSD 		= (CheckBox) m_View.findViewById(R.id.ch_SaveSD);
    	butt_DropDB 	= (Button) m_View.findViewById(R.id.butt_DropDB);
    	
    	ArrayList <String> data = new ArrayList<String>();
    	for(Language lang : Language.getAvaliableLanguages()){
    		data.add(lang.getAD_Language());
    	}
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(m_ctx, 
    			android.R.layout.simple_spinner_item, data);
		sp_Language.setAdapter(adapter);
    	//	
    	sp_LogLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				//	Evaluate
				Level level = Level.OFF;
		    	if(position == 0) {
		    		level = Level.INFO;
		    	} else if(position == 1) {
		    		level = Level.FINE;
		    	} else if(position == 2) {
		    		level = Level.FINER;
		    	} else if(position == 3) {
		    		level = Level.FINEST;
		    	} else if(position == 4) {
		    		level = Level.SEVERE;
		    	} else if(position == 5) {
		    		level = Level.WARNING;
		    	} else if(position == 6) {
		    		level = Level.OFF;
		    	}
		    	//	Set Level
		    	LogM.setTraceLevel(m_ctx, level);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//	
			}
    	});
    	//	Listener for Button
    	butt_DropDB.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				deleteDB();
			}
		});
		m_IsLoadOk = true;
    }
    
    
    /**
     * DElete Database
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void deleteDB() {
    	Builder ask = Msg.confirmMsg(m_ctx, getResources().getString(R.string.msg_AskDelete));
		ask.setPositiveButton(getResources().getString(R.string.msg_Acept), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				//	Delete
				//	Delete DataBase
				Msg.confirmMsg(m_ctx, "");
				m_ctx.deleteDatabase(Env.getDB_PathName());
				Env.cacheReset();
				Env.setIsEnvLoad(false);
				Env.setIsLogin(false);
				Env.setContext("#InitialLoadSynchronizing", false);
			}
		});
		ask.show();
    }
    
    /**
     * Reload Activity
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param language
     * @return void
     */
    private void reloadLanguage(String language){
    	Env.changeLanguage(language);
    	Intent refresh = new Intent(m_ctx, Login.class);
    	refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(refresh);
		getActivity().finish();
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
		String language = sp_Language.getSelectedItem().toString();
    	if(!language.equals(Env.getAD_Language())){
    		Env.setAD_Language(language);
    		reloadLanguage(language);
    	}
    	//	
    	if(!Env.isEnvLoad())
    		return true;
    	//	Default
    	return false;
	}

	@Override
	public boolean processActionCancel() {
		return validExit();
	}
	
	@Override
	public boolean loadData() {
		//	Auto Login Check
     	ch_RequestLogin.setChecked(Env.isAutoLogin());
 		//	Select Language
 		String language = Env.getAD_Language();
 		if(language != null
 				&& language.length() != 0) {
 			sp_Language.setSelection(getLanguagePosition(language));
 		} else {
 			sp_Language.setSelection(getLanguagePosition(Env.BASE_LANGUAGE));
 		}
    	//	Log Level
    	int position = -1;
    	int traceLevel = LogM.getTraceLevel(m_ctx);
    	//	Evaluate
    	if(traceLevel == Level.INFO.intValue()) {
    		position = 0;
    	} else if(traceLevel == Level.FINE.intValue()) {
    		position = 1;
    	} else if(traceLevel == Level.FINER.intValue()) {
    		position = 2;
    	} else if(traceLevel == Level.FINEST.intValue()) {
    		position = 3;
    	} else if(traceLevel == Level.SEVERE.intValue()) {
    		position = 4;
    	} else if(traceLevel == Level.WARNING.intValue()) {
    		position = 5;
    	} else if(traceLevel == Level.OFF.intValue()) {
    		position = 6;
    	}
    	//	No Trace Level
    	if(position == -1) {
    		position = 5;
    		LogM.setTraceLevel(m_ctx, Level.OFF);
    	}
    	//	Select Log Position
    	sp_LogLevel.setSelection(position);
    	
    	//	Save SD
    	ch_SaveSD.setChecked(Env.getContextAsBoolean("#SaveSD"));
 		//	
		return true;
	}
	
	/**
	 * Get Language position
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Language
	 * @return
	 * @return int
	 */
	private int getLanguagePosition(String p_Language) {
		for(int i = 0; i < sp_Language.getAdapter().getCount(); i++) {
			String m_Language = sp_Language.getItemAtPosition(i).toString();
			if(m_Language.equals(p_Language)) {
				return i;
			}
		}
		//	Default
		return 0;
	}

	@Override
	public void setEnabled(boolean enabled) {
		ch_RequestLogin.setEnabled(enabled);
    	sp_Language.setEnabled(enabled);
    	sp_LogLevel.setEnabled(enabled);
    	ch_SaveSD.setEnabled(enabled);
    	butt_DropDB.setEnabled(enabled);
	}
}
