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

import org.spinsuite.util.DisplayRecordItem;

import android.graphics.Bitmap;
import android.os.Parcel;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
 *
 */
public class DisplayBChatContactItem extends DisplayRecordItem {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 09:42:02
	 */
	public DisplayBChatContactItem() {
		super();
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 09:42:02
	 * @param pRecord_ID
	 * @param pValue
	 */
	public DisplayBChatContactItem(int pRecord_ID, String pValue) {
		super(pRecord_ID, pValue);
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 09:42:02
	 * @param pRecord_ID
	 * @param pValue
	 * @param pImageURL
	 */
	public DisplayBChatContactItem(int pRecord_ID, String pValue, String pImageURL) {
		super(pRecord_ID, pValue, pImageURL);
	}
	
	/**
	 * With Image
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 09:57:36
	 * @param pRecord_ID
	 * @param pValue
	 * @param m_Description
	 * @param m_Image
	 */
	public DisplayBChatContactItem(int pRecord_ID, String pValue, String m_Description, Bitmap m_Image) {
		super(pRecord_ID, pValue, null);
		setImage(m_Image);
		setDescription(m_Description);
	}

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com 08/05/2014, 09:42:02
	 * @param parcel
	 */
	public DisplayBChatContactItem(Parcel parcel) {
		super(parcel);
	}
	
	/**	Image				*/
	private Bitmap 		m_Image = null;
	/**	Description			*/
	private String 		m_Description = null;
	
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
}
