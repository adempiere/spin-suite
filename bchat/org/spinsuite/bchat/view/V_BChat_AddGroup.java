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

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.bchat.adapters.BChatContactAdapter;
import org.spinsuite.bchat.model.SPS_BC_Request;
import org.spinsuite.bchat.util.DisplayBChatContactItem;
import org.spinsuite.sync.content.Invited;
import org.spinsuite.sync.content.SyncRequest;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 6, 2015, 9:54:42 PM
 *
 */
public class V_BChat_AddGroup extends Activity {
    
   /**	List View					*/
	private ListView			lv_Contacts			= null;
	/**	Message						*/
	private EditText			et_GroupName 		= null;
	/**	Adapter						*/
    private BChatContactAdapter	m_ContactAdapter	= null;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.v_business_chat_add_group);
        lv_Contacts 	= (ListView) findViewById(R.id.lv_Contacts);
		et_GroupName 	= (EditText) findViewById(R.id.et_GroupName);
		//	
		lv_Contacts.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lv_Contacts.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		
		lv_Contacts.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				m_ContactAdapter.toggleSelection(position);
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.action_delete:
					return true;
				default:
					return false;
				}
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				m_ContactAdapter.removeSelection();
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}
			
		});
		
		getActionBar().setTitle(getString(R.string.BChat_AddGroup));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bc_add_group, menu);
		//	Get Item
		MenuItem item = menu.findItem(R.id.action_search);
		//	Search View
		final View searchView = SearchViewCompat.newSearchView(this);
		if (searchView != null) {
			//	Set Back ground Color
			int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
			EditText searchText = (EditText) searchView.findViewById(id);
			//	Set Parameters
			if(searchText != null)
				searchText.setTextAppearance(this, R.style.TextSearch);
			//	
			SearchViewCompat.setOnQueryTextListener(searchView,
					new OnQueryTextListenerCompat() {
				@Override
				public boolean onQueryTextChange(String newText) {
					if(m_ContactAdapter != null) {
						String mFilter = !TextUtils.isEmpty(newText) ? newText : null;
						m_ContactAdapter.getFilter().filter(mFilter);
					}
					return true;
				}
			});
			SearchViewCompat.setOnCloseListener(searchView,
					new OnCloseListenerCompat() {
				@Override
				public boolean onClose() {
					if (!TextUtils.isEmpty(SearchViewCompat.getQuery(searchView))) {
						SearchViewCompat.setQuery(searchView, null, true);
					}
					return true;
				}
                    
			});
			MenuItemCompat.setActionView(item, searchView);
		}
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
	    	case R.id.action_ok:
	    		okAction();
	    		return true;
	    	default:
	    		return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * Valid the Group Name
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void okAction() {
    	if(et_GroupName.getText() == null
    			|| et_GroupName.getText().toString().trim().length() == 0) {
    		Msg.alertMsg(this, getString(R.string.BChat_MustFillGroupName));
    	} else {
    		SparseBooleanArray itemsChecked = lv_Contacts.getCheckedItemPositions();
    		if(itemsChecked.size() == 0) {
    			Msg.alertMsg(this, getString(R.string.BChat_MustFillGroupName));
    		} else {
    			//	Create Request
    			SyncRequest request = new SyncRequest(0, 
    					String.valueOf(Env.getAD_User_ID()), 
    					SyncRequest.RT_BUSINESS_CHAT, 
    					String.valueOf(UUID.randomUUID()), et_GroupName.getText().toString(), false);
    			for(int i = 0; i < itemsChecked.size(); i++) {
        			boolean selected = itemsChecked.get(i);
        			if(selected) {
        				DisplayBChatContactItem contact = m_ContactAdapter.getItem(i);
        				request.addUser(
        						new Invited(contact.getRecord_ID(), 
        								SPS_BC_Request.STATUS_CREATED));
        			}
        		}
    			//	Save Request
    			SPS_BC_Request.newOutRequest(this, request);
    			//	
    			finish();
    		}
    	}
     }
    
    /**
     * Load All Contact
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void loadContact() {
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
		ArrayList<DisplayBChatContactItem> m_BChatContactData = new ArrayList<DisplayBChatContactItem>();
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
    	//	
    	m_ContactAdapter = new BChatContactAdapter(this, m_BChatContactData);
    	m_ContactAdapter.setDropDownViewResource(R.layout.i_bchat_contact);
		lv_Contacts.setAdapter(m_ContactAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadContact();
    	//	Hide Keyboard
    	getWindow().setSoftInputMode(
    			WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}