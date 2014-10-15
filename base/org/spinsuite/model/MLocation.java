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
public class MLocation extends X_C_Location {

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/10/2014, 22:40:42
	 * @param ctx
	 * @param C_Location_ID
	 * @param conn
	 */
	public MLocation(Context ctx, int C_Location_ID, DB conn) {
		super(ctx, C_Location_ID, conn);
	}

	/**
	 * *** Constructor ***
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 13/10/2014, 22:40:42
	 * @param ctx
	 * @param rs
	 * @param conn
	 */
	public MLocation(Context ctx, Cursor rs, DB conn) {
		super(ctx, rs, conn);
	}
	
	/**
	 * Get Location from ID
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 2:38:43
	 * @param ctx
	 * @param p_C_Location_ID
	 * @param conn
	 * @return
	 * @return MLocation
	 */
	public static MLocation get(Context ctx, int p_C_Location_ID, DB conn) {
		if(p_C_Location_ID <= 0)
			return null;
		//	
		return new MLocation(ctx, p_C_Location_ID, conn);
	}
	
	/**
	 * 	Get (local) Region Name
	 *	@return	region Name or ""
	 */
	public String getRegionName() {
		return getRegionName(false);
	}	//	getRegionName

	/**
	 * 	Get Region Name
	 * 	@param getFromRegion get from region (not locally)
	 *	@return	region Name or ""
	 */
	public String getRegionName (boolean getFromRegion) {
		MCountry country = getCountry();
		if(country == null)
			return null;
		//	
		if (getFromRegion && getCountry().isHasRegion() 
			&& getRegion() != null)
		{
			super.setRegionName("");	//	avoid duplicates
			return getRegion().getName();
		}
		//
		String regionName = super.getRegionName();
		if (regionName == null)
			regionName = "";
		return regionName;
	}	//	getRegionName
	
	/**
	 * Get Country Object
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:00:14
	 * @return
	 * @return MCountry
	 */
	public MCountry getCountry() {
		//	Valid to default
		if(getC_Country_ID() == 0) {
			return MCountry.getBaseCountry(getCtx(), get_Connection());
		}
		//	
		return MCountry.getCountry(getCtx(), getC_Country_ID(), get_Connection());
	}

	/**
	 * Get Country Object
	 * @author <a href="mailto:yamelsenih@gmail.com">Yamel Senih</a> 15/10/2014, 16:00:14
	 * @return
	 * @return MCountry
	 */
	public MRegion getRegion() {
		//	Valid to default
		if(getC_Region_ID() == 0)
			return null;
		//	
		return MRegion.getRegion(getCtx(), getC_Region_ID(), get_Connection());
	}
	
}
