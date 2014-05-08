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

import java.util.ArrayList;

import org.spinsuite.adapters.ImageTextAdapter;
import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.util.ActivityParameter;
import org.spinsuite.util.DisplayImageTextItem;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class LV_AttachView extends FragmentActivity {
	
	/**	Parameter				*/
	private ActivityParameter		m_activityParam = null;
	/**	List View				*/
	private ListView 				lv_AttachmentList = null;
	/**	Option Menu				*/
	private final int 				O_DOWNLOAD = 1;
	private final int 				O_DELETE = 2;
	
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
		//ll_HeaderReport	= (LinearLayout) findViewById(R.id.ll_HeaderReport);
		
		lv_AttachmentList = (ListView) findViewById(R.id.lv_AttachmentList);
		//	Event
		registerForContextMenu(lv_AttachmentList);
		//lv_AttachmentList.seton
		//	Title
    	getActionBar().setSubtitle("Test");
    	//	
    	loadAttachment();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.lv_AttachmentList) {
		    //AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
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
		    case O_DOWNLOAD:
		    	actionDownload(info.position);
		        return true;
		    case O_DELETE:
		    	actionDelete(info.position);
		        return true;
		    default:
		        return super.onContextItemSelected(item);
	    }
	}
	
	/**
	 * Delete Attachment
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/05/2014, 11:30:23
	 * @param position
	 * @return void
	 */
	private void actionDelete(int position){
		DisplayImageTextItem item = (DisplayImageTextItem) lv_AttachmentList.getAdapter().getItem(position);
		//	Delete
		DB.executeUpdate(getApplicationContext(), "DELETE FROM AD_Attachment WHERE AD_Attachment_ID = ?", item.getRecord_ID());
		//	Requery
		loadAttachment();
	}
	
	/**
	 * Download Attachment
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 08/05/2014, 11:31:01
	 * @param position
	 * @return void
	 */
	private void actionDownload(int position){
		
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
