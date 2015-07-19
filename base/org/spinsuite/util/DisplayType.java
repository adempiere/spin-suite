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

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;

import org.spinsuite.view.lookup.InfoField;

import android.content.Context;
import android.text.InputType;

/**
 *	System Display Types.
 *  <pre>
 *	SELECT AD_Reference_ID, Name FROM AD_Reference WHERE ValidationType = 'D'
 *  </pre>
 *  @author     Jorg Janke
 *  @version    $Id: DisplayType.java,v 1.6 2006/08/30 20:30:44 comdivision Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 				<li>BF [ 1810632 ] PricePrecision error in InfoProduct (and similar)
 */
public final class DisplayType
{
	/** Display Type 10	String	*/
	public static final int STRING     	= 10;
	/** Display Type 11	Integer	*/
	public static final int INTEGER    	= 11;
	/** Display Type 12	Amount	*/
	public static final int AMOUNT     	= 12;
	/** Display Type 13	ID	*/
	public static final int ID         	= 13;
	/** Display Type 14	Text	*/
	public static final int TEXT       	= 14;
	/** Display Type 15	Date	*/
	public static final int DATE       	= 15;
	/** Display Type 16	DateTime	*/
	public static final int DATE_TIME   = 16;
	/** Display Type 17	List	*/
	public static final int LIST       	= 17;
	/** Display Type 18	Table	*/
	public static final int TABLE      	= 18;
	/** Display Type 19	TableDir	*/
	public static final int TABLE_DIR   = 19;
	/** Display Type 20	YN	*/
	public static final int YES_NO      = 20;
	/** Display Type 21	Location	*/
	public static final int LOCATION   	= 21;
	/** Display Type 22	Number	*/
	public static final int NUMBER     	= 22;
	/** Display Type 23	BLOB	*/
	public static final int BINARY     	= 23;
	/** Display Type 24	Time	*/
	public static final int TIME       	= 24;
	/** Display Type 25	Account	*/
	public static final int ACCOUNT    	= 25;
	/** Display Type 26	RowID	*/
	public static final int ROW_ID      = 26;
	/** Display Type 27	Color   */
	public static final int COLOR      	= 27;
	/** Display Type 28	Button	*/
	public static final int BUTTON	   	= 28;
	/** Display Type 29	Quantity	*/
	public static final int QUANTITY   	= 29;
	/** Display Type 30	Search	*/
	public static final int SEARCH     	= 30;
	/** Display Type 31	Locator	*/
	public static final int LOCATOR    	= 31;
	/** Display Type 32 Image	*/
	public static final int IMAGE      	= 32;
	/** Display Type 33 Assignment	*/
	public static final int ASSIGNMENT 	= 33;
	/** Display Type 34	Memo	*/
	public static final int MEMO       	= 34;
	/** Display Type 35	PAttribute	*/
	public static final int PATTRIBUTE 	= 35;
	/** Display Type 36	CLOB	*/
	public static final int TEXT_LONG   = 36;
	/** Display Type 37	CostPrice	*/
	public static final int COST_PRICE  = 37;
	/** Display Type 38	File Path	*/
	public static final int FILE_PATH  	= 38;
	/** Display Type 39 File Name	*/
	public static final int FILE_NAME	= 39;
	/** Display Type 40	URL	*/
	public static final int URL			= 40;
	/** Display Type 42	PrinterName	*/
	public static final int PRINTER_NAME= 42;
	//	Candidates: 
	
	/**
	 *	- New Display Type
		INSERT INTO AD_REFERENCE
		(AD_REFERENCE_ID, AD_CLIENT_ID,AD_ORG_ID,ISACTIVE,CREATED,CREATEDBY,UPDATED,UPDATEDBY,
		NAME,DESCRIPTION,HELP, VALIDATIONTYPE,VFORMAT,ENTITYTYPE)
		VALUES (35, 0,0,'Y',SysDate,0,SysDate,0,
		'PAttribute','Product Attribute',null,'D',null,'D');
	 *
	 *  - org.compiere.model.MModel (??)
	 *	- org.compiere.grid.ed.VEditor/Dialog
	 *	- org.compiere.grid.ed.VEditorFactory
	 *	- RColumn, WWindow
	 *  add/check 0_cleanupAD.sql
	 */

