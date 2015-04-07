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
import org.spinsuite.bchat.adapters.BChatThreadListAdapter;
import org.spinsuite.bchat.util.DisplayBChatThreadListItem;
import org.spinsuite.interfaces.I_FragmentSelect;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
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
    
    /**	Call Back					*/
    private I_FragmentSelect			m_Callback 	= null;
    /**	Adapter						*/
    private BChatThreadListAdapter		m_Adapter 	= null;
    
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    					null, 
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
            m_Callback = (I_FragmentSelect) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement I_FragmentSelect");
        }
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	//	
    	onItemSelected(position);
    	//	Change on List View
    	getListView().setItemChecked(position, true);
    }

    @Override
    public void onItemSelected(int p_Record_ID) {
    	m_Callback.onItemSelected(p_Record_ID);
    }
}