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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.bchat.adapters.BChatThreadAdapter;
import org.spinsuite.bchat.model.SPS_BC_Message;
import org.spinsuite.bchat.model.SPS_BC_Request;
import org.spinsuite.bchat.util.BC_OpenMsg;
import org.spinsuite.bchat.util.DisplayBChatThreadItem;
import org.spinsuite.mqtt.connection.MQTTConnection;
import org.spinsuite.sync.content.Invited;
import org.spinsuite.sync.content.SyncMessage;
import org.spinsuite.sync.content.SyncRequest;
import org.spinsuite.util.AttachmentHandler;
import org.spinsuite.util.Env;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 6, 2015, 9:54:42 PM
 *
 */
public class FV_Thread extends Fragment {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/04/2014, 17:42:27
	 */
    public FV_Thread(){
    	
    }
    
    /**
     * 
     * *** Constructor ***
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_ctx
     */
    public FV_Thread(Context p_ctx){
    	m_ctx = p_ctx;
    }
    
    /**	View 						*/
	private View 						m_view 				= null;
	/**	List View					*/
	private ListView					lv_Thread			= null;
	/**	Message						*/
	private EditText					et_Message 			= null;
	/**	Button Send					*/
	private ImageButton					ib_Send				= null;
	/**	Request						*/
	private static SyncRequest 			m_Request			= null;
	/**	Is Active					*/
	private static boolean				m_IsActive			= false;
	/**	Thread Adapter				*/
	private static BChatThreadAdapter	m_ThreadAdapter		= null;
	/**	Reload Data					*/
	private boolean						m_Reload			= true;
	/**	Context						*/
	private Context						m_ctx 				= null;
	/**	Attach Handler				*/
	private AttachmentHandler			m_AttHandler		= null;
	
	/**	Conversation Type Constants	*/
	public static final int				CT_REQUEST			= 0;
	public static final int				CT_CHAT				= 1;
	
	/**	Results						*/
	private static final int 			ACTION_TAKE_FILE	= 3;
	private static final int 			ACTION_TAKE_PHOTO	= 4;
	
