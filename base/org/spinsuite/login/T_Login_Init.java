/*************************************************************************************
 * Product: Spin-Suite (Making your Business Spin)                                   *
 * getActivity() program is free software; you can redistribute it and/or modify it  *
 * under the terms version 2 of the GNU General Public License as published          *
 * by the Free Software Foundation. getActivity() program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied        *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                  *
 * See the GNU General Public License for more details.                              *
 * You should have received a copy of the GNU General Public License along           *
 * with getActivity() program; if not, write to the Free Software Foundation, Inc.,  *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                            *
 * For the text or an alternative of getActivity() public license, you may reach us  *
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Carlos Parada www.erpcya.com                      				 *
 *************************************************************************************/
package org.spinsuite.login;


import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.mqtt.connection.MQTTConnection;
import org.spinsuite.mqtt.connection.MQTTDefaultValues;
import org.spinsuite.sync.SyncService;
import org.spinsuite.util.Env;
import org.spinsuite.util.Msg;
import org.spinsuite.util.SyncValues;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * 
 * @author Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com Mar 6, 2015, 12:29:57 AM
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * 	<li>Add Url for connection
 *
 */
@SuppressLint("ValidFragment")
public class T_Login_Init extends DialogFragment 
							implements OnClickListener {
	
	/** Text User				*/
	private EditText 	et_User;
	/** Text Password			*/
	private EditText 	et_PassWord;
	/**	URL SOAP Communication	*/
	private EditText 	et_UrlServer;
	/**	Time Out				*/
	private EditText 	et_Timeout;
	/**	For MQTT Server			*/
	private EditText 	et_MQTT_ServerName;
	/**	MQTT User				*/
	private EditText 	et_MQTT_ServerUser;
	/**	MQTT Pass				*/
	private EditText 	et_MQTT_ServerPass;
	/**	MQTT Port				*/
	private EditText 	et_MQTT_ServerPort;
	/**	MQTT File Path			*/
	private Button 		bt_MQTT_SSL_File_Path;
	/**	Load Default Data		*/
	private CheckBox	ch_ch_LoadTestData;
	/**	Context					*/
	private Activity 	m_Callback = null;
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Callback
	 */
	public T_Login_Init(Activity p_Callback) {
		m_Callback = p_Callback;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.t_login_init, null);
		//	Get Views
		et_User		 			= (EditText) view.findViewById(R.id.et_User);
		et_PassWord 			= (EditText) view.findViewById(R.id.et_Pass);
		et_UrlServer 			= (EditText) view.findViewById(R.id.et_UrlServer);
    	et_Timeout 				= (EditText) view.findViewById(R.id.et_Timeout);
    	//	MQTT Server
    	et_MQTT_ServerName 		= (EditText) view.findViewById(R.id.et_MQTT_ServerName);
    	et_MQTT_ServerUser 		= (EditText) view.findViewById(R.id.et_MQTT_ServerUser);
    	et_MQTT_ServerPass 		= (EditText) view.findViewById(R.id.et_MQTT_ServerPass);
    	et_MQTT_ServerPort 		= (EditText) view.findViewById(R.id.et_MQTT_ServerPort);
    	bt_MQTT_SSL_File_Path 	= (Button) 	 view.findViewById(R.id.bt_MQTT_SSL_File_Path);
    	ch_ch_LoadTestData		= (CheckBox) view.findViewById(R.id.ch_LoadTestData);
		//	Set Authentication for test
		et_User.setText(SyncValues.DEFAULT_AD_USER);
		et_PassWord.setText(SyncValues.DEFAULT_AD_PASS);
    	// Carlos Parada Setting Parameters for Spin-Suite Service Call 
    	et_UrlServer.setText(SyncValues.DEFAULT_SOAP_URL);
    	et_Timeout.setText(String.valueOf(SyncValues.DEFAULT_TIMEOUT));
    	//End Carlos Parada
    	//	For MQTT Server
    	et_MQTT_ServerName.setText(MQTTDefaultValues.DEFAULT_MQTT_SERVER_NAME);
    	et_MQTT_ServerUser.setText(MQTTDefaultValues.DEFAULT_MQTT_USER);
    	et_MQTT_ServerPass.setText(MQTTDefaultValues.DEFAULT_MQTT_PASS);
    	et_MQTT_ServerPort.setText(String.valueOf(MQTTDefaultValues.DEFAULT_MQTT_PORT));
    	ch_ch_LoadTestData.setChecked(false);
		//	
		builder.setView(view);
		//	
		builder.setNegativeButton(R.string.Action_Cancel, this);
		builder.setPositiveButton(android.R.string.ok, this);
		return builder.create();
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		//	Valid for Initial Sync
		if (which == -1
				&& isValid()) {
			startSynchronization();
		} else {
			m_Callback.finish();
		}
	}
	
	/**
	 * Start Synchronization for Initial Load
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 8, 2014, 11:25:21 AM
	 * @return void
	 */
	public void startSynchronization() {
		//	Else
		Env.setContext("#SUrlSoap", et_UrlServer.getText().toString());
		Env.setContext("#SMethod", SyncValues.DEFAULT_METHOD);
		Env.setContext("#SNameSpace", SyncValues.DEFAULT_NAME_SPACE);
		Env.setContext("#SUser", et_User.getText().toString());
		Env.setContext("#SPass", et_PassWord.getText().toString());
		//	Set Values for MQTT Server
		MQTTConnection.setClient_ID(getActivity(), String.valueOf(Env.getAD_User_ID()));
		MQTTConnection.setHost(getActivity(), et_MQTT_ServerName.getText().toString());
		MQTTConnection.setAlarmTime(getActivity(), MQTTDefaultValues.DEFAULT_MQTT_ALARM_TIME);
		MQTTConnection.setMQTTUser(getActivity(), et_MQTT_ServerUser.getText().toString());
		MQTTConnection.setMQTTPassword(getActivity(), et_MQTT_ServerPass.getText().toString());
		//	Set Port
		if(et_MQTT_ServerPort.getText() != null 
				&& et_MQTT_ServerPort.getText().toString().length() > 0) {
			String port = et_MQTT_ServerPort.getText().toString();
			MQTTConnection.setPort(getActivity(), Integer.parseInt(port));
		}
		//	Valid SSL Connection
		if(bt_MQTT_SSL_File_Path.getText() != null 
				&& bt_MQTT_SSL_File_Path.getText().toString().length() > 0) {
			String m_SSL_File = bt_MQTT_SSL_File_Path.getText().toString();
			//	Hardcode
			MQTTConnection.setIsSSLConnection(getActivity(), m_SSL_File.contains(".ssl"));
			MQTTConnection.setSSLFilePath(getActivity(), m_SSL_File);
		} else {
			MQTTConnection.setIsSSLConnection(getActivity(), false);
			MQTTConnection.setSSLFilePath(getActivity(), null);
		}
		//	Valid Timeout
		if(et_Timeout.getText() != null 
				&& et_Timeout.getText().toString().length() > 0){
			String limit = et_Timeout.getText().toString();
			Env.setContext("#Timeout", Integer.parseInt(limit));
			MQTTConnection.setTimeout(getActivity(), Integer.parseInt(limit));
		}
		//	For Change to Dynamic
		MQTTConnection.setKeepAliveInverval(getActivity(), MQTTDefaultValues.DEFAULT_MQTT_KEEP_ALIVE_INT);
		//	Start Service
		MQTTConnection.setIsAutomaticService(getActivity(), true);
		//	For Test Data
		if(ch_ch_LoadTestData.isChecked()) {
			((Login)m_Callback).loadDefaultData();
			//	Default Return
			return;
		}
		//	Create Directory
		Env.createDefaultDirectory(getActivity());
		//	
		((I_Login)m_Callback).setEnabled(false);
		//	Add Value to Web
		String url = et_UrlServer.getText().toString();
		//	
		//	Instance Bundle
		Bundle bundle = new Bundle();
		//	Add Parameters
		bundle.putString(SyncValues.KEY_SOAP_URL, SyncValues.getInitialUrl(url));
		bundle.putString(SyncValues.KEY_NAME_SPACE, SyncValues.DEFAULT_NAME_SPACE);
		bundle.putString(SyncValues.KEY_METHOD, SyncValues.DEFAULT_METHOD);
		bundle.putBoolean(SyncValues.KEY_NET_SERVICE, true);
		bundle.putString(SyncValues.KEY_USER, et_User.getText().toString());
		bundle.putString(SyncValues.KEY_PASS, et_PassWord.getText().toString());
		bundle.putString(SyncValues.KEY_SOAP_ACTION, SyncValues.DEFAULT_NAME_SPACE + SyncValues.DEFAULT_METHOD);
		bundle.putInt(SyncValues.KEY_TIMEOUT, Env.getContextAsInt("#Timeout"));
		//	Instance Service
		Intent m_Service = new Intent(getActivity(), SyncService.class);
		m_Service.putExtras(bundle);
		m_Callback.startService(m_Service);
	}
	
	/**
     * Valid fields on Activity
     * @author Yamel Senih 24/04/2012, 12:33:57
     * @return
     * @return boolean
     */
    private boolean isValid(){
    	if(et_UrlServer.getText() != null 
    			&& et_UrlServer.getText().toString().length() > 0){
    		return true;
    	} else {
    		Msg.alertMustFillField(getActivity(), R.string.Url_Server, et_UrlServer);
    	}
    	return false;
    }
}