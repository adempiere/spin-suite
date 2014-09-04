package org.spinsuite.initialload;

import org.ksoap2.serialization.SoapObject;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.BackGroundProcess;
import org.spinsuite.login.T_Connection;
import org.spinsuite.login.T_Login_ProgressSync;
import org.spinsuite.util.BackGroundTask;

public class InitialLoadTask implements BackGroundProcess{

	/** Public Msg*/
	private String m_PublicMsg = "";

	/** Error Indicator*/
	private boolean m_Error = false;
	
	/** Progress Indicator*/
	private int m_Progress = -1 ;
	
	/** Background Task */ 
	private BackGroundTask m_Task = null;
	
	/** Connection Windows*/
	private T_Connection m_Conn = null;
	
	/** Dialog Message Process*/ 
	private T_Login_ProgressSync df = null;
	
	/** Url */
	private String m_URL = ""; 
	
	/** Name Space */ 
	private String m_NameSpace = "";
	
	/** Method */
	private String m_Method = "";
	
	/** Is .Net Service*/
	private boolean m_IsNetService = false;
	
	/** User*/
	private String m_User =  "";
	
	/** Password*/
	private String m_PassWord = "" ;
	
	/** Soap Action */
	private String m_SoapAction = "";
	
	/** Initial Load Soap Object*/ 
	private InitialLoad il ;
	
	public InitialLoadTask(String p_URL, String p_NameSpace, String p_Method,boolean p_IsNetService, String p_User,String p_PassWord,String p_SoapAction,T_Connection p_Conn) {
		// TODO Auto-generated constructor stub
		
		m_URL = p_URL;
		m_NameSpace = p_NameSpace;
		m_Method = p_Method;
		m_IsNetService = p_IsNetService;
		m_User = p_User; 
		m_SoapAction = p_SoapAction;
		m_PassWord = p_PassWord;
		m_Conn =p_Conn;
		
		
		
		/*
		 * 


				et_UrlSoap.getText().toString(), 
    			et_NameSpace.getText().toString(), 
    			et_Method.getText().toString(), 
    			true, 
    													et_NameSpace.getText().toString() + et_Method.getText().toString(), 
    														p_User, 
    															p_Pass, 



		 * 
		 */
		
		
	}
	
	@Override
	public void publishBeforeInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publishOnRunning() {
		// TODO Auto-generated method stub
		df.setMsg(m_PublicMsg,m_Error);
		df.setProgress(m_Progress, m_Error);
	}

	@Override
	public void publishAfterEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object run() {
		// TODO Auto-generated method stub

		// Call to WS Create Metadata 
		m_PublicMsg = "Calling";
		m_Task.refreshGUINow();
		il = new InitialLoad(m_URL, m_NameSpace, m_Method, m_IsNetService, m_SoapAction, m_User, m_PassWord, this);
		il.addPropertyToCall(ILCall.m_ServiceDefinitionField, InitialLoad.INITIALLOAD_ServiceDefinition);
		il.addPropertyToCall(ILCall.m_ServiceMethodField, InitialLoad.INITIALLOAD_ServiceMethodCreateMetaData);
		
		SoapObject so = il.callService();
		
		if (so!= null){
			il.writeDB(so, m_Conn);
			//m_PublicMsg = "End";
		//m_Task.refreshGUINow();
		}
		return null;
	}
	
	/**
	 * 
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 27/08/2014, 15:56:49
	 * @return void
	 */
	public void runTask(){

		df = new T_Login_ProgressSync();
    	df.show(m_Conn.getFragmentManager(), m_Conn.getResources().getString(R.string.InitSync));
		m_Task = new BackGroundTask(this, m_Conn);
		m_Task.runTask();
	}
	

	public void setM_PublicMsg(String m_PublicMsg) {
		this.m_PublicMsg = m_PublicMsg;
	}
	
	public void setM_Progress(int m_Progress) {
		this.m_Progress = m_Progress;
	}
	
	public void setM_Error(boolean m_Error) {
		this.m_Error = m_Error;
	}

	public void refreshGUINow(){
		m_Task.refreshGUINow();
	}
	
	public void setMaxValueProgressBar(int p_Max){
		df.setMaxValueProgressBar(p_Max);
	}
}
