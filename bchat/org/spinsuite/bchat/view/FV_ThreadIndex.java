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
import java.util.Date;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.bchat.adapters.BChatThreadListAdapter;
import org.spinsuite.bchat.util.DisplayBChatThreadListItem;
import org.spinsuite.interfaces.I_BC_FragmentSelect;
import org.spinsuite.interfaces.I_FragmentSelect;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.support.v4.widget.SearchViewCompat.OnQueryTextListenerCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 6, 2015, 9:54:42 PM
 *
 */
public class FV_ThreadIndex extends ListFragment 
				implements I_FragmentSelect {

	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/04/2014, 17:42:27
	 */
    public FV_ThreadIndex(){
    	
    }
    
    /**
     * With Context
     * *** Constructor ***
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @param p_ctx
     */
    public FV_ThreadIndex(Context p_ctx){
    	m_ctx = p_ctx;
    }
    
    /**	Call Back					*/
    private I_BC_FragmentSelect			m_Callback 	= null;
    /**	Adapter						*/
    private BChatThreadListAdapter		m_Adapter 	= null;
    /**	Context						*/
	private Context						m_ctx 		= null;
    
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    
    /**
     * Load List
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/04/2014, 21:11:36
     * @return
     * @return boolean
     */
    public boolean loadData() {
    	//	Create Connection
    	DB conn = DB.loadConnection(getActivity(), DB.READ_ONLY);
    	//	Compile Query
    	Cursor rs = conn.querySQL("SELECT "
    			+ "rq.SPS_BC_Request_ID, "
    			+ "COALESCE(rq.Name, us.Name) Name, "
    			+ "rq.LastMsg, "
    			+ "(strftime('%s', rq.Updated)*1000) Updated, "
    			+ "rq.Type "
    			+ "FROM SPS_BC_Request rq "
    			+ "INNER JOIN AD_User us ON(us.AD_User_ID = rq.AD_User_ID) "
    			+ "WHERE rq.IsActive = 'Y' "
    			+ "ORDER BY rq.Updated DESC", null);
		//	Instance Data
		ArrayList<DisplayBChatThreadListItem> data = new ArrayList<DisplayBChatThreadListItem>();
    	//	Valid Result set
    	if(rs.moveToFirst()) {
    		int col = 0;
    		//	Loop
    		do {
    			data.add(new DisplayBChatThreadListItem(
    					rs.getInt(col++), 
    					rs.getString(col++), 
    					rs.getString(col++), 
    					null, 
    					new Date(rs.getLong(col++)), 
    					rs.getString(col++)));
    			//	Set Column
    			col = 0;
    		} while(rs.moveToNext());
    	}
    	//	Close Connection
    	DB.closeConnection(conn);
    	m_Adapter = new BChatThreadListAdapter(getActivity(), data);
    	//	Set Adapter List
    	setListAdapter(m_Adapter);
    	//	Return
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
        //	Choice Mode
        if (getFragmentManager()
        		.findFragmentByTag(V_BChat.INDEX_FRAGMENT) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            m_Callback = (I_BC_FragmentSelect) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement I_BC_FragmentSelect");
        }
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	//	
    	DisplayBChatThreadListItem item = m_Adapter.getItem(position);
    	onItemSelected(item.getRecord_ID());
    	//	Change on List View
    	getListView().setItemChecked(position, true);
    }

    @Override
    public void onItemSelected(int p_Record_ID) {
    	m_Callback.onItemSelected(p_Record_ID, null, V_BChat.TYPE_SELECT_CONVERSATION);
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
					if(m_Adapter != null) {
						String mFilter = !TextUtils.isEmpty(newText) ? newText : null;
						m_Adapter.getFilter().filter(mFilter);
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
    
}