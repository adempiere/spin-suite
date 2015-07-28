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
 * Copyright (C) 2012-2015 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.util.option;

import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.bchat.view.V_BChat;
import org.spinsuite.util.DisplaySearchItem;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Jul 28, 2015, 10:37:43 AM
 * <li> Add Support to Menu in Standard Search
 * @see https://adempiere.atlassian.net/browse/SPIN-26
 */
public class SS_MenuItem_C_BPartner implements I_SS_MenuOption {
	
	/**	Option Menu for BPartner	*/
	private final int m_OptionMenu[] = new int[]
			{R.string.Action_Call, 
			R.string.Action_Add_To_Contact, 
			R.string.Action_Send_BChat, 
			R.string.Action_Send_Mail};

	@Override
	public int[] getMenuOption() {
		return m_OptionMenu;
	}

	@Override
	public void actionMenu(Context ctx, int p_Menu_ID, DisplaySearchItem item) {
		switch (p_Menu_ID) {
		case R.string.Action_Call:
			callBPartner(ctx, item);
			break;
		case R.string.Action_Add_To_Contact:
			callBPartner(ctx, item);
			break;
		case R.string.Action_Send_BChat:
			sendBChatBPartner(ctx, item);
			break;
		case R.string.Action_Send_Mail:
			sendMailBPartner(ctx, item);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Call Business Partner
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param item
	 * @return void
	 */
	private void callBPartner(Context ctx, DisplaySearchItem item) {
		String phone = DB.getSQLValueString(ctx, "SELECT MAX(COALESCE(u.Phone2, u.Phone)) Phone2 "
				+ "FROM AD_User u "
				+ "WHERE u.C_BPartner_ID = ? "
				+ "ORDER BY u.Updated", String.valueOf(item.getRecord_ID()));
		//	Valid Phone
		if(phone == null) {
			phone = DB.getSQLValueString(ctx, "SELECT MAX(COALESCE(l.Phone2, l.Phone)) Phone2 "
					+ "FROM C_BPartner_Location l "
					+ "WHERE l.C_BPartner_ID = ? "
					+ "ORDER BY l.Updated", String.valueOf(item.getRecord_ID()));
		}
		//	Do It
		if(phone != null) {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + phone));
			ctx.startActivity(callIntent);
		} else {
			Msg.toastMsg(ctx, "@No@ @InfoBPartner@");
		}
	}
	
	/**
	 * SEnd a Business Chat to BPartner
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param item
	 * @return void
	 */
	private void sendBChatBPartner(Context ctx, DisplaySearchItem item) {
		int m_AD_User_ID = 0;
		String m_UserName = null;
		//	Get Values
		try {
			DB conn = new DB(ctx);
			DB.loadConnection(conn, DB.READ_ONLY);
			conn.compileQuery("SELECT u.AD_User_ID, u.Name UserName "
					+ "FROM AD_User u "
					+ "WHERE u.C_BPartner_ID = ? "
					+ "ORDER BY u.Updated");
			//	Add Parameter
			conn.addInt(item.getRecord_ID());
			//	Get Values
			Cursor rs = conn.querySQL();
			//	
			if(rs.moveToFirst()) {
				m_AD_User_ID = rs.getInt(0);
				m_UserName = rs.getString(1);
			}
			//	Close Connection
			DB.closeConnection(conn);
		} catch (Exception e) {
			LogM.log(ctx, getClass(), Level.SEVERE, e.getLocalizedMessage());
			Msg.toastMsg(ctx, e.getLocalizedMessage());
		}
		//	Do It
		if(m_AD_User_ID >= 0
				&& m_UserName != null) {
			Intent bChat = new Intent(ctx, V_BChat.class);
			bChat.putExtra("AD_User_ID", m_AD_User_ID);
			bChat.putExtra("UserName", m_UserName);
			ctx.startActivity(bChat);
		} else {
			Msg.toastMsg(ctx, "@No@ @InfoBPartner@");
		}
	}
	
	/**
	 * Call Business Partner
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param item
	 * @return void
	 */
	private void sendMailBPartner(Context ctx, DisplaySearchItem item) {
		String eMail = DB.getSQLValueString(ctx, "SELECT MAX(u.EMail) EMail "
				+ "FROM AD_User u "
				+ "WHERE u.C_BPartner_ID = ? "
				+ "ORDER BY u.Updated", String.valueOf(item.getRecord_ID()));
		//	Do It
		if(eMail != null) {
			Intent sendMail = new Intent();
			sendMail.setAction(Intent.ACTION_SEND);
			sendMail.setData(Uri.parse("mailto:"));
			sendMail.setType("message/rfc822");
			sendMail.putExtra(Intent.EXTRA_EMAIL, new String[]{eMail});
			sendMail.putExtra(Intent.EXTRA_SUBJECT, ctx.getResources().getText(R.string.msg_SharedFromSFAndroid));
			//	
			ctx.startActivity(Intent.createChooser(sendMail, 
					ctx.getResources().getText(R.string.Action_Send_Mail)));
		} else {
			Msg.toastMsg(ctx, "@No@ @InfoBPartner@");
		}
	}
}