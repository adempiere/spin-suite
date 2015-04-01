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
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.login;

import java.util.logging.Level;

import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View.OnClickListener;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class T_Connection extends Activity implements I_Login {

	/**	URL SOAP Communication	*/
	public EditText 	et_UrlServer;
	private EditText 	et_Timeout;
	private Spinner 	sp_LogLevel;
	/**	Save data SD			*/
	private CheckBox 	ch_SaveSD;
	private Button		butt_DropDB;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.t_connection);
        
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
        StrictMode.setThreadPolicy(policy);
        
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        et_UrlServer 	= (EditText) findViewById(R.id.et_UrlServer);
    	et_Timeout 		= (EditText) findViewById(R.id.et_Timeout);
    	sp_LogLevel 	= (Spinner) findViewById(R.id.sp_LogLevel);
    	ch_SaveSD 		= (CheckBox) findViewById(R.id.ch_SaveSD);
    	butt_DropDB 	= (Button) findViewById(R.id.butt_DropDB);
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
    	//	Listener for Button
    	butt_DropDB.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				deleteDB();
			}
		});
    }
    
    /**
     * DElete Database
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void deleteDB() {
    	Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_AskDelete));
		ask.setPositiveButton(getResources().getString(R.string.msg_Acept), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				//	Delete
				//	Delete DataBase
				Msg.confirmMsg(getApplicationContext(), "");
				deleteDatabase(Env.getDB_PathName());
				Env.cacheReset();
				Env.setIsEnvLoad(false);
				Env.setIsLogin(false);
				Env.setContext("#InitialLoadSynchronizing", false);
			}
		});
		ask.show();
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
    	if(!Env.isEnvLoad()){
    		setEnabled(true);
    	} else {
    		setEnabled(false);
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
    	
    @Override
    public void onStart() {
        super.onStart();
        String url = et_UrlServer.getText().toString();
    	String timeout = et_Timeout.getText().toString();
    	//	Load URL SOAP or SOPA JAJAJAJAJAJAJAJ
    	if(url == null || url.length() == 0){
    		url = Env.getContext("#SUrlSoap");
    		if(url != null)
    			et_UrlServer.setText(url);
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
    	ch_SaveSD.setChecked(Env.getContextAsBoolean("#SaveSD"));
    	
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

	@Override
	public void setEnabled(boolean enabled) {
		et_UrlServer.setEnabled(enabled);
		ch_SaveSD.setEnabled(enabled);
		ch_SaveSD.setChecked(enabled);
	}
}