	//  See DBA_DisplayType.sql ----------------------------------------------

	/** Maximum number of digits    */
	private static final int    MAX_DIGITS 			= 28;        //  Oracle Standard Limitation 38 digits
	/** Digits of an Integer        */
	private static final int    INTEGER_DIGITS 		= 10;
	/** Maximum number of fractions */
	private static final int    MAX_FRACTION 		= 12;
	/** Default Amount Precision    */
	private static final int    AMOUNT_FRACTION 	= 2;

	/**
	 *	Returns true if (numeric) ID (Table, Search, Account, ..).
	 *  (stored as Integer)
	 *  @param displayType Display Type
	 *  @return true if ID
	 */
	public static boolean isID (int displayType)
	{
		if (displayType == ID || displayType == TABLE || displayType == TABLE_DIR
			|| displayType == SEARCH || displayType == LOCATION || displayType == LOCATOR
			|| displayType == ACCOUNT || displayType == ASSIGNMENT || displayType == PATTRIBUTE
			|| displayType == IMAGE || displayType == COLOR)
			return true;
		return false;
	}	//	isID
	
	/**
	 * Verify if is a Boolean
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 07/04/2014, 15:46:00
	 * @param displayType
	 * @return
	 * @return boolean
	 */
	public static boolean isBoolean(int displayType) {
		if(displayType == YES_NO)
			return true;
		return false;
	}

	/**
	 *	Returns true, if DisplayType is numeric (Amount, Number, Quantity, Integer).
	 *  (stored as BigDecimal)
	 *  @param displayType Display Type
	 *  @return true if numeric
	 */
	public static boolean isNumeric(int displayType)
	{
		if (displayType == AMOUNT || displayType == NUMBER || displayType == COST_PRICE 
			|| displayType == INTEGER || displayType == QUANTITY)
			return true;
		return false;
	}	//	isNumeric
	
	/**
	 * Is a BigDecimal
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 16/02/2014, 13:03:25
	 * @param displayType
	 * @return
	 * @return boolean
	 */
	public static boolean isBigDecimal(int displayType)
	{
		if (displayType == AMOUNT || displayType == NUMBER || displayType == COST_PRICE 
			|| displayType == QUANTITY)
			return true;
		return false;
	}	//	isBigDecimal
	
	/**
	 * 	Get Default Precision.
	 * 	Used for databases who cannot handle dynamic number precision.
	 *	@param displayType display type
	 *	@return scale (decimal precision)
	 */
	public static int getDefaultPrecision(int displayType)
	{
		if (displayType == AMOUNT)
			return 2;
		if (displayType == NUMBER)
			return 6;
		if (displayType == COST_PRICE 
			|| displayType == QUANTITY)
			return 4;
		return 0;
	}	//	getDefaultPrecision
	

	/**
	 *	Returns true, if DisplayType is text (String, Text, TextLong, Memo).
	 *  @param displayType Display Type
	 *  @return true if text
	 */
	public static boolean isText(int displayType)
	{
		if (displayType == STRING || displayType == TEXT 
			|| displayType == TEXT_LONG || displayType == MEMO
			|| displayType == FILE_PATH || displayType == FILE_NAME
			|| displayType == URL || displayType == PRINTER_NAME)
			return true;
		return false;
	}	//	isText

	/**
	 *	Returns true if DisplayType is a Date.
	 *  (stored as Timestamp)
	 *  @param displayType Display Type
	 *  @return true if date
	 */
	public static boolean isDate (int displayType)
	{
		if (displayType == DATE || displayType == DATE_TIME || displayType == TIME)
			return true;
		return false;
	}	//	isDate

	/**
	 *	Returns true if DisplayType is a VLookup (List, Table, TableDir, Search).
	 *  (stored as Integer)
	 *  @param displayType Display Type
	 *  @return true if Lookup
	 */
	public static boolean isLookup(int displayType)
	{
		if (displayType == LIST || displayType == TABLE
			|| displayType == TABLE_DIR || displayType == SEARCH
			//	Added
			|| displayType == LOCATION || displayType == LOCATOR)
			return true;
		return false;
	}	//	isLookup
	
