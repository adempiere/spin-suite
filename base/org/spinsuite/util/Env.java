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
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.google.gson.Gson;

/**
 * @author Yamel Senih
 *
 */
public final class Env {
	
	/**
	 * Get Context
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return Context
	 */
	public static Context getCtx() {
		return m_Ctx;
	}
	
	/**
	 * Get Instance
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @return
	 * @return Env
	 */
	public static Env getInstance(Context p_Ctx) {
		if(m_Instance == null) {
			m_Instance = new Env(p_Ctx);
		}
		//	Default Return
		return m_Instance;
	}
	
	/**
	 * Set Login Date and Valid date
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param date
	 * @return
	 * @return boolean
	 */
	public static boolean loginDate(Context ctx, Date date) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//	Format Date yyyy-MM-dd hh:mm:ss
		Env.setContext("#Date", sdf.format(date.getTime()));
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String curDate = sdf.format(currentDate.getTime());
		String ctxDate = sdf.format(date.getTime());
		
		//	Format Date yyyy-MM-dd
		
		Env.setContext("#DateP", ctxDate);
		
		if(!(curDate.equals(ctxDate))){
			Env.setContext("#IsCurrentDate", "N");
			return false;
		}
		//	Default
		Env.setContext("#IsCurrentDate", "Y");
		return true;
	}
	
	/**
	 * Create default directory
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @return void
	 */
	public static void createDefaultDirectory(Context ctx){
		if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			String basePathName = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
			//	Application Path
			String dbPath = basePathName + Env.DB_PATH_DIRECTORY;
			String dbPathName = basePathName + Env.DB_PATH_NAME;
			//	Documents 
			String docPathName = basePathName + Env.DOC_DIRECTORY;
			String tmpPathName = basePathName + Env.TMP_DIRECTORY;
			String attPathName = basePathName + Env.ATT_DIRECTORY;
			String bcPathName  = basePathName + Env.BC_DIRECTORY;
			
			//	
			Env.setAppBaseDirectory(basePathName);
			Env.setDB_PathName(dbPathName);
			Env.setDoc_DirectoryPathName(docPathName);
			Env.setTmp_DirectoryPathName(tmpPathName);
			Env.setAtt_DirectoryPathName(attPathName);
			Env.setBC_DirectoryPathName(bcPathName);
			//	Database
			File f = new File(dbPath);
			if(!f.exists()) {
				if(!f.mkdirs())
					Msg.toastMsg(ctx, ctx.getString(R.string.msg_ErrorCreatingDirectory) 
							+ "\"" + dbPathName + "\"");
			} else if(f.isDirectory()) {
				File fDB = new File(dbPathName);
				fDB.delete();
			} else if(f.isFile()){
				if(!f.mkdirs())
					Msg.toastMsg(ctx, ctx.getString(R.string.msg_ErrorCreatingDirectory) 
							+ "\"" + dbPathName + "\"");
			}
			//	Create Document Folder
			File doc = new File(docPathName);
			if(!doc.exists()
					|| doc.isFile()) {
				if(!doc.mkdirs())
					Msg.toastMsg(ctx, ctx.getString(R.string.msg_ErrorCreatingDirectory) 
							+ "\"" + docPathName + "\"");
			}
			//	Create Tmp Folder
			File tmp = new File(tmpPathName);
			if(!tmp.exists()
					|| tmp.isFile()) {
				if(!tmp.mkdirs())
					Msg.toastMsg(ctx, ctx.getString(R.string.msg_ErrorCreatingDirectory) 
							+ "\"" + tmpPathName + "\"");
			}
			//	Create Attachment Folder
			File att = new File(attPathName);
			if(!att.exists()
					|| att.isFile()) {
				if(!att.mkdirs())
					Msg.toastMsg(ctx, ctx.getString(R.string.msg_ErrorCreatingDirectory) 
							+ "\"" + attPathName + "\"");
			}
			//	Create Business Chat Folder
			File bc = new File(bcPathName);
			if(!bc.exists()
					|| bc.isFile()) {
				if(!bc.mkdirs())
					Msg.toastMsg(ctx, ctx.getString(R.string.msg_ErrorCreatingDirectory) 
							+ "\"" + bcPathName + "\"");
			}
		} else {
			Env.setDB_PathName(DB.DB_NAME);
		}
	}
	
	/**
	 * Private
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 */
	private Env(Context p_Ctx) {
		m_Ctx = p_Ctx;
	}
	
	/**
	 * Verify if is loaded environment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:58:55
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isEnvLoad(Context ctx) {
		return getContextAsBoolean(ctx, SET_ENV);
	}
	
	/**
	 * Verify if is loaded environment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public static boolean isEnvLoad() {
		return isEnvLoad(getCtx());
	}
	
	/**
	 * Get is load activity
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:58:42
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isLoadedActivity(Context ctx) {
		return getContextAsBoolean(ctx, "#IsLoadedActivity");
	}
	
	/**
	 * Get is load activity
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public static boolean isLoadedActivity() {
		return isLoadedActivity(getCtx());
	}
	
	/**
	 * Get if is login user
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:58:25
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isLogin(Context ctx) {
		return getContextAsBoolean(ctx, "#IsLogin");
	}
	
	/**
	 * Get if is login user
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public static boolean isLogin() {
		return isLogin(getCtx());
	}
	
	/**
	 * Set is initial load
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:58:13
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setIsEnvLoad(Context ctx, boolean value) {
		setContext(ctx, SET_ENV, value);
	}
	
	/**
	 * Set is initial load
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param value
	 * @return void
	 */
	public static void setIsEnvLoad(boolean value) {
		setIsEnvLoad(getCtx(), value);
	}
	
	/**
	 * Set if loaded activity
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:57:53
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setIsLoadedActivity(Context ctx, boolean value) {
		setContext(ctx, "#IsLoadedActivity", value);
	}
	
	/**
	 * Set if loaded activity
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param value
	 * @return void
	 */
	public static void setIsLoadedActivity(boolean value) {
		setIsLoadedActivity(getCtx(), value);
	}
	
	/**
	 * Set is login
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:57:36
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setIsLogin(Context ctx, boolean value) {
		setContext(ctx, "#IsLogin", value);
	}
	
	/**
	 * Set is login
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param value
	 * @return void
	 */
	public static void setIsLogin(boolean value) {
		setIsLogin(getCtx(), value);
	}
	
	/**
	 * Load Role Access from Current Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 18:55:17
	 * @param ctx
	 * @param isForce
	 * @return void
	 */
	public static void loadRoleAccess(Context ctx, boolean isForce) {
		int m_AD_Role_ID = getAD_Role_ID(ctx);
		if(!isForce
				&& (m_AD_Role_ID == 0
					|| isAccessLoaded(ctx, m_AD_Role_ID)))
			return;
		//	Do it
		loadRoleAccess(ctx, m_AD_Role_ID);
		//	Set Loaded
		setAccessLoaded(ctx, m_AD_Role_ID, true);
	}
	
	/**
	 * Load Role Access from Current Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	public static void loadRoleAccess() {
		loadRoleAccess(getCtx(), false);
	}
	
    /**
     * Load Context Data
     * @author Yamel Senih 17/10/2012, 16:46:40
     * @return void
     */
	public static void loadContext(Context ctx) {
    	//	Carlos Parada, Load var in comntext
		if (isEnvLoad(ctx)) {	
			String sql = new String("SELECT sc.Name, sc.Value FROM AD_SysConfig sc");
	    	DB con = new DB(ctx);
	    	con.openDB(DB.READ_ONLY);
	    	Cursor rs = con.querySQL(sql, null);
	    	if(rs.moveToFirst()) {
	    		Editor ep = getEditor(ctx);
				do {
					ep.putString("#" + rs.getString(0), rs.getString(1));
				} while(rs.moveToNext());
				//	Commit
				ep.commit();
			}
	    	con.closeDB(rs);
		}
    }
	
	/**
	 * Load Context Data
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	public static void loadContext() {
		loadContext(getCtx());
	}
	
	/**
	 * Set access loaded
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 19:01:04
	 * @param ctx
	 * @param loaded
	 * @return void
	 */
	public static void setAccessLoaded(Context ctx, int m_AD_Role_ID, boolean loaded) {
		if(m_AD_Role_ID == 0)
			return;
		//	Set
		setContext(ctx, S_IS_ACCESS_LOADED + "|" + m_AD_Role_ID, loaded);
	}
	
	/**
	 * Set access loaded
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_AD_Role_ID
	 * @param loaded
	 * @return void
	 */
	public static void setAccessLoaded(int m_AD_Role_ID, boolean loaded) {
		setAccessLoaded(getCtx(), loaded);
	}
	
	/**
	 * Set Access Loaded for current role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 19:08:00
	 * @param ctx
	 * @param loaded
	 * @return void
	 */
	public static void setAccessLoaded(Context ctx, boolean loaded) {
		setAccessLoaded(ctx, getAD_Role_ID(ctx), loaded);
	}
	
	/**
	 * Set Access Loaded for current role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param loaded
	 * @return void
	 */
	public static void setAccessLoaded(boolean loaded) {
		setAccessLoaded(getCtx(), loaded);
	}
	
	/**
	 * Is Access Loaded
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 19:02:12
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @return
	 * @return boolean
	 */
	public static boolean isAccessLoaded(Context ctx, int m_AD_Role_ID) {
		if(m_AD_Role_ID == 0)
			return false;
		//	Set
		return getContextAsBoolean(ctx, S_IS_ACCESS_LOADED + "|" + m_AD_Role_ID);
	}
	
	/**
	 * Is Access Loaded
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_AD_Role_ID
	 * @return
	 * @return boolean
	 */
	public static boolean isAccessLoaded(int m_AD_Role_ID) {
		return isAccessLoaded(getCtx(), m_AD_Role_ID);
	}
	
	/**
	 * Is Access Loaded with current role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 19:09:17
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isAccessLoaded(Context ctx) {
		return isAccessLoaded(ctx, Env.getAD_Role_ID(ctx));
	}
	
	/**
	 * Is Access Loaded with current role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public static boolean isAccessLoaded() {
		return isAccessLoaded(getCtx());
	}
	
	/**
	 * Load Role Access
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 16:31:03
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @return void
	 */
	public static void loadRoleAccess(Context ctx, int m_AD_Role_ID) {
		//	Get Process Access
		KeyNamePair[] processAccess = DB.getKeyNamePairs(ctx, 
				"SELECT pa.AD_Process_ID, COALESCE(pa.IsReadWrite, 'N') " +
				"FROM AD_Process_Access pa " +
				"WHERE pa.AD_Role_ID = ?", m_AD_Role_ID);
		//	Get Editor
		Editor ep = getEditor(ctx);
		//	Ok
		boolean ok = false;
		//	Delete if not exists
		if(processAccess == null
				|| processAccess.length == 0) {
			//	Cache Reset
			int deleted = cacheReset(ctx, S_PROCESS_ACCESS + "|" + m_AD_Role_ID + "|", false);
			LogM.log(ctx, "Env", Level.FINE, "Process Access Deleted = " + deleted);
		} else {
			for(KeyNamePair pAccess : processAccess) {
				ep.putString(S_PROCESS_ACCESS + "|" + m_AD_Role_ID + "|" + pAccess.getKey(), pAccess.getName());
			}
			//	Set Ok
			ok = true;
		}
		//	Get Windows Access
		KeyNamePair[] windowsAccess = DB.getKeyNamePairs(ctx, 
				"SELECT wa.SPS_Window_ID, COALESCE(wa.IsReadWrite, 'N') " +
				"FROM SPS_Window_Access wa " +
				"WHERE wa.AD_Role_ID = ?", m_AD_Role_ID);
		//	Delete if not exists
		if(windowsAccess == null
				|| windowsAccess.length == 0) {
			//	Cache Reset
			int deleted = cacheReset(ctx, S_WINDOW_ACCESS + "|" + m_AD_Role_ID + "|", false);
			LogM.log(ctx, "Env", Level.FINE, "Windows Access Deleted = " + deleted);
		} else {
			for(KeyNamePair wAccess : windowsAccess) {
				ep.putString(S_WINDOW_ACCESS + "|" + m_AD_Role_ID + "|" + wAccess.getKey(), wAccess.getName());
			}
			//	Set Ok
			ok = true;
		}
		//	Get Document Access
		KeyNamePair[] documentAccess = DB.getKeyNamePairs(ctx, 
				"SELECT da.C_DocType_ID, rl.Value " +
				"FROM AD_Document_Action_Access da " +
				"INNER JOIN AD_Ref_List rl ON(rl.AD_Ref_List_ID = da.AD_Ref_List_ID) " +
				"WHERE da.AD_Role_ID = ?", m_AD_Role_ID);
		//	Delete if not exists
		if(documentAccess == null
				|| documentAccess.length == 0) {
			//	Cache Reset
			int deleted = cacheReset(ctx, S_DOCUMENT_ACCESS + "|" + m_AD_Role_ID + "|", false);
			LogM.log(ctx, "Env", Level.FINE, "Document Access Deleted = " + deleted);
		} else {
			//	Delete Old
			int deleted = cacheReset(ctx, S_DOCUMENT_ACCESS + "|" + m_AD_Role_ID + "|", false);
			LogM.log(ctx, "Env", Level.FINE, "Document Access Deleted = " + deleted);
			//	
			for(KeyNamePair dAccess : documentAccess) {
				ep.putString(S_DOCUMENT_ACCESS + "|" + m_AD_Role_ID + "|" + dAccess.getKey() + "|" + dAccess.getName(), "Y");
			}
			//	Set Ok
			ok = true;
		}
		//	Commit
		if(ok)
			ep.commit();
	}
	
	/**
	 * Load Role Access
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_AD_Role_ID
	 * @return void
	 */
	public static void loadRoleAccess(int m_AD_Role_ID) {
		loadRoleAccess(getCtx(), m_AD_Role_ID);
	}
	
	/**
	 * Get Process Access with Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 15/09/2014, 19:12:38
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @param m_AD_Process_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getProcessAccess(Context ctx, int m_AD_Role_ID, int m_AD_Process_ID) {
		return getContextAsBoolean(ctx, S_PROCESS_ACCESS + "|" + m_AD_Role_ID + "|" + m_AD_Process_ID);
	}
	
	/**
	 * Get Process Access with Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_AD_Role_ID
	 * @param m_AD_Process_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getProcessAccess(int m_AD_Role_ID, int m_AD_Process_ID) {
		return getProcessAccess(getCtx(), m_AD_Role_ID, m_AD_Process_ID);
	}
	
	/**
	 * Get Process Access without Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 15/09/2014, 19:13:26
	 * @param ctx
	 * @param m_AD_Process_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getProcessAccess(Context ctx, int m_AD_Process_ID) {
		return getProcessAccess(ctx, getAD_Role_ID(ctx), m_AD_Process_ID);
	}
	
	/**
	 * Get Process Access without Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_AD_Process_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getProcessAccess(int m_AD_Process_ID) {
		return getProcessAccess(getCtx(), m_AD_Process_ID);
	}
	
	
	/**
	 * Get Windows Access with Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 15/09/2014, 19:15:37
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @param m_SPS_Window_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getWindowsAccess(Context ctx, int m_AD_Role_ID, int m_SPS_Window_ID) {
		return getContextAsBoolean(ctx, S_WINDOW_ACCESS + "|" + m_AD_Role_ID + "|" + m_SPS_Window_ID);
	}
	
	/**
	 * Get Windows Access with Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_AD_Role_ID
	 * @param m_SPS_Window_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getWindowsAccess(int m_AD_Role_ID, int m_SPS_Window_ID) {
		return getWindowsAccess(getCtx(), m_AD_Role_ID, m_SPS_Window_ID);
	}
	
	/**
	 * Get Valid DocAction
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/09/2014, 11:46:20
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @param m_C_DocType_ID
	 * @param m_DocAction
	 * @return
	 * @return boolean
	 */
	public static boolean getDocumentAccess(Context ctx, int m_AD_Role_ID, int m_C_DocType_ID, String m_DocAction) {
		return getContextAsBoolean(ctx, S_DOCUMENT_ACCESS + "|" + m_AD_Role_ID + "|" + m_C_DocType_ID + "|" + m_DocAction);
	}
	
	/**
	 * Get Valid DocAction
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_AD_Role_ID
	 * @param m_C_DocType_ID
	 * @param m_DocAction
	 * @return
	 * @return boolean
	 */
	public static boolean getDocumentAccess(int m_AD_Role_ID, int m_C_DocType_ID, String m_DocAction) {
		return getDocumentAccess(getCtx(), m_AD_Role_ID, m_C_DocType_ID, m_DocAction);
	}
	
	/**
	 * Get Valid DocAction without Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/09/2014, 11:47:34
	 * @param ctx
	 * @param m_C_DocType_ID
	 * @param m_DocAction
	 * @return
	 * @return boolean
	 */
	public static boolean getDocumentAccess(Context ctx, int m_C_DocType_ID, String m_DocAction) {
		return getDocumentAccess(ctx, getAD_Role_ID(ctx), m_C_DocType_ID, m_DocAction);
	}
	
	/**
	 * Get Valid DocAction without Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_C_DocType_ID
	 * @param m_DocAction
	 * @return
	 * @return boolean
	 */
	public static boolean getDocumentAccess(int m_C_DocType_ID, String m_DocAction) {
		return getDocumentAccess(getCtx(), m_C_DocType_ID, m_DocAction);
	}
	
	/**
	 * Get Windows Access without Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 15/09/2014, 19:13:26
	 * @param ctx
	 * @param m_SPS_Window_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getWindowsAccess(Context ctx, int m_SPS_Window_ID) {
		return getWindowsAccess(ctx, getAD_Role_ID(), m_SPS_Window_ID);
	}
	
	/**
	 * Get Windows Access without Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_SPS_Window_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getWindowsAccess(int m_SPS_Window_ID) {
		return getWindowsAccess(getCtx(), m_SPS_Window_ID);
	}
	
	/**
	 * Cache Reset
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 17:33:30
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int cacheReset(Context ctx) {
		return cacheReset(ctx, "#", true);
	}
	
	/**
	 * Cache Reset
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public static int cacheReset() {
		return cacheReset(getCtx());
	}
	
	/**
	 * Cache Reset
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/09/2014, 17:23:40
	 * @param ctx
	 * @param m_Prefix
	 * @param m_IgnorePrefix
	 * @return
	 * @return int
	 */
	public static int cacheReset(Context ctx, String m_Prefix, boolean m_IgnorePrefix) {
		//	Set Default Prefix
		if(m_Prefix == null)
			m_Prefix = "";
		//	Get Preferences
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		//	Get All Entries
		Map<String, ?> allEntries = preferences.getAll();
		//	Delete
		int deleted = 0;
		//	Get Editor
		Editor ep = getEditor(ctx);
		for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
			String key = entry.getKey();
			if(key == null
					|| (key.startsWith(m_Prefix)
							&& m_IgnorePrefix)
					|| (!key.startsWith(m_Prefix)
							&& !m_IgnorePrefix))
				continue;
			//	
			ep.remove(key);
			//	Count
			deleted++;
			//	Log
			LogM.log(ctx, "ENV", Level.FINE, "Entry [" + key + "] Deleted");
		}
		//	Commit
		if(deleted != 0)
			ep.commit();
		//	Return
		return deleted;
	}
	
	/**
	 * Get share preference editor
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:57:10
	 * @param ctx
	 * @return
	 * @return Editor
	 */
	public static Editor getEditor(Context ctx) {
		m_ShareP = PreferenceManager.getDefaultSharedPreferences(ctx);
		return m_ShareP.edit();
	}
	
	/**
	 * Get Share Preferences
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @return
	 * @return SharedPreferences
	 */
	public static SharedPreferences getSharePreferences(Context ctx) {
		if(m_ShareP == null) { 
			m_ShareP = PreferenceManager.getDefaultSharedPreferences(ctx);
		}
		return m_ShareP;
	}
	
	/**
	 * Remove a context value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/09/2014, 16:13:50
	 * @param ctx
	 * @param context
	 * @return void
	 */
	public static void removeContext(Context ctx, String context) {
		if (ctx == null || context == null)
			return;
		//	Log
		LogM.log(ctx, "Env", Level.FINE, "removeContext("  + context + ")");
		//	
		Editor ep = getEditor(ctx);
		ep.remove(context);
		ep.commit();
	}
	
	/**
	 * Remove a context value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @return void
	 */
	public static void removeContext(String context) {
		removeContext(getCtx(), context);
	}
	
	/**
	 *	Set Global Context to Value
	 *  @param ctx context
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Context ctx, String context, String value) {
		if (ctx == null || context == null)
			return;
		//	Log
		LogM.log(ctx, "Env", Level.FINE, "setContext("  + context + ", " + value + ")");
		//
		if (value == null)
			value = "";
		Editor ep = getEditor(ctx);
		ep.putString(context, value);
		ep.commit();
	}	//	setContext
	
	/**
	 * Set Global Context to Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (String context, String value) {
		setContext (getCtx(), context, value);
	}
	
	/**
	 * Set Context Object
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 15/05/2014, 14:41:53
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextObject(Context ctx, String context, Object value) {
		Editor prefsEditor = getEditor(ctx);
        Gson gson = new Gson();
        String json = gson.toJson(value);
        prefsEditor.putString(context, json);
        prefsEditor.commit();
	}
	
	/**
	 * Set Context Object
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextObject(String context, Object value) {
		setContextObject(getCtx(), context, value);
	}
	
	/**
	 * Set Context Object with Activity No and Tab No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/10/2014, 14:42:48
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextObject(Context ctx, int m_ActivityNo, int TabNo, String context, Object value) {
		if (ctx == null || context == null)
			return;
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "=" + value);
		//	
		setContextObject(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context Object with Activity No and Tab No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextObject(int m_ActivityNo, int TabNo, String context, Object value) {
		setContextObject(getCtx(), m_ActivityNo, TabNo, context, value);
	}
	
	
	/**
	 * Set Context Object
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/05/2014, 14:13:23
	 * @param ctx
	 * @param context
	 * @param clazz
	 * @return
	 * @return Object
	 */
	public static Object getContextObject(Context ctx, String context, Class<?> clazz) {
		Gson gson = new Gson();
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		String json = pf.getString(context, null);
		//	Valid null
		if(json == null)
			return null;
		//	
	    return gson.fromJson(json, clazz);
	}
	
	/**
	 * Set Context Object
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param clazz
	 * @return
	 * @return Object
	 */
	public static Object getContextObject(String context, Class<?> clazz) {
		return getContextObject(getCtx(), context, clazz);
	}
	
	/**
	 * Get Context Object with Activity No and Tab No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/10/2014, 14:55:22
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param clazz
	 * @return
	 * @return Object
	 */
	public static Object getContextObject(Context ctx, int m_ActivityNo, int TabNo, String context, Class<?> clazz) {
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		LogM.log(ctx, "Env", Level.INFO, "getContextObject=" + m_ActivityNo+"|"+TabNo+"|"+context);
		//	
		return getContextObject(ctx, m_ActivityNo+"|"+TabNo+"|"+context, clazz);
	}	//	getContext
	
	/**
	 * Get Context Object with Activity No and Tab No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param clazz
	 * @return
	 * @return Object
	 */
	public static Object getContextObject(int m_ActivityNo, int TabNo, String context, Class<?> clazz) {
		return getContextObject(getCtx(), m_ActivityNo, TabNo, context, clazz);
	}
	
	/**
	 * Set Context
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:56:56
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, String context, int value) {
		if (ctx == null || context == null)
			return;
		LogM.log(ctx, "Env", Level.INFO, "setContext(" + context+", " + value);
		setContext(context, String.valueOf(value));
	}	//	setContext
	
	
	/**
	 * Set Context for long
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(Context ctx, String context, long value) {
		if (ctx == null || context == null)
			return;
		LogM.log(ctx, "Env", Level.INFO, "setContext(" + context+", " + value);
		setContext(context, String.valueOf(value));
	}
	
	/**
	 * Set Context for Long
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (String context, long value) {
		setContext (getCtx(), context, value);
	}
	
	/**
	 * Set Context
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (String context, int value) {
		setContext (getCtx(), context, value);
	}
	
	
	/**
	 *	Set Context for Window & Tab to Value
	 *  @param ctx context
	 *  @param m_ActivityNo window no
	 *  @param TabNo tab no
	 *  @param context context key
	 *  @param value context value
	 *   */
	public static void setContext (Context ctx, int m_ActivityNo, int TabNo, String context, String value) {
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "==" + value);
		//
		if (value == null)
			if (context.endsWith("_ID"))
				// TODO: Research potential problems with tables with Record_ID=0
				value = new String("0");
			else
				value = new String("");
		setContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context for Window & Tab to Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (int m_ActivityNo, int TabNo, String context, String value) {
		setContext(getCtx(), m_ActivityNo, TabNo, context, value);
	}
	
	/**
	 * Set Context Array Int
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 15:45:00
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextArray (Context ctx, int m_ActivityNo, int TabNo, String context, int[] value) {
		if (ctx == null || context == null)
			return;
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "==" + value);
		//
		setContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context Array Int
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextArray (int m_ActivityNo, int TabNo, String context, int[] value) {
		setContextArray(getCtx(), m_ActivityNo, TabNo, context, value);
	}
	
	/**
	 * Set Context Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 15:42:54
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextArray (Context ctx, int m_ActivityNo, int TabNo, String context, String[] value) {
		if (ctx == null || context == null)
			return;
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "==" + value);
		//
		setContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContextArray (int m_ActivityNo, int TabNo, String context, String[] value) {
		setContextArray(getCtx(), m_ActivityNo, TabNo, context, value);
	}
	
	/**	
	 * Set Context as boolean
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/03/2014, 10:40:04
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(Context ctx, int m_ActivityNo, int TabNo, String context, boolean value) {
		if (ctx == null || context == null)
			return;
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "==" + value);
		//	
		setContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	Set Context
	
	/**
	 * Set Context as boolean
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(int m_ActivityNo, int TabNo, String context, boolean value) {
		setContext(getCtx(), m_ActivityNo, TabNo, context, value);
	}
	
	/**
	 * Set Context as Boolean
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 22/05/2014, 17:01:51
	 * @param ctx
	 * @param m_ActivityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, int m_ActivityNo, String context, boolean value) {
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+") " + context + "==" + value);
		//
		setContext(ctx, m_ActivityNo+"|"+context, value);
	}	//	Set Context
	
	/**
	 * Set Context as Boolean
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(int m_ActivityNo, String context, boolean value) {
		setContext(getCtx(), m_ActivityNo, context, value);
	}
	
	/**
	 * Set Context with Activity No and Tab No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 15/02/2014, 13:16:11
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, int m_ActivityNo, int TabNo, String context, int value) {
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "=" + value);
		//	
		setContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context with Activity No and Tab No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(int m_ActivityNo, int TabNo, String context, int value) {
		setContext(getCtx(), m_ActivityNo, TabNo, context, value);
	}
	
	/**
	 * Set Context with Activity No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 15/02/2014, 13:18:28
	 * @param ctx
	 * @param m_ActivityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(Context ctx, int m_ActivityNo, String context, int value) {
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+") " + context + "=" + value);
		//	
		setContext(ctx, m_ActivityNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context with Activity No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(int m_ActivityNo, String context, int value) {
		setContext(getCtx(), m_ActivityNo, context, value);
	}
	
	
	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param m_ActivityNo window no
	 *  @param context context key
	 *  @return value or 0
	 */
	public static int getContextAsInt(Context ctx, int m_ActivityNo, String context) {
		String s = getContext(ctx, m_ActivityNo, context, false);
		if (s == null || s.length() == 0)
			return 0;
		//
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LogM.log(ctx, "Env", Level.SEVERE, "(" + context + ") = " + s, e);
		}
		//	Default Return
		return 0;
	}	//	getContextAsInt
	
	/**
	 * Get Context and convert it to an integer (0 if error)
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param context
	 * @return
	 * @return int
	 */
	public static int getContextAsInt(int m_ActivityNo, String context) {
		return getContextAsInt(getCtx(), m_ActivityNo, context);
	}
	
	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param m_ActivityNo window no
	 *  @param context context key
	 *  @param onlyWindow  if true, no defaults are used unless explicitly asked for
	 *  @return value or 0
	 */
	public static int getContextAsInt(Context ctx, int m_ActivityNo, String context, boolean onlyWindow) {
		String s = getContext(ctx, m_ActivityNo, context, onlyWindow);
		if (s == null || s.length() == 0)
			return 0;
		//
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LogM.log(ctx, "Env", Level.SEVERE, "(" + context + ") = " + s, e);
		}
		//	Default Return
		return 0;
	}	//	getContextAsInt
	
	
	/**
	 * Get Context and convert it to an integer (0 if error)
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param context
	 * @param onlyWindow
	 * @return
	 * @return int
	 */
	public static int getContextAsInt(int m_ActivityNo, String context, boolean onlyWindow) {
		return getContextAsInt(getCtx(), m_ActivityNo, context, onlyWindow);
	}
	
	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param TabNo tab no
	 * 	@param context context key
	 *  @return value or 0
	 */
	public static int getContextAsInt(Context ctx, int WindowNo, int TabNo, String context) {
		String s = getContext(ctx, WindowNo, TabNo, context);
		if (s == null || s.length() == 0)
			return 0;
		//
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LogM.log(ctx, "Env", Level.SEVERE, "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt
	
	/**
	 * Get Context and convert it to an integer (0 if error)
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param WindowNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return int
	 */
	public static int getContextAsInt(int WindowNo, int TabNo, String context) {
		return getContextAsInt (getCtx(), WindowNo, TabNo, context);
	}
	
	/**
	 *	Set SO Trx
	 *  @param ctx context
	 *  @param isSOTrx SO Context
	 */
	public static void setISOTrx (Context ctx, boolean isSOTrx) {
		if (ctx == null)
			return;
		setContext(ctx, "IsSOTrx", isSOTrx? "Y": "N");
	}	//	setSOTrx
	
	/**
	 * Set SO Trx
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param isSOTrx
	 * @return void
	 */
	public static void setISOTrx (boolean isSOTrx) {
		setISOTrx(getCtx(), isSOTrx);
	}
	
	/**
	 *	Get global Value of Context
	 *  @param ctx context
	 *  @param context context key
	 *  @return value or ""
	 */
	public static String getContext(Context ctx, String context) {
		SharedPreferences pf = getSharePreferences(ctx);
		String value = pf.getString(context, null);
		LogM.log(ctx, "Env", Level.FINE, "getContext(" + context + ") = " + value);
		return value;
	}	//	getContext
	
	/**
	 * Get global Value of Context
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @return
	 * @return String
	 */
	public static String getContext(String context) {
		return getContext(getCtx(), context);
	}
	
	/**
	 * Get Date with format to
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:56:26
	 * @param ctx
	 * @param context
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @return String
	 */
	public static String getContextDateFormat(Context ctx, String context, String fromFormat, String toFormat) {
		String dateS = getContext(ctx, context);
		return getDateFormatString(dateS, fromFormat, toFormat);
	}	//	getContext
	
	/**
	 * Get Date with format to
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @return String
	 */
	public static String getContextDateFormat(String context, String fromFormat, String toFormat) {
		return getContextDateFormat(getCtx(), context, fromFormat, toFormat);
	}
	
	/**
	 * Get Date with format
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:56:08
	 * @param dateS
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @return String
	 */
	public static String getDateFormatString(String dateS, String fromFormat, String toFormat) {
		Date date;
		try {
			SimpleDateFormat fmtFront=new SimpleDateFormat(fromFormat);
	        SimpleDateFormat fmtBack=new SimpleDateFormat(toFormat);
			date = fmtFront.parse(dateS);
			return fmtBack.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//	
        return null;
	}	//	getContext
	
	/**
	 * Get Date Format
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/11/2014, 9:55:11
	 * @param dateS
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @return Date
	 */
	public static Date getDateFormat(String dateS, String fromFormat, String toFormat) {
		Date date;
		try {
			SimpleDateFormat fmtFront = new SimpleDateFormat(fromFormat);
			date = fmtFront.parse(dateS);
			return date;
		} catch (ParseException e) {
		//	
		}       
        return null;
	    
	}	//	getContext
	
	/**
	 * Get Current date formated
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:55:48
	 * @param format
	 * @return
	 * @return String
	 */
	public static String getCurrentDateFormat(String format) {
		Date date=new Date();
	    SimpleDateFormat fmt=new SimpleDateFormat(format);
	    return fmt.format(date);
	}	//	getContext
	
	/**
	 * Get Current Date
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/02/2014, 23:00:43
	 * @return
	 * @return Date
	 */
	public static Date getCurrentDate() {
		return new Date();		
	}	//	getContext
	
	/**
	 * Get Context As Boolean Value
	 * @author Yamel Senih 23/04/2012, 18:35:37
	 * @param ctx
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean (Context ctx, String context) {
		String s = getContext(ctx, context);
		//	
		boolean valid = (s != null && s.equals("Y"));
		//	Log
		LogM.log(ctx, "Env", Level.FINE, "getContextAsBoolean(" + context + ") = " + valid);
		return valid;
	}	//	getContext
	
	/**
	 * Get Context As Boolean Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean (String context) {
		return getContextAsBoolean (getCtx(), context);
	}
	
	/**
	 * Get Context as boolean
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/03/2014, 11:11:31
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean(Context ctx, int m_ActivityNo, int TabNo, String context) {
		String s = getContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context);
		LogM.log(ctx, "Env", Level.INFO, "getContext=" + m_ActivityNo+"|"+TabNo+"|"+context);
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		if (TAB_INFO == TabNo)
			return s != null ? s.equals("Y") : false;
		//
		if (s == null)
			s = getContext(ctx, m_ActivityNo, context);
		return s != null ? s.equals("Y") : false;
	}	//	getContext
	
	/**
	 * Get Context as boolean
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean(int m_ActivityNo, int TabNo, String context) {
		return getContextAsBoolean (getCtx(), m_ActivityNo, TabNo, context);
	}
	
	/**
	 * Get Context as Boolean with Activity No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 26/05/2014, 12:02:34
	 * @param ctx
	 * @param m_ActivityNo
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean(Context ctx, int m_ActivityNo, String context) {
		String s = getContext(ctx, m_ActivityNo+"|"+context);
		LogM.log(ctx, "Env", Level.INFO, "getContext=" + m_ActivityNo+"|"+context);
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		//
		if (s == null)
			s = getContext(ctx, m_ActivityNo, context);
		return s != null ? s.equals("Y") : false;
	}	//	getContext
	
	/**
	 * Get Context as Boolean with Activity No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean(int m_ActivityNo, String context) {
		return getContextAsBoolean(getCtx(), m_ActivityNo, context);
	}
	
	/**
	 *	Set Global Context to Y/N Value
	 *  @param ctx context
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext(Context ctx, String context, boolean value) {
		setContext(ctx, context, value? "Y": "N");
	}	//	setContext
	
	/**
	 * Set Global Context to Y/N Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(String context, boolean value) {
		setContext(getCtx(), context, value);
	}

	/**
	 *	Set Context for Window to Value
	 *  @param ctx context
	 *  @param m_ActivityNo window no
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Context ctx, int m_ActivityNo, String context, String value) {
		if (value == null)
			value = "";
		setContext(m_ActivityNo+"|"+context, value);
	}	//	setContext

	/**
	 * Set Context for Window to Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(int m_ActivityNo, String context, String value) {
		setContext(getCtx(), m_ActivityNo, context, value);
	}
	
	/**
	 *	Get Value of Context for Window.
	 *	if not found global context if available and enabled
	 *  @param ctx context
	 *  @param m_ActivityNo window
	 *  @param context context key
	 *  @param  onlyWindow  if true, no defaults are used unless explicitly asked for
	 *  @return value or ""
	 */
	public static String getContext(Context ctx, int m_ActivityNo, String context, boolean onlyWindow) {
		String s = getContext(ctx, m_ActivityNo+"|"+context);
		if (s == null) {
			//	Explicit Base Values
			if (context.startsWith("#") || context.startsWith("$"))
				return getContext(ctx, context);
			if (onlyWindow)			//	no Default values
				return "";
			return getContext(ctx, "#" + context);
		} else {
			LogM.log(ctx, "Env", Level.FINE, "getContext(" + m_ActivityNo+"|"+context + ") = " + s);
		}
		return s;
	}	//	getContext
	
	/**
	 * Get Value of Context for Window.
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param context
	 * @param onlyWindow
	 * @return
	 * @return String
	 */
	public static String getContext(int m_ActivityNo, String context, boolean onlyWindow) {
		return getContext(getCtx(), m_ActivityNo, context, onlyWindow);
	}
	
	/**
	 *	Get Value of Context for Window.
	 *	if not found global context if available
	 *  @param ctx context
	 *  @param m_ActivityNo window
	 *  @param context context key
	 *  @return value or ""
	 */
	public static String getContext(Context ctx, int m_ActivityNo, String context) {
		return getContext(ctx, m_ActivityNo, context, false);
	}	//	getContext
	
	/**
	 * Get Value of Context for Window.
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param context
	 * @return
	 * @return String
	 */
	public static String getContext(int m_ActivityNo, String context) {
		return getContext(getCtx(), m_ActivityNo, context);
	}

	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param ctx context
	 * @param m_ActivityNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @return value or ""
	 */
	public static String getContext(Context ctx, int m_ActivityNo, int TabNo, String context) {
		String s = getContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context);
		LogM.log(ctx, "Env", Level.INFO, "getContext=" + m_ActivityNo+"|"+TabNo+"|"+context);
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		if (TAB_INFO == TabNo)
			return s != null ? s : "";
		//
		if (s == null || s.length() == 0)
			return getContext(ctx, m_ActivityNo, context, false);
		return s;
	}	//	getContext
	
	/**
	 * Get Value of Context for Window & Tab,
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return String
	 */
	public static String getContext(int m_ActivityNo, int TabNo, String context) {
		return getContext(getCtx(), m_ActivityNo, TabNo, context);
	}
	
	/**
	 * Get Context As Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 15:15:20
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return String[]
	 */
	public static String[] getContextAsArray(Context ctx, String context) {
		LogM.log(ctx, "Env", Level.INFO, "getContext=" + context);
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		Set<String> set = pf.getStringSet(context, null);
		//	Default
		if(set == null)
			return null;
		//	Default
		String [] array = new String[set.size()];
		set.toArray(array);
		return array;
	}	//	getContext
	
	/**
	 * Get Context As Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @return
	 * @return String[]
	 */
	public static String[] getContextAsArray(String context) {
		return getContextAsArray(getCtx(), context);
	}
	
	
	/**
	 * Get Context As Int Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 15:35:26
	 * @param ctx
	 * @param context
	 * @return
	 * @return int[]
	 */
	public static int[] getContextAsIntArray(Context ctx, String context) {
		String [] array = getContextAsArray(ctx, context);
		if(array == null)
			return new int[]{0};
		//	
		try {
			int[] arrayInt = new int[array.length];
			for(int i = 0; i < array.length; i++) {
				arrayInt[i] = Integer.parseInt(array[i]);
			}
			//	
			return arrayInt;
		} catch (Exception e) {
			//	
		}
		//	Default
		return new int[]{0};
	}
	
	/**
	 * Get Context As Int Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @return
	 * @return int[]
	 */
	public static int[] getContextAsIntArray(String context) {
		return getContextAsIntArray(getCtx(), context);
	}
	
	/**
	 * Get Context as Array with Activity and tab
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 15:18:56
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return String[]
	 */
	public static String[] getContextAsArray(Context ctx, int m_ActivityNo, int TabNo, String context) {
		return getContextAsArray(ctx, m_ActivityNo+"|"+TabNo+"|"+context);
	}
	
	/**
	 * Get Context as Array with Activity and tab
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return String[]
	 */
	public static String[] getContextAsArray(int m_ActivityNo, int TabNo, String context) {
		return getContextAsArray(getCtx(), m_ActivityNo, TabNo, context);
	}
	
	/**
	 * Get Context as Int Array with Activity and tab
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 15:36:03
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return int[]
	 */
	public static int[] getContextAsIntArray(Context ctx, int m_ActivityNo, int TabNo, String context) {
		return getContextAsIntArray(ctx, m_ActivityNo+"|"+TabNo+"|"+context);
	}
	
	/**
	 * Get Context as Int Array with Activity and tab
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return int[]
	 */
	public static int[] getContextAsIntArray(int m_ActivityNo, int TabNo, String context) {
		return getContextAsIntArray(getCtx(), m_ActivityNo, TabNo, context);
	}
	
	/**
	 * Set Context Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 15:26:05
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(Context ctx, String context, String[] value) {
		LogM.log(ctx, "Env", Level.INFO, "setContext(" + context+", " + value);
		Editor ep = getEditor(ctx);
		if(value == null) {
			ep.putStringSet(context, null);
		} else {
			//	Set Array
			Set<String> set = new HashSet<String>(Arrays.asList(value));
			ep.putStringSet(context, set);
		}
		//	Commit
		ep.commit();
	}	//	setContext
	
	/**
	 * Set Context Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(String context, String[] value) {
		setContext(getCtx(), context, value);
	}
	
	/**
	 * Set Context As Int Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 15:40:51
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(Context ctx, String context, int[] value) {
		if(value == null
				|| value.length == 0) {
			setContext(ctx, context, (String[])null);
			//	Return
			return;
		}
		//	Do it
		String[] strValue = new String[value.length];
		for(int i = 0; i < value.length; i++) {
			strValue[i] = String.valueOf(value[i]);
		}
		//	
		setContext(ctx, context, strValue);
	}
	
	/**
	 * Set Context As Int Array
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext(String context, int[] value) {
		setContext (getCtx(), context, value);
	}
	

	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param ctx context
	 * @param m_ActivityNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @param onlyTab if true, no window value is searched
	 * @return value or ""
	 */
	public static String getContext(Context ctx, int m_ActivityNo, int TabNo, String context, boolean onlyTab) {
		final boolean onlyWindow = onlyTab ? true : false;
		return getContext(ctx, m_ActivityNo, TabNo, context, onlyTab, onlyWindow);
	}
	
	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param onlyTab
	 * @return
	 * @return String
	 */
	public static String getContext(int m_ActivityNo, int TabNo, String context, boolean onlyTab) {
		return getContext(getCtx(), m_ActivityNo, TabNo, context, onlyTab);
	}
	
	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked.
	 * @param ctx context
	 * @param m_ActivityNo window no
	 * @param TabNo tab no
	 * @param context context key
	 * @param onlyTab if true, no window value is searched
	 * @param onlyWindow if true, no global context will be searched
	 * @return value or ""
	 */
	public static String getContext(Context ctx, int m_ActivityNo, int TabNo, String context, boolean onlyTab, boolean onlyWindow) {
		String s = getContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context);
		if (TAB_INFO == TabNo)
			return s != null ? s : "";
		//
		if (s == null && ! onlyTab)
			return getContext(ctx, m_ActivityNo, context, onlyWindow);
		return s;
	}	//	getContext
	
	/**
	 * Get Value of Context for Window & Tab,
	 * if not found global context if available.
	 * If TabNo is TAB_INFO only tab's context will be checked
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param onlyTab
	 * @param onlyWindow
	 * @return
	 * @return String
	 */
	public static String getContext(int m_ActivityNo, int TabNo, String context, boolean onlyTab, boolean onlyWindow) {
		return getContext(getCtx(), m_ActivityNo, TabNo, context, onlyTab, onlyWindow);
	}

	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param context context key
	 *  @return value
	 */
	public static int getContextAsInt(Context ctx, String context) {
		try{
			if (ctx == null || context == null)
				throw new IllegalArgumentException ("Require Context");
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
			String value = sp.getString(context, "0");
			if(value != null && value.length() > 0)
				return Integer.parseInt(value);
		} catch (Exception e) {
			
		}
		return 0;
	}	//	getContextAsInt
	
	/**
	 * Get Context for long values
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param context
	 * @return
	 * @return long
	 */
	public static long getContextAsLong(Context ctx, String context) {
		try{
			if (ctx == null || context == null)
				throw new IllegalArgumentException ("Require Context");
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
			String value = sp.getString(context, "0");
			if(value != null && value.length() > 0)
				return Long.parseLong(value);
		} catch (Exception e) {
			
		}
		return 0;
	}	//	getContextAsInt
	
	/**
	 * Get Context for long values
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @return
	 * @return long
	 */
	public static long getContextAsLong(String context) {
		return getContextAsLong(getCtx(), context);
	}
	
	/**
	 * Get Context and convert it to an integer (0 if error)
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param context
	 * @return
	 * @return int
	 */
	public static int getContextAsInt(String context) {
		return getContextAsInt(getCtx(), context);
	}
	
	/**
	 *	Is Sales Order Trx
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @return true if SO (default)
	 */
	public static boolean isSOTrx (Context ctx, int WindowNo) {
		return getContextAsBoolean(ctx, WindowNo, "IsSOTrx");
	}	//	isSOTrx
	
	/**
	 * Is Sales Order Trx
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public static boolean isSOTrx () {
		return getContextAsBoolean(getCtx(), "IsSOTrx");
	}
	
	/**
	 * 	Get Login AD_Client_ID
	 *	@param ctx context
	 *	@return login AD_Client_ID
	 */
	public static int getAD_Client_ID (Context ctx) {
		return getContextAsInt(ctx, "#AD_Client_ID");
	}	//	getAD_Client_ID

	/**
	 * Get Login AD_Client_ID
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public static int getAD_Client_ID() {
		return getAD_Client_ID(getCtx());
	}
	
	/**
	 * 	Get Login AD_Org_ID
	 *	@param ctx context
	 *	@return login AD_Org_ID
	 */
	public static int getAD_Org_ID (Context ctx) {
		return getContextAsInt(ctx, "#AD_Org_ID");
	}	//	getAD_Client_ID

	/**
	 * Get Login AD_Org_ID
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public static int getAD_Org_ID() {
		return getAD_Org_ID(getCtx());
	}
	
	/**
	 * 	Get Login AD_User_ID
	 *	@param ctx context
	 *	@return login AD_User_ID
	 */
	public static int getAD_User_ID(Context ctx) {
		return getContextAsInt(ctx, "#AD_User_ID");
	}	//	getAD_User_ID
	
	/**
	 * Get Context
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public static int getAD_User_ID () {
		return getAD_User_ID(getCtx());
	}
	
	/**
	 * 	Get Login AD_Role_ID
	 *	@param ctx context
	 *	@return login AD_Role_ID
	 */
	public static int getAD_Role_ID(Context ctx) {
		return getContextAsInt(ctx, "#AD_Role_ID");
	}	//	getAD_Role_ID
	
	/**
	 * Get Login AD_Role_ID
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public static int getAD_Role_ID() {
		return getAD_Role_ID(getCtx());
	}	//	getAD_Role_ID

	/**
	 * Get Login M_Warehouse
	 * @author Yamel Senih 26/04/2012, 15:49:16
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int getM_Warehouse_ID(Context ctx) {
		return getContextAsInt(ctx, "#M_Warehouse_ID");
	}	//	getAD_Role_ID
	
	/**
	 * Get Login M_Warehouse
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public static int getM_Warehouse_ID() {
		return getM_Warehouse_ID(getCtx());
	}
	
	/**
	 * Set User ID
	 * @author Yamel Senih 26/04/2012, 20:21:48
	 * @param ctx
	 * @param m_AD_User_ID
	 * @return void
	 */
	public static void setAD_User_ID(Context ctx, int m_AD_User_ID) {
		setContext(ctx, "#AD_User_ID", m_AD_User_ID);
	}
	
	/**
	 * Set User ID
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_AD_User_ID
	 * @return void
	 */
	public static void setAD_User_ID(int m_AD_User_ID) {
		setAD_User_ID(getCtx(), m_AD_User_ID);
	}
	
	/**
	 * Set Client
	 * @author Yamel Senih 26/04/2012, 15:52:37
	 * @param ctx
	 * @param m_AD_Client_ID
	 * @return void
	 */
	public static void setAD_Client_ID(Context ctx, int m_AD_Client_ID) {
		setContext(ctx, "#AD_Client_ID", m_AD_Client_ID);
	}
	
	/**
	 * Set Client
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_AD_Client_ID
	 * @return void
	 */
	public static void setAD_Client_ID(int m_AD_Client_ID) {
		setAD_Client_ID(getCtx(), m_AD_Client_ID);
	}
	
	/**
	 * Set Org
	 * @author Yamel Senih 26/04/2012, 15:53:56
	 * @param ctx
	 * @param m_AD_Org_ID
	 * @return void
	 */
	public static void setAD_Org_ID(Context ctx, int m_AD_Org_ID) {
		setContext(ctx, "#AD_Org_ID", m_AD_Org_ID);
	}
	
	/**
	 * Set Org
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_AD_Org_ID
	 * @return void
	 */
	public static void setAD_Org_ID(int m_AD_Org_ID) {
		setAD_Org_ID(getCtx(), m_AD_Org_ID);
	}
	
	/**
	 * Set Role
	 * @author Yamel Senih 26/04/2012, 15:54:29
	 * @param ctx
	 * @param m_AD_Role_ID
	 * @return void
	 */
	public static void setAD_Role_ID(Context ctx, int m_AD_Role_ID) {
		setContext(ctx, "#AD_Role_ID", m_AD_Role_ID);
	}
	
	/**
	 * Set Role
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_AD_Role_ID
	 * @return void
	 */
	public static void setAD_Role_ID(int m_AD_Role_ID) {
		setAD_Role_ID(getCtx(), m_AD_Role_ID);
	}
	
	/**
	 * Set Warehouse
	 * @author Yamel Senih 26/04/2012, 15:55:17
	 * @param ctx
	 * @param m_M_Warehouse_ID
	 * @return void
	 */
	public static void setM_Warehouse_ID(Context ctx, int m_M_Warehouse_ID) {
		setContext(ctx, "#M_Warehouse_ID", m_M_Warehouse_ID);
	}
	
	/**
	 * Set Warehouse
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_M_Warehouse_ID
	 * @return void
	 */
	public static void setM_Warehouse_ID(int m_M_Warehouse_ID) {
		setM_Warehouse_ID(getCtx(), m_M_Warehouse_ID);
	}
	
	/**
	 * Set Save Pass
	 * @author Yamel Senih 26/04/2012, 17:46:21
	 * @param ctx
	 * @param isSavePass
	 * @return void
	 */
	public static void setSavePass(Context ctx, boolean isSavePass) {
		setContext(ctx, "#SavePass", isSavePass);
	}
	
	/**
	 * Set Save Pass
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param isSavePass
	 * @return void
	 */
	public static void setSavePass(boolean isSavePass) {
		setSavePass(getCtx(), isSavePass);
	}
	
	/**
	 * Set Automatic Visit Closing
	 * @author Yamel Senih 31/07/2012, 12:25:21
	 * @param ctx
	 * @param isSavePass
	 * @return void
	 */
	public static void setAutoLogin(Context ctx, boolean isAutoLogin) {
		setContext(ctx, "#AutoLogin", isAutoLogin);
	}
	
	/**
	 * Set Automatic Visit Closing
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param isAutoLogin
	 * @return void
	 */
	public static void setAutoLogin(boolean isAutoLogin) {
		setAutoLogin(getCtx(), isAutoLogin);
	}
	
	/**
	 * Set Auto Login Confirmed
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/10/2014, 1:11:40
	 * @param ctx
	 * @param isAutoLoginConfirmed
	 * @return void
	 */
	public static void setAutoLoginComfirmed(Context ctx, boolean isAutoLoginConfirmed) {
		setContext(ctx, "#IsAutoLoginConfirmed", isAutoLoginConfirmed);
	}
	
	/**
	 * Set Auto Login Confirmed
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param isAutoLoginConfirmed
	 * @return void
	 */
	public static void setAutoLoginComfirmed(boolean isAutoLoginConfirmed) {
		setAutoLoginComfirmed(getCtx(), isAutoLoginConfirmed);
	}
	
	/**
	 * Get Save Pass
	 * @author Yamel Senih 26/04/2012, 17:47:21
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isSavePass(Context ctx) {
		return getContextAsBoolean(ctx, "#SavePass");
	}
	
	/**
	 * Get Save Pass
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public static boolean isSavePass() {
		return isSavePass(getCtx());
	}
	
	/**
	 * get Automatic Visit Closing
	 * @author Yamel Senih 31/07/2012, 12:27:03
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isAutoLogin(Context ctx) {
		return getContextAsBoolean(ctx, "#AutoLogin");
	}
	
	/**
	 * get Automatic Visit Closing
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public static boolean isAutoLogin() {
		return isAutoLogin(getCtx());
	}
	
	/**
	 * Is Auto Login Confirmed
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/10/2014, 1:10:30
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isAutoLoginConfirmed(Context ctx) {
		return getContextAsBoolean(ctx, "#IsAutoLoginConfirmed");
	}
	
	/**
	 * Is Auto Login Confirmed
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public static boolean isAutoLoginConfirmed() {
		return isAutoLoginConfirmed(getCtx());
	}
	
	/**
	 * Set database name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:55:13
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setDB_PathName(Context ctx, String value) {
		setContext(ctx, DB_NAME_KEY, value);
	}
	
	/**
	 * Set database name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param value
	 * @return void
	 */
	public static void setDB_PathName(String value) {
		setDB_PathName(getCtx(), value);
	}
	
	/**
	 * Get database Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:55:02
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getDB_PathName(Context ctx) {
		return getContext(ctx, DB_NAME_KEY);
	}
	
	/**
	 * Get database Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public static String getDB_PathName() {
		return getDB_PathName(getCtx());
	}
	
	/**
	 * Set Document Path Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 09/05/2014, 11:02:17
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setDoc_DirectoryPathName(Context ctx, String value) {
		setContext(ctx, DOC_DIRECTORY_KEY, value);
	}
	
	/**
	 * Set Document Path Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param value
	 * @return void
	 */
	public static void setDoc_DirectoryPathName(String value) {
		setDoc_DirectoryPathName(getCtx(), value);
	}
	
	/**
	 * Get Document Path Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 09/05/2014, 11:02:27
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getDoc_DirectoryPathName(Context ctx) {
		return getContext(ctx, DOC_DIRECTORY_KEY);
	}
	
	/**
	 * Get Document Path Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public static String getDoc_DirectoryPathName() {
		return getDoc_DirectoryPathName(getCtx());
	}
	
	/**
	 * Set Image Path Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 09/05/2014, 11:03:10
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setTmp_DirectoryPathName(Context ctx, String value) {
		setContext(ctx, TMP_DIRECTORY_KEY, value);
	}
	
	/**
	 * Set Image Path Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param value
	 * @return void
	 */
	public static void setTmp_DirectoryPathName(String value) {
		setTmp_DirectoryPathName(getCtx(), value);
	}
	
	/**
	 * Set Attachment Directory Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 20:36:00
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setAtt_DirectoryPathName(Context ctx, String value) {
		setContext(ctx, ATT_DIRECTORY_KEY, value);
	}
	
	/**
	 * Set Attachment Directory Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param value
	 * @return void
	 */
	public static void setAtt_DirectoryPathName(String value) {
		setAtt_DirectoryPathName(getCtx(), value);
	}
	
	/**
	 * Get Attachment Directory Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 10/11/2014, 20:36:25
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getAtt_DirectoryPathName(Context ctx) {
		return getContext(ctx, ATT_DIRECTORY_KEY);
	}

	/**
	 * Get Business Chat Directory
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getBC_DirectoryPathName(Context ctx) {
		return getContext(ctx, BC_DIRECTORY_KEY);
	}
	
	/**
	 * Get Business Chat Image Directory
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getBC_IMG_DirectoryPathName(Context ctx) {
		return getAppBaseDirectory(ctx) + BC_IMG_DIRECTORY;
	}
	
	/**
	 * Set Business Chat Directory
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setBC_DirectoryPathName(Context ctx, String value) {
		setContext(ctx, BC_DIRECTORY_KEY, value);
	}
	
	/**
	 * Set Business Chat Directory
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param value
	 * @return void
	 */
	public static void setBC_DirectoryPathName(String value) {
		setBC_DirectoryPathName(getCtx(), value);
	}
	
	/**
	 * Get Attachment Directory Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public static String getAtt_DirectoryPathName() {
		return getAtt_DirectoryPathName(getCtx());
	}
	
	/**
	 * Get Image Path Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 09/05/2014, 11:03:22
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getTmp_DirectoryPathName(Context ctx) {
		return getContext(ctx, TMP_DIRECTORY_KEY);
	}
	
	/**
	 * Get Image Path Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public static String getTmp_DirectoryPathName() {
		return getTmp_DirectoryPathName(getCtx());
	}
	
	/**
	 * Get database version
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:54:48
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int getDB_Version(Context ctx) {
		return Env.getContextAsInt(ctx, DB_VERSION);
	}	//	getAD_Role_ID
	
	/**
	 * Get database version
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public static int getDB_Version() {
		return getDB_Version(getCtx());
	}
	
	/**
	 * Get Tab Record Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/02/2014, 12:11:05
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @return
	 * @return int
	 */
	public static int[] getTabRecord_ID(Context ctx, int m_ActivityNo, int TabNo) {
		//Msg.toastMsg(ctx, ID_TAB + tab + " " + getContextAsInt(ctx, ID_TAB + tab));
		return getContextAsIntArray(ctx, m_ActivityNo, TabNo, ID_TAB);
	}
	
	/**
	 * Get Tab Record Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @return
	 * @return int[]
	 */
	public static int[] getTabRecord_ID(int m_ActivityNo, int TabNo) {
		return getTabRecord_ID(getCtx(), m_ActivityNo, TabNo);
	}
	
	/**
	 * Get Tab KeyColumns
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 15:49:25
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @return
	 * @return String[]
	 */
	public static String[] getTabKeyColumns(Context ctx, int m_ActivityNo, int TabNo) {
		return getContextAsArray(ctx, m_ActivityNo, TabNo, ID_TAB_KEYS);
	}
	
	/**
	 * Get Tab KeyColumns
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @return
	 * @return String[]
	 */
	public static String[] getTabKeyColumns(int m_ActivityNo, int TabNo) {
		return getTabKeyColumns(getCtx(), m_ActivityNo, TabNo);
	}
	

	/**
	 * Set Tab Record Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/02/2014, 12:10:54
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param record_ID
	 * @return void
	 */
	public static void setTabRecord_ID(Context ctx, int m_ActivityNo, int TabNo, int[] record_ID) {
		setContextArray(ctx, m_ActivityNo, TabNo, ID_TAB, record_ID);
	}
	
	/**
	 * Set Tab Record Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param record_ID
	 * @return void
	 */
	public static void setTabRecord_ID(int m_ActivityNo, int TabNo, int[] record_ID) {
		setTabRecord_ID(getCtx(), m_ActivityNo, TabNo, record_ID);
	}
	
	/**
	 * Set Tab Key Columns
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 18/10/2014, 15:48:49
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param keyColumns
	 * @return void
	 */
	public static void setTabKeyColumns(Context ctx, int m_ActivityNo, int TabNo, String[] keyColumns) {
		setContextArray(ctx, m_ActivityNo, TabNo, ID_TAB_KEYS, keyColumns);
	}
	
	/**
	 * Set Tab Key Columns
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param keyColumns
	 * @return void
	 */
	public static void setTabKeyColumns(int m_ActivityNo, int TabNo, String[] keyColumns) {
		setTabKeyColumns(getCtx(), m_ActivityNo, TabNo, keyColumns);
	}
	
	/**
	 * Set Current Tab
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:54:11
	 * @param ctx
	 * @param m_ActivityNo
	 * @param tab
	 * @return void
	 */
	public static void setCurrentTab(Context ctx, int m_ActivityNo, int tabNo) {
		setContext(ctx, m_ActivityNo, CURRENT_TAB, tabNo);
	}
	
	/**
	 * Set Current Tab
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param tabNo
	 * @return void
	 */
	public static void setCurrentTab(int m_ActivityNo, int tabNo) {
		setCurrentTab(getCtx(), m_ActivityNo, tabNo);
	}
	
	/**
	 * Get Current Tab
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:53:45
	 * @param ctx
	 * @param m_ActivityNo
	 * @return
	 * @return int
	 */
	public static int getCurrentTab(Context ctx, int m_ActivityNo) {
		return getContextAsInt(ctx, m_ActivityNo, CURRENT_TAB);
	}
	
	/**
	 * Get Current Tab
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @return
	 * @return int
	 */
	public static int getCurrentTab(int m_ActivityNo) {
		return getCurrentTab(getCtx(), m_ActivityNo);
	}
	
	/**
	 * Is Current Tab No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 12/04/2014, 10:22:47
	 * @param ctx
	 * @param m_ActivityNo
	 * @param tabNo
	 * @return
	 * @return boolean
	 */
	public static boolean isCurrentTab(Context ctx, int m_ActivityNo, int tabNo) {
		return tabNo == getContextAsInt(ctx, m_ActivityNo, CURRENT_TAB);
	}
	
	/**
	 * Is Current Tab No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param m_ActivityNo
	 * @param tabNo
	 * @return
	 * @return boolean
	 */
	public static boolean isCurrentTab(int m_ActivityNo, int tabNo) {
		return isCurrentTab(getCtx(), m_ActivityNo, tabNo);
	}
	
	/**
	 * Set Database version
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:53:21
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setDB_Version(Context ctx, int value) {
		if (ctx == null)
			return;
		//
		Env.setContext(ctx, DB_VERSION, value);
	}
	
	/**
	 * Set Database version
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param value
	 * @return void
	 */
	public static void setDB_Version(int value) {
		setDB_Version(getCtx(), value);
	}
	
	/**
	 * Parse Context
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 15/02/2014, 12:43:28
	 * @param ctx
	 * @param whereClause
	 * @param ignoreUnparsable
	 * @return
	 * @return String
	 */
	public static String parseContext(Context ctx, String whereClause, boolean ignoreUnparsable) {
		return parseContext(ctx, 0, 0, whereClause, ignoreUnparsable, null);
	}
	
	/**
	 * Parse Context
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param whereClause
	 * @param ignoreUnparsable
	 * @return
	 * @return String
	 */
	public static String parseContext(String whereClause, boolean ignoreUnparsable) {
		return parseContext(getCtx(), whereClause, ignoreUnparsable);
	}
	
	
	
	/**
	 *	Parse Context replaces global or Window context @tag@ with actual value.
	 *
	 *  @tag@ are ignored otherwise "" is returned
	 *  @param ctx context
	 *	@param m_ActivityNo	Number of Window
	 *	@param whereClause Message to be parsed
	 *  @param onlyWindow if true, no defaults are used
	 * 	@param ignoreUnparsable if true, unsuccessful @return parsed String or "" if not successful and ignoreUnparsable
	 *	@return parsed context 
	 */
	public static String parseContext(Context ctx, int m_ActivityNo, int m_TabNo, 
			String whereClause, boolean ignoreUnparsable,String defaultUnparseable) {
		if (whereClause == null || whereClause.length() == 0)
			return "";

		String token;
		String inStr = new String(whereClause);
		StringBuffer outStr = new StringBuffer();

		int i = inStr.indexOf('@');
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf('@');						// next @
			if (j < 0)
			{
				LogM.log(ctx, "Env", Level.INFO, "No second tag: " + inStr);
				return "";						//	no second tag
			}

			token = inStr.substring(0, j);
			//	
			String ctxInfo = getContext(ctx, m_ActivityNo, m_TabNo, token);	// get context
			if (ctxInfo != null && ctxInfo.length() == 0)
				ctxInfo = getContext(ctx, m_ActivityNo, token);	//	get from windows
			if (ctxInfo != null && ctxInfo.length() == 0 && (token.startsWith("#") || token.startsWith("$")) )
				ctxInfo = getContext(ctx, token);	// get global context
			if (ctxInfo != null && ctxInfo.length() == 0) {
				LogM.log(ctx, "Env", Level.INFO, "No Context for: " + token);
				if (!ignoreUnparsable && defaultUnparseable==null)
					return "";						//	token not found
				else if (!ignoreUnparsable && defaultUnparseable!=null)
					outStr.append(defaultUnparseable);
					//ctxInfo=defaultUnparseable;
			}
			else
				outStr.append(ctxInfo);				// replace context with Context

			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf('@');
		}
		outStr.append(inStr);						// add the rest of the string
		//	
		LogM.log(ctx, "Env", Level.FINE, "parseContext(" + inStr + ")");
		//	
		return outStr.toString();
	}	//	parseContext
	
	/**
	 *	Parse Context replaces global or Window context @tag@ with actual value.
	 *
	 *  @tag@ are ignored otherwise "" is returned
	 *  @param ctx context
	 *	@param m_ActivityNo	Number of Window
	 *	@param whereClause Message to be parsed
	 *  @param onlyWindow if true, no defaults are used
	 * 	@param ignoreUnparsable if true, unsuccessful @return parsed String or "" if not successful and ignoreUnparsable
	 *	@return parsed context 
	 */
