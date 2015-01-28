package org.spinsuite.sync;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import org.ksoap2.serialization.SoapObject;
import org.spinsuite.base.DB;
import org.spinsuite.conn.CommunicationSoap;
import org.spinsuite.interfaces.BackGroundProcess;
import org.spinsuite.model.MSPSSyncMenu;
import org.spinsuite.model.MSPSTable;
import org.spinsuite.model.MWSWebServiceType;
import org.spinsuite.model.X_AD_Rule;
import org.spinsuite.model.X_AD_Table;
import org.spinsuite.model.X_WS_WebService;
import org.spinsuite.model.X_WS_WebServiceMethod;
import org.spinsuite.util.BackGroundTask;
import org.xmlpull.v1.XmlPullParserException;

import com._3e.ADInterface.WSModelCRUDRequest;

import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

public class SyncDataTask implements BackGroundProcess  {

	/** Sync Menu ID*/ 
	private int m_SPS_SyncMenu_ID = 0;
	
	/** Connection Database */
	private DB conn = null;
	
	/** Web Service Method Query Data*/
	public static final String WSMQueryData = "queryData"; 

	/** IS Net Web Service*/
	public static final boolean IsNetService = true;
	
	/** Data Set*/
	public static final String WSRespDataSet = "DataSet";
	
	/** Data Row*/
	public static final String WSRespDataRow = "DataRow";
	
	/** Column Name*/ 
	public static final String WSColumn = "column";
	
	/** Value */
	public static final String WSValue = "val";
	
	/** Quantity of Pages */
	public static final String WSQtyPages = "QtyPages";
	
	/** Context */
	private Context ctx;
	
	/** String Url Web Service*/
	private String m_Url;
	
	/** Web Service NameSpace */
	private String m_NameSpace;
	
	/** Web Service Method*/
	private String m_MethodValue;
	
	/** Soap Object Call*/
	private CommunicationSoap soapObject;
	
	/** Timeout for Response Web Service*/
	private int m_TimeOut = 0;
	
	/** Soap Object Response*/
	private SoapObject soapResponse = null;
	
	/** Notification Manager*/
	private NotificationManager m_NFManager = null;
	
	/** Max Value Progress Bar*/
	private int m_MaxPB = 0;
	
	/** Builder*/
	private Builder m_Builder = null;
	
	/** Progress Indicator*/
	private int m_Progress = -1 ;
	
	/** Public Msg*/
	private String m_PublicMsg = "";
	
	/** Public Tittle*/
	private String m_PublicTittle = "";
	
	/** Background Task*/
	private BackGroundTask bgTask = null;
	
	
	public SyncDataTask(int p_SPS_SyncMenu_ID,Context p_ctx) {
		// TODO Auto-generated constructor stub
		m_SPS_SyncMenu_ID = p_SPS_SyncMenu_ID;
		ctx = p_ctx;
		conn = new DB(ctx);
		conn.openDB(DB.READ_WRITE);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
        StrictMode.setThreadPolicy(policy);
        
        m_NFManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		m_Builder = new NotificationCompat.Builder(ctx);
		
		bgTask = new BackGroundTask(this, ctx);
		bgTask.runTask();
	}
	
	@Override
	public void publishBeforeInit() {
		// TODO Auto-generated method stub
		m_Builder.setContentTitle(m_PublicTittle)
		.setContentText(m_PublicMsg)
		.setProgress(m_MaxPB, m_Progress, m_Progress == -1)
		.setSmallIcon(android.R.drawable.stat_sys_download);
		m_NFManager.notify(0, m_Builder.build());
	}

	@Override
	public void publishOnRunning() {
		// TODO Auto-generated method stub
		m_Builder.setContentTitle(m_PublicTittle)
			.setContentText(m_PublicMsg)
			.setProgress(m_MaxPB, m_Progress, m_Progress == -1)
			.setSmallIcon(android.R.drawable.stat_sys_download);
			
		m_NFManager.notify(0, m_Builder.build());
	}

	@Override
	public void publishAfterEnd() {
		// TODO Auto-generated method stub
		m_Builder.setContentTitle(m_PublicTittle)
		.setContentText(m_PublicMsg)
		.setProgress(0, 0, false)
		.setSmallIcon(android.R.drawable.stat_sys_download_done);
		m_NFManager.notify(0, m_Builder.build());
	}

