package org.spinsuite.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.spinsuite.interfaces.BackGroundProcess;

import android.os.Handler;
import android.os.Message;

public class BackGroundCall implements Callable<Object>{

	BackGroundProcess m_Process;
	Handler m_Hdl;
	//ArrayList<Object> m_Params;
	Object[] m_Params;
	
	public BackGroundCall(BackGroundProcess process,Handler hdl,Object... params) {
		// TODO Auto-generated constructor stub
		m_Process = process;
		m_Hdl = hdl;
		m_Params = params;
	}
	
	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		Object retValue;
		//Set Message to Put
		Message msg = new Message();
		msg.obj = m_Process;
		//Send Process Object Before Run 
		m_Hdl.sendMessage(msg);
		
		Method method = getMethod(BackGroundProcess.MethodRunName);
		retValue = (Object) method.invoke(m_Process, m_Params);
		//Send Process Object After Run
		m_Hdl.sendMessage(msg);
		
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
		Method[] allMethods = m_Process.getClass().getMethods();
		for (int i = 0; i < allMethods.length; i++)
		{
			if (methodName.equals(allMethods[i].getName()))
				return allMethods[i];
		}
		return null;
	}	//	getMethod

}
