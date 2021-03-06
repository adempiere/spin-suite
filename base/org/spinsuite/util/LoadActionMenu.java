/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                                           *
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
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.util;

import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.model.MForm;
import org.spinsuite.view.LV_Menu;
import org.spinsuite.view.LV_Search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class LoadActionMenu {

	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/03/2014, 15:39:22
	 * @param activity
	 * @param isFromDrawer
	 * @param conn
	 */
	public LoadActionMenu(Activity activity, boolean isFromDrawer, DB conn){
		this.activity = activity;
		this.isFromDrawer = isFromDrawer;
		this.conn = conn;
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 19/03/2014, 15:39:27
	 * @param activity
	 * @param isFromDrawer
	 */
	public LoadActionMenu(Activity activity, boolean isFromDrawer){
		this(activity, isFromDrawer, null);
	}
	
	/**	Activity				*/
	private Activity 	activity;
	/**	Connection				*/
	private DB			conn;
	/**	Is From Drawer			*/
	private boolean		isFromDrawer = false;
	
	/**
	 * 
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/03/2014, 11:16:13
	 * @param item
	 * @param param
	 * @return
	 * @return Bundle
	 */
	public Bundle loadAction(DisplayMenuItem item, ActivityParameter param){
		//	
		Bundle bundle = new Bundle();
		//	Intent Activity
		Intent intent = null;
		if(item.isSummary()){
			bundle.putParcelable("Param", param);
			intent = new Intent(activity, LV_Menu.class);
			intent.putExtras(bundle);
			//	Show Activity
			activity.startActivityForResult(intent, 0);
		} else {
			//	Load Parameter
			ActivityParameter paramAct = new ActivityParameter(item);
			bundle.putParcelable("Param", paramAct);
			//	
			if(item.getDeploymentType() == null 
					|| item.getDeploymentType().equals(DisplayMenuItem.DEPLOYMENTTYPE_DirectForm)) {
				//	Load Activity
				loadActivityWithAction(item, bundle);
			} else if(item.getDeploymentType().equals(DisplayMenuItem.DEPLOYMENTTYPE_List)
					|| item.getDeploymentType().equals(DisplayMenuItem.DEPLOYMENTTYPE_ListWithQuickAction)) {
				//	Start Search
				bundle.putInt("SFA_Table_ID", item.getSFA_Table_ID());
            	intent = new Intent(activity, LV_Search.class);
    			intent.putExtras(bundle);
    			//	Start with result
    			activity.startActivityForResult(intent, 0);
			} else if(item.getDeploymentType().equals(DisplayMenuItem.DEPLOYMENTTYPE_MenuWithQuickAction)) {
				
			}
		}
		//	Return
		return bundle;
	}
	
	/**
	 * Load Activity with action
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 04/02/2014, 22:52:55
	 * @param item
	 * @param bundle
	 * @return
	 * @return boolean
	 */
	public boolean loadActivityWithAction(DisplayMenuItem item, Bundle bundle){
		boolean ok = false;
		boolean handleConnection = false;
		//	
		if(conn == null){
			conn = new DB(activity);
			handleConnection = true;
		}
		//	Load Action
		DB.loadConnection(conn, DB.READ_ONLY);
		
		if(item.getAction().equals(DisplayMenuItem.ACTION_Form)) {
			MForm form = new MForm(activity, item.getAD_Form_ID(), conn);
			ok = loadDynamicClass(form.getClassname(), bundle);
		} else if(item.getAction().equals(DisplayMenuItem.ACTION_Window)){
			ok = loadDynamicClass("org.spinsuite.view.TV_DynamicActivity", bundle);
		} else if(item.getAction().equals(DisplayMenuItem.ACTION_Process)
				|| item.getAction().equals(DisplayMenuItem.ACTION_Report)){
			ok = loadDynamicClass("org.spinsuite.view.V_Process", bundle);
		}
		//	Show Message
		if(!ok)
			Msg.alertMsg(activity, activity.getResources().getString(R.string.msg_LoadError), 
					activity.getResources().getString(R.string.msg_ClassNotFound));
		//	Close Connection
		if(handleConnection)
			DB.closeConnection(conn);
		return ok;
	}
	
	/**
	 * Load Dynamic Class Activity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/02/2014, 23:28:53
	 * @param className
	 * @param bundle
	 * @return
	 * @return boolean
	 */
	private boolean loadDynamicClass(String className, Bundle bundle){
		boolean ok = false;
		Class<?> clazz;
		try {
			clazz = Class.forName(className);
			Intent intent = new Intent(activity, clazz);
			intent.putExtras(bundle);
			//	Start
			if(!isFromDrawer)
				activity.startActivity(intent);
			else
				activity.startActivityForResult(intent, 0);
			ok = true;
		} catch (ClassNotFoundException e) {
			LogM.log(activity, getClass(), Level.SEVERE, "Error:", e);
		}
		return ok;
	}
}