	@Override
	public Object run() {
		try{
		syncData(m_SPS_SyncMenu_ID,0);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Synchronize data method
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 22/1/2015, 1:45:30
	 * @param p_SPS_SyncMenu_ID
	 * @return void
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 */
	private void syncData(int p_SPS_SyncMenu_ID, int PageNo){
		
		MSPSSyncMenu syncm = new MSPSSyncMenu(ctx, p_SPS_SyncMenu_ID, conn);
		SoapObject param  = null;
		int qtyPages = 1;
		int currentPage = PageNo + 1;
		
		//Run Script After Call Web Service 
		if (syncm.getAD_RuleAfter_ID()!=0){
			X_AD_Rule rule  = new X_AD_Rule(ctx, syncm.getAD_RuleAfter_ID(), conn);
			conn.executeSQL(rule.getScript());
		}
		
		//Call Web Services
		if (syncm.getWS_WebServiceType_ID()!=0){
			param= getSoapParam(syncm,PageNo);
			callWebService(param,syncm);
			
			if (soapResponse != null && soapResponse.hasAttribute(SyncDataTask.WSQtyPages))
				qtyPages = Integer.parseInt(soapResponse.getAttributeAsString(SyncDataTask.WSQtyPages));
			
			if (m_MethodValue.equals(SyncDataTask.WSMQueryData)){
				while (currentPage <= qtyPages){

					writeDB(syncm);
					
					if (currentPage != qtyPages){
						param= getSoapParam(syncm,currentPage);
						callWebService(param,syncm);
					}
					
					currentPage++;
						
					
				}
			}
		}
		
		//Run Script After Call Web Service 
		if (syncm.getAD_RuleBefore_ID()!=0){
			X_AD_Rule rule  = new X_AD_Rule(ctx, syncm.getAD_RuleBefore_ID(), conn);
			conn.executeSQL(rule.getScript());
		}
		
		//Get Child's Web Services
		List<MSPSSyncMenu> syncms = MSPSSyncMenu.getNodesFromParent(ctx, Integer.valueOf(p_SPS_SyncMenu_ID).toString(), conn);
		
		for (MSPSSyncMenu mspsSyncMenu : syncms)
			syncData(mspsSyncMenu.getSPS_SyncMenu_ID(),0);
		
	}
	
	/**
	 * 
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 24/1/2015, 19:29:26
	 * @param sm
	 * @return
	 * @return SoapObject
	 */
	private SoapObject getSoapParam(MSPSSyncMenu sm, int PageNo) {
		
		SoapObject param = null;
		MWSWebServiceType wst = new MWSWebServiceType(ctx, sm.getWS_WebServiceType_ID(), conn);
		
		if (wst.getWS_WebService_ID()!=0){
			X_WS_WebService ws = new X_WS_WebService(ctx, wst.getWS_WebService_ID(), conn);
			m_Url = ws.getURL();
			m_NameSpace =  ws.getNameSpace();
		}
		
		if (wst.getWS_WebServiceMethod_ID()!=0){
			X_WS_WebServiceMethod wsm =	new X_WS_WebServiceMethod(ctx, wst.getWS_WebServiceMethod_ID(), conn);
			//Web Service Query Data
			if (wsm.getValue() != null)
				m_MethodValue = wsm.getValue();
				if(m_MethodValue.equals(SyncDataTask.WSMQueryData)){
					param = getSoapParamQueryData(sm, wst, PageNo);
			}
		}
		 
		return param;
	}
	
	/**
	 * 
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 26/1/2015, 12:50:53
	 * @param sm
	 * @param wst
	 * @return
	 * @return SoapObject
	 */
	private SoapObject getSoapParamQueryData(MSPSSyncMenu sm,MWSWebServiceType wst,int PageNo) {
		SoapObject param = null;
		String whereClause;
		
		whereClause = "";//sm.getWhereClause();
		param = new WSModelCRUDRequest(ctx, m_NameSpace, wst.getWS_WebServiceType_ID(), conn, null, null, whereClause,PageNo);
		
		return param;
	}
	
	/**
	 * Call Web Service And get Response
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 24/1/2015, 18:46:08
	 * @param p_SO_Param
	 * @throws IOException
	 * @throws XmlPullParserException
	 * @return void
	 */
	private void callWebService(SoapObject p_SO_Param,MSPSSyncMenu sm) {
		
		m_PublicTittle = sm.getName();
		m_PublicMsg = sm.getDescription();
		m_Progress = -1;
		publishOnRunning();
		
		soapObject = new CommunicationSoap(m_Url, m_NameSpace, m_MethodValue, SyncDataTask.IsNetService);
		soapObject.setM_SoapAction(m_NameSpace + "/" + m_MethodValue);
		
		if (p_SO_Param != null)
			soapObject.addSoapObject(p_SO_Param);
		
		soapObject.init_envelope();
		
		if (m_TimeOut == 0)
			soapObject.initTransport();
		else
			soapObject.initTransport(m_TimeOut);

		try {
			soapObject.call();
			soapResponse = (SoapObject) soapObject.getM_Envelope().getResponse();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			m_PublicMsg = e.getLocalizedMessage();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			m_PublicMsg = e.getLocalizedMessage();	
		} catch ( IOException e) {
			e.printStackTrace();
			m_PublicMsg = e.getLocalizedMessage();	
		}catch (Exception e) {
			e.printStackTrace();
			m_PublicMsg = e.getLocalizedMessage();
	 	}
		finally{
			publishOnRunning();
		}
				
	}
	
	/**
	 * Write into DB
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> 27/1/2015, 23:04:11
	 * @param sm
	 * @return void
	 */
	private void writeDB(MSPSSyncMenu sm)
	{		
		 
		//Validate Data Set
		if (soapResponse == null || !soapResponse.hasProperty(SyncDataTask.WSRespDataSet))
			return;
		
		//Soap Data Set
		SoapObject soapDataSet= (SoapObject) soapResponse.getProperty(SyncDataTask.WSRespDataSet);
		
		//Validate Data Row
		if (soapDataSet == null || !soapDataSet.hasProperty(SyncDataTask.WSRespDataRow))
			return;
		
		//Soap Data Row
		SoapObject soapDataRow=(SoapObject)  soapDataSet.getProperty(SyncDataTask.WSRespDataRow);
		
		int countDataSet = soapDataSet.getPropertyCount();
		int countDataRow = soapDataRow.getPropertyCount();
		StringBuffer sql = new StringBuffer();
		StringBuffer fields =new StringBuffer();
		StringBuffer values =new StringBuffer();
		String [] data = new String[countDataRow];
		SoapObject field = null;
		Object value =null;
		String tableName = null;
		
		if (sm.getSPS_Table_ID()!= 0 ){
			MSPSTable table = new MSPSTable(ctx, sm.getSPS_Table_ID(), conn);
			tableName = table.getTableName();
		}
		else if (sm.getWS_WebServiceType_ID()!=0){
			MWSWebServiceType wst = new MWSWebServiceType(ctx, sm.getWS_WebServiceType_ID(), conn);
			if (wst.getAD_Table_ID()!=0){
				X_AD_Table table = new X_AD_Table(ctx, wst.getAD_Table_ID(), conn);
				tableName = table.getTableName();
			}
			else
				return;
		}
		else
			return;
		m_MaxPB = countDataSet;
		for (int i=0; i< countDataSet;i++){
			m_Progress = i+1;
			//Creating SQL Query
			sql.append("INSERT OR REPLACE INTO " + tableName);

			//Loading Fields And Values 
			for (int j=0;j<countDataRow;j++){
				field = (SoapObject) soapDataRow.getProperty(j);
				String columnName = field.getAttributeAsString(SyncDataTask.WSColumn);
				fields.append((fields.length()>0?","+columnName:columnName));
				values.append((values.length()>0?",?":"?"));
				if (field.getProperty(SyncDataTask.WSValue) == null)
					value = null;
				else
					value = field.getPropertyAsString(SyncDataTask.WSValue);
				
				data[j] = (value!=null ? value.toString() : null);
			}
			
			//Generating SQL
			sql.append("("+fields.toString()+") VALUES ("+values+"); ");
			try{
				conn.executeSQL(sql.toString(),data);
			}catch (SQLiteException e){
				e.printStackTrace();
				m_PublicMsg = e.getLocalizedMessage();
			}catch (Exception e) {
				e.printStackTrace();
				m_PublicMsg = e.getLocalizedMessage();
		 	}
			finally{
				publishOnRunning();
			}
		}
		soapResponse = null;
	}
}
