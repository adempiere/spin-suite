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
 * Copyright (C) 2012-2012 E.R.P. Consultores y Asociados, S.A. All Rights Reserved. *
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.util;

import org.spinsuite.base.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * @author Yamel Senih
 *
 */
public class Msg {

	/**
	 * Show Alert message
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:02:54
	 * @param ctx
	 * @param title
	 * @param msg
	 * @return void
	 */
	public static void alertMsg(Context ctx, String title, String msg){
		Builder dialog = new AlertDialog.Builder(ctx);
		dialog.setPositiveButton(ctx.getResources().getString(R.string.msg_Acept), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		dialog.setTitle(title);
		dialog.setMessage(msg);
		dialog.show();
	}
	
	/**
	 * Show a dialog with error message, must fill field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:02:20
	 * @param ctx
	 * @param label
	 * @param field
	 * @return void
	 */
	public static void alertMustFillField(Context ctx, String label, View field){
		Msg.alertMsg(ctx, ctx.getResources().getString(R.string.msg_ValidError), 
				ctx.getResources().getString(R.string.MustFillField) + 
				" " + label);
		if(field != null)
			field.requestFocus();
	}
	
	/**
	 * Show a dialog with error message, must fll field
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:08:24
	 * @param ctx
	 * @param field_ID
	 * @param field
	 * @return void
	 */
	public static void alertMustFillField(Context ctx, int field_ID, View field){
		Msg.alertMsg(ctx, ctx.getResources().getString(R.string.msg_ValidError), 
				ctx.getResources().getString(R.string.MustFillField) + 
				" " + ctx.getResources().getString(field_ID));
		if(field != null)
			field.requestFocus();
	}
	
	/**
	 * Show a confirm dialog
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:03:08
	 * @param ctx
	 * @param msg
	 * @return
	 * @return Builder
	 */
	public static Builder confirmMsg(Context ctx, String msg) {
		return confirmMsg(ctx, ctx.getResources().getString(R.string.msg_Ask), msg);
	}
	
	/**
	 * Show confirm dialog with title
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:05:01
	 * @param ctx
	 * @param title
	 * @param msg
	 * @return
	 * @return Builder
	 */
	public static Builder confirmMsg(Context ctx, String title, String msg){
		Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(title);
		builder.setMessage(msg);
		//	
		builder.setNegativeButton(ctx.getResources().getString(R.string.msg_Cancel), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		//	Return
		return builder;
	}
	
	/**
	 * Show toast message
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:05:41
	 * @param ctx
	 * @param msg
	 * @return void
	 */
	public static void toastMsg(Context ctx, String msg){
		Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
		toast.show();
	}

	/**************************************************************************
	 *	Get translated text for AD_Message
	 *  @param  ad_language - Language
	 *  @param	AD_Message - Message Key
	 *  @return translated text
	 */
	/*public static String getMsg (Context ctx, String ad_language, String AD_Message)
	{
		if (AD_Message == null || AD_Message.length() == 0)
			return "";
		//
		String AD_Language = ad_language;
		if (AD_Language == null || AD_Language.length() == 0)
			AD_Language = Env.BASE_LANGUAGE;
		//	Get Message
		String retStr = null;//get().lookup (AD_Language, AD_Message);
		//
		if (retStr == null || retStr.length() == 0)
		{
			LogM.log(ctx, "Msg", Level.WARNING, "NOT found: " + AD_Message);
			return AD_Message;
		}

		return retStr;
	}	//	getMsg*/

	/**
	 *  Get translated text message for AD_Message
	 *  @param  ctx Context to retrieve language
	 *  @param	AD_Message - Message Key
	 *  @return translated text
	 */
	/*public static String getMsg (Context ctx, String AD_Message)
	{
		return getMsg (ctx, Env.getAD_Language(ctx), AD_Message);
	}   //  getMeg*/
	
	/**
	 *	Get clear text for AD_Message with parameters
	 *  @param  ctx Context to retrieve language
	 *  @param AD_Message   Message key
	 *  @param args         MessageFormat arguments
	 *  @return translated text
	 *  @see java.text.MessageFormat for formatting options
	 */
	/*public static String getMsg(Context ctx, String AD_Message, Object[] args)
	{
		return getMsg (ctx, Env.getAD_Language(ctx), AD_Message, args);
	}	//	getMsg*/

	/**
	 *	Get clear text for AD_Message with parameters
	 *  @param ad_language  Language
	 *  @param AD_Message   Message key
	 *  @param args         MessageFormat arguments
	 *  @return translated text
	 *  @see java.text.MessageFormat for formatting options
	 */
	/*public static String getMsg (Context ctx, String ad_language, String AD_Message, Object[] args)
	{
		String msg = getMsg(ctx, ad_language, AD_Message);
		String retStr = msg;
		try
		{
			retStr = MessageFormat.format(msg, args);	//	format string
		}
		catch (Exception e)
		{
			LogM.log(ctx, "Msg", Level.SEVERE, "NOT found: " + AD_Message);
		}
		return retStr;
	}	//	getMsg*/


	/**************************************************************************
	 * 	Get Amount in Words
	 * 	@param language language
	 * 	@param amount numeric amount (352.80)
	 * 	@return amount in words (three*five*two 80/100)
	 */
	public static String getAmtInWords (String language, String amount) {
		if (amount == null || language == null)
			return amount;
		//	Try to find Class
		String className = "org.compiere.util.AmtInWords_";
		try {
			className += language.substring(3).toUpperCase();
			Class<?> clazz = Class.forName(className);
			AmtInWords aiw = (AmtInWords)clazz.newInstance();
			return aiw.getAmtInWords(amount);
		} catch (ClassNotFoundException e) {
			Log.d("Msg", "Class not found: " + className);
		} catch (Exception e) {
			Log.e("Msg", "Class not found: " + className, e);
		}
		
		//	Fallback
		StringBuffer sb = new StringBuffer();
		int pos = amount.lastIndexOf('.');
		int pos2 = amount.lastIndexOf(',');
		if (pos2 > pos)
			pos = pos2;
		for (int i = 0; i < amount.length(); i++)
		{
			if (pos == i)	//	we are done
			{
				String cents = amount.substring(i+1);
				sb.append(' ').append(cents).append("/100");
				break;
			}
			else
			{
				char c = amount.charAt(i);
				if (c == ',' || c == '.')	//	skip thousand separator
					continue;
				if (sb.length() > 0)
					sb.append("*");
				sb.append(String.valueOf(c));
			}
		}
		return sb.toString();
	}	//	getAmtInWords


	/**************************************************************************
	 *  Get Translation for Element
	 *  @param ad_language language
	 *  @param ColumnName column name
	 *  @param isSOTrx if false PO terminology is used (if exists)
	 *  @return Name of the Column or "" if not found
	 */
	/*public static String getElement (Context ctx, String ad_language, String ColumnName, boolean isSOTrx)
	{
		if (ColumnName == null || ColumnName.equals(""))
			return "";
		String AD_Language = ad_language;
		if (AD_Language == null || AD_Language.length() == 0)
			AD_Language = Language.getBaseAD_Language();

		//	Check AD_Element
		String retStr = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			try
			{
				if (AD_Language == null || AD_Language.length() == 0 || Env.isBaseLanguage(AD_Language, "AD_Element"))
					pstmt = DB.prepareStatement("SELECT Name, PO_Name FROM AD_Element WHERE UPPER(ColumnName)=?", null);
				else
				{
					pstmt = DB.prepareStatement("SELECT t.Name, t.PO_Name FROM AD_Element_Trl t, AD_Element e "
						+ "WHERE t.AD_Element_ID=e.AD_Element_ID AND UPPER(e.ColumnName)=? "
						+ "AND t.AD_Language=?", null);
					pstmt.setString(2, AD_Language);
				}
			}
			catch (Exception e)
			{
				return ColumnName;
			}
			finally {
				DB.close(rs);
				rs = null;
			}
			pstmt.setString(1, ColumnName.toUpperCase());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retStr = rs.getString(1);
				if (!isSOTrx)
				{
					String temp = rs.getString(2);
					if (temp != null && temp.length() > 0)
						retStr = temp;
				}
			}
		} catch (SQLException e) {
			LogM.log(ctx, "Msg", Level.SEVERE, "getElement", e);
			return "";
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		if (retStr != null)
			return retStr.trim();
		return retStr;
	}   //  getElement*/

	/**
	 *  Get Translation for Element using Sales terminology
	 *  @param ctx context
	 *  @param ColumnName column name
	 *  @return Name of the Column or "" if not found
	 */
	/*public static String getElement (Context ctx, String ColumnName)
	{
		return getElement (Env.getAD_Language(ctx), ColumnName, true);
	}   //  getElement*/
	
	/**
	 *  Get Translation for Element
	 *  @param ctx context
	 *  @param ColumnName column name
	 *  @param isSOTrx sales transaction
	 *  @return Name of the Column or "" if not found
	 */
	/*public static String getElement (Context ctx, String ColumnName, boolean isSOTrx)
	{
		return getElement (Env.getAD_Language(ctx), ColumnName, isSOTrx);
	}   //  getElement*/


	/**************************************************************************
	 *	"Translate" text.
	 *  <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 *  </pre>
	 *  If checking AD_Element, the SO terminology is used.
	 *  @param ad_language  Language
	 *  @param isSOTrx sales order context
	 *  @param text	Text - MsgText or Element Name
	 *  @return translated text or original text if not found
	 */
	/*public static String translate(String ad_language, boolean isSOTrx, String text)
	{
		if (text == null || text.equals(""))
			return "";
		String AD_Language = ad_language;
		if (AD_Language == null || AD_Language.length() == 0)
			AD_Language = Env.BASE_LANGUAGE;

		//	Check AD_Message
		String retStr = null;//get().lookup (AD_Language, text);
		if (retStr != null)
			return retStr;

		//	Check AD_Element
		retStr = getElement(AD_Language, text, isSOTrx);
		if (!retStr.equals(""))
			return retStr.trim();

		//	Nothing found
		if (!text.startsWith("*"))
			s_log.warning("NOT found: " + text);
		return text;
	}	//	translate*/

	/***
	 *	"Translate" text (SO Context).
	 *  <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 *  </pre>
	 *  If checking AD_Element, the SO terminology is used.
	 *  @param ad_language  Language
	 *  @param text	Text - MsgText or Element Name
	 *  @return translated text or original text if not found
	 */
	/*public static String translate(String ad_language, String text)
	{
		return translate (ad_language, true, text);
	}	//	translate*/

	/**
	 *	"Translate" text.
	 *  <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 *  </pre>
	 *  @param ctx  Context
	 *  @param text	Text - MsgText or Element Name
	 *  @return translated text or original text if not found
	 */
	/*public static String translate(Context ctx, String text)
	{
		if (text == null || text.length() == 0)
			return text;
		String s = (String)ctx.getProperty(text);
		if (s != null && s.length() > 0)
			return s;
		return translate (Env.getAD_Language(ctx), Env.isSOTrx(ctx), text);
	}   //  translate*/

	/**
	 *	Translate elements enclosed in "@" (at sign)
	 *  @param ctx      Context
	 *  @param text     Text
	 *  @return translated text or original text if not found
	 */
	/*public static String parseTranslation(Context ctx, String text)
	{
		if (text == null || text.length() == 0)
			return text;

		String inStr = text;
		String token;
		StringBuffer outStr = new StringBuffer();

		int i = inStr.indexOf('@');
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf('@');						// next @
			if (j < 0)										// no second tag
			{
				inStr = "@" + inStr;
				break;
			}

			token = inStr.substring(0, j);
			outStr.append(translate(ctx, token));			// replace context

			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf('@');
		}

		outStr.append(inStr);           					//	add remainder
		return outStr.toString();
	}   //  parseTranslation*/
	
}
