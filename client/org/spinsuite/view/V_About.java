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

import org.spinsuite.base.R;
import org.spinsuite.util.LogM;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Jul 24, 2015, 9:42:06 PM
 *
 */
public class V_About extends Activity {

	/**	Package Manager					*/
	private PackageManager 		manager;
	/**	Package Information				*/
    private PackageInfo 		info;
    /**	Application Version Name		*/
    private TextView 			tv_AppVersionName;
    /**	Application Version Code		*/
    private TextView 			tv_AppVersionCode;
    /**	Application Version Permissions	*/
    private TextView 			tv_AppPermissions;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_about);
    	//	Action Bar
    	getActionBar().setDisplayHomeAsUpEnabled(true);
    	getActionBar().setHomeButtonEnabled(true);
    	getActionBar().setTitle(R.string.app_name);
    	getActionBar().setSubtitle(R.string.About_Credits);
    	//	Inflate Values
    	tv_AppVersionName = (TextView) findViewById(R.id.tv_AppVersionName);
	    tv_AppVersionCode = (TextView) findViewById(R.id.tv_AppVersionCode);
	    tv_AppPermissions = (TextView) findViewById(R.id.tv_AppPermissions);
	}
	
	@Override
	public void onResume() {
        super.onResume();
        try {
        	manager = getPackageManager();
        	info = manager.getPackageInfo(getPackageName(), 0);
        	//	Set Values
        	tv_AppVersionName.setText(getString(R.string.app_version_name) + ": " + info.versionName);
        	tv_AppVersionCode.setText(getString(R.string.app_version_code) + ": " + String.valueOf(info.versionCode));
        	String permissions = "";
        	if(info.permissions != null) {
        		
        		for(int i = 0; i < info.permissions.length; i++){
        			permissions += "\n|" + info.permissions[i];
        		}
        		permissions += "|";
        		//	
        		tv_AppPermissions.setText(getString(R.string.app_permissions) + ": " + permissions);
        	} else {
        		tv_AppPermissions.setText(getString(R.string.None));
        	}
        	//	
        } catch (Exception e) {
        	LogM.log(getApplicationContext(), getClass(), Level.SEVERE, e.getLocalizedMessage());
        }
    }
}