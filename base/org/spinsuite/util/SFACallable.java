/*************************************************************************************
 * Product: SFAndroid (Sales Force Mobile)                                           *
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
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Carlos Parada www.erpcya.com                      				 *
 *************************************************************************************/
package org.spinsuite.util;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import android.content.Context;

public class SFACallable<V> implements Callable<Object>{

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 05/03/2014, 23:24:22
	 * @param p_ctx
	 * @param p_Class
	 * @param p_Method
	 * @param p_Args
	 */
	public SFACallable(Context p_ctx,Object p_Object,String p_Method,Object[] p_Args) {
		// TODO Auto-generated constructor stub
		m_Method = p_Method;
		m_Args = p_Args;
		ctx = p_ctx;
		m_Object = p_Object;
	}
	
	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		Object retValue = null;
		
		// Find Method
		Method method = getMethod(m_Method);
		if (method == null)
			throw new IllegalArgumentException("Method not found: "
					+ m_Method);
		int argParamsMethod = method.getParameterTypes().length;
		int argLength = (m_Args != null ? m_Args.length : 0);
		if (!(argParamsMethod == argLength))
			throw new IllegalArgumentException("Method " + m_Method
					+ " has invalid no of arguments: " + argLength + " <> " + argParamsMethod);
		
		// Call Method
		try {
			Object[] args = null;			
			args = m_Args;
			retValue = (Object) method.invoke(m_Object, args);
		} catch (Exception e) {
			Throwable ex = e.getCause(); // InvocationTargetException
			if (ex == null)
				ex = e;
			LogM.log(ctx, getClass(), Level.SEVERE, "start: " + m_Method , ex);
		}
		return retValue;
		
	}
	
	/**
	 * get Method Copied from CallOutEngine
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 05/03/2014, 23:25:00
	 * @param methodName
	 * @return
	 * @return Method
	 */
	private Method getMethod (String methodName)
	{
		Method[] allMethods = m_Object.getClass().getMethods();
		for (int i = 0; i < allMethods.length; i++)
		{
			if (methodName.equals(allMethods[i].getName()))
				return allMethods[i];
		}
		return null;
	}	//	getMethod
	
	/** Method to Call */
	private String m_Method = null;
	
	/** Arguments*/
	private Object[] m_Args ;
	
	/** Context*/
	private Context ctx; 
	
	/**Object Instanced*/
	private Object m_Object;

}
