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
package org.spinsuite.bchat.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class DisplayBChatThreadItem {
	
	/**
	 * 
	 * *** Full Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_SPS_BC_Message_UUID
	 * @param p_Text
	 * @param p_SPS_BC_Request_UUID
	 * @param p_AD_User_ID
	 * @param p_UserName
	 * @param p_Type
	 * @param p_Status
	 * @param p_Time
	 * @param p_FileName
	 * @param p_Attachment
	 */
	public DisplayBChatThreadItem(String p_SPS_BC_Message_UUID, 
			String p_SPS_BC_Request_UUID, int p_AD_User_ID, String p_UserName, String p_Text, String p_Type, String p_Status, Date p_Time, 
			String p_FileName, byte[] p_Attachment) {
		setSPS_BC_Message_UUID(p_SPS_BC_Message_UUID);
		setSPS_BC_Request_UUID(p_SPS_BC_Request_UUID);
		setAD_User_ID(p_AD_User_ID);
		setUserName(p_UserName);
		setText(p_Text);
		setType(p_Type);
		setStatus(p_Status);
		setTime(p_Time);
		setFileName(p_FileName);
		setAttachment(p_Attachment);
	}

	/**	Message Identifier	*/
	private String 		m_SPS_BC_Message_UUID 	= null;
	/**	Request Identifier	*/
	private String 		m_SPS_BC_Request_UUID 	= null;
	/**	User Identifier		*/
	private int 		m_AD_User_ID 			= 0;
	/**	User Name			*/
	private String 		m_UserName 				= null;
	/**	Text			*/
	private String 		m_Text 					= null;
	/**	Message Type		*/
	private String 		m_Type 					= null;
	/**	Message Status		*/
	private String 		m_Status 				= null;
	/**	Time				*/
	private Date		m_Time 					= null;
	/**	File Name			*/
	private String 		m_FileName 				= null;
	/**	File				*/
	private	byte[]		m_Attachment 			= null;
	
	/**
	 * Set Message Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_SPS_BC_Message_UUID
	 * @return void
	 */
	public void setSPS_BC_Message_UUID(String p_SPS_BC_Message_UUID) {
		m_SPS_BC_Message_UUID = p_SPS_BC_Message_UUID;
	}
	
	
	/**
	 * Get Message Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getSPS_BC_Message_UUID() {
		return m_SPS_BC_Message_UUID;
	}
	
	/**
	 * Set Text Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Text
	 * @return void
	 */
	public void setText(String p_Text) {
		m_Text = p_Text;
	}
	
	/**
	 * Get Text Message
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getText() {
		return m_Text;
	}
	
	/**
	 * Set Request Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_SPS_BC_Request_UUID
	 * @return void
	 */
	public void setSPS_BC_Request_UUID(String p_SPS_BC_Request_UUID) {
		m_SPS_BC_Request_UUID = p_SPS_BC_Request_UUID;
	}
	
	/**
	 * Get Requeste Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getSPS_BC_Request_UUID() {
		return m_SPS_BC_Request_UUID;
	}
	
	/**
	 * Set User Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_AD_User_ID
	 * @return void
	 */
	public void setAD_User_ID(int p_AD_User_ID) {
		m_AD_User_ID = p_AD_User_ID;
	}
	
	/**
	 * Get User Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return int
	 */
	public int getAD_User_ID() {
		return m_AD_User_ID;
	}
	
	/**
	 * Set User Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_UserName
	 * @return void
	 */
	public void setUserName(String p_UserName) {
		m_UserName = p_UserName;
	}
	
	/**
	 * Get User Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getUserName() {
		return m_UserName;
	}
	
	/**
	 * Set File Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_FileName
	 * @return void
	 */
	public void setFileName(String p_FileName) {
		m_FileName = p_FileName;
	}
	
	/**
	 * Get Message Attachment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return byte[]
	 */
	public byte[] getAttachment() {
		return m_Attachment;
	}
	
	/**
	 * Set Attachment
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Attachment
	 * @return void
	 */
	public void setAttachment(byte[] p_Attachment) {
		m_Attachment = p_Attachment;
	}
	
	/**
	 * Get File Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getFileName() {
		return m_FileName;
	}
	
	/**
	 * 	Set Message Type
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Type
	 * @return void
	 */
	public void setType(String p_Type) {
		m_Type = p_Type;
	}
	
	/**
	 * Get Message Type
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getType() {
		return m_Type;
	}
	
	/**
	 * Set Message Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Status
	 * @return void
	 */
	public void setStatus(String p_Status) {
		m_Status = p_Status;
	}
	
	/**
	 * Get Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getStatus() {
		return m_Status;
	}
	
	/**
	 * Get Time
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return Date
	 */
	public Date getTime() {
		return m_Time;
	}
	
	/**
	 * Get Value as String formatted
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getTimeAsString() {
		//	Valid Null Value
		if(m_Time == null)
			return null;
		//	Get Date Format
		SimpleDateFormat sdf = DisplayType.getDateFormat(Env.getCtx(), DisplayType.TIME, "h:mm a");
		//	Return Value
		return sdf.format(m_Time);
	}
	
	/**
	 * Get Date As String
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getDateAsString() {
		//	Valid Null Value
		if(m_Time == null)
			return null;
		//	Get Date Format
		SimpleDateFormat sdf = DisplayType.getDateFormat(Env.getCtx(), DisplayType.DATE);
		//	Return Value
		return sdf.format(m_Time);
	}
	
	/**
	 * Set Time
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Time
	 * @return void
	 */
	public void setTime(Date p_Time) {
		m_Time = p_Time;
	}
	
	@Override
	public String toString() {
		return "DisplayBChatThreadItem [m_SPS_BC_Message_UUID="
				+ m_SPS_BC_Message_UUID + ", m_SPS_BC_Request_UUID="
				+ m_SPS_BC_Request_UUID + ", m_AD_User_ID=" + m_AD_User_ID
				+ ", m_UserName=" + m_UserName + ", m_Text=" + m_Text
				+ ", m_Type=" + m_Type + ", m_Status=" + m_Status + ", m_Time="
				+ m_Time + ", m_FileName=" + m_FileName + ", m_Attachment="
				+ Arrays.toString(m_Attachment) + "]";
	}
	
	/**
	 * Get For Copy
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getCopy() {
		return m_UserName + "(" + getDateAsString() + ", " + getTimeAsString() + "): " + m_Text;
	}
}
