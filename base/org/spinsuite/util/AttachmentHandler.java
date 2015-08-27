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
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.logging.Level;

import org.spinsuite.base.R;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;

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
	public static final int 	IMG_TARGET_W		= 1024;//1280;//640;
	public static final int 	IMG_TARGET_H		= 768;//960;//480;
	public static final int 	IMG_MAX_Q			= 100;
	public static final int 	IMG_STD_Q			= 60;
	
	
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
	 * Process a Fille like Attachment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param origFileName
	 * @return
	 * @return boolean
	 */
	public boolean processFileAttach(String origFileName) {
        try {
        	File origFile = new File(origFileName);
            if(!origFile.exists())
            	return false;
            //	Do it
			return processFileAttach(new FileInputStream(origFile), origFile.getName());
		} catch (FileNotFoundException e) {
			LogM.log(m_ctx, getClass(), Level.SEVERE, 
					"unable to copy file: " + origFileName, e);
		}
        //	Default Return
        return false;
	}
	
	/**
	 * Process File Attachment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/11/2014, 20:43:37
	 * @param origFileName
	 * @param fileName
	 * @return
	 * @return boolean
	 */
	public boolean processFileAttach(InputStream p_File, String p_FileName) {
		//	Valid Null
		if(p_File == null)
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
				+ getAttachmentPathSnippet() + File.separator + p_FileName);
		if(destFile.exists()) {
			if(!destFile.delete()) {
				destFile.deleteOnExit();
			}
		}
		//	Copy
		try {
			OutputStream out = new FileOutputStream(destFile);
			byte[] buf = new byte[1024];
	        int len;
	        //	Copy
	        while ((len = p_File.read(buf)) > 0) {
	            out.write(buf, 0, len);
	        }
	        //	Close
	        p_File.close();
	        out.close();
	        //	
	        return true;
		} catch (FileNotFoundException e) {
			LogM.log(m_ctx, getClass(), Level.SEVERE, 
					"unable to copy file: " + p_FileName + " -> " + destFile.getPath(), e);
		} catch (IOException e) {
			LogM.log(m_ctx, getClass(), Level.SEVERE, 
					"unable to copy file: " + p_FileName + " -> " + destFile.getPath(), e);
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
		return processImgAttach(null, fileName, IMG_MAX_Q);
	}
	
	/**
	 * Save Bitmap without Quality
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Image
	 * @param p_DestFolder
	 * @param fileName
	 * @return
	 * @return boolean
	 */
	public boolean saveImageBitmap(Bitmap p_Image, String p_DestFolder, String fileName) {
		return saveImageBitmap(p_Image, p_DestFolder, fileName, IMG_MAX_Q);
	}
	
	/**
	 * Save Bitmap
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Image
	 * @param p_DestFolder
	 * @param fileName
	 * @param quality
	 * @return
	 * @return boolean
	 */
	public boolean saveImageBitmap(Bitmap p_Image, String p_DestFolder, String fileName, int quality) {
		//	
		if(p_DestFolder == null) {
			p_DestFolder = getAttDirectory() + File.separator + getAttachmentPathSnippet();
		}
		//	
		if(fileName == null)
			fileName = DEFAULT_IMG_NAME;
		else if(!fileName.contains(JPEG_FILE_SUFFIX))
			fileName += JPEG_FILE_SUFFIX;
		//	
		if(p_Image != null) {
			final File destFolder = new File(p_DestFolder);
			if(!destFolder.exists()) {
				if(!destFolder.mkdirs()) {
					LogM.log(m_ctx, getClass(), Level.SEVERE, "unable to create folder: " + destFolder.getPath());
				}
			}
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
				p_Image.compress(Bitmap.CompressFormat.JPEG, quality, outStream);
				outStream.flush();
				outStream.close();
			} catch (Exception e) {
				LogM.log(m_ctx, getClass(), Level.SEVERE, "unable to create File: " + destFile.getPath(), e);
			}
			//	
			return true;
		}
		//	Default
		return false;
	}
	
	/**
	 * Save Image to Attachment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 20:50:39
	 * @param p_DestFolder
	 * @param fileName
	 * @param quality
	 * @return
	 * @return boolean
	 */
	public boolean processImgAttach(String p_DestFolder, String fileName, int quality) {
    	File tmpFile = new File(getTMPImageName());
        if(!tmpFile.exists())
        	return false;
        //	
		Bitmap mImage = getBitmapFromFile(getTMPImageName(), IMG_TARGET_W, IMG_TARGET_H);
		return saveImageBitmap(mImage, p_DestFolder, fileName, quality);
    }
	
	/**
	 * Process File
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_FromFileName
	 * @param p_DestFolder
	 * @param p_FileName
	 * @return true
	 */
	public boolean processFile(String p_FromFileName, String p_DestFolder, String p_FileName) {
		if(p_FileName == null
				|| p_FromFileName == null) {
			return false;
		}
		//	
		if(p_DestFolder == null) {
			p_DestFolder = getAttDirectory() + File.separator + getAttachmentPathSnippet();
		}
		//	
		String extension = getExtension(p_FromFileName);
		//	
		File destFolder = new File(p_DestFolder);
		if(!destFolder.exists()) {
			if(!destFolder.mkdirs()) {
				LogM.log(m_ctx, getClass(), Level.SEVERE, "unable to create folder: " + destFolder.getPath());
			}
		}
		//	Copy
		return copyFile(p_FromFileName, p_DestFolder + File.separator + p_FileName + extension);
	}
	
	/**
	 * Get Extension
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_FileName
	 * @return
	 * @return String
	 */
	public String getExtension(String p_FileName) {
		if(p_FileName == null) {
			return null;
		}
		//	Get Extension
		int i = p_FileName.indexOf('.');
		String extension = "";
		if (i >= 0) {
		    extension = p_FileName.substring(i+1);
		}
		//	
		return extension;
	}
	
	/**
	 * Get Display Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param uri
	 * @return
	 * @return String
	 */
	public String getDisplayName(Uri uri) {
		Cursor cursor = m_ctx.getContentResolver()
	            .query(uri, null, null, null, null, null);
		//	
	    try {
	    	if (cursor != null 
	    			&& cursor.moveToFirst()) {
	    		return cursor.getString(
	                    cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
	        }
	    } finally {
	    	if(cursor != null) {
	    		cursor.close();
	    	}
	    }
	    //	Default
	    return null;
	}
	
	/**
	 * Get BitMap from Uri
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param uri
	 * @return
	 * @throws IOException
	 * @return Bitmap
	 */
	public Bitmap getBitmapFromUri(Uri uri) {
		if(uri == null) {
			return null;
		}
		//	
		ParcelFileDescriptor parcelFileDescriptor = null;
		Bitmap image = null;
		try {
			parcelFileDescriptor =
		            m_ctx.getContentResolver().openFileDescriptor(uri, "r");
			FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
		    image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
		    parcelFileDescriptor.close();
		} catch (Exception e) {
			LogM.log(m_ctx, getClass(), Level.SEVERE, "getBitmapFromUri Error", e);
		}
	    return image;
	}
	
	/**
	 * Copy File
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_FromFileName
	 * @param p_ToFileName
	 * @return boolean
	 */
	public boolean copyFile(String p_FromFileName, String p_ToFileName) {
		if(p_FromFileName == null
				|| p_ToFileName == null) {
			return false;
		}
		//	
		File fromFile = new File(p_FromFileName);
		File toFile = new File(p_ToFileName);
		//	
		if(toFile.exists()) {
			if(!toFile.delete()) {
				toFile.deleteOnExit();
			}
		}
		//	Write File
		try {
			FileInputStream inStream = new FileInputStream(fromFile);
		    FileOutputStream outStream = new FileOutputStream(toFile);
		    FileChannel inChannel = inStream.getChannel();
		    FileChannel outChannel = outStream.getChannel();
		    inChannel.transferTo(0, inChannel.size(), outChannel);
		    inStream.close();
		    outStream.close();
		    return true;
		} catch (Exception e) {
			LogM.log(m_ctx, getClass(), Level.SEVERE, "unable to create File: " + toFile.getPath(), e);
		}
		//	Default
		return false;
	}
	
	/**
	 * Get Bitmap from File
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/11/2014, 14:24:16
	 * @param fileName
	 * @param width
	 * @param height
	 * @return
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromFile(String fileName, int width, int height) {
		//	Valid Size
		if(width <= 0)
			width = 1;
		//	
		if(height <= 0)
			height = 1;
		// Get the size of the image
		Options options = new Options();
		BitmapFactory.decodeFile(fileName, options);
		//	Set bitmap options to scale the image decode target
		options.inJustDecodeBounds = false;
		options.inSampleSize = calculateInSampleSize(options, width, height);
		//	Decode the JPEG file into a Bitmap
		return BitmapFactory.decodeFile(fileName, options);
	}
	
	/**
	 * Get a bitmap from file
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param fileName
	 * @param options
	 * @return
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromFile(String fileName, Options options) {
		return BitmapFactory.decodeFile(fileName, options);
	}
	
	/**
	 * @See http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * @return int
	 */
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;

	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	    //	Default Return
	    return inSampleSize;
	}
	
	/**
	 * Get BitMap from File without size
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 11/11/2014, 14:25:21
	 * @param fileName
	 * @return
	 * @return Bitmap
	 */
	public static Bitmap getBitmapFromFile(String fileName) {
		return getBitmapFromFile(fileName, IMG_TARGET_W, IMG_TARGET_H);
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
	
	/**
	 * Show a image
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param uriPath
	 * @return void
	 */
	public static void showAttachment(Context ctx, Uri uriPath) {
		boolean ok = false;
		try {
			//	Launch Application
			Intent intent = new Intent(Intent.ACTION_VIEW);
			String mimeType = null;
			//	Set Data Type
			if(isGraphic(uriPath.toString())) {
				mimeType = "image/*";
			} else if(isPDF(uriPath.toString())) {
				mimeType = "application/pdf";
			} else if(FileUtil.getFileExtension(uriPath.getPath()) != null
					&& FileUtil.getFileExtension(uriPath.getPath()).equals(".txt")) {
				mimeType = "text/plain";
			} else {
				mimeType = FileUtil.getMimeTypeFromExtension(FileUtil.getFileExtension(uriPath.getPath()));
				if(mimeType == null
						|| mimeType.length() == 0) {
					mimeType = "text/plain";
				} else {
					mimeType = "*/*";
				}
			}
			//	
			intent.setDataAndType(uriPath, mimeType);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//	Start Activity
			ctx.startActivity(intent);
			//	
			ok = true;
		} catch (ActivityNotFoundException e) {
			LogM.log(ctx, AttachmentHandler.class, Level.WARNING, 
					"Error Launch Image: " + e.getLocalizedMessage());
		}
		//	Show Toast
		if(!ok) {
			Msg.toastMsg(ctx, ctx.getString(R.string.msg_AppIsNotAssociated));
		}

	}
	
	/**
	 * 	Is attachment entry a PDF
	 *  @param fileName
	 *	@return true if PDF
	 */
	public static boolean isPDF(String fileName) {
		return fileName.toLowerCase(Env.getLocate()).endsWith(".pdf");
	}	//	isPDF
	
	
	/**
	 * Get Path from Uri
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_Uri
	 * @return
	 * @return String
	 */
	public static String getPathFromUri(Context p_Ctx, Uri p_Uri) {
	    if(p_Uri == null)
	    	return null;
		if ("content".equalsIgnoreCase(p_Uri.getScheme())) {
	        String[] projection = { "_data" };
	        Cursor cursor = null;

	        try {
	            cursor = p_Ctx.getContentResolver().query(p_Uri, projection, null, null, null);
	            int column_index = cursor.getColumnIndexOrThrow("_data");
	            if (cursor.moveToFirst()) {
	                return cursor.getString(column_index);
	            }
	        } catch (Exception e) {
	            LogM.log(p_Ctx, AttachmentHandler.class, Level.SEVERE, "getPathFromUri(Context, Uri)", e);
	        }
	    } else if ("file".equalsIgnoreCase(p_Uri.getScheme())) {
	        return p_Uri.getPath();
	    }

	    return null;
	} 
	
	/**
	 * 	Is attachment entry a Graphic
	 *  @param fileName
	 *	@return true if *.gif, *.jpg, *.png
	 */
	public static boolean isGraphic(String fileName) {
		String m_lowname = fileName.toLowerCase(Env.getLocate());
		return m_lowname.endsWith(".gif") 
				|| m_lowname.endsWith(".jpg")
				|| m_lowname.endsWith(".jpeg")
				|| m_lowname.endsWith(".png");
	}	//	isGraphic
	
	/**
	 * Get Pretty Size
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/11/2014, 13:39:39
	 * @param ctx
	 * @param file
	 * @return
	 * @return String
	 */
	public static String getPrettySize(Context ctx, File file) {
		DecimalFormat m_numberFormat = DisplayType
				.getNumberFormat(ctx, DisplayType.AMOUNT, "###,###,###.##");
		float size = file.length();
		if (size <= 1024) {
			return m_numberFormat.format(size) + " B";
		} else {
			size /= 1024;
			if (size > 1024) {
				size /= 1024;
				return m_numberFormat.format(size) + " MB";
			} else {
				return m_numberFormat.format(size) + " kB";
			}
		}
	}
}
