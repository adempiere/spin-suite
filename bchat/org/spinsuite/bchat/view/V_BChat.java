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

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.bchat.adapters.BChatContactAdapter;
import org.spinsuite.bchat.util.DisplayBChatContactItem;
import org.spinsuite.mqtt.connection.MQTTConnection;
import org.spinsuite.mqtt.connection.MQTTDefaultValues;
import org.spinsuite.mqtt.connection.MQTTListener;
import org.spinsuite.sync.content.SyncRequest;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.SerializerUtil;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class V_BChat extends FragmentActivity {
	
	/**	Drawer Layout				*/
	private DrawerLayout 					m_DLayout;
	/**	List View with options		*/
    private ListView 						m_DList;
    /**	Toggle						*/
    private ActionBarDrawerToggle 			m_DToggle;
    /**	Flag (Drawer Loaded)		*/
    private boolean 						isDrawerLoaded = false;
    /**	Action Bar					*/
    private ActionBar 						actionBar = null;
    /** Menu						*/
    private Menu							m_CurrentMenu = null;
    /**	Contact Data				*/
    private ArrayList<DisplayBChatContactItem> 	m_BChatContactData = null;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	//	Add Support to Progress Bar in Action Bar
    	requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    	//	
    	super.setContentView(R.layout.tv_base);
    	//	Set ProgressBar to false
    	setProgressBarIndeterminateVisibility(false);
    	
    	actionBar = getActionBar();
    	//	
    	actionBar.setDisplayHomeAsUpEnabled(true);
    	actionBar.setHomeButtonEnabled(true);
    	//	Load Drawer
    	loadDrawer();
    	//	Load Contact
    	loadContact();
    }
    
    /**
     * Load Drawer
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 14/03/2014, 09:23:22
     * @return void
     */
    protected void loadDrawer(){
    	//	
    	m_DLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //	
    	m_DList = (ListView) findViewById(R.id.left_drawer);
        //	
        m_DLayout.setDrawerShadow(
        		Env.getResourceID(this, R.attr.ic_ab_drawer_shadow), GravityCompat.START);
        
        m_DList.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				m_DLayout.closeDrawers();
				DisplayBChatContactItem item = (DisplayBChatContactItem) adapter.getItemAtPosition(position);
				//	Send Request
				try {
					SyncRequest request = new SyncRequest(
							String.valueOf(Env.getAD_User_ID()), 
							SyncRequest.RT_BUSINESS_CHAT, 
							String.valueOf(UUID.randomUUID()));
					//	Verify Connection
					MQTTConnection currentConnection = MQTTConnection.getInstance(getApplicationContext(), 
							new MQTTListener(getApplicationContext()), 
							null);
					if(currentConnection.isConnected()) {
						currentConnection.subscribeEx(request.getTopicName(), MQTTConnection.AT_LEAST_ONCE_1);
						byte[] msg = SerializerUtil.serializeObject(request);
						MqttMessage message = new MqttMessage(msg);
						message.setQos(MQTTConnection.AT_LEAST_ONCE_1);
						message.setRetained(true);
						currentConnection.publish(MQTTDefaultValues.getRequestTopic(String.valueOf(item.getRecord_ID())), message);
					}
					
				} catch (MqttSecurityException e) {
					e.printStackTrace();
				} catch (MqttException e) {
					e.printStackTrace();
				}
			}
        });

        m_DToggle = new ActionBarDrawerToggle(this, m_DLayout, 
        		R.string.drawer_open, R.string.drawer_close) {
            
        	public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
            
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
            	super.onConfigurationChanged(newConfig);
            	m_DToggle.onConfigurationChanged(newConfig);
            }
        };
        //	
        m_DToggle.syncState();
        //	Set Toggle
        m_DLayout.setDrawerListener(m_DToggle);
        isDrawerLoaded = true;
    }
    
    /**
     * Load Contact Users
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void loadContact() {
    	if(m_BChatContactData == null) {
    		DB conn = new DB(this);
    		//	Load Connection
    		DB.loadConnection(conn, DB.READ_ONLY);
    		//	
    		//	SQL
    		String sql = new String("SELECT u.AD_User_ID, u.Name "
    				+ "FROM AD_User u "
    				+ "WHERE u.AD_User_ID <> " + Env.getAD_User_ID());
    		//	
			LogM.log(this, getClass(), Level.FINE, "SQL Load Contact BChat=" + sql);
			//	
			Cursor rs = conn.querySQL(sql.toString(), null);
			//	Instance
			m_BChatContactData = new ArrayList<DisplayBChatContactItem>();
			if(rs.moveToFirst()){
				do {
					m_BChatContactData.add(
							new DisplayBChatContactItem(
									rs.getInt(0), 
									rs.getString(1), 
									null
							)
					);
				} while(rs.moveToNext());
			}
	    	//	Close Connection
	    	DB.closeConnection(conn);
    	}
    	//	
    	BChatContactAdapter mi_adapter = new BChatContactAdapter(this, R.layout.i_bchat_contact, m_BChatContactData);
		mi_adapter.setDropDownViewResource(R.layout.i_bchat_contact);
		getDrawerList().setAdapter(mi_adapter);
    }
    
    /**
     * Set Visible a Indeterminate Progress Bar and hide or show Option Menu
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param visible
     * @return void
     */
    protected void setVisibleProgress(boolean visible) {
    	//	
    	setProgressBarIndeterminateVisibility(visible);
    	setVisibleMenu(!visible);
    }
    
    /**
     * Set Visible menu
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param visible
     * @return void
     */
    protected void setVisibleMenu(boolean visible) {
    	//	Valid Null
    	if(m_CurrentMenu == null)
    		return;
    	//	Set Visible
    	for(int i = 0; i < m_CurrentMenu.size(); i++) {
    		m_CurrentMenu.getItem(i).setVisible(visible);
    	}
    }
    
    /**
     * Is Drawer Loader
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/03/2014, 10:29:47
     * @return
     * @return boolean
     */
    protected boolean isDrawerLoaded(){
    	return isDrawerLoaded;
    }
    
    /**
     * Is Drawer Layout Open
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/03/2014, 10:32:25
     * @return
     * @return boolean
     */
    protected boolean isDrawerLayoutOpen(){
    	if(isDrawerLoaded)
        	return m_DLayout.isDrawerOpen(m_DList);
    	return false;
    }
    
    /**
     * Get Drawer List
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 14/03/2014, 17:43:59
     * @return
     * @return ListView
     */
    protected ListView getDrawerList(){
    	return m_DList;
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(isDrawerLoaded)
        	m_DToggle.syncState();
    }
    
    

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(isDrawerLoaded)
        	m_DToggle.onConfigurationChanged(newConfig);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	boolean ok = super.onCreateOptionsMenu(menu);
    	m_CurrentMenu = menu;
    	return ok;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(isDrawerLoaded) {
    		if (m_DToggle.onOptionsItemSelected(item)) {
                return true;
            }
    	}
        // Handle action buttons
        switch(item.getItemId()) {
        
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(isDrawerLoaded){
        	boolean drawerOpen = m_DLayout.isDrawerOpen(m_DList);
        	if(drawerOpen)
        		menu.setGroupVisible(R.id.group_tab_menu, false);
        }
        return super.onPrepareOptionsMenu(menu);
    }    
}
