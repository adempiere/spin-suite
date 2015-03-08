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
package org.spinsuite.util;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Mar 8, 2015, 7:10:14 AM
 *
 */
public class SyncValues {
	/**	Default Values for Web Services	*/
	public static final String 		DEFAULT_SOAP_URL 	= "http://192.168.1.254:8081";
	public static final String 		DEFAULT_URL_PATH	= "ADInterface/services/";
	public static final String 		DEFAULT_IL_VALUE	= "SpinSuiteService";
	public static final String 		DEFAULT_NAME_SPACE 	= "http://www.erpcya.com/";
	public static final String 		DEFAULT_METHOD 		= "InitialLoad";
	public static final String 		DEFAULT_USER 		= "SuperUser";
	public static final String 		DEFAULT_PASS 		= "System";
	/** Web Service Method Query Data	*/
	public static final String 		WSMQueryData 		= "queryData";
	/** Web Service Method Create Data	*/
	public static final String 		WSMCreateData 		= "createData";
	/** IS Net Web Service				*/
	public static final boolean 	IsNetService 		= true;
	/** Data Set						*/
	public static final String 		WSRespDataSet 		= "DataSet";
	/** Data Row						*/
	public static final String 		WSRespDataRow 		= "DataRow";
	/** Column Name						*/ 
	public static final String 		WSColumn 			= "column";
	/** Value 							*/
	public static final String 		WSValue 			= "val";
	/** Quantity of Pages 				*/
	public static final String 		WSQtyPages 			= "QtyPages";
	
	/**
	 * Get Valid Url
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param url
	 * @param value
	 * @return
	 * @return String
	 */
	public static String getValidURL(String url, String value) {
		//	Valid Null
		if(url == null)
			return "";
		if(value == null)
			value = "";
		//	Valid Url
		if(url.lastIndexOf("/") != (url.length() - 1)) {
			url += "/";
		}
		//	Add Path
		url += DEFAULT_URL_PATH;
		//	Add Value
		url += value;
		//	Return
		return url;
	}
	
	/**
	 * Get Valid Url for Initial Load
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param url
	 * @return
	 * @return String
	 */
	public static String getInitialUrl(String url) {
		return getValidURL(url, DEFAULT_IL_VALUE);
	}
	
	/**
	 * Get Duration from milliseconds
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param duration
	 * @return
	 * @return String
	 */
	public static String getDifferenceValue(long duration) {
		//	Get Times
		int hours = (int) ((duration / 1000) / 3600);
		int minutes = (int) (((duration / 1000) / 60) % 60);
		int seconds = (int) ((duration / 1000) % 60);
		//	Show Time
		String time = "H:" + hours + " M:" + minutes + " S:" + seconds; 
		//	Set Last Message
		return time;
	}
}
