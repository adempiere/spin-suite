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
 * Contributor(s): Yamel Senih www.erpcya.com                                        *
 *************************************************************************************/
package org.spinsuite.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.logging.Level;

import org.spinsuite.base.DB;
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
	private static void alertMsg(Context ctx, String title, String msg){
		Builder dialog = new AlertDialog.Builder(ctx);
		dialog.setPositiveButton(ctx.getResources().getString(R.string.msg_Acept), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		dialog.setTitle(title);
		dialog.setMessage(parseTranslation(ctx, msg));
		dialog.show();
	}
	
	/**
	 * Show Alert Message without title
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 03/09/2014, 19:12:07
	 * @param ctx
	 * @param msg
	 * @return void
	 */
	public static void alertMsg(Context ctx, String msg){
		alertMsg(ctx, null, msg);
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
		Msg.alertMsg(ctx, ctx.getResources().getString(R.string.MustFillField) + 
				" " + ctx.getResources().getString(field_ID));
		if(field != null)
			field.requestFocus();
	}
	
	/**
	 * Show confirm dialog with title
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 25/02/2014, 14:05:01
	 * @param ctx
	 * @param msg
	 * @return
	 * @return Builder
	 */
	public static Builder confirmMsg(Context ctx, String msg){
		Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(parseTranslation(ctx, msg));
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
		Toast toast = Toast.makeText(ctx, parseTranslation(ctx, msg), Toast.LENGTH_SHORT);
		toast.show();
	}

	/**
	 *  Get translated text message for AD_Message
	 *  @param  ctx Context to retrieve language
	 *  @param	AD_Message - Message Key
	 *  @return translated text
	 */
	public static String getMsg (Context ctx, String AD_Message) {
		String msg = getMsg (ctx, Env.getAD_Language(ctx), AD_Message);
		//	Base Language
		if(msg == null
				|| msg.length() == 0)
			msg = getMsg (ctx, Env.BASE_LANGUAGE, AD_Message);
		return msg;
	}   //  getMeg
	
	/**
	 *	Get clear text for AD_Message with parameters
	 *  @param  ctx Context to retrieve language
	 *  @param AD_Message   Message key
	 *  @param args         MessageFormat arguments
	 *  @return translated text
	 *  @see java.text.MessageFormat for formatting options
	 */
	public static String getMsg(Context ctx, String AD_Message, Object[] args) {
		String msg = getMsg (ctx, Env.getAD_Language(ctx), AD_Message, args);
		//	Base Language
		if(msg == null
				|| msg.length() == 0)
			msg = getMsg (ctx, Env.BASE_LANGUAGE, AD_Message, args);
		return msg;
	}	//	getMsg

	/**
	 *	Get clear text for AD_Message with parameters
	 *  @param ad_language  Language
	 *  @param AD_Message   Message key
	 *  @param args         MessageFormat arguments
	 *  @return translated text
	 *  @see java.text.MessageFormat for formatting options
	 */
	public static String getMsg (Context ctx, String ad_language, String AD_Message, Object[] args) {
		String msg = getMsg(ctx, ad_language, AD_Message);
		String retStr = msg;
		try {
			retStr = MessageFormat.format(msg, args);	//	format string
		} catch (Exception e) {
			LogM.log(ctx, "Msg", Level.SEVERE, "NOT found: " + AD_Message);
		}
		//	Return
		return retStr;
	}	//	getMsg


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
		String className = "org.spinsuite.util.AmtInWords_";
		try {
			className += language.substring(0, 2).toUpperCase(new Locale(language));
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

	
	/**
	 * Get translation from Cache
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 30/08/2014, 18:16:22
	 * @param ctx
	 * @param p_AD_Language
	 * @param p_Msg
	 * @return
	 * @return String
	 */
	public static String getTranslationCache(Context ctx, String p_AD_Language, String p_Msg) {
		//	Language
		if(p_AD_Language == null
				|| p_AD_Language.length() == 0)
			p_AD_Language = Env.BASE_LANGUAGE;
		//	
		return Env.getContext(ctx, MSG_PREFIX + "|" + p_AD_Language + "|" + p_Msg);
	}
	
	/**
	 * Set Translation to Cache
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 30/08/2014, 18:21:34
	 * @param ctx
	 * @param p_AD_Language
	 * @param p_Msg
	 * @param p_Value
	 * @return void
	 */
	private static void setTranslationCache(Context ctx, String p_AD_Language, String p_Msg, String p_Value) {
		//	Language
		if(p_AD_Language == null
				|| p_AD_Language.length() == 0)
			p_AD_Language = Env.BASE_LANGUAGE;
		//	Set Context
		Env.setContext(ctx, MSG_PREFIX + "|" + p_AD_Language + "|" + p_Msg, p_Value);
	}
	

	/**************************************************************************
	 *  Get Translation for Element
	 *  @param p_AD_Language language
	 *  @param p_ColumnName column name
	 *  @param isSOTrx if false PO terminology is used (if exists)
	 *  @return Name of the Column or "" if not found
	 */
	public static String getElement (Context ctx, String p_AD_Language, String p_ColumnName, boolean isSOTrx) {
		if (p_ColumnName == null 
				|| p_ColumnName.equals(""))
			return "";
		String m_AD_Language = p_AD_Language;
		if (m_AD_Language == null 
				|| m_AD_Language.length() == 0)
			m_AD_Language = Env.BASE_LANGUAGE;
		//	Get from Cache
		String retStr = getTranslationCache(ctx, p_AD_Language, 
				p_ColumnName + (isSOTrx? "|Y|": "|N|"));
		//	Valid Cache
		if(retStr != null) {
			LogM.log(ctx, "Msg", Level.FINE, "From Cache[Element="  + retStr + "]");
			return retStr;
		}
		//	Check AD_Element
		String sql = null;
		String [] params = null;
		String columnName = (isSOTrx? "Name": "PO_Name");
		//	Get from Language
		if (m_AD_Language == null 
				|| m_AD_Language.length() == 0 
				|| Env.BASE_LANGUAGE.equals(m_AD_Language)) {
			sql = "SELECT " + columnName + " FROM AD_Element WHERE ColumnName = ?";
			params = new String[] {p_ColumnName};
		} else {
			sql = "SELECT t." + columnName + " " 
				+ "FROM AD_Element e "
				+ "INNER JOIN AD_Element_Trl t ON(t.AD_Element_ID = e.AD_Element_ID) "
				+ "WHERE e.ColumnName = ? "
				+ "AND t.AD_Language = ?";
			params = new String[] {p_ColumnName, m_AD_Language};
		}
		//	Log
		LogM.log(ctx, "Msg", Level.FINE, "SQL getElement = [" + sql + "] " 
					+ "ColumnName=" + p_ColumnName + ", AD_Language=" + m_AD_Language);
		//	
		retStr = DB.getSQLValueString(ctx, sql, params);
		//	Log
		LogM.log(ctx, "Msg", Level.FINE, "From DB[Element="  + retStr + "]");
		//	Set to Cache
		setTranslationCache(ctx, p_AD_Language, 
				p_ColumnName + (isSOTrx? "|Y|": "|N|"), retStr);
		//	Return Translation
		if (retStr != null)
			return retStr.trim();
		//	Return
		return retStr;
	}   //  getElement

	
	/**
	 * Get Message
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 30/08/2014, 19:02:12
	 * @param ctx
	 * @param p_AD_Language
	 * @param p_MessageName
	 * @return
	 * @return String
	 */
	public static String getMsg(Context ctx, String p_AD_Language, String p_MessageName) {
		if (p_MessageName == null 
				|| p_MessageName.equals(""))
			return "";
		String m_AD_Language = p_AD_Language;
		if (m_AD_Language == null 
				|| m_AD_Language.length() == 0)
			m_AD_Language = Env.BASE_LANGUAGE;
		//	Get from Cache
		String retStr = getTranslationCache(ctx, p_AD_Language, p_MessageName);
		//	Valid Cache
		if(retStr != null) {
			LogM.log(ctx, "Msg", Level.FINE, "From Cache[Message="  + retStr + "]");
			return retStr;
		}
		//	Check AD_Element
		String sql = null;
		String [] params = null;
		//	Get from Language
		if (m_AD_Language == null 
				|| m_AD_Language.length() == 0 
				|| Env.BASE_LANGUAGE.equals(m_AD_Language)) {
			sql = "SELECT MsgText FROM AD_Message WHERE Value = ?";
			params = new String[] {p_MessageName};
		} else {
			sql = "SELECT t.MsgText " 
				+ "FROM AD_Message m "
				+ "INNER JOIN AD_Message_Trl t ON(t.AD_Message_ID = m.AD_Message_ID) "
				+ "WHERE m.Value = ? "
				+ "AND t.AD_Language = ?";
			params = new String[] {p_MessageName, m_AD_Language};
		}
		//	
		retStr = DB.getSQLValueString(ctx, sql, params);
		//	Log
		LogM.log(ctx, "Msg", Level.FINE, "From DB[Message="  + retStr + "]");
		//	Set to Cache
		setTranslationCache(ctx, p_AD_Language, p_MessageName, retStr);
		//	Return Translation
		if (retStr != null)
			return retStr.trim();
		//	Return
		return retStr;
	}   //  getElement
	
	
	/**
	 *  Get Translation for Element using Sales terminology
	 *  @param ctx context
	 *  @param ColumnName column name
	 *  @return Name of the Column or "" if not found
	 */
	public static String getElement (Context ctx, String ColumnName) {
		String language = Env.getAD_Language(ctx);
		String msg = getElement (ctx, language, ColumnName, true);
		if((msg == null
				|| msg.length() == 0)
				&& !language.equals(Env.BASE_LANGUAGE))
			msg = getElement (ctx, Env.BASE_LANGUAGE, ColumnName, true);
		return msg;
	}   //  getElement
	
	/**
	 *  Get Translation for Element
	 *  @param ctx context
	 *  @param ColumnName column name
	 *  @param isSOTrx sales transaction
	 *  @return Name of the Column or "" if not found
	 */
	public static String getElement (Context ctx, String ColumnName, boolean isSOTrx) {
		String language = Env.getAD_Language(ctx);
		String msg = getElement (ctx, Env.getAD_Language(ctx), ColumnName, isSOTrx);
		if((msg == null
				|| msg.length() == 0)
				&& !language.equals(Env.BASE_LANGUAGE))
			msg = getElement (ctx, Env.BASE_LANGUAGE, ColumnName, isSOTrx);
		return msg;
	}   //  getElement


	/**************************************************************************
	 *	"Translate" text.
	 *  <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 *  </pre>
	 *  If checking AD_Element, the SO terminology is used.
	 *  @param p_AD_Language  Language
	 *  @param isSOTrx sales order context
	 *  @param text	Text - MsgText or Element Name
	 *  @return translated text or original text if not found
	 */
	public static String translate(Context ctx, String p_AD_Language, boolean isSOTrx, String text) {
		if (text == null || text.equals(""))
			return "";
		String m_AD_Language = p_AD_Language;
		if (m_AD_Language == null || m_AD_Language.length() == 0)
			m_AD_Language = Env.BASE_LANGUAGE;

		//	Check AD_Message
		String retStr = getMsg(ctx, m_AD_Language, text);
		if (retStr != null
				&& retStr.length() != 0)
			return retStr.trim();

		//	Check AD_Element
		retStr = getElement(ctx, m_AD_Language, text, isSOTrx);
		if (retStr != null
				&& retStr.length() != 0)
			return retStr.trim();
		//	Re-Query
		retStr = getElement(ctx, m_AD_Language, text, !isSOTrx);
		if (retStr != null
				&& retStr.length() != 0)
			return retStr.trim();
		//	Set default cache
		setTranslationCache(ctx, p_AD_Language, 
				text + (isSOTrx? "|Y|": "|N|"), text);
		//	Nothing found
		if (!text.startsWith("*"))
			LogM.log(ctx, "Msg", Level.WARNING, "NOT found: " + text);
		return text;
	}	//	translate

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
	public static String translate(Context ctx, String ad_language, String text) {
		String msg = translate (ctx, ad_language, true, text);
		if((msg == null
				|| msg.length() == 0)
				&& !ad_language.equals(Env.BASE_LANGUAGE))
			msg = translate (ctx, Env.BASE_LANGUAGE, true, text);
		//	
		return msg;
	}	//	translate

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
	public static String translate(Context ctx, String text) {
		if (text == null || text.length() == 0)
			return text;
		return translate(ctx, Env.getAD_Language(ctx), Env.isSOTrx(ctx), text);
	}   //  translate*/

	/**
	 *	Translate elements enclosed in "@" (at sign)
	 *  @param ctx      Context
	 *  @param text     Text
	 *  @return translated text or original text if not found
	 */
	public static String parseTranslation(Context ctx, String text) {
		if (text == null || text.length() == 0)
			return text;

		String inStr = text;
		String token;
		StringBuffer outStr = new StringBuffer();

		int i = inStr.indexOf('@');
		while (i != -1) {
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf('@');						// next @
			if (j < 0) {									// no second tag
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
	}   //  parseTranslation
	
	/**	Prefix Message				*/
	private static final String		MSG_PREFIX = "MSG";
	
}
