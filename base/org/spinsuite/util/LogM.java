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
package org.spinsuite.util;

import java.util.logging.Level;

import android.content.Context;
import android.util.Log;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class LogM {
	/**	Trace Level Constant		*/
	private final static String 	TRACE_LEVEL_KEY = "#TraceLevel";
	
	/**
	 * Log
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 20:34:20
	 * @param ctx
	 * @param className
	 * @param level
	 * @param msg
	 * @return void
	 */
	public static void log(Context ctx, String className, Level level, String msg){
		if(msg == null)
			return;
		int m_TraceLevel = getTraceLevel(ctx);
		//	Level
		if(m_TraceLevel <=  level.intValue()){
			if(level == Level.FINE
					|| level == Level.FINER
					|| level == Level.FINEST)
				Log.v(className, msg);
			else if(level == Level.SEVERE)
				Log.e(className, msg);
			else if(level == Level.WARNING)
				Log.w(className, msg);
			else if(level == Level.INFO)
				Log.i(className, msg);
		}
	}
	
	/**
	 * Log
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 20:35:37
	 * @param ctx
	 * @param className
	 * @param level
	 * @param msg
	 * @param tr
	 * @return void
	 */
	public static void log(Context ctx, String className, Level level, String msg, Throwable tr) {
		if(msg == null)
			return;
		int m_TraceLevel = getTraceLevel(ctx);
		//	Level
		if(m_TraceLevel <=  level.intValue()){
			if(level == Level.FINE
					|| level == Level.FINER
					|| level == Level.FINEST)
				Log.v(className, msg, tr);
			else if(level == Level.SEVERE)
				Log.e(className, msg, tr);
			else if(level == Level.WARNING)
				Log.w(className, msg, tr);
			else if(level == Level.INFO)
				Log.i(className, msg, tr);
		}
	}
	
	/**
	 * Log
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 20:36:42
	 * @param ctx
	 * @param clazz
	 * @param level
	 * @param msg
	 * @param tr
	 * @return void
	 */
	public static void log(Context ctx, Class<?> clazz, Level level, String msg, Throwable tr) {
		log(ctx, clazz.getName(), level, msg, tr);
	}
	
	/**
	 * Log
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 20:38:27
	 * @param ctx
	 * @param clazz
	 * @param level
	 * @param msg
	 * @return void
	 */
	public static void log(Context ctx, Class<?> clazz, Level level, String msg) {
		log(ctx, clazz.getName(), level, msg);
	}
	/**
	 * Get current trace level
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 15:54:04
	 * @return
	 * @return int
	 */
	public static int getTraceLevel(Context ctx){
		return Env.getContextAsInt(ctx, TRACE_LEVEL_KEY);
	}
	
	/**
	 * Set Trace level
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 26/02/2014, 15:57:12
	 * @param traceLevel
	 * @return void
	 */
	public static void setTraceLevel(Context ctx, Level traceLevel) {
		Env.setContext(ctx, TRACE_LEVEL_KEY, traceLevel.intValue());
	}
}
