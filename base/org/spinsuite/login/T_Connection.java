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
import org.spinsuite.mqtt.connection.MQTTConnection;
import org.spinsuite.mqtt.connection.MQTTDefaultValues;
import org.spinsuite.mqtt.connection.MQTTSyncService;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
	private EditText 		et_UrlServer;
	private EditText 		et_Timeout;
	private Spinner 		sp_LogLevel;
	/**	For MQTT Server			*/
	private EditText 		et_MQTT_ServerName;
	/**	MQTT User				*/
	private EditText 		et_MQTT_ServerUser;
	/**	MQTT Pass				*/
	private EditText 		et_MQTT_ServerPass;
	/**	MQTT Port				*/
	private EditText 		et_MQTT_ServerPort;
	/**	Enable Connection		*/
	private CheckBox 		ch_MQTT_AutomaticService;
	/**	MQTT File Path			*/
	private Button 			bt_MQTT_SSL_File_Path;
	/**	Save data SD			*/
	private CheckBox 		ch_SaveSD;
	/**	Drop Data Base			*/
	private Button			butt_DropDB;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.t_connection);
        
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
        StrictMode.setThreadPolicy(policy);
        
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        et_UrlServer 				= (EditText) findViewById(R.id.et_UrlServer);
    	et_Timeout 					= (EditText) findViewById(R.id.et_Timeout);
    	sp_LogLevel 				= (Spinner) findViewById(R.id.sp_LogLevel);
    	ch_SaveSD 					= (CheckBox) findViewById(R.id.ch_SaveSD);
    	butt_DropDB 				= (Button) findViewById(R.id.butt_DropDB);
    	//	MQTT Server
    	et_MQTT_ServerName 			= (EditText) findViewById(R.id.et_MQTT_ServerName);
    	et_MQTT_ServerUser 			= (EditText) findViewById(R.id.et_MQTT_ServerUser);
    	et_MQTT_ServerPass 			= (EditText) findViewById(R.id.et_MQTT_ServerPass);
    	et_MQTT_ServerPort 			= (EditText) findViewById(R.id.et_MQTT_ServerPort);
    	ch_MQTT_AutomaticService	= (CheckBox) findViewById(R.id.ch_MQTT_AutomaticService);
    	bt_MQTT_SSL_File_Path 		= (Button) findViewById(R.id.bt_MQTT_SSL_File_Path);
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.cancel_ok, menu);
        return true;
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
            case R.id.action_cancel:
            	cancelAction();
            	return true;
            case R.id.action_ok:
            	aceptAction();
            	return true;
        }
        //	Super
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
    	//	Hide Keyboard
    	getWindow().setSoftInputMode(
    			WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    
    
    /**
     * Set Timeout in Activity
     * @author Carlos Parada 04/11/2012, 19:59:01
     * @return void
     */
    public void setTimeOut()
    {
    	//
		int timeoutInt = Env.getContextAsInt("#Timeout");
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
    		int timeoutInt = Env.getContextAsInt("#Timeout");
    		timeout = String.valueOf(timeoutInt);
    		et_Timeout.setText(timeout);
    	}
    	//	For MQTT Server
    	String m_MQTT_ServerName = et_MQTT_ServerName.getText().toString();
    	String m_MQTT_ServerUser = et_MQTT_ServerUser.getText().toString();
    	String m_MQTT_ServerPass = et_MQTT_ServerPass.getText().toString();
    	String m_MQTT_ServerPort = et_MQTT_ServerPort.getText().toString();
    	String m_MQTT_SSL_File_Path = bt_MQTT_SSL_File_Path.getText().toString();
    	boolean m_MQTT_AutomaticService = MQTTConnection.isAutomaticService(this);
		//	For MQTT Server
    	if(m_MQTT_ServerName == null || m_MQTT_ServerName.length() == 0){
    		m_MQTT_ServerName = MQTTConnection.getHost(this);
    		if(m_MQTT_ServerName != null)
    			et_MQTT_ServerName.setText(m_MQTT_ServerName);
    	}
    	//	For User
    	if(m_MQTT_ServerUser == null || m_MQTT_ServerUser.length() == 0){
    		m_MQTT_ServerUser = MQTTConnection.getMQTTUser(this);
    		if(m_MQTT_ServerUser != null)
    			et_MQTT_ServerUser.setText(m_MQTT_ServerUser);
    	}
    	//	For Pass
    	if(m_MQTT_ServerPass == null || m_MQTT_ServerPass.length() == 0){
    		m_MQTT_ServerPass = MQTTConnection.getMQTTPass(this);
    		if(m_MQTT_ServerPass != null)
    			et_MQTT_ServerPass.setText(m_MQTT_ServerPass);
    	}
    	//	Enable MQTT Service
    	ch_MQTT_AutomaticService.setChecked(m_MQTT_AutomaticService);
       	//	Port
    	if(m_MQTT_ServerPort == null || m_MQTT_ServerPort.length() == 0) {
    		int port = MQTTConnection.getPort(this);
    		m_MQTT_ServerPort = String.valueOf(port);
    		et_MQTT_ServerPort.setText(m_MQTT_ServerPort);
    	}
    	//	For SSL
    	if(m_MQTT_SSL_File_Path == null || m_MQTT_SSL_File_Path.length() == 0){
    		m_MQTT_SSL_File_Path = MQTTConnection.getSSLFilePath(this);
    		if(m_MQTT_SSL_File_Path != null)
    			bt_MQTT_SSL_File_Path.setText(m_MQTT_SSL_File_Path);
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
		//	Set Values for MQTT Server
		MQTTConnection.setClient_ID(this, String.valueOf(Env.getAD_User_ID()));
		MQTTConnection.setHost(this, et_MQTT_ServerName.getText().toString());
		MQTTConnection.setAlarmTime(this, MQTTDefaultValues.DEFAULT_MQTT_ALARM_TIME);
		MQTTConnection.setMQTTUser(this, et_MQTT_ServerUser.getText().toString());
		MQTTConnection.setMQTTPassword(this, et_MQTT_ServerPass.getText().toString());
		//	Set Port
		if(et_MQTT_ServerPort.getText() != null 
				&& et_MQTT_ServerPort.getText().toString().length() > 0) {
			String port = et_MQTT_ServerPort.getText().toString();
			MQTTConnection.setPort(this, Integer.parseInt(port));
		}
		//	Is Automatic Service
		MQTTConnection.setIsAutomaticService(this, ch_MQTT_AutomaticService.isChecked());
		//	Valid SSL Connection
		if(bt_MQTT_SSL_File_Path.getText() != null 
				&& bt_MQTT_SSL_File_Path.getText().toString().length() > 0) {
			String m_SSL_File = bt_MQTT_SSL_File_Path.getText().toString();
			//	Hardcode
			MQTTConnection.setIsSSLConnection(this, m_SSL_File.contains(".ssl"));
			MQTTConnection.setSSLFilePath(this, m_SSL_File);
		} else {
			MQTTConnection.setIsSSLConnection(this, false);
			MQTTConnection.setSSLFilePath(this, null);
		}
		//	Valid Timeout
		if(et_Timeout.getText() != null 
				&& et_Timeout.getText().toString().length() > 0){
			String limit = et_Timeout.getText().toString();
			Env.setContext("#Timeout", Integer.parseInt(limit));
			MQTTConnection.setTimeout(this, Integer.parseInt(limit));
		}
		//	Stop Service
		Intent service = new Intent(this, MQTTSyncService.class);
		LogM.log(this, getClass(), Level.FINE, "Stoping MQTT Service");
		stopService(service);
		MQTTConnection.setIsReloadService(this, true);
		//	Start Service
		LogM.log(this, getClass(), Level.FINE, "Starting MQTT Service");
		startService(service);
		//	
		finish();
		//	Default Return
		return true;
	}

	@Override
	public boolean cancelAction() {
		finish();
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
