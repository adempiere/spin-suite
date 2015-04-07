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
package org.spinsuite.model;

import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MSysConfig extends X_AD_SysConfig {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 10:41:06
	 * @param ctx
	 * @param AD_SysConfig_ID
	 * @param conn
	 */
	public MSysConfig(Context ctx, int AD_SysConfig_ID, DB conn) {
		super(ctx, AD_SysConfig_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 10:41:06
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MSysConfig(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}

	/**
	 * Get system configuration property of type string
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @return String
	 */
	public static String getValue(Context ctx, String Name, String defaultValue) {
		return getValue(ctx, Name, defaultValue, 0, 0);
	}
	
	/**
	 * Get system configuration property of type string with connection
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 1/11/2014, 13:08:13
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @param conn
	 * @return
	 * @return String
	 */
	public static String getValue(Context ctx, String Name, String defaultValue, DB conn) {
		return getValue(ctx, Name, defaultValue, 0, 0, conn);
	}
	
	/**
	 * Get system configuration property of type string
	 * @param ctx
	 * @param Name
	 * @return String
	 */
	public static String getValue(Context ctx, String Name) {
		return getValue(ctx, Name, null, null);
	}
	
	/**
	 * Get system configuration property of type string with connection
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 1/11/2014, 13:09:44
	 * @param ctx
	 * @param Name
	 * @param conn
	 * @return
	 * @return String
	 */
	public static String getValue(Context ctx, String Name, DB conn) {
		return getValue(ctx, Name, null, conn);
	}
	
	/**
	 * Get system configuration property of type int
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @return int
	 */
	public static int getIntValue(Context ctx, String Name, int defaultValue) {
		String s = getValue(ctx, Name);
		if (s == null)
			return defaultValue; 
		
		if (s.length() == 0)
			return defaultValue;
		//
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LogM.log(ctx, MSysConfig.class, Level.SEVERE, "getIntValue (" + Name + ") = " + s, e);
		}
		return defaultValue;
	}
	
	/**
	 * Get system configuration property of type double
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @return double
	 */
	public static double getDoubleValue(Context ctx, String Name, double defaultValue) {
		String s = getValue(ctx, Name);
		if (s == null || s.length() == 0)
			return defaultValue;
		//
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			LogM.log(ctx, MSysConfig.class, Level.SEVERE, "getDoubleValue (" + Name + ") = " + s, e);
		}
		return defaultValue;
	}
	
	/**
	 * Get system configuration property of type boolean
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @return boolean
	 */
	public static boolean getBooleanValue(Context ctx, String Name, boolean defaultValue, DB conn) {
		String s = getValue(ctx, Name, conn);
		if (s == null || s.length() == 0)
			return defaultValue;
		
		if ("Y".equalsIgnoreCase(s))
			return true;
		else if ("N".equalsIgnoreCase(s))
			return false;
		else
			return Boolean.valueOf(s).booleanValue();
	}
	
	/***
	 * Get Boolean Value with connection
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 1/11/2014, 13:11:18
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @return
	 * @return boolean
	 */
	public static boolean getBooleanValue(Context ctx, String Name, boolean defaultValue) {
		return getBooleanValue(ctx, Name, defaultValue, null);
	}
	
	/**
	 * Get client configuration property of type string
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @return String
	 */
	public static String getValue(Context ctx, String Name, String defaultValue, int AD_Client_ID) {
		return getValue(ctx, Name, defaultValue, AD_Client_ID, 0);
	}
	
	/**
	 * Get system configuration property of type string
	 * @param ctx
	 * @param Name
	 * @param Client ID
	 * @return String
	 */
	public static String getValue(Context ctx, String Name, int AD_Client_ID) {
		return (getValue(ctx, Name, null, AD_Client_ID));
	}
	
	/**
	 * Get system configuration property of type int
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @return int
	 */
	public static int getIntValue(Context ctx, String Name, int defaultValue, int AD_Client_ID) {
		String s = getValue(ctx, Name, AD_Client_ID);
		if (s == null)
			return defaultValue; 
		
		if (s.length() == 0)
			return defaultValue;
		//
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LogM.log(ctx, MSysConfig.class, Level.SEVERE, "getIntValue (" + Name + ") = " + s, e);
		}
		return defaultValue;
	}
	
	/**
	 * Get system configuration property of type double
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @return double
	 */
	public static double getDoubleValue(Context ctx, String Name, double defaultValue, int AD_Client_ID) {
		String s = getValue(ctx, Name, AD_Client_ID);
		if (s == null || s.length() == 0)
			return defaultValue;
		//
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			LogM.log(ctx, MSysConfig.class, Level.SEVERE, "getDoubleValue (" + Name + ") = " + s, e);
		}
		return defaultValue;
	}
	
	/**
	 * Get system configuration property of type boolean
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @return boolean
	 */
	public static boolean getBooleanValue(Context ctx, String Name, boolean defaultValue, int AD_Client_ID) {
		String s = getValue(ctx, Name, AD_Client_ID);
		if (s == null || s.length() == 0)
			return defaultValue;
		
		if ("Y".equalsIgnoreCase(s))
			return true;
		else if ("N".equalsIgnoreCase(s))
			return false;
		else
			return Boolean.valueOf(s).booleanValue();
	}

	/**
	 * Get client configuration property of type string
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @param Organization ID
	 * @return String
	 */
	public static String getValue(Context ctx, String Name, String defaultValue, int AD_Client_ID, int AD_Org_ID, DB conn) {
		String key = "#SysConfig|" + AD_Client_ID + "_" + AD_Org_ID + "_" + Name;
		String str = Env.getContext(key);
		if (str != null)
			return str;
		//	
		try {
			str = DB.getSQLValueStringEx(ctx, "SELECT Value FROM AD_SysConfig"
					+ " WHERE Name = ? AND AD_Client_ID IN (0, ?) AND AD_Org_ID IN (0, ?) AND IsActive='Y'"
					+ " ORDER BY AD_Client_ID DESC, AD_Org_ID DESC", conn, 
					new String[]{Name, String.valueOf(AD_Client_ID), String.valueOf(AD_Org_ID)});
		} catch (Exception e) {
			LogM.log(ctx, MSysConfig.class, Level.SEVERE, "getValue", e);
		}
		//
		if (str != null) {
			str = str.trim();
			Env.setContext(key, str);
			return str;
		} else {
			// anyways, put the not found key as null
			Env.setContext(key, str);
			return defaultValue;
		}
	}
	
	/**
	 * Get client configuration property of type string
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:20:25
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @param AD_Client_ID
	 * @param AD_Org_ID
	 * @return
	 * @return String
	 */
	public static String getValue(Context ctx, String Name, String defaultValue, int AD_Client_ID, int AD_Org_ID) {
		return getValue(ctx, Name, defaultValue, AD_Client_ID, AD_Org_ID, null);
	}
	
	/**
	 * Get system configuration property of type string
	 * @param ctx
	 * @param Name
	 * @param Client ID
	 * @param Organization ID
	 * @return String
	 */
	public static String getValue(Context ctx, String Name, int AD_Client_ID, int AD_Org_ID) {
		return getValue(ctx, Name, null, AD_Client_ID, AD_Org_ID);
	}
	
	/**
	 * Get system configuration property of type int
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @param Organization ID
	 * @param conn
	 * @return int
	 */
	public static int getIntValue(Context ctx, String Name, int defaultValue, int AD_Client_ID, int AD_Org_ID, DB conn) {
		String s = getValue(ctx, Name, null, AD_Client_ID, AD_Org_ID, conn);
		if (s == null)
			return defaultValue; 
		
		if (s.length() == 0)
			return defaultValue;
		//
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			LogM.log(ctx, MSysConfig.class, Level.SEVERE, "getIntValue (" + Name + ") = " + s, e);
		}
		return defaultValue;
	}
	
	/**
	 * Get system configuration property of type int
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:21:47
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @param AD_Client_ID
	 * @param AD_Org_ID
	 * @return
	 * @return int
	 */
	public static int getIntValue(Context ctx, String Name, int defaultValue, int AD_Client_ID, int AD_Org_ID) {
		return getIntValue(ctx, Name, defaultValue, AD_Client_ID, AD_Org_ID, null);
	}
	
	/**
	 * Get system configuration property of type double
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @param Organization ID
	 * @return double
	 */
	public static double getDoubleValue(Context ctx, String Name, double defaultValue, int AD_Client_ID, int AD_Org_ID) {
		String s = getValue(ctx, Name, AD_Client_ID, AD_Org_ID);
		if (s == null || s.length() == 0)
			return defaultValue;
		//
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			LogM.log(ctx, MSysConfig.class, Level.SEVERE, "getDoubleValue (" + Name + ") = " + s, e);
		}
		return defaultValue;
	}
	
	/**
	 * Get system configuration property of type boolean
	 * @param ctx
	 * @param Name
	 * @param defaultValue
	 * @param Client ID
	 * @param Organization ID
	 * @return boolean
	 */
	public static boolean getBooleanValue(Context ctx, String Name, boolean defaultValue, int AD_Client_ID, int AD_Org_ID) {
		String s = getValue(ctx, Name, AD_Client_ID, AD_Org_ID);
		if (s == null || s.length() == 0)
			return defaultValue;
		
		if ("Y".equalsIgnoreCase(s))
			return true;
		else if ("N".equalsIgnoreCase(s))
			return false;
		else
			return Boolean.valueOf(s).booleanValue();
	}	
}
