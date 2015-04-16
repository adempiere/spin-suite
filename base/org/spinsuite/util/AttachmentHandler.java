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
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class AttachmentHandler {

	
	/**
	 * Default
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 17:43:48
	 * @param ctx
	 * @param m_Table_ID
	 * @param m_Record_ID
	 */
	public AttachmentHandler(Context ctx, int m_Table_ID, int m_Record_ID) {
		this.m_ctx = ctx;
		this.m_Table_ID = m_Table_ID;
		this.m_Record_ID = m_Record_ID;
		m_AD_Client_ID = Env.getAD_Client_ID();
		m_tmpDirectory = Env.getTmp_DirectoryPathName();
		m_attDirectory = Env.getAtt_DirectoryPathName();
	}
	
	/**
	 * Just Table
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 17:53:00
	 * @param ctx
	 * @param m_Table_ID
	 */
	public AttachmentHandler(Context ctx, int m_Table_ID) {
		this(ctx, m_Table_ID, 0);
	}
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 17:44:17
	 * @param ctx
	 */
	public AttachmentHandler(Context ctx) {
		this(ctx, 0, 0);
	}
	
	/**	Context				*/
	private Context 	m_ctx 			= null;
	/**	Client				*/
	private int 		m_AD_Client_ID 	= 0;
	/**	Table Identifier	*/
	private int 		m_Table_ID		= 0;
	/**	Record Identifier	*/
	private int 		m_Record_ID		= 0;
	/**	Constants			*/
	public static final String 	JPEG_FILE_SUFFIX 	= ".jpg";
	public static final String 	DEFAULT_IMG_NAME 	= "defaultIMG" + JPEG_FILE_SUFFIX;
	public static String 		m_tmpDirectory		= null;
	public static String 		m_attDirectory		= null;
	/**	Images				*/
	public static final int 	IMG_TARGET_W		= 1280;//640;
	public static final int 	IMG_TARGET_H		= 960;//480;
	
	
	/**
	 * Set Record Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 17:58:33
	 * @param m_Record_ID
	 * @return void
	 */
	public void setRecord_ID(int m_Record_ID) {
		this.m_Record_ID = m_Record_ID;
	}
	
	/**
	 * Get Tmp Directory for Files
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 18:01:44
	 * @return
	 * @return String
	 */
	public String getTmpDirectory() {
		return m_tmpDirectory;
	}
	
	/**
	 * Get Attachment Directory
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 20:41:49
	 * @return
	 * @return String
	 */
	public String getAttDirectory() {
		return m_attDirectory;
	}
	
	/**
	 * Get Uri Attachment Directory for Record
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 22:17:52
	 * @return
	 * @return Uri
	 */
	public Uri getUriAttDirectoryRecord() {
		return Uri.fromFile(new File(getAttDirectory() 
				+ File.separator 
				+ getAttachmentPathSnippet()));
	}
	
	/**
	 * Get Attachment Directory for Record
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/11/2014, 10:55:23
	 * @return
	 * @return String
	 */
	public String getAttDirectoryRecord() {
		return getAttDirectory() 
				+ File.separator 
				+ getAttachmentPathSnippet();
	}
	
	/**
	 * Get Tmp Image Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 20:11:07
	 * @return
	 * @return String
	 */
	public String getTMPImageName() {
		return getTmpDirectory() 
				+ File.separator 
				+ "TMP" + JPEG_FILE_SUFFIX;
	}
	
	/**
	 * Returns a path snippet, containing client, table and record id.
	 * @return String
	 */
	private String getAttachmentPathSnippet() {
		return m_AD_Client_ID + File.separator + 
				m_Table_ID + File.separator + m_Record_ID;
	}
	
	/**
	 * Process Image Attachment from Camera
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 21:05:47
	 * @return
	 * @return boolean
	 */
	public boolean processImgAttach() {
		return processImgAttach(null);
	}
	
	/**
	 * Process File Attachment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/11/2014, 20:43:37
	 * @param origFileName
	 * @param fileName
	 * @return
	 * @return boolean
	 */
	public boolean processFileAttach(String origFileName) {
		File origFile = new File(origFileName);
        if(!origFile.exists())
        	return false;
        //	
        final File destFolder = new File(getAttDirectory() + File.separator + getAttachmentPathSnippet());
		if(!destFolder.exists()) {
			if(!destFolder.mkdirs()) {
				LogM.log(m_ctx, getClass(), Level.SEVERE, "unable to create folder: " + destFolder.getPath());
			}
		}
		//	
		final File destFile = new File(getAttDirectory() + File.separator
				+ getAttachmentPathSnippet() + File.separator + origFile.getName());
		if(destFile.exists()) {
			if(!destFile.delete()) {
				destFile.deleteOnExit();
			}
		}
		//	Copy
		try {
			InputStream in = new FileInputStream(origFile);
			OutputStream out = new FileOutputStream(destFile);
			byte[] buf = new byte[1024];
	        int len;
	        //	Copy
	        while ((len = in.read(buf)) > 0) {
	            out.write(buf, 0, len);
	        }
	        //	Close
	        in.close();
	        out.close();
	        //	
	        return true;
		} catch (FileNotFoundException e) {
			LogM.log(m_ctx, getClass(), Level.SEVERE, 
					"unable to copy file: " + origFile.getPath() + " -> " + destFile.getPath(), e);
		} catch (IOException e) {
			LogM.log(m_ctx, getClass(), Level.SEVERE, 
					"unable to copy file: " + origFile.getPath() + " -> " + destFile.getPath(), e);
		}
        //	Finish
        return false;
	}
	
	/**
	 * Process Attach
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param fileName
	 * @return
	 * @return boolean
	 */
	public boolean processImgAttach(String fileName) {
		return processImgAttach(null, fileName);
	}
	
	/**
	 * Save Image to Attachment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 20:50:39
	 * @param p_DestFolder
	 * @param fileName
	 * @return
	 * @return boolean
	 */
	public boolean processImgAttach(String p_DestFolder, String fileName) {
    	File tmpFile = new File(getTMPImageName());
        if(!tmpFile.exists())
        	return false;
        //	
		Bitmap mImage = getBitmapFromFile(getTMPImageName(), IMG_TARGET_W, IMG_TARGET_H);
		//	
		if(p_DestFolder == null) {
			p_DestFolder = getAttDirectory() + File.separator + getAttachmentPathSnippet();
		}
		if(mImage != null) {
			final File destFolder = new File(p_DestFolder);
			if(!destFolder.exists()) {
				if(!destFolder.mkdirs()) {
					LogM.log(m_ctx, getClass(), Level.SEVERE, "unable to create folder: " + destFolder.getPath());
				}
			}
			//	
			if(fileName == null)
				fileName = DEFAULT_IMG_NAME;
			else if(!fileName.contains(JPEG_FILE_SUFFIX))
				fileName += JPEG_FILE_SUFFIX;
			//	
			final File destFile = new File(p_DestFolder + File.separator + fileName);
			if(destFile.exists()) {
				if(!destFile.delete()) {
					destFile.deleteOnExit();
				}
			}
			//	Write File
			try {
				FileOutputStream outStream = new FileOutputStream(destFile);
				mImage.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
				outStream.flush();
				outStream.close();
			} catch (Exception e) {
				LogM.log(m_ctx, getClass(), Level.SEVERE, "unable to create File: " + destFile.getPath(), e);
			}
			//	
			return true;
		}
		return false;
    }
	
	/**
	 * Get Bitmap from File
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/11/2014, 14:24:16
	 * @param fileName
	 * @param weight
	 * @param height
	 * @return
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromFile(String fileName, int weight, int height) {
		//	Valid Size
		if(weight <= 0)
			weight = 1;
		//	
		if(height <= 0)
			height = 1;
		// Get the size of the image
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(fileName, options);
		int photoW = options.outWidth;
		int photoH = options.outHeight;
		
		//	Figure out which way needs to be reduced less
		int scaleFactor = 1;
		scaleFactor = Math.min(photoW/weight, photoH/height);	

		//	Set bitmap options to scale the image decode target
		options.inJustDecodeBounds = false;
		options.inSampleSize = scaleFactor;
		options.inPurgeable = true;

		//	Decode the JPEG file into a Bitmap
		return BitmapFactory.decodeFile(fileName, options);
	}
	
	/**
	 * Get BitMap from File without size
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/11/2014, 14:25:21
	 * @param fileName
	 * @return
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromFile(String fileName) {
		return getBitmapFromFile(fileName, 0, 0);
	}
	
	/**
	 * Verify if has Attachment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/11/2014, 8:51:48
	 * @return
	 * @return boolean
	 */
	public boolean hasAttachment() {
		File directory = new File(getAttDirectoryRecord());
		String [] m_FileName = directory.list();
		//	Verify if has files
		return m_FileName != null && m_FileName.length > 0;
	}
}
