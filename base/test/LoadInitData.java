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
package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.util.Env;
import org.spinsuite.util.Msg;

import android.content.Context;
import android.os.Environment;

/**
 * @author Yamel Senih
 *
 */
public class LoadInitData {

	private Context ctx;
	private static final String 	KEY_POS_TAB			= "posTab";
	
	/**
	 * 
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 15:56:19
	 * @param ctx
	 */
	public LoadInitData(Context ctx) {
		this.ctx = ctx;
	}
	
	/**
	 * Load initial data
	 * @author Yamel Senih 29/11/2012, 09:46:26
	 * @return void
	 */
	public void initialLoad_copyDB() {
		if(!Env.isEnvLoad(ctx)) {
			if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
				String basePathName = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
				//	Application Path
				String dbPath = basePathName + Env.DB_PATH_DIRECTORY;
				String dbPathName = basePathName + Env.DB_PATH_NAME;
				//	Documents 
				String docPathName = basePathName + Env.DOC_DIRECTORY;
				String tmpPathName = basePathName + Env.TMP_DIRECTORY;
				String attPathName = basePathName + Env.ATT_DIRECTORY;
				//	
				Env.setAppBaseDirectory(ctx, basePathName);
				Env.setDB_PathName(ctx, dbPathName);
				Env.setDoc_DirectoryPathName(ctx, docPathName);
				Env.setTmp_DirectoryPathName(ctx, tmpPathName);
				Env.setAtt_DirectoryPathName(ctx, attPathName);
				//	Database
				File f = new File(dbPath);
				if(!f.exists()) {
					if(!f.mkdirs())
						Msg.toastMsg(ctx, ctx.getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + dbPathName + "\"");
				} else if(f.isDirectory()) {
					File fDB = new File(dbPathName);
					fDB.delete();
				} else if(f.isFile()) {
					if(!f.mkdirs())
						Msg.toastMsg(ctx, ctx.getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + dbPathName + "\"");
				}
				//	Create Document Folder
				File doc = new File(docPathName);
				if(!doc.exists()
						|| doc.isFile()) {
					if(!doc.mkdirs())
						Msg.toastMsg(ctx, ctx.getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + docPathName + "\"");
				}
				//	Create Tmp Folder
				File tmp = new File(tmpPathName);
				if(!tmp.exists()
						|| tmp.isFile()) {
					if(!tmp.mkdirs())
						Msg.toastMsg(ctx, ctx.getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + tmpPathName + "\"");
				}
				//	Create Attachment Folder
				File att = new File(attPathName);
				if(!att.exists()
						|| att.isFile()) {
					if(!att.mkdirs())
						Msg.toastMsg(ctx, ctx.getString(R.string.msg_ErrorCreatingDirectory) 
								+ "\"" + attPathName + "\"");
				}
			} else {
				Env.setDB_PathName(ctx, DB.DB_NAME);
			}
			//	
			try {
				InputStream is = ctx.getResources().openRawResource(R.raw.spin_suite);
				
				File f = new File(Env.getDB_PathName(ctx));
				
				OutputStream outputStream = new FileOutputStream(f);

				int buffersize = 9048576;
				byte[] buffer = new byte[buffersize];

				int available = 0;
				//	Copy
				while ((available = is.read(buffer)) >= 0) {
					outputStream.write(buffer, 0, available);
				}
				//	Close
				outputStream.close();
				//	Set Conext
				setContextTest();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}		
	}	//	initialLoad_copyDB

	/**
	 * Set Context
	 * @author Yamel Senih 30/11/2012, 11:55:26
	 * @return void
	 */
	public void setContextTest() {
		//	Set Context Default Values for Demo
		Env.setIsEnvLoad(ctx, true);
		Env.setContext(ctx, "#SUser", "SuperUser");
		Env.setContext(ctx, "#SPass", "System");
		Env.setSavePass(ctx, true);
		Env.setAutoLogin(ctx, true);
		Env.setContext(ctx, KEY_POS_TAB, 1);
		Env.setContext(ctx, "#Timeout", 10000000);
	}	
}
