/*************************************************************************************
 * Product: Spin-Suite (Making your Business Spin)                                   *
 * This program is free software; you can redistribute it and/or modify it    		 *
 * under the terms version 2 of the GNU General Public License as published   		 *
 * by the Free Software Foundation. This program is distributed in the hope   		 *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied 		 *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           		 *
 * See the GNU General Public License for more details.                       		 *
 * You should have received a copy of the GNU General Public License along    		 *
 * with this program; if not, write to the Free Software Foundation, Inc.,    		 *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     		 *
 * For the text or an alternative of this public license, you may reach us    		 *
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com				  		 *
 *************************************************************************************/
package org.spinsuite.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.spinsuite.base.DB;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
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
	 * Verify if is loaded environment
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:58:55
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isEnvLoad(Context ctx) {
		return getContextAsBoolean(ctx, SET_ENV);
	}
	
	/**
	 * Get is load activity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:58:42
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isLoadedActivity(Context ctx) {
		return getContextAsBoolean(ctx, "#IsLoadedActivity");
	}
	
	/**
	 * Get if is login user
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:58:25
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isLogin(Context ctx) {
		return getContextAsBoolean(ctx, "#IsLogin");
	}
	
	/**
	 * Set is initial load
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:58:13
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setIsEnvLoad(Context ctx, boolean value) {
		setContext(ctx, SET_ENV, value);
	}
	
	/**
	 * Set if loaded activity
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:57:53
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setIsLoadedActivity(Context ctx, boolean value) {
		setContext(ctx, "#IsLoadedActivity", value);
	}
	
	/**
	 * Set is login
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:57:36
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setIsLogin(Context ctx, boolean value) {
		setContext(ctx, "#IsLogin", value);
	}
	
	/**
	 * Load Role Access from Current Role
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/09/2014, 18:55:17
	 * @param ctx
	 * @return void
	 */
	public static void loadRoleAccess(Context ctx) {
		int m_AD_Role_ID = getAD_Role_ID(ctx);
		if(m_AD_Role_ID == 0
				|| isAccessLoaded(ctx, m_AD_Role_ID))
			return;
		//	Do it
		loadRoleAccess(ctx, m_AD_Role_ID);
		//	Set Loaded
		setAccessLoaded(ctx, m_AD_Role_ID, true);
	}
	
	/**
	 * Set access loaded
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/09/2014, 19:01:04
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
	 * Set Access Loaded for current role
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/09/2014, 19:08:00
	 * @param ctx
	 * @param loaded
	 * @return void
	 */
	public static void setAccessLoaded(Context ctx, boolean loaded) {
		setAccessLoaded(ctx, getAD_Role_ID(ctx), loaded);
	}
	
	/**
	 * Is Access Loaded
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/09/2014, 19:02:12
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
	 * Is Access Loaded with current role
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/09/2014, 19:09:17
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isAccessLoaded(Context ctx) {
		return isAccessLoaded(ctx, Env.getAD_Role_ID(ctx));
	}
	
	/**
	 * Load Role Access
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/09/2014, 16:31:03
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
	 * Get Process Access with Role
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/09/2014, 19:12:38
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
	 * Get Process Access without Role
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/09/2014, 19:13:26
	 * @param ctx
	 * @param m_AD_Process_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getProcessAccess(Context ctx, int m_AD_Process_ID) {
		return getProcessAccess(ctx, getAD_Role_ID(ctx), m_AD_Process_ID);
	}
	
	
	/**
	 * Get Windows Access with Role
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/09/2014, 19:15:37
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
	 * Get Valid DocAction
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/09/2014, 11:46:20
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
	 * Get Valid DocAction without Role
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/09/2014, 11:47:34
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
	 * Get Windows Access without Role
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/09/2014, 19:13:26
	 * @param ctx
	 * @param m_SPS_Window_ID
	 * @return
	 * @return boolean
	 */
	public static boolean getWindowsAccess(Context ctx, int m_SPS_Window_ID) {
		return getWindowsAccess(ctx, getAD_Role_ID(ctx), m_SPS_Window_ID);
	}
	
	/**
	 * Cache Reset
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/09/2014, 17:33:30
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int cacheReset(Context ctx) {
		return cacheReset(ctx, "#", true);
	}
	
	/**
	 * Cache Reset
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/09/2014, 17:23:40
	 * @param ctx
	 * @param m_Prefix
	 * @param m_IgnorePrefix
	 * @return
	 * @return int
	 */
	private static int cacheReset(Context ctx, String m_Prefix, boolean m_IgnorePrefix) {
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:57:10
	 * @param ctx
	 * @return
	 * @return Editor
	 */
	private static Editor getEditor(Context ctx) {
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		return pf.edit();
	}
	
	/**
	 * Remove a context value
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/09/2014, 16:13:50
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
	 * Set Context Object
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/05/2014, 14:41:53
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
	 * Set Context Object with Activity No and Tab No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/10/2014, 14:42:48
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
	 * Set Context Object
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/05/2014, 14:13:23
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
	 * Get Context Object with Activity No and Tab No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/10/2014, 14:55:22
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
	
	
	/*public static void setContextObject(Context ctx, String context, Object value, boolean isExternal) {
		if(ctx == null
				|| context == null
				|| value == null) {
			return;
		}
		//	Get Cache Dir
		File cacheDir = ctx.getCacheDir();
		//	Valid if Exists
	    if(!cacheDir.exists())
	        cacheDir.mkdirs();
	    //	
	    File fileName = new File(cacheDir, context);
	    FileOutputStream fos = null;
	    fos = new FileOutputStream(fileName.getAbsolutePath());
	    fos.write(value.getBytes());
	    fos.close();
	}*/
	
	/**
	 * Set Context
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:56:56
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, String context, int value) {
		if (ctx == null || context == null)
			return;
		LogM.log(ctx, "Env", Level.INFO, "setContext(" + context+", " + value);
		Editor ep = getEditor(ctx);
		ep.putString(context, String.valueOf(value));
		ep.commit();
	}	//	setContext
	
	
	/**
	 *	Set Context for Window & Tab to Value
	 *  @param ctx context
	 *  @param m_ActivityNo window no
	 *  @param TabNo tab no
	 *  @param context context key
	 *  @param value context value
	 *   */
	public static void setContext (Context ctx, int m_ActivityNo, int TabNo, String context, String value)
	{
		if (ctx == null || context == null)
			return;
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
	 * Set Context Array Int
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 15:45:00
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
	 * Set Context Array
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 15:42:54
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
	 * Set Context as boolean
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/03/2014, 10:40:04
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, int m_ActivityNo, int TabNo, String context, boolean value) {
		if (ctx == null || context == null)
			return;
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "==" + value);
		//	
		setContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	Set Context
	
	/**
	 * Set Context as Boolean
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 22/05/2014, 17:01:51
	 * @param ctx
	 * @param m_ActivityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, int m_ActivityNo, String context, boolean value) {
		if (ctx == null || context == null)
			return;
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+") " + context + "==" + value);
		//
		setContext(ctx, m_ActivityNo+"|"+context, value);
	}	//	Set Context
	/**
	 * Set Context with Activity No and Tab No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 13:16:11
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, int m_ActivityNo, int TabNo, String context, int value)
	{
		if (ctx == null || context == null)
			return;
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+","+TabNo+") " + context + "=" + value);
		//	
		setContext(ctx, m_ActivityNo+"|"+TabNo+"|"+context, value);
	}	//	setContext
	
	/**
	 * Set Context with Activity No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 13:18:28
	 * @param ctx
	 * @param m_ActivityNo
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, int m_ActivityNo, String context, int value) {
		if (ctx == null || context == null)
			return;
		if (m_ActivityNo != WINDOW_FIND && m_ActivityNo != WINDOW_MLOOKUP)
			LogM.log(ctx, "Env", Level.FINE, "Context("+m_ActivityNo+") " + context + "=" + value);
		//	
		setContext(ctx, m_ActivityNo+"|"+context, value);
	}	//	setContext
	
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
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			LogM.log(ctx, "Env", Level.SEVERE, "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt
	
	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param m_ActivityNo window no
	 *  @param context context key
	 *  @param onlyWindow  if true, no defaults are used unless explicitly asked for
	 *  @return value or 0
	 */
	public static int getContextAsInt(Context ctx, int m_ActivityNo, String context, boolean onlyWindow)
	{
		String s = getContext(ctx, m_ActivityNo, context, onlyWindow);
		if (s == null || s.length() == 0)
			return 0;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			LogM.log(ctx, "Env", Level.SEVERE, "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt
	
	/**
	 *	Get Context and convert it to an integer (0 if error)
	 *  @param ctx context
	 *  @param WindowNo window no
	 *  @param TabNo tab no
	 * 	@param context context key
	 *  @return value or 0
	 */
	public static int getContextAsInt (Context ctx, int WindowNo, int TabNo, String context)
	{
		String s = getContext(ctx, WindowNo, TabNo, context);
		if (s == null || s.length() == 0)
			return 0;
		//
		try
		{
			return Integer.parseInt(s);
		}
		catch (NumberFormatException e)
		{
			LogM.log(ctx, "Env", Level.SEVERE, "(" + context + ") = " + s, e);
		}
		return 0;
	}	//	getContextAsInt
	
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
	 *	Get global Value of Context
	 *  @param ctx context
	 *  @param context context key
	 *  @return value or ""
	 */
	public static String getContext(Context ctx, String context) {
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		String value = pf.getString(context, null);
		LogM.log(ctx, "Env", Level.FINE, "getContext(" + context + ") = " + value);
		return value;
	}	//	getContext
	
	/**
	 * Get Date with format to
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:56:26
	 * @param ctx
	 * @param context
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @return String
	 */
	public static String getContextDateFormat(Context ctx, String context, String fromFormat, String toFormat)
	{
		String dateS = getContext(ctx, context);
		
		return getDateFormatString(dateS, fromFormat, toFormat);
	}	//	getContext
	
	/**
	 * Get Date with format
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:56:08
	 * @param dateS
	 * @param fromFormat
	 * @param toFormat
	 * @return
	 * @return String
	 */
	public static String getDateFormatString(String dateS, String fromFormat, String toFormat)
	{
		
		/*Date date=new Date(dateS);
	    SimpleDateFormat fmt=new SimpleDateFormat(format);
	    return fmt.format(date);
		*/
	    SimpleDateFormat fmtFront=new SimpleDateFormat(fromFormat);
        SimpleDateFormat fmtBack=new SimpleDateFormat(toFormat);
        
        
        Date date;
		try {
			date = fmtFront.parse(dateS);
			return fmtBack.format(date);
		} catch (ParseException e) {
		}       
        return null;
	    
	}	//	getContext
	
	
	public static Date getDateFormat(String dateS, String fromFormat, String toFormat)
	{
		
		/*Date date=new Date(dateS);
	    SimpleDateFormat fmt=new SimpleDateFormat(format);
	    return fmt.format(date);
		*/
	    SimpleDateFormat fmtFront=new SimpleDateFormat(fromFormat);
        //SimpleDateFormat fmtBack=new SimpleDateFormat(toFormat);
        
        
        Date date;
		try {
			date = fmtFront.parse(dateS);
			return date;
		} catch (ParseException e) {
		}       
        return null;
	    
	}	//	getContext
	
	/**
	 * Get Current date formated
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:55:48
	 * @param format
	 * @return
	 * @return String
	 */
	public static String getCurrentDateFormat(String format)
	{
		Date date=new Date();
	    SimpleDateFormat fmt=new SimpleDateFormat(format);
	    return fmt.format(date);
		
	}	//	getContext
	
	/**
	 * Get Current Date
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 10/02/2014, 23:00:43
	 * @return
	 * @return Date
	 */
	public static Date getCurrentDate()
	{
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
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		
		String s = getContext(ctx, context);
		//	
		boolean valid = (s != null && s.equals("Y"));
		//	Log
		LogM.log(ctx, "Env", Level.FINE, "getContextAsBoolean(" + context + ") = " + valid);
		return valid;
	}	//	getContext
	
	/**
	 * Get Context as boolean
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/03/2014, 11:11:31
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean (Context ctx, int m_ActivityNo, int TabNo, String context) {
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		String s = pf.getString(m_ActivityNo+"|"+TabNo+"|"+context, null);
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
	 * Get Context as Boolean with Activity No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/05/2014, 12:02:34
	 * @param ctx
	 * @param m_ActivityNo
	 * @param context
	 * @return
	 * @return boolean
	 */
	public static boolean getContextAsBoolean (Context ctx, int m_ActivityNo, String context)
	{
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		String s = pf.getString(m_ActivityNo+"|"+context, null);
		LogM.log(ctx, "Env", Level.INFO, "getContext=" + m_ActivityNo+"|"+context);
		// If TAB_INFO, don't check Window and Global context - teo_sarca BF [ 2017987 ]
		//
		if (s == null)
			s = getContext(ctx, m_ActivityNo, context);
		return s != null ? s.equals("Y") : false;
	}	//	getContext
	
	/**
	 *	Set Global Context to Y/N Value
	 *  @param ctx context
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Context ctx, String context, boolean value) {
		setContext(ctx, context, value? "Y": "N");
	}	//	setContext

	/**
	 *	Set Context for Window to Value
	 *  @param ctx context
	 *  @param m_ActivityNo window no
	 *  @param context context key
	 *  @param value context value
	 */
	public static void setContext (Context ctx, int m_ActivityNo, String context, String value) {
		if (ctx == null || context == null)
			return;
		Editor ed = getEditor(ctx);
		if (value == null)
			value = "";
		ed.putString(m_ActivityNo+"|"+context, value);
		ed.commit();
	}	//	setContext

	
	/**
	 *	Get Value of Context for Window.
	 *	if not found global context if available and enabled
	 *  @param ctx context
	 *  @param m_ActivityNo window
	 *  @param context context key
	 *  @param  onlyWindow  if true, no defaults are used unless explicitly asked for
	 *  @return value or ""
	 */
	public static String getContext (Context ctx, int m_ActivityNo, String context, boolean onlyWindow) {
		if (ctx == null)
			throw new IllegalArgumentException ("No Ctx");
		if (context == null)
			throw new IllegalArgumentException ("Require Context");
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		String s = pf.getString(m_ActivityNo+"|"+context, null);
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
	 *	Get Value of Context for Window.
	 *	if not found global context if available
	 *  @param ctx context
	 *  @param m_ActivityNo window
	 *  @param context context key
	 *  @return value or ""
	 */
	public static String getContext (Context ctx, int m_ActivityNo, String context) {
		return getContext(ctx, m_ActivityNo, context, false);
	}	//	getContext

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
	public static String getContext (Context ctx, int m_ActivityNo, int TabNo, String context) {
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		String s = pf.getString(m_ActivityNo+"|"+TabNo+"|"+context, null);
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
	 * Get Context As Array
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 15:15:20
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param context
	 * @return
	 * @return String[]
	 */
	public static String[] getContextAsArray (Context ctx, String context) {
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		//	
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
	 * Get Context As Int Array
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 15:35:26
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
	 * Get Context as Array with Activity and tab
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 15:18:56
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
	 * Get Context as Int Array with Activity and tab
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 15:36:03
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
	 * Set Context Array
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 15:26:05
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, String context, String[] value) {
		if (ctx == null || context == null)
			return;
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
	 * Set Context As Int Array
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 15:40:51
	 * @param ctx
	 * @param context
	 * @param value
	 * @return void
	 */
	public static void setContext (Context ctx, String context, int[] value) {
		String[] strValue = new String[value.length];
		for(int i = 0; i < value.length; i++) {
			strValue[i] = String.valueOf(value[i]);
		}
		//	
		setContext(ctx, context, strValue);
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
	public static String getContext (Context ctx, int m_ActivityNo, int TabNo, String context, boolean onlyTab) {
		final boolean onlyWindow = onlyTab ? true : false;
		return getContext(ctx, m_ActivityNo, TabNo, context, onlyTab, onlyWindow);
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
	public static String getContext (Context ctx, int m_ActivityNo, int TabNo, String context, boolean onlyTab, boolean onlyWindow) {
		if (ctx == null || context == null)
			throw new IllegalArgumentException ("Require Context");
		SharedPreferences pf = PreferenceManager.getDefaultSharedPreferences(ctx);
		String s = pf.getString(m_ActivityNo+"|"+TabNo+"|"+context, null);
		if (TAB_INFO == TabNo)
			return s != null ? s : "";
		//
		if (s == null && ! onlyTab)
			return getContext(ctx, m_ActivityNo, context, onlyWindow);
		return s;
	}	//	getContext

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
	 *	Is Sales Order Trx
	 *  @param ctx context
	 *  @return true if SO (default)
	 */
	public static boolean isSOTrx (Context ctx) {
		return getContextAsBoolean(ctx, "IsSOTrx");
	}	//	isSOTrx
	
	/**
	 * 	Get Login AD_Client_ID
	 *	@param ctx context
	 *	@return login AD_Client_ID
	 */
	public static int getAD_Client_ID (Context ctx) {
		return getContextAsInt(ctx, "#AD_Client_ID");
	}	//	getAD_Client_ID

	/**
	 * 	Get Login AD_Org_ID
	 *	@param ctx context
	 *	@return login AD_Org_ID
	 */
	public static int getAD_Org_ID (Context ctx) {
		return getContextAsInt(ctx, "#AD_Org_ID");
	}	//	getAD_Client_ID

	/**
	 * 	Get Login AD_User_ID
	 *	@param ctx context
	 *	@return login AD_User_ID
	 */
	public static int getAD_User_ID (Context ctx) {
		return getContextAsInt(ctx, "#AD_User_ID");
	}	//	getAD_User_ID
	
	/**
	 * 	Get Login AD_Role_ID
	 *	@param ctx context
	 *	@return login AD_Role_ID
	 */
	public static int getAD_Role_ID (Context ctx) {
		return getContextAsInt(ctx, "#AD_Role_ID");
	}	//	getAD_Role_ID

	/**
	 * Get Login M_Warehouse
	 * @author Yamel Senih 26/04/2012, 15:49:16
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int getM_Warehouse_ID (Context ctx) {
		return getContextAsInt(ctx, "#M_Warehouse_ID");
	}	//	getAD_Role_ID
	
	
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
	 * Set Auto Login Confirmed
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/10/2014, 1:11:40
	 * @param ctx
	 * @param isAutoLoginConfirmed
	 * @return void
	 */
	public static void setAutoLoginComfirmed(Context ctx, boolean isAutoLoginConfirmed) {
		setContext(ctx, "#IsAutoLoginConfirmed", isAutoLoginConfirmed);
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
	 * Is Auto Login Confirmed
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/10/2014, 1:10:30
	 * @param ctx
	 * @return
	 * @return boolean
	 */
	public static boolean isAutoLoginConfirmed(Context ctx) {
		return getContextAsBoolean(ctx, "#IsAutoLoginConfirmed");
	}
	
	/**
	 * Set database name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:55:13
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setDB_PathName(Context ctx, String value) {
		setContext(ctx, DB_NAME_KEY, value);
	}
	
	/**
	 * Get database Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:55:02
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getDB_PathName(Context ctx) {
		return getContext(ctx, DB_NAME_KEY);
	}
	
	/**
	 * Set Document Path Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 09/05/2014, 11:02:17
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setDoc_DirectoryPathName(Context ctx, String value) {
		setContext(ctx, DOC_DIRECTORY_KEY, value);
	}
	
	/**
	 * Get Document Path Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 09/05/2014, 11:02:27
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getDoc_DirectoryPathName(Context ctx) {
		return getContext(ctx, DOC_DIRECTORY_KEY);
	}
	
	/**
	 * Set Image Path Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 09/05/2014, 11:03:10
	 * @param ctx
	 * @param value
	 * @return void
	 */
	public static void setImg_DirectoryPathName(Context ctx, String value) {
		setContext(ctx, IMG_DIRECTORY_KEY, value);
	}
	
	/**
	 * Get Image Path Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 09/05/2014, 11:03:22
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getImg_DirectoryPathName(Context ctx) {
		return getContext(ctx, IMG_DIRECTORY_KEY);
	}
	
	/**
	 * Get database version
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:54:48
	 * @param ctx
	 * @return
	 * @return int
	 */
	public static int getDB_Version(Context ctx) {
		return Env.getContextAsInt(ctx, DB_VERSION);
	}	//	getAD_Role_ID
	
	/**
	 * Get Tab Record Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 12:11:05
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
	 * Get Tab KeyColumns
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 15:49:25
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @return
	 * @return String[]
	 */
	public static String[] getTabKeyColumns(Context ctx, int m_ActivityNo, int TabNo) {
		//Msg.toastMsg(ctx, ID_TAB + tab + " " + getContextAsInt(ctx, ID_TAB + tab));
		return getContextAsArray(ctx, m_ActivityNo, TabNo, ID_TAB_KEYS);
	}
	

	/**
	 * Set Tab Record Identifier
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 12:10:54
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param record_ID
	 * @return void
	 */
	public static void setTabRecord_ID(Context ctx, int m_ActivityNo, int TabNo, int[] record_ID) {
		//Msg.toastMsg(ctx, ID_TAB + tab + " " + record_ID);
		setContextArray(ctx, m_ActivityNo, TabNo, ID_TAB, record_ID);
	}
	
	/**
	 * Set Tab Key Columns
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 18/10/2014, 15:48:49
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param keyColumns
	 * @return void
	 */
	public static void setTabKeyColumns(Context ctx, int m_ActivityNo, int TabNo, String[] keyColumns) {
		//Msg.toastMsg(ctx, ID_TAB + tab + " " + record_ID);
		setContextArray(ctx, m_ActivityNo, TabNo, ID_TAB_KEYS, keyColumns);
	}
	
	/**
	 * Set Current Tab
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:54:11
	 * @param ctx
	 * @param m_ActivityNo
	 * @param tab
	 * @return void
	 */
	public static void setCurrentTab(Context ctx, int m_ActivityNo, int tabNo) {
		setContext(ctx, m_ActivityNo, CURRENT_TAB, tabNo);
	}
	
	/**
	 * Get Current Tab
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:53:45
	 * @param ctx
	 * @param m_ActivityNo
	 * @return
	 * @return int
	 */
	public static int getCurrentTab(Context ctx, int m_ActivityNo) {
		return getContextAsInt(ctx, m_ActivityNo, CURRENT_TAB);
	}
	
	/**
	 * Is Current Tab No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 12/04/2014, 10:22:47
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
	 * Set Database version
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/02/2014, 21:53:21
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
	 * Parse Context
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/02/2014, 12:43:28
	 * @param ctx
	 * @param whereClause
	 * @param ignoreUnparsable
	 * @return
	 * @return String
	 */
	public static String parseContext (Context ctx, String whereClause, boolean ignoreUnparsable) {
		return parseContext(ctx, 0, 0, whereClause, ignoreUnparsable,null);
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
	 *  @param ctx context
	 *	@param	WindowNo	Number of Window
	 *	@param	value		Message to be parsed
	 *  @param  onlyWindow  if true, no defaults are used
	 *  @return parsed String or "" if not successful
	 */
	/*public static String parseContext (Properties ctx, int WindowNo, String value,
		boolean onlyWindow)
	{
		return parseContext(ctx, WindowNo, value, onlyWindow, false);
	}	//	parseContext*/
	
	/**
	 * Parse expression, replaces global or PO properties @tag@ with actual value. 
	 * @param expression
	 * @param po
	 * @param trxName
	 * @return String
	 */
	/*public static String parseVariable(String expression, PO po, String trxName, boolean keepUnparseable) {
		if (expression == null || expression.length() == 0)
			return "";

		String token;
		String inStr = new String(expression);
		StringBuffer outStr = new StringBuffer();

		int i = inStr.indexOf('@');
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf('@');						// next @
			if (j < 0)
			{
				Log.d("No second tag: ", inStr);
				return "";						//	no second tag
			}

			token = inStr.substring(0, j);
			
			//format string
			String format = "";
			int f = token.indexOf('<');
			if (f > 0 && token.endsWith(">")) {
				format = token.substring(f+1, token.length()-1);
				token = token.substring(0, f);
			}
			
			if (token.startsWith("#") || token.startsWith("$")) {
				//take from context
				Properties ctx = po != null ? po.getCtx() : Env.getCtx();
				String v = Env.getContext(ctx, token);
				if (v != null && v.length() > 0)
					outStr.append(v);
				else if (keepUnparseable)
					outStr.append("@"+token+"@");
			} else if (po != null) {
				//take from po
				Object v = po.get_Value(token);
				if (v != null) {
					if (format != null && format.length() > 0) {
						if (v instanceof Integer && token.endsWith("_ID")) {
							int tblIndex = format.indexOf(".");
							String table = tblIndex > 0 ? format.substring(0, tblIndex) : token.substring(0, token.length() - 3);
							String column = tblIndex > 0 ? format.substring(tblIndex + 1) : format;
							outStr.append(DB.getSQLValueString(trxName, 
									"select " + column + " from  " + table + " where " + table + "_id = ?", (Integer)v));
						} else if (v instanceof Date) {
							SimpleDateFormat df = new SimpleDateFormat(format);
							outStr.append(df.format((Date)v));
						} else if (v instanceof Number) {
							DecimalFormat df = new DecimalFormat(format);
							outStr.append(df.format(((Number)v).doubleValue()));
						} else {
							MessageFormat mf = new MessageFormat(format);
							outStr.append(mf.format(v));
						}
					} else {
						outStr.append(v.toString());
					}
				}
				else if (keepUnparseable) {
					outStr.append("@"+token+"@");
				}
			}

			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf('@');
		}
		outStr.append(inStr);						// add the rest of the string

		return outStr.toString();
	}*/
	
	/**
	 * Get Default Language
	 * @author Yamel Senih 06/02/2013, 21:58:27
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getSOLanguage(Context ctx) {
		return ctx.getResources().getConfiguration().locale.getDisplayName();
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
	 * Get System AD_Language
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 07/02/2013, 19:53:16
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getAD_Language(Context ctx) {
		return getContext(ctx, LANGUAGE);
	}
	
	/**
	 * Get is base language
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 05/02/2014, 10:51:20
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
	 * Change Language
	 * @author Yamel Senih 06/02/2013, 22:04:21
	 * @param ctx
	 * @param language
	 * @param metrics
	 * @return void
	 */
	public static void changeLanguage(Context ctx, String language, DisplayMetrics metrics) {
		Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        ctx.getApplicationContext().getResources().updateConfiguration(config, metrics);
	}
	
	/**
	 * Get Locale from language
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 20:04:22
	 * @param ctx
	 * @return
	 * @return Locale
	 */
	public static Locale getLocate(Context ctx) {
		return new Locale(getAD_Language(ctx));
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
	 *  Set Date Pattern.
	 *  The date format is not checked for correctness
	 *  @param javaDatePattern for details see java.text.SimpleDateFormat,
	 *  format must be able to be converted to database date format by
	 *  using the upper case function.
	 *  It also must have leading zero for day and month.
	 */
	public static void setDateFormat (Context ctx, String javaDatePattern)
	{
		if (javaDatePattern == null)
			return;
		SimpleDateFormat m_dateFormat = (SimpleDateFormat)DateFormat.getDateInstance
				(DateFormat.SHORT, getLocate(ctx));
		try
		{
			m_dateFormat.applyPattern(javaDatePattern);
		}
		catch (Exception e)
		{
			LogM.log(ctx, "Env", Level.SEVERE, "Env.setDateFormat(Context, String)" + javaDatePattern, e);
			m_dateFormat = null;
		}
	}   //  setDateFormat

	/**
	 *  Get (Short) Date Format.
	 *  The date format must parseable by org.compiere.grid.ed.MDocDate
	 *  i.e. leading zero for date and month
	 *  @return date format MM/dd/yyyy - dd.MM.yyyy
	 */
	public static SimpleDateFormat getDateFormat(Context ctx)
	{
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
	 * 	Get Date Time Format.
	 * 	Used for Display only
	 *  @return Date Time format MMM d, yyyy h:mm:ss a z -or- dd.MM.yyyy HH:mm:ss z
	 *  -or- j nnn aaaa, H' ?????? 'm' ????'
	 */
	public static SimpleDateFormat getDateTimeFormat(Context ctx)
	{
		SimpleDateFormat retValue = (SimpleDateFormat)DateFormat.getDateTimeInstance
			(DateFormat.MEDIUM, DateFormat.LONG, getLocate(ctx));
	//	log.finer("Pattern=" + retValue.toLocalizedPattern() + ", Loc=" + retValue.toLocalizedPattern());
		return retValue;
	}	//	getDateTimeFormat

	/**
	 * 	Get Time Format.
	 * 	Used for Display only
	 *  @return Time format h:mm:ss z or HH:mm:ss z
	 */
	public static SimpleDateFormat getTimeFormat(Context ctx)
	{
		return (SimpleDateFormat)DateFormat.getTimeInstance
			(DateFormat.LONG, getLocate(ctx));
	}	//	getTimeFormat
	
	/**
	 * Get Current Activity No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/03/2014, 09:15:03
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
	 * Reset Activity No
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 16/03/2014, 09:16:05
	 * @param ctx
	 * @return void
	 */
	public static void resetActivityNo(Context ctx) {
		setContext(ctx, ACTIVITY_NO, 0);
	}
	
	/**
	 * Set App Base Directory
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 09/05/2014, 10:09:32
	 * @param ctx
	 * @param path
	 * @return void
	 */
	public static void setAppBaseDirectory(Context ctx, String path) {
		setContext(ctx, APP_BASE_DIRECTORY_CTX_NAME, path);
	}
	
	/**
	 * Get App Base Directory
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 09/05/2014, 10:10:18
	 * @param ctx
	 * @return
	 * @return String
	 */
	public static String getAppBaseDirectory(Context ctx) {
		return getContext(ctx, APP_BASE_DIRECTORY_CTX_NAME);
	}
	
	/**
	 * Get Resource Identifier from Attribute
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 01/09/2014, 16:30:25
	 * @param ctx
	 * @param att
	 * @return
	 * @return int
	 */
	public static int getResourceID(Context ctx, int att) {
		if(ctx == null)
			throw new IllegalArgumentException ("Require Context");
		//	
		TypedValue typedValueAttr = new TypedValue();
		ctx.getTheme().resolveAttribute(att, typedValueAttr, true);
		//	Return
		return typedValueAttr.resourceId;
	}
	
	/**
	 * Get Resource
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 17/09/2014, 19:23:39
	 * @param ctx
	 * @param att
	 * @return
	 * @return TypeValue
	 */
	public static TypedValue getResource(Context ctx, int att) {
		if(ctx == null)
			throw new IllegalArgumentException ("Require Context");
		//	
		TypedValue typedValueAttr = new TypedValue();
		ctx.getTheme().resolveAttribute(att, typedValueAttr, true);
		//	Return
		return typedValueAttr;
	}
	
	/**************************************************************************
	 *  Application Context
	 */
	
	private static final String	SET_ENV = "#SET_ENV#";
	
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
	private static final String		IMG_DIRECTORY_KEY 	= "#IMG_Name";
	/******************************************************************************
	 * App Context
	 */
	public static final String 		APP_DIRECTORY 		= "ERP";
	public static final String 		DB_DIRECTORY 		= "data";
	public static final String 		DOC_DIRECTORY 		= APP_DIRECTORY + File.separator + "Documents";
	public static final String 		IMG_DIRECTORY 		= APP_DIRECTORY + File.separator + "Images";
	public static final String 		TMP_DIRECTORY 		= APP_DIRECTORY + File.separator + "Tmp";
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