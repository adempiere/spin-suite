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

import java.util.logging.Level;

import org.spinsuite.adapters.MenuAdapter;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.login.Login;
import org.spinsuite.model.MSession;
import org.spinsuite.util.ActivityParameter;
import org.spinsuite.util.DisplayMenuItem;
import org.spinsuite.util.DisplayRecordItem;
import org.spinsuite.util.Env;
import org.spinsuite.util.LoadActionMenu;
import org.spinsuite.util.LogM;
import org.spinsuite.view.lookup.LookupMenu;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
/**
* @contributor Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com
*  	<li>Add Support to Log for Mobile
*  	@see https://adempiere.atlassian.net/browse/SPIN-6
**/
public class LV_Menu extends Activity {
	
	/**	List				*/
	private ListView 			menu;
	/**	Database Connection	*/
	private DB 					conn = null;
	/**	Parameter			*/
	private ActivityParameter 	param = null;
	/**	Activity No			*/
	protected int 				m_ActivityNo = 0;
	/**	Current Bundle		*/
	private Bundle 				currentOptionBundle = null;
	/**	Current Option Menu	*/
	private DisplayMenuItem 	currentMenuItem = null;
	/**	Lookup Menu			*/
	private LookupMenu 			lookupMenu = null;
	/**	Load Action Menu	*/
	private LoadActionMenu		loadActionMenu = null;;
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_menu);
		
		Bundle bundle = getIntent().getExtras();		
    	if(bundle != null){
    		param = (ActivityParameter)bundle.getParcelable("Param");
		}
    	if(param == null)
    		param = new ActivityParameter();
		//	
    	m_ActivityNo = param.getSPS_Menu_ID();
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //	new Menu
        lookupMenu = new LookupMenu(getApplicationContext(), LookupMenu.MAIN_MENU, conn);
        //	
        menu = (ListView) findViewById(R.id.lv_Menu);
        //	Load Data
        try {
        	load();
        } catch(Exception e) {
        	LogM.log(this, getClass(), Level.SEVERE, "Error:" + e.getLocalizedMessage());
        	finish();
        }
        //	Action Menu Loader
        loadActionMenu = new LoadActionMenu(this, false);
        //	
        menu.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				DisplayMenuItem item = (DisplayMenuItem) adapter.getItemAtPosition(position);
				param.setParent_ID(item.getSPS_Menu_ID());
				param.setActivityMenu_ID(item.getActivityMenu_ID());
				//	Load from Action
				currentOptionBundle = loadActionMenu.loadAction(item, param);
				currentMenuItem = item;
			}
        });
        
        //Carlos Parada Add Support to Log for Mobile
        MSession.get (this, true);
        //End Carlos Parada
       
	}
	
	/**
	 * Load Data
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 04/02/2014, 17:39:01
	 * @return
	 * @return boolean
	 */
	private boolean load(){
		//	Load Children
		lookupMenu.loadChildren(param.getParent_ID());
		//	
		MenuAdapter mi_adapter = new MenuAdapter(this, R.layout.i_image_text, true, lookupMenu.getData());
		mi_adapter.setDropDownViewResource(R.layout.i_image_text);
		menu.setAdapter(mi_adapter);
		//	
		return true;
	}
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == Activity.RESULT_OK) {
	    	if(data != null){
	    		Bundle bundle = data.getExtras();
	    		DisplayRecordItem item = (DisplayRecordItem) bundle.getParcelable("Record");
	    		//	if a record is not null
	    		if(item != null){
	    			int[] keys = item.getKeys();
		    		currentOptionBundle.putIntArray("Record_ID", keys);
		    		loadActionMenu.loadActivityWithAction(currentMenuItem, currentOptionBundle);
	    		}
	    	}
    	}
    }
	
	@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	        case android.R.id.home:
	    		//	Auto Login
	    		Env.setAutoLoginComfirmed(this, false);
	        	NavUtils.navigateUpTo(this, new Intent(this, Login.class));
	        return true;
	        default:
	        	return super.onOptionsItemSelected(item);
	     }
	}	
}
