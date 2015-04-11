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

import org.spinsuite.util.DisplayRecordItem;
import org.spinsuite.util.DisplayType;
import org.spinsuite.util.Env;

import android.graphics.Bitmap;
import android.os.Parcel;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class DisplayBChatThreadListItem extends DisplayRecordItem {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 09:42:02
	 */
	public DisplayBChatThreadListItem() {
		super();
	}
	
	/**
	 * With Image
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 09:57:36
	 * @param p_Record_ID
	 * @param p_Value
	 * @param p_Description
	 * @param p_Image
	 * @param p_Time
	 * @param p_Status
	 */
	public DisplayBChatThreadListItem(int p_Record_ID, String p_Value, String p_Description, 
			Bitmap p_Image, Date p_Time, String p_Status) {
		super(p_Record_ID, p_Value, null);
		setImage(p_Image);
		setDescription(p_Description);
		setTime(p_Time);
		setStatus(p_Status);
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 09:42:02
	 * @param parcel
	 */
	public DisplayBChatThreadListItem(Parcel parcel) {
		super(parcel);
	}
	
	/**	Image				*/
	private Bitmap 		m_Image = null;
	/**	Description			*/
	private String 		m_Description = null;
	/**	Time				*/
	private Date		m_Time = null;
	/**	Status				*/
	private String		m_Status = null;
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
	 * Set Image Name
	 * @author Yamel Senih 28/04/2012, 00:25:17
	 * @return
	 * @return String
	 */
	public String getDescription(){
		return m_Description;
	}
	
	/**
	 * Set Activity Description
	 * @author Yamel Senih 01/08/2012, 10:04:18
	 * @param description
	 * @return void
	 */
	public void setDescription(String description){
		this.m_Description = description;
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
	public String getStatus() {
		return m_Status;
	}
	
	/**
	 * Set Status
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Status
	 * @return void
	 */
	public void setStatus(String p_Status) {
		m_Status = p_Status;
	}
}
