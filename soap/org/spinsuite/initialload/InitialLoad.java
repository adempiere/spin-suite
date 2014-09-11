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
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Carlos Parada www.erpcya.com             				 		 *
 *************************************************************************************/
package org.spinsuite.initialload;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;

import org.ksoap2.serialization.SoapObject;
import org.spinsuite.base.DB;
import org.spinsuite.conn.CommunicationSoap;
import org.spinsuite.util.LogM;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;



/**
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 *
 */
public class InitialLoad extends CommunicationSoap{

	
	
	/** Soap Object for Params to Web Service Call */
	private ILCall call  = null;
	
	/** Task*/
	private InitialLoadTask m_Task = null;
	
	/** Timeout to wait response from web server*/
	private int m_Timeout = -1;
	
	private Context m_Ctx = null; 
	
	/** Web Service Definition*/
	public static String INITIALLOAD_ServiceDefinition = "Spin-Suite";
	public static String INITIALLOAD_ServiceMethodCreateMetaData = "CreateMetadata";
	public static String INITIALLOAD_ServiceMethodWebServiceDefinition = "WebServiceDefinition";
	public static String INITIALLOAD_ServiceMethodDataSynchronization = "DataSynchronization";
	
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 23:15:06
	 * @param p_Url
	 * @param p_NameSpace
	 * @param p_Method_Name
	 * @param isNetService
	 */
	public InitialLoad(String p_Url, String p_NameSpace, String p_Method_Name,
			boolean isNetService,Context p_Ctx) {
		super(p_Url, p_NameSpace, p_Method_Name, isNetService);
		m_Ctx = p_Ctx;
	}
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 23:20:07
	 * @param p_Url
	 * @param p_NameSpace
	 * @param p_Method_Name
	 * @param isNetService
	 * @param p_SoapAction
	 */
	public InitialLoad(String p_Url, String p_NameSpace, String p_Method_Name,
			boolean isNetService, String p_SoapAction ,Context p_Ctx) {
		this(p_Url, p_NameSpace, p_Method_Name,
				isNetService, p_Ctx);
		setM_SoapAction(p_SoapAction);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carloaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 23:23:10
	 * @param p_Url
	 * @param p_NameSpace
	 * @param p_Method_Name
	 * @param isNetService
	 * @param p_SoapAction
	 * @param p_User
	 * @param p_PassWord
	 * @param p_ServiceType
	 */
	public InitialLoad(String p_Url, String p_NameSpace, String p_Method_Name,
			boolean isNetService, String p_SoapAction, String p_User, String p_PassWord,InitialLoadTask p_Task, 
				int p_Timeout, Context p_Ctx) {
		this(p_Url, p_NameSpace, p_Method_Name,
				isNetService, p_SoapAction, p_Ctx);
		m_Task = p_Task;
		call = new ILCall(p_NameSpace, p_User, p_PassWord);
		m_Timeout = p_Timeout;
	}
	
	
	/**
	 * Call Service
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 23:30:42
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 * @return SoapObject
	 */
	public SoapObject callService() {
		SoapObject result = null;
		addSoapObject(call);
		
		init_envelope();
		if (m_Timeout <= 0)
			initTransport();
		else
			initTransport(m_Timeout);
		
		//Call Service
		try {
			call();
			result = (SoapObject) getM_Envelope().getResponse();
			LogM.log(m_Ctx, InitialLoad.class, Level.FINE, "Web Service Call");
		} catch (Exception e) {
			LogM.log(m_Ctx, InitialLoad.class, Level.SEVERE,e.getLocalizedMessage(),e.getCause());
			m_Task.setM_PublicMsg(e.getLocalizedMessage());
			m_Task.setM_Error(true);
			m_Task.refreshGUINow();
		}
		
		return result;
	}
	
	/**
	 * Write Response from Web Service to DB
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 07/03/2014, 14:21:24
	 * @param resp
	 * @return void
	 */
	public boolean writeDB(SoapObject resp){
		
		DB conn = new DB(m_Ctx);
		conn.openDB(DB.READ_WRITE);
		Object [] params = null;
		int countrec = resp.getPropertyCount();
		m_Task.setMaxValueProgressBar(countrec);
		
		try{
			for (int i= 0;i< countrec;i++){
				SoapObject query = (SoapObject) resp.getProperty(i);
							
				
				m_Task.setM_Progress(i+1);
				m_Task.setM_PublicMsg(query.getPropertyAsString("Name") + " "+m_Task.getM_Progress() + " / " + countrec );
				m_Task.refreshGUINow();
				
		    	String sql = query.getPropertyAsString("SQL");
		    	
		    	//Have Parameters
		    	if (query.hasProperty("DataRow")){
					SoapObject datarow = (SoapObject) query.getProperty("DataRow");
					params = new Object[datarow.getPropertyCount()];
					
					for (int j=0;j < datarow.getPropertyCount();j++){
						SoapObject values = (SoapObject) datarow.getProperty(j);
						if (values.hasProperty("Value"))
							params[j]=values.getPrimitiveProperty("Value");
					}
				}
		    	System.out.println(sql);
		    	//Execute SQL
				if (params==null)
					conn.executeSQL(sql);
		    	else
		    		conn.executeSQL(sql, params);
				
				
			}
		}
    	catch (Exception ex){
    		ex.printStackTrace();
    		m_Task.setM_PublicMsg(ex.getLocalizedMessage());
    		m_Task.setM_Error(true);
			m_Task.refreshGUINow();
			return false;
    	}
    	finally{
    		DB.closeConnection(conn);
    	}
		
		return true;
	}

	/**
	 * Add Property to Call SoapObject
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 04/09/2014, 00:43:43
	 * @param p_Name
	 * @param p_Value
	 * @return void
	 */
	public void addPropertyToCall(String p_Name, Object p_Value) {
		call.addProperty(p_Name, p_Value);
	}
	
}
