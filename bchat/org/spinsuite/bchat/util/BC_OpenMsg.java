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
package org.spinsuite.bchat.util;

import java.util.Vector;

import org.spinsuite.sync.content.SyncParent;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com Apr 14, 2015, 10:13:46 AM
 *
 */
public class BC_OpenMsg {

	/**
	 * *** Constructor ***
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 */
	private BC_OpenMsg() {
		m_OpenListMsg = new Vector<SyncParent>();
	}
	
	/**	Instance						*/
	private static BC_OpenMsg 	m_Instance = null;
	/**	List for Send					*/
	private Vector<SyncParent>	m_OpenListMsg = null;
	
	/**
	 * Get Actual Instance
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @return
	 * @return BC_OpenMsg
	 */
	public static synchronized BC_OpenMsg getInstance() {
		if(m_Instance == null) {
			m_Instance = new BC_OpenMsg();
		}
		//	Default Return
		return m_Instance;
	}
	
	/**
	 * Add New Msg to List
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param p_Msg
	 * @return void
	 */
	public synchronized void addMsg(SyncParent p_Msg) {
		m_OpenListMsg.add(p_Msg);
	}
	
	/**
	 * Get Open Msg
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpcya.com
	 * @param delete
	 * @return
	 * @return SyncParent
	 */
	public synchronized SyncParent getOpenMsg(boolean delete) {
		//	Verify if exists
		if(m_OpenListMsg.size() == 0)
			return null;
		SyncParent m_Msg =  m_OpenListMsg.firstElement();
		//	Delete
		if(delete) {
			m_OpenListMsg.remove(m_Msg);
		}
		//	Default Return 
		return m_Msg;
	}
	
}