//	public static String parseContext(int m_ActivityNo, int m_TabNo, 
//			String whereClause, boolean ignoreUnparsable,String defaultUnparseable) {
//		return parseContext(getCtx(), m_ActivityNo, m_TabNo, 
//				whereClause, ignoreUnparsable, defaultUnparseable);
//	}
	
	/**
	 * Get Default Language
	 * @author Yamel Senih 06/02/2013, 21:58:27
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getSOLanguage(Context ctx) {
		return ctx.getResources().getConfiguration().locale.getLanguage();
	}
	
	/**
	 * Get Default Language
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public static String getSOLanguage() {
		return getSOLanguage(getCtx());
	}
	
	/**
	 * Set Current Language
	 * @author Yamel Senih 06/02/2013, 22:02:13
	 * @param ctx
	 * @param language
	 * @return void
	 */
	public static void setAD_Language(Context ctx, String language) {
		setContext(ctx, LANGUAGE, language);
	}
	
	/**
	 * Set Current Language
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param language
	 * @return void
	 */
	public static void setAD_Language(String language) {
		setAD_Language(getCtx(), language);
	}
	
	/**
	 * Get System AD_Language
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 07/02/2013, 19:53:16
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getAD_Language(Context ctx) {
		return getContext(ctx, LANGUAGE);
	}
	
	/**
	 * Get System AD_Language
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public static String getAD_Language() {
		return getAD_Language(getCtx());
	}
	
	/**
	 * Get is base language
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 05/02/2014, 10:51:20
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isBaseLanguage(Context ctx) {
		String language = getAD_Language(ctx);
		if(language != null)
			return getAD_Language(ctx).equals(BASE_LANGUAGE);
		return true;
	}
	
	/**
	 * Get is base language
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return boolean
	 */
	public static boolean isBaseLanguage() {
		return isBaseLanguage(getCtx());
	}
	
	/**
	 * Change Language
	 * @author Yamel Senih 06/02/2013, 22:04:21
	 * @param ctx
	 * @param language
	 * @param metrics
	 * @return void
	 */
	public static void changeLanguage(Context ctx, String language, DisplayMetrics metrics) {
		Locale locale = Language.getLocale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        ctx.getApplicationContext().getResources().updateConfiguration(config, metrics);
	}
	
	/**
	 * Change Language
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param language
	 * @param metrics
	 * @return void
	 */
	public static void changeLanguage(String language, DisplayMetrics metrics) {
		changeLanguage(getCtx(), language, metrics);
	}
	
	/**
	 * Get Locale from language
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/02/2014, 20:04:22
	 * @param ctx
	 * @return
	 * @return Locale
	 */
	public static Locale getLocate(Context ctx) {
		String language = getAD_Language(ctx);
		if(language == null)
			language = BASE_LANGUAGE;
		return new Locale(language);
	}
	
	/**
	 * Get Locale from language
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return Locale
	 */
	public static Locale getLocate() {
		return getLocate(getCtx());
	}
	
	/**
	 * Change Language
	 * @author Yamel Senih 06/02/2013, 22:04:56
	 * @param ctx
	 * @param language
	 * @return void
	 */
	public static void changeLanguage(Context ctx, String language) {
		changeLanguage(ctx, language, null);
	}
	
	/**
	 * Change Language
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param language
	 * @return void
	 */
	public static void changeLanguage(String language) {
		changeLanguage(getCtx(), language);
	}
	
	/**
	 *  Set Date Pattern.
	 *  The date format is not checked for correctness
	 *  @param javaDatePattern for details see java.text.SimpleDateFormat,
	 *  format must be able to be converted to database date format by
	 *  using the upper case function.
	 *  It also must have leading zero for day and month.
	 */
	public static void setDateFormat(Context ctx, String javaDatePattern) {
		if (javaDatePattern == null)
			return;
		SimpleDateFormat m_dateFormat = (SimpleDateFormat)DateFormat.getDateInstance
				(DateFormat.SHORT, getLocate(ctx));
		try {
			m_dateFormat.applyPattern(javaDatePattern);
		} catch (Exception e) {
			LogM.log(ctx, "Env", Level.SEVERE, "Env.setDateFormat(Context, String)" + javaDatePattern, e);
			m_dateFormat = null;
		}
	}   //  setDateFormat
	
	/**
	 * Set Date Pattern.
	 *  The date format is not checked for correctness
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param javaDatePattern
	 * @return void
	 */
	public static void setDateFormat(String javaDatePattern) {
		setDateFormat(getCtx(), javaDatePattern);
	}

	/**
	 *  Get (Short) Date Format.
	 *  The date format must parseable by org.compiere.grid.ed.MDocDate
	 *  i.e. leading zero for date and month
	 *  @return date format MM/dd/yyyy - dd.MM.yyyy
	 */
	public static SimpleDateFormat getDateFormat(Context ctx) {
		SimpleDateFormat m_dateFormat = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT, getLocate(ctx));
		String sFormat = m_dateFormat.toPattern();
		//	some short formats have only one M and/or d (e.g. ths US)
		if (sFormat.indexOf("MM") == -1 || sFormat.indexOf("dd") == -1)
			{
			sFormat = sFormat.replaceFirst("d+", "dd");
			sFormat = sFormat.replaceFirst("M+", "MM");
		//	log.finer(sFormat + " => " + nFormat);
			m_dateFormat.applyPattern(sFormat);
		}
		//	Unknown short format => use JDBC
		if (m_dateFormat.toPattern().length() != 8)
			m_dateFormat.applyPattern("yyyy-MM-dd");

		//	4 digit year
		if (m_dateFormat.toPattern().indexOf("yyyy") == -1)
		{
			sFormat = m_dateFormat.toPattern();
			String nFormat = "";
			for (int i = 0; i < sFormat.length(); i++)
			{
				if (sFormat.charAt(i) == 'y')
					nFormat += "yy";
				else
					nFormat += sFormat.charAt(i);
			}
			m_dateFormat.applyPattern(nFormat);
		}
		m_dateFormat.setLenient(true);
		return m_dateFormat;
	}   //  getDateFormat
	
	/**
	 * Get (Short) Date Format.
	 * The date format must parseable by org.compiere.grid.ed.MDocDate
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getDateFormat() {
		return getDateFormat(getCtx());
	}

	/**
	 * 	Get Date Time Format.
	 * 	Used for Display only
	 *  @return Date Time format MMM d, yyyy h:mm:ss a z -or- dd.MM.yyyy HH:mm:ss z
	 *  -or- j nnn aaaa, H' ?????? 'm' ????'
	 */
	public static SimpleDateFormat getDateTimeFormat(Context ctx) {
		SimpleDateFormat retValue = (SimpleDateFormat)DateFormat.getDateTimeInstance
			(DateFormat.MEDIUM, DateFormat.LONG, getLocate(ctx));
	//	log.finer("Pattern=" + retValue.toLocalizedPattern() + ", Loc=" + retValue.toLocalizedPattern());
		return retValue;
	}	//	getDateTimeFormat

	/**
	 * Get Date Time Format.
	 * Used for Display only
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getDateTimeFormat() {
		return getDateTimeFormat(getCtx());
	}
	
	/**
	 * 	Get Time Format.
	 * 	Used for Display only
	 *  @return Time format h:mm:ss z or HH:mm:ss z
	 */
	public static SimpleDateFormat getTimeFormat(Context ctx) {
		return (SimpleDateFormat)DateFormat.getTimeInstance
			(DateFormat.LONG, getLocate(ctx));
	}	//	getTimeFormat
	
	/**
	 * Get Time Format.
	 * Used for Display only
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return SimpleDateFormat
	 */
	public static SimpleDateFormat getTimeFormat() {
		return getTimeFormat(getCtx());
	}
	
	/**
	 * Get Current Activity No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/03/2014, 09:15:03
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int getActivityNo(Context ctx) {
		//	Get Current Activity No
		int aNo = getContextAsInt(ctx, ACTIVITY_NO);
		//Msg.toastMsg(ctx, "ActivityNo=" + aNo);
		//	Set New
		setContext(ctx, ACTIVITY_NO, ++aNo);
		//	Return Activity
		return aNo;
	}
	
	/**
	 * Get Current Activity No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public static int getActivityNo() {
		return getActivityNo(getCtx());
	}
	
	/**
	 * Reset Activity No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/03/2014, 09:16:05
	 * @param ctx
	 * @return void
	 */
	public static void resetActivityNo(Context ctx) {
		setContext(ctx, ACTIVITY_NO, 0);
	}
	
	/**
	 * Reset Activity No
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return void
	 */
	public static void resetActivityNo() {
		resetActivityNo(getCtx());
	}
	
	/**
	 * Set App Base Directory
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 09/05/2014, 10:09:32
	 * @param ctx
	 * @param path
	 * @return void
	 */
	private  static void setAppBaseDirectory(Context ctx, String path) {
		setContext(ctx, APP_BASE_DIRECTORY_CTX_NAME, path);
	}
	
	/**
	 * Set App Base Directory
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param path
	 * @return void
	 */
	public static void setAppBaseDirectory(String path) {
		setAppBaseDirectory(getCtx(), path);
	}
	
	/**
	 * Get App Base Directory
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 09/05/2014, 10:10:18
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getAppBaseDirectory(Context ctx) {
		return getContext(ctx, APP_BASE_DIRECTORY_CTX_NAME);
	}
	
	/**
	 * Get App Base Directory
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public static String getAppBaseDirectory() {
		return getAppBaseDirectory(getCtx());
	}
	
	/**
	 * Get Resource Identifier from Attribute
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/09/2014, 16:30:25
	 * @param ctx
	 * @param att
	 * @return
	 * @return int
	 */
	public static int getResourceID(Context ctx, int att) {
		TypedValue typedValueAttr = new TypedValue();
		ctx.getTheme().resolveAttribute(att, typedValueAttr, true);
		//	Return
		return typedValueAttr.resourceId;
	}
	
	/**
	 * Get Resource
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/09/2014, 19:23:39
	 * @param ctx
	 * @param att
	 * @return
	 * @return TypeValue
	 */
	public static TypedValue getResource(Context ctx, int att) {
		TypedValue typedValueAttr = new TypedValue();
		ctx.getTheme().resolveAttribute(att, typedValueAttr, true);
		//	Return
		return typedValueAttr;
	}
	
	/**
	 * Return boolean Value for Yes/No Field
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 27/2/2015, 0:02:31
	 * @param value
	 * @return
	 * @return boolean
	 */
	public static boolean booleanValue(String value){
		if (value == null)
			return false;
		return value.equals("Y");
	}
	
	/**
	 * Set Text to Clipboard
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Ctx
	 * @param p_Text
	 * @return void
	 */
	public static void setClipboardText(Context p_Ctx, String p_Text) {
	    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
	        android.text.ClipboardManager clipB = (android.text.ClipboardManager) p_Ctx.getSystemService(Context.CLIPBOARD_SERVICE);
	        clipB.setText(p_Text);
	    } else {
	        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) p_Ctx.getSystemService(Context.CLIPBOARD_SERVICE);
	        android.content.ClipData clip = android.content.ClipData.newPlainText("", p_Text);
	        clipboard.setPrimaryClip(clip);
	    }
	}
	
	/**	Context					*/
	public static Context 				m_Ctx;
	/**	Share Preferences		*/
	public static SharedPreferences 	m_ShareP;
	/**	Env Instance			*/
	public static Env					m_Instance;
	
	/**************************************************************************
	 *  Application Context
	 */
	
	public static final String	SET_ENV = "#SET_ENV#";
	
	/** WindowNo for Main           */
	public static final int     WINDOW_MAIN = 0;
	/** WindowNo for Find           */
	public static final int     WINDOW_FIND = 1110;
	/** WinowNo for MLookup         */
	public static final int	   	WINDOW_MLOOKUP = 1111;
	/** WindowNo for PrintCustomize */
	public static final int     WINDOW_CUSTOMIZE = 1112;
	/** WindowNo for PrintCustomize */
	public static final int     WINDOW_INFO = 1113;

	/** Tab for Info                */
	public static final int     TAB_INFO = 1113;
	
	/**	New Line 		 */
	public static final String	NL = System.getProperty("line.separator");
	
	/**************************************************************************
	 *  Language issues
	 */

	/** Context Language identifier */
	public static final String      LANGUAGE 			= "#AD_Language";
	public static final String      BASE_LANGUAGE 		= "en_US";
	public static final String      BASE_COUNTRY_CODE 	= "US";
	
	/************************************Security******************************
	 * Security Access
	 */
	
	private static final String		S_PROCESS_ACCESS 	= "#PROCESS_ACCESS";
	private static final String		S_WINDOW_ACCESS 	= "#WINDOW_ACCESS";
	private static final String		S_DOCUMENT_ACCESS 	= "#DOCUMENT_ACCESS";
	private static final String		S_IS_ACCESS_LOADED 	= "#IS_ACCESS_LOADED";
	
	/************************************Env***************************************
	 * Database Context
	 */
	private static final String		DB_VERSION 			= "#DB_Version";
	private static final String		DB_NAME_KEY 		= "#DB_Name";
	private static final String		DOC_DIRECTORY_KEY 	= "#DOC_Name";
	private static final String		TMP_DIRECTORY_KEY 	= "#TMP_Name";
	private static final String		ATT_DIRECTORY_KEY 	= "#ATT_Name";
	private static final String		BC_DIRECTORY_KEY	= "#BC_Name";
	/******************************************************************************
	 * App Context
	 */
	public static final String 		APP_DIRECTORY 		= "ERP";
	public static final String 		DB_DIRECTORY 		= "data";
	public static final String 		DOC_DIRECTORY 		= APP_DIRECTORY + File.separator + "Documents";
	public static final String 		ATT_DIRECTORY 		= APP_DIRECTORY + File.separator + "Attachment";
	public static final String 		TMP_DIRECTORY 		= APP_DIRECTORY + File.separator + "Tmp";
	public static final String 		BC_DIRECTORY 		= APP_DIRECTORY + File.separator + "BChat";
	public static final String 		BC_IMG_DIRECTORY 	= BC_DIRECTORY + File.separator + "Images";
	//	Database
	public static final String 		DB_PATH_DIRECTORY 	= APP_DIRECTORY + File.separator + DB_DIRECTORY;
	public static final String		DB_PATH_NAME 		= DB_PATH_DIRECTORY + File.separator + DB.DB_NAME;
	//	Key Directory
	public static final String 		APP_BASE_DIRECTORY_CTX_NAME = "#APP_BASE_DIRECTORY_CTX_NAME";
	/***************************************************************************
	 * Prefix
	 */
	public static final String		ACTIVITY_NO 		= "|AN|";
	public static final String		CURRENT_ACTIVITY_NO = "|CAN|";
	public static final String		ID_TAB 				= "T_Record_ID";
	public static final String		ID_TAB_KEYS 		= "T_KeyColumn";
	public static final String		ID_PARENT_TAB 		= "T_P_Record_ID";
	public static final String		CURRENT_TAB 		= "|CT|";
	public static final String		SUMMARY_RECORD_ID 	= "#SummRID";
	
	/**	Big Decimal 0	 */
	public static final BigDecimal 	ZERO = new BigDecimal(0.0);
	/**	Big Decimal 1	 */
	public static final BigDecimal 	ONE = new BigDecimal(1.0);
	/**	Big Decimal 100	 */
	public static final BigDecimal 	ONEHUNDRED = new BigDecimal(100.0);
	
}
