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

import java.util.ArrayList;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Aug 20, 2015, 12:15:35 AM
 *
 */
public class FileUtil {
	
	/**	Constant for Types	*/
	/**	For Key				*/
	public static final int	TYPE_KEY	 	= 0;
	/**	For Document		*/
	public static final int	TYPE_DOCUMENT 	= 1;
	/**	For Image			*/
	public static final int	TYPE_IMAGE 		= 2;
	/**	Valid Extensions	*/
	private static final String[] VALID_EXT = 
		{
		//	For Key
		".key", 
		//	For Documents
		".pdf", ".doc", ".docx", ".odt", ".txt", 
		//	For Image
		".png", ".jpg", ".bmp", ".gif"
		};
	/**
	 * Get Chooser for types
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Type
	 * @return
	 * @return Intent
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static Intent getChooser(int p_Type) {
		//	Instance Mime
		MimeTypeMap mtm = MimeTypeMap.getSingleton();
		//	Instance intent
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("application/x-pem-file");
		//	Mime Type
        ArrayList<String> mimeTypes = new ArrayList<String>();
		//	
		switch (p_Type) {
			case TYPE_KEY:
				mimeTypes.add("application/x-pem-file");
		        mimeTypes.add("application/pkcs8");
		        mimeTypes.add("application/octet-stream");
		        mimeTypes.add("application/x-iwork-keynote-sffkey");
		        //	Add Extension
		        mimeTypes.add(mtm.getMimeTypeFromExtension("key"));
				break;
			case TYPE_DOCUMENT:
				//	.pdf
				mimeTypes.add("application/pdf");
				//	.doc
		        mimeTypes.add("application/msword");
		        //	.docx
		        mimeTypes.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		        //	.odt
		        mimeTypes.add("application/vnd.oasis.opendocument.text");
		        //	.txt
		        mimeTypes.add("text/plain");
				break;
			case TYPE_IMAGE:
				//	.png
				mimeTypes.add("image/png");
				//	.jpg
		        mimeTypes.add("image/jpeg");
		        //	.bmp
		        mimeTypes.add("image/bmp");
		        //	.gif
		        mimeTypes.add("image/gif");
				break;
			default:
				return null;
		}
		//	Add Mime Types
		intent.putExtra(Intent.EXTRA_MIME_TYPES, 
        		mimeTypes.toArray(new String[mimeTypes.size()]));
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//	Default
		return intent;
	}
	
	/**
	 * Get Mime Type from Extension
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ext
	 * @return
	 * @return String
	 */
	public static String getMimeTypeFromExtension(String p_Ext) {
		//	Valid Null
		if(p_Ext == null)
			return "";
		//	Change Extension
		//	Instance Mime
		MimeTypeMap mtm = MimeTypeMap.getSingleton();
		return mtm.getMimeTypeFromExtension(p_Ext.replaceAll(".", ""));
	}
	
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
	
	/**
	 * Verify if is valid extension file
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_FileName
	 * @return
	 * @return boolean
	 */
	public static boolean isValidExt(String p_FileName) {
		//	Valid null
		if(p_FileName == null
				|| p_FileName.length() == 0)
			return false;
		//	Iterate
		for(String ext : VALID_EXT) {
			if(p_FileName.lastIndexOf(ext) != -1) {
				return true;
			}
		}
		//	Default not valid
		return false;
	}
	
	/**
	 * Get file Extension
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_FileName
	 * @return
	 * @return String
	 */
	public static String getFileExtension(String p_FileName) {
		//	Valid null
		if(p_FileName == null
				|| p_FileName.length() == 0)
			return "";
		if(p_FileName.lastIndexOf(".") != -1) {
			return p_FileName.substring(p_FileName.lastIndexOf("."));
		}
		//	Default Return
		return "";
	}
}