	/**
	 * 	Returns true if DisplayType is a Large Object
	 *	@param displayType Display Type
	 *	@return true if LOB
	 */
	public static boolean isLOB (int displayType)
	{
		if (displayType == BINARY 
			|| displayType == TEXT_LONG)
			return true;
		return false;
	}	//	isLOB

	/**************************************************************************
	 *	Return Format for numeric DisplayType
	 *	@param context
	 *  @param displayType Display Type (default Number)
	 *  @param pattern Java Number Format pattern e.g. "#,##0.00"
	 *  @return number format
	 */
	public static DecimalFormat getNumberFormat(Context ctx, int displayType, String pattern) {
		String language = Env.BASE_LANGUAGE;
		if(ctx != null)
			language = Env.getAD_Language();
		//	
		Locale locale = Language.getLocale(language);
		DecimalFormat format = null;
		format = (DecimalFormat)NumberFormat.getNumberInstance(locale);
		//
		if (pattern != null && pattern.length() > 0) {
			try {
			format.applyPattern(pattern);
			return format;
			}
			catch (IllegalArgumentException e) {}
		} else if (displayType == INTEGER) {
			format.setParseIntegerOnly(true);
			format.setMaximumIntegerDigits(INTEGER_DIGITS);
			format.setMaximumFractionDigits(0);
		} else if (displayType == QUANTITY) {
			format.setMaximumIntegerDigits(MAX_DIGITS);
			format.setMaximumFractionDigits(MAX_FRACTION);
		} else if (displayType == AMOUNT) {
			format.setMaximumIntegerDigits(MAX_DIGITS);
			format.setMaximumFractionDigits(MAX_FRACTION);
			format.setMinimumFractionDigits(AMOUNT_FRACTION);
		} else if (displayType == COST_PRICE) {
			format.setMaximumIntegerDigits(MAX_DIGITS);
			format.setMaximumFractionDigits(MAX_FRACTION);
			format.setMinimumFractionDigits(AMOUNT_FRACTION);
		} else {
			format.setMaximumIntegerDigits(MAX_DIGITS);
			format.setMaximumFractionDigits(MAX_FRACTION);
			format.setMinimumFractionDigits(1);
		}
		return format;
	}	//	getDecimalFormat
	
	/**
	 *	Return Format for numeric DisplayType
	 *	@param context
	 *  @param displayType Display Type
	 *  @return number format
	 */
	public static DecimalFormat getNumberFormat(Context ctx, int displayType) {
		return getNumberFormat (ctx, displayType, null);
	}   //  getNumberFormat


	/*************************************************************************
	 *	Return Date Format
	 *  @return date format
	 */
	public static SimpleDateFormat getDateFormat(Context ctx) {
		return getDateFormat (ctx, DisplayType.DATE, null);
	}   //  getDateFormat

	/**
	 *	Return Date Format
	 *  @param language Language
	 *  @return date format
	 */
	public static SimpleDateFormat getDateFormat (Context ctx, String language) {
		return getDateFormat (ctx, DisplayType.DATE, language);
	}	//	getDateFormat

	/**
	 *	Return format for date displayType
	 *  @param displayType Display Type
	 *  @return date format
	 */
	public static SimpleDateFormat getDateFormat (Context ctx, int displayType) {
		return getDateFormat(ctx, displayType, null);
	}   //  getDateFormat
	
	/**
	 *	Return format for date displayType
	 *  @param displayType Display Type (default Date)
	 *  @param pattern Java Simple Date Format pattern e.g. "dd/MM/yy"
	 *  @return date format
	 */
	public static SimpleDateFormat getDateFormat (Context ctx, int displayType, String pattern) {
		//
		if ( pattern != null && pattern.length() > 0) {
			SimpleDateFormat format = (SimpleDateFormat)DateFormat.getInstance();
			try {
				format.applyPattern(pattern);
				return format;
			} catch (IllegalArgumentException e) {
				LogM.log(ctx, "DisplayType", Level.FINE, "Invalid date pattern: " + pattern);
			}
		}
		
		if (displayType == DATE_TIME)
			return Env.getDateTimeFormat();
		else if (displayType == TIME)
			return Env.getTimeFormat();
		//	
		return Env.getDateFormat();		//	default
	}	//	getDateFormat

