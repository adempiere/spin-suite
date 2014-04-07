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
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.login;

import java.io.File;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.interfaces.I_Login;
import org.spinsuite.util.Env;
import org.spinsuite.util.Msg;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * 
 * @author Yamel Senih
 *
 */
public class T_Connection extends FragmentActivity implements I_Login {

	/**	URL SOAP Comunication	*/
	private EditText et_UrlSoap;
	/**	Synchronization Method	*/
	private EditText et_Method;
	private EditText et_Timeout;
	/** NameSpace*/
	private EditText et_NameSpace;
	/** Soap Object InitialLoad	*/
	//private InitialLoad m_load ;
	/**	Sync					*/
	private Button butt_InitSync;
	/**	Save data SD			*/
	private CheckBox ch_SaveSD;
	
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
    	et_NameSpace = (EditText) findViewById(R.id.et_NameSpace);
    	ch_SaveSD = (CheckBox) findViewById(R.id.ch_SaveSD);
    	
    	// Carlos Parada Setting Parameters for SFAndroid Service Call 
    	et_UrlSoap.setText("http://193.1.1.243:8080/ADInterface/services/SFAndroidService");
    	et_NameSpace.setText("http://www.erpcya.com/");
    	et_Method.setText("InitialLoad");
    	//End Carlos Parada

    	butt_InitSync = (Button) findViewById(R.id.butt_InitSync);
    	
    	//lockFront();
    	
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
    	et_UrlSoap.setEnabled(true);
		et_Method.setEnabled(true);
		et_NameSpace.setEnabled(true);
    	DialogFragment df = new T_Login_Init(et_UrlSoap.getText().toString(),et_Method.getText().toString(),et_NameSpace.getText().toString());
    	df.show(getSupportFragmentManager(), this.getResources().getString(R.string.InitSync));
    	
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
				String docPath = Environment.getExternalStorageDirectory() 
						+ File.separator 
						+ Env.APP_DIRECTORY 
						+ File.separator 
						+ Env.DOC_DIRECTORY;
				//	
				String dbPath = Environment.getExternalStorageDirectory() + DB.getDB_Path();
				String dbPathName = Environment.getExternalStorageDirectory() + DB.getDB_PathName();
				File f = new File(dbPath);
				if(!f.exists()) {
					if(f.mkdirs()){
						Env.setDB_Path(this, dbPathName);
					} else {
						Env.setDB_Path(this, DB.DB_NAME);
					}
				} else if(f.isDirectory()) {
					File fDB = new File(dbPathName);
					fDB.delete();
					Env.setDB_Path(this, dbPathName);
				} else if(f.isFile()){
					if(f.mkdirs()){
						Env.setDB_Path(this, dbPathName);
					} else {
						Env.setDB_Path(this, DB.DB_NAME);
					}
				}
				//	Create Document Folder
				File doc = new File(docPath);
				if(!doc.exists()) {
					if(doc.mkdirs()){
						//	Set Application Directory
						Env.setAppDirName(this, docPath);
					}
				}
			} else {
				Env.setDB_Path(this, DB.DB_NAME);
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
    
}
