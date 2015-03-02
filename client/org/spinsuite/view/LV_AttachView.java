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

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.adapters.ImageTextAdapter;
import org.spinsuite.base.R;
import org.spinsuite.util.AttachmentHandler;
import org.spinsuite.util.DisplayImageTextItem;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Feb 28, 2015, 12:47:08 AM
 *
 */
public class LV_AttachView extends Activity {
	
	/**	List View				*/
	private ListView 							lv_AttachmentList 	= null;
	/**	File Path				*/
	private String								m_FilePath			= null;
	/**	Activity				*/
	private Activity							v_activity			= null;
	/**	Data					*/
	private ArrayList<DisplayImageTextItem> 	data				= null;
	/**	Number Format			*/
	private DecimalFormat						m_numberFormat		= null;
	/**	Option Menu				*/
	private static final int 		O_SHARE 			= 1;
	private static final int 		O_DELETE 			= 2;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_attach_list_view);
		//	Get File Path
		m_FilePath = getIntent().getStringExtra("FilePath");
    	//	Get Elements
		lv_AttachmentList = (ListView) findViewById(R.id.lv_AttachmentList);
		//	
		lv_AttachmentList.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View arg1, int position,
					long arg3) {
				DisplayImageTextItem item = (DisplayImageTextItem) lv_AttachmentList.getAdapter().getItem(position);
				//	Show Image
				if(item.getValue() != null) {
					String fileName = item.getValue();
					File file = new File(m_FilePath + File.separator + fileName);
					//	Show
					showAttachment(Uri.fromFile(file));
				}
			}
        });
		//	
		v_activity = this;
		//	Event
		registerForContextMenu(lv_AttachmentList);
		//	Enable Return
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		//	Title
    	getActionBar().setSubtitle(getString(R.string.Action_ViewAttachment));
    	//	Get Default Number Format
    	m_numberFormat = DisplayType.getNumberFormat(v_activity, DisplayType.AMOUNT, "###,###,###.##");
    	//	Load Files
    	new LoadTask().execute();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if(itemId == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.lv_AttachmentList) {
			//	Export
		    menu.add(Menu.NONE, O_SHARE, 
					Menu.NONE, getString(R.string.Action_Share));
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
		    case O_DELETE:
		    	actionDelete(info.position);
		        return true;
		    default:
		        return super.onContextItemSelected(item);
	    }
	}
	
	/**
	 * Show a image
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param uriPath
	 * @return void
	 */
	private void showAttachment(Uri uriPath) {
		boolean ok = false;
		try {
			//	Launch Application
			Intent intent = new Intent(Intent.ACTION_VIEW);
			//	Set Data Type
			if(isGraphic(uriPath.toString()))
				intent.setDataAndType(uriPath, "image/*");
			else if(isPDF(uriPath.toString()))
				intent.setDataAndType(uriPath, "application/pdf");
			else 
				intent.setDataAndType(uriPath, "*/*");
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//	Start Activity
			startActivity(intent);
			//	
			ok = true;
		} catch (ActivityNotFoundException e) {
			LogM.log(this, getClass(), Level.WARNING, 
					"Error Launch Image: " + e.getLocalizedMessage());
		}
		//	Show Toast
		if(!ok)
			Msg.toastMsg(v_activity, getString(R.string.msg_AppIsNotAssociated));

	}
	
	/**
	 * Delete Attachment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param position
	 * @return void
	 */
	private void actionDelete(final int position) {
		final DisplayImageTextItem item = data.get(position);
		String msg_Acept = this.getResources().getString(R.string.msg_Acept);
		Builder ask = Msg.confirmMsg(this, getResources().getString(R.string.msg_AskDelete));
		ask.setPositiveButton(msg_Acept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				//	Delete
				String fileName = item.getValue();
				File file = new File(m_FilePath + File.separator + fileName);
				if(file.exists()) {
					if(!file.delete()) {
						file.deleteOnExit();
					}
					//	Delete Item
					data.remove(position);
					//	Reload
					showView();
				}
			}
		});
		ask.show();
	}
	
	/**
	 * Show View in List
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/11/2014, 13:26:33
	 * @return void
	 */
	private void showView() {
		ImageTextAdapter mi_adapter = new ImageTextAdapter(v_activity, R.layout.i_image_text, data);
		mi_adapter.setDropDownViewResource(R.layout.i_image_text);
		//	
		lv_AttachmentList.setAdapter(mi_adapter);
	}
	
	/**
	 * Share Image
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param position
	 * @return void
	 */
	private void actionShare(int position) {
		//	
		DisplayImageTextItem item = (DisplayImageTextItem) lv_AttachmentList.getAdapter().getItem(position);
		//	Path
		String localPath = m_FilePath + File.separator + item.getValue();
		//	
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
		if(isGraphic(localPath))
			shareIntent.setType("image/*");
		else if(isPDF(localPath))
			shareIntent.setType("application/pdf");
		else 
			shareIntent.setType("*/*");
		//	
		startActivity(Intent.createChooser(shareIntent, 
				getResources().getText(R.string.Action_Share)));
	}
	
	/**
	 * Load Files
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Feb 28, 2015, 12:47:42 AM
	 *
	 */
	private class LoadTask extends AsyncTask<Void, Void, Void> {

		/**	Progress Bar			*/
		private ProgressDialog 						v_PDialog;
		
		@Override
		protected void onPreExecute() {
			v_PDialog = ProgressDialog.show(v_activity, null, 
					getString(R.string.msg_Loading), false, false);
			//	Set Max
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			loadData();
			//	Return
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Void... progress) {
			
		}

		@Override
		protected void onPostExecute(Void result) {
			//	Set Adapter
			showView();
			//	Hide
			v_PDialog.dismiss();
		}
		
		/**
		 * Load List View Attachment
		 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
		 * @return void
		 */
		private void loadData() {
			//	Data
			data = new ArrayList<DisplayImageTextItem>();
			//	
			File directory = new File(m_FilePath);
			File [] m_Files = directory.listFiles();
			//	Verify if has files
			if(m_Files != null 
					&& m_Files.length > 0) {
				for(File m_File : m_Files) {
					String fileName = m_File.getAbsolutePath();
					if(fileName != null
							&& 
							(fileName.contains(".jpg")
									|| fileName.contains(".jpeg")
									|| fileName.contains(".png"))) {
						//	Get Bytes
						//	Decode
						Bitmap bmimage = AttachmentHandler.getBitmapFromFile(fileName, 200, 200);
						//	Add to Array
						data.add(new DisplayImageTextItem(0, m_File.getName(), getPrettySize(m_File), bmimage));
					} else {
						data.add(new DisplayImageTextItem(0, m_File.getName(), getPrettySize(m_File), null));
					}
				}
			}
		}
		
		/**
		 * Get Pretty Size
		 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 11/11/2014, 13:39:39
		 * @param file
		 * @return
		 * @return String
		 */
		private String getPrettySize(File file) {
			float size = file.length();
			if (size <= 1024) {
				return m_numberFormat.format(size) + " B";
			} else {
				size /= 1024;
				if (size > 1024) {
					size /= 1024;
					return m_numberFormat.format(size) + " MB";
				} else {
					return m_numberFormat.format(size) + " kB";
				}
			}
		}
	}
	
	/**
	 * 	Is attachment entry a PDF
	 *  @param fileName
	 *	@return true if PDF
	 */
	public boolean isPDF(String fileName) {
		return fileName.toLowerCase(Env.getLocate(v_activity)).endsWith(".pdf");
	}	//	isPDF
	
	/**
	 * 	Is attachment entry a Graphic
	 *  @param fileName
	 *	@return true if *.gif, *.jpg, *.png
	 */
	public boolean isGraphic(String fileName) {
		String m_lowname = fileName.toLowerCase(Env.getLocate(v_activity));
		return m_lowname.endsWith(".gif") 
				|| m_lowname.endsWith(".jpg")
				|| m_lowname.endsWith(".jpeg")
				|| m_lowname.endsWith(".png");
	}	//	isGraphic
}
