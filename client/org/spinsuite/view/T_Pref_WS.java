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
import org.spinsuite.interfaces.I_CancelOk;
import org.spinsuite.mqtt.connection.MQTTConnection;
import org.spinsuite.util.Env;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class T_Pref_WS extends Fragment implements I_CancelOk {
	
	/**
	 * Default
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	public T_Pref_WS() {
		
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_ctx
	 */
	public T_Pref_WS(Context p_ctx) {
		m_ctx = p_ctx;
	}
	
	/**	Context					*/
	private Context			m_ctx = null;
	/**	URL SOAP Communication	*/
	private EditText 		et_UrlServer;
	/**	Timeout					*/
	private EditText 		et_Timeout;
	/**	Current View			*/
	private View 			m_View = null;
	/**	Is Load Ok				*/
	private boolean			m_IsLoadOk = false;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //	Current
    	if(m_View != null)
        	return m_View;
        //	RE-Load
        m_View = inflater.inflate(R.layout.t_pref_ws, container, false);
    	return m_View;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	if(m_IsLoadOk)
    		return;
    	//	
    	et_UrlServer 	= (EditText) m_View.findViewById(R.id.et_UrlServer);
    	et_Timeout 		= (EditText) m_View.findViewById(R.id.et_Timeout);
		m_IsLoadOk = true;
    }
    
    /**
     * Set Timeout in Activity
     * @author Carlos Parada 04/11/2012, 19:59:01
     * @return void
     */
    public void setTimeOut()
    {
    	//
		int timeoutInt = Env.getContextAsInt(m_ctx, "#Timeout");
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
    		url = Env.getContext(m_ctx, "#SUrlSoap");
    		if(url != null)
    			et_UrlServer.setText(url);
    	}
    	//	Timeout
    	if(timeout == null || timeout.length() == 0){
    		int timeoutInt = Env.getContextAsInt(m_ctx, "#Timeout");
    		timeout = String.valueOf(timeoutInt);
    		et_Timeout.setText(timeout);
    	}
//    	lockFront();
    }
	
    @Override
    public void onResume() {
        super.onResume();
//        lockFront();
    }
	
    @Override
	public boolean processActionOk() {
    	//	Standard Values
    	String url = et_UrlServer.getText().toString();
    	if(url != null 
    			&& url.length() > 0){
    		Env.setContext(m_ctx, "#SUrlSoap", url);
    	}
    	//	
    	String timeout = et_Timeout.getText().toString();
    	if(timeout != null 
    			&& timeout.length() > 0){
    		Env.setContext(m_ctx, "#Timeout", Integer.parseInt(timeout));
    	}
		//	Valid Timeout
		if(et_Timeout.getText() != null 
				&& et_Timeout.getText().toString().length() > 0){
			String limit = et_Timeout.getText().toString();
			Env.setContext(m_ctx, "#Timeout", Integer.parseInt(limit));
			MQTTConnection.setTimeout(getActivity(), Integer.parseInt(limit));
		}
		//	Stop Service
//		Intent service = new Intent(getActivity(), MQTTSyncService.class);
//		LogM.log(getActivity(), getClass(), Level.FINE, "Stoping MQTT Service");
//		stopService(service);
//		MQTTConnection.setIsReloadService(getActivity(), true);
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
}
