package org.spinsuite.initialload;

import java.io.IOException;


import org.ksoap2.serialization.SoapObject;
import org.spinsuite.base.DB;
import org.spinsuite.conn.CommunicationSoap;
import org.spinsuite.util.Env;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

/**
 * 
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 *
 */
public class InitialLoad extends CommunicationSoap{

	
	
	/** Soap Object for Params to Web Service Call */
	private ILCall call  = null;
	
	/** Task*/
	InitialLoadTask m_Task = null;

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
			boolean isNetService) {
		super(p_Url, p_NameSpace, p_Method_Name, isNetService);
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
			boolean isNetService, String p_SoapAction) {
		this(p_Url, p_NameSpace, p_Method_Name,
				isNetService);
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
			boolean isNetService, String p_SoapAction, String p_User, String p_PassWord,InitialLoadTask p_Task) {
		this(p_Url, p_NameSpace, p_Method_Name,
				isNetService, p_SoapAction);
		m_Task = p_Task;
		call = new ILCall(p_NameSpace, p_User, p_PassWord);
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
		int timeout =60000;
		init_envelope();
		
		initTransport(timeout);
		//Call Service
		try {
			call();
			result = (SoapObject) getM_Envelope().getResponse();
		} catch (IOException e) {
			e.printStackTrace();
			m_Task.setM_PublicMsg(e.getMessage());
			m_Task.setM_Error(true);
			m_Task.refreshGUINow();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			m_Task.setM_PublicMsg(e.getMessage());
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
	public void writeDB(SoapObject resp,Context ctx){
		
		DB con = new DB(ctx);
		con.openDB(DB.READ_WRITE);
		Object [] params = null;
		int countrec = resp.getPropertyCount();
		m_Task.setMaxValueProgressBar(countrec);
		
		for (int i= 0;i< countrec;i++){
			SoapObject query = (SoapObject) resp.getProperty(i);
						
			m_Task.setM_PublicMsg(query.getPropertyAsString("Name"));
			m_Task.setM_Progress(i+1);
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
	    	
	    	try{
	    		
	    	
			if (params==null)
	    		con.executeSQL(sql);
	    	else
	    		con.executeSQL(sql, params);
			
	    	}
	    	catch (SQLiteException ex){
	    		m_Task.setM_PublicMsg(ex.getMessage());
	    		m_Task.setM_Error(true);
				m_Task.refreshGUINow();
	    	}
			
	    	
		}
	}

	
	public void addPropertyToCall(String p_Name, Object p_Value) {
		call.addProperty(p_Name, p_Value);
	}
}
