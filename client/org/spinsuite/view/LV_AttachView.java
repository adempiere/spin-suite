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
 * Copyright (C) 2012-2014 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.adapters.ImageTextAdapter;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.util.ActivityParameter;
import org.spinsuite.util.DisplayImageTextItem;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class LV_AttachView extends Activity {
	
	/**	Parameter				*/
	private ActivityParameter		m_activityParam 	= null;
	/**	List View				*/
	private ListView 				lv_AttachmentList 	= null;
	/**	Option Menu				*/
	private static final int 		O_SHARE 			= 1;
	private static final int 		O_DOWNLOAD 			= 2;
	private static final int 		O_DELETE 			= 3;
	private static final String 	JPEG_FILE_SUFFIX 	= ".jpg";
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_attach_list_view);
    	//	Get Field
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null)
			m_activityParam = (ActivityParameter) bundle.getParcelable("Param");
		//	
		if(m_activityParam == null)
			m_activityParam = new ActivityParameter();
		//	Get Elements
		lv_AttachmentList = (ListView) findViewById(R.id.lv_AttachmentList);
		//	
		lv_AttachmentList.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				DisplayImageTextItem item = (DisplayImageTextItem) lv_AttachmentList.getAdapter().getItem(position);
				//	Show Image
				if(item.getImage() != null){
					String path = Images.Media.insertImage(getApplicationContext().getContentResolver(), 
							item.getImage(), item.getValue(), null);
					//	Show
					showImage(Uri.parse(path));
				}
			}
        });
		//	Event
		registerForContextMenu(lv_AttachmentList);
		//	Title
    	getActionBar().setSubtitle(getString(R.string.Action_ViewAttachment));
    	//	
    	loadAttachment();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.lv_AttachmentList) {
			//	Export
		    menu.add(Menu.NONE, O_SHARE, 
					Menu.NONE, getString(R.string.Action_Share));
			//	Export
		    menu.add(Menu.NONE, O_DOWNLOAD, 
					Menu.NONE, getString(R.string.Action_DownloadAttachment));
		    //	Delete
		    menu.add(Menu.NONE, O_DELETE, 
					Menu.NONE, getString(R.string.Action_Delete));
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
	            .getMenuInfo();
	    //	Options
	    switch (item.getItemId()) {
	    	case O_SHARE:
	    		actionShare(info.position);
	    		return true;
		    case O_DOWNLOAD:
		    	String path = null;
		    	path = actionDownload(info.position);
		    	//	Show Path
				if(path != null)
					showImage(Uri.fromFile(new File(path)));
		    	//	
		        return true;
		    case O_DELETE:
		    	actionDelete(info.position);
		        return true;
		    default:
		        return super.onContextItemSelected(item);
	    }
	}
	
	/**
	 * Show a Image
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 09/05/2014, 11:31:29
	 * @param uriPath
	 * @return void
	 */
	private void showImage(Uri uriPath){
		try {
			//	Launch Application
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(uriPath, "image/*");
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//	Start Activity
			startActivity(intent);
		} catch (ActivityNotFoundException e){
			LogM.log(this, getClass(), Level.WARNING, 
					"Error Launch Image: " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * Delete Attachment
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/05/2014, 11:30:23
	 * @param position
	 * @return void
	 */
	private void actionDelete(int position){
		final DisplayImageTextItem item = (DisplayImageTextItem) lv_AttachmentList.getAdapter().getItem(position);
		String msg_Acept = this.getResources().getString(R.string.msg_Acept);
		Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_AskDelete));
		ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				//	Delete
				DB.executeUpdate(getApplicationContext(), "DELETE FROM AD_Attachment " +
						"WHERE AD_Attachment_ID = ?", item.getRecord_ID());
				//	Re-Query
				loadAttachment();
			}
		});
		ask.show();
	}
	
	/**
	 * Download Attachment
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/05/2014, 11:31:01
	 * @param position
	 * @return void
	 * @throws IOException 
	 */
	private String actionDownload(int position) {
		String msg = null;
		try {
			DisplayImageTextItem item = (DisplayImageTextItem) lv_AttachmentList.getAdapter().getItem(position);
			if(item.getImage() != null){
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				//	Get Image
				Bitmap image = item.getImage();
				//	Compress
				String path = Env.getImg_DirectoryPathName(this);
				path = path + File.separator + item.getValue() + JPEG_FILE_SUFFIX;
				image.compress(Bitmap.CompressFormat.PNG, 100, bos);
				File file = new File(path);
				file.createNewFile();
				//	Write the bytes in file
				FileOutputStream fOut = new FileOutputStream(file);
				fOut.write(bos.toByteArray());
				//	Close Output
				fOut.close();
				return path;
			}
		} catch (IOException e) {
			LogM.log(getApplicationContext(), getClass(), 
					Level.SEVERE, "Error Download Image:", e);
			msg = getResources().getString(R.string.msg_IOException) 
						+ " " + e.getLocalizedMessage();
		}
		//	Show Message
		if(msg != null)
			Msg.alertMsg(this, msg);
		//	Return
		return null;
	}
	
	/**
	 * Share Image
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 09/05/2014, 16:38:08
	 * @param position
	 * @return void
	 */
	private void actionShare(int position){
		//	Path
		String localPath = actionDownload(position);
		//	
		DisplayImageTextItem item = (DisplayImageTextItem) lv_AttachmentList.getAdapter().getItem(position);
    	//	
		if(localPath != null) {
			//	Share
			Uri sourceUri = Uri.fromFile(new File(localPath));
			//	
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_STREAM, sourceUri);
			shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getText(R.string.msg_Image) 
					+ " \"" + item.getValue() + "\"");
			shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.msg_SharedFromSFAndroid));
			//	
			shareIntent.setType("image/*");
			//	
			startActivity(Intent.createChooser(shareIntent, 
					getResources().getText(R.string.Action_Share)));
		}
	}
	
	/**
	 * Load List view with attachment
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/05/2014, 09:36:49
	 * @return void
	 */
	private void loadAttachment(){
		//	Data
		ArrayList<DisplayImageTextItem> data = new ArrayList<DisplayImageTextItem>();
		//	Load Connection
		DB conn = new DB(this);
		DB.loadConnection(conn, DB.READ_ONLY);
		Cursor rs = null;
		rs = conn.querySQL("SELECT att.AD_Attachment_ID, att.Title, att.TextMsg, att.BinaryData " +
				"FROM AD_Attachment att " +
				"WHERE att.AD_Table_ID = ? AND att.Record_ID = ?", 
				new String[]{String.valueOf(m_activityParam.getFrom_SPS_Table_ID()), 
										String.valueOf(m_activityParam.getFrom_Record_ID())});
		if(rs.moveToFirst()){
			do{
				byte[] attach = rs.getBlob(3);
				Bitmap bmimage = null;
				if(attach != null) {
					bmimage = BitmapFactory.decodeByteArray(attach, 0,
							attach.length);
					data.add(new DisplayImageTextItem(rs.getInt(0), rs.getString(1), rs.getString(2), bmimage));
				}
			}while(rs.moveToNext());
		}
		DB.closeConnection(conn);
		//	Set Adapter
		ImageTextAdapter mi_adapter = new ImageTextAdapter(this, R.layout.i_image_text, data);
		mi_adapter.setDropDownViewResource(R.layout.i_image_text);
		//	
		lv_AttachmentList.setAdapter(mi_adapter);
	}
}