	/**	Handler						*/
	public static Handler 				UIHandler;
	
    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable); 
    }
	
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
				if(et_Message.getText() == null
						|| et_Message.getText().toString().trim().length() == 0)
					return;
				//	Send Message
				sendMessage();
			}
		});
		//	Hide Separator
		lv_Thread.setDividerHeight(0);
		lv_Thread.setDivider(null);
		lv_Thread.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		lv_Thread.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		lv_Thread.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				// Capture total checked items
				final int checkedCount = lv_Thread.getCheckedItemCount();
				// Set the CAB title according to total checked items
				mode.setTitle(checkedCount + " " + getString(R.string.BChat_Selected));
				// Calls toggleSelection method from ListViewAdapter Class
				m_ThreadAdapter.toggleSelection(position);
				
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.action_delete:
					SparseBooleanArray selectedItems = m_ThreadAdapter.getSelectedItems();
					StringBuffer inClause = new StringBuffer();
					for (int i = (selectedItems.size() - 1); i >= 0; i--) {
						if (selectedItems.valueAt(i)) {
							DisplayBChatThreadItem selectedItem = m_ThreadAdapter
									.getItem(selectedItems.keyAt(i));
							//	Add Separator
							if(inClause.length() > 0) {
								inClause.append(", ");
							}
							//	Add Value
							inClause.append(selectedItem.getRecord_ID());
							//	Remove Item
							m_ThreadAdapter.remove(selectedItem);
						}
					}
					//	Delete Records in DB
					if(inClause.length() > 0) {
						SPS_BC_Message.deleteMessage(m_ctx, m_Request, 
								"SPS_BC_Message_ID IN(" + inClause.toString() + ")");
					}
					mode.finish();
					return true;
				default:
					return false;
				}
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.getMenuInflater().inflate(R.menu.bc_thread_selected, menu);
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				m_ThreadAdapter.removeSelection();
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
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
		if(m_Request != null
    			&& m_Request.getSPS_BC_Request_ID() == 0) {
			SPS_BC_Request.newOutRequest(getActivity(), m_Request);
		}
		SyncMessage message = new SyncMessage(MQTTConnection.getClient_ID(getActivity()), 
				et_Message.getText().toString(), null, null, 
				m_Request.getSPS_BC_Request_ID(), Env.getAD_User_ID(), Env.getContext("#AD_User_Name"));
		//	Save Message
		BC_OpenMsg.getInstance().addMsg(message);
		//	Clear Data
		et_Message.setText("");
		//	
		m_Reload = true;
		//	Load
		addMsg(new DisplayBChatThreadItem(message.getSPS_BC_Message_ID(), 
				message.getText(), message.getSPS_BC_Request_ID(), 
				message.getAD_User_ID(), message.getUserName(), 
				SPS_BC_Message.TYPE_OUT, 
				SPS_BC_Message.STATUS_CREATED, 
				new Date(System.currentTimeMillis())));
    }
    
    /**
     * Load List
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/04/2014, 21:11:36
     * @return
     * @return boolean
     */
    private boolean loadData() {
    	//	Verify if is reload data
    	if(!m_Reload) {
    		return false;
    	}
    	m_Reload = false;
    	if(m_Request != null
    			&& m_Request.getSPS_BC_Request_ID() == 0) {
    		et_Message.setText(getString(R.string.BChat_Hi) + " " 
    			+ m_Request.getName() + ", " 
    			+ getString(R.string.BChat_NewRequest));
    		m_ThreadAdapter = new BChatThreadAdapter(getActivity(), new ArrayList<DisplayBChatThreadItem>(), m_Request.isGroup());
    		//	
    	} else {
    		//	Get Data
    		m_ThreadAdapter = new BChatThreadAdapter(getActivity(), getData(), (m_Request != null && m_Request.isGroup()));
    	}
    	//	
    	lv_Thread.setAdapter(m_ThreadAdapter);
		lv_Thread.setSelection(m_ThreadAdapter.getCount() - 1);
		//	Change Title
		getActivity().getActionBar().setTitle(m_Request.getName());
		getActivity().getActionBar().setSubtitle(m_Request.getLastMsg());
    	//	Return
        return true;
    }
    
    /**
     * Get Data for Chat
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return ArrayList<DisplayBChatThreadItem>
     */
    private ArrayList<DisplayBChatThreadItem> getData() {
    	//	Create Connection
    	DB conn = DB.loadConnection(getActivity(), DB.READ_ONLY);
    	//	Compile Query
    	conn.compileQuery("SELECT "
    			+ "m.SPS_BC_Message_ID, "
    			+ "m.Text, "
    			+ "m.SPS_BC_Request_ID, "
    			+ "m.AD_User_ID, "
    			+ "u.Name UserName, "
    			+ "m.Type, "
    			+ "m.Status, "
    			+ "(strftime('%s', m.Updated)*1000) Updated "
    			+ "FROM SPS_BC_Message m "
    			+ "INNER JOIN AD_User u ON(u.AD_User_ID = m.AD_User_ID) "
    			+ "WHERE m.SPS_BC_Request_ID = ? "
    			+ "ORDER BY m.Updated");
    	//	Add Parameter
    	conn.addInt(m_Request.getSPS_BC_Request_ID());
    	//	Load Data
    	Cursor rs = conn.querySQL();
		//	Instance Data
		ArrayList<DisplayBChatThreadItem> data = new ArrayList<DisplayBChatThreadItem>();
    	//	Valid Result set
    	if(rs != null 
    			&& rs.moveToFirst()) {
    		int col = 0;
    		//	Loop
    		do {
    			data.add(new DisplayBChatThreadItem(
    					rs.getInt(col++), 
    					rs.getString(col++), 
    					rs.getInt(col++), 
    					rs.getInt(col++), 
    					rs.getString(col++), 
    					rs.getString(col++), 
    					rs.getString(col++), 
    					new Date(rs.getLong(col++))));
    			//	Set Column
    			col = 0;
    		} while(rs.moveToNext());
    	}
    	//	Close Connection
    	DB.closeConnection(conn);
    	//	Return
    	return data;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	m_IsActive = true;
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	m_IsActive = false;
    }
    
    /**
     * Select a Conversation
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_SPS_BC_Request_ID
     * @return void
     */
    public void selectConversation(int p_SPS_BC_Request_ID) {
    	m_Request = SPS_BC_Request.getRequest(m_ctx, p_SPS_BC_Request_ID);
    	//	Set Reload Data
    	m_Reload = true;
    	if(m_view != null
    			&& m_Request != null) {
    		loadData();
    	}
    }
    
    /**
     * Add New Message
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param msg
     * @return void
     */
    public static void addMsg(DisplayBChatThreadItem msg) {
    	m_ThreadAdapter.add(msg);
    	m_ThreadAdapter.notifyDataSetChanged();
    }
    
    /**
     * Verify if is open thread
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_SPS_BC_Request_ID
     * @return
     * @return boolean
     */
    public static boolean isOpened(int p_SPS_BC_Request_ID) {
    	return (m_Request != null 
    			&& m_Request.getSPS_BC_Request_ID() == p_SPS_BC_Request_ID
    			&& m_IsActive);
    }
    
    /**
     * Select a User for request
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_AD_User_ID
     * @param p_Name
     * @return void
     */
    public void requestUser(int p_AD_User_ID, String p_Name) {
    	//	For Request
    	if(p_AD_User_ID != 0
    			&& p_AD_User_ID != -1) {
			int m_SPS_BC_Request_ID = DB.getSQLValue(m_ctx, 
					"SELECT r.SPS_BC_Request_ID FROM SPS_BC_Request r "
					+ "WHERE r.Name = ?", new String[]{p_Name});
			//	
			if(m_SPS_BC_Request_ID != 0
					&& m_SPS_BC_Request_ID != -1) {
				m_Request = SPS_BC_Request.getRequest(m_ctx, m_SPS_BC_Request_ID);
			} else {
				m_Request = new SyncRequest(0, 
						String.valueOf(Env.getAD_User_ID()), 
						SyncRequest.RT_BUSINESS_CHAT, 
						String.valueOf(UUID.randomUUID()), p_Name, false);
				//	Add User to Request
				m_Request.addUser(new Invited(p_AD_User_ID, SPS_BC_Request.STATUS_CREATED));
			}
		}
    	//	Set Reload Data
    	m_Reload = true;
    	if(m_view != null) {
    		loadData();
    	}
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.bc_thread, menu);
		//	Get Item
		MenuItem item = menu.findItem(R.id.action_search);
		//	Search View
		final View searchView = SearchViewCompat.newSearchView(m_ctx);
		if (searchView != null) {
			//	Set Back ground Color
			int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
			EditText searchText = (EditText) searchView.findViewById(id);
			//	Set Parameters
			if(searchText != null)
				searchText.setTextAppearance(m_ctx, R.style.TextSearch);
			//	
			SearchViewCompat.setOnQueryTextListener(searchView,
					new OnQueryTextListenerCompat() {
				@Override
				public boolean onQueryTextChange(String newText) {
					if(m_ThreadAdapter != null) {
						String mFilter = !TextUtils.isEmpty(newText) ? newText : null;
						m_ThreadAdapter.getFilter().filter(mFilter);
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
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
			case R.id.action_attach_photo:
				attachPhoto();
				return true;
			//	Default
			default:
				return super.onOptionsItemSelected(item);
    	}
    }
    
    /**
     * Attach Photo
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return void
     */
    private void attachPhoto() {
    	//	Instance Attachment
    	if(m_AttHandler == null)
    		m_AttHandler = new AttachmentHandler(getActivity());
    	//	
    	//	Delete Temp File
    	File tmpFile = new File(m_AttHandler.getTMPImageName());
    	if(tmpFile.exists()) {
    		if(!tmpFile.delete())
    			tmpFile.deleteOnExit();
    	}
    	//	
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    	intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tmpFile));
	    getActivity().startActivityForResult(intent, ACTION_TAKE_PHOTO);
	}
    
}