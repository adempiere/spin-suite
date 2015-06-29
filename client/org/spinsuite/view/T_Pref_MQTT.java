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
import org.spinsuite.mqtt.connection.MQTTConnection;
import org.spinsuite.mqtt.connection.MQTTDefaultValues;
import org.spinsuite.util.Env;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Feb 26, 2015, 11:51:10 PM
 *	<li> Login Correct
 * 	@see https://adempiere.atlassian.net/browse/SPIN-2
 */
public class T_Pref_MQTT extends T_Pref_Parent {
	
	/**
	 * Default
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public T_Pref_MQTT() {
		super();
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_ctx
	 */
	public T_Pref_MQTT(Context p_ctx) {
		super(p_ctx);
	}
	
	/**	For MQTT Server			*/
	private EditText 		et_MQTT_ServerName;
	/**	MQTT User				*/
	private EditText 		et_MQTT_ServerUser;
	/**	MQTT Pass				*/
	private EditText 		et_MQTT_ServerPass;
	/**	MQTT Port				*/
	private EditText 		et_MQTT_ServerPort;
	/**	MQTT Keep Alive Interval*/
	private EditText 		et_MQTT_KeepAliveInverval;
	/**	Enable Connection		*/
	private CheckBox 		ch_MQTT_AutomaticService;
	/**	MQTT File Path			*/
	private Button 			bt_MQTT_SSL_File_Path;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //	Current
    	if(m_View != null)
        	return m_View;
        //	RE-Load
        m_View = inflater.inflate(R.layout.t_pref_mqtt, container, false);
    	return m_View;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	if(m_IsLoadOk)
    		return;
    	//	MQTT Server
    	et_MQTT_ServerName 			= (EditText) m_View.findViewById(R.id.et_MQTT_ServerName);
    	et_MQTT_ServerUser 			= (EditText) m_View.findViewById(R.id.et_MQTT_ServerUser);
    	et_MQTT_ServerPass 			= (EditText) m_View.findViewById(R.id.et_MQTT_ServerPass);
    	et_MQTT_ServerPort 			= (EditText) m_View.findViewById(R.id.et_MQTT_ServerPort);
    	et_MQTT_KeepAliveInverval	= (EditText) m_View.findViewById(R.id.et_MQTT_KeepAliveInverval);
    	ch_MQTT_AutomaticService	= (CheckBox) m_View.findViewById(R.id.ch_MQTT_AutomaticService);
    	bt_MQTT_SSL_File_Path 		= (Button) m_View.findViewById(R.id.bt_MQTT_SSL_File_Path);
		m_IsLoadOk = true;
    }
    	
    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }
	
    @Override
    public void onResume() {
        super.onResume();
    }
	
    @Override
	public boolean processActionOk() {
		//	Set Values for MQTT Server
		MQTTConnection.setHost(m_ctx, et_MQTT_ServerName.getText().toString());
		MQTTConnection.setAlarmTime(m_ctx, MQTTDefaultValues.DEFAULT_MQTT_ALARM_TIME);
		MQTTConnection.setMQTTUser(m_ctx, et_MQTT_ServerUser.getText().toString());
		MQTTConnection.setMQTTPassword(m_ctx, et_MQTT_ServerPass.getText().toString());
		//	Set Port
		if(et_MQTT_ServerPort.getText() != null 
				&& et_MQTT_ServerPort.getText().toString().length() > 0) {
			String port = et_MQTT_ServerPort.getText().toString();
			MQTTConnection.setPort(m_ctx, Integer.parseInt(port));
		}
		//	Set Keep Alive Interval
		if(et_MQTT_KeepAliveInverval.getText() != null 
				&& et_MQTT_KeepAliveInverval.getText().toString().length() > 0) {
			String interal = et_MQTT_KeepAliveInverval.getText().toString();
			MQTTConnection.setKeepAliveInverval(m_ctx, Integer.parseInt(interal));
		}
		//	Is Automatic Service
		MQTTConnection.setIsAutomaticService(m_ctx, ch_MQTT_AutomaticService.isChecked());
		//	Valid SSL Connection
		if(bt_MQTT_SSL_File_Path.getText() != null 
				&& bt_MQTT_SSL_File_Path.getText().toString().length() > 0) {
			String m_SSL_File = bt_MQTT_SSL_File_Path.getText().toString();
			//	Hardcode
			MQTTConnection.setIsSSLConnection(m_ctx, m_SSL_File.contains(".crt"));
			MQTTConnection.setSSLFilePath(m_ctx, m_SSL_File);
		} else {
			MQTTConnection.setIsSSLConnection(m_ctx, false);
			MQTTConnection.setSSLFilePath(m_ctx, null);
		}
		//	Stop Service
//		Intent service = new Intent(m_ctx, MQTTSyncService.class);
//		LogM.log(m_ctx, getClass(), Level.FINE, "Stoping MQTT Service");
//		stopService(service);
//		MQTTConnection.setIsReloadService(m_ctx, true);
//		//	Start Service
//		LogM.log(this, getClass(), Level.FINE, "Starting MQTT Service");
//		startService(service);
//		//	
//		MQTTConnection.getInstance(Env.getCtx()).connectInThread();
//		finish();
		//	Default Return
		return true;
    }

	@Override
	public boolean processActionCancel() {
		return false;
	}

	@Override
	public boolean loadData() {
    	//	For MQTT Server
    	String m_MQTT_ServerName 			= et_MQTT_ServerName.getText().toString();
    	String m_MQTT_ServerUser 			= et_MQTT_ServerUser.getText().toString();
    	String m_MQTT_ServerPass 			= et_MQTT_ServerPass.getText().toString();
    	String m_MQTT_ServerPort 			= et_MQTT_ServerPort.getText().toString();
    	String m_MQTT_KeepAliveInverval 	= et_MQTT_KeepAliveInverval.getText().toString();
    	String m_MQTT_SSL_File_Path 		= bt_MQTT_SSL_File_Path.getText().toString();
    	boolean m_MQTT_AutomaticService 	= MQTTConnection.isAutomaticService(m_ctx);
		//	For MQTT Server
    	if(m_MQTT_ServerName == null || m_MQTT_ServerName.length() == 0){
    		m_MQTT_ServerName = MQTTConnection.getHost(m_ctx);
    		if(m_MQTT_ServerName != null) {
    			et_MQTT_ServerName.setText(m_MQTT_ServerName);
    		} else if(!Env.isEnvLoad()) {
    			et_MQTT_ServerName.setText(MQTTDefaultValues.DEFAULT_MQTT_SERVER_NAME);
    		}
    	}
    	//	For User
    	if(m_MQTT_ServerUser == null || m_MQTT_ServerUser.length() == 0){
    		m_MQTT_ServerUser = MQTTConnection.getMQTTUser(m_ctx);
    		if(m_MQTT_ServerUser != null) {
    			et_MQTT_ServerUser.setText(m_MQTT_ServerUser);
    		} else if(!Env.isEnvLoad()) {
    			et_MQTT_ServerUser.setText(MQTTDefaultValues.DEFAULT_MQTT_USER);
    		}
    	}
    	//	For Pass
    	if(m_MQTT_ServerPass == null || m_MQTT_ServerPass.length() == 0){
    		m_MQTT_ServerPass = MQTTConnection.getMQTTPass(m_ctx);
    		if(m_MQTT_ServerPass != null) {
    			et_MQTT_ServerPass.setText(m_MQTT_ServerPass);
    		} else if(!Env.isEnvLoad()) {
    			et_MQTT_ServerPass.setText(MQTTDefaultValues.DEFAULT_MQTT_PASS);
    		}
    	}
    	//	Enable MQTT Service
    	ch_MQTT_AutomaticService.setChecked(m_MQTT_AutomaticService);
       	//	Port
    	if(m_MQTT_ServerPort == null || m_MQTT_ServerPort.length() == 0) {
    		int port = MQTTConnection.getPort(m_ctx);
    		m_MQTT_ServerPort = String.valueOf(port);
    		if(m_MQTT_ServerPort != null
    				&& m_MQTT_ServerPort.length() > 0
    				&& Integer.parseInt(m_MQTT_ServerPort) > 0) {
    			et_MQTT_ServerPort.setText(m_MQTT_ServerPort);
    		} else if(!Env.isEnvLoad()) {
    			et_MQTT_ServerPort.setText(String.valueOf(MQTTDefaultValues.DEFAULT_MQTT_PORT));
    		}
    	}
       	//	Keep Alive Interval
    	if(m_MQTT_KeepAliveInverval == null || m_MQTT_KeepAliveInverval.length() == 0) {
    		int interval = MQTTConnection.getKeepAliveInverval(m_ctx);
    		m_MQTT_KeepAliveInverval = String.valueOf(interval);
    		et_MQTT_KeepAliveInverval.setText(m_MQTT_KeepAliveInverval);
    		if(m_MQTT_KeepAliveInverval != null
    				&& m_MQTT_KeepAliveInverval.length() > 0
    				&& Integer.parseInt(m_MQTT_KeepAliveInverval) > 0) {
    			et_MQTT_KeepAliveInverval.setText(m_MQTT_KeepAliveInverval);
    		} else if(!Env.isEnvLoad()) {
    			et_MQTT_KeepAliveInverval.setText(String.valueOf(MQTTDefaultValues.DEFAULT_MQTT_KEEP_ALIVE_INT));
    		}
    	}
    	//	For SSL
    	if(m_MQTT_SSL_File_Path == null || m_MQTT_SSL_File_Path.length() == 0){
    		m_MQTT_SSL_File_Path = MQTTConnection.getSSLFilePath(m_ctx);
    		if(m_MQTT_SSL_File_Path != null)
    			bt_MQTT_SSL_File_Path.setText(m_MQTT_SSL_File_Path);
    	}
		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		et_MQTT_ServerName.setEnabled(enabled);
    	et_MQTT_ServerUser.setEnabled(enabled);
    	et_MQTT_ServerPass.setEnabled(enabled);
    	et_MQTT_ServerPort.setEnabled(enabled);
    	et_MQTT_KeepAliveInverval.setEnabled(enabled);
    	ch_MQTT_AutomaticService.setEnabled(enabled);
    	bt_MQTT_SSL_File_Path.setEnabled(enabled);
	}
}
