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

import java.util.ArrayList;

import org.spinsuite.adapters.ImageTextAdapter;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_PR_FragmentSelect;
import org.spinsuite.util.DisplayImageTextItem;
import org.spinsuite.util.Env;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class FV_IndexPreference 
			extends ListFragment implements I_PR_FragmentSelect {
    
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/04/2014, 17:42:27
	 */
    public FV_IndexPreference(){
    	
    }
    
    public FV_IndexPreference(Context p_ctx){
    	m_ctx = p_ctx;
    }
    
    /**	Fragment Listener Call Back	*/
	private I_PR_FragmentSelect 	m_Callback 	= null;
	/**	Is Load Ok					*/
	private boolean					m_IsLoadOk	= false;
	/**	Context						*/
	private Context					m_ctx 		= null;
    
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	//	Load Ok
    	m_IsLoadOk = true;
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    /**
     * Load Data for Preferences
     * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
     * @return
     * @return boolean
     */
    private boolean loadData() {
    	ArrayList<DisplayImageTextItem> options = new ArrayList<DisplayImageTextItem>();
    	//	For Images
    	Bitmap generalImg = BitmapFactory.decodeResource(getResources(), 
    			Env.getResourceID(m_ctx, R.attr.ic_pr_general));
    	Bitmap wsImg = BitmapFactory.decodeResource(getResources(), 
    			Env.getResourceID(m_ctx, R.attr.ic_pr_ws));
    	Bitmap mqttImg = BitmapFactory.decodeResource(getResources(), 
    			Env.getResourceID(m_ctx, R.attr.ic_pr_mqtt));
    	//	Populate
    	options.add(new DisplayImageTextItem(I_PR_FragmentSelect.GENERAL, 
    			getString(R.string.PR_General), getString(R.string.PR_D_General), generalImg));
    	options.add(new DisplayImageTextItem(I_PR_FragmentSelect.WEB_SERVICES, 
    			getString(R.string.PR_WS), getString(R.string.PR_D_WS), wsImg));
    	options.add(new DisplayImageTextItem(I_PR_FragmentSelect.MQTT, 
    			getString(R.string.PR_MQTT), getString(R.string.PR_D_MQTT), mqttImg));
    	ImageTextAdapter adapter = new ImageTextAdapter(m_ctx, options);
    	setListAdapter(adapter);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
        //	Choice Mode
        if (getFragmentManager()
        		.findFragmentByTag(T_DynamicTabDetail.INDEX_FRAGMENT) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            m_Callback = (I_PR_FragmentSelect) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement I_PR_FragmentSelect");
        }
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //	Change on List View
    	DisplayImageTextItem option = (DisplayImageTextItem) getListAdapter().getItem(position);
    	onItemSelected(option.getRecord_ID());
    	getListView().setItemChecked(position, true);
    }

	@Override
	public void onItemSelected(int p_Item_ID) {
		if(!m_IsLoadOk) {
			return;
		}
		//	Is Load
		m_Callback.onItemSelected(p_Item_ID);
	}
}