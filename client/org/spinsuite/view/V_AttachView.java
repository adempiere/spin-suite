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

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.util.ActivityParameter;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class V_AttachView extends Activity {
	
	/**	Parameter				*/
	private ActivityParameter		m_activityParam = null;
	
	
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		super.setContentView(R.layout.v_attach_view);
    	//	Get Field
    	Bundle bundle = getIntent().getExtras();
		if(bundle != null)
			m_activityParam = (ActivityParameter) bundle.getParcelable("Param");
		//	
		if(m_activityParam == null)
			m_activityParam = new ActivityParameter();
		//	Get Elements
		//ll_HeaderReport	= (LinearLayout) findViewById(R.id.ll_HeaderReport);
		
		ImageView iView = (ImageView) findViewById(R.id.imgView);
		
		DB conn = new DB(this);
		DB.loadConnection(conn, DB.READ_ONLY);
		Cursor rs = null;
		rs = conn.querySQL("SELECT BinaryData FROM AD_Attachment WHERE AD_Table_ID = " + m_activityParam.getFrom_SPS_Table_ID() + " AND Record_ID = " + m_activityParam.getFrom_Record_ID(), null);
		if(rs.moveToFirst()){
			byte[] attach = rs.getBlob(0);
			if(attach != null) {
				Bitmap bmimage = BitmapFactory.decodeByteArray(attach, 0,
						attach.length);
				iView.setImageBitmap(bmimage);
			}
		}
		DB.closeConnection(conn);
		//	Title
    	getActionBar().setSubtitle("Test");
	}
}
