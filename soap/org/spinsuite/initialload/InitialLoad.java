package org.spinsuite.initialload;

import java.io.IOException;


import org.ksoap2.serialization.SoapObject;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.conn.CommunicationSoap;
import org.spinsuite.interfaces.BackGroundProcess;
import org.spinsuite.login.T_Connection;
import org.spinsuite.login.T_Login_ProgressSync;
import org.spinsuite.util.BackGroundTask;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;

/**
 * 
 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a>
 *
 */
public class InitialLoad extends CommunicationSoap implements BackGroundProcess{

	/** Public Msg*/
	private String m_PublicMsg = new String();

	/** Background Task */ 
	private BackGroundTask m_Task = null;
	
	/** Connection Windows*/
	private T_Connection m_Conn = null;
	
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 23:20:07
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
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 23:23:10
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
			boolean isNetService, String p_SoapAction, String p_User, String p_PassWord, String p_ServiceType,T_Connection p_con) {
		this(p_Url, p_NameSpace, p_Method_Name,
				isNetService, p_SoapAction);
		m_Conn = p_con;
		ILCall call = new ILCall(p_NameSpace, p_User, p_PassWord, p_ServiceType);
		addSoapObject(call);
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
		int timeout =10000;
		init_envelope();
		
		initTransport(timeout);
		//Call Service
		try {
			call();
			result = (SoapObject) getM_Envelope().getResponse();
		} catch (IOException e) {
			e.printStackTrace();
			m_PublicMsg = e.getMessage();
			publishOnRunning();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			m_PublicMsg = e.getMessage();
			publishOnRunning();
		}
		return result;
	}
	
	/**
	 * Write Response from Web Service to DB
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 07/03/2014, 14:21:24
	 * @param resp
	 * @return void
	 */
	public String writeDB(SoapObject resp,Context ctx){
		
		DB con = new DB(ctx);
		con.openDB(DB.READ_WRITE);
		Object [] params = null;
		for (int i= 0;i<resp.getPropertyCount();i++){
			SoapObject query = (SoapObject) resp.getProperty(i);
			
			System.out.println(query.getPropertyAsString("Name"));
			
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
	    	System.out.println(params);
	    	if (params==null)
	    		con.executeSQL(sql);
	    	else
	    		con.executeSQL(sql, params);
		}
		return "sucess";
	}

	@Override
	public void publishBeforeInit() {
		
	}

	@Override
	public void publishOnRunning() {
		System.out.println(m_PublicMsg);
	}

	@Override
	public void publishAfterEnd() {
		
	}

	@Override
	public Object run() {
		m_PublicMsg = "Calling";
		System.out.println(this);
		SoapObject so = callService();
		System.out.println(so);
		m_PublicMsg = "End";
		return so;
	}
	
	public void setM_PublicMsg(String m_PublicMsg) {
		this.m_PublicMsg = m_PublicMsg;
	}
	
	public void runTask(){
		

    	T_Login_ProgressSync df = new T_Login_ProgressSync(this);
    	df.show(m_Conn.getFragmentManager(), m_Conn.getResources().getString(R.string.InitSync));
		m_Task = new BackGroundTask(this, m_Conn);
		m_Task.runTask();
	}
}
