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

import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * 
 * @author Yamel Senih
 *
 */
public class T_Connection extends Activity implements I_Login {

	/**	URL SOAP Comunication	*/
	public EditText 	et_UrlSoap;
	/**	Synchronization Method	*/
	private EditText 	et_Method;
	private EditText 	et_Timeout;
	private Spinner 	sp_LogLevel;
	/** NameSpace*/
	private EditText 	et_NameSpace;
	/** Soap Object InitialLoad	*/
	//private InitialLoad m_load ;
	/**	Save data SD			*/
	private CheckBox 	ch_SaveSD;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.t_connection);
        
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
        StrictMode.setThreadPolicy(policy);
        
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        et_UrlSoap = (EditText) findViewById(R.id.et_UrlSoap);
    	et_Method = (EditText) findViewById(R.id.et_Method);
    	et_Timeout = (EditText) findViewById(R.id.et_Timeout);
    	sp_LogLevel = (Spinner) findViewById(R.id.sp_LogLevel);
    	et_NameSpace = (EditText) findViewById(R.id.et_NameSpace);
    	ch_SaveSD = (CheckBox) findViewById(R.id.ch_SaveSD);
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
		    	LogM.setTraceLevel(getApplicationContext(), level);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//	
			}
    	});
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, Login.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    /**
     * loock the field when sync is done
     * @author Yamel Senih 03/05/2012, 15:47:26
     * @return void
     */
    public void lockFront(){
    	if(!Env.isEnvLoad(this)){
    		et_UrlSoap.setEnabled(true);
    		et_Method.setEnabled(true);
    		et_NameSpace.setEnabled(true);
    		ch_SaveSD.setEnabled(true);
    		ch_SaveSD.setChecked(true);
    	} else {
    		et_UrlSoap.setEnabled(false);
    		et_Method.setEnabled(false);
    		et_NameSpace.setEnabled(false);
    		ch_SaveSD.setEnabled(false);
    		//Establece el Timeout en la Ventana
    		//Carlos Parada 04/11/2012
    		setTimeOut();
    		//Fin Carlos Parada
    	}
    }
    
    
    /**
     * Set Timeout in Activity
     * @author Carlos Parada 04/11/2012, 19:59:01
     * @return void
     */
    public void setTimeOut()
    {
    	//
		int timeoutInt = Env.getContextAsInt(this, "#Timeout");
		String timeout = String.valueOf(timeoutInt);
		et_Timeout.setText(timeout);
    }
    
    /**
     * Load Context Data
     * @author Yamel Senih 17/10/2012, 16:46:40
     * @return void
     */
    private void loadContext(){
    	/**
		 * Carlos Parada, Load var in comntext
		 */
    	
		if (Env.isEnvLoad(this))
		{	
			String sql = new String("SELECT sc.Name, sc.Value FROM AD_SysConfig sc");
	    	DB con = new DB(this);
	    	con.openDB(DB.READ_ONLY);
	    	Cursor rs = con.querySQL(sql, null);
	    	if(rs.moveToFirst()){
				do {
					Env.setContext(this, "#" + rs.getString(0), rs.getInt(1));
				} while(rs.moveToNext());
			}
	    	con.closeDB(rs);
	    		
		}
    }
    	
    @Override
    public void onStart() {
        super.onStart();
        String url = et_UrlSoap.getText().toString();
    	String method = et_Method.getText().toString();
    	String timeout = et_Timeout.getText().toString();
    	String nameSpace = et_NameSpace.getText().toString();
    	//	Load URL SOAP or SOPA JAJAJAJAJAJAJAJ
    	if(url == null || url.length() == 0){
    		url = Env.getContext(this, "#SUrlSoap");
    		if(url != null)
    			et_UrlSoap.setText(url);
    	}
    	//	Load Method
    	if(method == null || method.length() == 0){
    		method = Env.getContext(this, "#SMethod");
    		if(method != null)
    			et_Method.setText(method);
    	}
    	//	Name Space
    	if(nameSpace == null || nameSpace.length() == 0){
    		nameSpace = Env.getContext(this, "#SNameSpace");
    		if(nameSpace != null)
    			et_NameSpace.setText(nameSpace);
    	}
    	//	Timeout
    	if(timeout == null || timeout.length() == 0){
    		int timeoutInt = Env.getContextAsInt(this, "#Timeout");
    		timeout = String.valueOf(timeoutInt);
    		et_Timeout.setText(timeout);
    	}
    	//	Log Level
    	int position = -1;
    	int traceLevel = LogM.getTraceLevel(this);
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
    		LogM.setTraceLevel(this, Level.OFF);
    	}
    	//	Select Log Position
    	sp_LogLevel.setSelection(position);
    	
    	//	Save SD
    	ch_SaveSD.setChecked(Env.getContextAsBoolean(this, "#SaveSD"));
    	
    	lockFront();
    	
    }
	
    @Override
    public void onResume() {
        super.onResume();
        lockFront();
    }
	
    @Override
	public boolean aceptAction() {
		return true;
	}

	@Override
	public boolean cancelAction() {
		return false;
	}

	@Override
	public boolean loadData() {
		return false;
	}
}