	/**
	 *	JDBC Date Format YYYY-MM-DD
	 *  @return date format
	 */
	public static SimpleDateFormat getDateFormat_JDBC() {
		return new SimpleDateFormat ("yyyy-MM-dd");
	}   //  getDateFormat_JDBC

	/**
	 *	JDBC Timestamp Format yyyy-mm-dd hh:mm:ss
	 *  @return timestamp format
	 */
	public static SimpleDateFormat getTimestampFormat_Default() {
		return new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	}   //  getTimestampFormat_JDBC

	/**
	 *  Return Storage Class.
	 *  (used for MiniTable)
	 *  @param displayType Display Type
	 *  @param yesNoAsBoolean - yes or no as boolean
	 *  @return class Integer - BigDecimal - Timestamp - String - Boolean
	 */
	public static Class<?> getClass (int displayType, boolean yesNoAsBoolean) {
		if (isText(displayType) || displayType == LIST)
			return String.class;
		else if (isID(displayType) || displayType == INTEGER)    //  note that Integer is stored as BD
			return Integer.class;
		else if (isNumeric(displayType))
			return java.math.BigDecimal.class;
		else if (isDate(displayType))
			return java.sql.Timestamp.class;
		else if (displayType == YES_NO)
			return yesNoAsBoolean ? Boolean.class : String.class;
		else if (displayType == BUTTON)
			return String.class;
		else if (isLOB(displayType))	//	CLOB is String
			return byte[].class;
		//
		return Object.class;
	}   //  getClass

	/**
	 * Get value from Display Type
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 25/02/2014, 17:16:43
	 * @param displayType
	 * @param value
	 * @param yesNoAsBoolean
	 * @param dateAsDate
	 * @return
	 * @return Object
	 */
	public static Object getJDBC_Value (int displayType, Object value, boolean yesNoAsBoolean, boolean dateAsDate,String columnName) {
		if(value == null)
			return null;
		
		//	Else
		if (isText(displayType) 
				|| displayType == LIST
				|| displayType == BUTTON || (columnName!=null && columnName.equals("AD_Language"))
				//2015-05-18 Dixon Martinez Add Support to Column Entity Type  
				|| (columnName!=null && columnName.equals("EntityType"))
				//End Dixon Martinez
				) {
			if(String.valueOf(value).length() > 0)
				return String.valueOf(value);
			return null;
		} else if (isID(displayType) || displayType == INTEGER) {    //  note that Integer is stored as BD
			if((Integer)value != -1)
				return value;
			return null;
		} else if (isNumeric(displayType)) {
			return (BigDecimal) value;
		} else if (isDate(displayType)) {
			//	Only Date
			if(dateAsDate)
				return value;
			//	Format
			SimpleDateFormat format = getTimestampFormat_Default();
			//	Convert from Date to Standard Format
			Date valueDate = getDateToFormat((Date) value, format);
			//	Parse
			String valueString = format.format(valueDate);
			//	Return
			return valueString;
		} else if (displayType == YES_NO) {
			boolean yesNo = false;
			//	
			if(value instanceof String)
				yesNo = ((String) value).equals("Y");
			else if(value instanceof Boolean)
				yesNo = ((Boolean) value);
			//	Yes No As Boolean
			if(!yesNoAsBoolean) {
				return (yesNo? "Y": "N");
			} else {
				return yesNo;
			}
		} else if (isLOB(displayType))	//	CLOB is String
			return byte[].class;
		//
		return value;
	}   //  getClass
	
	
	/**
	 * Get OffSet from Time to Format
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Date
	 * @param p_To_Format
	 * @return
	 * @return Date
	 */
	public static Date getDateToFormat(Date p_Date, SimpleDateFormat p_To_Format) {
		//	Date to String
		long currentTimeLong = p_Date.getTime();
		long diffTimeLong = p_To_Format.getTimeZone().getRawOffset();
		diffTimeLong = diffTimeLong * Long.signum(diffTimeLong);
		//	Convert
		return new Date(currentTimeLong + diffTimeLong);
	}
	
