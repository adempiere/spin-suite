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
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.login;

import java.io.File;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.initialload.InitialLoadTask;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * 
 * @author Yamel Senih
 *
 */
public class T_Connection extends Activity implements I_Login {

	/**	URL SOAP Comunication	*/
	public EditText 	et_UrlSoap;
	/**	Synchronization Method	*/
	private EditText 	et_Method;
	private EditText 	et_Timeout;
	private Spinner 	sp_LogLevel;
	/** NameSpace*/
	private EditText 	et_NameSpace;
	/** Soap Object InitialLoad	*/
	//private InitialLoad m_load ;
	/**	Sync					*/
	private Button 		butt_InitSync;
	/**	Save data SD			*/
	private CheckBox 	ch_SaveSD;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.t_connection);
        
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
        StrictMode.setThreadPolicy(policy);
        
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        et_UrlSoap = (EditText) findViewById(R.id.et_UrlSoap);
    	et_Method = (EditText) findViewById(R.id.et_Method);
    	et_Timeout = (EditText) findViewById(R.id.et_Timeout);
    	sp_LogLevel = (Spinner) findViewById(R.id.sp_LogLevel);
    	et_NameSpace = (EditText) findViewById(R.id.et_NameSpace);
    	ch_SaveSD = (CheckBox) findViewById(R.id.ch_SaveSD);
    	
    	// Carlos Parada Setting Parameters for Spin-Suite Service Call 
    	et_UrlSoap.setText("http://192.168.20.71:8081/ADInterface/services/SpinSuiteService");
    	et_NameSpace.setText("http://www.erpcya.com/");
    	et_Method.setText("InitialLoad");
    	//End Carlos Parada

    	butt_InitSync = (Button) findViewById(R.id.butt_InitSync);
    	
    	sp_LogLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> a, View v,
					int position, long i) {
				//	Evaluate
				Level level = Level.OFF;
		    	if(position == 0) {
		    		level = Level.INFO;
		    	} else if(position == 1) {
		    		level = Level.FINE;
		    	} else if(position == 2) {
		    		level = Level.FINER;
		    	} else if(position == 3) {
		    		level = Level.FINEST;
		    	} else if(position == 4) {
		    		level = Level.SEVERE;
		    	} else if(position == 5) {
		    		level = Level.WARNING;
		    	} else if(position == 6) {
		    		level = Level.OFF;
		    	}
		    	//	Set Level
		    	LogM.setTraceLevel(getApplicationContext(), level);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				//	
			}
    	});
    	//	Init Sync
    	butt_InitSync.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				if(valid()){
					synchronize();
					lockFront();
				}
			}
		});
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, Login.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    /**
     * loock the field when sync is done
     * @author Yamel Senih 03/05/2012, 15:47:26
     * @return void
     */
    public void lockFront(){
    	if(!Env.isEnvLoad(this)){
    		et_UrlSoap.setEnabled(true);
    		et_Method.setEnabled(true);
    		et_NameSpace.setEnabled(true);
    		ch_SaveSD.setEnabled(true);
    		ch_SaveSD.setChecked(true);
    	} else {
    		et_UrlSoap.setEnabled(false);
    		et_Method.setEnabled(false);
    		et_NameSpace.setEnabled(false);
    		ch_SaveSD.setEnabled(false);
    		//Establece el Timeout en la Ventana
    		//Carlos Parada 04/11/2012
    		setTimeOut();
    		//Fin Carlos Parada
    	}
    }
    
    
    /**
     * Set Timeout in Activity
     * @author Carlos Parada 04/11/2012, 19:59:01
     * @return void
     */
    public void setTimeOut()
    {
    	//
		int timeoutInt = Env.getContextAsInt(this, "#Timeout");
		String timeout = String.valueOf(timeoutInt);
		et_Timeout.setText(timeout);
    }
    
    
    /**
     * Action Initial Synchronization
     * @author Yamel Senih 24/04/2012, 00:14:42
     * 		   Carlos Parada 17/05/2012 Se Coloco la carga inicial automatica desde adempiere
     * @return void
     */
    private void synchronize(){
    	
    	/*m_load = new InitialLoad(){
    		
    		@Override
    		protected void onPostExecute(Object result) {
    			super.onPostExecute(result);
    			//	Load Context
    			loadContext();
    		}
    	};
    	m_load.LoadSoapFromContext(this);*/
    	
    	//TestProcess tp = new TestProcess(this);
    	//InitialLoad il = new InitialLoad(m_Url, m_NameSpace, m_Method, true, m_NameSpace + m_Method, et_User.getText().toString(), et_PassWord.getText().toString(), "SFAndroidService");
		
    	T_Login_Init df = new T_Login_Init(this);
    	df.show(getFragmentManager(), this.getResources().getString(R.string.InitSync));
    	
    }
    
    /**
     * Load Context Data
     * @author Yamel Senih 17/10/2012, 16:46:40
     * @return void
     */
    private void loadContext(){
    	/**
		 * Carlos Parada, Load var in comntext
		 */
    	
		if (Env.isEnvLoad(this))
		{	
			String sql = new String("SELECT sc.Name, sc.Value FROM AD_SysConfig sc");
	    	DB con = new DB(this);
	    	con.openDB(DB.READ_ONLY);
	    	Cursor rs = con.querySQL(sql, null);
	    	if(rs.moveToFirst()){
				do {
					Env.setContext(this, "#" + rs.getString(0), rs.getInt(1));
				} while(rs.moveToNext());
			}
	    	con.closeDB(rs);
	    		
		}
    }
    
    /**
     * Valid fields on Activity
     * @author Yamel Senih 24/04/2012, 12:33:57
     * @return
     * @return boolean
     */
    private boolean valid(){
    	if(et_UrlSoap.getText() != null 
    			&& et_UrlSoap.getText().toString().length() > 0){
    		if(et_Method.getText() != null 
    				&& et_Method.getText().toString().length() > 0){
    			if(et_NameSpace.getText() != null 
        				&& et_NameSpace.getText().toString().length() > 0){
    				Env.setContext(this, "#SUrlSoap", et_UrlSoap.getText().toString());
    				Env.setContext(this, "#SMethod", et_Method.getText().toString());
    				Env.setContext(this, "#SNameSpace", et_NameSpace.getText().toString());
    				Env.setContext(this, "#SaveSD", ch_SaveSD.isChecked());
    				createDBDirectory();
    				
    				if(et_Timeout.getText() != null 
    	    				&& et_Timeout.getText().toString().length() > 0){
    					String limit = et_Timeout.getText().toString();
    					Env.setContext(this, "#Timeout", Integer.parseInt(limit));
    				}
    				
    	    		return true;
    			} else {
            		Msg.alertMustFillField(this, R.string.NameSpace, et_NameSpace);
            	}
        	} else {
        		Msg.alertMustFillField(this, R.string.MethodSync, et_Method);
        	}
    	} else {
    		Msg.alertMustFillField(this, R.string.Url_Soap, et_UrlSoap);
    	}
    	return false;
    }
    
    /**
	 * Create a folder /ERP/data with Database
	 * @author Yamel Senih 19/08/2012, 05:45:05
	 * @return void
	 */
	private void createDBDirectory(){
		if(!Env.isEnvLoad(this)){
			if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)
					&& ch_SaveSD.isChecked()){
				String basePathName = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
				//	Application Path
				String dbPath = basePathName + Env.DB_PATH_DIRECTORY;
				String dbPathName = basePathName + Env.DB_PATH_NAME;
				//	Documents 
				String docPathName = basePathName + Env.DOC_DIRECTORY;
				String imgPathName = basePathName + Env.IMG_DIRECTORY;
				//	
				Env.setAppBaseDirectory(this, basePathName);
				Env.setDB_PathName(this, dbPathName);
				Env.setDoc_DirectoryPathName(this, docPathName);
				Env.setImg_DirectoryPathName(this, imgPathName);
				//	Database
				File f = new File(dbPath);
				if(!f.exists()) {
					if(!f.mkdirs())
						Msg.toastMsg(this, getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + dbPathName + "\"");
				} else if(f.isDirectory()) {
					File fDB = new File(dbPathName);
					fDB.delete();
				} else if(f.isFile()){
					if(!f.mkdirs())
						Msg.toastMsg(this, getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + dbPathName + "\"");
				}
				//	Create Document Folder
				File doc = new File(docPathName);
				if(!doc.exists()
						|| doc.isFile()) {
					if(!doc.mkdirs())
						Msg.toastMsg(this, getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + docPathName + "\"");
				}
				//	Create Image Folder
				File img = new File(imgPathName);
				if(!img.exists()
						|| img.isFile()) {
					if(!img.mkdirs())
						Msg.toastMsg(this, getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + imgPathName + "\"");
				}
			} else {
				Env.setDB_PathName(this, DB.DB_NAME);
			}	
    	}
	}
	
    @Override
    public void onStart() {
        super.onStart();
        String url = et_UrlSoap.getText().toString();
    	String method = et_Method.getText().toString();
    	String timeout = et_Timeout.getText().toString();
    	String nameSpace = et_NameSpace.getText().toString();
    	//	Load URL SOAP or SOPA JAJAJAJAJAJAJAJ
    	if(url == null || url.length() == 0){
    		url = Env.getContext(this, "#SUrlSoap");
    		if(url != null)
    			et_UrlSoap.setText(url);
    	}
    	//	Load Method
    	if(method == null || method.length() == 0){
    		method = Env.getContext(this, "#SMethod");
    		if(method != null)
    			et_Method.setText(method);
    	}
    	//	Name Space
    	if(nameSpace == null || nameSpace.length() == 0){
    		nameSpace = Env.getContext(this, "#SNameSpace");
    		if(nameSpace != null)
    			et_NameSpace.setText(nameSpace);
    	}
    	//	Timeout
    	if(timeout == null || timeout.length() == 0){
    		int timeoutInt = Env.getContextAsInt(this, "#Timeout");
    		timeout = String.valueOf(timeoutInt);
    		et_Timeout.setText(timeout);
    	}
    	//	Log Level
    	int position = -1;
    	int traceLevel = LogM.getTraceLevel(this);
    	//	Evaluate
    	if(traceLevel == Level.INFO.intValue()) {
    		position = 0;
    	} else if(traceLevel == Level.FINE.intValue()) {
    		position = 1;
    	} else if(traceLevel == Level.FINER.intValue()) {
    		position = 2;
    	} else if(traceLevel == Level.FINEST.intValue()) {
    		position = 3;
    	} else if(traceLevel == Level.SEVERE.intValue()) {
    		position = 4;
    	} else if(traceLevel == Level.WARNING.intValue()) {
    		position = 5;
    	} else if(traceLevel == Level.OFF.intValue()) {
    		position = 6;
    	}
    	//	No Trace Level
    	if(position == -1) {
    		position = 5;
    		LogM.setTraceLevel(this, Level.OFF);
    	}
    	//	Select Log Position
    	sp_LogLevel.setSelection(position);
    	
    	//	Save SD
    	ch_SaveSD.setChecked(Env.getContextAsBoolean(this, "#SaveSD"));
    	
    	lockFront();
    	
    }
	
    @Override
    public void onResume() {
        super.onResume();
        lockFront();
    }
	
    @Override
	public boolean aceptAction() {
		if(valid())
			return true;
		return false;
	}

	@Override
	public boolean cancelAction() {
		return false;
	}

	@Override
	public boolean loadData() {
		return false;
	}

	/**
	 * Start Synchronization for Initial Load
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 8, 2014, 11:25:21 AM
	 * @param p_User
	 * @param p_Pass
	 * @return void
	 */
	public void startSynchronization(String p_User,String p_PassWord){
		if (!p_User.equals("") && !p_PassWord.equals("")){
    		InitialLoadTask ilt = new InitialLoadTask(et_UrlSoap.getText().toString(), 
    													et_NameSpace.getText().toString(), 
    														et_Method.getText().toString(), 
    															true, 
    																p_User,
    																	p_PassWord , 
    																		et_NameSpace.getText().toString() + et_Method.getText().toString(),
    																			this,
    																				et_Timeout.getText().toString());
    		
    		
    		ilt.runTask();
    		
    		/*
    		 * 
InitialLoad il = new InitialLoad(et_UrlSoap.getText().toString(), 
    											et_NameSpace.getText().toString(), 
    												et_Method.getText().toString(), 
    													true, 
    													et_NameSpace.getText().toString() + et_Method.getText().toString(), 
    														p_User, 
    															p_Pass, 
    																"initLoad",
    																	this);
    		 * 
    		 */
    		
    	}
    		
    	
		if(!Env.isEnvLoad(this)){
			
			/*RemoteViews contentView = new RemoteViews(this.getPackageName(), R.layout.v_progressdialog);
	        contentView.setImageViewResource(R.id.iV_Synchronizing, R.drawable.syncserver_m);
	        contentView.setTextViewText(R.id.tV_CurrentSinchronizing, this.getResources().getString(R.string.msg_CallingWebService));
	        contentView.setTextViewText(R.id.tV_Percentaje, "0%");
	        
	        NotificationManager notify = Msg.notificationMsg(this, R.drawable.syncserver_h, "",0, this.getParent().getIntent(), contentView);
	        m_load.setContentView(contentView);
	        m_load.setM_NotificationManager(notify);
			m_load.execute();*/
			
		} else {
			loadContext();
		}
    

	}
}
