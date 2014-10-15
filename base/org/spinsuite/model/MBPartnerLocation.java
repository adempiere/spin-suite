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
 * Contributor(s): Yamel Senih www.erpconsultoresyasociados.com                      *
 *************************************************************************************/
package org.spinsuite.model;

import org.spinsuite.base.DB;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MBPartnerLocation extends X_C_BPartner_Location {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/10/2014, 23:05:09
	 * @param ctx
	 * @param C_BPartner_Location_ID
	 * @param conn
	 */
	public MBPartnerLocation(Context ctx, int C_BPartner_Location_ID, DB conn) {
		super(ctx, C_BPartner_Location_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/10/2014, 23:05:09
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MBPartnerLocation(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	@Override
	protected boolean beforeSave(boolean isNew) {
		int m_unique = MSysConfig.getIntValue(getCtx(), "START_VALUE_BPLOCATION_NAME", 0, 
				getAD_Client_ID(), getAD_Org_ID(), get_Connection());
		String m_uniqueName = getName();
		MLocation address = MLocation.get(getCtx(), getC_Location_ID(), get_Connection());
		if (m_unique < 0 || m_unique > 4)
			m_unique = 0;
		if (m_uniqueName != null && m_uniqueName.equals(".")) {
			//	default
			m_uniqueName = null;
			m_uniqueName = makeUnique(address, m_unique, m_uniqueName);
		}
		//	Set Name
		setName(m_uniqueName);
		return super.beforeSave(isNew);
	}
	
	/**
	 * Make Unique Name
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:42:09
	 * @param address
	 * @param m_unique
	 * @param m_uniqueName
	 * @return
	 * @return String
	 */
	private String makeUnique (MLocation address, int m_unique, String m_uniqueName) {
		//	Valid Address
		if(address == null)
			return ".";
		m_uniqueName = "";

		//	0 - City
		if (m_unique >= 0 || m_uniqueName.length() == 0)
		{
			String xx = address.getCity(); 
			if (xx != null && xx.length() > 0)
				m_uniqueName = xx;
		}
		//	1 + Address1
		if (m_unique >= 1 || m_uniqueName.length() == 0)
		{
			String xx = address.getAddress1();
			if (xx != null && xx.length() > 0)
			{
				if (m_uniqueName.length() > 0)
					m_uniqueName += " ";
				m_uniqueName += xx;
			}
		}
		//	2 + Address2
		if (m_unique >= 2 || m_uniqueName.length() == 0)
		{
			String xx = address.getAddress2();
			if (xx != null && xx.length() > 0)
			{
				if (m_uniqueName.length() > 0)
					m_uniqueName += " ";
				m_uniqueName += xx;
			}
		}
		//	3 - Region	
		if (m_unique >= 3 || m_uniqueName.length() == 0)
		{
			String xx = address.getRegionName(true);
			if (xx != null && xx.length() > 0)
			{
				if (m_uniqueName.length() > 0)
					m_uniqueName += " ";
				m_uniqueName += xx;
			}
		}
		//	4 - ID	
		if (m_unique >= 4 || m_uniqueName.length() == 0)
		{
			int id = get_ID();
			if (id == 0)
				id = address.get_ID();
			m_uniqueName += "#" + id;		
		}
		//	Return
		return m_uniqueName;
	}	//	makeUnique
}
