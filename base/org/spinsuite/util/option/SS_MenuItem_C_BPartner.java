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

import java.util.ArrayList;
import java.util.logging.Level;

import org.spinsuite.base.DB;
import org.spinsuite.base.R;
import org.spinsuite.bchat.view.V_BChat;
import org.spinsuite.util.DisplaySearchItem;
import org.spinsuite.util.KeyNamePair;
import org.spinsuite.util.LogM;
import org.spinsuite.util.Msg;
import org.spinsuite.util.StringNamePair;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Intents;

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
			addAddressBPartner(ctx, item);
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
	 * Get Phone List
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param item
	 * @return
	 * @return ArrayList<StringNamePair>
	 */
	private ArrayList<StringNamePair> getPhone(Context ctx, DisplaySearchItem item) {
		ArrayList<StringNamePair> m_Phone = new ArrayList<StringNamePair>();
		//	
		try {
			DB conn = new DB(ctx);
			DB.loadConnection(conn, DB.READ_ONLY);
			StringBuffer sql = new StringBuffer("SELECT u.Name UserName, u.Phone PhoneCall "
					+ "FROM AD_User u "
					+ "WHERE u.C_BPartner_ID = ? AND u.Phone IS NOT NULL ");
			//	Union
			sql.append("UNION ALL ");
			sql.append("SELECT u.Name UserName, u.Phone2 PhoneCall "
					+ "FROM AD_User u "
					+ "WHERE u.C_BPartner_ID = ? AND u.Phone2 IS NOT NULL ");
			//	Get from Address
			sql.append("UNION ALL ");
			sql.append("SELECT l.Name, l.Phone PhoneCall "
					+ "FROM C_BPartner_Location l "
					+ "WHERE l.C_BPartner_ID = ? AND l.Phone IS NOT NULL ");
			//	Union
			sql.append("UNION ALL ");
			sql.append("SELECT l.Name, l.Phone2 PhoneCall "
					+ "FROM C_BPartner_Location l "
					+ "WHERE l.C_BPartner_ID = ? AND l.Phone2 IS NOT NULL ");
			//	Compile
			conn.compileQuery(sql.toString());
			//	Add Parameter
			conn.addInt(item.getRecord_ID());
			conn.addInt(item.getRecord_ID());
			conn.addInt(item.getRecord_ID());
			conn.addInt(item.getRecord_ID());
			//	Get Values
			Cursor rs = conn.querySQL();
			//	
			if(rs.moveToFirst()) {
				do {
					m_Phone.add(new StringNamePair(rs.getString(0), rs.getString(1)));
				} while(rs.moveToNext());
			}
			//	Close Connection
			DB.closeConnection(conn);
		} catch (Exception e) {
			LogM.log(ctx, getClass(), Level.SEVERE, e.getLocalizedMessage());
			Msg.toastMsg(ctx, e.getLocalizedMessage());
		}
		//	Return Phone List
		return m_Phone;
	}
	
	/**
	 * Call Business Partner
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param item
	 * @return void
	 */
	private void callBPartner(final Context ctx, DisplaySearchItem item) {
		final ArrayList<StringNamePair> m_Phone = getPhone(ctx, item);
		//	Do It
		if(m_Phone.size() == 1) {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + m_Phone.get(0).getName()));
			ctx.startActivity(callIntent);
		} else if(m_Phone.size() > 1){
			String m_PhoneArray[] = new String[m_Phone.size()];
			//	Load Items for views
			for(int i = 0; i < m_Phone.size(); i++) {
				StringNamePair pair = m_Phone.get(i);
				m_PhoneArray[i] = pair.getKey() + ": " + pair.getName();
			}
			//	
			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
			builder.setTitle(ctx.getString(R.string.PickNumber));
			builder.setItems(m_PhoneArray, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + m_Phone.get(item).getName()));
					ctx.startActivity(callIntent);
			    }
			});
			//	Create Dialog
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			Msg.toastMsg(ctx, "@No@ @InfoBPartner@");
		}
	}
	
	/**
	 * Add to Address Book
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param item
	 * @return void
	 */
	private void addAddressBPartner(Context ctx, DisplaySearchItem item) {
		//	Get Complete Name
		String m_Name = DB.getSQLValueString(ctx, 
				"SELECT b.Name || CASE WHEN b.Name2 IS NOT NULL THEN ' ' ||b.Name2 ELSE '' END FullName "
				+ "FROM C_BPartner b "
				+ "WHERE b.C_BPartner_ID = ?", String.valueOf(item.getRecord_ID()));
		//	Get Phone
		ArrayList<StringNamePair> m_Phone = getPhone(ctx, item);
		//	Get Mails
		ArrayList<StringNamePair> m_EMail = getEMail(ctx, item);
		//	
		if(m_Phone.size() != 0
				|| m_EMail.size() != 0)  {
			Intent address = new Intent(Intents.Insert.ACTION);
			address.setType(ContactsContract.RawContacts.CONTENT_TYPE);
			address.putExtra(Intents.Insert.NAME, m_Name);
			address.putExtra(Intents.Insert.NOTES, m_Name);
			//	For Phone
			if(m_Phone.size() > 0) {
				for(int i = 0; i< m_Phone.size(); i++) {
					if(i == 0) {
						address.putExtra(Intents.Insert.PHONE, m_Phone.get(i).getName());
						address.putExtra(Intents.Insert.PHONE_TYPE, Phone.TYPE_WORK);
					} else if(i == 1) {
						address.putExtra(Intents.Insert.SECONDARY_PHONE, m_Phone.get(i).getName());
						address.putExtra(Intents.Insert.SECONDARY_PHONE_TYPE, Phone.TYPE_WORK);
					} else if(i == 2) {
						address.putExtra(Intents.Insert.TERTIARY_PHONE, m_Phone.get(i).getName());
						address.putExtra(Intents.Insert.TERTIARY_PHONE_TYPE, Phone.TYPE_WORK);
					} else {
						break;
					}
				}
			}
			//	For Email
			if(m_EMail.size() > 0) {
				for(int i = 0; i< m_EMail.size(); i++) {
					if(i == 0) {
						address.putExtra(Intents.Insert.EMAIL, m_EMail.get(i).getName());
						address.putExtra(Intents.Insert.EMAIL_TYPE, Phone.TYPE_WORK);
					} else if(i == 1) {
						address.putExtra(Intents.Insert.SECONDARY_EMAIL, m_EMail.get(i).getName());
						address.putExtra(Intents.Insert.SECONDARY_EMAIL_TYPE, Phone.TYPE_WORK);
					} else if(i == 2) {
						address.putExtra(Intents.Insert.TERTIARY_EMAIL, m_EMail.get(i).getName());
						address.putExtra(Intents.Insert.TERTIARY_EMAIL_TYPE, Phone.TYPE_WORK);
					} else {
						break;
					}
				}
			}
			//	Launch
			ctx.startActivity(address);
		} else {
			Msg.toastMsg(ctx, "@No@ @InfoBPartner@");
		}
		
	}
	
	/**
	 * Send a Business Chat to BPartner
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param item
	 * @return void
	 */
	private void sendBChatBPartner(final Context ctx, DisplaySearchItem item) {
		final ArrayList<KeyNamePair> m_Contact = new ArrayList<KeyNamePair>();
		//	
		try {
			DB conn = new DB(ctx);
			DB.loadConnection(conn, DB.READ_ONLY);
			StringBuffer sql = new StringBuffer("SELECT u.AD_User_ID, u.Name UserName "
					+ "FROM AD_User u "
					+ "WHERE u.C_BPartner_ID = ? "
					+ "ORDER BY u.Updated DESC");
			//	Compile
			conn.compileQuery(sql.toString());
			//	Add Parameter
			conn.addInt(item.getRecord_ID());
			//	Get Values
			Cursor rs = conn.querySQL();
			//	
			if(rs.moveToFirst()) {
				do {
					m_Contact.add(new KeyNamePair(rs.getInt(0), rs.getString(1)));
				} while(rs.moveToNext());
			}
			//	Close Connection
			DB.closeConnection(conn);
		} catch (Exception e) {
			LogM.log(ctx, getClass(), Level.SEVERE, e.getLocalizedMessage());
			Msg.toastMsg(ctx, e.getLocalizedMessage());
		}
		//	Do It
		if(m_Contact.size() == 1) {
			Intent bChat = new Intent(ctx, V_BChat.class);
			bChat.putExtra("AD_User_ID", m_Contact.get(0).getKey());
			bChat.putExtra("UserName", m_Contact.get(0).getName());
			ctx.startActivity(bChat);
		} else if(m_Contact.size() > 1){
			
			String m_UserArray[] = new String[m_Contact.size()];
			//	Load Items for views
			for(int i = 0; i < m_Contact.size(); i++) {
				KeyNamePair pair = m_Contact.get(i);
				m_UserArray[i] = pair.getName();
			}
			//	
			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
			builder.setTitle(ctx.getString(R.string.PickContact));
			builder.setItems(m_UserArray, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			    	Intent bChat = new Intent(ctx, V_BChat.class);
					bChat.putExtra("AD_User_ID", m_Contact.get(item).getKey());
					bChat.putExtra("UserName", m_Contact.get(item).getName());
					ctx.startActivity(bChat);
			    }
			});
			//	Create Dialog
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			Msg.toastMsg(ctx, "@No@ @InfoBPartner@");
		}
	}
	
	/**
	 * Get EMails
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param item
	 * @return
	 * @return ArrayList<StringNamePair>
	 */
	private ArrayList<StringNamePair> getEMail(Context ctx, DisplaySearchItem item) {
		ArrayList<StringNamePair> m_EMail = new ArrayList<StringNamePair>();
		//	
		try {
			DB conn = new DB(ctx);
			DB.loadConnection(conn, DB.READ_ONLY);
			StringBuffer sql = new StringBuffer("SELECT u.Name UserName, u.EMail "
					+ "FROM AD_User u "
					+ "WHERE u.C_BPartner_ID = ? AND u.EMail IS NOT NULL ");
			//	Compile
			conn.compileQuery(sql.toString());
			//	Add Parameter
			conn.addInt(item.getRecord_ID());
			//	Get Values
			Cursor rs = conn.querySQL();
			//	
			if(rs.moveToFirst()) {
				do {
					m_EMail.add(new StringNamePair(rs.getString(0), rs.getString(1)));
				} while(rs.moveToNext());
			}
			//	Close Connection
			DB.closeConnection(conn);
		} catch (Exception e) {
			LogM.log(ctx, getClass(), Level.SEVERE, e.getLocalizedMessage());
			Msg.toastMsg(ctx, e.getLocalizedMessage());
		}
		//	Return EMails
		return m_EMail;
	}
	
	/**
	 * Send Mail
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param ctx
	 * @param item
	 * @return void
	 */
	private void sendMailBPartner(final Context ctx, DisplaySearchItem item) {
		ArrayList<StringNamePair> m_EMail = getEMail(ctx, item);
		//	Do It
		if(m_EMail.size() == 1) {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + m_EMail.get(0).getName()));
			ctx.startActivity(callIntent);
		} else if(m_EMail.size() > 1){
			final String m_EMailArray[] = new String[m_EMail.size()];
			//	Load Items for views
			for(int i = 0; i < m_EMail.size(); i++) {
				StringNamePair pair = m_EMail.get(i);
				m_EMailArray[i] = pair.getKey() + ": " + pair.getName();
			}
			//	
			
			final ArrayList<String> m_SelectedItems = new ArrayList<String>();
		    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		    // Set the dialog title
		    builder.setTitle(R.string.PickMails)
		    	.setMultiChoiceItems(m_EMailArray, null,
		                      new DialogInterface.OnMultiChoiceClickListener() {
		               @Override
		               public void onClick(DialogInterface dialog, int which,
		                       boolean isChecked) {
		                   if (isChecked) {
		                       m_SelectedItems.add(m_EMailArray[which]);
		                   } else if (m_SelectedItems.contains(m_EMailArray[which])) {
		                       m_SelectedItems.remove(m_EMailArray[which]);
		                   }
		               }
		           })
		           .setPositiveButton(R.string.Action_OK, new DialogInterface.OnClickListener() {
		               @Override
		               public void onClick(DialogInterface dialog, int id) {
		                   //	
		            	   if(m_SelectedItems.size() > 0) {
		            		   Intent sendMail = new Intent();
		           				sendMail.setAction(Intent.ACTION_SEND);
			           			sendMail.setData(Uri.parse("mailto:"));
			           			sendMail.setType("message/rfc822");
			           			String m_EMail[] = new String[m_SelectedItems.size()];
			           			m_SelectedItems.toArray(m_EMail);
			           			sendMail.putExtra(Intent.EXTRA_EMAIL, m_EMail);
			           			sendMail.putExtra(Intent.EXTRA_SUBJECT, ctx.getResources().getText(R.string.msg_SharedFromSFAndroid));
			           			//	
			           			ctx.startActivity(Intent.createChooser(sendMail, 
			           					ctx.getResources().getText(R.string.Action_Send_Mail)));
		            	   }
		               }
		           })
		           .setNegativeButton(R.string.Action_Cancel, new DialogInterface.OnClickListener() {
		               @Override
		               public void onClick(DialogInterface dialog, int id) {
		                   //	
		               }
		           });
		    //	Create Dialog
		    builder.create();
			builder.show();
		} else {
			Msg.toastMsg(ctx, "@No@ @InfoBPartner@");
		}
	}
}