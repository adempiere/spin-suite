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
 * Copyright (C) 2012-2012 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class HandlerFileShare  {
	
	/**
	 * Create a E-Mail with Recipients, Subject and Body
	 * @author Yamel Senih 05/12/2012, 00:16:47
	 * @param recipients
	 * @param subject
	 * @param body
	 * @return
	 * @return Intent
	 */
	private static Intent createEmail(String[] recipients, String subject, String body){
		Intent email = new Intent(android.content.Intent.ACTION_SEND);
		email.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
		email.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		email.putExtra(android.content.Intent.EXTRA_TEXT, body);
		return email;
	}
	
	/**
	 * Send Mail Plain Text with Attachment
	 * @author Yamel Senih 05/12/2012, 00:33:06
	 * @param recipients
	 * @param subject
	 * @param body
	 * @param path
	 * @param title
	 * @return void
	 */
	public static void sendPlainMail(Context ctx, String[] recipients, String subject, String body, String path,String title){
		File file = null;
		if(path != null && path.length() > 0)
			file = new File(path);
		sendMail(ctx, recipients, subject, body, file, title, "text/plain");
	}
	
	/**
	 * Send Mail Plain Text
	 * @author Yamel Senih 05/12/2012, 00:33:59
	 * @param recipients
	 * @param subject
	 * @param body
	 * @param title
	 * @return void
	 */
	public static void sendPlainMail(Context ctx, String[] recipients, String subject, String body, String title){
		sendMail(ctx, recipients, subject, body, null, title, "text/plain");
	}
	
	/**
	 * Send Mail
	 * @author Yamel Senih 05/12/2012, 00:35:47
	 * @param recipients
	 * @param subject
	 * @param body
	 * @param file
	 * @param title
	 * @param type
	 * @return void
	 */
	public static void sendMail(Context ctx, String[] recipients, String subject, String body, File file, String title, String type){
		Intent email = createEmail(recipients, subject, body);
		if(file != null && file.exists())
			email.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		email.setType(type);
		ctx.startActivity(Intent.createChooser(email, title));
	}
	
	/**
	 * Send Plain Text Mail
	 * @author Yamel Senih 05/12/2012, 00:37:40
	 * @param recipients
	 * @param subject
	 * @param body
	 * @param path
	 * @param title
	 * @return void
	 */
	public static void sendHtmlMail(Context ctx, String[] recipients, String subject, String body, String path,String title){
		File file = null;
		if(path != null && path.length() > 0)
			file = new File(path);
		sendMail(ctx, recipients, subject, body, file, title, "text/html");
	}
	
	/**
	 * Send HTML Mail
	 * @author Yamel Senih 05/12/2012, 00:37:30
	 * @param recipients
	 * @param subject
	 * @param body
	 * @param title
	 * @return void
	 */
	public static void sendHtmlMail(Context ctx, String[] recipients, String subject, String body, String title){
		sendMail(ctx, recipients, subject, body, null, title, "text/html");
	}
}