	/**
	 * Get value from Display Type
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 21/03/2014, 17:28:36
	 * @param displayType
	 * @param value
	 * @return
	 * @return Object
	 */
	public static Object getJDBC_Value (int displayType, Object value) {
		return getJDBC_Value (displayType, value, false, false, null);
	}
	
	/**
	 * Get Value From Display Type
	 * @author Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com 26/3/2015, 21:37:24
	 * @param displayType
	 * @param value
	 * @param yesNoAsBoolean
	 * @param dateAsDate
	 * @return
	 * @return Object
	 */
	public static Object getJDBC_Value (int displayType, Object value, boolean yesNoAsBoolean, boolean dateAsDate){
		return getJDBC_Value (displayType, value, yesNoAsBoolean, dateAsDate, null);
	}
	
	
	/**
	 * Set Context Value from activity
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/03/2014, 10:41:11
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param field
	 * @param value
	 * @return void
	 */
	public static void setContextValue (Context ctx, int m_ActivityNo, int TabNo, InfoField field, Object value) {
		if (isText(field.DisplayType) 
				|| field.DisplayType == LIST
				|| field.DisplayType == BUTTON) {
			if(value != null
					&& String.valueOf(value).length() > 0) {
				Env.setContext(m_ActivityNo, TabNo, field.ColumnName, String.valueOf(value));
				Env.setContext(m_ActivityNo, field.ColumnName, String.valueOf(value));
			} else
				Env.setContext(m_ActivityNo, TabNo, field.ColumnName, null);
		} else if (isID(field.DisplayType) 
				|| isLookup(field.DisplayType)
				|| field.DisplayType == INTEGER) {
			if(value != null) {
				Env.setContext(m_ActivityNo, TabNo, field.ColumnName, (Integer)value);
				Env.setContext(m_ActivityNo, field.ColumnName, (Integer)value);
			} else
				Env.setContext(m_ActivityNo, TabNo, field.ColumnName, -1);
		} else if (isNumeric(field.DisplayType)) {
			if(value != null) {
				Env.setContext(m_ActivityNo, TabNo, field.ColumnName, ((BigDecimal) value).toString());
				Env.setContext(m_ActivityNo, field.ColumnName, ((BigDecimal) value).toString());
			} else
				Env.setContext(m_ActivityNo, TabNo, field.ColumnName, null);
		} else if (isDate(field.DisplayType)) {
			if(value != null) {
				//	Format
				SimpleDateFormat format;
				//	Date and Time
				format = getTimestampFormat_Default();
				String dateString = format.format((Date) value);
				Env.setContext(m_ActivityNo, TabNo, field.ColumnName, dateString);
				Env.setContext(m_ActivityNo, field.ColumnName, dateString);
			} else 
				Env.setContext(m_ActivityNo, TabNo, field.ColumnName, null);
		} else if (field.DisplayType == YES_NO) {
			Env.setContext(m_ActivityNo, TabNo, field.ColumnName, (Boolean)value);
			Env.setContext(m_ActivityNo, field.ColumnName, (Boolean)value);
		} else if (isLOB(field.DisplayType))	//	CLOB is String
			return;
	}   //  Set Context
	
