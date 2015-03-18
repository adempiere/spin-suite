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
package org.spinsuite.sync;

import java.io.IOException;
import java.util.logging.Level;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.spinsuite.base.DB;
import org.spinsuite.conn.CommunicationSoap;
import org.spinsuite.util.LogM;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 *
 */
public class InitialLoad extends CommunicationSoap {

	/** Soap Object for Params to Web Service Call 			*/
	private ILCall m_Call  												= null;
	/** Task 												*/
	private SyncService m_Callback 										= null;
	/** Timeout to wait response from web server			*/
	private int m_Timeout 												= -1;
	/** Web Service Definition								*/
	public static String INITIALLOAD_ServiceDefinition 					= "Spin-Suite";
	/** Web Service Method Create Meta-data					*/
	public static String INITIALLOAD_ServiceMethodCreateMetaData 		= "CreateMetadata";
	/** Web Service Method Create Web Service Definition 	*/
	public static String INITIALLOAD_ServiceMethodWebServiceDefinition 	= "WebServiceDefinition";
	/** Web Service Method Create Data Synchronization		*/
	public static String INITIALLOAD_ServiceMethodDataSynchronization 	= "DataSynchronization";
	
	/**
	 * *** Constructor ***
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 25/02/2014, 23:15:06
	 * @param p_Url
	 * @param p_NameSpace
	 * @param p_Method_Name
	 * @param isNetService
	 */
	public InitialLoad(String p_Url, String p_NameSpace, String p_Method_Name,
			boolean isNetService, SyncService p_Task) {
		super(p_Url, p_NameSpace, p_Method_Name, isNetService);
		m_Callback = p_Task;
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
			boolean isNetService, String p_SoapAction , SyncService p_Ctx) {
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
	 * @param p_Task
	 * @param p_ServiceType
	 */
	public InitialLoad(String p_Url, String p_NameSpace, String p_Method_Name,
			boolean isNetService, String p_SoapAction, String p_User, 
			String p_PassWord,
			int p_Timeout, SyncService p_Task) {
		
		this(p_Url, p_NameSpace, p_Method_Name,
				isNetService, p_SoapAction, p_Task);
		
		m_Callback = p_Task;
		m_Call = new ILCall(p_NameSpace, p_User, p_PassWord);
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
		addSoapObject(m_Call);
		
		init_envelope();
		if (m_Timeout <= 0)
			initTransport();
		else
			initTransport(m_Timeout);
		
		//Call Service
		try {
			call();
			result = (SoapObject) getM_Envelope().getResponse();
			LogM.log(m_Callback, InitialLoad.class, Level.FINE, "Web Service Call");
		} catch (Exception e) {
			LogM.log(m_Callback, InitialLoad.class, Level.SEVERE,e.getLocalizedMessage(),e.getCause());
			m_Callback.sendStatus(e.getLocalizedMessage(), true, null);
		}
		
		return result;
	}
	
	/**
	 * Write Response from Web Service to DB
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 07/03/2014, 14:21:24
	 * @param resp
	 * @return void
	 */
	public boolean writeDB(SoapObject p_Resp,Integer p_PageCount,Integer p_CurrentPage){
		//boolean result = false;
		DB conn = new DB(m_Callback);
		conn.openDB(DB.READ_WRITE);
		Object [] params = null;
		int countrec = p_Resp.getPropertyCount();
		int hasPages = (p_Resp.hasProperty(ILCall.m_Pages) ? 1 :0);
		int hasWSCount = (p_Resp.hasProperty("WSCount") ? 1 :0);
		m_Callback.setMaxValueProgressBar(countrec - hasPages - hasWSCount);
		
		try {
			for (int i= 0;i< countrec;i++){
				
				if (p_Resp.getProperty(i) instanceof SoapPrimitive)
					continue;
				
				SoapObject query = (SoapObject) p_Resp.getProperty(i);
				//	Set Message
				m_Callback.sendStatus(query.getPropertyAsString("Name"), false, (i + 1));
//				m_Callback.sendStatus(query.getPropertyAsString("Name") +"\n" 
//						+ m_Callback.getString(R.string.Sync_Pages) + ": " + (p_PageCount != null && p_CurrentPage !=null 
//																			? p_CurrentPage + "/" + p_PageCount + "\n" 
//																					: "") 
//						+ m_Callback.getString(R.string.Sync_Records) + ": " + (i + 1) + "/" 
//													+ (countrec - hasPages - hasWSCount), false, (i + 1));
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
		    	
		    	//Execute SQL
				if (params==null)
					conn.executeSQL(sql);
		    	else
		    		conn.executeSQL(sql, params);
			}
		}
    	catch (Exception ex){
    		ex.printStackTrace();
    		m_Callback.sendStatus(ex.getLocalizedMessage(), true, null);
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
		m_Call.addProperty(p_Name, p_Value);
	}
}
