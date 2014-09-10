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
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.process;

import java.util.Map;

import org.spinsuite.util.Env;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Reset Cache
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class CacheReset extends StdProcess {

	@Override
	protected void prepare() {
		//	
	}
	
	@Override
	protected String doIt() throws Exception {
		//	Get Preferences
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getCtx());
		//	Get All Entries
		Map<String, ?> allEntries = preferences.getAll();
		//	Delete
		int deleted = 0;
		for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
			String key = entry.getKey();
			if(key == null
					|| key.startsWith("#"))
				continue;
			//	
			Env.removeContext(getCtx(), key);
			//	Log
			addLog("@Value@ = [" + key + "] @Deleted@");
			//	Count
			deleted++;
		}
		//	Default
		return "@Deleted@ = " + deleted;
	}

}