	/**
	 * Get Context value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/03/2014, 11:55:33
	 * @param ctx
	 * @param m_ActivityNo
	 * @param TabNo
	 * @param field
	 * @return
	 * @return Object
	 */
	public static Object getContextValue (Context ctx, int m_ActivityNo, int TabNo, InfoField field) {
		if (isText(field.DisplayType) 
				|| field.DisplayType == LIST
				|| field.DisplayType == BUTTON) {
			return Env.getContext(m_ActivityNo, TabNo, field.ColumnName);
		} else if (isID(field.DisplayType) || field.DisplayType == INTEGER) {    //  note that Integer is stored as BD
			return Env.getContextAsInt(m_ActivityNo, TabNo, field.ColumnName);
		} else if (isNumeric(field.DisplayType)) {
			return getNumber(ctx, Env.getContext(m_ActivityNo, TabNo, field.ColumnName), field.DisplayType);
		} else if (isDate(field.DisplayType)) {
			String value = Env.getContext(m_ActivityNo, TabNo, field.ColumnName);
			if(value != null)
				return getDate(value);
				//	
			return null;
		} else if (field.DisplayType == YES_NO) {
			return Env.getContextAsBoolean(m_ActivityNo, TabNo, field.ColumnName);
		} else if (isLOB(field.DisplayType))	//	CLOB is String
			return null;
		return null;
	}   //  getContext
	
	
	/**
	 * Parse Value
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 20/05/2014, 15:47:33
	 * @param value
	 * @param displayType
	 * @return
	 * @return Object
	 */
	public static Object parseValue (Object value, int displayType, String columnName) {
		//	Valid Null
		if(value == null
				|| String.valueOf(value).length() == 0)
			return null;
		//	
		if (isText(displayType) 
				|| displayType == LIST
				|| displayType == BUTTON || (columnName!=null && columnName.equals("AD_Language"))
				//2015-05-18 Dixon Martinez Add Support to Column Entity Type  
				|| (columnName!=null && columnName.equals("EntityType"))
				//End Dixon Martinez
				) {
			return String.valueOf(value);
		} else if (isID(displayType) || displayType == INTEGER) {
			if(value instanceof Integer) {
				return (Integer) value;
			} else if(value instanceof String) {
				try {
					return Integer.parseInt((String)value);
				} catch (Exception e) {
					return 0;
				}
			} else {
				return 0;
			}
		} else if (isNumeric(displayType)) {
			return getNumber(String.valueOf(value), displayType);
		} else if (isDate(displayType)) {
			return getDate(String.valueOf(value));
		} else if (displayType == YES_NO) {
			if(value instanceof Boolean)
				return value;
			else if(value instanceof String)
				return ((String)value).equals("Y");
		} else if (isLOB(displayType))
			return null;
		return null;
	}   //  getContext
	
	/**
	 * 
	 * @author Carlos Parada, cparada@erpcya.com, ERPCyA http://www.erpcya.com , 23:58:15
	 * @param value
	 * @param displayType
	 * @return
	 * @return Object
	 */
	public static Object parseValue (Object value, int displayType){
		return parseValue (value, displayType, null);
	}
	
	/**
	 * 	Get Description
	 *	@param displayType display Type
	 *	@return display type description
	 */
	public static String getDescription (int displayType) {
		if (displayType == STRING)
			return "String";
		if (displayType == INTEGER)
			return "Integer";
		if (displayType == AMOUNT)
			return "Amount";
		if (displayType == ID)
			return "ID";
		if (displayType == TEXT)
			return "Text";
		if (displayType == DATE)
			return "Date";
		if (displayType == DATE_TIME)
			return "DateTime";
		if (displayType == LIST)
			return "List";
		if (displayType == TABLE)
			return "Table";
		if (displayType == TABLE_DIR)
			return "TableDir";
		if (displayType == YES_NO)
			return "YesNo";
		if (displayType == LOCATION)
			return "Location";
		if (displayType == NUMBER)
			return "Number";
		if (displayType == BINARY)
			return "Binary";
		if (displayType == TIME)
			return "Time";
		if (displayType == ACCOUNT)
			return "Account";
		if (displayType == ROW_ID)
			return "RowID";
		if (displayType == COLOR)
			return "Color";
		if (displayType == BUTTON)
			return "Button";
		if (displayType == QUANTITY)
			return "Quantity";
		if (displayType == SEARCH)
			return "Search";
		if (displayType == LOCATOR)
			return "Locator";
		if (displayType == IMAGE)
			return "Image";
		if (displayType == ASSIGNMENT)
			return "Assignment";
		if (displayType == MEMO)
			return "Memo";
		if (displayType == PATTRIBUTE)
			return "PAttribute";
		if (displayType == TEXT_LONG)
			return "TextLong";
		if (displayType == COST_PRICE)
			return "CostPrice";
		if (displayType == FILE_PATH)
			return "FilePath";
		if (displayType == FILE_NAME)
			return "FileName";
		if (displayType == URL)
			return "URL";
		if (displayType == PRINTER_NAME)
			return "PrinterName";
		//
		return "UNKNOWN DisplayType=" + displayType;
	}	//	getDescription
	
	
	/**
	 * Get input Type
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 01/05/2012, 04:05:28
	 * @param displayType
	 * @return
	 * @return int
	 */
	public static int getInputType(int displayType) {
		int inputType = 0;
		if(isText(displayType)) {
			if(displayType == TEXT
					|| displayType == TEXT_LONG
					|| displayType == MEMO)
				inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE 
								| InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
			else if(displayType == URL)
				inputType = InputType.TYPE_TEXT_VARIATION_URI;
			else
				inputType = InputType.TYPE_CLASS_TEXT 
								| InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
		}
    	else if(displayType == INTEGER)
    		inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE;
    	else if(isNumeric(displayType))
    		inputType = InputType.TYPE_CLASS_NUMBER 
    									| InputType.TYPE_NUMBER_FLAG_DECIMAL 
    									| InputType.TYPE_NUMBER_FLAG_SIGNED
    									| InputType.TYPE_CLASS_PHONE;
    	else if(isDate(displayType))
    		inputType = InputType.TYPE_CLASS_DATETIME;
    	//	Default
		return inputType;
	}
	
