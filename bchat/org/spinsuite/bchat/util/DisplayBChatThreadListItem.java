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
import java.util.Date;

import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;

import android.graphics.Bitmap;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class DisplayBChatThreadListItem {
	
	
	/**
	 * Full Constructor
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_SPS_BC_Request_UUID
	 * @param p_Name
	 * @param p_Type
	 * @param p_TopicName
	 * @param p_LastMsg
	 * @param p_LastFileName
	 * @param p_Time
	 * @param p_Image
	 */
	public DisplayBChatThreadListItem(String p_SPS_BC_Request_UUID, String p_Name, 
			String p_Type, String p_TopicName, 
			String p_LastMsg, String p_LastFileName, 
			Date p_Time, Bitmap p_Image) {
		setSPS_BC_Request_UUID(p_SPS_BC_Request_UUID);
		setName(p_Name);
		setType(p_Type);
		setTopicName(p_TopicName);
		setLastMsg(p_LastMsg);
		setLastFileName(p_LastFileName);
		setTime(p_Time);
		setImage(p_Image);
	}
	
	/**	Request Identifier	*/
	private String 		m_SPS_BC_Request_UUID 	= null;
	/**	Name				*/
	private String		m_Name 					= null;
	/**	Type				*/
	private String		m_Type 					= null;
	/**	Topic				*/
	private String		m_Topic 				= null;
	/**	Last Message		*/
	private String 		m_LastMsg 				= null;
	/**	Last FileName		*/
	private String 		m_LastFileName 			= null;
	/**	Time				*/
	private Date		m_Time 					= null;
	/**	Image				*/
	private Bitmap 		m_Image 				= null;
	
	
	/**
	 * Get Request Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getSPS_BC_Request_UUID(){
		return m_SPS_BC_Request_UUID;
	}
	
	/**
	 * Set Request Identifier
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_SPS_BC_Request_UUID
	 * @return void
	 */
	public void setSPS_BC_Request_UUID(String p_SPS_BC_Request_UUID){
		this.m_SPS_BC_Request_UUID = p_SPS_BC_Request_UUID;
	}
	
	/**
	 * Set Image
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 09:56:51
	 * @param m_Image
	 * @return void
	 */
	public void setImage(Bitmap m_Image){
		this.m_Image = m_Image;
	}
	
	/**
	 * Get Image
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 09:57:16
	 * @return
	 * @return ImageView
	 */
	public Bitmap getImage(){
		return m_Image;
	}
	
	/**
	 * Set Last Msg
	 * @author Yamel Senih 28/04/2012, 00:25:17
	 * @return
	 * @return String
	 */
	public String getLastMsg(){
		return m_LastMsg;
	}
	
	/**
	 * Set Last Message
	 * @author Yamel Senih 01/08/2012, 10:04:18
	 * @param p_LastMsg
	 * @return void
	 */
	public void setLastMsg(String p_LastMsg){
		this.m_LastMsg = p_LastMsg;
	}
	
	/**
	 * Get Last File Name
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getLastFileName(){
		return m_LastFileName;
	}
	
	/**
	 * Set Last File Name
	 * @author Yamel Senih 01/08/2012, 10:04:18
	 * @param p_LastFileName
	 * @return void
	 */
	public void setLastFileName(String p_LastFileName){
		this.m_LastFileName = p_LastFileName;
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
	 * Set Time
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Time
	 * @return void
	 */
	public void setTime(Date p_Time) {
		m_Time = p_Time;
	}
	
	/**
	 * Get Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getName() {
		return m_Name;
	}
	
	/**
	 * Set Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Status
	 * @return void
	 */
	public void setName(String p_Status) {
		m_Name = p_Status;
	}
	
	/**
	 * Set Type
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getType() {
		return m_Type;
	}
	
	/**
	 * Get Type
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Type
	 * @return void
	 */
	public void setType(String p_Type) {
		m_Type = p_Type;
	}
	
	/**
	 * Get Topic
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return String
	 */
	public String getTopicName() {
		return m_Topic;
	}
	
	/**
	 * Set Topic
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_TopicName
	 * @return void
	 */
	public void setTopicName(String p_TopicName) {
		m_Topic = p_TopicName;
	}
}
