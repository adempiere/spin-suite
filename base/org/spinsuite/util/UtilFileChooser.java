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
package org.spinsuite.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Aug 20, 2015, 12:15:35 AM
 *
 */
public class UtilFileChooser {
	
	
	/**
	 * Get Name of File from Uri
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_Uri
	 * @return
	 * @return String
	 */
	public static String getNameFromUri(Context p_Ctx, Uri p_Uri) {
		Cursor cursor = p_Ctx.getContentResolver().query(p_Uri, null, null, null, null);
		//	Display Name
	    String displayName = null;
	    try {
	        if (cursor!=null && cursor.moveToFirst()) {
	            int cidx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
	            if (cidx != -1) {
	                displayName = cursor.getString(cidx);
	            }
	        }
	    } finally {
	        if(cursor!=null)
	            cursor.close();
	    }
	    //	Default Return
	    return displayName;
	}
}