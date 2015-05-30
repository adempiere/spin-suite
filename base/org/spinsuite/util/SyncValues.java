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
	public static final String 		DEFAULT_SOAP_URL 	= "http://test-spinsuite.erpcya.com:9080";
	public static final String 		DEFAULT_URL_PATH	= "ADInterface/services/";
	public static final String 		DEFAULT_IL_VALUE	= "SpinSuiteService";
	public static final String 		DEFAULT_NAME_SPACE 	= "http://www.erpcya.com/";
	public static final String 		DEFAULT_METHOD 		= "InitialLoad";
	public static final String 		DEFAULT_AD_USER 	= "SuperUser";
	public static final String 		DEFAULT_AD_PASS 	= "System";
	public static final int 		DEFAULT_TIMEOUT 	= 60;
	/**	Key For Map						*/
	public static final String 		KEY_SOAP_URL 		= "Sync_SoapURL";
	public static final String 		KEY_PATH_URL 		= "Sync_PathURL";
	public static final String 		KEY_IL_VALUE 		= "Sync_InitialLoadValue";
	public static final String 		KEY_NAME_SPACE 		= "Sync_NameSpace";
	public static final String 		KEY_METHOD 			= "Sync_Method";
	public static final String 		KEY_NET_SERVICE		= "Sync_NetService";
	public static final String 		KEY_USER 			= "Sync_User";
	public static final String 		KEY_PASS 			= "Sync_Pass";
	public static final String 		KEY_SOAP_ACTION 	= "Sync_SoapAction";
	public static final String 		KEY_TIMEOUT 		= "Sync_Timeout";
	/**	Broadcast Type Filter			*/
	/**	Initial Load Filter				*/
	public static final String 		BC_IL_FILTER 		= "BC_IL_Filter";
	/**	Business Chat Filter			*/
	public static final String 		BC_BC_FILTER 		= "BC_BC_Filter";
	/**	Synchronization					*/
	public static final String 		BC_SS_FILTER 		= "BC_SS_Filter";
	/**	Broadcast Values				*/
	public static final String 		BC_KEY_MSG 			= "BCV_Msg";
	public static final String 		BC_KEY_SUB_MSG 		= "BCV_SubMsg";
	public static final String 		BC_KEY_MSG_TYPE 	= "BCV_MsgType";
	public static final String 		BC_KEY_PROGRESS 	= "BCV_Progress";
	public static final String 		BC_KEY_STATUS 		= "BCV_Status";
	public static final String 		BC_KEY_MAX_VALUE 	= "BCV_MaxValue";
	/**	Message Type						*/
	public static final String 		BC_MSG_TYPE_ERROR 	= "BCV_MT_Error";
	public static final String 		BC_MSG_TYPE_PROGRESS= "BCV_MT_Progress";
	public static final String 		BC_MSG_TYPE_MSG 	= "BCV_MT_Msg";
	/**	Status								*/
	public static final String 		BC_STATUS_START 	= "BCV_ST_Start";
	public static final String 		BC_STATUS_PROGRESS 	= "BCV_ST_Progress";
	public static final String 		BC_STATUS_END 		= "BCV_ST_End";
	/** Web Service Method Query Data	*/
	public static final String 		WSMQueryData 		= "queryData";
	/** Web Service Method Create Data	*/
	public static final String 		WSMCreateData 		= "createData";
	/** Web Service Method Create Data	*/
	public static final String 		WSMUpdateData 		= "updateData";
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
		String time = hours + "h :" + minutes + "m :" + seconds + "s"; 
		//	Set Last Message
		return time;
	}
}