	/**
	 * Get BigDecimal format
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 03/02/2014, 21:53:00
	 * @param value
	 * @param ctx
	 * @return
	 * @return BigDecimal
	 */
	public static BigDecimal getNumber(Context ctx, String value, int displayType) {
		try {
			if(value != null 
					&& value.length() > 0) {
				DecimalFormat dFormat = getNumberFormat(ctx, displayType);
				//	
				dFormat.setParseBigDecimal(true);
				BigDecimal parsed = (BigDecimal)dFormat.parse(value, new ParsePosition(0));
				return parsed;
			}
		} catch (Exception e) {
			if(ctx != null)
				LogM.log(ctx, DisplayType.class, Level.SEVERE, "Not Parsed Value = " + value);
		}
		//	
		return Env.ZERO;
	}
	
	/**
	 * Get BigDecimal from String
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/10/2014, 16:43:08
	 * @param value
	 * @param displayType
	 * @return
	 * @return BigDecimal
	 */
	public static BigDecimal getNumber(String value, int displayType) {
		return getNumber(null, value, displayType);
	}
	
	/**
	 * Get BigDecimal from String
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 17/10/2014, 16:43:47
	 * @param value
	 * @return
	 * @return BigDecimal
	 */
	public static BigDecimal getNumber(String value) {
		return getNumber(null, value, AMOUNT);
	}
	
	/**
	 * Get Number As Double and valid null
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Number
	 * @return
	 * @return double
	 */
	public static double getNumberAsDouble(BigDecimal p_Number) {
		if(p_Number == null)
			return 0.0;
		//	Default
		return p_Number.doubleValue();
	}
	
	/**
	 * Get a valid Number
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Number
	 * @return
	 * @return BigDecimal
	 */
	public static BigDecimal getValidNumber(BigDecimal p_Number) {
		if(p_Number == null)
			return Env.ZERO;
		//	Default
		return p_Number;
	}
	
	
	/**
	 * Get Date As Long, if is null then retun (-1)
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Date
	 * @return
	 * @return long
	 */
	public static long getDataAsLong(Date p_Date) {
		if(p_Date == null)
			return -1;
		//	Default
		return p_Date.getTime();
	}
	
	
	/**
	 * Get Date
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 13/03/2014, 11:54:18
	 * @param date
	 * @return
	 * @return Date
	 */
	public static Date getDate(String date) {
		try {
			SimpleDateFormat format = getTimestampFormat_Default();
			return format.parse(date);
		} catch (ParseException e) {
			LogM.log(Env.getCtx(), DisplayType.class, Level.SEVERE, "Error Parsing", e);
		}
		return null;
	}
}	//	DisplayType
