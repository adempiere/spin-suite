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
import org.spinsuite.util.Env;

import android.content.Context;
import android.database.Cursor;

/**
 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a>
 *
 */
public class MCountry extends X_C_Country {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/10/2014, 22:36:56
	 * @param ctx
	 * @param C_Country_ID
	 * @param conn
	 */
	public MCountry(Context ctx, int C_Country_ID, DB conn) {
		super(ctx, C_Country_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/10/2014, 22:36:56
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MCountry(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * Get Country from Country Code
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/10/2014, 21:02:47
	 * @param ctx
	 * @param p_CountryCode
	 * @return
	 * @return MCountry
	 */
	public static MCountry getCountryFromCode(Context ctx, String p_CountryCode) {
		int m_C_Contry_ID = getC_Country_IDFromCode(ctx, p_CountryCode);
		//	Return Country
		if(m_C_Contry_ID > 0)
			return new MCountry(ctx, m_C_Contry_ID, null);
		//	Default
		return null;
	}
	
	/**
	 * Get Country
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/10/2014, 21:34:07
	 * @param ctx
	 * @param p_C_Country_ID
	 * @return
	 * @return MCountry
	 */
	public static MCountry getCountry(Context ctx, int p_C_Country_ID) {
		//	
		if(p_C_Country_ID <= 0)
			return null;
		//	Default
		return new MCountry(ctx, p_C_Country_ID, null);
	}
	
	/**
	 * Get Base Country
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/10/2014, 21:26:06
	 * @param ctx
	 * @return
	 * @return MCountry
	 */
	public static MCountry getBaseCountry(Context ctx) {
		int m_C_Contry_ID = getC_Country_IDFromCode(ctx, Env.BASE_COUNTRY_CODE);
		//	Return Country
		if(m_C_Contry_ID > 0)
			return new MCountry(ctx, m_C_Contry_ID, null);
		//	Default
		return null;
	}
	
	/**
	 * Get Country ID from Country Code
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 14/10/2014, 21:05:06
	 * @param ctx
	 * @param p_CountryCode
	 * @return
	 * @return int
	 */
	public static int getC_Country_IDFromCode(Context ctx, String p_CountryCode) {
		//	
		int m_C_Contry_ID = DB.getSQLValue(ctx, "SELECT c.C_Country_ID "
				+ "FROM C_Country c "
				+ "WHERE c.CountryCode = ?", p_CountryCode);
		//	Default Country
		return m_C_Contry_ID;
	}

}
