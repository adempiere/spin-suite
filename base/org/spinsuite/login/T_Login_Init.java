/*************************************************************************************
 * Product: Spin-Suite (Making your Business Spin)                                   *
 * getActivity() program is free software; you can redistribute it and/or modify it           *
 * under the terms version 2 of the GNU General Public License as published          *
 * by the Free Software Foundation. getActivity() program is distributed in the hope          *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied        *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                  *
 * See the GNU General Public License for more details.                              *
 * You should have received a copy of the GNU General Public License along           *
 * with getActivity() program; if not, write to the Free Software Foundation, Inc.,           *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                            *
 * For the text or an alternative of getActivity() public license, you may reach us           *
 * Copyright (C) 2012-2013 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Carlos Parada www.erpcya.com                      				 *
 *************************************************************************************/
package org.spinsuite.login;


import java.io.File;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.sync.InitialLoadTask;
import org.spinsuite.util.Env;
import org.spinsuite.util.Msg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * 
 * @author Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com Mar 6, 2015, 12:29:57 AM
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 * 	<li>Add Url for connection
 *
 */
@SuppressLint("ValidFragment")
public class T_Login_Init extends DialogFragment 
							implements OnClickListener {
	
	/** Text User				*/
	private EditText 	et_User;
	/** Text Password			*/
	private EditText 	et_PassWord;
	/**	URL SOAP Communication	*/
	public EditText 	et_UrlSoap;
	/**	Synchronization Method	*/
	private EditText 	et_Method;
	private EditText 	et_Timeout;
	/** NameSpace*/
	private EditText 	et_NameSpace;
	/**	Context					*/
	private Activity 	m_Callback = null;
	
	/**	Default Values			*/
	private final String DEFAULT_SOAP_URL 	= "http://200.71.185.123:9080/ADInterface/services/SpinSuiteService";
	private final String DEFAULT_NAME_SPACE = "http://www.erpcya.com/";
	private final String DEFAULT_METHOD 	= "InitialLoad";
	private final String DEFAULT_USER 		= "SuperUser";
	private final String DEFAULT_PASS 		= "System";
	
	/**
	 * 
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Callback
	 */
	public T_Login_Init(Activity p_Callback) {
		m_Callback = p_Callback;
	}
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.t_login_init, null);
		
		et_User		 	= (EditText) view.findViewById(R.id.et_User);
		et_PassWord 	= (EditText) view.findViewById(R.id.et_Pass);
		et_UrlSoap 		= (EditText) view.findViewById(R.id.et_UrlSoap);
    	et_Method 		= (EditText) view.findViewById(R.id.et_Method);
    	et_Timeout 		= (EditText) view.findViewById(R.id.et_Timeout);
    	et_NameSpace 	= (EditText) view.findViewById(R.id.et_NameSpace);
		//	Set Authentication for test
		et_User.setText(DEFAULT_USER);
		et_PassWord.setText(DEFAULT_PASS);
    	// Carlos Parada Setting Parameters for Spin-Suite Service Call 
    	et_UrlSoap.setText(DEFAULT_SOAP_URL);
    	et_NameSpace.setText(DEFAULT_NAME_SPACE);
    	et_Method.setText(DEFAULT_METHOD);
    	//End Carlos Parada
		
		builder.setView(view);
		
		builder.setNegativeButton(R.string.Action_Cancel, this);
		builder.setPositiveButton(android.R.string.ok, this);
		
		return builder.create();
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		//	Valid for Initial Sync
		if (which == -1
				&& isValid()){
			startSynchronization();
		}		
	}
	
	/**
	 * Start Synchronization for Initial Load
	 * @author <a href="mailto:carlosaparadam@gmail.com">Carlos Parada</a> Jul 8, 2014, 11:25:21 AM
	 * @return void
	 */
	public void startSynchronization(){
		InitialLoadTask ilt = new InitialLoadTask(et_UrlSoap.getText().toString(), et_NameSpace.getText().toString(), 
				et_Method.getText().toString(), true, 
				et_User.getText().toString(), 
				et_PassWord.getText().toString(), 
				et_NameSpace.getText().toString() + et_Method.getText().toString(), 
				m_Callback, Env.getContextAsInt(m_Callback, "#Timeout"));
		//	
		ilt.runTask();
		//	
		if(!Env.isEnvLoad(getActivity())) {
			
//			RemoteViews contentView = new RemoteViews(this.getPackageName(), R.layout.v_progressdialog);
//	        contentView.setImageViewResource(R.id.iV_Synchronizing, R.drawable.syncserver_m);
//	        contentView.setTextViewText(R.id.tV_CurrentSinchronizing, this.getResources().getString(R.string.msg_CallingWebService));
//	        contentView.setTextViewText(R.id.tV_Percentaje, "0%");
//	        
//	        NotificationManager notify = Msg.notificationMsg(this, R.drawable.syncserver_h, "",0, this.getParent().getIntent(), contentView);
//	        m_load.setContentView(contentView);
//	        m_load.setM_NotificationManager(notify);
//			m_load.execute();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle(R.string.tt_Conn);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/**
     * Valid fields on Activity
     * @author Yamel Senih 24/04/2012, 12:33:57
     * @return
     * @return boolean
     */
    private boolean isValid(){
    	if(et_UrlSoap.getText() != null 
    			&& et_UrlSoap.getText().toString().length() > 0){
    		if(et_Method.getText() != null 
    				&& et_Method.getText().toString().length() > 0){
    			if(et_NameSpace.getText() != null 
        				&& et_NameSpace.getText().toString().length() > 0){
    				Env.setContext(getActivity(), "#SUrlSoap", et_UrlSoap.getText().toString());
    				Env.setContext(getActivity(), "#SMethod", et_Method.getText().toString());
    				Env.setContext(getActivity(), "#SNameSpace", et_NameSpace.getText().toString());
    				createDBDirectory();
    				
    				if(et_Timeout.getText() != null 
    	    				&& et_Timeout.getText().toString().length() > 0){
    					String limit = et_Timeout.getText().toString();
    					Env.setContext(getActivity(), "#Timeout", Integer.parseInt(limit));
    				}
    				
    	    		return true;
    			} else {
            		Msg.alertMustFillField(getActivity(), R.string.NameSpace, et_NameSpace);
            	}
        	} else {
        		Msg.alertMustFillField(getActivity(), R.string.MethodSync, et_Method);
        	}
    	} else {
    		Msg.alertMustFillField(getActivity(), R.string.Url_Soap, et_UrlSoap);
    	}
    	return false;
    }
    
    /**
	 * Create a folder /ERP/data with Database
	 * @author Yamel Senih 19/08/2012, 05:45:05
	 * @return void
	 */
	private void createDBDirectory(){
		if(!Env.isEnvLoad(getActivity())){
			if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)
					//&& ch_SaveSD.isChecked()
					){
				String basePathName = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
				//	Application Path
				String dbPath = basePathName + Env.DB_PATH_DIRECTORY;
				String dbPathName = basePathName + Env.DB_PATH_NAME;
				//	Documents 
				String docPathName = basePathName + Env.DOC_DIRECTORY;
				String tmpPathName = basePathName + Env.TMP_DIRECTORY;
				String attPathName = basePathName + Env.ATT_DIRECTORY;
				
				//	
				Env.setAppBaseDirectory(getActivity(), basePathName);
				Env.setDB_PathName(getActivity(), dbPathName);
				Env.setDoc_DirectoryPathName(getActivity(), docPathName);
				Env.setTmp_DirectoryPathName(getActivity(), tmpPathName);
				Env.setAtt_DirectoryPathName(getActivity(), attPathName);
				//	Database
				File f = new File(dbPath);
				if(!f.exists()) {
					if(!f.mkdirs())
						Msg.toastMsg(getActivity(), getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + dbPathName + "\"");
				} else if(f.isDirectory()) {
					File fDB = new File(dbPathName);
					fDB.delete();
				} else if(f.isFile()){
					if(!f.mkdirs())
						Msg.toastMsg(getActivity(), getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + dbPathName + "\"");
				}
				//	Create Document Folder
				File doc = new File(docPathName);
				if(!doc.exists()
						|| doc.isFile()) {
					if(!doc.mkdirs())
						Msg.toastMsg(getActivity(), getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + docPathName + "\"");
				}
				//	Create Tmp Folder
				File tmp = new File(tmpPathName);
				if(!tmp.exists()
						|| tmp.isFile()) {
					if(!tmp.mkdirs())
						Msg.toastMsg(getActivity(), getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + tmpPathName + "\"");
				}
				//	Create Attachment Folder
				File att = new File(attPathName);
				if(!att.exists()
						|| att.isFile()) {
					if(!att.mkdirs())
						Msg.toastMsg(getActivity(), getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + attPathName + "\"");
				}
			} else {
				Env.setDB_PathName(getActivity(), DB.DB_NAME);
			}	
    	}
	}

	
}