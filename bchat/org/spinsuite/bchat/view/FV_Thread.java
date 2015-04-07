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
package org.spinsuite.bchat.view;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.spinsuite.base.R;
import org.spinsuite.bchat.model.SPS_BC_Request;
import org.spinsuite.interfaces.I_FragmentSelect;
import org.spinsuite.mqtt.connection.MQTTConnection;
import org.spinsuite.mqtt.connection.MQTTDefaultValues;
import org.spinsuite.mqtt.connection.MQTTListener;
import org.spinsuite.sync.content.SyncRequest;
import org.spinsuite.util.Env;
import org.spinsuite.util.SerializerUtil;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 6, 2015, 9:54:42 PM
 *
 */
public class FV_Thread extends Fragment 
				implements I_FragmentSelect {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/04/2014, 17:42:27
	 */
    public FV_Thread(){
    	
    }
    
    /**	Call Back					*/
    private I_FragmentSelect			m_Callback 			= null;
    /**	View 						*/
	private View 						m_view 				= null;
	/**	List View					*/
	private ListView					lv_Thread			= null;
	/**	Message						*/
	private EditText					et_Message 			= null;
	/**	Button Send					*/
	private ImageButton					ib_Send				= null;
	/**	Conversation Type			*/
	private int 						m_ConversationType 	= 0;
	/**	Conversation Type Constants	*/
	public static final int				CT_REQUEST			= 0;
	public static final int				CT_CHAT				= 1;
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		if(m_view != null)
			return m_view;
		//	Inflate
		m_view =  inflater.inflate(R.layout.v_business_chat_thread, container, false);
    	//	Scroll
		lv_Thread 	= (ListView) m_view.findViewById(R.id.lv_Thread);
		et_Message 	= (EditText) m_view.findViewById(R.id.et_Message);
		ib_Send 	= (ImageButton) m_view.findViewById(R.id.ib_Send);
		//	Listener
		ib_Send.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//	Send Message
				sendMessage();
			}
		});
		return m_view;
	}
    
    /**
     * Send Message
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void sendMessage() {
		//	Send Request
		try {
			SyncRequest request = new SyncRequest(
					String.valueOf(Env.getAD_User_ID()), 
					SyncRequest.RT_BUSINESS_CHAT, 
					String.valueOf(UUID.randomUUID()), "Epale");
			//	Insert New
			SPS_BC_Request.newOutRequest(getActivity(), request);
			//	Verify Connection
			MQTTConnection currentConnection = MQTTConnection.getInstance(getActivity(), 
					new MQTTListener(getActivity()), 
					null, false);
			if(currentConnection.isConnected()) {
				currentConnection.subscribeEx(request.getTopicName(), MQTTConnection.AT_LEAST_ONCE_1);
				byte[] msg = SerializerUtil.serializeObject(request);
				MqttMessage message = new MqttMessage(msg);
				message.setQos(MQTTConnection.AT_LEAST_ONCE_1);
				message.setRetained(true);
				currentConnection.publish(MQTTDefaultValues.getRequestTopic(String.valueOf(/*item.getRecord_ID()*/0)), message);
			}
			
		} catch (MqttSecurityException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}

    }
    
    /**
     * Load List
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/04/2014, 21:11:36
     * @return
     * @return boolean
     */
    private boolean loadData(){
    	//	Return
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            m_Callback = (I_FragmentSelect) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement I_FragmentSelect");
        }
    }
    
    /**
     * Set Conversation Type
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_ConversationType
     * @return void
     */
    public void setConversationType(int p_ConversationType) {
    	m_ConversationType = p_ConversationType;
    }
    
    @Override
    public void onItemSelected(int p_Record_ID) {
    	//	For Request
    	if(m_ConversationType == CT_REQUEST) {
    		if(p_Record_ID < 0) {
    			
    		}
    	}
    }
}